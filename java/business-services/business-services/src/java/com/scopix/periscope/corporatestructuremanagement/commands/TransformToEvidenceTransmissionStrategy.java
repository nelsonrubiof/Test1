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
 *  TransformToRegionServer.java
 * 
 *  Created on Jul 31, 2014, 12:15:24 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.EvidenceTransmitionStrategy;
import com.scopix.periscope.evaluationmanagement.RegionServer;
import com.scopix.periscope.frontend.dto.EvidenceTransmStrategyFEDTO;
import com.scopix.periscope.frontend.dto.RegionServerFrontEndDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class TransformToEvidenceTransmissionStrategy {

    private GenericDAO genericDAO;

    /**
     * Transform the given DTO to an EvidenceTransmitionStrategy Business Object
     *
     * @param evidenceTransmStrategyFEDTO
     * @return EvidenceTransmitionStrategy
     */
    public EvidenceTransmitionStrategy execute(EvidenceTransmStrategyFEDTO evidenceTransmStrategyFEDTO) throws ScopixException {
        EvidenceTransmitionStrategy ets = null;
        if (evidenceTransmStrategyFEDTO.getId() != null) {
            ets = getGenericDAO().get(evidenceTransmStrategyFEDTO.getId(), EvidenceTransmitionStrategy.class);
        }
        if (ets == null) {
            ets = new EvidenceTransmitionStrategy();
        }
        Store store = getGenericDAO().get(evidenceTransmStrategyFEDTO.getStore().getId(), Store.class);
        if(store == null){
            throw new ScopixException("Store not found: " + evidenceTransmStrategyFEDTO.getStore().getName());
        }
        ets.setStore(store);
        SituationTemplate situationTemplate = getGenericDAO().get(evidenceTransmStrategyFEDTO.getSituationTemplate().getId(), SituationTemplate.class);
        if (situationTemplate == null) {
            throw new ScopixException("Situation Template not found: " + evidenceTransmStrategyFEDTO.getSituationTemplate().getName());
        }
        ets.setSituationTemplate(situationTemplate);

        List<RegionServer> regionServers = new ArrayList<RegionServer>();

        if (evidenceTransmStrategyFEDTO.getRegionServers() != null) {
            for (RegionServerFrontEndDTO regionServerFrontEndDTO : evidenceTransmStrategyFEDTO.getRegionServers()) {
                RegionServer regionServer = getGenericDAO().get(regionServerFrontEndDTO.getId(), RegionServer.class);
                if (regionServer == null) {
                    throw new ScopixException("Region Server not found: " + regionServerFrontEndDTO.getCodeName());
                }
                regionServers.add(regionServer);
            }
        }
        ets.setRegionServers(regionServers);
        return ets;
    }

    /**
     * @return the genericDAO
     */
    public GenericDAO getGenericDAO() {
        if (genericDAO == null) {
            genericDAO = HibernateSupport.getInstance().findGenericDAO();
        }
        return genericDAO;
    }

    /**
     * @param genericDAO the genericDAO to set
     */
    public void setGenericDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }
}
