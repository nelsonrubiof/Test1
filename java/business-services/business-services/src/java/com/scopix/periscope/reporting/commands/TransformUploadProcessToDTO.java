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
 *  TransformUploadProcessToDTO.java
 * 
 *  Created on 14-01-2011, 10:09:50 AM
 * 
 */
package com.scopix.periscope.reporting.commands;

import com.scopix.periscope.reporting.ReportingManager;
import com.scopix.periscope.reporting.UploadProcess;
import com.scopix.periscope.reporting.UploadProcessDetail;
import com.scopix.periscope.reporting.dto.UploadProcessDTO;
import com.scopix.periscope.reporting.dto.UploadProcessDetailDTO;
import java.util.ArrayList;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

/**
 * Transforma un UplopadProcess en DTO
 * @author nelson
 */
public class TransformUploadProcessToDTO {

    private static Logger log = Logger.getLogger(TransformUploadProcessToDTO.class);
    private TransformUploadProcessDetailToDTO transformUploadProcessDetailToDTO;

    public UploadProcessDTO execute(UploadProcess up) {
        log.info("start");
        UploadProcessDTO dto = null;
        if (up != null) {
            dto = new UploadProcessDTO();
            dto.setId(up.getId());
            dto.setDateProcess(DateFormatUtils.format(up.getDateProcess(), ReportingManager.DATE_FORMAT));
            dto.setLoginUser(up.getLoginUser());
            dto.setLoginUserRunning(up.getLoginUserRunning());
            if (up.getMotiveClosing() != null) {
                dto.setMotiveClosing(up.getMotiveClosing().getName());
            }
            dto.setObservations(up.getComments());

            dto.setProcessDetails(new ArrayList<UploadProcessDetailDTO>());
            for (UploadProcessDetail detail : up.getProcessDetails()) {
                UploadProcessDetailDTO detailDTO = getTransformUploadProcessDetailToDTO().execute(detail);
                dto.getProcessDetails().add(detailDTO);
            }

            if (up.getStartDateProcess() != null) {
                dto.setStartDate(DateFormatUtils.format(up.getStartDateProcess(), ReportingManager.DATE_TIME_FORMAT));

            }
            if (up.getEndDateProcess() != null) {
                dto.setEndDate(DateFormatUtils.format(up.getEndDateProcess(), ReportingManager.DATE_TIME_FORMAT));
            }

            dto.setTotalGlobal(up.getTotalGlobal());
            dto.setProcessedGlobal(up.getTotalUpload());
            dto.setPercentGlobal(new Double(0));
            dto.setProcessState(up.getProcessState().getName());
            //Calcular el porcentaje global
            if (up.getTotalGlobal() != null
                    && up.getTotalUpload() != null
                    && up.getTotalGlobal().equals(up.getTotalUpload())) {
                dto.setPercentGlobal(new Double(100));
            } else if (up.getTotalGlobal() != null && up.getTotalGlobal() > 0) {
                Double calculo = new Double((up.getTotalUpload() * 100) / up.getTotalGlobal());
                dto.setPercentGlobal(calculo);
            }
        }
        log.info("end");
        return dto;
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
