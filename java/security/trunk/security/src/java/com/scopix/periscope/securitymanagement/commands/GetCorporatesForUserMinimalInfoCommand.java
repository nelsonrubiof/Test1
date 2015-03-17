/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * GetCorporatesForUserMinimalInfoCommand.java
 *
 * Created on 23-09-2013, 15:41:56 PM
 *
 */
package com.scopix.periscope.securitymanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.dao.SecurityManagementHibernateDAO;
import com.scopix.periscope.securitymanagement.dto.CorporateDTO;
import java.util.List;

/**
 *
 * @author Gustavo alvarez
 */
public class GetCorporatesForUserMinimalInfoCommand {

    private SecurityManagementHibernateDAO dao;
    
    public List<CorporateDTO> execute(String userName) throws ScopixException {
        return getDao().getCorporatesForUser(userName);
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
