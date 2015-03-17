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
 *  UsersGroup.java
 * 
 *  Created on May 27, 2014, 4:16:12 PM
 * 
 */
package com.scopix.periscope.operatorsgroup;

import com.scopix.periscope.groupuser.GroupUser;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Sebastian
 */
@Entity
public class OperatorsGroup extends BusinessObject {

    private String groupName;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "operatorsGroup")
    @Cascade({CascadeType.DELETE_ORPHAN, CascadeType.REMOVE, CascadeType.SAVE_UPDATE})
    private List<GroupUser> users;

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the users
     */
    public List<GroupUser> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<GroupUser> users) {
        this.users = users;
    }
}
