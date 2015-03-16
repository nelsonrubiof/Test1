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
 *  ObservedSituationFinishedDTO.java
 * 
 *  Created on 22-11-2011, 05:09:41 PM
 * 
 */
package com.scopix.periscope.qualitycontrol.dto;

/**
 *
 * @author nelson
 */
public class ObservedSituationFinishedDTO {

    private Integer observedSituationId;
    private String product;
    private String evidenceDate;    
    private String evaluationUser;
    private String situationTemplateName;

    public Integer getObservedSituationId() {
        return observedSituationId;
    }

    public void setObservedSituationId(Integer observedSituationId) {
        this.observedSituationId = observedSituationId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getEvidenceDate() {
        return evidenceDate;
    }

    public void setEvidenceDate(String evidenceDate) {
        this.evidenceDate = evidenceDate;
    }

    public String getEvaluationUser() {
        return evaluationUser;
    }

    public void setEvaluationUser(String evaluationUser) {
        this.evaluationUser = evaluationUser;
    }

    public String getSituationTemplateName() {
        return situationTemplateName;
    }

    public void setSituationTemplateName(String situationTemplateName) {
        this.situationTemplateName = situationTemplateName;
    }

}
