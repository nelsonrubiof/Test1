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
package com.scopix.periscope.operatorqueuepriority.command;

import com.scopix.periscope.operatorqueuepriority.OperatorQueuePriority;
import com.scopix.periscope.operatorqueuepriority.dao.OperatorQueuePriorityDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author Sebastian
 */
public class GetAllOrderedInMapCommand {

    private static Logger log = Logger.getLogger(GetAllOrderedInMapCommand.class);

    /**
     * Makes a map with all the corporates and their queues
     *
     * @return List <OperatorQueuePriority>
     */
    public Map<Integer, List<String>> execute() {
        log.debug("executing execute()");
        List<OperatorQueuePriority> list = SpringSupport.getInstance()
                .findBeanByClassName(OperatorQueuePriorityDAO.class)
                .listByCorporateName();
        Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
        for (OperatorQueuePriority operatorQueuePriority : list) {
            List<String> queueNameList = new ArrayList<String>();
            if (map.get(operatorQueuePriority.getCorporateId()) != null) {
                queueNameList = map.get(operatorQueuePriority.getCorporateId());
            }
            queueNameList.add(operatorQueuePriority.getOperatorQueueName());
            map.put(operatorQueuePriority.getCorporateId(), queueNameList);
        }
        return map;
    }
}
