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
 *  SituationTemplateFEContainerDTO.java
 * 
 *  Created on Jul 29, 2014, 3:24:56 PM
 * 
 */
package com.scopix.periscope.frontend.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@XmlRootElement(name = "SituationTemplateFEContainerDTO")
public class SituationTemplateFEContainerDTO extends RestResponseDTO {

    private List<SituationTemplateFrontEndDTO> situationTemplateDTOs;

    /**
     * @return the situationTemplateDTOs
     */
    public List<SituationTemplateFrontEndDTO> getSituationTemplateDTOs() {
        return situationTemplateDTOs;
    }

    /**
     * @param situationTemplateDTOs the situationTemplateDTOs to set
     */
    public void setSituationTemplateDTOs(List<SituationTemplateFrontEndDTO> situationTemplateDTOs) {
        this.situationTemplateDTOs = situationTemplateDTOs;
    }
}
