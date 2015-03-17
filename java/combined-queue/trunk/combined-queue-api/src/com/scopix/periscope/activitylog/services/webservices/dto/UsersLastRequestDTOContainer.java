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
 *  UsersLastRequestDTOContainer.java
 * 
 *  Created on Aug 21, 2014, 4:19:57 PM
 * 
 */

package com.scopix.periscope.activitylog.services.webservices.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@XmlRootElement(name = "UsersLastRequestDTOContainer")
public class UsersLastRequestDTOContainer {

    private List<UsersLastRequestDTO> usersLastRequest;

    /**
     * @return the usersLastRequest
     */
    public List<UsersLastRequestDTO> getUsersLastRequest() {
        return usersLastRequest;
    }

    /**
     * @param usersLastRequest the usersLastRequest to set
     */
    public void setUsersLastRequest(List<UsersLastRequestDTO> usersLastRequest) {
        this.usersLastRequest = usersLastRequest;
    }
    
}
