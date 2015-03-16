/*
 *
 * Copyright � 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * NewAutomaticEvidenceDTO.java
 *
 * Created on 06-04-2009, 11:23:33 AM
 *
 */
package com.scopix.periscope.evidencemanagement.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 *
 * @author Gustavo Alvarez
 * @version 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NewAutomaticEvidenceDTO { 

    private Integer evidenceProviderId;
    private String evidenceDate;
    private String fileName;
    private Integer processId;
    //private List<HashMap<String, Integer>> situationMetricList;

    /**
     * key corresponde a la situaacion y en el set estan las metricas asociadas
     */
    private List<SituationMetricsDTO> situationMetrics;
    private Integer extractionPlanServerId;

    /**
     * @return the evidenceProviderId
     */
    public Integer getEvidenceProviderId() {
        return evidenceProviderId;
    }

    /**
     * @param evidenceProviderId the evidenceProviderId to set
     */
    public void setEvidenceProviderId(Integer evidenceProviderId) {
        this.evidenceProviderId = evidenceProviderId;
    }

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
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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
     * @return the extractionPlanServerId
     */
    public Integer getExtractionPlanServerId() {
        return extractionPlanServerId;
    }

    /**
     * @param extractionPlanServerId the extractionPlanServerId to set
     */
    public void setExtractionPlanServerId(Integer extractionPlanServerId) {
        this.extractionPlanServerId = extractionPlanServerId;
    }

    /**
     * @return the situationMetrics
     */
    public List<SituationMetricsDTO> getSituationMetrics() {
        if(situationMetrics == null){
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
