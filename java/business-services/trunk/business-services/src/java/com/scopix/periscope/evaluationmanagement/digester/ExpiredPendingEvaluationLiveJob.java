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
 *  ExpiredPendingEvaluationLiveJob.java
 * 
 *  Created on 28-05-2014, 12:09:56 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.digester;

import com.scopix.periscope.evaluationmanagement.EvaluationManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Nelson
 */
public class ExpiredPendingEvaluationLiveJob implements Job {

    private static Logger log = Logger.getLogger(ExpiredPendingEvaluationLiveJob.class);

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        log.info("start");
        EvaluationManager em = SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class);

        if (em.getConfiguration().getBoolean("EXPIRE_LIVE.ACTIVE", Boolean.FALSE)) {
            try {
                List<Integer> pendingEvaluationExpired = em.expirePendingEvaluations(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
                for (Integer peId : pendingEvaluationExpired) {
                    em.expiredPendingEvaluation(peId);
                }
                log.debug("Expired " + pendingEvaluationExpired.size() + " Pending Evaluations");
            } catch (ScopixException e) {
                log.error("Error expire Pending evaluations " + e, e);
            }
        }
        log.info("end");
    }
}
