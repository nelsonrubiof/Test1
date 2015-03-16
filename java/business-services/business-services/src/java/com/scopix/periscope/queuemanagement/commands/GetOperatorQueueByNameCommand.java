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
 * GetOperatorQueueByNameCommand.java
 *
 * Created on 05-04-2010, 11:23:47 AM
 *
 */
package com.scopix.periscope.queuemanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.OperatorQueue;
import com.scopix.periscope.queuemanagement.dao.OperatorQueueManagementHibernateDAO;
import java.util.List;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetOperatorQueueByNameCommand {
    public List<OperatorQueue> execute(OperatorQueue operatorQueue) throws ScopixException {
        List<OperatorQueue> operatorQueues = null;
        OperatorQueueManagementHibernateDAO dao = SpringSupport.getInstance().findBeanByClassName(
                OperatorQueueManagementHibernateDAO.class);

        try {
            operatorQueues = dao.getOperatorQueueByName(operatorQueue);
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException("elementNotFound", objectRetrievalFailureException);
        }

        return operatorQueues;
    }
}
