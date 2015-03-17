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
 * WebServiceUrlMapping.java
 *
 * Created on 20-05-2008, 12:51:35 PM
 *
 */
package com.scopix.periscope.periscopefoundation.util.webservices.client;

import java.io.IOException;
import java.util.Properties;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public class WebServiceUrlMapping {

    private static final String PROPERTY_NAME = "webservices.properties";
    private static final String PROPERTY_NAME_DEFAULT = "webservices-default.properties";
    private static WebServiceUrlMapping webServiceUrlMapping;
    private PropertiesConfiguration propertiesConfiguration;
    private static Logger log = Logger.getLogger(WebServiceUrlMapping.class);

    private WebServiceUrlMapping() throws IOException {
        init();
    }

    public static synchronized WebServiceUrlMapping getInstance() throws IOException {
        if (webServiceUrlMapping == null) {
            webServiceUrlMapping = new WebServiceUrlMapping();
        }
        return webServiceUrlMapping;
    }

    public void init() throws IOException {
        log.info("start");
        try {
            propertiesConfiguration = new PropertiesConfiguration(PROPERTY_NAME);
            propertiesConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
        } catch (ConfigurationException e) {
            try {
                propertiesConfiguration = new PropertiesConfiguration(PROPERTY_NAME_DEFAULT);
                propertiesConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
            } catch (ConfigurationException e2) {
                log.error("No es posible recuperar Properties " + e2, e2);
            }
        }
        log.info("end");
    }

    /**
     * Obtain the url for the gived key
     *
     * @param key
     * @return
     */
    public String getURL(String key) {
        return propertiesConfiguration.getString(key);
    }
}
