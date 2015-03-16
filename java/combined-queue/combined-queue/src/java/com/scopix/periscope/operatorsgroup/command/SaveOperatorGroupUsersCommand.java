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
 *  SaveOperatorGroupUsersCommand.java
 * 
 *  Created on May 30, 2014, 3:47:49 PM
 * 
 */
package com.scopix.periscope.operatorsgroup.command;

import com.scopix.periscope.groupuser.GroupUser;
import com.scopix.periscope.operatorqueuepriority.OperatorQueuePriority;
import com.scopix.periscope.operatorqueuepriority.dao.OperatorQueuePriorityDAO;
import com.scopix.periscope.operatorsgroup.OperatorsGroup;
import com.scopix.periscope.operatorsgroup.dao.OperatorsGroupDAO;
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
public class SaveOperatorGroupUsersCommand {

    private static Logger log = Logger.getLogger(SaveOperatorGroupUsersCommand.class);

    /**
     * Saves Operator group users
     *
     * @param listOfUsers
     * @param operatorGroupName
     */
    public void execute(List<String> listOfUsers, Integer operatorsGroupId) {
        log.info("start");
        OperatorsGroup operatorsGroup = HibernateSupport.getInstance().findGenericDAO().get(operatorsGroupId, OperatorsGroup.class);
        List<GroupUser> users = operatorsGroup.getUsers();
        List<GroupUser> deletedUsers = new ArrayList<GroupUser>();

        for (GroupUser user : users) {
            Boolean found = false;
            for (String username : listOfUsers) {
                if (username.equals(user.getUserName())) {
                    found = true;
                }
            }
            if (!found) {
                deletedUsers.add(user);
            }
        }

        for (String username : listOfUsers) {
            Boolean found = false;
            for (GroupUser user : users) {
                if (username.equals(user.getUserName())) {
                    found = true;
                }
            }
            if (!found) {
                GroupUser newGroupUser = new GroupUser();
                newGroupUser.setUserName(username);
                newGroupUser.setOperatorsGroup(operatorsGroup);
                users.add(newGroupUser);
            }
        }
        users.removeAll(deletedUsers);
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        dao.save(operatorsGroup);
        log.info("end");
    }
}
