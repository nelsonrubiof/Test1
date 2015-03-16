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
 *  ConvertActivityLogToActivityLogDTOCommand.java
 * 
 *  Created on Jun 11, 2014, 4:12:47 PM
 * 
 */
package com.scopix.periscope.activitylog.command;

import com.scopix.periscope.activitylog.ActivityLog;
import com.scopix.periscope.activitylog.services.webservices.dto.ActivityLogDTO;
import com.scopix.periscope.activitylog.services.webservices.dto.ActivityLogDTOContainer;
import com.scopix.periscope.activitylog.services.webservices.dto.QueueInfoDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 *
 * @author Sebastian
 */
public class ConvertActivityLogToActivityLogDTOCommand {

    /**
     * Converts a list of ActivityLogs to a list of ActivityLogDTOs
     *
     * @param activityLogs
     * @return List<ActivityLogDTO>
     * @throws ScopixException
     */
    private DateFormat fullDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
    private  TimeZone targetTimeZone;

    public ActivityLogDTOContainer execute(String timeZoneId, List<ActivityLog> activityLogs) throws ScopixException {
        if (activityLogs == null) {
            throw new ScopixException("ActivityLog list can not be null");
        }
        targetTimeZone = TimeZone.getTimeZone(timeZoneId);
        ActivityLogDTOContainer activityLogDTOContainer = new ActivityLogDTOContainer();
        List<ActivityLogDTO> activityLogModels = new ArrayList<ActivityLogDTO>();
        List<String> users = new ArrayList<String>();
        Map<String, QueueInfoDTO> queues = new HashMap<String, QueueInfoDTO>();
        for (ActivityLog activityLog : activityLogs) {
            ActivityLogDTO activityLogDTO = activityLogModelDTORowMapper(activityLog);
            activityLogModels.add(activityLogModelDTORowMapper(activityLog));
            if (!users.contains(activityLog.getUserName())) {
                users.add(activityLog.getUserName());
            }
            if (!queues.containsKey(activityLog.getCorporateName() + activityLog.getQueueName())) {
                QueueInfoDTO queueInfoDTO = new QueueInfoDTO();
                queueInfoDTO.setCorporateName(activityLog.getCorporateName());
                queueInfoDTO.setQueueName(activityLog.getQueueName());
                if (ActivityLog.COMPLETED.equals(activityLogDTO.getStatus())) {
                    queueInfoDTO.setEvaluations(1);
                    queueInfoDTO.setAvgTimePerEval(activityLogDTO.getEvalTimeSeconds());
                } else {
                    queueInfoDTO.setEvaluations(0);
                    queueInfoDTO.setAvgTimePerEval(0);
                }

                queues.put(activityLog.getCorporateName() + activityLog.getQueueName(), queueInfoDTO);
            } else {
                if (ActivityLog.COMPLETED.equals(activityLogDTO.getStatus())) {
                    QueueInfoDTO queueInfoDTO = queues.get(activityLog.getCorporateName() + activityLog.getQueueName());
                    queueInfoDTO.setEvaluations(queueInfoDTO.getEvaluations() + 1);
                    Integer calc = ((queueInfoDTO.getAvgTimePerEval() * (queueInfoDTO.getEvaluations() - 1)) + activityLogDTO.getEvalTimeSeconds()) / queueInfoDTO.getEvaluations();
                    queueInfoDTO.setAvgTimePerEval(calc);
                }
            }
        }
        activityLogDTOContainer.setUsers(users);
        activityLogDTOContainer.setQueues(new ArrayList<QueueInfoDTO>(queues.values()));
        activityLogDTOContainer.setActivityLogs(activityLogModels);
        return activityLogDTOContainer;
    }

    /**
     * Converts a ActivityLogs to a ActivityLogDTOs
     *
     * @param activityLog
     * @return ActivityLogDTO
     * @throws ScopixException
     */
    public ActivityLogDTOContainer execute(ActivityLog activityLog) throws ScopixException {
        if (activityLog == null) {
            throw new ScopixException("ActivityLog can not be null");
        }
        ActivityLogDTOContainer activityLogDTOContainer = new ActivityLogDTOContainer();
        List<ActivityLogDTO> activityLogModels = new ArrayList<ActivityLogDTO>();
        activityLogModels.add(activityLogModelDTORowMapper(activityLog));
        List<String> users = new ArrayList<String>();
        users.add(activityLog.getUserName());
        List<QueueInfoDTO> queues = new ArrayList<QueueInfoDTO>();
        QueueInfoDTO queueInfoDTO = new QueueInfoDTO();
        queueInfoDTO.setCorporateName(activityLog.getCorporateName());
        queueInfoDTO.setQueueName(activityLog.getQueueName());
        queues.add(queueInfoDTO);
        activityLogDTOContainer.setUsers(users);
        activityLogDTOContainer.setQueues(queues);
        activityLogDTOContainer.setActivityLogs(activityLogModels);
        return activityLogDTOContainer;
    }

    private ActivityLogDTO activityLogModelDTORowMapper(ActivityLog activityLog) {
        ActivityLogDTO activityLogModelDTO = new ActivityLogDTO();
        activityLogModelDTO.setId(activityLog.getId());
        activityLogModelDTO.setUserName(activityLog.getUserName());
        activityLogModelDTO.setStatus(activityLog.getStatus());
        activityLogModelDTO.setCorporateName(activityLog.getCorporateName());
        activityLogModelDTO.setQueueName(activityLog.getQueueName());
        activityLogModelDTO.setRequestDate(getDateInTimeZone(activityLog.getRequestDate()));
        activityLogModelDTO.setSendDate("N/A");
        if (activityLog.getSendDate() != null) {
            activityLogModelDTO.setSendDate(getDateInTimeZone(activityLog.getSendDate()));
        }
        activityLogModelDTO.setEvalTimeSeconds(0);
        if (ActivityLog.COMPLETED.equals(activityLogModelDTO.getStatus())) {
            Long total = (activityLog.getSendDate().getTime() - activityLog.getRequestDate().getTime()) / 1000;
            activityLogModelDTO.setEvalTimeSeconds(total.intValue());
        }
        return activityLogModelDTO;
    }

    /**
     * Returns a String with the system date format and desired time zone
     *
     * @param inputDate
     * @return String date
     */
    private String getDateInTimeZone(Date inputDate) {
        fullDateFormat.setTimeZone(targetTimeZone);
        return fullDateFormat.format(inputDate);
    }
}
