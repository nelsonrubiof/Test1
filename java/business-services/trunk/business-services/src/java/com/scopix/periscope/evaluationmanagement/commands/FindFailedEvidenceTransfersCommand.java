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
 *  FindFailedEvidenceTransfersCommand.java
 * 
 *  Created on Sep 4, 2014, 11:12:05 AM
 * 
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.dao.EvidenceRegionTransferDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sebastian
 */
public class FindFailedEvidenceTransfersCommand {

    private EvidenceRegionTransferDAO evidenceRegionTransferDAO;

    /**
     * get failed evidences transmission from X days ago
     *
     * @param daysAgo
     */
    public List<Map<String, Object>> execute(Integer daysAgo) {
        if (daysAgo == null || daysAgo.equals(0)) {
            daysAgo = 1;
        }
        Long totalMillis = new Long(daysAgo) * new Long(24 * 60 * 60 * 1000);
        Date to = new Date();
        Date from = new Date(to.getTime() - totalMillis);
        return getEvidenceRegionTransferDAO().getFailedPendingEvidences(from, to);        
    }

    /**
     * @return the evidenceRegionTransferDAO
     */
    public EvidenceRegionTransferDAO getEvidenceRegionTransferDAO() {
        if (evidenceRegionTransferDAO == null) {
            evidenceRegionTransferDAO = SpringSupport.getInstance().findBeanByClassName(EvidenceRegionTransferDAO.class);
        }
        return evidenceRegionTransferDAO;
    }

    /**
     * @param evidenceRegionTransferDAO the evidenceRegionTransferDAO to set
     */
    public void setEvidenceRegionTransferDAO(EvidenceRegionTransferDAO evidenceRegionTransferDAO) {
        this.evidenceRegionTransferDAO = evidenceRegionTransferDAO;
    }
}
