/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * EvidenceRequest.java
 * 
 * Created on 16-06-2008, 05:34:27 PM
 */
package com.scopix.periscope.extractionservicesserversmanagement;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.periscopefoundation.BusinessObject;

/**
 * This class represents a Evidence request from business services
 * 
 * @author marko.perich
 */
@Entity
public class EvidenceRequest extends BusinessObject implements Comparable<EvidenceRequest> {

    private String requestType;

    @Temporal(TemporalType.TIME)
    private Date requestedTime;

    private Integer duration;

    private Integer deviceId;

    private Integer businessServicesRequestId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private ExtractionPlanDetail extractionPlanDetail;

    private Integer dayOfWeek;

    @Enumerated(EnumType.STRING)
    private EvidenceRequestType type;

    // variable para el uso con el proceso de motion detect.
    private Integer processId;

    // priorizacion de la evidencia
    private Integer priorization;

    private Boolean live;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Date getRequestedTime() {
        return requestedTime;
    }

    public void setRequestedTime(Date requestedTime) {
        this.requestedTime = requestedTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getBusinessServicesRequestId() {
        return businessServicesRequestId;
    }

    public void setBusinessServicesRequestId(Integer businessServicesRequestId) {
        this.businessServicesRequestId = businessServicesRequestId;
    }

    public ExtractionPlanDetail getExtractionPlanDetail() {
        return extractionPlanDetail;
    }

    public void setExtractionPlanDetail(ExtractionPlanDetail extractionPlanDetail) {
        this.extractionPlanDetail = extractionPlanDetail;
    }

    /**
     * @return the dayOfWeek
     */
    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * @param dayOfWeek the dayOfWeek to set
     */
    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public int compareTo(EvidenceRequest o) {
        return this.getId() - o.getId();
    }

    /**
     * @return the type
     */
    public EvidenceRequestType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(EvidenceRequestType type) {
        this.type = type;
    }

    /**
     * @return the processId
     */
    public Integer getProcessId() {
        return processId;
    }

    /**
     * @param processId the processId to set
     */
    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Integer getPriorization() {
        return priorization;
    }

    public void setPriorization(Integer priorization) {
        this.priorization = priorization;
    }

    /**
     * @return the live
     */
    public Boolean getLive() {
        return live;
    }

    /**
     * @param live the live to set
     */
    public void setLive(Boolean live) {
        this.live = live;
    }
}