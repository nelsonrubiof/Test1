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
 * 23/11/2006 - 21:19:35
 */
package com.scopix.periscope.periscopefoundation.states.configs;

import com.scopix.periscope.periscopefoundation.states.interceptor.EnumMatrix;

/**
 * This class represent a matrix with configuration which use enums on each
 * cell. <br>
 * It is used to hold state transitions configuration for State Manager
 * 
 * @author maximiliano.vazquez
 */
public class MatrixConfigurationHolder<R extends Enum, C extends Enum, E> {

  /**
   * Returns operation type related to specified operation name.
   */
  public C getOperationTypeByName(String operationEnumName) {
    Enum operation = Enum.valueOf(this.getOperationClass(), operationEnumName);
    return (C) operation;
  }

  protected EnumMatrix<R, C, E> getConfigurationMatrix() {
    return this.configurationMatrix;
  }

  protected Class<? extends C> getOperationClass() {
    return this.operationClass;
  }

  protected void initializeInstance(R initialState, C operation) {
    Class<? extends C> operationTypeClass = operation.getDeclaringClass();
    this.operationClass = operationTypeClass;

    Class<? extends R> stateTypeClass = initialState.getDeclaringClass();
    this.configurationMatrix = EnumMatrix.create(stateTypeClass, operationTypeClass);
  }

  private EnumMatrix<R, C, E> configurationMatrix;

  private Class<? extends C> operationClass;

}
