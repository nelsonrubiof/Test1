/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionplanmanagement;

import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author nelson
 */
@Entity
public class ExtractionPlanMetric extends BusinessObject implements Cloneable {
    
    @ManyToOne
    private ExtractionPlanCustomizing extractionPlanCustomizing;
    @ManyToOne
    private MetricTemplate metricTemplate;
    @Lob
    private String metricVariableName;
    /**
     * Esta lista se genera al monento de crear los EvidenceRequest a partir del ExtractionPlanCustomizing
     */
    @OneToMany(mappedBy = "extractionPlanMetric", fetch = FetchType.LAZY)
    private List<Metric> metrics;
    /**
     * Orden en el cual son presentados al operador para ser evaluado
     */
    private Integer evaluationOrder;
    @ManyToMany(targetEntity = EvidenceProvider.class, fetch = FetchType.LAZY)
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinTable(name = "rel_extraction_plan_metric_evidence_provider",
        joinColumns = {
            @JoinColumn(name = "extraction_plan_metric_id")},
        inverseJoinColumns = {
            @JoinColumn(name = "evidence_provider_id")})
    private List<EvidenceProvider> evidenceProviders;
    
    public ExtractionPlanCustomizing getExtractionPlanCustomizing() {
        return extractionPlanCustomizing;
    }
    
    public void setExtractionPlanCustomizing(ExtractionPlanCustomizing extractionPlanCustomizing) {
        this.extractionPlanCustomizing = extractionPlanCustomizing;
    }
    
    public MetricTemplate getMetricTemplate() {
        return metricTemplate;
    }
    
    public void setMetricTemplate(MetricTemplate metricTemplate) {
        this.metricTemplate = metricTemplate;
    }
    
    public String getMetricVariableName() {
        return metricVariableName;
    }
    
    public void setMetricVariableName(String metricVariableName) {
        this.metricVariableName = metricVariableName;
    }
    
    public List<Metric> getMetrics() {
        return metrics;
    }
    
    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }
    
    public Integer getEvaluationOrder() {
        return evaluationOrder;
    }
    
    public void setEvaluationOrder(Integer evaluationOrder) {
        this.evaluationOrder = evaluationOrder;
    }
    
    public List<EvidenceProvider> getEvidenceProviders() {
        if (evidenceProviders == null) {
            evidenceProviders = new ArrayList<EvidenceProvider>();
        }
        verifyProvider();
        return evidenceProviders;
    }
    
    public void setEvidenceProviders(List<EvidenceProvider> evidenceProviders) {
        this.evidenceProviders = evidenceProviders;
    }
    
    public void addEvidenceProvider(EvidenceProvider ep) {
        boolean exist = false;
        for (EvidenceProvider thisProvider : getEvidenceProviders()) {
            if (thisProvider.getId().equals(ep.getId())) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            getEvidenceProviders().add(ep);
        }
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        ExtractionPlanMetric cloned = (ExtractionPlanMetric) super.clone();
        cloned.setId(null);
        cloned.setMetrics(null);
        cloned.setEvidenceProviders(new ArrayList<EvidenceProvider>());
        for (EvidenceProvider ep : this.getEvidenceProviders()) {
            cloned.addEvidenceProvider(ep);
        }
        return cloned;
    }
    
    private void verifyProvider() {
        //verificamos que este solo 1 vez cada provider
        List<EvidenceProvider> tmp = new ArrayList<EvidenceProvider>();
        if (evidenceProviders != null) {
            for (EvidenceProvider ep : evidenceProviders) {
                boolean exist = false;
                for (EvidenceProvider epTmp : tmp) {
                    if (epTmp.getId().equals(ep.getId())) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    tmp.add(ep);
                }
            }
        }
        setEvidenceProviders(tmp);
    }
}
