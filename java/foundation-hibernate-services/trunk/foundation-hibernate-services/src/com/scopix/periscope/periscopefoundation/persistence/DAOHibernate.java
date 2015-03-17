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
 */
package com.scopix.periscope.periscopefoundation.persistence;

import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;
import com.scopix.periscope.periscopefoundation.persistence.paging.Page;
import com.scopix.periscope.periscopefoundation.persistence.paging.PageData;
import com.scopix.periscope.periscopefoundation.persistence.paging.PagedDetachedCriteria;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Hibernate implementation of DAO interface.<br>
 * It is used as the base class for all dao hibernate implementations.
 * 
 * @author Nelson Rubio
 * @version 1.0.0
 * 
 * @param <E> 
 * @param <PK> primary key del objeto 
 */
public abstract class DAOHibernate<E, PK extends Serializable> extends HibernateDaoSupport implements DAO<E, PK> {

    /**
     *
     * @param <E> Tipo de BeanDescription Solicitado
     */
    public interface BeanDescription<E> {

        /**
         *
         * @param bean de sistema
         */
        void describe(E bean);
    }

    /**
     *
     * @param beanDescription Descriptor de Bean 
     * @return Una instancia de la clase E
     */
    public E findByExample(BeanDescription beanDescription) {

        E bean = this.createBeanForExample();
        beanDescription.describe(bean);

        Example example = Example.create(bean);

        DetachedCriteria criteria = this.newCriteriaInstance();
        criteria.add(example);

        E beanFound = this.findUniqueResultByCriteria(criteria);
        return beanFound;
    }

    /**
     *
     * @param criteria Tipo de Paginacion
     * @return Page de la clase E
     */
    public Page<E> findPagedDataByCriteria(PagedDetachedCriteria criteria) {

        Page<E> page = criteria.listPage(this.getHibernateTemplate());

        return page;
    }

    /**
     *
     * @param criteria Criterio Independiente
     * @return Objeto del Tipo E
     */
    public E findUniqueResultByCriteria(final DetachedCriteria criteria) {
        return this.findUniqueResultByCriteria(criteria, null, null);
    }

    /**
     *
     * @param criteria criterio de busqueda
     * @param firstResult primer resultado
     * @param maxResults numero maximo de resultados
     * @return Objeto del Tipo E
     */
    public E findUniqueResultByCriteria(final DetachedCriteria criteria, final Integer firstResult, final Integer maxResults) {
        Assert.notNull(criteria, "DetachedCriteria must not be null");

        HibernateCallback callback = new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) {
                Criteria executableCriteria = criteria.getExecutableCriteria(session);
                if (firstResult != null) {
                    executableCriteria.setFirstResult(firstResult);
                }
                if (maxResults != null) {
                    executableCriteria.setMaxResults(maxResults);
                }
                return executableCriteria.uniqueResult();
            }
        };

        Object result = this.getHibernateTemplate().execute(callback);

        return (E) result;
    }

    /**
     *
     * @param criteria 
     * @return Object 
     */
    public Object findUniqueResultByExternalCriteria(final DetachedCriteria criteria) {
        return this.findUniqueResultByCriteria(criteria, null, null);
    }

    /**
     * Generic method to get an object based on identifier.
     *
     * @param id the identifier (primary key) of the class
     * @return object <E>
     */
    @Override
    public E get(PK id) {
        Object o = this.getHibernateTemplate().get(this.getType(), id);

        if (o == null) {
            throw new ObjectRetrievalFailureException(this.getType(), id);
        }

        return (E) o;
    }

    /**
     * Generic method used to get all objects. This is the same as lookup up all
     * rows in a table.     
     * @return lista de la clase instanciada
     */
    @Override
    public List<E> getAll() {
        Class<E> type = this.getType();
        return this.getHibernateTemplate().loadAll(type);
    }

    /**
     * Este metodo debe se <tt>overrideado</tt> a fin de obtener un
     * fully-fetched object.
     *
     * @param id Integer que representa a la llave del objeto
     * @return E classe del objeto instanciado
     */
    @Override
    public E getFullFetch(Integer id) {
        throw new UnsupportedOperationException("operation not supported");
    }

    /**
     *
     * @return DetachedCriteria
     */
    public DetachedCriteria newCriteriaInstance() {
        return DetachedCriteria.forClass(this.getType());
    }

    /**
     *
     * @param pageData 
     * @param order
     * @return PagedDetachedCriteria
     */
    public PagedDetachedCriteria newCriteriaInstance(PageData pageData, Order order) {
        return PagedDetachedCriteria.forClass(this.getType(), pageData, order);
    }

    /**
     * Generic method to delete an object based on id.
     *
     * @param id the identifier (primary key) of the class
     */
    @Override
    public void remove(PK id) {
        E e = this.get(id);
        this.getHibernateTemplate().delete(e);
    }

    /**
     * Generic method to save an object (handles both update and insert).
     *
     * @param o the object to save
     */
    @Override
    public void save(E o) {
        this.getHibernateTemplate().saveOrUpdate(o);
    }

    /**
     *
     * @return
     */
    protected E createBeanForExample() {
        try {
            Class<E> type = this.getType();
            Constructor<E> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            E newInstance = constructor.newInstance();
            return newInstance;
        } catch (InstantiationException e) {
            throw new UnexpectedRuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new UnexpectedRuntimeException(e);
        } catch (SecurityException e) {
            throw new UnexpectedRuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new UnexpectedRuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new UnexpectedRuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new UnexpectedRuntimeException(e);
        }
    }

    /** This method returns the type of classes that this dao manages.
     * @return 
     */
    protected Class<E> getType() {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        Type type = actualTypeArguments[0];
        return (Class<E>) type;
    }
}
