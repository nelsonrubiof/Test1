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
 *  VMSGatewayDownloadThread.java
 * 
 *  Created on 31-05-2012, 11:47:52 AM
 * 
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class VMSGatewayDownloadThread extends ExtractEvidenceThread {

    private static Logger log = Logger.getLogger(VMSGatewayDownloadThread.class);
    private Integer evidenceFileId;
    private String fileName;
    private boolean initialized;

    public void init(Integer evidenceFileId, String fileName) {
        this.evidenceFileId = evidenceFileId;
        this.fileName = fileName;
        this.initialized = true;
        this.setName(this.getClass().getSimpleName() + "-" + this.fileName);
    }

    @Override
    public void run() {
        log.info("start");
        try {
            if (initialized) {
                SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class).
                        vmsGatewaydonwloadFile(evidenceFileId, fileName);
            } else {
                log.debug("No se ha inicializado el Thread");
            }
        } catch (Exception e) {
            log.error("No es posible terminar proceso fileName:" + fileName + " evidenceFileId:" + evidenceFileId, e);
        }
        log.info("end");
    }
}
