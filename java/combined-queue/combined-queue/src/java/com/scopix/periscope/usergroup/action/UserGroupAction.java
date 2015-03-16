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
 *  UserGroupAction.java
 * 
 *  Created on May 28, 2014, 11:34:41 AM
 * 
 */
package com.scopix.periscope.usergroup.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.scopix.periscope.groupuser.GroupUser;
import com.scopix.periscope.operatorsgroup.OperatorsGroup;
import com.scopix.periscope.operatorsgroup.management.OperatorsGroupManager;
import com.scopix.periscope.securitymanagement.SecurityManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
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
    @Result(name = "edit", value = "/WEB-INF/jsp/usergroup/usergroup.jsp")
})
@ParentPackage(value = "default")
@Namespace("/")
public class UserGroupAction extends ActionSupport implements SessionAware {

    private Logger log = Logger.getLogger(UserGroupAction.class);
    private Map session;
    private static final String EDIT = "edit";
    private static final String HOME = "home";
    private OperatorsGroup newGroup;
    private boolean saveNewGroup;
    private boolean deleteGroup;
    private boolean saveUserList;
    private boolean cancel;
    private SecurityManager securityManager;
    private OperatorsGroupManager operatorsGroupManager;
    private SubscriptionManager subscriptionManager;
    private Integer selectedOperatorGroup;
    private List<OperatorsGroup> operatorsGroups;
    private List<String> availableUsers;
    private List<String> assignedUsers;
    private String usersAvailable;
    private String usersAssigned;

    /**
     * Action class
     *
     * @return jsp or view to display
     * @throws Exception
     */
    @Override
    public String execute() throws Exception {
        boolean reload = false;
        if (!loggedIn()) {
            return HOME;
        } else if (isSaveNewGroup()) {
            saveNewGroup();
            reload = true;
        } else if (isDeleteGroup()) {
            deleteGroup();
            clear();
            reload = true;
        } else if (isCancel()) {
            clear();
        } else if (isSaveUserList()) {
            saveUserList();
        }
        getAllOperatorsGroup(reload);
        setUserLists();
        return EDIT;
    }

    private void clear() throws ScopixException {
        setSelectedOperatorGroup(null);
        setUsersAvailable(null);
        setUsersAssigned(null);
    }

    private void saveNewGroup() {
        try {
            log.info("Processing request");
            getNewGroup().setGroupName(getNewGroup().getGroupName().trim());
            if (getNewGroup() != null && getNewGroup().getGroupName().isEmpty()) {
                addActionError(getText("action.group.error.blank"));
            } else {
                getOperatorsGroupManager().saveOperatorsGroup(newGroup);
                addActionMessage(SUCCESS);
            }
            newGroup = new OperatorsGroup();
        } catch (ScopixException ex) {
            log.info("Unable to save group", ex);
            addActionError(getText("action.group.error.exists"));
            newGroup = new OperatorsGroup();
        }

    }

    private void saveUserList() throws ScopixException {
        if (getSelectedOperatorGroup() != null && !getSelectedOperatorGroup().equals("-1")) {
            try {
                getOperatorsGroupManager().saveOperatorsGroupUsers(createUsersAssignedList(), selectedOperatorGroup);
                addActionMessage(SUCCESS);
            } catch (Exception e) {
                getSubscriptionManager().reloadUserSubscriptionMap();
                throw new ScopixException(e);
            }
            getSubscriptionManager().reloadUserSubscriptionMap();
        } else {
            addActionError(getText("action.user.group.error.missing"));
        }
    }

    private void deleteGroup() {
        log.info("Processing request");
        if (selectedOperatorGroup != null && !selectedOperatorGroup.equals(-1)) {
            getOperatorsGroupManager().deleteOperatorGroup(selectedOperatorGroup);
            getSubscriptionManager().reloadUserSubscriptionMap();
        }
        addActionMessage(SUCCESS);
    }

    private void getAllOperatorsGroup(boolean force) {
        if (getOperatorsGroups() == null || force) {
            operatorsGroups = getOperatorsGroupManager().getAllOperatorsGroups();
        }
    }

    private boolean loggedIn() {
        if (getSession().get("sessionId") != null && (Long) getSession().get("sessionId") > 0L) {
            return true;
        }
        return false;
    }

    private void setUserLists() throws ScopixException {
        setAvailableUsers(new ArrayList<String>());
        setAssignedUsers(new ArrayList<String>());
        if (getSelectedOperatorGroup() != null && !getSelectedOperatorGroup().equals(-1)) {
            setAssignedUsers(createUsersSelectedList());
            List<String> allOperatorsList = new ArrayList<String>(getSecurityManager()
                    .getOperatorUsersList(0, (Long) getSession().get("sessionId")));
            allOperatorsList.removeAll(getAssignedUsers());
            setAvailableUsers(allOperatorsList);
        }
    }

    private List<String> createUsersSelectedList() {
        OperatorsGroup operatorsGroup = getOperatorsGroupManager().getOperatorsGroup(selectedOperatorGroup);
        List<String> groupUsers = new ArrayList<String>();
        for (GroupUser groupUser : operatorsGroup.getUsers()) {
            groupUsers.add(groupUser.getUserName());
        }
        return groupUsers;
    }

    private List<String> createUsersAssignedList() {
        if (getUsersAssigned() != null) {
            return Arrays.asList(getUsersAssigned().split("\\s*,\\s*"));
        }
        return new ArrayList<String>();
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
     * @return the saveNewGroup
     */
    public boolean isSaveNewGroup() {
        return saveNewGroup;
    }

    /**
     * @param saveNewGroup the saveNewGroup to set
     */
    public void setSaveNewGroup(boolean saveNewGroup) {
        this.saveNewGroup = saveNewGroup;
    }

    /**
     * @return the newGroup
     */
    public OperatorsGroup getNewGroup() {
        return newGroup;
    }

    /**
     * @param newGroup the newGroup to set
     */
    public void setNewGroup(OperatorsGroup newGroup) {
        this.newGroup = newGroup;
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
     * @return the deleteGroup
     */
    public boolean isDeleteGroup() {
        return deleteGroup;
    }

    /**
     * @param deleteGroup the deleteGroup to set
     */
    public void setDeleteGroup(boolean deleteGroup) {
        this.deleteGroup = deleteGroup;
    }

    /**
     * @return the selectedOperatorGroup
     */
    public Integer getSelectedOperatorGroup() {
        return selectedOperatorGroup;
    }

    /**
     * @param selectedOperatorGroup the selectedOperatorGroup to set
     */
    public void setSelectedOperatorGroup(Integer selectedOperatorGroup) {
        this.selectedOperatorGroup = selectedOperatorGroup;
    }

    /**
     * @return the operatorsGroups
     */
    public List<OperatorsGroup> getOperatorsGroups() {
        return operatorsGroups;
    }

    /**
     * @param operatorsGroups the operatorsGroups to set
     */
    public void setOperatorsGroups(List<OperatorsGroup> operatorsGroups) {
        this.operatorsGroups = operatorsGroups;
    }

    /**
     * @return the availableUsers
     */
    public List<String> getAvailableUsers() {
        return availableUsers;
    }

    /**
     * @param availableUsers the availableUsers to set
     */
    public void setAvailableUsers(List<String> availableUsers) {
        this.availableUsers = availableUsers;
    }

    /**
     * @return the assignedUsers
     */
    public List<String> getAssignedUsers() {
        return assignedUsers;
    }

    /**
     * @param assignedUsers the assignedUsers to set
     */
    public void setAssignedUsers(List<String> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    /**
     * @return the usersAvailable
     */
    public String getUsersAvailable() {
        return usersAvailable;
    }

    /**
     * @param usersAvailable the usersAvailable to set
     */
    public void setUsersAvailable(String usersAvailable) {
        this.usersAvailable = usersAvailable;
    }

    /**
     * @return the usersAssigned
     */
    public String getUsersAssigned() {
        return usersAssigned;
    }

    /**
     * @param usersAssigned the usersAssigned to set
     */
    public void setUsersAssigned(String usersAssigned) {
        this.usersAssigned = usersAssigned;
    }

    /**
     * @return the securityManager
     */
    public SecurityManager getSecurityManager() {
        if (securityManager == null) {
            securityManager = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        }
        return securityManager;
    }

    /**
     * @param securityManager the securityManager to set
     */
    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    /**
     * @return the saveUserList
     */
    public boolean isSaveUserList() {
        return saveUserList;
    }

    /**
     * @param saveUserList the saveUserList to set
     */
    public void setSaveUserList(boolean saveUserList) {
        this.saveUserList = saveUserList;
    }

    /**
     * @return the cancel
     */
    public boolean isCancel() {
        return cancel;
    }

    /**
     * @param cancel the cancel to set
     */
    public void setCancel(boolean cancel) {
        this.cancel = cancel;
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
