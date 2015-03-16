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
 *  GroupManager.java
 * 
 *  Created on May 28, 2014, 12:32:18 PM
 * 
 */
package com.scopix.periscope.operatorsgroup.management;

import com.scopix.periscope.operatorsgroup.OperatorsGroup;
import com.scopix.periscope.operatorsgroup.command.DeleteOperatorGroupCommand;
import com.scopix.periscope.operatorsgroup.command.GetAllOperatorsGroupCommand;
import com.scopix.periscope.operatorsgroup.command.GetOperatorsGroupCommand;
import com.scopix.periscope.operatorsgroup.command.SaveOperatorGroupUsersCommand;
import com.scopix.periscope.operatorsgroup.command.SaveOperatorsGroupCommand;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.subscription.management.SubscriptionManager;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Sebastian
 */
@SpringBean(rootClass = OperatorsGroupManager.class)
@Transactional(rollbackFor = {ScopixException.class, Exception.class})
public class OperatorsGroupManagerImpl implements OperatorsGroupManager {

    private SubscriptionManager subscriptionManager;

    @Override
    public OperatorsGroup getOperatorsGroup(Integer id) {
        GetOperatorsGroupCommand getOperatorsGroupCommand = new GetOperatorsGroupCommand();
        return getOperatorsGroupCommand.execute(id);
    }

    @Override
    public void saveOperatorsGroup(OperatorsGroup operatorsGroup) throws ScopixException {
        SaveOperatorsGroupCommand saveOperatorsGroupCommand = new SaveOperatorsGroupCommand();
        saveOperatorsGroupCommand.execute(operatorsGroup);
    }

    @Override
    public List<OperatorsGroup> getAllOperatorsGroups() {
        GetAllOperatorsGroupCommand getAllOperatorsGroupCommand = new GetAllOperatorsGroupCommand();
        return getAllOperatorsGroupCommand.execute();
    }

    @Override
    public void deleteOperatorGroup(Integer id) {
        DeleteOperatorGroupCommand deleteOperatorGroupCommand = new DeleteOperatorGroupCommand();
        deleteOperatorGroupCommand.execute(id);
        getSubscriptionManager().deleteByGroupId(id);
    }

    @Override
    public void saveOperatorsGroupUsers(List<String> listOfUsers, Integer operatorsGroupId) {
        SaveOperatorGroupUsersCommand saveOperatorGroupUsersCommand = new SaveOperatorGroupUsersCommand();
        saveOperatorGroupUsersCommand.execute(listOfUsers, operatorsGroupId);
    }

    /**
     * @return the subscriptionManager
     */
    public SubscriptionManager getSubscriptionManager() {
        if (subscriptionManager == null) {
            subscriptionManager = SpringSupport.getInstance().findBeanByClassName(SubscriptionManager.class);
        }
        return subscriptionManager;
    }

    /**
     * @param subscriptionManager the subscriptionManager to set
     */
    public void setSubscriptionManager(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }
}
