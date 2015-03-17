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
package com.scopix.periscope.evaluationmanagement.commands;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.scopix.periscope.evaluationmanagement.dao.EvidenceRegionTransferDAO;
import com.scopix.periscope.frontend.dto.EvidenceRegionTransferDTO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 * @author Sebastian
 *
 */
public class GetRegionTransferByCriteriaCommand {

    private EvidenceRegionTransferDAO evidenceRegionTransferDAO;

    /**
     * Get Region Transfer DTOS by criteria
     *
     * @param storeId
     * @param situationTemplateId
     * @param startTime
     * @param endTimen
     * @return List<EvidenceRegionTransferDTO>
     */
    public List<EvidenceRegionTransferDTO> execute(Integer storeId, Integer situationTemplateId, Integer transmNotTransm, Date day, Date start, Date end) {
        if (day != null && (start == null || end == null)) {
            Calendar c = Calendar.getInstance();
            c.setTime(day);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            start = new Date(c.getTimeInMillis());
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 999);
            end = new Date(c.getTimeInMillis());
        }
        return getEvidenceRegionTransferDAO().getEvidenceRegionTransferByCriteria(storeId, situationTemplateId, transmNotTransm, start, end);
    }

    /**
     * @return the evidenceRegionTransferDAO
     */
    public EvidenceRegionTransferDAO getEvidenceRegionTransferDAO() {
        if (evidenceRegionTransferDAO == null) {
            evidenceRegionTransferDAO = SpringSupport.getInstance().findBeanByClassName(EvidenceRegionTransferDAO.class);

        }
        return evidenceRegionTransferDAO;
    }

    /**
     * @param evidenceRegionTransferDAO the evidenceRegionTransferDAO to set
     */
    public void setEvidenceRegionTransferDAO(EvidenceRegionTransferDAO evidenceRegionTransferDAO) {
        this.evidenceRegionTransferDAO = evidenceRegionTransferDAO;
    }
}
