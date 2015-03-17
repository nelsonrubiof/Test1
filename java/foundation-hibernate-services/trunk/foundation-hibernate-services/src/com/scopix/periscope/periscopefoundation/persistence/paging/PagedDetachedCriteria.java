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
 */
/**
 * 06/02/2007 - 11:43:05
 */
package com.scopix.periscope.periscopefoundation.persistence.paging;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * This class extends a basic DetachedCriteria and attachs functionality about
 * doing paged queries. This type of query needs an order to be specified.
 * 
 * @author maximiliano.vazquez
 * @version 1.0.0
 */
public class PagedDetachedCriteria extends DetachedCriteria {

    /**
     *
     * @param clazz Class para generar PagedDetachedCriteria
     * @param pageData data para clase
     * @param order order en PagedDetachedCriteria
     * @return PagedDetachedCriteria asociado a clazz recibido
     */
    public static PagedDetachedCriteria forClass(Class clazz, PageData pageData, Order order) {
        PagedDetachedCriteria pagedDetachedCriteria = new PagedDetachedCriteria(clazz.getName());

        pagedDetachedCriteria.pageData = pageData;
        pagedDetachedCriteria.order = order;

        return pagedDetachedCriteria;
    }

    /**
     *
     * @param impl
     * @param criteria
     */
    protected PagedDetachedCriteria(CriteriaImpl impl, Criteria criteria) {
        super(impl, criteria);
    }

    /**
     *
     * @param entityName
     */
    protected PagedDetachedCriteria(String entityName) {
        super(entityName);
    }

    /**
     *
     * @param entityName
     * @param alias
     */
    protected PagedDetachedCriteria(String entityName, String alias) {
        super(entityName, alias);
    }

    /**
     * This method does the query and returns a Page object which has the paged
     * result.
     * @param hibernateTemplate HibernateTemplate asociado
     * @return Page para un hibernateTemplate dado
     */
    public Page listPage(HibernateTemplate hibernateTemplate) {

        List resultadoPaginado;
        Integer total = 0;

        if (this.pageData != null) {

            this.setProjection(Projections.rowCount());

            List<Integer> resultadoProyeccion = hibernateTemplate.findByCriteria(this, 0, Integer.MAX_VALUE);

            if (resultadoProyeccion != null) {
                for (Integer integer : resultadoProyeccion) {
                    total += integer;
                }
            }

            this.setProjection(null);
            this.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
            this.addOrder(this.order);

            resultadoPaginado = hibernateTemplate.findByCriteria(this, this.pageData.getFirstResult() - 1, this.pageData.
                    getMaxResults());

        } else {

            resultadoPaginado = hibernateTemplate.findByCriteria(this);
            total = resultadoPaginado.size();

        }

        Page page = new Page();
        page.setElements(resultadoPaginado);

        page.setTotalElements(total);

        return page;

    }
    private Order order;
    private PageData pageData;
}
