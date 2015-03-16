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
 *  ActivityLogModelDTO.java
 * 
 *  Created on Jun 11, 2014, 12:42:43 PM
 * 
 */
package com.scopix.periscope.activitylog.services.webservices.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@XmlRootElement(name = "ActivityLogDTO")
public class ActivityLogDTO {

    private Integer id;
    private String userName;
    private String status;
    private String queueName;
    private String corporateName;
    private String requestDate;
    private String sendDate;
    private Integer evalTimeSeconds;

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
     * @return the requestDate
     */
    public String getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate the requestDate to set
     */
    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * @return the sendDate
     */
    public String getSendDate() {
        return sendDate;
    }

    /**
     * @param sendDate the sendDate to set
     */
    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * @return the evalTimeSeconds
     */
    public Integer getEvalTimeSeconds() {
        return evalTimeSeconds;
    }

    /**
     * @param evalTimeSeconds the evalTimeSeconds to set
     */
    public void setEvalTimeSeconds(Integer evalTimeSeconds) {
        this.evalTimeSeconds = evalTimeSeconds;
    }
    
    
}
