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
 *  RegionServerWebServicesImpl.java
 * 
 *  Created on Jul 31, 2014, 10:16:02 AM
 * 
 */
package com.scopix.periscope.frontend.services.webservices;

import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.frontend.dto.RegionServerFrontEndDTO;
import com.scopix.periscope.frontend.dto.RegionServerFrontendContainerDTO;
import com.scopix.periscope.frontend.dto.RestResponseDTO;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 *
 * @author Sebastian
 */
@WebService(endpointInterface = "com.scopix.periscope.frontend.services.webservices.RegionServerWebServices")
@SpringBean(rootClass = RegionServerWebServices.class)
@Path("/regionserver")
public class RegionServerWebServicesImpl implements RegionServerWebServices {

    private CorporateStructureManager corporateStructureManager;
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RegionServerWebServices.class);

    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getShortRegionServers/{sessionId}")
    public RegionServerFrontendContainerDTO getShortRegionServers(@PathParam(value = "sessionId") Long sessionId) {
        log.info("Proccesing incoming request");
        try {
            List<RegionServerFrontEndDTO> list = getCorporateStructureManager().getRegionServerFrontEndDTOList(false);
            RegionServerFrontendContainerDTO rsfcdto = new RegionServerFrontendContainerDTO();
            rsfcdto.setRegionServers(list);
            return rsfcdto;
        } catch (Exception ex) {
            log.error("An error ocurred processing request", ex);
            RegionServerFrontendContainerDTO containerDTO = new RegionServerFrontendContainerDTO();
            containerDTO.setError(true);
            containerDTO.setErrorMessage(ex.getMessage());
            return containerDTO;
        }
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getRegionServerById/{id}/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public RegionServerFrontendContainerDTO getRegionServerById(@PathParam(value = "id") Integer id, @PathParam(value = "sessionId") Long sessionId) {
        log.info("Proccesing incoming request");
        try {
            RegionServerFrontEndDTO rsfedto = getCorporateStructureManager().getRegionServerDTOById(id);
            List<RegionServerFrontEndDTO> list = new ArrayList<RegionServerFrontEndDTO>();
            list.add(rsfedto);
            RegionServerFrontendContainerDTO rsfcdto = new RegionServerFrontendContainerDTO();
            rsfcdto.setRegionServers(list);
            return rsfcdto;
        } catch (Exception ex) {
            log.error("An error ocurred processing request", ex);
            RegionServerFrontendContainerDTO containerDTO = new RegionServerFrontendContainerDTO();
            containerDTO.setError(true);
            containerDTO.setErrorMessage(ex.getMessage());
            return containerDTO;
        }
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/saveOrUpdateRegionServer/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponseDTO saveOrUpdateRegionServer(RegionServerFrontEndDTO regionServerDTO, @PathParam(value = "sessionId") Long sessionId) {
        try {
            log.info("Proccesing incoming request");
            getCorporateStructureManager().saveOrUpdateRegionServerFromDTO(regionServerDTO);
            RestResponseDTO response = new RestResponseDTO();
            return response;
        } catch (Exception ex) {
            log.error("An error ocurred processing request", ex);
            RestResponseDTO containerDTO = new RestResponseDTO();
            containerDTO.setError(true);
            containerDTO.setErrorMessage(ex.getMessage());
            return containerDTO;
        }
    }

    @Override
    @POST
    @Path("/deleteRegionServer/{regionServerId}/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponseDTO deleteRegionServer(@PathParam(value = "regionServerId") Integer regionServerId, @PathParam(value = "sessionId") Long sessionId) {
        try {
            log.info("Proccesing incoming request");
            getCorporateStructureManager().deleteRegionServerById(regionServerId);
            RestResponseDTO response = new RestResponseDTO();
            return response;
        } catch (Exception ex) {
            log.error("An error ocurred processing request", ex);
            RestResponseDTO containerDTO = new RestResponseDTO();
            containerDTO.setError(true);
            containerDTO.setErrorMessage(ex.getMessage());
            return containerDTO;
        }
    }

    /**
     * @return the corporateStructureManager
     */
    public CorporateStructureManager getCorporateStructureManager() {
        if (corporateStructureManager == null) {
            corporateStructureManager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        }
        return corporateStructureManager;
    }

    /**
     * @param corporateStructureManager the corporateStructureManager to set
     */
    public void setCorporateStructureManager(CorporateStructureManager corporateStructureManager) {
        this.corporateStructureManager = corporateStructureManager;
    }
}
