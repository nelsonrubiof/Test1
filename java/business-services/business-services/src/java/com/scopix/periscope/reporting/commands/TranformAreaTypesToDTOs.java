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
 *  TranformAreaTypesToDTOs.java
 * 
 *  Created on 14-01-2011, 11:59:20 AM
 * 
 */
package com.scopix.periscope.reporting.commands;

import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.corporatestructuremanagement.dto.AreaTypeDTO;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class TranformAreaTypesToDTOs {

    private static Logger log = Logger.getLogger(TranformAreaTypesToDTOs.class);

    public List<AreaTypeDTO> execute(List<AreaType> lista) {
        log.debug("start");
        List<AreaTypeDTO> dTOs = new ArrayList<AreaTypeDTO>();
        for (AreaType at : lista) {
            AreaTypeDTO dto = new AreaTypeDTO();
            dto.setId(at.getId());
            dto.setName(at.getName());
            dto.setDescription(at.getDescription());
            dTOs.add(dto);
        }
        log.debug("end");
        return dTOs;
    }
}
