/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * PendingEvaluationHibernateDAOImpl.java
 *
 * Created on 14-05-2008, 04:44:21 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.dao;

import com.scopix.periscope.evaluationmanagement.EvaluationQueue;
import com.scopix.periscope.evaluationmanagement.EvaluationState;
import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.dao.DataAccessResourceFailureException;

/**
 *
 * @author Nelson Rubio
 * @version 2.0.0
 */
@SpringBean(rootClass = PendingEvaluationHibernateDAOImpl.class)
public class PendingEvaluationHibernateDAOImpl extends DAOHibernate<PendingEvaluation, Integer> implements PendingEvaluationHibernateDAO {

    private static Logger log = Logger.getLogger(PendingEvaluationHibernateDAOImpl.class);

    /**
     * Busca un Pending Evaluacion para un observedSituation asociado
     *
     * @param observedSituationId id observedsituation
     * @return PendingEvaluation
     */
    @Override
    public PendingEvaluation getPendingEvaluationFromObservedSituationId(Integer observedSituationId) {
        log.info("start [observedSituationId:" + observedSituationId + "]");
        Session session = this.getSession();
        PendingEvaluation pe = null;
        try {
            Criteria criteria = session.createCriteria(PendingEvaluation.class);
            criteria.add(Restrictions.eq("observedSituation.id", observedSituationId));
            List<PendingEvaluation> lista = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            pe = null;
            if (!lista.isEmpty() && lista.size() > 0) {
                log.debug("lista size " + lista.size());
                //recuperamos el primero retornado por la lista para ser devuelto
                pe = lista.get(0);
            }
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        log.info("end " + pe);
        return pe;
    }

//    @Deprecated
//    public List<PendingEvaluation> findPendingEvaluationList(PendingEvaluation pendingEvaluation) {
//        List<PendingEvaluation> pendingEvaluations = null;
//        Session session = null;
//        try {
//            session = this.getSession();
//            Criteria criteria = session.createCriteria(PendingEvaluation.class);
//            criteria.addOrder(Order.asc("priority"));
//            if (pendingEvaluation != null) {
//                if (pendingEvaluation.getEvaluationQueue() != null) {
//                    criteria.add(Restrictions.eq("evaluationQueue", pendingEvaluation.getEvaluationQueue()));
//                }
//                if (pendingEvaluation.getEvaluationState() != null) {
//                    criteria.add(Restrictions.eq("evaluationState", pendingEvaluation.getEvaluationState()));
//                }
//                if (pendingEvaluation.getObservedSituation() != null && pendingEvaluation.getObservedSituation().
//                        getId() != null) {
//                    criteria.add(Restrictions.eq("observedSituation.id", pendingEvaluation.getObservedSituation().getId()));
//                }
//            }
//            pendingEvaluations = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
//        } finally {
//            this.releaseSession(session);
//        }
//        log.debug("pendingEvaluations = "
//                + (pendingEvaluations != null ? pendingEvaluations.size() : null));
//        return pendingEvaluations;
//    }
//
    @Deprecated
    @Override
    public List<PendingEvaluation> findPendingEvaluationListSQL(PendingEvaluation pendingEvaluation) {
        List<PendingEvaluation> pendingEvaluations = new ArrayList<PendingEvaluation>();
        Session session = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT pe.id, pe.evaluation_queue, pe.evaluation_state, pe.observed_situation_id, pe.priority FROM ");
            sql.append("pending_evaluation pe ");
            StringBuilder where = new StringBuilder();
            if (pendingEvaluation != null) {
                if (pendingEvaluation.getEvaluationQueue() != null) {
                    where.append(" WHERE pe.evaluation_queue = '").append(pendingEvaluation.getEvaluationQueue().name());
                    where.append("'");
                }
                if (pendingEvaluation.getEvaluationState() != null) {
                    if (where.length() == 0) {
                        where.append(" WHERE");
                    } else {
                        where.append(" AND");
                    }
                    where.append(" pe.evaluation_state = '");
                    where.append(pendingEvaluation.getEvaluationState().name()).append("'");
                }
                if (pendingEvaluation.getObservedSituation() != null && pendingEvaluation.getObservedSituation().
                        getId() != null) {
                    if (where.length() == 0) {
                        where.append(" WHERE");
                    } else {
                        where.append(" AND");
                    }
                    where.append(" pe.observed_situation_id = ");
                    where.append(pendingEvaluation.getObservedSituation().getId());
                }
            }
            sql.append(where).append(" ORDER BY pe.priority");
            session = this.getSession();

            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();

            int i = 1;
            StringBuilder updateBatch = new StringBuilder();
            for (Map m : list) {
                PendingEvaluation pe = new PendingEvaluation();
                pe.setId((Integer) m.get("id"));
                pe.setEvaluationQueue(EvaluationQueue.valueOf((String) m.get("evaluation_queue")));
                pe.setEvaluationState(EvaluationState.valueOf((String) m.get("evaluation_state")));
                ObservedSituation os = new ObservedSituation();
                os.setId((Integer) m.get("observed_situation_id"));
                pe.setObservedSituation(os);
                pe.setPriority(i);
                pendingEvaluations.add(pe);

                updateBatch.append("UPDATE pending_evaluation set priority = ").
                        append(i).
                        append(" WHERE id = ").
                        append(pe.getId()).
                        append(";");
                i++;
            }

            if (updateBatch.length() > 0) {
                session.createSQLQuery(updateBatch.toString()).executeUpdate();
            }
        } finally {
            if (session != null) {
                this.releaseSession(session);
            }
        }
        log.debug("pendingEvaluations = " + (pendingEvaluations != null ? pendingEvaluations.size()
                : null));
        return pendingEvaluations;
    }

    @Override
    public Integer getCountPendingEvaluationFromObservedSituationId(Integer observedSituationId) {
        log.info("start");
        Integer count = 0;
        Session session = this.getSession();
        try {
            String sql = "select count(1) as cnt from pending_evaluation where observed_situation_id = " + observedSituationId;
            Query query = session.createSQLQuery(sql.toString()).addScalar("cnt", Hibernate.INTEGER);
            count = (Integer) query.uniqueResult();
        } catch (DataAccessResourceFailureException e) {
            log.error(e, e);
        } catch (IllegalStateException e) {
            log.error(e, e);
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        log.info("end " + count);
        return count;
    }
}
