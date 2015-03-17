/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * AddOperatorQueueCommand.java
 *
 * Created on 25-03-2010, 05:05:19 PM
 *
 */
package com.scopix.periscope.queuemanagement.commands;

import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.OperatorQueue;
import com.scopix.periscope.queuemanagement.OperatorQueueDetail;
import com.scopix.periscope.queuemanagement.dao.OperatorQueueManagementHibernateDAO;

/**
 *
 * @author Gustavo Alvarez
 */
public class AddOperatorQueueCommand {

    public void execute(OperatorQueue operatorQueue) {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        OperatorQueueManagementHibernateDAO operatorDao = SpringSupport.getInstance().findBeanByClassName(
                OperatorQueueManagementHibernateDAO.class);
        dao.save(operatorQueue);

        for (OperatorQueueDetail detail: operatorQueue.getOperatorQueueDetailList()) {
            detail.setId(null);
            detail.setOperatorQueue(operatorQueue);
            dao.save(detail);
        }

        operatorDao.changePendingEvaluationWhenCreateQueue(operatorQueue);
    }
}
