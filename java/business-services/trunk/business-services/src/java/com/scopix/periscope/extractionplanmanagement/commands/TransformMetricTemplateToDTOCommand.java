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
 *  TransformMetricTemplateToDTOCommand.java
 * 
 *  Created on 27-09-2010, 05:47:47 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.templatemanagement.MetricTemplate;
import com.scopix.periscope.templatemanagement.dto.MetricTemplateDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class TransformMetricTemplateToDTOCommand {

    public List<MetricTemplateDTO> execute(List<MetricTemplate> metricTemplates) {
        List<MetricTemplateDTO> metricTemplateDTOs = new ArrayList<MetricTemplateDTO>();
        for (MetricTemplate metricTemplate : metricTemplates) {
            MetricTemplateDTO dto  = new MetricTemplateDTO();
            dto.setId(metricTemplate.getId());
            dto.setName(metricTemplate.getName());
            dto.setDescription(metricTemplate.getDescription());
            dto.setEvaluationInstruction(metricTemplate.getEvaluationInstruction());
            dto.setOperatorDescription(metricTemplate.getOperatorDescription());
            metricTemplateDTOs.add(dto);
        }
        return metricTemplateDTOs;
    }
}
