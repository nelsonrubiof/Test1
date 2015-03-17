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
 *  EvidenceTransmitionStrategyFrontEndDTO.java
 * 
 *  Created on Jul 30, 2014, 11:52:30 AM
 * 
 */

package com.scopix.periscope.frontend.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@XmlRootElement(name = "EvidenceTransmStrategyFEDTO")
public class EvidenceTransmStrategyFEDTO {
    
    private Integer id;
    
    private StoreFrontEndDTO store;
    
    private SituationTemplateFrontEndDTO situationTemplate;
    
    private List<RegionServerFrontEndDTO> regionServers;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the store
     */
    public StoreFrontEndDTO getStore() {
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(StoreFrontEndDTO store) {
        this.store = store;
    }

    /**
     * @return the situationTemplate
     */
    public SituationTemplateFrontEndDTO getSituationTemplate() {
        return situationTemplate;
    }

    /**
     * @param situationTemplate the situationTemplate to set
     */
    public void setSituationTemplate(SituationTemplateFrontEndDTO situationTemplate) {
        this.situationTemplate = situationTemplate;
    }

    /**
     * @return the regionServers
     */
    public List<RegionServerFrontEndDTO> getRegionServers() {
        return regionServers;
    }

    /**
     * @param regionServers the regionServers to set
     */
    public void setRegionServers(List<RegionServerFrontEndDTO> regionServers) {
        this.regionServers = regionServers;
    }
}
