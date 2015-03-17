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
 * Oct 11, 2006 - 5:18:46 PM
 */
package com.scopix.periscope.periscopefoundation.util.classeslocator.observers;

import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is an implementation of {@link ClassesLocatorObserver} which knows which classes should be registered as Spring
 * beans. For doing this, his observer checks that a class has an {@link SpringBean} annotation.
 *
 * @author maximiliano.vazquez
 */
public class SpringBeansLocatorObserver implements ClassesLocatorObserver {

    /**
     * Singleton object
     */
    private static SpringBeansLocatorObserver instance;

    /**
     *
     * @return
     */
    public static SpringBeansLocatorObserver getInstance() {
        if (instance == null) {
            instance = new SpringBeansLocatorObserver();
        }
        return instance;
    }

    private SpringBeansLocatorObserver() {
        // Hide visible constructor
    }

    /**
     * Callback method executed when some class was found
     *
     * @param clazz
     */
    @Override
    public void classFound(Class clazz) {
        if (this.isSpringBean(clazz)) {
            this.classes.add(clazz);
        }
    }

    /**
     * Marks ending process
     */
    @Override
    public void finish() {
        this.configurationFinished = true;
    }

    /**
     * Get a list with all spring beans classes found after {@link ClassesLocator}
     *
     * @return
     */
    public List<Class> getAllSpringBeans() {

        if (!this.configurationFinished) {
            throw new UnexpectedRuntimeException("Process was not started.");
        }

//    if (this.classes.isEmpty()) {
//      throw new UnexpectedRuntimeException("No spring beans persistible classes found");
//    }

        return this.classes;
    }

    /**
     * Marks starting process
     */
    @Override
    public void start() {
    }

    /**
     * This method checks if specified class is a spring bean class.
     */
    private boolean isSpringBean(Class clazz) {

        if (clazz.isAnnotationPresent(SpringBean.class)) {
            return true;
        }

        return false;

    }
    private List<Class> classes = new ArrayList<Class>();
    private boolean configurationFinished = false;
}
