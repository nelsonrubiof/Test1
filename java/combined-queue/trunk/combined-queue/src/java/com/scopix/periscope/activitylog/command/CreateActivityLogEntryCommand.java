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
 *  CreateActivityLogEntryCommand.java
 * 
 *  Created on Jun 10, 2014, 5:13:37 PM
 * 
 */
package com.scopix.periscope.activitylog.command;

import com.scopix.periscope.activitylog.ActivityLog;
import com.scopix.periscope.activitylog.dao.ActivityLogDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.Date;

/**
 *
 * @author Sebastian
 */
public class CreateActivityLogEntryCommand {

    private ActivityLogDAO activityLogDAO;

    /**
     * Creates an Activity Log entry
     *
     * @param userName
     * @param action
     * @param queueName
     * @param corporateName
     * @param date
     */
    public void execute(String userName, String status, Integer pendingEvaluationId, String corporateName, String queueName, Date requestDate) {
        corporateName = corporateName.toUpperCase();
        queueName = queueName.toUpperCase();
        ActivityLog activityLog = new ActivityLog();
        activityLog.setUserName(userName);
        activityLog.setPendingEvaluationId(pendingEvaluationId);
        activityLog.setStatus(status);
        activityLog.setCorporateName(corporateName);
        activityLog.setQueueName(queueName);
        activityLog.setRequestDate(requestDate);
        getActivityLogDAO().save(activityLog);
    }

    /**
     * @return the activityLogDAO
     */
    public ActivityLogDAO getActivityLogDAO() {
        if (activityLogDAO == null) {
            activityLogDAO = SpringSupport.getInstance().findBeanByClassName(ActivityLogDAO.class);
        }
        return activityLogDAO;
    }

    /**
     * @param activityLogDAO the activityLogDAO to set
     */
    public void setActivityLogDAO(ActivityLogDAO activityLogDAO) {
        this.activityLogDAO = activityLogDAO;
    }
}
