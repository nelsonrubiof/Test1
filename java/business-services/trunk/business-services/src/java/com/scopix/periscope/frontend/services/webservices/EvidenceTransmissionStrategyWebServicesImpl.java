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
 *  EvidenceTransmitionStrategyWebServicesImpl.java
 * 
 *  Created on Jul 30, 2014, 2:23:20 PM
 * 
 */
package com.scopix.periscope.frontend.services.webservices;

import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.frontend.dto.EvidenceTransmStrategyFEContainerDTO;
import com.scopix.periscope.frontend.dto.EvidenceTransmStrategyFEDTO;
import com.scopix.periscope.frontend.dto.RequestBulkDelEvTransmDTO;
import com.scopix.periscope.frontend.dto.RequestBulkEditNewlEvTransmDTO;
import com.scopix.periscope.frontend.dto.RequestEvidenceTransmStrategyDTO;
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
import org.apache.log4j.Logger;

/**
 *
 * @author Sebastian
 */
@WebService(endpointInterface = "com.scopix.periscope.frontend.services.webservices.EvidenceTransmissionStrategyWebServices")
@SpringBean(rootClass = EvidenceTransmissionStrategyWebServices.class)
@Path("/evidencetransmissionstrategy")
public class EvidenceTransmissionStrategyWebServicesImpl implements EvidenceTransmissionStrategyWebServices {

    private static final Logger log = Logger.getLogger(EvidenceTransmissionStrategyWebServices.class);
    private CorporateStructureManager corporateStructureManager;

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getStrategyById/{id}/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public EvidenceTransmStrategyFEContainerDTO getStrategyById(@PathParam(value = "id") Integer id, @PathParam(value = "sessionId") Long sessionId) {
        try {
            log.info("Proccesing incoming request");
            EvidenceTransmStrategyFEDTO etsDTO = getCorporateStructureManager().getEvidenceTransmStrategyFEDTObyId(id);
            List<EvidenceTransmStrategyFEDTO> list = new ArrayList<EvidenceTransmStrategyFEDTO>();
            list.add(etsDTO);
            EvidenceTransmStrategyFEContainerDTO containerDTO = new EvidenceTransmStrategyFEContainerDTO();
            containerDTO.setEvidenceTransmissionStrategies(list);
            return containerDTO;
        } catch (Exception ex) {
            log.error("An error ocurred processing request", ex);
            EvidenceTransmStrategyFEContainerDTO containerDTO = new EvidenceTransmStrategyFEContainerDTO();
            containerDTO.setError(true);
            containerDTO.setErrorMessage(ex.getMessage());
            return containerDTO;
        }
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getStrategiesByCriteria/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public EvidenceTransmStrategyFEContainerDTO getStrategiesByCriteria(RequestEvidenceTransmStrategyDTO request, @PathParam(value = "sessionId") Long sessionId) {
        try {
            log.info("Proccesing incoming request");
            Integer situationTemplateId = null;
            if (request.getSituationTemplateId() != null && !request.getSituationTemplateId().equals(-1)) {
                situationTemplateId = request.getSituationTemplateId();
            }
            Integer storeId = null;
            if (request.getStoreId() != null && !request.getStoreId().equals(-1)) {
                storeId = request.getStoreId();
            }
            List<EvidenceTransmStrategyFEDTO> list = getCorporateStructureManager().getEvidenceTransmStrategyDTOlist(storeId, situationTemplateId);
            EvidenceTransmStrategyFEContainerDTO containerDTO = new EvidenceTransmStrategyFEContainerDTO();
            containerDTO.setEvidenceTransmissionStrategies(list);
            return containerDTO;
        } catch (Exception ex) {
            log.error("An error ocurred processing request", ex);
            EvidenceTransmStrategyFEContainerDTO containerDTO = new EvidenceTransmStrategyFEContainerDTO();
            containerDTO.setError(true);
            containerDTO.setErrorMessage(ex.getMessage());
            return containerDTO;
        }
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/saveOrUpdateEvidenceTransmissionStrategy/{forceOverWrite}/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponseDTO saveOrUpdateEvidenceTransmissionStrategy(EvidenceTransmStrategyFEDTO etsDTO, @PathParam(value = "forceOverWrite") boolean forceOverWrite, @PathParam(value = "sessionId") Long sessionId) {
        try {
            log.info("Proccesing incoming request");
            List<EvidenceTransmStrategyFEDTO> l = getCorporateStructureManager().getEvidenceTransmStrategyDTOlist(etsDTO.getStore().getId(), etsDTO.getSituationTemplate().getId());
            if (l.size() > 0) {
                if (forceOverWrite) {
                    etsDTO.setId(l.get(0).getId());

                } else {
                    RestResponseDTO response = new RestResponseDTO();
                    response.setError(true);
                    response.setErrorMessage("EXIST");
                    return response;
                }
            }
            getCorporateStructureManager().saveOrUpdateEvTranmStratFromDTO(etsDTO);
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
    @Path("/deleteEvidenceTransmissionStrategy/{evidenceTransmissionStrategyId}/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponseDTO deleteEvidenceTransmissionStrategy(@PathParam(value = "evidenceTransmissionStrategyId") Integer etsId, @PathParam(value = "sessionId") Long sessionId) {
        try {
            log.info("Proccesing incoming request");
            getCorporateStructureManager().deleteEvidenceTransmissionStrategy(etsId);
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/deleteBulkEvidenceTransmissionStrategies/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponseDTO deleteBulkEvidenceTransmissionStrategies(RequestBulkDelEvTransmDTO request, @PathParam(value = "sessionId") Long sessionId) {
        try {
            log.info("Proccesing incoming request");
            getCorporateStructureManager().deleteEvidenceTransmissionStrategies(request.getIds());
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/editNewBulkEvidenceTransmissionStrategies/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponseDTO editNewBulkEvidenceTransmissionStrategies(RequestBulkEditNewlEvTransmDTO request, @PathParam(value = "sessionId") Long sessionId) {
        try {
            log.info("Proccesing incoming request");
            getCorporateStructureManager().createOrEditEvidenceTransmissionStrategies(request.getStoreIds(), request.getSituationTemplateIds(), request.getRegionServerIds());
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
