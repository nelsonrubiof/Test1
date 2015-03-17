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
 *  RegionServerFrontendContainerDTO.java
 * 
 *  Created on Jul 30, 2014, 12:24:34 PM
 * 
 */
package com.scopix.periscope.frontend.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@XmlRootElement(name = "RegionServerFrontendContainerDTO")
public class RegionServerFrontendContainerDTO extends RestResponseDTO {

    private List<RegionServerFrontEndDTO> regionServers;

    /**
     * @return the regionServers
     */
    public List<RegionServerFrontEndDTO> getRegionServers() {
        return regionServers;
    }

    /**
     * @param regionServers the regionServers to set
     */
    public void setRegionServers(List<RegionServerFrontEndDTO> regionServers) {
        this.regionServers = regionServers;
    }
}
