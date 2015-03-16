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
 *  AllLastEvaluationsCommand.java
 * 
 *  Created on 07-10-2013, 09:37:29 AM
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
 * @author Nelson Rubio
 * @autor-email nelson.rubio@scopixsolutions.com
 * @version 1.0.0
 */
public class AllLastEvaluationsCommand {

    private static Logger log = Logger.getLogger(AllLastEvaluationsCommand.class);
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

    public Map<String, Map<String, Double>> execute(List<Integer> situationTemplatesIds, List<Integer> storeIds) {
        log.info("start");
        Map<String, Map<String, Double>> ret = getDao().getAllLastEvaluations(situationTemplatesIds, storeIds);
        log.info("end");
        return ret;
    }
}
