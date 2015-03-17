/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.businessservices.commons.digester;

import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Admin
 */
public class SendEPCCronJob implements Job {

    private static final Logger log = Logger.getLogger(SendEPCCronJob.class);

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        log.info("start");
        try {
            CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
            manager.sendExtractionPlanToPastCron();
        } catch (Exception e) {
            log.error(e, e);
        }
        log.info("end");
    }

}
