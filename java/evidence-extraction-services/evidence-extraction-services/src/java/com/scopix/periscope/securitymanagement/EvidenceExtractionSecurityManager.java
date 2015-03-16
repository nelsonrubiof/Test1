/*
 *
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * EvidenceExtractionSecurityManager.java
 *
 * Created on 19-01-2010, 05:34:17 PM
 *
 */
package com.scopix.periscope.securitymanagement;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.SecurityExceptionType;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.securitymanagement.commands.EvidenceExtractionLoginCommand;
import com.scopix.periscope.securitymanagement.dto.EvidenceExtractionUserDTO;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
@SpringBean(rootClass = EvidenceExtractionSecurityManager.class)
public class EvidenceExtractionSecurityManager {

    Logger log = Logger.getLogger(EvidenceExtractionSecurityManager.class);
    private static Map usersPrivileges = new HashMap<Integer, Map>();

    public long login(String user, String password) throws ScopixException {
        log.debug("start, user = " + user + " password = " + password);
        

        // try {
        String filePath = "users.xml";

        EvidenceExtractionLoginCommand command = new EvidenceExtractionLoginCommand();
        EvidenceExtractionUserDTO dTO = command.execute(user, password, filePath);

        Long sessionId = dTO.getSessionId();

        HashMap<String, EvidenceExtractionUserDTO> sessionMap = new HashMap<String, EvidenceExtractionUserDTO>();
        sessionMap.put("userDTO", dTO);
        usersPrivileges.put(sessionId, sessionMap);
//        } catch (ScopixException pex) {
//            log.debug("Error: " + pex.getMessage());


//            throw new PeriscopeSecurityException("login.error.", pex,
//                    PeriscopeSecurityException.Type.ACCESS_DENIED);
//        }

        log.debug("end, sessionId = " + sessionId);
        return sessionId;
    }

    public void logout(Long sessionId) {
        log.debug("start, sessionId = " + sessionId);
        usersPrivileges.remove(sessionId);
        log.debug("end");
    }

    public Boolean checkSecurity(Long sessionId, String privilegeId) throws ScopixException {
        log.debug("start, sessionId = " + sessionId + " privilegeId = " + privilegeId);
        HashMap sessionMap = (HashMap) usersPrivileges.get(sessionId);
        String message = "NO MESSAGE";

        if (sessionMap == null) {
            message = SecurityExceptionType.SESSION_NOT_FOUND.name();
            log.error("Error = " + message);
//            throw new PeriscopeSecurityException(PeriscopeSecurityException.Type.SESSION_NOT_FOUND.name(),
//                    PeriscopeSecurityException.Type.SESSION_NOT_FOUND);
            throw new ScopixException(message);
        }
        EvidenceExtractionUserDTO dto = (EvidenceExtractionUserDTO) sessionMap.get("userDTO");

        List<String> privileges = dto.getPrivileges();
        int privilege = Collections.binarySearch(privileges, privilegeId);
        log.debug("privilege = " + privilege);
        if (privilege < 0) {
            message = SecurityExceptionType.ACCESS_DENIED.name();
            log.error("Error = " + message);
//            throw new PeriscopeSecurityException(PeriscopeSecurityException.Type.ACCESS_DENIED.name(),
//                    PeriscopeSecurityException.Type.ACCESS_DENIED);
            throw new ScopixException(message);
        }
        log.debug("end");
        return true;
    }

    public String getUserName(long sessionId) throws ScopixException {
        log.debug("start");
        String userName = null;

        HashMap sessionMap = (HashMap) usersPrivileges.get(sessionId);

        if (sessionMap != null) {
            EvidenceExtractionUserDTO userDTO = (EvidenceExtractionUserDTO) sessionMap.get("userDTO");

            userName = userDTO.getNombre();
        } else {
            log.error("Error = " + SecurityExceptionType.SESSION_NOT_FOUND.name());
//            throw new PeriscopeSecurityException("NOT FOUND", PeriscopeSecurityException.Type.SESSION_NOT_FOUND);
            throw new ScopixException(SecurityExceptionType.SESSION_NOT_FOUND.getName());
        }

        log.debug("end, userName = " + userName);
        return userName;
    }
}
