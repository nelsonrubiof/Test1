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
 * GetOperatorQueueListCommand.java
 *
 * Created on 25-03-2010, 04:53:11 PM
 *
 */
package com.scopix.periscope.queuemanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.OperatorQueue;
import com.scopix.periscope.queuemanagement.dao.OperatorQueueManagementHibernateDAO;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetOperatorQueueListCommand {

    private Logger log = Logger.getLogger(GetOperatorQueueListCommand.class);

    public List<OperatorQueue> execute(OperatorQueue operatorQueue) throws ScopixException {
        log.debug("start");
        OperatorQueueManagementHibernateDAO dao = SpringSupport.getInstance().findBeanByClassName(
                OperatorQueueManagementHibernateDAO.class);
        List<OperatorQueue> operatorQueues = null;

        operatorQueues = dao.getOperatorQueuesList(operatorQueue);

        log.debug("end");
        return operatorQueues;
    }
}
