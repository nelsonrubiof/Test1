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
 *  ObservedSituationFinishedListCommand.java
 * 
 *  Created on 24-11-2011, 12:30:40 PM
 * 
 */
package com.scopix.periscope.qualitycontrol.commands;

import com.scopix.periscope.periscopefoundation.util.SortUtil;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.qualitycontrol.dao.QualityControlHibernateDAO;
import com.scopix.periscope.qualitycontrol.dao.QualityControlHibernateDAOImpl;
import com.scopix.periscope.qualitycontrol.dto.ObservedSituationFinishedDTO;
import com.scopix.periscope.queuemanagement.dto.FilteringData;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class ObservedSituationFinishedListCommand {

    private static Logger log = Logger.getLogger(ObservedSituationFinishedListCommand.class);
    private QualityControlHibernateDAO dao;

    public List<ObservedSituationFinishedDTO> execute(FilteringData filters) {
        log.info("start");
        List<ObservedSituationFinishedDTO> list = getDao().getObservedSituationFinishedList(filters);
        //ordenamos la lista antes de devolverla
        LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
        cols.put("evidenceDate", Boolean.FALSE);
        cols.put("product", Boolean.FALSE);
        SortUtil.sortByColumn(cols, list);
        log.info("end");
        return list;
    }

    public QualityControlHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(QualityControlHibernateDAOImpl.class);
        }
        return dao;
    }

    public void setDao(QualityControlHibernateDAO dao) {
        this.dao = dao;
    }
}
