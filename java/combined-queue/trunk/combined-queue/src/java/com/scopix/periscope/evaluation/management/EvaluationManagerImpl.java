/*
 * 
 * Copyright 2013 SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and cano
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 * 
 */
package com.scopix.periscope.evaluation.management;

import com.scopix.periscope.activitylog.management.runnable.CreateActivityLogEntryRunnable;
import com.scopix.periscope.activitylog.management.runnable.UpdateActiviyLogRunnable;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceEvaluationDTO;
import com.scopix.periscope.evaluationmanagement.dto.SituationSendDTO;
import com.scopix.periscope.evaluationmanagement.services.webservices.EvaluationWebServices;
import com.scopix.periscope.evaluationmanagement.services.webservices.client.EvaluationWebServicesClient;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.QueueManager;
import com.scopix.periscope.subscription.management.SubscriptionManager;
import com.scopix.periscope.securitymanagement.SecurityManager;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Sebastian
 */
@SpringBean(rootClass = EvaluationManager.class)
@Transactional(rollbackFor = {ScopixWebServiceException.class})
public class EvaluationManagerImpl implements EvaluationManager {

    private SubscriptionManager userSubscriptionManager;
    private QueueManager queueManager;
    private SecurityManager securityManager;
    private static Logger log = Logger.getLogger(EvaluationManagerImpl.class);

    @Override
    public SituationSendDTO getNextEvidence(String queueName, String corporateName, long sessionId)
            throws ScopixWebServiceException {
        try {
            String userName = getSecurityManager().getUserName(sessionId);
            List<Map<String, Object>> userSubscriptions = getUserSubscriptionManager().getUserSubscriptionsList(userName);
            if (userSubscriptions != null) {
                for (Map<String, Object> userMap : userSubscriptions) {
                    String corporateNameFromMap = (String) userMap.get("corporateName");
                    String operatorQueueName = (String) userMap.get("operatorQueueName");
                    //check and confirm
                    Integer pendingElements = getQueueManager()
                            .getPendingElementsForQueue(corporateNameFromMap, operatorQueueName);
                    if (pendingElements <= 0) {
                        log.info("there are no evidences available for corporate: "
                                + corporateNameFromMap + " queue:" + operatorQueueName + " continuing..");
                        continue;
                    }
                    try {
                        log.info("retrieving evidence from subscription " + operatorQueueName);
                        SituationSendDTO situation = getEvaluationWebService(corporateNameFromMap).getNextEvidence(operatorQueueName, sessionId);
                        if (situation == null) {
                            log.info("there are no evidences available for corporate: "
                                    + corporateNameFromMap + " queue:" + operatorQueueName + " continuing..");
                            continue;
                        }
                        Thread t = new Thread(new CreateActivityLogEntryRunnable(userName, situation.getPendingEvaluationId(), corporateNameFromMap, operatorQueueName, new Date()));
                        t.start();
                        return situation;

                    } catch (Exception ex) {
                        log.info("Exception ocurred: will proceed to the next element");
                        log.info(ex.getMessage());
                        log.debug(ex.getStackTrace());
                        continue;
                    }
                }
            }
            log.info("retrieving evidence from connected queue " + queueName);
            SituationSendDTO situation = getEvaluationWebService(corporateName).getNextEvidence(queueName, sessionId);
            if (situation != null) {
                Thread t = new Thread(new CreateActivityLogEntryRunnable(userName, situation.getPendingEvaluationId(), corporateName, queueName, new Date()));
                t.start();
            }
            return situation;
        } catch (ScopixException ex) {
            log.info("Unable to retrieve info from security service");
            log.info(ex.getMessage());
            log.debug(ex.getStackTrace());
            throw new ScopixWebServiceException(ex);
        }
    }

    @Override
    public void sendEvidenceEvaluation(List<EvidenceEvaluationDTO> evidenceEvaluations, String corporateName, long sessionId)
            throws ScopixWebServiceException {
        try {
            log.info("Sending evaluations");
            String userName = getSecurityManager().getUserName(sessionId);
            getEvaluationWebService(corporateName).sendEvidenceEvaluation(evidenceEvaluations, sessionId);
            Thread t = new Thread(new UpdateActiviyLogRunnable(userName, evidenceEvaluations.get(0).getPendingEvaluationId(), new Date()));
            t.start();
        } catch (ScopixException ex) {
            throw new ScopixWebServiceException(ex);
        }
    }

    /**
     * getter for manager
     *
     * @return
     */
    public SubscriptionManager getUserSubscriptionManager() {
        if (userSubscriptionManager == null) {
            setUserSubscriptionManager(SpringSupport.getInstance().findBeanByClassName(SubscriptionManager.class));
        }
        return userSubscriptionManager;
    }

    /**
     * setter for manager
     *
     * @param userSubscriptionManager
     */
    public void setUserSubscriptionManager(SubscriptionManager userSubscriptionManager) {
        this.userSubscriptionManager = userSubscriptionManager;
    }

    /**
     * Setter for manager
     *
     * @return
     */
    public SecurityManager getSecurityManager() {
        if (userSubscriptionManager == null) {
            setSecurityManager(SpringSupport.getInstance().findBeanByClassName(SecurityManager.class));
        }
        return securityManager;
    }

    /**
     * getter for manager
     *
     * @param securityManager
     */
    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    /**
     * @return the queueManager
     */
    public QueueManager getQueueManager() {
        if (queueManager == null) {
            setQueueManager(SpringSupport.getInstance().findBeanByClassName(QueueManager.class));
        }
        return queueManager;
    }

    /**
     * @param queueManager the queueManager to set
     */
    public void setQueueManager(QueueManager queueManager) {
        this.queueManager = queueManager;
    }

    /**
     * get web service for selected corporate
     *
     * @param corporateName
     * @return EvaluationWebServices
     * @throws ScopixException
     */
    public EvaluationWebServices getEvaluationWebService(String corporateName) throws ScopixException {
        corporateName = StringUtils.replace(corporateName, " ", "_").toUpperCase();
        log.info("start - getEvaluationWebService " + corporateName);
        EvaluationWebServices evaluationWebService = new EvaluationWebServicesClient().getWebService(corporateName);
        log.info("end - getEvaluationWebService " + corporateName);
        return evaluationWebService;
    }
}
