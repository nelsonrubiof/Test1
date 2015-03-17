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
 * EvidenceDTO.java
 *
 * Created on 12-06-2009, 07:33:22 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.dto;

import com.scopix.periscope.templatemanagement.EvidenceType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class EvidenceDTO implements Comparable<EvidenceDTO> {

    private Integer evidenceId;
    private String evidencePath;
    private String flvPath;
    private EvidenceType evidenceType;
    private Date evidenceDate;
    private String camera;
    private Integer evidenceEvaluationId;
    private Integer evidenceEvaluationResult;
    private String cantDoReason;
    private List<ProofDTO> proofs;
    private Boolean defaultEvidenceProvider;
    private Integer viewOrder;
    private static Comparator<EvidenceDTO> comparatorByCamera;

    public static Comparator<EvidenceDTO> getComparatorByCamera() {
        comparatorByCamera = new Comparator<EvidenceDTO>() {

            public int compare(EvidenceDTO o1, EvidenceDTO o2) {
                return o1.getCamera().compareTo(o2.getCamera());
            }
        };
        return comparatorByCamera;
    }

    public static void setComparatorByCamera(Comparator<EvidenceDTO> aComparatorByCamera) {
        comparatorByCamera = aComparatorByCamera;
    }

    /**
     * @return the evidenceId
     */
    public Integer getEvidenceId() {
        return evidenceId;
    }

    /**
     * @param evidenceId the evidenceId to set
     */
    public void setEvidenceId(Integer evidenceId) {
        this.evidenceId = evidenceId;
    }

    /**
     * @return the evidencePath
     */
    public String getEvidencePath() {
        return evidencePath;
    }

    /**
     * @param evidencePath the evidencePath to set
     */
    public void setEvidencePath(String evidencePath) {
        this.evidencePath = evidencePath;
    }

    /**
     * @return the flvPath
     */
    public String getFlvPath() {
        return flvPath;
    }

    /**
     * @param flvPath the flvPath to set
     */
    public void setFlvPath(String flvPath) {
        this.flvPath = flvPath;
    }

    /**
     * @return the evidenceType
     */
    public EvidenceType getEvidenceType() {
        return evidenceType;
    }

    /**
     * @param evidenceType the evidenceType to set
     */
    public void setEvidenceType(EvidenceType evidenceType) {
        this.evidenceType = evidenceType;
    }

    /**
     * @return the evidenceDate
     */
    public Date getEvidenceDate() {
        return evidenceDate;
    }

    /**
     * @param evidenceDate the evidenceDate to set
     */
    public void setEvidenceDate(Date evidenceDate) {
        this.evidenceDate = evidenceDate;
    }

    /**
     * @return the camera
     */
    public String getCamera() {
        return camera;
    }

    /**
     * @param camera the camera to set
     */
    public void setCamera(String camera) {
        this.camera = camera;
    }

    public int compareTo(EvidenceDTO o) {
        return this.getEvidenceId() - o.getEvidenceId();
    }

    /**
     * @return the proofs
     */
    public List<ProofDTO> getProofs() {
        if (proofs == null) {
            proofs = new ArrayList<ProofDTO>();
        }
        return proofs;
    }

    /**
     * @param proofs the proofs to set
     */
    public void setProofs(List<ProofDTO> proofs) {
        this.proofs = proofs;
    }

    /**
     * @return the evidenceEvaluationId
     */
    public Integer getEvidenceEvaluationId() {
        return evidenceEvaluationId;
    }

    /**
     * @param evidenceEvaluationId the evidenceEvaluationId to set
     */
    public void setEvidenceEvaluationId(Integer evidenceEvaluationId) {
        this.evidenceEvaluationId = evidenceEvaluationId;
    }

    /**
     * @return the evidenceEvaluationResult
     */
    public Integer getEvidenceEvaluationResult() {
        return evidenceEvaluationResult;
    }

    /**
     * @param evidenceEvaluationResult the evidenceEvaluationResult to set
     */
    public void setEvidenceEvaluationResult(Integer evidenceEvaluationResult) {
        this.evidenceEvaluationResult = evidenceEvaluationResult;
    }

    /**
     * @return the cantDoReason
     */
    public String getCantDoReason() {
        return cantDoReason;
    }

    /**
     * @param cantDoReason the cantDoReason to set
     */
    public void setCantDoReason(String cantDoReason) {
        this.cantDoReason = cantDoReason;
    }

    /**
     * @return the defaultEvidenceProvider
     */
    public Boolean getDefaultEvidenceProvider() {
        return defaultEvidenceProvider;
    }

    /**
     * @param defaultEvidenceProvider the defaultEvidenceProvider to set
     */
    public void setDefaultEvidenceProvider(Boolean defaultEvidenceProvider) {
        this.defaultEvidenceProvider = defaultEvidenceProvider;
    }

    /**
     * @return the viewOrder
     */
    public Integer getViewOrder() {
        return viewOrder;
    }

    /**
     * @param viewOrder the viewOrder to set
     */
    public void setViewOrder(Integer viewOrder) {
        this.viewOrder = viewOrder;
    }
}
