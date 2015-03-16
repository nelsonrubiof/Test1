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
 *  GetUsersOnQueuesCommand.java
 * 
 *  Created on Aug 14, 2014, 10:48:34 AM
 * 
 */
package com.scopix.periscope.activitylog.command;

import com.scopix.periscope.activitylog.dao.ActivityLogDAO;
import com.scopix.periscope.activitylog.services.webservices.dto.UsersOnQueueDTO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sebastian
 */
public class GetUsersOnQueuesCommand {
    
    private ActivityLogDAO activityLogDAO;

    /**
     * Get users on queues within the desired time range
     *
     * @param secondsInterval
     * @return List<UsersOnQueueDTO>
     */
    public List<UsersOnQueueDTO> execute(Long secondsInterval) {
//user_name, corporate_name, queue_name
        Date to = new Date();
        Date from = new Date(to.getTime() - (secondsInterval * 1000));
        List<Map<String, Object>> results = getActivityLogDAO().getUsersPerQueue(from, to);
        Map<String, UsersOnQueueDTO> dtos = new HashMap<String, UsersOnQueueDTO>();
        for (Map<String, Object> result : results) {
            String userName = (String) result.get("user_name");
            String corporateName = (String) result.get("corporate_name");
            String queueName = (String) result.get("queue_name");
            String key = corporateName + queueName;
            if (dtos.containsKey(key)) {
                if (!dtos.get(key).getUsers().contains(userName)) {
                    dtos.get(key).getUsers().add(userName);
                    dtos.get(key).setTotalNumber(dtos.get(key).getTotalNumber() + 1);
                }
            } else {
                UsersOnQueueDTO usersOnQueueDTO = new UsersOnQueueDTO();
                usersOnQueueDTO.setCorporateName(corporateName);
                usersOnQueueDTO.setQueueName(queueName);
                List<String> users = new ArrayList<String>();
                users.add(userName);
                usersOnQueueDTO.setTotalNumber(1);
                usersOnQueueDTO.setUsers(users);
                dtos.put(key, usersOnQueueDTO);
            }
        }
        return new ArrayList<UsersOnQueueDTO>(dtos.values());
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
