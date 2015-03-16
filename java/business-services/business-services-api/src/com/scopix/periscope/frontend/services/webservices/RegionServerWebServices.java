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
 *  RegionServerWebServices.java
 * 
 *  Created on Jul 31, 2014, 10:03:16 AM
 * 
 */
package com.scopix.periscope.frontend.services.webservices;

import com.scopix.periscope.frontend.dto.RegionServerFrontEndDTO;
import com.scopix.periscope.frontend.dto.RegionServerFrontendContainerDTO;
import com.scopix.periscope.frontend.dto.RestResponseDTO;

/**
 *
 * @author Sebastian
 */
public interface RegionServerWebServices {

    /**
     * get all region servers RegionServerFrontendContainerDTO
     *
     * @param sessionId
     * @return RegionServerFrontendContainerDTO
     */
    RegionServerFrontendContainerDTO getShortRegionServers(Long sessionId);

    /**
     *
     * @param id
     * @param sessionId
     * @return RegionServerFrontendContainerDTO
     */
    RegionServerFrontendContainerDTO getRegionServerById(Integer id, Long sessionId);

    /**
     * Saves or updates the desired Evidence Transmission Strategy
     *
     * @param regionServerDTO
     * @param sessionId
     * @return response
     */
    RestResponseDTO saveOrUpdateRegionServer(RegionServerFrontEndDTO regionServerDTO, Long sessionId);

    /**
     * Deletes the desired Evidence Transmission Strategy
     *
     *
     * @param regionServerId
     * @param sessionId
     * @return response
     */
    RestResponseDTO deleteRegionServer(Integer regionServerId, Long sessionId);
}
