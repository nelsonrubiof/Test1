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
 *  ExportUsersToJoomlaCommand.java
 * 
 *  Created on 05-11-2010, 10:01:33 AM
 * 
 */
package com.scopix.periscope.securitymanagement.commands;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.core.io.ClassPathResource;
import scriptella.configuration.ConfigurationFactory;
import scriptella.execution.EtlExecutor;
import scriptella.execution.EtlExecutorException;

/**
 *
 * @author nelson
 */
public class ExportUsersToJoomlaCommand {

    private static Logger log = Logger.getLogger(ExportUsersToJoomlaCommand.class);

    public void execute(String corporateName) {
        log.info("start");
        try {
            EtlExecutor exec = (EtlExecutor) SpringSupport.getInstance().findBean("executorJoomla");
            
            Properties properties = getProperties(corporateName);
            Map<String, String> param = new HashMap<String, String>();
            param.put("url", properties.getProperty("joomla.db.url")); //"jdbc:mysql://localhost:3306/joomla15"
            param.put("userDb", properties.getProperty("joomla.db.user")); //root
            param.put("pwdDb", properties.getProperty("joomla.db.pwd")); //"root"
            param.put("corporateId", properties.getProperty("joomla.corporateId")); //"6"


            ConfigurationFactory cf = new ConfigurationFactory();
            cf.setExternalParameters(param);
            cf.setResourceURL(exec.getConfiguration().getDocumentUrl());
            exec.setConfiguration(cf.createConfiguration());

            exec.execute();
        } catch (IOException e) {
            log.warn("No es posible enviar usuario a joomla " + e);
        } catch (EtlExecutorException e) {
            log.warn("No es posible enviar usuario a joomla " + e);
        } catch (BeansException e) {
            log.error(e);
        }
        log.info("end");

    }

    private Properties getProperties(String corporateName) throws IOException {
        //el nombre no debe contener mayusculas ni espacios
        corporateName = StringUtils.replace(corporateName, " ", "_").toLowerCase();
        Properties properties = new Properties();
        properties.load(new ClassPathResource("/etl/properties/" + corporateName + ".properties").getInputStream());
        return properties;
    }
}
