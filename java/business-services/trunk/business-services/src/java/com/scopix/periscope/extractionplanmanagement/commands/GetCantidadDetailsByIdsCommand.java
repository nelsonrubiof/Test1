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
 *  GetCantidadDetailsCommand.java
 *
 *  Created on 03-12-2010, 10:45:32 AM
 *
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAO;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAOImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo
 */
public class GetCantidadDetailsByIdsCommand {
    private static Logger log = Logger.getLogger(GetCantidadDetailsCommand.class);
    private ExtractionPlanCustomizingDAO dao;

    public int execute(List<Integer> epcIds, Integer storeId) {
        int cant = 0;
        try {
            cant = getDao().getCantidadDetailesForEpcStoreByIds(epcIds, storeId);
        } catch (ScopixException e) {
            log.error("No es posible recuperar cantidad detalles " + e, e);
        }
        return cant;
    }

    public ExtractionPlanCustomizingDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanCustomizingDAOImpl.class);
        }
        return dao;
    }

    public void setDao(ExtractionPlanCustomizingDAO dao) {
        this.dao = dao;
    }
}
