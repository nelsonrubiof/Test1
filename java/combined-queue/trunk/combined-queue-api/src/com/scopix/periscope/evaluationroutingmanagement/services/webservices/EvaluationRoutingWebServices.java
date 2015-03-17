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
 *
 * Created on 14/11/2013, 03:36:14 PM
 *
 */
package com.scopix.periscope.evaluationroutingmanagement.services.webservices;


import com.scopix.periscope.evaluationmanagement.dto.EvidenceEvaluationDTO;
import com.scopix.periscope.evaluationmanagement.dto.SituationSendDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import java.util.List;
import javax.jws.WebService;

/**
 *
 * @author Sebastian
 */


@WebService(name = "EvaluationRoutingWebServices")
public interface EvaluationRoutingWebServices {
    
    /**
     * get the next evidence based on the users subscriptions
     * @param queueName
     * @param corporateName 
     * @param sessionId
     * @return SituationSendDTO situation DTO
     * @throws ScopixWebServiceException
     */
    SituationSendDTO getNextEvidence(String queueName, String corporateName, long sessionId) throws ScopixWebServiceException;

    /**
     * Sends a list of evidences
     * @param evidenceEvaluations
     * @param corporateName 
     * @param sessionId
     * @throws ScopixWebServiceException
     */
    void sendEvidenceEvaluation(List<EvidenceEvaluationDTO> evidenceEvaluations, String corporateName, long sessionId) 
            throws ScopixWebServiceException;

  
}