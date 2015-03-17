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
 * SecurityWebServicesImpl.java
 *
 * Created on 15-05-2008, 06:22:48 PM
 *
 */
package com.scopix.periscope.securitymanagement.services.webservices;

import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.exception.SecurityExceptionType;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.SecurityManager;

/**
 * 
 * @author EmO
 */
@WebService(endpointInterface = "com.scopix.periscope.securitymanagement.services.webservices.SecurityMainWebServicesRest")
@SpringBean(rootClass = SecurityMainWebServicesRestImpl.class)
@Path("/main")
public class SecurityMainWebServicesRestImpl implements SecurityMainWebServicesRest {

    private static Logger log = Logger.getLogger(SecurityMainWebServicesRestImpl.class);
	private SecurityManager securityManager;

	@POST
	@Path("/login/{user}/{password}/{corporateId}")
	@Override
	public Response login(@PathParam("user") String user, @PathParam("password") String password,
			@PathParam("corporateId") Integer corporateId) throws ScopixWebServiceException {
		log.info("start");
		// check corporateId is not null
		if (corporateId == null) {
			log.error("parameter corporateId can not be null");
			throw new ScopixWebServiceException(SecurityExceptionType.ACCESS_DENIED.getName());

		}
		long sessionId = 0;
		try {
			sessionId = getSecurityManager().login(user, password, corporateId);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.info("end");
		return Response.ok(sessionId, MediaType.TEXT_PLAIN).build();
	}

	@POST
	@Path("/loginApp/{user}/{password}/{corporateId}/{application}")
    @Override
	public Response login1(@PathParam("user") String user, @PathParam("password") String password,
			@PathParam("corporateId") Integer corporateId, @PathParam("application") String application)
			throws ScopixWebServiceException {
        log.info("start");
        long sessionId = 0;
        try {
			sessionId = getSecurityManager().login(user, password, corporateId, application);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
		return Response.ok(sessionId, MediaType.TEXT_PLAIN).build();
    }

	@POST
	@Path("/logout/{sessionId}")
    @Override
	public Response logout(@PathParam("sessionId") long sessionId) throws ScopixWebServiceException {
        log.info("Starting web method");
		getSecurityManager().logout(sessionId);
        log.info("Ending web method");
		return Response.ok().build();
    }

	@POST
	@Path("/checkSecurity/{sessionId}/{privilegeId}")
    @Override
	public Response checkSecurity(@PathParam("sessionId") long sessionId, @PathParam("privilegeId") String privilegeId)
			throws ScopixWebServiceException {
        log.info("Starting web method");
        try {
			getSecurityManager().checkSecurity(sessionId, privilegeId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("Ending web method");
		return Response.ok().build();
    }

	public SecurityManager getSecurityManager() {
		if (securityManager == null) {
			securityManager = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        }
		return securityManager;
    }

    public void setSecMan(SecurityManager secMan) {
		this.securityManager = secMan;
    }

}
