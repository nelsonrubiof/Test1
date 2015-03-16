/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * GetObservedMetricCommand.java
 *
 * Created on 08-05-2008, 03:03:46 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import com.scopix.periscope.evaluationmanagement.Proof;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetProofCommand {

    public Proof execute(Integer id) throws ScopixException {
        TransferHibernateDAO dao = SpringSupport.getInstance().findBeanByClassName(TransferHibernateDAO.class);

        Proof proof = dao.getProof(id);

        return proof;
    }
}
