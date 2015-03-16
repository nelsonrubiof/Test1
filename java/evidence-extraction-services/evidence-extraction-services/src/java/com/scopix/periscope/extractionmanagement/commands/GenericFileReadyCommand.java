/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * GenericFileReadyCommand.java
 * 
 * Created on 25/09/2014
 */
package com.scopix.periscope.extractionmanagement.commands;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;

public abstract class GenericFileReadyCommand {

    private PropertiesConfiguration configuration;
    private static Logger log = Logger.getLogger(GenericFileReadyCommand.class);

    /**
     * 
     * @return
     */
    public PropertiesConfiguration getConfiguration() {
        if (configuration == null) {
            try {
                configuration = new PropertiesConfiguration("system.properties");
                configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
            } catch (ConfigurationException e) {
                log.error("No es posible cargar propiedades " + e, e);
            }
        }
        return configuration;
    }

    /**
     * 
     * @param configuration
     */
    public void setConfiguration(PropertiesConfiguration configuration) {
        this.configuration = configuration;
    }
}