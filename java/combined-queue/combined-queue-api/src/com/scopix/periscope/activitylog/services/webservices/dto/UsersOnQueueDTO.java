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
 *  UsersOnQueueDTO.java
 * 
 *  Created on Aug 13, 2014, 4:11:04 PM
 * 
 */

package com.scopix.periscope.activitylog.services.webservices.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@XmlRootElement(name = "UsersOnQueueDTO")
public class UsersOnQueueDTO {
    
    private String corporateName;
    private String queueName;
    private Integer totalNumber;
    private List<String> users;

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
     * @return the totalNumber
     */
    public Integer getTotalNumber() {
        return totalNumber;
    }

    /**
     * @param totalNumber the totalNumber to set
     */
    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    /**
     * @return the users
     */
    public List<String> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<String> users) {
        this.users = users;
    }

}
