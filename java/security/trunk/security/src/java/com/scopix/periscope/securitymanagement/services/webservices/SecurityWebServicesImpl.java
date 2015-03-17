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

import java.util.List;
import java.util.Set;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.exception.SecurityExceptionType;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.Corporate;
import com.scopix.periscope.securitymanagement.PeriscopeUser;
import com.scopix.periscope.securitymanagement.RolesGroup;
import com.scopix.periscope.securitymanagement.SecurityManager;
import com.scopix.periscope.securitymanagement.SynchronizationManager;
import com.scopix.periscope.securitymanagement.dto.AreaTypeDTO;
import com.scopix.periscope.securitymanagement.dto.CorporateDTO;
import com.scopix.periscope.securitymanagement.dto.PeriscopeUserDTO;
import com.scopix.periscope.securitymanagement.dto.RolesGroupDTO;
import com.scopix.periscope.securitymanagement.dto.StoreDTO;

/**
 *
 * @author C?sar Abarza Suazo.
 */
//@CustomWebService(serviceClass = SecurityWebServices.class)
@WebService(endpointInterface = "com.scopix.periscope.securitymanagement.services.webservices.SecurityWebServices")
@SpringBean(rootClass = SecurityWebServices.class)
public class SecurityWebServicesImpl implements SecurityWebServices {

    private static Logger log = Logger.getLogger(SecurityWebServicesImpl.class);
    private SecurityManager secMan;

    @Override
    public long login(String user, String password, Integer corporateId) throws ScopixWebServiceException {
        log.info("start");
        // check corporateId is not null
        if (corporateId == null) {
            log.error("parameter corporateId can not be null");
//            throw new PeriscopeSecurityException(PeriscopeSecurityException.Type.ACCESS_DENIED.name(),
//                    PeriscopeSecurityException.Type.ACCESS_DENIED);
            throw new ScopixWebServiceException(SecurityExceptionType.ACCESS_DENIED.getName());

        }
        long sessionId = 0;
        try {
            sessionId = getSecMan().login(user, password, corporateId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return sessionId;
    }

    @Override
    public long login1(String user, String password, Integer corporateId, String application) throws ScopixWebServiceException {
        log.info("start");
        long sessionId = 0;
        try {
            sessionId = getSecMan().login(user, password, corporateId, application);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return sessionId;
    }

    @Override
    public void logout(long sessionId) throws ScopixWebServiceException {
        log.info("Starting web method");
        getSecMan().logout(sessionId);
        log.info("Ending web method");
    }

    @Override
    public void checkSecurity(long sessionId, String privilegeId) throws ScopixWebServiceException {
        log.info("Starting web method");
        try {
            getSecMan().checkSecurity(sessionId, privilegeId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("Ending web method");
    }

    @Override
    public void changePass(String user, String oldPassword, String newPassword, long sessionId) throws ScopixWebServiceException {
        log.info("Starting web method");
        try {
            getSecMan().changePass(user, oldPassword, newPassword, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("Ending web method");
    }

    @Override
    public List<Integer> getStoreIdsForUser(Integer corporateId, long sessionId) throws ScopixWebServiceException {
        log.info("Starting web method");
        List<Integer> ids = null;
        try {
            ids = getSecMan().getStoreIdsForUser(corporateId, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("Ending web method");
        return ids;
    }

    @Override
    public List<Integer> getAreaIdsForUser(Integer corporateId, long sessionId) throws ScopixWebServiceException {
        log.info("Starting web method");
        List<Integer> ids = null;
        try {
            ids = getSecMan().getAreaIdsForUser(corporateId, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("StarEndingting web method");
        return ids;
    }

    @Override
    public void recoverPassword(String userName) throws ScopixWebServiceException {
        log.info("Starting web method");
        try {
            getSecMan().passwordRecover(userName);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("Ending web method");
    }

    @Override
    public String getUserName(long sessionId) throws ScopixWebServiceException {
        log.info("Starting web method");
        String userName = null;
        try {
            userName = getSecMan().getUserName(sessionId);
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
    @Override
    public Integer getCorporateId(long sessionId) throws ScopixWebServiceException {
        log.info("Starting web method");
        Integer corporateId = null;
        try {
            corporateId = getSecMan().getCorporateId(sessionId);
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
    @Override
    public List<CorporateDTO> getCorporatesForUser(String userName, long sessionId) throws ScopixWebServiceException {
        log.info("Starting web method");

        List<CorporateDTO> list = null;
        try {
            list = getSecMan().getCorporatesForUser(userName, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("Ending web method");
        return list;
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
    @Override
    public List<CorporateDTO> getCorporatesForUserMinimalInfo(String userName, long sessionId) throws ScopixWebServiceException {
        log.info("start");

        List<CorporateDTO> list = null;
        try {
            list = getSecMan().getCorporatesForUserMinimalInfo(userName, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return list;
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
    @Override
    public List<PeriscopeUserDTO> getUserList(Integer corporateId, long sessionId) throws ScopixWebServiceException {
        log.info("start web method");

        List<PeriscopeUserDTO> periscopeUserDTOs = null;
        try {
            PeriscopeUser periscopeUser = new PeriscopeUser();
            Corporate corporate = new Corporate();
            corporate.setId(corporateId);
            periscopeUser.setMainCorporate(corporate);
            periscopeUserDTOs = getSecMan().getUserListDTOs(periscopeUser, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end web method");
        return periscopeUserDTOs;
    }

    /**
     * Obtiene la lista de usuarios para el corporate dado, solo la informacion basica.
     *
     * @param corporateId
     * @param sessionId
     * @return
     */
    @Override
    public List<PeriscopeUserDTO> getUserListMinimalInfo(Integer corporateId, long sessionId) throws ScopixWebServiceException {
        log.info("start");

        List<PeriscopeUserDTO> periscopeUserDTOs = null;
        try {
            periscopeUserDTOs = getSecMan().getUserListMinimalInfo(corporateId, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
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
    @Override
    public void addUser(PeriscopeUserDTO periscopeUserDTO, long sessionId) throws ScopixWebServiceException {
        log.info("start web method");
        PeriscopeUser periscopeUser = null;
        try {
            periscopeUser = getSecMan().addUser(periscopeUserDTO, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        try {
            getSecMan().sendUserJoomla(periscopeUserDTO);
            SynchronizationManager synchronizationManager = SpringSupport.getInstance().
                    findBeanByClassName(SynchronizationManager.class);

            synchronizationManager.synchronizeUser(periscopeUser.getId());
        } catch (ScopixException e) {
            log.error(e, e);
        }
        log.info("end web method");
    }

    /**
     * Actualizar una lista de usuarios a partir de una lista de PeriscopeUserDTOs
     *
     * @param list
     * @param sessionId
     * @throws PeriscopeSecurityException
     * @throws PeriscopeException
     */
    @Override
    public void updateUserList(List<PeriscopeUserDTO> list, long sessionId) throws ScopixWebServiceException {
        log.info("start web method");
        try {
            getSecMan().updateUserList(list, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        if (!list.isEmpty()) {
            getSecMan().sendUserJoomla(list.get(0));
        }
        try {
            SynchronizationManager synchronizationManager = SpringSupport.getInstance().
                    findBeanByClassName(SynchronizationManager.class);
            synchronizationManager.synchronizeUsers(list);
        } catch (ScopixException e) {
            log.error(e, e);
        }
        log.info("end web method");
    }

    @Override
    public void deleteUserList(List<Integer> list, long sessionId) throws ScopixWebServiceException {
        log.info("start web method");
        try {
            getSecMan().deleteUserList(list, sessionId);
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
    }

    @Override
    public List<RolesGroupDTO> getRolesGroupList(long sessionId) throws ScopixWebServiceException {
        log.info("start web method");
        List<RolesGroupDTO> list = null;
        try {
            list = null;
            RolesGroup rolesGroup = new RolesGroup();
            rolesGroup.setDeleted(false);
            rolesGroup.setName("%");

            list = getSecMan().getRolesGroupDTOList(rolesGroup, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end web method");
        return list;
    }

    @Override
    public List<RolesGroupDTO> getRolesGroupListForUser(String username, long sessionId)
            throws ScopixWebServiceException {
        log.info("start web method");
        List<RolesGroupDTO> list = null;
        try {
            list = getSecMan().getRolesGroupListForUser(username, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end web method");
        return list;
    }

    @Override
    public Set<String> getRoles(long sessionId) throws ScopixWebServiceException {
        log.info("start");
        Set<String> roles = null;
        try {
            roles = getSecMan().getRoles(sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return roles;
    }

    @Override
    public List<StoreDTO> getStoresForCorporate(Integer corporateId, long sessionId)
            throws ScopixWebServiceException {
        log.info("start");
        List<StoreDTO> list = null;
        try {
            list = getSecMan().getStoresForCorporate(corporateId, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return list;
    }

    @Override
    public List<AreaTypeDTO> getAreaTypesForCorporate(Integer corporateId, long sessionId)
            throws ScopixWebServiceException {
        log.info("start");
        List<AreaTypeDTO> list = null;
        try {
            list = getSecMan().getAreaTypesForCorporate(corporateId, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return list;
    }

    @Override
    public List<StoreDTO> getStoresForUser(String userName, long sessionId)
            throws ScopixWebServiceException {
        log.info("start");
        List<StoreDTO> list = null;
        try {
            list = getSecMan().getStoresForUser(userName, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return list;
    }

    @Override
    public List<AreaTypeDTO> getAreaTypesForUser(String userName, long sessionId)
            throws ScopixWebServiceException {
        log.info("start");
        List<AreaTypeDTO> list = null;
        try {
            list = getSecMan().getAreaTypesForUser(userName, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return list;
    }

    public SecurityManager getSecMan() {
        if (secMan == null) {
            secMan = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        }
        return secMan;
    }

    public void setSecMan(SecurityManager secMan) {
        this.secMan = secMan;
    }

}
