/*
 * 
 * Copyright © 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 */

package com.scopix.periscope.periscopefoundation.persistence.transactions;


import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;
import com.scopix.periscope.periscopefoundation.persistence.exceptions.NoHibernateSessionBountToThreadException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * This class is a transactions manager helper. It knows about sessions and
 * transaction within Hibernate.
 * 
 * @author maximiliano.vazquez
 * @version 1.0.0
 */
public abstract class TransactionManager {

  /**
   * This method executes code defined on callback within a Transaction. It
   * handles code related on opening and closing transactions.
   * @param <T> retorno esperado
   * @param callBack transaccion para llamar
   * @return Respuesta a la llamada de una transaccion
   */
  public static <T> T doInTransaction(TransactionCallback callBack) {
    PlatformTransactionManager txManager = SpringSupport.getInstance().findTxManager();
    TransactionTemplate transactionTemplate = new TransactionTemplate(txManager);

    Object object = transactionTemplate.execute(callBack);

    return (T) object;
  }

  /**
   * Ir returns Session related to current thread.
   * @return Session session de base de datos para la conexion activa
   */
  public static Session getCurrentSession() {

    try {

      org.hibernate.classic.Session currentSession;
      HibernateSupport.getInstance().findSessionFactory();
      currentSession = HibernateSupport.getInstance().findSessionFactory().getCurrentSession();
      return currentSession;

    } catch (HibernateException e) {

      if (e.getMessage().startsWith("No Hibernate Session bound to thread")) {
        throw new NoHibernateSessionBountToThreadException(e);
      }
      throw new UnexpectedRuntimeException(e);
    }

  }

  /**
   * It returns Transaction related to current thread.
   * @return Transaction transaccion definida por hibernate
   */
  public static Transaction getCurrentTransaction() {
    return getCurrentSession().getTransaction();
  }

    /**
     *
     */
    protected TransactionManager() {
  }

}
