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

import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.securitymanagement.RolesGroup;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public class AddRolesGroupCommand {
    public void execute(RolesGroup rolesGroup){
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        dao.save(rolesGroup);
    }
}