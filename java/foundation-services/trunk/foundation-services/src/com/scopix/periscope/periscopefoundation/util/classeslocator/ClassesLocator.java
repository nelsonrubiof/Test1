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
 * 16/03/2007 - 14:22:47
 */
package com.scopix.periscope.periscopefoundation.util.classeslocator;

import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;
import com.scopix.periscope.periscopefoundation.util.classeslocator.observers.ClassesLocatorObserver;
import com.scopix.periscope.periscopefoundation.util.classeslocator.observers.PersistentObjectsLocatorObserver;
import com.scopix.periscope.periscopefoundation.util.classeslocator.observers.SpringBeansLocatorObserver;
import com.scopix.periscope.periscopefoundation.util.classeslocator.strategies.ClassPathStrategy;
import com.scopix.periscope.periscopefoundation.util.classeslocator.strategies.OutputClassesClassPathStrategy;
import com.scopix.periscope.periscopefoundation.util.classeslocator.strategies.SystemPropertyClassPathStrategy;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * This class knows how to find all classes and knows some observers which are interested about some of those classes.<br>
 * It is useful to automatization of some tasks, such as configuring frameworks.<br>
 * For instance, Hibernate needs to know all persistent classes.
 *
 * Observers {@link ClassesLocatorObserver} must be registered on this class.
 *
 * @author maximiliano.vazquez
 */
public class ClassesLocator {

    private Boolean init = Boolean.FALSE;
    private static Logger log = Logger.getLogger(ClassesLocator.class);
    private List<ClassesLocatorObserver> observers = new ArrayList<ClassesLocatorObserver>();
    /**
     * Current strategy to perform class searching.
     */
    private ClassPathStrategy strategy = new SystemPropertyClassPathStrategy();
    /**
     * Singleton object
     */
    private static ClassesLocator instance;

    /**
     *
     * @return
     */
    public static ClassesLocator getInstance() {
        if (instance == null) {
            instance = new ClassesLocator();
        }
        return instance;
    }

    private ClassesLocator() {
    }

    /**
     * It changes current strategy to perform classpath location.
     *
     * @param classPathStrategy
     */
    public void changeStrategy(ClassPathStrategy classPathStrategy) {
        this.strategy = classPathStrategy;
    }

    /**
     * It performs default initialization.
     */
    public void initWithDefaultObservers() {

        if (this.init) {
            throw new UnexpectedRuntimeException("Classes Locator has been init before");
        }

        this.init = Boolean.TRUE;

        ClassesLocator classesLocator = ClassesLocator.getInstance();

        classesLocator.registerObserver(PersistentObjectsLocatorObserver.getInstance());

        classesLocator.registerObserver(SpringBeansLocatorObserver.getInstance());

        classesLocator.visitAllClasses();

    }

    /**
     * It register a new observer
     *
     * @param observer
     */
    public void registerObserver(ClassesLocatorObserver observer) {
        this.getObservers().add(observer);
    }

    /**
     * It finds all classes and for each one it notifies his observers.
     */
    public void visitAllClasses() {
        List<Class> filesClasses = this.findAllClasses();

        this.fireClassVisitingStarted();

        for (Class file : filesClasses) {

            this.fireClassFound(file);
        }

        this.fireClassVisitingFinished();
    }

    /**
     * It performs class searching and return a list with all classes found.
     */
    private List<Class> findAllClasses() {

        List<Class> allClasses;
        try {
            allClasses = this.strategy.getAllClasses();
        } catch (RuntimeException e) {
            this.changeStrategy(new OutputClassesClassPathStrategy());
            allClasses = this.strategy.getAllClasses();
        }
        return allClasses;
    }

    /**
     * This method is executed to notify observers that a class was found.
     */
    private void fireClassFound(Class clazz) {

        List<ClassesLocatorObserver> observers = this.getObservers();

        for (ClassesLocatorObserver observer : observers) {
            observer.classFound(clazz);
        }

        if (log.isDebugEnabled()) {
            log.debug(clazz);
        }
    }

    /**
     * This method is executed to notify observers about process ending
     */
    private void fireClassVisitingFinished() {
        List<ClassesLocatorObserver> lObservers = this.getObservers();

        for (ClassesLocatorObserver observer : lObservers) {
            observer.finish();
        }
    }

    /**
     * This method is executed to notify observers about process starting
     */
    private void fireClassVisitingStarted() {
        List<ClassesLocatorObserver> lObservers = this.getObservers();

        for (ClassesLocatorObserver observer : lObservers) {
            observer.start();
        }
    }

    private List<ClassesLocatorObserver> getObservers() {
        return this.observers;
    }
}
