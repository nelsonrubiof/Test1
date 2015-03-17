/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
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
 * Created on 29-04-2008, 09:14:59 PM
 *
 */
package com.scopix.periscope.securitymanagement;

import com.scopix.periscope.corporatestructuremanagement.Corporate;
import com.scopix.periscope.corporatestructuremanagement.commands.GetCorporateCommand;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.services.webservices.SecurityWebServices;
import com.scopix.periscope.securitymanagement.services.webservices.client.SecurityWebServicesClient;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gustavo Alvarez
 */
@SpringBean(rootClass = SecurityManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class SecurityManagerImpl implements SecurityManager {

    public static final String NAME_APPLICATION = "BUSINESS-SERVICES";
    private Logger log = Logger.getLogger(SecurityManagerImpl.class);

    @Override
    public long login(String user, String password) throws ScopixException {
        log.debug("Starting method");
        SecurityResultVO result = new SecurityResultVO();
        long sessionId = 0;
        try {
            // get the corporate this instance is running for
            GetCorporateCommand command = new GetCorporateCommand();
            Corporate corporate;
            try {
                corporate = command.execute();
            } catch (ScopixException e) {
                log.debug("No se encontró objeto corporate", e);
                throw new ScopixException("ACCESS_DENIED", e);
//            throw new PeriscopeSecurityException(PeriscopeSecurityException.Type.ACCESS_DENIED.name(),
//                    PeriscopeSecurityException.Type.ACCESS_DENIED);
            }
            // call the login webservice
            SecurityWebServices webServices = SpringSupport.getInstance().findBeanByClassName(SecurityWebServicesClient.class).
                    getWebService();
            sessionId = webServices.login1(user, password, corporate.getUniqueCorporateId(), NAME_APPLICATION);
            result.setSessionId(sessionId);
        } catch (ScopixWebServiceException e) {
            throw new ScopixException(e.getFaultInfo().getExceptionMessage());
        }
        log.debug("Finished method");
        return sessionId;
    }

    @Override
    public Boolean checkSecurity(Long sessionId, String privilegeId) throws ScopixException {
        log.debug("Starting method");
        Boolean result = false;
        try {
            SecurityWebServices webServices = SpringSupport.getInstance().findBeanByClassName(SecurityWebServicesClient.class).
                    getWebService();
            webServices.checkSecurity(sessionId, privilegeId);
            result = true;
            
        } catch (ScopixWebServiceException e) {
            throw new ScopixException(e.getFaultInfo().getExceptionMessage());
        }
        log.debug("Finished method");
        return result;
    }

    @Override
    public void logout(Long sessionId) throws ScopixException {
        log.debug("Starting method");
        try {
            SecurityWebServices webServices = SpringSupport.getInstance().findBeanByClassName(SecurityWebServicesClient.class).
                    getWebService();
            webServices.logout(sessionId);
        } catch (ScopixWebServiceException e) {
            throw new ScopixException(e.getFaultInfo().getExceptionMessage());
        }
        log.debug("Finished method");
    }

    @Override
    public String getUserName(long sessionId) throws ScopixException {
        log.debug("start");
        String userName = null;
        try {
            SecurityWebServices webServices = SpringSupport.getInstance().findBeanByClassName(SecurityWebServicesClient.class).
                    getWebService();
            userName = webServices.getUserName(sessionId);
        } catch (ScopixWebServiceException e) {
            throw new ScopixException(e.getFaultInfo().getExceptionMessage());
        }
        log.debug("end, userName = " + userName);
        return userName;
    }
}
