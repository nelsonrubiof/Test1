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
 *  TransformExtractionPlanRangeDetailToDTOCommand.java
 * 
 *  Created on 28-09-2010, 05:33:03 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRangeDetail;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanRangeDetailDTO;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 *
 * @author nelson
 */
public class TransformExtractionPlanRangeDetailToDTOCommand {

    public List<ExtractionPlanRangeDetailDTO> execute(List<ExtractionPlanRangeDetail> planRangeDetails) {
        List<ExtractionPlanRangeDetailDTO> detailDTOs = new ArrayList<ExtractionPlanRangeDetailDTO>();
        for (ExtractionPlanRangeDetail detail : planRangeDetails) {
            ExtractionPlanRangeDetailDTO dto = new ExtractionPlanRangeDetailDTO();
            dto.setId(detail.getId());
            dto.setTimeSample(DateFormatUtils.format(detail.getTimeSample(), "HH:mm"));
            dto.setExtractionPlanRangeId(detail.getExtractionPlanRange().getId());
            detailDTOs.add(dto);
        }
        return detailDTOs;
    }
}
