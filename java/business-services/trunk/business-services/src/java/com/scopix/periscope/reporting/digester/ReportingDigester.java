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
 *  ReportingDigester.java
 * 
 *  Created on 14-02-2011, 06:03:19 PM
 * 
 */
package com.scopix.periscope.reporting.digester;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SystemConfig;
import java.text.ParseException;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 *
 * @author nelson
 */
@SpringBean(rootClass = ReportingDigester.class) 
public class ReportingDigester implements InitializingBean {

    private static Logger log = Logger.getLogger(ReportingDigester.class);
    private PropertiesConfiguration configuration;

    @Override
    public void afterPropertiesSet() throws Exception {
        //generateTrigger();
        generateCronTrigger();
    }

    private void generateTrigger() {
        //recuperamos el profile.server.properties y buscamos el campo de inicio de upload
        try {
            String hourMin = SystemConfig.getStringParameter("reporting.upload.time"); //HH:mm
            String[] data = StringUtils.split(hourMin, ":");


            JobDetail job = new JobDetail("ReportingDataJob", "ReportingDataUpload", ReportingDataJob.class);
            Trigger trigger = TriggerUtils.makeDailyTrigger(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
            trigger.setName("ReportingDigesterTrigger");

            addJob(job, trigger);
        } catch (Exception e) {
            log.warn("no es posible generar trigger " + e, e);
        }
    }

    public void generateCronTrigger() {
        log.info("start");
        try {
            if (getConfiguration().getBoolean("uploading.state.active")) { //solo si la subida esta activa
                CronTrigger trigger = new CronTrigger();
                trigger.setCronExpression(getConfiguration().getString("uploading.interval"));
                trigger.setName("ReportingDigesterTrigger");
                trigger.setGroup("ReportingDigesterTrigger");

                JobDetail job = new JobDetail("ReportingUploadAutomaticJob", "ReportingUploadAutomaticJob",
                        ReportingDataJob.class);

                addJob(job, trigger);
            }
        } catch (ParseException e) {
            log.warn("no es posible generar trigger " + e, e);
        } catch (ScopixException e) {
            log.warn("no es posible generar trigger " + e, e);
        }
        log.info("end");
    }

    public void removeCronTrigger() {
        log.info("start");
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler = sf.getScheduler();
            scheduler.standby();
            scheduler.deleteJob("ReportingUploadAutomaticJob", "ReportingUploadAutomaticJob");
            scheduler.start();
        } catch (SchedulerException e) {
            log.error("No es posible borrar job " + e, e);
        }
        log.info("end");
    }

    public void addJob(JobDetail job, Trigger trigger) throws ScopixException {
        log.debug("start");

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler;
        try {
            scheduler = sf.getScheduler();
            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException ex) {
            throw new ScopixException("Error adding a job to the scheduler", ex);
        }
        log.debug("end [" + job.getName() + "][NextFireTime: " + trigger.getNextFireTime().toString() + "]");
    }

    /**
     * Recupera la configuracion para ReportingUloading Automatico si no esta cargada
     *
     * @return the configuration
     */
    public PropertiesConfiguration getConfiguration() {
        if (configuration == null) {
            try {
                configuration = new PropertiesConfiguration("reporting_upload.properties");
                configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
            } catch (ConfigurationException e) {
                log.warn("Error inicializando archivos de properties reporting_uploading.properties " + e);
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
