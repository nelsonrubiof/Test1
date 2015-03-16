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
 *  ReportingDataListByObservedSituationCommand.java
 * 
 *  Created on 02-09-2011, 02:56:02 PM
 * 
 */
package com.scopix.periscope.reporting.commands;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.ReportingData;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAO;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAOImpl;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class ReportingDataListByObservedSituationCommand {

    private ReportingHibernateDAO dao;
    private static Logger log = Logger.getLogger(ReportingDataListByObservedSituationCommand.class);

    public ReportingHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(ReportingHibernateDAOImpl.class);
        }
        return dao;
    }

    public void setDao(ReportingHibernateDAO dao) {
        this.dao = dao;
    }

    public List<ReportingData> execute(Integer osId) {
        log.info("start osId:" + osId);
        List<ReportingData> datas = getDao().getReportingDataListByObservedSituation(osId);

        log.debug("registros null:" + (datas == null));
        return datas;
    }
}
