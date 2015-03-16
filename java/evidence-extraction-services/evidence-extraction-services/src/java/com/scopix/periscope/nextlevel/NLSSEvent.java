/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
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
 *  NLSSEvent.java
 * 
 *  Created on 08-09-2011, 03:36:32 PM
 * 
 */
package com.scopix.periscope.nextlevel;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author nelson
 */
public class NLSSEvent {

    private String eventID;
    private String customerID;
    private String siteID;
    private Long eventTime;
    private Integer eventCategory;
    private Integer eventType;
    private Integer eventSeverityID;
    private String eventDescription;
    private String eventResource;
    private Integer displayUI;
    private String payload;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public void setEventTime(Long eventTime) {
        this.eventTime = eventTime;
    }

    public Integer getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(Integer eventCategory) {
        this.eventCategory = eventCategory;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer getEventSeverityID() {
        return eventSeverityID;
    }

    public void setEventSeverityID(Integer eventSeverityID) {
        this.eventSeverityID = eventSeverityID;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventResource() {
        return eventResource;
    }

    public void setEventResource(String eventResource) {
        this.eventResource = eventResource;
    }

    public Integer getDisplayUI() {
        return displayUI;
    }

    public void setDisplayUI(Integer displayUI) {
        this.displayUI = displayUI;
    }

    public String getNameFile() {
        String[] file = StringUtils.split(this.getPayload(), ",");
        String ret = FilenameUtils.getName(file[0]);
        return ret;
    }
}
