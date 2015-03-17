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
 *  GetEPCFromStoreSituationTemplateEvidenceProviderCommand.java
 * 
 *  Created on 16-12-2010, 05:58:27 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanMetric;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAO;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAOImpl;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;

/**
 *
 * @author nelson
 */
public class GetEPCFromStoreSituationTemplateEvidenceProviderCommand {

    private ExtractionPlanCustomizingDAO dao;
    private GenericDAO genericDao;

    public ExtractionPlanCustomizingDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanCustomizingDAOImpl.class);
        }
        return dao;
    }

    public void setDao(ExtractionPlanCustomizingDAO dao) {
        this.dao = dao;
    }

    public ExtractionPlanCustomizing execute(Integer storeId, Integer situationTemplateId, Integer evidenceProviderId) {
        ExtractionPlanCustomizing epc = null;
        List<Integer> ids = getDao().getAllEPCFromStoreSituatioTemplateEvidenceProvider(storeId, situationTemplateId,
                evidenceProviderId);
        if (!ids.isEmpty()) {
            for (Integer idEpc : ids) {
                epc = getGenericDao().get(idEpc, ExtractionPlanCustomizing.class);
                epc.getExtractionPlanMetrics().isEmpty();
                epc.getExtractionPlanRanges().isEmpty();
                //recorremos las metricas y recuperamso su providers
                for (ExtractionPlanMetric metric : epc.getExtractionPlanMetrics()){
                    metric.getEvidenceProviders().isEmpty();
                }
            }
        }
        return epc;
    }

    public GenericDAO getGenericDao() {
        if (genericDao == null) {
            genericDao = HibernateSupport.getInstance().findGenericDAO();
        }
        return genericDao;
    }

    public void setGenericDao(GenericDAO genericDao) {
        this.genericDao = genericDao;
    }
}
