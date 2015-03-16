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
 *  CreateActivityLogEntryRunnable.java
 * 
 *  Created on Jun 11, 2014, 11:44:30 AM
 * 
 */
package com.scopix.periscope.activitylog.management.runnable;

import com.scopix.periscope.activitylog.management.ActivityLogManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author Sebastian
 */
public class CreateActivityLogEntryRunnable implements Runnable {

    private ActivityLogManager activityLogManager;
    private String userName;
    private Integer pendingEvaluationId;
    private String corporateName;
    private String queueName;
    private Date date;
    private static Logger log = Logger.getLogger(ActivityLogManager.class);

    private CreateActivityLogEntryRunnable() {
    }

    /**
     * Constructor
     *
     * @param userName
     * @param pendingEvaluationId 
     * @param corporateName
     * @param queueName
     * @param requestDate 
     */
    public CreateActivityLogEntryRunnable(String userName, Integer pendingEvaluationId, String corporateName, String queueName, Date requestDate) {
        this.userName = userName;
        this.pendingEvaluationId = pendingEvaluationId;
        this.corporateName = corporateName;
        this.queueName = queueName;
        this.date = requestDate;
    }

    @Override
    public void run() {
        try {
            getActivityLogManager().createRequestLogRecord(userName, pendingEvaluationId, corporateName, queueName, date);
        } catch (Exception ex) {
            log.error("Unexpected error ocurred on creating log entry", ex);
        }
    }

    /**
     * @return the activityLogManager
     */
    public ActivityLogManager getActivityLogManager() {
        if (activityLogManager == null) {
            activityLogManager = SpringSupport.getInstance().findBeanByClassName(ActivityLogManager.class);
        }
        return activityLogManager;
    }

    /**
     * @param activityLogManager the activityLogManager to set
     */
    public void setActivityLogManager(ActivityLogManager activityLogManager) {
        this.activityLogManager = activityLogManager;
    }
}
