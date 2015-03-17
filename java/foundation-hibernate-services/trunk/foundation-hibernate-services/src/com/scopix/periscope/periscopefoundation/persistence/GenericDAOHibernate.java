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

import com.scopix.periscope.periscopefoundation.BusinessObject;

import org.springframework.orm.ObjectRetrievalFailureException;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Generic DAO which helps on basic operations which can be generalized, such as
 * loading or removing an object by id.
 * 
 * @author maximiliano.vazquez
 * @version 1.0.0
 */
public class GenericDAOHibernate extends DAOHibernate implements GenericDAO {

    /**
     * @return wheter an identifier exist or not.<br>
     * 
     * @param id the identifier (primary key) of the class
     * @param clazz clase para la cual se debe determinar si existe dato
     */
    @Override
    public boolean exists(Serializable id, Class clazz) {
        try {
            this.get(id, clazz);
            return true;
        } catch (ObjectRetrievalFailureException e) {
            return false;
        }
    }

    /**
     * @param <E> Classque se desea recuperar
     * @param id the identifier (primary key) of the class
     * @return object based on identifier.<br>
     */
    @Override
    public <E> E get(Serializable id, Class<E> clazz) {
        Object object = this.getHibernateTemplate().get(clazz, id);

        if (object == null) {
            throw new ObjectRetrievalFailureException(clazz, id);
        }

        return (E) object;
    }

    /**
     * Generic method used to get all objects.<br>
     * This is the same as lookup up all rows in a table.
     * 
     * @param <E> 
     * @param clazz represent entity class
     * @return List of entities found
     */
    @Override
    public <E> List<E> getAll(Class<E> clazz) {
        return this.getHibernateTemplate().loadAll(clazz);
    }

    /**
     * Generic method to delete an object
     * 
     * @param o to be removed
     * 
     */
    @Override
    public void remove(Object o) {
        this.getHibernateTemplate().delete(o);
        this.getHibernateTemplate().flush();
    }

    /**
     * Generic method to delete an object based on id
     * 
     * @param id the identifier (primary key)
     * @param clazz represents entity class
     */
    @Override
    public void remove(Serializable id, Class clazz) {
        Object e = this.get(id, clazz);
        this.getHibernateTemplate().delete(e);
        this.getHibernateTemplate().flush();
    }

    /**
     * Generic method to save an object (create an object).
     * 
     * @param o the object to save
     */
    @Override
    public void save(BusinessObject o) {
        this.getHibernateTemplate().saveOrUpdate(o);
        this.getHibernateTemplate().flush();
    }

    /**
     * Generic method to save a list of objects.
     * 
     * @param o objects to save
     */
    @Override
    public void save(BusinessObject... o) {
        for (Object object : o) {
            this.save(object);
            this.getHibernateTemplate().flush();
        }
    }

    /**
     * Generic method to save a list of objects.
     * 
     * @param list objects to save
     */
    @Override
    public void save(List list) {
        for (Object object : list) {
            this.save(object);
            this.getHibernateTemplate().flush();
        }
    }
}
