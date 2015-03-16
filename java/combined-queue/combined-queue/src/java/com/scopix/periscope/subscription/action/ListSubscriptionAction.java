
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
package com.scopix.periscope.subscription.action;

import com.opensymphony.xwork2.ActionSupport;
import com.scopix.periscope.operatorsgroup.OperatorsGroup;
import com.scopix.periscope.operatorsgroup.management.OperatorsGroupManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.subscription.Subscription;
import com.scopix.periscope.subscription.management.SubscriptionManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author Sebastian Torres Brown
 */
@Results({
    @Result(name = "home", value = "/WEB-INF/jsp/index.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/listusersubscription/listusersubscription.jsp")
})
@ParentPackage(value = "default")
@Namespace("/")
public class ListSubscriptionAction extends ActionSupport implements SessionAware {

    private Map session;
    private static final String EDIT = "edit";
    private static final String HOME = "home";
    private SubscriptionManager subscriptionManager;
    private OperatorsGroupManager operatorsGroupManager;
    private List<Subscription> subscriptionList;
    private List<String> users;
    private String user;
    private String GROUP_KEY = "GROUP - ";

    /**
     * Action class
     *
     * @return jsp or view to display
     * @throws Exception
     */
    @Override
    public String execute() throws Exception {
        if (!loggedIn()) {
            return HOME;
        }
        retrieveUsersList();
        retrieveUserSubscriptionsForUser();
        return EDIT;
    }

    private boolean loggedIn() {
        if (getSession().get("sessionId") != null && (Long) getSession().get("sessionId") > 0L) {
            return true;
        }
        return false;
    }

    private void retrieveUsersList() {
        List<OperatorsGroup> listAllOperators = getOperatorsGroupManager().getAllOperatorsGroups();
        List<String> usersFromMap = new ArrayList<String>();
        for (OperatorsGroup operatorsGroup : listAllOperators) {
            List<Subscription> subs = getSubscriptionManager().getAllByGroupNameAndSortByCorporateIdAsc(operatorsGroup.getGroupName());
            if (!subs.isEmpty()) {
                usersFromMap.add(GROUP_KEY + operatorsGroup.getGroupName());
            }
        }
        usersFromMap.addAll(new ArrayList<String>(getSubscriptionManager().getUserSubscriptionMap().keySet()));
        setUsers(usersFromMap);
    }

    private void retrieveUserSubscriptionsForUser() {
        if (getUser() != null && !user.equals("defval")) {
            if (getUser().contains(GROUP_KEY)) {
                setSubscriptionList(new ArrayList<Subscription>(
                        getSubscriptionManager().getAllByGroupNameAndSortByCorporateIdAsc(getUser().replace(GROUP_KEY, ""))));

            } else {
                setSubscriptionList(new ArrayList<Subscription>(
                        getSubscriptionManager().getAllByUserNameAndSortByCorporateIdAsc(getUser())));
            }
        }
    }

    /**
     * returns the session
     *
     * @return session
     */
    public Map getSession() {
        return session;
    }

    /**
     * sets the session
     *
     * @param session
     */
    @Override
    public void setSession(Map session) {
        this.session = session;
    }

    /**
     * @return the users
     */
    public List<String> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<String> users) {
        this.users = users;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the subscriptionManager
     */
    public SubscriptionManager getSubscriptionManager() {
        if (subscriptionManager == null) {
            setSubscriptionManager(SpringSupport.getInstance().findBeanByClassName(SubscriptionManager.class));
        }
        return subscriptionManager;
    }

    /**
     * @param subscriptionManager the subscriptionManager to set
     */
    public void setSubscriptionManager(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }

    /**
     * @return the subscriptionList
     */
    public List<Subscription> getSubscriptionList() {
        return subscriptionList;
    }

    /**
     * @param subscriptionList the subscriptionList to set
     */
    public void setSubscriptionList(List<Subscription> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }

    /**
     * @return the operatorsGroupManager
     */
    public OperatorsGroupManager getOperatorsGroupManager() {
        if (operatorsGroupManager == null) {
            operatorsGroupManager = SpringSupport.getInstance().findBeanByClassName(OperatorsGroupManager.class);
        }
        return operatorsGroupManager;
    }

    /**
     * @param operatorsGroupManager the operatorsGroupManager to set
     */
    public void setOperatorsGroupManager(OperatorsGroupManager operatorsGroupManager) {
        this.operatorsGroupManager = operatorsGroupManager;
    }
}
