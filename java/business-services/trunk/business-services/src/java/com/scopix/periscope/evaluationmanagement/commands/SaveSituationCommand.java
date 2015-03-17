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
 *  SaveSituationCommand.java
 * 
 *  Created on 11-07-2014, 03:17:03 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAO;
import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAOImpl;
import com.scopix.periscope.extractionplanmanagement.Situation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.apache.log4j.Logger;

/**
 *
 * @author Nelson
 */
public class SaveSituationCommand {

    private static Logger log = Logger.getLogger(SaveSituationCommand.class);
    private EvaluationHibernateDAO evaluationHibernateDAO;

    public void execute(Situation s) throws ScopixException {
        s.setId(getEvaluationHibernateDAO().getNextIdForSituation());
        getEvaluationHibernateDAO().saveSituation(s);
    }

    /**
     * @return the evaluationHibernateDAO
     */
    public EvaluationHibernateDAO getEvaluationHibernateDAO() {
        if (evaluationHibernateDAO == null) {
            evaluationHibernateDAO = SpringSupport.getInstance().findBeanByClassName(EvaluationHibernateDAOImpl.class);
        }
        return evaluationHibernateDAO;
    }

    /**
     * @param evaluationHibernateDAO the evaluationHibernateDAO to set
     */
    public void setEvaluationHibernateDAO(EvaluationHibernateDAO evaluationHibernateDAO) {
        this.evaluationHibernateDAO = evaluationHibernateDAO;
    }
}
