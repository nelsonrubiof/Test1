/*
 * 
 * Copyright (c)> 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * QueueManagementWebServicesImpl.java
 *
 * Created on 20-05-2008, 01:38:57 PM
 *
 */
package com.scopix.periscope.queuemanagement.services.webservices;

import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import com.scopix.periscope.evaluationmanagement.EvaluationState;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.dto.FilteringData;
import com.scopix.periscope.queuemanagement.dto.PendingEvaluationDTO;
import com.scopix.periscope.queuemanagement.QueueManager;
import com.scopix.periscope.queuemanagement.dto.SummaryDTO;
import com.scopix.periscope.queuemanagement.dto.OperatorQueueDTO;
import java.util.List;
import java.util.Map;
import javax.jws.WebService;
import org.apache.log4j.Logger;

/**
 *
 * @author C?sar Abarza Suazo.
 */
@WebService(endpointInterface = "com.scopix.periscope.queuemanagement.services.webservices.QueueManagementWebServices")
@SpringBean(rootClass = QueueManagementWebServices.class)
public class QueueManagementWebServicesImpl implements QueueManagementWebServices {

    private static Logger log = Logger.getLogger(QueueManagementWebServicesImpl.class);
    private static final String SECURITY_PASS_PHRASE = "REQUESTING-ACCESS-TO-SERVICE-FROM-SCOPIX-APP";

    @Override
    public List<PendingEvaluationDTO> getPendingEvaluationList(FilteringData filter, long sessionId) throws 
            ScopixWebServiceException {
        log.info("start");
        List<PendingEvaluationDTO> pendingEvaluationDTOs = null;
        try {
            pendingEvaluationDTOs = SpringSupport.getInstance().findBeanByClassName(QueueManager.class).
                    getPendingEvaluationList(filter, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return pendingEvaluationDTOs;
    }

    @Override
    public void changeState(List<Integer> pendingEvaluationIds, String newState, long sessionId) throws 
            ScopixWebServiceException {
        log.info("start");
        try {
            SpringSupport.getInstance().findBeanByClassName(QueueManager.class).
                    changeState(pendingEvaluationIds, newState, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
    }

    @Override
    public List<PendingEvaluationDTO> changePriority(List<Integer> pendingEvaluationIds, int startPriority, FilteringData filter,
            long sessionId) throws ScopixWebServiceException {
        log.info("start");
        List<PendingEvaluationDTO> pendingEvaluationDTOs = null;
        try {
            pendingEvaluationDTOs = SpringSupport.getInstance().findBeanByClassName(QueueManager.class).
                    changePriority(pendingEvaluationIds, startPriority, filter, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return pendingEvaluationDTOs;
    }

    @Override
    @Deprecated
    public void refreshQueue(String queueType, String state, Integer queueNameId, long sessionId) throws 
            ScopixWebServiceException {
        log.info("start");
        //SpringSupport.getInstance().findBeanByClassName(QueueManager.class).refreshQueue(queueType, state, sessionId);
        log.info("end");
    }

    @Override
    public List<StoreDTO> getStoreList(long sessionId) throws ScopixWebServiceException {
        log.info("start");
        List<StoreDTO> stores = null;
        try {
            stores = SpringSupport.getInstance().findBeanByClassName(QueueManager.class).getStores(sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return stores;
    }

    @Override
    public List<PendingEvaluationDTO> backToQueue(List<Integer> checksIds, FilteringData filters, Integer startPosition,
            EvaluationState evaluationState, long sessionId) throws ScopixWebServiceException {
        log.info("start");
        List<PendingEvaluationDTO> pendingEvaluationDTOs = null;
        try {
            pendingEvaluationDTOs = SpringSupport.getInstance().findBeanByClassName(QueueManager.class).
                    backToQueue(checksIds, filters, startPosition, evaluationState, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return pendingEvaluationDTOs;
    }

    @Override
    public List<SummaryDTO> getSummaryList(long sessionId) throws ScopixWebServiceException {
        log.info("start");
        List<SummaryDTO> summary = null;
        try {
            summary = SpringSupport.getInstance().findBeanByClassName(QueueManager.class).getSummaryList(sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return summary;
    }

    @Override
    public List<PendingEvaluationDTO> getNFirstElementOfQueue(int quantity, Integer queueNameId, long sessionId) throws 
            ScopixWebServiceException {
        log.info("start");
        List<PendingEvaluationDTO> list = null;
        try {
            list = SpringSupport.getInstance().findBeanByClassName(QueueManager.class).
                    getNFirstElementOfQueue(quantity, queueNameId, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return list;
    }

    @Override
    public List<OperatorQueueDTO> getOperatorQueues() throws ScopixWebServiceException {
        log.info("start");
        List<OperatorQueueDTO> oqs = null;
        try {
            oqs = SpringSupport.getInstance().findBeanByClassName(QueueManager.class).getOperatorQueues();
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return oqs;
    }

    @Override
    public List<PendingEvaluationDTO> moveTo(List<Integer> peIds, Integer toQeueue, Integer position,
            FilteringData filters, long sessionId) throws ScopixWebServiceException {
        log.info("start");
        List<PendingEvaluationDTO> pedtos = null;
        try {
            pedtos = SpringSupport.getInstance().findBeanByClassName(QueueManager.class).
                    moveTo(peIds, toQeueue, position, filters, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return pedtos;
    }
    
    @Override
    public Map<String, Integer> countPendingEvaluationEvidences(String securityPassPhrase) throws ScopixWebServiceException {
        log.info("start");
        if (SECURITY_PASS_PHRASE.equals(securityPassPhrase)) {
            Map<String, Integer> response = null;
            try {
                response = SpringSupport.getInstance()
                        .findBeanByClassName(QueueManager.class).getEvidencesAvailableCountMap();
            } catch (ScopixException e) {
                throw new ScopixWebServiceException(e);
            }
            log.info("end");
            return response;
        } 
        log.info("the security pass phrase is incorrect");
        throw new  ScopixWebServiceException("The security pass phrase is incorrect");
    }
    
}
