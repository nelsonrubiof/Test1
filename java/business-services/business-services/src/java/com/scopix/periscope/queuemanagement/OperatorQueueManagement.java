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
 * OperatorQueueManagement.java
 *
 * Created on 25-03-2010, 02:30:40 PM
 *
 */
package com.scopix.periscope.queuemanagement;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.commands.AddOperatorQueueCommand;
import com.scopix.periscope.queuemanagement.commands.GetOperatorQueueByNameCommand;
import com.scopix.periscope.queuemanagement.commands.GetOperatorQueueCommand;
import com.scopix.periscope.queuemanagement.commands.GetOperatorQueueDetailListCommand;
import com.scopix.periscope.queuemanagement.commands.GetOperatorQueueDetailListToValidateCommand;
import com.scopix.periscope.queuemanagement.commands.GetOperatorQueueListCommand;
import com.scopix.periscope.queuemanagement.commands.InactiveOperatorQueueCommand;
import com.scopix.periscope.queuemanagement.commands.UpdateOperatorQueueCommand;
import com.scopix.periscope.securitymanagement.OperatorQueueManagementPermissions;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import com.scopix.periscope.securitymanagement.SecurityManager;

/**
 *
 * @author Gustavo Alvarez
 */
@SpringBean(rootClass = OperatorQueueManagement.class)
@Transactional(rollbackFor = {ScopixException.class})
public class OperatorQueueManagement {

    private Logger log = Logger.getLogger(OperatorQueueManagement.class);

    public OperatorQueue getOperatorQueue(Integer operatorQueueId, long sessionId) throws ScopixException {
        log.debug("start");
        OperatorQueue operatorQueue = null;

        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        secMan.checkSecurity(sessionId, OperatorQueueManagementPermissions.GET_OPERATOR_QUEUE_PERMISSION);

        GetOperatorQueueCommand command = new GetOperatorQueueCommand();
        operatorQueue = command.execute(operatorQueueId);

        log.debug("end");
        return operatorQueue;
    }

    public List<OperatorQueue> getOperatorQueueByName(OperatorQueue operatorQueue, long sessionId) throws ScopixException {
        log.debug("start");
        List<OperatorQueue> operatorQueues = null;

        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        secMan.checkSecurity(sessionId, OperatorQueueManagementPermissions.GET_OPERATOR_QUEUE_PERMISSION);

        GetOperatorQueueByNameCommand command = new GetOperatorQueueByNameCommand();
        operatorQueues = command.execute(operatorQueue);

        log.debug("end");
        return operatorQueues;
    }

    public List<OperatorQueue> getOperatorQueuesList(OperatorQueue operatorQueue, long sessionId) throws ScopixException {
        log.debug("start");
        List<OperatorQueue> operatorQueues = null;

        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        secMan.checkSecurity(sessionId, OperatorQueueManagementPermissions.GET_OPERATOR_QUEUE_LIST_PERMISSION);

        GetOperatorQueueListCommand command = new GetOperatorQueueListCommand();
        operatorQueues = command.execute(operatorQueue);
        log.debug("queueListSize: " + operatorQueues.size());

        log.debug("end");
        return operatorQueues;
    }

    public List<OperatorQueueDetail> getOperatorQueuesDetailList(OperatorQueue operatorQueue) throws ScopixException {
        log.debug("start");
        List<OperatorQueueDetail> details = null;

        GetOperatorQueueDetailListCommand command = new GetOperatorQueueDetailListCommand();
        details = command.execute(operatorQueue);

        log.debug("end");
        return details;
    }

    public void addOperatorQueue(OperatorQueue operatorQueue, long sessionId) throws ScopixException {
        log.debug("start");
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        secMan.checkSecurity(sessionId, OperatorQueueManagementPermissions.ADD_OPERATOR_QUEUE_PERMISSION);

        AddOperatorQueueCommand command = new AddOperatorQueueCommand();
        command.execute(operatorQueue);

        log.debug("end");
    }

    public void updateOperatorQueue(OperatorQueue operatorQueue, long sessionId) throws ScopixException {
        log.debug("start");
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        secMan.checkSecurity(sessionId, OperatorQueueManagementPermissions.EDIT_OPERATOR_QUEUE_PERMISSION);

        UpdateOperatorQueueCommand command = new UpdateOperatorQueueCommand();
        command.execute(operatorQueue);

        log.debug("end");
    }

    public void inactiveOperatorQueue(OperatorQueue operatorQueue, long sessionId) throws ScopixException {
        log.debug("start");
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        secMan.checkSecurity(sessionId, OperatorQueueManagementPermissions.DELETE_OPERATOR_QUEUE_PERMISSION);

        InactiveOperatorQueueCommand command = new InactiveOperatorQueueCommand();
        command.execute(operatorQueue);

        log.debug("end");
    }

    /**
     * Metodo que verifica si la combinacion store-areatype existe previamente en otra cola ingresada previamente
     *
     * @param operatorQueueDetail
     * @return Boolean Indica si la combinacion esta repetida. TRUE para indicar repeticion. FALSE en caso contrario
     * @throws PeriscopeException
     * @throws PeriscopeSecurityException
     */
    public Boolean validateStoreAreaTypeRepeat(OperatorQueueDetail operatorQueueDetail, long sessionId) throws ScopixException {
        Boolean resp = false;
        List<OperatorQueueDetail> details = null;

        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        secMan.checkSecurity(sessionId, OperatorQueueManagementPermissions.GET_OPERATOR_QUEUE_LIST_PERMISSION);

        GetOperatorQueueDetailListToValidateCommand command = new GetOperatorQueueDetailListToValidateCommand();
        details = command.execute(operatorQueueDetail);

        //esto significa que si existe un detalle, la dupla no puede ser ingresada nuevamente.
        if (details != null && details.size() > 0) {
            resp = true;
        }

        return resp;
    }

    /**
     *
     * @param name
     * @param sessionId
     * @return Boolean Verifica si el nombre de la cola ya fue ingresado. TRUE indica que el nombre ya fue ingresado. FALSE en
     * caso contrario
     * @throws PeriscopeException
     * @throws PeriscopeSecurityException
     */
    public Boolean validateQueueByName(String name, long sessionId) throws ScopixException {
        Boolean resp = false;
        List<OperatorQueue> list = null;

        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        secMan.checkSecurity(sessionId, OperatorQueueManagementPermissions.GET_OPERATOR_QUEUE_PERMISSION);

        OperatorQueue operatorQueue = new OperatorQueue();
        operatorQueue.setName(name);
        operatorQueue.setActivo(Boolean.TRUE);

        list = this.getOperatorQueueByName(operatorQueue, sessionId);

        if (list != null && list.size() > 0) {
            resp = true;
        }

        return resp;
    }
}
