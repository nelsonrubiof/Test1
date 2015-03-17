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
 *  RealRandomJob.java
 * 
 *  Created on 07-01-2014, 11:35:06 AM
 * 
 */
package com.scopix.periscope.schedulermanagement;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.extractionmanagement.RequestTimeZone;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author nelson
 */
public class RealRandomJob implements Job {

    private static Logger log = Logger.getLogger(RealRandomJob.class);
    public static String LIST_REQUEST_TIME_ZONE = "LIST_REQUEST_TIME_ZONE";
    public static String DAY_OF_WEEK = "DAY_OF_WEEK";

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        log.info("start " + jec.getJobDetail().getKey().getName());
        List<RequestTimeZone> l = (List<RequestTimeZone>) jec.getJobDetail().getJobDataMap().get(LIST_REQUEST_TIME_ZONE);
        Integer dayOfWeek = (Integer) jec.getJobDetail().getJobDataMap().get(DAY_OF_WEEK);
        try {
            ExtractionManager extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
            //extractionManager.extractEvidence(req, null);
            extractionManager.extractEvidenceRealRandom(l, dayOfWeek);
        } catch (ScopixException e) {
            throw new JobExecutionException("Error executing job " + jec.getJobDetail().getKey().getName() + ".", e);
        }

        log.info("end " + jec.getJobDetail().getKey().getName());
    }

}
