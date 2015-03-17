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
import com.scopix.periscope.operatorqueuepriority.dao.OperatorQueuePriorityDAO;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.subscription.Subscription;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 *
 * @author Sebastian
 */
public class SaveOQUserSubscriptionsCommand {

    private static Logger log = Logger.getLogger(SaveOQUserSubscriptionsCommand.class);

    /**
     * Saves an OperatorQueuePriority object
     *
     * @param listOfUsers
     * @param listofGroups
     * @param corporateId
     * @param operatorQueueName
     */
    public void execute(List<String> listOfUsers, List<Integer> listofGroups, Integer corporateId, String operatorQueueName) {
        log.info("start");
        OperatorQueuePriorityDAO extendedDAO = SpringSupport.getInstance().findBeanByClassName(OperatorQueuePriorityDAO.class);
        OperatorQueuePriority operatorQueuePriority = extendedDAO
                .getByCorporateIdAndOperatorQueueName(corporateId, operatorQueueName);
        List<Subscription> userSubscriptions = operatorQueuePriority.getUserSubscriptions();
        List<Subscription> deletedUserSubscriptions = new ArrayList<Subscription>();
        for (Subscription userSubscription : userSubscriptions) {
            Boolean found = false;
            if (userSubscription.getUserName() != null && !userSubscription.getUserName().isEmpty()) {
                for (String username : listOfUsers) {
                    if (username.equals(userSubscription.getUserName())) {
                        found = true;
                    }
                }
                if (!found) {
                    deletedUserSubscriptions.add(userSubscription);
                }
            } else {
                for (Integer groupId : listofGroups) {
                    if (userSubscription.getOperatorsGroupId().equals(groupId)) {
                        found = true;
                    }
                }
                if (!found) {
                    deletedUserSubscriptions.add(userSubscription);
                }
            }
        }
        for (String username : listOfUsers) {
            Boolean found = false;
            for (Subscription userSubscription : userSubscriptions) {
                if (userSubscription.getUserName() != null && userSubscription.getUserName().equals(username)) {
                    found = true;
                }
            }
            if (!found) {
                Subscription newUserSubscription = new Subscription();
                newUserSubscription.setUserName(username);
                newUserSubscription.setOperatorQueuePriority(operatorQueuePriority);
                userSubscriptions.add(newUserSubscription);
            }
        }
        for (Integer groupId : listofGroups) {
            Boolean found = false;
            for (Subscription userSubscription : userSubscriptions) {
                if (userSubscription.getOperatorsGroupId() != null && userSubscription.getOperatorsGroupId().equals(groupId)) {
                    found = true;
                }
            }
            if (!found) {
                Subscription newUserSubscription = new Subscription();
                newUserSubscription.setOperatorsGroupId(groupId);
                newUserSubscription.setOperatorQueuePriority(operatorQueuePriority);
                userSubscriptions.add(newUserSubscription);
            }
        }
        userSubscriptions.removeAll(deletedUserSubscriptions);
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        dao.save(operatorQueuePriority);
        log.info("end");
    }
}
