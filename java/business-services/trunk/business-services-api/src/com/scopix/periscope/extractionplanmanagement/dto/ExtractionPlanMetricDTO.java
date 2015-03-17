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
 *  ExtractionPlanMetricDTO.java
 * 
 *  Created on 27-09-2010, 06:26:35 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.dto;

import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceProviderDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class ExtractionPlanMetricDTO {

    private Integer id;
    private Integer metricTemplateId;
    private String metricVariableName;
    private Integer evaluationOrder;
    private List<EvidenceProviderDTO> evidenceProviderDTOs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMetricTemplateId() {
        return metricTemplateId;
    }

    public void setMetricTemplateId(Integer metricTemplateId) {
        this.metricTemplateId = metricTemplateId;
    }

    public String getMetricVariableName() {
        return metricVariableName;
    }

    public void setMetricVariableName(String metricVariableName) {
        this.metricVariableName = metricVariableName;
    }

    public Integer getEvaluationOrder() {
        return evaluationOrder;
    }

    public void setEvaluationOrder(Integer evaluationOrder) {
        this.evaluationOrder = evaluationOrder;
    }

    public List<EvidenceProviderDTO> getEvidenceProviderDTOs() {
        if (evidenceProviderDTOs == null){
            evidenceProviderDTOs = new ArrayList<EvidenceProviderDTO>();
        }
        return evidenceProviderDTOs;
    }

    public void setEvidenceProviderDTOs(List<EvidenceProviderDTO> evidenceProviderDTOs) {
        this.evidenceProviderDTOs = evidenceProviderDTOs;
    }
}
