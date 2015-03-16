/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * FilteringData.java
 *
 * Created on 20-05-2008, 01:18:44 PM
 *
 */


package com.scopix.periscope.queuemanagement.dto;

import java.util.Date;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class FilteringData {
    private Date date;
    private Integer area;
    private Integer store;
    private String status;
    private String queue;
    private Integer queueNameId;
    private String initialTime;
    private String endTime;
    private String dateFilter;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    /**
     * @return the queueName
     */
    public Integer getQueueNameId() {
        return queueNameId;
    }

    /**
     * @param queueName the queueName to set
     */
    public void setQueueNameId(Integer queueNameId) {
        this.queueNameId = queueNameId;
    }

    /**
     * @return the initialTime
     */
    public String getInitialTime() {
        return initialTime;
    }

    /**
     * @param initialTime the initialTime to set
     */
    public void setInitialTime(String initialTime) {
        this.initialTime = initialTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the dateFilter
     */
    public String getDateFilter() {
        return dateFilter;
    }

    /**
     * @param dateFilter the dateFilter to set
     */
    public void setDateFilter(String dateFilter) {
        this.dateFilter = dateFilter;
    }
}