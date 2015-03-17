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
 * MountDeviceJob.java
 *
 * Created on 02-03-2010, 06:15:40 PM
 *
 */
package com.scopix.periscope.schedulermanagement;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Gustavo Alvarez
 */
public class MountDeviceJob implements Job {

    private static Logger log = Logger.getLogger(MountDeviceJob.class.getName());
    private static boolean working = false;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        log.debug("start");
        ExtractionManager extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);

        if (!isWorking()) {
            setWorking(true);
            try {
                extractionManager.mountExternalDevice();
            } catch(ScopixException pex) {
                log.debug("Error: " + pex.getMessage());
            } finally {
                setWorking(false);
            }
            log.debug("end");
        } else {
            log.debug("job is working. Exit.");
        }
    }

    public static boolean isWorking() {
        return working;
    }

    public static void setWorking(boolean aWorking) {
        working = aWorking;
    }

}