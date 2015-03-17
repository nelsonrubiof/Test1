/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  AutomaticEvaluationResultDTO.java
 * 
 *  Created on 14-05-2014, 04:52:18 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement;

import java.io.Serializable;

/**
 *
 * @author Nelson
 */
public class AutomaticEvaluationResultDTO implements Serializable {

    private static final long serialVersionUID = 2407672388063335817L;

    private String timestamp;
    private Boolean result;
    private long requestDuration;
    private String evaluationPath;
    private Integer evidenceId;
    private Integer situationTemplateId;
    private Integer observedMetricId;
    private Integer pendingEvaluationId;

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the result
     */
    public Boolean getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(Boolean result) {
        this.result = result;
    }

    /**
     * @return the requestDuration
     */
    public long getRequestDuration() {
        return requestDuration;
    }

    /**
     * @param requestDuration the requestDuration to set
     */
    public void setRequestDuration(long requestDuration) {
        this.requestDuration = requestDuration;
    }

    /**
     * @return the evaluationPath
     */
    public String getEvaluationPath() {
        return evaluationPath;
    }

    /**
     * @param evaluationPath the evaluationPath to set
     */
    public void setEvaluationPath(String evaluationPath) {
        this.evaluationPath = evaluationPath;
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
     * @return the situationTemplateId
     */
    public Integer getSituationTemplateId() {
        return situationTemplateId;
    }

    /**
     * @param situationTemplateId the situationTemplateId to set
     */
    public void setSituationTemplateId(Integer situationTemplateId) {
        this.situationTemplateId = situationTemplateId;
    }

    /**
     * @return the observedMetricId
     */
    public Integer getObservedMetricId() {
        return observedMetricId;
    }

    /**
     * @param observedMetricId the observedMetricId to set
     */
    public void setObservedMetricId(Integer observedMetricId) {
        this.observedMetricId = observedMetricId;
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
}
