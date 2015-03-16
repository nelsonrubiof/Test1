/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  EvidenceTransmitionStrategyDAOImpl.java
 * 
 *  Created on Jul 23, 2014, 2:53:25 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.dao;

import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.EvidenceTransmitionStrategy;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@SpringBean(rootClass = EvidenceTransmitionStrategyDAO.class)
public class EvidenceTransmitionStrategyDAOImpl extends DAOHibernate<EvidenceTransmitionStrategy, Integer>
        implements EvidenceTransmitionStrategyDAO {

    private static Logger log = Logger.getLogger(EvidenceTransmitionStrategyDAOImpl.class);

    @Override
    public EvidenceTransmitionStrategy getByStoreAndSituationTemplate(Store store, SituationTemplate situationTemplate) {
        Session session = this.getSession();
        EvidenceTransmitionStrategy evidenceTransmitionStrategy = null;
        try {
            Criteria criteria = session.createCriteria(EvidenceTransmitionStrategy.class);
            criteria.add(Restrictions.eq("store.id", store.getId()));
            criteria.add(Restrictions.eq("situationTemplate.id", situationTemplate.getId()));
            evidenceTransmitionStrategy = (EvidenceTransmitionStrategy) criteria.uniqueResult();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return evidenceTransmitionStrategy;
    }

    @Override
    public List<EvidenceTransmitionStrategy> getAllByStoreAndOrSituationTemplate(Store store, SituationTemplate situationTemplate) {
        Session session = this.getSession();
        List<EvidenceTransmitionStrategy> evidenceTransmitionStrategies = null;
        try {
            Criteria criteria = session.createCriteria(EvidenceTransmitionStrategy.class);
//        criteria.setFetchMode("Store", FetchMode.JOIN);
//        criteria.setFetchMode("SituationTemplate", FetchMode.JOIN);
            if (store != null) {
                criteria.add(Restrictions.eq("store.id", store.getId()));
            }
            if (situationTemplate != null) {
                criteria.add(Restrictions.eq("situationTemplate.id", situationTemplate.getId()));
            }
            evidenceTransmitionStrategies = criteria.list();
        } catch (HibernateException e) {
            log.error(e, e);
        } finally{
            this.releaseSession(session);
        }
        return evidenceTransmitionStrategies;
    }

    @Override
    public List<EvidenceTransmitionStrategy> getByStoresAndSituationTemplates(List<Integer> storeIds, List<Integer> situationTemplateIds) {
        Session session = this.getSession();
        List<EvidenceTransmitionStrategy> evidenceTransmitionStrategies = null;
        try {
            Criteria criteria = session.createCriteria(EvidenceTransmitionStrategy.class);
            criteria.add(Restrictions.in("store.id", storeIds));
            criteria.add(Restrictions.in("situationTemplate.id", situationTemplateIds));
            evidenceTransmitionStrategies = criteria.list();
        } catch (HibernateException e) {
            log.error(e,e);
        } finally {
            this.releaseSession(session);
        }
        return evidenceTransmitionStrategies;
    }

    @Override
    public void deleteMultiple(List<Integer> ids) {

        GenericDAO gd = HibernateSupport.getInstance().findGenericDAO();
        for (Integer id : ids) {
            gd.remove(id, EvidenceTransmitionStrategy.class);
        }
//        String hql = "delete from EvidenceTransmitionStrategy where id in (:ids)";
//        Query query = this.getSession().createQuery(hql);
//        query.setParameterList("ids", ids);
//        query.executeUpdate();
    }
}
