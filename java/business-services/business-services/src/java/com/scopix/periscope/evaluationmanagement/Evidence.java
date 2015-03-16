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
 * Evidence.java
 *
 * Created on 06-05-2008, 07:44:43 PM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.corporatestructuremanagement.EvidenceServicesServer;
import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class Evidence extends BusinessObject implements Comparable<Evidence> {

    @Lob
    private String evidencePath;
    @ManyToMany(targetEntity = EvidenceEvaluation.class, mappedBy = "evidences", fetch = FetchType.LAZY, cascade = {
        CascadeType.PERSIST, CascadeType.MERGE})
    private List<EvidenceEvaluation> evidenceEvaluations;
    @ManyToOne
    private EvidenceServicesServer evidenceServicesServer;
    @ManyToMany(targetEntity = ObservedMetric.class, fetch = FetchType.LAZY, mappedBy = "evidences", cascade = {
        CascadeType.ALL})
    private List<ObservedMetric> observedMetrics;
    @ManyToMany(targetEntity = EvidenceRequest.class, fetch = FetchType.LAZY, mappedBy = "evidences", cascade = {
        CascadeType.PERSIST, CascadeType.MERGE})
    private List<EvidenceRequest> evidenceRequests;
    @Temporal(TemporalType.TIMESTAMP)
    private Date evidenceDate;
    @Lob
    private String flvPath;
    @OneToMany(mappedBy = "evidence")
    private List<Proof> proofs;
    private Boolean sentToAlternativeSFTP;
    @OneToMany(mappedBy = "evidence")
    private List<EvidenceRegionTransfer> evidenceRegionTransfers;

    /**
     *
     * @return evidenceServicesServer;
     */
    public EvidenceServicesServer getEvidenceServicesServer() {
        return evidenceServicesServer;
    }

    /**
     *
     * @param evidenceServicesServer
     */
    public void setEvidenceServicesServer(EvidenceServicesServer evidenceServicesServer) {
        this.evidenceServicesServer = evidenceServicesServer;
    }

    /**
     *
     * @return evidencePath
     */
    public String getEvidencePath() {
        return evidencePath;
    }

    /**
     *
     * @param evidencePath
     */
    public void setEvidencePath(String evidencePath) {
        this.evidencePath = evidencePath;
    }

    /**
     *
     * @return evidenceEvaluations
     */
    public List<EvidenceEvaluation> getEvidenceEvaluations() {
        return evidenceEvaluations;
    }

    /**
     *
     * @param evidenceEvaluations
     */
    public void setEvidenceEvaluation(List<EvidenceEvaluation> evidenceEvaluations) {
        this.evidenceEvaluations = evidenceEvaluations;
    }

    /**
     *
     * @return observedMetrics
     */
    public List<ObservedMetric> getObservedMetrics() {
        if (observedMetrics == null) {
            observedMetrics = new ArrayList<ObservedMetric>();
        }
        return observedMetrics;
    }

    /**
     *
     * @param observedMetrics
     */
    public void setObservedMetrics(List<ObservedMetric> observedMetrics) {
        this.observedMetrics = observedMetrics;
    }

    /**
     *
     * @return evidenceRequests
     */
    public List<EvidenceRequest> getEvidenceRequests() {
        if (evidenceRequests == null) {
            evidenceRequests = new ArrayList<EvidenceRequest>();
        }
        return evidenceRequests;
    }

    /**
     *
     * @param evidenceRequests
     */
    public void setEvidenceRequests(List<EvidenceRequest> evidenceRequests) {
        this.evidenceRequests = evidenceRequests;
    }

    /**
     *
     * @return evidenceDate
     */
    public Date getEvidenceDate() {
        return evidenceDate;
    }

    /**
     *
     * @param evidenceDate
     */
    public void setEvidenceDate(Date evidenceDate) {
        this.evidenceDate = evidenceDate;
    }

    /**
     *
     * @return flvPath
     */
    public String getFlvPath() {
        return flvPath;
    }

    /**
     *
     * @param flvPath
     */
    public void setFlvPath(String flvPath) {
        this.flvPath = flvPath;
    }

    public int compareTo(Evidence o) {
        return this.getId() - o.getId();
    }

    /**
     * @return the proofs
     */
    public List<Proof> getProofs() {
        if (proofs == null) {
            proofs = new ArrayList<Proof>();
        }
        return proofs;
    }

    /**
     * @param proofs the proofs to set
     */
    public void setProofs(List<Proof> proofs) {
        this.proofs = proofs;
    }

    /**
     * @return the sentToAlternativeSFTP
     */
    public Boolean getSentToAlternativeSFTP() {
        if (sentToAlternativeSFTP == null) {
            sentToAlternativeSFTP = false;
        }
        return sentToAlternativeSFTP;
    }

    /**
     * @param sentToAlternativeSFTP the sentToAlternativeSFTP to set
     */
    public void setSentToAlternativeSFTP(Boolean sentToAlternativeSFTP) {
        this.sentToAlternativeSFTP = sentToAlternativeSFTP;
    }

    /**
     * @return the evidenceRegionTransfers
     */
    public List<EvidenceRegionTransfer> getEvidenceRegionTransfers() {
        if (evidenceRegionTransfers == null) {
            evidenceRegionTransfers = new ArrayList<EvidenceRegionTransfer>();
        }
        return evidenceRegionTransfers;
    }

    /**
     * @param evidenceRegionTransfers the evidenceRegionTransfers to set
     */
    public void setEvidenceRegionTransfers(List<EvidenceRegionTransfer> evidenceRegionTransfers) {
        this.evidenceRegionTransfers = evidenceRegionTransfers;
    }
}
