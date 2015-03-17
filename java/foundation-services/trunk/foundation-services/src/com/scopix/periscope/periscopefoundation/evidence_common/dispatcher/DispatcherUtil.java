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
 * DispatcherUtil.java
 *
 * Created on June 19, 2007, 4:09 PM
 *
 */
package com.scopix.periscope.periscopefoundation.evidence_common.dispatcher;

import com.scopix.periscope.periscopefoundation.evidence_common.exception.ConfigurationException;
import java.net.MalformedURLException;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 *
 * @author jorge
 */
public class DispatcherUtil {

    /**
     * Creates a new instance of DispatcherUtil
     */
    private DispatcherUtil() {
    }

    /**
     * Creates the proxy to communicate with the web service.
     * <br>
     * <br>
     * Will be interesting to research whether we can reuse the proxy created from multiples threads. This will improve
     * performance.
     *
     * @param <E> Tipo de Class a retornar
     * @param wsUrl url del WebService asociado a la clase
     * @param clazz clase de la cual se desea crea el webservice 
     * @return 
     * @throws MalformedURLException en caso de Exception
     * @throws ConfigurationException en caso de Exception
     */
    public static <E> E createWSAgent(String wsUrl, Class<E> clazz) throws MalformedURLException, ConfigurationException {
//        ObjectServiceFactory serviceFactory = new ObjectServiceFactory();
//        org.codehaus.xfire.service.Service serviceModel = serviceFactory.create(clazz);
//
//        XFireProxyFactory proxyFactory = new XFireProxyFactory();
//
//        E wsAgent = (E) proxyFactory.create(serviceModel, wsUrl);

        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        //register WebService interface
        factory.setServiceClass(clazz);
        //set webservice publish address to factory.
        factory.setAddress(wsUrl);
        E wsAgent = (E) factory.create();

//        ServerFactoryBean sf = new ServerFactoryBean();
//        //sf.getServiceFactory().setDataBinding(new AegisDatabinding());
//        sf.setServiceClass(clazz);
////        sf.setServiceClass(clazz.getClass());
//        sf.setAddress(wsUrl);
//        E wsAgent = (E) sf.create();
        return (wsAgent);
    }
}
