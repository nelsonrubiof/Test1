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
package com.scopix.periscope.frameworksfoundation.hibernate.interceptor;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.EntityMode;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;

import com.scopix.periscope.periscopefoundation.lazymanager.LazyManager;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;
import com.scopix.periscope.periscopefoundation.states.clients.InterceptedInstanceGenerator;

/**
 * Interceptors in Hibernate allow user code to inspect and/or change property
 * values. Inspection occurs before property values are written and after they
 * are read from the database. This class adds custom interceptor behavior,
 * defined by its methods.
 * 
 * @author maximiliano.vazquez
 * @version 1.0.0
 */
public class CustomHibernateInterceptor extends EmptyInterceptor {

  /**
   * Singleton object
   */
  public static final Interceptor CUSTOM_INTERCEPTOR_INSTANCE = new CustomHibernateInterceptor();

    /**
     *
     */
    protected CustomHibernateInterceptor() {
    // Hide visible constructor
  }

  /**
   * It handles transaction completion to notify {@link LazyManager} about this
   * situation.
   * @param tx 
   */
  @Override
  public void afterTransactionCompletion(Transaction tx) {
    super.afterTransactionCompletion(tx);

    LazyManager.getInstance().resolveForTransaction(tx);
  }

  /**
   * Get Hibernate entity related name, proxy instances should return superclass
   * name.
   * @param object 
   * @return 
   */
  @Override
  public String getEntityName(Object object) {
    Class classToPersist = object.getClass();
    if (InterceptedInstanceGenerator.isProxiedClass(classToPersist)) {
      // Si la instancia esta proxiada con CGLib, la posta es la super
      classToPersist = classToPersist.getSuperclass();
      if (InterceptedInstanceGenerator.isProxiedClass(classToPersist)) {
        throw new UnexpectedRuntimeException("La superclase tambien es un proxy? Este caso no esta considerado");
      }
      return classToPersist.getName();
    }
    return null;
  }

  /**
   * Check if object instantiation should be managed by State Manager.
   * @param entityName 
   * @param entityMode 
   * @param id 
   * @return 
   */
  @Override
  public Object instantiate(String entityName, EntityMode entityMode, Serializable id) {

    Class clazz;

    try {
      clazz = Class.forName(entityName);
    } catch (ClassNotFoundException e) {
      throw new UnexpectedRuntimeException(e);
    }

    if (!InterceptedInstanceGenerator.shouldBeProxied(clazz)) {
      return super.instantiate(entityName, entityMode, id);
    }

    BusinessObject businessObject = (BusinessObject) InterceptedInstanceGenerator.generate(clazz);
    businessObject.setId((Integer) id);

    return businessObject;
  }

}
