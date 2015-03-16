/**
 *
 * Copyright © 2014, SCOPIX. All rights reserved.
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
 */
package com.scopix.periscope.evaluationmanagement.dao;

import java.util.Date;
import java.util.List;

import com.scopix.periscope.frontend.dto.EvidenceRegionTransferDTO;
import java.util.Map;

/**
 * @author Sebastian
 *
 */
public interface EvidenceRegionTransferDAO {

    /**
     * gets the desired evidence region transfers based on a given criteria
     *
     * @param storeId
     * @param situationTemplateId
     * @param transmNotTransm
     * @param startDate
     * @param endDate
     * @return List<EvidenceRegionTransferDTO>
     */
    List<EvidenceRegionTransferDTO> getEvidenceRegionTransferByCriteria(
            Integer storeId, Integer situationTemplateId, Integer transmNotTransm, Date startDate,
            Date endDate);

    /**
     * Updates Evidence Region Transfer Record
     *
     * @param id
     * @param completed
     * @param errorMessage
     */
    void updateById(Integer id, Boolean completed, String errorMessage);
    
    /**
     * gets the desired evidence region transfers stats based on a given criteria
     * @param storeId
     * @param situationTemplateId
     * @param transmNotTransm
     * @param startDate
     * @param endDate
     * @return List map with stats
     */
    List<Map<String, Object>> getRegionTransferStats(Integer storeId, Integer situationTemplateId, Integer transmNotTransm,
            Date startDate, Date endDate);
    
    
    /**
     * gets the desired evidence region transfers that failed or are pending
     * @param startDate
     * @param endDate
     * @return List<Map<String, Object>> evidence_id / evidence _regionTransfer_id / as region_server_name
     */
    List<Map<String, Object>> getFailedPendingEvidences(Date startDate, Date endDate);
}
