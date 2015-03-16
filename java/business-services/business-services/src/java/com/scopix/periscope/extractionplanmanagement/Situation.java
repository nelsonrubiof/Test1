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
 * Situation.java
 *
 * Created on 27-03-2008, 01:37:54 PM
 *
 */
package com.scopix.periscope.extractionplanmanagement;

import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Situation extends BusinessObject {

    @Lob
    private String description;
    @OneToMany(mappedBy = "situation", fetch = FetchType.LAZY)
    private List<ObservedSituation> observedSituations;
    @ManyToOne
    private SituationTemplate situationTemplate;
    @OneToMany(mappedBy = "situation", fetch = FetchType.LAZY)
    private List<Metric> metrics;
    private Integer processId;

    public SituationTemplate getSituationTemplate() {
        return situationTemplate;
    }

    public void setSituationTemplate(SituationTemplate situationTemplate) {
        this.situationTemplate = situationTemplate;
    }

    public List<ObservedSituation> getObservedSituations() {
        if (observedSituations == null) {
            observedSituations = new ArrayList<ObservedSituation>();
        }
        return observedSituations;
    }

    public void setObservedSituations(List<ObservedSituation> observedSituations) {
        this.observedSituations = observedSituations;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
