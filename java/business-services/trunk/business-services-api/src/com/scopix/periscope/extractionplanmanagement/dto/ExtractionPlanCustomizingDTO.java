/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionplanmanagement.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class ExtractionPlanCustomizingDTO {

    private Integer id;
    private Integer situationTemplateId;
    private Integer storeId;
    //Descripcion del areaType
    private String areaType; //areaType.name
    //Id del areaType
    private Integer areaTypeId;
    private Boolean active;
    private Boolean oneEvaluation;
    private String status;
    private Boolean send;
    private List<Integer> sensorIds;
    private List<ExtractionPlanMetricDTO> extractionPlanMetricDTOs;
    /**
     * priorizacion para el epc este puede ser null
     */
    private Integer priorization;
    /**
     * Random de Camaras para solicitud de epc TRUE|FALSE
     */
    private Boolean randomCamera;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSituationTemplateId() {
        return situationTemplateId;
    }

    public void setSituationTemplateId(Integer situationTemplateId) {
        this.situationTemplateId = situationTemplateId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getAreaTypeId() {
        return areaTypeId;
    }

    public void setAreaTypeId(Integer areaTypeId) {
        this.areaTypeId = areaTypeId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getOneEvaluation() {
        return oneEvaluation;
    }

    public void setOneEvaluation(Boolean oneEvaluation) {
        this.oneEvaluation = oneEvaluation;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Integer> getSensorIds() {
        if (sensorIds == null){
            sensorIds = new ArrayList<Integer>();
        }
        return sensorIds;
    }

    public void setSensorIds(List<Integer> sensorIds) {
        this.sensorIds = sensorIds;
    }

    public List<ExtractionPlanMetricDTO> getExtractionPlanMetricDTOs() {
        if (extractionPlanMetricDTOs == null) {
            extractionPlanMetricDTOs = new ArrayList<ExtractionPlanMetricDTO>();
        }
        return extractionPlanMetricDTOs;
    }

    public void setExtractionPlanMetricDTOs(List<ExtractionPlanMetricDTO> extractionPlanMetricDTOs) {
        this.extractionPlanMetricDTOs = extractionPlanMetricDTOs;
    }

    public Boolean getSend() {
        return send;
    }

    public void setSend(Boolean send) {
        this.send = send;
    }

    public Integer getPriorization() {
        return priorization;
    }

    public void setPriorization(Integer priorization) {
        this.priorization = priorization;
    }

    public Boolean getRandomCamera() {
        return randomCamera;
    }

    public void setRandomCamera(Boolean randomCamera) {
        this.randomCamera = randomCamera;
    }
}
