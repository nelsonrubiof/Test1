/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
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
 *  GetTransmistionStrategyByStoreAndSituation.java
 * 
 *  Created on Jul 23, 2014, 3:37:03 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.EvidenceTransmitionStrategy;
import com.scopix.periscope.evaluationmanagement.dao.EvidenceTransmitionStrategyDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class GetTransmStgiesByCriteriaCommand {

    private EvidenceTransmitionStrategyDAO evidenceTransmitionStrategyDAO;

    /**
     * Gets the desired EvidenceTransmitionStrategy given an Store and
     * Department
     *
     * @param store
     * @param situationTemplate
     * @return EvidenceTransmitionStrategy
     */
    public List <EvidenceTransmitionStrategy> execute(Store store, SituationTemplate situationTemplate) {

        return getEvidenceTransmitionStrategyDAO().getAllByStoreAndOrSituationTemplate(store, situationTemplate);
             
    }

    /**
     * @return the evidenceTransmitionStrategyDAO
     */
    public EvidenceTransmitionStrategyDAO getEvidenceTransmitionStrategyDAO() {
        if (evidenceTransmitionStrategyDAO == null) {
            evidenceTransmitionStrategyDAO = SpringSupport.getInstance().findBeanByClassName(EvidenceTransmitionStrategyDAO.class);
        }
        return evidenceTransmitionStrategyDAO;
    }

    /**
     * @param evidenceTransmitionStrategyDAO the evidenceTransmitionStrategyDAO
     * to set
     */
    public void setEvidenceTransmitionStrategyDAO(EvidenceTransmitionStrategyDAO evidenceTransmitionStrategyDAO) {
        this.evidenceTransmitionStrategyDAO = evidenceTransmitionStrategyDAO;
    }
}
