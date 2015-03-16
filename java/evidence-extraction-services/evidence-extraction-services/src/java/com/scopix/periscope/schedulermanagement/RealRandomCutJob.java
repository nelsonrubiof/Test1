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
 *  RealRandomCutJob.java
 * 
 *  Created on 30-08-2012, 03:44:55 PM
 * 
 */
package com.scopix.periscope.schedulermanagement;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.extractionmanagement.SituationRequestRealRandom;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author nelson
 */
public class RealRandomCutJob implements Job {

    private static Logger log = Logger.getLogger(RealRandomCutJob.class);
    private SchedulerManager schedulerManager;
    private ExtractionManager extractionManager;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        log.info("start " + jec.getJobDetail().getKey().getName());
        JobDataMap jobData = jec.getJobDetail().getJobDataMap();
        //SituationRequestRealRandom realRandom = (SituationRequestRealRandom) jobData.get("realRandom");
        Integer realRandomId = (Integer) jobData.get("realRandomId");
        //createNewJob(realRandom, jec);
        
        //debemos crear la solicitud de Evidencia
//        getExtractionManager().generateRequestFromRealRandom(realRandom, jec.getJobDetail().getGroup());
        getExtractionManager().generateRequestFromRealRandom(realRandomId, jec.getJobDetail().getKey().getGroup());
        log.info("end");
    }
    
    

    public SchedulerManager getSchedulerManager() {
        if (schedulerManager == null) {
            schedulerManager = SpringSupport.getInstance().findBeanByClassName(SchedulerManager.class);
        }
        return schedulerManager;
    }

    public void setSchedulerManager(SchedulerManager manager) {
        this.schedulerManager = manager;
    }

    public ExtractionManager getExtractionManager() {
        if (extractionManager == null) {
            extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
        }
        return extractionManager;
    }

    public void setExtractionManager(ExtractionManager extractionManager) {
        this.extractionManager = extractionManager;
    }
}
