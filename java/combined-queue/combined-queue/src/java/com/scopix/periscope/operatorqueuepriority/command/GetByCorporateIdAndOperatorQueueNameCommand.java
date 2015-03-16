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
 * Created on 07/11-2013, 03:36:14 PM
 *
 */
package com.scopix.periscope.operatorqueuepriority.command;

import com.scopix.periscope.operatorqueuepriority.OperatorQueuePriority;
import com.scopix.periscope.operatorqueuepriority.dao.OperatorQueuePriorityDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.apache.log4j.Logger;

/**
 *
 * @author Sebastian
 */


public class GetByCorporateIdAndOperatorQueueNameCommand{

    private static Logger log = Logger.getLogger(GetAllByCorporateNameCommand.class);

    /**
     * List all OperatorQueuePriority get objects  CorporateId and OperatorQueueName
     * @param corporateId 
     * @param operatorQueueName 
     * @return OperatorQueuePriority 
     */
    public OperatorQueuePriority execute(Integer corporateId, String operatorQueueName) {
        log.debug("executing execute()");
        return SpringSupport.getInstance().findBeanByClassName(OperatorQueuePriorityDAO.class)
                .getByCorporateIdAndOperatorQueueName(corporateId, operatorQueueName);
        
    }
    
}
