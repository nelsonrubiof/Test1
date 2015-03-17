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
 * EvaluationWebServicesImpl.java
 *
 * Created on 15-05-2008, 06:20:52 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.services.webservices;

import com.scopix.periscope.evaluationmanagement.EvaluationManager;
import com.scopix.periscope.evaluationmanagement.dto.AutomaticEvidenceAvailableDTO;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceAvailableDTO;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceEvaluationDTO;
import com.scopix.periscope.evaluationmanagement.dto.EvidencesAndProofsDTO;
import com.scopix.periscope.evaluationmanagement.dto.SituationSendDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@WebService(endpointInterface = "com.scopix.periscope.evaluationmanagement.services.webservices.EvaluationWebServices")
//@CustomWebService(serviceClass = EvaluationWebServices.class)
@SpringBean(rootClass = EvaluationWebServices.class)
public class EvaluationWebServicesImpl implements EvaluationWebServices {

    private static Logger log = Logger.getLogger(EvaluationWebServices.class);

    @Override
    //se elimina chequeo de seguridad se asume que el llamdo es desde sistema , long sessionId
    public String newEvidenceAvailable(EvidenceAvailableDTO evidenceAvailable) throws ScopixWebServiceException {
        log.debug("Starting web method");
        try {
            //sessionId
            SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class).acceptNewEvidence(evidenceAvailable);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.debug("Ending web method");
        return "ACK";
    }

    @Override
    public synchronized SituationSendDTO getNextEvidence(String queueName, long sessionId) throws ScopixWebServiceException {
        log.debug("Starting web method");
        SituationSendDTO situationSendDTO = null;
        try {
            situationSendDTO = SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class).
                getNextEvidenceForOperator(queueName, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }

        log.debug("Ending web method");
        return situationSendDTO;
    }

    @Override
    public void sendEvidenceEvaluation(List<EvidenceEvaluationDTO> evidenceEvaluations, long sessionId)
        throws ScopixWebServiceException {
        log.debug("Starting web method");
        try {
            SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class).acceptEvidenceEvaluation(evidenceEvaluations,
                sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.debug("Ending web method");
    }

    @Override
    public List<EvidencesAndProofsDTO> getEvidencesAndProofs(Integer observedMetricId, long sessionId)
        throws ScopixWebServiceException {
        log.debug("Starting web method");
        List<EvidencesAndProofsDTO> list = null;
        try {
            list = SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class).
                getEvidencesAndProofs(observedMetricId, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.debug("Ending web method");
        return list;
    }

    //se elimina chequeo de seguridad se asume que esta llamada es directa desde sistema, long sessionId
    @Override
    public String newAutomaticEvidenceAvailable(AutomaticEvidenceAvailableDTO evidenceAvailable) throws
        ScopixWebServiceException {
        log.debug("Starting web method");
        try {
            //,sessionId
            SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class)
                .acceptNewAutomaticEvidence(evidenceAvailable);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.debug("Ending web method");
        return "ACK";
    }

    /**
     * Return the full path of a evidence.
     *
     * @param observedMetricId
     * @param evidenceId
     * @param sessionId
     * @return
     * @throws ScopixWebServiceException
     */
    @Override
    public String getEvidencesPath(Integer observedMetricId, Integer evidenceId, long sessionId) throws ScopixWebServiceException {
        log.debug("Starting web method");
        String path = null;
        try {
            path = SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class).getEvidencesPath(observedMetricId,
                evidenceId, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.debug("Ending web method");
        return path;
    }

    /**
     * Return the full path of the proof
     *
     * @param proofId Id of the proof
     * @param observedMetricId Id of the observed metric
     * @param withMarks This indicate if the path is withmarks or without marks
     * @param sessionId Id of user session
     * @return String Full path of the proof
     * @throws ScopixWebServiceException
     */
    @Override
    public String getProofsPath(Integer proofId, Integer observedMetricId, boolean withMarks, long sessionId)
        throws ScopixWebServiceException {
        log.debug("Starting web method");
        String path = null;
        try {
            path = SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class).getProofsPath(proofId,
                observedMetricId, withMarks, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.debug("Ending web method");
        return path;
    }

}
