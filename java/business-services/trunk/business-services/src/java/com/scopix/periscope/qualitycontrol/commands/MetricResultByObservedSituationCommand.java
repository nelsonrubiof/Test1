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
 *  MetricResultByObservedSituationCommand.java
 * 
 *  Created on 24-11-2011, 02:39:58 PM
 * 
 */
package com.scopix.periscope.qualitycontrol.commands;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.qualitycontrol.dao.QualityControlHibernateDAO;
import com.scopix.periscope.qualitycontrol.dao.QualityControlHibernateDAOImpl;
import com.scopix.periscope.qualitycontrol.dto.MetricResultDTO;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class MetricResultByObservedSituationCommand {

    private static Logger log = Logger.getLogger(MetricResultByObservedSituationCommand.class);
    private QualityControlHibernateDAO dao;

    public List<MetricResultDTO> execute(Integer situationFinishedId) {
        log.info("start");
        List<MetricResultDTO> list = getDao().getMetricResultByObservedSituation(situationFinishedId);
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
