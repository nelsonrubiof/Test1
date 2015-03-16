/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * OperatorQueueManagementHibernateDAO.java
 *
 * Created on 25-03-2010, 04:24:40 PM
 *
 */
package com.scopix.periscope.queuemanagement.dao;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.queuemanagement.OperatorQueue;
import com.scopix.periscope.queuemanagement.OperatorQueueDetail;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessResourceFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
@SpringBean(rootClass = OperatorQueueManagementHibernateDAO.class)
public class OperatorQueueManagementHibernateDAO extends DAOHibernate<OperatorQueue, Integer> {

    private Logger log = Logger.getLogger(OperatorQueueManagementHibernateDAO.class);

    public List<OperatorQueue> getOperatorQueuesList(OperatorQueue operatorQueue) throws ScopixException {
        log.debug("start");
        List<OperatorQueue> operatorQueues = null;

        try {
            Criteria criteria = this.getSession().createCriteria(OperatorQueue.class);
            criteria.addOrder(Order.asc("name"));
            criteria.add(Restrictions.eq("activo", Boolean.TRUE));

            if (operatorQueue != null) {
                if (operatorQueue.getName() != null && operatorQueue.getName().length() > 0) {
                    criteria.add(Restrictions.ilike("name", operatorQueue.getName(), MatchMode.ANYWHERE));
                }
                if (operatorQueue.getCreationDate() != null) {
                    criteria.add(Restrictions.eq("creationDate", operatorQueue.getCreationDate()));
                }
                if (operatorQueue.getModifiedDate() != null) {
                    criteria.add(Restrictions.eq("modifiedDate", operatorQueue.getModifiedDate()));
                }
            }

            operatorQueues = criteria.list();

        } catch (Exception ex) {
            log.debug("Error: ", ex);
            throw new ScopixException("operatorQueueManagement.error.list");
        }

        log.debug("end");
        return operatorQueues;

    }

    public List<OperatorQueueDetail> getOperatorQueuesDetailList(OperatorQueue operatorQueue) throws ScopixException {
        log.debug("start");
        List<OperatorQueueDetail> operatorQueuesDetails = null;

        try {
            Criteria criteria = this.getSession().createCriteria(OperatorQueueDetail.class);

            if (operatorQueue != null) {
                if (operatorQueue.getId() != null && operatorQueue.getId() > 0) {
                    criteria.add(Restrictions.eq("operatorQueue.id", operatorQueue.getId()));
                }
            }
            operatorQueuesDetails = criteria.list();

        } catch (Exception ex) {
            log.debug("Error: ", ex);
            throw new ScopixException("operatorQueueManagement.error.detail.list");
        }

        log.debug("end");
        return operatorQueuesDetails;

    }

    public List<OperatorQueueDetail> getOperatorQueuesDetailList(OperatorQueueDetail operatorQueueDetail)
        throws ScopixException {
        log.debug("start");
        List<OperatorQueueDetail> details = null;

        try {
            Criteria criteria = this.getSession().createCriteria(OperatorQueueDetail.class);

            if (operatorQueueDetail != null) {
                if (operatorQueueDetail.getStore().getId() != null && operatorQueueDetail.getStore().getId() > 0) {
                    criteria.add(Restrictions.eq("store.id", operatorQueueDetail.getStore().getId()));
                }
//                if (operatorQueueDetail.getAreaType().getId() != null && operatorQueueDetail.getAreaType().getId() > 0) {
//                    criteria.add(Restrictions.eq("areaType.id", operatorQueueDetail.getAreaType().getId()));
//                }
                if (operatorQueueDetail.getSituationTemplate().getId() != null && operatorQueueDetail.getSituationTemplate().getId() > 0) {
                    criteria.add(Restrictions.eq("situationTemplate.id", operatorQueueDetail.getSituationTemplate().getId()));
                }

                Criteria crit = criteria.createCriteria("operatorQueue");
                crit.add(Restrictions.eq("activo", Boolean.TRUE));
            }

            details = criteria.list();

        } catch (Exception ex) {
            log.debug("Error: ", ex);
            throw new ScopixException("operatorQueueManagement.error.detail.list");
        }

        log.debug("end");
        return details;
    }

    public List<OperatorQueue> getOperatorQueueByName(OperatorQueue operatorQueue) throws ScopixException {
        log.debug("start");
        List<OperatorQueue> operatorQueueList = null;

        try {
            Criteria criteria = this.getSession().createCriteria(OperatorQueue.class);
            criteria.addOrder(Order.asc("name"));
            criteria.add(Restrictions.eq("activo", Boolean.TRUE));

            if (operatorQueue != null) {
                if (operatorQueue.getName() != null && operatorQueue.getName().length() > 0) {
                    criteria.add(Restrictions.eq("name", operatorQueue.getName()));
                }
            }
            operatorQueueList = criteria.list();

        } catch (Exception ex) {
            log.debug("Error: ", ex);
            throw new ScopixException("operatorQueueManagement.error.getName");
        }

        log.debug("end");
        return operatorQueueList;

    }

    public void deleteOperatorQueueDetail(Integer operatorQueueId) throws ScopixException {
        log.debug("start");

        try {
            if (operatorQueueId != null && operatorQueueId > 0) {
                String sql = "delete from operator_queue_detail where operator_queue_id = " + operatorQueueId;
                this.getSession().createSQLQuery(sql).executeUpdate();
            } else {
                throw new ScopixException();
            }

        } catch (Exception ex) {
            log.debug("Error: ", ex);
            throw new ScopixException("operatorQueueManagement.error.deleteQueueDetail");
        }

        log.debug("end");

    }

    public void updatePendingEvaluation(OperatorQueue operatorQueue) throws ScopixException {
        log.debug("start");
        try {
            if (operatorQueue != null && operatorQueue.getId() != null && operatorQueue.getId() > 0) {
                this.getSession().createSQLQuery("update pending_evaluation set operator_queue_id = null "
                    + "where operator_queue_id = " + operatorQueue.getId()).executeUpdate();
            } else {
                throw new ScopixException();
            }
        } catch (Exception ex) {
            log.debug("Error: ", ex);
            throw new ScopixException("operatorQueueManagement.error.updatePendingEvaluation");
        }
        log.debug("end");
    }

    public void changePendingEvaluationWhenCreateQueue(OperatorQueue queue) {
        log.debug("start");
        if (queue != null) {
            Integer queueId = queue.getId();
            Session session = null;
            for (OperatorQueueDetail det : queue.getOperatorQueueDetailList()) {
                try {
                    Integer storeId = det.getStore().getId();
                    Integer situationTemplateId = det.getSituationTemplate().getId();
                    StringBuilder sql = new StringBuilder();
                    sql.append(" UPDATE pending_evaluation set operator_queue_id = ").append(queueId);
                    sql.append(" where id in (");
                    sql.append("select distinct pe.id ");
                    sql.append(" from pending_evaluation pe, observed_situation os, situation s, observed_metric om, metric m");
                    sql.append(" where pe.evaluation_queue = 'OPERATOR' ");
                    sql.append(" AND pe.evaluation_state = 'ENQUEUED' ");
                    sql.append(" and os.id = pe.observed_situation_id ");
                    sql.append(" and os.observed_situation_date >= (now() - interval '1 month') ");
                    sql.append(" and s.id = os.situation_id ");
                    sql.append(" and s.situation_template_id = ").append(situationTemplateId).append(" ");
                    sql.append(" and om.observed_situation_id = os.id ");
                    sql.append(" and m.id = om.metric_id ");
                    sql.append(" and m.store_id = ").append(storeId).append(" ");
                    sql.append(" ) AND (operator_queue_id is null OR operator_queue_id <> -1) ");
                    this.getSession().createSQLQuery(sql.toString()).executeUpdate();
                } catch (IllegalStateException e) {
                    log.error("Error when try to add pending evaluations to queue", e);
                } catch (HibernateException e) {
                    log.error("Error when try to add pending evaluations to queue", e);
                } catch (DataAccessResourceFailureException e) {
                    log.error("Error when try to add pending evaluations to queue", e);
                } finally {
                    try {
                        this.releaseSession(session);
                    } catch (Exception e) {
                        log.error("Error " + e.getMessage(), e);
                    }
                }
            }
        }
        log.debug("end");
    }

    public void moveToQueue(List<Integer> ids, Integer toQeueue) {
        log.debug("start");
        Session session = null;

        try {
            this.getSession().createSQLQuery("UPDATE pending_evaluation set operator_queue_id = "
                + toQeueue
                + "where id in (" + ids.toString().replaceAll("(\\[)|(\\])", "") + ") ").executeUpdate();
        } catch (DataAccessResourceFailureException e) {
            log.error("Error when try to add pending evaluations to queue", e);
        } catch (IllegalStateException e) {
            log.error("Error when try to add pending evaluations to queue", e);
        } catch (HibernateException e) {
            log.error("Error when try to add pending evaluations to queue", e);
        } finally {
            try {
                this.releaseSession(session);
            } catch (Exception e) {
                log.error("Error " + e.getMessage(), e);
            }
        }
        log.debug("end");
    }
}
