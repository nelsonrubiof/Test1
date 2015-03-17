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
 *  GroupManager.java
 * 
 *  Created on May 28, 2014, 12:35:43 PM
 * 
 */
package com.scopix.periscope.operatorsgroup.management;

import com.scopix.periscope.operatorsgroup.OperatorsGroup;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public interface OperatorsGroupManager {

    /**
     * get Operators group given an Id
     *
     * @param id
     * @return OperatorsGroup
     */
    OperatorsGroup getOperatorsGroup(Integer id);

    /**
     * saves desired Group
     *
     * @param group
     * @throws ScopixException
     */
    void saveOperatorsGroup(OperatorsGroup group) throws ScopixException;

    /**
     * gets All Operators Groups
     *
     * @return List<OperatorsGroup>
     */
    List<OperatorsGroup> getAllOperatorsGroups();

    /**
     * deletes an Operator Group by id
     *
     * @param id
     */
    void deleteOperatorGroup(Integer id);

    /**
     *
     * @param listOfUsers
     * @param operatorsGroupId
     */
    void saveOperatorsGroupUsers(List<String> listOfUsers, Integer operatorsGroupId);
}
