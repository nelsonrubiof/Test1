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

import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.queuemanagement.dto.PendingEvaluationDTO;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@Deprecated
public class SavePriorityChangeCommand {

    private Logger log = Logger.getLogger(SavePriorityChangeCommand.class);

    public void execute(List<PendingEvaluationDTO> pendingEvaluationDTOs) throws ScopixException {
        log.debug("start");
        try {
            Collections.sort(pendingEvaluationDTOs, PendingEvaluationDTO.getComparatorByPriority());
            for (PendingEvaluationDTO pendingEvaluationDTO : pendingEvaluationDTOs) {
                PendingEvaluation pendingEvaluation = HibernateSupport.getInstance().findGenericDAO().get(pendingEvaluationDTO.
                        getPendingEvaluationId(), PendingEvaluation.class);
                pendingEvaluation.setPriority(pendingEvaluationDTO.getPriority());
                pendingEvaluation.backToQueue();
            }
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            log.error("error = " + objectRetrievalFailureException.getMessage(), objectRetrievalFailureException);
            //"periscopeexception.entity.validate.elementNotFound", new String[]{
            throw new ScopixException("tab.queueManagement.pending");
        }
        log.debug("end");
    }
}
