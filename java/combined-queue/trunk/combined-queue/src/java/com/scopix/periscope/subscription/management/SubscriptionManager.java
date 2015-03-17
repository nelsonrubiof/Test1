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
 * Created on 14/11/2013, 03:36:14 PM
 *
 */
package com.scopix.periscope.subscription.management;

import com.scopix.periscope.subscription.Subscription;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sebastian
 */
public interface SubscriptionManager {

    /**
     * Returns a map containing all the operator queue subscriptions. the map
     * keys refers to the userName It will return a list of Operator Queue
     * Subscriptions already sorted by priority The list contains a map with two
     * parameters set by the following keys: corporateId corporateName
     * operatorQueueName
     *
     * @return Map <String, List<Map<String, Object>>>
     */
    Map<String, List<Map<String, Object>>> getUserSubscriptionMap();

    /**
     * Returns a List of Operator Queue Subscriptions already sorted by priority
     * The list contains a map with two parameters set by the following keys:
     * corporateId corporateName operatorQueueName
     *
     * @param userName
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getUserSubscriptionsList(String userName);

    /**
     * reloads the user subscription map
     */
    void reloadUserSubscriptionMap();

    /**
     * return a list of user subscription sorted by User Name and Operator Queue
     * Priority
     *
     * @param userName
     * @return List<UserSubscription> list of user subscriptions
     */
    List<Subscription> getAllByUserNameAndSortByCorporateIdAsc(String userName);

    /**
     * return a list of user subscription matching the Group Name and Operator Queue
     * Priority
     * @param groupName
     * @return  List<Subscription>
     */
    List<Subscription> getAllByGroupNameAndSortByCorporateIdAsc(String groupName);
    
    
    /**
     * deletes by a given  group id
     * @param operatorsGroupId
     */
    public void deleteByGroupId(Integer operatorsGroupId);
    
    
}
