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
 *  TransformToRegionServerCommand.java
 * 
 *  Created on Aug 12, 2014, 11:03:10 AM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.evaluationmanagement.RegionServer;
import com.scopix.periscope.frontend.dto.RegionServerFrontEndDTO;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 *
 * @author Sebastian
 */
public class TransformToRegionServerCommand {

    private GenericDAO genericDAO;

    /**
     * Based on a given Region Server DTO it will create or update a Region
     * Server Object
     *
     * @param regionServerDTO
     * @return RegionServer
     */
    public RegionServer execute(RegionServerFrontEndDTO regionServerDTO) {

        RegionServer regionServer;

        if (regionServerDTO.getId() != null) {
            regionServer = getGenericDAO().get(regionServerDTO.getId(), RegionServer.class);
        } else {
            regionServer = new RegionServer();
        }
        regionServer.setCodeName(regionServerDTO.getCodeName());
        regionServer.setsFTPIp(regionServerDTO.getsFTPIp());
        regionServer.setsFTPUser(regionServerDTO.getsFTPUser());
        if (regionServerDTO.getsFTPPassword() != null && !regionServerDTO.getsFTPPassword().isEmpty()) {
            regionServer.setsFTPPassword(regionServerDTO.getsFTPPassword());
        }
        regionServer.setsFTPPath(regionServerDTO.getsFTPPath());
        regionServer.setServerIp(regionServerDTO.getServerIp());
        regionServer.setActive(regionServerDTO.isActive());
        return regionServer;

    }

    /**
     * @return the genericDAO
     */
    public GenericDAO getGenericDAO() {
        if (genericDAO == null) {
            genericDAO = HibernateSupport.getInstance().findGenericDAO();
        }
        return genericDAO;
    }

    /**
     * @param genericDAO the genericDAO to set
     */
    public void setGenericDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }
}
