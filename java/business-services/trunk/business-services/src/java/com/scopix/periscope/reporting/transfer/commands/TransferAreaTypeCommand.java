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
 *  TransferAreaTypeCommand.java
 * 
 *  Created on 18-05-2011, 04:09:13 PM
 * 
 */
package com.scopix.periscope.reporting.transfer.commands;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.transfer.dao.ReportingUploadDao;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class TransferAreaTypeCommand {

    private ReportingUploadDao dao;

    public ReportingUploadDao getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(ReportingUploadDao.class);
        }
        return dao;
    }

    public void setDao(ReportingUploadDao dao) {
        this.dao = dao;
    }

    public void execute(List<Map<String, Object>> areaTypes) {
        getDao().transferAreaTypes(areaTypes);
    }
}
