/**
 *
 * Copyright © 2014, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 *
 */
package com.scopix.periscope.frontend.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Sebastian
 *
 */
@XmlRootElement(name = "RequestRegionTransferDTO")
public class RequestRegionTransferDTO {

    private Integer storeId;
    private Integer situationTemplateId;
    private String dayDate;
    private Integer completed;
    private String startTime;
    private String endTime;

    /**
     * @return the storeId
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * @param storeId the storeId to set
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * @return the situationTemplateId
     */
    public Integer getSituationTemplateId() {
        return situationTemplateId;
    }

    /**
     * @param situationTemplateId the situationTemplateId to set
     */
    public void setSituationTemplateId(Integer situationTemplateId) {
        this.situationTemplateId = situationTemplateId;
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
     * @return the completed
     */
    public Integer getCompleted() {
        return completed;
    }

    /**
     * @param completed the completed to set
     */
    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
}
