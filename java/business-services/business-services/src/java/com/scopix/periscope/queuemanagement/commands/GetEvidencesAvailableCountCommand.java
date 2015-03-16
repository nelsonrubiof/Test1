/*
 * 
 * Copyright 2013, SCOPIX. All rights reserved. 
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * GetEvidencesAvailableCountMapCommand.java
 *
 * Created on 22-11-2013
 *
 */
package com.scopix.periscope.queuemanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.dao.QueueManagementHibernateDAO;
import java.util.Map;

/**
 *
 * @author Sebastian Torres Brown
 */
public class GetEvidencesAvailableCountCommand {

    /**
     * Looks on the database for available evidences to be processed
     * the result is mapped on map and each key will be equivalent to the queue
     * name and the value is a Integer representing the total of evidences
     * available to be processed for that specific queue
     * 
     * @return Map<String, Integer> ex DSO,9
     * @throws PeriscopeException
     */
    public Map<String, Integer> execute() throws ScopixException {

        QueueManagementHibernateDAO dao = SpringSupport.getInstance()
                .findBeanByClassName(QueueManagementHibernateDAO.class);
        return dao.countAvailableEvidencesByQueue();
    }
}
