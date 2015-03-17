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
package com.scopix.periscope.scheduled.services;

import com.scopix.periscope.corporate.Corporate;
import com.scopix.periscope.corporate.CorporateUtils;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.scheduled.tasks.CountPendingEvaluationTask;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sebastian Torres Brown
 */

@SpringBean(rootClass = PendingEvalSchedulerService.class)
@Service
public class PendingEvalSchedulerService  {
    
    private volatile Logger log = Logger.getLogger(PendingEvalSchedulerService.class);
    
    /**
     * scheduled process that looks for pending evaluation evidences in all corporates
     * @throws ScopixException
     */
    @Scheduled(cron="0 * * * * *")   
    public void doPendingEvaluationCountScheduled() throws ScopixException{
        List<Corporate> corporates =CorporateUtils.getCorporates();
        log.info("Getting pending evaluation evidences");
        for (Corporate corporate :corporates){
            Thread t = new Thread(new CountPendingEvaluationTask(corporate.getName()));
            t.start();            
        }
    }
    
}
