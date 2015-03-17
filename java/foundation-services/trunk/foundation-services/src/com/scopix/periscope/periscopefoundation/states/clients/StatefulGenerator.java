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

/**
 * Oct 2, 2006 - 10:44:49 AM
 */
package com.scopix.periscope.periscopefoundation.states.clients;

import net.sf.cglib.proxy.MethodInterceptor;

import com.scopix.periscope.periscopefoundation.states.interceptor.StatefulInterceptor;
import com.scopix.periscope.periscopefoundation.util.ProxyUtils;

/**
 * This class builds {@link Stateful} objects which manage automatically their
 * state.
 * 
 * This implementation use CGlib to intercept method calls. Objects built by
 * this class use {@link StatefulInterceptor} which manage his state based on
 * configuration read on meta-information class {@link StateConfigurationHolder}).
 * This configuration is access from {@link StatefulConfigurationRepository}
 * where all state configurations are stored.
 * 
 * @author maximiliano.vazquez
 */
public abstract class StatefulGenerator {

  public static <E extends Stateful> E generate(Class<E> clase) {
    MethodInterceptor interceptor = new StatefulInterceptor();
    return ProxyUtils.createProxy(clase, interceptor);
  }

  protected StatefulGenerator() {
  }

}
