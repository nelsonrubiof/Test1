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
 *  CalendarInitDatesCommand.java
 * 
 *  Created on 07-02-2011, 06:14:11 PM
 * 
 */
package com.scopix.periscope.reporting.commands;

import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.LinkedHashMap;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class CalendarInitDatesCommand {

    private static Logger log = Logger.getLogger(CalendarInitDatesCommand.class);
    private TransferHibernateDAO dao;

    public TransferHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(TransferHibernateDAO.class);
        }
        return dao;
    }

    public void setDao(TransferHibernateDAO dao) {
        this.dao = dao;
    }

    public LinkedHashMap<Integer, String> execute() {
        log.debug("start");
        LinkedHashMap<Integer, String> l = getDao().getCalendarInitDates();
        log.debug("end");
        return l;
    }
}
