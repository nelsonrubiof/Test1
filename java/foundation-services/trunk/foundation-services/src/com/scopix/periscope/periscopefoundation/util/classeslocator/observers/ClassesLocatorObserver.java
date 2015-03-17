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

/**
 * 16/03/2007 - 14:59:45
 */
package com.scopix.periscope.periscopefoundation.util.classeslocator.observers;

/**
 * This interface represents an observer of the Classes locator It provides some
 * callback methods wich will be called at correct time while Classes Locator is
 * running
 * 
 * @author maximiliano.vazquez
 */
public interface ClassesLocatorObserver {

  /**
   * Called when a class was found
   * @param clazz 
   */
  void classFound(Class clazz);

  /**
   * Call once at the end of the process
   */
  void finish();

  /**
   * Call once at the beginning of the process
   */
  void start();

}
