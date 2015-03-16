/*
 * 
 * Copyright 2013 SCOPIX. All rights reserved.
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
 *
 * 
 *
 */
package com.scopix.periscope.evaluation.management;

import com.scopix.periscope.evaluationmanagement.dto.EvidenceEvaluationDTO;
import com.scopix.periscope.evaluationmanagement.dto.SituationSendDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import java.util.List;

/**
 *
 * @author Sebastian
 */


public interface EvaluationManager {
    
    

    /**
     * Get the User subscription list and calls the Evaluation service
     * for the desired corporate and queue if no evidences available on 
     * subscriptions retrieve evidence from the queue the user originally connected
     * get the next evidence based on the users subscriptions
     * @param queueName
     * @param corporateName 
     * @param sessionId
     * @return SituationSendDTO situation DTO
     * @throws ScopixWebServiceException
     */
    SituationSendDTO getNextEvidence(String queueName, String corporateName, long sessionId) throws ScopixWebServiceException;

    /**
     * Get the User subscription list and calls the Evaluation service
     * for the desired corporate and queue if no evidences available on 
     * subscriptions retrieve evidence from the queue the user originally connected
     * Sends a list of evidences
     * @param evidenceEvaluations
     * @param corporateName 
     * @param sessionId
     * @throws ScopixWebServiceException
     */
    void sendEvidenceEvaluation(List<EvidenceEvaluationDTO> evidenceEvaluations, String corporateName, long sessionId) 
            throws ScopixWebServiceException;
    
}
