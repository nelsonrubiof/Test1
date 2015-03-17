/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * RejectBWCommand.java
 *
 * Created on 15-10-2008, 11:51:55 AM
 *
 */
package com.scopix.periscope.businesswarehouse.transfer.commands;

import com.scopix.periscope.businesswarehouse.transfer.dao.TransferBWHibernateDAO;
import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public class RejectBWCommand {

    private TransferBWHibernateDAO dao;
    private TransferHibernateDAO hibernateDAO;
    private Logger log = Logger.getLogger(RejectBWCommand.class);

    public void execute(List<Integer> oseIds, List<Integer> valIds) throws ScopixException {
        log.info("start [oseIds:" + StringUtils.join(oseIds.toArray(), ",") + "]"
                + "[valIds:" + StringUtils.join(valIds.toArray(), ",") + "]");
        try {
            getDao().validateConnection(false);
            if (oseIds != null && !oseIds.isEmpty()) {
                log.debug("DELETING compliance_facts with id = " + StringUtils.join(oseIds.toArray(), ","));
                getDao().deleteComplianceFacts(oseIds);
                log.debug("DELETED compliance_facts with id = " + StringUtils.join(oseIds.toArray(), ","));

            }
            if (valIds != null && !valIds.isEmpty()) {
                log.debug("DELETING indicator_facts with id = " + StringUtils.join(valIds.toArray(), ","));
                getDao().deleteIndicatorFacts(valIds);
                log.debug("DELETED indicator_facts with id = " + StringUtils.join(valIds.toArray(), ","));
            }
        } catch (ScopixException e) {
            log.debug("Error when try to delete the fact", e);
            //se debe dejar en base de datos un registro con la tupla a procesar al momento del reenvio
            //getHibernateDAO().addRejectError(oseIds, valIds);
            throw new ScopixException(e);
        }
        log.info("end");
    }

    public TransferBWHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(TransferBWHibernateDAO.class);
        }
        return dao;
    }

    public void setDao(TransferBWHibernateDAO dao) {
        this.dao = dao;
    }

    public TransferHibernateDAO getHibernateDAO() {
        if (hibernateDAO == null) {
            hibernateDAO = SpringSupport.getInstance().findBeanByClassName(TransferHibernateDAO.class);
        }
        return hibernateDAO;
    }

    public void setHibernateDAO(TransferHibernateDAO hibernateDAO) {
        this.hibernateDAO = hibernateDAO;
    }
}
