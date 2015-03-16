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
 * InactiveOperatorQueueCommand.java
 *
 * Created on 05-04-2010, 04:07:13 PM
 *
 */
package com.scopix.periscope.queuemanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.OperatorQueue;
import com.scopix.periscope.queuemanagement.dao.OperatorQueueManagementHibernateDAO;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class InactiveOperatorQueueCommand {
    private Logger log = Logger.getLogger(UpdateOperatorQueueCommand.class);

    public void execute(OperatorQueue operatorQueue) throws ScopixException {
        log.debug("start");
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        OperatorQueueManagementHibernateDAO operatorDao = SpringSupport.getInstance().findBeanByClassName(
                OperatorQueueManagementHibernateDAO.class);
        try {
            OperatorQueue operatorQueueTemp = dao.get(operatorQueue.getId(), OperatorQueue.class);

            operatorQueueTemp.setModifiedDate(operatorQueue.getModifiedDate());
            operatorQueueTemp.setActivo(Boolean.FALSE);

            operatorDao.updatePendingEvaluation(operatorQueue);
            
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException("operatorQueueManagement.error.objectNotFound",
                    objectRetrievalFailureException);
        }
        
        log.debug("end");
    }
}
