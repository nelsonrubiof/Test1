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
package com.scopix.periscope.businesswarehouse.datasource;

import com.scopix.periscope.periscopefoundation.util.config.SystemConfig;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

/**
 * This class extends {@link BasicDataSource} with the purpose of being able to configure progammatically the datasource.
 *
 * @author maximiliano.vazquez
 * @version 2.0.0
 */
public class CustomDataSourceBW extends BasicDataSource {

    private Logger log = Logger.getLogger(CustomDataSourceBW.class);

    /**
     * Set parameters of datasource taken from the .properties file.
     */
    public void init() {
        log.debug("Start init");
        this.setDriverClassName(SystemConfig.getStringParameter("datasource.driverclass.manager_gui"));
        this.setUrl(SystemConfig.getStringParameter("datasource.url.manager_gui"));
        this.setUsername(SystemConfig.getStringParameter("datasource.username.manager_gui"));
        this.setPassword(SystemConfig.getStringParameter("datasource.password.manager_gui"));
        this.setMaxActive(SystemConfig.getIntegerParameter("datasource.pool.maxactive.manager_gui"));
        this.setMaxIdle(SystemConfig.getIntegerParameter("datasource.pool.maxidle.manager_gui"));
        this.setMaxWait(SystemConfig.getIntegerParameter("datasource.pool.maxwait.manager_gui"));
        this.setDefaultAutoCommit(SystemConfig.getBooleanParameter("datasource.autocommit.manager_gui"));
        log.debug("end init, DriverClassName = " + this.getDriverClassName() + " " + this.getUrl());
    }
}
