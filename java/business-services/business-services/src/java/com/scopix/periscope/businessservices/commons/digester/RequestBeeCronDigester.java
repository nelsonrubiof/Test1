/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.businessservices.commons.digester;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
 * @author Nelson
 */
@SpringBean(rootClass = RequestBeeCronDigester.class)
public class RequestBeeCronDigester implements InitializingBean {

    private static final Logger log = Logger.getLogger(RequestBeeCronDigester.class);
    private PropertiesConfiguration configuration;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("start");
        generateCronJob();
        log.info("end");
    }

    private void generateCronJob() {
        log.info("start");
        String[] execution = getConfiguration().getStringArray("bee_cron.execution");
        for (String hourMin : execution) {
            String[] data = StringUtils.split(hourMin, ":");
            try {
                JobDetail job = new JobDetail("RequestBeeCronDigester_" + hourMin, "RequestBeeCronDigester", RequestBeeCronJob.class);
                Trigger trigger = TriggerUtils.makeDailyTrigger(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                trigger.setName("RequestBeeCronDigesterTrigger_" + hourMin);

                addJob(job, trigger);
            } catch (NumberFormatException e) {
                log.error("No create Job to BEE Cron " + e, e);
            } catch (ScopixException e) {
                log.error("No create Job to BEE  Cron " + e, e);
            }
        }

        log.info("end");
    }

    public void addJob(JobDetail job, Trigger trigger) throws ScopixException {
        log.info("start");

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler;
        try {
            scheduler = sf.getScheduler();
            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException ex) {
            throw new ScopixException("Error adding a job to the scheduler", ex);
        }
        log.info("end [" + job.getName() + "][NextFireTime: " + trigger.getNextFireTime().toString() + "]");
    }

    /**
     * @return the configuration
     */
    public PropertiesConfiguration getConfiguration() {
        if (configuration == null) {
            try {
                configuration = new PropertiesConfiguration("bee_cron.properties");
                configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
            } catch (ConfigurationException e) {
                log.warn("Error inicializando archivos de properties epc_cron.properties " + e);
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
