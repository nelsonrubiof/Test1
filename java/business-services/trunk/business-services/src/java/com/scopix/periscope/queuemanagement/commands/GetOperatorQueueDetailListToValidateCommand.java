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
 * GetOperatorQueueDetailListToValidateCommand.java
 *
 * Created on 04-04-2010, 11:22:23 PM
 *
 */
package com.scopix.periscope.queuemanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.OperatorQueueDetail;
import com.scopix.periscope.queuemanagement.dao.OperatorQueueManagementHibernateDAO;
import java.util.List;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetOperatorQueueDetailListToValidateCommand {

    public List<OperatorQueueDetail> execute(OperatorQueueDetail operatorQueueDetail) throws ScopixException {
        List<OperatorQueueDetail> details = null;
        OperatorQueueManagementHibernateDAO dao = SpringSupport.getInstance().findBeanByClassName(
                OperatorQueueManagementHibernateDAO.class);

        details = dao.getOperatorQueuesDetailList(operatorQueueDetail);

        return details;
    }
}
