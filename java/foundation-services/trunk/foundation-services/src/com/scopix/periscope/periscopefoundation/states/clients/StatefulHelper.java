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
 * Oct 2, 2006 - 10:44:49 AM
 */
package com.scopix.periscope.periscopefoundation.states.clients;

import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;
import com.scopix.periscope.periscopefoundation.states.configs.StateConfigurationHolder;
import com.scopix.periscope.periscopefoundation.states.configs.StateOperation;
import com.scopix.periscope.periscopefoundation.states.configs.StateTransition;
import com.scopix.periscope.periscopefoundation.states.configs.StateType;
import com.scopix.periscope.periscopefoundation.states.configs.StatefulConfigurationRepository;
import com.scopix.periscope.periscopefoundation.states.exception.InvalidOperationException;

import java.lang.reflect.Method;

/**
 * This class is a helper to manage {@link Stateful} objects.
 * 
 * @author maximiliano.vazquez
 */
public abstract class StatefulHelper {

    /**
     * Determines if an operation can be executed based on stateful present state.
     *
     */
    public static boolean canExecuteOperation(Stateful stateful, Enum operation) {
        Enum<? extends StateType> nextState = StatefulHelper.getNextState(stateful, operation);
        return nextState != null;
    }

    /**
     * Get all possible operations.
     */
    public static Enum[] getAllOperationsFor(Stateful stateful) {
        StateConfigurationHolder configurationHolder = StatefulConfigurationRepository.getConfigurationFor(stateful.getClass());
        return configurationHolder.getAllOperations();
    }

    /**
     * Get next state based on stateful present state and specified operation.
     */
    public static Enum getNextState(Stateful stateful, Enum operation) {
        StateConfigurationHolder configurationHolder = StatefulConfigurationRepository.getConfigurationFor(stateful.getClass());
        return configurationHolder.getNextStateType(stateful.getStateType(), operation);
    }

    /**
     * Get next state based on stateful present state and specified method wich
     * determines an operation.
     */
    public static Enum getNextState(Stateful stateful, Method method) {
        StateConfigurationHolder configurationHolder = StatefulConfigurationRepository.getConfigurationFor(stateful.getClass());

        Enum<? extends StateType> currentStateType = stateful.getStateType();
        Enum<? extends StateOperation> operationType = StatefulHelper.getOperationTypeFrom(method, configurationHolder);

        Enum<? extends StateType> nextStateType = configurationHolder.getNextStateType(currentStateType, operationType);
        return nextStateType;
    }

    private static Enum getOperationTypeFrom(Method method, StateConfigurationHolder configurationHolder) {
        StateTransition methodAnnotation = method.getAnnotation(StateTransition.class);
        String string = methodAnnotation.enumType();

        // This check is meaningful at development time
        StatefulHelper.verifyCorrectThrowsInvalidOperationClause(method);

        Enum<? extends StateOperation> operationType = configurationHolder.getOperationTypeByName(string);
        return operationType;
    }

    /**
     * This method is a runtime check to force politics to use <br>
     * throws {@link InvalidOperationException} <br>
     * on methods marked as {@link StateTransition}
     *
     * @param method method to verify
     */
    private static void verifyCorrectThrowsInvalidOperationClause(Method method) {
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        for (Class<?> exceptionType : exceptionTypes) {
            if (InvalidOperationException.class.equals(exceptionType)) {
                return;
            }
        }
        throw new UnexpectedRuntimeException("Method " + method + " mark with " + StateTransition.class.getSimpleName() +
                " must declare throws " + InvalidOperationException.class.getSimpleName() + " on his method sign");
    }

    protected StatefulHelper() {
    }
}
