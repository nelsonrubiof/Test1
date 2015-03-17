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
 */
/**
 * Oct 2, 2006 - 10:44:49 AM
 */
package com.scopix.periscope.periscopefoundation.states.interceptor;

import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;
import com.scopix.periscope.periscopefoundation.states.clients.Stateful;
import com.scopix.periscope.periscopefoundation.states.clients.StatefulAware;
import com.scopix.periscope.periscopefoundation.states.clients.StatefulHelper;
import com.scopix.periscope.periscopefoundation.states.configs.StateTransition;
import com.scopix.periscope.periscopefoundation.states.configs.StateType;
import com.scopix.periscope.periscopefoundation.states.exception.InvalidOperationException;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * This class implements a CGlib method interceptor, which allows adding a
 * "state handling aspect".
 * 
 * With this class we implement automatically state handling .
 * 
 * @author mvazquez
 */
public class StatefulInterceptor implements MethodInterceptor {

    public static StatefulInterceptor create() {
        return new StatefulInterceptor();
    }

    /**
     * This method implements cglib interception method. <br>
     * This method is a central point of State Manager operations.<br>
     * Each intercepted method is checked to determine if contains any
     * {@link StateTransition} annotation and then it checks on state transitions
     * matrix to allow real execution of this method.
     */
    public Object intercept(Object instancia, Method metodoReal, Object[] argumentos, MethodProxy metodoProxy) throws Throwable {
        if (metodoReal.getAnnotation(StateTransition.class) == null) {
            Object result = this.doOperation(instancia, metodoProxy, argumentos);
            return result;
        }

        Class<? extends Object> objectClass = instancia.getClass();
        if (!Stateful.class.isAssignableFrom(objectClass)) {
            throw new UnexpectedRuntimeException("La clase " + objectClass + " debe implementar " + Stateful.class);
        }
        Stateful stateful = (Stateful) instancia;

        Enum<? extends StateType> nextStateType = StatefulHelper.getNextState(stateful, metodoReal);

        if (nextStateType == null) {
            Enum<? extends StateType> currentState = stateful.getStateType();
            throw new InvalidOperationException("El estado actual [" + currentState + "] no permite la operacion [" + metodoReal +
                    "]");
        }

        if (this.executingMethod != null) {
            throw new UnexpectedRuntimeException("No se puede cambiar de en forma anidada [" + this.executingMethod + "," +
                    metodoReal + "]");
        }

        // Se establece el metodo en ejecucion en el contexto de otra ejecucion
        this.executingMethod = metodoReal;
        Object result = null;
        try {
            result = this.doOperation(stateful, metodoProxy, argumentos);
        } finally {
            this.executingMethod = null;
        }

        stateful.changeStateType(nextStateType);

        this.afterStateChange(stateful);

        return result;
    }

    /**
     * It checks if stateful implements {@link StatefulAware} to notify
     * afterStateChange event.
     */
    private void afterStateChange(Stateful stateful) {
        if (!(stateful instanceof StatefulAware)) {
            return;
        }

        StatefulAware statefulAware = (StatefulAware) stateful;
        statefulAware.afterStateChange();
    }

    private Object doOperation(Object obj, MethodProxy methodProxy, Object[] args) throws Throwable {
        return methodProxy.invokeSuper(obj, args);
    }
    /**
     * Method which is currently executing
     */
    private Method executingMethod = null;
}
