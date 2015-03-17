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
package com.scopix.periscope.subscription.action;

import com.opensymphony.xwork2.ActionSupport;
import com.scopix.periscope.corporate.Corporate;
import com.scopix.periscope.corporate.CorporateUtils;
import com.scopix.periscope.groupuser.GroupUser;
import com.scopix.periscope.operatorqueuepriority.OperatorQueuePriority;
import com.scopix.periscope.operatorqueuepriority.management.OperatorQueuePriorityManager;
import com.scopix.periscope.operatorsgroup.OperatorsGroup;
import com.scopix.periscope.operatorsgroup.management.OperatorsGroupManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.securitymanagement.SecurityManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.subscription.Subscription;
import com.scopix.periscope.subscription.management.SubscriptionManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author Sebastian
 */
@Results({
    @Result(name = "home", value = "/WEB-INF/jsp/index.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/subscription/subscription.jsp")
})
@ParentPackage(value = "default")
@Namespace("/")
public class SubscriptionAction extends ActionSupport implements SessionAware {
    
    private static final String EDIT = "edit";
    private static final String HOME = "home";
    private static final String GROUP = "GROUP - ";
    private Map session;
    private OperatorQueuePriorityManager operatorQueuePriorityManager;
    private SubscriptionManager userSubscriptionManager;
    private List<Corporate> corporates;
    private List<String> queues;
    private Map<Integer, List<String>> operatorQueuePriorityMap;
    private SecurityManager securityManager;
    private Integer selectedCorporate;
    private String selectedQueue;
    private List<String> availableUsers;
    private List<String> assignedUsers;
    private String usersAvailable;
    private String usersAssigned;
    private boolean cancel;
    private boolean save;
    private OperatorsGroupManager operatorsGroupManager;
    private List<OperatorsGroup> groups;
    private Logger log = Logger.getLogger(SubscriptionAction.class);

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
        if (isCancel()) {
            cancelAction();
        } else if (isSave()) {
            saveAction();
        }
        prepare();
        return EDIT;
    }
    
    private boolean loggedIn() {
        if (getSession().get("sessionId") != null && (Long) getSession().get("sessionId") > 0L) {
            return true;
        }
        return false;
    }
    
    private void saveAction() throws ScopixException {
        if (getSelectedQueue() != null && !getSelectedQueue().equals("-1")
                && getSelectedCorporate() != null && !getSelectedCorporate().equals(-1)) {
            try {
                
                List<String> assignments = getUsersAssignedAsList();
                List<String> groupNames = new ArrayList<String>();
                List<String> usersUnfiltered = new ArrayList<String>();
                for (String assignment : assignments) {
                    if (!assignment.contains(GROUP)) {
                        usersUnfiltered.add(assignment);
                    } else {
                        groupNames.add(assignment.replaceAll(GROUP, ""));
                    }
                }
                List<OperatorsGroup> groupList = new ArrayList<OperatorsGroup>();
                List<Integer> groupIds = new ArrayList<Integer>();
                for (String groupName : groupNames) {
                    for (OperatorsGroup group : getGroups()) {
                        if (group.getGroupName().equals(groupName)) {
                            groupList.add(group);
                            groupIds.add(group.getId());
                        }
                    }
                }
                List<String> userNames = new ArrayList<String>();
                for (String userName : usersUnfiltered) {
                    boolean found = false;
                    for (OperatorsGroup group : groupList) {
                        for (GroupUser user : group.getUsers()) {
                            if (user.getUserName().equals(userName)) {
                                found = true;
                            }
                        }
                    }
                    if (!found) {
                        userNames.add(userName);
                    }
                }
                getOperatorQueuePriorityManager()
                        .saveOperatorQueueUserSubscriptions(userNames, groupIds, getSelectedCorporate(), getSelectedQueue());
            } catch (Exception e) {
                getUserSubscriptionManager().reloadUserSubscriptionMap();
                log.error("An error ocurred saving the Subscriptions: ", e);
                throw new ScopixException(e);
            }
            getUserSubscriptionManager().reloadUserSubscriptionMap();
        } else {
            addActionError(getText("action.user.subscription.error.missing"));
        }
    }
    
    private List<String> getUsersAssignedAsList() {
        if (getUsersAssigned() != null) {
            return Arrays.asList(getUsersAssigned().split("\\s*,\\s*"));
        }
        return new ArrayList<String>();
    }
    
    private void cancelAction() throws ScopixException {
        setSelectedCorporate(null);
        setSelectedQueue(null);
        setUsersAvailable(null);
        setUsersAssigned(null);
    }
    
    private void prepare() throws ScopixException {
        setOperatorQueuePriorityMap(getOperatorQueuePriorityManager().getAllOrderedInMap());
        setCorporates();
        setQueues();
        setUserLists();
    }
    
    private void setCorporates() throws ScopixException {
        if (getCorporates() == null) {
            List<Corporate> corporateList = new ArrayList<Corporate>();
            for (Integer corporateId : getOperatorQueuePriorityMap().keySet()) {
                corporateList.add(CorporateUtils.getCorporate(corporateId));
            }
            setCorporates(corporateList);
        }
    }
    
    private void setQueues() {
        setQueues(new ArrayList<String>());
        if (getSelectedCorporate() != null && !getSelectedCorporate().equals(-1)) {
            setQueues(getOperatorQueuePriorityMap().get(getSelectedCorporate()));
        }
    }
    
    private void setUserLists() throws ScopixException {
        setAvailableUsers(new ArrayList<String>());
        setAssignedUsers(new ArrayList<String>());
        if (getSelectedCorporate() != null && !getSelectedCorporate().equals(-1)
                && getSelectedQueue() != null && !getSelectedQueue().equals("-1")) {
            setAssignedUsers(createUsersSelectedList());
            List<String> allOperatorsList = getGroupsNamesList();
            allOperatorsList.addAll(getSecurityManager().getOperatorUsersList(0, (Long) getSession().get("sessionId")));
            allOperatorsList.removeAll(getAssignedUsers());
            setAvailableUsers(allOperatorsList);
        }
    }
    
    private List<String> createUsersSelectedList() {
        OperatorQueuePriority operatorQueuePriority = getOperatorQueuePriorityManager()
                .getByCorporateIdAndOperatorQueueName(selectedCorporate, selectedQueue);
        List<String> usersSubscribed = new ArrayList();
        List<Subscription> userSubscriptions = operatorQueuePriority.getUserSubscriptions();
        for (Subscription userSubscription : userSubscriptions) {
            if (userSubscription.getUserName() != null && !userSubscription.getUserName().isEmpty()) {
                usersSubscribed.add(userSubscription.getUserName());
            } else {
                for (OperatorsGroup group : getGroups()) {
                    if (group.getId().equals(userSubscription.getOperatorsGroupId())) {
                        usersSubscribed.add(GROUP + group.getGroupName());
                    }
                }
            }
        }
        return usersSubscribed;
    }
    
    private List<String> getGroupsNamesList() {
        List<String> groupNames = new ArrayList();
        for (OperatorsGroup group : getGroups()) {
            groupNames.add(GROUP + group.getGroupName());
        }
        return groupNames;
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
     * getter
     *
     * @return OperatorQueuePriorityManager
     */
    public OperatorQueuePriorityManager getOperatorQueuePriorityManager() {
        if (operatorQueuePriorityManager == null) {
            setOperatorQueuePriorityManager(SpringSupport.getInstance().findBeanByClassName(OperatorQueuePriorityManager.class));
        }
        return operatorQueuePriorityManager;
    }

    /**
     * setter
     *
     * @param operatorQueuePriorityManager
     */
    public void setOperatorQueuePriorityManager(OperatorQueuePriorityManager operatorQueuePriorityManager) {
        this.operatorQueuePriorityManager = operatorQueuePriorityManager;
    }

    /**
     * getter
     *
     * @return OperatorQueuePriorityManager
     */
    public SecurityManager getSecurityManager() {
        if (securityManager == null) {
            setSecurityManager(SpringSupport.getInstance().findBeanByClassName(SecurityManager.class));
        }
        return securityManager;
    }

    /**
     * setter
     *
     * @param securityManager
     */
    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    /**
     * getter
     *
     * @return Map<Integer, List<String>>
     */
    public Map<Integer, List<String>> getOperatorQueuePriorityMap() {
        return operatorQueuePriorityMap;
    }

    /**
     * setter
     *
     * @param operatorQueuePriorityMap
     */
    public void setOperatorQueuePriorityMap(Map<Integer, List<String>> operatorQueuePriorityMap) {
        this.operatorQueuePriorityMap = operatorQueuePriorityMap;
    }

    /**
     * getter
     *
     * @return Integer
     */
    public Integer getSelectedCorporate() {
        return selectedCorporate;
    }

    /**
     * setter
     *
     * @param selectedCorporate
     */
    public void setSelectedCorporate(Integer selectedCorporate) {
        this.selectedCorporate = selectedCorporate;
    }

    /**
     * getter
     *
     * @return List<Corporate>
     */
    public List<Corporate> getCorporates() {
        return corporates;
    }

    /**
     * setter
     *
     * @param corporates
     */
    public void setCorporates(List<Corporate> corporates) {
        this.corporates = corporates;
    }

    /**
     * getter
     *
     * @return List<String>
     */
    public List<String> getQueues() {
        return queues;
    }

    /**
     * setter
     *
     * @param queues
     */
    public void setQueues(List<String> queues) {
        this.queues = queues;
    }

    /**
     * getter
     *
     * @return String
     */
    public String getSelectedQueue() {
        return selectedQueue;
    }

    /**
     * setter
     *
     * @param selectedQueue
     */
    public void setSelectedQueue(String selectedQueue) {
        this.selectedQueue = selectedQueue;
    }

    /**
     * getter
     *
     * @return List<String>
     */
    public List<String> getAvailableUsers() {
        return availableUsers;
    }

    /**
     * setter
     *
     * @param availableUsers
     */
    public void setAvailableUsers(List<String> availableUsers) {
        this.availableUsers = availableUsers;
    }

    /**
     * getter
     *
     * @return List<String>
     */
    public List<String> getAssignedUsers() {
        return assignedUsers;
    }

    /**
     * setter
     *
     * @param assignedUsers
     */
    public void setAssignedUsers(List<String> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    /**
     * getter
     *
     * @return String
     */
    public String getUsersAvailable() {
        return usersAvailable;
    }

    /**
     * setter
     *
     * @param usersAvailable
     */
    public void setUsersAvailable(String usersAvailable) {
        this.usersAvailable = usersAvailable;
    }

    /**
     * getter
     *
     * @return String
     */
    public String getUsersAssigned() {
        return usersAssigned;
    }

    /**
     * setter
     *
     * @param usersAssigned
     */
    public void setUsersAssigned(String usersAssigned) {
        this.usersAssigned = usersAssigned;
    }

    /**
     * getter
     *
     * @return boolean
     */
    public boolean isCancel() {
        return cancel;
    }

    /**
     * setter
     *
     * @param cancel
     */
    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    /**
     * getter
     *
     * @return boolean
     */
    public boolean isSave() {
        return save;
    }

    /**
     * setter
     *
     * @param save
     */
    public void setSave(boolean save) {
        this.save = save;
    }

    /**
     * getter
     *
     * @return
     */
    public SubscriptionManager getUserSubscriptionManager() {
        if (userSubscriptionManager == null) {
            setUserSubscriptionManager(SpringSupport.getInstance().findBeanByClassName(SubscriptionManager.class));
        }
        return userSubscriptionManager;
    }

    /**
     * setter
     *
     * @param userSubscriptionManager
     */
    public void setUserSubscriptionManager(SubscriptionManager userSubscriptionManager) {
        this.userSubscriptionManager = userSubscriptionManager;
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

    /**
     * @return the groups
     */
    public List<OperatorsGroup> getGroups() {
        if (groups == null) {
            groups = getOperatorsGroupManager().getAllOperatorsGroups();
        }
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(List<OperatorsGroup> groups) {
        this.groups = groups;
    }
}