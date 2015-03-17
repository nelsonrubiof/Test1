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
 *  DeleteOperatorGroupCommand.java
 * 
 *  Created on May 29, 2014, 2:55:00 PM
 * 
 */
package com.scopix.periscope.operatorsgroup.command;

import com.scopix.periscope.operatorsgroup.OperatorsGroup;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 *
 * @author Sebastian
 */
public class DeleteOperatorGroupCommand {

    /**
     * remove Operators Group
     * @param operatorsGroupId
     */
    public void execute(Integer operatorsGroupId) {
        HibernateSupport.getInstance().findGenericDAO().remove(operatorsGroupId, OperatorsGroup.class);

    }
}