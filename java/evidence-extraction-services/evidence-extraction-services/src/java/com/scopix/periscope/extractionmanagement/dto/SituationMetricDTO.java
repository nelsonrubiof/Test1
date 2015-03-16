/*
 *
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * SituationMetricDTO.java
 *
 * Created on 08-04-2009, 04:24:58 PM
 *
 */


package com.scopix.periscope.extractionmanagement.dto;

import com.scopix.periscope.extractionmanagement.MetricRequest;
import com.scopix.periscope.extractionmanagement.SituationRequest;

/**
 *
 * @author Gustavo Alvarez
 */
public class SituationMetricDTO {

    private SituationRequest situationRequest;
    private MetricRequest metricRequest;

    public SituationRequest getSituationRequest() {
        return situationRequest;
    }

    public void setSituationRequest(SituationRequest situationRequest) {
        this.situationRequest = situationRequest;
    }

    public MetricRequest getMetricRequest() {
        return metricRequest;
    }

    public void setMetricRequest(MetricRequest metricRequest) {
        this.metricRequest = metricRequest;
    }
}