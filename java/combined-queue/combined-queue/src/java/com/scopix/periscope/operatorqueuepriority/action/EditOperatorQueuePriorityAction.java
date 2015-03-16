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
package com.scopix.periscope.operatorqueuepriority.action;

import com.scopix.periscope.operatorqueuepriority.management.OperatorQueuePriorityManager;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.scopix.periscope.corporate.Corporate;
import com.scopix.periscope.corporate.CorporateUtils;
import com.scopix.periscope.operatorqueuepriority.OperatorQueuePriority;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.Queue;
import com.scopix.periscope.queuemanagement.QueueManager;
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
 * @author Sebastian
 */
@Results({
    @Result(name = "home", value = "/WEB-INF/jsp/index.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/operatorqueuepriority/operatorqueuepymgr.jsp")
})
@ParentPackage(value = "default")
@Namespace("/")
public class EditOperatorQueuePriorityAction extends ActionSupport implements SessionAware, Preparable {

    private List<Corporate> corporates;
    private List<Queue> queuesForCorporate;
    private static final String EDIT = "edit";
    private static final String HOME = "home";
    private OperatorQueuePriority operatorQueuePriority;
    private Map session;
    private boolean addNew;
    private Integer editCurrent;
    private Integer deleteCurrent;
    private QueueManager queueManager;
    OperatorQueuePriorityManager operatorQueuePriorityManager;
    List<OperatorQueuePriority> operatorQueuePriorities;
    private SubscriptionManager userSubscriptionManager;

    /**
     * Action class
     * @return jsp or view to display 
     * @throws Exception
     */
    @Override
    public String execute() throws Exception {
        if (!loggedIn()) {
            return HOME;
        }
        if (isAddNew()) {
            saveNewOperatorQueuePriority();
        } else if (getEditCurrent() != null) {
            editNewOperatorQueuePriority();
        } else if (getDeleteCurrent() != null) {
            deleteNewOperatorQueuePriority();
        }
        retrieveQueues();
        getAllOperatorQueuePriority(false);
        return EDIT;
    }

    private boolean loggedIn() {
        if (getSession().get("sessionId")!=null && (Long) getSession().get("sessionId") > 0L) {
            return true;
        }
        return false;
    }

    private void saveNewOperatorQueuePriority() throws ScopixException {
        if (!isRepeatedOperatorQueue()) {
            getOperatorQueuePriority().setCorporateName(getCorporateNamefromUtils());
            getOperatorQueuePriorityManager().saveOperatorQueuePriority(getOperatorQueuePriority());
            setOperatorQueuePriority(new OperatorQueuePriority());
            getAllOperatorQueuePriority(true);
            getUserSubscriptionManager().reloadUserSubscriptionMap();
        } else {
            addActionError(getText("action.corporate.error.exist"));
        }

    }

    private void editNewOperatorQueuePriority() throws ScopixException {
        getOperatorQueuePriorityManager()
                .saveOperatorQueuePriority(getOperatorQueuePriorities()
                .get(getEditCurrent()));
        getAllOperatorQueuePriority(true);
        getUserSubscriptionManager().reloadUserSubscriptionMap();
    }

    private void deleteNewOperatorQueuePriority() throws ScopixException {
        getOperatorQueuePriorityManager()
                .deleteOperatorQueuePriority(getOperatorQueuePriorities()
                .get(getDeleteCurrent()).getId());
        getAllOperatorQueuePriority(true);
        getUserSubscriptionManager().reloadUserSubscriptionMap();
    }

    private void setBlankQueues() {
        setQueuesForCorporate(new ArrayList<Queue>());
    }

    private void getAllOperatorQueuePriority(boolean force) {
        if (getOperatorQueuePriorities() == null || force) {
            setOperatorQueuePriorities(getOperatorQueuePriorityManager().getAllByCorporateName());
        }
    }

    private void retrieveQueues() throws ScopixException {
        if (getOperatorQueuePriority() != null
                && getOperatorQueuePriority().getCorporateId() != null
                && !getOperatorQueuePriority().getCorporateId().equals(-1)) {
            setQueuesForCorporate(getQueueManager()
                    .getQueuesForOperator(getCorporateNameForQueueManager()));
        }
    }

    private String getCorporateNamefromUtils() throws ScopixException {
        return CorporateUtils
                .getCorporate(getOperatorQueuePriority().getCorporateId()).getName();
    }

    private String getCorporateNameForQueueManager() throws ScopixException {
        String corporateName = CorporateUtils
                .getCorporate(getOperatorQueuePriority().getCorporateId()).getName();
        return corporateName.toUpperCase().replaceAll(" ", "_");
    }

    private boolean isRepeatedOperatorQueue() {
        OperatorQueuePriority objNew = getOperatorQueuePriority();
        if(getOperatorQueuePriorities() !=null){
            for (OperatorQueuePriority objCurrent : getOperatorQueuePriorities()) {
                if (objCurrent.getCorporateId().equals(objNew.getCorporateId())
                        && objCurrent.getOperatorQueueName().equals(objNew.getOperatorQueueName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * preconditions to execute before execute method
     * @throws Exception
     */
    @Override
    public void prepare() throws Exception {
        setCorporates(CorporateUtils.getCorporates());
        setBlankQueues();
    }

    /**
     * gets the queueManager
     * @return QueueManager
     */
    public QueueManager getQueueManager() {
        if (queueManager == null) {
            setQueueManager(SpringSupport.getInstance().findBeanByClassName(QueueManager.class));
        }
        return queueManager;
    }

    /**
     * sets the queue manager
     * @param queueManager
     */
    public void setQueueManager(QueueManager queueManager) {
        this.queueManager = queueManager;
    }

    /**
     * getter
     * @return List<Corporate
     */
    public List<Corporate> getCorporates() {
        return corporates;
    }

    /**
     * setter
     * @param corporates
     */
    public void setCorporates(List<Corporate> corporates) {
        this.corporates = corporates;
    }

    /**
     * getter
     * @return List<Queue>
     */
    public List<Queue> getQueuesForCorporate() {
        return queuesForCorporate;
    }

    /**
     * setter
     * @param queuesForCorporate
     */
    public void setQueuesForCorporate(List<Queue> queuesForCorporate) {
        this.queuesForCorporate = queuesForCorporate;
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
     * @return boolean
     */
    public boolean isAddNew() {
        return addNew;
    }

    /**
     * setter
     * @param addNew
     */
    public void setAddNew(boolean addNew) {
        this.addNew = addNew;
    }

    /**
     * getter
     * @return OperatorQueuePriority
     */
    public OperatorQueuePriority getOperatorQueuePriority() {
        return operatorQueuePriority;
    }

    /**
     * setter
     * @param operatorQueuePriority
     */
    public void setOperatorQueuePriority(OperatorQueuePriority operatorQueuePriority) {
        this.operatorQueuePriority = operatorQueuePriority;
    }

    /**
     * getter
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
     * @param operatorQueuePriorityManager
     */
    public void setOperatorQueuePriorityManager(OperatorQueuePriorityManager operatorQueuePriorityManager) {
        this.operatorQueuePriorityManager = operatorQueuePriorityManager;
    }

    /**
     * getter
     * @return List<OperatorQueuePriority> 
     */
    public List<OperatorQueuePriority> getOperatorQueuePriorities() {
        return operatorQueuePriorities;
    }

    /**
     * setter
     * @param operatorQueuePriorities
     */
    public void setOperatorQueuePriorities(List<OperatorQueuePriority> operatorQueuePriorities) {
        this.operatorQueuePriorities = operatorQueuePriorities;
    }

    /**
     * getter
     * @return Integer 
     */
    public Integer getEditCurrent() {
        return editCurrent;
    }

    /**
     * setter
     * @param editCurrent
     */
    public void setEditCurrent(Integer editCurrent) {
        this.editCurrent = editCurrent;
    }

    /**
     * getter
     * @return Integer
     */
    public Integer getDeleteCurrent() {
        return deleteCurrent;
    }

    /**
     * setter
     * @param deleteCurrent
     */
    public void setDeleteCurrent(Integer deleteCurrent) {
        this.deleteCurrent = deleteCurrent;
    }
        /**
     * getter
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
     * @param userSubscriptionManager
     */
    public void setUserSubscriptionManager(SubscriptionManager userSubscriptionManager) {
        this.userSubscriptionManager = userSubscriptionManager;
    }
}
