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

import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.securitymanagement.dto.AreaTypeDTO;
import com.scopix.periscope.securitymanagement.dto.CorporateDTO;
import com.scopix.periscope.securitymanagement.dto.PeriscopeUserDTO;
import com.scopix.periscope.securitymanagement.dto.RolesGroupDTO;
import com.scopix.periscope.securitymanagement.dto.StoreDTO;
import java.util.List;
import java.util.Set;
import javax.jws.WebService;

/**
 *
 * @author C�sar Abarza Suazo.
 */
//@CustomWebService
@WebService(name = "SecurityWebServices")
public interface SecurityWebServices {

    long login1(String user, String password, Integer corporateId, String application) throws ScopixWebServiceException;

    long login(String user, String password, Integer corporateId) throws ScopixWebServiceException;

    void logout(long sessionId) throws ScopixWebServiceException;

    void checkSecurity(long sessionId, String privilegeId) throws ScopixWebServiceException;

    void changePass(String user, String oldPassword, String newPassword, long sessionId) throws ScopixWebServiceException;

    List<Integer> getStoreIdsForUser(Integer corporateId, long sessionid) throws ScopixWebServiceException;

    List<Integer> getAreaIdsForUser(Integer corporateId, long sessionid) throws ScopixWebServiceException;

    void recoverPassword(String userName) throws ScopixWebServiceException;

    String getUserName(long sessionId) throws ScopixWebServiceException;

    Integer getCorporateId(long sessionId) throws ScopixWebServiceException;

    List<CorporateDTO> getCorporatesForUser(String userName, long sessionId) throws ScopixWebServiceException;

    List<CorporateDTO> getCorporatesForUserMinimalInfo(String userName, long sessionId) throws ScopixWebServiceException;

    List<PeriscopeUserDTO> getUserList(Integer corporateId, long sessionId) throws ScopixWebServiceException;

    List<PeriscopeUserDTO> getUserListMinimalInfo(Integer corporateId, long sessionId) throws ScopixWebServiceException;

    void addUser(PeriscopeUserDTO periscopeUserDTO, long sessionId) throws ScopixWebServiceException;

    void updateUserList(List<PeriscopeUserDTO> list, long sessionId) throws ScopixWebServiceException;

    void deleteUserList(List<Integer> list, long sessionId) throws ScopixWebServiceException;

    List<RolesGroupDTO> getRolesGroupList(long sessionId) throws ScopixWebServiceException;

    List<RolesGroupDTO> getRolesGroupListForUser(String userName, long sessionId) throws ScopixWebServiceException;

    /**
     * Retorna la lista de Roles del usuario en session, dado su sessionId
     * @param sessionId session de usuario conectado
     * @return  Set String
     * @throws ScopixWebServiceException en caso de Excepcion
     */
    Set<String> getRoles(long sessionId) throws ScopixWebServiceException;

    List<StoreDTO> getStoresForCorporate(Integer corporateId, long sessionId) throws ScopixWebServiceException;

    List<AreaTypeDTO> getAreaTypesForCorporate(Integer corporateId, long sessionId) throws ScopixWebServiceException;

    List<StoreDTO> getStoresForUser(String userName, long sessionId) throws ScopixWebServiceException;

    List<AreaTypeDTO> getAreaTypesForUser(String userName, long sessionId) throws ScopixWebServiceException;
}
