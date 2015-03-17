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
 *  GetActivityLogForUsersCommand.java
 * 
 *  Created on Jun 16, 2014, 5:09:00 PM
 * 
 */
package com.scopix.periscope.activitylog.command;

import com.scopix.periscope.activitylog.ActivityLog;
import com.scopix.periscope.activitylog.dao.ActivityLogDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class GetActivityLogForUsersCommand {

    private ActivityLogDAO activityLogDAO;

    /**
     * Gets the Activity Log for a user given a time criteria
     *
     * @param userName
     * @param day
     * @param start can be null
     * @param end can be null
     * @return List<ActivityLog>
     */
    public List<ActivityLog> execute(Date day, Date start, Date end) {
        if (start == null || end == null) {
            Calendar c = Calendar.getInstance();
            c.setTime(day);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            start = new Date(c.getTimeInMillis());
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 999);
            end = new Date(c.getTimeInMillis());
        }
        return getActivityLogDAO().getForDateRange(start, end);
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
