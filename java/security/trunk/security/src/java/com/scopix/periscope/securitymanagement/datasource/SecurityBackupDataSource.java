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
package com.scopix.periscope.securitymanagement.datasource;

import com.scopix.periscope.periscopefoundation.util.config.SystemConfig;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author nelson
 */
public class SecurityBackupDataSource extends BasicDataSource {

    public void init() {

        this.setDriverClassName(SystemConfig.getStringParameter("security_backup.driverclass"));
        this.setUrl(SystemConfig.getStringParameter("security_backup.url"));
        this.setUsername(SystemConfig.getStringParameter("security_backup.username"));
        this.setPassword(SystemConfig.getStringParameter("security_backup.password"));
        this.setMaxActive(SystemConfig.getIntegerParameter("security_backup.pool.maxactive"));
        this.setMaxIdle(SystemConfig.getIntegerParameter("security_backup.pool.maxidle"));
        this.setMaxWait(SystemConfig.getIntegerParameter("security_backup.pool.maxwait"));
        this.setDefaultAutoCommit(SystemConfig.getBooleanParameter("security_backup.autocommit"));

    }
}
