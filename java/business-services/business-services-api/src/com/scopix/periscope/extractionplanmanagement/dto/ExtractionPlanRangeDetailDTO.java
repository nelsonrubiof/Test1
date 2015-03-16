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
 *  ExtractionPlanRangeDetailDTO.java
 * 
 *  Created on 27-09-2010, 06:35:21 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.dto;

/**
 *
 * @author nelson
 */
public class ExtractionPlanRangeDetailDTO {

    private Integer id;
    private Integer extractionPlanRangeId;
    private String timeSample;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExtractionPlanRangeId() {
        return extractionPlanRangeId;
    }

    public void setExtractionPlanRangeId(Integer extractionPlanRangeId) {
        this.extractionPlanRangeId = extractionPlanRangeId;
    }

    public String getTimeSample() {
        return timeSample;
    }

    public void setTimeSample(String timeSample) {
        this.timeSample = timeSample;
    }
}
