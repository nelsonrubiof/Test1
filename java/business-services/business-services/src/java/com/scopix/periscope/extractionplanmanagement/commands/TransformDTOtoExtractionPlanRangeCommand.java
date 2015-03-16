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
 *  TransformDTOtoExtractionPlanRangeCommand.java
 * 
 *  Created on 30-09-2010, 01:15:54 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRangeType;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanRangeDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.text.ParseException;
import org.apache.commons.lang.time.DateUtils;

/**
 * TransForma un DTO en ExtractionPlanRange
 * @author nelson
 */
public class TransformDTOtoExtractionPlanRangeCommand {

    private GenericDAO dao;

    public ExtractionPlanRange execute(ExtractionPlanCustomizing epc, ExtractionPlanRangeDTO planRangeDTO)
            throws ScopixException {

        ExtractionPlanRange planRange = new ExtractionPlanRange();
//        if (planRangeDTO.getId() != null && planRangeDTO.getId() > 0) {
//            //si existe los recuperamos
//            planRange = getDao().get(planRangeDTO.getId(), ExtractionPlanRange.class);
//        } else {
//            //si no existe lo generamos
//            planRange = new ExtractionPlanRange();
//            planRange.setExtractionPlanCustomizing(epc);
//        }
//
        planRange.setId(planRangeDTO.getId());
        planRange.setDayOfWeek(planRangeDTO.getDayOfWeek());
        planRange.setDuration(planRangeDTO.getDuration());
        planRange.setExtractionPlanCustomizing(epc);

        try {
            planRange.setEndTime(DateUtils.parseDate(planRangeDTO.getEndTime(), new String[]{"HH:mm"}));
            planRange.setInitialTime(DateUtils.parseDate(planRangeDTO.getInitialTime(), new String[]{"HH:mm"}));
        } catch (ParseException e) {
            throw new ScopixException("Error parseando fechas se esperaba formato HH:mm", e);
        }
        planRange.setFrecuency(planRangeDTO.getFrecuency());
        planRange.setSamples(planRangeDTO.getSamples());
        if (ExtractionPlanRangeType.AUTOMATIC_EVIDENCE.toString().equals(planRangeDTO.getType())) {
            planRange.setExtractionPlanRangeType(ExtractionPlanRangeType.AUTOMATIC_EVIDENCE);
        } else if (ExtractionPlanRangeType.FIXED.toString().equals(planRangeDTO.getType())) {
            planRange.setExtractionPlanRangeType(ExtractionPlanRangeType.FIXED);
        } else if (ExtractionPlanRangeType.RANDOM.toString().equals(planRangeDTO.getType())) {
            planRange.setExtractionPlanRangeType(ExtractionPlanRangeType.RANDOM);
        } else if (ExtractionPlanRangeType.REAL_RANDOM.toString().equals(planRangeDTO.getType())) {
            planRange.setExtractionPlanRangeType(ExtractionPlanRangeType.REAL_RANDOM);
        }


        return planRange;

    }

    public GenericDAO getDao() {
        if (dao == null) {
            dao = HibernateSupport.getInstance().findGenericDAO();
        }
        return dao;
    }

    public void setDao(GenericDAO dao) {
        this.dao = dao;
    }
}
