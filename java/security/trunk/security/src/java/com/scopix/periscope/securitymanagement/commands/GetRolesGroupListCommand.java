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
 * GetUserListCommand.java
 *
 * Created on 16-06-2008, 01:35:24 PM
 *
 */


package com.scopix.periscope.securitymanagement.commands;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.RolesGroup;
import com.scopix.periscope.securitymanagement.dao.SecurityManagementHibernateDAO;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public class GetRolesGroupListCommand {
    
    Logger log = Logger.getLogger(GetRolesGroupListCommand.class);
    
    public List<RolesGroup> execute(RolesGroup rolesGroup){
        log.debug("start");
        SecurityManagementHibernateDAO dao = SpringSupport.getInstance().
                findBeanByClassName(SecurityManagementHibernateDAO.class);
        
        List<RolesGroup> rolesGroups = dao.getRolesGroupList(rolesGroup);
        log.debug("end, result = " + rolesGroups);
        return rolesGroups;
    }
}