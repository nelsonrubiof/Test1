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
 *  PeriscopeUsersBackupForAccessReportCommand.java
 * 
 *  Created on 24-06-2011, 03:02:10 PM
 * 
 */
package com.scopix.periscope.qualitycontrol.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.dao.SecurityBackupHibernateDAO;
import com.scopix.periscope.qualitycontrol.dto.PeriscopeUserForQualityControlDTO;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class PeriscopeUsersBackupForAccessReportCommand {

    private static Logger log = Logger.getLogger(PeriscopeUsersBackupForAccessReportCommand.class);
    private SecurityBackupHibernateDAO dao;

    public List<PeriscopeUserForQualityControlDTO> execute(Date start, Date end, int corporateId) throws ScopixException {
        log.info("start");
        List<PeriscopeUserForQualityControlDTO> list = getDao().getPeriscopeUsersForAccessReports(start, end, corporateId);
        log.info("end");
        return list;

    }

    public SecurityBackupHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(SecurityBackupHibernateDAO.class);
        }
        return dao;
    }

    public void setDao(SecurityBackupHibernateDAO dao) {
        this.dao = dao;
    }
}
