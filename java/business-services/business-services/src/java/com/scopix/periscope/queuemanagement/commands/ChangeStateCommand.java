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
 * SavePriorityChangeCommand.java
 *
 * Created on 02-04-2008, 02:05:43 PM
 *
 */
package com.scopix.periscope.queuemanagement.commands;

import com.scopix.periscope.evaluationmanagement.EvaluationState;
import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public class ChangeStateCommand {

    private Logger log = Logger.getLogger(ChangeStateCommand.class);

    public void execute(List<Integer> pendingEvaluationIds, String state) throws ScopixException {
        log.debug("start");
        try {
            for (int id : pendingEvaluationIds) {
                PendingEvaluation pendingEvaluation =
                        HibernateSupport.getInstance().findGenericDAO().get(id, PendingEvaluation.class);
                pendingEvaluation.setEvaluationState(EvaluationState.valueOf(state));
            }
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            log.error("error = " + objectRetrievalFailureException.getMessage(), objectRetrievalFailureException);
            //"periscopeexception.entity.validate.elementNotFound", new String[]{
            throw new ScopixException("tab.queueManagement.pending");
        }
        log.debug("end");
    }
}
