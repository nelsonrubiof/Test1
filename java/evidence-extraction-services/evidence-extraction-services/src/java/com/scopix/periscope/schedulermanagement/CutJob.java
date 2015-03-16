/*
 *
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * CutJob.java
 *
 * Created on 14 de junio de 2007, 12:13
 *
 */
package com.scopix.periscope.schedulermanagement;

import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.utilities.Garbage;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author marko.perich
 */
public class CutJob implements Job {

    private static Logger log = Logger.getLogger(CutJob.class.getName());
    public static final String EVIDENCE_EXTRACTION_REQUEST = "EVIDENCE_EXTRACTION_REQUEST";
    public static final String PARAM_LENGTH = "LENGTH";
    public static final String EVIDENCE_EXTRACTION_REQUEST_ID = "EVIDENCE_EXTRACTION_REQUEST_ID";

//    /** Creates a new instance of CutJob */
//    public CutJob() {
//    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("start " + jobExecutionContext.getJobDetail().getKey().getName());
        String jobName = jobExecutionContext.getJobDetail().getKey().getName();

        JobDataMap jobData = jobExecutionContext.getJobDetail().getJobDataMap();
        Integer evidenceExtractionRequestId = (Integer) jobData.get(EVIDENCE_EXTRACTION_REQUEST_ID);
        //EvidenceExtractionRequest req = (EvidenceExtractionRequest) jobData.get(EVIDENCE_EXTRACTION_REQUEST);
        try {
            ExtractionManager extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
            //extractionManager.extractEvidence(req, null);
            extractionManager.extractEvidence(evidenceExtractionRequestId, null);
        } catch (ScopixException ex) {
            throw new JobExecutionException("Error executing job " + jobName + ".", ex);
        }
        
        //Garbage.freeMemory();
        log.info("end " + jobExecutionContext.getJobDetail().getKey().getName());
    }
}
