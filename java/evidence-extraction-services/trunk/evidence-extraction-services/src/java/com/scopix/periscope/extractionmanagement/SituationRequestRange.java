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
 *  SituationRequestRange.java
 * 
 *  Created on 06-06-2012, 12:59:18 PM
 * 
 */
package com.scopix.periscope.extractionmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private SituationRequestRangeType rangeType;
    private Integer samples;

    public SituationRequest getSituationRequest() {
        return situationRequest;
    }

    public void setSituationRequest(SituationRequest situationRequest) {
        this.situationRequest = situationRequest;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

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
        return situationExtractionRequests;
    }

    public void setSituationExtractionRequests(List<SituationExtractionRequest> situationExtractionRequests) {
        this.situationExtractionRequests = situationExtractionRequests;
    }

    public SituationRequestRangeType getRangeType() {
        return rangeType;
    }

    public void setRangeType(SituationRequestRangeType rangeType) {
        this.rangeType = rangeType;
    }

    public Integer getSamples() {
        return samples;
    }

    public void setSamples(Integer samples) {
        this.samples = samples;
    }
}
