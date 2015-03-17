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
package com.scopix.periscope.periscopefoundation.states.configs;

import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;
import com.scopix.periscope.periscopefoundation.states.clients.Stateful;
import com.scopix.periscope.periscopefoundation.states.clients.StatefulConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a transitions configurations repository.<br>
 * It is used to hold static refernce to {@link StateConfigurationHolder} where
 * state configurations are stored. <br>
 * It stores one holder for each class which use State Manager.
 * 
 * 
 * @author mvazquez
 */
public abstract class StatefulConfigurationRepository {

    /**
     * Configurations map by class
     */
    private static Map<Class, StateConfigurationHolder> configurations = new HashMap();

    /**
     * Get Stateful configuration
     */
    public static StateConfigurationHolder getConfigurationFor(Class<? extends Stateful> clazz) {
        StateConfigurationHolder holder = StatefulConfigurationRepository.configurations.get(clazz);
        if (holder == null) {
            holder = StatefulConfigurationRepository.createHolderForClass(clazz);
            StatefulConfigurationRepository.configurations.put(clazz, holder);
        }
        return holder;
    }

    /**
     * Creates stateful configuration based on configurator class specified on
     * specified class.
     */
    private static StateConfigurationHolder createHolderForClass(Class<? extends Stateful> clazz) {
        StateConfigurationHolder holder = new MatrixStateConfigurationHolder();
        Method method = StatefulConfigurationRepository.getConfiguratorMethodFrom(clazz);
        try {
            method.invoke(null, holder);
        } catch (IllegalArgumentException e) {
            throw new UnexpectedRuntimeException("El metodo debe recibir un solo argumento del tipo " +
                    MatrixStateConfigurationHolder.class, e);
        } catch (IllegalAccessException e) {
            throw new UnexpectedRuntimeException("El metodo de configuracion debe ser accesible por esta clase", e);
        } catch (InvocationTargetException e) {
            throw new UnexpectedRuntimeException("Se produjo un error en el metodo de configuracion: " + method, e);
        }

        return holder;
    }

    private static Method getConfiguratorMethodFrom(Class<? extends Stateful> clazz) {
        StatefulConfiguration annotation = clazz.getAnnotation(StatefulConfiguration.class);
        if (annotation == null) {
            throw new UnexpectedRuntimeException("La clase " + clazz + " debe estar anotada con " + StatefulConfiguration.class);
        }
        Class<?> configuratorClass = annotation.configuratorClass();
        if (configuratorClass == null) {
            throw new UnexpectedRuntimeException("La anotacion " + StatefulConfiguration.class + " de la clase " + clazz +
                    " debe indicar la clase configuradora");
        }
        try {
            return configuratorClass.getMethod("configure", StateConfigurationHolder.class);
        } catch (SecurityException e) {
            throw new UnexpectedRuntimeException("El metodo configurador debe ser accesible por esta clase");
        } catch (NoSuchMethodException e) {
            throw new UnexpectedRuntimeException("La clase " + clazz +
                    " no posee el metodo configurador [public static void configure(" + StateConfigurationHolder.class + ")]");
        }
    }

    protected StatefulConfigurationRepository() {
    }
}
