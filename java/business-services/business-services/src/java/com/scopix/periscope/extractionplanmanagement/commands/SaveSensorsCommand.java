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
 *  SaveSensorsCommand.java
 * 
 *  Created on 29-09-2010, 01:01:56 PM
 * 
 */

package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.Sensor;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.List;

/**
 *
 * @author nelson
 */
public class SaveSensorsCommand {
    private GenericDAO dao;

    public GenericDAO getDao() {
        if (dao == null) {
            dao = HibernateSupport.getInstance().findGenericDAO();
        }
        return dao;
    }

    public void setDao(GenericDAO dao) {
        this.dao = dao;
    }

    public void execute(ExtractionPlanCustomizing planCustomizing, List<Integer> sensorIds) {
        planCustomizing.getSensors().clear();
        for (Integer sensorId : sensorIds) {
            Sensor sensor = getDao().get(sensorId, Sensor.class);
            planCustomizing.getSensors().add(sensor);
        }
        getDao().save(planCustomizing);
    }
}
