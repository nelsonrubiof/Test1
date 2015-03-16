/*
 *
 * Copyright � 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * AcceptAutomaticNewEvidenceCommand.java
 *
 * Created on 06-04-2009, 17:46:21 PM
 *
 */
package com.scopix.periscope.evidencemanagement.commands;

import com.scopix.periscope.evaluationmanagement.dto.AutomaticEvidenceAvailableDTO;
import com.scopix.periscope.evaluationmanagement.services.webservices.EvaluationWebServices;
import com.scopix.periscope.evidencemanagement.Evidence;
import com.scopix.periscope.evidencemanagement.dto.SituationMetricsDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class AcceptAutomaticNewEvidenceCommand {

    private static Logger log = Logger.getLogger(AcceptAutomaticNewEvidenceCommand.class);

//    public void execute(Evidence evidence, EvaluationWebServices webServices, List<HashMap<String, Integer>> situationMetricList,
//            long sessionId) throws ScopixException {
//        notifyToBusinessServices(evidence, webServices, situationMetricList, sessionId);
//    }
    //se elimina chequeo de seguridad, se asume que la solicitud solo la hace sistema
    //long sessionId
    public void execute(Evidence evidence, EvaluationWebServices webServices, List<SituationMetricsDTO> situationMetricList)
            throws ScopixException {
        notifyToBusinessServices(evidence, webServices, situationMetricList);
    }

//    private void notifyToBusinessServices(Evidence evidence, EvaluationWebServices webServices,
//            List<HashMap<String, Integer>> situationMetricList, long sessionId) throws ScopixException {
    //long sessionId
    private void notifyToBusinessServices(Evidence evidence, EvaluationWebServices webServices,
            List<SituationMetricsDTO> situationMetricList) throws ScopixException {
        log.info("start [evidence:" + evidence + "][situationMetricList:" + situationMetricList + "]");
        try {

            AutomaticEvidenceAvailableDTO automaticEvidenceAvailableDTO = new AutomaticEvidenceAvailableDTO();
            automaticEvidenceAvailableDTO.setDeviceId(evidence.getDeviceId());
            automaticEvidenceAvailableDTO.setEvidenceDate(evidence.getEvidenceTimestamp());
            automaticEvidenceAvailableDTO.setPath(evidence.getEvidenceUri());
            automaticEvidenceAvailableDTO.setProcessId(evidence.getProcessId());
            automaticEvidenceAvailableDTO.setEvidenceServicesServerId(evidence.getExtractionPlanDetail().getExtractionPlan().
                    getEvidenceExtractionServicesServer().getServerId());
            automaticEvidenceAvailableDTO.setEvidenceExtractionServicesServerId(evidence.getExtractionPlanDetail().
                    getExtractionPlan().getEvidenceExtractionServicesServer().getIdAtBusinessServices());
//            automaticEvidenceAvailableDTO.setSituationMetricList(situationMetricList);
            List<com.scopix.periscope.evaluationmanagement.dto.SituationMetricsDTO> newSituationMetrics
                    = new ArrayList<com.scopix.periscope.evaluationmanagement.dto.SituationMetricsDTO>();
            for (SituationMetricsDTO st : situationMetricList) {
                com.scopix.periscope.evaluationmanagement.dto.SituationMetricsDTO stDTO
                        = new com.scopix.periscope.evaluationmanagement.dto.SituationMetricsDTO();
                stDTO.setSituationId(st.getSituationId());
                for (Integer mt : st.getMetricIds()) {
                    stDTO.getMetricIds().add(mt);
                }
                newSituationMetrics.add(stDTO);
            }
            automaticEvidenceAvailableDTO.setSituationMetrics(newSituationMetrics);

            webServices.newAutomaticEvidenceAvailable(automaticEvidenceAvailableDTO); //, sessionId
        } catch (ScopixWebServiceException e) {
            throw new ScopixException(e);
        }
        log.info("end");
    }
}
