package com.scopix.periscope.dao;

import java.util.List;

public interface GenericDAO<T> {

    /**
     * checks if a record exists
     *
     * @param id
     * @return
     */
    boolean exists(Integer id);

    /**
     * get record from db
     *
     * @param id
     * @return
     */
    T get(Integer id);

    /**
     * gets all objects on a list
     *
     * @return List<T>
     */
    List<T> getAll();

    /**
     *
     * @param object
     */
    void remove(T object);

    /**
     * removes an assignee object by id
     *
     * @param id
     */
    void remove(Integer id);

    /**
     * save an object
     *
     * @param object
     * @return object id in DB.
     */
    Integer save(T object);

    /**
     * save a list of objects
     *
     * @param list
     * @return
     */
    List<Integer> save(List<T> list);

    /**
     * updates an object
     *
     * @param object
     */
    void update(T object);

    /**
     * updates a list of objects
     *
     * @param list
     */
    void update(List<T> list);
}
