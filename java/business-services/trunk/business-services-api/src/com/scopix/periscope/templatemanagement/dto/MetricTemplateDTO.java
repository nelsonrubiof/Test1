/**
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
 *  MetricTemplateDTO.java
 * 
 *  Created on 23-09-2010, 12:28:29 PM
 * 
 */
package com.scopix.periscope.templatemanagement.dto;

/**
 *
 * @author nelson
 */
public class MetricTemplateDTO {

    private Integer id;
    private String evaluationInstruction;
    private String description;
    private String name;
    private String operatorDescription;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getOperatorDescription() {
        return operatorDescription;
    }

    public void setOperatorDescription(String operatorDescription) {
        this.operatorDescription = operatorDescription;
    }
}
