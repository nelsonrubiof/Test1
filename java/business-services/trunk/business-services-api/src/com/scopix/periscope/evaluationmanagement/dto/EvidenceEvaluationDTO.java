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
 * EvidenceEvaluationDTO.java
 *
 * Created on 02-06-2008, 04:43:02 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class EvidenceEvaluationDTO {

    private Integer id;
    private List<Integer> evidenceIds;
    private Integer metricId;
    private Integer evidenceEvaluationResult;
    private String cantDoReason;
    private List<ProofDTO> proofs;
    private Integer pendingEvaluationId;
    private Date initEvaluation;
    private Date endEvaluation;
    private Long evaluationTimeInSeconds;

    public List<ProofDTO> getProofs() {
        if (proofs == null) {
            proofs = new ArrayList<ProofDTO>();
        }
        return proofs;
    }

    public void setProofs(List<ProofDTO> proofs) {
        this.proofs = proofs;
    }

    public Integer getMetricId() {
        return metricId;
    }

    public void setMetricId(Integer metricId) {
        this.metricId = metricId;
    }

    public Integer getEvidenceEvaluationResult() {
        return evidenceEvaluationResult;
    }

    public void setEvidenceEvaluationResult(Integer evidenceEvaluationResult) {
        this.evidenceEvaluationResult = evidenceEvaluationResult;
    }

    public String getCantDoReason() {
        return cantDoReason;
    }

    public void setCantDoReason(String cantDoReason) {
        this.cantDoReason = cantDoReason;
    }

    public Integer getPendingEvaluationId() {
        return pendingEvaluationId;
    }

    public void setPendingEvaluationId(Integer pendingEvaluationId) {
        this.pendingEvaluationId = pendingEvaluationId;
    }

    /**
     * @return the evidenceIds
     */
    public List<Integer> getEvidenceIds() {
        if (evidenceIds == null) {
            evidenceIds = new ArrayList<Integer>();
        }
        return evidenceIds;
    }

    /**
     * @param evidenceIds the evidenceIds to set
     */
    public void setEvidenceIds(List<Integer> evidenceIds) {
        this.evidenceIds = evidenceIds;
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

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
