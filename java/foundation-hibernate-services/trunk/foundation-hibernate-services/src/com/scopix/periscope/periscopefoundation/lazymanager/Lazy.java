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

/**
 * This interface represents a task to be solved at a time posterior to its
 * creation
 * 
 * @author maximiliano.vazquez
 * @version 1.0.0
 */
public interface Lazy {

  /**
   * Resolve the pending task
   */
  void resolve();

}
