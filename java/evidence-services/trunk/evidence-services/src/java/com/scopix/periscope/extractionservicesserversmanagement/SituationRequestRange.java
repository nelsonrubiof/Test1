/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author nelson
 */
@Entity
public class SituationRequestRange extends BusinessObject {

    @ManyToOne
    private SituationRequest situationRequest;
    private Integer dayOfWeek;
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date initialTime; //formato HHmm
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date endTime; //formato HHmm
    private Integer frecuency; //In minutes
    private Integer duration; //In seconds
    @OneToMany(mappedBy = "situationRequestRange", fetch = FetchType.LAZY)
    private List<SituationExtractionRequest> situationExtractionRequests;
    private Integer samples;
    
    private String rangeType; //AUTOMATIC_EVIDENCE, RANDOM, FIXED, REAL_RANDOM

    public Date getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(Date initialTime) {
        this.initialTime = initialTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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

    public List<SituationExtractionRequest> getSituationExtractionRequests() {
        if (situationExtractionRequests == null) {
            situationExtractionRequests = new ArrayList<SituationExtractionRequest>();
        }
        return situationExtractionRequests;
    }

    public void setSituationExtractionRequests(List<SituationExtractionRequest> situationExtractionRequests) {
        this.situationExtractionRequests = situationExtractionRequests;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public SituationRequest getSituationRequest() {
        return situationRequest;
    }

    public void setSituationRequest(SituationRequest situationRequest) {
        this.situationRequest = situationRequest;
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
