/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  GetEvidenceEvaluationDTOListCommand.java
 * 
 *  Created on 19-03-2014, 04:42:17 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.evaluationmanagement.Proof;
import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAO;
import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAOImpl;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceEvaluationDTO;
import com.scopix.periscope.evaluationmanagement.dto.ProofDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Nelson
 * @version 1.0.0
 */
public class GetEvidenceEvaluationDTOListCommand {
    
    private static final Logger log = Logger.getLogger(GetEvidenceEvaluationDTOListCommand.class);
    private EvaluationHibernateDAO dao;
    
    public List<EvidenceEvaluationDTO> execute(Integer observedMetricId) throws ScopixException {
        log.info("start [observedMetricId:" + observedMetricId + "]");
        
        List<EvidenceEvaluation> evaluations = getDao().getEvidenceEvaluationByObservedMetric(observedMetricId);
        
        List<EvidenceEvaluationDTO> evaluationDTOs = transformEvidenceEvaluationToDTO(evaluations);
        log.info("start [dtos.size:" + evaluationDTOs.size() + "]");
        return evaluationDTOs;
    }

    /**
     * @return the dao
     */
    public EvaluationHibernateDAO getDao() {
        if (dao == null) {
            dao = HibernateSupport.getInstance().findDao(EvaluationHibernateDAOImpl.class);
        }
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(EvaluationHibernateDAO dao) {
        this.dao = dao;
    }
    
    private List<EvidenceEvaluationDTO> transformEvidenceEvaluationToDTO(List<EvidenceEvaluation> evaluations) {
        log.info("start");
        List<EvidenceEvaluationDTO> evaluationDTOs = new ArrayList<EvidenceEvaluationDTO>();
        for (EvidenceEvaluation ee : evaluations) {
            EvidenceEvaluationDTO dto = new EvidenceEvaluationDTO();
            dto.setId(ee.getId());
            dto.setMetricId(ee.getObservedMetric().getMetric().getId());
            dto.setCantDoReason(ee.getCantDoReason());
            dto.setEvidenceEvaluationResult(ee.getEvidenceResult());
            dto.setPendingEvaluationId(ee.getObservedMetric().getObservedSituation().getPendingEvaluation().getId());
            dto.setInitEvaluation(ee.getInitEvaluation());
            dto.setEndEvaluation(ee.getEndEvaluation());
            dto.setEvaluationTimeInSeconds(ee.getEvaluationTimeInSeconds());
            for (Evidence e : ee.getEvidences()) {
                dto.getEvidenceIds().add(e.getId());
            }
            for (Proof proof : ee.getProofs()) {
                ProofDTO proofDTO = new ProofDTO();
                proofDTO.setProofId(proof.getId());
                proofDTO.setPathWithMarks(proof.getPathWithMarks());                
                proofDTO.setPathWithoutMarks(proof.getPathWithoutMarks());
                proofDTO.setOrder(proof.getProofOrder());
                proofDTO.setEvidenceId(proof.getEvidence().getId());
                proofDTO.setProofResult(proof.getProofResult());
                dto.getProofs().add(proofDTO);
            }
            evaluationDTOs.add(dto);
        }
        log.info("end");
        return evaluationDTOs;
    }
    
}
