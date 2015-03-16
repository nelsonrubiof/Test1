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
 *  FrontEndWebServices.java
 * 
 *  Created on Jul 29, 2014, 2:47:16 PM
 * 
 */
package com.scopix.periscope.frontend.services.webservices;

import com.scopix.periscope.frontend.dto.SituationTemplateFEContainerDTO;
import com.scopix.periscope.frontend.dto.StoreFrontEndContainerDTO;
import javax.jws.WebService;

/**
 *
 * @author Sebastian
 */
@WebService(name = "FrontEndWebServices")
public interface FrontEndWebServices {

    /**
     * get all Stores
     *
     * @param sessionId
     * @return StoreFrontEndContainerDTO
     */
    StoreFrontEndContainerDTO getStores(Long sessionId);

    /**
     * get all SituationTemplates
     *
     * @param sessionId
     * @return SituationTemplateFEContainerDT
     */
    SituationTemplateFEContainerDTO getSituationTemplates(Long sessionId);
}
