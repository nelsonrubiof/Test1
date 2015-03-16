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
 *  TransformSensorsToDTOCommand.java
 * 
 *  Created on 27-09-2010, 05:26:23 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.Sensor;
import com.scopix.periscope.corporatestructuremanagement.dto.SituationSensorDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class TransformSensorsToDTOCommand {

    public List<SituationSensorDTO> execute(List<Sensor> sensors) {
        List<SituationSensorDTO> sensorDTOs = new ArrayList<SituationSensorDTO>();
        for (Sensor sensor : sensors) {
            SituationSensorDTO dto = new SituationSensorDTO();
            dto.setId(sensor.getId());
            dto.setName(sensor.getName());
            dto.setDescription(sensor.getDescription());
            sensorDTOs.add(dto);
        }
        return sensorDTOs;
    }
}
