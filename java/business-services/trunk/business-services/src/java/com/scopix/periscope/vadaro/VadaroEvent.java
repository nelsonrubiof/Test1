/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * 
 * VadaroEvent.java
 * 
 * Created on 10-07-2014, 12:58:08 PM
 */
package com.scopix.periscope.vadaro;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.periscopefoundation.BusinessObject;

/**
 *
 * @author Nelson
 */
@Entity
public class VadaroEvent extends BusinessObject {

    private static final long serialVersionUID = 7758141503957341016L;

    @OneToOne
    private Store store;
    private Integer abandoned;
    @Lob
    private String cameraName;
    private Integer entered;
    private Integer exited;
    private Integer length;
    @Lob
    private String service;
    private Double serviceTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    private Double waitTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date serverTime = new Date();
    
    @OneToOne
    private Evidence evidence;

    @Override
    public String toString() {
        return "VadaroEvent [store=" + store + ", abandoned=" + abandoned + ", cameraName=" + cameraName + ", entered=" + entered
                + ", exited=" + exited + ", length=" + length + ", service=" + service + ", serviceTime=" + serviceTime
                + ", time=" + time + ", waitTime=" + waitTime + ", serverTime=" + serverTime + "]";
    }

    /**
     * @return the store
     */
    public Store getStore() {
        return store;
    }

    /**
     * @param store
     *            the store to set
     */
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * @return the abandoned
     */
    public Integer getAbandoned() {
        return abandoned;
    }

    /**
     * @param abandoned
     *            the abandoned to set
     */
    public void setAbandoned(Integer abandoned) {
        this.abandoned = abandoned;
    }

    /**
     * @return the cameraName
     */
    public String getCameraName() {
        return cameraName;
    }

    /**
     * @param cameraName
     *            the cameraName to set
     */
    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    /**
     * @return the entered
     */
    public Integer getEntered() {
        return entered;
    }

    /**
     * @param entered
     *            the entered to set
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
     * @param exited
     *            the exited to set
     */
    public void setExited(Integer exited) {
        this.exited = exited;
    }

    /**
     * @return the length
     */
    public Integer getLength() {
        return length;
    }

    /**
     * @param length
     *            the length to set
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     * @return the service
     */
    public String getService() {
        return service;
    }

    /**
     * @param service
     *            the service to set
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * @return the serviceTime
     */
    public Double getServiceTime() {
        return serviceTime;
    }

    /**
     * @param serviceTime
     *            the serviceTime to set
     */
    public void setServiceTime(Double serviceTime) {
        this.serviceTime = serviceTime;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time
     *            the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return the waitTime
     */
    public Double getWaitTime() {
        return waitTime;
    }

    /**
     * @param waitTime
     *            the waitTime to set
     */
    public void setWaitTime(Double waitTime) {
        this.waitTime = waitTime;
    }

    public Date getServerTime() {
        return serverTime;
    }

    public void setServerTime(Date serverTime) {
        this.serverTime = serverTime;
    }

    /**
     * @return the evidence
     */
    public Evidence getEvidence() {
        return evidence;
    }

    /**
     * @param evidence the evidence to set
     */
    public void setEvidence(Evidence evidence) {
        this.evidence = evidence;
    }

}
