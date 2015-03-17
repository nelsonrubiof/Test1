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
 * CutJobListener.java
 *
 * Created on 18 de junio de 2007, 17:18
 *
 */

package com.scopix.periscope.schedulermanagement;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 *
 * @author marko.perich
 */
public class CutJobListener implements JobListener {
    private static Logger log = Logger.getLogger(CutJobListener.class.getName());

    
    /** Creates a new instance of CutJobListener */
    public CutJobListener() {
    }
    
    public String getName() {
        return CutJobListener.class.getName();
    }
    
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
    }
    
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
    }
    
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException jobExecutionException) {
        log.debug("jobWasExecuted "+ jobExecutionContext.getJobDetail().getKey().getName());
    }
    
}
