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
 * UserLoginHibernateDAO.java
 *
 * Created on 12-05-2008, 06:14:01 PM
 *
 */
package com.scopix.periscope.queuemanagement.dao;

import com.scopix.periscope.evaluationmanagement.EvaluationQueue;
import com.scopix.periscope.evaluationmanagement.EvaluationState;
import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.queuemanagement.OperatorQueue;
import com.scopix.periscope.queuemanagement.dto.FilteringData;
import com.scopix.periscope.queuemanagement.dto.OperatorQueueDTO;
import com.scopix.periscope.queuemanagement.dto.PendingEvaluationDTO;
import com.scopix.periscope.queuemanagement.dto.SummaryDTO;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.dao.DataAccessResourceFailureException;

/**
 *
 * @author Gustavo Alvarez
 * @version 2.0.0
 */
@SpringBean(rootClass = QueueManagementHibernateDAO.class)
public class QueueManagementHibernateDAO extends DAOHibernate<PendingEvaluation, Long> {

    private Logger log = Logger.getLogger(QueueManagementHibernateDAO.class);

    /**
     *
     * @param filter Filtros para retornar lista
     * @return List<PendingEvaluationDTO> generada para filtros
     * @throws ScopixException Excepcion en caso de error
     */
    public List<PendingEvaluationDTO> getPendingEvaluationList(FilteringData filter) throws ScopixException {
        log.info("start");
        List<PendingEvaluationDTO> pendingEvaluationDTOs = new ArrayList<PendingEvaluationDTO>();
        Session session = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT DISTINCT   ");
            sql.append(" pe.id AS pendingEvaluationId,    ");
            sql.append(" pe.user_name AS userName,    ");
            sql.append(" st.name AS situationTemplate,    ");
            sql.append(" st.id AS situationTemplateId,    ");
            sql.append(" a.description AS area,    ");
            sql.append(" a.id AS areaId,    ");
            sql.append(" mt.description AS description,    ");
            sql.append(" mt.metric_type_element AS type,    ");
            sql.append(" e.evidence_date AS date,    ");
            sql.append(" os.id AS observedSituationId,    ");
            sql.append(" pe.evaluation_state AS state,    ");
            sql.append(" pe.evaluation_queue AS queue,    ");
            sql.append(" pe.priority AS priority,    ");
            sql.append(" store.description AS store_description,     ");
            //sql.append(" e.id AS evidenceId,    ");
            sql.append(" e.evidence_date AS evidenceDate,    ");
            sql.append(" p.description  AS product,  ");
            sql.append(" m.metric_order   ");
            sql.append("FROM  ");
            sql.append("pending_evaluation pe, ");
            sql.append("observed_situation os,  ");
            sql.append("situation s, ");
            sql.append("situation_template st, ");
            sql.append("observed_metric om, ");
            sql.append("metric m, ");
            sql.append("metric_template mt, ");
            sql.append("product p, ");
            sql.append("place a, ");
            sql.append("place store, ");
            sql.append("evidence e, ");
            sql.append("rel_observed_metric_evidence rel ");
            sql.append("where  ");
            sql.append("os.id = pe.observed_situation_id ");
            sql.append("and s.id = os.situation_id ");
            sql.append("and st.id = s.situation_template_id ");
            sql.append("and om.observed_situation_id = os.id ");
            sql.append("and m.id = om.metric_id ");
            sql.append("and mt.id = m.metric_template_id ");
            sql.append("and a.id = m.area_id ");
            sql.append("and store.id = m.store_id ");
            sql.append("and p.id = st.product_id ");
            sql.append("and rel.observed_metric_id = om.id ");
            sql.append("and e.id = rel.evidence_id ");
            if (filter != null) {
                if (filter.getDate() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sql.append(" AND os.observed_situation_date = '").append(sdf.format(filter.getDate())).append("'");
                }
                if (filter.getArea() != null && filter.getArea() > 0) {
                    sql.append(" AND a.id = ").append(filter.getArea());
                }
                if (filter.getQueue() != null && filter.getQueue().length() > 0) {
                    sql.append(" AND pe.evaluation_queue = '").append(EvaluationQueue.valueOf(filter.getQueue())).append("'");
                }
                if (filter.getStore() != null && filter.getStore() > 0) {
                    sql.append(" AND a.store_id = ").append(filter.getStore());
                }
                if (filter.getStatus() != null && filter.getStatus().length() > 0) {
                    sql.append(" AND pe.evaluation_state = '").append(EvaluationState.valueOf(filter.getStatus())).append("'");
                }
                if (filter.getQueueNameId() != null && (filter.getQueueNameId() > 0 || filter.getQueueNameId() == -1)) {
                    sql.append(" AND pe.operator_queue_id = ").append(filter.getQueueNameId());
                } else {
                    sql.append(" AND pe.operator_queue_id is null");
                }
            }
            sql.append(" ORDER BY pe.priority, pe.id, m.metric_order ASC");
            session = this.getSession();

            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();

            int id = 0;
            PendingEvaluationDTO dto = null;
            for (Map m : list) {
                if (id != (Integer) m.get("pendingevaluationid")) {
                    id = (Integer) m.get("pendingevaluationid");
                    dto = new PendingEvaluationDTO();
                    dto.setPendingEvaluationId(id);
                    dto.setSituationTemplate((String) m.get("situationtemplate"));
                    dto.setUserName((String) m.get("username"));
                    dto.setSituationTemplateId((Integer) m.get("situationtemplateid"));
                    dto.setArea((String) m.get("area"));
                    dto.setAreaId((Integer) m.get("areaid"));
                    dto.setDescription((String) m.get("description"));
                    dto.setType((String) m.get("type"));
                    dto.setDate((Date) m.get("date"));
                    dto.setObservedSituationId((Integer) m.get("observedsituationid"));
                    dto.setState(EvaluationState.valueOf((String) m.get("state")));
                    dto.setQueue(EvaluationQueue.valueOf((String) m.get("queue")));
                    dto.setPriority((Integer) m.get("priority"));
                    //dto.setEvidenceId(rs.getInt("evidenceId"));
                    dto.setEvidenceDate((Date) m.get("evidencedate"));
                    dto.setProduct((String) m.get("product"));
                    dto.setStore((String) m.get("store_description"));
                    pendingEvaluationDTOs.add(dto);
                } else {
                    dto.setDescription(dto.getDescription() + "<br>" + (String) m.get("description"));
                    dto.setType(dto.getType() + "<br>" + (String) m.get("type"));
                }
            }

        } catch (Exception e) {
            log.error("Error " + e, e);
            //"periscopeexception.list.error", new String[]{
            throw new ScopixException("tab.queueManagement.pending");
        } finally {
            try {
                this.releaseSession(session);
            } catch (Exception e) {
                log.error("Error " + e.getMessage(), e);
            }
        }
        log.info("end, result = " + pendingEvaluationDTOs);
        return pendingEvaluationDTOs;
    }

    /**
     *
     * @param filter Filtros de Cola y estado de Evaluacion
     * @return List<PendingEvaluation> lista con resultados para dicha lista
     * @throws PeriscopeException Excepcion en cado de Error
     */
    public List<PendingEvaluation> getPendingEvaluationListBasic(FilteringData filter) throws ScopixException {
        log.info("start");
        List<PendingEvaluation> pendingEvaluations = null;
        try {
            Criteria criteria = this.getSession().createCriteria(PendingEvaluation.class);
            criteria.addOrder(Order.asc("priority"));
            if (filter != null) {
                if (filter.getQueue() != null && filter.getQueue().length() > 0) {
                    criteria.add(Restrictions.eq("evaluationQueue", EvaluationQueue.valueOf(filter.getQueue())));
                }
                if (filter.getStatus() != null && filter.getStatus().length() > 0) {
                    criteria.add(Restrictions.eq("evaluationState", EvaluationState.valueOf(filter.getStatus())));
                }
            }
            pendingEvaluations = criteria.list();
        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
            //"periscopeexception.list.error", new String[]{
            throw new ScopixException("tab.queueManagement.pending");
        }
        log.info("end, result = " + pendingEvaluations);
        return pendingEvaluations;
    }

    /**
     *
     * @param pendingEvaluationId id de PendingEvaluation para filtro
     * @return List<MetricTemplate> lista de Metric Template asociados a un pendingEvaluation
     * @throws PeriscopeException Excepcion en caso de Error
     */
    public List<MetricTemplate> getMetricTemplateForAPendingEvaluation(Integer pendingEvaluationId) throws ScopixException {
        log.info("start");
        List<MetricTemplate> metricTemplates = null;
        try {
            Criteria criteria = this.getSession().createCriteria(MetricTemplate.class).createCriteria("metrics").createCriteria(
                    "situation").createCriteria("observedSituations");
            criteria.createCriteria("pendingEvaluation").add(Restrictions.eq("id", pendingEvaluationId));
            metricTemplates = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        } catch (Exception e) {
            log.error("Error " + e, e);
            //"periscopeexception.list.error", new String[]{
            throw new ScopixException("label.metricTemplateForAPendingEvaluation");
        }
        log.info("end, result = " + metricTemplates);
        return metricTemplates;
    }

    /**
     * Cambia la prioridad de un pending evaluation en una cola determinada
     *
     * @param startPosition inicio de posicion
     * @param queueNameId id de cola asociada
     */
    public void changePriorityToPendingEvaluation(int startPosition, Integer queueNameId) {
        String hql = "SELECT 1 FROM changePriorityToPendingEvaluation(" + startPosition + ", " + queueNameId + ")";
        SQLQuery query = this.getSession().createSQLQuery(hql);
        query.uniqueResult();
    }

    /**
     *
     * @return
     */
    public List<SummaryDTO> getSummaryList() {
        log.info("start");
        List<SummaryDTO> summaryDTOs = new ArrayList<SummaryDTO>();
        log.info("end");
        return summaryDTOs;
    }

    /**
     *
     * @param quantity
     * @param queueNameId
     * @return
     * @throws PeriscopeException
     */
    public List<PendingEvaluationDTO> getNFirstElementOfQueue(int quantity, Integer queueNameId) throws ScopixException {
        log.info("start");
        List<PendingEvaluationDTO> pendingEvaluationDTOs = new ArrayList<PendingEvaluationDTO>();
        Session session = null;
        try {
            StringBuilder sql = new StringBuilder();

            sql.append("SELECT DISTINCT ");
            sql.append("st.name AS situationTemplate, ");
            sql.append("a.description AS area, ");
            sql.append("prod.description AS product, ");
            sql.append("pe.priority AS priority, ");
            sql.append("min(e.evidence_date) AS evidenceDate, ");
            sql.append("store.description AS store ");
            sql.append("FROM ");
            sql.append("pending_evaluation pe, ");
            sql.append("evidence e, ");
            sql.append("observed_situation os, ");
            sql.append("situation s, ");
            sql.append("metric m, ");
            sql.append("observed_metric om, ");
            sql.append("place a, ");
            sql.append("place store, ");
            sql.append("situation_template st, ");
            sql.append("product prod, ");
            sql.append("rel_observed_metric_evidence rel ");
            sql.append("WHERE ");
            sql.append("pe.evaluation_queue = 'OPERATOR' ");
            sql.append("AND pe.evaluation_state = 'ENQUEUED' ");
            sql.append("AND os.id = pe.observed_situation_id ");
            sql.append("and os.id = om.observed_situation_id ");
            sql.append("AND s.id = os.situation_id ");
            sql.append("AND s.id = m.situation_id ");
            sql.append("AND a.id = m.area_id ");
            sql.append("AND st.id = s.situation_template_id ");
            sql.append("AND store.id = m.store_id ");
            sql.append("AND m.id = om.metric_id ");
            sql.append("AND om.id = rel.observed_metric_id ");
            sql.append("AND e.id = rel.evidence_id ");
            sql.append("AND prod.id = st.product_id ");
            if (queueNameId != null && (queueNameId > 0 || queueNameId == -1)) {
                sql.append("AND pe.operator_queue_id = ").append(queueNameId).append(" ");
            } else {
                sql.append("AND pe.operator_queue_id is null ");
            }
            sql.append("group by pe.id, st.name, st.id, a.description, a.id, prod.description, os.id, pe.evaluation_state, ");
            sql.append("pe.evaluation_queue, pe.priority, store.description, store.id ");
            sql.append("ORDER BY pe.priority ASC LIMIT ").append(quantity);

            session = this.getSession();

            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();

            int id = 0;
            PendingEvaluationDTO dto = null;
            for (Map m : list) {
                dto = new PendingEvaluationDTO();
                dto.setPendingEvaluationId(id);
                dto.setSituationTemplate((String) m.get("situationtemplate"));
                dto.setProduct((String) m.get("product"));
                dto.setArea((String) m.get("area"));
                dto.setPriority((Integer) m.get("priority"));
                dto.setEvidenceDate((Date) m.get("evidencedate"));
                dto.setDate(dto.getEvidenceDate());
                dto.setStore((String) m.get("store"));

                pendingEvaluationDTOs.add(dto);
            }

        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
            //"periscopeexception.list.error", new String[]{
            throw new ScopixException("tab.queueManagement.pending");
        } finally {
            try {
                this.releaseSession(session);
            } catch (Exception e) {
                log.error("Error " + e.getMessage(), e);
            }
        }
        log.info("end, result = " + pendingEvaluationDTOs);
        return pendingEvaluationDTOs;
    }

    /**
     *
     * @param ids
     */
    public void updatePendingEvaluationForChangePriority(List<Integer> ids) {
        log.info("start");
        Session session = null;

        try {
            StringBuilder sql = new StringBuilder();

            sql.append("update pending_evaluation set change_priority = TRUE ");
            sql.append("WHERE id in ( ");
            sql.append(ids.toString().replaceAll("(\\[)|(\\])", ""));
            sql.append(")");
            this.getSession().createSQLQuery(sql.toString()).executeUpdate();
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

    /**
     *
     * @param os
     * @return
     */
    public OperatorQueue getOperatorQueueForAObservedSituation(ObservedSituation os) {
        log.info("start");
        OperatorQueue operatorQueue = null;
        Session session = this.getSession();
        try {
            if (os != null) {
                Criteria criteria = session.createCriteria(OperatorQueue.class);
                criteria.add(Restrictions.eq("activo", Boolean.TRUE));
                Criteria critDetail = criteria.createCriteria("operatorQueueDetailList");
                //critDetail.add(Restrictions.eq("areaType", os.getObservedMetrics().get(0).getMetric().getArea().getAreaType()));
                critDetail.add(Restrictions.eq("situationTemplate", os.getSituation().getSituationTemplate()));
                critDetail.add(Restrictions.eq("store", os.getObservedMetrics().get(0).getMetric().getArea().getStore()));
                List<OperatorQueue> oqs = criteria.list();
                if (oqs != null && !oqs.isEmpty()) {
                    operatorQueue = oqs.get(0);
                }
            }
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        log.info("end, result = " + operatorQueue);
        return operatorQueue;
    }

    // obtiene el primer PendingEvaluation para una cola de operadores
    /**
     *
     * @param queueName
     * @return
     */
    public PendingEvaluation getFirstElementOfQueue(String queueName) {
        log.info("start [queueName:" + queueName + "]");

        // obtener ID de Pending Evaluation
        int peId = getFirstElementOfQueueId(queueName);
        log.debug("peId: " + peId);
        Session session = this.getSession();
        PendingEvaluation pe = null;
        try {
            Criteria criteria = session.createCriteria(PendingEvaluation.class);
            //criteria.addOrder(Order.asc("priority"));
            criteria.add(Restrictions.eq("id", peId));

//        List<PendingEvaluation> pes = criteria.list();
//        if (pes != null && !pes.isEmpty()) {
//            pe = pes.get(0);
//            pe.getEvidenceEvaluations().isEmpty();
//            pe.getObservedSituation().getObservedMetrics().isEmpty();
//            pe.getRejectedHistorys().isEmpty();
//        }
            pe = (PendingEvaluation) criteria.uniqueResult();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        log.info("end [result:" + pe + "]");
        return pe;
    }

    // obtiene el ID del primer pending evaluation para una cola de operadores
    /**
     *
     * @param queueName
     * @return
     */
    public int getFirstElementOfQueueId(String queueName) {
        log.info("start [queueName:" + queueName + "]");

        Integer peId = null;
        Session session = null;

        try {
            StringBuilder sql = new StringBuilder();
//            sql.append("select pe.id, pe.priority ");
//            sql.append("from pending_evaluation pe ");
//            sql.append("where ");
//            sql.append("pe.evaluation_queue = 'OPERATOR' ");
//            sql.append("and pe.evaluation_state = 'ENQUEUED' ");
//            if (queueName != null && queueName.length() > 0) {
//                sql.append("and pe.operator_queue_id = (select id from operator_queue op where op.activo = true ");
//                sql.append("and op.name = '").append(queueName).append("') ");
//            } else {
//                sql.append("and pe.operator_queue_id is null ");
//            }
//            sql.append("order by pe.priority ");
//            sql.append("limit 1 ");
            boolean forQueue = queueName != null && queueName.length() > 0;
            sql.append("select pe.id, pe.priority, os.evidence_date ");
            sql.append("from pending_evaluation pe, observed_situation os, situation s, ");
            sql.append("situation_template st, observed_metric om, metric m, place store ");
            if (forQueue) {
                //add to table
                sql.append(", operator_queue oq ");
            }
            sql.append("where ");
            sql.append("pe.evaluation_queue = 'OPERATOR' ");
            sql.append("and pe.evaluation_state = 'ENQUEUED' ");
            if (forQueue) {
//                sql.append("and pe.operator_queue_id = (select id from operator_queue op where op.activo = true ");
//                sql.append("and op.name = '").append(queueName).append("') ");
                sql.append("and pe.operator_queue_id = oq.id ");
                sql.append("and oq.activo = true ");
                sql.append("and oq.name = '").append(queueName).append("' ");
            } else {
                sql.append("and pe.operator_queue_id is null ");
            }
            sql.append("and os.id = pe.observed_situation_id ");
            /**
             * add where for return pending evaluation not expired in situation live
             */
            sql.append("and s.id = os.situation_id ");
            sql.append("and st.id = s.situation_template_id ");
            sql.append("and om.observed_situation_id = os.id ");
            sql.append("and m.id = om.metric_id ");
            sql.append("and store.id = m.store_id ");
            sql.append("and  ");
            sql.append("	case  ");
            sql.append("		when (st.live = true) then ");
            sql.append("		os.evidence_date >= ( cast((now() AT TIME ZONE store.time_zone_id) as timestamp without time zone) - interval '1 minute' * st.delay_in_minutes) ");
            sql.append("		else true ");
            sql.append("		end ");
            sql.append("order by pe.priority , os.evidence_date desc ");
            sql.append("limit 1");

            session = this.getSession();

            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();

            if (list == null || list.isEmpty()) {
                peId = 0;
            } else {
                peId = ((Integer) ((Map) list.get(0)).get("id"));
            }
            log.debug("peId: " + peId);
        } catch (DataAccessResourceFailureException e) {
            log.error("Error " + e.getMessage(), e);
        } catch (IllegalStateException e) {
            log.error("Error " + e.getMessage(), e);
        } catch (HibernateException e) {
            log.error("Error " + e.getMessage(), e);
        } finally {
            try {
                this.releaseSession(session);
            } catch (Exception e) {
                log.error("Error " + e.getMessage(), e);
            }
        }
        log.info("end [result:" + peId + "]");
        return peId;
    }

    /**
     *
     * @return @throws PeriscopeException
     */
    public List<OperatorQueueDTO> getOperatorQueues() throws ScopixException {
        log.info("start");
        List<OperatorQueueDTO> operatorQueueDTOs = new ArrayList<OperatorQueueDTO>();
        Session session = null;

        try {
            String sql = "SELECT DISTINCT name, id FROM operator_queue where activo = true ";

            session = this.getSession();

            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();

            OperatorQueueDTO dto = null;
            for (Map m : list) {
                dto = new OperatorQueueDTO();
                dto.setId((Integer) m.get("id"));
                dto.setName((String) m.get("name"));

                operatorQueueDTOs.add(dto);
            }
        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
        } finally {
            try {
                this.releaseSession(session);
            } catch (Exception e) {
                log.error("Error " + e.getMessage(), e);
            }
        }
        log.info("end, result = " + operatorQueueDTOs);
        return operatorQueueDTOs;
    }    // obtiene el primer PendingEvaluation para una cola de operadores

    /**
     * Looks on the database for available evidences to be processed the result is mapped on map and each key will be equivalent
     * to the queue name and the value is a Integer representing the total of evidences available to be processed for that
     * specific queue
     *
     * @return Map<String, Integer> ex DSO,9
     * @throws PeriscopeException
     */
    public Map<String, Integer> countAvailableEvidencesByQueue() throws ScopixException {
        log.info("start countAvailableEvidencesByQueue()");

        Session session = null;
        Map<String, Integer> resultMap = new HashMap<String, Integer>();

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select count(1) as total, data.name as queuename ");
            sql.append(" from ");
            sql.append("(");
            sql.append("  select distinct pe.id, oq.name ");
            sql.append("   from operator_queue oq, pending_evaluation pe, observed_situation os, ");
            sql.append("   situation s, situation_template st, observed_metric om, metric m, place store ");
            sql.append("   where ");
            sql.append("   oq.activo = true ");
            sql.append("   and pe.operator_queue_id = oq.id ");
            sql.append("   and pe.evaluation_state ='ENQUEUED' ");
            sql.append("   and pe.evaluation_queue = 'OPERATOR' ");
            sql.append("   and os.id = pe.observed_situation_id ");
            sql.append("   and s.id = os.situation_id ");
            sql.append("   and st.id = s.situation_template_id ");
            sql.append("   and st.active = true   ");
            sql.append("   and om.observed_situation_id = os.id ");
            sql.append("   and m.id = om.metric_id ");
            sql.append("   and store.id = m.store_id ");
            sql.append("   and st.live = true ");
            sql.append("   and os.evidence_date >= (cast((now() AT TIME ZONE store.time_zone_id) as timestamp without time zone) - interval '1 minute' * st.delay_in_minutes) ");
            sql.append(") as data ");
            sql.append(" group by data.name ");
            sql.append(" union all");
            sql.append("  select count(1) as total, oq.name as queuename ");
            sql.append("   from operator_queue oq, pending_evaluation pe, observed_situation os, ");
            sql.append("   situation s, situation_template st");
            sql.append("   where ");
            sql.append("   oq.activo = true ");
            sql.append("   and pe.operator_queue_id = oq.id ");
            sql.append("   and pe.evaluation_state ='ENQUEUED' ");
            sql.append("   and pe.evaluation_queue = 'OPERATOR' ");
            sql.append("   and os.id = pe.observed_situation_id ");
            sql.append("   and s.id = os.situation_id ");
            sql.append("   and st.id = s.situation_template_id ");
            sql.append("   and st.active = true ");
            sql.append("   and st.live = false");
            sql.append(" group by oq.name");

//            sql.append("select count(1) as total, data.name as queuename ");
//            sql.append(" from ");
//            sql.append("(");
//            sql.append("  select distinct pe.id, oq.name ");
//            sql.append("   from operator_queue oq, pending_evaluation pe, observed_situation os, ");
//            sql.append("   situation s, situation_template st, observed_metric om, metric m, place store ");
//            sql.append("   where ");
//            sql.append("   oq.activo = true ");
//            sql.append("   and pe.operator_queue_id = oq.id ");
//            sql.append("   and pe.evaluation_state ='ENQUEUED' ");
//            sql.append("   and pe.evaluation_queue = 'OPERATOR' ");
//            sql.append("   and os.id = pe.observed_situation_id ");
//            sql.append("   and s.id = os.situation_id ");
//            sql.append("   and st.id = s.situation_template_id ");
//            sql.append("   and om.observed_situation_id = os.id ");
//            sql.append("   and m.id = om.metric_id ");
//            sql.append("   and store.id = m.store_id ");
//            sql.append("  and ");
//            sql.append("    case ");
//            sql.append("      when (st.live = true) then ");
//            sql.append("	   os.evidence_date >= (cast((now() AT TIME ZONE store.time_zone_id) as timestamp without time zone) - interval '1 minute' * st.delay_in_minutes) ");
//            sql.append("      else true ");
//            sql.append("     end ");
//            sql.append(") as data ");
//            sql.append(" group by data.name");
//            sql.append("select count(1) as total, oq.name as queuename ");
//            sql.append("from operator_queue oq, pending_evaluation pe ");
//            sql.append("where ");
//            sql.append("oq.activo = true ");
//            sql.append("and pe.operator_queue_id = oq.id ");
//            sql.append("and pe.evaluation_state ='ENQUEUED' ");
//            sql.append("and pe.evaluation_queue = 'OPERATOR' ");
//            sql.append("group by oq.name ");
            session = this.getSession();

            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> queueCountlist = query.list();

            if (queueCountlist != null && !queueCountlist.isEmpty()) {
                for (Map<String, Object> queueCount : queueCountlist) {
                    resultMap.put((String) queueCount.get("queuename"), ((BigInteger) queueCount.get("total")).intValue());
                }
            }
            log.debug("results processed");
        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
            throw new ScopixException(e);
        } finally {
            try {
                this.releaseSession(session);
            } catch (Exception e) {
                log.error("Error " + e.getMessage(), e);
            }
        }
        log.info("end [results retrieved from database:" + resultMap.size() + "]");
        return resultMap;
    }
}
