/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  QueueManager.java
 * 
 *  Created on 05/11/2013
 *
 * 
 */

package com.scopix.periscope.queuemanagement;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.util.List;

/**
 *
 * @author Sebastian
 */


public interface QueueManager {
    
    /**
     * Retrieve a list with operator queues and its details for a desired corporate
     * @param corporate
     * @return
     * @throws ScopixException  
     */
    List<Queue> getQueuesForOperator(String corporate) throws ScopixException;
    
    /**
     * calls a webservice method that receives a map containing all the available
     * pending evaluation evidences for corporate and ordered by queue and saves this info
     * in a static hashmap
     * @param corporate
     * @throws ScopixException
     */
    void getPendingEvaluationEvidenceMap(String corporate) throws ScopixException; 
    
    /**
     * returns a Integer representing the total of queues
     * @param corporateName 
     * @param queueName
     * @return
     */
    Integer getPendingElementsForQueue(String corporateName, String queueName);
    
}
