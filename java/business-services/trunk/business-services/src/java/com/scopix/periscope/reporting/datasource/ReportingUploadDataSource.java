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
 *  ReportingUploadDataSource.java
 * 
 *  Created on 28-01-2011, 05:56:07 PM
 * 
 */
package com.scopix.periscope.reporting.datasource;

import com.scopix.periscope.periscopefoundation.util.config.SystemConfig;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author nelson
 */
public class ReportingUploadDataSource extends BasicDataSource {

    public void init() {

//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
//        dataSource.setUrl("jdbc:hsqldb:hsql://localhost:");
//        dataSource.setUsername("sa");
//        dataSource.setPassword("");

        this.setDriverClassName(SystemConfig.getStringParameter("datasource.driverclass.reporting"));
        this.setUrl(SystemConfig.getStringParameter("datasource.url.reporting"));
        this.setUsername(SystemConfig.getStringParameter("datasource.username.reporting"));
        this.setPassword(SystemConfig.getStringParameter("datasource.password.reporting"));
        this.setMaxActive(SystemConfig.getIntegerParameter("datasource.pool.maxactive.reporting"));
        this.setMaxIdle(SystemConfig.getIntegerParameter("datasource.pool.maxidle.reporting"));
        this.setMaxWait(SystemConfig.getIntegerParameter("datasource.pool.maxwait.reporting"));
        this.setDefaultAutoCommit(SystemConfig.getBooleanParameter("datasource.autocommit.reporting"));

    }
}
