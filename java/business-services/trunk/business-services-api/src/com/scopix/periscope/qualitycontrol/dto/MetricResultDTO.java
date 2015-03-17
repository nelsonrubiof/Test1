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
 *  MetricResultDTO.java
 * 
 *  Created on 09-12-2011, 02:07:06 PM
 * 
 */
package com.scopix.periscope.qualitycontrol.dto;

import com.scopix.periscope.evaluationmanagement.dto.EvidenceDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class MetricResultDTO {

    private String evidencePrePath;
    private String proofPrePath;
    private Integer metricId;
    private String metricName;
    private Integer metricTemplateId;
    private Integer observedMetricId;
    private String metricType;
    private Integer areaId;
    private String area;
    private Integer metricResult;
    private String descriptionOperator;
    private List<EvidenceDTO> evidences;
    private String situtionTemplateName;

    public String getEvidencePrePath() {
        return evidencePrePath;
    }

    public void setEvidencePrePath(String evidencePrePath) {
        this.evidencePrePath = evidencePrePath;
    }

    public String getProofPrePath() {
        return proofPrePath;
    }

    public void setProofPrePath(String proofPrePath) {
        this.proofPrePath = proofPrePath;
    }

    public Integer getMetricId() {
        return metricId;
    }

    public void setMetricId(Integer metricId) {
        this.metricId = metricId;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public Integer getMetricTemplateId() {
        return metricTemplateId;
    }

    public void setMetricTemplateId(Integer metricTemplateId) {
        this.metricTemplateId = metricTemplateId;
    }

    public Integer getObservedMetricId() {
        return observedMetricId;
    }

    public void setObservedMetricId(Integer observedMetricId) {
        this.observedMetricId = observedMetricId;
    }

    public String getMetricType() {
        return metricType;
    }

    public void setMetricType(String metricType) {
        this.metricType = metricType;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getMetricResult() {
        return metricResult;
    }

    public void setMetricResult(Integer metricResult) {
        this.metricResult = metricResult;
    }

    public List<EvidenceDTO> getEvidences() {
        if (evidences == null) {
            evidences = new ArrayList<EvidenceDTO>();
        }
        return evidences;
    }

    public void setEvidences(List<EvidenceDTO> evidences) {
        this.evidences = evidences;
    }

    public String getSitutionTemplateName() {
        return situtionTemplateName;
    }

    public void setSitutionTemplateName(String situtionTemplateName) {
        this.situtionTemplateName = situtionTemplateName;
    }

    public String getDescriptionOperator() {
        return descriptionOperator;
    }

    public void setDescriptionOperator(String descriptionOperator) {
        this.descriptionOperator = descriptionOperator;
    }
}
