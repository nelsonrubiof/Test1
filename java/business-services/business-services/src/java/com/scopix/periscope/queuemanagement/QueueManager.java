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
 * QueueManager.java
 *
 * Created on 20-05-2008, 01:12:58 PM
 *
 */
package com.scopix.periscope.queuemanagement;

import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.AreaDTO;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.EvaluationState;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.DTOTranformUtil;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.commands.ChangeStateCommand;
import com.scopix.periscope.queuemanagement.commands.ExecutePriorityChangeCommand;
import com.scopix.periscope.queuemanagement.commands.ExecutePriorityChangeRecoveryCommand;
import com.scopix.periscope.queuemanagement.commands.GetEvidencesAvailableCountCommand;
import com.scopix.periscope.queuemanagement.commands.GetNFirstElementOfQueueCommand;
import com.scopix.periscope.queuemanagement.commands.GetOperatorQueuesCommand;
import com.scopix.periscope.queuemanagement.commands.GetPendingEvaluationListCommand;
import com.scopix.periscope.queuemanagement.commands.GetSummaryListCommand;
import com.scopix.periscope.queuemanagement.commands.MoveToCommand;
import com.scopix.periscope.queuemanagement.dto.FilteringData;
import com.scopix.periscope.queuemanagement.dto.OperatorQueueDTO;
import com.scopix.periscope.queuemanagement.dto.PendingEvaluationDTO;
import com.scopix.periscope.queuemanagement.dto.SummaryDTO;
import com.scopix.periscope.securitymanagement.QueueManagerPermissions;
import com.scopix.periscope.securitymanagement.SecurityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@SpringBean(rootClass = QueueManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class QueueManager {

    private Logger log = Logger.getLogger(QueueManager.class);

    public List<PendingEvaluationDTO> getPendingEvaluationList(FilteringData filter, long sessionId) throws ScopixException {
        log.debug("start");
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.SecurityManager.class);
        secMan.checkSecurity(sessionId, QueueManagerPermissions.GET_PENDING_EVALUATION_LIST_FOR_QUEUE_MANAGER_PERMISSION);
        GetPendingEvaluationListCommand command = new GetPendingEvaluationListCommand();
        List<PendingEvaluationDTO> pendingEvaluationDTOs = command.execute(filter);
        log.debug("end, result = " + pendingEvaluationDTOs);
        return pendingEvaluationDTOs;
    }

    public List<PendingEvaluationDTO> changePriority(List<Integer> pendingEvaluationIds, int startPriority, FilteringData filter,
            long sessionId) throws ScopixException {
        log.debug("start");
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.SecurityManager.class);
        secMan.checkSecurity(sessionId, QueueManagerPermissions.CHANGE_PRIORITY_PERMISSION);
        ExecutePriorityChangeCommand command = new ExecutePriorityChangeCommand();
        command.execute(pendingEvaluationIds, filter.getQueueNameId(), startPriority);
        List<PendingEvaluationDTO> pendingEvaluationDTOs = this.getPendingEvaluationList(filter, sessionId);
        log.debug("end, result = " + pendingEvaluationDTOs);
        return pendingEvaluationDTOs;
    }

    public void changeState(List<Integer> pendingEvaluationIds, String state, long sessionId) throws ScopixException {
        log.debug("start");
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.SecurityManager.class);
        secMan.checkSecurity(sessionId, QueueManagerPermissions.CHANGE_STATE_PERMISSION);
        ChangeStateCommand command = new ChangeStateCommand();
        command.execute(pendingEvaluationIds, state);
        log.debug("end");
    }

//    @Deprecated
//    private void savePriorityChange(List<PendingEvaluationDTO> queueList) throws ScopixException {
//        log.debug("start");
//        SavePriorityChangeCommand command = new SavePriorityChangeCommand();
//        command.execute(queueList);
//        log.debug("end");
//    }
    public List<PendingEvaluationDTO> backToQueue(List<Integer> checksIds, FilteringData filters, Integer startPosition,
            EvaluationState evaluationState, long sessionId) throws ScopixException {
        log.debug("start");
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.SecurityManager.class);
        secMan.checkSecurity(sessionId, QueueManagerPermissions.BACK_TO_QUEUE_PERMISSION);
        ExecutePriorityChangeRecoveryCommand command = new ExecutePriorityChangeRecoveryCommand();
        command.execute(checksIds, startPosition, filters.getQueueNameId());
        filters.setStatus(evaluationState.getName());
        List<PendingEvaluationDTO> pendingEvaluationDTOs = this.getPendingEvaluationList(filters, sessionId);
        log.debug("end, result = " + pendingEvaluationDTOs);
        return pendingEvaluationDTOs;
    }

    public List<AreaDTO> getAreas(long sessionId) throws ScopixException {
        log.debug("start");
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.SecurityManager.class);
        secMan.checkSecurity(sessionId, QueueManagerPermissions.GET_AREAS_PERMISSION);
        List<Area> areas = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).
                getAreaList(new Area(), sessionId);
        List<AreaDTO> areaDTOs = DTOTranformUtil.getInstance().transformEntityListToDOTList(areas, AreaDTO.class, false);
        log.debug("end, result = " + areaDTOs);
        return areaDTOs;
    }

    public List<StoreDTO> getStores(long sessionId) throws ScopixException {
        log.debug("start");
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.SecurityManager.class);
        secMan.checkSecurity(sessionId, QueueManagerPermissions.GET_STORES_FOR_QM_PERMISSION);
        log.debug("security ok");
        List<Store> stores = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getStoreList(null,
                sessionId);
        log.debug("stores = " + stores.size());
        DTOTranformUtil.getInstance().setRecursiveLevels(1);
        log.debug("before transform");

        List<StoreDTO> storeDTOs = new ArrayList<StoreDTO>();
        for (Store store : stores) {
            StoreDTO sdto = new StoreDTO();
            sdto.setId(store.getId());
            sdto.setName(store.getName());
            sdto.setDescription(store.getDescription());
            sdto.setAreas(new ArrayList<AreaDTO>());
            storeDTOs.add(sdto);
            for (Area a : store.getAreas()) {
                AreaDTO adto = new AreaDTO();
                adto.setId(a.getId());
                adto.setName(a.getName());
                adto.setDescription(a.getDescription());
                sdto.getAreas().add(adto);
            }
        }

        log.debug("after transform, stores = " + storeDTOs.size());
        return storeDTOs;
    }

    public List<SummaryDTO> getSummaryList(long sessionId) throws ScopixException {
        log.debug("start");
        List<SummaryDTO> summary = null;
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.SecurityManager.class);
        secMan.checkSecurity(sessionId, QueueManagerPermissions.GET_SUMMARY_LIST_PERMISSION);
        GetSummaryListCommand command = new GetSummaryListCommand();
        summary = command.execute();
        log.debug("end");
        return summary;
    }

    public List<PendingEvaluationDTO> getNFirstElementOfQueue(int quantity, Integer queueNameId, long sessionId) throws
            ScopixException {
        log.debug("start");
        List<PendingEvaluationDTO> list = null;
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.SecurityManager.class);
        secMan.checkSecurity(sessionId, QueueManagerPermissions.GET_N_FIRST_ELEMENT_OF_QUEUE_LIST_PERMISSION);
        GetNFirstElementOfQueueCommand command = new GetNFirstElementOfQueueCommand();
        list = command.execute(quantity, queueNameId);
        log.debug("end");
        return list;
    }

    public List<OperatorQueueDTO> getOperatorQueues() throws ScopixException {
        log.debug("start");
        List<OperatorQueueDTO> oqs = null;
        GetOperatorQueuesCommand getOperatorQueuesCommand = new GetOperatorQueuesCommand();
        oqs = getOperatorQueuesCommand.execute();
        log.debug("end");
        return oqs;
    }

    public List<PendingEvaluationDTO> moveTo(List<Integer> peIds, Integer toQueue, Integer position, FilteringData filters,
            long sessionId)
            throws ScopixException {
        log.debug("start");
        MoveToCommand moveToCommand = new MoveToCommand();
        ExecutePriorityChangeCommand executePriorityChangeCommand = new ExecutePriorityChangeCommand();
        moveToCommand.execute(peIds, toQueue);
        executePriorityChangeCommand.execute(peIds, toQueue, position);

        List<PendingEvaluationDTO> pedtos = this.getPendingEvaluationList(filters, sessionId);
        log.debug("end");
        return pedtos;
    }

    /**
     * Looks on the database for available evidences to be processed the result is mapped on map and each key will be equivalent
     * to the queue name and the value is a Integer representing the total of evidences available to be processed for that
     * specific queue
     *
     * @return Map<String, Integer> ex DSO,9
     */
    public Map<String, Integer> getEvidencesAvailableCountMap() throws ScopixException {
        log.info("start [ getEvidencesAvailableCountMap ");
        GetEvidencesAvailableCountCommand command = new GetEvidencesAvailableCountCommand();
        Map<String, Integer> result = command.execute();
        log.info("end");
        return result;
    }
}
