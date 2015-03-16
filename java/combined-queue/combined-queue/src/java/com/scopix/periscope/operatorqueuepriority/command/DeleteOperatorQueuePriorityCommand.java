/*
 * 
 * Copyright (C) 2013, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 *
 * Created on 30-10-20013, 09:14:59 PM
 *
 */
package com.scopix.periscope.operatorqueuepriority.command;

import com.scopix.periscope.operatorqueuepriority.OperatorQueuePriority;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

import org.apache.log4j.Logger;

/**
 *
 * @author Sebastian
 */
public class DeleteOperatorQueuePriorityCommand {

    private static Logger log = Logger.getLogger(DeleteOperatorQueuePriorityCommand.class);

    /**
     * Saves an OperatorQueuePriority object
     * @param id 
     */
    public void execute(Integer id) {
        log.info("start");
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        OperatorQueuePriority operatorQueuePriority = dao.get(id, OperatorQueuePriority.class);
        dao.remove(operatorQueuePriority);
        log.info("end");
    }
}
