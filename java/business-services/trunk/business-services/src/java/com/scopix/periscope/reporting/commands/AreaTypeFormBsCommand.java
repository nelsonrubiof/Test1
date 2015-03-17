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
 *  AreaTypeFormBsCommand.java
 * 
 *  Created on 18-05-2011, 04:01:38 PM
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
public class AreaTypeFormBsCommand {

    private static Logger log = Logger.getLogger(AreaTypeFormBsCommand.class);
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

    public List<Map<String, Object>> execute() {
        log.debug("start");
        List<Map<String, Object>> areaTypes = getDao().getAreaTypeFromBS();
        log.debug("end");
        return areaTypes;

    }
}
