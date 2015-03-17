/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * SituationDTO.java
 * 
 * Created on 29-05-2009, 04:47:38 PM
 */
package com.scopix.periscope.evaluationmanagement.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class SituationSendDTO implements Comparable<SituationSendDTO> {

    private List<MetricSendDTO> metrics;
    private String corporate;
    private String storeDescription;
    private String storeName;
    private String area;
    private Integer pendingEvaluationId;
    private boolean rejected;
    private String rejectedObservation;
    private String evidenceDateTime;
    private Integer situationId;
    private String productName;
    private String productDescription;

    /**
     * @return the metrics
     */
    public List<MetricSendDTO> getMetrics() {
        if (metrics == null) {
            metrics = new ArrayList<MetricSendDTO>();
        }
        return metrics;
    }

    /**
     * @param metrics the metrics to set
     */
    public void setMetrics(List<MetricSendDTO> metrics) {
        this.metrics = metrics;
    }

    /**
     * @return the corporate
     */
    public String getCorporate() {
        return corporate;
    }

    /**
     * @param corporate the corporate to set
     */
    public void setCorporate(String corporate) {
        this.corporate = corporate;
    }

    /**
     * @return the area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return the pendingEvaluationId
     */
    public Integer getPendingEvaluationId() {
        return pendingEvaluationId;
    }

    /**
     * @param pendingEvaluationId the pendingEvaluationId to set
     */
    public void setPendingEvaluationId(Integer pendingEvaluationId) {
        this.pendingEvaluationId = pendingEvaluationId;
    }

    /**
     * @return the rejected
     */
    public boolean isRejected() {
        return rejected;
    }

    /**
     * @param rejected the rejected to set
     */
    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    /**
     * @return the rejectedObservation
     */
    public String getRejectedObservation() {
        return rejectedObservation;
    }

    /**
     * @param rejectedObservation the rejectedObservation to set
     */
    public void setRejectedObservation(String rejectedObservation) {
        this.rejectedObservation = rejectedObservation;
    }

    /**
     * @return the evidenceDateTime
     */
    public String getEvidenceDateTime() {
        return evidenceDateTime;
    }

    /**
     * @param evidenceDateTime the evidenceDateTime to set
     */
    public void setEvidenceDateTime(String evidenceDateTime) {
        this.evidenceDateTime = evidenceDateTime;
    }

    @Override
    public int compareTo(SituationSendDTO o) {
        return this.getSituationId() - o.getSituationId();
    }

    /**
     * @return the situationId
     */
    public Integer getSituationId() {
        return situationId;
    }

    /**
     * @param situationId the situationId to set
     */
    public void setSituationId(Integer situationId) {
        this.situationId = situationId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    /**
     * @return the storeDescription
     */
    public String getStoreDescription() {
        return storeDescription;
    }

    /**
     * @param storeDescription the storeDescription to set
     */
    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
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