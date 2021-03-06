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
 *  RetryUpdateRegionTransferEntryCommand.java
 * 
 *  Created on Sep 4, 2014, 4:42:55 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.EvidenceRegionTransfer;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.Date;

/**
 *
 * @author Sebastian
 */
public class RetryUpdateRegionTransferEntryCommand {

    /**
     * Refreshes the region transfer record to pending and a new start date
     *
     * @param evidenceRegionTransferId
     * @return EvidenceRegionTransfer
     */
    public EvidenceRegionTransfer execute(Integer evidenceRegionTransferId) {

        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        EvidenceRegionTransfer evidenceRegionTransfer = dao.get(evidenceRegionTransferId, EvidenceRegionTransfer.class);
        if ( evidenceRegionTransfer!= null && !evidenceRegionTransfer.isCompleted()) {
            evidenceRegionTransfer.setTransmisitionDate(new Date());
            evidenceRegionTransfer.setErrorMessage("PENDING");
            dao.save(evidenceRegionTransfer);
            return evidenceRegionTransfer;
        }
        return null;
    }
}
