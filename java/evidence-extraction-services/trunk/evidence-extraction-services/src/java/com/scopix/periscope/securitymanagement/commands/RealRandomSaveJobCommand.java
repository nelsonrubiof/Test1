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
 *  RealRandomSaveJobCommand.java
 * 
 *  Created on 31-08-2012, 05:53:21 PM
 * 
 */
package com.scopix.periscope.securitymanagement.commands;

import com.scopix.periscope.extractionmanagement.SituationRequestRange;
import com.scopix.periscope.extractionmanagement.SituationRequestRealRandom;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.Date;

/**
 *
 * @author nelson
 */
public class RealRandomSaveJobCommand {

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

    public SituationRequestRealRandom execute(Date initBlock, Date endBlock, String timeZoneId, SituationRequestRange range,
            Date requestedTime, Date hourExecution) {
        SituationRequestRealRandom realRandom = new SituationRequestRealRandom();
        realRandom.setCreationDate(new Date());
        realRandom.setEndBlock(endBlock);
        realRandom.setInitBlock(initBlock);
        realRandom.setRequestedTime(requestedTime);
        realRandom.setExecutionTime(hourExecution);
        realRandom.setSituationRequestRange(range);
        realRandom.setTimeZoneId(timeZoneId);
        getDao().save(realRandom);
        return realRandom;
    }
}
