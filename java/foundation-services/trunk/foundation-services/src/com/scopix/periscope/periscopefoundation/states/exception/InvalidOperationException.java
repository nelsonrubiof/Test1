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

/**
 * Oct 2, 2006 - 10:53:58 AM
 */
package com.scopix.periscope.periscopefoundation.states.exception;

/**
 * This exception raises when executing a method which represents an invalid
 * operation.
 * 
 * @author mvazquez
 */
public class InvalidOperationException extends RuntimeException {

  public InvalidOperationException(String message) {
    super(message);
  }

  public InvalidOperationException(String message, Throwable cause) {
    super(message, cause);
  }

}
