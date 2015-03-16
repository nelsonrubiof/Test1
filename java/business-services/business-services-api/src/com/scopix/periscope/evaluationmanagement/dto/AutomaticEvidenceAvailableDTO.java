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
 * EvidenceAvailableDTO.java
 *
 * Created on 09-05-2008, 11:51:10 AM
 *
 */
package com.scopix.periscope.evaluationmanagement.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 *
 * @author Cesar Abarza Suazo.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AutomaticEvidenceAvailableDTO {

    private String path;
    private Integer deviceId;
    private Integer evidenceServicesServerId;
    private Integer evidenceExtractionServicesServerId;
    private Date evidenceDate;
    private Integer processId;
//    private List<HashMap<String, Integer>> situationMetricList;
    /**
     * key corresponde a la situaacion y en el set estan las metricas asociadas
     */
    private List<SituationMetricsDTO> situationMetrics;

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the deviceId
     */
    public Integer getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return the evidenceServicesServerId
     */
    public Integer getEvidenceServicesServerId() {
        return evidenceServicesServerId;
    }

    /**
     * @param evidenceServicesServerId the evidenceServicesServerId to set
     */
    public void setEvidenceServicesServerId(Integer evidenceServicesServerId) {
        this.evidenceServicesServerId = evidenceServicesServerId;
    }

    /**
     * @return the evidenceDate
     */
    public Date getEvidenceDate() {
        return evidenceDate;
    }

    /**
     * @param evidenceDate the evidenceDate to set
     */
    public void setEvidenceDate(Date evidenceDate) {
        this.evidenceDate = evidenceDate;
    }

    /**
     * @return the processId
     */
    public Integer getProcessId() {
        return processId;
    }

    /**
     * @param processId the processId to set
     */
    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

//    /**
//     * @return the situationMetricList
//     */
//    public List<HashMap<String, Integer>> getSituationMetricList() {
//        if (situationMetricList == null) {
//            situationMetricList = new ArrayList<HashMap<String, Integer>>();
//        }
//        return situationMetricList;
//    }
//
//    /**
//     * @param situationMetricList the situationMetricList to set
//     */
//    public void setSituationMetricList(List<HashMap<String, Integer>> situationMetricList) {
//        this.situationMetricList = situationMetricList;
//    }

    /**
     * @return the evidenceExtractionServicesServerId
     */
    public Integer getEvidenceExtractionServicesServerId() {
        return evidenceExtractionServicesServerId;
    }

    /**
     * @param evidenceExtractionServicesServerId the evidenceExtractionServicesServerId to set
     */
    public void setEvidenceExtractionServicesServerId(Integer evidenceExtractionServicesServerId) {
        this.evidenceExtractionServicesServerId = evidenceExtractionServicesServerId;
    }

    /**
     * @return the situationMetrics
     */
    public List<SituationMetricsDTO> getSituationMetrics() {
        if(situationMetrics == null) {
            situationMetrics = new ArrayList<SituationMetricsDTO>();
        }
        return situationMetrics;
    }

    /**
     * @param situationMetrics the situationMetrics to set
     */
    public void setSituationMetrics(List<SituationMetricsDTO> situationMetrics) {
        this.situationMetrics = situationMetrics;
    }

}
