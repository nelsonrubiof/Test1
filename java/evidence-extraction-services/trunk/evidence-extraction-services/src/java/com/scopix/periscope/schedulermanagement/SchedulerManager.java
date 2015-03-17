/*
 *
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * SchedulerManager.java
 *
 * Created on 13 de junio de 2007, 17:58
 *
 */
package com.scopix.periscope.schedulermanagement;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.transaction.annotation.Transactional;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.extractionmanagement.ExtractionPlan;
import com.scopix.periscope.extractionmanagement.ScopixJob;
import com.scopix.periscope.extractionmanagement.SituationExtractionRequest;
import com.scopix.periscope.extractionmanagement.SituationRequest;
import com.scopix.periscope.extractionmanagement.SituationRequestRange;
import com.scopix.periscope.extractionmanagement.SituationRequestRangeType;
import com.scopix.periscope.extractionmanagement.SituationRequestRealRandom;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.securitymanagement.commands.RealRandomSaveJobCommand;
import com.scopix.periscope.utilities.ScopixUtilities;

/**
 * Es capaz de conversar con una instancia del scheduler para definir los jobs y triggers que permitir
 *
 * @author marko.perich
 */
@SpringBean(rootClass = SchedulerManager.class, initMethod = "init")
@Transactional(rollbackFor = {ScopixException.class})
public class SchedulerManager {

    public static final String GROUPJOB_DAYLIGHT_SAVING = "DaylightSavingJob";
    public static final String SCHEDULER_TYPE_SERVER = "server";
	public static final String SCHEDULER_TYPE_STORE = "store";

    /**
     * properties para el manejo de jobs Automaticos
     */
    public static final String PROPERTIES_UPLOADJOB_UPLOAD_CRON_EXPRESSION = "UploadJob.uploadCronExpression";
    public static final String PROPERTIES_UPLOADJOB_FILEMANAGER_TYPE = "UploadJob.fileManagerType";
    public static final String PROPERTIES_DEVICEUTIL_MOUNT_CRON_EXPRESSION = "DeviceUtil.mountCronExpression";
    public static final String PROPERTIES_DEVICEUTIL_UNMOUNT_CRON_EXPRESSION = "DeviceUtil.unmountCronExpression";
    /**
     * fin properties genericos
     */

    private static Logger log = Logger.getLogger(SchedulerManager.class);
    private static int scheduleDelayInMins = 0;
    private boolean initUploadJob;
    private boolean initMotionDetectionJob;
    private PropertiesConfiguration systemConfiguration;

    /**
     *
     * @throws PeriscopeException
     */
    public void init() throws ScopixException {
        log.info("start");
        startScheduler();
        initializeSchedulerChange();
        //se deja corriendo la subida de archivos 
        createUploadJob(
                getStringProperties(PROPERTIES_UPLOADJOB_UPLOAD_CRON_EXPRESSION),
                getStringProperties(PROPERTIES_UPLOADJOB_FILEMANAGER_TYPE),
                getStringProperties(PROPERTIES_DEVICEUTIL_MOUNT_CRON_EXPRESSION),
                getStringProperties(PROPERTIES_DEVICEUTIL_UNMOUNT_CRON_EXPRESSION));

        log.info("end");
    }

    /**
     * Stop the scheduler and deletes all jobs previously created.
     *
     * @param storeJobGroupName
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    public void initializeScheduler(String storeJobGroupName) throws ScopixException {
        log.debug("start");
        //String[] jobGroups;
        List<String> jobGroups;
        String[] jobsInGroup;
        int i;
        int j;
        try {
            // get scheduler instance;
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler = sf.getScheduler();
            scheduler.standby();

            //delete previously created jobs and triggers
            jobGroups = scheduler.getJobGroupNames();

            for (i = 0; i < jobGroups.size(); i++) {

				// si el job pertenece al grupo DAYLIGHT_SAVING o al store del
				// plan que esta ingresando se borran
                if (storeJobGroupName.equals(jobGroups.get(i)) || GROUPJOB_DAYLIGHT_SAVING.equals(jobGroups.get(i))) {

                    for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroups.get(i)))) {
                        scheduler.deleteJob(jobKey);
                    }

                }
            }
        } catch (SchedulerException ex) {
            throw new ScopixException("Error initializing Scheduler.", ex);
        }
        log.debug("end");

    }

    /**
     * Adds a job to the scheduler
     *
     * @param job
     * @param trigger
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    public void addJob(JobDetail job, Trigger trigger) throws ScopixException {
        log.debug("start [name:" + job.getKey().getName() + "][group:" + job.getKey().getGroup() + "]");

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler;
        try {
            scheduler = sf.getScheduler();
            scheduler.scheduleJob(job, trigger);

            // Set up the listener
            JobListener listener = new CutJobListener();
            scheduler.getListenerManager().addJobListener(listener);

        } catch (SchedulerException ex) {
            throw new ScopixException("Error adding a job to the scheduler", ex);
        }
        log.debug("end [" + job.getKey().getName() + "][NextFireTime: " + trigger.getNextFireTime().toString() + "]");
    }

    /**
     * Starts the scheduler
     *
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    public void startScheduler() throws ScopixException {
        log.debug("start");
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler;
        try {
            scheduler = sf.getScheduler();
            scheduler.start();
        } catch (SchedulerException ex) {
            throw new ScopixException("Error starting scheduler.", ex);
        }
        log.debug("end");

    }

    /**
     * For each task in the plan, call the method that create the jobs and triggers that carry out that task
     *
     * @param plan
     * @param motionDetectionCronExpression
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    @Deprecated
    public void updateExtractionPlanScheduling(ExtractionPlan plan, String motionDetectionCronExpression)
            throws ScopixException {
        log.debug("start");

        if (plan != null) {
            // reinitialize the scheduler. Stop it and clean all triggers and jobs.
            //cancelamos todos los job para el store del plan que viene ingresando
            initializeScheduler("CutJobGroup_" + plan.getStoreName());

            //creamos los job dados los EvidenceExtractionRequest
            createEvidenceExtractionRequestJob(plan);
            //creamos los job dados los SituationExtractionRequest
            createSituationExtractionRequestJob(plan);

            if (!isInitMotionDetectionJob()) {
                createMotionDetectionJob(motionDetectionCronExpression);
                setInitMotionDetectionJob(true);
            }
            // restart the scheduler
            startScheduler();

        } else {
            log.debug("Extraction Plan is null");
        }
        log.debug("end");
    }

    public void updateExtractionPlanScheduling(String motionDetectionCronExpression) throws ScopixException {
        log.debug("start");

        if (!isInitMotionDetectionJob()) {
            createMotionDetectionJob(motionDetectionCronExpression);
            setInitMotionDetectionJob(true);
        }
        // restart the scheduler
        startScheduler();

        log.debug("end");
    }

    /**
     *
     * @param scopixJob
     */
    public void createSimpleJob(ScopixJob scopixJob) {
        try {
            Integer newDayOfWeek = scopixJob.getDayOfWeek();
            Calendar calFechaHoraSystem = Calendar.getInstance();

            calFechaHoraSystem.setTimeInMillis(DateUtils.parseDate(scopixJob.getExecution(), new String[]{"HH:mm:ss"}).getTime());

            JobDetail job = createJob("SimpleJob_" + scopixJob.getDayOfWeek() + "_" + scopixJob.getExecution(),
                    "SimpleJobGroup", SimpleJob.class);

            job.getJobDataMap().put(SimpleJob.LIST_REQUEST_TIME_ZONE, scopixJob.getRequestTimeZones());
            job.getJobDataMap().put(SimpleJob.DAY_OF_WEEK, scopixJob.getDayOfWeek());

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("SimpleJob_" + scopixJob.getDayOfWeek() + "_"
                    + scopixJob.getExecution(), "SimpleJobGroup").
                    withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(newDayOfWeek,
                                    calFechaHoraSystem.get(Calendar.HOUR_OF_DAY),
                                    calFechaHoraSystem.get(Calendar.MINUTE))).forJob(job).build();

            addJob(job, trigger);
        } catch (ScopixException e) {
            log.error(e, e);
        } catch (ParseException e) {
            log.error(e, e);
        }
    }

    private void crearJobSituationExtractionRequest(SituationRequestRange range, ExtractionPlan plan) throws ScopixException {
        Calendar calHoraEvidencia = Calendar.getInstance();
        //el trigger debe ejecutarse según hora local
        Calendar calFechaHoraSystem = Calendar.getInstance();

        for (SituationExtractionRequest request : range.getSituationExtractionRequests()) {
            //Create job se cambia el grupo del Job a "CutJobGroup_" + plan.getStoreName() para que sea solo para el
            //store N mantenemos el grupo para eliminar los job cuando se reinicie para la tienda
            JobDetail job = createJob("CutJob_" + range.getId() + "_" + request.getId(),
                    "CutJobGroup_" + plan.getStoreName(), SituationCutJob.class);
            log.debug("ini create job: " + job.getKey().getName());
            // Passes the Request to the job
            job.getJobDataMap().put(SituationCutJob.SITUATION_EXTRACTION_REQUEST, request);
            //request.getTimeSample() + la duracion del rango
            Date extractionStartTime = DateUtils.addSeconds(request.getTimeSample(), range.getDuration());
            calHoraEvidencia.setTime(extractionStartTime);
            int dayOfWeek = calHoraEvidencia.get(Calendar.DAY_OF_WEEK);
            log.debug("[extractionStartTime:" + extractionStartTime + "]");
            if (plan.getTimeZoneId() != null && plan.getTimeZoneId().length() > 0) {
                //recuperamos la diferencia en hrs entre el timeZone del Store y el TimeZone del Server
                double d = TimeZoneUtils.getDiffInHoursTimeZone(plan.getTimeZoneId());
                log.debug("[timeZoneId:" + plan.getTimeZoneId() + "][difHours:" + d + "]");
                extractionStartTime = DateUtils.addHours(extractionStartTime, (int) d);
            }
            calHoraEvidencia.setTime(extractionStartTime);
            log.debug("[calHoraEvidencia:" + calHoraEvidencia.getTime() + "]");
            int difDayOfWeek = 0;
            if (calHoraEvidencia.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
                difDayOfWeek = calHoraEvidencia.get(Calendar.DAY_OF_WEEK) - dayOfWeek;
            }
            calFechaHoraSystem.setTimeInMillis(System.currentTimeMillis());
            log.debug("[calFechaHoraSystem:" + calFechaHoraSystem.getTime() + "]");
            calFechaHoraSystem.set(Calendar.HOUR_OF_DAY, calHoraEvidencia.get(Calendar.HOUR_OF_DAY));
            calFechaHoraSystem.set(Calendar.MINUTE, calHoraEvidencia.get(Calendar.MINUTE));
            //recuperamos el dayOfWeek en el cual se debe ejecutar el job
            calHoraEvidencia.set(Calendar.DAY_OF_WEEK, range.getDayOfWeek() + difDayOfWeek);
            int newDayOfWeek = calHoraEvidencia.get(Calendar.DAY_OF_WEEK);
            log.debug("[dayOfWeek:" + range.getDayOfWeek() + "][newDayOfWeek:" + newDayOfWeek + "]");
            //The TriggerUtils day is 1 to SUNDAY and 7 to Saturday
//            Trigger trigger = TriggerUtils.makeWeeklyTrigger(newDayOfWeek, calFechaHoraSystem.get(Calendar.HOUR_OF_DAY),
//                    calFechaHoraSystem.get(Calendar.MINUTE));

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("CutJob_" + request.getId(), "CutJobGroup_" + plan.getStoreName()).
                    withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(newDayOfWeek,
                                    calFechaHoraSystem.get(Calendar.HOUR_OF_DAY), calFechaHoraSystem.get(Calendar.MINUTE)))
                    .forJob(job).build();

            addJob(job, trigger);
        }
    }

    private void createEvidenceExtractionRequestJob(ExtractionPlan plan) throws ScopixException {
        log.info("start, extractionPlan=" + plan);

        Calendar calHoraEvidencia = Calendar.getInstance();
        //el trigger debe ejecutarse según hora local
        Calendar calFechaHoraSystem = Calendar.getInstance();

        //schedule a new job for each detail
        //this section is used for request evidence to provider
        for (EvidenceExtractionRequest det : plan.getEvidenceExtractionRequests()) {
            if (!det.getType().equals(EvidenceRequestType.SCHEDULED)) {
                continue;
            }

            //Create job se cambia el grupo del Job a "CutJobGroup_" + plan.getStoreName() para que sea solo para el store N
            JobDetail job = createJob("CutJob_" + det.getId(), "CutJobGroup_" + plan.getStoreName(), CutJob.class);
            log.debug("ini create job: " + job.getKey().getName());
            // Passes the Request to the job
            //job.getJobDataMap().put(CutJob.EVIDENCE_EXTRACTION_REQUEST, det);
            job.getJobDataMap().put(CutJob.EVIDENCE_EXTRACTION_REQUEST_ID, det.getId());
            //seteamos la hora de la toma de la muestra
            Date extractionStartTime = det.getExtractionStartTime();
            calHoraEvidencia.setTime(extractionStartTime);
            int dayOfWeek = calHoraEvidencia.get(Calendar.DAY_OF_WEEK);
            log.debug("[extractionStartTime:" + extractionStartTime + "]");
            if (plan.getTimeZoneId() != null && plan.getTimeZoneId().length() > 0) {
                //recuperamos la diferencia en hrs entre el timeZone del Store y el TimeZone del Server
                double d = TimeZoneUtils.getDiffInHoursTimeZone(plan.getTimeZoneId());
                log.debug("[timeZoneId:" + plan.getTimeZoneId() + "][difHours:" + d + "]");
                extractionStartTime = DateUtils.addHours(extractionStartTime, (int) d);
            }
            calHoraEvidencia.setTime(extractionStartTime);
            log.debug("[calHoraEvidencia:" + calHoraEvidencia.getTime() + "]");
            int difDayOfWeek = 0;
            if (calHoraEvidencia.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
                difDayOfWeek = calHoraEvidencia.get(Calendar.DAY_OF_WEEK) - dayOfWeek;
            }
            calFechaHoraSystem.setTimeInMillis(System.currentTimeMillis());
            log.debug("[calFechaHoraSystem:" + calFechaHoraSystem.getTime() + "]");
            calFechaHoraSystem.set(Calendar.HOUR_OF_DAY, calHoraEvidencia.get(Calendar.HOUR_OF_DAY));
            calFechaHoraSystem.set(Calendar.MINUTE, calHoraEvidencia.get(Calendar.MINUTE));
            //recuperamos el dayOfWeek en el cual se debe ejecutar el job
            calHoraEvidencia.set(Calendar.DAY_OF_WEEK, det.getDayOfWeek() + difDayOfWeek);
            int newDayOfWeek = calHoraEvidencia.get(Calendar.DAY_OF_WEEK);
            log.debug("[dayOfWeek:" + det.getDayOfWeek() + "][newDayOfWeek:" + newDayOfWeek + "]");
            //The TriggerUtils day is 1 to SUNDAY and 7 to Saturday            
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("CutJob_" + det.getId(), "CutJobGroup_" + plan.getStoreName()).
                    withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(newDayOfWeek, calFechaHoraSystem.get(Calendar.HOUR_OF_DAY), calFechaHoraSystem.get(Calendar.MINUTE)))
                    .forJob(job).build();

            addJob(job, trigger);
        }
        log.info("end, extractionPlan=" + plan);
    }

    private void createSituationExtractionRequestJob(ExtractionPlan plan) throws ScopixException {
        log.info("start, extractionPlan=" + plan);
        for (SituationRequest sr : plan.getSituationRequests()) {
            //debemos cargar las Camaras asociadas
            sr.getEvidenceProviderRequests().isEmpty();
            log.info("situation.evidenceProviderRequests:" + sr.getEvidenceProviderRequests().size());
            //solo generamos para situaciones con Random de Camaras
            if (sr.getRandomCamera()) { //revisamos los rangos y generamos request segun corresponda
                for (SituationRequestRange range : sr.getSituationRequestRanges()) {
                    if (!range.getSituationExtractionRequests().isEmpty()) {
                        crearJobSituationExtractionRequest(range, plan);
                    } else if (range.getRangeType() != null
                            && range.getRangeType().equals(SituationRequestRangeType.REAL_RANDOM)) {
                        crearJobRealRandom(range, plan);
                    }
                }
            } else { //de lo contrario revisamos los rangos y generamos solo para rangos RealRandom
                for (SituationRequestRange range : sr.getSituationRequestRanges()) {
                    if (range.getRangeType().equals(SituationRequestRangeType.REAL_RANDOM)) {
                        crearJobRealRandom(range, plan);
                    }
                }
            }

        }
        log.info("end, extractionPlan=" + plan);
    }

    /**
     *
     * @return
     */
    public static int getScheduleDelayInMins() {
        return scheduleDelayInMins;
    }

    /**
     *
     * @param aScheduleDelayInMins
     */
    public static void setScheduleDelayInMins(int aScheduleDelayInMins) {
        scheduleDelayInMins = aScheduleDelayInMins;
    }

    /**
     *
     * @param uploadCronExpression
     * @param uploadFileManagerType
     * @param mountCronExpression
     * @param unmountCronExpression
     * @throws PeriscopeException
     */
    public void createUploadJob(String uploadCronExpression, String uploadFileManagerType,
            String mountCronExpression, String unmountCronExpression) throws ScopixException {
        log.debug("start");
        if (!uploadFileManagerType.equals(ExtractionManager.UPLOAD_FILE_MANAGER_LOCAL)) {
            addJob(uploadCronExpression, "UploadEvidenceJob", UploadEvidenceJob.class);
        } else {
            addJob(mountCronExpression, "MountDeviceJob", MountDeviceJob.class);
            addJob(unmountCronExpression, "UnMountDeviceJob", UnMountDeviceJob.class);
        }
        log.debug("end");
    }

    /**
     *
     * @param motionDetectionCronExpression
     * @throws PeriscopeException
     */
    public void createMotionDetectionJob(String motionDetectionCronExpression) throws ScopixException {
        log.debug("start. motionDetectionCronExpression: " + motionDetectionCronExpression);
        try {
            //agendando la revision de la evidencia de motion detection
            JobDetail job = createJob("MotionDetectionJob", "MotionDetectionGroup", MotionDetectionJob.class);
            CronTrigger trigger = createCronTrigger("MotionDetectionJob", "MotionDetectionGroup", motionDetectionCronExpression);
            addJob(job, trigger);
        } catch (Exception ex) {
            log.debug("Error: " + ex.getMessage());
        }
        log.debug("end");
    }

    private CronTrigger createCronTrigger(String name, String group, String motionDetectionCronExpression) {
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group).
                withSchedule(CronScheduleBuilder.cronSchedule(motionDetectionCronExpression)).build();
        return trigger;
    }

    /**
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws PeriscopeException
     */
    public boolean deleteJob(String jobName, String jobGroup) throws ScopixException {
        log.debug("start [jobNam:" + jobName + "]");
        boolean answer = false;

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler;
        try {
            scheduler = sf.getScheduler();
            answer = scheduler.deleteJob(new JobKey(jobName, jobGroup));

        } catch (SchedulerException ex) {
            throw new ScopixException("Error deleting scheduler.", ex);
        }
        log.debug("end");
        return answer;
    }

    /**
     *
     * @param uploadCronExpression
     * @param jobName
     * @param clase
     * @throws PeriscopeException
     */
    public void addJob(String uploadCronExpression, String jobName, Class clase) throws ScopixException {
        log.info("start [jobName:" + jobName + "][clase:" + clase.getSimpleName() + "]");
        // add job for uploading files
        JobDetail job = createJob(jobName, "UploadGroup", clase);

        // construct trigger
        CronTrigger trigger;
        if (clase == UploadEvidenceJob.class) {
            trigger = createCronTrigger(jobName, "UploadGroup", uploadCronExpression);
//                trigger.setCronExpression(uploadCronExpression);
//                trigger.setName(jobName);
//                //job.getJobDataMap().put("storeName", storeName);
//                trigger.setGroup("UploadGroup");
            addJob(job, trigger);
        } else if (clase == MountDeviceJob.class) {

            trigger = createCronTrigger(jobName, "UploadGroup", uploadCronExpression);
//                trigger.setCronExpression(uploadCronExpression);
//                trigger.setName(jobName);
//                trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW);
//                trigger.setGroup("UploadGroup");
            //job.getJobDataMap().put("storeName", storeName);
            addJob(job, trigger);
        } else if (clase == UnMountDeviceJob.class) {
            trigger = createCronTrigger(jobName, "UploadGroup", uploadCronExpression);
//                trigger.setCronExpression(uploadCronExpression);
//                trigger.setName(jobName);
//                trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW);
//                trigger.setGroup("UploadGroup");
            //job.getJobDataMap().put("storeName", storeName);
            addJob(job, trigger);
        }

        log.info("end");
    }

    /**
     * se modifica para que se ejecute solo cuando se levanta la aplicacion Inicaliza los job para cambio de fecha resumidos en el
     * System.properties Modificar para que las propiedades se levante desde XML timezone.xml Calcular el cambio para cada Store
     * asociado segun timezone del Store
     */
    public void initializeSchedulerChange() {
        log.debug("start");
        try {
            String type = null;
            String timeZoneId = null;
            Integer day = null;
            Integer month = null;
            Integer dayOfWeek = null;
            Integer position = null;
            Integer hourOfDay = null;
            //cargamos las propiedades desde el xml
            XMLConfiguration config = new XMLConfiguration("timezone.xml");
            List<HierarchicalConfiguration> l = config.configurationsAt("timezone");
            for (HierarchicalConfiguration sub : l) {
                type = sub.getString("[@type]");
                //name = sub.getString("name");
                timeZoneId = sub.getString("timezoneid");
                day = sub.getInt("day");
                month = sub.getInt("month");
                dayOfWeek = sub.getInt("dayofweek");
                position = sub.getInt("position");
                hourOfDay = sub.getInt("hourofday");
                //Date dateHour = DateUtils.parseDate(hour, new String[]{"HH:mm"});
                if (type.equals(SCHEDULER_TYPE_STORE)) {
                    List<HierarchicalConfiguration> l2 = sub.configurationsAt("stores");
                    Set<String> storesNames = new HashSet<String>();
                    for (HierarchicalConfiguration sub2 : l2) {
                        String storeName = sub2.getString("store");
                        storesNames.add(storeName);
                    }
                    createJobSchedulerChange(month, day, dayOfWeek, position, hourOfDay, storesNames, timeZoneId);
                } else if (type.equals(SCHEDULER_TYPE_SERVER)) {
                    createJobSchedulerChange(month, day, dayOfWeek, position, hourOfDay, null, null);
                }
            }

            //Se crea reload para el año siguinte el 1-1 a las 00:01
            JobDetail job = createJob("DaylightSavingJob_" + 1 + "_" + 1, GROUPJOB_DAYLIGHT_SAVING, DaylightSavingJob.class);
            Calendar c = Calendar.getInstance();
            Date execute = com.scopix.periscope.periscopefoundation.util.DateUtils.encodeDate(c.get(Calendar.YEAR) + 1, 0, 1);
            execute = DateUtils.addHours(execute, 0);
            execute = DateUtils.addMinutes(execute, 1);
            Trigger trigger = new SimpleTriggerImpl("DaylightSavingJob_" + 1 + "_" + 1, GROUPJOB_DAYLIGHT_SAVING, execute);
//            trigger.setStartTime(execute);
//            trigger.setEndTime(DateUtils.addMinutes(execute, 1));
            addJob(job, trigger);
        } catch (ConfigurationException e) {
            log.warn("No es posible cargar constantes de cambio de fecha desde system.properties " + e);
        } catch (NumberFormatException e) {
            log.warn("No inicializado jobs " + e);
        } catch (ScopixException e) {
            log.warn("No inicializado jobs " + e);
        }
        log.debug("end");
    }

    private void createJobSchedulerChange(int month, int day, int dayOfWeek, int pos, int hourOfDay, Set<String> storeNames,
            String timeZoneId) throws ScopixException {
        log.debug("param [month:" + month + "][day:" + day + "][dayOfWeek:" + dayOfWeek + "][pos:" + pos + "]"
                + "[hourOfDay:" + hourOfDay + "]");
        if (timeZoneId != null) {
            double d = TimeZoneUtils.getDiffInHoursTimeZone(timeZoneId);
            log.debug("[timeZoneId:" + timeZoneId + "][difHours:" + d + "]");
            hourOfDay = (int) (hourOfDay + d);
        }

        Calendar c = Calendar.getInstance();
        Date dateInit = com.scopix.periscope.periscopefoundation.util.DateUtils.encodeDate(c.get(Calendar.YEAR), month - 1, day);

        Date executeDate = TimeZoneUtils.getNextScheduleChange(dayOfWeek, dateInit, pos);
        executeDate = DateUtils.setHours(executeDate, 0);
        executeDate = DateUtils.addHours(executeDate, hourOfDay);
        //Create job
        //log.debug("preparamos para job [day:" + DateFormatUtils.format(executeDate, "yyyy-MM-dd HH:mm:ss") + "]");
        Date now = new Date();
        if (executeDate.after(now)) {
            String subfixJob = "";
            if (storeNames != null && !storeNames.isEmpty()) {
                subfixJob = "_" + StringUtils.join(storeNames, "_");
            }
            log.debug("creando job [day:" + DateFormatUtils.format(executeDate, "yyyy-MM-dd HH:mm:ss") + "]");
            JobDetail job = createJob("DaylightSavingJob_" + day + "_" + month + subfixJob, GROUPJOB_DAYLIGHT_SAVING,
                    DaylightSavingJob.class);

            Trigger trigger = new SimpleTriggerImpl("DaylightSavingJob_" + day + "_" + month + subfixJob, GROUPJOB_DAYLIGHT_SAVING, executeDate);
            job.getJobDataMap().put("store_names", storeNames);
            job.getJobDataMap().put("timeZoneId", timeZoneId);
//            trigger.setStartTime(executeDate);
//
//            trigger.setEndTime(DateUtils.addMinutes(executeDate, 1));
            addJob(job, trigger);
        }
    }

    /**
     *
     * @return
     */
    public boolean isInitUploadJob() {
        return initUploadJob;
    }

    /**
     *
     * @param initUploadJob
     */
    public void setInitUploadJob(boolean initUploadJob) {
        this.initUploadJob = initUploadJob;
    }

    /**
     *
     * @return
     */
    public boolean isInitMotionDetectionJob() {
        return initMotionDetectionJob;
    }

    /**
     *
     * @param initMotionDetectionJob
     */
    public void setInitMotionDetectionJob(boolean initMotionDetectionJob) {
        this.initMotionDetectionJob = initMotionDetectionJob;
    }

    private void crearJobRealRandom(SituationRequestRange range, ExtractionPlan plan) {
        //recorremos el rango y generamos job independientes para cada segmento
        log.debug("start range " + range.getId() + " initialTime=" + DateFormatUtils.format(range.getInitialTime(), "HH:mm")
                + " endTime=" + DateFormatUtils.format(range.getEndTime(), "HH:mm"));
        Calendar calNow = Calendar.getInstance();
        int largoBloque = range.getFrecuency() / range.getSamples();
        int duracionMinutes = 1;
        if (range.getDuration() >= 60) {
            duracionMinutes = (range.getDuration() / 60);
        }

        //generamos los posibles terminos en minutos 
        //es decir desde el punto de inicio en cuantos minutos se puede comenzar
        Integer[] topeMuestraMinutos = new Integer[range.getSamples()];
        topeMuestraMinutos[0] = largoBloque - duracionMinutes;
        for (int i = 1; i < range.getSamples(); i++) {
            topeMuestraMinutos[i] = topeMuestraMinutos[i - 1] + largoBloque;
        }
        log.info("topeMuestraMinutos " + StringUtils.join(topeMuestraMinutos, ","));
        //desde el incio de la frecuencia agregamos los rangos intermedios para la toma de las muestras
        Date initFrecuency = range.getInitialTime();

        //revisar que pasa con el untimo bloque del rango
        while (initFrecuency.before(range.getEndTime())) {
            double diff = ScopixUtilities.diffDateInMin(initFrecuency, range.getEndTime());
            boolean validarDiff = diff < range.getFrecuency();
            Date iniMuestra = initFrecuency;
            boolean endCiclo = false;
            for (int tope : topeMuestraMinutos) {
                if (validarDiff) {
                    if (tope > diff) {
                        tope = (int) diff;
                        endCiclo = true;
                    }
                }
                Date horaTomaMuestra = ScopixUtilities.getRandomDateBetween(iniMuestra,
                        DateUtils.addMinutes(initFrecuency, tope));
                log.debug("horaTomaMuestra:" + horaTomaMuestra + " initFrecuency:" + initFrecuency + " tope:" + tope);
                if (horaTomaMuestra.after(DateUtils.addMinutes(range.getEndTime(), -duracionMinutes))) {
                    log.error("tomaMuestra=" + DateFormatUtils.format(horaTomaMuestra, "HH:mm")
                            + " endTime=" + DateFormatUtils.format(range.getEndTime(), "HH:mm")
                            + " duracionMinutes=" + duracionMinutes);
                }

                Date horaExecution = ScopixUtilities.calculateDateByStore(horaTomaMuestra, plan.getTimeZoneId(),
                        range.getDayOfWeek());
                log.debug("horaExecution:" + horaTomaMuestra);
                Calendar calExecute = Calendar.getInstance();
                calExecute.setTime(horaExecution);
                if (calNow.getTimeInMillis() > calExecute.getTimeInMillis()) {
                    calExecute.add(Calendar.DAY_OF_WEEK, 7);
                }
                RealRandomSaveJobCommand command = new RealRandomSaveJobCommand();

                SituationRequestRealRandom realRandom = command.execute(iniMuestra, DateUtils.addMinutes(initFrecuency, tope),
                        plan.getTimeZoneId(), range, horaTomaMuestra, calExecute.getTime());

				// creamos Simple Job para la hora x
                JobDetail job = createJob("CutJob_RealRandom_" + realRandom.getId(), "CutJobGroup_" + plan.getStoreName(),
                        RealRandomCutJob.class);
                log.debug("ini create job: " + job.getKey().getName());
                //job.getJobDataMap().put("realRandom", realRandom);
                job.getJobDataMap().put("realRandomId", realRandom.getId());

                Trigger trigger = new SimpleTriggerImpl("CutJob_RealRandom_" + realRandom.getId(),
                        "CutJobGroup_" + plan.getStoreName(), calExecute.getTime());

                try {
                    addJob(job, trigger);
                } catch (ScopixException e) {
                    log.error("No es posible crear trigger " + e);
                }

                iniMuestra = DateUtils.addMinutes(initFrecuency, tope + duracionMinutes);
                if (endCiclo) {
                    break;
                }
            }
            initFrecuency = DateUtils.addMinutes(initFrecuency, range.getFrecuency());
        }
        log.debug("end");
    }

    /**
     *
     * @param realRandom
     * @param group
     */
    public void newJobRealRandom(SituationRequestRealRandom realRandom, String group) {
        log.info("start realRandom:" + realRandom);
        Calendar calNow = Calendar.getInstance();
        Date horaTomaMuestra = ScopixUtilities.getRandomDateBetween(realRandom.getInitBlock(), realRandom.getEndBlock());
        // Passes the Request to the job

        Date horaExecution = ScopixUtilities.calculateDateByStore(horaTomaMuestra, realRandom.getTimeZoneId(),
                calNow.get(Calendar.DAY_OF_WEEK));
        Calendar calExecute = Calendar.getInstance();
        calExecute.setTime(horaExecution);
        //siempre agendamos para 7 dias mas 
        calExecute.add(Calendar.DAY_OF_WEEK, 7);
        //debemos grabar el nuevo realRandom en base de datos
        RealRandomSaveJobCommand command = new RealRandomSaveJobCommand();
        SituationRequestRealRandom realRandomNew = command.execute(realRandom.getInitBlock(), realRandom.getEndBlock(),
                realRandom.getTimeZoneId(), realRandom.getSituationRequestRange(), horaTomaMuestra, calExecute.getTime());
        JobDetail job = createJob("CutJob_RealRandom_" + realRandomNew.getId(), group, RealRandomCutJob.class);
        log.debug("ini create job: " + job.getKey().getName());
        job.getJobDataMap().put("realRandom", realRandomNew);
        //creamos nuevo job para ejecutarse
        Trigger trigger = new SimpleTriggerImpl("CutJob_RealRandom_" + realRandomNew.getId(), group, calExecute.getTime());
        try {
            addJob(job, trigger);
        } catch (ScopixException e) {
            log.error("No es posible crear trigger " + e, e);
        }
        log.info("end");
    }

    /**
     * @return the systemConfiguration
     */
    public PropertiesConfiguration getSystemConfiguration() {
        if (systemConfiguration == null) {
            try {
                systemConfiguration = new PropertiesConfiguration("system.properties");
                systemConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
            } catch (ConfigurationException e) {
                log.error("No es posible cargar propiedades " + e, e);
            }
        }
        return systemConfiguration;
    }

    /**
     * @param systemConfiguration the systemConfiguration to set
     */
    public void setSystemConfiguration(PropertiesConfiguration systemConfiguration) {
        this.systemConfiguration = systemConfiguration;
    }

    /**
     *
     * @param key
     * @return
     */
    public String getStringProperties(String key) {
        return getSystemConfiguration().getString(key, "");
    }

    private JobDetail createJob(String motionDetectionJob, String motionDetectionGroup, Class<? extends Job> aClass) {
        JobDetail job = JobBuilder.newJob(aClass).withIdentity(motionDetectionJob, motionDetectionGroup).build();
        return job;
    }

    public void createRealRandomJob(ScopixJob scopixJob) {
        try {
            Integer newDayOfWeek = scopixJob.getDayOfWeek();
            Calendar calFechaHoraSystem = Calendar.getInstance();

            calFechaHoraSystem.setTimeInMillis(DateUtils.parseDate(scopixJob.getExecution(), new String[]{"HH:mm:ss"}).getTime());
            //con esto los jobs se ejecutaran 10 segundos antes de la partida del rango 
            //para dar tiempo a los calculos dentro del rango
            calFechaHoraSystem.add(Calendar.MINUTE, getSystemConfiguration().getInt("RANDOM.before", 30) * -1);

            JobDetail job = createJob("RealRandomJob_" + scopixJob.getDayOfWeek() + "_" + scopixJob.getExecution(),
                    "RealRandomJobGroup", RealRandomJob.class);

            job.getJobDataMap().put(SimpleJob.LIST_REQUEST_TIME_ZONE, scopixJob.getRequestTimeZones());
            job.getJobDataMap().put(SimpleJob.DAY_OF_WEEK, scopixJob.getDayOfWeek());

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("RealRandomJob_" + scopixJob.getDayOfWeek() + "_"
                    + scopixJob.getExecution(), "RealRandomJob").
                    withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(newDayOfWeek,
                                    calFechaHoraSystem.get(Calendar.HOUR_OF_DAY),
                                    calFechaHoraSystem.get(Calendar.MINUTE))).forJob(job).build();

            addJob(job, trigger);
        } catch (ScopixException e) {
            log.error(e, e);
        } catch (ParseException e) {
            log.error(e, e);
        }
    }

}
