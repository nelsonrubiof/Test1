/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.queuemanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.dao.QueueManagementHibernateDAO;
import com.scopix.periscope.queuemanagement.dto.OperatorQueueDTO;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Cesar
 */
public class GetOperatorQueuesCommand {

    private Logger log = Logger.getLogger(GetPendingEvaluationListBasicCommand.class);

    public List<OperatorQueueDTO> execute() throws ScopixException {
        log.debug("start");
        QueueManagementHibernateDAO dao = SpringSupport.getInstance().findBeanByClassName(QueueManagementHibernateDAO.class);
        List<OperatorQueueDTO> operatorQueueDTOs  = dao.getOperatorQueues();
        log.debug("end, result = " + operatorQueueDTOs);
        return operatorQueueDTOs;
    }
}
