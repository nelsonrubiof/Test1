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
 *  OperatorsGroupDAO.java
 * 
 *  Created on May 28, 2014, 1:01:58 PM
 * 
 */
package com.scopix.periscope.operatorsgroup.dao;

/**
 *
 * @author Sebastian
 */
public interface OperatorsGroupDAO {

    /**
     * Finds Group by user name
     *
     * @param groupName
     * @return boolean
     */
    boolean existGroupName(String groupName);
}
