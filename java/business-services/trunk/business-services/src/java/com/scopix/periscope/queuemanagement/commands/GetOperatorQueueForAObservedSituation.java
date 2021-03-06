/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * AddBusinessRuleCheckCommand.java
 *
 * Created on 08-05-2008, 12:59:35 PM
 *
 */
package com.scopix.periscope.queuemanagement.commands;

import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.OperatorQueue;
import com.scopix.periscope.queuemanagement.dao.QueueManagementHibernateDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetOperatorQueueForAObservedSituation {

    private Logger log = Logger.getLogger(GetOperatorQueueForAObservedSituation.class);

    /**
     * Dado un observed situation se obtiene la cola configurada, debe ser una.
     * @param observedSituation
     * @return
     * @throws PeriscopeException
     */
    public OperatorQueue execute(ObservedSituation observedSituation) throws ScopixException {
        log.debug("start");
        OperatorQueue operatorQueue = null;
        observedSituation = HibernateSupport.getInstance().findGenericDAO().get(observedSituation.getId(), ObservedSituation.class);
        QueueManagementHibernateDAO dao = SpringSupport.getInstance().findBeanByClassName(QueueManagementHibernateDAO.class);
        operatorQueue = dao.getOperatorQueueForAObservedSituation(observedSituation);
        log.debug("end, result = " + operatorQueue);
        return operatorQueue;
    }
}
