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
 *  CalculaMaxRecordsUploadProcessDetailCommand.java
 * 
 *  Created on 14-01-2011, 05:17:21 PM
 * 
 */
package com.scopix.periscope.reporting.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.UploadProcess;
import com.scopix.periscope.reporting.UploadProcessDetail;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAO;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAOImpl;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class CalculaMaxRecordsUploadProcessDetailCommand {

    private static Logger log = Logger.getLogger(CalculaMaxRecordsUploadProcessDetailCommand.class);
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

    public Integer execute(UploadProcess processRunning) throws ScopixException {
        Integer totProcess = 0;
        for (UploadProcessDetail detail : processRunning.getProcessDetails()) {
            Integer totRecords = getDao().getTotalRecords(detail);
            log.debug("[DateEnd:" + detail.getDateEnd() + "][AreaType:" + detail.getAreaType().getId() + "]"
                    + "[Store:" + detail.getStore().getId() + "][totRecords: " + totRecords + "]");
            detail.setTotalRecords(totRecords);
            totProcess += totRecords;
        }
        log.debug("[totProcess:" + totProcess + "]");
        return totProcess;
    }
}
