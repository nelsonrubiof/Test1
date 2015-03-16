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
 * EvidenceExtractionSecurityWebServicesImpl.java
 *
 * Created on 19-01-2010, 05:31:43 PM
 *
 */
package com.scopix.periscope.securitymanagement.services.webservices;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.EvidenceExtractionSecurityManager;
import com.scopix.periscope.securitymanagement.dto.AreaTypeDTO;
import com.scopix.periscope.securitymanagement.dto.CorporateDTO;
import com.scopix.periscope.securitymanagement.dto.PeriscopeUserDTO;
import com.scopix.periscope.securitymanagement.dto.RolesGroupDTO;
import com.scopix.periscope.securitymanagement.dto.StoreDTO;
import java.util.List;
import java.util.Set;
import javax.jws.WebService;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 * @version 1.0.0
 */
//@CustomWebService(serviceClass = SecurityWebServices.class)
@WebService(endpointInterface = "com.scopix.periscope.securitymanagement.services.webservices.SecurityWebServices")
@SpringBean(rootClass = SecurityWebServices.class)
public class EvidenceExtractionSecurityWebServicesImpl implements SecurityWebServices {

    private Logger log = Logger.getLogger(EvidenceExtractionSecurityWebServicesImpl.class);

    @Override
    public long login(String user, String password, Integer corporateId) throws ScopixWebServiceException {
        log.debug("Starting web method");
        long sessionId = 0;
        try {
            sessionId = SpringSupport.getInstance().findBeanByClassName(EvidenceExtractionSecurityManager.class).
                    login(user, password);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        
        log.debug("Ending web method");
        return sessionId;
    }

    @Override
    public void logout(long sessionId) throws ScopixWebServiceException {
        log.debug("Starting web method");
        SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.EvidenceExtractionSecurityManager.class).logout(
                sessionId);
        log.debug("Ending web method");
    }

    @Override
    public void checkSecurity(long sessionId, String privilegeId) throws ScopixWebServiceException {
        log.debug("Starting web method");
        try {
            SpringSupport.getInstance().findBeanByClassName(
                    com.scopix.periscope.securitymanagement.EvidenceExtractionSecurityManager.class).
                    checkSecurity(sessionId, privilegeId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.debug("Ending web method");
    }

    @Override
    public void changePass(String string, String string1, String string2, long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Integer> getStoreIdsForUser(Integer intgr, long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Integer> getAreaIdsForUser(Integer intgr, long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void recoverPassword(String string) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getUserName(long sessionId) throws ScopixWebServiceException {
        log.debug("Starting web method");
        String userName = null;
        try {
            userName = SpringSupport.getInstance().findBeanByClassName(
                    EvidenceExtractionSecurityManager.class).getUserName(sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.debug("Ending web method");
        return userName;        
    }

    @Override
    public List<CorporateDTO> getCorporatesForUser(String string, long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PeriscopeUserDTO> getUserList(Integer intgr, long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addUser(PeriscopeUserDTO pudto, long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateUserList(List<PeriscopeUserDTO> list, long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteUserList(List<Integer> list, long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<RolesGroupDTO> getRolesGroupList(long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<RolesGroupDTO> getRolesGroupListForUser(String string, long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long login1(String string, String string1, Integer intgr, String string2) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Set<String> getRoles(long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Integer getCorporateId(long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools 
        //| Templates.
    }

    @Override
    public List<CorporateDTO> getCorporatesForUserMinimalInfo(String string, long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools 
        //| Templates.
    }

    @Override
    public List<PeriscopeUserDTO> getUserListMinimalInfo(Integer intgr, long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools 
        //| Templates.
    }

    @Override
    public List<StoreDTO> getStoresForCorporate(Integer intgr, long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools 
        //| Templates.
    }

    @Override
    public List<AreaTypeDTO> getAreaTypesForCorporate(Integer intgr, long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools
        //| Templates.
    }

    @Override
    public List<StoreDTO> getStoresForUser(String string, long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools 
        //| Templates.
    }

    @Override
    public List<AreaTypeDTO> getAreaTypesForUser(String string, long l) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools 
        //| Templates.
    }

}
