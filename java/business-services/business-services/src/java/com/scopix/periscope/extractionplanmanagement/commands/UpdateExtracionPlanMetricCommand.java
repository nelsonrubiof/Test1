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
 *  UpdateExtracionPlanMetricCommand.java
 * 
 *  Created on 10-09-2010, 10:54:40 AM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanMetric;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 *
 * @author nelson
 */
public class UpdateExtracionPlanMetricCommand {

    private GenericDAO dao;

    public void execute(ExtractionPlanMetric extractionPlanMetric) {

        if (extractionPlanMetric != null) {
            //GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
            ExtractionPlanMetric wc = getDao().get(extractionPlanMetric.getId(), ExtractionPlanMetric.class);
            wc.setEvaluationOrder(extractionPlanMetric.getEvaluationOrder());
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
}
