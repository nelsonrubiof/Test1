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
 *  UpdateActivityLogCommand.java
 * 
 *  Created on Jul 3, 2014, 5:34:40 PM
 * 
 */
package com.scopix.periscope.activitylog.command;

import com.scopix.periscope.activitylog.dao.ActivityLogDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.Date;

/**
 *
 * @author Sebastian
 */
public class UpdateActivityLogCommand {

    private ActivityLogDAO activityLogDAO;

    /**
     * updates the activity log entry
     *
     * @param userName
     * @param pendingEvaluationId
     * @param sendDate
     */
    public void execute(String userName, String status, Integer pendingEvaluationId, Date sendDate) {
        getActivityLogDAO().UpdateActivityLogEntry(userName, pendingEvaluationId, status, sendDate);
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
