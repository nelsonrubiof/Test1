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
 */
/**
 * Oct 2, 2006 - 10:27:19 AM
 */
package com.scopix.periscope.periscopefoundation.states.configs;

import com.scopix.periscope.periscopefoundation.states.interceptor.EnumMatrix;

/**
 * This class is default implementation for {@link StateConfigurationHolder}.
 * 
 * State Transitions configuration can be shown as a graph. This implementation
 * use a matrix to represent graph. Rows represent initial states and columns
 * represent possible transitions and on each intersection cell there are final
 * states.
 * 
 * @author mvazquez
 */
public class MatrixStateConfigurationHolder
        extends MatrixConfigurationHolder<Enum<? extends StateType>, Enum<? extends StateOperation>, Enum<? extends StateType>>
        implements StateConfigurationHolder {

    /**
     * Configures transition based on initial state, operation and final state
     */
    public <E extends Enum> void configureTransition(E initialState, Enum operation, E finalState) {
        if (this.getConfigurationMatrix() == null) {
            this.initializeInstance(initialState, operation);
        }

        this.getConfigurationMatrix().put(initialState, operation, finalState);
    }

    /**
     * Get all possible operations
     */
    public Enum[] getAllOperations() {
        return this.getOperationClass().getEnumConstants();
    }

    /**
     * Get next state type based on current state and specified operation
     */
    public Enum getNextStateType(Enum currentState, Enum operationType) {
        EnumMatrix<Enum<? extends StateType>, Enum<? extends StateOperation>, Enum<? extends StateType>> transitionMatrix2 = this.
                getConfigurationMatrix();
        return transitionMatrix2.get(currentState, operationType);
    }
}
