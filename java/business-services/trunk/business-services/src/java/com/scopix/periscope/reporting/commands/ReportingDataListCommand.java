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
 *  ReportingDataListCommand.java
 * 
 *  Created on 31-01-2011, 11:55:45 AM
 * 
 */
package com.scopix.periscope.reporting.commands;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.ReportingData;
import com.scopix.periscope.reporting.UploadProcessDetail;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAO;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAOImpl;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class ReportingDataListCommand {

    private static Logger log = Logger.getLogger(ReportingDataListCommand.class);
    private ReportingHibernateDAO dao;

    public ReportingHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(ReportingHibernateDAOImpl.class);
        }
        return dao;
    }

    public void setDao(ReportingHibernateDAO dao) {
        this.dao = dao;
    }

    public List<ReportingData> execute(UploadProcessDetail detail) {
        log.debug("start");
        List<ReportingData> l = getDao().getReportingDataFormDetail(detail);
        log.debug("end");
        return l;
    }
}
