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
 * Oct 2, 2006 - 10:27:19 AM
 */
package com.scopix.periscope.periscopefoundation.states.configs;

/**
 * This interface represent configuration for states and transitions. With this
 * object we can know which transitions are valid.
 * 
 * @author maximiliano.vazquez
 */
public interface StateConfigurationHolder {

  <E extends Enum> void configureTransition(E initialState, Enum operation, E finalState);

  /**
   * Get all existent operations for this configuration.
   */
  Enum[] getAllOperations();

  /**
   * Get next possible state from specified state and transition
   */
  Enum getNextStateType(Enum currentState, Enum operationType);

  /**
   * Get Enum Operation instance related to operation name.
   */
  Enum getOperationTypeByName(String operationEnumName);
}
