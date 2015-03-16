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
 * MetricTemplate.java
 *
 * Created on 06-05-2008, 01:01:57 PM
 *
 */
package com.scopix.periscope.templatemanagement;

import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class MetricTemplate extends BusinessObject implements Comparable<MetricTemplate> {

    @ManyToMany(targetEntity = SituationTemplate.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
        CascadeType.MERGE})
    @JoinTable(name = "rel_metric_template_situation_template", joinColumns = {@JoinColumn(name = "metric_template_id")},
    inverseJoinColumns = {@JoinColumn(name = "situation_template_id")})
    private List<SituationTemplate> situationTemplates;
    private String evidenceSpringBeanEvaluatorName;
    private String metricSpringBeanEvaluatorName;
    @Enumerated(EnumType.STRING)
    private EvidenceType evidenceTypeElement;
    @Enumerated(EnumType.STRING)
    private MetricType metricTypeElement;
    @Enumerated(EnumType.STRING)
    private YesNoType yesNoType;
    @OneToMany(mappedBy = "metricTemplate", fetch = FetchType.LAZY)
    private List<Metric> metrics;
    private String evaluationInstruction;
    private String description;
    private String name;
    private String operatorDescription;
    private static Comparator<MetricTemplate> comparatorByDescription;

    public List<SituationTemplate> getSituationTemplates() {
        if (situationTemplates == null) {
            situationTemplates = new ArrayList<SituationTemplate>();
        }
        return situationTemplates;
    }

    public void setSituationTemplates(List<SituationTemplate> situationTemplate) {
        this.situationTemplates = situationTemplate;
    }

    public String getEvidenceSpringBeanEvaluatorName() {
        return evidenceSpringBeanEvaluatorName;
    }

    public void setEvidenceSpringBeanEvaluatorName(String evidenceSpringBeanEvaluatorName) {
        this.evidenceSpringBeanEvaluatorName = evidenceSpringBeanEvaluatorName;
    }

    public String getMetricSpringBeanEvaluatorName() {
        return metricSpringBeanEvaluatorName;
    }

    public void setMetricSpringBeanEvaluatorName(String metricSpringBeanEvaluatorName) {
        this.metricSpringBeanEvaluatorName = metricSpringBeanEvaluatorName;
    }

    public EvidenceType getEvidenceTypeElement() {
        return evidenceTypeElement;
    }

    public void setEvidenceTypeElement(EvidenceType evidenceTypeElement) {
        this.evidenceTypeElement = evidenceTypeElement;
    }

    public MetricType getMetricTypeElement() {
        return metricTypeElement;
    }

    public void setMetricTypeElement(MetricType metricTypeElement) {
        this.metricTypeElement = metricTypeElement;
    }

    public List<Metric> getMetrics() {
        if (metrics == null) {
            metrics = new ArrayList<Metric>();
        }
        return metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }

    public String getEvaluationInstruction() {
        return evaluationInstruction;
    }

    public void setEvaluationInstruction(String evaluationInstruction) {
        this.evaluationInstruction = evaluationInstruction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public YesNoType getYesNoType() {
        return this.yesNoType;
    }

    public void setYesNoType(YesNoType yesNoType) {
        this.yesNoType = yesNoType;
    }

    public int compareTo(MetricTemplate o) {
        return this.getId() - o.getId();
    }

    public static Comparator<MetricTemplate> getComparatorByDescription() {
        comparatorByDescription = new Comparator<MetricTemplate>() {

            public int compare(MetricTemplate o1, MetricTemplate o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        };
        return comparatorByDescription;
    }

    /**
     * @return the operatorDescription
     */
    public String getOperatorDescription() {
        return operatorDescription;
    }

    /**
     * @param operatorDescription the operatorDescription to set
     */
    public void setOperatorDescription(String operatorDescription) {
        this.operatorDescription = operatorDescription;
    }
}
