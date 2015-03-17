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
import com.scopix.periscope.securitymanagement.dto.AreaTypeListDTO;
import com.scopix.periscope.securitymanagement.dto.CorporateListDTO;
import com.scopix.periscope.securitymanagement.dto.IntListDTO;
import com.scopix.periscope.securitymanagement.dto.PeriscopeUserDTO;
import com.scopix.periscope.securitymanagement.dto.PeriscopeUserListDTO;
import com.scopix.periscope.securitymanagement.dto.RolesGroupListDTO;
import com.scopix.periscope.securitymanagement.dto.StoreListDTO;
import com.scopix.periscope.securitymanagement.dto.StringListDTO;

/**
 * 
 * @author EmO
 */
@WebService(name = "SecurityAdminWebServicesRest")
public interface SecurityAdminWebServicesRest {

	public Response changePass(String user, String oldPassword, String newPassword, long sessionId)
			throws ScopixWebServiceException;

	IntListDTO getStoreIdsForUser(Integer corporateId, long sessionid) throws ScopixWebServiceException;

	IntListDTO getAreaIdsForUser(Integer corporateId, long sessionid) throws ScopixWebServiceException;

	public Response recoverPassword(String userName) throws ScopixWebServiceException;

    String getUserName(long sessionId) throws ScopixWebServiceException;

    Integer getCorporateId(long sessionId) throws ScopixWebServiceException;

	CorporateListDTO getCorporatesForUser(String userName, long sessionId) throws ScopixWebServiceException;

	CorporateListDTO getCorporatesForUserMinimalInfo(String userName, long sessionId) throws ScopixWebServiceException;

	PeriscopeUserListDTO getUserList(Integer corporateId, long sessionId) throws ScopixWebServiceException;

	PeriscopeUserListDTO getUserListMinimalInfo(Integer corporateId, long sessionId) throws ScopixWebServiceException;

	public Response addUser(PeriscopeUserDTO periscopeUserDTO, long sessionId) throws ScopixWebServiceException;

	public Response updateUserList(PeriscopeUserListDTO list, long sessionId) throws ScopixWebServiceException;

	public Response deleteUserList(IntListDTO list, long sessionId) throws ScopixWebServiceException;

	RolesGroupListDTO getRolesGroupList(long sessionId) throws ScopixWebServiceException;

	RolesGroupListDTO getRolesGroupListForUser(String userName, long sessionId) throws ScopixWebServiceException;

    /**
     * Retorna la lista de Roles del usuario en session, dado su sessionId
     * @param sessionId session de usuario conectado
     * @return  Set String
     * @throws ScopixWebServiceException en caso de Excepcion
     */
	StringListDTO getRoles(long sessionId) throws ScopixWebServiceException;

	StoreListDTO getStoresForCorporate(Integer corporateId, long sessionId) throws ScopixWebServiceException;

	AreaTypeListDTO getAreaTypesForCorporate(Integer corporateId, long sessionId) throws ScopixWebServiceException;

	StoreListDTO getStoresForUser(String userName, long sessionId) throws ScopixWebServiceException;

	AreaTypeListDTO getAreaTypesForUser(String userName, long sessionId) throws ScopixWebServiceException;
}
