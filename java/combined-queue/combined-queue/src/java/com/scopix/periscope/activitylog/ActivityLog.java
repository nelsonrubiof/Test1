/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
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
 *  ActivityLog.java
 * 
 *  Created on Jun 10, 2014, 3:09:42 PM
 * 
 */
package com.scopix.periscope.activitylog;

import java.util.Date;

/**
 *
 * @author Sebastian
 */
public class ActivityLog {

    public static final String PENDING = "PENDING";
    public static final String COMPLETED = "COMPLETED";
    private Integer id;
    private String userName;
    private Integer pendingEvaluationId;
    private String status;
    private String corporateName;
    private String queueName;
    private Date requestDate;
    private Date sendDate;

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

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the corporateName
     */
    public String getCorporateName() {
        return corporateName;
    }

    /**
     * @param corporateName the corporateName to set
     */
    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    /**
     * @return the queueName
     */
    public String getQueueName() {
        return queueName;
    }

    /**
     * @param queueName the queueName to set
     */
    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    /**
     * @return the requestDate
     */
    public Date getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate the requestDate to set
     */
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * @return the sendDate
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * @param sendDate the sendDate to set
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
