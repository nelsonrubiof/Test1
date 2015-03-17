/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * GetPendingEvaluationCommand.java
 *
 * Created on 14-05-2008, 03:22:06 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public class GetPendingEvaluationCommand {
    private static Logger log = Logger.getLogger(GetPendingEvaluationCommand.class);

    public PendingEvaluation execute(Integer id) throws ScopixException {
        log.info("start " + id);
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        PendingEvaluation pendingEvaluation = null;

        //PENDING error message
        try {
            pendingEvaluation = dao.get(id, PendingEvaluation.class);
            pendingEvaluation.getObservedSituation().getObservedMetrics().isEmpty();
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException("PENDING", objectRetrievalFailureException);
        }
        log.info("end");
        return pendingEvaluation;
    }
}
