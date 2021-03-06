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

package com.scopix.periscope.periscopefoundation.exception;

/**
 * This class represents the exception that occurs when a Service bean is not
 * found. Runtime Exception
 * 
 * @author Nelson Rubio
 * @version 2.0.0
 * 
 */
public class ServiceInstanceNotFoundException extends RuntimeException {

  /**
   * Standard constructor for the Exception
   * 
   */
  public ServiceInstanceNotFoundException() {
  }

  /**
   * Constructor for the Exception
   * 
   * @param message A string that contains the message to be displayed with the
   *        exception
   */
  public ServiceInstanceNotFoundException(String message) {
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
  public ServiceInstanceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor for the Exception
   * 
   * @param cause Throwable is the superclass of all errors and exceptions in
   *        the Java language. Cause is another throwable that caused this
   *        throwable to get thrown
   */
  public ServiceInstanceNotFoundException(Throwable cause) {
    super(cause);
  }

}
