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
 * SituationTemplate.java
 *
 * Created on 27-03-2008, 03:46:21 PM
 *
 */
package com.scopix.periscope.templatemanagement;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.extractionplanmanagement.Formula;
import com.scopix.periscope.extractionplanmanagement.Situation;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.queuemanagement.OperatorQueueDetail;

@Entity
public class SituationTemplate extends BusinessObject implements Comparable<SituationTemplate> {

    @ManyToMany(targetEntity = MetricTemplate.class, fetch = FetchType.LAZY, mappedBy = "situationTemplates", cascade = {
        CascadeType.PERSIST, CascadeType.MERGE})
    private List<MetricTemplate> metricTemplate;
    @OneToMany(mappedBy = "situationTemplate", fetch = FetchType.LAZY)
    private List<Situation> situations;
    @Lob
    private String name;
    @ManyToOne
    private Product product;
    @ManyToOne
    private AreaType areaType;
    private Boolean active;
    @Lob
    private String evidenceSpringBeanEvaluatorName;
    @ManyToMany(targetEntity = Formula.class, mappedBy = "situationTemplates", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Formula> formulas;
    /**
     * Se agrega para el manejo de colas por Store/Situacion
     */
    @OneToMany(mappedBy = "situationTemplate")
    private List<OperatorQueueDetail> operatorQueueDetails;
    private boolean live;
    private Integer delayInMinutes;

    public List<Situation> getSituations() {
        if (situations == null) {
            situations = new ArrayList<Situation>();
        }
        return situations;
    }

    public void setSituations(List<Situation> situations) {
        this.situations = situations;
    }

    public List<MetricTemplate> getMetricTemplate() {
        if (metricTemplate == null) {
            metricTemplate = new ArrayList<MetricTemplate>();
        }
        return metricTemplate;
    }

    public void setMetricTemplate(List<MetricTemplate> metricTemplate) {
        this.metricTemplate = metricTemplate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int compareTo(SituationTemplate o) {
        return this.getId() - o.getId();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public AreaType getAreaType() {
        return areaType;
    }

    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }

    /**
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * @return the evidenceSpringBeanEvaluatorName
     */
    public String getEvidenceSpringBeanEvaluatorName() {
        return evidenceSpringBeanEvaluatorName;
    }

    /**
     * @param evidenceSpringBeanEvaluatorName the
     * evidenceSpringBeanEvaluatorName to set
     */
    public void setEvidenceSpringBeanEvaluatorName(String evidenceSpringBeanEvaluatorName) {
        this.evidenceSpringBeanEvaluatorName = evidenceSpringBeanEvaluatorName;
    }

    /**
     * @return the formulas
     */
    public List<Formula> getFormulas() {
        if (formulas == null) {
            formulas = new ArrayList<Formula>();
        }
        return formulas;
    }

    /**
     * @param formulas the formulas to set
     */
    public void setFormulas(List<Formula> formulas) {
        this.formulas = formulas;
    }

    /**
     * @return the operatorQueueDetails
     */
    public List<OperatorQueueDetail> getOperatorQueueDetails() {
        return operatorQueueDetails;
    }

    /**
     * @param operatorQueueDetails the operatorQueueDetails to set
     */
    public void setOperatorQueueDetails(List<OperatorQueueDetail> operatorQueueDetails) {
        this.operatorQueueDetails = operatorQueueDetails;
    }

    /**
     * @return the live
     */
    public boolean isLive() {
        return live;
    }

    /**
     * @return the live
     */
    public Boolean getLive() {
        return live;
    }

    /**
     * @param live the live to set
     */
    public void setLive(Boolean live) {
        this.live = live;
    }

    /**
     * @return the delayInMinutes
     */
    public Integer getDelayInMinutes() {
        return delayInMinutes;
    }

    /**
     * @param delayInMinutes the delayInMinutes to set
     */
    public void setDelayInMinutes(Integer delayInMinutes) {
        this.delayInMinutes = delayInMinutes;
    }

}
