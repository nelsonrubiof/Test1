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
 * 
 */

package com.scopix.periscope.periscopefoundation.persistence.exceptions;

/**
 * This exception is raised when the hibernate session fails at runtime
 * 
 * @author maximiliano.vazquez
 * @version 1.0.0
 */
public class NoHibernateSessionBountToThreadException extends RuntimeException {

  /**
   * Standard constructor for the Exception
   * 
   */
  public NoHibernateSessionBountToThreadException() {
    super();
  }

  /**
   * Constructor for the Exception
   * 
   * @param message A string that contains the message to be displayed with the
   *        exception
   */
  public NoHibernateSessionBountToThreadException(String message) {
    super(message);
  }

  /**
   * Constructor for the Exception
   * 
   * @param message A string that contains the message to be displayed with the
   *        exception
   * @param cause Throwable is the superclass of all errors and exceptions in
   *        the Java language. Cause is another throwable that caused this
   *        throwable to get thrown
   */
  public NoHibernateSessionBountToThreadException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor for the Exception
   * 
   * @param cause Throwable is the superclass of all errors and exceptions in
   *        the Java language. Cause is another throwable that caused this
   *        throwable to get thrown
   */
  public NoHibernateSessionBountToThreadException(Throwable cause) {
    super(cause);
  }

}
