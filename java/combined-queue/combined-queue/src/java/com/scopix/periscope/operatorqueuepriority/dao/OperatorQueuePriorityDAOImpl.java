/* 
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
package com.scopix.periscope.operatorqueuepriority.dao;

import com.scopix.periscope.operatorqueuepriority.OperatorQueuePriority;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Sebastian
 */

@SpringBean(rootClass = OperatorQueuePriorityDAO.class)
public class OperatorQueuePriorityDAOImpl extends DAOHibernate<BusinessObject, Integer> implements OperatorQueuePriorityDAO  {
    private Logger log = Logger.getLogger(OperatorQueuePriorityDAO.class);

        
    /**
    * List objects of type OperatorQueuePriority sorted by Corporate Name
    * @return List<OperatorQueuePriority>
    */
    @Override
    public List<OperatorQueuePriority> listByCorporateName() {
        log.info("executing listByCorporateName()");
        List<OperatorQueuePriority> list;  
        Criteria criteria = this.getSession().createCriteria(OperatorQueuePriority.class); 
        criteria.addOrder(Order.asc("corporateName"));
        criteria.addOrder(Order.asc("priority"));
        criteria.setFetchMode("userSubscriptions", FetchMode.SELECT);
        list=criteria.list();
        log.info("end)");
        return list;           
    }
        
    /**
     * finds a OperatorQueuePriority by corporate Id and Operator Queue Name
     * @param corporateId
     * @param operatorQueueName
     * @return OperatorQueuePriority
     */
    @Override
    public OperatorQueuePriority getByCorporateIdAndOperatorQueueName(Integer corporateId, String operatorQueueName) {
        Criteria criteria = this.getSession().createCriteria(OperatorQueuePriority.class);
        criteria.setMaxResults(1);
        criteria.add(Restrictions.eq("corporateId", corporateId));
        criteria.add(Restrictions.eq("operatorQueueName", operatorQueueName));
        criteria.setFetchMode("userSubscriptions", FetchMode.SELECT);
        OperatorQueuePriority operatorQueuePriority = (OperatorQueuePriority) criteria.uniqueResult();
        return operatorQueuePriority;
    }

    
    
}
