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
 * MetricEvaluatorYesNoOR.java
 *
 * Created on 09-06-2008, 06:26:52 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.evaluators;

import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.evaluationmanagement.MetricEvaluation;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public class MetricEvaluatorYesNoOR implements MetricEvaluator {

    public void evaluate(ObservedMetric observedMetric) throws ScopixException {
        MetricEvaluation metricEvaluation = new MetricEvaluation();
        metricEvaluation.setObservedMetric(observedMetric);
        boolean result = false;
        boolean saveResult = true;
        int counter = 0;
        for (EvidenceEvaluation evidenceEvaluation : observedMetric.getEvidenceEvaluations()) {
            if (evidenceEvaluation.isRejected()) {
                continue;
            } else if (evidenceEvaluation.getEvidenceResult() == null) {
                saveResult = false;
                break;
            }
            result = result || (evidenceEvaluation.getEvidenceResult() == 1 ? true : false);
            counter++;
            if (result) {
                break;
            }
        }
        if (saveResult && counter>0) {
            metricEvaluation.setMetricEvaluationResult((result ? 1 : 0));
            HibernateSupport.getInstance().findGenericDAO().save(metricEvaluation);
        }
    }
}
