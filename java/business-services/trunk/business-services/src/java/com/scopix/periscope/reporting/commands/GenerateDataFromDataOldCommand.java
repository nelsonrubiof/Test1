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
 *  GenerateDataFromDataOldCommand.java
 * 
 *  Created on 06-09-2011, 03:02:56 PM
 * 
 */

package com.scopix.periscope.reporting.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAO;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAOImpl;

/**
 *
 * @author nelson
 * @version 1.0.0
 * @deprecated ya no se utiliza
 */

public class GenerateDataFromDataOldCommand {
    private ReportingHibernateDAO dao;

    public ReportingHibernateDAO getDao() {
        if (dao == null){
            dao = SpringSupport.getInstance().findBeanByClassName(ReportingHibernateDAOImpl.class);
        }
        return dao;
    }

    public void setDao(ReportingHibernateDAO dao) {
        this.dao = dao;
    }

    public void execute() throws ScopixException {
        getDao().generateReportingDataFromDataOld();
    }
}
