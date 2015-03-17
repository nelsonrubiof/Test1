/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evidencemanagement.dto;

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
