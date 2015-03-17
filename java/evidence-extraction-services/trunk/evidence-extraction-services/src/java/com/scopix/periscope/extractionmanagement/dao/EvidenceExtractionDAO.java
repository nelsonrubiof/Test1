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
 *  EvidenceExtractionDAO.java
 * 
 *  Created on 27-03-2012, 01:02:22 PM
 * 
 */
package com.scopix.periscope.extractionmanagement.dao;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.RequestTimeZone;
import com.scopix.periscope.extractionmanagement.ScopixJob;
import com.scopix.periscope.extractionmanagement.ScopixListenerJob;
import com.scopix.periscope.extractionmanagement.SituationMetricExtractionRequest;
import com.scopix.periscope.extractionmanagement.SituationRequest;
import com.scopix.periscope.extractionmanagement.SituationRequestRange;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 *
 * @author nelson
 */
public interface EvidenceExtractionDAO {

    /**
     * Obtiene las situaciones a partir del ID de la camara.
     *
     * @param camId ID de la camara
     * @return Lista de situaciones
     * @throws ScopixException
     */
    List<SituationRequest> findSituationsForAEvidenceProvider(String camId) throws ScopixException;

    /**
     * Obtiene las situaciones a partir del ID del sensor.
     *
     * @param sensorId ID del sensor
     * @return Lista de situaciones
     * @throws ScopixException
     */
    List<SituationRequest> findSituationsForASensor(String sensorId) throws ScopixException;

    /**
     * Obtiene un numero unico que servira de identificacion para el proceso de evidencia automatica
     *
     * @return numero de identificacion
     * @throws ScopixException
     */
    Integer getProcessId() throws ScopixException;

    List<SituationMetricExtractionRequest> getSituationMetricExtractionRequest(Integer erId) throws ScopixException;

    /**
     * Retorna una lista de nombres de archivos ordenadas segun priorizacion y fecha
     * @param fileNames
     * @return List<String>
     */
    List<String> getEvidenceExtractionRequestByPriorization(Vector<String> fileNames); //se elimina , String storeName

    /**
     * Obtiene las situaciones a partir del ID del sensor y una fecha .
     *
     * @param sensorId del sensor
     * @param date fecha
     * @return Lista de situaciones
     * @throws ScopixException
     */
    List<SituationRequest> findSituationsForASensor(String sensorId, Date date) throws ScopixException;

    /**
     * Obtiene las situaciones a partir del ID de la camara, Date.
     *
     * @param camId ID de la camara
     * @param date fecha evididencia
     * @return Lista de situaciones
     * @throws ScopixException
     */
    List<SituationRequest> findSituationsForAEvidenceProvider(String camId, Date date) throws ScopixException;

    List<ScopixJob> getAllJobs();

    List<EvidenceExtractionRequest> getEvidenceExtractionRequestByRequestTimeZone(List<RequestTimeZone> requestTimeZones,
            Integer dayOfWeek) throws ScopixException;

    List<ScopixJob> getRealRandomJobs();

	List<ScopixListenerJob> getListenerJobs(String storeName);

    List<SituationRequestRange> getSituationRequestRangeRealRandomByRequestTimeZone(
            List<RequestTimeZone> requestTimeZones, Integer dayOfWeek);

}
