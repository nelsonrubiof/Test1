/*
 * 
 * Copyright @ 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * UserLogin.java
 *
 * Created on 09-05-2008, 07:44:32 PM
 *
 */
package com.scopix.periscope.securitymanagement;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *  This class don't extends of BusinessObject because the ID in BusinessObject is an
 *  Integer and this entity have an ID as Long.
 * @author Gustavo Alvarez
 */
@Entity
public class UserLogin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date loginDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date logoutDate;

    private String description;
    
    private String userName;
    
    @ManyToOne
    private PeriscopeUser periscopeUser;

    @ManyToOne
    private Corporate corporate;

    private String applicationName;

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(Date logoutDate) {
        this.logoutDate = logoutDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public PeriscopeUser getPeriscopeUser() {
        return periscopeUser;
    }

    public void setPeriscopeUser(PeriscopeUser periscopeUser) {
        this.periscopeUser = periscopeUser;
    }

    /**
     * @return the corporate
     */
    public Corporate getCorporate() {
        return corporate;
    }

    /**
     * @param corporate the corporate to set
     */
    public void setCorporate(Corporate corporate) {
        this.corporate = corporate;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
