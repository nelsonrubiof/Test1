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
 * QueueManagementWebServices.java
 *
 * Created on 20-05-2008, 11:37:05 AM
 *
 */
package com.scopix.periscope.queuemanagement.services.webservices;

import com.scopix.periscope.queuemanagement.dto.PendingEvaluationDTO;
import com.scopix.periscope.queuemanagement.dto.FilteringData;
import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import com.scopix.periscope.evaluationmanagement.EvaluationState;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.queuemanagement.dto.OperatorQueueDTO;
import com.scopix.periscope.queuemanagement.dto.SummaryDTO;
import java.util.List;
import java.util.Map;
import javax.jws.WebService;

/**
 *
 * @author Cesar Abarza Suazo.
 */
@WebService(name = "QueueManagementWebServices")
public interface QueueManagementWebServices {

    List<PendingEvaluationDTO> getPendingEvaluationList(FilteringData filter, long sessionId) throws ScopixWebServiceException;

    void changeState(List<Integer> pendingEvaluationIds, String newState, long sessionId) throws ScopixWebServiceException;

    List<PendingEvaluationDTO> changePriority(List<Integer> pendingEvaluationIds, int startPriority, FilteringData filter,
            long sessionId) throws ScopixWebServiceException;

    @Deprecated
    void refreshQueue(String queueType, String state, Integer queueNameId, long sessionId) throws ScopixWebServiceException;

    List<StoreDTO> getStoreList(long sessionId) throws ScopixWebServiceException;

    List<PendingEvaluationDTO> backToQueue(List<Integer> checksIds, FilteringData filters, Integer startPosition,
            EvaluationState evaluationState, long sessionId) throws ScopixWebServiceException;

    List<SummaryDTO> getSummaryList(long sessionId) throws ScopixWebServiceException;

    List<PendingEvaluationDTO> getNFirstElementOfQueue(int quantity, Integer queueNameId, long sessionId) throws
            ScopixWebServiceException;

    List<OperatorQueueDTO> getOperatorQueues() throws ScopixWebServiceException;

    List<PendingEvaluationDTO> moveTo(List<Integer> peIds, Integer toQeueue, Integer position, FilteringData filters,
            long sessionId) throws ScopixWebServiceException;

    Map<String, Integer> countPendingEvaluationEvidences(String securityPassPhrase) throws ScopixWebServiceException;
}
