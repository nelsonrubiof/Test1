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
 *  TransformMotivosRejectsToDTOCommand.java
 * 
 *  Created on 13-12-2011, 06:18:30 PM
 * 
 */

package com.scopix.periscope.qualitycontrol.commands;

import com.scopix.periscope.qualitycontrol.Clasificacion;
import com.scopix.periscope.qualitycontrol.MotivoRejected;
import com.scopix.periscope.qualitycontrol.dto.ClasificacionDTO;
import com.scopix.periscope.qualitycontrol.dto.MotivoRejectedDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class TransformMotivosRejectsToDTOCommand {

    public List<MotivoRejectedDTO> execute(List<MotivoRejected> list) {
        List<MotivoRejectedDTO> ret = new ArrayList<MotivoRejectedDTO>();
        for (MotivoRejected mr : list) {
            MotivoRejectedDTO dto = new MotivoRejectedDTO();
            dto.setId(mr.getId());
            dto.setDescription(mr.getDescription());
            dto.setClasificacionDTOs(new ArrayList<ClasificacionDTO>());
            for (Clasificacion c: mr.getClasificacions()){
                ClasificacionDTO cdto = new ClasificacionDTO();
                cdto.setId(c.getId());
                cdto.setDescription(c.getDescription());
                dto.getClasificacionDTOs().add(cdto);
            }
            ret.add(dto);
        }
        return ret;
    }

}
