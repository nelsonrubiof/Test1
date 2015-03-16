
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
 * PendingEvaluationDTO.java
 *
 * Created on 20-05-2008, 01:16:02 PM
 *
 */
package com.scopix.periscope.queuemanagement.dto;

import com.scopix.periscope.evaluationmanagement.EvaluationQueue;
import com.scopix.periscope.evaluationmanagement.EvaluationState;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class PendingEvaluationDTO implements Comparable<PendingEvaluationDTO> {

    private Integer pendingEvaluationId;
    private String product; //product.description
    private Integer situationTemplateId;
    private String situationTemplate; //SituationTemplate.name
    private String area; //metric.area.description
    private Integer areaId;
    private String description; //MetricTemplate.description
    private Integer metricTemplateId;
    private String type; //metric.metricType
    private Integer metricId;
    private Date date; //ObservedSituation.observedSituationDate
    private Integer observedSituationId;
    private EvaluationState state;
    private EvaluationQueue queue;
    private boolean checked;
    private Integer position;
    private Integer priority;
    private Date evidenceDate;
    private Integer evidenceId;
    private boolean priorityChangeable;
    private String store;
    private Integer storeId;
    private String userName;
    private static Comparator<PendingEvaluationDTO> comparatorByPriority;
    private static Comparator<PendingEvaluationDTO> comparatorByChecked;

    public static Comparator<PendingEvaluationDTO> getComparatorByPriority() {
        comparatorByPriority = new Comparator<PendingEvaluationDTO>() {
            public int compare(PendingEvaluationDTO o1, PendingEvaluationDTO o2) {
                return o1.getPriority().compareTo(o2.getPriority());
            }
        };

        return comparatorByPriority;
    }

    public static void setComparatorByPriority(Comparator<PendingEvaluationDTO> aComparatorByPriority) {
        comparatorByPriority = aComparatorByPriority;
    }

    /**
     * Compare this instance with a different instance of this class by id
     *
     * @param o of type PendingEvaluationDTO
     * @return
     */
    public int compareTo(PendingEvaluationDTO o) {
        return this.getPendingEvaluationId().compareTo(o.getPendingEvaluationId());
    }

    public static Comparator<PendingEvaluationDTO> getComparatorByChecked() {
        comparatorByChecked = new Comparator<PendingEvaluationDTO>() {
            public int compare(PendingEvaluationDTO o1, PendingEvaluationDTO o2) {
                //CHECKSTYLE:OFF
                return (o1.isChecked() && !o2.isChecked()) ? -1 : (o2.isChecked() && !o1.isChecked()) ? 1 : 0;
                //CHECKSTYLE:ON
            }
        };

        return comparatorByChecked;
    }

    public static void setComparatorByChecked(Comparator<PendingEvaluationDTO> aComparatorByChecked) {
        comparatorByChecked = aComparatorByChecked;
    }

    public Integer getPendingEvaluationId() {
        return pendingEvaluationId;
    }

    public void setPendingEvaluationId(Integer pendingEvaluationId) {
        this.pendingEvaluationId = pendingEvaluationId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getSituationTemplateId() {
        return situationTemplateId;
    }

    public void setSituationTemplateId(Integer situationTemplateId) {
        this.situationTemplateId = situationTemplateId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMetricTemplateId() {
        return metricTemplateId;
    }

    public void setMetricTemplateId(Integer metricTemplateId) {
        this.metricTemplateId = metricTemplateId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMetricId() {
        return metricId;
    }

    public void setMetricId(Integer metricId) {
        this.metricId = metricId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getObservedSituationId() {
        return observedSituationId;
    }

    public void setObservedSituationId(Integer observedSituationId) {
        this.observedSituationId = observedSituationId;
    }

    public EvaluationState getState() {
        return state;
    }

    public void setState(EvaluationState state) {
        this.state = state;
    }

    public EvaluationQueue getQueue() {
        return queue;
    }

    public void setQueue(EvaluationQueue queue) {
        this.queue = queue;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getEvidenceDate() {
        return evidenceDate;
    }

    public void setEvidenceDate(Date evidenceDate) {
        this.evidenceDate = evidenceDate;
    }

    public Integer getEvidenceId() {
        return evidenceId;
    }

    public void setEvidenceId(Integer evidenceId) {
        this.evidenceId = evidenceId;
    }

    public boolean isPriorityChangeable() {
        //CHECKSTYLE:OFF
        return (this.getState() != null) ? this.getState().isPriorityChangeable() : false;
        //CHECKSTYLE:ON
    }

    public void setPriorityChangeable(boolean priorityChangeable) {
        this.priorityChangeable = priorityChangeable;
    }

    /**
     * @return the store
     */
    public String getStore() {
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(String store) {
        this.store = store;
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
     * @return the situationTemplate
     */
    public String getSituationTemplate() {
        return situationTemplate;
    }

    /**
     * @param situationTemplate the situationTemplate to set
     */
    public void setSituationTemplate(String situationTemplate) {
        this.situationTemplate = situationTemplate;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}