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
 * ObservedMetricHibernateDAO.java
 *
 * Created on 09-05-2008, 04:44:21 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.dao;

import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.evaluationmanagement.EvaluationState;
import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.evaluationmanagement.MetricEvaluation;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.evaluationmanagement.dto.EvidencesAndProofsDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@SpringBean(rootClass = ObservedMetricHibernateDAO.class)
public class ObservedMetricHibernateDAO extends DAOHibernate<ObservedMetric, Integer> {

    private Logger log = Logger.getLogger(ObservedMetricHibernateDAO.class);

    public ObservedMetric getObservedMetricForADay(Integer metricId, Date day) {
        ObservedMetric observedMetric = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(ObservedMetric.class);
            criteria.add(Restrictions.eq("observedMetricDate", day));
            criteria.add(Restrictions.eq("metric.id", metricId));
            List<ObservedMetric> observedMetrics = criteria.list();
            if (observedMetrics != null && !observedMetrics.isEmpty()) {
                observedMetric = observedMetrics.get(0);
            }
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return observedMetric;
    }

    public List<ObservedMetric> getObservedMetricList(ObservedMetric observedMetric) {
        log.info("start");
        registerLogs(observedMetric);
        Session session = this.getSession();
        List<ObservedMetric> observedMetrics = null;
        try {
            Criteria criteria = session.createCriteria(ObservedMetric.class);
            criteria.addOrder(Order.asc("id"));
            if (observedMetric != null) {
                log.debug("observedMetric es diferente de null");
                if (observedMetric.getEvaluationState() != null) {
                    log.debug("evaluationState criteria: [" + observedMetric.getEvaluationState() + "]");
                    criteria.add(Restrictions.eq("evaluationState", observedMetric.getEvaluationState()));
                }
                if (observedMetric.getMetric() != null && observedMetric.getMetric().getId() != null) {
                    log.debug("metricId criteria: [" + observedMetric.getMetric().getId() + "]");
                    criteria.add(Restrictions.eq("metric.id", observedMetric.getMetric().getId()));
                }
                if (observedMetric.getObservedMetricDate() != null) {
                    log.debug("observedMetricDate criteria: [" + observedMetric.getObservedMetricDate() + "]");
                    criteria.add(Restrictions.eq("observedMetricDate", observedMetric.getObservedMetricDate()));
                }
                if (observedMetric.getObservedSituation() != null && observedMetric.getObservedSituation().getId() != null) {
                    log.debug("observedSituationId criteria: [" + observedMetric.getObservedSituation().getId() + "]");
                    criteria.add(Restrictions.eq("observedSituation.id", observedMetric.getObservedSituation().getId()));
                }
            }
            log.debug("antes de listar observedMetrics");
            observedMetrics = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        log.info("end");
        return observedMetrics;
    }

    /**
     * Registros de logs para diagnóstico de issue #1003, existen casos en donde no se retornan todas las observed metrics, se
     * agrupa en un try-catch en caso que algun nullPointerException no interfiera con el flujo
     *
     * @param observedMetric
     */
    private void registerLogs(ObservedMetric observedMetric) {
        log.info("start, observedMetric.getId(): [" + observedMetric.getId() + "]");
        try {
            EvaluationState evaluationState = observedMetric.getEvaluationState();
            if (evaluationState != null) {
                log.debug("evaluationState.getName(): [" + evaluationState.getName() + "]");
            }
            log.debug("observedMetric.getEvidenceDate(): [" + observedMetric.getEvidenceDate() + "]");

            List<EvidenceEvaluation> lstEvidenceEvaluations = observedMetric.getEvidenceEvaluations();
            if (lstEvidenceEvaluations != null && !lstEvidenceEvaluations.isEmpty()) {
                log.debug("lstEvidenceEvaluations.size(): [" + lstEvidenceEvaluations.size() + "]");
                for (EvidenceEvaluation evidenceEvaluation : lstEvidenceEvaluations) {
                    log.debug("evidenceEvaluation.getId(): [" + evidenceEvaluation.getId() + "]");
                }
            }

            List<Evidence> lstEvidences = observedMetric.getEvidences();
            if (lstEvidences != null && !lstEvidences.isEmpty()) {
                log.debug("lstEvidences.size(): [" + lstEvidences.size() + "]");
                for (Evidence evidence : lstEvidences) {
                    log.debug("evidence.getId(): [" + evidence.getId() + "]");
                    log.debug("evidence.getEvidencePath(): [" + evidence.getEvidencePath() + "]");
                }
            }

            Metric metric = observedMetric.getMetric();
            if (metric != null) {
                log.debug("metric.getId(): [" + metric.getId() + "]");
                log.debug("metric.getDescription(): [" + metric.getDescription() + "]");
            }

            MetricEvaluation metricEvaluation = observedMetric.getMetricEvaluation();
            if (metricEvaluation != null) {
                log.debug("metricEvaluation.getId(): [" + metricEvaluation.getId() + "]");
            }
            log.debug("observedMetric.getObservedMetricDate(): [" + observedMetric.getObservedMetricDate() + "]");

            ObservedSituation observedSituation = observedMetric.getObservedSituation();
            if (observedSituation != null) {
                log.debug("observedSituation.getId(): [" + observedSituation.getId() + "]");
            }
            log.debug("observedMetric.getPriority(): [" + observedMetric.getPriority() + "]");

            EvaluationState stateType = observedMetric.getStateType();
            if (stateType != null) {
                log.debug("stateType.getName(): [" + stateType.getName() + "]");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("end");
    }

    public List<ObservedMetric> getObservedMetricListSQL(ObservedMetric observedMetric) {
        List<ObservedMetric> observedMetrics = new ArrayList<ObservedMetric>();
        log.info("start");
        Session session = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select om.id, om.evaluation_state, om.metric_id, om.observed_metric_date, ");
            sql.append("om.observed_situation_id from observed_metric om ");
            if (observedMetric != null) {
                if (observedMetric.getEvaluationState() != null) {
                    sql.append(" WHERE om.evaluation_state = '");
                    sql.append(observedMetric.getEvaluationState().name());
                    sql.append("'");
                }
            }
            session = this.getSession();
            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();

            if (list.size() > 0) {
                for (Map m : list) {
                    ObservedMetric om = new ObservedMetric();
                    om.setId((Integer) m.get("id"));
                    om.setEvaluationState(EvaluationState.valueOf((String) m.get("evaluation_state")));
                    Metric metric = new Metric();
                    metric.setId((Integer) m.get("metric_id"));
                    om.setMetric(metric);
                    om.setObservedMetricDate((Date) m.get("observed_metric_date"));
                    ObservedSituation observedSituation = new ObservedSituation();
                    observedSituation.setId((Integer) m.get("observed_situation_id"));
                    om.setObservedSituation(observedSituation);

                    observedMetrics.add(om);
                }
            }

        } catch (Exception ex) {
            log.error("error = " + ex.getMessage());
        } finally {
            if (session != null) {
                this.releaseSession(session);
            }
        }
        log.info("end");
        return observedMetrics;
    }

    public List<EvidenceEvaluation> getEvidenceEvaluationsForAObservedMetric(int observedMetricId) {
        List<EvidenceEvaluation> evidenceEvaluations = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(EvidenceEvaluation.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.eq("observedMetric.id", observedMetricId));
            evidenceEvaluations = criteria.list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return evidenceEvaluations;
    }

    public List<EvidencesAndProofsDTO> getEvidenceAndProof(Integer observedMetricId) throws ScopixException {
        List<EvidencesAndProofsDTO> listResult = new ArrayList<EvidencesAndProofsDTO>();
        Session session = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT distinct e.id AS evidence_id, p.id AS proof_id, ep.description, ep.id AS camera");
            sql.append(" FROM");
            sql.append(" evidence_evaluation ee,");
            sql.append(" rel_evidence_evaluation_evidence reee,");
            sql.append(" evidence e,");
            sql.append(" rel_evidence_request_evidence rel,");
            sql.append(" evidence_request er,");
            sql.append(" evidence_provider ep,");
            sql.append(" proof p");
            sql.append(" WHERE");
            sql.append(" ee.observed_metric_id = ");
            sql.append(observedMetricId);
            sql.append(" and reee.evidence_evaluation_id=ee.id");
            sql.append(" and reee.evidence_id = e.id");
            sql.append(" and p.evidence_evaluation_id = ee.id");
            sql.append(" and rel.evidence_id = e.id");
            sql.append(" and rel.evidence_request_id = er.id");
            sql.append(" and er.evidence_provider_id = ep.id");
            sql.append(" and ee.rejected = false");
            sql.append(" ORDER BY ep.id");

            session = this.getSession();
            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();

            if (list.size() > 0) {
                Integer evidenceId = null;
                EvidencesAndProofsDTO dto = null;
                List<Integer> proofs = null;
                for (Map m : list) {
                    if (evidenceId == null || !evidenceId.equals((Integer) m.get("evidence_id"))) {
                        if (dto != null) {
                            dto.setProofsId(proofs);
                            listResult.add(dto);
                        }
                        evidenceId = (Integer) m.get("evidence_id");
                        dto = new EvidencesAndProofsDTO();
                        dto.setEvidenceId(evidenceId);
                        proofs = new ArrayList<Integer>();
                    }
                    proofs.add((Integer) m.get("proof_id"));
                }
                if (dto != null) {
                    dto.setProofsId(proofs);
                    listResult.add(dto);
                }
            } else {
                sql.append("SELECT distinct e.id AS evidence_id, -1 AS proof_id, ep.description, ep.id AS camera");
                sql.append(" FROM");
                sql.append(" evidence_evaluation ee,");
                sql.append(" rel_evidence_evaluation_evidence reee,");
                sql.append(" evidence e,");
                sql.append(" rel_evidence_request_evidence rel,");
                sql.append(" evidence_request er,");
                sql.append(" evidence_provider ep");
                sql.append(" WHERE");
                sql.append(" ee.observed_metric_id =");
                sql.append(observedMetricId);
                sql.append(" and reee.evidence_evaluation_id=ee.id");
                sql.append(" and reee.evidence_id = e.id");
                sql.append(" and rel.evidence_id = e.id");
                sql.append(" and rel.evidence_request_id = er.id");
                sql.append(" and er.evidence_provider_id = ep.id");
                sql.append(" and ee.rejected = false");
                sql.append(" ORDER BY ep.id");

                query = session.createSQLQuery(sql.toString());
                list = query.list();

                Integer evidenceId = null;
                EvidencesAndProofsDTO dto = null;
                List<Integer> proofs = null;
                for (Map m : list) {
                    if (evidenceId == null || !evidenceId.equals((Integer) m.get("evidence_id"))) {
                        if (dto != null) {
                            dto.setProofsId(proofs);
                            listResult.add(dto);
                        }
                        evidenceId = (Integer) m.get("evidence_id");
                        dto = new EvidencesAndProofsDTO();
                        dto.setEvidenceId(evidenceId);
                        proofs = new ArrayList<Integer>();
                    }
                    proofs.add((Integer) m.get("proof_id"));
                }
                if (dto != null) {
                    dto.setProofsId(proofs);
                    listResult.add(dto);
                }

            }

        } catch (Exception ex) {
            log.error("error = " + ex.getMessage());
        } finally {
            if (session != null) {
                this.releaseSession(session);
            }
        }
        return listResult;
    }
}
