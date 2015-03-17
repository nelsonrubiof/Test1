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
 *  CopyExtractionPlanRangerCompletoInDays.java
 * 
 *  Created on 06-10-2010, 10:02:23 AM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class CopyExtractionPlanRangeInDaysCommand {

    private static Logger log = Logger.getLogger(CopyExtractionPlanRangeInDaysCommand.class);

    /**
     * copia los Rangos de un dia particular en una lista de dias en el EPC recibido
     */
    public List<ExtractionPlanRange> execute(ExtractionPlanCustomizing epc, List<ExtractionPlanRange> rangosDay,
            List<Integer> days) throws ScopixException {
        List<ExtractionPlanRange> rangesGenerados = new ArrayList<ExtractionPlanRange>();
        try {
            for (Integer day : days) {
                for (ExtractionPlanRange range : rangosDay) {
                    ExtractionPlanRange newRange = (ExtractionPlanRange) range.clone();
                    newRange.setDayOfWeek(day);
                    newRange.setExtractionPlanCustomizing(epc);
                    rangesGenerados.add(newRange);
                }
            }
        } catch (CloneNotSupportedException e) {
            log.error("Error " + e, e);
            throw new ScopixException("ERROR_CLON_RANGE"); //no es posible copiar rango " + e);
        }
        return rangesGenerados;
    }
}
