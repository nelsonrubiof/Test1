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
 *  ActivityLogDAO.java
 * 
 *  Created on Jun 11, 2014, 5:26:48 PM
 * 
 */
package com.scopix.periscope.activitylog.dao;

import com.scopix.periscope.activitylog.ActivityLog;
import com.scopix.periscope.dao.GenericDAO;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sebastian
 */
public interface ActivityLogDAO extends GenericDAO<ActivityLog> {

    /**
     * Gets the activity log for a user an a desired time period
     *
     * @param userName
     * @param from
     * @param to
     * @return List<ActivityLog>
     */
    List<ActivityLog> getForUserAndDateRange(String userName, Date from, Date to);

    /**
     * gets users in log
     *
     * @return List<String>
     */
    List<String> getUserNamesInLog();

    /**
     * Gets the activity log for a desired time period
     *
     * @param from
     * @param to
     * @return List<ActivityLog>
     */
    List<ActivityLog> getForDateRange(Date from, Date to);

    /**
     * gets a list of usernames withing the specified range
     *
     * @param from
     * @param to
     * @return List<String>
     */
    List<String> getUsersNameForDateRange(Date from, Date to);

    /**
     * Updates the activity Log entry
     *
     * @param userName
     * @param pendingEvaluationId
     * @param status
     *
     * @param sendDate
     */
    void UpdateActivityLogEntry(String userName, Integer pendingEvaluationId, String status, Date sendDate);

    /**
     * Shows all users that had activity on a given queue for a desired time
     * period
     *
     * @param from
     * @param to
     * @return List map of users per queues
     */
    List<Map<String, Object>> getUsersPerQueue(Date from, Date to);
    
     /**
     *Shows all users  latest request that had activity on a given queue for a desired time
     * @param from
     * @param to
     * @return List map of users latest request per queues
     */

    List<Map<String, Object>> getUsersLatestRequestQueue(Date from, Date to);
}
