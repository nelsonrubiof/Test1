/*
 * 
 * Copyright (C) 2013, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * SecurityManagerImpl.java
 *
 * Created on 30-10-20013, 09:14:59 PM
 *
 */
package com.scopix.periscope.securitymanagement;

import com.scopix.periscope.periscopefoundation.evidence_common.exception.ConfigurationException;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.securitymanagement.dto.PeriscopeUserDTO;
import com.scopix.periscope.securitymanagement.dto.RolesGroupDTO;
import com.scopix.periscope.securitymanagement.services.webservices.SecurityWebServices;
import com.scopix.periscope.securitymanagement.services.webservices.client.SecurityWebServicesClient;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Sebastian Torres Brown
 */
@SpringBean(rootClass = SecurityManager.class)
@Transactional(rollbackFor = {ScopixWebServiceException.class, ScopixException.class})
public class SecurityManagerImpl implements SecurityManager {

    /**
     * Contains logic regarding permissions and access
     */
    private Logger log = Logger.getLogger(SecurityManagerImpl.class);
    private SecurityWebServices securityWebService;
    private static List<String> operatorsUsersList;
    private static Map<Long, String> sessionIds;

    /**
     * Creates session when users try to log on
     *
     * @param user
     * @param password
     * @return sessionId for the connecting user
     * @throws ScopixWebServiceException
     */
    @Override
    public long login(String user, String password) throws ScopixWebServiceException {
        log.debug("Starting method");

        long sessionId = 0;
        try {
            // call the login webservice
            password = hash256(password, user);
            sessionId = getSecurityWebService().login(user, password, 0);
            setOperatorsUsersList(null);
        } catch (NoSuchAlgorithmException ex) {
            log.error(ex, ex);
            throw new ScopixWebServiceException(ex.getMessage());
        }
        log.debug("Finished method");
        return sessionId;
    }

    /**
     *
     * @param sessionId
     * @param privilegeId
     * @return true
     * @throws ScopixWebServiceException
     */
    @Override
    public Boolean checkSecurity(Long sessionId, String privilegeId) throws ScopixWebServiceException {
        log.debug("Starting method");
        getSecurityWebService().checkSecurity(sessionId, privilegeId);
        log.debug("Finished method");
        return true;
    }

    /**
     * Logout from the current session
     *
     * @param sessionId
     * @throws ScopixWebServiceException
     */
    @Override
    public void logout(Long sessionId) throws ScopixWebServiceException {
        log.debug("Starting method");
        getSecurityWebService().logout(sessionId);
        log.debug("Finished method");
    }

    /**
     * retrieves the user name of the session
     *
     * @param sessionId
     * @return string with the username
     * @throws ScopixWebServiceException
     */
    @Override
    public String getUserName(long sessionId) throws ScopixWebServiceException {
        log.debug("start");
        String userName = null;
        if (getSessionIds().containsKey(sessionId)) {
            return getSessionIds().get(sessionId);
        } else {
            userName = getSecurityWebService().getUserName(sessionId);
            getSessionIds().put(sessionId, userName);
        }
        log.debug("end, userName = " + userName);
        return userName;
    }

//    /**
//     * Returns the corporate name from a sessionId
//     * @param sessionId
//     * @return
//     * @throws ScopixException  
//     */
//    @Override
//    public String getCorporateName(long sessionId) throws ScopixException {
//        log.debug("obtaining corporateid for session");
//        Integer corporateId = null;
//        try {
//            corporateId = getSecurityWebService().getCorporateId(sessionId);
//        } catch (PeriscopeSecurityException ex) {
//            log.error(ex, ex);
//            throw new ScopixException(ex.getMessage());
//        }
//        return CorporateUtils.getCorporate(corporateId).getName();
//    }
    /**
     * gets a list of operator users for a following corporate.
     *
     * @param corporateId
     * @param sessionId
     * @return
     * @throws ScopixWebServiceException
     */
    @Override
    public List<String> getOperatorUsersList(Integer corporateId, long sessionId) throws ScopixWebServiceException {
        if (operatorsUsersList == null) {
            log.info("getting Operators user list");
            List<String> operatorList = new ArrayList();
            List<PeriscopeUserDTO> periscopeUserDTOList = getSecurityWebService().
                    getUserList(corporateId, sessionId);
            if (periscopeUserDTOList != null
                    && periscopeUserDTOList.size() > 0) {
                for (PeriscopeUserDTO userDTO : periscopeUserDTOList) {
                    List<RolesGroupDTO> rolesGroupDTOList = userDTO.getRolesGroups();
                    if (userDTO.getUserState().equals("ACTIVE")) {
                        if (rolesGroupDTOList != null) {
                            for (RolesGroupDTO roleDTO : rolesGroupDTOList) {
                                if (roleDTO.getName().equals("Operator Roles Group")) {
                                    operatorList.add(userDTO.getUserName());
                                }
                            }
                        }else{
                            log.info("User does not have Role groups define, skipping: " + userDTO.getUserName());
                        }
                    }
                }
            }
            setOperatorsUsersList(operatorList);
            return operatorList;
        }
        return getOperatorsUsersList();
    }

    /**
     * getter
     *
     * @return SecurityWebService
     * @throws ScopixWebServiceException
     */
    public SecurityWebServices getSecurityWebService() throws ScopixWebServiceException {
        if (securityWebService == null) {
            try {
                securityWebService = SecurityWebServicesClient.getInstance().getWebService();
            } catch (IOException ex) {
                throw new ScopixWebServiceException(ex);
            } catch (ConfigurationException ex) {
                throw new ScopixWebServiceException(ex);
            }
        }
        return securityWebService;
    }

    /**
     * setter
     *
     * @param webService
     */
    public void setSecurityWebService(SecurityWebServices webService) {
        this.securityWebService = webService;
    }

    /**
     * Encripta password
     *
     * @param password dato a encriptar
     * @param login login del usuario autenticado
     * @return String con dato encriptado
     * @throws NoSuchAlgorithmException si no es posible generar la encriptacion
     * del dato
     */
    public String hash256(String password, String login) throws NoSuchAlgorithmException {
        log.info("start - " + login);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        log.info("end - " + login);
        return bytesToHex(md.digest(), login);
    }

    /**
     *
     * @param bytes arreglo de bytes a transformar
     * @param login login del usuario autenticado
     * @return String con los byts transformados a hexadecimal
     */
    public String bytesToHex(byte[] bytes, String login) {
        log.info("start - " + login);
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        log.info("end - " + login);
        return result.toString();
    }

    /**
     * getter
     *
     * @return List<String>
     */
    public static List<String> getOperatorsUsersList() {

        return operatorsUsersList;
    }

    /**
     * setter
     *
     * @param operatorsUsersList
     */
    public static void setOperatorsUsersList(List<String> operatorsUsersList) {
        SecurityManagerImpl.operatorsUsersList = operatorsUsersList;
    }

    /**
     * @return the sessionIds
     */
    public static Map<Long, String> getSessionIds() {
        if (sessionIds == null) {
            sessionIds = new HashMap<Long, String>();
        } else if (sessionIds.size() > 4000) {
            sessionIds = new HashMap<Long, String>();
        }
        return sessionIds;
    }

    /**
     * @param sessionIds the sessionIds to set
     */
    public void setSessionIds(Map<Long, String> sessionIds) {
        SecurityManagerImpl.sessionIds = sessionIds;
    }
}
