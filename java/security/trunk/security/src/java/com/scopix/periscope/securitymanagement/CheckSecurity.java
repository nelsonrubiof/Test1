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
 * CheckSecurity.java
 *
 * Created on 12-05-2008, 01:32:37 PM
 *
 */
package com.scopix.periscope.securitymanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class CheckSecurity extends BusinessObject {

    //userName of the user logged
    private String userName;

    //sessionId of the user logged
    private Long sessionId;

    //description about the access
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date privilegeAccessTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPrivilegeAccessTime() {
        return privilegeAccessTime;
    }

    public void setPrivilegeAccessTime(Date privilegeAccessTime) {
        this.privilegeAccessTime = privilegeAccessTime;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
}
