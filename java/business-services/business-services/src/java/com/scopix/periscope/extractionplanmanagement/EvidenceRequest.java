/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
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
 * Created on 06-05-2008, 09:13:51 PM
 *
 */
package com.scopix.periscope.extractionplanmanagement;

import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.templatemanagement.EvidenceType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class EvidenceRequest extends BusinessObject implements Comparable<EvidenceRequest> {

    @Temporal(TemporalType.TIME)
    private Date evidenceTime;
    @ManyToOne
    private Metric metric;
    @Enumerated(EnumType.STRING)
    private EvidenceType type;

    @ManyToOne
    private EvidenceProvider evidenceProvider;
    @ManyToOne
    private ExtractionPlanRangeDetail extractionPlanRangeDetail;

    @ManyToMany(targetEntity = Evidence.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "rel_evidence_request_evidence", joinColumns = {@JoinColumn(name = "evidence_request_id")},
    inverseJoinColumns = {@JoinColumn(name = "evidence_id")})
    private List<Evidence> evidences;
    private Integer day;
    @Enumerated(EnumType.STRING)
    private EvidenceRequestType evidenceRequestType;

    //agregamos la priorizacion
    private Integer priorization;

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public Date getEvidenceTime() {
        return evidenceTime;
    }

    public void setEvidenceTime(Date evidenceTime) {
        this.evidenceTime = evidenceTime;
    }

    public EvidenceProvider getEvidenceProvider() {
        return evidenceProvider;
    }

    public void setEvidenceProvider(EvidenceProvider evidenceProvider) {
        this.evidenceProvider = evidenceProvider;
    }

    public List<Evidence> getEvidences() {
        if (evidences == null) {
            evidences = new ArrayList<Evidence>();
        }
        return evidences;
    }

    public void setEvidences(List<Evidence> evidences) {
        this.evidences = evidences;
    }

    public int compareTo(EvidenceRequest o) {
        return this.getId() - o.getId();
    }

    public String getFormatDisplayName() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String result = sdf.format(this.evidenceTime) + " " + this.evidenceProvider.getName() + " " + day;
        return result;
    }

    public EvidenceType getType() {
        return type;
    }

    public void setType(EvidenceType type) {
        this.type = type;
    }

    /**
     * @return the day
     */
    public Integer getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * @return the evidenceRequestType
     */
    public EvidenceRequestType getEvidenceRequestType() {
        return evidenceRequestType;
    }

    /**
     * @param evidenceRequestType the evidenceRequestType to set
     */
    public void setEvidenceRequestType(EvidenceRequestType evidenceRequestType) {
        this.evidenceRequestType = evidenceRequestType;
    }

    public ExtractionPlanRangeDetail getExtractionPlanRangeDetail() {
        return extractionPlanRangeDetail;
    }

    public void setExtractionPlanRangeDetail(ExtractionPlanRangeDetail extractionPlanRangeDetail) {
        this.extractionPlanRangeDetail = extractionPlanRangeDetail;
    }

    public Integer getPriorization() {
        return priorization;
    }

    public void setPriorization(Integer priorization) {
        this.priorization = priorization;
    }
}
