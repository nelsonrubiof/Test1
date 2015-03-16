/*
 * 
 * Copyright (c) 2007, SCopyright (c) 2007COPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * EvidenceExtractionAdminService.java
 * 
 * Created on 19-05-2008, 04:15:59 PM
 */
package com.scopix.periscope.admin;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.scopix.periscope.periscopefoundation.services.Service;

/**
 *
 * @author marko.perich
 */
// @CustomWebService
@WebService(name = "EvidenceExtractionAdminService")
public interface EvidenceExtractionAdminService extends Service {

    /**
     * Clean database
     */
    @WebMethod
    void resetDb();

    @WebMethod
    void resetListeners();
}
