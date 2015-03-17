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
 *  DetalleSolicitudDTO.java
 * 
 *  Created on 07-10-2010, 12:56:23 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.dto;

/**
 *
 * @author nelson
 */
public class DetalleSolicitudDTO {
    private String store;
    private String situationTemplate;
    private String area;
    private Integer day;
    private String date;
    private String duration;

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getSituationTemplate() {
        return situationTemplate;
    }

    public void setSituationTemplate(String situationTemplate) {
        this.situationTemplate = situationTemplate;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
