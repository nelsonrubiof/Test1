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
 *  TransformExtractionPlanCustomizingToDTOCommand.java
 * 
 *  Created on 21-09-2010, 06:31:10 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanMetric;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanCustomizingDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class TransformExtractionPlanCustomizingsToDTOCommand {

    public static final String EDITION = "EDITION";
    public static final String SENT = "SENT";

    public List<ExtractionPlanCustomizingDTO> execute(List<ExtractionPlanCustomizing> customizings) {
        List<ExtractionPlanCustomizingDTO> extractionPlanCustomizingDTOs = new ArrayList<ExtractionPlanCustomizingDTO>();
        for (ExtractionPlanCustomizing epc : customizings) {
            ExtractionPlanCustomizingDTO dto = new ExtractionPlanCustomizingDTO();
            dto.setActive(epc.isActive());
            dto.setAreaTypeId(epc.getAreaType().getId());
            dto.setAreaType(epc.getAreaType().getDescription());
            dto.setId(epc.getId());
            dto.setOneEvaluation(epc.isOneEvaluation());
            dto.setSituationTemplateId(epc.getSituationTemplate().getId());
            dto.setStoreId(epc.getStore().getId());
            dto.setStatus(epc.isActive() == null ? EDITION : SENT);
            dto.setSend(isSend(epc));
            dto.setPriorization(epc.getPriorization());
            dto.setRandomCamera(epc.isRandomCamera());
            extractionPlanCustomizingDTOs.add(dto);
        }
        return extractionPlanCustomizingDTOs;
    }

    private Boolean isSend(ExtractionPlanCustomizing epc) {
        boolean ret = true;
        ret = ret && !epc.getExtractionPlanMetrics().isEmpty();
        //verificamos que todos los epm tengan almenos un evidence porivder
        for (ExtractionPlanMetric epm : epc.getExtractionPlanMetrics()) {
            ret = ret && !epm.getEvidenceProviders().isEmpty();
        }
        ret = ret && !epc.getExtractionPlanRanges().isEmpty();
        ret = ret && (epc.isActive() == null);
        return ret;
    }
}
