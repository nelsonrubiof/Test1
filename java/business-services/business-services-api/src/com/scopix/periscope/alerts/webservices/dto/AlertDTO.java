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
 *  AlertDTO.java
 * 
 *  Created on 25-06-2012, 12:11:53 PM
 * 
 */
package com.scopix.periscope.alerts.webservices.dto;

import java.util.Map;

/**
 *
 * @author nelson
 */
public class AlertDTO {

    private String evidenceDate;
    private Integer storeId;
    private Integer areaId;
    private Map<String, Double> data;

    /**
     * @return the evidenceDate
     */
    public String getEvidenceDate() {
        return evidenceDate;
    }

    /**
     * @param evidenceDate the evidenceDate to set
     */
    public void setEvidenceDate(String evidenceDate) {
        this.evidenceDate = evidenceDate;
    }

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
     * @return the areaId
     */
    public Integer getAreaId() {
        return areaId;
    }

    /**
     * @param areaId the areaId to set
     */
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    /**
     * @return the data
     */
    public Map<String, Double> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Map<String, Double> data) {
        this.data = data;
    }

}
