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
 *  TransformExtractionPlanRangeToDTOCommand.java
 * 
 *  Created on 07-10-2010, 05:51:01 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanRangeDTO;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * Transforma un ExtractionPlanRange en DTO
 * @author nelson
 */
public class TransformExtractionPlanRangeToDTOCommand {

    public ExtractionPlanRangeDTO execute(ExtractionPlanRange planRange) {
        ExtractionPlanRangeDTO dto = new ExtractionPlanRangeDTO();
        dto.setId(planRange.getId());
        dto.setDayOfWeek(planRange.getDayOfWeek());
        dto.setDuration(planRange.getDuration());
        dto.setEndTime(DateFormatUtils.format(planRange.getEndTime(), "HH:mm"));
        dto.setFrecuency(planRange.getFrecuency());
        dto.setInitialTime(DateFormatUtils.format(planRange.getInitialTime(), "HH:mm"));
        dto.setSamples(planRange.getSamples());
        dto.setType(planRange.getExtractionPlanRangeType().getName());
        return dto;
    }
}
