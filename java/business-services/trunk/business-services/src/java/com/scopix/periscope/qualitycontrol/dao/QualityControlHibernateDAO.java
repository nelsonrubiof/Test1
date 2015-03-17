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
 *  QualityControlHibernateDAO.java
 * 
 *  Created on 24-11-2011, 12:36:36 PM
 * 
 */
package com.scopix.periscope.qualitycontrol.dao;

import com.scopix.periscope.evaluationmanagement.dto.EvidenceDTO;
import com.scopix.periscope.evaluationmanagement.dto.ProofDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.qualitycontrol.dto.EvidenceFinishedDTO;
import com.scopix.periscope.qualitycontrol.dto.MetricResultDTO;
import com.scopix.periscope.qualitycontrol.dto.ObservedMetricResultDTO;
import com.scopix.periscope.qualitycontrol.dto.ObservedSituationFinishedDTO;
import com.scopix.periscope.queuemanagement.dto.FilteringData;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author nelson
 */
public interface QualityControlHibernateDAO {

    List<ObservedMetricResultDTO> getEvidenceFinishedList(FilteringData filter) throws ScopixException;

    List<ObservedMetricResultDTO> getEvidenceFinishedListLowes(FilteringData filter) throws ScopixException;

    List<EvidenceFinishedDTO> getEvidenceFinishedList(Date start, Date end, boolean rejected) throws ScopixException;

    List<ProofDTO> getProofPerEvidenceEvaluation(int evidenceEvaluationId) throws ScopixException;

    List<ObservedSituationFinishedDTO> getObservedSituationFinishedList(FilteringData filters);

    List<EvidenceDTO> getEvidencesByMetric(Integer metricId, Integer situationFinishId);

    EvidenceDTO getEvidenceDTO(Integer evidenceId, Integer evidenceEvaluationId);

    void rejectsEvaluationsByObservedSituation(Set<Integer> observedSituationIds, String comments) throws ScopixException;

    List<MetricResultDTO> getMetricResultByObservedSituation(Integer situationFinishedId);

    void rejectsEvaluations(List<Integer> observedMetricIds, String comments) throws ScopixException;
}
