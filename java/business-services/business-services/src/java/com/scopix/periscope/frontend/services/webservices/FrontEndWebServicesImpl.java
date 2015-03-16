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
 *  FrontEndWebServicesImpl.java
 * 
 *  Created on Jul 29, 2014, 2:54:21 PM
 * 
 */
package com.scopix.periscope.frontend.services.webservices;

import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.frontend.dto.SituationTemplateFEContainerDTO;
import com.scopix.periscope.frontend.dto.SituationTemplateFrontEndDTO;
import com.scopix.periscope.frontend.dto.StoreFrontEndContainerDTO;
import com.scopix.periscope.frontend.dto.StoreFrontEndDTO;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Sebastian
 */
@WebService(endpointInterface = "com.scopix.periscope.frontend.services.webservices.FrontEndWebServices")
@SpringBean(rootClass = FrontEndWebServices.class)
@Path("/frontend")
public class FrontEndWebServicesImpl implements FrontEndWebServices {

    private CorporateStructureManager corporateStructureManager;
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(FrontEndWebServices.class);

    @Override
    @POST
    @Path("/getStores/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public StoreFrontEndContainerDTO getStores(@PathParam(value = "sessionId") Long sessionId) {
        log.info("Proccesing incoming request");
        try {
            List<StoreFrontEndDTO> list = getCorporateStructureManager().getStoreFrontEndDTOs();
            StoreFrontEndContainerDTO sfedtoc = new StoreFrontEndContainerDTO();
            sfedtoc.setStoreDTOs(list);
            return sfedtoc;
        } catch (Exception ex) {
            log.error("An error ocurred processing request", ex);
            StoreFrontEndContainerDTO containerDTO = new StoreFrontEndContainerDTO();
            containerDTO.setError(true);
            containerDTO.setErrorMessage(ex.getMessage());
            return containerDTO;
        }
    }

    @Override
    @POST
    @Path("/getSituationTemplates/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public SituationTemplateFEContainerDTO getSituationTemplates(@PathParam(value = "sessionId") Long sessionId) {
        log.info("Proccesing incoming request");
        try {
            List<SituationTemplateFrontEndDTO> list = getCorporateStructureManager().getSituationTemplateFrontEndDTOList();
            SituationTemplateFEContainerDTO sfedtoc = new SituationTemplateFEContainerDTO();
            sfedtoc.setSituationTemplateDTOs(list);
            return sfedtoc;
        } catch (Exception ex) {
            log.error("An error ocurred processing request", ex);
            SituationTemplateFEContainerDTO containerDTO = new SituationTemplateFEContainerDTO();
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
