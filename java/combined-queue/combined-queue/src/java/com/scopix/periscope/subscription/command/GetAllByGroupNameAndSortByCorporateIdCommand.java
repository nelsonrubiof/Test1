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

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.subscription.Subscription;
import com.scopix.periscope.subscription.dao.SubscriptionDAO;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class GetAllByGroupNameAndSortByCorporateIdCommand {

    /**
     * return a list of user subscription sorted by User Name and Operator Queue
     * Priority
     *
     * @param userName
     * @return List<UserSubscription> list of user subscriptions
     */
    public List<Subscription> execute(String groupName) {

        return SpringSupport.getInstance()
                .findBeanByClassName(SubscriptionDAO.class).getAllByGroupNameAndCorporateIdAsc(groupName);
    }
}
