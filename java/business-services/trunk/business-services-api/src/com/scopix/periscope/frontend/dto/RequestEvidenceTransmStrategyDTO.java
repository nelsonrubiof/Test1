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
 *  RequestEvidenceTransmStrategyDTO.java
 * 
 *  Created on Jul 30, 2014, 2:26:34 PM
 * 
 */

package com.scopix.periscope.frontend.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@XmlRootElement(name = "RequestEvidenceTransmStrategyDTO")
public class RequestEvidenceTransmStrategyDTO {
    
    private Integer storeId;
    private Integer situationTemplateId;

    /**
     * @return the storeId
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * @param storeId the storeId to set
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * @return the situationTemplateId
     */
    public Integer getSituationTemplateId() {
        return situationTemplateId;
    }

    /**
     * @param situationTemplateId the situationTemplateId to set
     */
    public void setSituationTemplateId(Integer situationTemplateId) {
        this.situationTemplateId = situationTemplateId;
    }

}
