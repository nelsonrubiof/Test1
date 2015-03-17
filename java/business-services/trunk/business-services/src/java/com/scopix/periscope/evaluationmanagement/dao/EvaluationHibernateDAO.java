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
 *  EvaluationHibernateDAO.java
 * 
 *  Created on 15-06-2012, 11:12:07 AM
 * 
 */
package com.scopix.periscope.evaluationmanagement.dao;

import java.util.List;

import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.StorePlan;
import com.scopix.periscope.evaluationmanagement.AutomaticEvaluationResult;
import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.evaluationmanagement.Process;
import com.scopix.periscope.evaluationmanagement.dto.AutomaticEvidenceAvailableDTO;
import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.extractionplanmanagement.Situation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.Date;

/**
 *
 * @author nelson
 */
public interface EvaluationHibernateDAO {

    /**
     *
     * @param evidencePath nombre de la evidencia
     * @param evidenceRequestId request asociado
     * @return Evidence evidencia asociada
     */
    Evidence getEvidenceByPath(String evidencePath, Integer evidenceRequestId);

    /**
     *
     * @param evidenceRequest request utilizado para filtro
     * @return EvidenceRequest
     */
    EvidenceRequest getEvidenceRequestForAMetric(EvidenceRequest evidenceRequest);

    /**
     *
     * @param processId Id de processo
     * @param situationTemplateId id situation template
     * @param metricTemplateId id metric template
     * @param evidenceProviderId id de provider
     * @return Metric asociada al filtro null en caso de no existir
     */
    Metric getMetricForAProcessId(Integer processId, Integer situationTemplateId, Integer metricTemplateId,
        Integer evidenceProviderId);

    /**
     * Retorna el id de una metrica si existe para los parametros del filtro
     *
     * @param processId Id de processo
     * @param situationTemplateId id situation template
     * @param metricTemplateId id metric template
     * @param evidenceProviderId id de provider
     * @return Integer id de metrica asociada null en caso de no existir
     */
    Integer getMetricIdForAProcessId(Integer processId, Integer situationTemplateId, Integer metricTemplateId,
        Integer evidenceProviderId);

    /**
     *
     * @return Integer id del siguiente Evidence Request
     */
    Integer getNextIdForEvidenceRequest();

    /**
     *
     * @param processId Id de Processo
     * @param situationTemplateId id situation template
     * @return Situation asociada al filtro
     */
    Situation getSituationForAProcessId(Integer processId, Integer situationTemplateId);

    /**
     *
     * @param evidenceRequest request a almacenar
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    void saveEvidenceRequest(EvidenceRequest evidenceRequest) throws ScopixException;

    /**
     *
     * @param situation objeto a almacenar
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    void saveSituation(Situation situation) throws ScopixException;

    /**
     *
     * @return Integer id para siguiente Situacion
     */
    Integer getNextIdForSituation();

    /**
     *
     * @return Integer id para siguiente Metrica
     */
    Integer getNextIdForMetric();

    /**
     *
     * @param metric objeto a almacenar
     * @throws ScopixException En caso de Excepcion
     */
    void saveMetric(Metric metric) throws ScopixException;

    /**
     * Recupera un process dado el EES que se recibe junto con el ProcessId en ES
     *
     * @param evidenceAvailable evidencia automatica
     * @return Process Objeto asociado
     */
    Process getProcessFromAutomaticEvidenceAvailable(AutomaticEvidenceAvailableDTO evidenceAvailable);

    /**
     *
     * @param areaTypeId id de Area Type
     * @param storeId Store asociado
     * @return Area dado un filtro
     */
    Area getAreaByreaTypeStore(Integer areaTypeId, Integer storeId);

    /**
     *
     * @param observedMetricId Id de ObservedMetric del cual se necesitan los datos
     * @return List<EvidenceEvaluation> lista de evaluaciones para el observedMetric solicitado
     * @throws ScopixException Excepcion en caso de Error
     */
    List<EvidenceEvaluation> getEvidenceEvaluationByObservedMetric(Integer observedMetricId) throws ScopixException;

    /**
     *
     * Returns the automatic evaluation result for an evidence.
     *
     * @param evidenceId
     * @param situationTemplateId
     * @return {@link AutomaticEvaluationResult}
     */
    AutomaticEvaluationResult getAutomaticEvaluationResultByEvidenceIdAndSituationTemplateId(Integer evidenceId,
        Integer situationTemplateId);

    /**
     *
     * @param date
     * @return List<Integer> ids PendingEvaluation
     */
    List<Integer> findAllPendingEvalutionLiveExpired(String date);

    List<StorePlan> getSituationStoreDay(Date date);

    List<StorePlan> getSituationStoreDayReady(Date date);

    List<StorePlan> getEvidenceForStoreDaySituation(Date date);

    List<StorePlan> getEvidenceForStoreDaySituationReal(Date date);

    List<String> getUrlsBee(Date date);
}
