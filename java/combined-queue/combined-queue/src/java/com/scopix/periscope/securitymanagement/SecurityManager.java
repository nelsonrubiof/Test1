/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  SecurityManager.java
 * 
 *  Created on 30/10/2013
 *
 * 
 */

package com.scopix.periscope.securitymanagement;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import java.util.List;


/**
 *
 * @author sebastian torres brown
 */
public interface SecurityManager {

    /**
     *Creates session when users try to log on
     * @param user
     * @param password
     * @return sessionId for the connecting user
     * @throws ScopixWebServiceException 
     */
    long login(String user, String password) throws ScopixWebServiceException;
    
    /**
     * check if the user posses the required privilege.
     * @param sessionId
     * @param privilegeId
     * @return true
     * @throws ScopixWebServiceException 
     */
    Boolean checkSecurity(Long sessionId, String privilegeId) throws ScopixWebServiceException;
    
    /**
     * Logout from the current session
     * @param sessionId
     * @throws ScopixWebServiceException  
     */
    void logout(Long sessionId) throws ScopixWebServiceException;

    /**
     * retrieves the user name of the session
     * @param sessionId
     * @return string with the username
     * @throws ScopixWebServiceException 
     */
    String getUserName(long sessionId) throws ScopixWebServiceException;
    
    /**
     * gets a list of operate users for a following corporate.
     * @param corporateId
     * @param sessionId
     * @return
     * @throws ScopixWebServiceException  
     */
     List<String> getOperatorUsersList(Integer corporateId, long sessionId) throws ScopixWebServiceException;
     
//     /**
//     * Returns the corporate name from a sessionId
//     * @param sessionId
//     * @return
//     * @throws ScopixException  
//     */
//    String getCorporateName(long sessionId) throws ScopixException;
    
}
