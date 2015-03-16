/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 * 
 */
package com.scopix.periscope.admin.services;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.scopix.periscope.periscopefoundation.services.Service;

/**
 *
 * Service for Administration related operations. Most of this method are just temporal and are used to simulate unimplemented
 * operations.
 *
 * @author maximiliano.vazquez
 * @version 2.0.0
 */
@WebService(name = "AdminService")
public interface AdminService extends Service {

    /**
     * Clean database
     */
    @WebMethod
    void resetDb();
    
    @WebMethod
	void reloadGhostbusterConfig();
}
