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
 *  EvidenceTransmitionStrategy.java
 * 
 *  Created on Jul 23, 2014, 12:42:05 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Sebastian
 */
@Entity
public class EvidenceTransmitionStrategy extends BusinessObject {

    @OneToOne(fetch = FetchType.LAZY)
    private Store store;
    @OneToOne(fetch = FetchType.LAZY)
    private SituationTemplate situationTemplate;
    @ManyToMany(targetEntity = RegionServer.class, fetch = FetchType.LAZY) //, mappedBy = "strategies"
    @JoinTable(name = "rel_evidence_transmition_strategy_region_server",
            joinColumns = {
        @JoinColumn(name = "evidence_transmition_strategy_id")},
            inverseJoinColumns = {
        @JoinColumn(name = "region_server_id")})
    private List<RegionServer> regionServers;

    /**
     * @return the store
     */
    public Store getStore() {
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * @return the situationTemplate
     */
    public SituationTemplate getSituationTemplate() {
        return situationTemplate;
    }

    /**
     * @param situationTemplate the situationTemplate to set
     */
    public void setSituationTemplate(SituationTemplate situationTemplate) {
        this.situationTemplate = situationTemplate;
    }

    /**
     * @return the regionServers
     */
    public List<RegionServer> getRegionServers() {
        if (regionServers == null) {
            regionServers = new ArrayList<RegionServer>();
        }
        return regionServers;
    }

    /**
     * @param regionServers the regionServers to set
     */
    public void setRegionServers(List<RegionServer> regionServers) {
        this.regionServers = regionServers;
    }
}
