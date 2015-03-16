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
 *  GetActivityLogForUserCommand.java
 * 
 *  Created on Jun 10, 2014, 5:42:22 PM
 * 
 */
package com.scopix.periscope.activitylog.command;

import com.scopix.periscope.activitylog.dao.ActivityLogDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class GetUsersInActivityLogCommand {

    private ActivityLogDAO activityLogDAO;

    /**
     * Gets the Activity Log for a user given a time criteria
     *
     * @return List<ActivityLog>
     */
    public List<String> execute() {
        return getActivityLogDAO().getUserNamesInLog();
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
