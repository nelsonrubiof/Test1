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
 *  TransformToRegionServerFrontEndDTO.java
 * 
 *  Created on Jul 31, 2014, 11:06:52 AM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.evaluationmanagement.RegionServer;
import com.scopix.periscope.frontend.dto.RegionServerFrontEndDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class TransformToRegionServerFrontEndDTO {

    /**
     * Converts a list of RegionServers to a list of RegionServerFrontEndDTOs
     *
     * @param regionServers
     * @return List<RegionServerFrontEndDTO>
     */
    public List<RegionServerFrontEndDTO> execute(List<RegionServer> regionServers, boolean fullDTO) {
        if (regionServers == null) {
            return null;
        }
        List<RegionServerFrontEndDTO> regionServerModels = new ArrayList<RegionServerFrontEndDTO>();
        for (RegionServer regionServer : regionServers) {
            if (fullDTO) {
                regionServerModels.add(regionServerModelDTORowMapperFull(regionServer));
            } else {
                regionServerModels.add(regionServerModelDTORowMapper(regionServer));
            }
        }
        return regionServerModels;
    }

    /**
     * Converts a RegionServers to a RegionServerFrontEndDTOs
     *
     * @param regionServer
     * @return RegionServerFrontEndDTO
     */
    public RegionServerFrontEndDTO execute(RegionServer regionServer, boolean fullDTO) {
        if (regionServer == null) {
            return null;
        }
        if (fullDTO) {
            return regionServerModelDTORowMapperFull(regionServer);
        }

        return regionServerModelDTORowMapper(regionServer);
    }

    private RegionServerFrontEndDTO regionServerModelDTORowMapper(RegionServer regionServer) {
        RegionServerFrontEndDTO regionServerModelDTO = new RegionServerFrontEndDTO();
        regionServerModelDTO.setId(regionServer.getId());
        regionServerModelDTO.setCodeName(regionServer.getCodeName());
        regionServerModelDTO.setActive(regionServer.isActive());
        return regionServerModelDTO;
    }

    private RegionServerFrontEndDTO regionServerModelDTORowMapperFull(RegionServer regionServer) {
        RegionServerFrontEndDTO regionServerModelDTO = new RegionServerFrontEndDTO();
        regionServerModelDTO.setId(regionServer.getId());
        regionServerModelDTO.setActive(regionServer.isActive());
        regionServerModelDTO.setCodeName(regionServer.getCodeName());
        regionServerModelDTO.setServerIp(regionServer.getServerIp());
        regionServerModelDTO.setsFTPIp(regionServer.getsFTPIp());
        regionServerModelDTO.setsFTPPath(regionServer.getsFTPPath());
        regionServerModelDTO.setsFTPUser(regionServer.getsFTPUser());
        return regionServerModelDTO;
    }
}
