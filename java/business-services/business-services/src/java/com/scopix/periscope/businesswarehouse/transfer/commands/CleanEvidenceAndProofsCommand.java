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
 * CleanEvidenceAndProofsCommand.java
 *
 * Created on 08-11-2012, 03:12:19 PM
 *
 */
package com.scopix.periscope.businesswarehouse.transfer.commands;

import com.scopix.periscope.businesswarehouse.transfer.dao.TransferBWHibernateDAO;
import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class CleanEvidenceAndProofsCommand {

    private static final String START = "start";
    private static final String END = "end";
    private Logger log = Logger.getLogger(CleanEvidenceAndProofsCommand.class);
    private TransferHibernateDAO dao;
    private TransferBWHibernateDAO daoBW;

    /**
     * Limpia los evidences and proofs que esten sin asociación en bw
     */
    public void execute() {
        log.info(START);

        try {
            List<Integer> proofIdList = getDao().getProofToClean();
            log.debug("proof to delete: " + proofIdList.size());

            getDaoBW().deleteProofs(proofIdList);
            log.debug("proofs deleted");

            getDaoBW().deleteEmptyEvidencesAndProofs();
            log.debug("evidences and proofs deleted");

            //updating proof data status
            if (proofIdList.size() > 0) {
                getDao().updateProofSentToMisData(proofIdList, Boolean.FALSE);
            }
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