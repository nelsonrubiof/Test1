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
 *  TransferStoreToReportingCommand.java
 * 
 *  Created on 31-01-2011, 12:40:14 PM
 * 
 */
package com.scopix.periscope.reporting.transfer.commands;

import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.transfer.dao.ReportingUploadDao;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class TransferStoreToReportingCommand {
    private static Logger log = Logger.getLogger(TransferStoreToReportingCommand.class);
    private ReportingUploadDao dao;

    public void execute(List<Store> stores) {
        log.debug("start");
        getDao().transferStore(stores);
        log.debug("end");
    }

    public ReportingUploadDao getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(ReportingUploadDao.class);
        }

        return dao;
    }

    public void setDao(ReportingUploadDao dao) {
        this.dao = dao;
    }
}
