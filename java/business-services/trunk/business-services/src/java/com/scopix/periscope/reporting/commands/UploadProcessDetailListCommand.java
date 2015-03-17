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
 *  UploadProcessDetailListCommand.java
 * 
 *  Created on 14-01-2011, 12:39:45 PM
 * 
 */
package com.scopix.periscope.reporting.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.ProcessState;
import com.scopix.periscope.reporting.UploadProcessDetail;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAO;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAOImpl;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Recupera la lista de Detalles de un Proceso guardado
 * @author nelson
 */
public class UploadProcessDetailListCommand {

    private static Logger log = Logger.getLogger(UploadProcessDetailListCommand.class);
    private ReportingHibernateDAO dao;

    public List<UploadProcessDetail> execute() throws ScopixException {
        log.debug("start");
        List<UploadProcessDetail> list = getDao().getUploadProcessDetailList(ProcessState.SCHEDULED);
        log.debug("end");
        return list;
    }

    public ReportingHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(ReportingHibernateDAOImpl.class);
        }
        return dao;
    }

    public void setDao(ReportingHibernateDAO dao) {
        this.dao = dao;
    }
}
