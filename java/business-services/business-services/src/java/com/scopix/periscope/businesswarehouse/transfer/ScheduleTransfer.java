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
 *  ScheduleTransfer.java
 * 
 *  Created on 05-11-2010, 04:49:55 PM
 * 
 */
package com.scopix.periscope.businesswarehouse.transfer;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Gustavo Alvarez
 */
public class ScheduleTransfer implements Job {
    private static Logger log = Logger.getLogger(ScheduleTransfer.class.getName());
    private static boolean working = false;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.debug("start");
        if (!isWorking()) {
            setWorking(true);
            try {
                SchedulerFactory sf = new StdSchedulerFactory();
                Scheduler sched = sf.getScheduler();

                //agendando job de transferencia de datos para auditing
                JobDetail job = new JobDetail("TransferSimpleJob", "TransferGroup", Transfer.class);

                JobDataMap jobDataMap = new JobDataMap();
                job.setJobDataMap(jobDataMap);

                SimpleTrigger trigger = new SimpleTrigger("TransferSimpleTrigger", "TransferGroup");
                sched.scheduleJob(job, trigger);
                
                
                //agendando job de transferencia de archivos de proof para auditing
                job = new JobDetail("TransferProofSimpleJob", "TransferProofGroup", TransferProofs.class);

                jobDataMap = new JobDataMap();
                job.setJobDataMap(jobDataMap);

                trigger = new SimpleTrigger("TransferProofSimpleTrigger", "TransferProofGroup");
                sched.scheduleJob(job, trigger);

            } catch (SchedulerException schExp) {
                log.debug("Error: " + schExp);
                throw new JobExecutionException(schExp);
            } finally {
                setWorking(false);
            }
            log.debug("end");
        } else {
            log.debug("Transfer in progress... exit");
        }
    }

    public static boolean isWorking() {
        return working;
    }

    public static void setWorking(boolean aWorking) {
        working = aWorking;
    }
}
