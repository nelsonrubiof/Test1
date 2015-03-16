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
 * SecurityManagementHibernateDAO.java
 *
 * Created on 16-06-2008, 01:53:46 PM
 *
 */
package com.scopix.periscope.templatemanagement.dao;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import com.scopix.periscope.templatemanagement.Product;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@SpringBean(rootClass = TemplateManagementHibernateDAOImpl.class)
public class TemplateManagementHibernateDAOImpl extends DAOHibernate<MetricTemplate, Integer>
        implements TemplateManagementHibernateDAO {

    private Logger log = Logger.getLogger(TemplateManagementHibernateDAOImpl.class);

    @Override
    public List<MetricTemplate> getMetricTemplateList(MetricTemplate metricTemplate) throws ScopixException {
        log.debug("start");
        List<MetricTemplate> metricTemplates = null;
        try {
            Criteria criteria = this.getSession().createCriteria(MetricTemplate.class);
            criteria.addOrder(Order.asc("id"));
            if (metricTemplate != null) {
                if (metricTemplate.getName() != null && metricTemplate.getName().length() > 0) {
                    criteria.add(Restrictions.ilike("name", metricTemplate.getName(), MatchMode.ANYWHERE));
                }
                if (metricTemplate.getDescription() != null && metricTemplate.getDescription().length() > 0) {
                    criteria.add(Restrictions.ilike("description", metricTemplate.getDescription(), MatchMode.ANYWHERE));
                }
                if (metricTemplate.getEvidenceTypeElement() != null) {
                    criteria.add(Restrictions.eq("evidenceTypeElement", metricTemplate.getEvidenceTypeElement()));
                }
                if (metricTemplate.getMetricTypeElement() != null) {
                    criteria.add(Restrictions.eq("metricTypeElement", metricTemplate.getMetricTypeElement()));
                }
            }
            metricTemplates = criteria.list();
        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
            //"periscopeexception.list.error", new String[]{
            throw new ScopixException("label.metricTemplate");
        }
        log.debug("end");
        return metricTemplates;
    }

    @Override
    public List<SituationTemplate> getSituationTemplateList(SituationTemplate situationTemplate) throws ScopixException {
        log.debug("start");
        List<SituationTemplate> situationTemplates = null;
        try {
            Criteria criteria = this.getSession().createCriteria(SituationTemplate.class);
            criteria.addOrder(Order.asc("name"));
            if (situationTemplate != null) {
                if (situationTemplate.getName() != null && situationTemplate.getName().length() > 0) {
                    criteria.add(Restrictions.ilike("name", situationTemplate.getName(), MatchMode.ANYWHERE));
                }
                if (situationTemplate.getActive() != null) {
                    criteria.add(Restrictions.eq("active", situationTemplate.getActive()));
                }
            }
            situationTemplates = criteria.list();
        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
            //"periscopeexception.list.error", new String[]{
            throw new ScopixException("label.situationTemplate");
        }
        log.debug("end");
        return situationTemplates;
    }

    @Override
    public List<MetricTemplate> getMetricTemplateForASituationTemplate(Integer situationTemplateId) throws ScopixException {
        log.debug("start");
        List<MetricTemplate> metricTemplates = null;
        try {
            Criteria criteria = this.getSession().createCriteria(MetricTemplate.class);
            criteria.addOrder(Order.asc("id"));
            criteria.createCriteria("situationTemplates").add(Restrictions.eq("id", situationTemplateId));
            metricTemplates = criteria.list();
        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
            //"periscopeexception.list.error",new String[]{
            throw new ScopixException("label.metricTemplateForASituationTemplate");
        }
        log.debug("end");
        return metricTemplates;
    }

    @Override
    public List<Product> getProductList(Product product) throws ScopixException {
        log.debug("start");
        List<Product> products = null;
        try {
            Criteria criteria = this.getSession().createCriteria(Product.class);
            criteria.addOrder(Order.asc("id"));
            if (product != null) {
                if (product.getName() != null && product.getName().length() > 0) {
                    criteria.add(Restrictions.ilike("name", product.getName(), MatchMode.ANYWHERE));
                }
                if (product.getDescription() != null && product.getDescription().length() > 0) {
                    criteria.add(Restrictions.ilike("description", product.getDescription(), MatchMode.ANYWHERE));
                }
            }
            products = criteria.list();
        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
            //"periscopeexception.list.error", new String[]{
            throw new ScopixException("label.product");
        }
        log.debug("end");
        return products;
    }
}
