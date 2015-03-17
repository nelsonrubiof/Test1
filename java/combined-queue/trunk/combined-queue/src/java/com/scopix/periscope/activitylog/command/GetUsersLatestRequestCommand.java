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
 *  GetUsersLatestRequestCommand.java
 * 
 *  Created on Aug 20, 2014, 5:00:02 PM
 * 
 */
package com.scopix.periscope.activitylog.command;

import com.scopix.periscope.activitylog.dao.ActivityLogDAO;
import com.scopix.periscope.activitylog.services.webservices.dto.UsersLastRequestDTO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
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
public class GetUsersLatestRequestCommand {

    private ActivityLogDAO activityLogDAO;
    private DateFormat fullDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

    /**
     * get users last request given a period of time
     *
     * @param secondsInterval
     * @return List<UsersLastRequestDTO>
     */
    public List<UsersLastRequestDTO> execute(String timeZoneId, Long secondsInterval) {
        Date to = new Date();
        Date from = new Date(to.getTime() - (secondsInterval * 1000));
        List<Map<String, Object>> results = getActivityLogDAO().getUsersLatestRequestQueue(from, to);
        Map<String, Map<String, Object>> userLastRequest = new HashMap<String, Map<String, Object>>();
        List<UsersLastRequestDTO> userRequests = new ArrayList<UsersLastRequestDTO>();
        TimeZone targetTimeZone = TimeZone.getTimeZone(timeZoneId);

        for (Map<String, Object> result : results) {
            if (!userLastRequest.containsKey((String) result.get("user_name"))) {
                userLastRequest.put((String) result.get("user_name"), result);
            } else {
                Date currVal = (Date) userLastRequest.get((String) result.get("user_name")).get("max");
                Date newVal = (Date) result.get("max");
                if (currVal.before(newVal)) {
                    userLastRequest.put((String) result.get("user_name"), result);
                }
            }
        }
        for (Map<String, Object> entry : userLastRequest.values()) {
            UsersLastRequestDTO usersLastRequest = new UsersLastRequestDTO();
            usersLastRequest.setUserName((String) entry.get("user_name"));
            usersLastRequest.setCorporateName((String) entry.get("corporate_name"));
            usersLastRequest.setQueueName((String) entry.get("queue_name"));
            Date requestDate = (Date) entry.get("max");
            String dateFormatted = getDateInTimeZone(requestDate, targetTimeZone);
            usersLastRequest.setLastRequest(dateFormatted);
            Long diffInMins = ((to.getTime() - requestDate.getTime()) / 1000) / 60;
            usersLastRequest.setMinutesAgo(diffInMins.toString());
            userRequests.add(usersLastRequest);
        }
        return userRequests;
    }

    /**
     * Returns a String with the system date format and desired time zone
     *
     * @param inputDate
     * @param targetTimeZone
     * @return String date
     */
    private String getDateInTimeZone(Date inputDate, TimeZone targetTimeZone) {
        fullDateFormat.setTimeZone(targetTimeZone);
        return fullDateFormat.format(inputDate);
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
