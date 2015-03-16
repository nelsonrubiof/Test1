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
 *  TransformToEvidenceTransmStrategyFEDTOCommand.java
 * 
 *  Created on Jul 30, 2014, 12:01:33 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.evaluationmanagement.EvidenceTransmitionStrategy;
import com.scopix.periscope.evaluationmanagement.RegionServer;
import com.scopix.periscope.frontend.dto.EvidenceTransmStrategyFEDTO;
import com.scopix.periscope.frontend.dto.RegionServerFrontEndDTO;
import com.scopix.periscope.frontend.dto.SituationTemplateFrontEndDTO;
import com.scopix.periscope.frontend.dto.StoreFrontEndDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class TransformToEvidenceTransmStrategyFEDTOCommand {

    /**
     * Converts a list of EvidenceTransmitionStrategys to a list of
     * EvidenceTransmStrategyFEDTOs
     *
     * @param evidenceTransmitionStrategys
     * @return List<EvidenceTransmStrategyFEDTO>
     * @throws ScopixException
     */
    public List<EvidenceTransmStrategyFEDTO> execute(List<EvidenceTransmitionStrategy> evidenceTransmitionStrategys) throws ScopixException {
        List<EvidenceTransmStrategyFEDTO> evidenceTransmitionStrategyModels = new ArrayList<EvidenceTransmStrategyFEDTO>();
        if (evidenceTransmitionStrategys == null) {
            return evidenceTransmitionStrategyModels;
        }
        for (EvidenceTransmitionStrategy evidenceTransmitionStrategy : evidenceTransmitionStrategys) {
            evidenceTransmitionStrategyModels.add(evidenceTransmitionStrategyModelDTORowMapper(evidenceTransmitionStrategy));
        }
        return evidenceTransmitionStrategyModels;
    }

    /**
     * Converts a EvidenceTransmitionStrategys to a EvidenceTransmStrategyFEDTOs
     *
     * @param evidenceTransmitionStrategy
     * @return EvidenceTransmStrategyFEDTO
     * @throws ScopixException
     */
    public EvidenceTransmStrategyFEDTO execute(EvidenceTransmitionStrategy evidenceTransmitionStrategy) throws ScopixException {
        if (evidenceTransmitionStrategy == null) {
            throw new ScopixException("EvidenceTransmitionStrategy can not be null");
        }
        return evidenceTransmitionStrategyModelDTORowMapper(evidenceTransmitionStrategy);
    }
    
    private EvidenceTransmStrategyFEDTO evidenceTransmitionStrategyModelDTORowMapper(EvidenceTransmitionStrategy evidenceTransmitionStrategy) {
        EvidenceTransmStrategyFEDTO evidenceTransmitionStrategyModelDTO = new EvidenceTransmStrategyFEDTO();
        evidenceTransmitionStrategyModelDTO.setId(evidenceTransmitionStrategy.getId());
        SituationTemplateFrontEndDTO situationTemplateFrontEndDTO = new SituationTemplateFrontEndDTO();
        situationTemplateFrontEndDTO.setId(evidenceTransmitionStrategy.getSituationTemplate().getId());
        situationTemplateFrontEndDTO.setName(evidenceTransmitionStrategy.getSituationTemplate().getName());
        StoreFrontEndDTO storeFrontEndDTO = new StoreFrontEndDTO();
        storeFrontEndDTO.setId(evidenceTransmitionStrategy.getStore().getId());
        storeFrontEndDTO.setName(evidenceTransmitionStrategy.getStore().getName());
        evidenceTransmitionStrategyModelDTO.setStore(storeFrontEndDTO);
        evidenceTransmitionStrategyModelDTO.setSituationTemplate(situationTemplateFrontEndDTO);
        List<RegionServerFrontEndDTO> regionServers = new ArrayList<RegionServerFrontEndDTO>();
        for (RegionServer regionServer : evidenceTransmitionStrategy.getRegionServers()) {
            RegionServerFrontEndDTO regionServerFrontendDTO = new RegionServerFrontEndDTO();
            regionServerFrontendDTO.setId(regionServer.getId());
            regionServerFrontendDTO.setCodeName(regionServer.getCodeName());
            regionServers.add(regionServerFrontendDTO);
        }
        evidenceTransmitionStrategyModelDTO.setRegionServers(regionServers);
        return evidenceTransmitionStrategyModelDTO;
    }
}
