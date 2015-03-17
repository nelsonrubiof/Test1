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
 * Metric.java
 *
 * Created on 06-05-2008, 09:00:31 PM
 *
 */
package com.scopix.periscope.extractionplanmanagement;

import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class Metric extends BusinessObject implements Comparable<Metric> {

    @Lob
    private String description;
    @ManyToOne
    private Situation situation;
    @ManyToOne
    private MetricTemplate metricTemplate;
    private Integer metricOrder;
    @ManyToOne
    private Store store;
    @ManyToOne
    private Area area;
    @OneToMany(mappedBy = "metric", fetch = FetchType.LAZY)
    private List<EvidenceRequest> evidenceRequests;
    @OneToMany(mappedBy = "metric", fetch = FetchType.LAZY)
    private List<ObservedMetric> observedMetrics;
//    @ManyToOne
//    private ExtractionPlanCustomizing extractionPlanCustomizing;
    @ManyToOne
    private ExtractionPlanMetric extractionPlanMetric;
    @Lob
    private String metricVariableName;

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public MetricTemplate getMetricTemplate() {
        return metricTemplate;
    }

    public void setMetricTemplate(MetricTemplate metricTemplate) {
        this.metricTemplate = metricTemplate;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<EvidenceRequest> getEvidenceRequests() {
        if (evidenceRequests == null) {
            evidenceRequests = new ArrayList<EvidenceRequest>();
        }
        return evidenceRequests;
    }

    public void setEvidenceRequests(List<EvidenceRequest> evidenceRequests) {
        this.evidenceRequests = evidenceRequests;
    }

    public List<ObservedMetric> getObservedMetrics() {
        if (observedMetrics == null) {
            observedMetrics = new ArrayList<ObservedMetric>();
        }
        return observedMetrics;
    }

    public void setObservedMetrics(List<ObservedMetric> observedMetrics) {
        this.observedMetrics = observedMetrics;
    }

    public Integer getMetricOrder() {
        return metricOrder;
    }

    public void setMetricOrder(Integer metricOrder) {
        this.metricOrder = metricOrder;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public int compareTo(Metric o) {
        return this.getId() - o.getId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the metricVariableName
     */
    public String getMetricVariableName() {
        return metricVariableName;
    }

    /**
     * @param metricVariableName the metricVariableName to set
     */
    public void setMetricVariableName(String metricVariableName) {
        this.metricVariableName = metricVariableName;
    }

    public ExtractionPlanMetric getExtractionPlanMetric() {
        return extractionPlanMetric;
    }

    public void setExtractionPlanMetric(ExtractionPlanMetric extractionPlanMetric) {
        this.extractionPlanMetric = extractionPlanMetric;
    }
}
