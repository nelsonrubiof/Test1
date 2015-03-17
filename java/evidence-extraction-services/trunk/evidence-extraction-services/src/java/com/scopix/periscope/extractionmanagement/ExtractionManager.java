/*
 * Copyright (c) 2007, SCOPIX. All rights reserved. This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and disclosure. It is protected by copyright, patent
 * and other intellectual and industrial property laws. Copy, reverse engineering, disassembly or decompilation of all or part of
 * it, except to the extent required to obtain interoperability with other independently created software as specified by a
 * license agreement, is prohibited. ExtractionManager.java Created on 19-05-2008, 03:42:50 PM
 */
package com.scopix.periscope.extractionmanagement;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.evidencemanagement.services.webservices.EvidenceWebService;
import com.scopix.periscope.evidencemanagement.services.webservices.client.EvidenceWebServiceClient;
import com.scopix.periscope.extractionmanagement.commands.ArecontImageExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.AxisGenericVideoExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.AxisP3301ImageExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.BrickcomImageExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.BroadwareHTTPVideoExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.BroadwareHTTPVideoFileReadyCommand;
import com.scopix.periscope.extractionmanagement.commands.BroadwareHTTPVideoFileReadyThread;
import com.scopix.periscope.extractionmanagement.commands.BroadwareImageExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.BroadwareImageFileReadyCommand;
import com.scopix.periscope.extractionmanagement.commands.BroadwareVideoExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.BroadwareVideoFileReadyCommand;
import com.scopix.periscope.extractionmanagement.commands.CheckMountDeviceCommand;
import com.scopix.periscope.extractionmanagement.commands.CheckPartitionExistCommand;
import com.scopix.periscope.extractionmanagement.commands.Cisco3520F_1_2_1ImageExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.Cisco7ImageExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.Cisco7VideoExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.CiscoPeopleCountingExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.CiscoPeopleCountingFileReadyCommand;
import com.scopix.periscope.extractionmanagement.commands.CognimaticsPeopleCounter141ExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.CognimaticsPeopleCounter150ExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.CognimaticsPeopleCounter212ExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.DeleteEvidencePoolExecutor;
import com.scopix.periscope.extractionmanagement.commands.DownloadVideoConverterFileReadyCommand;
import com.scopix.periscope.extractionmanagement.commands.EvidenceExtractionThread;
import com.scopix.periscope.extractionmanagement.commands.ExtractEvidencePoolExecutor;
import com.scopix.periscope.extractionmanagement.commands.FindSituationsCommand;
import com.scopix.periscope.extractionmanagement.commands.FindSituationsSensorDateCommand;
import com.scopix.periscope.extractionmanagement.commands.FormatDeviceCommand;
import com.scopix.periscope.extractionmanagement.commands.GetAllJobsCommand;
import com.scopix.periscope.extractionmanagement.commands.GetEvidenceExtractionRequestBySituationCommand;
import com.scopix.periscope.extractionmanagement.commands.GetEvidenceExtractionRequestCommand;
import com.scopix.periscope.extractionmanagement.commands.GetListenerJobsCommand;
import com.scopix.periscope.extractionmanagement.commands.GetProcessIdCommand;
import com.scopix.periscope.extractionmanagement.commands.GetRealRandomJobsCommand;
import com.scopix.periscope.extractionmanagement.commands.GetSituationRequestCommanand;
import com.scopix.periscope.extractionmanagement.commands.GetSituationRequestRealRandomCommand;
import com.scopix.periscope.extractionmanagement.commands.KumGoImageExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.ListEvidenceExtractionRequestCommand;
import com.scopix.periscope.extractionmanagement.commands.ListSituationEvidenceExtractionRequestCommand;
import com.scopix.periscope.extractionmanagement.commands.ListenerThread;
import com.scopix.periscope.extractionmanagement.commands.MountExternalDeviceCommand;
import com.scopix.periscope.extractionmanagement.commands.NextLevel3VideoExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.NextLevelVideoExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.NextLevelVideoFileReadyCommand;
import com.scopix.periscope.extractionmanagement.commands.PeopleCountingExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.ReadUrlPHPImageExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.ReadUrlPHPXmlExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.UnMountExternalDeviceCommand;
import com.scopix.periscope.extractionmanagement.commands.UploadEvidenceCommand;
import com.scopix.periscope.extractionmanagement.commands.UploadEvidencePoolExecutor;
import com.scopix.periscope.extractionmanagement.commands.UploadEvidenceToFileCommand;
import com.scopix.periscope.extractionmanagement.commands.VMSGatewayDownloadThread;
import com.scopix.periscope.extractionmanagement.commands.VMSGatewayVideoExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.VMSGatewayVideoFileReadyCommand;
import com.scopix.periscope.extractionmanagement.commands.VadaroEvidenceInjectCommand;
import com.scopix.periscope.extractionmanagement.commands.VadaroPeopleCountingExtractionCommand;
import com.scopix.periscope.extractionmanagement.commands.VmsGatewayMarkEvidenceFileCommand;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.extractionmanagement.dto.EvidenceRequestVO;
import com.scopix.periscope.extractionmanagement.dto.SituationMetricDTO;
import com.scopix.periscope.extractionplanmanagement.EServerDAO;
import com.scopix.periscope.extractionplanmanagement.commands.dao.EvidenceExtractionRequestDAO;
import com.scopix.periscope.http.HttpSupport;
import com.scopix.periscope.nextlevel.NextLevelManager;
import com.scopix.periscope.nextlevel.utilities.GetClipFileThread;
import com.scopix.periscope.nextlevel.utilities.NextLevel3GetClipFileThread;
// import com.scopix.periscope.periscopefoundation.evidence_common.exception.InvalidParameter;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.scheduler.exception.FileManagerException;
import com.scopix.periscope.scheduler.exception.UploadJobException;
import com.scopix.periscope.scheduler.fileManager.FileManager;
import com.scopix.periscope.scheduler.fileManager.FileManagerFactory;
import com.scopix.periscope.scheduler.fileManager.LocalFileManager;
import com.scopix.periscope.schedulermanagement.CutJob;
import com.scopix.periscope.schedulermanagement.CutJobListener;
import com.scopix.periscope.schedulermanagement.SchedulerManager;
import com.scopix.periscope.schedulermanagement.UploadEvidenceJob;
import com.scopix.periscope.utilities.ScopixUtilities;
import com.scopix.periscope.vadaro.VadaroEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 *
 * @author marko.perich
 */
@SpringBean(rootClass = ExtractionManager.class, initMethod = "init")
@Transactional(rollbackFor = {ScopixException.class})
public class ExtractionManager {
    
    public static final String POOL_CLIP_NEXTLEVEL_POOL_EXECUTOR = "clipNextLevelPoolExecutor";
    public static final String POOL_DELETE_EVIDENCE_POOL_EXECUTOR = "deleteEvidencePoolExecutor";
    public static final String POOL_EXTRACT_BROADWARE_EVIDENCE_POOL_EXECUTOR = "extractBroadWareEvidencePoolExecutor";
    public static final String PROPERTIES_DOWNLOAD_BROADWARE_HTTP_MAX_THREADS = "BroadwareCallback.downloadMaxThreads";
    public static final String POOL_EXTRACT_BROADWARE_HTTP_EVIDENCE_POOL_EXECUTOR = "extractBroadwareHttpEvidencePoolExecutor";
    
    public static final String POOL_EXTRACT_CISCO7_VIDEO_EVIDENCE_POOL_EXECUTOR = "extractCisco7VideoEvidencePoolExecutor";
    public static final String POOL_EXTRACT_CISCO7_IMAGE_EVIDENCE_POOL_EXECUTOR = "extractCisco7ImageEvidencePoolExecutor";
    public static final String POOL_EXTRACT_NEXTLEVEL_EVIDENCE_POOL_EXECUTOR = "extractNextLevelEvidencePoolExecutor";
    public static final String POOL_EXTRACT_CISCO_PCOUNTING_EVIDENCE_POOL_EXECUTOR = "extractCiscoPCountingEvidencePoolExecutor";
    public static final String POOL_LISTEN_VADARO_EVENTS_EXECUTOR = "listenVadaroEventsPoolExecutor";
    public static final String POOL_EXTRACT_CISCO_PCOUNTING_FREADY_POOL_EXECUTOR = "extractCiscoPCountingFReadyPoolExecutor";

    /*
     * carlos polo 19-oct-2012. Se crea variable para que sirva de llave al momento de crear el pool de hilos para la cola de
     * NextLevel en la nueva versión 3
     */
    public static final String POOL_CLIP_NEXTLEVEL3_POOL_EXECUTOR = "clipNextLevel3PoolExecutor";
    public static final String POOL_EXTRACT_NEXTLEVEL3_EVIDENCE_POOL_EXECUTOR = "extractNextLevel3EvidencePoolExecutor";
    public static final String POOL_UPLOAD_EVIDENCE_POOL_EXECUTOR = "uploadEvidencePoolExecutor";
    public static final String POOL_DOWNLOAD_VMSGATEWAY_POOL_EXECUTOR = "downloadVmsGatewayPoolExecutor";
    public static final String PROPERTIES_UPLOADJOB_CORPORATE = "UploadJob.corporate";
    public static final String PROPERTIES_UPLOADJOB_FILEMANAGER_TYPE = "UploadJob.fileManagerType";
    public static final String PROPERTIES_UPLOADJOB_UPLOAD_HOST = "UploadJob.uploadHost";
    public static final String PROPERTIES_UPLOADJOB_UPLOAD_LOCAL_DIR = "UploadJob.uploadLocalDir";
    public static final String PROPERTIES_UPLOADJOB_UPLOAD_PASSWORD = "UploadJob.uploadPassword";
    public static final String PROPERTIES_UPLOADJOB_UPLOAD_USER = "UploadJob.uploadUser";
    public static final String PROPERTIES_UPLOADJOB_WAIT_TIME = "UploadJob.waitTime";
    public static final String PROPERTIES_UPLOADJOB_PROTOCOL_TO_UPLOAD = "UploadJob.protocolToUpload";
    public static final String PROPERTIES_UPLOADJOB_UPLOAD_CRON_EXPRESSION = "UploadJob.uploadCronExpression";
    public static final String PROPERTIES_UPLOADJOB_UPLOAD_MAX_THREADS = "UploadJob.uploadMaxThreads";
    public static final String PROPERTIES_UPLOADJOB_UPLOAD_REMOTE_DIR = "UploadJob.uploadRemoteDir";
    public static final String PROPERTIES_DEVICEUTIL_MOUNT_CRON_EXPRESSION = "DeviceUtil.mountCronExpression";
    public static final String PROPERTIES_DEVICEUTIL_ROOT_FOLDER = "DeviceUtil.rootFolder";
    public static final String PROPERTIES_DEVICEUTIL_UNMOUNT_CRON_EXPRESSION = "DeviceUtil.unmountCronExpression";
    public static final String PROPERTIES_EVIDENCE_WEB_SERVICE_URL = "EvidenceWebService.URL";
    public static final String PROPERTIES_MOTIONDETECTION_DELAY = "MotionDetection.delay";
    public static final String PROPERTIES_MOTIONDETECTION_CAMERAS_LOCAL_DIR = "MotionDetection.camerasLocalDir";
    public static final String PROPERTIES_MOTIONDETECTION_CRON_EXPRESSION = "MotionDetection.cronExpression";
    public static final String PROPERTIES_DOWNLOAD_VMS_MAX_THREADS = "VMSGatewayCallback.downloadMaxThreads";
    public static final String PROPERTIES_CISCO7_VIDEO_MAX_THREADS = "cisco7.video.downloadMaxThreads";
    public static final String PROPERTIES_CISCO7_IMAGE_MAX_THREADS = "cisco7.image.downloadMaxThreads";
    public static final String PROPERTIES_CISCO_PCOUNTING_MAX_THREADS = "cisco.pcounting.downloadMaxThreads";
    
    public static final String PROPERTIES_VADARO_LISTENER_MAX_THREADS = "vadaro.listener.maxThreads";
    public static final String PROPERTIES_VADARO_LISTENER_RESTART_MINUTES_DELAY = "vadaro.listener.restartDelay.minutes";
    public static final String PROPERTIES_VADARO_LISTENER_RECONNECT_SECONDS_DELAY = "vadaro.listener.reconnecttDelay.seconds";
    
    private static Logger log = Logger.getLogger(ExtractionManager.class);
    // to upload files from extraction agent to evidence manager
    public static final String EVIDENCE_SERVER_WEBSERVICE_KEY = "EVIDENCE_SERVER_WEBSERVICE_KEY";
    public static final String UPLOAD_FILE_MANAGER_LOCAL = "LOCAL";
    public static final String DEVICE_MOUNT = "mount";
    public static final String DEVICE_UNMOUNT = "unmount";
    public static final String DEVICE_INFORMATION = "info";
    public static final String DEVICE_FORMAT = "format";
    private NextLevelVideoFileReadyCommand nextLevelVideoFileReadyCommand;
    private Set<String> alternativesFileName;
    private Set<String> evidenceUploading;
    // private Map<String, Map<String, Object>> mapPoolsStore;
    private Map<String, Map<String, ThreadPoolExecutor>> mapPoolsStore;
    private ExtractEvidencePoolExecutor downloadVmsgatewayPoolExecutor;
    // private ExtractEvidencePoolExecutor downloadBroadwareHTTPPoolExecutor;
    private UploadEvidencePoolExecutor uploadEvidencePoolExecutor;
    private UploadEvidencePoolExecutor pushUploadEvidencePoolExecutor;
    private PropertiesConfiguration configuration;
    private SchedulerManager schedulerManager;
    private VmsGatewayMarkEvidenceFileCommand vmsGatewayMarkEvidenceFileCommand;
    private GetSituationRequestCommanand situationRequestCommanand;
    private NextLevelManager nextLevelManager;
    private GetSituationRequestRealRandomCommand situationRequestRealRandomCommand;
    
    public static final String PROPERTIES_MAX_POOL_IMAGE_EXTRACTOR = "pool.imageExtractor.max";
    private ExtractEvidencePoolExecutor imagePoolExecutor;
    public static final String PROPERTIES_MAX_POOL_VIDEO_EXTRACTOR = "pool.videoExtractor.max";
    public static final String MAX_ROUTES = "httpClient.maxRoutes";
    public static final String MAX_PER_ROUTE = "httpClient.maxPerRoute";
    public static final String CISCO_PEOPLE_COUNTING_SERVICE_URL = "cisco.pcounting.service.url";
    public static final String CISCO_PEOPLE_COUNTING_NOTIFY_URL = "cisco.pcounting.notify.url";
    public static final String CHECK_CISCO7_IMAGES = "check.cisco7.images";
    private ExtractEvidencePoolExecutor videoPoolExecutor;
    private ExtractEvidencePoolExecutor listenersExecutor;
    
    public Set<String> getEvidenceUploading() {
        if (evidenceUploading == null) {
            evidenceUploading = new HashSet<String>();
        }
        return evidenceUploading;
    }
    
    public void setEvidenceUploading(Set<String> aEvidenceUploading) {
        evidenceUploading = aEvidenceUploading;
    }
    
    public Set<String> getAlternativesFileName() {
        if (alternativesFileName == null) {
            alternativesFileName = new HashSet<String>();
        }
        return alternativesFileName;
    }
    
    public void setAlternativesFileName(Set<String> aAlternativesFileName) {
        alternativesFileName = aAlternativesFileName;
    }
    
    public void init() throws ScopixException {
        log.debug("start");

        // debemos recuperar todos lod evidence file que esten como downloadedFile False
        // esto indica que se aviso que esta listo pero no se alcanzo a recuperar
        addAllFilesGatewayFileReady();
        
        LinkedBlockingQueue<Runnable> imageThreadQueue = new LinkedBlockingQueue<Runnable>();
        imagePoolExecutor = new ExtractEvidencePoolExecutor(getIntegerProperties(PROPERTIES_MAX_POOL_IMAGE_EXTRACTOR),
                imageThreadQueue);
        
        LinkedBlockingQueue<Runnable> videoThreadQueue = new LinkedBlockingQueue<Runnable>();
        videoPoolExecutor = new ExtractEvidencePoolExecutor(getIntegerProperties(PROPERTIES_MAX_POOL_VIDEO_EXTRACTOR),
                videoThreadQueue);

        // Inicializando el pool http.
        HttpSupport.initInstance(getIntegerProperties(MAX_ROUTES), getIntegerProperties(MAX_PER_ROUTE));
        initEvidenceServiceWebService();
        
        updateExtractionPlan();
        
        log.info("end");
    }
    
    private void inicializeJobs() {
        // agregar jobs Por grupo de horas dias de la Semana
        List<ScopixJob> list = getAllJobs();
        for (ScopixJob job : list) {
            getSchedulerManager().createSimpleJob(job);
        }
        
        List<ScopixJob> listRealRandom = getRealRandomJobs();
        for (ScopixJob job : listRealRandom) {
            getSchedulerManager().createRealRandomJob(job);
        }
    }

    /**
     * Utilizado para la inicializacion por Store
     */
    public void init(String storeName) throws ScopixException {
        log.info("start [storeName:" + storeName + "]");
        try {
            createPoolByStore(storeName);
            this.unMountExternalDevice(storeName);
        } catch (InvalidDataAccessResourceUsageException ex) {
            log.warn("Cannot initialize ExtractionManager bean." + ex.getMessage());
        }
        log.info("end");
    }
    
    private boolean checkIntevalForExtractionRequest(SituationRequest situationRequest, Date fechaSolicitud,
            SituationRequestRange range, String cameraName) throws ScopixException {
        // verificamos que no exista algun request generado en para la hora delta de la pedida actual
        // solo lo pedimos si hay que verificar el intervalo
        EvidenceExtractionRequest eerPrevia = null;
        List<EvidenceExtractionRequest> requestAutoGenerados = getEvidenceExtractionRequestBySituation(situationRequest,
                fechaSolicitud);
        boolean continuar = true;
        boolean validateCamera = cameraName != null;
        for (EvidenceExtractionRequest eer : requestAutoGenerados) {
            
            if (validateCamera && !cameraName.equals(eer.getEvidenceProvider().getName())) {
                //si se debe validar camera y esta no corresponde debemos salir directo
                continue;
            }
//            if (fechaSolicitud.getTime() < eer.getCreationTimestamp().getTime()) {
            //se modifica para verificar la fecha de evidencia
            if (fechaSolicitud.getTime() < eer.getEvidenceDate().getTime()) {
                if (eerPrevia != null) {
                    Date test = DateUtils.addMinutes(eer.getEvidenceDate(), range.getFrecuency());
                    if (test.after(fechaSolicitud)) {
                        // la ultima ejecucion impide generar un nuevo request por frecuencia
                        continuar = false;
                    }
                    break;
                }
            }
            eerPrevia = eer;
        }
        if (continuar && eerPrevia != null) {
            Date test = DateUtils.addMinutes(eerPrevia.getCreationTimestamp(), range.getFrecuency());
            if (test.after(fechaSolicitud)) {
                // la ultima ejecucion impide generar un nuevo request por frecuencia
                continuar = false;
            }
        }
        
        return continuar;
    }
    
    private void createPoolByStore(String storeName) {
        log.info("start");
        Map<String, ThreadPoolExecutor> poolStore = getMapPoolsStore().get(storeName);
        if (poolStore == null) {
            poolStore = new HashMap<String, ThreadPoolExecutor>();
            
            LinkedBlockingQueue<Runnable> deleteThreadQueue = new LinkedBlockingQueue<Runnable>();
            poolStore.put(POOL_DELETE_EVIDENCE_POOL_EXECUTOR, new DeleteEvidencePoolExecutor(1, deleteThreadQueue));
            
            LinkedBlockingQueue<Runnable> queueBroadWare = new LinkedBlockingQueue<Runnable>();
            poolStore.put(POOL_EXTRACT_BROADWARE_EVIDENCE_POOL_EXECUTOR, new ExtractEvidencePoolExecutor(1, queueBroadWare,
                    POOL_EXTRACT_BROADWARE_EVIDENCE_POOL_EXECUTOR + "-" + storeName));
            
            LinkedBlockingQueue<Runnable> queueNextLevel = new LinkedBlockingQueue<Runnable>();
            poolStore.put(POOL_EXTRACT_NEXTLEVEL_EVIDENCE_POOL_EXECUTOR, new ExtractEvidencePoolExecutor(1, queueNextLevel,
                    POOL_EXTRACT_NEXTLEVEL_EVIDENCE_POOL_EXECUTOR + "-" + storeName));
            
            LinkedBlockingQueue<Runnable> queueClipNextLevel = new LinkedBlockingQueue<Runnable>();
            poolStore.put(POOL_CLIP_NEXTLEVEL_POOL_EXECUTOR, new ExtractEvidencePoolExecutor(3, queueClipNextLevel,
                    POOL_CLIP_NEXTLEVEL_POOL_EXECUTOR + "-" + storeName));

            // carlos polo 19-oct-2012. Se crean nuevas colas para crear el pool de hilos para NextLevel en la nueva versión 3
            // Queue para la descarga de archivos en la nueva versión de NextLevel
            LinkedBlockingQueue<Runnable> queueClipNextLevel3 = new LinkedBlockingQueue<Runnable>();
            poolStore.put(POOL_CLIP_NEXTLEVEL3_POOL_EXECUTOR, new ExtractEvidencePoolExecutor(3, queueClipNextLevel3,
                    POOL_CLIP_NEXTLEVEL3_POOL_EXECUTOR + "-" + storeName));

            // Queue para la creación de las solicitudes de archivos en la nueva versión de NextLevel
            LinkedBlockingQueue<Runnable> queueNextLevel3 = new LinkedBlockingQueue<Runnable>();
            poolStore.put(POOL_EXTRACT_NEXTLEVEL3_EVIDENCE_POOL_EXECUTOR, new ExtractEvidencePoolExecutor(1, queueNextLevel3,
                    POOL_EXTRACT_NEXTLEVEL3_EVIDENCE_POOL_EXECUTOR + "-" + storeName));

            // Queue para la creación de las solicitudes de archivos en BroadwareHttp
            LinkedBlockingQueue<Runnable> httpBroadware = new LinkedBlockingQueue<Runnable>();
            poolStore.put(POOL_EXTRACT_BROADWARE_HTTP_EVIDENCE_POOL_EXECUTOR, new ExtractEvidencePoolExecutor(
                    getIntegerProperties(PROPERTIES_DOWNLOAD_BROADWARE_HTTP_MAX_THREADS), httpBroadware,
                    POOL_EXTRACT_BROADWARE_HTTP_EVIDENCE_POOL_EXECUTOR + "-" + storeName));
            
            LinkedBlockingQueue<Runnable> cisco7Image = new LinkedBlockingQueue<Runnable>();
            poolStore.put(POOL_EXTRACT_CISCO7_IMAGE_EVIDENCE_POOL_EXECUTOR, new ExtractEvidencePoolExecutor(
                    getIntegerProperties(PROPERTIES_CISCO7_IMAGE_MAX_THREADS), cisco7Image,
                    POOL_EXTRACT_CISCO7_IMAGE_EVIDENCE_POOL_EXECUTOR + "-" + storeName));
            
            LinkedBlockingQueue<Runnable> cisco7Video = new LinkedBlockingQueue<Runnable>();
            poolStore.put(POOL_EXTRACT_CISCO7_VIDEO_EVIDENCE_POOL_EXECUTOR, new ExtractEvidencePoolExecutor(
                    getIntegerProperties(PROPERTIES_CISCO7_VIDEO_MAX_THREADS), cisco7Video,
                    POOL_EXTRACT_CISCO7_VIDEO_EVIDENCE_POOL_EXECUTOR + "-" + storeName));
            
            LinkedBlockingQueue<Runnable> ciscoPeopleCounting = new LinkedBlockingQueue<Runnable>();
            poolStore.put(POOL_EXTRACT_CISCO_PCOUNTING_EVIDENCE_POOL_EXECUTOR, new ExtractEvidencePoolExecutor(
                    getIntegerProperties(PROPERTIES_CISCO_PCOUNTING_MAX_THREADS), ciscoPeopleCounting,
                    POOL_EXTRACT_CISCO_PCOUNTING_EVIDENCE_POOL_EXECUTOR + "-" + storeName));
            
            LinkedBlockingQueue<Runnable> vadaroListener = new LinkedBlockingQueue<Runnable>();
            poolStore.put(POOL_LISTEN_VADARO_EVENTS_EXECUTOR, new ExtractEvidencePoolExecutor(
                    getIntegerProperties(PROPERTIES_VADARO_LISTENER_MAX_THREADS), vadaroListener,
                    POOL_LISTEN_VADARO_EVENTS_EXECUTOR + "-" + storeName));
            
            LinkedBlockingQueue<Runnable> ciscoPeopleCountingFileReady = new LinkedBlockingQueue<Runnable>();
            poolStore.put(POOL_EXTRACT_CISCO_PCOUNTING_FREADY_POOL_EXECUTOR, new ExtractEvidencePoolExecutor(
                    getIntegerProperties(PROPERTIES_CISCO_PCOUNTING_MAX_THREADS), ciscoPeopleCountingFileReady,
                    POOL_EXTRACT_CISCO_PCOUNTING_FREADY_POOL_EXECUTOR + "-" + storeName));

            // agregamos el pool para listener
            LinkedBlockingQueue<Runnable> listenerQueue = new LinkedBlockingQueue<Runnable>();
            poolStore.put(POOL_LISTEN_VADARO_EVENTS_EXECUTOR, new ExtractEvidencePoolExecutor(
                    getIntegerProperties(PROPERTIES_VADARO_LISTENER_MAX_THREADS), listenerQueue,
                    POOL_LISTEN_VADARO_EVENTS_EXECUTOR + "-" + storeName));
            
            getMapPoolsStore().put(storeName, poolStore);
        }
        log.info("end");
    }
    
    public void broadwareFileReady(String broadwareFile, String fileType) throws ScopixException {
        if (fileType.equals("image")) {
            log.info("Receiving Notification of Image file ready: " + broadwareFile);
            EvidenceFile ef = getEvidenceFileByFileName(broadwareFile + ".jpg");
            String storeName = ef.getEvidenceExtractionRequest().getExtractionPlan().getStoreName();
            BroadwareImageFileReadyCommand broadwareImageFileReadyCommand = new BroadwareImageFileReadyCommand();
            broadwareImageFileReadyCommand.execute(broadwareFile, (DeleteEvidencePoolExecutor) getMapPoolsStore().get(storeName)
                    .get(POOL_DELETE_EVIDENCE_POOL_EXECUTOR), ef.getEvidenceExtractionRequest().getLive());
        } else if (fileType.equals("video")) {
            log.info("Receiving Notification of Video file ready: " + broadwareFile);
            EvidenceFile ef = getEvidenceFileByFileName(broadwareFile + ".bwm");
            String storeName = ef.getEvidenceExtractionRequest().getExtractionPlan().getStoreName();
            BroadwareVideoFileReadyCommand broadwareVideoFileReadyCommand = new BroadwareVideoFileReadyCommand();
            broadwareVideoFileReadyCommand.execute(broadwareFile, (DeleteEvidencePoolExecutor) getMapPoolsStore().get(storeName)
                    .get(POOL_DELETE_EVIDENCE_POOL_EXECUTOR));
        }
    }
    
    public void broadwareHTTPFileReady(String broadwareFile, String fileType) throws ScopixException {
        log.info("start");
        if (fileType.equals("video")) {
            log.info("Receiving Notification of Video file ready: " + broadwareFile);
            EvidenceFile ef = getEvidenceFileByFileName(broadwareFile + ".bwm");
            
            if (ef != null) {
                String storeName = ef.getEvidenceExtractionRequest().getExtractionPlan().getStoreName();
                BroadwareHTTPEvidenceProvider bhttpep = (BroadwareHTTPEvidenceProvider) ef.getEvidenceExtractionRequest()
                        .getEvidenceProvider();
                // cambiar buffer reader por lectura por http
                String broadwareHTTPURL = "http://" + bhttpep.getIpAddress() + ":" + getStringProperties("ApacheWebServerPort")
                        + "/" + broadwareFile + "/" + broadwareFile + ".bwm";

                // creamos nuevo thread para que se encarge de la descarga antes de modificar datos
                BroadwareHTTPVideoFileReadyThread thread = new BroadwareHTTPVideoFileReadyThread();
                thread.init(broadwareHTTPURL, ef.getId(), storeName, broadwareFile);

                // creamos los pool en caso de no existir
                createPoolByStore(storeName);

                // recuperamos el pool asociado a la tienda
                ExtractEvidencePoolExecutor pool = (ExtractEvidencePoolExecutor) getMapPoolsStore().get(storeName).get(
                        POOL_EXTRACT_BROADWARE_HTTP_EVIDENCE_POOL_EXECUTOR);
                
                pool.pause();
                pool.runTask(thread);
                pool.resume();
            }
            
        }
        log.info("end");
    }
    
    public void broadwareHTTPDownloadFile(Integer evidenceId, String storeName, File tmp, String originalName) {
        BroadwareHTTPVideoFileReadyCommand broadwareHTTPVideoFileReadyCommand = new BroadwareHTTPVideoFileReadyCommand();
        broadwareHTTPVideoFileReadyCommand.execute(evidenceId, tmp, (DeleteEvidencePoolExecutor) getMapPoolsStore()
                .get(storeName).get(POOL_DELETE_EVIDENCE_POOL_EXECUTOR), originalName);
    }
    
    public void nextLevelVideoReady(String fileNameNextLevel, File tmp) {
        log.info("Inicio nextLevelVideoReady");
        getNextLevelVideoFileReadyCommand().execute(fileNameNextLevel, getAlternativesFileName(), tmp);
        log.info("end");
    }
    
    public synchronized void addGetFileByEvent(String fileNameNextLevel, String storeName, String urlGateway, String user,
            String pass) {
        log.info("start");
        if (getAlternativesFileName().contains(fileNameNextLevel)) {
            log.debug("recuperando " + fileNameNextLevel);
            GetClipFileThread clipFileThread = new GetClipFileThread(fileNameNextLevel, storeName, urlGateway, user, pass);
            // detenemos el pool para porder agregar en nuevo task
            // recuperamos el Pool de clip
            ExtractEvidencePoolExecutor clipNextLevelPoolExecutor = (ExtractEvidencePoolExecutor) getMapPoolsStore().get(
                    storeName).get(POOL_CLIP_NEXTLEVEL_POOL_EXECUTOR);
            clipNextLevelPoolExecutor.pause();
            clipNextLevelPoolExecutor.runTask(clipFileThread);
            // dejamos seguir el pool nuevamente
            clipNextLevelPoolExecutor.resume();
        } else {
            log.debug("el Archivo " + fileNameNextLevel + " no ha sido solicitado por sistema");
        }
        log.info("end");
    }

    /**
     * Ejecuta hilos para la descarga de archivos para la nueva versión
     *
     * @author carlos polo
     * @param fileNameNextLevel
     * @param storeName
     * @param urlGateway
     * @param user
     * @param pass
     * @version 3.0
     * @date 22-oct-2012
     */
    public synchronized void addGetFileByEventNextLevel3(String fileNameNextLevel, String storeName, String urlGateway,
            String user, String pass) {
        
        log.info("Inicio addGetFileByEventNextLevel3, para la descarga del archivo " + fileNameNextLevel);
        if (getAlternativesFileName().contains(fileNameNextLevel)) {
            log.debug("Recuperando archivo: " + fileNameNextLevel);
            NextLevel3GetClipFileThread clipFileThread = new NextLevel3GetClipFileThread(fileNameNextLevel, storeName,
                    urlGateway, user, pass);

            // detenemos el pool para porder agregar en nuevo task, recuperamos el Pool de clip
            ExtractEvidencePoolExecutor clipNextLevelPoolExecutor = (ExtractEvidencePoolExecutor) getMapPoolsStore().get(
                    storeName).get(POOL_CLIP_NEXTLEVEL3_POOL_EXECUTOR);
            clipNextLevelPoolExecutor.pause();
            clipNextLevelPoolExecutor.runTask(clipFileThread);
            // dejamos seguir el pool nuevamente
            clipNextLevelPoolExecutor.resume();
        } else {
            log.debug("el Archivo " + fileNameNextLevel + " no ha sido solicitado por sistema");
        }
        log.info("end");
    }

    /**
     * agrega la lista de todos los files por bajar desde vms al pool de bajada
     */
    private void addAllFilesGatewayFileReady() {
        try {
            List<EvidenceFile> files = SpringSupport.getInstance().findBeanByClassName(EvidenceFileDAO.class)
                    .getEvidenceFilesWereNotDownloaded();
            
            getDownloadVmsgatewayPoolExecutor().pause();
            for (EvidenceFile ef : files) {
                VMSGatewayDownloadThread downloadThread = new VMSGatewayDownloadThread();
                downloadThread.init(ef.getId(), ef.getAlternativeFileName());
                getDownloadVmsgatewayPoolExecutor().runTask(downloadThread);
            }
            getDownloadVmsgatewayPoolExecutor().resume();
        } catch (Exception e) {
            log.error("No es posible recuperar los archivos pendientes de VMS" + e, e);
        }
    }
    
    public void vmsGatewayFileReady(Integer evidenceFileId, String fileName) {
        log.info("Receiving Notification of Video file ready [evidenceFileId:" + evidenceFileId + "][fileName:" + fileName + "]");
        // intervenir con thread
        VMSGatewayDownloadThread downloadThread = new VMSGatewayDownloadThread();
        downloadThread.init(evidenceFileId, fileName);
        // debemos persistir este dato en base de datos
        getVmsGatewayMarkEvidenceFileCommand().execute(evidenceFileId, fileName);
        getDownloadVmsgatewayPoolExecutor().pause();
        getDownloadVmsgatewayPoolExecutor().runTask(downloadThread);
        getDownloadVmsgatewayPoolExecutor().resume();
        log.info("end");
        
    }

    // esto se ejecuta una vez recibido la indicacion que el archivo esta generado
    public void vmsGatewaydonwloadFile(Integer evidenceFileId, String fileName) {
        log.info("start [evidenceFileId:" + evidenceFileId + "][fileName:" + fileName + "]");
        
        VMSGatewayVideoFileReadyCommand command = new VMSGatewayVideoFileReadyCommand();
        command.execute(evidenceFileId, fileName);
        
        log.info("end");
    }
    
    private void initEvidenceServiceWebService() throws ScopixException {
        Map<String, String> serverURLs = new HashMap<String, String>();
        serverURLs.put(EVIDENCE_SERVER_WEBSERVICE_KEY, getStringProperties(PROPERTIES_EVIDENCE_WEB_SERVICE_URL));
        // evidenceWebServiceClientURL se reemplaza por la pedida directa al properties
        EvidenceWebServiceClient.getInstance().initializeClients(serverURLs);
    }
    
    private ExtractionPlan loadPlan(String storeName) {
        log.debug("loadPlan()");
        ExtractionPlan extractionPlan = null;
        try {
            EServerDAO dao = SpringSupport.getInstance().findBeanByClassName(EServerDAO.class);
            extractionPlan = dao.getExtractionPlanEnable(storeName);
            // no aseguramos de cargar la situaciones para el epc
            extractionPlan.getSituationRequests().isEmpty();
            for (SituationRequest request : extractionPlan.getSituationRequests()) {
                request.getEvidenceProviderRequests().isEmpty();
            }
        } catch (ObjectRetrievalFailureException ex) {
            log.warn("Extraction Plan not loaded." + ex.getMessage());
        } catch (IndexOutOfBoundsException ex) {
            log.warn("Extraction Plan not loaded." + ex.getMessage());
        }
        log.debug("Plan Id:" + (extractionPlan != null ? extractionPlan.getId() : null));
        return extractionPlan;
    }
    
    @Deprecated
    private void createScheduling(ExtractionPlan extractionPlan) throws ScopixException {
        log.info("start");

        // debemos crear los los pool para el store si no existen
        createPoolByStore(extractionPlan.getStoreName());
        
        getSchedulerManager().updateExtractionPlanScheduling(extractionPlan,
                getStringProperties(PROPERTIES_MOTIONDETECTION_CRON_EXPRESSION));

        /**
         * Se mueven directo al SchedulerManager getStringProperties(PROPERTIES_UPLOADJOB_UPLOAD_CRON_EXPRESSION),
         * getStringProperties(PROPERTIES_UPLOADJOB_FILEMANAGER_TYPE),
         * getStringProperties(PROPERTIES_DEVICEUTIL_MOUNT_CRON_EXPRESSION),
         * getStringProperties(PROPERTIES_DEVICEUTIL_UNMOUNT_CRON_EXPRESSION),
         *
         */
    }
    
    @Deprecated
    public void updateExtractionPlanByStoreName(String storeName) throws ScopixException {
        log.info("start");
        // creamos los job para el cambio de horario
        getSchedulerManager().initializeSchedulerChange();

        // ExtractionPlan extractionPlan = loadPlan(storeName);
        // debemos llamar al mismo proceso del inicio de aplicacion
        // createScheduling(extractionPlan);
        log.info("end");
    }
    
    public void updateExtractionPlan() throws ScopixException {
        log.info("start");
        try {

            // recuperamos todos los store y los inicializamos en caso de llegar nuevos
            EServerDAO dao = SpringSupport.getInstance().findBeanByClassName(EServerDAO.class);

            // inicializamos los store
            List<String> storeNames = dao.getStoreNamesAvailables();
            for (String storeName : storeNames) {
                init(storeName);
            }

            // limpiamos los jobs SimpleJobGroup
            getSchedulerManager().initializeScheduler("SimpleJobGroup");
            // limpiamos los jobs RealRandomJobGroup
            getSchedulerManager().initializeScheduler("RealRandomJobGroup");

            // inicalizamos los jobs de cambio horario
            getSchedulerManager().initializeSchedulerChange();

            // inicializamos los jobs genericos
            inicializeJobs();
            
            getSchedulerManager().startScheduler();

            // Inicializamos listeners
            // debemos levantarla por store
            for (String storeName : storeNames) {
                startMotionDetection(storeName);
            }
            
        } catch (ScopixException e) {
            log.error(e, e);
            throw (e);
        }
        
        log.info("end");
    }
    
    public void extractEvidenceNow(List<RequestTimeZone> requestTimeZones, Integer dayOfWeek) throws ScopixException {

        // recuperar los request para la hora dia de la semana
        List<EvidenceExtractionRequest> eers = new ListEvidenceExtractionRequestCommand().execute(requestTimeZones, dayOfWeek);

        // recuperar los pool asociados, pausarlos y volver a ejecutarlos
        imagePoolExecutor.pause();
        videoPoolExecutor.pause();
        // solicitar su generacion
        for (EvidenceExtractionRequest eer : eers) {
            // si son video agregarlos al pool de solicitud de videos (2 simultaneos)
            if (ScopixUtilities.findMethod(eer.getClass(), "setLengthInSecs") != null) { // video
                // TODO crear codigo thread
                EvidenceExtractionThread t = new EvidenceExtractionThread(eer, null);
                videoPoolExecutor.runTask(t);
            } else { // image
                EvidenceExtractionThread t = new EvidenceExtractionThread(eer, null);
                imagePoolExecutor.runTask(t);
            }
            // si son imagen agregarlos al pool de solicitud de imagenes (10 simultaneos)
            // extractEvidence(eer, null);
        }
        imagePoolExecutor.resume();
        videoPoolExecutor.resume();
        
    }
    
    public void extractEvidence(Integer evidenceExtractionRequestId, Date date) throws ScopixException {
        log.info("start [evidenceExtractionRequestId:" + evidenceExtractionRequestId + "][date:" + date + "]");
        GetEvidenceExtractionRequestCommand command = new GetEvidenceExtractionRequestCommand();
        EvidenceExtractionRequest eer = command.execute(evidenceExtractionRequestId);
        log.debug("eer: " + eer);
        extractEvidence(eer, date);
        log.info("end");
    }
    
    public void extractEvidence(EvidenceExtractionRequest evidenceExtractionRequest, Date date) throws ScopixException {
        log.debug("Inicio extractEvidence");
        EvidenceProvider evidenceProvider = evidenceExtractionRequest.getEvidenceProvider();
        
        log.debug("evidence provider: " + evidenceProvider);
        String storeName = evidenceExtractionRequest.getExtractionPlan().getStoreName();
        log.debug("inicialiando pool para store " + storeName);
        // nos preocupamos de crear el pool para el store
        createPoolByStore(storeName);
        // recuperamos los posibles pool a enviar a los comandos
        Map<String, ThreadPoolExecutor> pools = getMapPoolsStore().get(storeName);

        // TO-DO recupera solo 1 pool para el trabajo no es necesario recuperar todos los pool ya que la evidencias solo necesita
        // 1
        ExtractEvidencePoolExecutor extractBroadWareEvidencePoolExecutor = (ExtractEvidencePoolExecutor) pools
                .get(POOL_EXTRACT_BROADWARE_EVIDENCE_POOL_EXECUTOR);
        
        ExtractEvidencePoolExecutor extractNextLevelEvidencePoolExecutor = (ExtractEvidencePoolExecutor) pools
                .get(POOL_EXTRACT_NEXTLEVEL_EVIDENCE_POOL_EXECUTOR);
        
        ExtractEvidencePoolExecutor extractCisco7ImageEvidencePoolExecutor = (ExtractEvidencePoolExecutor) pools
                .get(POOL_EXTRACT_CISCO7_IMAGE_EVIDENCE_POOL_EXECUTOR);
        ExtractEvidencePoolExecutor extractCisco7VideoEvidencePoolExecutor = (ExtractEvidencePoolExecutor) pools
                .get(POOL_EXTRACT_CISCO7_VIDEO_EVIDENCE_POOL_EXECUTOR);
        
        ExtractEvidencePoolExecutor extractCiscoPCountingEvidencePoolExecutor = (ExtractEvidencePoolExecutor) pools
                .get(POOL_EXTRACT_CISCO_PCOUNTING_EVIDENCE_POOL_EXECUTOR);

        /*
         * carlos polo 19-oct-2012. Se crea variable para que sirva de llave al momento de crear el pool de hilos para la cola de
         * NextLevel en la nueva versión 3
         */
        ExtractEvidencePoolExecutor extractNextLevel3EvidencePoolExecutor = (ExtractEvidencePoolExecutor) pools
                .get(POOL_EXTRACT_NEXTLEVEL3_EVIDENCE_POOL_EXECUTOR);
        
        if (evidenceExtractionRequest instanceof BroadwareVideoExtractionRequest) {
            log.debug("BroadwareVideoExtractionRequest");
            BroadwareVideoExtractionCommand providerAdaptor = new BroadwareVideoExtractionCommand();
            providerAdaptor.execute((BroadwareVideoExtractionRequest) evidenceExtractionRequest,
                    (BroadwareEvidenceProvider) evidenceProvider, extractBroadWareEvidencePoolExecutor, date);
        } else if (evidenceExtractionRequest instanceof BroadwareImageExtractionRequest) {
            log.debug("BroadwareImageExtractionRequest");
            BroadwareImageExtractionCommand providerAdaptor = new BroadwareImageExtractionCommand();
            providerAdaptor.execute((BroadwareImageExtractionRequest) evidenceExtractionRequest,
                    (BroadwareEvidenceProvider) evidenceProvider, extractBroadWareEvidencePoolExecutor, date);
        } else if (evidenceExtractionRequest instanceof PeopleCountingExtractionRequest) {
            log.debug("PeopleCountingExtractionRequest");
            PeopleCountingExtractionCommand command = new PeopleCountingExtractionCommand();
            command.execute((PeopleCountingExtractionRequest) evidenceExtractionRequest,
                    (PeopleCountingEvidenceProvider) evidenceProvider, date);
        } else if (evidenceExtractionRequest instanceof CognimaticsPeopleCounter141ExtractionRequest) {
            log.debug("CognimaticsPeopleCounter141ExtractionRequest");
            CognimaticsPeopleCounter141ExtractionCommand command = new CognimaticsPeopleCounter141ExtractionCommand();
            command.execute((CognimaticsPeopleCounter141ExtractionRequest) evidenceExtractionRequest,
                    (CognimaticsPeopleCounter141EvidenceProvider) evidenceProvider, date);
        } else if (evidenceExtractionRequest instanceof KumGoImageExtractionRequest) {
            log.debug("KumGoImageExtractionRequest");
            KumGoImageExtractionCommand command = new KumGoImageExtractionCommand();
            command.execute((KumGoImageExtractionRequest) evidenceExtractionRequest, (KumGoEvidenceProvider) evidenceProvider,
                    date);
        } else if (evidenceExtractionRequest instanceof AxisP3301ImageExtractionRequest) {
            log.debug("AxisP3301ImageExtractionRequest");
            AxisP3301ImageExtractionCommand command = new AxisP3301ImageExtractionCommand();
            command.execute((AxisP3301ImageExtractionRequest) evidenceExtractionRequest,
                    (AxisP3301EvidenceProvider) evidenceProvider, date);
        } else if (evidenceExtractionRequest instanceof CognimaticsPeopleCounter212ExtractionRequest) {
            log.debug("CognimaticsPeopleCounter212ExtractionRequest");
            CognimaticsPeopleCounter212ExtractionCommand command = new CognimaticsPeopleCounter212ExtractionCommand();
            command.execute((CognimaticsPeopleCounter212ExtractionRequest) evidenceExtractionRequest,
                    (CognimaticsPeopleCounter212EvidenceProvider) evidenceProvider, date);
        } else if (evidenceExtractionRequest instanceof CognimaticsPeopleCounter150ExtractionRequest) {
            log.debug("CognimaticsPeopleCounter15ExtractionRequest");
            CognimaticsPeopleCounter150ExtractionCommand command = new CognimaticsPeopleCounter150ExtractionCommand();
            command.execute((CognimaticsPeopleCounter150ExtractionRequest) evidenceExtractionRequest,
                    (CognimaticsPeopleCounter150EvidenceProvider) evidenceProvider, date);
        } else if (evidenceExtractionRequest instanceof AxisGenericVideoExtractionRequest) {
            log.debug("AxisGenericVideoExtractionRequest");
            AxisGenericVideoExtractionCommand command = new AxisGenericVideoExtractionCommand();
            command.execute((AxisGenericVideoExtractionRequest) evidenceExtractionRequest,
                    (AxisGenericEvidenceProvider) evidenceProvider, date);
        } else if (evidenceExtractionRequest instanceof NextLevelVideoExtractionRequest) {
            log.debug("NextLevelVideoExtractionRequest");
            NextLevelEvidenceProvider ep = (NextLevelEvidenceProvider) evidenceProvider;
            // levantamos los enventos si estos no han sido levantados anteriormente
            SpringSupport.getInstance().findBeanByClassName(NextLevelManager.class)
                    .upEvents(storeName, ep.getUrlGateway(), ep.getUserName(), ep.getPassword());
            NextLevelVideoExtractionCommand command = new NextLevelVideoExtractionCommand();
            command.execute((NextLevelVideoExtractionRequest) evidenceExtractionRequest, ep,
                    extractNextLevelEvidencePoolExecutor, date, getAlternativesFileName());
        } else if (evidenceExtractionRequest instanceof BrickcomImageExtractionRequest) { // Agregado 20110908
            log.debug("BrickcomImageExtractionRequest");
            BrickcomImageExtractionCommand command = new BrickcomImageExtractionCommand();
            command.execute((BrickcomImageExtractionRequest) evidenceExtractionRequest,
                    (BrickcomEvidenceProvider) evidenceProvider, date);
        } else if (evidenceExtractionRequest instanceof ArecontImageExtractionRequest) { // Agregado 20110908
            log.debug("ArecontImageExtractionRequest");
            ArecontImageExtractionCommand command = new ArecontImageExtractionCommand();
            command.execute((ArecontImageExtractionRequest) evidenceExtractionRequest,
                    (ArecontEvidenceProvider) evidenceProvider, date);
        } else if (evidenceExtractionRequest instanceof VMSGatewayVideoExtractionRequest) {
            log.debug("VMSGatewayVideoExtractionRequest");
            VMSGatewayVideoExtractionCommand command = new VMSGatewayVideoExtractionCommand();
            command.execute((VMSGatewayVideoExtractionRequest) evidenceExtractionRequest,
                    (VMSGatewayEvidenceProvider) evidenceProvider, date);
        } else if (evidenceExtractionRequest instanceof NextLevel3VideoExtractionRequest) {
            /*
             * carlos polo 19-oct-2012. Se crea condición para la nueva versión de integración entre EES y NextLevel
             */
            log.debug("evidenceExtractionRequest es del tipo NextLevel3VideoExtractionRequest");
            NextLevel3EvidenceProvider nextLevel3EvProvider = (NextLevel3EvidenceProvider) evidenceProvider;

            // Se levantan los hilos que atenderán los eventos generados
            SpringSupport
                    .getInstance()
                    .findBeanByClassName(NextLevelManager.class)
                    .upNextLevel3Events(storeName, nextLevel3EvProvider.getUrlGateway(), nextLevel3EvProvider.getUserName(),
                            nextLevel3EvProvider.getPassword());

            // Invoca operaciones para extracción de evidencias
            NextLevel3VideoExtractionCommand command = new NextLevel3VideoExtractionCommand();
            command.execute((NextLevel3VideoExtractionRequest) evidenceExtractionRequest, nextLevel3EvProvider,
                    extractNextLevel3EvidencePoolExecutor, date, getAlternativesFileName());
        } else if (evidenceExtractionRequest instanceof BroadwareHTTPVideoExtractionRequest) {
            log.debug("BroadwareHTTPVideoExtractionRequest");
            BroadwareHTTPVideoExtractionCommand providerAdaptor = new BroadwareHTTPVideoExtractionCommand();
            providerAdaptor.execute((BroadwareHTTPVideoExtractionRequest) evidenceExtractionRequest,
                    (BroadwareHTTPEvidenceProvider) evidenceProvider, extractBroadWareEvidencePoolExecutor, date);
        } else if (evidenceExtractionRequest instanceof Cisco3520F_1_2_1ImageExtractionRequest) {
            log.debug("Cisco3520F_1_2_1ImageExtractionRequest");
            Cisco3520F_1_2_1ImageExtractionCommand providerAdaptor = new Cisco3520F_1_2_1ImageExtractionCommand();
            providerAdaptor.execute((Cisco3520F_1_2_1ImageExtractionRequest) evidenceExtractionRequest,
                    (Cisco3520F_1_2_1EvidenceProvider) evidenceProvider, date);
        } else if (evidenceExtractionRequest instanceof ReadUrlPHPImageExtractionRequest) {
            log.debug("ReadUrlPHPImageExtractionRequest");
            ReadUrlPHPImageExtractionCommand providerAdaptor = new ReadUrlPHPImageExtractionCommand();
            providerAdaptor.execute((ReadUrlPHPImageExtractionRequest) evidenceExtractionRequest,
                    (ReadUrlPHPEvidenceProvider) evidenceProvider, date);
        } else if (evidenceExtractionRequest instanceof ReadUrlPHPXmlExtractionRequest) {
            log.debug("ReadUrlPHPXmlExtractionRequest");
            ReadUrlPHPXmlExtractionCommand providerAdaptor = new ReadUrlPHPXmlExtractionCommand();
            providerAdaptor.execute((ReadUrlPHPXmlExtractionRequest) evidenceExtractionRequest,
                    (ReadUrlPHPEvidenceProvider) evidenceProvider, date);
        } else if (evidenceExtractionRequest instanceof Cisco7VideoExtractionRequest) {
            log.debug("Cisco7VideoExtractionRequest");
            Cisco7VideoExtractionCommand providerAdaptor = new Cisco7VideoExtractionCommand();
            providerAdaptor.setStoreName(storeName);
            // ejecuta comando
            providerAdaptor.execute((Cisco7VideoExtractionRequest) evidenceExtractionRequest,
                    (Cisco7EvidenceProvider) evidenceProvider, date, extractCisco7VideoEvidencePoolExecutor);
        } else if (evidenceExtractionRequest instanceof Cisco7ImageExtractionRequest) {
            log.debug("Cisco7ImageExtractionRequest");
            String checkImages = getStringProperties(CHECK_CISCO7_IMAGES);
            
            Cisco7ImageExtractionCommand providerAdaptor = new Cisco7ImageExtractionCommand();
            providerAdaptor.setStoreName(storeName);
            providerAdaptor.setCheckImages(checkImages);

            // ejecuta comando
            providerAdaptor.execute((Cisco7ImageExtractionRequest) evidenceExtractionRequest,
                    (Cisco7EvidenceProvider) evidenceProvider, date, extractCisco7ImageEvidencePoolExecutor);
        } else if (evidenceExtractionRequest instanceof CiscoPeopleCountingExtractionRequest) {
            log.debug("CiscoPeopleCountingExtractionRequest");
            CiscoPeopleCountingExtractionCommand providerAdaptor = new CiscoPeopleCountingExtractionCommand();
            
            String serviceURL = getStringProperties(CISCO_PEOPLE_COUNTING_SERVICE_URL);
            String notifyURL = getStringProperties(CISCO_PEOPLE_COUNTING_NOTIFY_URL);
            
            log.debug("storeName: [" + storeName + "], serviceURL: [" + serviceURL + "], notifyURL: [" + notifyURL + "]");
            providerAdaptor.setServiceURL(serviceURL);
            providerAdaptor.setStoreName(storeName);
            providerAdaptor.setNotifyURL(notifyURL);

            // ejecuta comando
            providerAdaptor.execute((CiscoPeopleCountingExtractionRequest) evidenceExtractionRequest,
                    (CiscoPeopleCountingEvidenceProvider) evidenceProvider, date, extractCiscoPCountingEvidencePoolExecutor);
            
        } else if (evidenceExtractionRequest instanceof VadaroPeopleCountingExtractionRequest) {
            log.debug("VadaroPeopleCountingExtractionRequest");
            VadaroPeopleCountingExtractionCommand command = new VadaroPeopleCountingExtractionCommand();
            command.execute((VadaroPeopleCountingExtractionRequest) evidenceExtractionRequest,
                    (VadaroPeopleCountingEvidenceProvider) evidenceProvider, date);
        } else {
            throw new ScopixException("Invalid Evidence Request " + evidenceExtractionRequest);
        }
        log.debug("end");
    }

    /**
     *
     * @param fileName
     * @throws ScopixException
     */
    public void uploadEvidence(String fileName) throws ScopixException {
        log.info("start, fileName: [" + fileName + "] (for live push it will be != NULL)");
        Map<String, String> params = prepareUploadParams();
        
        UploadEvidencePoolExecutor uploadEvidencePoolExecutor = null;
        if (fileName == null) {
            log.debug("getting upload pool instance for normal evidence upload");
            uploadEvidencePoolExecutor = getUploadEvidencePoolExecutor();
        } else {
            log.debug("getting upload pool instance for live evidence upload push");
            uploadEvidencePoolExecutor = getPushUploadEvidencePoolExecutor();
        }
        log.debug("creating a new UploadEvidenceCommand for evidence upload");
        UploadEvidenceCommand command = new UploadEvidenceCommand(uploadEvidencePoolExecutor);
        
        EvidenceWebServiceClient ewc = EvidenceWebServiceClient.getInstance();
        EvidenceWebService webservice = ewc.getWebServiceClient(EVIDENCE_SERVER_WEBSERVICE_KEY);
        boolean https = false;
        
        try {
            //recuperamos la URL del servicio y verificamos si es http o https para agregar la no validacion de certificados
            URL url = new URL(getStringProperties(PROPERTIES_EVIDENCE_WEB_SERVICE_URL));
            if (url.getProtocol().equals("https")) {
                https = true;
            }
        } catch (MalformedURLException e) {
            log.error("error in URL service" + e, e);
            throw new ScopixException(e);
        }
        
        Client client = ClientProxy.getClient(webservice);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        
        if (https) {
            TLSClientParameters parameters = new TLSClientParameters();
            parameters.setSSLSocketFactory(createSSLContext().getSocketFactory());
            parameters.setDisableCNCheck(true);
            http.setTlsClientParameters(parameters);
            
        }
        
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(0);
        httpClientPolicy.setReceiveTimeout(0);
        httpClientPolicy.setAllowChunking(false);
        http.setClient(httpClientPolicy);

        //verificamos la URL y le colocamos certificados https si es necesario
        command.execute(params, webservice, getEvidenceUploading(), fileName);
        log.info("end");
    }
    
    private SSLContext createSSLContext() {
        SSLContext sslContext = null;
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }
                
                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }
            }};
            
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyManagementException e) {
        }
        return sslContext;
    }

    /**
     * @return
     */
    public Map<String, String> prepareUploadParams() {
        log.info("start");
        String uploadRemoteDirForExternalDevice = null;
        String corporate = getStringProperties(PROPERTIES_UPLOADJOB_CORPORATE);
        String rootFolder = getStringProperties(PROPERTIES_DEVICEUTIL_ROOT_FOLDER);
        String uploadRemoteDir = getStringProperties(PROPERTIES_UPLOADJOB_UPLOAD_REMOTE_DIR);
        String uploadFileManagerType = getStringProperties(PROPERTIES_UPLOADJOB_FILEMANAGER_TYPE);
        
        if (uploadFileManagerType.equalsIgnoreCase(ExtractionManager.UPLOAD_FILE_MANAGER_LOCAL)) {
            uploadRemoteDir = rootFolder + corporate + "/";
            uploadRemoteDirForExternalDevice = getStringProperties("UploadJob.uploadRemoteDir");
        }
        
        Map<String, String> params = new HashMap<String, String>();
        params.put(UploadEvidenceCommand.UPLOAD_HOST, getStringProperties(PROPERTIES_UPLOADJOB_UPLOAD_HOST));
        params.put(UploadEvidenceCommand.UPLOAD_USER, getStringProperties(PROPERTIES_UPLOADJOB_UPLOAD_USER));
        params.put(UploadEvidenceCommand.UPLOAD_PASSWORD, getStringProperties(PROPERTIES_UPLOADJOB_UPLOAD_PASSWORD));
        params.put(UploadEvidenceCommand.UPLOAD_CORPORATE, corporate);
        params.put(UploadEvidenceCommand.ROOT_FOLDER, rootFolder);
        params.put(UploadEvidenceCommand.UPLOAD_LOCAL_DIR, getStringProperties(PROPERTIES_UPLOADJOB_UPLOAD_LOCAL_DIR));
        params.put(UploadEvidenceCommand.UPLOAD_REMOTE_DIR, uploadRemoteDir);
        params.put(UploadEvidenceCommand.UPLOAD_REMOTE_DIR_FOR_EXTERNAL_DEVICE, uploadRemoteDirForExternalDevice);
        params.put(UploadEvidenceCommand.PROTOCOL_TO_UPLOAD, getStringProperties(PROPERTIES_UPLOADJOB_PROTOCOL_TO_UPLOAD));
        params.put(UploadEvidenceCommand.UPLOAD_FILEMANAGER_TYPE, uploadFileManagerType);
        params.put(UploadEvidenceCommand.UPLOAD_MAX_THREADS, getStringProperties(PROPERTIES_UPLOADJOB_UPLOAD_MAX_THREADS));
        params.put(UploadEvidenceCommand.EVIDENCE_WEBSERVICE_URL, getStringProperties(PROPERTIES_EVIDENCE_WEB_SERVICE_URL));
        
        log.info("end");
        return params;
    }

    /**
     *
     * @param date se genera por string formato yyyy-MM-dd
     * @return
     * @throws ScopixException
     */
    public List<Integer> getPastEvidence(Date date, String storeName) throws ScopixException {
        log.debug("start [date:" + date + "][storeName:" + storeName + "]");
        List<Integer> data = new ArrayList<Integer>();
        EServerDAO dao = SpringSupport.getInstance().findBeanByClassName(EServerDAO.class);
        GenericDAO genericDAO = HibernateSupport.getInstance().findGenericDAO();
        String[] patterns = new String[]{"yyyy-MM-dd HH:mm:ss"};
        if (date != null) {
            // recuperamos una lista de extracionPlan
            List<ExtractionPlan> extractionPlans = dao.getExtractionPlanListForADate(date, storeName);
            log.debug("extractionPlans size = " + extractionPlans.size());
            // recuperamos el primer plan para hacer los calculos
            if (extractionPlans.isEmpty()) {
                // si no existe extraction plan para la fecha salimos
                return data;
            }
            ExtractionPlan ep = extractionPlans.get(0);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int day = cal.get(Calendar.DAY_OF_WEEK);
            log.debug("day = " + day);
            
            String dateInit = DateFormatUtils.format(date, "yyyy-MM-dd");
            // debe ser hora del servidor
            Date nowInStore = new Date();
            if (ep.getTimeZoneId() != null && ep.getTimeZoneId().length() > 0) {
                // recuperamos la diferencia en hrs entre el timeZone del Store y el TimeZone del Server
                double d = TimeZoneUtils.getDiffInHoursTimeZone(ep.getTimeZoneId());
                log.debug("[timeZoneId:" + ep.getTimeZoneId() + "][difHours:" + d + "]");
                nowInStore = DateUtils.addHours(nowInStore, (int) d * -1);
            }
            Date initProcess = null;
            Date finProcess = null;
            try {
                initProcess = DateUtils.parseDate(dateInit + " 00:00:00", patterns);
                finProcess = DateUtils.parseDate(dateInit + " 23:59:59", patterns);
                if (finProcess.after(nowInStore)) {
                    finProcess = nowInStore;
                }
            } catch (ParseException e) {
                log.error("NO se pudo obtener fechas de proceso");
            }
            log.debug("initProcess:" + initProcess + " endProcess:" + finProcess);
            // recuperamos y bloquemos los pool de evidencias
            Map<String, ThreadPoolExecutor> pools = getMapPoolsStore().get(storeName);
            ExtractEvidencePoolExecutor extractNextLevelEvidencePoolExecutor = (ExtractEvidencePoolExecutor) pools
                    .get(POOL_EXTRACT_NEXTLEVEL_EVIDENCE_POOL_EXECUTOR);
            ExtractEvidencePoolExecutor extractBroadWareEvidencePoolExecutor = (ExtractEvidencePoolExecutor) pools
                    .get(POOL_EXTRACT_BROADWARE_EVIDENCE_POOL_EXECUTOR);
            ExtractEvidencePoolExecutor extractCisco7VideoEvidencePoolExecutor = (ExtractEvidencePoolExecutor) pools
                    .get(POOL_EXTRACT_CISCO7_VIDEO_EVIDENCE_POOL_EXECUTOR);
            ExtractEvidencePoolExecutor extractCisco7ImageEvidencePoolExecutor = (ExtractEvidencePoolExecutor) pools
                    .get(POOL_EXTRACT_CISCO7_IMAGE_EVIDENCE_POOL_EXECUTOR);
            ExtractEvidencePoolExecutor extractCiscoPCountingEvidencePoolExecutor = (ExtractEvidencePoolExecutor) pools
                    .get(POOL_EXTRACT_CISCO_PCOUNTING_EVIDENCE_POOL_EXECUTOR);
            
            extractNextLevelEvidencePoolExecutor.pause();
            extractBroadWareEvidencePoolExecutor.pause();
            extractCisco7VideoEvidencePoolExecutor.pause();
            extractCisco7ImageEvidencePoolExecutor.pause();
            extractCiscoPCountingEvidencePoolExecutor.pause();

            // para todos los epc pedimos sus request para el dia
            for (ExtractionPlan plan : extractionPlans) {
                if (!plan.getStoreName().equals(storeName)) {
                    // se esta pidiendo para otro plan
                    continue;
                }
                
                Date expirationDateInStore = plan.getExpirationDate();
                // obtenemos la hora de expiracion segun store
                if (expirationDateInStore != null && plan.getTimeZoneId() != null && plan.getTimeZoneId().length() > 0) {
                    // recuperamos la diferencia en hrs entre el timeZone del Store y el TimeZone del Server
                    double d = TimeZoneUtils.getDiffInHoursTimeZone(plan.getTimeZoneId());
                    log.debug("[timeZoneId:" + plan.getTimeZoneId() + "][difHours:" + d + "]");
                    expirationDateInStore = DateUtils.addHours(expirationDateInStore, (int) d * -1);
                }
                log.debug("ep:" + plan.getId() + " expirationDate " + expirationDateInStore + " in Store");
                // se asume que el primer epc
                // tansformar hora de expiracion en hora en store
                List<EvidenceExtractionRequest> eers = dao.getEvidenceExtractionRequestForAday(plan.getId(), day, date);
                // los EvidenceExtractionRequest vienen ordenados por requestedTime
                for (EvidenceExtractionRequest extractionRequest : eers) {
                    if (!extractionRequest.getAllowsExtractionPlanToPast()) {
                        // si el request no permite envio al pasado continuamos
                        continue;
                    }
                    // TODO Caso REAL_RANDOM
                    // log.debug("extractionRequest:" + extractionRequest);
                    Date evidenceDate = null;
                    try {
                        evidenceDate = DateUtils.parseDate(
                                dateInit + " " + DateFormatUtils.format(extractionRequest.getRequestedTime(), "HH:mm:ss"),
                                patterns);
                    } catch (ParseException e) {
                        log.error("no se pudo obtener fecha para la evidencia");
                        continue;
                    }
                    if (evidenceDate.before(initProcess)) { // es menor al inicio del proceso
                        continue;
                    } else if (expirationDateInStore != null && evidenceDate.after(expirationDateInStore)) {
                        // plan.getExpirationDate() se cambia por expirationDateInStore
                        // la evidencia debe ser tomada desde el siguiente plan
                        log.debug("ep terminado:" + plan.getId() + " expirationDateInStore:"
                                + DateFormatUtils.format(expirationDateInStore, patterns[0]) + " horaSaltoFin:"
                                + DateFormatUtils.format(evidenceDate, patterns[0]));
                        break;
                    } else if (evidenceDate.after(finProcess)) {// es mayor que el fin de proceso
                        log.debug("evidencia superior a fin de proceso " + DateFormatUtils.format(evidenceDate, patterns[0])
                                + " - " + DateFormatUtils.format(finProcess, patterns[0]));
                        break;
                    }
                    // log.debug("evidenceDate:" + evidenceDate);
                    initProcess = evidenceDate; // aumentamos el inicio
                    EvidenceFile evidenceFile = null;
                    if (extractionRequest.getEvidenceFiles() != null && !extractionRequest.getEvidenceFiles().isEmpty()) {
                        evidenceFile = extractionRequest.getEvidenceFiles().get(0);
                    }
                    // EvidenceFile evidenceFile = evidenceDAO.findEvidenceFileByEERAndDate(extractionRequest.getId(), date);
                    // verificación sólo para las auto generadas
                    if ((EvidenceRequestType.AUTO_GENERATED.equals(extractionRequest.getType()) || EvidenceRequestType.REAL_RANDOM
                            .equals(extractionRequest.getType())) && evidenceFile == null) {
                        // si eer no tiene fecha de creación o no corresponde a 'date', ignorar
                        // if (extractionRequest.getCreationTimestamp() == null
                        // || !DateFormatUtils.format(extractionRequest.getCreationTimestamp(), "yyyy-MM-dd").
                        // equals(DateFormatUtils.format(date, "yyyy-MM-dd"))) {
                        // continue;
                        // }
                        if (extractionRequest.getEvidenceDate() == null
                                || !DateFormatUtils.format(extractionRequest.getEvidenceDate(), "yyyy-MM-dd").equals(
                                        DateFormatUtils.format(date, "yyyy-MM-dd"))) {
                            log.debug("se excluye request extractionRequest:" + extractionRequest + " evidence_date"
                                    + extractionRequest.getEvidenceDate());
                            continue;
                        }
                    }
                    
                    if (evidenceFile == null
                            || (evidenceFile.getFileCreationDate() == null && evidenceFile.getUploadDate() == null)) {
                        try {
                            extractionRequest = genericDAO.get(extractionRequest.getId(), EvidenceExtractionRequest.class);
                            log.debug("[eer:" + extractionRequest + "]");
                            data.add(extractionRequest.getRemoteRequestId());
                            // esto se debe lanzar en thread independiente
                            extractEvidence(extractionRequest, date);
                            // hasta aqui
                        } catch (ScopixException ex) {
                            log.error("Error getting evidence " + extractionRequest.getRequestedTime() + ".", ex);
                        }
                    }
                }
                
                if (expirationDateInStore != null && expirationDateInStore.after(finProcess)) {
                    // plan.getExpirationDate() se cambia por expirationDateInStore
                    // el plan solicitado es mayor al fin del proceso ya que es para un dia en particular
                    log.debug("Fin proceso por fin ep:" + plan.getId());
                    break;
                }
            }
            extractNextLevelEvidencePoolExecutor.resume();
            extractBroadWareEvidencePoolExecutor.resume();
            extractCisco7VideoEvidencePoolExecutor.resume();
            extractCisco7ImageEvidencePoolExecutor.resume();
            extractCiscoPCountingEvidencePoolExecutor.resume();
        }
        log.debug("end, date = " + date);
        return data;
    }
    
    public synchronized void startMotionDetection(String storeName) throws ScopixException {
        
        log.debug("Start");
        // Buscamos los motion detection providers
        List<ScopixListenerJob> listeners = getListenerJobs(storeName);
        int restartMinutes = getIntegerProperties(PROPERTIES_VADARO_LISTENER_RESTART_MINUTES_DELAY);
        int reconnectSeconds = getIntegerProperties(PROPERTIES_VADARO_LISTENER_RECONNECT_SECONDS_DELAY);
        log.info("Listeners restart every " + restartMinutes + " minutes.");
        log.info("Listeners attempt to reconect every " + reconnectSeconds + " seconds.");
        log.debug("Starting  " + listeners.size() + " jobs");

        // Arrancamos threads.
        // LinkedBlockingQueue<Runnable> listenersQueue = new LinkedBlockingQueue<Runnable>();
        // listenersExecutor = new ExtractEvidencePoolExecutor(getIntegerProperties(PROPERTIES_VADARO_LISTENER_MAX_THREADS),
        // listenersQueue);
        // creamos los pool en caso de no existir
        createPoolByStore(storeName);

        // recuperar los pool asociados, pausarlos y volver a ejecutarlos
        listenersExecutor = (ExtractEvidencePoolExecutor) getMapPoolsStore().get(storeName).get(
                POOL_LISTEN_VADARO_EVENTS_EXECUTOR);
        
        listenersExecutor.shutdownNow();
        
        LinkedBlockingQueue<Runnable> listenerQueue = new LinkedBlockingQueue<Runnable>();
        listenersExecutor = new ExtractEvidencePoolExecutor(getIntegerProperties(PROPERTIES_VADARO_LISTENER_MAX_THREADS),
                listenerQueue, POOL_LISTEN_VADARO_EVENTS_EXECUTOR + "-" + storeName);

        // agregamos el pool para listener
        getMapPoolsStore().get(storeName).put(POOL_LISTEN_VADARO_EVENTS_EXECUTOR, listenersExecutor);

        // correr tasks
        for (ScopixListenerJob listener : listeners) {
            log.info("Job: " + listener);
            listenersExecutor.runTask(new ListenerThread(restartMinutes, reconnectSeconds, listener));
        }
        
        log.debug("End");
    }
    
    public synchronized void sendMotionDetection() throws ScopixException {
        // this file manager is always local
        FileManager fileManager = FileManagerFactory.getFilemanager("LOCAL");
        
        try {
            fileManager.connectAndLogin("", "", "");
        } catch (FileManagerException fmex) {
            log.debug("Error: " + fmex.getMessage());
            throw new UploadJobException("Error connecting to file manager.", fmex);
        } catch (Exception ex) {
            log.debug("Error: " + ex.getMessage());
            throw new ScopixException(ex);
        }
        
        try {
            // obtain the files in the folder
            File[] files = null;
            String camerasLocalDir = null;
            try {
                // fileManager.changeDirectory(ExtractionManager.camerasLocalDir);
                camerasLocalDir = getStringProperties(PROPERTIES_MOTIONDETECTION_CAMERAS_LOCAL_DIR);
                fileManager.changeDirectory(camerasLocalDir);
                
                files = ((LocalFileManager) fileManager).getSortedFileNames(camerasLocalDir,
                        LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
                if (files != null) {
                    log.debug("Filename: " + files.length);
                } else {
                    log.debug("Filename: files is null");
                }
            } catch (FileManagerException fmex) {
                log.debug("Error: " + fmex.getMessage());
                try {
                    fileManager.disconnect();
                } catch (FileManagerException fmex2) {
                    log.debug("Error: " + fmex2.getMessage());
                    throw new UploadJobException("Error disconnecting from ftp server while managing exception.", fmex2);
                }
                throw new UploadJobException("Error changing working directory." + camerasLocalDir, fmex);
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            Calendar cal = Calendar.getInstance();

            // read file name and obtain camera id
            String fileName;
            if (files != null) {
                for (File file : files) {
                    fileName = file.getName();
                    log.debug("fileName: " + fileName);
                    
                    try {
                        String cameraId = getCameraIdForMotionDetection(fileName);
                        log.debug("cameraId: " + cameraId);

                        // set time for get evidence
                        cal.setTimeInMillis(file.lastModified());
                        log.debug("lastModified: " + sdf.format(cal.getTime()));

                        // Modify the calendar time adding a delay (can be negative value)
                        Integer motionDetectionDelay = getIntegerProperties(PROPERTIES_MOTIONDETECTION_DELAY);
                        cal.add(Calendar.SECOND, motionDetectionDelay);
                        log.debug("lastModified + delay(" + motionDetectionDelay + "): " + sdf.format(cal.getTime()));

                        // sending evidence call
                        sendAutomaticEvidence(cameraId, cal.getTime());
                        
                    } catch (Exception ex) {
                        log.debug("Error: " + ex.getMessage());
                    }
                    fileManager.deleteFile(camerasLocalDir + fileName);
                }
            }
        } finally {
            try {
                fileManager.disconnect();
            } catch (FileManagerException ex) {
                throw new UploadJobException("Error disconnecting from file server at the end of the session.", ex);
            }
        }
    }

    /**
     * Devuelve el ID configurado en la camara necesario para la extraccion de evidencia.
     *
     * @param fileName Nombre del archivo (sin la ruta de acceso).
     * @return String ID de la camara obtenido del nombre del archivo.
     */
    private String getCameraIdForMotionDetection(String fileName) {
        String fileNameBase = FilenameUtils.getBaseName(fileName);
        Integer index = fileNameBase.indexOf("-");
        String cameraId = fileNameBase.substring(0, index);
        
        return cameraId;
    }

    /**
     * Metodo para el sistema de Motion Detect. Se encarga de agendar los envios de evidencia. Se valida el horario de la tienda,
     * el tiempo entre una peticion y otra y se obtiene la evidencia que corresponda.
     *
     * @param sensorID ID de la camara
     * @param fechaActual Fecha en que se generá la petición
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void sendAutomaticEvidence(String sensorID, Date fechaActual) throws ScopixException {
        log.info("start [sensorID:" + sensorID + "][fechaActual:" + fechaActual + "]");
        FindSituationsCommand command = new FindSituationsCommand();
        List<SituationRequest> listSituation = command.execute(sensorID);
        // se asume que el sensor pertence a un solo store
        String storeName = listSituation.get(0).getExtractionPlan().getStoreName();

        // se deben chechear intervalos y es para todas las camaras
        List<EvidenceRequestVO> preEvidenceRequestDTOs = getPreEvidenceRequestDTOsInjection(listSituation, fechaActual, true,
                null, null, false);
        scheduleEvidenceRequest(preEvidenceRequestDTOs, fechaActual, storeName, EvidenceRequestType.AUTO_GENERATED, null, true);
        
        log.info("end");
    }

    /**
     * Metodo para el Inyectar cualquier evidencia al sistema Se encarga de agendar los envios de evidencia. y se obtiene la
     * evidencia que corresponda.
     *
     * @param sensorID ID del Sensor
     * @param fechaEvidencia Fecha en que se generá la petición
     * @param checkInterval
     * @param now
     * @param cameraName
     * @param duration
     * @param beeId
     * @throws ScopixException
     */
    public void injectAutomaticEvidence(String sensorID, Date fechaEvidencia, boolean checkInterval, boolean now,
            String cameraName, Integer duration, Integer beeId) throws ScopixException {
        log.info("start, [sensorID:" + sensorID + "][fechaEvidencia:" + fechaEvidencia + "][checkInterval:" + checkInterval + "]"
                + "[cameraName:" + cameraName + "][duration:" + duration + "][beeId:" + beeId + "]");
        // FindSituationsCommand command = new FindSituationsCommand();
        FindSituationsSensorDateCommand command2 = new FindSituationsSensorDateCommand();
        // recuperamos las situaciones para el sensor Store y/o camara con este unique_id
        List<SituationRequest> listSituation = command2.execute(sensorID, fechaEvidencia);
        // Se asume que las situaciones corresponden a un solo Store dado que el sensor es Unico y por Store
        String storeName = listSituation.get(0).getExtractionPlan().getStoreName();
        // Recuperamos el ExtractionPlan para la tienda para modificar la fecha de solicitud si es now
        if (now) { // si la fecha actual es now se debe calcular que hora es en el Store asociado a los request
            try {
                // determinar por cada vo el extractionPlanLocal enable por store
                EServerDAO dao = SpringSupport.getInstance().findBeanByClassName(EServerDAO.class);
                ExtractionPlan extractionPlanLocal = dao.getExtractionPlanEnable(storeName);
                if (extractionPlanLocal.getTimeZoneId() != null && extractionPlanLocal.getTimeZoneId().length() > 0) {
                    double d = TimeZoneUtils.getDiffInHoursTimeZone(extractionPlanLocal.getTimeZoneId());
                    fechaEvidencia = DateUtils.addHours(fechaEvidencia, (int) d);
                }
            } catch (ObjectRetrievalFailureException ex) {
                log.warn("Extraction Plan not loaded." + ex.getMessage());
            } catch (IndexOutOfBoundsException ex) {
                log.warn("Extraction Plan not loaded." + ex.getMessage());
            }
            
        }
        List<EvidenceRequestVO> preEvidenceRequestDTOs = getPreEvidenceRequestDTOsInjection(listSituation, fechaEvidencia,
                checkInterval, cameraName, duration, false);
        scheduleEvidenceRequest(preEvidenceRequestDTOs, fechaEvidencia, storeName, EvidenceRequestType.AUTO_GENERATED, beeId,
                true);
        log.info("end");
    }

    /**
     * Retorna los EvidenceRequestDTO para la situacion camara
     *
     * @param listSituation
     * @return
     */
    public List<EvidenceRequestVO> getPreEvidenceRequestDTOsInjection(List<SituationRequest> listSituation, Date fechaSolicitud,
            boolean checkInterval, String cameraName, Integer duration, boolean checkIntervalCamera) throws ScopixException {
        log.info("start");
        // GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        List<EvidenceRequestVO> preEvidenceRequestDTOs = new ArrayList<EvidenceRequestVO>();
        Calendar calFechaActual = Calendar.getInstance();
        
        log.debug("recorriendo situationRequest...");
        for (SituationRequest situationRequest : listSituation) {
            calFechaActual.setTime(fechaSolicitud);
            log.debug("situationRequest ID:" + situationRequest.getId());
            log.debug("ranges.isEmpty:" + situationRequest.getSituationRequestRanges().isEmpty());
            // si la fecha esta entre las posibiliades de los rangos continuamos de lo contrario no hacemos
            SituationRequestRange range = getSituationRange(situationRequest.getSituationRequestRanges(), calFechaActual);
            if (range != null) {
                if (checkInterval) {
                    //si se debe realizar chequeo por situation camara
                    boolean next = checkIntevalForExtractionRequest(situationRequest, fechaSolicitud, range,
                            checkIntervalCamera ? cameraName : null);
                    if (!next) { // si no debemos continuar vamos al sig situation request
                        continue;
                    }
                }
                log.debug("lastProcess: " + situationRequest.getLastProcess());
                boolean isRandomCamera = situationRequest.getRandomCamera().booleanValue();
                log.debug("isRandomCamera " + isRandomCamera);
                if (cameraName == null && isRandomCamera) {
                    cameraName = getRandomCamera(situationRequest);
                }
                situationRequest.setLastProcess(fechaSolicitud);
                // dao.save(situationRequest);
                // seteamos la duracion para efectos de proceso solamente
                situationRequest.setDuration(range.getDuration());
                if (duration != null) { // en el caso que se reciba duracion se debe mantener esta
                    situationRequest.setDuration(duration);
                }
                
                log.debug("recorriendo metricRequest...");
                for (MetricRequest metricRequest : situationRequest.getMetricRequests()) {
                    for (EvidenceProviderRequest evidenceProviderRequest : metricRequest.getEvidenceProviderRequests()) {
                        if (cameraName != null
                                && !evidenceProviderRequest.getEvidenceProvider().getDescription().equals(cameraName)) {
                            // si viene nombre de camara se debe determiniar si
                            // corresponde a la actual el provider no es el que se esta tratando de lanzar
                            continue;
                        }
                        log.debug("evidenceProviderRequest:" + evidenceProviderRequest);
                        boolean create = true;
                        Collections.sort(preEvidenceRequestDTOs);
                        // recorriendo el dto que se va llenando
                        log.debug("recorriendo el DTO que se va llenando...");

                        // VERIFICAR GENERACION de los preEvidenceRequestDTOs y sus DTOS asociados
                        for (EvidenceRequestVO dTO : preEvidenceRequestDTOs) {
                            // verificando el evidenceProviderId
                            log.debug("evidenceProviderRequest.getDeviceId():" + evidenceProviderRequest.getDeviceId()
                                    + " - dTO.getEvidenceProvider().getDeviceId():" + dTO.getEvidenceProvider().getDeviceId());
                            if (evidenceProviderRequest.getDeviceId().equals(dTO.getEvidenceProvider().getDeviceId())) {
                                // duda con el get(0)
                                if (!dTO.getSituationMetricDTOs().isEmpty()) {
                                    // si la duracion es igual a la del rango o igual a la que se recibio
                                    if (dTO.getSituationMetricDTOs().get(0).getSituationRequest().getDuration()
                                            .equals(range.getDuration())
                                            || duration.equals(dTO.getSituationMetricDTOs().get(0).getSituationRequest()
                                                    .getDuration())) {
                                        SituationMetricDTO situationMetricDTO = new SituationMetricDTO();
                                        situationMetricDTO.setSituationRequest(situationRequest);
                                        situationMetricDTO.setMetricRequest(metricRequest);
                                        dTO.getSituationMetricDTOs().add(situationMetricDTO);
                                        create = false;
                                        break;
                                    }
                                }
                            }
                        }
                        
                        if (create) {
                            log.debug("creando un EvidenceRequestVO");
                            EvidenceRequestVO preEvidenceRequestDTO = new EvidenceRequestVO();
                            preEvidenceRequestDTO.setEvidenceProvider(evidenceProviderRequest.getEvidenceProvider());
                            SituationMetricDTO situationMetricDTO = new SituationMetricDTO();
                            situationMetricDTO.setSituationRequest(situationRequest);
                            situationMetricDTO.setMetricRequest(metricRequest);
                            preEvidenceRequestDTO.getSituationMetricDTOs().add(situationMetricDTO);
                            preEvidenceRequestDTOs.add(preEvidenceRequestDTO);
                        }
                    }
                }
            }
        }
        log.info("end preEvidenceRequestDTOs isEmpty " + preEvidenceRequestDTOs.isEmpty());
        return preEvidenceRequestDTOs;
    }

    // valida que la fecha se encuentre en alguno de los rangos
    private SituationRequestRange getSituationRange(List<SituationRequestRange> situationRequestRanges, Calendar calFechaActual) {
        int actualHour = calFechaActual.get(Calendar.HOUR_OF_DAY) * 100 + calFechaActual.get(Calendar.MINUTE);
        log.debug("actualHour:" + actualHour + " dayOfWeek:" + calFechaActual.get(Calendar.DAY_OF_WEEK) + " calFechaActual:"
                + calFechaActual.getTime());
        for (SituationRequestRange range : situationRequestRanges) {
            if (range.getDayOfWeek() == calFechaActual.get(Calendar.DAY_OF_WEEK)) {
                Calendar calInitialTime = Calendar.getInstance();
                Calendar calEndTime = Calendar.getInstance();
                
                calInitialTime.setTime(range.getInitialTime());
                calEndTime.setTime(range.getEndTime());
                int initialTime = calInitialTime.get(Calendar.HOUR_OF_DAY) * 100 + calInitialTime.get(Calendar.MINUTE);
                log.debug("initialTime:" + initialTime);
                int endTime = calEndTime.get(Calendar.HOUR_OF_DAY) * 100 + calEndTime.get(Calendar.MINUTE);
                log.debug("endTime:" + endTime);
                if ((initialTime <= actualHour) && (endTime >= actualHour)) {
                    return range;
                }
            }
        }
        return null;
        
    }

    /**
     * Recuperar todas EvidenceFile para una situacion dia
     */
    private List<EvidenceExtractionRequest> getEvidenceExtractionRequestBySituation(SituationRequest situationRequest,
            Date fechaSolicitud) throws ScopixException {
        GetEvidenceExtractionRequestBySituationCommand command = new GetEvidenceExtractionRequestBySituationCommand();
        return command.execute(situationRequest, fechaSolicitud);
    }

    /**
     * Se agenda el envio de evidencia. Esto se utiliza para el envio automatico de evidencia, store
     *
     * @param evidenceRequestDTOs
     * @param fechaEvidencia
     * @return List<EvidenceExtractionRequest>
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<EvidenceExtractionRequest> scheduleEvidenceRequest(List<EvidenceRequestVO> evidenceRequestDTOs,
            Date fechaEvidencia, String storeName, EvidenceRequestType evidenceRequestType, Integer beeId, boolean schedule)
            throws ScopixException {
        log.info("start schedule");
        List<EvidenceExtractionRequest> list = new ArrayList<EvidenceExtractionRequest>();
        GetProcessIdCommand getProcessIdCommand = new GetProcessIdCommand();
        
        ExtractionPlan extractionPlanLocal = getExtracionPlanLocal(fechaEvidencia, storeName);
        
        if (evidenceRequestDTOs != null && !evidenceRequestDTOs.isEmpty()) {
            GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
            EvidenceExtractionRequestDAO extractionRequestDAO = SpringSupport.getInstance().findBeanByClassName(
                    EvidenceExtractionRequestDAO.class);
            // processId es uno por grupo
            Integer processId = getProcessIdCommand.execute();
            log.debug("processId = " + processId);
            for (EvidenceRequestVO preEvidenceRequestDTO : evidenceRequestDTOs) {
                log.debug("EvidenceProvider = " + preEvidenceRequestDTO.getEvidenceProvider().getClass().getSimpleName());
                // Calendar cal = Calendar.getInstance();
                // cal.setTime(fechaEvidencia);
                // cal.get(Calendar.DAY_OF_WEEK);
                Integer dayOfWeek = getDOW(fechaEvidencia);
                
                EvidenceExtractionRequest evidenceExtractionRequest = getClassByPreEvidenceRequestDTO(preEvidenceRequestDTO);
                
                if (evidenceExtractionRequest == null) {
                    log.debug("Not exits EvidenceExtractionRequest for Provider "
                            + preEvidenceRequestDTO.getEvidenceProvider().getClass().getSimpleName());
                    continue;
                }
                
                evidenceExtractionRequest.setPriorization(preEvidenceRequestDTO.getSituationMetricDTOs().get(0)
                        .getSituationRequest().getPriorization());
                evidenceExtractionRequest.setProcessId(processId);
                evidenceExtractionRequest.setType(evidenceRequestType); // EvidenceRequestType.AUTO_GENERATED
                evidenceExtractionRequest.setExtractionPlan(extractionPlanLocal);
                evidenceExtractionRequest.setEvidenceDate(fechaEvidencia);
                evidenceExtractionRequest.setRequestedTime(fechaEvidencia);
                
                evidenceExtractionRequest.setDayOfWeek(dayOfWeek);
                evidenceExtractionRequest.setEvidenceProvider(preEvidenceRequestDTO.getEvidenceProvider());
                // evidenceExtractionRequest.setCreationTimestamp(fechaActual);
                evidenceExtractionRequest.setCreationTimestamp(new Date());
                evidenceExtractionRequest.setBeeId(beeId);
                
                Integer id = extractionRequestDAO.saveEERS(evidenceExtractionRequest);
                evidenceExtractionRequest = dao.get(id, EvidenceExtractionRequest.class);
                log.debug("evidenceExtractionRequest = " + evidenceExtractionRequest);
                list.add(evidenceExtractionRequest);
                List<SituationMetricDTO> situationMetricDTOs = preEvidenceRequestDTO.getSituationMetricDTOs();
                log.debug("situationMetricDTOs = " + situationMetricDTOs.size());
                SituationMetricExtractionRequest situationMetricExtractionRequest;
                for (SituationMetricDTO situationMetricDTO : situationMetricDTOs) {
                    situationMetricExtractionRequest = new SituationMetricExtractionRequest();
                    situationMetricExtractionRequest.setEvidenceExtractionRequest(evidenceExtractionRequest);
                    situationMetricExtractionRequest.setSituationTemplateId(situationMetricDTO.getSituationRequest()
                            .getSituationTemplateId());
                    situationMetricExtractionRequest.setMetricTemplateId(situationMetricDTO.getMetricRequest()
                            .getMetricTemplateId());
                    
                    dao.save(situationMetricExtractionRequest);
                }
                if (schedule) {
                    scheduleEvidenceRequest(evidenceExtractionRequest, extractionPlanLocal);
                }
            }
        }
        log.info("end");
        return list;
    }
    
    private void scheduleEvidenceRequest(EvidenceExtractionRequest evidenceExtractionRequest, ExtractionPlan extractionPlanLocal) {
        Calendar calTemp = Calendar.getInstance();
        Calendar calJob = Calendar.getInstance();
        try {
            JobDetail jobDetail = new JobDetailImpl("TransferAutomaticJob" + evidenceExtractionRequest.getId(), "TransferGroup",
                    CutJob.class);
            JobDataMap jobDataMap = new JobDataMap();
            // jobDataMap.put("EVIDENCE_EXTRACTION_REQUEST", evidenceExtractionRequest);
            jobDataMap.put("EVIDENCE_EXTRACTION_REQUEST_ID", evidenceExtractionRequest.getId());
            // para metro no se ocupa
            // jobDataMap.put("fechaActual", fechaActual);
            // jobDetail.setJobDataMap(jobDataMap);
            jobDetail.getJobDataMap().putAll(jobDataMap);
            /**
             * Validacion de fecha para la generacion de Triggers siempre a futuro 2010-07-02 *
             */
            Date dateInStore = new Date(); // el calculo de horas se realiza contra la hora del store
            if (extractionPlanLocal.getTimeZoneId() != null && extractionPlanLocal.getTimeZoneId().length() > 0) {
                // si existe diferencia horaria con la tienda lo calcualmos para que la fecha de la evidencia corresponda
                double d = TimeZoneUtils.getDiffInHoursTimeZone(extractionPlanLocal.getTimeZoneId());
                dateInStore = DateUtils.addHours(dateInStore, (int) d);
            }
            log.debug("[evidenceExtractionRequest.getEvidenceDate():" + evidenceExtractionRequest.getEvidenceDate() + "]"
                    + "[dateInStore:" + dateInStore + "]");

            // verificamos el delay para lsa solicitud
            int delayInSecs = getLengthInSecs(evidenceExtractionRequest);
            int minutes = delayInSecs / 60;
            
            if (evidenceExtractionRequest.getType().equals(EvidenceRequestType.REAL_RANDOM)) {
                log.debug("condicion evidenceExtractionRequest.getType().equals(EvidenceRequestType.REAL_RANDOM)");
                calTemp.setTime(new Date());
                calTemp.add(Calendar.MINUTE, 1 + minutes);
            } else if (evidenceExtractionRequest.getEvidenceDate().before(dateInStore)) { // ExtractionStartTime
                log.debug("condicion evidenceExtractionRequest.getEvidenceDate().before(dateInStore)");
                calTemp.setTime(new Date());
                calTemp.add(Calendar.MINUTE, 1 + minutes);
            } else {
                log.debug("condicion no evidenceExtractionRequest.getEvidenceDate().before(dateInStore)");
                // calculamos la hora en que se debe ejecutar en el Store
                Date newDate = new Date(evidenceExtractionRequest.getEvidenceDate().getTime()); // ExtractionStartTime()
                if (extractionPlanLocal.getTimeZoneId() != null && extractionPlanLocal.getTimeZoneId().length() > 0) {
                    double d = TimeZoneUtils.getDiffHourTimezoneToServer(extractionPlanLocal.getTimeZoneId());
                    newDate = DateUtils.addHours(newDate, (int) d);
                    // se agregamos ademas el largo de la evidencia en el caso q exista
                    newDate = DateUtils.addMinutes(newDate, minutes);
                }
                calTemp.setTime(newDate);
                // calTemp.setTime(evidenceExtractionRequest.getExtractionStartTime());
            }
            
            calJob.setTime(calTemp.getTime());
            calJob.set(Calendar.HOUR_OF_DAY, calTemp.get(Calendar.HOUR_OF_DAY));
            calJob.set(Calendar.MINUTE, calTemp.get(Calendar.MINUTE));
            calJob.set(Calendar.SECOND, calTemp.get(Calendar.SECOND));
            log.debug("creation job for [time:" + calJob.getTime() + "]" + "[eer.id:" + evidenceExtractionRequest.getId() + "]");

            // SimpleTrigger trigger = new SimpleTriggerImpl("TransferSimpleTrigger_" + evidenceExtractionRequest.getId(),
            // "TransferGroup", calJob.getTime());
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("TransferSimpleTrigger_" + evidenceExtractionRequest.getId(), "TransferGroup")
                    .startAt(calJob.getTime()).build();
            
            getSchedulerManager().addJob(jobDetail, trigger);
        } catch (ScopixException sex) {
            log.debug("error al agendar el job de subida de archivo (motion detect): " + sex.getMessage());
        }
    }
    
    private ExtractionPlan getExtracionPlanLocal(Date fechaEvidencia, String storeName) {
        ExtractionPlan extractionPlanLocal = null;
        try {
            // determinar por cada vo el extractionPlanLocal enable por store
            EServerDAO dao = SpringSupport.getInstance().findBeanByClassName(EServerDAO.class);
            List<ExtractionPlan> eps = dao.getExtractionPlanListForADate(fechaEvidencia, storeName);
            // recuperamos el primero dado que
            // extractionPlanLocal = dao.getExtractionPlanEnable(storeName);
            extractionPlanLocal = eps.get(0);
        } catch (ObjectRetrievalFailureException ex) {
            log.warn("Extraction Plan not loaded." + ex.getMessage());
        } catch (IndexOutOfBoundsException ex) {
            log.warn("Extraction Plan not loaded." + ex.getMessage());
        }
        return extractionPlanLocal;
    }
    
    private int getLengthInSecs(EvidenceExtractionRequest evidenceExtractionRequest) {
        int secs = 1;
        if (evidenceExtractionRequest instanceof EvidenceExtractionRequestVideo) {
            secs = ((EvidenceExtractionRequestVideo) evidenceExtractionRequest).getLengthInSecs();
        } else if (evidenceExtractionRequest instanceof BroadwareVideoExtractionRequest) {
            secs = ((BroadwareVideoExtractionRequest) evidenceExtractionRequest).getLengthInSecs();
        } else if (evidenceExtractionRequest instanceof AxisGenericVideoExtractionRequest) {
            secs = ((AxisGenericVideoExtractionRequest) evidenceExtractionRequest).getLengthInSecs();
        } else if (evidenceExtractionRequest instanceof NextLevelVideoExtractionRequest) {
            secs = ((NextLevelVideoExtractionRequest) evidenceExtractionRequest).getLengthInSecs();
        } else if (evidenceExtractionRequest instanceof VMSGatewayVideoExtractionRequest) {
            secs = ((VMSGatewayVideoExtractionRequest) evidenceExtractionRequest).getLengthInSecs();
        } else if (evidenceExtractionRequest instanceof BroadwareHTTPVideoExtractionRequest) {
            secs = ((BroadwareHTTPVideoExtractionRequest) evidenceExtractionRequest).getLengthInSecs();
        }
        return secs;
    }
    
    private EvidenceExtractionRequest getClassByPreEvidenceRequestDTO(EvidenceRequestVO preEvidenceRequestDTO) {
        log.info("start");
        EvidenceExtractionRequest ret = null;
        if (preEvidenceRequestDTO.getEvidenceProvider() == null) {
            return ret;
        }
        
        EvidenceProvider provider = preEvidenceRequestDTO.getEvidenceProvider();
        
        log.debug("EvidenceProvider = " + provider.getClass().getSimpleName());
        
        if (provider instanceof BroadwareEvidenceProvider) {
            if (preEvidenceRequestDTO.getSituationMetricDTOs() != null
                    && preEvidenceRequestDTO.getSituationMetricDTOs().get(0).getSituationRequest().getDuration() != null
                    && preEvidenceRequestDTO.getSituationMetricDTOs().get(0).getSituationRequest().getDuration() > 0) {
                ret = new BroadwareVideoExtractionRequest();
                ((BroadwareVideoExtractionRequest) ret).setLengthInSecs(preEvidenceRequestDTO.getSituationMetricDTOs().get(0)
                        .getSituationRequest().getDuration());
            } else {
                ret = new BroadwareImageExtractionRequest();
            }
        } else if (provider instanceof PeopleCountingEvidenceProvider) {
            ret = new PeopleCountingExtractionRequest();
        } else if (provider instanceof AxisP3301EvidenceProvider) {
            ret = new AxisP3301ImageExtractionRequest();
        } else if (provider instanceof KumGoEvidenceProvider) {
            ret = new KumGoImageExtractionRequest();
        } else if (provider instanceof CognimaticsPeopleCounter141EvidenceProvider) {
            ret = new CognimaticsPeopleCounter141ExtractionRequest();
        } else if (provider instanceof CognimaticsPeopleCounter212EvidenceProvider) {
            ret = new CognimaticsPeopleCounter212ExtractionRequest();
        } else if (provider instanceof CognimaticsPeopleCounter150EvidenceProvider) {
            ret = new CognimaticsPeopleCounter150ExtractionRequest();
        } else if (provider instanceof AxisGenericEvidenceProvider) {
            ret = new AxisGenericVideoExtractionRequest();
            ((AxisGenericVideoExtractionRequest) ret).setLengthInSecs(preEvidenceRequestDTO.getSituationMetricDTOs().get(0)
                    .getSituationRequest().getDuration());
        } else if (provider instanceof NextLevelEvidenceProvider) {
            ret = new NextLevelVideoExtractionRequest();
            ((NextLevelVideoExtractionRequest) ret).setLengthInSecs(preEvidenceRequestDTO.getSituationMetricDTOs().get(0)
                    .getSituationRequest().getDuration());
        } else if (provider instanceof BrickcomEvidenceProvider) {
            ret = new BrickcomImageExtractionRequest();
        } else if (provider instanceof ArecontEvidenceProvider) {
            ret = new ArecontImageExtractionRequest();
        } else if (provider instanceof VMSGatewayEvidenceProvider) {
            ret = new VMSGatewayVideoExtractionRequest();
            ((VMSGatewayVideoExtractionRequest) ret).setLengthInSecs(preEvidenceRequestDTO.getSituationMetricDTOs().get(0)
                    .getSituationRequest().getDuration());
            // seteamos la variable para saber si puede o no pedir al pasado
            ((VMSGatewayVideoExtractionRequest) ret).setAllowsExtractionPlanToPast(((VMSGatewayEvidenceProvider) provider)
                    .getExtractionPlanToPast());
        } else if (provider instanceof BroadwareHTTPEvidenceProvider) {
            ret = new BroadwareHTTPVideoExtractionRequest();
            ((BroadwareHTTPVideoExtractionRequest) ret).setLengthInSecs(preEvidenceRequestDTO.getSituationMetricDTOs().get(0)
                    .getSituationRequest().getDuration());
        } else if (provider instanceof Cisco7EvidenceProvider) {
            if (preEvidenceRequestDTO.getSituationMetricDTOs() != null
                    && preEvidenceRequestDTO.getSituationMetricDTOs().get(0).getSituationRequest().getDuration() != null
                    && preEvidenceRequestDTO.getSituationMetricDTOs().get(0).getSituationRequest().getDuration() > 0) {
                ret = new Cisco7VideoExtractionRequest();
                ((Cisco7VideoExtractionRequest) ret).setLengthInSecs(preEvidenceRequestDTO.getSituationMetricDTOs().get(0)
                        .getSituationRequest().getDuration());
            } else {
                ret = new Cisco7ImageExtractionRequest();
            }
        } else if (provider instanceof VadaroPeopleCountingEvidenceProvider) {
            ret = new VadaroPeopleCountingExtractionRequest();
        } else if (provider instanceof VadaroEvidenceProvider) {
            // TODO Revisar que existan los que corresponden
            ret = new VadaroXmlExtractionRequest();
        } else {
            log.debug("No existe Evidence request para provider " + provider.getClass().getSimpleName());
        }
        
        log.info("end");
        return ret;
    }

    /**
     * Este metodo es para pruebas se llama desde controller
     *
     * @param operation
     */
    public boolean uploadEvidenceToExternalDevice(String operation, String storeName) throws ScopixException {
        boolean resp = false;
        // verificar operacion y validar de acuerdo a la operacion
        if (operation.equals(ExtractionManager.DEVICE_MOUNT)) {
            resp = mountExternalDevice();
        } else if (operation.equals(ExtractionManager.DEVICE_UNMOUNT)) {
            resp = unMountExternalDevice(storeName);
        } else if (operation.equals(ExtractionManager.DEVICE_FORMAT)) {
            resp = formatDevice();
        } else if (operation.equals(ExtractionManager.DEVICE_INFORMATION)) {
            getDeviceInfo();
        } else {
            // throw new InvalidParameter("Invalid parameter: " + operation);
            throw new ScopixException("Invalid parameter: " + operation);
        }

        // otras operaciones...
        return resp;
    }

    /**
     * Este metodo sirve para montar el dispositivo. Ademas se crea el JOB para la copia de archivos al dispositivo.
     *
     * @return
     * @throws ScopixException
     */
    public boolean mountExternalDevice() throws ScopixException {
        log.debug("start");
        boolean resp = false;
        try {
            CheckPartitionExistCommand checkPartitionExistCommand = new CheckPartitionExistCommand();
            
            if (checkPartitionExistCommand.execute()) {
                CheckMountDeviceCommand checkMountDeviceCommand = new CheckMountDeviceCommand();
                
                if (!checkMountDeviceCommand.execute()) {
                    log.debug("Disco desmontado, montando...");
                    MountExternalDeviceCommand command = new MountExternalDeviceCommand();
                    resp = command.execute();
                } else {
                    log.debug("Disco previamente montado. Continuando... ");
                    resp = true;
                }
                
                log.debug("Mount Status: " + resp);
                
                if (resp) {
                    // schedulerManager.addJob("", uploadCronExpression, "UploadEvidenceJob", UploadEvidenceJob.class);
                    getSchedulerManager().addJob(getStringProperties(PROPERTIES_UPLOADJOB_UPLOAD_CRON_EXPRESSION),
                            "UploadEvidenceJob", UploadEvidenceJob.class);
                }
            }
        } catch (Exception ex) {
            log.debug("Exception Error: " + ex.getMessage());
        }
        log.debug("end");
        return resp;
    }

    /**
     * Este metodo sirve para desmontar el dispositivo. Se verifica previamente que no existan JOBs en ejecuci�n, de estarlo se
     * espera por su t�rmino.
     *
     * @return
     * @throws ScopixException
     */
    public boolean unMountExternalDevice(String storeName) throws ScopixException {
        log.debug("start");
        boolean resp = false;
        
        try {
            CheckMountDeviceCommand checkMountDeviceCommand = new CheckMountDeviceCommand();
            
            if (checkMountDeviceCommand.execute()) {
                resp = getSchedulerManager().deleteJob("UploadEvidenceJob", "UploadGroup");
                
                log.debug("deleteJob: " + resp);
                UploadEvidencePoolExecutor uploadEvidencePoolExecutor = (UploadEvidencePoolExecutor) getMapPoolsStore().get(
                        storeName).get(POOL_UPLOAD_EVIDENCE_POOL_EXECUTOR);
                int runningJobs = uploadEvidencePoolExecutor.getActiveCount();
                log.debug("runningJob: " + runningJobs);

                // se mantiene el ciclo hasta que finalicen los job en proceso.
                while (runningJobs > 0) {
                    runningJobs = uploadEvidencePoolExecutor.getActiveCount();
                    log.debug("runningJob: " + runningJobs);
                    // wait(waitTime);
                    wait(getLongProperties(PROPERTIES_UPLOADJOB_WAIT_TIME).longValue());
                }
                
                log.debug("Desmontando Unidad...");

                // desmontando unidad
                UnMountExternalDeviceCommand command = new UnMountExternalDeviceCommand();
                resp = command.execute();
                
                log.debug("Unidad Desmontada. Status: " + resp);
            }
        } catch (Exception ex) {
            log.debug("Exception Error: " + ex.getMessage());
        }
        log.debug("end.");
        return resp;
    }

    /**
     * Este metodo sirve para formatear el dispositivo.
     *
     * @return
     * @throws ScopixException
     */
    public boolean formatDevice() throws ScopixException {
        log.debug("start");
        boolean resp = false;
        
        try {
            FormatDeviceCommand command = new FormatDeviceCommand();
            resp = command.execute();
            
        } catch (ScopixException pex) {
            throw pex;
        }
        
        log.debug("end. Status: " + resp);
        return resp;
    }

    /**
     * Este metodo obtiene informacion del dispositivo. Por ahora no esta implementado.
     */
    public void getDeviceInfo() {
        // Este metodo sera implementado mas adelante
        throw new NotImplementedException("Este metodo sera implementado mas adelante");
    }

    /**
     * Este metodo devuelve el numero de job encolados.
     *
     * @return
     */
    public Integer getCopyInfo(String storeName) {
        log.debug("start");
        Integer resp = null;
        // UploadEvidencePoolExecutor uploadEvidencePoolExecutor =
        // (UploadEvidencePoolExecutor) getMapPoolsStore().get(storeName).get(POOL_UPLOAD_EVIDENCE_POOL_EXECUTOR);
        UploadEvidencePoolExecutor poolExecutor = getUploadEvidencePoolExecutor();
        int runningJobs = poolExecutor.getActiveCount();
        log.debug("runningJob: " + runningJobs);
        
        log.debug("end");
        return resp;
    }
    
    public EvidenceFile getEvidenceFileByFileName(String fileName) throws ScopixException {
        EvidenceFileDAO evidenceFileDAO = HibernateSupport.getInstance().findDao(EvidenceFileDAO.class);
        EvidenceFile evidenceFile = evidenceFileDAO.findEvidenceFileByFileName(fileName);
        
        return evidenceFile;
    }

    /**
     * Encapsulamos los metodos del UploadEvidencThread para no perder las Transacciones
     */
    public void uploadEvidenceToFile(String fileName, Map<String, String> params, String remoteFileManagerType,
            FileManager localFileManager, EvidenceWebService webservice, Set<String> evidenceUploading) throws ScopixException {
        EvidenceFile evidenceFile = getEvidenceFileByFileName(fileName);
        UploadEvidenceToFileCommand command = new UploadEvidenceToFileCommand();
        command.execute(fileName, evidenceFile, params, remoteFileManagerType, localFileManager, webservice, evidenceUploading);
    }
    
    public NextLevelVideoFileReadyCommand getNextLevelVideoFileReadyCommand() {
        if (nextLevelVideoFileReadyCommand == null) {
            nextLevelVideoFileReadyCommand = new NextLevelVideoFileReadyCommand();
        }
        return nextLevelVideoFileReadyCommand;
    }
    
    public void setNextLevelVideoFileReadyCommand(NextLevelVideoFileReadyCommand nextLevelVideoFileReadyCommand) {
        this.nextLevelVideoFileReadyCommand = nextLevelVideoFileReadyCommand;
    }
    
    public PropertiesConfiguration getConfiguration() {
        if (configuration == null) {
            try {
                configuration = new PropertiesConfiguration("system.properties");
                configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
            } catch (ConfigurationException e) {
                log.error("No es posible cargar propiedades " + e, e);
            }
        }
        return configuration;
    }
    
    public void setConfiguration(PropertiesConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Retorna un String con la propiedad solicitada, si la propiedad no existe o es null retorna blanco
     */
    public String getStringProperties(String key) {
        return getConfiguration().getString(key, "");
    }
    
    public Integer getIntegerProperties(String key) {
        return getConfiguration().getInteger(key, -1);
    }
    
    public Long getLongProperties(String key) {
        return getConfiguration().getLong(key, 0L);
    }
    
    public Map<String, Map<String, ThreadPoolExecutor>> getMapPoolsStore() {
        if (mapPoolsStore == null) {
            mapPoolsStore = new HashMap<String, Map<String, ThreadPoolExecutor>>();
        }
        return mapPoolsStore;
    }
    
    public void setMapPoolsStore(Map<String, Map<String, ThreadPoolExecutor>> aMapPool) {
        mapPoolsStore = aMapPool;
    }
    
    public SchedulerManager getSchedulerManager() {
        if (schedulerManager == null) {
            schedulerManager = SpringSupport.getInstance().findBeanByClassName(SchedulerManager.class);
        }
        return schedulerManager;
    }
    
    public void setSchedulerManager(SchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }
    
    public ExtractEvidencePoolExecutor getDownloadVmsgatewayPoolExecutor() {
        if (downloadVmsgatewayPoolExecutor == null) {
            // si el pool no esta generado lo generamos
            LinkedBlockingQueue<Runnable> downloadThreadQueue = new LinkedBlockingQueue<Runnable>();
            downloadVmsgatewayPoolExecutor = new ExtractEvidencePoolExecutor(
                    getIntegerProperties(PROPERTIES_DOWNLOAD_VMS_MAX_THREADS), downloadThreadQueue,
                    POOL_DOWNLOAD_VMSGATEWAY_POOL_EXECUTOR);
            
        }
        return downloadVmsgatewayPoolExecutor;
    }
    
    public void setDownloadVmsgatewayPoolExecutor(ExtractEvidencePoolExecutor downloadVmsgatewayPoolExecutor) {
        this.downloadVmsgatewayPoolExecutor = downloadVmsgatewayPoolExecutor;
    }
    
    public VmsGatewayMarkEvidenceFileCommand getVmsGatewayMarkEvidenceFileCommand() {
        if (vmsGatewayMarkEvidenceFileCommand == null) {
            vmsGatewayMarkEvidenceFileCommand = new VmsGatewayMarkEvidenceFileCommand();
        }
        return vmsGatewayMarkEvidenceFileCommand;
    }
    
    public void setVmsGatewayMarkEvidenceFileCommand(VmsGatewayMarkEvidenceFileCommand vmsGatewayMarkEvidenceFileCommand) {
        this.vmsGatewayMarkEvidenceFileCommand = vmsGatewayMarkEvidenceFileCommand;
    }
    
    private String getRandomCamera(SituationRequest situationRequest) {
        Set<String> cameras = new HashSet<String>();
        
        List<EvidenceProviderRequest> list = situationRequest.getEvidenceProviderRequests();
        log.debug("EvidenceProviderRequests.size:" + list.size());
        for (EvidenceProviderRequest epr : list) {
            cameras.add(epr.getEvidenceProvider().getDescription());
        }
        log.debug("cameras:" + cameras.size());
        String name = null;
        if (cameras.size() > 0) {
            String[] names = cameras.toArray(new String[0]);
            int position = (int) (Math.random() * 100) % cameras.size();
            name = names[position];
        }
        if (name == null || name.length() == 0) {
            log.error("no se recupera name de camara");
            name = "";
        }
        return name;
    }
    
    public void generateRequestFromRealRandom(Integer realRandomId, String group) {
        SituationRequestRealRandom realRandom = getSituationRequestRealRandomCommand().execute(realRandomId);
        generateRequestFromRealRandom(realRandom, group);
    }
    
    public void generateRequestFromRealRandom(SituationRequestRealRandom realRandom, String group) {
        log.info("start");
        try {
            // creamos el nuevo job y lo agendamos
            getSchedulerManager().newJobRealRandom(realRandom, group);

            // genermos los datos de ejecucion con el job actual
            List<SituationRequest> situationRequests = new ArrayList<SituationRequest>();
            
            SituationRequest sr = getSituationRequestCommanand().execute(
                    realRandom.getSituationRequestRange().getSituationRequest().getId());
            situationRequests.add(sr);
            String cameraName = null;
            if (sr.getRandomCamera()) {
                cameraName = getRandomCamera(sr);
            }
            // generamos los VOs asociados
            Date requestTime = realRandom.getRequestedTime();
            Calendar d = Calendar.getInstance();
            requestTime = DateUtils.setYears(requestTime, d.get(Calendar.YEAR));
            requestTime = DateUtils.setMonths(requestTime, d.get(Calendar.MONTH));
            requestTime = DateUtils.setDays(requestTime, d.get(Calendar.DATE));
            List<EvidenceRequestVO> evidenceRequestVOs = getPreEvidenceRequestDTOsInjection(situationRequests, requestTime,
                    false, cameraName, realRandom.getSituationRequestRange().getDuration(), false);
            // agendamos la solicitud de los request
            scheduleEvidenceRequest(evidenceRequestVOs, requestTime, sr.getExtractionPlan().getStoreName(),
                    EvidenceRequestType.REAL_RANDOM, null, true);
        } catch (ScopixException e) {
            log.error("Error " + e, e);
        }
        log.info("end");
    }
    
    private NextLevelManager getNextLevelManager() {
        if (nextLevelManager == null) {
            nextLevelManager = SpringSupport.getInstance().findBeanByClassName(NextLevelManager.class);
        }
        return nextLevelManager;
    }

    /**
     * @return Comando para recuperar un situation request en particular
     */
    public GetSituationRequestCommanand getSituationRequestCommanand() {
        if (situationRequestCommanand == null) {
            situationRequestCommanand = new GetSituationRequestCommanand();
        }
        return situationRequestCommanand;
    }

    /**
     * @param situationRequestCommanand the situationRequestCommanand to set
     */
    public void setSituationRequestCommanand(GetSituationRequestCommanand situationRequestCommanand) {
        this.situationRequestCommanand = situationRequestCommanand;
    }

    /**
     * Recupera el Pool Inicializado si este no existe crea uno con los valores por defecto del system.properties
     *
     * @return the uploadEvidencePoolExecutor
     */
    public UploadEvidencePoolExecutor getUploadEvidencePoolExecutor() {
        if (uploadEvidencePoolExecutor == null) {
            LinkedBlockingQueue<Runnable> uploadThreadQueue = new LinkedBlockingQueue<Runnable>();
            uploadEvidencePoolExecutor = new UploadEvidencePoolExecutor(
                    getIntegerProperties(PROPERTIES_UPLOADJOB_UPLOAD_MAX_THREADS), uploadThreadQueue,
                    "CRON-UploadEvidencePoolExecutor");
        }
        return uploadEvidencePoolExecutor;
    }

    /**
     * @param uploadEvidencePoolExecutor the uploadEvidencePoolExecutor to set
     */
    public void setUploadEvidencePoolExecutor(UploadEvidencePoolExecutor uploadEvidencePoolExecutor) {
        this.uploadEvidencePoolExecutor = uploadEvidencePoolExecutor;
    }

    /**
     * @return the pushUploadEvidencePoolExecutor
     */
    public UploadEvidencePoolExecutor getPushUploadEvidencePoolExecutor() {
        if (pushUploadEvidencePoolExecutor == null) {
            LinkedBlockingQueue<Runnable> uploadThreadQueue = new LinkedBlockingQueue<Runnable>();
            pushUploadEvidencePoolExecutor = new UploadEvidencePoolExecutor(
                    getIntegerProperties(PROPERTIES_UPLOADJOB_UPLOAD_MAX_THREADS), uploadThreadQueue,
                    "PUSH-UploadEvidencePoolExecutor");
        }
        return pushUploadEvidencePoolExecutor;
    }

    /**
     * @param pushUploadEvidencePoolExecutor the pushUploadEvidencePoolExecutor to set
     */
    public void setPushUploadEvidencePoolExecutor(UploadEvidencePoolExecutor pushUploadEvidencePoolExecutor) {
        this.pushUploadEvidencePoolExecutor = pushUploadEvidencePoolExecutor;
    }

    /**
     * @return the situationRequestRealRandomCommand
     */
    public GetSituationRequestRealRandomCommand getSituationRequestRealRandomCommand() {
        if (situationRequestRealRandomCommand == null) {
            situationRequestRealRandomCommand = new GetSituationRequestRealRandomCommand();
        }
        return situationRequestRealRandomCommand;
    }

    /**
     * @param situationRequestRealRandomCommand the situationRequestRealRandomCommand to set
     */
    public void setSituationRequestRealRandomCommand(GetSituationRequestRealRandomCommand situationRequestRealRandomCommand) {
        this.situationRequestRealRandomCommand = situationRequestRealRandomCommand;
    }
    
    private List<ScopixJob> getAllJobs() {
        List<ScopixJob> list = null;
        GetAllJobsCommand command = new GetAllJobsCommand();
        list = command.execute();
        return list;
    }
    
    private List<ScopixJob> getRealRandomJobs() {
        List<ScopixJob> list = null;
        GetRealRandomJobsCommand command = new GetRealRandomJobsCommand();
        list = command.execute();
        return list;
        
    }
    
    private List<ScopixListenerJob> getListenerJobs(String storeName) {
        List<ScopixListenerJob> list = null;
        GetListenerJobsCommand command = new GetListenerJobsCommand();
        list = command.execute(storeName);
        return list;
        
    }

    /**
     * Genera los jobs para realRandom y los deja listos para su ejecucion
     *
     * @param timeZones
     * @param dayOfWeek
     * @throws ScopixException
     */
    public void extractEvidenceRealRandom(List<RequestTimeZone> timeZones, Integer dayOfWeek) throws ScopixException {
        Calendar d = Calendar.getInstance();

        // recuperar los rangos asociados para store - hour - day_of_week y generar triggers para las ejecuciones
        List<SituationRequestRange> srrs = new ListSituationEvidenceExtractionRequestCommand().execute(timeZones, dayOfWeek);
        
        for (SituationRequestRange range : srrs) {
            // calcular random para pedida y lanzar
            log.debug("[range:" + range.getId() + "][initialTime:" + DateFormatUtils.format(range.getInitialTime(), "HH:mm")
                    + "][endTime:" + DateFormatUtils.format(range.getEndTime(), "HH:mm") + "]");
            int largoBloque = range.getFrecuency() / range.getSamples();
            int duracionMinutes = 1;
            if (range.getDuration() >= 60) {
                duracionMinutes = (range.getDuration() / 60);
            }

            // Store timezone diff
            double diffInHoursTimeZone = TimeZoneUtils.getDiffInHoursTimeZone(range.getSituationRequest().getExtractionPlan()
                    .getTimeZoneId(), TimeZone.getDefault().getID());

            // Calcular dia del store.
            Date storeDate = TimeZoneUtils.calculateDayOfWeek(new Date(), diffInHoursTimeZone);
            d.setTimeInMillis(storeDate.getTime());

            // generamos los posibles terminos en minutos
            // es decir desde el punto de inicio en cuantos minutos se puede comenzar
            Integer[] topeMuestraMinutos = new Integer[range.getSamples()];
            topeMuestraMinutos[0] = largoBloque - duracionMinutes;
            for (int i = 1; i < range.getSamples(); i++) {
                topeMuestraMinutos[i] = topeMuestraMinutos[i - 1] + largoBloque;
            }
            log.info("topeMuestraMinutos " + StringUtils.join(topeMuestraMinutos, ","));

            // desde el incio de la frecuencia agregamos los rangos intermedios para la toma de las muestras
            Date initFrecuency = range.getInitialTime();
            // Set correct store date
            initFrecuency = DateUtils.setYears(initFrecuency, d.get(Calendar.YEAR));
            initFrecuency = DateUtils.setMonths(initFrecuency, d.get(Calendar.MONTH));
            initFrecuency = DateUtils.setDays(initFrecuency, d.get(Calendar.DAY_OF_MONTH));
            
            ExtractionPlan plan = range.getSituationRequest().getExtractionPlan();

            // revisar que pasa con el untimo bloque del rango
            Date endTime = range.getEndTime();
            // Set correct store date
            endTime = DateUtils.setYears(endTime, d.get(Calendar.YEAR));
            endTime = DateUtils.setMonths(endTime, d.get(Calendar.MONTH));
            endTime = DateUtils.setDays(endTime, d.get(Calendar.DAY_OF_MONTH));
            
            while (initFrecuency.before(endTime)) {
                double diff = ScopixUtilities.diffDateInMin(initFrecuency, endTime);
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
                    if (horaTomaMuestra.after(DateUtils.addMinutes(endTime, -duracionMinutes))) {
                        log.error("tomaMuestra=" + DateFormatUtils.format(horaTomaMuestra, "HH:mm") + " endTime="
                                + DateFormatUtils.format(endTime, "HH:mm") + " duracionMinutes=" + duracionMinutes);
                    }

                    // calcular la hora de ejecucion en el server ya que las horaTomaMuestra corresponde a horas en el store
                    Date horaExecution = ScopixUtilities.calculateDateByStore(horaTomaMuestra, plan.getTimeZoneId(),
                            range.getDayOfWeek());
                    log.debug("horaExecution:" + horaExecution);

                    // la ejecucion debe ser al la hora + el largo del video o muestra
                    Calendar calExecute = Calendar.getInstance();
                    calExecute.setTime(horaExecution);
                    calExecute.add(Calendar.SECOND, range.getDuration() + 10);
                    
                    List<EvidenceExtractionRequest> evidenceRequests = generateEvidenceExtractionRequestFromRange(range,
                            horaTomaMuestra);
                    // creamos Simple Job para cada request
                    log.debug("evidenceRequests [evidenceRequests:" + evidenceRequests.size() + "]");
                    for (EvidenceExtractionRequest evidenceExtractionRequest : evidenceRequests) {
                        JobDetail job = createJob("CutJob_" + evidenceExtractionRequest.getId(),
                                "CutJobGroup_" + plan.getStoreName(), CutJob.class);
                        log.debug("ini create job: " + job.getKey().getName());
                        
                        job.getJobDataMap().put(CutJob.EVIDENCE_EXTRACTION_REQUEST_ID, evidenceExtractionRequest.getId());
                        
                        Trigger trigger = TriggerBuilder
                                .newTrigger()
                                .withIdentity("CutJob_" + +evidenceExtractionRequest.getId(),
                                        "CutJobGroup_" + plan.getStoreName()).startAt(calExecute.getTime()).build();
                        
                        try {
                            addJob(job, trigger);
                        } catch (ScopixException e) {
                            log.error("No es posible crear trigger " + e);
                        }
                        
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
        
    }

    /**
     * Should generalize with {@link SchedulerManager} version
     *
     *
     * @param jobId
     * @param groupId
     * @param aClass
     * @return
     */
    private JobDetail createJob(String jobId, String groupId, Class<? extends Job> aClass) {
        JobDetail job = JobBuilder.newJob(aClass).withIdentity(jobId, groupId).build();
        return job;
    }

    /**
     * Adds a job to the scheduler
     *
     * @param job
     * @param trigger
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    private void addJob(JobDetail job, Trigger trigger) throws ScopixException {
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
    
    private List<EvidenceExtractionRequest> generateEvidenceExtractionRequestFromRange(SituationRequestRange range,
            Date requestTime) throws ScopixException {
        log.info("start [range:" + range + "][requestTime:" + requestTime + "]");
        List<EvidenceExtractionRequest> ret = new ArrayList<EvidenceExtractionRequest>();
        try {
            GetProcessIdCommand getProcessIdCommand = new GetProcessIdCommand();
            EvidenceExtractionRequestDAO extractionRequestDAO = SpringSupport.getInstance().findBeanByClassName(
                    EvidenceExtractionRequestDAO.class);
            GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
            List<SituationRequest> situationRequests = new ArrayList<SituationRequest>();
            SituationRequest sr = getSituationRequestCommanand().execute(range.getSituationRequest().getId());
            situationRequests.add(sr);
            String cameraName = null;
            if (sr.getRandomCamera()) {
                cameraName = getRandomCamera(sr);
            }
            // generamos los VOs asociados
            Calendar d = Calendar.getInstance();
            requestTime = DateUtils.setYears(requestTime, d.get(Calendar.YEAR));
            requestTime = DateUtils.setMonths(requestTime, d.get(Calendar.MONTH));
            requestTime = DateUtils.setDays(requestTime, d.get(Calendar.DATE));
            List<EvidenceRequestVO> evidenceRequestVOs = getPreEvidenceRequestDTOsInjection(situationRequests, requestTime,
                    false, cameraName, range.getDuration(), false);

            // solicitamos processId
            Integer processId = getProcessIdCommand.execute();
            for (EvidenceRequestVO preEvidenceRequestDTO : evidenceRequestVOs) {
                log.debug("EvidenceProvider = " + preEvidenceRequestDTO.getEvidenceProvider().getClass().getSimpleName());
                
                EvidenceExtractionRequest evidenceExtractionRequest = getClassByPreEvidenceRequestDTO(preEvidenceRequestDTO);
                
                if (evidenceExtractionRequest == null) {
                    continue;
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(requestTime);
                
                Integer dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                
                evidenceExtractionRequest.setPriorization(preEvidenceRequestDTO.getSituationMetricDTOs().get(0)
                        .getSituationRequest().getPriorization());
                evidenceExtractionRequest.setProcessId(processId);
                evidenceExtractionRequest.setType(EvidenceRequestType.REAL_RANDOM); // EvidenceRequestType.AUTO_GENERATED
                evidenceExtractionRequest.setExtractionPlan(range.getSituationRequest().getExtractionPlan());
                evidenceExtractionRequest.setEvidenceDate(requestTime); // fecha de evidencia
                evidenceExtractionRequest.setRequestedTime(requestTime); // hora evidencia

                evidenceExtractionRequest.setDayOfWeek(dayOfWeek);
                evidenceExtractionRequest.setEvidenceProvider(preEvidenceRequestDTO.getEvidenceProvider());
                // evidenceExtractionRequest.setCreationTimestamp(fechaActual);
                evidenceExtractionRequest.setCreationTimestamp(new Date());
                
                Integer id = extractionRequestDAO.saveEERS(evidenceExtractionRequest);
                evidenceExtractionRequest = dao.get(id, EvidenceExtractionRequest.class);
                log.debug("evidenceExtractionRequest = " + evidenceExtractionRequest);
                
                List<SituationMetricDTO> situationMetricDTOs = preEvidenceRequestDTO.getSituationMetricDTOs();
                log.debug("situationMetricDTOs = " + situationMetricDTOs.size());
                SituationMetricExtractionRequest situationMetricExtractionRequest;
                for (SituationMetricDTO situationMetricDTO : situationMetricDTOs) {
                    situationMetricExtractionRequest = new SituationMetricExtractionRequest();
                    situationMetricExtractionRequest.setEvidenceExtractionRequest(evidenceExtractionRequest);
                    situationMetricExtractionRequest.setSituationTemplateId(situationMetricDTO.getSituationRequest()
                            .getSituationTemplateId());
                    situationMetricExtractionRequest.setMetricTemplateId(situationMetricDTO.getMetricRequest()
                            .getMetricTemplateId());
                    
                    dao.save(situationMetricExtractionRequest);
                }
                log.debug("add evidenceExtractionRequest to list " + evidenceExtractionRequest);
                ret.add(evidenceExtractionRequest);
                
            }
        } catch (ScopixException e) {
            log.error(e, e);
            throw new ScopixException(e);
        }
        log.info("end [ret:" + ret.size());
        return ret;
    }
    
    public void ciscoPeopleCountingFileReady(String fileName, String xmlDate, String xmlValueIn, String xmlValueOut,
            String storeName, Integer evidenceFileId) {
        
        log.info("start, evidenceFileId: [" + evidenceFileId + "], fileName: [" + fileName + "], xmlDate: [" + xmlDate + "], "
                + "xmlValueIn: [" + xmlValueIn + "], xmlValueOut: [" + xmlValueOut + "], storeName: [" + storeName + "]");

        // recuperamos el pool asociado a la tienda
        ExtractEvidencePoolExecutor threadPool = (ExtractEvidencePoolExecutor) getMapPoolsStore().get(storeName).get(
                POOL_EXTRACT_CISCO_PCOUNTING_FREADY_POOL_EXECUTOR);
        
        CiscoPeopleCountingFileReadyCommand fileReadyCommand = new CiscoPeopleCountingFileReadyCommand();
        fileReadyCommand.setFileName(fileName);
        fileReadyCommand.setXmlDate(xmlDate);
        fileReadyCommand.setXmlValueIn(xmlValueIn);
        fileReadyCommand.setXmlValueOut(xmlValueOut);
        fileReadyCommand.setThreadPool(threadPool);
        fileReadyCommand.setEvidenceFileId(evidenceFileId);
        
        fileReadyCommand.execute();
        log.info("end");
    }
    
    public void injectEventVadaroToEvidenceRequest(VadaroEvent event) throws ScopixException {
        log.debug("start, event = " + event);
        VadaroEvidenceInjectCommand vadaroCommand = new VadaroEvidenceInjectCommand();
        vadaroCommand.execute(event, PROPERTIES_UPLOADJOB_UPLOAD_LOCAL_DIR);
        log.debug("end");
    }
    
    private Integer getDOW(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Integer dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    /**
     * Inject new Request for Event Abandoned
     *
     * @param time date the Event
     * @param cameraName Camera that generated the event
     */
    public void injectEventVadaroAbandnedToEvidenceRequest(Date time, String cameraName) {
        log.info("start [time:" + time + "][cameraName:" + cameraName + "]");
        try {
            String capitalizeName = StringUtils.capitalize(
                    StringUtils.lowerCase(
                            StringUtils.substring(
                                    cameraName, StringUtils.indexOf(cameraName, ".") + 1)));
            capitalizeName = StringUtils.remove(capitalizeName, "_ws");
            
            if (capitalizeName.indexOf("-") > 0) {
                capitalizeName = StringUtils.substring(capitalizeName, 0, capitalizeName.indexOf("-"));
            }
            
            Date newEvidenceDate = DateUtils.addSeconds(time, -10);
            //generamos lo que se necesita para injectar las nuevas solicitudes
            log.debug("camera:" + capitalizeName);
            FindSituationsSensorDateCommand command = new FindSituationsSensorDateCommand();
            List<SituationRequest> listSituation = command.execute(capitalizeName, newEvidenceDate);
            
            List<EvidenceRequestVO> preEvidenceRequestDTOs = getPreEvidenceRequestDTOsInjection(listSituation,
                    newEvidenceDate, false, capitalizeName, null, false);
            
            String storeName = listSituation.get(0).getExtractionPlan().getStoreName();
            
            scheduleEvidenceRequest(preEvidenceRequestDTOs,
                    newEvidenceDate, storeName, EvidenceRequestType.AUTO_GENERATED, null, true);
            
        } catch (ScopixException e) {
            log.error(e, e);
        }
        log.info("end");
    }
    
    public void videoConverterFileReady(String error, String evidenceId, String originalName, String convertedName,
            String waitForConverter) {
        log.info("start evidenceId:[" + evidenceId + "], originalName:[" + originalName + "], "
                + "convertedName:[" + convertedName + "], error:[" + error + "], waitForConverter:[" + waitForConverter + "]");
        if ("S".equalsIgnoreCase(waitForConverter)) {
            // TODO acá haría el finish del fileReady
            try {
                DownloadVideoConverterFileReadyCommand command = new DownloadVideoConverterFileReadyCommand();
                command.execute(error, evidenceId, originalName, convertedName, waitForConverter);
            } catch (ScopixException e) {
                log.error("Error converter [originalName:" + originalName + "][convertedName:" + convertedName + "] " + e, e);
            }
        }
        log.info("end");
    }
    
}
