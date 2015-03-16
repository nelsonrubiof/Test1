/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * EvidenceExtractionRequest.java
 * 
 * Created on 19-05-2008, 03:55:32 PM
 */
package com.scopix.periscope.extractionmanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.periscopefoundation.BusinessObject;

/**
 *
 * @author marko.perich
 */
@Entity
@SuppressWarnings("serial")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class EvidenceExtractionRequest extends BusinessObject {

    @ManyToOne
    private ExtractionPlan extractionPlan;
    @OneToMany(mappedBy = "evidenceExtractionRequest", fetch = FetchType.LAZY)
    private List<EvidenceFile> evidenceFiles;
    @Temporal(TemporalType.TIME)
    private Date requestedTime;
    /*
     * The id of the request Id in the evidence services server
     */
    private Integer remoteRequestId;
    @ManyToOne
    private EvidenceProvider evidenceProvider;
    private Integer dayOfWeek;
    private Integer processId;
    @Enumerated(EnumType.STRING)
    private EvidenceRequestType type;
    @OneToMany(mappedBy = "evidenceExtractionRequest", fetch = FetchType.LAZY)
    private List<SituationMetricExtractionRequest> situationMetricExtractionRequest;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTimestamp;
    private Integer priorization;

    // solo aplica para los autogenerados
    @Temporal(TemporalType.TIMESTAMP)
    private Date evidenceDate;

    private Integer beeId;

    private Boolean live;

    public Date getRequestedTime() {
        return requestedTime;
    }

    public void setRequestedTime(Date requestedTime) {
        this.requestedTime = requestedTime;
    }

    public ExtractionPlan getExtractionPlan() {
        return extractionPlan;
    }

    public void setExtractionPlan(ExtractionPlan extractionPlan) {
        this.extractionPlan = extractionPlan;
    }

    public Integer getRemoteRequestId() {
        return remoteRequestId;
    }

    public void setRemoteRequestId(Integer remoteRequestId) {
        this.remoteRequestId = remoteRequestId;
    }

    /*
     * This method returs the time the evidence must be requested at. The value is the UTC value
     */
    public abstract Date getExtractionStartTime();

    // throw new RuntimeException("getExtractionStartTime() must be overriden");

    public EvidenceProvider getEvidenceProvider() {
        return evidenceProvider;
    }

    public void setEvidenceProvider(EvidenceProvider evidenceProvider) {
        this.evidenceProvider = evidenceProvider;
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
     * @return the evidenceFiles
     */
    public List<EvidenceFile> getEvidenceFiles() {
        return evidenceFiles;
    }

    /**
     * @param evidenceFiles the evidenceFiles to set
     */
    public void setEvidenceFiles(List<EvidenceFile> evidenceFiles) {
        this.evidenceFiles = evidenceFiles;
    }

    public List<SituationMetricExtractionRequest> getSituationMetricExtractionRequest() {
        if (situationMetricExtractionRequest == null) {
            situationMetricExtractionRequest = new ArrayList<SituationMetricExtractionRequest>();
        }
        return situationMetricExtractionRequest;
    }

    public void setSituationMetricExtractionRequest(List<SituationMetricExtractionRequest> situationMetricExtractionRequest) {
        this.situationMetricExtractionRequest = situationMetricExtractionRequest;
    }

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Integer getPriorization() {
        return priorization;
    }

    public void setPriorization(Integer priorization) {
        this.priorization = priorization;
    }

    public abstract boolean getAllowsExtractionPlanToPast();

    public Date getEvidenceDate() {
        return evidenceDate;
    }

    public void setEvidenceDate(Date evidenceDate) {
        this.evidenceDate = evidenceDate;
    }

    /**
     * @return the beeId
     */
    public Integer getBeeId() {
        return beeId;
    }

    /**
     * @param beeId the beeId to set
     */
    public void setBeeId(Integer beeId) {
        this.beeId = beeId;
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