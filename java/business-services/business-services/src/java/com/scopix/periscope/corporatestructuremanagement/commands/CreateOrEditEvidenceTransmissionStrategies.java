/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  CreateOrEditEvidenceTransmissionStrategies.java
 * 
 *  Created on Aug 25, 2014, 3:32:39 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.EvidenceTransmitionStrategy;
import com.scopix.periscope.evaluationmanagement.RegionServer;
import com.scopix.periscope.evaluationmanagement.dao.EvidenceTransmitionStrategyDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sebastian
 */
public class CreateOrEditEvidenceTransmissionStrategies {
    
    private GenericDAO genericDAO;
    private EvidenceTransmitionStrategyDAO evidenceTransmitionStrategyDAO;
    
    public void execute(List<Integer> storeIds, List<Integer> situationTemplateIds, List<Integer> regionServerIds) throws ScopixException {
        
        List<Store> stores = new ArrayList<Store>();
        List<SituationTemplate> situationTemplates = new ArrayList<SituationTemplate>();
        List<RegionServer> regionServers = new ArrayList<RegionServer>();
        
        if (storeIds == null || storeIds.isEmpty()) {
            throw new ScopixException("Stores can not be empty");
        }
        if (situationTemplateIds == null || situationTemplateIds.isEmpty()) {
            throw new ScopixException("Situation Templates can not be empty");
        }
        if (regionServerIds == null) {
            regionServerIds = new ArrayList<Integer>();
        }
        
        for (Integer storeId : storeIds) {
            Store store = getGenericDAO().get(storeId, Store.class);
            if (store == null) {
                throw new ScopixException("Store by Id: " + storeId + " not found");
            }
            stores.add(store);
        }
        for (Integer situationTemplateId : situationTemplateIds) {
            SituationTemplate situationTemplate = getGenericDAO().get(situationTemplateId, SituationTemplate.class);
            if (situationTemplate == null) {
                throw new ScopixException("Situation Template by Id: " + situationTemplateId + " not found");
            }
            situationTemplates.add(situationTemplate);
            
        }
        for (Integer regionServerId : regionServerIds) {
            RegionServer regionServer = getGenericDAO().get(regionServerId, RegionServer.class);
            if (regionServer == null) {
                throw new ScopixException(" Region Server by Id: " + regionServerId + " not found");
            }
            regionServers.add(regionServer);
        }
        
        List<EvidenceTransmitionStrategy> existingStrategies = getEvidenceTransmitionStrategyDAO().getByStoresAndSituationTemplates(storeIds, situationTemplateIds);
        Map<String, EvidenceTransmitionStrategy> existingMap = new HashMap<String, EvidenceTransmitionStrategy>();
        
        for (EvidenceTransmitionStrategy strategy : existingStrategies) {
            strategy.setRegionServers(regionServers);
            existingMap.put(strategy.getStore().getId() + "-" + strategy.getSituationTemplate().getId(), strategy);
        }
        
        for (Store store : stores) {
            for (SituationTemplate situationTemplate : situationTemplates) {
                if (!existingMap.containsKey(store.getId() + "-" + situationTemplate.getId())) {
                    EvidenceTransmitionStrategy ets = new EvidenceTransmitionStrategy();
                    ets.setStore(store);
                    ets.setSituationTemplate(situationTemplate);
                    ets.setRegionServers(regionServers);
                    existingMap.put(store.getId() + "-" + situationTemplate.getId(), ets);
                }
            }
        }
        getGenericDAO().save(new ArrayList(existingMap.values()));      
    }

    /**
     * @return the genericDAO
     */
    public GenericDAO getGenericDAO() {
        if (genericDAO == null) {
            genericDAO = HibernateSupport.getInstance().findGenericDAO();
        }
        return genericDAO;
    }

    /**
     * @param genericDAO the genericDAO to set
     */
    public void setGenericDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    /**
     * @return the evidenceTransmitionStrategyDAO
     */
    public EvidenceTransmitionStrategyDAO getEvidenceTransmitionStrategyDAO() {
        if (evidenceTransmitionStrategyDAO == null) {
            evidenceTransmitionStrategyDAO = SpringSupport.getInstance().findBeanByClassName(EvidenceTransmitionStrategyDAO.class);
        }
        return evidenceTransmitionStrategyDAO;
    }

    /**
     * @param evidenceTransmitionStrategyDAO the evidenceTransmitionStrategyDAO
     * to set
     */
    public void setEvidenceTransmitionStrategyDAO(EvidenceTransmitionStrategyDAO evidenceTransmitionStrategyDAO) {
        this.evidenceTransmitionStrategyDAO = evidenceTransmitionStrategyDAO;
    }
}
