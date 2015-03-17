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
 *  GetExtractionPlanCustomizingBySTAndStoreCommand.java
 * 
 *  Created on 13-09-2010, 10:36:27 AM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAO;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAOImpl;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;

/**
 * Recupera un Extraction Plan Customizing ACTIVO dado un Situation Template y un Store
 * @author nelson
 */
public class GetExtractionPlanCustomizingBySTAndStoreCommand {

    private ExtractionPlanCustomizingDAO dao;

    public ExtractionPlanCustomizing execute(SituationTemplate st, Store store) {
        ExtractionPlanCustomizing extractionPlanCustomizing = getDao().getExtractionPlanCustomizing(st, store);
        return extractionPlanCustomizing;
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
