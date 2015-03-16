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
 *  EvidenceTransmitionStrategyDAO.java
 * 
 *  Created on Jul 23, 2014, 2:48:14 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.dao;

import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.EvidenceTransmitionStrategy;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public interface EvidenceTransmitionStrategyDAO {

    /**
     * Looks the desired evidenceTransmitionStrategy by store and situation
     * template
     *
     * @param store
     * @param situationTemplate
     * @return EvidenceTransmitionStrategy
     */
    EvidenceTransmitionStrategy getByStoreAndSituationTemplate(Store store, SituationTemplate situationTemplate);

    /**
     * get all by Store and or Situation template
     *
     * @param store
     * @param situationTemplate
     * @return List<EvidenceTransmitionStrategy>
     */
    List<EvidenceTransmitionStrategy> getAllByStoreAndOrSituationTemplate(Store store, SituationTemplate situationTemplate);

    /**
     * get all by Stores and or Situation templates
     *
     * @param storeIds
     * @param situationTemplateIds
     * @return List<EvidenceTransmitionStrategy>
     */
    List<EvidenceTransmitionStrategy> getByStoresAndSituationTemplates(List<Integer> storeIds, List<Integer> situationTemplateIds);

    /**
     * deletes all desired Evidence transmission strategies given a set of ids
     *
     * @param ids
     */
    void deleteMultiple(List<Integer> ids);
}
