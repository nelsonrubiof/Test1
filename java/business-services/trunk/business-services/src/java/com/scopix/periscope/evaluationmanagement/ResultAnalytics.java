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
 *  ResultAnalytics.java
 * 
 *  Created on 09-03-2012, 04:43:06 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author nelson
 */
@Entity
public class ResultAnalytics extends BusinessObject {

    @Lob
    private String data;
    private Integer cantDetections;
    @OneToOne(fetch = FetchType.EAGER)
    private PendingEvaluation pendingEvaluation;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRecord;
    @Enumerated(EnumType.STRING)
    private ResultAnalyticsType resultAnalyticsType;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getCantDetections() {
        return cantDetections;
    }

    public void setCantDetections(Integer cantDetections) {
        this.cantDetections = cantDetections;
    }

    public PendingEvaluation getPendingEvaluation() {
        return pendingEvaluation;
    }

    public void setPendingEvaluation(PendingEvaluation pendingEvaluation) {
        this.pendingEvaluation = pendingEvaluation;
    }

    public Date getDateRecord() {
        return dateRecord;
    }

    public void setDateRecord(Date dateRecord) {
        this.dateRecord = dateRecord;
    }

    /**
     * @return the resultAnalyticsType
     */
    public ResultAnalyticsType getResultAnalyticsType() {
        return resultAnalyticsType;
    }

    /**
     * @param resultAnalyticsType the resultAnalyticsType to set
     */
    public void setResultAnalyticsType(ResultAnalyticsType resultAnalyticsType) {
        this.resultAnalyticsType = resultAnalyticsType;
    }
}
