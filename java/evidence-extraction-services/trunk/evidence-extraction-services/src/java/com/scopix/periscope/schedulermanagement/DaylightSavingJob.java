/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.schedulermanagement;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.Set;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author nelson
 */
public class DaylightSavingJob implements Job {

    private static Logger log = Logger.getLogger(DaylightSavingJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("start " + jobExecutionContext.getJobDetail().getKey().getName());
        JobDataMap jobData = jobExecutionContext.getJobDetail().getJobDataMap();
        Set<String> storeNames = (Set<String>) jobData.get("storeNames");
        //String timeZoneId = jobData.getString("timeZoneId");
        try {
            ExtractionManager em = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
            for (String storeName : storeNames) {
                em.init(storeName);
            }

        } catch (ScopixException e) {
            log.warn("No es posible ejecutar init de ExtractionManager " + e);
        }
        log.info("end " + jobExecutionContext.getJobDetail().getKey().getName());
    }
}
