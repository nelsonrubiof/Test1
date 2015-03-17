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

import com.scopix.periscope.evaluationmanagement.EvidenceRegionTransfer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;

import com.scopix.periscope.frontend.dto.EvidenceRegionTransferDTO;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

/**
 * @author Sebastian
 *
 */
@SpringBean(rootClass = EvidenceRegionTransferDAO.class)
public class EvidenceRegionTransferDAOImpl extends DAOHibernate<EvidenceRegionTransfer, Integer> implements EvidenceRegionTransferDAO {

    private Logger log = Logger.getLogger(EvidenceTransmitionStrategyDAOImpl.class);
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.scopix.periscope.evaluationmanagement.dao.EvidenceRegionTransferDAO
     * #getEvidenceRegionTransferByCriteria(java.lang.Integer,
     * java.lang.Integer, java.util.Date, java.util.Date)
     */

    @Override
    public List<EvidenceRegionTransferDTO> getEvidenceRegionTransferByCriteria(Integer storeId, Integer situationTemplateId, Integer transmNotTransm,
            Date startDate, Date endDate) {
        Session session = this.getSession();
        try {
            List<EvidenceRegionTransferDTO> list = new ArrayList<EvidenceRegionTransferDTO>();

            StringBuilder sql = new StringBuilder();
            sql.append("select distinct (e.id), e.evidence_path, e.evidence_date, ert.region_server_name, ert.completed, ert.transmisition_date, ert.error_message ");
            sql.append("from place store, situation_template st,situation s, metric m, evidence_request er, rel_evidence_request_evidence rel, evidence_region_transfer ert, evidence e ");
            sql.append("where rel.evidence_id = e.id ");
            sql.append("and er.id = rel.evidence_request_id ");
            sql.append("and m.id = er.metric_id ");
            sql.append("and s.id = m.situation_id ");
            sql.append("and st.id = s.situation_template_id ");
            sql.append("and store.id = m.store_id ");
            sql.append("and e.id = ert.evidence_id ");
            if (storeId != null) {
                sql.append("and store.id = ").append(storeId.toString()).append(" ");
            }
            if (situationTemplateId != null) {
                sql.append("and st.id = ").append(situationTemplateId.toString()).append(" ");
            }
            if (transmNotTransm.equals(1)) {
                sql.append("and ert.completed = true").append(" ");
            } else if (transmNotTransm.equals(2)) {
                sql.append("and ert.completed = false").append(" ");
            }
            List<Map<String, Object>> resultSql;
            if (startDate != null && endDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                sql.append("and  ert.transmisition_date >= '").append(sdf.format(startDate)).append("'");
                sql.append("and  ert.transmisition_date <= '").append(sdf.format(endDate)).append("'");
                sql.append("order by ert.transmisition_date desc limit 1000");
                Query query = session.createSQLQuery(sql.toString());
                query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
                resultSql = query.list();
            } else {
                sql.append("order by ert.transmisition_date desc limit 1000");
                Query query = session.createSQLQuery(sql.toString());
                query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
                resultSql = query.list();
            }

            for (Map<String, Object> row : resultSql) {
                Integer evidenceId = (Integer) row.get("id");
                String evidenceFileName = (String) row.get("evidence_path");
                Date evidenceDate = (Date) row.get("evidence_date");
                String evidenceDateString = DateFormatUtils.format(evidenceDate,
                        "dd-MM-yyyy HH:mm:ss");
                String regionServerName = (String) row.get("region_server_name");
                Boolean completed = (Boolean) row.get("completed");
                Date transmisitionDate = (Date) row.get("transmisition_date");
                String transmisitionDateString = DateFormatUtils.format(
                        transmisitionDate, "dd-MM-yyyy HH:mm:ss");
                String errorMessage = (String) row.get("error_message");
                if (errorMessage == null) {
                    errorMessage = "";
                }
                EvidenceRegionTransferDTO evidenceRegionTransferDTO = new EvidenceRegionTransferDTO();
                evidenceRegionTransferDTO.setEvidenceId(evidenceId);
                evidenceRegionTransferDTO.setEvidenceFileName(evidenceFileName);
                evidenceRegionTransferDTO.setEvidenceDate(evidenceDateString);
                evidenceRegionTransferDTO.setRegionServerName(regionServerName);
                evidenceRegionTransferDTO.setCompleted(completed);
                evidenceRegionTransferDTO.setTransmissionDate(transmisitionDateString);
                evidenceRegionTransferDTO.setErrorMessage(errorMessage);
                list.add(evidenceRegionTransferDTO);
            }
            return list;
        } finally {
            this.releaseSession(session);
        }
    }

    @Override
    public List<Map<String, Object>> getRegionTransferStats(Integer storeId, Integer situationTemplateId, Integer transmNotTransm,
            Date startDate, Date endDate) {
        Session session = this.getSession();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select  count (distinct e.id) , ert.completed ");
            sql.append("from place store, situation_template st,situation s, metric m, evidence_request er, rel_evidence_request_evidence rel, evidence_region_transfer ert, evidence e ");
            sql.append("where rel.evidence_id = e.id ");
            sql.append("and er.id = rel.evidence_request_id ");
            sql.append("and m.id = er.metric_id ");
            sql.append("and s.id = m.situation_id ");
            sql.append("and st.id = s.situation_template_id ");
            sql.append("and store.id = m.store_id ");
            sql.append("and e.id = ert.evidence_id ");
            if (storeId != null) {
                sql.append("and store.id = ").append(storeId.toString()).append(" ");
            }
            if (situationTemplateId != null) {
                sql.append("and st.id = ").append(situationTemplateId.toString()).append(" ");
            }
            List<Map<String, Object>> resultSql;
            if (startDate != null && endDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                sql.append("and  ert.transmisition_date >= '").append(sdf.format(startDate)).append("'");
                sql.append("and  ert.transmisition_date <= '").append(sdf.format(endDate)).append("'");

            }
            sql.append(" group by ert.completed ");
            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            resultSql = query.list();
            return resultSql;
        } finally {
            this.releaseSession(session);
        }
    }

    @Override
    public void updateById(Integer id, Boolean completed, String errorMessage) {
        Session session = this.getSession();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("update evidence_region_transfer set completed =").append(completed).append(", ");
            sql.append("error_message ='").append(errorMessage).append("' ");
            sql.append("where id =").append(id).append(" ");
            SQLQuery query = session.createSQLQuery(sql.toString());
            query.executeUpdate();
        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
        } finally {
            try {
                this.releaseSession(session);
            } catch (Exception e) {
                log.error("Error " + e.getMessage(), e);
            }
        }
        log.info("end");
    }

    @Override
    public List<Map<String, Object>> getFailedPendingEvidences(Date startDate, Date endDate) {
        Session session = this.getSession();
        try {
            StringBuilder sql = new StringBuilder();      
            sql.append("SELECT DISTINCT ON (ert.id) ert.id as evidence_region_transfer_id, ert.region_server_name as region_server_name, pe.evaluation_state as pending_evaluation_state ");
            sql.append("FROM ");
            sql.append("evidence ev join evidence_region_transfer ert on ev.id = ert.evidence_id, ");
            sql.append("observed_situation os left join pending_evaluation pe on  os.id = pe.observed_situation_id, ");
            sql.append("observed_metric om, ");
            sql.append("rel_observed_metric_evidence rel ");
            sql.append("WHERE rel.evidence_id = ev.id ");
            sql.append("AND rel.observed_metric_id = om.id ");
            sql.append("AND om.observed_situation_id = os.id ");
            sql.append("AND ert.completed = false ");
            List<Map<String, Object>> resultSql;
            if (startDate != null && endDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                sql.append("and  ert.transmisition_date >= '").append(sdf.format(startDate)).append("'");
                sql.append("and  ert.transmisition_date <= '").append(sdf.format(endDate)).append("'");
            }
            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            resultSql = query.list();
            return resultSql;
        } finally {
            this.releaseSession(session);
        }
    }
}
