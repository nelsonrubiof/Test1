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
 * AddBusinessRuleCheckCommand.java
 *
 * Created on 08-05-2008, 12:59:35 PM
 *
 */
package com.scopix.periscope.queuemanagement.commands;

import com.scopix.periscope.evaluationmanagement.EvaluationState;
import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.dao.QueueManagementHibernateDAO;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class ExecutePriorityChangeRecoveryCommand {

    private Logger log = Logger.getLogger(ExecutePriorityChangeRecoveryCommand.class);

    public void execute(List<Integer> pendingEvaluationIds, int startPosition, Integer queueNameId) throws ScopixException {
        log.info("start");
        QueueManagementHibernateDAO dao = SpringSupport.getInstance().findBeanByClassName(QueueManagementHibernateDAO.class);
        GenericDAO genericDAO = HibernateSupport.getInstance().findGenericDAO();
        for (Integer id : pendingEvaluationIds) {
            PendingEvaluation pe = genericDAO.get(id, PendingEvaluation.class);
            pe.setEvaluationState(EvaluationState.ENQUEUED);
            pe.setChangePriority(true);
            pe.setEvidenceEvaluations(null);
            //limpiamos los datos asignados como usuario y fechas de inicio y fin
            pe.setUserName(null);
            pe.setEvaluationStartDate(null);
            pe.setEvaluationEndDate(null);
        }
        dao.changePriorityToPendingEvaluation(startPosition, queueNameId);
        log.info("end");
    }
}
