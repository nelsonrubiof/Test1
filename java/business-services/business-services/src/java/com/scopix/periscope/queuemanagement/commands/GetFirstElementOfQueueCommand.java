/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.queuemanagement.commands;

import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.dao.QueueManagementHibernateDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author Cesar
 */
public class GetFirstElementOfQueueCommand {

    private Logger log = Logger.getLogger(GetFirstElementOfQueueCommand.class);

    public PendingEvaluation execute(String queueName) throws ScopixException {
        log.info("start queueName:" + queueName);
        QueueManagementHibernateDAO dao = SpringSupport.getInstance().findBeanByClassName(QueueManagementHibernateDAO.class);
        PendingEvaluation pendingEvaluation = dao.getFirstElementOfQueue(queueName);
        log.info("end, result = " + pendingEvaluation);
        return pendingEvaluation;
    }
}
