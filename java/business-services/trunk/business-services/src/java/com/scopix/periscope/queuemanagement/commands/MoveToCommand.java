/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.queuemanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.dao.OperatorQueueManagementHibernateDAO;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Cesar
 */
public class MoveToCommand {

    private Logger log = Logger.getLogger(GetPendingEvaluationListBasicCommand.class);

    public void execute(List<Integer> peIds, Integer toQeueue) throws ScopixException {
        log.debug("start");
        OperatorQueueManagementHibernateDAO dao = SpringSupport.getInstance().
                findBeanByClassName(OperatorQueueManagementHibernateDAO.class);

        dao.moveToQueue(peIds, toQeueue);
        log.debug("end");
    }
}
