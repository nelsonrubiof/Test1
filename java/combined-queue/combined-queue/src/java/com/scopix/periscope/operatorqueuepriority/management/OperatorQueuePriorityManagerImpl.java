/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 *
 * Created on 07/11-2013, 03:36:14 PM
 *
 */
package com.scopix.periscope.operatorqueuepriority.management;

import com.scopix.periscope.operatorqueuepriority.OperatorQueuePriority;
import com.scopix.periscope.operatorqueuepriority.command.DeleteOperatorQueuePriorityCommand;
import com.scopix.periscope.operatorqueuepriority.command.GetAllByCorporateNameCommand;
import com.scopix.periscope.operatorqueuepriority.command.GetAllOrderedInMapCommand;
import com.scopix.periscope.operatorqueuepriority.command.GetByCorporateIdAndOperatorQueueNameCommand;
import com.scopix.periscope.operatorqueuepriority.command.SaveOQUserSubscriptionsCommand;
import com.scopix.periscope.operatorqueuepriority.command.SaveOperatorQueuePriorityCommand;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Sebastian
 */
@SpringBean(rootClass = OperatorQueuePriorityManager.class)
@Transactional(rollbackFor = {ScopixException.class, Exception.class})
public class OperatorQueuePriorityManagerImpl implements OperatorQueuePriorityManager {

    private Logger log = Logger.getLogger(OperatorQueuePriorityManagerImpl.class);

    @Override
    public void saveOperatorQueuePriority(OperatorQueuePriority operatorQueuePriority) {
        log.info("calling  SaveOperatorQueuePriorityCommand ");
        SaveOperatorQueuePriorityCommand command = new SaveOperatorQueuePriorityCommand();
        command.execute(operatorQueuePriority);
    }

    @Override
    public List<OperatorQueuePriority> getAllByCorporateName() {
        log.info("calling  getAllByCorporateNameCommand ");
        GetAllByCorporateNameCommand command = new GetAllByCorporateNameCommand();
        return command.execute();
    }

    @Override
    public void deleteOperatorQueuePriority(Integer id) {
        log.info("calling  DeleteOperatorQueuePriorityCommand ");
        DeleteOperatorQueuePriorityCommand command = new DeleteOperatorQueuePriorityCommand();
        command.execute(id);
    }

    @Override
    public Map<Integer, List<String>> getAllOrderedInMap() {
        log.info("calling  DeleteOperatorQueuePriorityCommand ");
        GetAllOrderedInMapCommand command = new GetAllOrderedInMapCommand();
        return command.execute();
    }

    @Override
    public void saveOperatorQueueUserSubscriptions(List<String> listOfUsers, List<Integer> listofGroups, Integer corporateId, String operatorQueueName) {
        log.info("calling  SaveOQUserSubscriptionsCommand ");
        SaveOQUserSubscriptionsCommand command = new SaveOQUserSubscriptionsCommand();
        command.execute(listOfUsers, listofGroups, corporateId, operatorQueueName);
    }

    @Override
    public OperatorQueuePriority getByCorporateIdAndOperatorQueueName(Integer corporateId, String operatorQueueName) {
        log.info("calling  GetByCorporateIdAndOperatorQueue ");
        GetByCorporateIdAndOperatorQueueNameCommand command = new GetByCorporateIdAndOperatorQueueNameCommand();
        return command.execute(corporateId, operatorQueueName);
    }
}
