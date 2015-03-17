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
 *  ManagerMock.java
 * 
 *  Created on 05-11-2010, 01:49:46 PM
 * 
 */
package com.scopix.periscope.securitymanagement.commands;

import com.scopix.periscope.securitymanagement.PeriscopeUser;
import java.util.Date;

/**
 *
 * @author nelson
 */
class ManagerMock {

    static PeriscopeUser createPeriscopeUser(int id) {
        PeriscopeUser user = new PeriscopeUser();
        user.setId(id);
        user.setName("user " + id);
        user.setPassword("user_" + id);
        user.setUserState(null);
        user.setEmail("user" + id + "@scopixsolutions.com");
        user.setModificationDate(new Date());
        user.setDeleted(false);
        user.setRolesGroups(null);
        user.setAreaTypes(null);
        user.setStores(null);
        user.setCorporates(null);
        user.setFullName("user " + id + " full");
        user.setJobPosition("");
        user.setMainCorporate(null);
        return user;
    }
}
