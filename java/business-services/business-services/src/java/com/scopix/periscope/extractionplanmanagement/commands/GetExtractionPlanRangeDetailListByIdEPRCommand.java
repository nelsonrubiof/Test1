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
 *  GetExtractionPlanRangeDetailListByIdEPRCommand.java
 * 
 *  Created on 28-09-2010, 05:11:08 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRangeDetail;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAO;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAOImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;

/**
 *
 * @author nelson
 */
public class GetExtractionPlanRangeDetailListByIdEPRCommand {

    private ExtractionPlanCustomizingDAO dao;

    public List<ExtractionPlanRangeDetail> execute(ExtractionPlanRange planRange) throws ScopixException {
        List<ExtractionPlanRangeDetail> details = getDao().getExtractionPlanRangeDetails(planRange);
        return details;
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
