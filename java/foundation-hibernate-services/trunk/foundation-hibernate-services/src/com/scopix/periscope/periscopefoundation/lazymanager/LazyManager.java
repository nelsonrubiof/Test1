/*
 * 
 * Copyright 2007, SCOPIX. All rights reserved.
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
package com.scopix.periscope.periscopefoundation.lazymanager;

import com.scopix.periscope.periscopefoundation.persistence.exceptions.NoHibernateSessionBountToThreadException;
import com.scopix.periscope.periscopefoundation.persistence.transactions.TransactionManager;

import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * This class is used as an interceptor to resolve the lazy objects after
 * transaction processing of the DB is finished.<br>
 * To resolve this, entities are registered in this interceptor and commited in
 * Hibernates interceptor when commit is due.
 * 
 * @param <E> 
 * @author maximiliano.vazquez
 * @version 1.0.0
 */
public class LazyManager<E extends Lazy> {

  private static LazyManager instance;

  /**
   * @return instance of Lazy manager. This is a singleton object
   */
  public static LazyManager getInstance() {
    if (instance == null) {
      instance = new LazyManager();
    }
    return instance;
  }

  /**
   * Constructor method. Initializes lazy entries.
   */
  public LazyManager() {
    this.entries = new HashMap();
  }

  /**
   * @return if manager has still pending lazy objects to process.
   */
  public boolean hasPendingLazies() {
    Transaction currentTransaction = TransactionManager.getCurrentTransaction();
    List<E> auditEntriesForCurrentTransaction = this.entries.get(currentTransaction);
    return (auditEntriesForCurrentTransaction != null) && !auditEntriesForCurrentTransaction.isEmpty();
  }

  /**
   * Registers the pending task at the moment of closing the transaction. The
   * added tasks will be solved in the same order they were added. (It is very
   * important that this contract is respected, because of the dependency
   * between lazys.
   * 
   * @param auditEntryLazy Task to be solved.
   */
  public void register(E auditEntryLazy) {

    Transaction currentTransaction;
    try {
      currentTransaction = TransactionManager.getCurrentTransaction();
    } catch (NoHibernateSessionBountToThreadException e) {
      auditEntryLazy.resolve();
      return;
    }

    List<E> auditEntriesForCurrentTransaction = this.entries.get(currentTransaction);

    if (auditEntriesForCurrentTransaction == null) {
      List list = new ArrayList();
      list.add(auditEntryLazy);
      this.entries.put(currentTransaction, list);
    } else {
      auditEntriesForCurrentTransaction.add(auditEntryLazy);
    }

  }

  /**
   * Call pending registered lazy entries for transaction and resolve them
   * 
   * @param transaction The transaction
   */
  public synchronized void resolveForTransaction(Transaction transaction) {

    // Transaction currentTransaction =
    // TransactionManager.getCurrentTransaction();

    List<E> auditEntriesForCurrentTransaction = this.entries.get(transaction);

    if (auditEntriesForCurrentTransaction == null) {
      return;
    }

    for (E entry : auditEntriesForCurrentTransaction) {
      entry.resolve();
    }

    this.unRegisterEntriesForTransaction(transaction);

  }

  /**
   * Unregisters lazy entries for a transaction
   * 
   * @param transaction The transaction.
   */
  public void unRegisterEntriesForTransaction(Transaction transaction) {
    this.entries.remove(transaction);
  }

  private Map<Transaction, List<E>> entries;

}
