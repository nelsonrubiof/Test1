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
 *  StoreFrontEndContainerDTO.java
 * 
 *  Created on Jul 29, 2014, 2:44:46 PM
 * 
 */

package com.scopix.periscope.frontend.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@XmlRootElement(name = "StoreFrontEndDTOContainer")
public class StoreFrontEndContainerDTO extends RestResponseDTO {
    
    private List<StoreFrontEndDTO> storeDTOs;

    /**
     * @return the storeDTOs
     */
    public List<StoreFrontEndDTO> getStoreDTOs() {
        return storeDTOs;
    }

    /**
     * @param storeDTOs the storeDTOs to set
     */
    public void setStoreDTOs(List<StoreFrontEndDTO> storeDTOs) {
        this.storeDTOs = storeDTOs;
    }
    
}
