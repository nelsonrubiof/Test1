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
 * QualityControlWebServices.java
 *
 * Created on 04-06-2008, 04:04:37 PM
 *
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.qualitycontrol.services.webservices;

import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.qualitycontrol.dto.EvidenceFinishedDTO;
import com.scopix.periscope.qualitycontrol.dto.MetricResultDTO;
import com.scopix.periscope.qualitycontrol.dto.MotivoRejectedDTO;
import com.scopix.periscope.qualitycontrol.dto.ObservedMetricResultDTO;
import com.scopix.periscope.qualitycontrol.dto.QualityEvaluationDTO;
import com.scopix.periscope.qualitycontrol.dto.ObservedSituationFinishedDTO;
import com.scopix.periscope.queuemanagement.dto.FilteringData;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;

/**
 *
 * @author Cesar Abarza Suazo.
 */
@WebService(name = "QualityControlWebServices")
public interface QualityControlWebServices {

    List<StoreDTO> getStores(long sessionId) throws ScopixWebServiceException;

    List<ObservedMetricResultDTO> getEvidenceFinished(FilteringData filters, long sessionId) throws ScopixWebServiceException;

    void rejectsEvaluations(List<Integer> observedMetricIds, String comments, long sessionId) throws ScopixWebServiceException;

    List<EvidenceFinishedDTO> getEvidenceFinishedList(Date start, Date end, boolean rejected, long sessionId) throws
            ScopixWebServiceException;

//    Se comenta ya que los reportes se manejan desde fuera con Tableau  
//    byte[] getQualityControlReport(Date start, Date end, boolean rejected, ReportType reportType, long sessionId) throws
//            ScopixWebServiceException;

    /**
     * Nuevo Quality Flex
     */
    List<ObservedSituationFinishedDTO> getObservedSituationFinished(FilteringData filters, long sessionId)
            throws ScopixWebServiceException;

    List<MetricResultDTO> getMetricResultByObservedSituation(Integer situationFinishedId, long sessionId)
            throws ScopixWebServiceException;

    List<EvidenceDTO> getEvidenceDTOByMetricResult(Integer metricResultId, Integer situationFinishedId, long sessionId)
            throws ScopixWebServiceException;

    List<MotivoRejectedDTO> getMotivosRejected(long sessionId) throws ScopixWebServiceException;

    /**
     * Almacena un resultado de una evaluacion hecha por Quality
     *
     * @param evaluation
     * @param sessionId
     * @throws ScopixWebServiceException
     */
    void saveEvaluations(List<QualityEvaluationDTO> evaluations, long sessionId) throws ScopixWebServiceException;
}
