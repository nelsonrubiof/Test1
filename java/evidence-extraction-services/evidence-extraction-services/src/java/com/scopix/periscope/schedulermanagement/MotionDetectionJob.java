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
 * MotionDetectionJob.java
 *
 * Created on 13-04-2010, 12:07:31 PM
 *
 */
package com.scopix.periscope.schedulermanagement;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Gustavo Alvarez
 */
public class MotionDetectionJob implements Job {

    private static Logger log = Logger.getLogger(MotionDetectionJob.class.getName());
    private static boolean working = false;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //getLog().debug("execute");
        ExtractionManager extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
        if (!(isWorking())) {
            setWorking(true);
            try {
                extractionManager.sendMotionDetection();
            } catch (Exception ex) {
                getLog().debug("Error: " + ex.getMessage());
            } finally {
                setWorking(false);
            }
        } else {
            getLog().debug("Job was executing. Exit.");
        }
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger aLog) {
        log = aLog;
    }

    public static boolean isWorking() {
        return working;
    }

    public static void setWorking(boolean aWorking) {
        working = aWorking;
    }
}
