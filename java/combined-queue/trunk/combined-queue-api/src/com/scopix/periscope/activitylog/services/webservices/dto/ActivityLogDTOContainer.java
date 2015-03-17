
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
 *  ActivityLogDTOContainer.java
 * 
 *  Created on Jun 11, 2014, 12:55:34 PM
 * 
 */
package com.scopix.periscope.activitylog.services.webservices.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@XmlRootElement(name = "ActivityLogDTOContainer")
public class ActivityLogDTOContainer {

    private List<String> users;
    private List<QueueInfoDTO> queues;
    private List<ActivityLogDTO> activityLogs;

    /**
     * @return the users
     */
    public List<String> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<String> users) {
        this.users = users;
    }

    /**
     * @return the queues
     */
    public List<QueueInfoDTO> getQueues() {
        return queues;
    }

    /**
     * @param queues the queues to set
     */
    public void setQueues(List<QueueInfoDTO> queues) {
        this.queues = queues;
    }

    /**
     * @return the activityLogs
     */
    public List<ActivityLogDTO> getActivityLogs() {
        return activityLogs;
    }

    /**
     * @param activityLogs the activityLogs to set
     */
    public void setActivityLogs(List<ActivityLogDTO> activityLogs) {
        this.activityLogs = activityLogs;
    }

}
