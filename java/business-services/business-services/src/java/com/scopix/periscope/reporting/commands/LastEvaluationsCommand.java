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
 *  LastEvaluationsCommand.java
 * 
 *  Created on 01-06-2012, 10:24:55 AM
 * 
 */
package com.scopix.periscope.reporting.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAO;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAOImpl;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class LastEvaluationsCommand {

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

    /**
     * formato esperado de fecha yyyyMMdd HHmmss
     */
    public Map<String, Map<String, Double>> execute(List<Integer> situationTemplatesIds, Integer storeId, Integer cantGroup)
            throws ScopixException {
        Map<String, Map<String, Double>> ret = getDao().getLastEvaluations(situationTemplatesIds, storeId, cantGroup);
        return ret;
    }
}
