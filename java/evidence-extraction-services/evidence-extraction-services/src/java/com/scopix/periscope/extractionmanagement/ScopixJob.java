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
 *  Jobs.java
 * 
 *  Created on 02-01-2014, 05:30:57 PM
 * 
 */
package com.scopix.periscope.extractionmanagement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class ScopixJob {

    private Integer dayOfWeek;
    private String execution;
    private List<RequestTimeZone> requestTimeZones;

    public ScopixJob(Integer dayOfWeek, String execution) {
        this.dayOfWeek = dayOfWeek;
        this.execution = execution;
    }

    /**
     * @return the dayOfWeek
     */
    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * @param dayOfWeek the dayOfWeek to set
     */
    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * @return the execution
     */
    public String getExecution() {
        return execution;
    }

    /**
     * @param execution the execution to set
     */
    public void setExecution(String execution) {
        this.execution = execution;
    }

    /**
     * @return the requestTimeZones
     */
    public List<RequestTimeZone> getRequestTimeZones() {
        if (requestTimeZones == null){
            requestTimeZones = new ArrayList<RequestTimeZone>();
        }
        return requestTimeZones;
    }

    /**
     * @param requestTimeZones the requestTimeZones to set
     */
    public void setRequestTimeZones(List<RequestTimeZone> requestTimeZones) {
        this.requestTimeZones = requestTimeZones;
    }

}
