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
 *  GetAllOperatorsGroupCommand.java
 * 
 *  Created on May 29, 2014, 12:47:46 PM
 * 
 */
package com.scopix.periscope.operatorsgroup.command;

import com.scopix.periscope.operatorsgroup.OperatorsGroup;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class GetAllOperatorsGroupCommand {

    /**
     * returns all Operators Groups Stored in db
     *
     * @return List<OperatorsGroup>
     */
    public List<OperatorsGroup> execute() {
        return HibernateSupport.getInstance().findGenericDAO().getAll(OperatorsGroup.class);
    }
}
