/*
 * 
 * Copyright ï¿½ 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * ObservedMetricHibernateDAO.java
 * 
 * Created on 09-05-2008, 04:44:21 PM
 */
package com.scopix.periscope.evaluationmanagement.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.StorePlan;
import com.scopix.periscope.evaluationmanagement.AutomaticEvaluationResult;
import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.evaluationmanagement.Process;
import com.scopix.periscope.evaluationmanagement.dto.AutomaticEvidenceAvailableDTO;
import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.extractionplanmanagement.RequestedImage;
import com.scopix.periscope.extractionplanmanagement.RequestedVideo;
import com.scopix.periscope.extractionplanmanagement.RequestedXml;
import com.scopix.periscope.extractionplanmanagement.Situation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Session;

/**
 *
 * @author Cesar Abarza Suazo.
 */
@SpringBean(rootClass = EvaluationHibernateDAOImpl.class)
public class EvaluationHibernateDAOImpl extends DAOHibernate<Situation, Integer> implements EvaluationHibernateDAO {

    private static final Logger log = Logger.getLogger(EvaluationHibernateDAOImpl.class);
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public Situation getSituationForAProcessId(Integer processId, Integer situationTemplateId) {
        log.debug("start");
        Situation situation = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(Situation.class);
            criteria.add(Restrictions.eq("processId", processId));
            criteria.createCriteria("situationTemplate").add(Restrictions.eq("id", situationTemplateId));
            List<Situation> situations = criteria.list();
            if (situations != null && !situations.isEmpty()) {
                situation = situations.get(0);
            }
        } catch (HibernateException e) {
        } finally {
            this.releaseSession(session);
        }
        log.debug("end");

        return situation;
    }

    @Override
    public Metric getMetricForAProcessId(Integer processId, Integer situationTemplateId, Integer metricTemplateId,
            Integer evidenceProviderId) {
        log.debug("start");
        Metric metric = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(Metric.class);
            criteria.createCriteria("metricTemplate").add(Restrictions.eq("id", metricTemplateId));

            Criteria situationCrit = criteria.createCriteria("situation");
            situationCrit.add(Restrictions.eq("processId", processId));
            situationCrit.createCriteria("situationTemplate").add(Restrictions.eq("id", situationTemplateId));

            // createCriteria("extractionPlanCustomizing")
            Criteria epmCriteria = criteria.createCriteria("extractionPlanMetric");
            epmCriteria.createCriteria("evidenceProviders").add(Restrictions.eq("id", evidenceProviderId));

            List<Metric> metrics = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            if (metrics != null && !metrics.isEmpty()) {
                metric = metrics.get(0);
            }
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        log.debug("end");
        return metric;
    }

    @Override
    public Integer getMetricIdForAProcessId(Integer processId, Integer situationTemplateId, Integer metricTemplateId,
            Integer evidenceProviderId) {
        log.info("start");
        StringBuilder sql = new StringBuilder();
        Integer metricId = null;
        Session session = this.getSession();
        try {
            sql.append("select distinct m.id as id ");
            sql.append(" from metric m, situation s, rel_extraction_plan_metric_evidence_provider rel ");
            sql.append(" where ");
            sql.append(" m.situation_id = s.id ");
            sql.append(" and s.situation_template_id = ").append(situationTemplateId);
            sql.append(" and s.process_id = ").append(processId);
            sql.append(" and m.metric_template_id = ").append(metricTemplateId);
            sql.append(" and rel.evidence_provider_id = ").append(evidenceProviderId);
            sql.append(" and rel.extraction_plan_metric_id = m.extraction_plan_metric_id");
            Query query = session.createSQLQuery(sql.toString()).addScalar("id", Hibernate.INTEGER);
            metricId = (Integer) query.uniqueResult();
        } catch (DataAccessResourceFailureException e) {
            log.error(e, e);
        } catch (IllegalStateException e) {
            log.error(e, e);
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        log.info("end [metricId:" + metricId + "]");
        return metricId;
    }

    @Override
    public EvidenceRequest getEvidenceRequestForAMetric(EvidenceRequest evidenceRequest) {
        log.debug("start");
        EvidenceRequest er = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(EvidenceRequest.class);
            if (evidenceRequest != null) {
                if (evidenceRequest.getMetric() != null && evidenceRequest.getMetric().getId() != null
                        && evidenceRequest.getMetric().getId() > 0) {
                    log.debug("metricId:" + evidenceRequest.getMetric().getId());
                    criteria.createCriteria("metric").add(Restrictions.eq("id", evidenceRequest.getMetric().getId()));
                }
                if (evidenceRequest.getDay() != null) {
                    log.debug("day:" + evidenceRequest.getDay());
                    criteria.add(Restrictions.eq("day", evidenceRequest.getDay()));
                }
                if (evidenceRequest.getType() != null) {
                    log.debug("type:" + evidenceRequest.getType());
                    criteria.add(Restrictions.eq("type", evidenceRequest.getType()));
                }
                if (evidenceRequest.getEvidenceProvider() != null && evidenceRequest.getEvidenceProvider().getId() != null
                        && evidenceRequest.getEvidenceProvider().getId() > 0) {
                    log.debug("providerId:" + evidenceRequest.getEvidenceProvider().getId());
                    criteria.createCriteria("evidenceProvider").add(
                            Restrictions.eq("id", evidenceRequest.getEvidenceProvider().getId()));
                }
            }
            List<EvidenceRequest> evidenceRequests = criteria.list();
            log.debug("evidenceRequests.size:" + evidenceRequests.size());
            if (evidenceRequests != null && !evidenceRequests.isEmpty()) {
                er = evidenceRequests.get(0);
            }
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }

        log.debug("end");
        return er;
    }

    @Override
    public Evidence getEvidenceByPath(String evidencePath, Integer evidenceRequestId) {
        log.debug("start");
        Evidence evidence = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(Evidence.class);
            if (evidencePath != null && evidencePath.length() > 0) {
                criteria.add(Restrictions.eq("evidencePath", evidencePath));
                criteria.createCriteria("evidenceRequests").add(Restrictions.eq("id", evidenceRequestId));
                List<Evidence> evidences = criteria.list();
                if (evidences != null && !evidences.isEmpty()) {
                    evidence = evidences.get(0);
                }
            }
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        log.debug("end");
        return evidence;
    }

    @Override
    public Integer getNextIdForEvidenceRequest() {
        String sql = "select nextval('evidence_request_seq')";
        Integer id = null;
        try {
            id = getJdbcTemplate().queryForObject(sql, Integer.class);
        } catch (DataAccessException e) {
            log.error("no es posible recuperar new Id para evidence_request_seq " + e, e);
        }
        return id;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveEvidenceRequest(EvidenceRequest evidenceRequest) throws ScopixException {
        log.info("start");
        StringBuilder sqlInsert = new StringBuilder();
        sqlInsert.append("INSERT INTO evidence_request(dtype, id, \"day\", evidence_request_type, evidence_time, \"type\", ");
        sqlInsert.append(" duration, evidence_provider_id, extraction_plan_range_detail_id, metric_id ,priorization) VALUES ( ");
        sqlInsert.append(" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        String dType = "";
        switch (evidenceRequest.getType()) {
            case VIDEO:
                dType = RequestedVideo.class.getSimpleName();
                break;
            case IMAGE:
                dType = RequestedImage.class.getSimpleName();
                break;
            case XML:
                dType = RequestedXml.class.getSimpleName();
                break;
            default:
                log.info("no se recibe evidenceType valido" + evidenceRequest.getType().getName());
                throw new ScopixException("Tipo de evidencia no soportado " + evidenceRequest.getType().getName());

        }
        try {
            getJdbcTemplate().update(
                    sqlInsert.toString(),
                    new Object[]{dType, evidenceRequest.getId(), evidenceRequest.getDay(),
                        evidenceRequest.getEvidenceRequestType().name(), evidenceRequest.getEvidenceTime(),
                        evidenceRequest.getType().name(), 0, evidenceRequest.getEvidenceProvider().getId(), null,
                        evidenceRequest.getMetric().getId(), evidenceRequest.getPriorization()});
        } catch (DataAccessException e) {
            log.error("Error Insertantdo ER " + e, e);
            throw new ScopixException(e);
        }
        log.info("end");
        // evidenceRequest.getExtractionPlanRangeDetail().getExtractionPlanRange().getDuration();
        // evidenceRequest.getExtractionPlanRangeDetail().getId();
    }

    @Override
    public Integer getNextIdForSituation() {
        String sql = "select nextval('situation_seq')";
        Integer id = null;
        try {
            id = getJdbcTemplate().queryForObject(sql, Integer.class);
        } catch (DataAccessException e) {
            log.error("no es posible recuperar new Id para situation_seq " + e, e);
        }
        return id;
    }

    @Override
    public void saveSituation(Situation situation) throws ScopixException {
        log.info("start");
        StringBuilder sqlInsert = new StringBuilder();
        sqlInsert.append("Insert into situation (id, description, process_id, situation_template_id) values (?, ?, ?, ?)");
        try {
            getJdbcTemplate().update(
                    sqlInsert.toString(),
                    new Object[]{situation.getId(), situation.getDescription(), situation.getProcessId(),
                        situation.getSituationTemplate().getId()});
        } catch (DataAccessException e) {
            log.error("Error Insertantdo ER " + e, e);
            throw new ScopixException(e);
        }
        log.info("end");

    }

    @Override
    public Integer getNextIdForMetric() {
        String sql = "select nextval('metric_seq')";
        Integer id = null;
        try {
            id = getJdbcTemplate().queryForObject(sql, Integer.class);
        } catch (DataAccessException e) {
            log.error("no es posible recuperar new Id para metric_seq " + e, e);
        }
        return id;
    }

    @Override
    public void saveMetric(Metric metric) throws ScopixException {
        log.info("start");
        StringBuilder sqlInsert = new StringBuilder();
        sqlInsert.append("Insert into metric (id, description, metric_order, metric_variable_name, area_id, ");
        sqlInsert.append("extraction_plan_metric_id, metric_template_id, situation_id, store_id) ");
        sqlInsert.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        log.debug("Metric [id:" + metric.getId() + "][description:" + metric.getDescription() + "]" + "[order:"
                + metric.getMetricOrder() + "][variableName:" + metric.getMetricVariableName() + "]" + "[area:"
                + metric.getArea() + "][extractionPlanMetric:" + metric.getExtractionPlanMetric() + "]" + "[metricTemplate:"
                + metric.getMetricTemplate() + "][situation:" + metric.getSituation() + "]" + "[store:"
                + metric.getStore().getId() + "]");
        try {
            getJdbcTemplate().update(
                    sqlInsert.toString(),
                    new Object[]{metric.getId(), metric.getDescription(), metric.getMetricOrder(),
                        metric.getMetricVariableName(), metric.getArea().getId(), metric.getExtractionPlanMetric().getId(),
                        metric.getMetricTemplate().getId(), metric.getSituation().getId(), metric.getStore().getId()});
        } catch (DataAccessException e) {
            log.error("Error Insertantdo ER " + e, e);
            throw new ScopixException(e);
        }
        log.info("end");
    }

    @Override
    public Process getProcessFromAutomaticEvidenceAvailable(AutomaticEvidenceAvailableDTO evidenceAvailable) {
        Process p = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(Process.class);
            criteria.add(Restrictions.eq("processIdEs", evidenceAvailable.getProcessId()));
            criteria.createCriteria("eess").add(Restrictions.eq("id", evidenceAvailable.getEvidenceExtractionServicesServerId()));
            List<Process> processes = criteria.list();
            if (processes != null && !processes.isEmpty()) {
                p = processes.get(0);
            }
        } catch (DataAccessResourceFailureException e) {
            log.warn(e);
        } catch (IllegalStateException e) {
            log.warn(e);
        } catch (HibernateException e) {
            log.warn(e);
        } finally {
            this.releaseSession(session);
        }
        return p;

    }

    @Override
    public Area getAreaByreaTypeStore(Integer areaTypeId, Integer storeId) {
        Area a = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(Area.class);
            criteria.createCriteria("store").add(Restrictions.eq("id", storeId));
            criteria.createCriteria("areaType").add(Restrictions.eq("id", areaTypeId));
            List<Area> list = criteria.list();
            // se retorna la primera area de la lista si existen mas conbinaciones
            if (!list.isEmpty()) {
                a = list.get(0);
            }
            // a = (Area) criteria.uniqueResult();
        } catch (DataAccessResourceFailureException e) {
            log.error(e, e);
        } catch (IllegalStateException e) {
            log.error(e, e);
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return a;
    }

    @Override
    public AutomaticEvaluationResult getAutomaticEvaluationResultByEvidenceIdAndSituationTemplateId(Integer evidenceId,
            Integer situationTemplateId) {
        log.debug("start");
        AutomaticEvaluationResult aer = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(AutomaticEvaluationResult.class);
            criteria.createCriteria("evidence").add(Restrictions.eq("id", evidenceId));
            criteria.createCriteria("situationTemplate").add(Restrictions.eq("id", situationTemplateId));
            List<AutomaticEvaluationResult> aers = criteria.list();
            if (aers != null && !aers.isEmpty()) {
                aer = aers.get(0);
            }
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        log.debug("end");

        return aer;
    }

    @Override
    public List<EvidenceEvaluation> getEvidenceEvaluationByObservedMetric(Integer observedMetricId) throws ScopixException {
        log.info("start [observedMetricId:" + observedMetricId + "]");
        List<EvidenceEvaluation> ret = null;
        // recuperar datos completos de las evaluaciones evaluaciones y proofs
        Session session = this.getSession();
        try {
            // only evaluations is not rejected
            Criteria criteria = session.createCriteria(EvidenceEvaluation.class);
            criteria.createCriteria("observedMetric").add(Restrictions.eq("id", observedMetricId));
            criteria.add(Restrictions.eq("rejected", Boolean.FALSE));
            ret = criteria.list();
        } catch (DataAccessResourceFailureException e) {
            log.error(e, e);
        } catch (IllegalStateException e) {
            log.error(e, e);
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        log.info("end");
        return ret;
    }

    @Override
    public List<Integer> findAllPendingEvalutionLiveExpired(String date) {
        List<Integer> ret = null;
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct pe.id "); // --m.store_id, store.name, store.time_zone_id,
        sql.append(" from pending_evaluation pe, observed_situation os, situation s, situation_template st, ");
        sql.append(" observed_metric om, metric m, place store ");
        sql.append(" where ");
        sql.append(" st.live = true ");
        sql.append(" and s.situation_template_id = st.id ");
        sql.append(" and os.situation_id = s.id ");
        sql.append(" and os.observed_situation_date = '").append(date).append("' ");
        sql.append(" and pe.observed_situation_id = os.id ");
        sql.append(" and om.observed_situation_id = os.id ");
        sql.append(" and m.id = om.metric_id ");
        sql.append(" and store.id = m.store_id ");
        sql.append(" and pe.evaluation_queue = 'OPERATOR' ");
        sql.append(" and pe.evaluation_state = 'ENQUEUED' ");
        sql.append(" and operator_queue_id > 0 ");
        sql.append(" and os.evidence_date <= ");
        sql.append("((now() AT TIME ZONE store.time_zone_id)::timestamp without time zone - interval '1 minute' * ");
        sql.append("st.delay_in_minutes)");
        ret = getJdbcTemplate().queryForList(sql.toString(), Integer.class);
        return ret;
    }

    @Override
    public List<StorePlan> getSituationStoreDay(Date date) {
        log.info("start [date:" + date + "]");
        List<StorePlan> ret = null;
        try {
            String sDate = DateFormatUtils.format(date, "yyyy-MM-dd");
            StringBuilder sql = new StringBuilder();
            sql.append("select data.store_name, data.store_id , data.day, data.situation_template_name, ");
            sql.append(" data.situation_template_id, count(1) as cnt ");
            sql.append(" from ");
            sql.append(" (select distinct to_number(store.name, '9999') as store_name,");
            sql.append(" store.id as store_id, st.name as situation_template_name, st.id as situation_template_id, ");
            sql.append(" s.description , er.day");
            sql.append("	from");
            sql.append("	extraction_plan_customizing  epc,");
            sql.append("	place store,");
            sql.append("	extraction_plan_metric epm,");
            sql.append("	situation_template st,");
            sql.append("	metric m,");
            sql.append("	situation s,");
            sql.append("	evidence_request er");
            sql.append("	where st.active = true");
            sql.append("	and st.name ='WT_Checkouts'");
            sql.append("	and epc.situation_template_id = st.id");
            sql.append("	and epc.active = true");
            sql.append("	and epm.extraction_plan_customizing_id = epc.id");
            sql.append("	and m.extraction_plan_metric_id = epm.id");
            sql.append("	and m.situation_id = s.id");
            sql.append("	and s.situation_template_id = st.id");
            sql.append("	and store.id = epc.store_id");
            sql.append("	and er.metric_id = m.id");
            sql.append("	) data");
            sql.append(" where data.day = (EXTRACT(dow FROM TIMESTAMP '").append(sDate).append("') +1)");
            sql.append(" group by data.day, data.store_name, data.store_id, data.situation_template_name, ");
            sql.append(" data.situation_template_id ");
            sql.append(" order by data.store_name,data.day, data.situation_template_name");
            List<Map<String, Object>> lista = getJdbcTemplate().queryForList(sql.toString());
            ret = new ArrayList<StorePlan>();
            for (Map m : lista) {
                Integer storeId = (Integer) m.get("store_id");
                String storeName = ((BigDecimal) m.get("store_name")).toString();
                String situationTemplateName = (String) m.get("situation_template_name");
                Integer day = (Integer) m.get("day");
                Integer count = ((Long) m.get("cnt")).intValue();
                Integer situationId = (Integer) m.get("situation_template_id");
                StorePlan sp = new StorePlan();
                sp.setStoreId(storeId);
                sp.setCount(count);
                sp.setDayOfWeek(day);
                sp.setName(storeName);
                sp.setSituationName(situationTemplateName);
                sp.setSituationId(situationId);
                ret.add(sp);
            }
        } catch (DataAccessException e) {
            log.error(e, e);
        }
        log.info("end [ret:" + ret.size() + "]");
        return ret;
    }

    @Override
    public List<StorePlan> getSituationStoreDayReady(Date date) {
        log.info("start [date:" + date + "]");
        List<StorePlan> ret = null;
        try {
            String sDate = DateFormatUtils.format(date, "yyyy-MM-dd");
            StringBuilder sql = new StringBuilder();
            sql.append("select data.store_name, data.store_id, data.day, data.situation_template_name, ");
            sql.append(" data.situation_template_id, count(1) as cnt");
            sql.append(" from (");
            sql.append("	select distinct to_number(store.name, '9999') as store_name, store.id as store_id,  ");
            sql.append(" st.name as situation_template_name, st.id as situation_template_id ,");
            sql.append(" (EXTRACT(dow FROM TIMESTAMP '").append(sDate).append("') +1) as day, ");
            sql.append(" os.evidence_date ");
            sql.append(" from ");
            sql.append("	pending_evaluation pe, observed_situation os, situation s, situation_template st,");
            sql.append("	observed_metric om, metric m, place store");
            sql.append("	where pe.observed_situation_id = os.id");
            sql.append("	and s.id = os.situation_id");
            sql.append("	and st.id = s.situation_template_id");
            sql.append("	and os.observed_situation_date ='").append(sDate).append("'");
            sql.append("	and om.observed_situation_id = os.id");
            sql.append("	and m.id = om.metric_id");
            sql.append("	and store.id = m.store_id");
            sql.append("	and st.name ='WT_Checkouts'");
            sql.append(" )data ");
            sql.append(" group by data.day, data.store_name, data.store_id, data.situation_template_name");
            sql.append(" , data.situation_template_id");
            sql.append(" order by data.store_name,data.day, data.situation_template_name");
            List<Map<String, Object>> lista = getJdbcTemplate().queryForList(sql.toString());
            ret = new ArrayList<StorePlan>();
            for (Map m : lista) {
                Integer storeId = (Integer) m.get("store_id");
                String storeName = ((BigDecimal) m.get("store_name")).toString();
                String situationTemplateName = (String) m.get("situation_template_name");
                Integer day = ((Double) m.get("day")).intValue();
                Integer count = ((Long) m.get("cnt")).intValue();
                Integer situationId = (Integer) m.get("situation_template_id");
                StorePlan sp = new StorePlan();
                sp.setStoreId(storeId);
                sp.setCount(count);
                sp.setDayOfWeek(day);
                sp.setName(storeName);
                sp.setSituationName(situationTemplateName);
                sp.setSituationId(situationId);
                ret.add(sp);
            }
        } catch (DataAccessException e) {
            log.error(e, e);
        }
        log.info("end [ret:" + ret.size() + "]");
        return ret;
    }

    @Override
    public List<StorePlan> getEvidenceForStoreDaySituation(Date date) {
        log.info("start [date:" + date + "]");
        List<StorePlan> ret = null;
        try {
            String sDate = DateFormatUtils.format(date, "yyyy-MM-dd");
            StringBuilder sql = new StringBuilder();

            sql.append("select data.store_id, data.store_name,  data.day_of_week, data.st_id, data.st_name, ");
            sql.append(" count(1)::integer as cnt");
            sql.append(" from (select distinct to_number(s.name, '9999') as store_name, s.id as store_id,  epr.day_of_week,");
            sql.append(" st.name as st_name, st.id as st_id,");
            sql.append(" eprd.time_sample, ep.description, epr.duration, epr.extraction_plan_range_type");
            sql.append(" from  extraction_plan_customizing  epc,");
            sql.append(" place s, extraction_plan_metric epm, rel_extraction_plan_metric_evidence_provider rel,");
            sql.append(" evidence_provider ep, extraction_plan_range epr, extraction_plan_range_detail eprd,");
            sql.append(" situation_template st");
            sql.append(" where");
            sql.append(" epc.store_id = s.id");
            sql.append(" and epc.active = true");
            sql.append(" and epm.extraction_plan_customizing_id = epc.id");
            sql.append(" and rel.extraction_plan_metric_id = epm.id");
            sql.append(" and ep.id = rel.evidence_provider_id");
            sql.append(" and epr.extraction_plan_customizing_id = epc.id");
            sql.append(" and (epr.extraction_plan_range_type != 'AUTOMATIC_EVIDENCE' ");
            sql.append("     and epr.extraction_plan_range_type != 'REAL_RANDOM')");
            sql.append(" and eprd.extraction_plan_range_id = epr.id");
            sql.append(" and epr.day_of_week = (EXTRACT(dow FROM TIMESTAMP '").append(sDate).append("') +1)");
            sql.append(" and st.id = epc.situation_template_id");
            sql.append(" and st.active = true) as data");
            sql.append(" group by data.day_of_week, data.store_name, data.st_name, data.store_id, data.st_id");
            sql.append(" union all");
//                sql.append( "-- cantidad evidencias para store con real_random"
            sql.append(" select data.store_id, data.store_name, data.day_of_week, data.st_id, data.st_name, ");
            sql.append(" sum(data.evidences)::integer as cnt");
            sql.append(" from (");
            sql.append(" select distinct to_number(store.name, '9999') as store_name, epr.day_of_week,");
            sql.append(" ((extract(epoch from (epr.end_time - epr.initial_time))/60) / epr.frecuency)* epr.samples as evidences,");
            sql.append(" extract(epoch from (epr.end_time - epr.initial_time))/60,");
            sql.append(" ep.description, epr.end_time, epr.initial_time,epr.frecuency, st.name as st_name,");
            sql.append(" store.id as store_id, st.id as st_id");
            sql.append(" from extraction_plan_customizing epc, extraction_plan_range epr, place store, extraction_plan_metric epm,");
            sql.append(" rel_extraction_plan_metric_evidence_provider rel, evidence_provider ep,");
            sql.append(" situation_template st");
            sql.append(" where epr.extraction_plan_customizing_id = epc.id");
            sql.append(" and epc.active = true");
            sql.append(" and epr.extraction_plan_range_type ='REAL_RANDOM' ");
            sql.append(" and store.id  = epc.store_id");
            sql.append(" and epm.extraction_plan_customizing_id = epc.id");
            sql.append(" and rel.extraction_plan_metric_id = epm.id");
            sql.append(" and ep.id = rel.evidence_provider_id");
            sql.append(" and day_of_week = (EXTRACT(dow FROM TIMESTAMP '").append(sDate).append("') +1)");
            sql.append(" and st.id = epc.situation_template_id");
            sql.append(" ) as data");
            sql.append(" group by data.store_name,data.day_of_week, data.st_name, data.store_id, data.st_id");
            sql.append(" order by 2,3,5");
            List<Map<String, Object>> lista = getJdbcTemplate().queryForList(sql.toString());
            ret = new ArrayList<StorePlan>();
            for (Map m : lista) {
                Integer storeId = (Integer) m.get("store_id");
                String storeName = ((BigDecimal) m.get("store_name")).toString();
                String situationTemplateName = (String) m.get("st_name");
                Integer day = (Integer) m.get("day_of_week");
                Integer count = ((Integer) m.get("cnt")).intValue();
                Integer situationId = (Integer) m.get("st_id");
                StorePlan sp = new StorePlan();
                sp.setStoreId(storeId);
                sp.setCount(count);
                sp.setDayOfWeek(day);
                sp.setName(storeName);
                sp.setSituationName(situationTemplateName);
                sp.setSituationId(situationId);
                ret.add(sp);
            }
        } catch (DataAccessException e) {
            log.error(e, e);
        }
        return ret;
    }

    @Override
    public List<StorePlan> getEvidenceForStoreDaySituationReal(Date date) {
        log.info("start [date:" + date + "]");
        List<StorePlan> ret = null;

        try {
            String sDate = DateFormatUtils.format(date, "yyyy-MM-dd");
            StringBuilder sql = new StringBuilder();
            sql.append("select data.store_id, data.store_name, data.day_of_week, data.st_id, data.st_name, ");
            sql.append(" count(1)::integer as cnt");
            sql.append(" from ");
            sql.append(" (");
            sql.append(" select distinct store.id as store_id, to_number(store.name,'9999') as store_name, st.name as st_name, ");
            sql.append(" e.evidence_path,");
            sql.append(" (EXTRACT(dow FROM  om.observed_metric_date) +1) as day_of_week , st.id as st_id");
            sql.append(" from evidence e, rel_observed_metric_evidence rel1, observed_metric om, metric m, place store, ");
            sql.append(" observed_situation os, situation s, situation_template st");
            sql.append(" where e.id = rel1.evidence_id");
            sql.append(" and om.id = rel1.observed_metric_id");
            sql.append(" and om.observed_metric_date = '").append(sDate).append("'");
            sql.append(" and m.id = om.metric_id");
            sql.append(" and store.id = m.store_id");
            sql.append(" and os.id = om.observed_situation_id");
            sql.append(" and s.id = os.situation_id");
            sql.append(" and st.id = s.situation_template_id");
            sql.append(" ) data");
            sql.append(" group by data.store_name, data.store_id, data.day_of_week, data.st_name, data.st_id");
            sql.append(" order by data.store_name, data.day_of_week, data.st_name");

            List<Map<String, Object>> lista = getJdbcTemplate().queryForList(sql.toString());
            ret = new ArrayList<StorePlan>();
            for (Map m : lista) {
                Integer storeId = (Integer) m.get("store_id");
                String storeName = ((BigDecimal) m.get("store_name")).toString();
                String situationTemplateName = (String) m.get("st_name");
                Integer day = ((Double) m.get("day_of_week")).intValue();
                Integer count = ((Integer) m.get("cnt")).intValue();
                Integer situationId = (Integer) m.get("st_id");
                StorePlan sp = new StorePlan();
                sp.setStoreId(storeId);
                sp.setCount(count);
                sp.setDayOfWeek(day);
                sp.setName(storeName);
                sp.setSituationName(situationTemplateName);
                sp.setSituationId(situationId);
                ret.add(sp);
            }
        } catch (DataAccessException e) {
            log.error(e, e);
        }
        return ret;
    }

    @Override
    public List<String> getUrlsBee(Date date) {
        log.info("end");
        List<String> ret = null;
        try {
            //TODO determinar para que fecha se necesita la generacion de URLs
            String sDateIni = DateFormatUtils.format(DateUtils.addDays(date, -1), "yyyy-MM-dd");
            String sDateEnd = DateFormatUtils.format(date, "yyyy-MM-dd");
            StringBuilder sql = new StringBuilder();

            sql.append("select distinct to_number(store.name,'9999')::integer as store_name, ee.evaluation_user, ");
            sql.append(" (eess.url || '/spring/automaticevidenceinjectioncontroller?sensorID=' ||sensor.name ||");
            sql.append(" '&date=' || to_char(e.evidence_date,'YYYYMMDD') || '&time=' || to_char(e.evidence_date,'HH24MI') || ");
            sql.append(" '&duration=300' || '&cameraName=' || ep.description || '&delay=0') as url ");
            sql.append(" from evidence_evaluation ee, observed_metric om, metric m, metric_template mt, place store,");
            sql.append(" rel_observed_metric_evidence rel_1, evidence e, evidence_provider ep, ");
            sql.append(" rel_evidence_request_evidence rel_2, evidence_extraction_services_server eess, sensor, ");
            sql.append(" evidence_request er, ( ");
            sql.append("  select distinct ee.pending_evaluation_id from evidence_evaluation ee");
            sql.append("  where ee.cant_do_reason  in('The video file has ended','Finalizo el video')");
            sql.append("  and ee.evaluation_date between '").append(sDateIni).append("' and '").append(sDateEnd).append("') data");
            sql.append(" where ee.pending_evaluation_id = data.pending_evaluation_id");
            sql.append(" and om.id = ee.observed_metric_id");
            sql.append(" and m.id = om.metric_id");
            sql.append(" and store.id = m.store_id");
            sql.append(" and mt.id = m.metric_template_id");
            sql.append(" and mt.id = 294912"); //metric template Input Number 
            sql.append(" and rel_1.observed_metric_id = om.id");
            sql.append(" and e.id = rel_1.evidence_id");
            sql.append(" and rel_2.evidence_id = e.id");
            sql.append(" and er.id = rel_2.evidence_request_id");
            sql.append(" and ep.id = er.evidence_provider_id");
            sql.append(" and er.metric_id = m.id");
            sql.append(" and ep.description ='Camera ' || ee.evidence_result");
            sql.append(" and ep.definition_data like '%provider_type=i3DM%'");
            sql.append(" and eess.id = store.evidence_extraction_services_server_id ");
            sql.append(" and sensor.store_id = store.id");
            sql.append(" order by store_name");
            List<Map<String, Object>> lista = getJdbcTemplate().queryForList(sql.toString());
            ret = new ArrayList<String>();
            for (Map row : lista) {
                Integer store = (Integer) row.get("store_name");
                String user = (String) row.get("evaluation_user");
                String url = (String) row.get("url");
                log.debug("bee for [store:" + store + "][url:" + user + "]");
                ret.add(url);
            }
        } catch (DataAccessException e) {
            log.error(e, e);
        }
        log.info("end");
        return ret;
    }

}
