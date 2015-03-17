/*
 * 
 * Copyright (C) 2013, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * QueueManagerImpl.java
 *
 * Created on 05/11/20013, 09:14:59 PM
 *
 */
package com.scopix.periscope.queuemanagement;


import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.queuemanagement.dto.OperatorQueueDTO;
import com.scopix.periscope.queuemanagement.services.webservices.QueueManagementWebServices;
import com.scopix.periscope.queuemanagement.services.webservices.client.QueueManagementWebServiceClient;
import java.util.List;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Sebastian
 */
@SpringBean(rootClass = QueueManager.class)
@Transactional(rollbackFor = {ScopixException.class, Exception.class})
public class QueueManagerImpl implements QueueManager {

    private Logger log = Logger.getLogger(QueueManagerImpl.class);  
    private static Map<String, Map<String, Integer>> pendingEvaluationEvidencesMap;
    private static final String SECURITY_PASS_PHRASE = "REQUESTING-ACCESS-TO-SERVICE-FROM-SCOPIX-APP";
    /**
     * Retrieve a list with operator queues and its details for a desired
     * corporate
     *
     * @param corporate
     * @return
     * @throws ScopixException
     */
    @Override
    public List<Queue> getQueuesForOperator(String corporate) throws ScopixException {
            log.info("calling  getQueuesForOperator");
            return processOperatorQueues(getQueManagementWebService(
                    corporate.toUpperCase().replaceAll(" ", "_")).getOperatorQueues());
    }
    
    /**
     * Procesa las colas del cliente
     *
     * @author carlos polo
     * @version 1.0.0
     * @param queuesDTO 
     * @return 
     * @since 6.0
     * @date 19/02/2013
     */
    protected List<Queue> processOperatorQueues(List<OperatorQueueDTO> queuesDTO) {
        List<Queue> queues = new ArrayList<Queue>();
        if (queuesDTO != null && !queuesDTO.isEmpty()) {
            for (OperatorQueueDTO queueDTO : queuesDTO) {
                Queue queue = new Queue();
                queue.setId(queueDTO.getId());
                queue.setName(queueDTO.getName());
                queues.add(queue);
            }
        }
        return queues;
    }
    
    /**
     * calls a webservice method that receives a map containing all the available
     * pending evaluation evidences for corporate and ordered by queue and saves this info
     * in a static hashmap
     * @param corporate
     * @throws ScopixException
     */
    @Override
    public void getPendingEvaluationEvidenceMap(String corporate) throws ScopixException {

        log.info("Getting pending evaluation evidences for corporate: " + corporate);
        Map<String, Integer> pendingEvalsMap = getQueManagementWebServiceWithTimeout(StringUtils
                .replace(corporate, " ", "_").toUpperCase()).countPendingEvaluationEvidences(SECURITY_PASS_PHRASE);
        getPendingEvaluationEvidencesMap().put(corporate, pendingEvalsMap);
         log.info("Response received for corporate: " + corporate);
    }
    
    /**
     * returns a Integer representing the total of queues
     * @param corporateName 
     * @param queueName
     * @return
     */
    @Override
    public synchronized Integer getPendingElementsForQueue(String corporateName, String queueName) {
        if (getPendingEvaluationEvidencesMap().containsKey(corporateName)) {
            if (getPendingEvaluationEvidencesMap().get(corporateName).containsKey(queueName)) {
                Integer result = getPendingEvaluationEvidencesMap().get(corporateName).get(queueName);
                if(result > 0){
                   getPendingEvaluationEvidencesMap().get(corporateName).put(queueName, result -1);
                }
                return result;
            }
        }
        return 0;
    }

    /**
     * @return the pendingEvaluationEvidencesMap
     */
    public static Map<String, Map<String, Integer>> getPendingEvaluationEvidencesMap() {
        if(pendingEvaluationEvidencesMap == null){
            pendingEvaluationEvidencesMap = new HashMap<String, Map<String, Integer>>();
        }    
        return pendingEvaluationEvidencesMap;
    }

    /**
     * @param aPendingEvaluationEvidencesMap the pendingEvaluationEvidencesMap to set
     */
    public static void setPendingEvaluationEvidencesMap(Map<String, Map<String, Integer>> aPendingEvaluationEvidencesMap) {
        pendingEvaluationEvidencesMap = aPendingEvaluationEvidencesMap;
    }

    private QueueManagementWebServices getQueManagementWebService(String corporate) throws ScopixException {       
        QueueManagementWebServices queueWebService = new QueueManagementWebServiceClient().getWebService(corporate);
        return queueWebService;
    }
    
    private QueueManagementWebServices getQueManagementWebServiceWithTimeout(String corporate) throws ScopixException {    
        QueueManagementWebServices queueWebService = new QueueManagementWebServiceClient().getWebService(corporate);
        Client cl = ClientProxy.getClient(queueWebService);
        HTTPConduit http = (HTTPConduit) cl.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(25000);
        httpClientPolicy.setReceiveTimeout(20000); 
        http.setClient(httpClientPolicy);
        return queueWebService;
    }

}
