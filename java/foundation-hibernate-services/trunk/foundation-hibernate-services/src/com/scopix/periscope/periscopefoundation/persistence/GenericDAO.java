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


import java.io.Serializable;
import java.util.List;

/**
 * Generic DAO which helps on basic operations which can be generalized, such as
 * loading or removing an object by id.
 * 
 * @author maximiliano.vazquez
 * @version 1.0.0
 */
public interface GenericDAO {

    /**
     * Check wheter an identifier exist or not.<br>
     * 
     * @param id the identifier (primary key) of the class
     * @param clazz represents entity class
     * @return booelan que indica la existencia del objeto solicitado
     */
    boolean exists(Serializable id, Class clazz);

    /**
     * Generic method to get an object based on identifier.<br>
     * 
     * @param <T> 
     * @param id the identifier (primary key) of the class
     * @param clazz represents entity class
     * @return object found
     */
    <T> T get(Serializable id, Class<T> clazz);

    /**
     * Generic method used to get all objects.<br>
     * This is the same as lookup up all rows in a table.
     * @param <T> 
     * @param clazz 
     * @return Retorna lista para clase señalada
     */
    <T> List<T> getAll(Class<T> clazz);

    /**
     * Generic method to delete an object
     * 
     * @param o object to be removed
     */
    void remove(Object o);

    /**
     * Generic method to delete an object based on id
     * 
     * @param id the identifier (primary key) of the class
     * @param clazz represents entity class
     * 
     */
    void remove(Serializable id, Class clazz);

    /**
     * Generic method to save an object (create an object).
     * 
     * @param o the object to save
     */
    void save(BusinessObject o);

    /**
     * Generic method to save a list of objects.
     * 
     * @param o the objects to save
     */
    void save(BusinessObject... o);

    /**
     * Generic method to save a list of objects.
     * 
     * @param list the objects to save
     */
    void save(List list);
}
