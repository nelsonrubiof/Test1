/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.queuemanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.dao.QueueManagementHibernateDAO;
import com.scopix.periscope.queuemanagement.dto.PendingEvaluationDTO;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Cesar
 */
public class GetNFirstElementOfQueueCommand {

    private Logger log = Logger.getLogger(GetPendingEvaluationListBasicCommand.class);

    public List<PendingEvaluationDTO> execute(int quantity, Integer queueNameId) throws ScopixException {
        log.debug("start");
        QueueManagementHibernateDAO dao = SpringSupport.getInstance().findBeanByClassName(QueueManagementHibernateDAO.class);
        List<PendingEvaluationDTO> pendingEvaluations = null;
        pendingEvaluations = dao.getNFirstElementOfQueue(quantity, queueNameId);
        log.debug("end, result = " + pendingEvaluations);
        return pendingEvaluations;
    }
}
