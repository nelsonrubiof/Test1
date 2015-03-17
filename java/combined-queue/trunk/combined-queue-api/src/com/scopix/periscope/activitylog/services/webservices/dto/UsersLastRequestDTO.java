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
 *  UsersLastRequest.java
 * 
 *  Created on Aug 21, 2014, 9:39:16 AM
 * 
 */
package com.scopix.periscope.activitylog.services.webservices.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@XmlRootElement(name = "UsersLastRequestDTO")
public class UsersLastRequestDTO {

    private String userName;
    private String corporateName;
    private String queueName;
    private String lastRequest;
    private String minutesAgo;

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
     * @return the lastRequest
     */
    public String getLastRequest() {
        return lastRequest;
    }

    /**
     * @param lastRequest the lastRequest to set
     */
    public void setLastRequest(String lastRequest) {
        this.lastRequest = lastRequest;
    }

    /**
     * @return the minutesAgo
     */
    public String getMinutesAgo() {
        return minutesAgo;
    }

    /**
     * @param minutesAgo the minutesAgo to set
     */
    public void setMinutesAgo(String minutesAgo) {
        this.minutesAgo = minutesAgo;
    }

    
}
