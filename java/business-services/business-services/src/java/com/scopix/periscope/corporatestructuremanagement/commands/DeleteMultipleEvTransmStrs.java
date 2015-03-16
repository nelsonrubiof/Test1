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
 *  DeleteMultipleEvTransmStrs.java
 * 
 *  Created on Aug 22, 2014, 5:43:09 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.evaluationmanagement.dao.EvidenceTransmitionStrategyDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class DeleteMultipleEvTransmStrs {

    private EvidenceTransmitionStrategyDAO evidenceTransmitionStrategyDAO;

    /**
     * deletes desired Evidence Transmission Strategies given a list of ids
     *
     * @param ids
     */
    public void execute(List<Integer> ids) {
        getEvidenceTransmitionStrategyDAO().deleteMultiple(ids);

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
