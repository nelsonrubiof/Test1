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
 * MetricEvaluation.java
 *
 * Created on 06-05-2008, 08:02:05 PM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class MetricEvaluation extends BusinessObject {

    @OneToOne(fetch = FetchType.EAGER)
    private ObservedMetric observedMetric;
    private Integer metricEvaluationResult;

    public ObservedMetric getObservedMetric() {
        return observedMetric;
    }

    public void setObservedMetric(ObservedMetric observedMetric) {
        this.observedMetric = observedMetric;
    }

    public Integer getMetricEvaluationResult() {
        return metricEvaluationResult;
    }

    public void setMetricEvaluationResult(Integer metricEvaluationResult) {
        this.metricEvaluationResult = metricEvaluationResult;
    }
}
