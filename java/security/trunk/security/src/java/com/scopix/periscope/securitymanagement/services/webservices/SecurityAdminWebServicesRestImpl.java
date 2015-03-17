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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.Corporate;
import com.scopix.periscope.securitymanagement.PeriscopeUser;
import com.scopix.periscope.securitymanagement.RolesGroup;
import com.scopix.periscope.securitymanagement.SecurityManager;
import com.scopix.periscope.securitymanagement.SynchronizationManager;
import com.scopix.periscope.securitymanagement.dto.AreaTypeDTO;
import com.scopix.periscope.securitymanagement.dto.AreaTypeListDTO;
import com.scopix.periscope.securitymanagement.dto.CorporateDTO;
import com.scopix.periscope.securitymanagement.dto.CorporateListDTO;
import com.scopix.periscope.securitymanagement.dto.IntListDTO;
import com.scopix.periscope.securitymanagement.dto.PeriscopeUserDTO;
import com.scopix.periscope.securitymanagement.dto.PeriscopeUserListDTO;
import com.scopix.periscope.securitymanagement.dto.RolesGroupDTO;
import com.scopix.periscope.securitymanagement.dto.RolesGroupListDTO;
import com.scopix.periscope.securitymanagement.dto.StoreDTO;
import com.scopix.periscope.securitymanagement.dto.StoreListDTO;
import com.scopix.periscope.securitymanagement.dto.StringListDTO;

/**
 * 
 * @author EmO
 */
@WebService(endpointInterface = "com.scopix.periscope.securitymanagement.services.webservices.SecurityAdminWebServicesRest")
@SpringBean(rootClass = SecurityAdminWebServicesRestImpl.class)
@Path("/admin")
public class SecurityAdminWebServicesRestImpl implements SecurityAdminWebServicesRest {

    private static Logger log = Logger.getLogger(SecurityAdminWebServicesRestImpl.class);
	private SecurityManager securityManager;

	@POST
	@Path("/changePass/{user}/{oldPassword}/{newPassword}/{sessionId}")
    @Override
	public Response changePass(@PathParam("user") String user, @PathParam("oldPassword") String oldPassword,
			@PathParam("newPassword") String newPassword, @PathParam("sessionId") long sessionId)
			throws ScopixWebServiceException {
        log.info("Starting web method");
        try {
			getSecurityManager().changePass(user, oldPassword, newPassword, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("Ending web method");
		return Response.ok().build();
    }

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getStoreIdsForUser/{corporateId}/{sessionId}")
    @Override
	public IntListDTO getStoreIdsForUser(@PathParam("corporateId") Integer corporateId, @PathParam("sessionId") long sessionId)
			throws ScopixWebServiceException {
        log.info("Starting web method");
		List<Integer> ids = new ArrayList<Integer>();
        try {
			ids = getSecurityManager().getStoreIdsForUser(corporateId, sessionId);
		} catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
		}
        log.info("Ending web method");
		IntListDTO listIntDTO = new IntListDTO();
		listIntDTO.setList(ids);
		return listIntDTO;
    }

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAreaIdsForUser/{corporateId}/{sessionId}")
    @Override
	public IntListDTO getAreaIdsForUser(@PathParam("corporateId") Integer corporateId, @PathParam("sessionId") long sessionId)
			throws ScopixWebServiceException {
        log.info("Starting web method");
        List<Integer> ids = null;
        try {
			ids = getSecurityManager().getAreaIdsForUser(corporateId, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("StarEndingting web method");
		
        IntListDTO listIntsDTO = new IntListDTO();
		listIntsDTO.setList(ids);
		return listIntsDTO;
    }

	@POST
	@Path("/recoverPassword/{userName}")
    @Override
	public Response recoverPassword(@PathParam("userName") String userName) throws ScopixWebServiceException {
        log.info("Starting web method");
        try {
			getSecurityManager().passwordRecover(userName);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("Ending web method");
		return Response.ok().build();
    }

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getUserName/{sessionId}")
    @Override
	public String getUserName(@PathParam("sessionId") long sessionId) throws ScopixWebServiceException {
        log.info("Starting web method");
		String userName = "";
		try {
			userName = getSecurityManager().getUserName(sessionId);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
        log.info("Ending web method");
        return userName;
    }

    /**
     * Returns the corporate Id from the current user session
     *
     * @param sessionId
     * @return integer representing the corporateId
     * @throws PeriscopeSecurityException
     */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getCorporateId/{sessionId}")
    @Override
	public Integer getCorporateId(@PathParam("sessionId") long sessionId) throws ScopixWebServiceException {
        log.info("Starting web method");
		Integer corporateId = 0;
		try {
			corporateId = getSecurityManager().getCorporateId(sessionId);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
        log.info("Ending web method");
        return corporateId;
    }

    /**
     * Obtener lista de Corporates para usuario
     *
     * @param userName Nombre del usuario del que se desea obtener los Corporates
     * @param sessionId
     * @return
     */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getCorporatesForUser/{userName}/{sessionId}")
    @Override
	public CorporateListDTO getCorporatesForUser(@PathParam("userName") String userName, @PathParam("sessionId") long sessionId)
			throws ScopixWebServiceException {
        log.info("Starting web method");

        List<CorporateDTO> list = null;
		try {
			list = getSecurityManager().getCorporatesForUser(userName, sessionId);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
        log.info("Ending web method");

		CorporateListDTO corporates = new CorporateListDTO();
		corporates.setList(list);
		return corporates;
    }

    /**
     * Este metodo obtiene los corporates del usuario, solo su id, nombre y descripcion.
     *
     * @param userName
     * @param sessionId
     * @return
     * @throws PeriscopeSecurityException
     * @throws PeriscopeException
     */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getCorporatesForUserMinimalInfo/{userName}/{sessionId}")
    @Override
	public CorporateListDTO getCorporatesForUserMinimalInfo(@PathParam("userName") String userName,
			@PathParam("sessionId") long sessionId) throws ScopixWebServiceException {
        log.info("start");

        List<CorporateDTO> list = null;
        try {
			list = getSecurityManager().getCorporatesForUserMinimalInfo(userName, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");

		CorporateListDTO corporates = new CorporateListDTO();
		corporates.setList(list);
		return corporates;
    }

    /**
     * Obtener lista de Usuarios que en su mainCorporate tiene el Corporate con id corporateID
     *
     * @param corporateId
     * @param sessionId
     * @return
     * @throws PeriscopeSecurityException
     * @throws PeriscopeException
     */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getUserList/{corporateId}/{sessionId}")
    @Override
	public PeriscopeUserListDTO getUserList(@PathParam("corporateId") Integer corporateId,
			@PathParam("sessionId") long sessionId)
			throws ScopixWebServiceException {
        log.info("start web method");

		List<PeriscopeUserDTO> list = null;
        try {
            PeriscopeUser periscopeUser = new PeriscopeUser();
            Corporate corporate = new Corporate();
            corporate.setId(corporateId);
            periscopeUser.setMainCorporate(corporate);
			list = getSecurityManager().getUserListDTOs(periscopeUser, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end web method");

		PeriscopeUserListDTO periscopeUserDTOs = new PeriscopeUserListDTO();
		periscopeUserDTOs.setList(list);

		return periscopeUserDTOs;
    }

    /**
     * Obtiene la lista de usuarios para el corporate dado, solo la informacion basica.
     *
     * @param corporateId
     * @param sessionId
     * @return
     */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getUserListMinimalInfo/{corporateId}/{sessionId}")
    @Override
	public PeriscopeUserListDTO getUserListMinimalInfo(@PathParam("corporateId") Integer corporateId,
			@PathParam("sessionId") long sessionId) throws ScopixWebServiceException {
        log.info("start");

		List<PeriscopeUserDTO> list = null;
        try {
			list = getSecurityManager().getUserListMinimalInfo(corporateId, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
		PeriscopeUserListDTO periscopeUserDTOs = new PeriscopeUserListDTO();
		periscopeUserDTOs.setList(list);

		return periscopeUserDTOs;
    }

    /**
     * Agregar un usuario a partir de un DTO
     *
     * @param periscopeUserDTO
     * @param sessionId
     * @throws PeriscopeSecurityException
     * @throws PeriscopeException
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addUser/{periscopeUserDTO}/{sessionId}")
    @Override
	public Response addUser(PeriscopeUserDTO periscopeUserDTO,
			@PathParam("sessionId") long sessionId)
			throws ScopixWebServiceException {
        log.info("start web method");
        PeriscopeUser periscopeUser = null;
        try {
			periscopeUser = getSecurityManager().addUser(periscopeUserDTO, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        try {
			getSecurityManager().sendUserJoomla(periscopeUserDTO);
            SynchronizationManager synchronizationManager = SpringSupport.getInstance().
                    findBeanByClassName(SynchronizationManager.class);

            synchronizationManager.synchronizeUser(periscopeUser.getId());
        } catch (ScopixException e) {
            log.error(e, e);
        }
        log.info("end web method");
		return Response.ok().build();
    }

    /**
     * Actualizar una lista de usuarios a partir de una lista de PeriscopeUserDTOs
     *
     * @param list
     * @param sessionId
     * @throws PeriscopeSecurityException
     * @throws PeriscopeException
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updateUserList/{list}/{sessionId}")
    @Override
	public Response updateUserList(PeriscopeUserListDTO dtos, @PathParam("sessionId") long sessionId)
			throws ScopixWebServiceException {
        log.info("start web method");

		List<PeriscopeUserDTO> list = dtos.getList();
        try {
			getSecurityManager().updateUserList(list, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        if (!list.isEmpty()) {
			getSecurityManager().sendUserJoomla(list.get(0));
        }
        try {
            SynchronizationManager synchronizationManager = SpringSupport.getInstance().
                    findBeanByClassName(SynchronizationManager.class);
            synchronizationManager.synchronizeUsers(list);
        } catch (ScopixException e) {
            log.error(e, e);
        }
        log.info("end web method");
		return Response.ok().build();
    }

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteUserList/{list}/{sessionId}")
    @Override
	public Response deleteUserList(IntListDTO dtos, @PathParam("sessionId") long sessionId)
			throws ScopixWebServiceException {
        log.info("start web method");

		List<Integer> list = dtos.getList();
        try {
			getSecurityManager().deleteUserList(list, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        try {
            SynchronizationManager synchronizationManager = SpringSupport.getInstance().
                    findBeanByClassName(SynchronizationManager.class);
            synchronizationManager.synchronizeDeleteUsers(list);
        } catch (ScopixException e) {
            log.error(e, e);
        }
        log.info("end web method");
		return Response.ok().build();
    }

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getRolesGroupList/{sessionId}")
    @Override
	public RolesGroupListDTO getRolesGroupList(@PathParam("sessionId") long sessionId) throws ScopixWebServiceException {
        log.info("start web method");
        List<RolesGroupDTO> list = null;
        try {
            list = null;
            RolesGroup rolesGroup = new RolesGroup();
            rolesGroup.setDeleted(false);
            rolesGroup.setName("%");

			list = getSecurityManager().getRolesGroupDTOList(rolesGroup, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end web method");

		RolesGroupListDTO rolesGroupListDTO = new RolesGroupListDTO();
		rolesGroupListDTO.setList(list);

		return rolesGroupListDTO;
    }

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getRolesGroupListForUser/{username}/{sessionId}")
    @Override
	public RolesGroupListDTO getRolesGroupListForUser(@PathParam("username") String username,
			@PathParam("sessionId") long sessionId)
            throws ScopixWebServiceException {
        log.info("start web method");
        List<RolesGroupDTO> list = null;
        try {
			list = getSecurityManager().getRolesGroupListForUser(username, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end web method");

		RolesGroupListDTO rolesGroupListDTO = new RolesGroupListDTO();
		rolesGroupListDTO.setList(list);

		return rolesGroupListDTO;
    }

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getRoles/{sessionId}")
    @Override
	public StringListDTO getRoles(@PathParam("sessionId") long sessionId) throws ScopixWebServiceException {
        log.info("start");
        Set<String> roles = null;
        try {
			roles = getSecurityManager().getRoles(sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");

		StringListDTO stringListDTO = new StringListDTO();
		stringListDTO.addList(roles);

		return stringListDTO;
    }

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getStoresForCorporate/{corporateId}/{sessionId}")
    @Override
	public StoreListDTO getStoresForCorporate(@PathParam("corporateId") Integer corporateId,
			@PathParam("sessionId") long sessionId)
            throws ScopixWebServiceException {
        log.info("start");
        List<StoreDTO> list = null;
        try {
			list = getSecurityManager().getStoresForCorporate(corporateId, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");

		StoreListDTO storeListDTO = new StoreListDTO();
		storeListDTO.setList(list);

		return storeListDTO;
    }

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAreaTypesForCorporate/{corporateId}/{sessionId}")
    @Override
	public AreaTypeListDTO getAreaTypesForCorporate(@PathParam("corporateId") Integer corporateId,
			@PathParam("sessionId") long sessionId)
            throws ScopixWebServiceException {
        log.info("start");
        List<AreaTypeDTO> list = null;
        try {
			list = getSecurityManager().getAreaTypesForCorporate(corporateId, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");

		AreaTypeListDTO areaTypeListDTO = new AreaTypeListDTO();
		areaTypeListDTO.setList(list);

		return areaTypeListDTO;
    }

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getStoresForUser/{userName}/{sessionId}")
    @Override
	public StoreListDTO getStoresForUser(@PathParam("userName") String userName, @PathParam("sessionId") long sessionId)
            throws ScopixWebServiceException {
        log.info("start");
        List<StoreDTO> list = null;
        try {
			list = getSecurityManager().getStoresForUser(userName, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");

		StoreListDTO storeListDTO = new StoreListDTO();
		storeListDTO.setList(list);

		return storeListDTO;
    }

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAreaTypesForUser/{userName}/{sessionId}")
    @Override
	public AreaTypeListDTO getAreaTypesForUser(@PathParam("userName") String userName, @PathParam("sessionId") long sessionId)
            throws ScopixWebServiceException {
        log.info("start");
        List<AreaTypeDTO> list = null;
        try {
			list = getSecurityManager().getAreaTypesForUser(userName, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");

		AreaTypeListDTO areaTypeListDTO = new AreaTypeListDTO();
		areaTypeListDTO.setList(list);

		return areaTypeListDTO;
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
