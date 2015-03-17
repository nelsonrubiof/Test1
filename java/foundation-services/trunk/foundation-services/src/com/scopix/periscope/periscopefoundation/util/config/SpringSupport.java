/**
 *
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 *
 */
package com.scopix.periscope.periscopefoundation.util.config;

import com.scopix.periscope.periscopefoundation.exception.ServiceInstanceNotFoundException;
import com.scopix.periscope.periscopefoundation.services.Service;
import java.beans.Introspector;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.transaction.PlatformTransactionManager;

/**
 *
 * This is a support class to help Spring application context access.<br> It hides complexity about finding different beans on
 * application context.<br> It defines conventions about finding beans from a class name.
 *
 * @author maximiliano.vazquez
 * @version 2.0.0
 */
public class SpringSupport {

    /**
     * Singleton instance
     */
    private static SpringSupport instance;
    private BeanFactory beanFactory;

    private void init() {
    }

    /**
     *
     * @return una instancia de la clase SpringSupport
     */
    public static SpringSupport getInstance() {
        if (instance == null) {
            instance = new SpringSupport();
            instance.init();
        }
        return instance;
    }

    /**
     * Find bean specified by name on application context
     *
     * @param bean
     * @return retorna un objeto que repesenta al bean solicitado
     */
    public Object findBean(String bean) {
        return this.getBeanFactory().getBean(bean);
    }

    /**
     * Find bean specified by class (based on class name) on application context
     *
     * @param <E>
     * @param clazz
     * @return
     */
    public <E> E findBeanByClassName(Class<E> clazz) {

        String simpleName = clazz.getSimpleName();

        String decapitalized = Introspector.decapitalize(simpleName);

        return (E) this.findBean(decapitalized);
    }

    /**
     * Find specified {@link Service} on application context
     *
     * @param <T>
     * @param serviceClazz
     * @return
     * @throws ServiceInstanceNotFoundException
     */
    public <T extends Service> T findService(Class<T> serviceClazz) throws ServiceInstanceNotFoundException {
        T service = this.findBeanByClassName(serviceClazz);
        if (service == null) {
            throw new ServiceInstanceNotFoundException(serviceClazz.toString());
        }
        return service;

    }

    /**
     * Find {@link PlatformTransactionManager} on application context
     *
     * @return
     */
    public PlatformTransactionManager findTxManager() {
        return (PlatformTransactionManager) this.findBean("txManager");
    }

    protected BeanFactory getBeanFactory() {
        if (this.beanFactory == null) {
            BeanFactoryLocator bfl = ContextSingletonBeanFactoryLocator.getInstance();
            BeanFactoryReference bf = bfl.useBeanFactory("com.scopix");
            BeanFactory factory = bf.getFactory();
            this.beanFactory = factory;

        }
        return this.beanFactory;
    }
}
