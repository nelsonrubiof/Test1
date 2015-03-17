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

import java.io.Serializable;
import java.util.List;

/**
 * Data Access Object (DAO) interface.<br>
 * Provides common methods to all DAOs.<br>
 * 
 * @author Nelson Rubio
 * @version 1.0.0
 * 
 * @param <E> Lista para los objtos
 * @param <PK> primary Key de la class
 */
public interface DAO<E, PK extends Serializable> {

  /**
   * Generic method to get an object based on identifier.<br>
   * 
   * @param id the identifier (primary key) of the class
   * @return object found
   */
  E get(PK id);

  /**
   * Generic method used to get all objects.<br>
   * This is the same as lookup up all rows in a table.
   * 
   * @return objects found
   */
  List<E> getAll();

  /**
   * Generic method used to get all objects with fully-fetched collections<br>
   * 
   * @param id Valor entero para relizar Fetch
   * @return object found
   */
  E getFullFetch(Integer id);

  /**
   * Generic method to delete an object based on id
   * 
   * @param id the identifier (primary key) of the class
   */
  void remove(PK id);

  /**
   * Generic method to save an object - handles both update and insert.
   * 
   * @param o the object to save
   */
  void save(E o);
}
