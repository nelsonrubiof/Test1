/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
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
 *  Created on 07-09-2010, 02:45:15 PM
 * 
 */

package com.scopix.periscope.securitymanagement;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 *
 * @author nelson
 */
public interface SecurityManager {

    Boolean checkSecurity(Long sessionId, String privilegeId) throws ScopixException;

    String getUserName(long sessionId) throws ScopixException;

    long login(String user, String password) throws ScopixException;

    void logout(Long sessionId) throws ScopixException;

}
