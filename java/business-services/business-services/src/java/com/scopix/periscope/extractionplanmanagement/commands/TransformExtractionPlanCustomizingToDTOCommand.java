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
 *  Created on 23-09-2010, 03:35:14 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanCustomizingDTO;

/**
 *
 * @author nelson
 */
public class TransformExtractionPlanCustomizingToDTOCommand {

    public static final String EDITION = "EDITION";
    public static final String SENT = "SENT";

    public ExtractionPlanCustomizingDTO execute(ExtractionPlanCustomizing epc) {
        ExtractionPlanCustomizingDTO dto = new ExtractionPlanCustomizingDTO();
        dto.setAreaTypeId(epc.getAreaType().getId());
        dto.setAreaType(epc.getAreaType().getDescription());
        dto.setId(epc.getId());
        dto.setSituationTemplateId(epc.getSituationTemplate().getId());
        dto.setStoreId(epc.getStore().getId());
        dto.setStatus(EDITION);
        dto.setPriorization(epc.getPriorization());
        dto.setRandomCamera(epc.isRandomCamera());
        return dto;
    }
}
