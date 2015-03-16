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
 *  TransformUploadProcessDetailToDTO.java
 * 
 *  Created on 14-01-2011, 10:16:54 AM
 * 
 */
package com.scopix.periscope.reporting.commands;

import com.scopix.periscope.corporatestructuremanagement.dto.AreaTypeDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import com.scopix.periscope.reporting.ReportingManager;
import com.scopix.periscope.reporting.UploadProcessDetail;
import com.scopix.periscope.reporting.dto.UploadProcessDetailDTO;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * Transforma un UploadProcessDetail en DTO
 * @author nelson
 */
public class TransformUploadProcessDetailToDTO {

    public UploadProcessDetailDTO execute(UploadProcessDetail detail) {
        UploadProcessDetailDTO dto = null;
        if (detail != null) {
            dto = new UploadProcessDetailDTO();
            dto.setId(detail.getId());
            if (detail.getDateEnd() != null) {
                dto.setDateEnd(DateFormatUtils.format(detail.getDateEnd(), ReportingManager.DATE_FORMAT));
            }
            dto.setTotalRecords(detail.getTotalRecords());
            dto.setUpRecords(detail.getUpRecords());
            if (detail.getTotalRecords() != null && detail.getTotalRecords() > 0) {
                dto.setPercent(new Double((detail.getUpRecords() * 100) / detail.getTotalRecords()));
            } else if ((detail.getTotalRecords() == null && detail.getUpRecords() == null)
                    || (detail.getTotalRecords() == 0 && detail.getUpRecords() == 0)) {
                dto.setPercent(new Double(100));
            }

            StoreDTO storeDTO = new StoreDTO();
            storeDTO.setId(detail.getStore().getId());
            storeDTO.setName(detail.getStore().getName());
            storeDTO.setDescription(detail.getStore().getDescription());
            dto.setStore(storeDTO);

            AreaTypeDTO areaTypeDTO = new AreaTypeDTO();
            areaTypeDTO.setId(detail.getAreaType().getId());
            areaTypeDTO.setName(detail.getAreaType().getName());
            areaTypeDTO.setDescription(detail.getAreaType().getDescription());
            dto.setAreaType(areaTypeDTO);
        }
        return dto;
    }
}
