/*
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
 * EditOperatorQueuePriorityAction.java
 *
 * Created on 05/11/20013, 09:14:59 PM
 */
package com.scopix.periscope.subscription.management;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.subscription.Subscription;
import com.scopix.periscope.subscription.command.DeleteGroupSubscribtionsCommand;
import com.scopix.periscope.subscription.command.GetAllByGroupNameAndSortByCorporateIdCommand;
import com.scopix.periscope.subscription.command.GetAllByUserNameAndSortByCorporateIdCommand;
import com.scopix.periscope.subscription.command.GetUserSubscriptionMapCommand;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Sebastian
 */
@SpringBean(rootClass = SubscriptionManager.class)
@Transactional(rollbackFor = {ScopixException.class, Exception.class})
public class SubscriptionManagerImpl implements SubscriptionManager {

    private Logger log = Logger.getLogger(SubscriptionManagerImpl.class);
    private static Map<String, List<Map<String, Object>>> userSubscriptionMap;

    /**
     * Returns a map containing all the operator queue subscriptions. the map
     * keys refers to the userName It will return a list of Operator Queue
     * Subscriptios already sortered by priority The list contains a map with
     * two parameters set by the following keys: corporateId operatorQueueName
     *
     * @return Map <String, List<Map<String, Object>>>
     */
    public Map<String, List<Map<String, Object>>> getUserSubscriptionMapFromDb() {

        log.info("calling  GetUserSubscriptionMapCommand");

        GetUserSubscriptionMapCommand command = new GetUserSubscriptionMapCommand();
        return command.execute();

    }

    /**
     * Returns a List of Operator Queue Subscriptios already sortered by
     * priority The list contains a map with two parameters set by the following
     * keys: corporateId operatorQueueName
     *
     * @param userName
     * @return List<Map<String, Object>>
     */
    @Override
    public List<Map<String, Object>> getUserSubscriptionsList(String userName) {
        Map<String, List<Map<String, Object>>> map = getUserSubscriptionMap();
        return map.get(userName);
    }

    /**
     * Returns a map containing all the operator queue subscriptions. the map
     * keys refers to the userName It will return a list of Operator Queue
     * Subscriptions already sorted by priority The list contains a map with two
     * parameters set by the following keys: corporateId operatorQueueName
     *
     * @return Map <String, List<Map<String, Object>>>
     */
    @Override
    public Map<String, List<Map<String, Object>>> getUserSubscriptionMap() {
        if (userSubscriptionMap == null) {
            setUserSubscriptionMap(getUserSubscriptionMapFromDb());
        }
        return userSubscriptionMap;
    }

    /**
     * setter for user subscription map
     *
     * @param userSubscriptionMap
     */
    public static void setUserSubscriptionMap(Map<String, List<Map<String, Object>>> userSubscriptionMap) {
        SubscriptionManagerImpl.userSubscriptionMap = userSubscriptionMap;
    }

    /**
     * reloads the user subscription map
     */
    @Override
    public void reloadUserSubscriptionMap() {
        setUserSubscriptionMap(getUserSubscriptionMapFromDb());
    }

    /**
     * return a list of user subscription sorted by User Name and Operator Queue
     * Priority
     *
     * @param userName
     * @return List<UserSubscription> list of user subscriptions
     */
    @Override
    public List<Subscription> getAllByUserNameAndSortByCorporateIdAsc(String userName) {
        log.info("calling  getAllByUserNameAndSortByCorporateIdAsc");
        GetAllByUserNameAndSortByCorporateIdCommand command = new GetAllByUserNameAndSortByCorporateIdCommand();
        return command.execute(userName);
    }

    /**
     * return a list of user subscription matching the Group Name and Operator
     * Queue Priority
     *
     * @param groupName
     * @return List<Subscription>
     */
    @Override
    public List<Subscription> getAllByGroupNameAndSortByCorporateIdAsc(String groupName) {
        log.info("calling  getAllByUserNameAndSortByCorporateIdAsc");
        GetAllByGroupNameAndSortByCorporateIdCommand command = new GetAllByGroupNameAndSortByCorporateIdCommand();
        return command.execute(groupName);
    }

    @Override
    public void deleteByGroupId(Integer operatorsGroupId) {
        DeleteGroupSubscribtionsCommand deleteGroupSubscribtionsCommand = new DeleteGroupSubscribtionsCommand();
        deleteGroupSubscribtionsCommand.execute(operatorsGroupId);
    }
}
