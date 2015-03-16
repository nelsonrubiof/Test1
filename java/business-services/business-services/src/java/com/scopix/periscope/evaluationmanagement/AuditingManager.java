/*
 * 
 * Copyright ? 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * EvaluatorManager.java
 *
 * Created on 27-03-2008, 02:33:29 PM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.evaluationmanagement.commands.GetEvidenceCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetObservedMetricCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetProofCommand;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.FileUtils;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SystemConfig;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author C?sar Abarza Suazo
 */
@SpringBean(rootClass = AuditingManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class AuditingManager {

    private static Logger log = Logger.getLogger(AuditingManager.class);

    public String getEvidencePath(Integer evidenceId, Integer observedMetricId) throws ScopixException {
        log.info("start");
        GetEvidenceCommand getEvidenceCommand = new GetEvidenceCommand();
        GetObservedMetricCommand getObservedMetricCommand = new GetObservedMetricCommand();

        Evidence evidence = getEvidenceCommand.execute(evidenceId);
        ObservedMetric observedMetric = getObservedMetricCommand.execute(observedMetricId);

        String evidencePath = null;
        String separator = (evidence.getEvidenceServicesServer().getEvidencePath().indexOf("/") >= 0) ? "/" : "\\";
        if (evidence.getFlvPath() != null && evidence.getFlvPath().length() > 0) {
            //get the flv evidence path
            evidencePath = evidence.getEvidenceServicesServer().getEvidencePath();
            evidencePath += observedMetric.getMetric().getArea().getStore().getCorporate().getName();
            evidencePath += separator + observedMetric.getMetric().getArea().getStore().getName();

            //the new directory structure is:  yyyy/MM/dd                   (20100727)
            evidencePath += separator + FileUtils.getPathFromDate(evidence.getEvidenceDate(), separator);
            evidencePath += separator + evidence.getFlvPath();
        } else {
            String baseUrl = SystemConfig.getStringParameter("CORPORATE_PATH");
            evidencePath = baseUrl + SystemConfig.getStringParameter("scopix.no.evidence");
        }
        log.info("end, evidence path = " + evidencePath);
        return evidencePath;
    }

    public String getProofPath(Integer proofId, Integer observedMetricId, boolean withMarks) throws ScopixException {
        log.info("start");
        GetProofCommand getProofCommand = new GetProofCommand();
        GetObservedMetricCommand getObservedMetricCommand = new GetObservedMetricCommand();
        GetEvidenceCommand getEvidenceCommand = new GetEvidenceCommand();
        Proof proof = null;
        if (proofId > 0) {
            proof = getProofCommand.execute(proofId);
        }
        ObservedMetric observedMetric = getObservedMetricCommand.execute(observedMetricId);
        String evidencePath = null;
        if (proof != null) {
            //Evidence evidenceAux = getEvidenceCommand.execute(proof.getEvidenceEvaluation().getEvidences().get(0).getId());
            Evidence evidenceAux = getEvidenceCommand.execute(proof.getEvidence().getId());
            //get the proof path
            String evidenceServicesServerPath = evidenceAux.getEvidenceServicesServer().getProofPath();
            String separator = (evidenceServicesServerPath.indexOf("/") >= 0) ? "/" : "\\";

            //the new directory structure is:  yyyy/MM/dd                   (20100727)
            evidencePath = evidenceServicesServerPath;
            evidencePath += observedMetric.getMetric().getArea().getStore().getCorporate().getName();
            evidencePath += separator + observedMetric.getMetric().getArea().getStore().getName();
            evidencePath += separator + FileUtils.getPathFromDate(evidenceAux.getEvidenceDate(), separator);
            if (withMarks) {
                if (proof.getPathWithMarks() != null && proof.getPathWithMarks().length() > 0) {
                    evidencePath += separator + proof.getPathWithMarks();
                } else {
                    String baseUrl = SystemConfig.getStringParameter("CORPORATE_PATH");
                    evidencePath = baseUrl + SystemConfig.getStringParameter("scopix.no.proof");
                }
            } else {
                if (proof.getPathWithoutMarks() != null && proof.getPathWithoutMarks().length() > 0) {
                    evidencePath += separator + proof.getPathWithoutMarks();
                } else {
                    String baseUrl = SystemConfig.getStringParameter("CORPORATE_PATH");
                    evidencePath = baseUrl + SystemConfig.getStringParameter("scopix.no.proof");
                }
            }
        } else {
            if (observedMetric.getEvidences() != null && !observedMetric.getEvidences().isEmpty()) {
                String baseUrl = SystemConfig.getStringParameter("CORPORATE_PATH");
                evidencePath = baseUrl + SystemConfig.getStringParameter("scopix.no.proof");
            }
        }
        if (evidencePath != null) {
            evidencePath = evidencePath.replace('\\', '/');
        }
        log.debug("end, proof path = " + evidencePath);
        return evidencePath;
    }
}
