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
 *  TransformToSitTemplateFEDTOCommand.java
 * 
 *  Created on Jul 29, 2014, 3:44:37 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.frontend.dto.SituationTemplateFrontEndDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class TransformToSitTemplateFEDTOCommand {

    /**
     * Converts a list of SituationTemplates to a list of
     * SituationTemplateFrontEndDTOs
     *
     * @param situationTemplates
     * @return List<SituationTemplateFrontEndDTO>
     * @throws ScopixException
     */
    public List<SituationTemplateFrontEndDTO> execute(List<SituationTemplate> situationTemplates) throws ScopixException {
        if (situationTemplates == null) {
            throw new ScopixException("SituationTemplate list can not be null");
        }
        List<SituationTemplateFrontEndDTO> situationTemplateModels = new ArrayList<SituationTemplateFrontEndDTO>();
        for (SituationTemplate situationTemplate : situationTemplates) {
            situationTemplateModels.add(situationTemplateModelDTORowMapper(situationTemplate));
        }
        return situationTemplateModels;
    }

    /**
     * Converts a SituationTemplates to a SituationTemplateFrontEndDTOs
     *
     * @param situationTemplate
     * @return SituationTemplateFrontEndDTO
     * @throws ScopixException
     */
    public SituationTemplateFrontEndDTO execute(SituationTemplate situationTemplate) throws ScopixException {
        if (situationTemplate == null) {
            throw new ScopixException("SituationTemplate can not be null");
        }
        return situationTemplateModelDTORowMapper(situationTemplate);
    }

    private SituationTemplateFrontEndDTO situationTemplateModelDTORowMapper(SituationTemplate situationTemplate) {
        SituationTemplateFrontEndDTO situationTemplateModelDTO = new SituationTemplateFrontEndDTO();
        situationTemplateModelDTO.setId(situationTemplate.getId());
        situationTemplateModelDTO.setName(situationTemplate.getName());
        return situationTemplateModelDTO;
    }
}
