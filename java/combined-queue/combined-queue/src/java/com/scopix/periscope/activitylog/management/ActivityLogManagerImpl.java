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
 *  ActivityLogManagerImpl.java
 * 
 *  Created on Jun 10, 2014, 4:38:45 PM
 * 
 */
package com.scopix.periscope.activitylog.management;

import com.scopix.periscope.activitylog.ActivityLog;
import com.scopix.periscope.activitylog.command.ConvertActivityLogToActivityLogDTOCommand;
import com.scopix.periscope.activitylog.command.CreateActivityLogEntryCommand;
import com.scopix.periscope.activitylog.command.GetActiveUsersInTimeRangeCommand;
import com.scopix.periscope.activitylog.command.GetActivityLogForUserCommand;
import com.scopix.periscope.activitylog.command.GetActivityLogForUsersCommand;
import com.scopix.periscope.activitylog.command.GetUsersInActivityLogCommand;
import com.scopix.periscope.activitylog.command.GetUsersLatestRequestCommand;
import com.scopix.periscope.activitylog.command.GetUsersOnQueuesCommand;
import com.scopix.periscope.activitylog.command.UpdateActivityLogCommand;
import com.scopix.periscope.activitylog.services.webservices.dto.ActivityLogDTOContainer;
import com.scopix.periscope.activitylog.services.webservices.dto.UsersLastRequestDTO;
import com.scopix.periscope.activitylog.services.webservices.dto.UsersOnQueueDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Sebastian
 */
@SpringBean(rootClass = ActivityLogManager.class)
@Transactional(rollbackFor = {ScopixException.class, DataAccessException.class})
public class ActivityLogManagerImpl implements ActivityLogManager {

    private static Logger log = Logger.getLogger(ActivityLogManager.class);

    @Override
    public void createRequestLogRecord(String userName, Integer pendingEvaluationId, String corporateName, String queueName, Date requestDate) {
        log.info("Saving log record entry for pending evaluation: " + pendingEvaluationId.toString() + " and user: " + userName);
        CreateActivityLogEntryCommand createActivityLogEntryCommand = new CreateActivityLogEntryCommand();
        createActivityLogEntryCommand.execute(userName, ActivityLog.PENDING, pendingEvaluationId, corporateName, queueName, requestDate);
        log.info("finished");
    }

    @Override
    public void updateStatusLogRecord(String userName, Integer pendingEvaluationId, Date sendDate) {
        log.info("updating log record entry for user: " + userName);
        UpdateActivityLogCommand updateActivityLogCommand = new UpdateActivityLogCommand();
        updateActivityLogCommand.execute(userName, ActivityLog.COMPLETED, pendingEvaluationId, sendDate);
        log.info("finished");
    }

    @Override
    public List<ActivityLog> getLogForUser(String userName, Date date, Date startTime, Date endTime) {
        log.info("getting log recor for user : " + userName);
        GetActivityLogForUserCommand getActivityLogForUserCommand = new GetActivityLogForUserCommand();
        return getActivityLogForUserCommand.execute(userName, date, startTime, endTime);

    }

    @Override
    public List<ActivityLog> getLogForUsers(Date date, Date startTime, Date endTime) {
        log.info("getting log recor for user : ");
        GetActivityLogForUsersCommand getActivityLogForUsersCommand = new GetActivityLogForUsersCommand();
        return getActivityLogForUsersCommand.execute(date, startTime, endTime);

    }

    @Override
    public ActivityLogDTOContainer getLogDTOForUser(String userName, String timeZoneId, Date date, Date startTime, Date endTime) throws ScopixException {
        log.info("getting log recor for user : " + userName);
        List<ActivityLog> actLog = getLogForUser(userName, date, startTime, endTime);
        ConvertActivityLogToActivityLogDTOCommand convertActivityLogToActivityLogDTOCommand = new ConvertActivityLogToActivityLogDTOCommand();
        return convertActivityLogToActivityLogDTOCommand.execute(timeZoneId, actLog);

    }

    @Override
    public List<String> getOperatorsUsersInLog() {
        GetUsersInActivityLogCommand getUsersInActivityLogCommand = new GetUsersInActivityLogCommand();
        return getUsersInActivityLogCommand.execute();
    }

    @Override
    public ActivityLogDTOContainer getLogDTO(String timeZoneId, Date date, Date startTime, Date endTime) throws ScopixException {
        log.info("getting log record for users");
        List<ActivityLog> actLog = getLogForUsers(date, startTime, endTime);
        ConvertActivityLogToActivityLogDTOCommand convertActivityLogToActivityLogDTOCommand = new ConvertActivityLogToActivityLogDTOCommand();
        return convertActivityLogToActivityLogDTOCommand.execute(timeZoneId, actLog);

    }

    @Override
    public List<String> getActiveUsersInTimeRange(Date date, Date startTime, Date endTime) {
        log.info("getting active users for time range ");
        GetActiveUsersInTimeRangeCommand getActiveUsersInTimeRangeCommand = new GetActiveUsersInTimeRangeCommand();
        return getActiveUsersInTimeRangeCommand.execute(date, startTime, endTime);
    }

    @Override
    public List<UsersOnQueueDTO> getActiveUsersOnQueues(Long fromSecondAgo) {
        GetUsersOnQueuesCommand getUsersOnQueuesCommand = new GetUsersOnQueuesCommand();
        return getUsersOnQueuesCommand.execute(fromSecondAgo);
    }

    @Override
    public List<UsersLastRequestDTO> getUsersLastRequest(String timeZoneId, Long fromSecondAgo) {
        GetUsersLatestRequestCommand getUsersLatestRequestCommand = new GetUsersLatestRequestCommand();
        return getUsersLatestRequestCommand.execute(timeZoneId, fromSecondAgo);
    }
}
