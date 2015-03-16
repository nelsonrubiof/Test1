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
 *  AddRejectErrorListCommand.java
 * 
 *  Created on 04-07-2014, 17:01:40 PM
 * 
 */
package com.scopix.periscope.businesswarehouse.transfer.commands;

import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class AddRejectErrorListCommand {
    private TransferHibernateDAO hibernateDAO;
    private Logger log = Logger.getLogger(AddRejectErrorListCommand.class);
    
    public void execute(List<Integer> oseIds, List<Integer> valIds) {
        getHibernateDAO().addRejectError(oseIds, valIds);
    }

    /**
     * @return the hibernateDAO
     */
    public TransferHibernateDAO getHibernateDAO() {
        return hibernateDAO;
    }

    /**
     * @param hibernateDAO the hibernateDAO to set
     */
    public void setHibernateDAO(TransferHibernateDAO hibernateDAO) {
        this.hibernateDAO = hibernateDAO;
    }
}
