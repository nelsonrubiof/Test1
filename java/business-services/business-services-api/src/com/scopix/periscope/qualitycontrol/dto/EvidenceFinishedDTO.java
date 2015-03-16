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
 * EvidenceFinishedDTO.java
 *
 * Created on 04-06-2008, 03:37:38 PM
 *
 */
package com.scopix.periscope.qualitycontrol.dto;

import com.scopix.periscope.evaluationmanagement.dto.ProofDTO;
import com.scopix.periscope.templatemanagement.EvidenceType;
import com.scopix.periscope.templatemanagement.MetricType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class EvidenceFinishedDTO implements Comparable<EvidenceFinishedDTO> {

    private Integer evidenceId;
    private Integer evidenceEvaluationId;
    private Integer situationId;
    private String proofPrePath;
    private String evidencePath;
    private String flvPath;
    private Integer metricId;
    private String metricName;
    private Integer metricTemplateId;
    private Integer observedMetricId;
    private MetricType metricType;
    private EvidenceType evidenceType;
    private List<ProofDTO> proofs;
    private Integer areaId;
    private String area;
    private Date evidenceDate;
    private Integer evidenceEvaluationResult;
    private Integer totalMeasuredByCamera;
    private String cantDoReason;
    private String provider;
    private String evaluationInstruction;
    private String userName;
    private boolean rejected;
    private Date evaluationDate;
    private String camera;
    private Date initEvaluationDate;
    private Date endEvaluationDate;
    private Long evaluationTimeInSeconds;
    private String storeName;

    public Integer getEvidenceId() {
        return evidenceId;
    }

    public void setEvidenceId(Integer evidenceId) {
        this.evidenceId = evidenceId;
    }

    public Integer getEvidenceEvaluationId() {
        return evidenceEvaluationId;
    }

    public void setEvidenceEvaluationId(Integer evidenceEvaluationId) {
        this.evidenceEvaluationId = evidenceEvaluationId;
    }

    public Integer getMetricId() {
        return metricId;
    }

    public void setMetricId(Integer metricId) {
        this.metricId = metricId;
    }

    public Integer getMetricTemplateId() {
        return metricTemplateId;
    }

    public void setMetricTemplateId(Integer metricTemplateId) {
        this.metricTemplateId = metricTemplateId;
    }

    public MetricType getMetricType() {
        return metricType;
    }

    public void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }

    public EvidenceType getEvidenceType() {
        return evidenceType;
    }

    public void setEvidenceType(EvidenceType evidenceType) {
        this.evidenceType = evidenceType;
    }

    public List<ProofDTO> getProofs() {
        if (proofs == null) {
            proofs = new ArrayList<ProofDTO>();
        }
        return proofs;
    }

    public void setProofs(List<ProofDTO> proofs) {
        this.proofs = proofs;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getEvidenceDate() {
        return evidenceDate;
    }

    public void setEvidenceDate(Date evidenceDate) {
        this.evidenceDate = evidenceDate;
    }

    public Integer getTotalMeasuredByCamera() {
        return totalMeasuredByCamera;
    }

    public void setTotalMeasuredByCamera(Integer totalMeasuredByCamera) {
        this.totalMeasuredByCamera = totalMeasuredByCamera;
    }

    public Integer getEvidenceEvaluationResult() {
        return evidenceEvaluationResult;
    }

    public void setEvidenceEvaluationResult(Integer evidenceEvaluationResult) {
        this.evidenceEvaluationResult = evidenceEvaluationResult;
    }

    public int compareTo(EvidenceFinishedDTO o) {
        return this.getEvidenceEvaluationId() - o.getEvidenceEvaluationId();
    }

    public String getProofPrePath() {
        return proofPrePath;
    }

    public void setProofPrePath(String proofPrePath) {
        this.proofPrePath = proofPrePath;
    }

    public String getEvidencePath() {
        return evidencePath;
    }

    public void setEvidencePath(String evidencePath) {
        this.evidencePath = evidencePath;
    }

    public Integer getObservedMetricId() {
        return observedMetricId;
    }

    public void setObservedMetricId(Integer observedMetricId) {
        this.observedMetricId = observedMetricId;
    }

    public String getFlvPath() {
        return flvPath;
    }

    public void setFlvPath(String flvPath) {
        this.flvPath = flvPath;
    }

    public String getCantDoReason() {
        return cantDoReason;
    }

    public void setCantDoReason(String cantDoReason) {
        this.cantDoReason = cantDoReason;
    }

    public boolean isYesNoType() {
        if (metricType.equals(MetricType.YES_NO)) {
            return true;
        }
        return false;
    }

    public void setYesNoType(boolean yesNoType) {
        return;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getEvaluationInstruction() {
        return evaluationInstruction;
    }

    public void setEvaluationInstruction(String evaluationInstruction) {
        this.evaluationInstruction = evaluationInstruction;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public Date getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(Date evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public Integer getSituationId() {
        return situationId;
    }

    public void setSituationId(Integer situationId) {
        this.situationId = situationId;
    }

    /**
     * @return the initEvaluationDate
     */
    public Date getInitEvaluationDate() {
        return initEvaluationDate;
    }

    /**
     * @param initEvaluationDate the initEvaluationDate to set
     */
    public void setInitEvaluationDate(Date initEvaluationDate) {
        this.initEvaluationDate = initEvaluationDate;
    }

    /**
     * @return the endEvaluationDate
     */
    public Date getEndEvaluationDate() {
        return endEvaluationDate;
    }

    /**
     * @param endEvaluationDate the endEvaluationDate to set
     */
    public void setEndEvaluationDate(Date endEvaluationDate) {
        this.endEvaluationDate = endEvaluationDate;
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
     * @return the storeName
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * @param storeName the storeName to set
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
