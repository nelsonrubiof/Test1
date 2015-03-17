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
 *  RequestBulkEditNewlEvTransmDTO.java
 * 
 *  Created on Aug 25, 2014, 12:54:15 PM
 * 
 */
package com.scopix.periscope.frontend.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@XmlRootElement(name = "RequestBulkEditNewlEvTransmDTO")
public class RequestBulkEditNewlEvTransmDTO {

    private List<Integer> storeIds;
    private List<Integer> situationTemplateIds;
    private List<Integer> regionServerIds;

    /**
     * @return the storeIds
     */
    public List<Integer> getStoreIds() {
        return storeIds;
    }

    /**
     * @param storeIds the storeIds to set
     */
    public void setStoreIds(List<Integer> storeIds) {
        this.storeIds = storeIds;
    }

    /**
     * @return the situationTemplateIds
     */
    public List<Integer> getSituationTemplateIds() {
        return situationTemplateIds;
    }

    /**
     * @param situationTemplateIds the situationTemplateIds to set
     */
    public void setSituationTemplateIds(List<Integer> situationTemplateIds) {
        this.situationTemplateIds = situationTemplateIds;
    }

    /**
     * @return the regionServerIds
     */
    public List<Integer> getRegionServerIds() {
        return regionServerIds;
    }

    /**
     * @param regionServerIds the regionServerIds to set
     */
    public void setRegionServerIds(List<Integer> regionServerIds) {
        this.regionServerIds = regionServerIds;
    }
}
