/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * MetricEvaluatorManager.java
 *
 * Created on 10-06-2008, 02:11:14 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.evaluators;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import com.scopix.periscope.evaluationmanagement.EvaluationQueue;
import com.scopix.periscope.evaluationmanagement.EvaluationQueueManager;
import com.scopix.periscope.evaluationmanagement.EvaluationState;
import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.evaluationmanagement.Proof;
import com.scopix.periscope.evaluationmanagement.commands.AddEvidenceEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddProofCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetObservedMetricCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetPendingEvaluationCommand;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.queuemanager.QueueManager;
import com.scopix.periscope.periscopefoundation.states.exception.InvalidOperationException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@SpringBean(rootClass = AutomaticEvaluatorManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class AutomaticEvaluatorManager implements InitializingBean {
    
    private Logger log = Logger.getLogger(AutomaticEvaluatorManager.class);
    private static AnalyticsUCPoolExcecutor analyticsUCPoolExcecutor;
    private GetPendingEvaluationCommand pendingEvaluationCommand;
    private Map<String, Object> hashEvaluationAutomatic;
    private CommonEvaluationManager commonEvalManager;
    
    @Override //se cambia por el init
    public void afterPropertiesSet() throws Exception {
        //inicalizamos el pool de pedida a la dll de UC
        //levantamos parametros de configutacion
        Integer analyticsmaxTheadPool = 1;
        try {
            PropertiesConfiguration property = new PropertiesConfiguration("system.properties");
            property.setReloadingStrategy(new FileChangedReloadingStrategy());
            analyticsmaxTheadPool = property.getInteger("ANALYTICS_MAX_THREAD_POOL", analyticsmaxTheadPool);
        } catch (ConfigurationException e) {
            log.warn("Error recuperando configuracion " + e);
        }
        
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        analyticsUCPoolExcecutor = new AnalyticsUCPoolExcecutor(analyticsmaxTheadPool, queue);
    }

    /**
     * Get the first element in METRIC queue
     *
     * @return
     */
    public int getNextAutomaticEvaluation() throws Exception {
        log.debug("[getNextAutomaticEvaluation run] Calling dequeueObject");
        PendingEvaluation pendingEvaluation = null;
        try {
            QueueManager queueManagerAutomatic = new QueueManager(EvaluationQueue.AUTOMATIC);
            while (true) {
                pendingEvaluation = (PendingEvaluation) queueManagerAutomatic.dequeueObject(PendingEvaluation.class);
                
                if (getCommonEvalManager().checkExpiredPendingEvaluation(pendingEvaluation, "AUTOMATIC")) {
                    continue;
                }
                
                if (pendingEvaluation == null || pendingEvaluation.getEvaluationState().getName().equals(
                    EvaluationState.ENQUEUED.getName())) {
                    break;
                }
            }
            log.debug("[getNextAutomaticEvaluation run] pendingEvaluation "
                + pendingEvaluation != null ? pendingEvaluation.getId()
                : null);
            pendingEvaluation.asignToCheck();
        } catch (Exception e) {
            pendingEvaluation.fail();
            log.error("error = " + e.getMessage(), e);
            throw e;
        }
        return pendingEvaluation.getId();
    }

    /**
     * Evaluate the selected observed metric
     *
     * @param observedMetricId
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    public void evaluateAutomaticEvaluation(int automaticEvaluationId) throws ScopixException {
        PendingEvaluation pendingEvaluation = null;
        try {
            pendingEvaluation = getPendingEvaluationCommand().execute(automaticEvaluationId);

            //colocar variable para determinar si se debe o no cambiar el estado del pending evaluation
            boolean changePendingEvaluation = true;
            if (pendingEvaluation != null) {
                String evaluatorName = pendingEvaluation.getObservedSituation().getSituation().getSituationTemplate().
                    getEvidenceSpringBeanEvaluatorName();
                if (evaluatorName != null && evaluatorName.length() > 0) {
                    EvidenceEvaluatorForST evidenceEvaluator = (EvidenceEvaluatorForST) SpringSupport.getInstance().
                        findBeanByClassName(Class.forName(evaluatorName));
                    evidenceEvaluator.evaluate(pendingEvaluation);
                    //pedir al evaluardor la info si se debe o no cambiar el estado del pending Evaluation asociado
                    changePendingEvaluation = evidenceEvaluator.hasChangePendingEvaluation();
                    
                }
            }
            //verificar variable para cambiar estado de PE si corresponde
            if (changePendingEvaluation) {
                log.debug(" Compute Result ");
                pendingEvaluation.computeResult();
                enqueueObservedMetric(automaticEvaluationId);
            }
        } catch (Exception e) {
            log.error("ERROR = " + e, e);
            pendingEvaluation.fail();
            throw new ScopixException(e);
        }
    }

    /**
     *
     * @param automaticEvaluationId
     * @throws ScopixException
     */
    public void enqueueObservedMetric(Integer automaticEvaluationId) throws ScopixException {
        log.info("start [automaticEvaluationId:" + automaticEvaluationId + "]");
        GetObservedMetricCommand getObservedMetricCommand = new GetObservedMetricCommand();
        PendingEvaluation pendingEvaluation = getPendingEvaluationCommand().execute(automaticEvaluationId);
        EvaluationQueueManager evaluationQueueManager = SpringSupport.getInstance().findBeanByClassName(
            EvaluationQueueManager.class);
        log.debug("observed situation:" + pendingEvaluation.getObservedSituation());
        log.debug("observed_metric_size:" + pendingEvaluation.getObservedSituation().getObservedMetrics().size());
        for (ObservedMetric om : pendingEvaluation.getObservedSituation().getObservedMetrics()) {
            log.debug("enqueued [observed_metric:" + om.getId() + "]");
            ObservedMetric observedMetric = getObservedMetricCommand.execute(om.getId());
            observedMetric.setEvaluationState(EvaluationState.ENQUEUED);
            evaluationQueueManager.enqueue(observedMetric, EvaluationQueue.METRIC);
        }
        log.debug("end");
    }

    /**
     * Se utilizan para proceso Automatico de AnaliticsUC
     */
    public int getNextAutomaticAnalyticsEvaluation() throws Exception {
        log.info("start");
        PendingEvaluation pendingEvaluation = null;
        try {
            QueueManager queueManagerAutomaticAnalitics = new QueueManager(EvaluationQueue.AUTOMATIC_ANALITICS);
            while (true) {
                pendingEvaluation = (PendingEvaluation) queueManagerAutomaticAnalitics.dequeueObject(PendingEvaluation.class);
                
                if (getCommonEvalManager().checkExpiredPendingEvaluation(pendingEvaluation, "AUTOMATIC_ANALYTIC")) {
                    continue;
                }
                
                if (pendingEvaluation == null || pendingEvaluation.getEvaluationState().getName().equals(
                    EvaluationState.ENQUEUED.getName())) {
                    break;
                }
            }
            log.debug("pendingEvaluation " + pendingEvaluation != null ? pendingEvaluation.getId() : null);
            pendingEvaluation.asignToCheck();
        } catch (Exception e) {
            pendingEvaluation.fail();
            log.error("error = " + e.getMessage(), e);
            throw e;
        }
        log.info("end");
        return pendingEvaluation.getId();
    }
    
    public void evaluateAutomaticAnalyticsEvaluation(int pendingEvaluationId) {
        //coloca el pending evaluation en el pool para su ejecucion
        AnalyticsUCThread thread = new AnalyticsUCThread();
        thread.init(pendingEvaluationId);
        analyticsUCPoolExcecutor.pause();
        analyticsUCPoolExcecutor.runTask(thread);
        analyticsUCPoolExcecutor.resume();
    }
    
    public void evaluateAnalyticsEvaluation(Integer pendingEvaluationId) {
        log.info("start [peId:" + pendingEvaluationId + "]");
        PendingEvaluation pendingEvaluation = null;
        
        try {
            pendingEvaluation = getPendingEvaluationCommand().execute(pendingEvaluationId);
            List<EvidenceEvaluation> evidenceEvaluations = new ArrayList<EvidenceEvaluation>();

            //
            boolean successEvaluation = true;
            boolean evaluationResult = true;
            
            log.debug("observedMetrics:" + pendingEvaluation.getObservedSituation().getObservedMetrics().size());
            for (ObservedMetric om : pendingEvaluation.getObservedSituation().getObservedMetrics()) {
                //recuperamos la clase del evaluador
                log.debug("om: " + om.getId());
                log.debug("om.getMetric().getMetricTemplate(): " + om.getMetric().getMetricTemplate().getId());
                String evaluatorName = om.getMetric().getMetricTemplate().getEvidenceSpringBeanEvaluatorName();
                if (evaluatorName != null) {
                    EvidenceEvaluatorForMT evidenceEvaluator = (EvidenceEvaluatorForMT) SpringSupport.getInstance().
                        findBeanByClassName(Class.forName(evaluatorName));
                    try {
                        // evaluamos el om
                        log.debug("evidenceEvaluator " + evidenceEvaluator.getClass().getSimpleName());
                        log.debug("before evaluate om.getEvidenceEvaluations:" + om.getEvidenceEvaluations().size());
                        evaluationResult = evidenceEvaluator.evaluate(om, pendingEvaluationId) && evaluationResult;
                        log.debug("after evaluate om.getEvidenceEvaluations:" + om.getEvidenceEvaluations().size());
                        evidenceEvaluations.addAll(om.getEvidenceEvaluations());
                        
                    } catch (ScopixException e) {
                        log.debug("Failed to process " + om, e);
                        //recorrer todos los evidenceEvaluations y borrar los archivos fisicos existentes
                        for (EvidenceEvaluation ee : om.getEvidenceEvaluations()) {
                            Evidence ev = ee.getEvidences().get(0);
                            String datePath = DateFormatUtils.format(ev.getEvidenceDate(), "yyyy\\MM\\dd");
                            String proofPath = ev.getEvidenceServicesServer().getEvidencePath() + om.getMetric().getStore().
                                getCorporate().getName()
                                + "\\" + om.getMetric().getStore().getName() + "\\" + datePath;
                            
                            log.debug("Deleting " + ee.getProofs().size() + " Proofs.");
                            for (Proof proof : ee.getProofs()) {
                                try {
                                    log.debug("Deleting  " + proofPath + "\\" + proof.getPathWithMarks());
                                    FileUtils.forceDelete(new File(proofPath + "\\" + proof.getPathWithMarks()));
                                    log.debug("Deleting  " + proofPath + "\\" + proof.getPathWithoutMarks());
                                    FileUtils.forceDelete(new File(proofPath + "\\" + proof.getPathWithoutMarks()));
                                } catch (IOException ioe) {
                                    log.error("No es posible borrar file de proof "
                                        + "[" + proofPath + "\\" + proof.getPathWithMarks() + "]"
                                        + "[" + proofPath + "\\" + proof.getPathWithoutMarks() + "]" + ioe);
                                }
                            }
                        }
                        
                        successEvaluation = false;
                    }
                } else {
                    successEvaluation = false;
                }
            }
            
            log.debug("almacenando EE " + evidenceEvaluations.size());
            // si no hay excepcion guardamos todas las evaluaciones, movemos los
            // arhivos a las rutas definitivas
            // y encolamos el PE en la cola de OM
            // alamacenamos todas los EE
            AddEvidenceEvaluationCommand addEvidenceEvaluationCommand = new AddEvidenceEvaluationCommand();
            AddProofCommand addProofCommand = new AddProofCommand();
            for (EvidenceEvaluation ee : evidenceEvaluations) {
                addEvidenceEvaluationCommand.execute(ee);
                for (Proof p : ee.getProofs()) {
                    addProofCommand.execute(p);
                }
            }
            
            if (!successEvaluation || !evaluationResult) {
                // si hay excepcion salimos y no guardamos y pasamos el PE a la
                // cola de Operator
                pendingEvaluation.setEvaluationQueue(EvaluationQueue.OPERATOR);
                // el estado antes de salir
                pendingEvaluation.setEvaluationState(EvaluationState.ENQUEUED);
                //cambiar de  encolar no volver a la cola anterior
                enqueuePendingEvaluation(pendingEvaluation);
                //pendingEvaluation.backToQueue();
            } else {
                pendingEvaluation.computeResult();
                enqueueObservedMetric(pendingEvaluationId);
            }
            
        } catch (ScopixException e) {
            pendingEvaluation.fail();
            log.error("error = " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            pendingEvaluation.fail();
            log.error("error = " + e.getMessage(), e);
        } catch (InvalidOperationException e) {
            pendingEvaluation.fail();
            log.error("error = " + e.getMessage(), e);
        } catch (Exception e) {
            pendingEvaluation.fail();
            log.error("error = " + e.getMessage(), e);
        }
        log.info("end [peId:" + pendingEvaluationId + "]");
    }

    /**
     * @return the pendingEvaluationCommand
     */
    public GetPendingEvaluationCommand getPendingEvaluationCommand() {
        if (pendingEvaluationCommand == null) {
            pendingEvaluationCommand = new GetPendingEvaluationCommand();
        }
        return pendingEvaluationCommand;
    }

    /**
     * @param pendingEvaluationCommand the pendingEvaluationCommand to set
     */
    public void setPendingEvaluationCommand(GetPendingEvaluationCommand pendingEvaluationCommand) {
        this.pendingEvaluationCommand = pendingEvaluationCommand;
    }
    
    private void enqueuePendingEvaluation(PendingEvaluation pendingEvaluation) throws ScopixException {
        EvaluationQueueManager evaluationQueueManager = SpringSupport.getInstance().findBeanByClassName(
            EvaluationQueueManager.class);
        
        evaluationQueueManager.enqueueNewEvaluation(pendingEvaluation, pendingEvaluation.getEvaluationQueue());
    }

    /**
     * @return the hashEvaluationAutomatic
     */
    public Map<String, Object> getHashEvaluationAutomatic() {
        if (hashEvaluationAutomatic == null) {
            hashEvaluationAutomatic = new HashMap<String, Object>();
        }
        return hashEvaluationAutomatic;
    }

    /**
     * @param hashEvaluationAutomatic the hashEvaluationAutomatic to set
     */
    public void setHashEvaluationAutomatic(Map<String, Object> hashEvaluationAutomatic) {
        this.hashEvaluationAutomatic = hashEvaluationAutomatic;
    }

    /**
     * @return the commonEvalManager
     */
    public CommonEvaluationManager getCommonEvalManager() {
        if (commonEvalManager == null) {
            commonEvalManager = SpringSupport.getInstance().findBeanByClassName(CommonEvaluationManager.class);
        }
        return commonEvalManager;
    }
}
