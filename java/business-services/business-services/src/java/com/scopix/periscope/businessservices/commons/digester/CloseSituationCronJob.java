/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.businessservices.commons.digester;

import com.scopix.periscope.evaluationmanagement.EvaluationManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Admin
 */
public class CloseSituationCronJob implements Job {

    private static final Logger log = Logger.getLogger(CloseSituationCronJob.class);

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        log.info("start");
        try {
            EvaluationManager manager = SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class);
            manager.closeSituationsCron();
        } catch (Exception e) {
            log.error(e, e);
        }
        log.info("end");
    }

}
