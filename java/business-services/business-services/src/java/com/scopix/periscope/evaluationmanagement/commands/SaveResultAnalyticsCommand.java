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
 *  SaveResultAnalyticsCommand.java
 * 
 *  Created on 09-03-2012, 04:37:41 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.evaluationmanagement.ResultAnalytics;
import com.scopix.periscope.evaluationmanagement.ResultAnalyticsType;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.Date;

/**
 *
 * @author nelson
 */
public class SaveResultAnalyticsCommand {

    private GenericDAO dao;

    public GenericDAO getDao() {
        if (dao == null) {
            dao = HibernateSupport.getInstance().findGenericDAO();
        }
        return dao;
    }

    public void setDao(GenericDAO dao) {
        this.dao = dao;
    }

    public void execute(String xml, Integer pendingEvaluationId, Integer cantDetections, ResultAnalyticsType type) {
        ResultAnalytics analytics = new ResultAnalytics();
        analytics.setData(xml);
        analytics.setDateRecord(new Date());
        PendingEvaluation pe = new PendingEvaluation();
        pe.setId(pendingEvaluationId);
        analytics.setPendingEvaluation(pe);
        analytics.setCantDetections(cantDetections);
        analytics.setResultAnalyticsType(type);
        getDao().save(analytics);
    }
}
