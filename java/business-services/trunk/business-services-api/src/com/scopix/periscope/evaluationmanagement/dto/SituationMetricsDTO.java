/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  SituationMetricsDTO.java
 * 
 *  Created on 01-04-2014, 11:31:09 AM
 * 
 */
package com.scopix.periscope.evaluationmanagement.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nelson
 */
public class SituationMetricsDTO {

    private Integer situationId;
    private List<Integer> metricIds;

    /**
     * @return the situationId
     */
    public Integer getSituationId() {
        return situationId;
    }

    /**
     * @param situationId the situationId to set
     */
    public void setSituationId(Integer situationId) {
        this.situationId = situationId;
    }

    /**
     * @return the metricIds
     */
    public List<Integer> getMetricIds() {
        if (metricIds == null) {
            metricIds = new ArrayList<Integer>();
        }
        return metricIds;
    }

    /**
     * @param metricIds the metricIds to set
     */
    public void setMetricIds(List<Integer> metricIds) {
        this.metricIds = metricIds;
    }
}
