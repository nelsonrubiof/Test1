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

package com.scopix.periscope.periscopefoundation.persistence.datasource;



import com.scopix.periscope.periscopefoundation.util.config.SystemConfig;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * This class extends {@link BasicDataSource} with the purpose of being able to
 * configure progammatically the datasource.
 * 
 * @author maximiliano.vazquez
 * @version 1.0.0
 */
public class CustomDataSource extends BasicDataSource {

  /**
   * Set parameters of datasource taken from the .properties file.
   */
  public void init() {
    this.setDriverClassName(SystemConfig.getStringParameter("datasource.driverclass"));
    this.setUrl(SystemConfig.getStringParameter("datasource.url"));
    this.setUsername(SystemConfig.getStringParameter("datasource.username"));
    this.setPassword(SystemConfig.getStringParameter("datasource.password"));
    this.setMaxActive(SystemConfig.getIntegerParameter("datasource.pool.maxactive"));
    this.setMaxIdle(SystemConfig.getIntegerParameter("datasource.pool.maxidle"));
    this.setMaxWait(SystemConfig.getIntegerParameter("datasource.pool.maxwait"));
    this.setDefaultAutoCommit(SystemConfig.getBooleanParameter("datasource.autocommit"));
  }

}
