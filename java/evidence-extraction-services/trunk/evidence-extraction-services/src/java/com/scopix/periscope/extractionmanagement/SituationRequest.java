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
 * EvidenceRequest.java
 *
 * Created on 16-06-2008, 05:34:27 PM
 *
 */
package com.scopix.periscope.extractionmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.NotNull;

/**
 * This class represents a Evidence request from business services
 * 
 * @author marko.perich
 */
@Entity
public class SituationRequest extends BusinessObject implements Comparable<SituationRequest> {

    private Integer situationTemplateId;
    //se mueven a SituationRequestRange
    private Integer frecuency; //In minutes
    private Integer duration; //In seconds
    @OneToMany(mappedBy = "situationRequest")
    private List<MetricRequest> metricRequests;
    @OneToMany(mappedBy = "situationRequest")
    private List<SituationSensor> situationSensors;
    @ManyToOne
    private ExtractionPlan extractionPlan;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastProcess;
    @NotNull
    private Boolean randomCamera;
    @OneToMany(mappedBy = "situationRequest")
    private List<SituationRequestRange> situationRequestRanges;
    @OneToMany(mappedBy = "situationRequest")
    private List<EvidenceProviderRequest> evidenceProviderRequests;
    private Integer priorization;
    

    @Override
    public int compareTo(SituationRequest o) {
        return this.getId() - o.getId();
    }

    /**
     * @return the situationTemplateId
     */
    public Integer getSituationTemplateId() {
        return situationTemplateId;
    }

    /**
     * @param situationTemplateId the situationTemplateId to set
     */
    public void setSituationTemplateId(Integer situationTemplateId) {
        this.situationTemplateId = situationTemplateId;
    }

    /**
     * @return the frecuency
     */
    public Integer getFrecuency() {
        return frecuency;
    }

    /**
     * @param frecuency the frecuency to set
     */
    public void setFrecuency(Integer frecuency) {
        this.frecuency = frecuency;
    }

    /**
     * @return the duration
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * @return the metricRequests
     */
    public List<MetricRequest> getMetricRequests() {
        return metricRequests;
    }

    /**
     * @param metricRequests the metricRequests to set
     */
    public void setMetricRequests(List<MetricRequest> metricRequests) {
        this.metricRequests = metricRequests;
    }

    /**
     * @return the extractionPlan
     */
    public ExtractionPlan getExtractionPlan() {
        return extractionPlan;
    }

    /**
     * @param extractionPlan the extractionPlan to set
     */
    public void setExtractionPlan(ExtractionPlan extractionPlan) {
        this.extractionPlan = extractionPlan;
    }

    /**
     * @return the lastProcess
     */
    public Date getLastProcess() {
        return lastProcess;
    }

    /**
     * @param lastProcess the lastProcess to set
     */
    public void setLastProcess(Date lastProcess) {
        this.lastProcess = lastProcess;
    }

    /**
     * @return the situationSensors
     */
    public List<SituationSensor> getSituationSensors() {
        if (situationSensors == null) {
            situationSensors = new ArrayList<SituationSensor>();
        }
        return situationSensors;
    }

    /**
     * @param situationSensors the situationSensors to set
     */
    public void setSituationSensors(List<SituationSensor> situationSensors) {
        this.situationSensors = situationSensors;
    }

    public Boolean getRandomCamera() {
        return randomCamera;
    }

    public void setRandomCamera(Boolean randomCamera) {
        this.randomCamera = randomCamera;
    }

    public List<SituationRequestRange> getSituationRequestRanges() {
        if (situationRequestRanges == null) {
            situationRequestRanges = new ArrayList<SituationRequestRange>();
        }
        return situationRequestRanges;
    }

    public void setSituationRequestRanges(List<SituationRequestRange> situationRequestRanges) {
        this.situationRequestRanges = situationRequestRanges;
    }

    public List<EvidenceProviderRequest> getEvidenceProviderRequests() {
        if (evidenceProviderRequests ==null){
            evidenceProviderRequests = new ArrayList<EvidenceProviderRequest>();
        }
        return evidenceProviderRequests;
    }

    public void setEvidenceProviderRequests(List<EvidenceProviderRequest> evidenceProviderRequests) {
        this.evidenceProviderRequests = evidenceProviderRequests;
    }

    public Integer getPriorization() {
        return priorization;
    }

    public void setPriorization(Integer priorization) {
        this.priorization = priorization;
    }
}
