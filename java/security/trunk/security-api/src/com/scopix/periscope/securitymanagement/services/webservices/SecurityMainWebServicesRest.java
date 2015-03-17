/*
 * 
 * Copyright @ 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * SecurityWebServices.java
 *
 * Created on 15-05-2008, 06:22:48 PM
 *
 */
package com.scopix.periscope.securitymanagement.services.webservices;

import javax.jws.WebService;
import javax.ws.rs.core.Response;

import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;

/**
 * 
 * @author EmO
 */
@WebService(name = "SecurityMainWebServicesRest")
public interface SecurityMainWebServicesRest {

	public Response login1(String user, String password, Integer corporateId, String application)
			throws ScopixWebServiceException;

	public Response login(String user, String password, Integer corporateId) throws ScopixWebServiceException;

	public Response logout(long sessionId) throws ScopixWebServiceException;

	public Response checkSecurity(long sessionId, String privilegeId) throws ScopixWebServiceException;

}
