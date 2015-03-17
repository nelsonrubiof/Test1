package com.scopix.periscope.model;

import java.io.Serializable;

/**
 * Clase para referenciar datos de las evidencias a evaluar en la aplicación
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public class Evidence implements Serializable {

    private int evidenceId;
    private String proofPath;
    private String evidencePath;
    private String templatePath;
    private String evidenceType;
    private String operatorImagesIp;
    private EvidenceProvider evidenceProvider;
    private static final long serialVersionUID = 5659001518086160781L;

    /**
     * @return the evidenceId
     */
    public int getEvidenceId() {
        return evidenceId;
    }

    /**
     * @param evidenceId the evidenceId to set
     */
    public void setEvidenceId(int evidenceId) {
        this.evidenceId = evidenceId;
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
     * @return the evidenceProvider
     */
    public EvidenceProvider getEvidenceProvider() {
        return evidenceProvider;
    }

    /**
     * @param evidenceProvider the evidenceProvider to set
     */
    public void setEvidenceProvider(EvidenceProvider evidenceProvider) {
        this.evidenceProvider = evidenceProvider;
    }

    /**
     * @return the operatorImagesIp
     */
    public String getOperatorImagesIp() {
        return operatorImagesIp;
    }

    /**
     * @param operatorImagesIp the operatorImagesIp to set
     */
    public void setOperatorImagesIp(String operatorImagesIp) {
        this.operatorImagesIp = operatorImagesIp;
    }
}