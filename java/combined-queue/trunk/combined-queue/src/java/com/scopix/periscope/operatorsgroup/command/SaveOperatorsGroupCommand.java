/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
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
 *  SaveGroupCommand.java
 * 
 *  Created on May 28, 2014, 12:42:16 PM
 * 
 */
package com.scopix.periscope.operatorsgroup.command;

import com.scopix.periscope.operatorsgroup.OperatorsGroup;
import com.scopix.periscope.operatorsgroup.dao.OperatorsGroupDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author Sebastian
 */
public class SaveOperatorsGroupCommand {

    /**
     * Saves group check if there is other group with the same name
     *
     * @param group
     */
    public void execute(OperatorsGroup operatorsGroup) throws ScopixException {
        if (SpringSupport.getInstance().findBeanByClassName(OperatorsGroupDAO.class).existGroupName(operatorsGroup.getGroupName())) {
            throw new ScopixException("Group already created");
        }
        HibernateSupport.getInstance().findGenericDAO().save(operatorsGroup);
    }
}
