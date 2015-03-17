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
 * TransferHibernateDAO.java
 *
 * Created on 16-06-2008, 01:53:46 PM
 *
 */
package com.scopix.periscope.extractionplanmanagement.dao;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanMetric;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRangeDetail;
import com.scopix.periscope.businesswarehouse.transfer.commands.RejectBWCommand;
import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.Indicator;
import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.extractionplanmanagement.Situation;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import com.scopix.periscope.templatemanagement.Product;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@SpringBean(rootClass = ExtractionPlanCustomizingDAOImpl.class)
public class ExtractionPlanCustomizingDAOImpl extends DAOHibernate<BusinessObject, Integer>
        implements ExtractionPlanCustomizingDAO {

    private Logger log = Logger.getLogger(ExtractionPlanCustomizingDAOImpl.class);

    /**
     * Retorna una lista ExtractionPlanCustomizing unicos que esten activos o nulos
     */
    @Override
    public List<ExtractionPlanCustomizing> getExtractionPlanCustomizingActiveOrNullList(ExtractionPlanCustomizing epcFilter) {
        List<ExtractionPlanCustomizing> result = new ArrayList<ExtractionPlanCustomizing>();
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(ExtractionPlanCustomizing.class);
            //criteria.addOrder(Order.asc("evaluationOrder"));
            if (epcFilter != null) {
                if (epcFilter.getSituationTemplate() != null
                        && epcFilter.getSituationTemplate().getId() != null
                        && epcFilter.getSituationTemplate().getId() > 0) {
                    criteria.add(Restrictions.eq("situationTemplate.id", epcFilter.getSituationTemplate().getId()));
                }

                if (epcFilter.getStore() != null && epcFilter.getStore().getId() != null
                        && epcFilter.getStore().getId() > 0) {
                    criteria.add(Restrictions.eq("store.id", epcFilter.getStore().getId()));
                }
            }

            criteria.add(Restrictions.or(Restrictions.eq("active", true), Restrictions.isNull("active")));

            result = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return result;
    }

    /**
     * Retorna una lista ExtractionPlanCustomizing unicos que esten activos para un store, stituation template si llegan
     */
    @Override
    public List<ExtractionPlanCustomizing> getExtractionPlanCustomizingActiveList(ExtractionPlanCustomizing epcFilter) {
        List<ExtractionPlanCustomizing> result = new ArrayList<ExtractionPlanCustomizing>();
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(ExtractionPlanCustomizing.class);
            //criteria.addOrder(Order.asc("evaluationOrder"));
            if (epcFilter != null) {
                if (epcFilter.getSituationTemplate() != null
                        && epcFilter.getSituationTemplate().getId() != null
                        && epcFilter.getSituationTemplate().getId() > 0) {
                    criteria.add(Restrictions.eq("situationTemplate.id", epcFilter.getSituationTemplate().getId()));
                }

                if (epcFilter.getStore() != null && epcFilter.getStore().getId() != null
                        && epcFilter.getStore().getId() > 0) {
                    criteria.add(Restrictions.eq("store.id", epcFilter.getStore().getId()));
                }
            }

            criteria.add(Restrictions.eq("active", true));
            result = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return result;
    }

    /**
     * Retorna una lista ExtractionPlanCustomizing unicos que esten null para un store, stituation template si llegan
     */
    @Override
    public List<ExtractionPlanCustomizing> getExtractionPlanCustomizingNullList(ExtractionPlanCustomizing epcFilter) {
        List<ExtractionPlanCustomizing> result = new ArrayList<ExtractionPlanCustomizing>();
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(ExtractionPlanCustomizing.class);
            //criteria.addOrder(Order.asc("evaluationOrder"));
            if (epcFilter != null) {
                if (epcFilter.getSituationTemplate() != null
                        && epcFilter.getSituationTemplate().getId() != null
                        && epcFilter.getSituationTemplate().getId() > 0) {
                    criteria.add(Restrictions.eq("situationTemplate.id", epcFilter.getSituationTemplate().getId()));
                }

                if (epcFilter.getStore() != null && epcFilter.getStore().getId() != null
                        && epcFilter.getStore().getId() > 0) {
                    criteria.add(Restrictions.eq("store.id", epcFilter.getStore().getId()));
                }
            }

            criteria.add(Restrictions.isNull("active"));
            result = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return result;
    }

    @Override
    public List<ExtractionPlanCustomizing> getExtractionPlanCustomizingNullListByIds(List<Integer> epcIds) {
        List<ExtractionPlanCustomizing> result = new ArrayList<ExtractionPlanCustomizing>();
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(ExtractionPlanCustomizing.class);
            if (epcIds != null && !epcIds.isEmpty()) {
                criteria.add(Restrictions.in("id", epcIds));
            }

            criteria.add(Restrictions.isNull("active"));
            result = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return result;
    }

    @Override
    public List<Indicator> getIndicatorNameList(AreaType areaType, Product product) {
        List<Indicator> indicators = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(Indicator.class);
            criteria.addOrder(Order.asc("name"));
            if (areaType != null) {
                criteria.add(Restrictions.eq("areaType.id", areaType.getId()));
            }
            if (product != null) {
                criteria.add(Restrictions.eq("product.id", product.getId()));
            }
            indicators = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return indicators;
    }

    @Override
    public void updateOrder(ExtractionPlanMetric epm) throws ScopixException {
        log.info("start");
        Session session = null;
//        PreparedStatement pst = null;
//        Connection con = null;
        try {
            session = this.getSession();
//            con = session.connection();
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE metric SET metric_order = ");
            sql.append(epm.getEvaluationOrder());
            sql.append(" WHERE extraction_plan_metric_id = ");
            sql.append(epm.getId());

            Query query = session.createSQLQuery(sql.toString());
            query.executeUpdate();
//            pst = con.prepareStatement(sql);
//            pst.executeUpdate();
        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
            throw new ScopixException(e);
        } finally {
            try {
//                if (pst != null) {
//                    pst.close();
//                    pst = null;
//                }
//                if (con != null) {
//                    con.close();
//                    con = null;
//                }
                if (session != null) {
                    this.releaseSession(session);
                }
            } catch (Exception e) {
                log.error("Error " + e.getMessage(), e);
            }
        }
        log.info("end");
    }

    //CHECKSTYLE:OFF
    @Override
    public void reprocess(Date startDate, Date endDate, List<Integer> situationTemplateIds, List<Integer> storeIds) throws
            ScopixException {
        log.info("start");
        log.debug("startDate: " + startDate.toString());
        log.debug("endDate: " + endDate.toString());
        log.debug("stIds: " + situationTemplateIds.toString());
        log.debug("storeIds: " + storeIds.toString());
        Session session = null;
        List<Integer> indicatorValuesList = new ArrayList<Integer>();
        List<Integer> observedSituationEvaluationList = new ArrayList<Integer>();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String idSTs = situationTemplateIds.toString().replaceAll("(\\[)|(\\])", "");
            String idStore = storeIds.toString().replaceAll("(\\[)|(\\])", "");
            String init = sdf.format(startDate);
            String end = sdf.format(endDate);

            session = this.getSession();
            //select indicator values
            StringBuilder sql = new StringBuilder();
            sql.append("select id from indicator_values ");
            sql.append("where ");
            sql.append("observed_situation_id in  ");
            sql.append("( ");
            sql.append("        select distinct os.id  ");
            sql.append("        from observed_situation os, ");
            sql.append("        situation s, ");
            sql.append("        observed_metric om, ");
            sql.append("        metric m ");
            sql.append("        where ");
            sql.append("        os.situation_id = s.id ");
            sql.append("        and (os.evaluation_state = 'FINISHED' or os.evaluation_state = 'ERROR') ");
            sql.append("        and s.situation_template_id in (");
            sql.append(idSTs);
            sql.append(") ");
            sql.append("        and os.observed_situation_date >= '");
            sql.append(init);
            sql.append("' ");
            sql.append("        and os.observed_situation_date <= '");
            sql.append(end);
            sql.append("' ");
            sql.append("        and om.observed_situation_id = os.id ");
            sql.append("        and om.metric_id = m.id ");
            sql.append("        and m.store_id in (");
            sql.append(idStore);
            sql.append(") ");
            sql.append(")");

            Query query = session.createSQLQuery(sql.toString()).addScalar("id", Hibernate.INTEGER);
            List<Integer> l = query.list();
            for (Integer v : l) {
                indicatorValuesList.add(v);
            }

            //select observed situation evaluation
            sql = new StringBuilder();
            sql.append("select id from observed_situation_evaluation ");
            sql.append("where ");
            sql.append("observed_situation_id in  ");
            sql.append("( ");
            sql.append("        select distinct os.id  ");
            sql.append("        from observed_situation os, ");
            sql.append("        situation s, ");
            sql.append("        observed_metric om, ");
            sql.append("        metric m ");
            sql.append("        where ");
            sql.append("        os.situation_id = s.id ");
            sql.append("        and (os.evaluation_state = 'FINISHED' or os.evaluation_state = 'ERROR') ");
            sql.append("        and s.situation_template_id in (");
            sql.append(idSTs);
            sql.append(") ");
            sql.append("        and os.observed_situation_date >= '");
            sql.append(init);
            sql.append("' ");
            sql.append("        and os.observed_situation_date <= '");
            sql.append(end);
            sql.append("' ");
            sql.append("        and om.observed_situation_id = os.id ");
            sql.append("        and om.metric_id = m.id ");
            sql.append("        and m.store_id in (");
            sql.append(idStore);
            sql.append(") ");
            sql.append(")");

            query = session.createSQLQuery(sql.toString()).addScalar("id", Hibernate.INTEGER);
            List<Integer> list = query.list();

            for (Integer num : list) {
                observedSituationEvaluationList.add(num);
            }

            //Call RejectBWCommand
            RejectBWCommand bWCommand = new RejectBWCommand();
            bWCommand.execute(observedSituationEvaluationList, indicatorValuesList);

            //delete observed situation evaluation
            sql = new StringBuilder();
            sql.append("delete from observed_situation_evaluation where ");
            sql.append("observed_situation_id in  ");
            sql.append("( ");
            sql.append("        select distinct os.id  ");
            sql.append("        from observed_situation os, ");
            sql.append("        situation s, ");
            sql.append("        observed_metric om, ");
            sql.append("        metric m ");
            sql.append("        where ");
            sql.append("        os.situation_id = s.id ");
            sql.append("        and (os.evaluation_state = 'FINISHED' or os.evaluation_state = 'ERROR') ");
            sql.append("        and s.situation_template_id in (");
            sql.append(idSTs);
            sql.append(") ");
            sql.append("        and os.observed_situation_date >= '");
            sql.append(init);
            sql.append("' ");
            sql.append("        and os.observed_situation_date <= '");
            sql.append(end);
            sql.append("' ");
            sql.append("        and om.observed_situation_id = os.id ");
            sql.append("        and om.metric_id = m.id ");
            sql.append("        and m.store_id in (");
            sql.append(idStore);
            sql.append(") ");
            sql.append(")");

            query = session.createSQLQuery(sql.toString());
            query.executeUpdate();

            //delete indicator values
            sql = new StringBuilder();
            sql.append("delete from indicator_values where ");
            sql.append("observed_situation_id in  ");
            sql.append("( ");
            sql.append("        select distinct os.id  ");
            sql.append("        from observed_situation os, ");
            sql.append("        situation s, ");
            sql.append("        observed_metric om, ");
            sql.append("        metric m ");
            sql.append("        where ");
            sql.append("        os.situation_id = s.id ");
            sql.append("        and (os.evaluation_state = 'FINISHED' or os.evaluation_state = 'ERROR') ");
            sql.append("        and s.situation_template_id in (");
            sql.append(idSTs);
            sql.append(") ");
            sql.append("        and os.observed_situation_date >= '");
            sql.append(init);
            sql.append("' ");
            sql.append("        and os.observed_situation_date <= '");
            sql.append(end);
            sql.append("' ");
            sql.append("        and om.observed_situation_id = os.id ");
            sql.append("        and om.metric_id = m.id ");
            sql.append("        and m.store_id in (");
            sql.append(idStore);
            sql.append(") ");
            sql.append(")");

            query = session.createSQLQuery(sql.toString());
            query.executeUpdate();

            //update evaluation state = 'ENQUEUED' on observed situation
            sql = new StringBuilder();
            sql.append("update observed_situation set evaluation_state = 'ENQUEUED'");
            sql.append(" where id in ");
            sql.append("( ");
            sql.append("        select distinct os.id  ");
            sql.append("        from observed_situation os, ");
            sql.append("        situation s, ");
            sql.append("        observed_metric om, ");
            sql.append("        metric m ");
            sql.append("        where ");
            sql.append("        os.situation_id = s.id ");
            sql.append("        and (os.evaluation_state = 'FINISHED' or os.evaluation_state = 'ERROR') ");
            sql.append("        and s.situation_template_id in (");
            sql.append(idSTs);
            sql.append(") ");
            sql.append("        and os.observed_situation_date >= '");
            sql.append(init);
            sql.append("' ");
            sql.append("        and os.observed_situation_date <= '");
            sql.append(end);
            sql.append("' ");
            sql.append("        and om.observed_situation_id = os.id ");
            sql.append("        and om.metric_id = m.id ");
            sql.append("        and m.store_id in (");
            sql.append(idStore);
            sql.append(") ");
            sql.append(")");

            query = session.createSQLQuery(sql.toString());
            query.executeUpdate();
        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
            throw new ScopixException(e);
        } finally {
            try {
                if (session != null) {
                    this.releaseSession(session);
                }
            } catch (Exception e) {
                log.error("Error " + e.getMessage(), e);
            }
        }
        //CHECKSTYLE:ON

        log.info("end");
    }

    /**
     * retorna un ExtractionPlanCustomizing dado SituationTemplate, MetricTemplate, Store, EvidenceProvider Esto se genera por
     * cambio de generacion y extraccion del EPC
     */
    @Override
    public ExtractionPlanCustomizing getWizardCustomizing(ExtractionPlanCustomizing wizardCustomizing) {
        ExtractionPlanCustomizing extractionPlanCustomizing = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(ExtractionPlanCustomizing.class);
            //criteria.addOrder(Order.asc("evaluationOrder"));
            if (wizardCustomizing != null) {
                if (wizardCustomizing.getSituationTemplate() != null
                        && wizardCustomizing.getSituationTemplate().getId() != null
                        && wizardCustomizing.getSituationTemplate().getId() > 0) {
                    criteria.add(Restrictions.eq("situationTemplate.id", wizardCustomizing.getSituationTemplate().getId()));
                }
                //si existen metricas definidas
                if (!wizardCustomizing.getExtractionPlanMetrics().isEmpty()) {
                    MetricTemplate metricTemplate = wizardCustomizing.getExtractionPlanMetrics().get(0).getMetricTemplate();
                    if (metricTemplate != null && metricTemplate.getId() != null && metricTemplate.getId() > 0) {
                        criteria.add(Restrictions.eq("metricTemplate.id", metricTemplate.getId()));
                    }
                }

                if (wizardCustomizing.getStore() != null && wizardCustomizing.getStore().getId() != null
                        && wizardCustomizing.getStore().getId() > 0) {
                    criteria.add(Restrictions.eq("store.id", wizardCustomizing.getStore().getId()));
                }
            }
            criteria.add(Restrictions.or(Restrictions.eq("active", true), Restrictions.isNull("active")));
            List<ExtractionPlanCustomizing> l = criteria.setResultTransformer(
                    Criteria.DISTINCT_ROOT_ENTITY).list();
            if (l.size() > 1) {
                for (ExtractionPlanCustomizing epc : l) {
                    if (epc.isActive() == null) {
                        extractionPlanCustomizing = epc;
                        break;
                    }
                }
                log.debug("lista de epc > 1 " + l.size());
                //todo revisar que paso
            } else if (l.size() == 1) {
                extractionPlanCustomizing = l.get(0);
            }
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return extractionPlanCustomizing;
    }

    @Override
    public void cleanExtractionPlanMetrics(ExtractionPlanCustomizing customizing) throws ScopixException {
        log.info("start");

        //sql para eliminar la relacion con los provider
        Session session = this.getSession();
        StringBuilder sqlDeleteRelacion = new StringBuilder();
        sqlDeleteRelacion.append("delete from rel_extraction_plan_metric_evidence_provider where extraction_plan_metric_id in ");
        sqlDeleteRelacion.append(" (select id from extraction_plan_metric where extraction_plan_customizing_id = ");
        sqlDeleteRelacion.append(customizing.getId()).append(" )");

        StringBuilder hql = new StringBuilder("Delete from ExtractionPlanMetric where extractionPlanCustomizing.id = ");
        hql.append(customizing.getId());
        int rowAfected = 0;
        try {
            session.createSQLQuery(sqlDeleteRelacion.toString()).executeUpdate();
            rowAfected = this.getHibernateTemplate().bulkUpdate(hql.toString());
        } catch (IllegalStateException e) {
            log.error("error = " + e, e);
            throw new ScopixException("NO_DELETE_EPC_METRIC", e);
        } catch (HibernateException e) {
            log.error("error = " + e, e);
            throw new ScopixException("NO_DELETE_EPC_METRIC", e);
        } catch (DataAccessException e) {
            log.error("error = " + e, e);
            throw new ScopixException("NO_DELETE_EPC_METRIC", e);
        } finally {
            this.releaseSession(session);
        }

        log.info("end, borrados = " + rowAfected);
    }

    @Override
    public void cleanExtractionPlanRanges(ExtractionPlanCustomizing customizing) throws ScopixException {
        log.info("start");
        String hql = "Delete from ExtractionPlanRange where extractionPlanCustomizing.id = " + customizing.getId();
        int rowAfected = 0;
        try {
            rowAfected = this.getHibernateTemplate().bulkUpdate(hql);
        } catch (DataAccessException e) {
            log.error("error = " + e, e);
            throw new ScopixException("NO_DELETE_EPC_RANGES", e);
        }

        log.info("end, borrados = " + rowAfected);
    }

    /**
     * borra todos los detalles para un ExtractionPlanRange
     */
    @Override
    public void cleanDetailExtractionPlanRange(ExtractionPlanRange extractionPlanRange) throws ScopixException {
        log.info("start");
        String hql = "Delete from ExtractionPlanRangeDetail where extractionPlanRange.id = " + extractionPlanRange.getId();
        int rowAfected = 0;
        try {
            rowAfected = this.getHibernateTemplate().bulkUpdate(hql);
        } catch (DataAccessException e) {
            log.error("error = " + e, e);
            throw new ScopixException("NO_DELETE_EPC_RANGE_DETAILS", e);
        }

        log.info("end, borrados = " + rowAfected);
    }

    /**
     * Retorna un Extraction Plan Customizing ACTIVO dado un Situation Template y un Store
     */
    @Override
    public ExtractionPlanCustomizing getExtractionPlanCustomizing(SituationTemplate st, Store store) {
        ExtractionPlanCustomizing customizing = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(ExtractionPlanCustomizing.class);
            criteria.setMaxResults(1);
            criteria.add(Restrictions.eq("situationTemplate.id", st.getId()));
            criteria.add(Restrictions.eq("store.id", store.getId()));
            criteria.add(Restrictions.eq("active", true));
            customizing = (ExtractionPlanCustomizing) criteria.uniqueResult();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return customizing;
    }

    @Override
    public List<EvidenceRequest> getEvidenceRequestList(EvidenceRequest evidenceRequest, Date init, Date end) {
        List<EvidenceRequest> list = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(EvidenceRequest.class);
            criteria.addOrder(Order.asc("id"));
            if (evidenceRequest != null) {
                if (init != null && end != null) {
                    criteria.add(Restrictions.ge("evidenceTime", init));
                    criteria.add(Restrictions.le("evidenceTime", end));
                } else if (init != null) {
                    criteria.add(Restrictions.ge("evidenceTime", init));
                } else if (end != null) {
                    criteria.add(Restrictions.le("evidenceTime", end));
                } else if (evidenceRequest.getEvidenceTime() != null) {
                    criteria.add(Restrictions.eq("evidenceTime", evidenceRequest.getEvidenceTime()));
                }
                if (evidenceRequest.getType() != null) {
                    criteria.add(Restrictions.eq("type", evidenceRequest.getType()));
                }
                if (evidenceRequest.getEvidenceProvider() != null && evidenceRequest.getEvidenceProvider().getId() != null
                        && evidenceRequest.getEvidenceProvider().getId() > 0) {
                    criteria.add(Restrictions.eq("evidenceProvider.id", evidenceRequest.getEvidenceProvider().getId()));
                }

                ExtractionPlanMetric epm = evidenceRequest.getMetric().getExtractionPlanMetric();
                if (evidenceRequest.getMetric() != null
                        && epm != null
                        && epm.getExtractionPlanCustomizing() != null
                        && epm.getExtractionPlanCustomizing().isActive() != null) {
                    criteria.createCriteria("metric").createCriteria("extractionPlanCustomizing").add(Restrictions.eq("active",
                            epm.getExtractionPlanCustomizing().isActive()));
                }
            }
            list = criteria.list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return list;
    }

    @Override
    public List<EvidenceRequest> getFreeEvidenceRequestList(EvidenceRequest evidenceRequest, Date init, Date end) {
        List<EvidenceRequest> list = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(EvidenceRequest.class);
            criteria.addOrder(Order.asc("id"));
            if (evidenceRequest != null) {
                if (init != null && end != null) {
                    criteria.add(Restrictions.ge("evidenceTime", init));
                    criteria.add(Restrictions.le("evidenceTime", end));
                } else if (init != null) {
                    criteria.add(Restrictions.ge("evidenceTime", init));
                } else if (end != null) {
                    criteria.add(Restrictions.le("evidenceTime", end));
                } else if (evidenceRequest.getEvidenceTime() != null) {
                    criteria.add(Restrictions.eq("evidenceTime", evidenceRequest.getEvidenceTime()));
                }
                if (evidenceRequest.getType() != null) {
                    criteria.add(Restrictions.eq("type", evidenceRequest.getType()));
                }
                if (evidenceRequest.getEvidenceProvider() != null && evidenceRequest.getEvidenceProvider().getId() != null
                        && evidenceRequest.getEvidenceProvider().getId() > 0) {
                    criteria.add(Restrictions.eq("evidenceProvider.id", evidenceRequest.getEvidenceProvider().getId()));
                }
            }
            criteria.add(Restrictions.isNull("metric.id"));
            list = criteria.list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return list;
    }

    @Override
    public List<Metric> getMetricList(Metric metric) {
        log.info("start");
        List<Metric> metrics = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(Metric.class);
            criteria.addOrder(Order.asc("id"));
            if (metric != null) {
                registerLogs(metric);

                if (metric.getArea() != null && metric.getArea().getId() != null && metric.getArea().getId() > 0) {
                    log.debug("criteria metric.getArea().getId(): [" + metric.getArea().getId() + "]");
                    criteria.add(Restrictions.eq("area.id", metric.getArea().getId()));
                }
                if (metric.getMetricOrder() != null && metric.getMetricOrder() >= 0) {
                    log.debug("criteria metric.getMetricOrder(): [" + metric.getMetricOrder() + "]");
                    criteria.add(Restrictions.eq("metricOrder", metric.getMetricOrder()));
                }
                if (metric.getMetricTemplate() != null && metric.getMetricTemplate().getId() != null && metric.getMetricTemplate().
                        getId() > 0) {
                    log.debug("criteria metric.getMetricTemplate().getId(): [" + metric.getMetricTemplate().getId() + "]");
                    criteria.add(Restrictions.eq("metricTemplate.id", metric.getMetricTemplate().getId()));
                }
                if (metric.getSituation() != null && metric.getSituation().getId() != null && metric.getSituation().getId() > 0) {
                    log.debug("criteria metric.getSituation().getId(): [" + metric.getSituation().getId() + "]");
                    criteria.add(Restrictions.eq("situation.id", metric.getSituation().getId()));
                }
                if (metric.getArea() != null
                        && metric.getArea().getAreaType() != null
                        && metric.getArea().getAreaType().getId() != null
                        && metric.getArea().getAreaType().getId() > 0) {
                    log.debug("criteria metric.getArea().getAreaType().getId(): [" + metric.getArea().getAreaType().getId() + "]");
                    criteria.createCriteria("area").add(Restrictions.eq("areaType.id", metric.getArea().getAreaType().getId()));
                }
                ExtractionPlanMetric epm = metric.getExtractionPlanMetric();
                if (epm != null && epm.getExtractionPlanCustomizing().isActive() != null) {
                    log.debug("criteria epm.getExtractionPlanCustomizing().isActive(): ["
                            + epm.getExtractionPlanCustomizing().isActive() + "]");
                    criteria.createCriteria("extractionPlanCustomizing").add(Restrictions.eq("active",
                            epm.getExtractionPlanCustomizing().isActive()));
                }
            }
            log.debug("antes de criteria.list()");
            metrics = criteria.list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        log.info("end, metrics: [" + metrics + "]");
        return metrics;
    }

    /**
     * Registros de logs para diagnóstico de issue #1003, existen casos en donde no se retornan todas las observed metrics, se
     * agrupa en un try-catch en caso que algun nullPointerException no interfiera con el flujo
     *
     * @param observedMetric
     */
    private void registerLogs(Metric metric) {
        log.info("start, metricId: [" + metric.getId() + "], order: [" + metric.getMetricOrder() + "], "
                + "variableName: [" + metric.getMetricVariableName() + "], description: [" + metric.getDescription() + "]");
        try {
            Area area = metric.getArea();
            if (area != null) {
                log.debug("area.getId(): [" + area.getId() + "]");
                log.debug("area.getName(): [" + area.getName() + "]");
                log.debug("area.getDescription(): [" + area.getDescription() + "]");
            }
            log.debug("metric.getEvidenceRequests(): [" + metric.getEvidenceRequests() + "]");

            ExtractionPlanMetric exPlanMetric = metric.getExtractionPlanMetric();
            if (exPlanMetric != null) {
                log.debug("exPlanMetric.getId(): [" + exPlanMetric.getId() + "]");
            }

            MetricTemplate metricTemplate = metric.getMetricTemplate();
            if (metricTemplate != null) {
                log.debug("metricTemplate.getId(): [" + metricTemplate.getId() + "]");
                log.debug("metricTemplate.getName(): [" + metricTemplate.getName() + "]");
                log.debug("metricTemplate.getDescription(): [" + metricTemplate.getDescription() + "]");
            }
            log.debug("metric.getObservedMetrics(): [" + metric.getObservedMetrics() + "]");

            Situation situation = metric.getSituation();
            if (situation != null) {
                log.debug("situation.getId(): " + situation.getId());
                log.debug("situation.getDescription(): " + situation.getDescription());
            }

            Store store = metric.getStore();
            if (store != null) {
                log.debug("store.getId(): " + store.getId());
                log.debug("store.getName(): " + store.getName());
                log.debug("store.getDescription(): " + store.getDescription());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("end");
    }

    @Override
    public List<Metric> getFreeMetricList(Metric metric) {
        List<Metric> metrics = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(Metric.class);
            criteria.addOrder(Order.asc("id"));
            if (metric != null) {
                if (metric.getArea() != null && metric.getArea().getId() != null && metric.getArea().getId() > 0) {
                    criteria.add(Restrictions.eq("area.id", metric.getArea().getId()));
                }
                if (metric.getMetricOrder() != null && metric.getMetricOrder() >= 0) {
                    criteria.add(Restrictions.eq("metricOrder", metric.getMetricOrder()));
                }
                if (metric.getMetricTemplate() != null && metric.getMetricTemplate().getId() != null && metric.getMetricTemplate().
                        getId() > 0) {
                    criteria.add(Restrictions.eq("metricTemplate.id", metric.getMetricTemplate().getId()));
                }
                if (metric.getSituation() != null && metric.getSituation().getId() != null && metric.getSituation().getId() > 0) {
                    criteria.add(Restrictions.eq("metricTemplate.id", metric.getMetricTemplate().getId()));
                }
                if (metric.getSituation() != null && metric.getSituation().getSituationTemplate() != null && metric.getSituation().
                        getSituationTemplate().getId() != null && metric.getSituation().getSituationTemplate().getId() > 0) {
                    SituationTemplate st = HibernateSupport.getInstance().findGenericDAO().get(metric.getSituation().
                            getSituationTemplate().getId(), SituationTemplate.class);
                    List<Integer> ids = new ArrayList<Integer>();
                    for (MetricTemplate mt : st.getMetricTemplate()) {
                        ids.add(mt.getId());
                    }
                    criteria.add(Restrictions.in("metricTemplate.id", ids));
                }
            }
            criteria.add(Restrictions.isNull("situation"));
            metrics = criteria.list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return metrics;
    }

    @Override
    public List<Situation> getSituationList(Situation situation) {
        List<Situation> list = null;
        Session session = this.getSession();
        try {
            Criteria criteria = session.createCriteria(Situation.class);
            criteria.addOrder(Order.asc("id"));
            //Agregando un filtro fijo: situation que tengan asociadas metricas cuyo extraction plan customizing tenga estado activo
            Criteria criteriaMetric = criteria.createCriteria("metrics");
            Criteria criteriaExtraction = criteriaMetric.createCriteria("extractionPlanCustomizing");
            criteriaExtraction.add(Restrictions.eq("active", true));
            //**********************************************************************************************************************
            if (situation != null) {
                if (situation.getSituationTemplate() != null
                        && situation.getSituationTemplate().getId() != null
                        && situation.getSituationTemplate().getId() >= 0) {
                    criteria.add(Restrictions.eq("situationTemplate.id", situation.getSituationTemplate().getId()));
                }
            }
            list = criteria.list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return list;
    }

    @Override
    public List<ExtractionPlanRange> getExtractionPlanRanges(ExtractionPlanCustomizing extractionPlanCustomizing)
            throws ScopixException {
        if (extractionPlanCustomizing == null) {
            throw new ScopixException("PENDING revisar mensaje");
        }
        Session session = this.getSession();
        List<ExtractionPlanRange> planRanges = null;
        try {
            StringBuilder hql = new StringBuilder();
            hql.append("select epr.id as id, ");
            hql.append(" epr.initialTime as initialTime, epr.endTime as endTime, epr.samples as samples, ");
            hql.append(" epr.frecuency as frecuency, ");
            hql.append(" epr.duration as duration, epr.dayOfWeek as dayOfWeek, ");
            hql.append(" epr.extractionPlanRangeType as extractionPlanRangeType ");
            hql.append(" from ExtractionPlanRange epr ");
            hql.append(" where epr.extractionPlanCustomizing.id = ").append(extractionPlanCustomizing.getId());
            hql.append(" order by epr.dayOfWeek desc, epr.initialTime desc");
            planRanges = session.createQuery(hql.toString()).
                    setResultTransformer(Transformers.aliasToBean(ExtractionPlanRange.class)).list();

//        Criteria criteria = this.getSession().createCriteria(ExtractionPlanRange.class);
//        criteria.addOrder(Order.asc("dayOfWeek"));
//        criteria.addOrder(Order.asc("initialTime"));
//        criteria.add(Restrictions.eq("extractionPlanCustomizing.id", extractionPlanCustomizing.getId()));
//        planRanges = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return planRanges;
    }

    @Override
    public List<ExtractionPlanRangeDetail> getExtractionPlanRangeDetails(ExtractionPlanRange extractionPlanRange)
            throws ScopixException {
        if (extractionPlanRange == null) {
            throw new ScopixException("PENDING revisar mensaje");
        }
        Session session = this.getSession();
        List<ExtractionPlanRangeDetail> details = null;
        try {
            StringBuilder hql = new StringBuilder();
            hql.append("select eprd.id as id, eprd.timeSample as timeSample, eprd.extractionPlanRange as extractionPlanRange");
            hql.append(" from ExtractionPlanRangeDetail eprd ");
            hql.append(" where eprd.extractionPlanRange.id = ").append(extractionPlanRange.getId());
            hql.append(" order by eprd.timeSample");
            details = session.createQuery(hql.toString()).
                    setResultTransformer(Transformers.aliasToBean(ExtractionPlanRangeDetail.class)).list();
//        Criteria criteria = this.getSession().createCriteria(ExtractionPlanRangeDetail.class);
//        criteria.addOrder(Order.asc("timeSample"));
//        criteria.add(Restrictions.eq("extractionPlanRange.id", extractionPlanRange.getId()));
//        details = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return details;
    }

    @Override
    public int getCantidadDetailesForEpcStore(Integer epcId, Integer storeId) throws ScopixException {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) as cant");
        sql.append(" from   ");
        sql.append("  extraction_plan_customizing epc,");
        sql.append("  extraction_plan_range  epr, ");
        sql.append("  extraction_plan_range_detail eprd ");
        sql.append(" where ");
        sql.append("  eprd.extraction_plan_range_id = epr.id ");
        sql.append("  and epr.extraction_plan_customizing_id = epc.id ");
        sql.append("  and epc.active is null ");
        if (epcId != null && epcId.intValue() > 0) {
            sql.append(" and epc.id =  ").append(epcId.toString());
        }
        if (storeId != null && storeId.intValue() > 0) {
            sql.append("  and epc.store_id = ").append(storeId.toString());
        }
        Session s = this.getSession();
        BigInteger ret = null;
        try {
            Query query = s.createSQLQuery(sql.toString()).addScalar("cant", Hibernate.BIG_INTEGER);

            ret = (BigInteger) query.uniqueResult();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(s);
        }
        return ret.intValue();
    }

    @Override
    public int getCantidadDetailesForEpcStoreByIds(List<Integer> epcIds, Integer storeId) throws ScopixException {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) as cant");
        sql.append(" from   ");
        sql.append("  extraction_plan_customizing epc,");
        sql.append("  extraction_plan_range  epr, ");
        sql.append("  extraction_plan_range_detail eprd ");
        sql.append(" where ");
        sql.append("  eprd.extraction_plan_range_id = epr.id ");
        sql.append("  and epr.extraction_plan_customizing_id = epc.id ");
        sql.append("  and epc.active is null ");
        if (epcIds != null && !epcIds.isEmpty()) {
            sql.append(" and epc.id in (").append(StringUtils.join(epcIds, ",")).append(")");
        }
        if (storeId != null && storeId.intValue() > 0) {
            sql.append(" and epc.store_id = ").append(storeId.toString());
        }
        Session s = this.getSession();
        BigInteger ret = null;
        try {
            Query query = s.createSQLQuery(sql.toString()).addScalar("cant", Hibernate.BIG_INTEGER);

            ret = (BigInteger) query.uniqueResult();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(s);
        }
        return ret.intValue();
    }

    @Override
    public List<Integer> getAllEPCFromStoreSituatioTemplateEvidenceProvider(Integer storeId, Integer situationTemplateId,
            Integer evidenceProviderId) {
        List<Integer> ids = null;
        StringBuilder sql = new StringBuilder();
        sql.append(" select epc.id as id ");
        sql.append(" from extraction_plan_customizing epc, extraction_plan_metric epm, ");
        sql.append(" rel_extraction_plan_metric_evidence_provider rel ");
        sql.append(" where ");
        sql.append("  rel.extraction_plan_metric_id = epm.id and ");
        sql.append("  epm.extraction_plan_customizing_id = epc.id and ");
        sql.append("  epc.active = true ");
        if (storeId != null && storeId > 0) {
            sql.append("  and epc.store_id = ").append(storeId);
        }
        if (situationTemplateId != null && situationTemplateId > 0) {
            sql.append("  and epc.situation_template_id = ").append(situationTemplateId);
        }
        if (evidenceProviderId != null && evidenceProviderId > 0) {
            sql.append("  and rel.evidence_provider_id = ").append(evidenceProviderId);
        }
        Session session = this.getSession();
        try {
            ids = session.createSQLQuery(sql.toString()).addScalar("id", Hibernate.INTEGER).list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }

        return ids;
    }

    @Override
    public void disableExtractionPlanCustomizings(List<Integer> epcIds) {
        log.info("start");
        StringBuilder hql = new StringBuilder();
        hql.append("update ExtractionPlanCustomizing set active = false, disabled_date =now() where id in (");
        hql.append(StringUtils.join(epcIds.toArray(), ","));
        hql.append(")");

        int rowAfected = this.getHibernateTemplate().bulkUpdate(hql.toString());

        log.info("end. actualizados: " + rowAfected);
    }

    @Override
    public int createRecordsForEPC(ExtractionPlanCustomizing epc) {
        Session session = this.getSession();
        Integer cant = null;
        try {
            String sql = "select \"createRecordsForEPC\"(" + epc.getId() + ") as c";
            cant = (Integer) session.createSQLQuery(sql).addScalar("c", Hibernate.INTEGER).uniqueResult();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        log.debug("Insertados " + cant + " registros");
        return cant;
    }

    @Override
    public void cleanRangeDetailForExtractionPlanRanges(Set<Integer> idsRangeClear) throws ScopixException {
        log.info("start");
        String hql = "Delete from ExtractionPlanRangeDetail where extractionPlanRange.id in( "
                + StringUtils.join(idsRangeClear, ",") + ")";
        int rowAfected = 0;
        try {
            rowAfected = this.getHibernateTemplate().bulkUpdate(hql);
        } catch (DataAccessException e) {
            log.error("error = " + e, e);
            throw new ScopixException("NO_DELETE_EPC_RANGE_DETAILS", e);
        }

        log.info("end, borrados = " + rowAfected);
    }

    @Override
    public void cleanSensors(List<Integer> sensorIds) throws ScopixException {
        log.info("start");
        StringBuilder sql = new StringBuilder();
        sql.append("delete from rel_extraction_plan_customizing_sensor where sensor_id in (");
        sql.append(StringUtils.join(sensorIds, ","));
        sql.append(")");
        Session session = this.getSession();
        try {
            session.createSQLQuery(sql.toString()).executeUpdate();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        log.info("end");
    }
}
