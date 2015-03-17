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

import com.scopix.periscope.evaluationmanagement.EvaluationQueue;
import com.scopix.periscope.evaluationmanagement.EvaluationState;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.commands.GetObservedMetricCommand;
import com.scopix.periscope.evaluationmanagement.commands.RemoveMetricEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.UpdateObservedSituationCommand;
import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.queuemanager.QueueManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import com.scopix.periscope.templatemanagement.MetricType;
import com.scopix.periscope.templatemanagement.YesNoType;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@SpringBean(rootClass = MetricEvaluatorManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class MetricEvaluatorManager {

    private Logger log = Logger.getLogger(MetricEvaluatorManager.class);

    /**
     * Get the first element in METRIC queue
     *
     * @return
     */
    public int getNextObservedMetric() throws Exception {
        log.debug("[getNextObservedMetric run] Calling dequeueObject");
        ObservedMetric observedMetric = null;
        try {
            QueueManager queueManagerMetric = new QueueManager(EvaluationQueue.METRIC);
            while (true) {
                observedMetric = (ObservedMetric) queueManagerMetric.dequeueObject(ObservedMetric.class);
                if (observedMetric == null || observedMetric.getEvaluationState().getName().equals(EvaluationState.ENQUEUED.
                        getName())) {
                    break;
                }
            }
            log.debug("[getNextObservedMetric run] observedMetric " + observedMetric);
            observedMetric.asignToCheck();
        } catch (Exception e) {
            log.error("error = " + e.getMessage(), e);
            if (observedMetric != null) {
                observedMetric.fail();
            }
            throw e;
        }
        log.info("end");
        return observedMetric.getId();
    }

    /**
     * Evaluate the selected observed metric
     *
     * @param observedMetricId
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    public void evaluateObservedMetric(int observedMetricId) throws ScopixException {
        log.info("start " + observedMetricId);
        MetricEvaluator metricEvaluator = null;
        ObservedMetric observedMetric = null;
        try {
            GetObservedMetricCommand command = new GetObservedMetricCommand();
            observedMetric = command.execute(observedMetricId);
            //Delete previous evaluation
            if (observedMetric.getMetricEvaluation() != null) {
                RemoveMetricEvaluationCommand removeMetricEvaluationCommand = new RemoveMetricEvaluationCommand();
                removeMetricEvaluationCommand.execute(observedMetric.getMetricEvaluation().getId());
            }
            //Load bags
            observedMetric.getEvidenceEvaluations().isEmpty();
            log.debug("[evaluateMetricQueue run] evidenceEvaluations " + observedMetric.getEvidenceEvaluations().size());
            if (observedMetric.getEvidenceEvaluations().size() > 0) {
                MetricTemplate metricTemplate = observedMetric.getMetric().getMetricTemplate();
                log.debug("[evaluateMetricQueue run] metricTemplate " + metricTemplate);
                log.debug("[evaluateMetricQueue run] metricTemplate.getMetricTypeElement() "
                        + metricTemplate.getMetricTypeElement());
                if (metricTemplate.getMetricSpringBeanEvaluatorName() != null
                        && metricTemplate.getMetricSpringBeanEvaluatorName().length() > 0) {
                    log.debug("[evaluateMetricQueue run] metricTemplate.getMetricSpringBeanEvaluatorName() " + metricTemplate.
                            getMetricSpringBeanEvaluatorName());
                    metricEvaluator = (MetricEvaluator) SpringSupport.getInstance().findBeanByClassName(Class.forName(
                            metricTemplate.getMetricSpringBeanEvaluatorName()));
                } else if (metricTemplate.getMetricTypeElement().equals(MetricType.COUNTING)) {
                    metricEvaluator = new MetricEvaluatorCounting();
                } else if (metricTemplate.getMetricTypeElement().equals(MetricType.YES_NO)) {
                    if (metricTemplate.getYesNoType().equals(YesNoType.OR)) {
                        metricEvaluator = new MetricEvaluatorYesNoOR();
                    } else {
                        metricEvaluator = new MetricEvaluatorYesNoAND();
                    }
                } else if (metricTemplate.getMetricTypeElement().equals(MetricType.MEASURE_TIME)) {
                    metricEvaluator = new MetricEvaluatorMeasureTime();
                } else if (metricTemplate.getMetricTypeElement().equals(MetricType.NUMBER_INPUT)) {
                    metricEvaluator = new MetricEvaluatorNumberInput();
                }
                if (metricEvaluator != null) {
                    metricEvaluator.evaluate(observedMetric);
                    observedMetric.computeResult();
                    this.enqueueObservedSituation(observedMetricId);
                }
            }
        } catch (Exception e) {
            log.error("[evaluateObservedMetric run] ERROR = " + e.getMessage(), e);
            observedMetric.fail();
            throw new ScopixException(e);
        }
        log.info("end");
    }

    /**
     * Enqueue the observed situation in the SITUATION queue if apply
     *
     * @param observedMetricId
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    public void enqueueObservedSituation(int observedMetricId) throws ScopixException {
        GetObservedMetricCommand command = new GetObservedMetricCommand();
        ObservedMetric observedMetric = command.execute(observedMetricId);
        if (observedMetric.getObservedSituation() != null) {
            List<ObservedMetric> observedMetrics = observedMetric.getObservedSituation().getObservedMetrics();
            List<Metric> metrics = observedMetric.getObservedSituation().getSituation().getMetrics();
            for (ObservedMetric om : observedMetrics) {
                if (om.getEvaluationState() != null && om.getEvaluationState().equals(EvaluationState.FINISHED)) {
                    Collections.sort(metrics);
                    Metric m = new Metric();
                    m.setId(om.getMetric().getId());
                    int index = Collections.binarySearch(metrics, m);
                    if (index >= 0) {
                        metrics.remove(index);
                    }
                }
            }
            if (metrics.isEmpty()) {
                observedMetric.getObservedSituation().setEvaluationState(EvaluationState.ENQUEUED);
                UpdateObservedSituationCommand observedSituationCommand = new UpdateObservedSituationCommand();
                observedSituationCommand.execute(observedMetric.getObservedSituation());
                QueueManager queueManagerSituation = new QueueManager(EvaluationQueue.SITUATION);
                queueManagerSituation.enqueue(observedMetric.getObservedSituation());
            }
        }
    }
}
