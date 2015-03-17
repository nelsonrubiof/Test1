/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * UploadEvidenceJob.java
 * 
 * Created on 3 de julio de 2007, 11:52
 */
package com.scopix.periscope.schedulermanagement;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 * This class is responsible of uploading the files available in the video server.
 *
 * Folders and connection parameters are specified in system.properties
 *
 * @author marko.perich
 */
public class UploadEvidenceJob implements Job {

    private static boolean working = false;
    private static String fileManagerType = "NETBIOS";
    private static Logger log = Logger.getLogger(UploadEvidenceJob.class);

    /**
     * Creates a new instance of UploadEvidenceJob
     */
    public UploadEvidenceJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("start");
        ExtractionManager extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
        if (!isWorking()) {
            setWorking(true);
            try {
                extractionManager.uploadEvidence(null); // null -> no es push de evidencia live
            } catch (ScopixException ex) {
                log.debug("Error: [" + ex.getMessage() + "]");
            } finally {
                setWorking(false);
            }
        } else {
            log.debug("Job was executing, exit");
        }
        log.info("end");
    }

    public static synchronized boolean isWorking() {
        return working;
    }

    public static synchronized void setWorking(boolean aWorking) {
        working = aWorking;
    }

    public static String getFileManagerType() {
        return fileManagerType;
    }

    public static void setFileManagerType(String aFileManagerType) {
        fileManagerType = aFileManagerType;
    }
}