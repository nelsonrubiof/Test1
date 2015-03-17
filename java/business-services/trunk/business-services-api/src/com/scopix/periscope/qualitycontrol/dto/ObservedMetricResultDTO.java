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
 * EvidenceFinishedDTO.java
 *
 * Created on 04-06-2008, 03:37:38 PM
 *
 */
package com.scopix.periscope.qualitycontrol.dto;

import com.scopix.periscope.evaluationmanagement.dto.EvidenceDTO;
import com.scopix.periscope.templatemanagement.MetricType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class ObservedMetricResultDTO implements Comparable<ObservedMetricResultDTO> {

    private Integer situationId;
    private String evidencePrePath;
    private String proofPrePath;
    private Integer metricId;
    private String metricName;
    private Integer metricTemplateId;
    private Integer observedMetricId;
    private MetricType metricType;
    private Integer areaId;
    private String area;
    private Integer metricResult;
    private String evaluationInstruction;
    private String userName;
    private Date evaluationDate;
    private Date evidenceDate;
    private List<EvidenceDTO> evidences;
    private String situtionTemplateName;
    private static Comparator<ObservedMetricResultDTO> comparatorByEvidenceDateAndSituationId;

    public static Comparator<ObservedMetricResultDTO> getComparatorByEvidenceDateAndSituationId() {
        comparatorByEvidenceDateAndSituationId =
                new Comparator<ObservedMetricResultDTO>() {

                    public int compare(ObservedMetricResultDTO o1, ObservedMetricResultDTO o2) {
                        int resp = o1.getEvidenceDate().compareTo(o2.getEvidenceDate());

                        if (resp == 0) {
                            resp = o1.getSituationId().compareTo(o2.getSituationId());
                        }

                        return resp;
                    }
                };

        return comparatorByEvidenceDateAndSituationId;
    }

    public static void setComparatorByEvidenceDateAndSituationId(
            Comparator<ObservedMetricResultDTO> aComparatorByEvidenceDateAndSituationId) {
        comparatorByEvidenceDateAndSituationId = aComparatorByEvidenceDateAndSituationId;
    }
    
    public Integer getMetricId() {
        return metricId;
    }

    public void setMetricId(Integer metricId) {
        this.metricId = metricId;
    }

    public Integer getMetricTemplateId() {
        return metricTemplateId;
    }

    public void setMetricTemplateId(Integer metricTemplateId) {
        this.metricTemplateId = metricTemplateId;
    }

    public MetricType getMetricType() {
        return metricType;
    }

    public void setMetricType(MetricType metricType) {
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

    public String getProofPrePath() {
        return proofPrePath;
    }

    public void setProofPrePath(String proofPrePath) {
        this.proofPrePath = proofPrePath;
    }

    public Integer getObservedMetricId() {
        return observedMetricId;
    }

    public void setObservedMetricId(Integer observedMetricId) {
        this.observedMetricId = observedMetricId;
    }

    public boolean isYesNoType() {
        if (metricType.equals(MetricType.YES_NO)) {
            return true;
        }
        return false;
    }

    public void setYesNoType(boolean yesNoType) {
        return;
    }

    public String getEvaluationInstruction() {
        return evaluationInstruction;
    }

    public void setEvaluationInstruction(String evaluationInstruction) {
        this.evaluationInstruction = evaluationInstruction;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(Date evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public Integer getSituationId() {
        return situationId;
    }

    public void setSituationId(Integer situationId) {
        this.situationId = situationId;
    }

    /**
     * @return the evidences
     */
    public List<EvidenceDTO> getEvidences() {
        if (evidences == null) {
            evidences = new ArrayList<EvidenceDTO>();
        }
        return evidences;
    }

    /**
     * @param evidences the evidences to set
     */
    public void setEvidences(List<EvidenceDTO> evidences) {
        this.evidences = evidences;
    }

    /**
     * @return the metricResult
     */
    public Integer getMetricResult() {
        return metricResult;
    }

    /**
     * @param metricResult the metricResult to set
     */
    public void setMetricResult(Integer metricResult) {
        this.metricResult = metricResult;
    }

    public int compareTo(ObservedMetricResultDTO o) {
        return this.getObservedMetricId() - o.getObservedMetricId();
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

    public String getEvidencePrePath() {
        return evidencePrePath;
    }

    public void setEvidencePrePath(String evidencePrePath) {
        this.evidencePrePath = evidencePrePath;
    }

    public String getSitutionTemplateName() {
        return situtionTemplateName;
    }

    public void setSitutionTemplateName(String situtionTemplateName) {
        this.situtionTemplateName = situtionTemplateName;
    }
}
