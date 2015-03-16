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
 *  TransformUploadProcessDetailListToDTO.java
 * 
 *  Created on 14-01-2011, 12:47:02 PM
 * 
 */
package com.scopix.periscope.reporting.commands;

import com.scopix.periscope.reporting.UploadProcessDetail;
import com.scopix.periscope.reporting.dto.UploadProcessDetailDTO;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Transforma una lista de UploadProcessDetail a una lista de DTOs
 * @author nelson
 */
public class TransformUploadProcessDetailListToDTO {

    private static Logger log = Logger.getLogger(TransformUploadProcessDetailListToDTO.class);
    private TransformUploadProcessDetailToDTO transformUploadProcessDetailToDTO;

    public List<UploadProcessDetailDTO> execute(List<UploadProcessDetail> details) {
        log.debug("start");
        List<UploadProcessDetailDTO> dTOs = new ArrayList<UploadProcessDetailDTO>();
        for (UploadProcessDetail detail : details) {
            dTOs.add(getTransformUploadProcessDetailToDTO().execute(detail));
        }
        log.debug("end");
        return dTOs;
    }

    public TransformUploadProcessDetailToDTO getTransformUploadProcessDetailToDTO() {
        if (transformUploadProcessDetailToDTO == null) {
            transformUploadProcessDetailToDTO = new TransformUploadProcessDetailToDTO();
        }
        return transformUploadProcessDetailToDTO;
    }

    public void setTransformUploadProcessDetailToDTO(TransformUploadProcessDetailToDTO transformUploadProcessDetailToDTO) {
        this.transformUploadProcessDetailToDTO = transformUploadProcessDetailToDTO;
    }
}
