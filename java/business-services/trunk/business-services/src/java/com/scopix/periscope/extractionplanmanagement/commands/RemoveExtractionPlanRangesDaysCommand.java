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
 *  RemoveExtractionPlanRangesDaysCommand.java
 * 
 *  Created on 06-10-2010, 09:53:55 AM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAO;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAOImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author nelson
 */
public class RemoveExtractionPlanRangesDaysCommand {

    private GenericDAO dao;
    private ExtractionPlanCustomizingDAO daoEPC;

    public void execute(ExtractionPlanCustomizing epc, List<Integer> days) throws ScopixException {
        Iterator<ExtractionPlanRange> iterRange = epc.getExtractionPlanRanges().iterator();
        List<ExtractionPlanRange> extractionPlanRangesDelete = new ArrayList<ExtractionPlanRange>();
        Set<Integer> idsRangeClear = new HashSet<Integer>();
        while (iterRange.hasNext()) {
            ExtractionPlanRange range = iterRange.next();
            for (Integer day : days) {
                if (day.equals(range.getDayOfWeek())) {
                    //lo eliminamos de base de datos                    
                    //borramos los detalles del rango
                    range.getExtractionPlanRangeDetails().clear();
                    extractionPlanRangesDelete.add(range);
                    idsRangeClear.add(range.getId());
                    iterRange.remove();
                    //getDao().remove(range);
                    break;
                }
            }
        }

        if (idsRangeClear.size() > 0) { //debemos borrar solo si contiene rangos Actualmente
            getDaoEPC().cleanRangeDetailForExtractionPlanRanges(idsRangeClear);
        }
        for (ExtractionPlanRange eprDelete : extractionPlanRangesDelete) {
            getDao().remove(eprDelete);
        }

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

    public ExtractionPlanCustomizingDAO getDaoEPC() {
        if (daoEPC == null) {
            daoEPC = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanCustomizingDAOImpl.class);
        }
        return daoEPC;
    }

    public void setDaoEPC(ExtractionPlanCustomizingDAO daoEPC) {
        this.daoEPC = daoEPC;
    }
}
