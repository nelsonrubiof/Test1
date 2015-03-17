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
 *  RequestActivityLogDTO.java
 * 
 *  Created on Jun 11, 2014, 1:01:07 PM
 * 
 */
package com.scopix.periscope.activitylog.services.webservices.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@XmlRootElement(name = "RequestActivityLogDTO")
public class RequestActivityLogDTO {

    private String userName;
    private String dayDate;
    private String fromTime;
    private String toTime;
    private String timeZoneId;

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the dayDate
     */
    public String getDayDate() {
        return dayDate;
    }

    /**
     * @param dayDate the dayDate to set
     */
    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }

    /**
     * @return the fromTime
     */
    public String getFromTime() {
        return fromTime;
    }

    /**
     * @param fromTime the fromTime to set
     */
    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    /**
     * @return the toTime
     */
    public String getToTime() {
        return toTime;
    }

    /**
     * @param toTime the toTime to set
     */
    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    /**
     * @return the timeZoneId
     */
    public String getTimeZoneId() {
        return timeZoneId;
    }

    /**
     * @param timeZoneId the timeZoneId to set
     */
    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }
}
