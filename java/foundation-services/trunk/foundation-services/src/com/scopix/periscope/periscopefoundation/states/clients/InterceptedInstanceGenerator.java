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
 * Nov 27, 2006 - 2:39:19 PM
 */
package com.scopix.periscope.periscopefoundation.states.clients;

import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;
import com.scopix.periscope.periscopefoundation.states.interceptor.StatefulInterceptor;
import com.scopix.periscope.periscopefoundation.util.ProxyUtils;

import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * This class can build objects which method calls must be intercepted by
 * {@link MethodInterceptor}. Interceptors to be added to the object are
 * configured with special annotations.
 * 
 * @author maximiliano.vazquez
 */
public class InterceptedInstanceGenerator {

  /**
   * Singleton Object.
   */
  //CHECKSTYLE:OFF
  private static final InterceptedInstanceGenerator singleton = new InterceptedInstanceGenerator();
  //CHECKSTYLE:ON

  /**
   * Facility method to avoid singleton method call.
   * 
   * @param <T> Instance Type to return.
   * @param clazz Class of the intercepted object.
   * @return Object created for which method calls will be intercepted
   */
  public static <T> T generate(Class<T> clazz) {
    return InterceptedInstanceGenerator.singleton.generateInstance(clazz);
  }

  /**
   * Determine if class is CGLib proxy managed.
   */
  public static boolean isProxiedClass(Class clazz) {
    return clazz.getName().contains("EnhancerByCGLIB");
  }

  public static boolean shouldBeProxied(Class clazz) {
    return Stateful.class.isAssignableFrom(clazz);
  }

  private InterceptedInstanceGenerator() {
  }

  /**
   * Builds an intercepted object.
   */
  private <T> T generateInstance(Class<T> clazz) {
    MethodInterceptor interceptor = null;
    if (clazz.isAnnotationPresent(StatefulConfiguration.class)) {
      interceptor = StatefulInterceptor.create();
    }
    if (interceptor != null) {
      return ProxyUtils.createProxy(clazz, interceptor);
    }
    return this.generateNormalInstance(clazz);
  }

  /**
   * Builds an instance with empty constructor. Class must have this
   * constructor.
   */
  private <T> T generateNormalInstance(Class<T> clazz) {
    try {
      Constructor<T> constructor = clazz.getDeclaredConstructor();
      constructor.setAccessible(true);
      T instance = constructor.newInstance();
      return instance;
    } catch (SecurityException e) {
      throw new UnexpectedRuntimeException("There is a security problem", e);
    } catch (IllegalArgumentException e) {
      throw new UnexpectedRuntimeException("Empty constructor has an error about parameters?!", e);
    } catch (NoSuchMethodException e) {
      throw new UnexpectedRuntimeException("There must be an empty constructor", e);
    } catch (InstantiationException e) {
      throw new UnexpectedRuntimeException("Class specified should allow instantiation", e);
    } catch (IllegalAccessException e) {
      throw new UnexpectedRuntimeException("Reflection could not be used. Check securiy schemes.", e);
    } catch (InvocationTargetException e) {
      throw new UnexpectedRuntimeException("An error was thrown when constructor was executed", e);
    }
  }

}
