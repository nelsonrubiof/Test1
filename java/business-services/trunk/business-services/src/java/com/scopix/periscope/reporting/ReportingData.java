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
 *  ReportingData.java
 * 
 *  Created on 24-01-2011, 03:09:48 PM
 * 
 */
package com.scopix.periscope.reporting;

import java.util.Date;

/**
 *
 * @author nelson
 */
public class ReportingData  {

    private Integer id;
    private Integer uploadProcessId;
    private Integer observedSituationId;
    private Integer observedMetricId;
    private Integer metricTemplateId;
    private Double metricVal;
    private boolean sentToMIS;
    private Date sentToMISDate;
    //es del OSE
    private Double target;
    //private Double standard;
    //debe ir fuera
    private Integer metricCount;
    private String department;
    private String product;
    private Integer areaId;
    private Integer storeId;
    private String state;
    private Date evidenceDate;
    //data desde evidence evaluation    
    private Date evaluationStartDate;    
    private Date evaluationEndDate;
    private boolean reject;
    private boolean cantDo;
    private String cantDoReason;
    private String evaluationUser;
    /**Fecha en la cual es ingresado el registro a Base de datos*/
    private Date dateRecord;
    /**Fecha en la cual se marco como reject*/
    private Date rejectedDate;
    private String rejectedUser;
    
    private Integer situationTemplateId;

    public Integer getUploadProcessId() {
        return uploadProcessId;
    }

    public void setUploadProcessId(Integer uploadProcessId) {
        this.uploadProcessId = uploadProcessId;
    }

    public Integer getObservedSituationId() {
        return observedSituationId;
    }

    public void setObservedSituationId(Integer observedSituationId) {
        this.observedSituationId = observedSituationId;
    }

    public Double getMetricVal() {
        return metricVal;
    }

    public void setMetricVal(Double metricVal) {
        this.metricVal = metricVal;
    }

    public boolean isSentToMIS() {
        return sentToMIS;
    }

    public void setSentToMIS(boolean sentToMIS) {
        this.sentToMIS = sentToMIS;
    }

    public Date getSentToMISDate() {
        return sentToMISDate;
    }

    public void setSentToMISDate(Date sentToMISDate) {
        this.sentToMISDate = sentToMISDate;
    }

    public Double getTarget() {
        return target;
    }

    public void setTarget(Double target) {
        this.target = target;
    }

    public Integer getMetricCount() {
        return metricCount;
    }

    public void setMetricCount(Integer metricCount) {
        this.metricCount = metricCount;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getEvidenceDate() {
        return evidenceDate;
    }

    public void setEvidenceDate(Date evidenceDate) {
        this.evidenceDate = evidenceDate;
    }

    public Date getEvaluationStartDate() {
        return evaluationStartDate;
    }

    public void setEvaluationStartDate(Date evaluationStartDate) {
        this.evaluationStartDate = evaluationStartDate;
    }

    public Date getEvaluationEndDate() {
        return evaluationEndDate;
    }

    public void setEvaluationEndDate(Date evaluationEndDate) {
        this.evaluationEndDate = evaluationEndDate;
    }

    public boolean isReject() {
        return reject;
    }

    public void setReject(boolean reject) {
        this.reject = reject;
    }

    public boolean isCantDo() {
        return cantDo;
    }

    public void setCantDo(boolean cantDo) {
        this.cantDo = cantDo;
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

    public Date getDateRecord() {
        return dateRecord;
    }

    public void setDateRecord(Date dateRecord) {
        this.dateRecord = dateRecord;
    }

    public Date getRejectedDate() {
        return rejectedDate;
    }

    public void setRejectedDate(Date rejectedDate) {
        this.rejectedDate = rejectedDate;
    }

    public String getRejectedUser() {
        return rejectedUser;
    }

    public void setRejectedUser(String rejectedUser) {
        this.rejectedUser = rejectedUser;
    }

    public Integer getObservedMetricId() {
        return observedMetricId;
    }

    public void setObservedMetricId(Integer observedMetricId) {
        this.observedMetricId = observedMetricId;
    }

    public Integer getMetricTemplateId() {
        return metricTemplateId;
    }

    public void setMetricTemplateId(Integer metricTemplateId) {
        this.metricTemplateId = metricTemplateId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
