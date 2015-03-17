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
 *  GenerateDetalleSolucitudDTOToEPCCommand.java
 * 
 *  Created on 08-10-2010, 10:54:27 AM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.periscopefoundation.util.SortUtil;
import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRangeDetail;
import com.scopix.periscope.extractionplanmanagement.dto.DetalleSolicitudDTO;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 *
 * @author nelson
 */
public class GenerateDetalleSolucitudDTOFromEPCCommand {

    public List<DetalleSolicitudDTO> execute(ExtractionPlanCustomizing epc) {

        Store store = epc.getStore();
        SituationTemplate situationTemplate = epc.getSituationTemplate();
        AreaType areaType = epc.getAreaType();
        List<DetalleSolicitudDTO> solicitudDTOs = new ArrayList<DetalleSolicitudDTO>();
        List<ExtractionPlanRange> planRanges = epc.getExtractionPlanRanges();

        //ordenamos los rangos por dia horaInicio
        LinkedHashMap<String, Boolean> colsSort = new LinkedHashMap<String, Boolean>();
        colsSort.put("dayOfWeek", Boolean.FALSE);
        colsSort.put("initialTime", Boolean.FALSE);
        SortUtil.sortByColumn(colsSort, planRanges);
        //por cada rango ordenamos sus detalles por hora
        colsSort.clear();
        colsSort.put("timeSample", Boolean.FALSE);
        for (ExtractionPlanRange planRange : planRanges) {
            List<ExtractionPlanRangeDetail> details = planRange.getExtractionPlanRangeDetails();
            SortUtil.sortByColumn(colsSort, details);
            for (ExtractionPlanRangeDetail detail : details) {
                //aqui generamos el dto para enviarlo
                DetalleSolicitudDTO solicitudDTO = new DetalleSolicitudDTO();
                solicitudDTO.setStore(store.getDescription());
                solicitudDTO.setSituationTemplate(situationTemplate.getName());
                solicitudDTO.setArea(areaType.getDescription());
                solicitudDTO.setDay(planRange.getDayOfWeek());
                solicitudDTO.setDate(DateFormatUtils.format(detail.getTimeSample(), "HH:mm"));
                solicitudDTO.setDuration(planRange.getDuration().toString());
                solicitudDTOs.add(solicitudDTO);
            }
        }

        return solicitudDTOs;

    }
}
