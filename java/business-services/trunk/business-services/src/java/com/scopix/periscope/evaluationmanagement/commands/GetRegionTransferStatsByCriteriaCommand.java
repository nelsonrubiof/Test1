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
import com.scopix.periscope.frontend.dto.EvidenceRegionTransferStatsDTO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * @author Sebastian
 *
 */
public class GetRegionTransferStatsByCriteriaCommand {

    private EvidenceRegionTransferDAO evidenceRegionTransferDAO;

    /**
     * Get Region Transfer DTOS by criteria
     *
     * @param storeId
     * @param situationTemplateId
     * @param startTime
     * @param endTimen
     * @return EvidenceRegionTransferStatsDTO
     */
    public EvidenceRegionTransferStatsDTO execute(Integer storeId, Integer situationTemplateId, Integer transmNotTransm, Date day, Date start, Date end) {
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
        List<Map<String, Object>> results = getEvidenceRegionTransferDAO().getRegionTransferStats(storeId, situationTemplateId, transmNotTransm, start, end);
        Integer total = 0;
        Integer tranm = 0;
        Integer notTranm = 0;

        for (Map<String, Object> result : results) {
            Boolean completed = (Boolean) result.get("completed");
            Integer count = ((BigInteger) result.get("count")).intValue();
            total = total + count;
            if (completed) {
                tranm = count;
            } else {
                notTranm = count;
            }
        }
        DecimalFormat df = new DecimalFormat("#.##");
        Double perctTranm = new Double(0);
        if (total > new Double(0)) {
            perctTranm = (new Double(tranm) * new Double(100) / new Double(total));
        }
        perctTranm = Double.valueOf(df.format(perctTranm));
        Double percNotTranm = new Double(100) - perctTranm;
        percNotTranm = Double.valueOf(df.format(percNotTranm));
        EvidenceRegionTransferStatsDTO ertsdto = new EvidenceRegionTransferStatsDTO();
        ertsdto.setTransferredPercentage(perctTranm);
        ertsdto.setNotTransferredPercentage(percNotTranm);
        ertsdto.setTotalEvidences(total);
        ertsdto.setTransferredEvidences(tranm);
        ertsdto.setNotTransferredEvidences(notTranm);
        return ertsdto;
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
