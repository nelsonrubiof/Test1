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
 *  TransformSituationTemplateToDTOCommand.java
 * 
 *  Created on 21-09-2010, 06:10:41 PM
 * 
 */
package com.scopix.periscope.templatemanagement.commands;

import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.templatemanagement.dto.SituationTemplateDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class TransformSituationTemplateToDTOCommand {

    public List<SituationTemplateDTO> execute(List<SituationTemplate> situationTemplateList) {
        List<SituationTemplateDTO> situationTemplateDTOs = new ArrayList<SituationTemplateDTO>();
        for (SituationTemplate st : situationTemplateList) {
            SituationTemplateDTO dto = new SituationTemplateDTO();
            dto.setId(st.getId());
            dto.setName(st.getName());
            situationTemplateDTOs.add(dto);
        }
        return situationTemplateDTOs;
    }
}
