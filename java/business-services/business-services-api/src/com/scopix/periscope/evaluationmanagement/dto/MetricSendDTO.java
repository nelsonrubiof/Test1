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
 * MetricSendDTO.java
 *
 * Created on 29-05-2009, 04:48:32 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class MetricSendDTO implements Comparable<MetricSendDTO> {

    private List<EvidenceSendDTO> evidences;
    private String type;
    private String description;
    private String evalInstruction;
    private String name;
    private String operatorObservation;
    private Integer metricId;
    private boolean multiple;
    private Integer order;
    
    private List<EvidenceEvaluationDTO> evaluationDTOs;
    
    private static Comparator<MetricSendDTO> comparatorByOrder;

    public static Comparator<MetricSendDTO> getComparatorByOrder() {
        comparatorByOrder = new Comparator<MetricSendDTO>() {

            public int compare(MetricSendDTO o1, MetricSendDTO o2) {
                return o1.getOrder().compareTo(o2.getOrder());
            }
        };

        return comparatorByOrder;
    }

    public static void setComparatorByOrder(Comparator<MetricSendDTO> aComparatorByOrder) {
        comparatorByOrder = aComparatorByOrder;
    }

    /**
     * @return the evidences
     */
    public List<EvidenceSendDTO> getEvidences() {
        if (evidences == null) {
            evidences = new ArrayList<EvidenceSendDTO>();
        }
        return evidences;
    }

    /**
     * @param evidences the evidences to set
     */
    public void setEvidences(List<EvidenceSendDTO> evidences) {
        this.evidences = evidences;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the evalInstructioin
     */
    public String getEvalInstruction() {
        return evalInstruction;
    }

    /**
     * @param evalInstructioin the evalInstructioin to set
     */
    public void setEvalInstruction(String evalInstruction) {
        this.evalInstruction = evalInstruction;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the operatorObservation
     */
    public String getOperatorObservation() {
        return operatorObservation;
    }

    /**
     * @param operatorObservation the operatorObservation to set
     */
    public void setOperatorObservation(String operatorObservation) {
        this.operatorObservation = operatorObservation;
    }

    /**
     * @return the metricId
     */
    public Integer getMetricId() {
        return metricId;
    }

    /**
     * @param metricId the metricId to set
     */
    public void setMetricId(Integer metricId) {
        this.metricId = metricId;
    }

    /**
     * @return the multiple
     */
    public boolean isMultiple() {
        return multiple;
    }

    /**
     * @param multiple the multiple to set
     */
    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    /**
     * @return the order
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(Integer order) {
        this.order = order;
    }

    public int compareTo(MetricSendDTO o) {
        return this.getMetricId() - o.getMetricId();
    }

    /**
     * @return the evaluationDTOs
     */
    public List<EvidenceEvaluationDTO> getEvaluationDTOs() {
        if (evaluationDTOs == null) {
            evaluationDTOs = new ArrayList<EvidenceEvaluationDTO>();
        }
        return evaluationDTOs;
    }

    /**
     * @param evaluationDTOs the evaluationDTOs to set
     */
    public void setEvaluationDTOs(List<EvidenceEvaluationDTO> evaluationDTOs) {
        this.evaluationDTOs = evaluationDTOs;
    }
}
