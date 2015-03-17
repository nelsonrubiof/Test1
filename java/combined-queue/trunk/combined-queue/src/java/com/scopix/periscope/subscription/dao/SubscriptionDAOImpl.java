/*
 * 
 * Copyright � 2013, SCOPIX. All rights reserved.
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
 *
 */
package com.scopix.periscope.subscription.dao;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.subscription.Subscription;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Sebastian
 */
@SpringBean(rootClass = SubscriptionDAO.class)
public class SubscriptionDAOImpl extends DAOHibernate<BusinessObject, Integer> implements SubscriptionDAO {

    private Logger log = Logger.getLogger(SubscriptionDAO.class);

    @Override
    public List<Subscription> getAllByUserNameAndCorporateIdAsc() {

        log.info("executing getAllByUserNameAndCorporateIdAsc()");
        List<Subscription> list;
        Criteria criteria = this.getSession().createCriteria(Subscription.class);
        criteria.addOrder(Order.asc("userName"));
        criteria.createAlias("operatorQueuePriority", "oqp");
        criteria.addOrder(Order.asc("oqp.priority"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        list = criteria.list();
        log.info("end()");
        return list;
    }

    @Override
    public List<Subscription> getForUserNameSortedByCorporateIdAsc(String userName) {
        log.info("executing getAllByUserNameAndCorporateIdAsc()");
        StringBuilder sql = new StringBuilder();
        sql.append("select DISTINCT sub.id as id, sub.operatorsGroupId as operatorsGroupId, sub.operatorQueuePriority as operatorQueuePriority");
        sql.append(" from Subscription sub, OperatorsGroup og join og.users user ");
        sql.append(" where ((sub.userName = '");
        sql.append(userName);
        sql.append("'");
        sql.append(")or ( sub.operatorsGroupId = og.id and user.userName ='");
        sql.append(userName);
        sql.append("'))");
        sql.append(" order by sub.operatorQueuePriority.priority asc");
        Session s = this.getSession();
        log.info("end()");
        Query query = s.createQuery(sql.toString());
        List<Subscription> sub = query.setResultTransformer(Transformers.aliasToBean(Subscription.class)).list();
        return sub;
    }

    @Override
    public List<Subscription> getAllByGroupNameAndCorporateIdAsc(String groupName) {
        StringBuilder sql = new StringBuilder();
        sql.append("select sub.id as id, sub.operatorsGroupId as operatorsGroupId, sub.operatorQueuePriority as operatorQueuePriority");
        sql.append(" from Subscription sub, OperatorsGroup og ");
        sql.append(" where sub.operatorsGroupId =og.id and og.groupName = '");
        sql.append(groupName);
        sql.append("'");
        sql.append(" order by sub.operatorQueuePriority.priority asc, og.groupName asc");
        Session s = this.getSession();
        log.info("end()");
        Query query = s.createQuery(sql.toString());
        List<Subscription> sub = query.setResultTransformer(Transformers.aliasToBean(Subscription.class)).list();
        return sub;
    }

    @Override
    public void deleteByGroupId(Integer operatorsGroupId) {
        log.info("executing deleteByGroupId()");
        Query query = this.getSession().createQuery("delete Subscription where operatorsGroupId = :operatorsGroupId");
        query.setParameter("operatorsGroupId", operatorsGroupId);
        query.executeUpdate();
    }
}
