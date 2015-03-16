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
 *  GetEvidencesAndProofsForTransferCommand.java
 * 
 *  Created on 22-07-2011, 12:28:42 PM
 * 
 */
package com.scopix.periscope.businesswarehouse.transfer.commands;

import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import com.scopix.periscope.evaluationmanagement.Proof;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetProofListCommand {

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

    public List<Proof> execute(Set<Integer> proofIds) {
        return getDao().getProofList(proofIds);
    }
}
