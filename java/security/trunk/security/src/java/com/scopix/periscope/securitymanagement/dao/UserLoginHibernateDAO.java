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
 *  UserLoginHibernateDAO.java
 * 
 *  Created on 05-11-2010, 11:36:04 AM
 * 
 */
package com.scopix.periscope.securitymanagement.dao;

import com.scopix.periscope.securitymanagement.PeriscopeUser;
import com.scopix.periscope.securitymanagement.UserLogin;
import com.scopix.periscope.securitymanagement.dto.RolesGroupDTO;
import java.util.List;

/**
 *
 * @author nelson
 */
public interface UserLoginHibernateDAO {

    PeriscopeUser getUser(String user, String password);

    PeriscopeUser getUserByName(String user);

    List<String> getUserPrivileges(PeriscopeUser periscopeUser);

    void save(UserLogin userLogin);
    
    List<RolesGroupDTO> getRolesGroupForUser(String userName);
}
