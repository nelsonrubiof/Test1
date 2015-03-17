/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * GetPendingEvaluationListCommand.java
 *
 * Created on 14-05-2008, 03:22:06 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.evaluationmanagement.dao.PendingEvaluationHibernateDAO;
import com.scopix.periscope.evaluationmanagement.dao.PendingEvaluationHibernateDAOImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Nelson Rubio
 * @version 2.0.0
 */
@Deprecated
public class GetPendingEvaluationListCommand {

    private PendingEvaluationHibernateDAO dao;

    public List<PendingEvaluation> execute(PendingEvaluation pendingEvaluation) throws ScopixException {

        List<PendingEvaluation> pendingEvaluations = null;
        try {
            pendingEvaluations = getDao().findPendingEvaluationListSQL(pendingEvaluation);
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException("PENDING", objectRetrievalFailureException);
        }
        return pendingEvaluations;
    }

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
}
