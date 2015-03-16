/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
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
 *  EvidenceTransmissionStrategyWebServices.java
 * 
 *  Created on Jul 30, 2014, 1:02:22 PM
 * 
 */
package com.scopix.periscope.frontend.services.webservices;

import com.scopix.periscope.frontend.dto.EvidenceTransmStrategyFEContainerDTO;
import com.scopix.periscope.frontend.dto.EvidenceTransmStrategyFEDTO;
import com.scopix.periscope.frontend.dto.RequestBulkDelEvTransmDTO;
import com.scopix.periscope.frontend.dto.RequestBulkEditNewlEvTransmDTO;
import com.scopix.periscope.frontend.dto.RequestEvidenceTransmStrategyDTO;
import com.scopix.periscope.frontend.dto.RestResponseDTO;
import javax.jws.WebService;

/**
 *
 * @author Sebastian
 */
@WebService(name = "EvidenceTransmissionStrategyWebServices")
public interface EvidenceTransmissionStrategyWebServices {

    /**
     * gets EvidenceTransmStrategyFEDTO By id
     *
     * @param id
     * @param sessionId
     * @return EvidenceTransmStrategyFEContainerDTO
     */
    EvidenceTransmStrategyFEContainerDTO getStrategyById(Integer id, Long sessionId);

    /**
     * Gets all evidences transmission strategies based on a given criteria
     *
     * @param request
     * @param sessionId
     * @return EvidenceTransmStrategyFEContainerDTO
     */
    EvidenceTransmStrategyFEContainerDTO getStrategiesByCriteria(RequestEvidenceTransmStrategyDTO request, Long sessionId);

    /**
     * Saves or updates the desired Evidence Transmission Strategy
     *
     * @param etsDTO
     * @param forceOverWrite
     * @param sessionId
     * @return response
     */
    RestResponseDTO saveOrUpdateEvidenceTransmissionStrategy(EvidenceTransmStrategyFEDTO etsDTO, boolean forceOverWrite, Long sessionId);

    /**
     * Deletes the desired Evidence Transmission Strategy
     *
     * @param etsId
     * @param sessionId
     * @return response
     */
    RestResponseDTO deleteEvidenceTransmissionStrategy(Integer etsId, Long sessionId);

    /**
     * Deletes requested evidences transmission strategies given a set of ids
     *
     * @param request
     * @param sessionId
     * @return RestResponseDTO
     */
    RestResponseDTO deleteBulkEvidenceTransmissionStrategies(RequestBulkDelEvTransmDTO request, Long sessionId);

    
    
    /**
     * Create or Edit desired Evidence Transmission Strategies
     * @param request
     * @param sessionId
     * @return RestResponseDTO 
     */
    RestResponseDTO editNewBulkEvidenceTransmissionStrategies(RequestBulkEditNewlEvTransmDTO request, Long sessionId);
}
