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
 * EvaluationWebServices.java
 *
 * Created on 15-05-2008, 06:20:52 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.services.webservices;

import com.scopix.periscope.evaluationmanagement.dto.AutomaticEvidenceAvailableDTO;
import com.scopix.periscope.evaluationmanagement.dto.EvidencesAndProofsDTO;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceAvailableDTO;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceEvaluationDTO;
import com.scopix.periscope.evaluationmanagement.dto.SituationSendDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import java.util.List;
import javax.jws.WebService;

/**
 *
 * @author Cesar Abarza Suazo.
 */
@WebService(name = "EvaluationWebServices")
public interface EvaluationWebServices {

    //se elimina chequeo de seguridad se asume que todas las llamadas a este servicio son realizadas desde el sistema
    //, long sessionId
    String newEvidenceAvailable(EvidenceAvailableDTO evidenceAvailable) throws ScopixWebServiceException;

    //se elimina chequeo de seguridad se asume que todas las llamadas a este servicio son realizadas desde el sistema
    //, long sessionId
    String newAutomaticEvidenceAvailable(AutomaticEvidenceAvailableDTO evidenceAvailable) throws
            ScopixWebServiceException;

    SituationSendDTO getNextEvidence(String queueName, long sessionId) throws ScopixWebServiceException;

    void sendEvidenceEvaluation(List<EvidenceEvaluationDTO> evidenceEvaluations, long sessionId) throws ScopixWebServiceException;

    List<EvidencesAndProofsDTO> getEvidencesAndProofs(Integer observedMetricId, long sessionId) throws ScopixWebServiceException;

    String getEvidencesPath(Integer observedMetricId, Integer evidenceId, long sessionId) throws ScopixWebServiceException;

    String getProofsPath(Integer proofId, Integer observedMetricId, boolean withMarks, long sessionId)
            throws ScopixWebServiceException;
}
