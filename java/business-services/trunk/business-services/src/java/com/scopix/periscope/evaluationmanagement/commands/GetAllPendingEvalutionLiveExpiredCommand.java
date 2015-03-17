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
 *  GetAllPendingEvalutionLiveExpiredCommand.java
 * 
 *  Created on 28-05-2014, 09:58:49 AM
 * 
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAO;
import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAOImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Nelson
 */
public class GetAllPendingEvalutionLiveExpiredCommand {

    private static Logger log = Logger.getLogger(GetAllPendingEvalutionLiveExpiredCommand.class);
    private EvaluationHibernateDAO dao;
    public List<Integer> execute(String date) throws ScopixException {
        log.info("start");
        List<Integer> l = getDao().findAllPendingEvalutionLiveExpired(date);
        log.info("end");
        return l;

    }

    /**
     * @return the dao
     */
    public EvaluationHibernateDAO getDao() {
        if(dao == null) {
            dao = HibernateSupport.getInstance().findDao(EvaluationHibernateDAOImpl.class);
        }
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(EvaluationHibernateDAO dao) {
        this.dao = dao;
    }
}
