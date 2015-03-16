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
 *  TransformExtractionPlanCustomizingDatosGeneraleToDTOCommand.java
 * 
 *  Created on 28-09-2010, 10:38:27 AM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.Sensor;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceProviderDTO;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanMetric;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanCustomizingDTO;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanMetricDTO;
import com.scopix.periscope.periscopefoundation.util.SortUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author nelson
 */
public class TransformEPCGeneralToDTOCommand {

    public static final String SENT = "SENT";
    public static final String EDITION = "EDITION";

    public ExtractionPlanCustomizingDTO execute(ExtractionPlanCustomizing epc) {
        ExtractionPlanCustomizingDTO dto = new ExtractionPlanCustomizingDTO();
        dto.setOneEvaluation(epc.isOneEvaluation());
        dto.setAreaTypeId(epc.getAreaType().getId());
        dto.setAreaType(epc.getAreaType().getDescription());
        dto.setId(epc.getId());
        dto.setSituationTemplateId(epc.getSituationTemplate().getId());
        dto.setStoreId(epc.getStore().getId());
        dto.setPriorization(epc.getPriorization());
        dto.setRandomCamera(epc.isRandomCamera());
        if (epc.isActive() == null) {
            dto.setStatus(EDITION);
        } else if (epc.isActive().equals(Boolean.TRUE)) {
            dto.setStatus(SENT);
        }

        dto.setSensorIds(new ArrayList<Integer>());
        for (Sensor sensor : epc.getSensors()) {
            dto.getSensorIds().add(sensor.getId());
        }

        dto.setExtractionPlanMetricDTOs(new ArrayList<ExtractionPlanMetricDTO>());
        List<ExtractionPlanMetric> metrics = epc.getExtractionPlanMetrics();
        LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
        cols.put("evaluationOrder", Boolean.FALSE);
        SortUtil.sortByColumn(cols, metrics);
        for (ExtractionPlanMetric metric : metrics) {
            ExtractionPlanMetricDTO metricDTO = new ExtractionPlanMetricDTO();
            metricDTO.setId(metric.getId());
            metricDTO.setEvaluationOrder(metric.getEvaluationOrder());
            metricDTO.setMetricTemplateId(metric.getMetricTemplate().getId());
            metricDTO.setMetricVariableName(metric.getMetricVariableName());
            metricDTO.setEvidenceProviderDTOs(new ArrayList<EvidenceProviderDTO>());
            //metricDTO.setProviderIds(new ArrayList<Integer>());            
            for (EvidenceProvider provider : metric.getEvidenceProviders()) {
                EvidenceProviderDTO evidenceProviderDTO = new EvidenceProviderDTO();
                evidenceProviderDTO.setId(provider.getId());
                evidenceProviderDTO.setDescription(provider.getDescription());

                evidenceProviderDTO.setDefinitionData(new HashMap<String, String>());
                evidenceProviderDTO.setProviderType(provider.getEvidenceProviderType().getDescription());
                if (!provider.getAreas().isEmpty()) {
                    evidenceProviderDTO.setAreaType(provider.getAreas().get(0).getAreaType().getDescription());
                }
                metricDTO.getEvidenceProviderDTOs().add(evidenceProviderDTO);
//                metricDTO.getProviderIds().add(provider.getId());
            }
            dto.getExtractionPlanMetricDTOs().add(metricDTO);
        }

        return dto;
    }
}
