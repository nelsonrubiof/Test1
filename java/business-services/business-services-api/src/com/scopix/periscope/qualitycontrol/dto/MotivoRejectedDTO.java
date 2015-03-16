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
 *  MotivoRejectedDTO.java
 * 
 *  Created on 13-12-2011, 04:39:48 PM
 * 
 */

package com.scopix.periscope.qualitycontrol.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class MotivoRejectedDTO {
    private Integer id;
    private String description;
    private List<ClasificacionDTO> clasificacionDTOs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ClasificacionDTO> getClasificacionDTOs() {
        if (clasificacionDTOs == null) {
            clasificacionDTOs = new ArrayList<ClasificacionDTO>();
        }
        return clasificacionDTOs;
    }

    public void setClasificacionDTOs(List<ClasificacionDTO> clasificacionDTOs) {
        this.clasificacionDTOs = clasificacionDTOs;
    }

}
