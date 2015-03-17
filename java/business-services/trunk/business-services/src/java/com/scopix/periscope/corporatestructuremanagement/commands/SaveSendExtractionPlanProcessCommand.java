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
 *  SaveSendExtractionPlanProcessCommand.java
 * 
 *  Created on 03-12-2010, 03:46:19 PM
 * 
 */

package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.SendExtractionPlanProcess;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nelson
 */
public class SaveSendExtractionPlanProcessCommand {
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

    public SendExtractionPlanProcess execute(long sessionId, Integer storeId, List<Integer> ecpIds) {
       SendExtractionPlanProcess process = new SendExtractionPlanProcess(sessionId, storeId, ecpIds);
       process.setIni(new Date());
       getDao().save(process);
       return process;
    }

}
