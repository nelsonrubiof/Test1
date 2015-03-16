/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  SynchronizeManager.java
 * 
 *  Created on 03-03-2014, 10:38:26 AM
 * 
 */
package com.scopix.periscope.synchronize;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson
 * @version 1.0.0
 */
@SpringBean(rootClass = SynchronizeManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class SynchronizeManager {

    public static Logger log = Logger.getLogger(SynchronizeManager.class);
    private PropertiesConfiguration configuration;

    public void sendDataOtherSystem(int observedSituationId) throws ScopixException {
        log.info("start");
        try {
            String[] appsExternal = getConfiguration().getStringArray("APPS_EXTRENAL");

            //por cada apliacion externa generamos un llamado
            for (String app : appsExternal) {
                if ("DSR".equals(app)) {
                    DSRThread thread = new DSRThread();
                    thread.init(observedSituationId);
                    thread.start();
                } else if ("DSO".equals(app)) {
                    DSOThread thread = new DSOThread();
                    thread.init(observedSituationId);
                    thread.start();
                } else {
                    log.debug("app: " + app + " no definida");
                }
            }
        } catch (ScopixException e) {
            log.warn("no es posible procesar apps Externas " + e);
        }
        log.info("end");
    }

    /**
     * @return the configuration
     */
    public PropertiesConfiguration getConfiguration() throws ScopixException {
        if (configuration == null) {
            try {
                configuration = new PropertiesConfiguration("system.properties");
                configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
            } catch (ConfigurationException e) {
                log.error("Error obteniendo configuration" + e, e);
                throw new ScopixException(e);
            }
        }
        return configuration;
    }

    /**
     * @param configuration the configuration to set
     */
    public void setConfiguration(PropertiesConfiguration configuration) {
        this.configuration = configuration;
    }
}
