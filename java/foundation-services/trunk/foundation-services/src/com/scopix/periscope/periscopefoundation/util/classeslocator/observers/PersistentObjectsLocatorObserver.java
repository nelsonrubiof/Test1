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
 * Oct 11, 2006 - 5:18:46 PM
 */
package com.scopix.periscope.periscopefoundation.util.classeslocator.observers;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

/**
 * This class is an implementation of {@link ClassesLocatorObserver} which knows which classes are Persistent classes. For doing
 * this, his observer checks that a class has an {@link Entity} annotation and extends {@link BusinessObject}
 *
 * @author maximiliano.vazquez
 */
public class PersistentObjectsLocatorObserver implements ClassesLocatorObserver {

    /**
     * Singleton object
     */
    private static PersistentObjectsLocatorObserver instance;
    private List<Class> classes = new ArrayList<Class>();
    private boolean configurationFinished = false;

    /**
     *
     * @return
     */
    public static PersistentObjectsLocatorObserver getInstance() {
        if (instance == null) {
            instance = new PersistentObjectsLocatorObserver();
        }
        return instance;
    }

    private PersistentObjectsLocatorObserver() {
        // Hide visible constructor
    }

    /**
     * Callback method executed when some class was found
     *
     * @param clazz
     */
    @Override
    public void classFound(Class clazz) {
        if (this.isPersistentClass(clazz)) {
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
     * Get a list with all persistent classes
     *
     * @return
     */
    public List<Class> getAllPersistentObjects() {

        if (!this.configurationFinished) {
            throw new UnexpectedRuntimeException("Process was not started.");
        }

        return this.classes;
    }

    /**
     * Marks starting process
     */
    @Override
    public void start() {
    }

    /**
     * This method checks if specified class is a persistent class.
     */
    private boolean isPersistentClass(Class clazz) {

        boolean hasEntityAnnotation = clazz.isAnnotationPresent(Entity.class);
        if (hasEntityAnnotation) {
            return true;
        }

        // Se hace una validacion extra para determinar si es una clase mal
        // configurada
        if (BusinessObject.class.isAssignableFrom(clazz) && !clazz.equals(BusinessObject.class)) {

            if (!clazz.isAnnotationPresent(MappedSuperclass.class)) {
                throw new UnexpectedRuntimeException("Class: " + clazz + " should have " + Entity.class + " annotation");
            }

        }
        return false;

    }
}
