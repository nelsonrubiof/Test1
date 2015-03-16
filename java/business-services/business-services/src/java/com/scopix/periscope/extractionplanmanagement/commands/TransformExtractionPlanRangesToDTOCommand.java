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
 *  TransformExtracionPlanRangesToDTOCommand.java
 * 
 *  Created on 28-09-2010, 03:44:28 PM
 * 
 */

package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanRangeDTO;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * Transforma una lista de ExtractionPlanRanges en una lista de DTOs
 * @author nelson
 */
public class TransformExtractionPlanRangesToDTOCommand {

    public List<ExtractionPlanRangeDTO> execute(List<ExtractionPlanRange> planRanges) {
        List<ExtractionPlanRangeDTO> planRangeDTOs = new ArrayList<ExtractionPlanRangeDTO>();
        for (ExtractionPlanRange planRange : planRanges) {
            ExtractionPlanRangeDTO dto = new ExtractionPlanRangeDTO();
            dto.setId(planRange.getId());
            dto.setDayOfWeek(planRange.getDayOfWeek());
            dto.setDuration(planRange.getDuration());
            dto.setEndTime(DateFormatUtils.format(planRange.getEndTime(), "HH:mm"));
            dto.setFrecuency(planRange.getFrecuency());
            dto.setInitialTime(DateFormatUtils.format(planRange.getInitialTime(), "HH:mm"));
            dto.setSamples(planRange.getSamples());
            dto.setType(planRange.getExtractionPlanRangeType().getName());
            planRangeDTOs.add(dto);
        }
        return planRangeDTOs;
    }

}
