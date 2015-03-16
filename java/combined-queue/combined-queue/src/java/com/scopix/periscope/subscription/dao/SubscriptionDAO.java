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
package com.scopix.periscope.subscription.dao;

import com.scopix.periscope.subscription.Subscription;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public interface SubscriptionDAO {

    /**
     * return a list of user subscription sorted by User Name and Operator Queue
     * Priority
     *
     * @return List<UserSubscription> list of user subscriptions
     */
    List<Subscription> getAllByUserNameAndCorporateIdAsc();

    /**
     * return a list of user subscriptions based on the userName it is sorted by
     * corporateIs
     *
     * @param userName
     * @return List<UserSubscription> list of user subscriptions
     */
    List<Subscription> getForUserNameSortedByCorporateIdAsc(String userName);

    /**
     * return a list of user subscriptions based on the group name and it is
     * sorted by corporateIs
     *
     * @param groupName
     * @return List<Subscription>
     */
    List<Subscription> getAllByGroupNameAndCorporateIdAsc(String groupName);
    
    /**
     * deletes subscriptions for an operators group
     * @param operatorsGroupId
     */
    public void deleteByGroupId(Integer operatorsGroupId);
}
