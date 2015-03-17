/*
 * 
 * Copyright � 2013, SCOPIX. All rights reserved.
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
 *
 */
package com.scopix.periscope.operatorqueuepriority.dao;

import com.scopix.periscope.operatorqueuepriority.OperatorQueuePriority;
import java.util.List;

/**
 *
 * @author Sebastian
 */


public interface OperatorQueuePriorityDAO {
    
    /**
     * List objects of type OperatorQueuePriority sorted by Corporate Name
     * @return List<OperatorQueuePriority>
     */
    List<OperatorQueuePriority> listByCorporateName();
    
    /**
     * finds a OperatorQueuePriority by corporate Id and Operator Queue Name
     * @param corporateId
     * @param operatorQueueName
     * @return OperatorQueuePriority
     */
    OperatorQueuePriority getByCorporateIdAndOperatorQueueName(Integer corporateId, String operatorQueueName); 
}
