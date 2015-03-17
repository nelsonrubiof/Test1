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
 *  OperatorUsersDTOContainer.java
 * 
 *  Created on Jun 12, 2014, 9:41:24 AM
 * 
 */
package com.scopix.periscope.activitylog.services.webservices.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@XmlRootElement(name = "OperatorUsersDTOContainer")
public class OperatorUsersDTOContainer {

   private List<String> operatorUsersDTO;

    /**
     * @return the operatorUsersDTO
     */
    public List<String> getOperatorUsersDTO() {
        return operatorUsersDTO;
    }

    /**
     * @param operatorUsersDTO the operatorUsersDTO to set
     */
    public void setOperatorUsersDTO(List<String> operatorUsersDTO) {
        this.operatorUsersDTO = operatorUsersDTO;
    }
}
