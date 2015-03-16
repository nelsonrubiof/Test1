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
 *  EvidenceExtractionThread.java
 * 
 *  Created on 10-01-2014, 04:51:20 PM
 * 
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class EvidenceExtractionThread extends ExtractEvidenceThread {

    private static Logger log = Logger.getLogger(EvidenceExtractionThread.class);
    EvidenceExtractionRequest evidenceExtractionRequest;
    Date d;

    public EvidenceExtractionThread(EvidenceExtractionRequest eer, Date date) {
        this.evidenceExtractionRequest = eer;
        this.d = date;
        this.setName(this.getClass().getSimpleName() + "-" + eer.getId());
    }

    @Override
    public void run() {
        log.info("start");
        try {
            //se debe llamar al extractor por id de request
            SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class).
                    extractEvidence(evidenceExtractionRequest.getId(), d);
        } catch (ScopixException e) {
            log.error(e, e);
        }
        log.info("end");
    }

}
