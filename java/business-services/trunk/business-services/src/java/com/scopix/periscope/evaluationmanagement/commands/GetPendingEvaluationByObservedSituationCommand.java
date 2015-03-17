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
 *  GetPendingEvaluationByObservedSituationCommand.java
 * 
 *  Created on 05-06-2013, 02:57:02 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.evaluationmanagement.dao.PendingEvaluationHibernateDAO;
import com.scopix.periscope.evaluationmanagement.dao.PendingEvaluationHibernateDAOImpl;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class GetPendingEvaluationByObservedSituationCommand {

    private static Logger log = Logger.getLogger(GetPendingEvaluationByObservedSituationCommand.class);
    private PendingEvaluationHibernateDAO dao;

    /**
     * @return the dao
     */
    public PendingEvaluationHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(PendingEvaluationHibernateDAOImpl.class);
        }
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(PendingEvaluationHibernateDAO dao) {
        this.dao = dao;
    }

    public PendingEvaluation execute(Integer observedSituationId) {
        log.info("start [observedSituationId:" + observedSituationId + "]"); 
        PendingEvaluation pe = getDao().getPendingEvaluationFromObservedSituationId(observedSituationId);
        log.info("end " + pe);
        return pe;
    }
}
