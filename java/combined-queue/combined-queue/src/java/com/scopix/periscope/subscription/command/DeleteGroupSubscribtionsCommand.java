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
 *  DeleteGroupSubscribtionsCommand.java
 * 
 *  Created on Jun 10, 2014, 11:28:28 AM
 * 
 */
package com.scopix.periscope.subscription.command;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.subscription.dao.SubscriptionDAO;

/**
 *
 * @author Sebastian
 */
public class DeleteGroupSubscribtionsCommand {

    /**
     * Deletes all operators group subscriptions
     */
    public void execute(Integer operatorGroupId) {
        SpringSupport.getInstance()
                .findBeanByClassName(SubscriptionDAO.class).deleteByGroupId(operatorGroupId);
    }
}
