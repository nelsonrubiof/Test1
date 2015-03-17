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
 * EvaluationQueueManager.java
 *
 * Created on 27-03-2008, 03:14:53 PM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.evaluationmanagement.commands.GetObservedMetricListSQLCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetObservedSituationListSQLCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetPendingEvaluationListCommand;
import com.scopix.periscope.evaluationmanagement.commands.UpdateObservedMetricCommand;
import com.scopix.periscope.evaluationmanagement.commands.UpdateObservedSituationCommand;
import com.scopix.periscope.evaluationmanagement.commands.UpdatePendingEvaluationCommand;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.queuemanager.Prioritization;
import com.scopix.periscope.periscopefoundation.queuemanager.QueueManager;
import com.scopix.periscope.periscopefoundation.queuemanager.QueueType;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.EvaluationQueueManagerPermissions;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author C�sar Abarza Suazo
 */
@SpringBean(rootClass = EvaluationQueueManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class EvaluationQueueManager implements InitializingBean {

    private Logger log = Logger.getLogger(EvaluationQueueManager.class);

    /**
     *
     * @param queueType
     * @param sessionId
     * @return
     * @throws
     * com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     * @throws
     * com.scopix.periscope.periscopefoundation.exception.PeriscopeSecurityException
     */
    public Prioritization dequeue(QueueType queueType, long sessionId) throws ScopixException {
        log.debug("start");
        SpringSupport.getInstance().findBeanByClassName(com.scopix.periscope.securitymanagement.SecurityManager.class).
                checkSecurity(sessionId, EvaluationQueueManagerPermissions.DEQUEUE_PERMISSION);
        QueueManager queueManager = new QueueManager(queueType);
        Prioritization prioritization = queueManager.dequeueObject(PendingEvaluation.class);
        log.debug("end, result " + prioritization);
        return prioritization;
    }

    /**
     *
     * @param prioritization
     * @param queueType
     * @param sessionId
     */
    public void enqueue(Prioritization prioritization, QueueType queueType, long sessionId) throws ScopixException {
        log.debug("start");
        SpringSupport.getInstance().findBeanByClassName(com.scopix.periscope.securitymanagement.SecurityManager.class).
                checkSecurity(sessionId, EvaluationQueueManagerPermissions.ENQUEUE_PERMISSION);
        QueueManager queueManager = new QueueManager(queueType);
        prioritization = queueManager.enqueue(prioritization);
        if (prioritization instanceof PendingEvaluation) {
            SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class).updatePendingEvaluation(
                    (PendingEvaluation) prioritization, sessionId);
        } else if (prioritization instanceof ObservedMetric) {
            SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class).updateObservedMetric(
                    (ObservedMetric) prioritization, sessionId);
        } else if (prioritization instanceof ObservedSituation) {
            UpdateObservedSituationCommand command = new UpdateObservedSituationCommand();
            command.execute((ObservedSituation) prioritization);
        }
        log.debug("end");
    }

    public void enqueueNewEvaluation(Prioritization prioritization, QueueType queueType) throws ScopixException {
        log.debug("start");
        QueueManager queueManager = new QueueManager(queueType);
        prioritization = queueManager.enqueue(prioritization);
        if (prioritization instanceof PendingEvaluation) {
            SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class).updatePendingEvaluation(
                    (PendingEvaluation) prioritization);
        } else if (prioritization instanceof ObservedMetric) {
            SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class).updateObservedMetric(
                    (ObservedMetric) prioritization);
        } else if (prioritization instanceof ObservedSituation) {
            UpdateObservedSituationCommand command = new UpdateObservedSituationCommand();
            command.execute((ObservedSituation) prioritization);
        }
        log.debug("end");
    }

    /**
     *
     * @param prioritization
     * @param queueType
     * @throws ScopixException
     */
    public void enqueue(Prioritization prioritization, QueueType queueType) throws ScopixException {
        log.debug("start");
        QueueManager queueManager = new QueueManager(queueType);
        prioritization = queueManager.enqueue(prioritization);
        if (prioritization instanceof PendingEvaluation) {
            UpdatePendingEvaluationCommand command = new UpdatePendingEvaluationCommand();
            command.execute((PendingEvaluation) prioritization);
        } else if (prioritization instanceof ObservedMetric) {
            UpdateObservedMetricCommand command = new UpdateObservedMetricCommand();
            command.execute((ObservedMetric) prioritization);
        } else if (prioritization instanceof ObservedSituation) {
            UpdateObservedSituationCommand command = new UpdateObservedSituationCommand();
            command.execute((ObservedSituation) prioritization);
        }
        log.debug("end");
    }

    /**
     *
     * @deprecated se debe utilizar enqueue sin session ya que corresponde a una
     * operacion de sistema
     *
     * @param prioritizations
     * @param queueType
     * @param sessionId
     * @throws ScopixException
     */
    public void enqueue(List<? extends Prioritization> prioritizations, QueueType queueType, long sessionId) throws
            ScopixException {
        log.debug("start");
        SpringSupport.getInstance().findBeanByClassName(com.scopix.periscope.securitymanagement.SecurityManager.class).
                checkSecurity(sessionId, EvaluationQueueManagerPermissions.ENQUEUE_LIST_PERMISSION);
        for (Prioritization prioritization : prioritizations) {
            this.enqueue(prioritization, queueType, sessionId);
        }
        log.debug("end");
    }

    public void enqueue(List<? extends Prioritization> prioritizations, QueueType queueType) throws
            ScopixException {
        log.debug("start");
        for (Prioritization prioritization : prioritizations) {
            this.enqueueNewEvaluation(prioritization, queueType);
        }
        log.debug("end");
    }

    /**
     * This method must be invoked during startup, to reload elements in queue.
     * This method can be invoked to refrresh the queue.
     *
     * @param queueType
     * @param prioritizations
     * @param sessionId
     * @throws ScopixException
     */
    public void refreshQueue(QueueType queueType, List<? extends Prioritization> prioritizations, long sessionId) throws
            ScopixException {
        log.debug("start");
        SpringSupport.getInstance().findBeanByClassName(com.scopix.periscope.securitymanagement.SecurityManager.class).
                checkSecurity(sessionId, EvaluationQueueManagerPermissions.REFRESH_QUEUE_FOR_EVALUATION_QUEUE_MANAGER_PERMISSION);
        QueueManager queueManager = new QueueManager(queueType);
        queueManager.refreshQueue(prioritizations);
        log.debug("end");
    }

    public void refreshMetricQueue() {
        try {
            log.debug("start");
            QueueManager queueManagerMetric = new QueueManager(EvaluationQueue.METRIC);
            GetObservedMetricListSQLCommand getObservedMetricListCommand = new GetObservedMetricListSQLCommand();
            ObservedMetric observedMetricFilter = new ObservedMetric();
            observedMetricFilter.setEvaluationState(EvaluationState.ENQUEUED);
            List<ObservedMetric> observedMetrics = getObservedMetricListCommand.execute(observedMetricFilter);
            log.debug("observedMetrics " + observedMetrics);
            if (observedMetrics != null) {
                log.debug("observedMetrics size " + observedMetrics.size());
                queueManagerMetric.refreshQueue(observedMetrics);
            }
            log.debug("end");
        } catch (ScopixException ex) {
            log.error("Error = " + ex.getMessage());
        }
    }

    public void refreshSituationQueue() {
        try {
            log.debug("start");
            QueueManager queueManagerSituation = new QueueManager(EvaluationQueue.SITUATION);
            GetObservedSituationListSQLCommand getObservedSituationListCommand = new GetObservedSituationListSQLCommand();
            ObservedSituation observedSituationFilter = new ObservedSituation();
            observedSituationFilter.setEvaluationState(EvaluationState.ENQUEUED);
            List<ObservedSituation> observedSituations = getObservedSituationListCommand.execute(observedSituationFilter);
            log.debug("observedSituations " + observedSituations);
            if (observedSituations != null) {
                log.debug("observedSituations size " + observedSituations.size());
                queueManagerSituation.refreshQueue(observedSituations);
            }
            log.debug("end");
        } catch (ScopixException ex) {
            log.error("Error = " + ex.getMessage());
        }

    }

    public int queueSize(QueueType queueType) {
        log.debug("start");
        QueueManager queueManager = new QueueManager(queueType);
        int size = queueManager.getQueueSize(queueType);
        log.debug("end, size = " + size);
        return size;
    }

    /**
     * Init method
     */
    // @Override
    public void afterPropertiesSet() throws Exception {
        log.info("start");
        try {
            //QueueManager queueManagerOperator = new QueueManager(EvaluationQueue.OPERATOR);
            QueueManager queueManagerAutomatic = new QueueManager(EvaluationQueue.AUTOMATIC);
            QueueManager queueManagerMetric = new QueueManager(EvaluationQueue.METRIC);
            QueueManager queueManagerSituation = new QueueManager(EvaluationQueue.SITUATION);
            QueueManager queueManagerAutomaticAnalitics = new QueueManager(EvaluationQueue.AUTOMATIC_ANALITICS);

            //Re enqueue the pending evaluation OPERATOR
            GetPendingEvaluationListCommand command = new GetPendingEvaluationListCommand();
//            PendingEvaluation filter = new PendingEvaluation();
//            filter.setEvaluationQueue(EvaluationQueue.OPERATOR);
//            filter.setEvaluationState(EvaluationState.ENQUEUED);
//            List<PendingEvaluation> pendingEvaluations = command.execute(filter);
//            queueManagerOperator.createQueue();
//            log.debug("[init OPERATOR] Operator Queue created");
//            if (pendingEvaluations != null) {
//                log.debug("[init OPERATOR] pendingEvaluations size " + pendingEvaluations.size());
//                for (PendingEvaluation pendingEvaluation : pendingEvaluations) {
//                    queueManagerOperator.enqueue(pendingEvaluation);
//                }
//            }
            //Re enqueue the pending evaluation AUTOMATIC
            PendingEvaluation filter = new PendingEvaluation();
            filter.setEvaluationQueue(EvaluationQueue.AUTOMATIC);
            filter.setEvaluationState(EvaluationState.ENQUEUED);
            List<PendingEvaluation> pendingEvaluations = command.execute(filter);
            queueManagerAutomatic.createQueue();
            log.debug("[init AUTOMATIC] Automatic Queue created");
            if (pendingEvaluations != null) {
                log.debug("[init AUTOMATIC] pendingEvaluations size " + pendingEvaluations.size());
                for (PendingEvaluation pendingEvaluation : pendingEvaluations) {
                    queueManagerAutomatic.enqueue(pendingEvaluation);
                }
            }
            //Re enqueue the observed metric
            GetObservedMetricListSQLCommand getObservedMetricListCommand = new GetObservedMetricListSQLCommand();
            ObservedMetric observedMetricFilter = new ObservedMetric();
            observedMetricFilter.setEvaluationState(EvaluationState.ENQUEUED);
            List<ObservedMetric> observedMetrics = getObservedMetricListCommand.execute(observedMetricFilter);
            queueManagerMetric.createQueue();
            log.debug("[init METRIC] Metric Queue created");
            if (observedMetrics != null) {
                log.debug("[init METRIC] observedMetrics size " + observedMetrics.size());
                for (ObservedMetric observedMetric : observedMetrics) {
                    queueManagerMetric.enqueue(observedMetric);
                }
            }

            //Re enqueue the observed situation
            GetObservedSituationListSQLCommand getObservedSituationListCommand = new GetObservedSituationListSQLCommand();
            ObservedSituation observedSituationFilter = new ObservedSituation();
            observedSituationFilter.setEvaluationState(EvaluationState.ENQUEUED);
            List<ObservedSituation> observedSituations = getObservedSituationListCommand.execute(observedSituationFilter);
            queueManagerSituation.createQueue();
            log.debug("[init SITUATION] Situation Queue created");
            if (observedSituations != null) {
                log.debug("[init SITUATION] observedSituations size " + observedSituations.size());
                for (ObservedSituation observedSituation : observedSituations) {
                    queueManagerSituation.enqueue(observedSituation);
                }
            }

            //Re enqueue the pending evaluation AUTOMATIC_ANALITICS
            PendingEvaluation filter2 = new PendingEvaluation();
            filter2.setEvaluationQueue(EvaluationQueue.AUTOMATIC_ANALITICS);
            filter2.setEvaluationState(EvaluationState.ENQUEUED);
            List<PendingEvaluation> pendingEvaluations2 = command.execute(filter2);
            queueManagerAutomatic.createQueue();
            log.debug("[init AUTOMATIC_ANALITICS] Automatic Analitics Queue created");
            if (pendingEvaluations2 != null) {
                log.debug("[init AUTOMATIC_ANALITICS] pendingEvaluations size " + pendingEvaluations2.size());
                for (PendingEvaluation pendingEvaluation : pendingEvaluations2) {
                    queueManagerAutomaticAnalitics.enqueue(pendingEvaluation);
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        log.info("end");
    }
}
