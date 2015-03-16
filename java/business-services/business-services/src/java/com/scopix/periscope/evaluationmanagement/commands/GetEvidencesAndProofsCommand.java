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
 * GetEvidenceAndProofCommand.java
 *
 * Created on 05-11-2008, 10:48:24 AM
 *
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.Proof;
import com.scopix.periscope.evaluationmanagement.dto.EvidencesAndProofsDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.templatemanagement.MetricType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class GetEvidencesAndProofsCommand {

    public List<EvidencesAndProofsDTO> execute(Integer observedMetricId) throws ScopixException {
        List<EvidencesAndProofsDTO> list = new ArrayList<EvidencesAndProofsDTO>();
        GenericDAO genericDAO = HibernateSupport.getInstance().findGenericDAO();
        ObservedMetric observedMetric = genericDAO.get(observedMetricId, ObservedMetric.class);

        MetricType metricType = observedMetric.getMetric().getMetricTemplate().getMetricTypeElement();
        Integer proofId1 = 0;
        Integer proofId2 = 0;
        for (Evidence evidence : observedMetric.getEvidences()) {
            EvidencesAndProofsDTO dto = new EvidencesAndProofsDTO();
            dto.setEvidenceId(evidence.getId());
            dto.setMetricType(metricType.name());
            dto.setCameraName(evidence.getEvidenceRequests().get(0).getEvidenceProvider().getDescription());
            Collections.sort(evidence.getProofs(), Proof.getComparatorByProofOrder());
            for (Proof proof : evidence.getProofs()) {
                if (proof.getEvidenceEvaluation().getObservedMetric().compareTo(observedMetric) == 0 
                        && !proof.getEvidenceEvaluation().isRejected()) {
                    if (metricType.equals(MetricType.COUNTING) || metricType.equals(MetricType.YES_NO) 
                            || metricType.equals(MetricType.NUMBER_INPUT)) {
                        dto.getProofsId().add(proof.getId());
                        dto.getProofsResult().add((proof.getProofResult() != null) ? proof.getProofResult() : -1);
                        dto.getEvaluationTimes().add(0);
                        break;
                    } else if (metricType.equals(MetricType.MEASURE_TIME)) {
                        if (proof.getProofOrder().equals(0)) {
                            proofId1 = proof.getId();
                            dto.getProofsId().add(proof.getId());
                            dto.getProofsResult().add((proof.getProofResult() != null) ? proof.getProofResult() : -1);
                            dto.getEvaluationTimes().add(0);
                        } else if (!dto.getProofsId().isEmpty()) {
                            proofId2 = proof.getId();
                            dto.getProofsId().add(proof.getId());
                            dto.getProofsResult().add((proof.getProofResult() != null) ? proof.getProofResult() : -1);
                            dto.getEvaluationTimes().add(0);
                        } else {
                            proofId2 = proof.getId();
                            dto.getProofsId().add(0);
                            dto.getProofsResult().add(-1);
                            dto.getEvaluationTimes().add(0);
                            dto.getProofsId().add(proof.getId());
                            dto.getProofsResult().add((proof.getProofResult() != null) ? proof.getProofResult() : -1);
                            dto.getEvaluationTimes().add(0);
                        }
                    }

                }
            }

            if (dto.getProofsId().isEmpty()) {
                if (metricType.equals(MetricType.COUNTING) || metricType.equals(MetricType.YES_NO)
                        || metricType.equals(MetricType.NUMBER_INPUT)) {
                    dto.getProofsId().add(0);
                    dto.getProofsResult().add(-1);
                    dto.getEvaluationTimes().add(0);
                } else if (metricType.equals(MetricType.MEASURE_TIME)) {
                    dto.getProofsId().add(0);
                    dto.getProofsResult().add(-1);
                    dto.getEvaluationTimes().add(0);
                    dto.getProofsId().add(0);
                    dto.getProofsResult().add(-1);
                    dto.getEvaluationTimes().add(0);
                }
            }
            list.add(dto);
        }
        if (metricType.equals(MetricType.MEASURE_TIME)) {
            for (EvidencesAndProofsDTO dto : list) {
                dto.getProofsId().set(0, proofId1);
                if (dto.getProofsId().size() == 2) {
                    dto.getProofsId().set(1, proofId2);
                } else {
                    dto.getProofsId().add(proofId2);
                    dto.getProofsResult().add(-1);
                    dto.getEvaluationTimes().add(0);
                }
            }
        }

        Collections.sort(list, EvidencesAndProofsDTO.getComparatorByCameraName());
        return list;
    }
}
