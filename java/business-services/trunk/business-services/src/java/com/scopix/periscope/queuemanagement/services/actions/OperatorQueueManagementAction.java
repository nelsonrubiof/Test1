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
 * OperatorQueueManagementAction.java
 *
 * Created on 25-03-2010, 01:55:00 PM
 *
 */
package com.scopix.periscope.queuemanagement.services.actions;

import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.OperatorQueue;
import com.scopix.periscope.queuemanagement.OperatorQueueDetail;
import com.scopix.periscope.queuemanagement.OperatorQueueManagement;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author Gustavo Alvarez
 */
@Results({
    @Result(name = "success", value = "/WEB-INF/jsp/operatorqueuemanagement/operatorQueueManagement.jsp"),
    @Result(name = "list", value = "/WEB-INF/jsp/operatorqueuemanagement/operatorQueueList.jsp"),
    @Result(name = "detailList", value = "/WEB-INF/jsp/operatorqueuemanagement/storeAreaGroupList.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/operatorqueuemanagement/operatorQueueEdit.jsp"),
    @Result(name = "showStoreAreaType", value = "/WEB-INF/jsp/operatorqueuemanagement/storeAreaGroup.jsp")
})
@ParentPackage(value = "default")
@Namespace("/operatorqueuemanagement")
public class OperatorQueueManagementAction extends BaseAction implements SessionAware {

    private Logger log = Logger.getLogger(OperatorQueueManagementAction.class);
    private static final String LIST = "list";
    private static final String EDIT = "edit";
    private static final String SHOW_STORE_AREA_TYPE = "showStoreAreaType";
    private static final String DETAIL_LIST = "detailList";
    private Map session;
    private OperatorQueue operatorQueue;
    private List<OperatorQueue> operatorQueues;
    private OperatorQueueDetail operatorQueueDetail;
    private List<OperatorQueueDetail> operatorQueueDetails;
    private Integer[] storeGroup;
    //private Integer[] areaGroup;
    private Integer[] situationTemplateGroup;
    private Integer operatorQueueDetailId;
    private Integer selectedOption;

    @Override
    public String execute() throws Exception {
        this.loadFilters();
        return SUCCESS;
    }

    private void loadFilters() {
        operatorQueue = (OperatorQueue) getSession().get("queueManagementFilter");
    }

    public String readyList() throws ScopixException {
        this.list();
        return LIST;
    }

    public String storeAreaReadyList() throws ScopixException {
        this.detailList();
        return DETAIL_LIST;
    }

    public String newOperatorQueue() {
        this.setEditable(false);
        operatorQueue = new OperatorQueue();
        session.remove("queueManagementFilter");
        session.remove("queueNameOrigin");
        session.remove("operatorQueueEdit");

        operatorQueueDetails = new ArrayList<OperatorQueueDetail>();
        session.put("operatorQueueDetails", operatorQueueDetails);
        operatorQueueDetailId = 1;
        session.put("operatorQueueDetailId", operatorQueueDetailId);
        return EDIT;
    }

    public String cancel() {
        this.loadFilters();
        return SUCCESS;
    }

    public String list() throws ScopixException {
        if (operatorQueue == null || operatorQueue.getId() != null) {
            operatorQueue = (OperatorQueue) getSession().get("queueManagementFilter");
        }
        if (operatorQueue != null) {
            if (selectedOption != null && selectedOption == 1) {
                operatorQueue.setActivo(null);
            } else if (selectedOption != null && selectedOption == 2) {
                operatorQueue.setActivo(Boolean.TRUE);
            } else if (selectedOption != null && selectedOption == 3) {
                operatorQueue.setActivo(Boolean.FALSE);
            }

            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            operatorQueues = SpringSupport.getInstance().findBeanByClassName(OperatorQueueManagement.class).
                    getOperatorQueuesList(operatorQueue, sessionId);
            session.put("queueManagementFilter", operatorQueue);
        }
        return LIST;
    }

    public String detailList() throws ScopixException {
        if (operatorQueue == null || operatorQueue.getId() != null) {
            operatorQueue = (OperatorQueue) getSession().get("operatorQueueEdit");
        }
        if (operatorQueue != null) {
            log.debug("operatorQueueId: " + operatorQueue.getId().toString());
            operatorQueueDetails = SpringSupport.getInstance().findBeanByClassName(OperatorQueueManagement.class).
                    getOperatorQueuesDetailList(operatorQueue);
            session.put("operatorQueueDetails", operatorQueueDetails);
        }
        return DETAIL_LIST;
    }

    public String editOperatorQueue() throws ScopixException {
        if (operatorQueue != null && operatorQueue.getId() != null && operatorQueue.getId() > 0) {
            this.setEditable(true);
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            OperatorQueueManagement management = SpringSupport.getInstance().findBeanByClassName(OperatorQueueManagement.class);
            operatorQueue = management.getOperatorQueue(operatorQueue.getId(), sessionId);

            session.put("queueNameOrigin", operatorQueue.getName());
            session.put("operatorQueueEdit", operatorQueue);
            operatorQueueDetailId = 1;
            session.put("operatorQueueDetailId", operatorQueueDetailId);

        } else {
            this.addActionError(this.getText("error.general.edit",
                    new String[]{this.getText("label.queueManagement.operatorQueue")}));
        }

        return EDIT;
    }

    public String addStoreSituationTemplateGroup() throws ScopixException {
        OperatorQueueManagement management = SpringSupport.getInstance().findBeanByClassName(OperatorQueueManagement.class);
        List<Store> stores = (List<Store>) session.get("storesOperatorQueue");
        List<SituationTemplate> situationTemplates =
                (List<SituationTemplate>) session.get("situationTemplatePojosOperatorQueue");

        Collections.sort(stores);
        Collections.sort(situationTemplates);
        long sessionId = (session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0);
        if (storeGroup != null && storeGroup.length > 0 && situationTemplateGroup != null && situationTemplateGroup.length > 0) {
            for (Integer storeId : storeGroup) {
                Store store = null;
                Store storeSearch = new Store();
                storeSearch.setId(storeId);
                int i = Collections.binarySearch(stores, storeSearch);
                if (i >= 0) {
                    store = stores.get(i);
                }
                for (Integer situationTemplatId : situationTemplateGroup) {
                    Integer id = (Integer) session.get("operatorQueueDetailId");
                    operatorQueueDetails = (List<OperatorQueueDetail>) session.get("operatorQueueDetails");
                    SituationTemplate st = null;
                    SituationTemplate stSearch = new SituationTemplate();
                    stSearch.setId(situationTemplatId);
                    int j = Collections.binarySearch(situationTemplates, stSearch);
                    if (j >= 0) {
                        st = situationTemplates.get(j);
                    }
                    OperatorQueueDetail detail = new OperatorQueueDetail();

                    detail.setId(id);
                    detail.setSituationTemplate(st);
                    detail.setStore(store);

                    //validar nombres repetidos
                    if (!management.validateStoreAreaTypeRepeat(detail, sessionId)) {
                        operatorQueueDetails.add(detail);
                    } else {
                        //indicar repeticion

                        if (store != null && st != null) {
                            this.addActionError(this.getText("operatorQueueManagement.error.storeareatype.repeat",
                                    new String[]{store.getDescription(), st.getName()}));
                        }
                    }

                    //retornando variables a session
                    session.put("operatorQueueDetails", operatorQueueDetails);
                    id = id + 1;
                    session.put("operatorQueueDetailId", id);

                }

            }

            if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
                return DETAIL_LIST;
            }
        }
        return DETAIL_LIST;
    }

//    public String addStoreAreaGroup() throws ScopixException {
//        OperatorQueueManagement management = SpringSupport.getInstance().findBeanByClassName(OperatorQueueManagement.class);
//        List<Store> stores = (List<Store>) session.get("storesOperatorQueue");
//        List<AreaType> areaTypes = (List<AreaType>) session.get("areaTypesOperatorQueue");
//        Collections.sort(stores);
//        Collections.sort(areaTypes);
//        long sessionId = (session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0);
//
//        if (storeGroup != null && storeGroup.length > 0 && areaGroup != null && areaGroup.length > 0) {
//            for (Integer storeId : storeGroup) {
//                Store store = null;
//                Store storeSearch = new Store();
//                storeSearch.setId(storeId);
//                int i = Collections.binarySearch(stores, storeSearch);
//                if (i >= 0) {
//                    store = stores.get(i);
//                }
//
//                //TO-DO reeemplazar por pojo de Situation Template
//                for (Integer areaTypeId : areaGroup) {
//                    Integer id = (Integer) session.get("operatorQueueDetailId");
//                    operatorQueueDetails = (List<OperatorQueueDetail>) session.get("operatorQueueDetails");
//
//                    AreaType areaType = null;
//                    AreaType areaTypeSearch = new AreaType();
//                    areaTypeSearch.setId(areaTypeId);
//
//                    int j = Collections.binarySearch(areaTypes, areaTypeSearch);
//                    if (j >= 0) {
//                        areaType = areaTypes.get(j);
//                    }
//
//                    OperatorQueueDetail detail = new OperatorQueueDetail();
//
//                    detail.setId(id);
////                    detail.setAreaType(areaType);
//                    //TO-DO se debe cambiar para que sea un situation template asiociado
//                    detail.setSituationTemplate(null);
//                    detail.setStore(store);
//
//                    //validar nombres repetidos
//                    if (!management.validateStoreAreaTypeRepeat(detail, sessionId)) {
//                        operatorQueueDetails.add(detail);
//                    } else {
//                        //indicar repeticion
//
//                        if (store != null && areaType != null) {
//                            this.addActionError(this.getText("operatorQueueManagement.error.storeareatype.repeat",
//                                    new String[]{store.getDescription(), areaType.getDescription()}));
//                        }
//                    }
//
//                    //retornando variables a session
//                    session.put("operatorQueueDetails", operatorQueueDetails);
//                    id = id + 1;
//                    session.put("operatorQueueDetailId", id);
//                }
//            }
//
//            if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
//                return DETAIL_LIST;
//            }
//
//        }
//        return DETAIL_LIST;
//    }

    public String deleteOperatorQueueDetail() {
        if (operatorQueueDetail != null && operatorQueueDetail.getId() != null && operatorQueueDetail.getId() > 0) {
            operatorQueueDetails = (List<OperatorQueueDetail>) session.get("operatorQueueDetails");
            Collections.sort(operatorQueueDetails);
            int index = Collections.binarySearch(operatorQueueDetails, operatorQueueDetail);
            if (index >= 0) {
                operatorQueueDetails.remove(index);
                session.put("operatorQueueDetails", operatorQueueDetails);
            }

            log.debug("operatorQueueDetailId: " + operatorQueueDetailId);
        }

        return DETAIL_LIST;
    }

    public String deleteQueue() throws ScopixException {
        OperatorQueueManagement management = SpringSupport.getInstance().findBeanByClassName(OperatorQueueManagement.class);
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;

        if (operatorQueue != null && operatorQueue.getId() != null && operatorQueue.getId() >= 0) {
            operatorQueue.setModifiedDate(new Date());
            operatorQueue.setActivo(false);
            management.inactiveOperatorQueue(operatorQueue, sessionId);
        }

        this.list();

        return LIST;
    }

    public String showStoreAreaType() {
        return SHOW_STORE_AREA_TYPE;
    }

    public List<Store> getStoreGroupList() throws ScopixException {
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        List<Store> stores = manager.getStoreList(new Store(), sessionId);

        session.put("storesOperatorQueue", stores);

        return stores;
    }

//se agrega muevo metodo
    public List<SituationTemplatePojo> getSituationTemplatePojoList() throws ScopixException {

        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);

        List<SituationTemplate> situationTemplates = manager.getSituationTemplateList();
        List<SituationTemplatePojo> situationTemplatePojos = new ArrayList<SituationTemplatePojo>();
        for (SituationTemplate st : situationTemplates) {
            SituationTemplatePojo pojo = new SituationTemplatePojo();
            pojo.setId(st.getId());
            pojo.setDescription(st.getName() + " - " + st.getAreaType().getDescription());
            situationTemplatePojos.add(pojo);
        }
        session.put("situationTemplatePojosOperatorQueue", situationTemplates);

        return situationTemplatePojos;
    }

    //TO-DO se debe modificar para que la lista sea de Un POJO de Situation Template
    public List<AreaType> getAreaGroupList() throws ScopixException {
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);

        List<AreaType> areaTypes = manager.getAreaTypeList(new AreaType(), sessionId);

        session.put("areaTypesOperatorQueue", areaTypes);

        return areaTypes;
    }

    public String save() throws ScopixException {
        OperatorQueueManagement management = SpringSupport.getInstance().findBeanByClassName(OperatorQueueManagement.class);
        Long sessionId = (session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0);

        if (getOperatorQueue().getName() == null || getOperatorQueue().getName().length() == 0) {
            this.addActionError(this.getText("operatorQueueManagement.error.name.void"));
        } else {
            if (!isEditable() && management.validateQueueByName(getOperatorQueue().getName(), sessionId)) {
                this.addActionError(this.getText("operatorQueueManagement.error.queue.name"));
            }
            String queueNameOriginal = (String) session.get("queueNameOrigin");
            if (isEditable() && queueNameOriginal != null
                    && !getOperatorQueue().getName().equals(queueNameOriginal)
                    && management.validateQueueByName(getOperatorQueue().getName(), sessionId)) {
                this.addActionError(this.getText("operatorQueueManagement.error.queue.name"));
            }
        }
        if (getOperatorQueueDetails().size() == 0) {
            this.addActionError(this.getText("operatorQueueManagement.error.detail.list.empty"));
        }

        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return EDIT;
        }

        getOperatorQueue().setOperatorQueueDetailList(getOperatorQueueDetails());
        getOperatorQueue().setActivo(Boolean.TRUE);

        if (isEditable()) {
            getOperatorQueue().setModifiedDate(new Date());
            management.updateOperatorQueue(getOperatorQueue(), sessionId);
        } else {
            getOperatorQueue().setCreationDate(new Date());
            management.addOperatorQueue(getOperatorQueue(), sessionId);
        }

        session.put("queueManagementFilter", new OperatorQueue());

        this.loadFilters();
        return SUCCESS;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public OperatorQueue getOperatorQueue() {
        return operatorQueue;
    }

    public void setOperatorQueue(OperatorQueue operatorQueue) {
        this.operatorQueue = operatorQueue;
    }

    public List<OperatorQueue> getOperatorQueues() {
        if (operatorQueues == null) {
            operatorQueues = new ArrayList<OperatorQueue>();
        }
        return operatorQueues;
    }

    public void setOperatorQueues(List<OperatorQueue> operatorQueues) {
        this.operatorQueues = operatorQueues;
    }

    public OperatorQueueDetail getOperatorQueueDetail() {
        return operatorQueueDetail;
    }

    public void setOperatorQueueDetail(OperatorQueueDetail operatorQueueDetail) {
        this.operatorQueueDetail = operatorQueueDetail;
    }

    public List<OperatorQueueDetail> getOperatorQueueDetails() {
        operatorQueueDetails = (List<OperatorQueueDetail>) session.get("operatorQueueDetails");

        if (operatorQueueDetails == null) {
            operatorQueueDetails = new ArrayList<OperatorQueueDetail>();
        }
        return operatorQueueDetails;
    }

    public void setOperatorQueueDetails(List<OperatorQueueDetail> operatorQueueDetails) {
        this.operatorQueueDetails = operatorQueueDetails;
    }

    public Integer[] getStoreGroup() {
        return storeGroup;
    }

    public void setStoreGroup(Integer[] storeGroup) {
        this.storeGroup = storeGroup;
    }

//    public Integer[] getAreaGroup() {
//        return areaGroup;
//    }
//
//    public void setAreaGroup(Integer[] areaGroup) {
//        this.areaGroup = areaGroup;
//    }

    public Integer getOperatorQueueDetailId() {
        return operatorQueueDetailId;
    }

    public void setOperatorQueueDetailId(Integer operatorQueueDetailId) {
        this.operatorQueueDetailId = operatorQueueDetailId;
    }

    public Integer getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(Integer selectedOption) {
        this.selectedOption = selectedOption;
    }

    /**
     * @return the situationTemplateGroup
     */
    public Integer[] getSituationTemplateGroup() {
        return situationTemplateGroup;
    }

    /**
     * @param situationTemplateGroup the situationTemplateGroup to set
     */
    public void setSituationTemplateGroup(Integer[] situationTemplateGroup) {
        this.situationTemplateGroup = situationTemplateGroup;
    }

    public static class SituationTemplatePojo implements Comparable<SituationTemplatePojo> {

        private Integer id;
        private String description;

        public SituationTemplatePojo() {
        }

        /**
         * @return the id
         */
        public Integer getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description the description to set
         */
        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public int compareTo(SituationTemplatePojo o) {
            return this.getId() - o.getId();
        }
    }
}
