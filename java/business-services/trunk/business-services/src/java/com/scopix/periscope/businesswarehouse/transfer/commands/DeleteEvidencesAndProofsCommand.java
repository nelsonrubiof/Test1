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
 *  DeleteEvidencesAndProofsCommand.java
 * 
 *  Created on 12-10-2011, 11:11:42 AM
 * 
 */
package com.scopix.periscope.businesswarehouse.transfer.commands;

import com.scopix.periscope.businesswarehouse.transfer.dao.TransferBWHibernateDAO;
import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class DeleteEvidencesAndProofsCommand {

    private TransferHibernateDAO daoBS;
    private TransferBWHibernateDAO daoBW;
    private Logger log = Logger.getLogger(DeleteEvidencesAndProofsCommand.class);

    public void execute(List<Integer> omIds) {
        log.info("start");

        try {
            //get proofs
            List<Integer> proofIdList = getDaoBW().getProofList(omIds);

            log.debug("proofIdList size: " + proofIdList.size());

            //deleting proofs
            getDaoBW().deleteProofs(proofIdList);
            log.debug("proofs deleted");

            //deleting evidences and proofs without proofs associated
            getDaoBW().deleteEmptyEvidencesAndProofs();
            log.debug("evidences and proofs deleted");

            //updating proof data status
            if (proofIdList.size() > 0) {
                getDaoBS().updateProofSentToMisData(proofIdList, Boolean.FALSE);
            }
        } catch (ScopixException e) {
            log.warn("Error when try to delete evidences and proofs", e);
        }

        log.info("end");
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

    public TransferHibernateDAO getDaoBS() {
        if (daoBS == null) {
            daoBS = SpringSupport.getInstance().findBeanByClassName(TransferHibernateDAO.class);
        }
        return daoBS;
    }

    public void setDaoBS(TransferHibernateDAO daoBS) {
        this.daoBS = daoBS;
    }
}
