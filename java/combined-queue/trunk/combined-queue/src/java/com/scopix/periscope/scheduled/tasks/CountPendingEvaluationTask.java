/*
 * 
 * Copyright (C) 2013, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * SecurityManagerImpl.java
 *
 * Created on 30-10-20013, 09:14:59 PM
 *
 */
package com.scopix.periscope.scheduled.tasks;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.QueueManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Sebastian
 */


public class CountPendingEvaluationTask implements Runnable{
    
    private String corporate;
    private Logger log = Logger.getLogger(CountPendingEvaluationTask.class);  
        
    /**
     * Constructor, sets the corporate name to be used in order to call the correct
     * service.
     * @param corporate
     */
    public CountPendingEvaluationTask(String corporate){
        this.corporate =corporate;
    }

    @Override
    public void run() {
        try {
            SpringSupport.getInstance().findBeanByClassName(QueueManager.class).getPendingEvaluationEvidenceMap(corporate);
        } catch (ScopixException ex) {
            log.info("An error ocurred trying to retrieve the pending evaluation Map"
                    + " for corporate " + corporate 
                    + " will be retried again on the next scheduled execution ");           
             log.warn(ex);              
        }              
    }
}

