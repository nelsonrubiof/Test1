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
import com.scopix.periscope.evaluationmanagement.PendingEvidenceRegionTransfers;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Nelson
 */
public class RetryRegionTransferPendingEvaluationsJob implements Job {

    private static Logger log = Logger.getLogger(RetryRegionTransferPendingEvaluationsJob.class);
    private PendingEvidenceRegionTransfers pendingEvidenceRegionTransfers;

    /**
     * Executes Retry Failed Evidence Transfer if the evaluations are not finished
     * @param jec
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        log.info("start");

        EvaluationManager em = SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class);
        if (em.getConfiguration().getBoolean("TRANSFERSFTP.RETRY.ENABLE", Boolean.FALSE)) {
            Integer daysAgo = em.getConfiguration().getInteger("TRANSFERSFTP.RETRY.DAYS.AGO", 1);
            List<Map<String, Object>> pendingTransferEvidences = em.getFailedEvidenceTransfersCommand(daysAgo);
            log.debug("Rescheduling total evidences for transfer: " + pendingTransferEvidences.size());
            for (Map<String, Object> pt : pendingTransferEvidences) {
                String pState = (String) pt.get("pending_evaluation_state");
                if (pState == null || !"FINISHED".equals(pState)) {
                    Integer ertId = (Integer) pt.get("evidence_region_transfer_id");
                    String regionServer = (String) pt.get("region_server_name");
                    if (!getPendingEvidenceRegionTransfers().existTransfer(ertId)) {
                        try {
                            Thread t = new Thread(new ReScheduleTransfer(ertId, regionServer));
                            em.getRetryExecutor().runTask(t);
                        } catch (Exception ex) {
                            log.error("An error ocurred Rescheduling evidence region transfer: " + ertId, ex);
                        }
                    }
                }
            }
            log.debug("Retry region transfer evidences completed ");

        }
        log.info("end");
    }

    private class ReScheduleTransfer implements Runnable {

       private Integer ertId;
       private String regionServer;

        ReScheduleTransfer(Integer ertId, String regionServer) {
            this.ertId = ertId;
            this.regionServer = regionServer;
        }
        @Override
        public void run() {
            try {
                EvaluationManager em = SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class);
                em.retryTransferEvidenceToSituationFTPServer(ertId, regionServer);
            } catch (ScopixException ex) {
                log.error("An error ocurred Rescheduling evidence region transfer: " + ertId, ex);
            }
        }
    }

    /**
     * @return the pendingEvidenceRegionTransfers
     */
    public PendingEvidenceRegionTransfers getPendingEvidenceRegionTransfers() {
        if (pendingEvidenceRegionTransfers == null) {
            pendingEvidenceRegionTransfers = SpringSupport.getInstance().findBeanByClassName(PendingEvidenceRegionTransfers.class);
        }
        return pendingEvidenceRegionTransfers;
    }

    /**
     * @param pendingEvidenceRegionTransfers the pendingEvidenceRegionTransfers
     * to set
     */
    public void setPendingEvidenceRegionTransfers(PendingEvidenceRegionTransfers pendingEvidenceRegionTransfers) {
        this.pendingEvidenceRegionTransfers = pendingEvidenceRegionTransfers;
    }
}
