package com.scopix.periscope.model;

import java.io.Serializable;
import java.util.List;

/**
 * Clase para referenciar datos de los clientes de la aplicación
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public class Client implements Serializable {

    private String name;
    private String security;
    private String proofPath;
    private String description;
    private String templateUrl;
    private String evidenceUrl;
    private List<Queue> queues;
    private String uniqueCorporateId;
    private String evaluationWebService;
    private String operatorImgServicesURL;
    private String operatorImgPrivateServicesURL;
    
    private static final long serialVersionUID = 780322199731394953L;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the queues
     */
    public List<Queue> getQueues() {
        return queues;
    }

    /**
     * @param queues the queues to set
     */
    public void setQueues(List<Queue> queues) {
        this.queues = queues;
    }

    /**
     * @return the uniqueCorporateId
     */
    public String getUniqueCorporateId() {
        return uniqueCorporateId;
    }

    /**
     * @param uniqueCorporateId the uniqueCorporateId to set
     */
    public void setUniqueCorporateId(String uniqueCorporateId) {
        this.uniqueCorporateId = uniqueCorporateId;
    }

    /**
     * @return the evaluationWebService
     */
    public String getEvaluationWebService() {
        return evaluationWebService;
    }

    /**
     * @param evaluationWebService the evaluationWebService to set
     */
    public void setEvaluationWebService(String evaluationWebService) {
        this.evaluationWebService = evaluationWebService;
    }

    /**
     * @return the evidenceUrl
     */
    public String getEvidenceUrl() {
        return evidenceUrl;
    }

    /**
     * @param evidenceUrl the evidenceUrl to set
     */
    public void setEvidenceUrl(String evidenceUrl) {
        this.evidenceUrl = evidenceUrl;
    }

    /**
     * @return the templateUrl
     */
    public String getTemplateUrl() {
        return templateUrl;
    }

    /**
     * @param templateUrl the templateUrl to set
     */
    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    /**
     * @return the operatorImgServicesURL
     */
    public String getOperatorImgServicesURL() {
        return operatorImgServicesURL;
    }

    /**
     * @param operatorImgServicesURL the operatorImgServicesURL to set
     */
    public void setOperatorImgServicesURL(String operatorImgServicesURL) {
        this.operatorImgServicesURL = operatorImgServicesURL;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return the security
     */
    public String getSecurity() {
        return security;
    }

    /**
     * @param security the security to set
     */
    public void setSecurity(String security) {
        this.security = security;
    }

    /**
     * @return the operatorImgPrivateServicesURL
     */
    public String getOperatorImgPrivateServicesURL() {
        return operatorImgPrivateServicesURL;
    }

    /**
     * @param operatorImgPrivateServicesURL the operatorImgPrivateServicesURL to set
     */
    public void setOperatorImgPrivateServicesURL(String operatorImgPrivateServicesURL) {
        this.operatorImgPrivateServicesURL = operatorImgPrivateServicesURL;
    }
}