/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
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
package com.scopix.periscope.operatorqueuepriority.management;

import com.scopix.periscope.operatorqueuepriority.OperatorQueuePriority;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sebastian
 */


public interface OperatorQueuePriorityManager {
    
    /**
     * deletes OperatorQueuePriority object
     * @param id 
     */
    void deleteOperatorQueuePriority(Integer id);
    /**
     * Saves operatorQueuePriority
     * @param operatorQueuePriority
     */
    void saveOperatorQueuePriority(OperatorQueuePriority operatorQueuePriority);
    
    /**
     * List all OperatorQueuePriority objects Sorted By Corporate Name
     * @return List <OperatorQueuePriority>
     */
    List <OperatorQueuePriority> getAllByCorporateName();
    
    /**
     *  Makes a map with all the corporates and their queues
     * @return Map<Integer, List<String>>
     */
    Map<Integer, List<String>> getAllOrderedInMap();
    
    /**
     * Saves the new list of users assigned;
     * @param listOfUsers
     * @param listofGroups 
     * @param corporateId
     * @param operatorQueueName
     */
    void saveOperatorQueueUserSubscriptions(List<String> listOfUsers, List<Integer> listofGroups, Integer corporateId, String operatorQueueName);
    
    /**
     *  returns OperatorQueuePriority given a  corporateId and operatorQueueName
     * @param corporateId
     * @param operatorQueueName
     * @return OperatorQueuePriority 
     */
    OperatorQueuePriority getByCorporateIdAndOperatorQueueName(Integer corporateId, String operatorQueueName); 
}
