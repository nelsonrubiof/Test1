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
 * SituationMetricExtractionRequest.java
 *
 * Created on 08-04-2009, 12:35:50 PM
 *
 */


package com.scopix.periscope.extractionmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class SituationMetricExtractionRequest extends BusinessObject {

    @ManyToOne
    private EvidenceExtractionRequest evidenceExtractionRequest;

    private Integer situationTemplateId;
    
    private Integer metricTemplateId;

    public EvidenceExtractionRequest getEvidenceExtractionRequest() {
        return evidenceExtractionRequest;
    }

    public void setEvidenceExtractionRequest(EvidenceExtractionRequest evidenceExtractionRequest) {
        this.evidenceExtractionRequest = evidenceExtractionRequest;
    }

    public Integer getSituationTemplateId() {
        return situationTemplateId;
    }

    public void setSituationTemplateId(Integer situationTemplateId) {
        this.situationTemplateId = situationTemplateId;
    }

    public Integer getMetricTemplateId() {
        return metricTemplateId;
    }

    public void setMetricTemplateId(Integer metricTemplateId) {
        this.metricTemplateId = metricTemplateId;
    }
}