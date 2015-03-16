
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

import com.scopix.periscope.evaluation.management.EvaluationManager;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceEvaluationDTO;
import com.scopix.periscope.evaluationmanagement.dto.SituationSendDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;

import javax.jws.WebService;
import org.apache.log4j.Logger;

/**
 *
 * @author Sebastian
 */
@WebService(endpointInterface = 
        "com.scopix.periscope.evaluationroutingmanagement.services.webservices.EvaluationRoutingWebServices")
@SpringBean(rootClass = EvaluationRoutingWebServices.class)
public class EvaluationRoutingWebServicesImpl implements EvaluationRoutingWebServices {

        private static Logger log = Logger.getLogger(EvaluationRoutingWebServicesImpl.class);

    /**
     * Returns a situation dto to be analized
     * @param queueName
     * @param corporateName
     * @param sessionId
     * @return SituationSendDTOnext situation to be analyzed
     * @throws ScopixWebServiceException
     */
    @Override
    public SituationSendDTO getNextEvidence(String queueName, String corporateName, long sessionId) 
            throws ScopixWebServiceException {
        log.info("Starting web method getNextEvidence for corporate: " + corporateName);
        SituationSendDTO result = SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class)
                .getNextEvidence(queueName, corporateName, sessionId);
        log.info("Ending web method");
        return result; 
    }

    /**
     * receives evaluation to be processed
     * @param evidenceEvaluations
     * @param corporateName
     * @param sessionId
     * @throws ScopixWebServiceException
     */
    @Override
    public void sendEvidenceEvaluation(List<EvidenceEvaluationDTO> evidenceEvaluations, String corporateName, long sessionId)
            throws ScopixWebServiceException {
        log.info("Starting web method sendEvidenceEvaluation for corporate: " + corporateName);
        SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class)
                .sendEvidenceEvaluation(evidenceEvaluations, corporateName, sessionId);
        log.info("Ending web method");
    }

}

