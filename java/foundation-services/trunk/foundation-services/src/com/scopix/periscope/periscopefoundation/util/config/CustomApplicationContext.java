/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
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
package com.scopix.periscope.periscopefoundation.util.config;

import com.scopix.periscope.periscopefoundation.util.classeslocator.ClassesLocator;
import com.scopix.periscope.periscopefoundation.util.classeslocator.observers.SpringBeansLocatorObserver;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.beans.Introspector;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * This class is used to perform a custom configuration of Spring application context.<br> It performs configuration mixing xml
 * definitions and programatic definitions. Those beans which should be registered and are classes which can be simply ideintified
 * are registered programtically by this class to avoid writing xml which is error prone. By now, beans registered programatically
 * are those represented by classes which has {@link SpringBean} annotation.
 *
 * @author maximiliano.vazquez
 * @version 2.0.0
 */
public class CustomApplicationContext extends GenericApplicationContext {

    private static Logger log = Logger.getLogger(CustomApplicationContext.class);

    /**
     *
     */
    public CustomApplicationContext() {
        super();
        //destruction lifecycle events are processed
        this.registerShutdownHook();
    }

    /* 
     * initialization Method.
     * must be configured in beanRefContext.xml
     */
    public void init() {
        log.info("start");

        ClassesLocator.getInstance().initWithDefaultObservers();

        List<Class> beans = SpringBeansLocatorObserver.getInstance().getAllSpringBeans();
        this.registerBeans(beans);

        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(this);
        xmlReader.loadBeanDefinitions(new ClassPathResource(SystemConfig.getStringParameter("spring.contextpath")));

        this.refresh();
        log.info("end");

    }

    /**
     * Register specified class as a spring bean.
     */
    private void registerBean(Class bean) {
        log.debug("start [bean:" + bean + "]");
        SpringBean annotation = (SpringBean) bean.getAnnotation(SpringBean.class);
        Class rootClass;
        rootClass = annotation.rootClass();
        log.debug("[bean:" + bean + "][rootClass:" + rootClass + "]");
        if (rootClass.equals(SpringBean.class)) {
            rootClass = bean;
        }

        BeanDefinitionBuilder rootBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(bean);
        rootBeanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);
        if (annotation.initMethod() != null && annotation.initMethod().length() > 0) {
            log.debug("[bean:" + bean + "][initMethod: " + annotation.initMethod() + "]");
            rootBeanDefinition.setInitMethodName(annotation.initMethod());
            rootBeanDefinition.setLazyInit(true);
        }
        String beanName = Introspector.decapitalize(rootClass.getSimpleName());
        log.debug("[bean:" + bean + "][beanName:" + beanName + "]");
        this.registerBeanDefinition(beanName, rootBeanDefinition.getBeanDefinition());
        log.debug("end [bean:" + bean + "]");
    }

    private void registerBeans(List<Class> classes) {

        for (Class clazz : classes) {
            this.registerBean(clazz);
        }
    }
}
