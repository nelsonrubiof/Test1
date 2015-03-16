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
 *  CleanExtractionPlanMetricsCommand.java
 * 
 *  Created on 21-09-2010, 10:41:04 AM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAO;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAOImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author nelson
 */
public class CleanExtractionPlanMetricsCommand {

    private ExtractionPlanCustomizingDAO dao;

    public ExtractionPlanCustomizingDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanCustomizingDAOImpl.class);
        }
        return dao;
    }

    public void setDao(ExtractionPlanCustomizingDAO dao) {
        this.dao = dao;
    }

    public void execute(ExtractionPlanCustomizing customizing) throws ScopixException {
        getDao().cleanExtractionPlanMetrics(customizing);
    }
}
