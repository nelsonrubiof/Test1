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
 *  MetricNamesFromBsCommand.java
 * 
 *  Created on 01-02-2011, 11:20:31 AM
 * 
 */
package com.scopix.periscope.reporting.commands;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAO;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAOImpl;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class MetricNamesFromBsCommand {

    private static Logger log = Logger.getLogger(MetricNamesFromBsCommand.class);
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

    public List<Map> execute() {
        log.debug("start");
        List<Map> metricNames = getDao().getMetricNamesFromBS();
        log.debug("end");
        return metricNames;
    }
}
