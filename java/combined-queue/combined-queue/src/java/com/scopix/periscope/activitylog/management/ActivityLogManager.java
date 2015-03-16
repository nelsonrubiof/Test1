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
 *  ActivityLogManager.java
 * 
 *  Created on Jun 10, 2014, 4:38:28 PM
 * 
 */
package com.scopix.periscope.activitylog.management;

import com.scopix.periscope.activitylog.ActivityLog;
import com.scopix.periscope.activitylog.services.webservices.dto.ActivityLogDTOContainer;
import com.scopix.periscope.activitylog.services.webservices.dto.UsersLastRequestDTO;
import com.scopix.periscope.activitylog.services.webservices.dto.UsersOnQueueDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public interface ActivityLogManager {

    /**
     * Create Request ActivityLog entrance
     *
     * @param userName
     * @param pendingEvaluationId
     * @param corporateName
     * @param queueName
     * @param requestDate
     */
    void createRequestLogRecord(String userName, Integer pendingEvaluationId, String corporateName, String queueName, Date requestDate);

    /**
     * Create update ActivityLog entrance
     *
     * @param userName
     * @param pendingEvaluationId
     * @param sendDate
     */
    void updateStatusLogRecord(String userName, Integer pendingEvaluationId, Date sendDate);

    /**
     * gets activitylog for a user and a current date
     *
     * @param userName
     * @param date
     * @param startTime
     * @param endTime
     * @return List<ActivityLog>
     */
    List<ActivityLog> getLogForUser(String userName, Date date, Date startTime, Date endTime);

    /**
     * gets activitylog for a user and a current date
     *
     * @param date
     * @param startTime
     * @param endTime
     * @return List<ActivityLog>
     */
    List<ActivityLog> getLogForUsers(Date date, Date startTime, Date endTime);

    /**
     * Returns Activity Log DTO list for a user and a current date
     *
     * @param userName
     * @param timeZoneId
     * @param date
     * @param startTime
     * @param endTime
     * @return ActivityLogDTOContainer
     * @throws ScopixException
     */
    ActivityLogDTOContainer getLogDTOForUser(String userName, String timeZoneId, Date date, Date startTime, Date endTime) throws ScopixException;

    /**
     * Returns Activity Log DTO list for all users and a time
     *
     * @param timeZoneId
     * @param date
     * @param startTime
     * @param endTime
     * @return ActivityLogDTOContainer
     * @throws ScopixException
     */
    ActivityLogDTOContainer getLogDTO(String timeZoneId, Date date, Date startTime, Date endTime) throws ScopixException;

    /**
     * Returns active users for a time range
     *
     * @param date
     * @param startTime
     * @param endTime
     * @return List<String>
     */
    List<String> getActiveUsersInTimeRange(Date date, Date startTime, Date endTime);

    /**
     * get users in log
     *
     * @return List<String>
     */
    List<String> getOperatorsUsersInLog();

    /**
     * get all active users operating on a queue given a time period
     *
     * @param fromSecondAgo
     * @return List<UsersOnQueueDTO>
     */
    List<UsersOnQueueDTO> getActiveUsersOnQueues(Long fromSecondAgo);

    /**
     * get users last request given a time period
     *
     * @param timeZoneId
     * @param fromSecondAgo
     * @return List<UsersOnQueueDTO>
     */
    List<UsersLastRequestDTO> getUsersLastRequest(String timeZoneId, Long fromSecondAgo);
}
