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

import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.EvaluationState;
import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.extractionplanmanagement.Formula;
import com.scopix.periscope.extractionplanmanagement.Situation;
import com.scopix.periscope.extractionplanmanagement.dto.IndicatorValuesDTO;
import com.scopix.periscope.extractionplanmanagement.dto.ObservedSituationEvaluationDTO;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@SpringBean(rootClass = ObservedSituationHibernateDAOImpl.class)
public class ObservedSituationHibernateDAOImpl extends DAOHibernate<ObservedSituation, Integer>
        implements ObservedSituationHibernateDAO {

    private Logger log = Logger.getLogger(ObservedSituationHibernateDAOImpl.class);

    @Override
    public ObservedSituation getObservedSituationForADay(Integer situationId, Date day) {
        ObservedSituation observedSituation = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(ObservedSituation.class);
            criteria.add(Restrictions.eq("observedSituationDate", day));
            criteria.add(Restrictions.eq("situation.id", situationId));
            List<ObservedSituation> observedSituations = criteria.list();
            if (observedSituations != null && !observedSituations.isEmpty()) {
                observedSituation = observedSituations.get(0);
            }
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return observedSituation;
    }

    @Override
    public List<ObservedSituation> getObservedSituationList(ObservedSituation observedSituation) {
        Session session = this.getSession();
        List<ObservedSituation> observedSituations = null;
        try {
            Criteria criteria = session.createCriteria(ObservedSituation.class);
            criteria.addOrder(Order.asc("id"));
            if (observedSituation != null) {
                if (observedSituation.getEvaluationState() != null) {
                    criteria.add(Restrictions.eq("evaluationState", observedSituation.getEvaluationState()));
                }
                if (observedSituation.getSituation() != null && observedSituation.getSituation().getId() != null) {
                    criteria.add(Restrictions.eq("situation.id", observedSituation.getSituation().getId()));
                }
                if (observedSituation.getObservedSituationDate() != null) {
                    criteria.add(Restrictions.eq("observedSituationDate", observedSituation.getObservedSituationDate()));
                }
            }
            observedSituations = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return observedSituations;
    }

    @Override
    public List<ObservedSituation> getObservedSituationListSQL(ObservedSituation observedSituation) {
        List<ObservedSituation> observedSituations = new ArrayList<ObservedSituation>();
        Session session = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select os.id, os.evaluation_state, os.observed_situation_date, os.situation_id ");
            sql.append("from observed_situation os ");
            if (observedSituation != null) {
                if (observedSituation.getEvaluationState() != null) {
                    sql.append(" WHERE os.evaluation_state = '");
                    sql.append(observedSituation.getEvaluationState().name()).append("'");
                }
            }
            session = this.getSession();

            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();
            for (Map m : list) {
                ObservedSituation os = new ObservedSituation();
                os.setId((Integer) m.get("id"));
                os.setEvaluationState(EvaluationState.valueOf((String) m.get("evaluation_state")));
                os.setObservedSituationDate((Date) m.get("observed_situation_date"));
                Situation situation = new Situation();
                situation.setId((Integer) m.get("situation_id"));
                os.setSituation(situation);

                observedSituations.add(os);
            }
        } catch (Exception ex) {
            log.error("error = " + ex.getMessage());
        } finally {
            if (session != null) {
                this.releaseSession(session);
            }
        }
        return observedSituations;
    }

    @Override
    public List<ObservedSituationEvaluationDTO> getObservedSituationEvaluationDTOs(Date startDate, Date endDate,
            List<Integer> situationTemplateIds, List<Integer> storeIds) {
        List<ObservedSituationEvaluationDTO> dtos = new ArrayList<ObservedSituationEvaluationDTO>();
        Session session = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String idSTs = situationTemplateIds.toString().replaceAll("(\\[)|(\\])", "");
            String idStore = storeIds.toString().replaceAll("(\\[)|(\\])", "");
            StringBuilder sql = new StringBuilder();

            sql.append("SELECT DISTINCT ");
            sql.append("os.id,   ");
            sql.append("s.description AS situation,  ");
            sql.append("os.observed_situation_date,  ");
            sql.append("ose.evaluation_result,  ");
            sql.append("ose.compliant,  ");
            sql.append("ose.rule_name,  ");
            sql.append("ose.metric1,  ");
            sql.append("ose.metric2,  ");
            sql.append("ose.metric3,  ");
            sql.append("ose.metric4,  ");
            sql.append("ose.metric5,  ");
            sql.append("ose.metric6,  ");
            sql.append("ose.metric7,  ");
            sql.append("ose.metric8,  ");
            sql.append("ose.metric9,  ");
            sql.append("ose.metric10,  ");
            sql.append("(select mt.description from metric_template mt, metric m, observed_metric om    ");
            sql.append("   where ose.metric_id1 = om.id   ");
            sql.append("   and m.id = om.metric_id   ");
            sql.append("   and mt.id = m.metric_template_id) AS metricName1,   ");
            sql.append("(select mt.description from metric_template mt, metric m, observed_metric om    ");
            sql.append("   where ose.metric_id2 = om.id   ");
            sql.append("   and m.id = om.metric_id   ");
            sql.append("   and mt.id = m.metric_template_id) AS metricName2,   ");
            sql.append("(select mt.description from metric_template mt, metric m, observed_metric om    ");
            sql.append("   where ose.metric_id3 = om.id   ");
            sql.append("   and m.id = om.metric_id   ");
            sql.append("   and mt.id = m.metric_template_id) AS metricName3,   ");
            sql.append("(select mt.description from metric_template mt, metric m, observed_metric om    ");
            sql.append("   where ose.metric_id4 = om.id   ");
            sql.append("   and m.id = om.metric_id   ");
            sql.append("   and mt.id = m.metric_template_id) AS metricName4,   ");
            sql.append("(select mt.description from metric_template mt, metric m, observed_metric om    ");
            sql.append("   where ose.metric_id5 = om.id   ");
            sql.append("   and m.id = om.metric_id   ");
            sql.append("   and mt.id = m.metric_template_id) AS metricName5,   ");
            sql.append("(select mt.description from metric_template mt, metric m, observed_metric om    ");
            sql.append("   where ose.metric_id6 = om.id   ");
            sql.append("   and m.id = om.metric_id   ");
            sql.append("   and mt.id = m.metric_template_id) AS metricName6,   ");
            sql.append("(select mt.description from metric_template mt, metric m, observed_metric om    ");
            sql.append("   where ose.metric_id7 = om.id   ");
            sql.append("   and m.id = om.metric_id   ");
            sql.append("   and mt.id = m.metric_template_id) AS metricName7,   ");
            sql.append("(select mt.description from metric_template mt, metric m, observed_metric om    ");
            sql.append("   where ose.metric_id8 = om.id   ");
            sql.append("   and m.id = om.metric_id   ");
            sql.append("   and mt.id = m.metric_template_id) AS metricName8,   ");
            sql.append("(select mt.description from metric_template mt, metric m, observed_metric om    ");
            sql.append("   where ose.metric_id9 = om.id   ");
            sql.append("   and m.id = om.metric_id   ");
            sql.append("   and mt.id = m.metric_template_id) AS metricName9,   ");
            sql.append("(select mt.description from metric_template mt, metric m, observed_metric om    ");
            sql.append("   where ose.metric_id10 = om.id   ");
            sql.append("   and m.id = om.metric_id   ");
            sql.append("   and mt.id = m.metric_template_id) AS metricName10,   ");
            sql.append("ose.sent_tomis,  ");
            sql.append("ose.sent_tomisdate,  ");
            sql.append("ose.target,  ");
            sql.append("ose.standard,  ");
            sql.append("ose.metric_count,  ");
            sql.append("ose.department,  ");
            sql.append("ose.product,  ");
            sql.append("store.description AS store_name,  ");
            sql.append("ose.state,  ");
            sql.append("ose.evaluation_date  ");
            sql.append("FROM   ");
            sql.append("observed_situation_evaluation ose,   ");
            sql.append("observed_situation os,  ");
            sql.append("situation s,  ");
            sql.append("place store ");
            sql.append("WHERE  ");
            sql.append("ose.store_id = store.id  ");
            sql.append("AND ose.observed_situation_id = os.id  ");
            sql.append("AND os.situation_id = s.id  ");
            sql.append("AND os.observed_situation_date >= to_date('").append(sdf.format(startDate)).append("', 'YYYY-MM-DD')  ");
            sql.append("AND os.observed_situation_date <= to_date('").append(sdf.format(endDate)).append("', 'YYYY-MM-DD')  ");
            sql.append("AND s.situation_template_id in (").append(idSTs).append(") ");
            sql.append("AND store.id in (").append(idStore).append(") ");
            sql.append("ORDER BY os.observed_situation_date, s.description ASC ");

            session = this.getSession();

            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();

            for (Map m : list) {
                ObservedSituationEvaluationDTO dto = new ObservedSituationEvaluationDTO();
                dto.setSituation((String) m.get("situation"));
                dto.setDate((Date) m.get("observed_situation_date"));
                dto.setEvaluationResult((Double) m.get("evaluation_result"));
                dto.setCompliant((Integer) m.get("compliant"));
                dto.setRuleName((String) m.get("rule_name"));
                dto.setMetric1((Double) m.get("metric1"));
                dto.setMetricName1((String) m.get("metricname1"));
                dto.setMetric2((Double) m.get("metric2"));
                dto.setMetricName2((String) m.get("metricname2"));
                dto.setMetric3((Double) m.get("metric3"));
                dto.setMetricName3((String) m.get("metricname3"));
                dto.setMetric4((Double) m.get("metric4"));
                dto.setMetricName4((String) m.get("metricname4"));
                dto.setMetric5((Double) m.get("metric5"));
                dto.setMetricName5((String) m.get("metricname5"));
                dto.setMetric6((Double) m.get("metric6"));
                dto.setMetricName6((String) m.get("metricname6"));
                dto.setMetric7((Double) m.get("metric7"));
                dto.setMetricName7((String) m.get("metricname7"));
                dto.setMetric8((Double) m.get("metric8"));
                dto.setMetricName8((String) m.get("metricname8"));
                dto.setMetric9((Double) m.get("metric9"));
                dto.setMetricName9((String) m.get("metricname9"));
                dto.setMetric10((Double) m.get("metric10"));
                dto.setMetricName10((String) m.get("metricname10"));
                dto.setSentToMIS((Boolean) m.get("sent_tomis"));
                dto.setSentToMISDate((Date) m.get("sent_tomisdate"));
                dto.setTarget((Double) m.get("target"));
                dto.setStandard((Double) m.get("standard"));
                dto.setMetricCount((Integer) m.get("metric_count"));
                dto.setDepartment((String) m.get("department"));
                dto.setProduct((String) m.get("product"));
                dto.setStoreName((String) m.get("store_name"));
                dto.setState((String) m.get("state"));
                dto.setEvaluationDate((Date) m.get("evaluation_date"));
                dtos.add(dto);
            }

        } catch (Exception ex) {
            log.error("error = " + ex.getMessage());
        } finally {
            if (session != null) {
                this.releaseSession(session);
            }
        }
        return dtos;
    }

    @Override
    public List<IndicatorValuesDTO> getIndicatorValuesDTOs(Date startDate, Date endDate, List<Integer> situationTemplateIds,
            List<Integer> storeIds) {
        List<IndicatorValuesDTO> dtos = new ArrayList<IndicatorValuesDTO>();
        Session session = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String idSTs = situationTemplateIds.toString().replaceAll("(\\[)|(\\])", "");
            String idStore = storeIds.toString().replaceAll("(\\[)|(\\])", "");
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT DISTINCT ");
            sql.append("s.description AS situation, ");
            sql.append("os.observed_situation_date, ");
            sql.append("i.name AS indicator_name, ");
            sql.append("iv.denominator, ");
            sql.append("iv.numerator, ");
            sql.append("iv.state, ");
            sql.append("iv.sent_tomis, ");
            sql.append("iv.sent_tomisdate, ");
            sql.append("store.description AS store_name, ");
            sql.append("iv.evaluation_date ");
            sql.append("FROM  ");
            sql.append("indicator_values iv, ");
            sql.append("indicator i, ");
            sql.append("place store, ");
            sql.append("observed_situation os, ");
            sql.append("situation s ");
            sql.append("WHERE ");
            sql.append("iv.observed_situation_id = os.id ");
            sql.append("AND s.id = os.situation_id ");
            sql.append("AND store.id = iv.store_id ");
            sql.append("AND i.id = iv.indicator_id ");
            sql.append("AND os.observed_situation_date >= to_date('").append(sdf.format(startDate)).append("', 'YYYY-MM-DD') ");
            sql.append("AND os.observed_situation_date <= to_date('").append(sdf.format(endDate)).append("', 'YYYY-MM-DD') ");
            sql.append("AND s.situation_template_id in (").append(idSTs).append(") ");
            sql.append("AND store.id in (").append(idStore).append(") ");
            sql.append("ORDER BY os.observed_situation_date, s.description ASC ");

            session = this.getSession();

            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();

            for (Map m : list) {
                IndicatorValuesDTO dto = new IndicatorValuesDTO();
                dto.setSituation((String) m.get("situation"));
                dto.setDate((Date) m.get("observed_situation_date"));
                dto.setDenominator((Double) m.get("denominator"));
                dto.setEvaluationDate((Date) m.get("evaluation_date"));
                dto.setIndicatorName((String) m.get("indicator_name"));
                dto.setNumerator((Double) m.get("numerator"));
                dto.setSentToMIS((Boolean) m.get("sent_tomis"));
                dto.setSentToMISDate((Date) m.get("sent_tomisdate"));
                dto.setState((String) m.get("state"));
                dto.setStoreName((String) m.get("store_name"));
                dtos.add(dto);
            }
        } catch (Exception ex) {
            log.error("error = " + ex.getMessage());
        } finally {
            if (session != null) {
                this.releaseSession(session);
            }
        }
        return dtos;

    }

    @Override
    public List<Formula> getFormulaList(Formula filter) {
        Session session = this.getSession();
        List<Formula> formulas = null;
        try {
            Criteria criteria = session.createCriteria(Formula.class);
            criteria.addOrder(Order.asc("description"));
            if (filter != null) {
                if (filter.getDescription() != null && filter.getDescription().length() > 0) {
                    criteria.add(Restrictions.ilike("description", filter.getDescription(), MatchMode.ANYWHERE));
                }
                if (filter.getSituationTemplates() != null && !filter.getSituationTemplates().isEmpty()) {
                    List<Integer> stIds = new ArrayList<Integer>();
                    for (SituationTemplate st : filter.getSituationTemplates()) {
                        stIds.add(st.getId());
                    }
                    criteria.createCriteria("situationTemplates").add(Restrictions.in("id", stIds));
                }
                if (filter.getStores() != null && !filter.getStores().isEmpty()) {
                    List<Integer> sIds = new ArrayList<Integer>();
                    for (Store s : filter.getStores()) {
                        sIds.add(s.getId());
                    }
                    criteria.createCriteria("stores").add(Restrictions.in("id", sIds));
                }
                if (filter.getType() != null) {
                    criteria.add(Restrictions.eq("type", filter.getType()));
                }
                if (filter.getCompliantType() != null) {
                    criteria.add(Restrictions.eq("compliantType", filter.getCompliantType()));
                }
            }
            formulas = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return formulas;
    }

    @Override
    public List<Integer> getObservedSituationListSQL(int storeId, Date observedSituationDate, Integer[] situationTemplateIds) {
        //List<ObservedSituation> observedSituations = new ArrayList<ObservedSituation>();
        Session session = null;
        List<Integer> l = null;
        String situationTemplates = StringUtils.join(situationTemplateIds, ",");
        try {
            String patterns = "yyyy-MM-dd";
            StringBuilder sql = new StringBuilder();
            sql.append("select distinct os.id, os.evidence_date ");
            sql.append(" FROM place store, metric m, observed_metric om, situation s, ");
            sql.append(" observed_situation os LEFT JOIN pending_evaluation pe ON os.id = pe.observed_situation_id ");
            sql.append("WHERE ");
            sql.append(" pe.observed_situation_id IS NULL ");
            sql.append(" and os.observed_situation_date  = '");
            sql.append(DateFormatUtils.format(observedSituationDate, patterns)).append("' ");
            sql.append(" and om.observed_situation_id = os.id");
            sql.append(" and m.id = om.metric_id ");
            sql.append(" and store.id = m.store_id ");
            sql.append(" and s.id = os.situation_id");
            if (situationTemplates != null && situationTemplates.length() > 0) {
                sql.append(" and s.situation_template_id in(").append(situationTemplates).append(")");
            }
            sql.append(" and store.id = ").append(storeId).append("");
            sql.append(" order by os.evidence_date");
            session = this.getSession();

            Query query = session.createSQLQuery(sql.toString());

            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();
            l = new ArrayList<Integer>();
            for (Map m : list) {
                Integer osId = (Integer) m.get("id");
                l.add(osId);
            }

            //l = (List<Integer>) query.list();
        } catch (Exception ex) {
            log.error("error = " + ex.getMessage());
        } finally {
            if (session != null) {
                this.releaseSession(session);
            }
        }
        //return observedSituations;
        return l;

    }
}
