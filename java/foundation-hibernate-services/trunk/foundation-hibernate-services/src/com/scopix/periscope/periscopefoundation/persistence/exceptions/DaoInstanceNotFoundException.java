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

package com.scopix.periscope.periscopefoundation.persistence.exceptions;

/**
 * This Exception is raised when a DAO bean is not found.
 * 
 * @author maximiliano.vazquez
 * @version 1.0.0
 */
public class DaoInstanceNotFoundException extends RuntimeException {

  /**
   * Standard constructor for the Exception
   * 
   */
  public DaoInstanceNotFoundException() {
    super();
  }

  /**
   * Constructor for the Exception
   * 
   * @param message A string that contains the message to be displayed with the
   *        exception
   */
  public DaoInstanceNotFoundException(String message) {
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
  public DaoInstanceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor for the Exception
   * 
   * @param cause Throwable is the superclass of all errors and exceptions in
   *        the Java language. Cause is another throwable that caused this
   *        throwable to get thrown
   */
  public DaoInstanceNotFoundException(Throwable cause) {
    super(cause);
  }

}
