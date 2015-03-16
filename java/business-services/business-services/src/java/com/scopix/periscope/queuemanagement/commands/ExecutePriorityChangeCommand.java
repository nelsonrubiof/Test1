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

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.dao.QueueManagementHibernateDAO;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class ExecutePriorityChangeCommand {

    private Logger log = Logger.getLogger(ExecutePriorityChangeCommand.class);

    public void execute(List<Integer> pendingEvaluationIds, Integer queueNameId, int startPosition) throws ScopixException {
        log.debug("start");
        QueueManagementHibernateDAO dao = SpringSupport.getInstance().findBeanByClassName(QueueManagementHibernateDAO.class);
        dao.updatePendingEvaluationForChangePriority(pendingEvaluationIds);
        dao.changePriorityToPendingEvaluation(startPosition, queueNameId);
        log.debug("end");
    }
}
