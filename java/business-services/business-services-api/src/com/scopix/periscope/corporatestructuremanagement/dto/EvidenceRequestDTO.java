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
 * EvidenceRequestDTO.java
 *
 * Created on 01-07-2008, 01:52:30 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.dto;

import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author marko.perich
 */
public class EvidenceRequestDTO implements Comparator<EvidenceRequestDTO> {

    private ExtractionPlanDetailDTO extractionPlanDetailDTO;
    private String requestType;
    private Date requestedTime;
    private Integer duration;
    private Integer deviceId;
    private Integer businessServicesRequestId;
    private Integer dayOfWeek;
    private Boolean live;
    /**
     * nuevo campo
     */
    private Integer priorization;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Date getRequestedTime() {
        return requestedTime;
    }

    public void setRequestedTime(Date requestedTime) {
        this.requestedTime = requestedTime;
    }

    public Integer getDuration() {
        if (duration == null) {
            duration = 0;
        }
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public ExtractionPlanDetailDTO getExtractionPlanDetailDTO() {
        return extractionPlanDetailDTO;
    }

    public void setExtractionPlanDetailDTO(ExtractionPlanDetailDTO extractionPlanDetailDTO) {
        this.extractionPlanDetailDTO = extractionPlanDetailDTO;
    }

    public int compare(EvidenceRequestDTO er1, EvidenceRequestDTO er2) {
        int result = er1.getRequestType().compareTo(er2.getRequestType());
        if (result == 0) {
            result = er1.getLive().compareTo(er2.getLive());
            if (er1.getDeviceId() > er2.getDeviceId()) {
                result = 1;
            } else if (er1.getDeviceId() < er2.getDeviceId()) {
                result = -1;
            } else if (er1.getRequestedTime().after(er2.getRequestedTime())) {
                result = 1;
            } else if (er1.getRequestedTime().before(er2.getRequestedTime())) {
                result = -1;
            } else if (er1.getDuration() != null && er1.getDuration() > er2.getDuration()) {
                result = 1;
            } else if (er1.getDuration() != null && er1.getDuration() < er2.getDuration()) {
                result = -1;
            } else if (er1.getDayOfWeek() > er2.getDayOfWeek()) {
                result = 1;
            } else if (er1.getDayOfWeek() < er2.getDayOfWeek()) {
                result = -1;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "RequestedType: " + getRequestType() + "; DeviceId: " + getDeviceId() + "; Duration: " + getDuration()
                + "; RequestedTime:" + requestedTime;
    }

    public Integer getBusinessServicesRequestId() {
        return businessServicesRequestId;
    }

    public void setBusinessServicesRequestId(Integer businessServicesRequestId) {
        this.businessServicesRequestId = businessServicesRequestId;
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

    public Integer getPriorization() {
        return priorization;
    }

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
