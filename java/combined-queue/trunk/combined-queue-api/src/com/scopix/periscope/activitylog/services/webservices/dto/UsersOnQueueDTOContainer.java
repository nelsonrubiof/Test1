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
 *  UsersOnQueueDTOContainer.java
 * 
 *  Created on Aug 14, 2014, 11:54:25 AM
 * 
 */
package com.scopix.periscope.activitylog.services.webservices.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@XmlRootElement(name = "UsersOnQueueDTOContainer")
public class UsersOnQueueDTOContainer {

    private List<UsersOnQueueDTO> usersOnQueues;

    /**
     * @return the usersOnQueues
     */
    public List<UsersOnQueueDTO> getUsersOnQueues() {
        return usersOnQueues;
    }

    /**
     * @param usersOnQueues the usersOnQueues to set
     */
    public void setUsersOnQueues(List<UsersOnQueueDTO> usersOnQueues) {
        this.usersOnQueues = usersOnQueues;
    }
}
