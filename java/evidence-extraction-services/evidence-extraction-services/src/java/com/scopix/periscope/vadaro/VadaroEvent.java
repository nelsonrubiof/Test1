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
 *  VadaroEvent.java
 * 
 *  Created on 08-07-2014, 11:44:21 AM
 * 
 */
package com.scopix.periscope.vadaro;

import java.util.Date;

/**
 *
 * @author Nelson
 */
public class VadaroEvent {

    private Date time;
    private String service;
    private String name;
    private Integer entered;
    private Integer exited;
    private Integer abandoned;
    private Integer length;
    private Double waitTime;
    private Double serviceTime;

    @Override
	public String toString() {
		return "VadaroEvent [time=" + time + ", service=" + service + ", name="
				+ name + ", entered=" + entered + ", exited=" + exited
				+ ", abandoned=" + abandoned + ", length=" + length
				+ ", waitTime=" + getWaitTime() + ", serviceTime=" + getServiceTime()
				+ "]";
	}

	/**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return the service
     */
    public String getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the entered
     */
    public Integer getEntered() {
        return entered;
    }

    /**
     * @param entered the entered to set
     */
    public void setEntered(Integer entered) {
        this.entered = entered;
    }

    /**
     * @return the exited
     */
    public Integer getExited() {
        return exited;
    }

    /**
     * @param exited the exited to set
     */
    public void setExited(Integer exited) {
        this.exited = exited;
    }

    /**
     * @return the abandoned
     */
    public Integer getAbandoned() {
        return abandoned;
    }

    /**
     * @param abandoned the abandoned to set
     */
    public void setAbandoned(Integer abandoned) {
        this.abandoned = abandoned;
    }

    /**
     * @return the length
     */
    public Integer getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     * @return the waitTime
     */
    public Double getWaitTime() {
        return waitTime;
    }

    /**
     * @param waitTime the waitTime to set
     */
    public void setWaitTime(Double waitTime) {
        this.waitTime = waitTime;
    }

    /**
     * @return the serviceTime
     */
    public Double getServiceTime() {
        return serviceTime;
    }

    /**
     * @param serviceTime the serviceTime to set
     */
    public void setServiceTime(Double serviceTime) {
        this.serviceTime = serviceTime;
    }

}
