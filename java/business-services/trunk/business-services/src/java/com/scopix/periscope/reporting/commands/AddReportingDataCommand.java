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
 *  AddReportingDataCommand.java
 * 
 *  Created on 03-02-2011, 12:30:20 PM
 * 
 */
package com.scopix.periscope.reporting.commands;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.ReportingData;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAO;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAOImpl;

/**
 *
 * @author nelson
 * @version 1.1.0
 */
public class AddReportingDataCommand {

    private ReportingHibernateDAO dao;

    /**
     *
     * @param data ReportingData datos a insertar o actualizar
     */
    public void execute(ReportingData data) {
        getDao().addReportingData(data);
    }

    /**
     *
     * @return ReportingHibernateDAO dao de datos
     */
    public ReportingHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(ReportingHibernateDAOImpl.class);
        }
        return dao;
    }

    /**
     *
     * @param dao ReportingHibernateDAO
     */
    public void setDao(ReportingHibernateDAO dao) {
        this.dao = dao;
    }
}
