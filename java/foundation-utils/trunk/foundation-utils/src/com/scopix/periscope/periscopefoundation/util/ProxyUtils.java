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
package com.scopix.periscope.periscopefoundation.util;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 *
 * This class provides some Proxy related helper methods.
 *
 * @author maximiliano.vazquez
 * @version 1.0.0
 *
 */
public abstract class ProxyUtils {

    /**
     * Creates CGLib proxy of specified class using specified {@link MethodInterceptor}
     *
     * @param <E> Tipo de Proxy
     * @param clazz Class pra crear proxy
     * @param interceptor Interceptor asociado
     * @return Instancia de <E>
     */
    public static <E> E createProxy(Class<E> clazz, MethodInterceptor interceptor) {

        E object2 = (E) Enhancer.create(clazz, interceptor);

        return object2;
    }

    /**
     *
     */
//    protected ProxyUtils() {
//    }
}
