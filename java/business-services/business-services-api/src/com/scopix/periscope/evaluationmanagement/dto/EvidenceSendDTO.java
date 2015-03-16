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
 * EvidenceSendDTO.java
 *
 * Created on 29-05-2009, 04:48:44 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class EvidenceSendDTO implements Comparable<EvidenceSendDTO> {

    private EvidenceProviderSendDTO evidenceProvider;
    private String evidencePath;
    private String proofPath;
    private Integer evidenceId;
    private String evidenceType;
    private String templatePath;
    private List<RegionTransferSendDTO> regionTransfers;

    /**
     * @return the evidenceProviderSendDTO
     */
    public EvidenceProviderSendDTO getEvidenceProvider() {
        return evidenceProvider;
    }

    /**
     * @param evidenceProviderSendDTO the evidenceProviderSendDTO to set
     */
    public void setEvidenceProvider(EvidenceProviderSendDTO evidenceProviderSendDTO) {
        this.evidenceProvider = evidenceProviderSendDTO;
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
     * @return the evidenceType
     */
    public String getEvidenceType() {
        return evidenceType;
    }

    /**
     * @param evidenceType the evidenceType to set
     */
    public void setEvidenceType(String evidenceType) {
        this.evidenceType = evidenceType;
    }

    /**
     * @return the templatePath
     */
    public String getTemplatePath() {
        return templatePath;
    }

    /**
     * @param templatePath the templatePath to set
     */
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public int compareTo(EvidenceSendDTO o) {
        return this.getEvidenceId() - o.getEvidenceId();
    }

    /**
     * @return the proofPath
     */
    public String getProofPath() {
        return proofPath;
    }

    /**
     * @param proofPath the proofPath to set
     */
    public void setProofPath(String proofPath) {
        this.proofPath = proofPath;
    }

    /**
     * @return the regionTransfers
     */
    public List<RegionTransferSendDTO> getRegionTransfers() {
        if (regionTransfers == null) {
            regionTransfers = new ArrayList<RegionTransferSendDTO>();
        }
        return regionTransfers;
    }

    /**
     * @param regionTransfers the regionTransfers to set
     */
    public void setRegionTransfers(List<RegionTransferSendDTO> regionTransfers) {
        this.regionTransfers = regionTransfers;
    }
}
