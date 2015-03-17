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
package com.scopix.periscope.frameworksfoundation.hibernate.config;

import com.scopix.periscope.frameworksfoundation.hibernate.interceptor.CustomHibernateInterceptor;
import com.scopix.periscope.periscopefoundation.util.classeslocator.observers.PersistentObjectsLocatorObserver;
import com.scopix.periscope.periscopefoundation.util.config.SystemConfig;

import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import java.util.List;

/**
 * This class extends the behavior of <br> {@link AnnotationSessionFactoryBean}, that's the class through which <br>
 * Spring configures the Hibernate {@link SessionFactory}. <br>
 * The following is added to the default behavior: <br>
 * <ul>
 * <li> {@link SchemaExportHelper} is configured. (it is done here since, <br>
 * it is the last point where a reference to the Hibernate configuration object is hold</li>
 * <li>A naming strategy is specified to have control over the way a class name is mapped to a table name. </li>
 * <li>The dialect is specified.</li>
 * <li>The classes managed by Hibernate are specified</li>
 * <li>A customized interceptor is specified(see the interceptor documentation)</li>
 * </ul>
 *
 * @author maximiliano.vazquez
 * @version 1.0.0
 */
public class CustomAnnotationSessionFactory extends AnnotationSessionFactoryBean {

    /**
     *
     * @param conf
     */
    @Override
    protected void postProcessAnnotationConfiguration(final AnnotationConfiguration conf) {

        HibernateConfigurationHolder.conf = conf;

        conf.setInterceptor(CustomHibernateInterceptor.CUSTOM_INTERCEPTOR_INSTANCE);

        conf.setNamingStrategy(CustomNamingStrategy.getInstance());

        conf.setProperty("hibernate.dialect", SystemConfig.getStringParameter("hibernate.dialect"));

        PersistentObjectsLocatorObserver domainObjectsLocator = PersistentObjectsLocatorObserver.getInstance();
        List<Class> allDomainObjects = domainObjectsLocator.getAllPersistentObjects();

        for (Class domainObjectClass : allDomainObjects) {
            conf.addAnnotatedClass(domainObjectClass);
        }

    }
}
