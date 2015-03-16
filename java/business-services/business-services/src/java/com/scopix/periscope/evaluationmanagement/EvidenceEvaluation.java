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
 * EvidenceEvaluation.java
 *
 * Created on 06-05-2008, 07:53:02 PM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
public class EvidenceEvaluation extends BusinessObject {

    @ManyToMany(targetEntity = Evidence.class, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "rel_evidence_evaluation_evidence", joinColumns = {@JoinColumn(name = "evidence_evaluation_id")},
    inverseJoinColumns = {@JoinColumn(name = "evidence_id")})
    private List<Evidence> evidences;
    @ManyToOne
    private ObservedMetric observedMetric;
    @OneToMany(mappedBy = "evidenceEvaluation", fetch = FetchType.LAZY)
    private List<Proof> proofs;
    private Integer evidenceResult;
    @Lob
    private String cantDoReason;
    @Lob
    private String evaluationUser;
    private boolean rejected;
    @Temporal(TemporalType.TIMESTAMP)
    private Date evaluationDate;
    @ManyToOne
    private PendingEvaluation pendingEvaluation;
    @Temporal(TemporalType.TIMESTAMP)
    private Date initEvaluation;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endEvaluation;
    private Long evaluationTimeInSeconds;
    
    

    public List<Evidence> getEvidences() {
        if (evidences == null) {
            evidences = new ArrayList<Evidence>();
        }
        return evidences;
    }

    public void setEvidences(List<Evidence> evidences) {
        this.evidences = evidences;
    }

    public ObservedMetric getObservedMetric() {
        return observedMetric;
    }

    public void setObservedMetric(ObservedMetric observedMetric) {
        this.observedMetric = observedMetric;
    }

    public Integer getEvidenceResult() {
        return evidenceResult;
    }

    public void setEvidenceResult(Integer evidenceResult) {
        this.evidenceResult = evidenceResult;
    }

    public List<Proof> getProofs() {
        return proofs;
    }

    public void setProofs(List<Proof> proofs) {
        this.proofs = proofs;
    }

    public String getCantDoReason() {
        return cantDoReason;
    }

    public void setCantDoReason(String cantDoReason) {
        this.cantDoReason = cantDoReason;
    }

    public String getEvaluationUser() {
        return evaluationUser;
    }

    public void setEvaluationUser(String evaluationUser) {
        this.evaluationUser = evaluationUser;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public PendingEvaluation getPendingEvaluation() {
        return pendingEvaluation;
    }

    public void setPendingEvaluation(PendingEvaluation pendingEvaluation) {
        this.pendingEvaluation = pendingEvaluation;
    }

    /**
     * @return the evaluationDate
     */
    public Date getEvaluationDate() {
        return evaluationDate;
    }

    /**
     * @param evaluationDate the evaluationDate to set
     */
    public void setEvaluationDate(Date evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    /**
     * @return the initEvaluation
     */
    public Date getInitEvaluation() {
        return initEvaluation;
    }

    /**
     * @param initEvaluation the initEvaluation to set
     */
    public void setInitEvaluation(Date initEvaluation) {
        this.initEvaluation = initEvaluation;
    }

    /**
     * @return the endEvaluation
     */
    public Date getEndEvaluation() {
        return endEvaluation;
    }

    /**
     * @param endEvaluation the endEvaluation to set
     */
    public void setEndEvaluation(Date endEvaluation) {
        this.endEvaluation = endEvaluation;
    }

    /**
     * @return the evaluationTimeInSeconds
     */
    public Long getEvaluationTimeInSeconds() {
        return evaluationTimeInSeconds;
    }

    /**
     * @param evaluationTimeInSeconds the evaluationTimeInSeconds to set
     */
    public void setEvaluationTimeInSeconds(Long evaluationTimeInSeconds) {
        this.evaluationTimeInSeconds = evaluationTimeInSeconds;
    }
}
