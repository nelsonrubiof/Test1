/*
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
 *  ReportingWebServices.java
 *  
 *  Created on 11-01-2011, 03:14:00 PM
 */
package com.scopix.periscope.reporting.services.webservices;

import com.scopix.periscope.corporatestructuremanagement.dto.AreaTypeDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.reporting.dto.UploadProcessDTO;
import com.scopix.periscope.reporting.dto.UploadProcessDetailAddDTO;
import com.scopix.periscope.reporting.dto.UploadProcessDetailDTO;
import java.util.List;
import javax.jws.WebService;

/**
 *
 * @author Nelson Rubio
 * @version 1.0.0
 */
@WebService(name = "ReportingWebServices")
public interface ReportingWebServices {

    /**
     * Retorna un Proceso de subida de datos
     * @param sessionId session del usuario conectado
     * @return UploadProcessDTO con datos para visualizacion
     * @throws ScopixWebServiceException
     */
    UploadProcessDTO getUploadProcess(long sessionId) throws ScopixWebServiceException;

    /**
     * Retona una lista de Store dado la session activa
     * @param sessionId session del usuario conectado
     * @return Lista de Store para la session
     * @throws ScopixWebServiceException
     */
    List<StoreDTO> getStores(long sessionId) throws ScopixWebServiceException;

    /**
     * Retorna la lista de areas para el corporate asignado al Core que se este utilizando
     * @param sessionId session del usuario conectado
     * @return Lista de AreaTypeDTO
     * @throws ScopixWebServiceException
     */
    List<AreaTypeDTO> getAreasType(long sessionId) throws ScopixWebServiceException;

    /**
     * Retorna una lista con detalles para un processo agendado
     * @param sessionId session del usuario conectado
     * @return Lista de detalles UploadProcessDetailDTO
     * @throws ScopixWebServiceException
     */
    List<UploadProcessDetailDTO> getUploadProcessDetail(long sessionId) throws ScopixWebServiceException;

    /**
     *
     * @param storesId
     * @param areasTypeId
     * @param endDate
     * @param sessionId session del usuario conectado
     * @return UploadProcessDetailAddDTO
     * @throws ScopixWebServiceException
     */
    UploadProcessDetailAddDTO addUploadProcessDetail(List<Integer> storesId, List<Integer> areasTypeId, String endDate,
            long sessionId) throws ScopixWebServiceException;

    /**
     *
     * @param updIds
     * @param sessionId session del usuario conectado
     * @return
     * @throws ScopixWebServiceException
     */
    List<UploadProcessDetailDTO> deleteUploadProcessDetail(List<Integer> updIds, long sessionId) throws ScopixWebServiceException;

    /**
     *
     * @param sessionId session del usuario conectado
     * @return
     * @throws ScopixWebServiceException
     */
    UploadProcessDTO uploadNow(long sessionId) throws ScopixWebServiceException;

    /**
     *
     * @param sessionId session del usuario conectado
     * @return
     * @throws ScopixWebServiceException
     */
    UploadProcessDTO cancelUpload(long sessionId) throws ScopixWebServiceException;

    /**
     *
     * @param sessionId session del usuario conectado
     * @return
     * @throws ScopixWebServiceException
     */
    Boolean getStateUploadingAutomatic(long sessionId) throws ScopixWebServiceException;

    /**
     * 
     * @param sessionId session del usuario conectado
     * @throws ScopixWebServiceException
     */
    void enabledUploadingAutomatic(long sessionId) throws ScopixWebServiceException;

    /**
     *
     * @param sessionId session del usuario conectado
     * @throws ScopixWebServiceException
     */
    void disabledUploadingAutomatic(long sessionId) throws ScopixWebServiceException;
}
