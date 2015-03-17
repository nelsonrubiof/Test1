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
 *  SaveExtractionPlanMetricCommand.java
 * 
 *  Created on 21-09-2010, 10:53:59 AM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceProviderDTO;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanMetric;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanMetricDTO;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class SaveExtractionPlanMetricCommand {

    private static Logger log = Logger.getLogger(SaveExtractionPlanMetricCommand.class);
    private GenericDAO dao;

    public GenericDAO getDao() {
        if (dao == null) {
            dao = HibernateSupport.getInstance().findGenericDAO();
        }
        return dao;
    }

    public void setDao(GenericDAO dao) {
        this.dao = dao;
    }

    public void execute(ExtractionPlanCustomizing epc, ExtractionPlanMetricDTO planMetricDTO) {
        ExtractionPlanMetric planMetric = new ExtractionPlanMetric();
        planMetric.setMetricVariableName(planMetricDTO.getMetricVariableName());
        planMetric.setEvaluationOrder(planMetricDTO.getEvaluationOrder());

        MetricTemplate mt = new MetricTemplate();
        mt.setId(planMetricDTO.getMetricTemplateId());
        planMetric.setMetricTemplate(mt);
        planMetric.setExtractionPlanCustomizing(epc);

        for (EvidenceProviderDTO evidenceProviderDTO : planMetricDTO.getEvidenceProviderDTOs()) {
            log.debug("evidenceProviderDTO.getId():" + evidenceProviderDTO.getId());
            EvidenceProvider ep = getDao().get(evidenceProviderDTO.getId(), EvidenceProvider.class);

            planMetric.addEvidenceProvider(ep); //.getEvidenceProviders().add(ep);
        }
        getDao().save(planMetric);
    }
}
