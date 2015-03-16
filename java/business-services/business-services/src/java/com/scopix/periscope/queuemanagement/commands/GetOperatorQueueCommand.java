/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * GetOperatorQueueCommand.java
 *
 * Created on 26-03-2010, 12:14:21 PM
 *
 */
package com.scopix.periscope.queuemanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.queuemanagement.OperatorQueue;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetOperatorQueueCommand {

    public OperatorQueue execute(Integer id) throws ScopixException {
        OperatorQueue operatorQueue = null;
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        try {
            operatorQueue = dao.get(id, OperatorQueue.class);
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException("elementNotFound", objectRetrievalFailureException);
        }

        return operatorQueue;
    }
}
