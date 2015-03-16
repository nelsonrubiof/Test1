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
 *  SecurityManagerMock.java
 * 
 *  Created on 07-09-2010, 03:57:41 PM
 * 
 */
package mockup;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.securitymanagement.SecurityManager;

/**
 *
 * @author nelson
 */
public class SecurityManagerMock implements SecurityManager {

    public SecurityManagerMock() {
    }

    public Boolean checkSecurity(Long sessionId, String privilegeId) throws ScopixException {
        return true;
    }

    public String getUserName(long sessionId) throws ScopixException {
        throw new ScopixException("Not supported yet.");
    }

    public long login(String user, String password) throws ScopixException {
        throw new ScopixException("Not supported yet.");
    }

    public void logout(Long sessionId) throws ScopixException {
        throw new ScopixException("Not supported yet.");
    }

}
