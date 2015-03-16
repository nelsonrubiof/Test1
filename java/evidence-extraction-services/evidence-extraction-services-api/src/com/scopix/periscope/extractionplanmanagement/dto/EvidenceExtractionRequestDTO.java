/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * EvidenceExtractionRequestDTO.java
 * 
 * Created on 02-07-2008, 02:07:31 AM
 */
package com.scopix.periscope.extractionplanmanagement.dto;

/**
 *
 * @author marko.perich
 */
public class EvidenceExtractionRequestDTO {

    private String requestType;
    private String requestedTime;
    private Integer duration;
    private Integer deviceId;
    private Integer extractionPlanDetailId;
    private Integer dayOfWeek;
    // para priorizar la subida de datos
    private Integer priorization;
    private Boolean live;

    /**
     *
     * @return
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     *
     * @param requestType
     */
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    /**
     *
     * @return
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     *
     * @param duration
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     *
     * @return
     */
    public Integer getDeviceId() {
        return deviceId;
    }

    /**
     *
     * @param deviceId
     */
    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    /**
     *
     * @return
     */
    public Integer getExtractionPlanDetailId() {
        return extractionPlanDetailId;
    }

    /**
     *
     * @param extractionPlanDetailId
     */
    public void setExtractionPlanDetailId(Integer extractionPlanDetailId) {
        this.extractionPlanDetailId = extractionPlanDetailId;
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
     * @return the requestedTime
     */
    public String getRequestedTime() {
        return requestedTime;
    }

    /**
     * @param requestedTime the requestedTime to set
     */
    public void setRequestedTime(String requestedTime) {
        this.requestedTime = requestedTime;
    }

    /**
     *
     * @return
     */
    public Integer getPriorization() {
        return priorization;
    }

    /**
     *
     * @param priorization
     */
    public void setPriorization(Integer priorization) {
        this.priorization = priorization;
    }

    /**
     * @return the live
     */
    public Boolean getLive() {
        return live;
    }

    /**
     * @param live the live to set
     */
    public void setLive(Boolean live) {
        this.live = live;
    }
}