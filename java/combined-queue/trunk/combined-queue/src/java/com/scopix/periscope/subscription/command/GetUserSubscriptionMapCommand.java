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
package com.scopix.periscope.subscription.command;

import com.scopix.periscope.groupuser.GroupUser;
import com.scopix.periscope.operatorsgroup.OperatorsGroup;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.subscription.Subscription;
import com.scopix.periscope.subscription.dao.SubscriptionDAO;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author Sebastian
 */
public class GetUserSubscriptionMapCommand {
    
    private static Logger log = Logger.getLogger(GetUserSubscriptionMapCommand.class);

    /**
     * Returns a Map containing the user subscriptions to a queue sorted by
     * priority
     *
     * @return Map<String, List<Map<String, Object>>>
     */
    public Map<String, List<Map<String, Object>>> execute() {
        log.debug("executing execute()");
        Map<String, List<Map<String, Object>>> userSubscriptionMap = new HashMap<String, List<Map<String, Object>>>();
        List<Subscription> userSubscriptions = SpringSupport.getInstance()
                .findBeanByClassName(SubscriptionDAO.class).getAllByUserNameAndCorporateIdAsc();
        for (Subscription userSubscription : userSubscriptions) {
            List<Map<String, Object>> operatorQueuesPList = new ArrayList<Map<String, Object>>();
            
            List<String> userNames = new ArrayList<String>();
            if (userSubscription.getOperatorsGroupId() != null) {
                OperatorsGroup operatorsGroup = HibernateSupport.getInstance().findGenericDAO().get(userSubscription.getOperatorsGroupId(), OperatorsGroup.class);
                for (GroupUser user : operatorsGroup.getUsers()) {
                    userNames.add(user.getUserName());
                }
            } else {
                userNames.add(userSubscription.getUserName());
            }
            for (String username : userNames) {
                
                if (userSubscriptionMap.containsKey(username)) {
                    operatorQueuesPList = userSubscriptionMap.get(username);
                }
                Map<String, Object> operatorQueuesPDetail = new HashMap<String, Object>();
                operatorQueuesPDetail.put("corporateId", userSubscription.getOperatorQueuePriority().getCorporateId());
                operatorQueuesPDetail.put("corporateName", userSubscription.getOperatorQueuePriority().getCorporateName());
                operatorQueuesPDetail.put("operatorQueueName", userSubscription.getOperatorQueuePriority().getOperatorQueueName());
                operatorQueuesPList.add(operatorQueuesPDetail);
                userSubscriptionMap.put(username, operatorQueuesPList);
            }
        }
        return userSubscriptionMap;
    }
}
