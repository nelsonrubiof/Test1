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
 *  RejectReportingDataFromObservedMetricCommand.java
 * 
 *  Created on 03-02-2011, 03:51:46 PM
 * 
 */

package com.scopix.periscope.reporting.commands;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAO;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAOImpl;
import java.util.List;

/**
 *
 * @author nelson
 */
public class RejectReportingDataFromObservedMetricCommand {
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

    public void execute(List<Integer> observedMetricIds, String userName) {
        getDao().rejectReportingDataByObservedMetricIds(observedMetricIds, userName);
    }
}
