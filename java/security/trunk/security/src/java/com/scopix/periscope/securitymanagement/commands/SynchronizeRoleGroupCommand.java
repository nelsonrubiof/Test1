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
 *  SynchronizeRoleGroupCommand.java
 * 
 *  Created on 23-06-2011, 12:58:51 PM
 * 
 */
package com.scopix.periscope.securitymanagement.commands;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.dao.SecurityBackupHibernateDAO;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author nelson
 */
public class SynchronizeRoleGroupCommand {

    private static Logger log = Logger.getLogger(SynchronizeRoleGroupCommand.class);
    private SecurityBackupHibernateDAO dao;
    public void execute(Integer rolesGroupId) {
        log.info("start");
        getDao().synchronizeRoleGroup(rolesGroupId);
//        try {
//            EtlExecutor executor = (EtlExecutor) SpringSupport.getInstance().findBean("synchronizeRoleGroup");
//            Map<String, String> param = getParameters();
//
//            param.put("roleGroupId", rolesGroupId.toString());
//
//            ConfigurationFactory cf = new ConfigurationFactory();
//            cf.setExternalParameters(param);
//            cf.setResourceURL(executor.getConfiguration().getDocumentUrl());
//            executor.setConfiguration(cf.createConfiguration());
//            executor.execute();
//        } catch (IOException e) {
//            log.warn("No es posible sincronizar RoleGroup " + e);
//        } catch (EtlExecutorException e) {
//            log.warn("No es posible sincronizar RoleGroup " + e, e);
//        } catch (BeansException e) {
//            log.error(e, e);
//        }
        log.info("end");
    }

    private Map<String, String> getParameters() throws IOException {
        Properties properties = getProperties();
        Map<String, String> param = new HashMap<String, String>();
        param.put("url", properties.getProperty("db.url"));
        param.put("userDb", properties.getProperty("db.user"));
        param.put("pwdDb", properties.getProperty("db.pwd"));
        param.put("driver", properties.getProperty("db.driver"));
        return param;
    }

    private Properties getProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new ClassPathResource("/etl/synchronize/security_respaldo.properties").getInputStream());
        return properties;
    }

    public SecurityBackupHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(SecurityBackupHibernateDAO.class);
        }
        return dao;
    }

    public void setDao(SecurityBackupHibernateDAO dao) {
        this.dao = dao;
    }
}
