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
 *  GetAreaTypesForUserCommand.java
 * 
 *  Created on 25-09-2013, 12:28:13 PM
 * 
 */
package com.scopix.periscope.securitymanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.dao.SecurityManagementHibernateDAO;
import com.scopix.periscope.securitymanagement.dto.AreaTypeDTO;
import java.util.List;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetAreaTypesForUserCommand {

    private SecurityManagementHibernateDAO dao;

    public List<AreaTypeDTO> execute(String userName) throws ScopixException {
        return getDao().getAreaTypesForUser(userName);
    }

    public SecurityManagementHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(SecurityManagementHibernateDAO.class);
        }
        return dao;
    }

    public void setDao(SecurityManagementHibernateDAO dao) {
        this.dao = dao;
    }
}
