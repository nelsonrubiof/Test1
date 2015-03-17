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
 *  SynchronizeToSecurityCommand.java
 * 
 *  Created on 06-12-2010, 05:50:44 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import scriptella.configuration.ConfigurationFactory;
import scriptella.driver.spring.EtlExecutorBean;
import scriptella.execution.EtlExecutorException;

/**
 *
 * @author nelson
 */
public class SynchronizeToSecurityCommand {

    private static Logger log = Logger.getLogger(SynchronizeToSecurityCommand.class);

    public void execute() {
        log.debug("start");
        try {
            //de esta forma habilitamos la recarga del properties si este ha sido cambiado
            PropertiesConfiguration pc = new PropertiesConfiguration(new ClassPathResource("/etl/etl.properties").getFile());
            pc.setReloadingStrategy(new FileChangedReloadingStrategy());

//            Properties prop = new Properties();
//            prop.load(new ClassPathResource("/etl/etl.properties").getInputStream());
            Map<String, String> param = new HashMap<String, String>();
            param.put("driver", pc.getString("db.driver", ""));
            param.put("url", pc.getString("db.url", ""));
            param.put("user", pc.getString("db.user", ""));
            param.put("pwd", pc.getString("db.pwd", ""));
//            param.put("driver", prop.getProperty("db.driver"));
//            param.put("url", prop.getProperty("db.url"));
//            param.put("user", prop.getProperty("db.user"));
//            param.put("pwd", prop.getProperty("db.pwd"));

            ConfigurationFactory cf = new ConfigurationFactory();
            cf.setExternalParameters(param);

            EtlExecutorBean exec = (EtlExecutorBean) SpringSupport.getInstance().findBean("synchronizeEtl");
            cf.setResourceURL(exec.getConfiguration().getDocumentUrl());
            exec.setConfiguration(cf.createConfiguration());
//            exec.setProperties(param);
            exec.execute();
        } catch (ConfigurationException e) {
            log.error(e, e);
        } catch (IOException e) {
            log.error(e, e);
        } catch (EtlExecutorException e) {
            log.error(e, e);
        }
        log.debug("end");
    }
}
