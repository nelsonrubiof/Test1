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
 *  UserForGroup.java
 * 
 *  Created on May 27, 2014, 4:56:05 PM
 * 
 */
package com.scopix.periscope.groupuser;

import com.scopix.periscope.operatorsgroup.OperatorsGroup;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 *
 * @author Sebastian
 */
@Entity
public class GroupUser extends BusinessObject {

    private String userName;
    @ManyToOne(fetch = FetchType.EAGER)
    private OperatorsGroup operatorsGroup;

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
     * @return the operatorsGroup
     */
    public OperatorsGroup getOperatorsGroup() {
        return operatorsGroup;
    }

    /**
     * @param operatorsGroup the operatorsGroup to set
     */
    public void setOperatorsGroup(OperatorsGroup operatorsGroup) {
        this.operatorsGroup = operatorsGroup;
    }

}
