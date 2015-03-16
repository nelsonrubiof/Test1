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
 *  CreateRegionTransferEntryCommand.java
 * 
 *  Created on Aug 28, 2014, 5:28:15 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.EvidenceRegionTransfer;
import com.scopix.periscope.evaluationmanagement.RegionServer;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author Sebastian
 */
public class CreateRegionTransferEntryCommand {

    private static Logger log = Logger.getLogger(CreateRegionTransferEntryCommand.class);

    /**
     * Creates a Region Transfer Record
     *
     * @param evidence
     * @param regionServer
     */
    public EvidenceRegionTransfer execute(Evidence evidence, RegionServer regionServer) {
        log.info("Creating region transfer record");
        EvidenceRegionTransfer evidenceRegionTransfer = new EvidenceRegionTransfer();
        evidenceRegionTransfer.setTransmisitionDate(new Date());
        evidenceRegionTransfer.setEvidence(evidence);
        evidenceRegionTransfer.setRegionServerName(regionServer.getCodeName());
        evidenceRegionTransfer.setRegionServerIp(regionServer.getServerIp());
        evidenceRegionTransfer.setCompleted(false);
        evidenceRegionTransfer.setErrorMessage("PENDING");
        HibernateSupport.getInstance().findGenericDAO().save(evidenceRegionTransfer);
        log.info("end");
        return evidenceRegionTransfer;
    }
}
