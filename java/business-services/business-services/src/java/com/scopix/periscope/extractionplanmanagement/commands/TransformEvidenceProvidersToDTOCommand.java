/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
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
 *  TransformEvidenceProvidersToDTOCommand.java
 * 
 *  Created on 27-09-2010, 04:06:54 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceProviderDTO;
import com.scopix.periscope.periscopefoundation.util.SortUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class TransformEvidenceProvidersToDTOCommand {

    private static Logger log = Logger.getLogger(TransformEvidenceProvidersToDTOCommand.class);

    public List<EvidenceProviderDTO> execute(List<EvidenceProvider> evidenceProviders) {
        log.info("start");
        List<EvidenceProviderDTO> providerDTOs = new ArrayList<EvidenceProviderDTO>();
        for (EvidenceProvider provider : evidenceProviders) {
            EvidenceProviderDTO dto = new EvidenceProviderDTO();
            dto.setId(provider.getId());
            dto.setDescription(provider.getDescription());

            if (!provider.getAreas().isEmpty()) {
                dto.setAreaType(provider.getAreas().get(0).getAreaType().getDescription());
            }
            log.debug("dto [id:" + dto.getId() + "][descripcion:" + dto.getDescription() + "]");

            dto.setDefinitionData(new HashMap<String, String>());
            dto.setProviderType(provider.getEvidenceProviderType().getDescription());
            providerDTOs.add(dto);
        }

        //antes de salir ordenamos por descripcion
        LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
        cols.put("description", Boolean.FALSE);
        SortUtil.sortByColumn(cols, providerDTOs);
        log.info("end " + providerDTOs.size());
        return providerDTOs;
    }
}
