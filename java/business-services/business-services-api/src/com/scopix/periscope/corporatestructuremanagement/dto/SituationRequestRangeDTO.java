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
 *  SituationRequestRangeDTO.java
 * 
 *  Created on 05-06-2012, 12:05:00 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class SituationRequestRangeDTO {

    private Integer dayOfWeek;
    private String initialTime; //formato HHmm
    private String endTime; //formato HHmm
    private Integer frecuency; //In minutes
    private Integer duration; //In seconds
    private List<SituationExtractionRequestDTO> situationExtractionRequestDTOs;
    private String rangeType; //AUTOMATIC_EVIDENCE, RANDOM, FIXED, REAL_RANDOM
    private Integer samples; 

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(String initialTime) {
        this.initialTime = initialTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(Integer frecuency) {
        this.frecuency = frecuency;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<SituationExtractionRequestDTO> getSituationExtractionRequestDTOs() {
        if (situationExtractionRequestDTOs == null) {
            situationExtractionRequestDTOs = new ArrayList<SituationExtractionRequestDTO>();
        }
        return situationExtractionRequestDTOs;
    }

    public void setSituationExtractionRequestDTOs(List<SituationExtractionRequestDTO> situationExtractionRequestDTOs) {
        this.situationExtractionRequestDTOs = situationExtractionRequestDTOs;
    }

    public String getRangeType() {
        return rangeType;
    }

    public void setRangeType(String rangeType) {
        this.rangeType = rangeType;
    }

    public Integer getSamples() {
        return samples;
    }

    public void setSamples(Integer samples) {
        this.samples = samples;
    }
}
