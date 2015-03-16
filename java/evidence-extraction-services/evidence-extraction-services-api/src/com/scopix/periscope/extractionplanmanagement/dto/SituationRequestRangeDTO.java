/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionplanmanagement.dto;

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

    public List<SituationExtractionRequestDTO> getSituationExtractionRequestDTOs() {
        if (situationExtractionRequestDTOs == null) {
            situationExtractionRequestDTOs = new ArrayList<SituationExtractionRequestDTO>();
        }
        return situationExtractionRequestDTOs;
    }

    public void setSituationExtractionRequestDTOs(List<SituationExtractionRequestDTO> situationExtractionRequestDTOs) {
        this.situationExtractionRequestDTOs = situationExtractionRequestDTOs;
    }

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
