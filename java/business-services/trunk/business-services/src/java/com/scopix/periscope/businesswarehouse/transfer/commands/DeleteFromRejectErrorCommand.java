/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * DeleteFromRejectErrorCommand.java
 *
 * Created on 08-11-2012, 03:11:50 PM
 *
 */
package com.scopix.periscope.businesswarehouse.transfer.commands;

import com.scopix.periscope.businesswarehouse.transfer.dao.TransferBWHibernateDAO;
import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class DeleteFromRejectErrorCommand {

    private static final String START = "start";
    private static final String END = "end";
    private Logger log = Logger.getLogger(DeleteFromRejectErrorCommand.class);
    private TransferHibernateDAO dao;
    private TransferBWHibernateDAO daoBW;

    /**
     * Elimina los rechazados de BW
     */
    public void execute() {
        log.info(START);
        try {
            List<Map> listaRejectError = getDao().getRejectError();
            Set<Integer> idsDelete = new HashSet<Integer>();
            for (Map det : listaRejectError) {
                Integer idTupla = (Integer) det.get("id");
                String oseIds = (String) det.get("ose_id");
                String indicatorValues = (String) det.get("indicator_values");
                //tratamos de eliminar en BW
                if (oseIds.length() > 0) {
                    getDaoBW().deleteComplianceFacts(oseIds);
                }
                if (indicatorValues.length() > 0) {
                    getDaoBW().deleteIndicatorFacts(indicatorValues);
                }

                idsDelete.add(idTupla);
            }

            //borramos en BS
            getDao().deleteRejectError(idsDelete);
        } catch (Exception ex) {
            log.error("Error: " + ex.getMessage(), ex);
        }
        log.info(END);
    }

    public TransferHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(TransferHibernateDAO.class);
        }
        return dao;
    }

    public void setDao(TransferHibernateDAO dao) {
        this.dao = dao;
    }

    public TransferBWHibernateDAO getDaoBW() {
        if (daoBW == null) {
            daoBW = SpringSupport.getInstance().findBeanByClassName(TransferBWHibernateDAO.class);
        }
        return daoBW;
    }

    public void setDaoBW(TransferBWHibernateDAO daoBW) {
        this.daoBW = daoBW;
    }
}