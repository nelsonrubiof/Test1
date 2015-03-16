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
 * EvaluatorManager.java
 *
 * Created on 27-03-2008, 02:33:29 PM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.EvidenceServicesServer;
import com.scopix.periscope.corporatestructuremanagement.Location;
import com.scopix.periscope.corporatestructuremanagement.RelationEvidenceProviderLocation;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.corporatestructuremanagement.StorePlan;
import com.scopix.periscope.corporatestructuremanagement.commands.AddAreaCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetEvidenceForStoreDaySituationCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetEvidenceForStoreDaySituationRealCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetObservedSituationPendingListCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddAutomaticEvidenceRequestCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddAutomaticMetricCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddAutomaticSituationCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddEvidenceCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddEvidenceEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddMarquisCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddMetricEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddObservedMetricCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddObservedSituationCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddObservedSituationEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddPendingEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddProofCommand;
import com.scopix.periscope.evaluationmanagement.commands.CreateRegionTransferEntryCommand;
import com.scopix.periscope.evaluationmanagement.commands.FindFailedEvidenceTransfersCommand;
import com.scopix.periscope.evaluationmanagement.commands.FindRegionServerByCodeNameCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetAllPendingEvalutionLiveExpiredCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetAreaByAreaTypeStoreCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetCountPendingEvaluationByObservedSituationCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetEvidenceByPathCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetEvidenceCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetEvidenceEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetEvidenceEvaluationDTOListCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetEvidencesAndProofsCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetMetricEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetObservedMetricCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetObservedMetricForADayCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetObservedMetricListCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetObservedSituationCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetObservedSituationEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetObservedSituationForADayCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetPendingEvaluationByObservedSituationCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetPendingEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetProcessFromAutomaticEvidenceAvailableCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetRegionTransferByCriteriaCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetRegionTransferStatsByCriteriaCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetTransmistionStrategyByStoreAndSituationCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetUrlsBeeCommand;
import com.scopix.periscope.evaluationmanagement.commands.RemoveEvidenceCommand;
import com.scopix.periscope.evaluationmanagement.commands.RemoveEvidenceEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.RemoveMetricEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.RemoveObservedMetricCommand;
import com.scopix.periscope.evaluationmanagement.commands.RemoveObservedSituationCommand;
import com.scopix.periscope.evaluationmanagement.commands.RemoveObservedSituationEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.RetryUpdateRegionTransferEntryCommand;
import com.scopix.periscope.evaluationmanagement.commands.TransferToRegionServerFTPCommand;
import com.scopix.periscope.evaluationmanagement.commands.UpdateObservedMetricCommand;
import com.scopix.periscope.evaluationmanagement.digester.ExpiredPendingEvaluationLiveJob;
import com.scopix.periscope.evaluationmanagement.digester.RetryRegionTransferPendingEvaluationsJob;
import com.scopix.periscope.evaluationmanagement.dto.AutomaticEvidenceAvailableDTO;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceAvailableDTO;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceEvaluationDTO;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceProviderSendDTO;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceSendDTO;
import com.scopix.periscope.evaluationmanagement.dto.EvidencesAndProofsDTO;
import com.scopix.periscope.evaluationmanagement.dto.MarquisDTO;
import com.scopix.periscope.evaluationmanagement.dto.MetricSendDTO;
import com.scopix.periscope.evaluationmanagement.dto.ProofDTO;
import com.scopix.periscope.evaluationmanagement.dto.RegionTransferSendDTO;
import com.scopix.periscope.evaluationmanagement.dto.SituationMetricsDTO;
import com.scopix.periscope.evaluationmanagement.dto.SituationSendDTO;
import com.scopix.periscope.evaluationmanagement.evaluators.CommonEvaluationManager;
import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.exception.HttpClientInitializationException;
import com.scopix.periscope.exception.HttpGetException;
import com.scopix.periscope.executors.singlethread.ScopixThreadTimeoutExecutor;
import com.scopix.periscope.executors.threadpool.ScopixPoolExecutor;
import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanManager;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanMetric;
import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.extractionplanmanagement.Situation;
import com.scopix.periscope.extractionplanmanagement.commands.GetEvidenceRequestCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GetMetricListCommand;
import com.scopix.periscope.extractionplanmanagement.commands.UpdateEvidenceRequestCommand;
import com.scopix.periscope.frontend.dto.EvidenceRegionTransferDTO;
import com.scopix.periscope.frontend.dto.EvidenceRegionTransferStatsDTO;
import com.scopix.periscope.http.HttpSupport;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.queuemanager.Prioritization;
import com.scopix.periscope.periscopefoundation.queuemanager.QueueType;
import com.scopix.periscope.periscopefoundation.util.FileUtils;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.periscopefoundation.util.config.SystemConfig;
import com.scopix.periscope.qualitycontrol.RejectedHistory;
import com.scopix.periscope.queuemanagement.OperatorQueue;
import com.scopix.periscope.queuemanagement.commands.GetFirstElementOfQueueCommand;
import com.scopix.periscope.queuemanagement.commands.GetOperatorQueueForAObservedSituation;
import com.scopix.periscope.securitymanagement.CorporateStructureManagerPermissions;
import com.scopix.periscope.securitymanagement.EvaluationManagerPermissions;
import com.scopix.periscope.securitymanagement.SecurityManager;
import com.scopix.periscope.templatemanagement.EvidenceType;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import hidden.org.codehaus.plexus.interpolation.util.StringUtils;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.transaction.annotation.Propagation;

/**
 *
 * @author César Abarza Suazo
 * @version 2.0.0
 */
@SpringBean(rootClass = EvaluationManager.class)
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ScopixException.class})
public class EvaluationManager implements InitializingBean {

    private static final Logger log = Logger.getLogger(EvaluationManager.class);
    private boolean isCloseSituation;
    private SecurityManager securityManager;
    private AddMarquisCommand addMarquisCommand;
    private static final int CORPORATE_LOWES = 6;
    private PropertiesConfiguration configuration;
    //formato para fecha de evidencia del situationSendDTO
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private CorporateStructureManager corporateStructureManager;
    private GetOperatorQueueForAObservedSituation operatorQueueForAObservedSituation;
    private AddPendingEvaluationCommand addPendingEvaluationCommand;
    private GetObservedSituationPendingListCommand observedSituationPendingListCommand;
    private GetMetricListCommand metricListCommand;
    private CommonEvaluationManager commonEvalManager;
    private GetPendingEvaluationByObservedSituationCommand pendingEvaluationByObservedSituationCommand;
    private GetCountPendingEvaluationByObservedSituationCommand countPendingEvaluationByObservedSituationCommand;
    private CreateRegionTransferEntryCommand createRegionTransferEntryCommand;
    private FindFailedEvidenceTransfersCommand findFailedEvidenceTransfersCommand;
    private PendingEvidenceRegionTransfers pendingEvidenceRegionTransfers;
    private RetryUpdateRegionTransferEntryCommand retryUpdateRegionTransferEntryCommand;
    private FindRegionServerByCodeNameCommand findRegionServerByCodeNameCommand;
    /**
     * Pool for transfer file to cache
     */
    private Map<String, ScopixPoolExecutor> mapTransfer;
    private ScopixPoolExecutor retryExecutor;

    public static final String MAX_ROUTES = "httpClient.maxRoutes";
    public static final String MAX_PER_ROUTE = "httpClient.maxPerRoute";

    /**
     * Se elimina cheque de seguridad se asume que este llamado es realizado desde el sistema
     *
     * @param evidenceAvailableDTO dto recibido desde Servicio
     * @throws ScopixException excepcion en caso de error
     */
    public synchronized void acceptNewEvidence(EvidenceAvailableDTO evidenceAvailableDTO) throws ScopixException {
        //, long sessionId
        //getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.ACCEPT_NEW_EVIDENCE_PERMISSION);

        log.info("start evidenceAvailableDTO [path:" + evidenceAvailableDTO.getPath() + "]"
                + "[date:" + evidenceAvailableDTO.getEvidenceDate() + "]"
                + "[erIds:" + evidenceAvailableDTO.getEvidenceRequestIds() + "]"
                + "[serverId:" + evidenceAvailableDTO.getEvidenceServicesServerId() + "]");

        EvidenceRequest evidenceRequest;
        ObservedMetric observedMetric = null;
        ObservedSituation observedSituation;

        //List<ObservedSituation> observedSituationList = new ArrayList<ObservedSituation>();
//        Map<Integer, List<ObservedMetric>> observedMetrics = new HashMap<Integer, List<ObservedMetric>>();
        Map<Integer, ObservedSituation> mapObservedSituation = new HashMap<Integer, ObservedSituation>();
        //COMMANDS

        AddEvidenceCommand addEvidenceCommand = new AddEvidenceCommand();
        GetEvidenceByPathCommand getEvidenceByPathCommand = new GetEvidenceByPathCommand();

        log.debug("[evidenceAvailableDTO:" + evidenceAvailableDTO + "]");
        if (evidenceAvailableDTO != null && evidenceAvailableDTO.getEvidenceRequestIds() != null
                && !evidenceAvailableDTO.getEvidenceRequestIds().isEmpty()) {
            List<Integer> evidenceRequestIds = new ArrayList<Integer>();
            // Validate if the evidence exist, falta la validación por tienda
            log.debug("[evidenceAvailableDTO.getEvidenceRequestIds():" + evidenceAvailableDTO.getEvidenceRequestIds() + "]");
            if (evidenceAvailableDTO.getEvidenceRequestIds() != null) {
                log.debug("[evidenceAvailableDTO.getEvidenceRequestIds().size():"
                        + evidenceAvailableDTO.getEvidenceRequestIds().size() + "]");

                for (Integer erId : evidenceAvailableDTO.getEvidenceRequestIds()) {
                    log.debug("[evidenceAvailableDTO.getPath():" + evidenceAvailableDTO.getPath() + "]");
                    Evidence evidenceExist = getEvidenceByPathCommand.execute(evidenceAvailableDTO.getPath(), erId);
                    if (evidenceExist == null) {
                        log.debug("erId:" + erId + " no tiene evidencia");
                        evidenceRequestIds.add(erId);
                    }
                }
            }
            log.debug("[evidenceRequestIds.isEmpty():" + evidenceRequestIds.isEmpty() + "]");
            if (!evidenceRequestIds.isEmpty()) {
                evidenceAvailableDTO.setEvidenceRequestIds(evidenceRequestIds);
                //Get EvidenceServiceServer
                EvidenceServicesServer evidenceServicesServer = getCorporateStructureManager().
                        getEvidenceServicesServer(evidenceAvailableDTO.getEvidenceServicesServerId()); //, sessionId

                //Create Evidence
                Evidence evidence = new Evidence();
                evidence.setEvidencePath(evidenceAvailableDTO.getPath());
                evidence.setEvidenceServicesServer(evidenceServicesServer);
                evidence.setEvidenceDate(evidenceAvailableDTO.getEvidenceDate());
                addEvidenceCommand.execute(evidence);
                log.debug("new evidence id: " + evidence.getId());
                log.debug("new evidence path: " + evidenceAvailableDTO.getPath());

                String evidencePath = null;
                String separator = (evidence.getEvidenceServicesServer().getEvidencePath().indexOf("/") >= 0) ? "/" : "\\";
                Map<Integer, RegionServer> regionServers = new HashMap<Integer, RegionServer>();
                // for each evidence request id received in the list
                for (Integer evidenceRequestId : evidenceAvailableDTO.getEvidenceRequestIds()) {
                    log.debug("evidenceRequestId from Dto: " + evidenceRequestId);
                    //Get the evidence request
                    evidenceRequest = getEvidenceRequest(evidenceRequestId);
                    log.debug("evidenceRequest Id: " + evidenceRequest);
                    // just checking if evidence request exists
                    if (evidenceRequest != null) {
                        //Update EvidenceRequest
                        evidenceRequest.getEvidences().add(evidence);
                        updateEvidenceRequest(evidenceRequest);
                        evidence.getEvidenceRequests().add(evidenceRequest);
                        //look for existing observed metric and observed situation for this evidence
                        observedSituation = getObservedSituation(evidenceRequest.getMetric().getSituation(),
                                evidenceAvailableDTO.getEvidenceDate()); //, sessionId
                        if (!mapObservedSituation.containsKey(observedSituation.getId())) {
                            mapObservedSituation.put(observedSituation.getId(), observedSituation);
                        }
                        observedMetric = getObservedMetric(evidenceRequest.getMetric(), evidenceAvailableDTO.getEvidenceDate(),
                                observedSituation, evidence); //sessionId

                        //getting Evidence Transmition Strategy
                        SituationTemplate situationTemplate = observedSituation.getSituation().getSituationTemplate();
                        Store store = observedMetric.getMetric().getStore();
                        GetTransmistionStrategyByStoreAndSituationCommand gtsbsasCommand = new GetTransmistionStrategyByStoreAndSituationCommand();
                        EvidenceTransmitionStrategy ets = gtsbsasCommand.execute(store, situationTemplate);
                        if (ets != null) {
                            for (RegionServer regionServer : ets.getRegionServers()) {
                                if (!regionServers.containsKey(regionServer.getId())) {
                                    regionServers.put(regionServer.getId(), regionServer);
                                }
                            }
                        }
                        log.debug("observedMetric Id: [" + observedMetric.getId() + "], observedSituation Id: [" + observedSituation.getId() + "]");

                        //No agregar datos repetidos
                        Collections.sort(observedSituation.getObservedMetrics());
                        if (Collections.binarySearch(observedSituation.getObservedMetrics(), observedMetric) < 0) {
                            observedSituation.getObservedMetrics().add(observedMetric);
                        }

                        log.debug("evidencePath (solo es !=null en caso de videos): [" + evidencePath + "]");
                        if (evidencePath == null && "mp4".equalsIgnoreCase(FilenameUtils.getExtension(evidence.getEvidencePath()))) {
                            log.debug("se genera path de evidencia de video, evidence.getEvidencePath(): [" + evidence.getEvidencePath() + "]");
                            evidencePath = generateEvidencePath(evidence, observedMetric, separator);
                        }
                    }
                }
                creationPendingEvaluation(new ArrayList<ObservedSituation>(mapObservedSituation.values())); //sessionId
                //enviamos la evidencia por ftp si es necesario
                //Esto no funciona (por arreglar)
                //transferEvidenceToAlternativeSFTPServer(evidence);

                //transmitiendo  a regiones
                if (!regionServers.isEmpty()) {
                    for (RegionServer regionServer : regionServers.values()) {
                        if (regionServer.isActive()) {
                            transferEvidenceToSituationFTPServer(evidence, observedMetric, regionServer);
                        }
                    }
                }

                if (evidencePath != null) {
                    //invoca generacion de sprites para el video especificado
                    generateVideoSprites(evidencePath);
                }
            }
        } else {
            throw new ScopixException("PENDING");
        }
        log.info("end");
    }

    /**
     * Invoca generacion de sprites para el video especificado
     *
     * @param videoPath path del video
     */
    private void generateVideoSprites(String videoPath) {
        log.info("start, videoPath: [" + videoPath + "]");
        try {
            String opImagesServicesURL = getConfiguration().getString("operator.img.services.url");
            log.debug("opImagesServicesURL: [" + opImagesServicesURL + "]");

            WebClient serviceClient = WebClient.create(opImagesServicesURL);
            serviceClient.path("/generateVideoSprites").accept(MediaType.APPLICATION_JSON_TYPE).type(MediaType.APPLICATION_JSON_TYPE);
            Response response = serviceClient.post(videoPath);
            log.debug("response.getStatus(): [" + response.getStatus() + "]");

        } catch (Exception e) {
            log.warn("Error durante para la generacion de sprites del video: [" + videoPath + "]. Mensaje: [" + e.getMessage() + "]", e);
        }
        log.info("end");
    }

    //, long sessionId sacamos chequeo de seguridad se asume que llamada es de sistema
    private ObservedSituation getObservedSituation(Situation situation, Date evidenceDate) throws
            ScopixException {
        log.info("start");
        AddObservedSituationCommand addObservedSituationCommand = new AddObservedSituationCommand();
        ObservedSituation observedSituation = getObservedSituationForADay(situation.getId(), evidenceDate); //sessionId
        log.debug("situation_id:" + situation.getId());
        if (observedSituation == null) {
            //si no existe ObservedSituation correspondiente se debe crear
            observedSituation = new ObservedSituation();
            observedSituation.setObservedSituationDate(evidenceDate);
            observedSituation.setSituation(situation);
            //se agrega para mantener dato
            observedSituation.setEvidenceDate(evidenceDate);
            //agregegamos el os a los datos
            addObservedSituationCommand.execute(observedSituation);
            log.debug("created new observedSituation: " + observedSituation.getId());
        }
        log.info("end");
        return observedSituation;
    }

    // se saca chequeo de seguridad se asume que la llamada es interna de sistema, long sessionId
    private ObservedMetric getObservedMetric(Metric metric, Date evidenceDate,
            ObservedSituation observedSituation, Evidence evidence) throws ScopixException {
        log.info("start");
        AddObservedMetricCommand addObservedMetricCommand = new AddObservedMetricCommand();
        ObservedMetric observedMetric = getObservedMetricForADay(metric.getId(), evidenceDate); //sessionId
        // if observed metric doesn't exists, create it
        if (observedMetric == null) {
            //si no existe ObservedMetric al que le sirva la evidencia, crearlo. recuerda que pueden ser
            //varios si la evidencia sirve a varias metricas
            observedMetric = new ObservedMetric();
            observedMetric.setMetric(metric);
            observedMetric.setObservedMetricDate(evidenceDate);
            observedMetric.setObservedSituation(observedSituation);
            observedMetric.getEvidences().add(evidence);
            //se agrega para mantener dato
            observedMetric.setEvidenceDate(evidenceDate);

            addObservedMetricCommand.execute(observedMetric);
            log.debug("created new observedMetric: " + observedMetric.getId());
        } else {
            // si existe observed metric, agregarle la evidencia
            observedMetric.addEvidence(evidence);
        }
        log.info("end");
        return observedMetric;
    }

    // se elimina chequeo de seguridad se asume que llamada es de sistema long sessionId
    private void creationPendingEvaluation(List<ObservedSituation> observedSituationList) throws ScopixException {
        log.info("start");
        List<PendingEvaluation> pendingEvaluations = new ArrayList<PendingEvaluation>();
        //Crear el PendingEvaluation para cada par Situation-Evidence
        for (ObservedSituation os : observedSituationList) {
            //antes de segui se debe verificar si ya existe pendingevaluation para este os
            log.debug("observedSituation: [" + os + "]");
            Integer countPendingEvaluation = getCountPendingEvaluationByObservedSituationCommand().execute(os.getId());
            log.debug("countPendingEvaluation: [" + countPendingEvaluation + "]");
            //PendingEvaluation pendingEvaluationAux = getPendingEvaluationByObservedSituationCommand().execute(os.getId());
//            log.debug("[pendingEvaluationAux:" + pendingEvaluationAux + "]");
            //solo si no existe pe seguimos

//            if (pendingEvaluationAux == null) {
            if (countPendingEvaluation == 0) {
                ExtractionPlanCustomizing epc = getEpcByOs(os);
                log.debug("observed_situation_id = " + os.getId());
                log.debug("situation_template_id = " + os.getSituation().getSituationTemplate().getId());
                //Recorrer cada observed metric
                int count = os.getObservedMetrics().size();
                //para los casos de un epc con random de camaras se debe crear siempre el pending evaluation
                boolean create = isCreatePendingEvaluation(os);

                Metric m = new Metric();
                m.setSituation(os.getSituation());
                List<Metric> metrics = getMetricListCommand().execute(m);

                log.debug("count observedMetrics: [" + count + "], "
                        + "create pendingEvaluation: [" + create + "], metrics.size() = " + metrics.size());

                boolean createByMetric = (count == metrics.size());
                log.debug("createByMetric (valor inicial): [" + createByMetric + "], "
                        + "epc.isRandomCamera(): [" + epc.isRandomCamera() + "]");

                if (epc != null && epc.isRandomCamera()) {
                    log.debug("ingresa a createByMetric = true;");
                    createByMetric = true;
                }
                log.debug("create: [" + create + "], createByMetric (valor final): [" + createByMetric + "]");

                if (create && createByMetric) {
                    log.debug("se creara nueva pending evaluation");
                    PendingEvaluation pendingEvaluation = createNewPendingEvaluation(os, epc, CreationType.AUTOMATIC);

                    if (!getCommonEvalManager().checkExpiredPendingEvaluation(pendingEvaluation, null)) {
                        pendingEvaluations.add(pendingEvaluation);
                    }
                }
            }
        }
        //Encolar la lista de PendingEvaluationEvaluationQueue

        enqueuePendingEvaluationForEvaluation(pendingEvaluations); //sessionId
        log.info("end");
    }

    private PendingEvaluation createNewPendingEvaluation(ObservedSituation os, ExtractionPlanCustomizing epc,
            CreationType type) throws ScopixException {

        log.info("start, os.id: [" + os.getId() + "], type: [" + type.getName() + "]");
        PendingEvaluation pendingEvaluation = new PendingEvaluation();
        pendingEvaluation.setObservedSituation(os);
        pendingEvaluation.setCreationType(type);
        pendingEvaluation.setCreationDate(new Date());
        pendingEvaluation.setEvaluationState(EvaluationState.ENQUEUED);
        // depending on the evaluator for the evidence, enqueue on Operator or Automatic queue
        if (os.getSituation().getSituationTemplate().getEvidenceSpringBeanEvaluatorName() == null) {
            log.debug("OPERATOR");
            pendingEvaluation.setEvaluationQueue(EvaluationQueue.OPERATOR);
        } else {
            log.debug("AUTOMATIC");
            pendingEvaluation.setEvaluationQueue(EvaluationQueue.AUTOMATIC);
        }
        OperatorQueue queue = getOperatorQueueForAObservedSituation().execute(os);
        log.debug("operatorQueue: [" + queue + "]");
        if (queue != null) {
            pendingEvaluation.setOperatorQueue(queue);
        }

        // Si el epc asociado tiene priorizacion, darle esa posicion al pending evaluation
        // valores de priorizacion entre 1 y 10.
        // Al encolar se asigna la priorizacion dada aqui. Si no lleva priorizacion, se le asigna el ID como 
        // priorización (clase del foundation)
        if (epc != null && epc.getPriorization() != null) {
            pendingEvaluation.setPriority(epc.getPriorization());
        }

        // Check if its live and override priority.
        if (os.getSituation().getSituationTemplate().isLive()) {
            pendingEvaluation.setPriority(0);
        }

        getAddPendingEvaluationCommand().execute(pendingEvaluation);
        log.info("end, pendingEvaluation: [" + pendingEvaluation + "]");
        return pendingEvaluation;
    }

    /**
     * retorna el EPC asociado a un Objecto os activo
     *
     * @param os observedsituation actvo
     * @return ExtractionPlanCustomizing asociado a observed situation
     */
    public ExtractionPlanCustomizing getEpcByOs(ObservedSituation os) {
        log.info("start");
        ExtractionPlanCustomizing epc = null;
        for (ObservedMetric om : os.getObservedMetrics()) {
            ExtractionPlanMetric epm = om.getMetric().getExtractionPlanMetric();
            if (epm != null) {
                ExtractionPlanCustomizing epcAux = epm.getExtractionPlanCustomizing();
                if (epcAux != null) {
                    epc = epcAux;
                    break;
                }
            }
        }
        log.info("end [epc:" + epc + "]");
        return epc;
    }

    private boolean isCreatePendingEvaluation(ObservedSituation os) {
        boolean create = true;
        int count = 0;
        for (ObservedMetric om : os.getObservedMetrics()) {
            count++;
            log.debug("observed_metric_id: [" + om.getId() + "]");
            log.debug("metric_template_id: [" + om.getMetric().getMetricTemplate().getId() + "]");
            log.debug("count: [" + count + "]");
            ExtractionPlanMetric epm = om.getMetric().getExtractionPlanMetric();
            log.debug("extractionPlanMetric: [" + epm + "]");

            if (epm != null) {
                ExtractionPlanCustomizing epc = epm.getExtractionPlanCustomizing();
                //validar que tenga todas las evidencias correspondientes considerar los 2 casos
                log.debug("extractionPlanCustomizing: [" + epc + "]");
                if (epc != null) {
                    log.debug("om.getEvidences(): [" + om.getEvidences() + "]");
                    if (om.getEvidences() != null && !om.getEvidences().isEmpty()) {
                        log.debug("om.getEvidences().size(): ["
                                + om.getEvidences().size() + "], epc.isRandomCamera(): [" + epc.isRandomCamera() + "]");
                        if (!epc.isRandomCamera()) {
                            //si no es random de camaras preguntamos si las evidencias coinciden con los provider si no es asi
                            //no creamos pending evaluation para el om
                            log.debug("om.getEvidences().size(): [" + om.getEvidences().size() + "], "
                                    + "epm.getEvidenceProviders().size(): [" + epm.getEvidenceProviders().size() + "]");
                            if (om.getEvidences().size() < epm.getEvidenceProviders().size()) {
                                log.debug("create = false");
                                create = false;
                                break;
                            }
                        }
                    } else {
                        log.debug("create = false");
                        // no se debe crear ya que se deben verificar todas las evidencias recibidas
                        create = false;
                        break;
                    }
                } else {
                    log.debug("epc es null");
                    Calendar cal = Calendar.getInstance();
                    for (Metric m : os.getSituation().getMetrics()) {
                        int erCount = 0;
                        for (EvidenceRequest er : m.getEvidenceRequests()) {
                            cal.setTime(os.getObservedSituationDate());
                            int day = cal.get(Calendar.DAY_OF_WEEK);
                            if (er.getDay().equals(day)) {
                                erCount++;
                            }
                        }
                        if (erCount > om.getEvidences().size()) {
                            create = false;
                            break;
                        }
                    }
                }
            }
            log.debug("[createPendingEvaluation: [" + create + "][om.id:" + om.getId() + "]");
            if (!create) {
                break;
            }
        }
        log.info("end, create: [" + create + "], os_id: [" + os.getId() + "]");
        return create;
    }

    /**
     * Recibe una evidencia automatica nueva, recorre los extractionPlanCustomizing asociados al store para validar si
     * EvidenceProvider existe en el extraction plan crea una situacion, multiples metricas y evidenceRequest para todos los
     * ExtractionPlanMetric asociados al ExtractionPlanCustomizing
     *
     * Se elimina chequeo de seguridad se asume que corresponde a una llamada interna del sistema
     *
     * al finalizar llama acceptNewEvidence como si fuera una evidencia normal
     *
     * @param evidenceAvailable dto recibido desde servicio
     * @param sessionId session de usuario
     * @throws ScopixException Excepcion local
     *
     *
     */
    //, long sessionId
    public synchronized void acceptNewAutomaticEvidence(AutomaticEvidenceAvailableDTO evidenceAvailable) throws
            ScopixException {
        log.info("start");
        //getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.ACCEPT_NEW_EVIDENCE_PERMISSION);

        Situation situation;
        Metric metric;
        EvidenceRequest evidenceRequest;

        List<Integer> evidenceRequestIds = null;
        //MANAGERS
        log.debug("[evidenceProviderId:" + evidenceAvailable.getDeviceId() + "]"
                + "[evidenceExtractionServicesServerId:" + evidenceAvailable.getEvidenceExtractionServicesServerId() + "]");
        //recuperamos el evidence provider desde base de datos
        //se elimina chequeo de seguridad
        EvidenceProvider evidenceProvider = getCorporateStructureManager().getEvidenceProvider(evidenceAvailable.getDeviceId());

        ExtractionPlanManager wizardManager = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class);
        log.debug("evidenceAvailable.deviceId = " + evidenceAvailable.getDeviceId());
        if (evidenceAvailable != null && evidenceAvailable.getDeviceId() != null) {
            evidenceRequestIds = new ArrayList<Integer>();

            //Obtain the evidenceServicesServer
            //se elimina chequeo de seguridad
            EvidenceServicesServer ess = getCorporateStructureManager().getEvidenceServicesServer(
                    evidenceAvailable.getEvidenceServicesServerId());
            log.debug("ess = " + ess);

            //se obtienen todos los epc para la situation store y/o que tengan random de camaras
            List<ExtractionPlanCustomizing> extractionPlanCustomizings = wizardManager.getAutomaticExtractionPlanCustomizings(
                    evidenceProvider.getStore(),
                    evidenceAvailable.getDeviceId());

            for (ExtractionPlanCustomizing epc : extractionPlanCustomizings) {
                log.debug("epc.id = " + epc);
                //si existe la situacion metrica y el epc esta activo se puede continuar
                boolean continueOK = validateSituationMetricList(evidenceAvailable, epc);
                if (continueOK) {
                    boolean perteneceProviderTOEPC = perteneceProviderToEPC(evidenceAvailable, epc);
                    if (!perteneceProviderTOEPC) {
                        //si el provider no pertenece al epc seguimos con el siguiente epc
                        log.debug("Provider: " + evidenceAvailable.getDeviceId() + " no pertenece a epc:" + epc);
                        continue;
                    }
                    log.debug("Provider: " + evidenceAvailable.getDeviceId());
                    //creamos la situacion si es necesario esto se ejecutaba en el ciclo despues de verificar si el provider
                    //pertenece o no al epc
                    situation = createSituationIfNotExist(epc, evidenceAvailable);

                    //se crea un EvidenceProvider para poder realizar una busqueda con Collections.binarySearch
                    // y no tener que recorrer la lista epc.getEvidenceProviders()
                    EvidenceProvider ep = new EvidenceProvider();
                    ep.setId(evidenceAvailable.getDeviceId());

                    //la creacion de Metric se realiza recorriendo epc.getExtractionPlanMetrics() del actual
                    //y verificando si el ep que llego en el evidence corresponde al epm actual
                    for (ExtractionPlanMetric epm : epc.getExtractionPlanMetrics()) {
                        Collections.sort(epm.getEvidenceProviders());
                        int index = Collections.binarySearch(epm.getEvidenceProviders(), ep);
                        if (index >= 0) {
                            //evidenceProvider,
                            metric = createMetricIfNotExist(epc, epm, evidenceAvailable, situation);
                            for (EvidenceProvider epToEpm : epm.getEvidenceProviders()) {
                                //para el caso que sea epc random Camera se debe crear solo el evidence Request para el 
                                // EvidenceProvider que viene llegando
                                boolean createEvidenceRequest = false;
                                if (epc.isRandomCamera()) {
                                    createEvidenceRequest = epToEpm.getId().equals(ep.getId());
                                } else {
                                    createEvidenceRequest = true;
                                }
                                if (createEvidenceRequest) {
                                    //creamos todos los evidenceRequest para los provider
                                    evidenceRequest = createEvidencRequestIfNotExist(evidenceAvailable, epToEpm, metric, epm);
                                    //si el ep de el epm es igual al de la evidencia
                                    if (!evidenceRequestIds.contains(evidenceRequest.getId())
                                            && epToEpm.getId().equals(evidenceProvider.getId())) {
                                        evidenceRequestIds.add(evidenceRequest.getId());
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (!evidenceRequestIds.isEmpty()) {
                log.debug("evidenceRequestIds = " + evidenceRequestIds);
                EvidenceAvailableDTO evidenceAvailableDTO = new EvidenceAvailableDTO();
                evidenceAvailableDTO.setEvidenceDate(evidenceAvailable.getEvidenceDate());
                evidenceAvailableDTO.setEvidenceServicesServerId(evidenceAvailable.getEvidenceServicesServerId());
                evidenceAvailableDTO.setPath(evidenceAvailable.getPath());
                evidenceAvailableDTO.setEvidenceRequestIds(evidenceRequestIds);
                acceptNewEvidence(evidenceAvailableDTO);
            }
        }
        if (evidenceRequestIds != null) {
            log.debug("evidenceRequestIds = " + evidenceRequestIds.size());
        }
        log.info("end");
    }

    /**
     * Valida que el provider que viene llegando pertenesca al epc
     *
     */
    private boolean perteneceProviderToEPC(AutomaticEvidenceAvailableDTO evidenceAvailable, ExtractionPlanCustomizing epc) {
        Integer providerID = evidenceAvailable.getDeviceId();
        log.info("start [provider:" + providerID + "]");
        boolean ret = false;
        log.debug("Metricas:" + epc.getExtractionPlanMetrics().size());
        for (ExtractionPlanMetric planMetric : epc.getExtractionPlanMetrics()) {
            for (EvidenceProvider ep : planMetric.getEvidenceProviders()) {
                log.debug("epId " + ep.getId() + " providerID " + providerID);
                if (ep.getId().equals(providerID)) {
                    ret = true;
                    break;
                }
            }
            if (ret) {
                //si el provider pertenece al epc salimos
                break;
            }
        }
        log.info("end [perteneceProviderToEPC:" + ret + "]");
        return ret;
    }

    /**
     * crea un EvidenceRequest si este no esta almacenado, si existe retorna el almacenado
     */
    private EvidenceRequest createEvidencRequestIfNotExist(AutomaticEvidenceAvailableDTO evidenceAvailable,
            EvidenceProvider evidenceProvider, Metric metric, ExtractionPlanMetric epm) throws ScopixException {
        //Create EvidenceRequest
        log.info("start");
        Calendar cal = Calendar.getInstance();
        cal.setTime(evidenceAvailable.getEvidenceDate());
        EvidenceRequest evidenceRequest = new EvidenceRequest();
        evidenceRequest.setDay(cal.get(Calendar.DAY_OF_WEEK));
        evidenceRequest.setEvidenceProvider(evidenceProvider);
        evidenceRequest.setEvidenceTime(evidenceAvailable.getEvidenceDate());
        evidenceRequest.setMetric(metric);
        evidenceRequest.setType(epm.getMetricTemplate().getEvidenceTypeElement());
        evidenceRequest.setEvidenceRequestType(EvidenceRequestType.AUTO_GENERATED);
        evidenceRequest = this.addAutomaticEvidenceRequest(evidenceRequest);
        log.debug("end [evidenceRequest:" + evidenceRequest + "]");
        return evidenceRequest;
    }

    /**
     * Crea una metrica si no existe almacenada, si esta existe retorna la que esta almacenada
     */
    private Metric createMetricIfNotExist(ExtractionPlanCustomizing epc, ExtractionPlanMetric epm,
            AutomaticEvidenceAvailableDTO evidenceAvailable, Situation situation) throws ScopixException {
        log.info("start");
        //Create Metric
        Metric metric = new Metric();
        metric.setExtractionPlanMetric(epm);
        //recuperamos el Area del epc especifico
        GetAreaByAreaTypeStoreCommand command = new GetAreaByAreaTypeStoreCommand();
        Area area = command.execute(epc.getAreaType().getId(), epc.getStore().getId());
        //si no existe el area la creamos para no perder evidencias 
        if (area == null) {
            log.debug("Agregamos nueva area ");
            area = createNewArea(epc.getAreaType(), epc.getStore());
        }
        StringBuilder description = new StringBuilder();
        description.append(epm.getMetricTemplate().getName()).append(" ");
        description.append(DateFormatUtils.format(evidenceAvailable.getEvidenceDate(), "HH:mm:ss")).append(" ");
        description.append(area.getDescription());
        metric.setArea(area);
        metric.setDescription(description.toString());
        metric.setMetricOrder(epm.getEvaluationOrder());
        metric.setMetricTemplate(epm.getMetricTemplate());
        metric.setMetricVariableName(epm.getMetricVariableName());
        metric.setSituation(situation);
        metric.setStore(epc.getStore());
        metric = this.addAutomaticMetric(metric, evidenceAvailable.getDeviceId());
        log.info("end [metric:" + metric.getId() + "]");
        return metric;
    }

    /**
     * crea una situacion si no existe, si esta existe retorna la que se encuentra almacenada
     */
    private Situation createSituationIfNotExist(ExtractionPlanCustomizing epc, AutomaticEvidenceAvailableDTO evidenceAvailable)
            throws ScopixException {
        //recuperamos el Process Asociado al EES y process recibo desde ES
        GetProcessFromAutomaticEvidenceAvailableCommand command = new GetProcessFromAutomaticEvidenceAvailableCommand();
        Process process = command.execute(evidenceAvailable);
        //Create Situation si es necesario
        Situation situation = new Situation();
        situation.setDescription(epc.getSituationTemplate().getName() + " "
                + DateFormatUtils.format(evidenceAvailable.getEvidenceDate(), "HH:mm:ss"));
        situation.setSituationTemplate(epc.getSituationTemplate());
        situation.setProcessId(process.getId()); //evidenceAvailable.getProcessId()
        // de aqui en adelante continuamos normal
        situation = this.addAutomaticSituation(situation);
        log.debug("situation = " + situation.getId());
        return situation;
    }

    /**
     * recorre el map de SituationMetricList y si encuentra alguan coincidencia entre los datos y el ExtractionPlanCustomizing
     * retorna verdadero
     */
    private boolean validateSituationMetricList(AutomaticEvidenceAvailableDTO evidenceAvailable, ExtractionPlanCustomizing epc) {
        boolean continueOK = false;
//        log.debug("evidenceAvailable.getSituationMetricList() = " + evidenceAvailable.getSituationMetricList());
        log.debug("evidenceAvailable.getSituationMetricList() = " + evidenceAvailable.getSituationMetrics());
        log.debug("epc.isActive = " + epc.isActive());
        for (SituationMetricsDTO stDTO : evidenceAvailable.getSituationMetrics()) {
            log.debug("situationTemplateId = " + stDTO.getSituationId());
            for (Integer metricId : stDTO.getMetricIds()) {
                log.debug("metricId = " + metricId);
                for (ExtractionPlanMetric epm : epc.getExtractionPlanMetrics()) {
                    if (epc.getSituationTemplate().getId().equals(stDTO.getSituationId())
                            && epm.getMetricTemplate() != null
                            && epm.getMetricTemplate().getId().equals(metricId)
                            && epc.isActive() != null) {
                        continueOK = true;
                        break;
                    }
                }
                if (continueOK) {
                    break;
                }
            }
            if (continueOK) {
                break;
            }
        }
//        for (Integer situationTemplateId : evidenceAvailable.getSituationMetrics().keySet()) {
//            log.debug("situationTemplateId = " + situationTemplateId);
//            List<Integer> metricIds = evidenceAvailable.getSituationMetrics().get(situationTemplateId);
//            for (Integer metricId : metricIds) {
//                log.debug("metricId = " + metricId);
//                for (ExtractionPlanMetric epm : epc.getExtractionPlanMetrics()) {
//                    if (epc.getSituationTemplate().getId().equals(situationTemplateId)
//                            && epm.getMetricTemplate() != null
//                            && epm.getMetricTemplate().getId().equals(metricId)
//                            && epc.isActive() != null) {
//                        continueOK = true;
//                        break;
//                    }
//                }
//                if (continueOK) {
//                    break;
//                }
//            }
//            if (continueOK) {
//                break;
//            }
//        }

//        for (Map<String, Integer> data : evidenceAvailable.getSituationMetricList()) {
//            log.debug("data = " + data);
//            Integer stId = data.get("situation");
//            log.debug("stId = " + stId);
//            Integer mtId = data.get("metric");
//            log.debug("mtId = " + mtId);
//            log.debug("epc.isActive = " + epc.isActive());
//            //por cada ExtractionMetricTemplate del ExtractionPlanCustomizing
//            for (ExtractionPlanMetric epm : epc.getExtractionPlanMetrics()) {
//                if (epc.getSituationTemplate().getId().equals(stId)
//                        && epm.getMetricTemplate() != null
//                        && epm.getMetricTemplate().getId().equals(mtId)
//                        && epc.isActive() != null) {
//                    continueOK = true;
//                    break;
//                }
//            }
//        }
        log.debug("continue = " + continueOK);
        return continueOK;
    }

    /**
     * Obtain all extractionPlanCustomizing with tath evidence provider for all Stores of evidenceServicesServer
     */
//    private List<ExtractionPlanCustomizing> getListExtractionPlanCustomizingByStore(EvidenceServicesServer ess,
//            ExtractionPlanManager wizardManager, AutomaticEvidenceAvailableDTO evidenceAvailable) throws ScopixException {
//
//        List<ExtractionPlanCustomizing> extractionPlanCustomizings = new ArrayList<ExtractionPlanCustomizing>();
//        log.debug("stores = " + ess.getStores().size());
//        for (Store store : ess.getStores()) {
//            extractionPlanCustomizings.addAll(wizardManager.getAutomaticExtractionPlanCustomizings(store,
//                    evidenceAvailable.getDeviceId()));
//        }
//        log.debug("epcs.size = " + extractionPlanCustomizings.size());
//        return extractionPlanCustomizings;
//    }
//    private void transferEvidenceToAlternativeSFTPServer(Evidence evidence) throws ScopixException {
//        if (evidence != null && !evidence.getSentToAlternativeSFTP()) {
//            TransferToAlternativeSFTPCommand command = new TransferToAlternativeSFTPCommand();
//            command.execute(evidence);
//        }
//    }
    public void transferEvidenceToSituationFTPServer(Evidence evidence, ObservedMetric observedMetric,
            RegionServer regionServer) throws ScopixException {
        log.info("start [evidence:" + evidence.getEvidencePath() + "]");
        Integer retrive = getConfiguration().getInt("TRANSFERSFTP.RETRIVE", 3);
        Integer timeoutOrig = getConfiguration().getInt("TRANSFERSFTP.CONN.TIMEOUT", 0);

        //One pool per region server
        ScopixPoolExecutor poolRegion = getMapTransfer().get(regionServer.getCodeName());
        if (poolRegion == null) {
            log.debug("creating pool for [regionServer:" + regionServer.getCodeName() + "]");
            LinkedBlockingQueue<Runnable> evidenceTransferQueue = new LinkedBlockingQueue<Runnable>();
            poolRegion = new ScopixPoolExecutor(5, evidenceTransferQueue, regionServer.getCodeName());
            getMapTransfer().put(regionServer.getCodeName(), poolRegion);
        }
        EvidenceRegionTransfer ert = getCreateRegionTransferEntryCommand().execute(evidence, regionServer);
        Thread t = new Thread(new ScopixThreadTimeoutExecutor(timeoutOrig, TimeUnit.SECONDS,
                new TransferToRegionServerFTPCommand(evidence, observedMetric, regionServer, ert, retrive)));
        getPendingEvidenceRegionTransfers().addTransfer(ert.getId());
        poolRegion.runTask(t);
        log.info("end [evidence:" + evidence.getEvidencePath() + "]");
    }

    public void retryTransferEvidenceToSituationFTPServer(Integer ertId, String regionServerName) throws ScopixException {
        log.info("start");
        Integer retrive = getConfiguration().getInt("TRANSFERSFTP.RETRIVE", 3);
        Integer timeoutOrig = getConfiguration().getInt("TRANSFERSFTP.CONN.TIMEOUT", 0);

        RegionServer regionServer = getRegionServerByCodeName(regionServerName);
        if (regionServer != null && regionServer.isActive()) {
            EvidenceRegionTransfer ert = retryFailedRegionTransfer(ertId);
            if (ert != null) {
                Evidence evidence = ert.getEvidence();
                log.info("[evidence:" + evidence.getEvidencePath() + "]");
                ObservedMetric observedMetric = evidence.getObservedMetrics().get(0);
                ScopixPoolExecutor poolRegion = getMapTransfer().get(regionServer.getCodeName());
                if (poolRegion == null) {
                    log.debug("creating pool for [regionServer:" + regionServer.getCodeName() + "]");
                    LinkedBlockingQueue<Runnable> evidenceTransferQueue = new LinkedBlockingQueue<Runnable>();
                    poolRegion = new ScopixPoolExecutor(5, evidenceTransferQueue, regionServer.getCodeName());
                    getMapTransfer().put(regionServer.getCodeName(), poolRegion);
                }
                Thread t = new Thread(new ScopixThreadTimeoutExecutor(timeoutOrig, TimeUnit.SECONDS, new TransferToRegionServerFTPCommand(evidence, observedMetric, regionServer, ert, retrive)));
                getPendingEvidenceRegionTransfers().addTransfer(ert.getId());
                poolRegion.runTask(t);
                log.info("retry [evidence:" + evidence.getEvidencePath() + "] created");
            } else {
                log.info("retry [evidence region transfer not create, completed or dequeue");
            }
        }
        log.info("end");
    }

    /**
     * This method validate if exist a situation with the processId then return that, else create a new situation
     *
     * @param situation
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    private Situation addAutomaticSituation(Situation situation) throws ScopixException {
        AddAutomaticSituationCommand addAutomaticSituationCommand = new AddAutomaticSituationCommand();
        situation = addAutomaticSituationCommand.execute(situation);
        return situation;
    }

    /**
     * If is created a motric for the procssId return that, else create new metric
     *
     * @param metric
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    private Metric addAutomaticMetric(Metric metric, Integer evidenceProviderId) throws ScopixException {
        AddAutomaticMetricCommand addAutomaticMetricCommand = new AddAutomaticMetricCommand();
        metric = addAutomaticMetricCommand.execute(metric, evidenceProviderId);
        return metric;
    }

    /**
     * Create a new Evidence Request
     *
     * @param er
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    private EvidenceRequest addAutomaticEvidenceRequest(EvidenceRequest er) throws ScopixException {
        AddAutomaticEvidenceRequestCommand addAutomaticEvidenceRequestCommand = new AddAutomaticEvidenceRequestCommand();
        er = addAutomaticEvidenceRequestCommand.execute(er);
        return er;
    }

    /**
     * Recepción de Evidencia desde Operador Web o Gui
     *
     * @param evidenceEvaluationDTOs dtos enviados por servicios de evaluacion
     * @param sessionId session de usuario
     * @throws ScopixException excepcion en caso de error
     */
    public void acceptEvidenceEvaluation(List<EvidenceEvaluationDTO> evidenceEvaluationDTOs, long sessionId) throws
            ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.ACCEPT_EVIDENCE_EVALUATION_PERMISSION);
        List<ObservedMetric> observedMetrics = new ArrayList<ObservedMetric>();
        EvidenceEvaluation evidenceEvaluation;
        Proof proof;
        Marquis marquis;
        PendingEvaluation pendingEvaluation;
        //COMMANDS
        GetPendingEvaluationCommand getPendingEvaluationCommand = new GetPendingEvaluationCommand();
        GetEvidenceCommand getEvidenceCommand = new GetEvidenceCommand();
        GetObservedMetricForADayCommand getObservedMetricForADayCommand = new GetObservedMetricForADayCommand();
        AddEvidenceEvaluationCommand addEvidenceEvaluationCommand = new AddEvidenceEvaluationCommand();
        AddProofCommand addProofCommand = new AddProofCommand();

        String userName = SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.SecurityManager.class).
                getUserName(sessionId);

        if (evidenceEvaluationDTOs != null && !evidenceEvaluationDTOs.isEmpty()) {
            Integer pendingEvaluationId = evidenceEvaluationDTOs.get(0).getPendingEvaluationId();
            //Obtiene pending evaluation
            log.debug("obteniendo pending evaluation, id: " + pendingEvaluationId + ", user: " + userName);
            pendingEvaluation = getPendingEvaluationCommand.execute(pendingEvaluationId);

            for (EvidenceEvaluationDTO evidenceEvaluationDTO : evidenceEvaluationDTOs) {
                evidenceEvaluation = new EvidenceEvaluation();
                Integer eeId = evidenceEvaluationDTO.getId();
                if (eeId != null) {
                    //recuperar evaluacion para ser actualizada
                    evidenceEvaluation = getEvidenceEvaluation(eeId);
                }

                //Create EvidenceEvaluation
                List<Evidence> evidences = new ArrayList<Evidence>();
                for (Integer evidenceId : evidenceEvaluationDTO.getEvidenceIds()) {
                    Evidence evidence = getEvidenceCommand.execute(evidenceId);
                    evidences.add(evidence);
                }

                ObservedMetric observedMetric = getObservedMetricForADayCommand.execute(evidenceEvaluationDTO.getMetricId(),
                        evidences.get(0).getEvidenceDate());
                Collections.sort(observedMetrics);
                if (Collections.binarySearch(observedMetrics, observedMetric) < 0) {
                    observedMetric.getMetric().getEvidenceRequests().isEmpty();
                    observedMetric.getEvidenceEvaluations().isEmpty();
                    observedMetrics.add(observedMetric);
                }
                evidenceEvaluation.setEvidences(evidences);
                evidenceEvaluation.setObservedMetric(observedMetric);
                if (evidenceEvaluationDTO.getCantDoReason() != null && !evidenceEvaluationDTO.getCantDoReason().isEmpty()) {
                    evidenceEvaluation.setEvidenceResult(null);
                } else {
                    evidenceEvaluation.setEvidenceResult(evidenceEvaluationDTO.getEvidenceEvaluationResult());
                }

                evidenceEvaluation.setCantDoReason(evidenceEvaluationDTO.getCantDoReason());
                evidenceEvaluation.setEvaluationUser(userName);
                evidenceEvaluation.setPendingEvaluation(pendingEvaluation);
                evidenceEvaluation.setEvaluationDate(new Date());
                evidenceEvaluation.setInitEvaluation(evidenceEvaluationDTO.getInitEvaluation());
                evidenceEvaluation.setEndEvaluation(evidenceEvaluationDTO.getEndEvaluation());
                evidenceEvaluation.setEvaluationTimeInSeconds(evidenceEvaluationDTO.getEvaluationTimeInSeconds());
                addEvidenceEvaluationCommand.execute(evidenceEvaluation);
                pendingEvaluation.getEvidenceEvaluations().add(evidenceEvaluation);
                //Create proofs
                for (ProofDTO proofDTO : evidenceEvaluationDTO.getProofs()) {
                    proof = new Proof();
                    proof.setEvidenceEvaluation(evidenceEvaluation);
                    proof.setPathWithMarks(proofDTO.getPathWithMarks());
                    proof.setPathWithoutMarks(proofDTO.getPathWithoutMarks());
                    proof.setProofDate(new Date()); //Date when proof is created in the system
                    proof.setProofOrder(proofDTO.getOrder());
                    Evidence proofEvidence = new Evidence();
                    proofEvidence.setId(proofDTO.getEvidenceId());
                    proof.setEvidence(proofEvidence);
                    proof.setProofResult(proofDTO.getProofResult());
                    addProofCommand.execute(proof);
                    //Create marquis
                    List<MarquisDTO> lstMaquisDTO = proofDTO.getMarquis();

                    if (lstMaquisDTO != null && !lstMaquisDTO.isEmpty()) {
                        log.debug("there're marquis");
                        for (MarquisDTO marquisDTO : proofDTO.getMarquis()) {
                            marquis = new Marquis();
                            marquis.setColor(marquisDTO.getColor());
                            marquis.setHeight(marquisDTO.getHeight());
                            marquis.setWidth(marquisDTO.getWidth());
                            marquis.setX(marquisDTO.getX());
                            marquis.setY(marquisDTO.getY());
                            marquis.setType(marquisDTO.getType());
                            marquis.setProof(proof);

                            //adds marquis
                            addMarquis(marquis);
                        }
                    }
                }
            }
            // Fecha final de evaluación
            Date evaluationEndDate = new Date();
            log.debug("asignando fecha final a la pendingEvaluation: " + evaluationEndDate);
            pendingEvaluation.setEvaluationEndDate(evaluationEndDate);
            //Assign finished to pending evaluation
            pendingEvaluation.computeResult();

            this.enqueueObservedMetric(pendingEvaluation.getObservedSituation());
        }
        log.info("end");
    }

    /**
     *
     * @deprecated se debe utilizar enqueueObservedMetric sin session ya que es una operacion interna
     *
     * @param observedMetrics
     * @param sessionId
     * @throws ScopixException
     *
     */
    @Deprecated
    private void enqueueObservedMetric(ObservedSituation os, long sessionId) throws ScopixException {
        List<ObservedMetric> list = new ArrayList<ObservedMetric>();
        for (ObservedMetric om : os.getObservedMetrics()) {
            om.setEvaluationState(EvaluationState.ENQUEUED);
            list.add(om);
        }
        this.enqueueForEvaluation(list, EvaluationQueue.METRIC, sessionId);
    }

    private void enqueueObservedMetric(ObservedSituation os) throws ScopixException {
        List<ObservedMetric> list = new ArrayList<ObservedMetric>();
        for (ObservedMetric om : os.getObservedMetrics()) {
            om.setEvaluationState(EvaluationState.ENQUEUED);
            list.add(om);
        }
        this.enqueueForEvaluation(list, EvaluationQueue.METRIC);
    }

    /**
     *
     * @param metricId id de Metric
     * @param day if null, then take the default value, this date
     * @param sessionId session de usuario
     * @return ObservedMetric para filtros ingresados
     * @throws ScopixException excepcion en caso de error
     */
    public ObservedMetric getObservedMetricForADay(Integer metricId, Date day, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId,
                EvaluationManagerPermissions.GET_OBSERVED_METRIC_FOR_A_EVIDENCE_REQUEST_PERMISSION);
        ObservedMetric observedMetric = null;
        GetObservedMetricForADayCommand command = new GetObservedMetricForADayCommand();
        observedMetric = command.execute(metricId, day);
        log.info("end");
        return observedMetric;
    }

    public ObservedMetric getObservedMetricForADay(Integer metricId, Date day) throws ScopixException {
        log.info("start [metricId:" + metricId + "][day" + day + "]");
        GetObservedMetricForADayCommand command = new GetObservedMetricForADayCommand();
        ObservedMetric observedMetric = command.execute(metricId, day);
        log.info("end");
        return observedMetric;
    }

    /**
     *
     * @param observedMetric filtro para recuperar lista
     * @param sessionId session del usuario
     * @return List<ObservedMetric> lista de observed Metric
     * @throws ScopixException excepcion en caso de error
     */
    public List<ObservedMetric> getObservedMetricList(ObservedMetric observedMetric, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.GET_OBSERVED_METRIC_LIST_PERMISSION);
        List<ObservedMetric> observedMetrics = null;
        GetObservedMetricListCommand command = new GetObservedMetricListCommand();
        observedMetrics = command.execute(observedMetric);
        return observedMetrics;
    }

    /**
     *
     * @param situationId id de situation
     * @param day dia del observed situation solicitado
     * @param sessionId session de usuario
     * @return ObservedSituation resultado de los filtros
     * @throws ScopixException excepcion en caso de error
     */
    public ObservedSituation getObservedSituationForADay(Integer situationId, Date day, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId,
                EvaluationManagerPermissions.GET_OBSERVED_SITUATION_FOR_A_METRIC_PERMISSION);

        ObservedSituation observedSituation = getObservedSituationForADay(situationId, day);
        return observedSituation;
    }

    public ObservedSituation getObservedSituationForADay(Integer situationId, Date day) throws ScopixException {
        GetObservedSituationForADayCommand command = new GetObservedSituationForADayCommand();
        ObservedSituation observedSituation = command.execute(situationId, day);
        return observedSituation;
    }

    /**
     *
     * @deprecated ya no se utiliza se debe usar enqueueForEvaluation sin Session ya que corresponde a una operacion de sistema
     * enqueue into the proper queue depending in the type of evaluation of the
     *
     * @param prioritization priorizacion para el encolado
     * @param queueType tipo de Queue
     * @param sessionId session de usuario
     * @throws ScopixException Excepcion en caso de Error
     */
    @Deprecated
    private void enqueueForEvaluation(Prioritization prioritization, QueueType queueType, long sessionId) throws
            ScopixException {
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.ENQUEUE_FOR_EVALUATION_PERMISSION);
        SpringSupport.getInstance().findBeanByClassName(EvaluationQueueManager.class).enqueue(prioritization, queueType,
                sessionId);
    }

    public void enqueueForEvaluation(Prioritization prioritization, QueueType queueType) throws
            ScopixException {
        //se cambia metodo ya que existe uno con estos mismos parametros
        SpringSupport.getInstance().findBeanByClassName(EvaluationQueueManager.class).
                enqueueNewEvaluation(prioritization, queueType);
    }

    /**
     * @deprecated se debe utilizar enqueueForEvaluation sin session ya que corresponde a una operacion interna enqueue a list
     * into the proper queue depending in the type of evaluation of the Required Situation Element
     *
     * @param prioritizations lista de objetos priorizables como ObservedMetric, ObservedSituation, PendingEvaluation
     * @param queueType Tipo de cola asociada
     * @param sessionId session de usuario conectado
     * @throws ScopixException Excepcion en caso de Error
     */
    @Deprecated
    private void enqueueForEvaluation(List<? extends Prioritization> prioritizations, QueueType queueType, long sessionId)
            throws ScopixException {

        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.ENQUEUE_FOR_EVALUATION_LIST_PERMISSION);
        SpringSupport.getInstance().findBeanByClassName(EvaluationQueueManager.class).enqueue(prioritizations, queueType,
                sessionId);
    }

    /**
     * enqueue a list into the proper queue depending in the type of evaluation of the Required Situation Element
     *
     * @param prioritizations lista de objetos priorizables como ObservedMetric, ObservedSituation, PendingEvaluation
     * @param queueType Tipo de cola asociada
     * @throws ScopixException Excepcion en caso de Error
     *
     */
    public void enqueueForEvaluation(List<? extends Prioritization> prioritizations, QueueType queueType) throws ScopixException {
        SpringSupport.getInstance().findBeanByClassName(EvaluationQueueManager.class).enqueue(prioritizations, queueType);
    }

    /**
     *
     * @param pendingEvaluations Lista de Pendings Evaluation para agregar a la cola
     * @throws ScopixException Excepcion en caso de Error
     */
    //se elimina chequeo de seguridad se asume que la llamada es de sistema
    public void enqueuePendingEvaluationForEvaluation(List<PendingEvaluation> pendingEvaluations)
            throws ScopixException {
        log.info("start, pendingEvaluations size: [" + pendingEvaluations.size() + "]");
        for (PendingEvaluation pendingEvaluation : pendingEvaluations) {
            this.enqueueForEvaluation(pendingEvaluation, pendingEvaluation.getEvaluationQueue());
        }
        log.info("end");
    }

    /**
     *
     * @param queueName nombre de la cola
     * @param sessionId id de Session del usuario conectado
     * @return PendingEvaluation asociado a la cola especificada
     * @throws ScopixException Excepcion en caso de Error
     */
    public PendingEvaluation getNextPendingEvaluation(String queueName, long sessionId) throws ScopixException {
        log.info("start [queueName:" + queueName + "]");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.GET_NEXT_PENDING_EVALUATION_PERMISSION);
        PendingEvaluation pendingEvaluation = getNextPendingEvaluation(queueName);
        log.info("end [pendingEvaluation:" + pendingEvaluation + "]");
        return pendingEvaluation;
    }

    /**
     * Retorna el siguiente Pending Evaluation dada una cola no verifica seguridad
     */
    private PendingEvaluation getNextPendingEvaluation(String queueName) throws ScopixException {
        GetFirstElementOfQueueCommand command = new GetFirstElementOfQueueCommand();
        PendingEvaluation pendingEvaluation = command.execute(queueName);
        if (pendingEvaluation == null) {
            throw new ScopixException("NO_EVIDENCE_AVAILABLE");
        }
        return pendingEvaluation;
    }

    /**
     *
     * @param queueName nombre de la cola
     * @param sessionId id de Session del usuario conectado
     * @return SituationSendDTO objeto para transporte hacia operators WEB y GUI
     * @throws ScopixException Excepcion en caso de Error
     */
    public SituationSendDTO getNextEvidenceForOperator(String queueName, long sessionId) throws ScopixException {
        log.info("start, queueName: [" + queueName + "], sessionId: [" + sessionId + "]");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.GET_NEXT_EVIDENCE_FOR_OPERATOR_PERMISSION);
        //Obtiene login del usuario autenticado
        log.debug("obteniendo login del usuario autenticado");
        String userName = getSecurityManager().getUserName(sessionId);

        SituationSendDTO situationSendDTO = null;
        PendingEvaluation pendingEvaluation = null;
        try {
            while (true) {
//                pendingEvaluation = getNextPendingEvaluation(queueName, sessionId);
                //no verificamos seguridad ya que se verifico al iniciar el metodo
                pendingEvaluation = getNextPendingEvaluation(queueName);

                if (pendingEvaluation.getEvaluationState().getName().equals(EvaluationState.ENQUEUED.getName())) {
                    //checks if pending evaluation is live and has expired
                    break;
                }
            }
            if (pendingEvaluation != null) {
                log.debug("asignando userName a la pendingEvaluation: " + userName);
                pendingEvaluation.setUserName(userName);
                Date evaluationStartDate = new Date();
                log.debug("asignando fecha inicial a la pendingEvaluation: " + evaluationStartDate);
                pendingEvaluation.setEvaluationStartDate(evaluationStartDate);

                //Asigna a checking
                log.debug("asignando a checking pending evaluation con id: [" + pendingEvaluation.getId() + "]");
                pendingEvaluation.asignToCheck();
                situationSendDTO = transformPendingEvaluation(pendingEvaluation);
            }
        } catch (Exception e) {
            log.error("error = " + e.getMessage(), e);
        }
        if (situationSendDTO != null) {
            log.debug("Corporate = " + situationSendDTO.getCorporate());
            log.debug("StoreName = " + situationSendDTO.getStoreName());
            log.debug("StoreDescription = " + situationSendDTO.getStoreDescription());
            log.debug("Area = " + situationSendDTO.getArea());
            log.debug("Date = " + situationSendDTO.getEvidenceDateTime());
            log.debug("metrics size = " + situationSendDTO.getMetrics().size());
            log.debug("pendingEvaluationId = " + situationSendDTO.getPendingEvaluationId());
            log.debug("situationId = " + situationSendDTO.getSituationId());
        }
        log.info("end");
        return situationSendDTO;
    }

    /**
     * Los tipos de evidencia que se envian a operator son Video e Image, por lo cual hay que hacer el parseo necesario para que
     * esto siga así
     *
     * @param pendingEvaluation objeto a transformar
     * @return SituationSendDTO objeto de transporte entre Core y Operators WEB y GUI
     * @throws ScopixException Excepcion en caso de Error
     */
    private SituationSendDTO transformPendingEvaluation(PendingEvaluation pe) throws ScopixException {
        log.debug("start");
        GetObservedMetricCommand getObservedMetricCommand = new GetObservedMetricCommand();
        GetObservedMetricListCommand getObservedMetricListCommand = new GetObservedMetricListCommand();

        //PendingEvaluation pendingEvaluation = command.execute(pe.getId());
        SituationSendDTO situationSendDTO = new SituationSendDTO();
        //TO-DO revisar si PendingEvaluation esta cargado completamente
        log.debug("pendingEvaluationId: [" + pe.getId() + "]");
        log.debug("situationId: [" + pe.getObservedSituation().getSituation().getId() + "]");

        situationSendDTO.setSituationId(pe.getObservedSituation().getSituation().getId());
        situationSendDTO.setPendingEvaluationId(pe.getId());
        log.debug("rejected history: [" + pe.getRejectedHistorys().size() + "]");

        if (!pe.getRejectedHistorys().isEmpty()) {
            Collections.sort(pe.getRejectedHistorys());
            RejectedHistory rh = pe.getRejectedHistorys().get(pe.getRejectedHistorys().size() - 1);
            situationSendDTO.setRejectedObservation(rh.getRejectComment() != null ? rh.getRejectComment() : "");
            situationSendDTO.setRejected(true);
        }
        boolean pass = false;
        ObservedMetric omFilter = new ObservedMetric();
        omFilter.setObservedSituation(pe.getObservedSituation());

        log.debug("antes de consultar observedMetrics");
        List<ObservedMetric> lstObservedMetrics = getObservedMetricListCommand.execute(omFilter);
        log.debug("lstObservedMetrics: [" + lstObservedMetrics + "]");

        if (lstObservedMetrics != null && !lstObservedMetrics.isEmpty()) {
            log.debug("lstObservedMetrics.size(): [" + lstObservedMetrics.size() + "]");

            for (ObservedMetric obsMetric : lstObservedMetrics) {
                log.debug("obsMetric id: [" + obsMetric.getId() + "]");
                ObservedMetric observedMetric = getObservedMetricCommand.execute(obsMetric.getId());

                Metric metric = observedMetric.getMetric();
                log.debug("metricId: [" + metric.getId() + "], description: [" + metric.getDescription() + "]");

                if (!pass) {
                    appendDataBasicSituationSendDTO(situationSendDTO, metric, observedMetric);
                    pass = true;
                }
                MetricSendDTO metricSendDTO = new MetricSendDTO();
                metricSendDTO.setMetricId(metric.getId());
                Collections.sort(situationSendDTO.getMetrics());

                if (Collections.binarySearch(situationSendDTO.getMetrics(), metricSendDTO) < 0) {
                    log.debug("entra a ver epc");
                    ExtractionPlanMetric epm = observedMetric.getMetric().getExtractionPlanMetric();
                    if (epm != null) {
                        ExtractionPlanCustomizing epc = epm.getExtractionPlanCustomizing();
                        //MetricType
                        metricSendDTO.setType(metric.getMetricTemplate().getMetricTypeElement().name());
                        log.debug("metric type = " + metric.getMetricTemplate().getMetricTypeElement().name());
                        //description
                        if (metric.getMetricTemplate().getOperatorDescription() != null && metric.getMetricTemplate().
                                getOperatorDescription().length() > 0) {
                            metricSendDTO.setDescription(metric.getMetricTemplate().getOperatorDescription());
                        } else {
                            metricSendDTO.setDescription(metric.getMetricTemplate().getDescription());
                        }
                        log.debug("mt desc = " + metricSendDTO.getDescription());
                        //evalInstruction
                        metricSendDTO.setEvalInstruction(metric.getMetricTemplate().getEvaluationInstruction());
                        log.debug("eval inst = " + metric.getMetricTemplate().getEvaluationInstruction());
                        //name
                        metricSendDTO.setName(metric.getMetricTemplate().getName());
                        metricSendDTO.setOrder(metric.getMetricOrder());
                        log.debug("mt name = " + metric.getMetricTemplate().getName() + " m order = " + metric.getMetricOrder());
                        if (epc != null) {
                            metricSendDTO.setMultiple(epc.isOneEvaluation());
                            log.debug("one eval = " + epc.isOneEvaluation());
                        }

                        log.debug("metricSendDTO por adicionar en situationSendDTO. [ID: " + metricSendDTO.getMetricId() + "], "
                                + "[NAME: " + metricSendDTO.getName() + "], "
                                + "[DESCRIPTION: " + metricSendDTO.getDescription() + "], "
                                + "[ORDER: " + metricSendDTO.getOrder() + "]");
                        situationSendDTO.getMetrics().add(metricSendDTO);
                        log.debug("evidences size = " + observedMetric.getEvidences().size());

                        for (Evidence evidence : observedMetric.getEvidences()) {
                            //Evidence Path

                            String separator = (evidence.getEvidenceServicesServer().getEvidencePath().indexOf("/") >= 0)
                                    ? "/" : "\\";

                            EvidenceProvider ep = evidence.getEvidenceRequests().get(0).getEvidenceProvider();

                            String proofPath = generateProofPath(evidence, observedMetric, separator);
                            log.debug("proof path = " + proofPath);
                            String evidencePath = generateEvidencePath(evidence, observedMetric, separator);
                            String templatePath = generateTemplatePath(evidence, observedMetric, separator, ep, pe);
                            EvidenceSendDTO evidenceSendDTO = generateEvidenceSendDTO(evidence, evidencePath, proofPath, metric,
                                    templatePath, epm, ep);
                            metricSendDTO.getEvidences().add(evidenceSendDTO);
                        }
                        //Agregamos las Evaluaciones a la metrica
                        List<EvidenceEvaluationDTO> evaluationDTOs = getEvaluationFromObservedMetric(observedMetric.getId());
                        metricSendDTO.setEvaluationDTOs(evaluationDTOs);
                    } //end epm != null
                }

            }
        }
        log.debug("situationSendDTO.getMetrics().size(): [" + situationSendDTO.getMetrics().size() + "]");
        Collections.sort(situationSendDTO.getMetrics(), MetricSendDTO.getComparatorByOrder());
        log.debug("end");
        return situationSendDTO;
    }

    private List<EvidenceEvaluationDTO> getEvaluationFromObservedMetric(Integer observedMetricId) throws ScopixException {
        //se deben recuperar desde base de datos
        GetEvidenceEvaluationDTOListCommand command = new GetEvidenceEvaluationDTOListCommand();
        List<EvidenceEvaluationDTO> evaluationDTOs = command.execute(observedMetricId);
        return evaluationDTOs;
    }

    private void appendDataBasicSituationSendDTO(SituationSendDTO situationSendDTO, Metric metric,
            ObservedMetric observedMetric) {
        log.info("start");
        //Corporate
        situationSendDTO.setCorporate(metric.getArea().getStore().getCorporate().getDescription());
        //Store
        situationSendDTO.setStoreName(metric.getArea().getStore().getName());
        situationSendDTO.setStoreDescription(metric.getArea().getStore().getDescription());
        //Area
        situationSendDTO.setArea(metric.getArea().getDescription());

        //Obtiene fecha de evidencia
        Date evidenceDate = observedMetric.getEvidences().get(0).getEvidenceDate();
        //Formatea fecha al tipo yyyy-MM-dd HH:mm:ss
        String evidenceDateTime = DateFormatUtils.format(evidenceDate, DATE_TIME_FORMAT);
        //Asigna fecha de evidencia formateada al DTO
        situationSendDTO.setEvidenceDateTime(evidenceDateTime);
        log.debug("situationSendDTO.evidenceDateTime: " + evidenceDateTime);

        situationSendDTO.setProductName(metric.getSituation().getSituationTemplate().getProduct().getName());
        situationSendDTO.setProductDescription(metric.getSituation().getSituationTemplate().getProduct().getDescription());
        log.info("end");
    }

    private EvidenceSendDTO generateEvidenceSendDTO(Evidence evidence, String evidencePath, String proofPath, Metric metric,
            String templatePath, ExtractionPlanMetric epm, EvidenceProvider ep)
            throws ScopixException {
        log.info("start");
        EvidenceSendDTO evidenceSendDTO = new EvidenceSendDTO();
        evidenceSendDTO.setEvidenceId(evidence.getId());
        evidenceSendDTO.setEvidencePath(evidencePath);
        evidenceSendDTO.setProofPath(proofPath);
        String evidenceType;
        if (metric.getMetricTemplate().getEvidenceTypeElement().equals(EvidenceType.IMAGE)) {
            evidenceType = EvidenceType.IMAGE.name();
        } else {
            evidenceType = metric.getMetricTemplate().getEvidenceTypeElement().name();
        }
        log.debug("evidence type =" + evidenceType);
        evidenceSendDTO.setEvidenceType(evidenceType);
        //Evidence Provider
//                            EvidenceProvider ep = getEvidenceProviderCommand.execute(evidence.getEvidenceRequests().get(0).
//                                    getEvidenceProvider().getId());
        //TO-DO revisar si esta completamente cargado

        log.debug("template path = " + templatePath);
        //agregamos el path del template a la evidenceSendDTO
        evidenceSendDTO.setTemplatePath(templatePath);
        Set<Integer> providerIds = new HashSet<Integer>();
        if (getCorporateStructureManager().getCorporateId() == CORPORATE_LOWES) {
            for (EvidenceRequest evidenceRequest : metric.getEvidenceRequests()) {
                providerIds.add(evidenceRequest.getEvidenceProvider().getId());
            }
        } else {
            for (EvidenceProvider epTemp : epm.getEvidenceProviders()) {
                providerIds.add(epTemp.getId());
            }
        }
        log.debug("providers RelationEvidenceProviderLocations "
                + ep.getRelationEvidenceProviderLocationsFrom().size());
        EvidenceProviderSendDTO epsdto = transformProviderToDTO(ep, metric, providerIds);
        evidenceSendDTO.setEvidenceProvider(epsdto);
        List<RegionTransferSendDTO> regionsSent = new ArrayList<RegionTransferSendDTO>();
        for (EvidenceRegionTransfer regionTransfers : evidence.getEvidenceRegionTransfers()) {
            if (regionTransfers.isCompleted()) {
                RegionTransferSendDTO regionTransferSendDTO = new RegionTransferSendDTO();
                regionTransferSendDTO.setIp(regionTransfers.getRegionServerIp());
                regionTransferSendDTO.setServerCodeName(regionTransfers.getRegionServerName());
                regionsSent.add(regionTransferSendDTO);
            }
        }
        evidenceSendDTO.setRegionTransfers(regionsSent);;
        log.debug("end");
        return evidenceSendDTO;
    }

    private String generateTemplatePath(Evidence evidence, ObservedMetric observedMetric, String separator, EvidenceProvider ep,
            PendingEvaluation pe) {
        //movemos la solicitud del templatePath hasta conocer el ep correspondiente
        //Template Path
        log.info("start");
        String templatePath = evidence.getEvidenceServicesServer().getEvidencePath();
        templatePath += observedMetric.getMetric().getArea().getStore().getCorporate().getName();
        templatePath += separator + observedMetric.getMetric().getArea().getStore().getName();
        templatePath += separator + SystemConfig.getStringParameter("TEMPLATES_FOLDER");
        templatePath += separator + ep.getTemplatePath(pe.getObservedSituation().getSituation().getSituationTemplate().getId());
        log.info("end");
        return templatePath;
    }

    private EvidenceProviderSendDTO transformProviderToDTO(EvidenceProvider ep, Metric metric, Set<Integer> providerIds) {
        log.info("start");
        EvidenceProviderSendDTO epsdto = new EvidenceProviderSendDTO();
        epsdto.setId(ep.getId());
        epsdto.setDescription(ep.getDescription());
        for (RelationEvidenceProviderLocation location : ep.getRelationEvidenceProviderLocationsFrom()) {

            //se excluyen las de otras areas
            if (!location.getArea().getId().equals(metric.getArea().getId())) {
                continue;
            }
            // se excluye la dirección para la interfaz de operator cuando
            if (!providerIds.contains(location.getEvidenceProviderTo().getId())) {
                continue;
            }

            if (location.getLocation().equals(Location.LEFT)) {
                epsdto.setLeft(location.getEvidenceProviderTo().getId());
            } else if (location.getLocation().equals(Location.RIGHT)) {
                epsdto.setRight(location.getEvidenceProviderTo().getId());
            } else if (location.getLocation().equals(Location.TOP)) {
                epsdto.setTop(location.getEvidenceProviderTo().getId());
            } else if (location.getLocation().equals(Location.BOTTOM)) {
                epsdto.setBottom(location.getEvidenceProviderTo().getId());
            }
        }
        epsdto.setDefaultEvidenceProvider(ep.getDefaultEvidenceProvider());
        epsdto.setViewOrder(ep.getViewOrder());
        log.debug("epsdto [botton:" + epsdto.getBottom() + "][left:" + epsdto.getLeft() + "]"
                + "[right:" + epsdto.getRight() + "][top:" + epsdto.getTop() + "]"
                + "[default:" + epsdto.getDefaultEvidenceProvider() + "]"
                + "[desc:" + epsdto.getDescription() + "][id:" + epsdto.getId() + "]");
        log.info("end");
        return epsdto;
    }

    private String generateProofPath(Evidence evidence, ObservedMetric observedMetric, String proofSeparator) {
        log.info("start");
        //the new directory structure is:  yyyy/MM/dd (20100727)
        String proofPath = evidence.getEvidenceServicesServer().getProofPath();
        proofPath += observedMetric.getMetric().getArea().getStore().getCorporate().getName();
        proofPath += proofSeparator + observedMetric.getMetric().getArea().getStore().getName();
        proofPath += proofSeparator + FileUtils.getPathFromDate(evidence.getEvidenceDate(), proofSeparator);
        proofPath += proofSeparator;
        log.info("end");
        return proofPath;
    }

    private String generateEvidencePath(Evidence evidence, ObservedMetric observedMetric, String separator) {
        log.info("start");
        String evidencePath;
        log.debug("[alternativeMode:" + evidence.getEvidenceServicesServer().getAlternativeMode() + "]");
        if (evidence.getEvidenceServicesServer().getAlternativeMode()) {
            evidencePath = evidence.getEvidenceServicesServer().getAlternativeEvidencePath();
            evidencePath += observedMetric.getMetric().getArea().getStore().getCorporate().getName();
            evidencePath += separator + observedMetric.getMetric().getArea().getStore().getName();
            evidencePath += separator + FileUtils.getPathFromDate(evidence.getEvidenceDate(), separator);
            evidencePath += separator + evidence.getEvidencePath();
        } else {
            evidencePath = evidence.getEvidenceServicesServer().getEvidencePath();
            evidencePath += observedMetric.getMetric().getArea().getStore().getCorporate().getName();
            evidencePath += separator + observedMetric.getMetric().getArea().getStore().getName();
            evidencePath += separator + FileUtils.getPathFromDate(evidence.getEvidenceDate(), separator);
            evidencePath += separator + evidence.getEvidencePath();
        }
        log.info("end [evidencePath:" + evidencePath + "]");
        return evidencePath;
    }

    /**
     *
     * @param observedSituation agrega un ObservedSituation a base de datos
     * @param sessionId session de usuario conectado
     * @throws ScopixException excepcion en caso de error
     */
    public void addObservedSituation(ObservedSituation observedSituation, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.ADD_OBSERVED_SITUATION_PERMISSION);
        if (observedSituation == null) {
            //PENDING error message
            throw new ScopixException("PENDING");
        }

        AddObservedSituationCommand command = new AddObservedSituationCommand();
        command.execute(observedSituation);
        log.info("end");
    }

    /**
     *
     * @param observedSituationEvaluation objecto a almacenar
     * @param sessionId sesion de usuario conectado
     * @throws ScopixException excepcion en caso de error
     */
    public void addObservedSituationEvaluation(ObservedSituationEvaluation observedSituationEvaluation, long sessionId)
            throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.ADD_OBSERVED_SITUATION_EVALUATION_PERMISSION);

        if (observedSituationEvaluation == null) {
            //PENDING error message
            throw new ScopixException("PENDING");
        }

        AddObservedSituationEvaluationCommand command = new AddObservedSituationEvaluationCommand();
        command.execute(observedSituationEvaluation);
        log.info("end");
    }

    /**
     *
     * @param pendingEvaluation objeto a actualizar
     * @param sessionId session de usuario
     * @throws ScopixException excepcion en caso de error
     */
    public void updatePendingEvaluation(PendingEvaluation pendingEvaluation, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.UPDATE_PENDING_EVALUATION_PERMISSION);
        updatePendingEvaluation(pendingEvaluation);
        log.info("end");
    }

    public void updatePendingEvaluation(PendingEvaluation pendingEvaluation) throws ScopixException {
        log.info("start");
        getCommonEvalManager().updatePendingEvaluation(pendingEvaluation);
        log.info("end");
    }

    /**
     *
     * @param observedMetric objeto a actualizar
     * @param sessionId session de usuario
     * @throws ScopixException excepcion en caso de error
     */
    public void updateObservedMetric(ObservedMetric observedMetric, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.UPDATE_OBSERVED_METRIC_PERMISSION);
        updateObservedMetric(observedMetric);
        log.info("end");
    }

    public void updateObservedMetric(ObservedMetric observedMetric) throws ScopixException {
        log.info("start");
        if (observedMetric == null) {
            //PENDING error message
            throw new ScopixException("PENDING");
        }
        UpdateObservedMetricCommand command = new UpdateObservedMetricCommand();
        command.execute(observedMetric);
        log.info("end");
    }

    /**
     *
     * @param observedMetric objeto a ser almacenado en base de datos
     * @param sessionId id de session de usuario conectado
     * @throws ScopixException excepcion en caso de error
     */
    public void addObservedMetric(ObservedMetric observedMetric, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.ADD_OBSERVED_METRIC_PERMISSION);
        if (observedMetric == null) {
            //PENDING error message
            throw new ScopixException("PENDING");
        }
        AddObservedMetricCommand command = new AddObservedMetricCommand();
        command.execute(observedMetric);
        log.info("end");
    }

    /**
     *
     * @param metricEvaluation objeto a ser almacenado en base de datos
     * @param sessionId id de session de usuario conectado
     * @throws ScopixException excepcion en caso de error
     */
    public void addMetricEvaluation(MetricEvaluation metricEvaluation, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.ADD_METRIC_EVALUATION_PERMISSION);
        if (metricEvaluation == null) {
            //PENDING error message
            throw new ScopixException("PENDING");
        }
        AddMetricEvaluationCommand command = new AddMetricEvaluationCommand();
        command.execute(metricEvaluation);
        log.info("end");
    }

    /**
     *
     * @param evidence objeto a ser almacenado en base de datos
     * @param sessionId id de session de usuario conectado
     * @throws ScopixException excepcion en caso de error
     */
    public void addEvidence(Evidence evidence, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.ADD_EVIDENCE_PERMISSION);
        if (evidence == null) {
            //PENDING error message
            throw new ScopixException("PENDING");
        }
        AddEvidenceCommand command = new AddEvidenceCommand();
        command.execute(evidence);
        log.info("end");
    }

    /**
     *
     *
     * @param evidenceEvaluation objeto a ser almacenado en base de datos
     * @param sessionId id de session de usuario conectado
     * @throws ScopixException excepcion en caso de error
     */
    public void addEvidenceEvaluation(EvidenceEvaluation evidenceEvaluation, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.ADD_EVIDENCE_EVALUATION_PERMISSION);
        if (evidenceEvaluation == null) {
            //PENDING error message
            throw new ScopixException("PENDING");
        }
        AddEvidenceEvaluationCommand command = new AddEvidenceEvaluationCommand();
        command.execute(evidenceEvaluation);
        log.info("end");
    }

    /**
     *
     * @param proof objeto a ser almacenado en base de datos
     * @param sessionId id de session de usuario conectado
     * @throws ScopixException excepcion en caso de error
     */
    public void addProof(Proof proof, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.ADD_PROOF_PERMISSION);
        if (proof == null) {
            //PENDING error message
            throw new ScopixException("PENDING");
        }
        AddProofCommand command = new AddProofCommand();
        command.execute(proof);
        log.info("end");
    }

    /**
     *
     * @param marquis objeto a ser almacenado en base de datos
     * @param sessionId id de session de usuario conectado
     * @throws ScopixException excepcion en caso de error
     */
    public void addMarquis(Marquis marquis, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.ADD_MARQUIS_PERMISSION);
        addMarquis(marquis);
        log.info("end");
    }

    public void addMarquis(Marquis marquis) throws ScopixException {
        log.info("start");
        if (marquis == null) {
            throw new ScopixException("There's no marquis");
        }
        getAddMarquisCommand().execute(marquis);
        log.info("end");
    }

    /**
     *
     * @param idObservedSituation id de Observed situation a recuperar
     * @param sessionId id de session de usuario conectado
     * @return ObservedSituation para el id solicitado
     * @throws ScopixException excepcion en caso de error
     */
    public ObservedSituation getObservedSituation(int idObservedSituation, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.GET_OBSERVED_SITUATION_PERMISSION);
        GetObservedSituationCommand command = new GetObservedSituationCommand();
        ObservedSituation observedSituation = command.execute(idObservedSituation);
        log.info("end");
        return observedSituation;
    }

    /**
     *
     * @param idObservedSituationEvaluation id de Observed situation evaluation a recuperar
     * @param sessionId id de session de usuario conectado
     * @return ObservedSituationEvaluation para el id solicitado
     * @throws ScopixException excepcion en caso de error
     */
    public ObservedSituationEvaluation getObservedSituationEvaluation(int idObservedSituationEvaluation, long sessionId)
            throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.GET_OBSERVED_SITUATION_EVALUATION_PERMISSION);
        GetObservedSituationEvaluationCommand command = new GetObservedSituationEvaluationCommand();
        ObservedSituationEvaluation observedSituationEvaluation = command.execute(idObservedSituationEvaluation);
        log.info("end");
        return observedSituationEvaluation;
    }

    /**
     *
     * @param idObservedMetric id de Observed metric a recuperar
     * @param sessionId id de session de usuario conectado
     * @return ObservedMetric para el id solicitado
     * @throws ScopixException excepcion en caso de error
     */
    public ObservedMetric getObservedMetric(int idObservedMetric, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.GET_OBSERVED_METRIC_PERMISSION);
        GetObservedMetricCommand command = new GetObservedMetricCommand();
        ObservedMetric observedMetric = command.execute(idObservedMetric);
        log.info("start");
        return observedMetric;
    }

    /**
     *
     * @param idMetricEvaluation id de Metric Evaluation a recuperar
     * @param sessionId id de session de usuario conectado
     * @return MetricEvaluation para el id solicitado
     * @throws ScopixException excepcion en caso de error
     */
    public MetricEvaluation getMetricEvaluation(int idMetricEvaluation, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.GET_METRIC_EVALUATION_PERMISSION);
        GetMetricEvaluationCommand command = new GetMetricEvaluationCommand();
        MetricEvaluation metricEvaluation = command.execute(idMetricEvaluation);
        log.info("start");
        return metricEvaluation;
    }

    /**
     *
     * @param evidenceId id de Evidence a recuperar
     * @param sessionId id de session de usuario conectado
     * @return Evidence para el id solicitado
     * @throws ScopixException excepcion en caso de error
     */
    public Evidence getEvidence(int evidenceId, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.GET_EVIDENCE_PERMISSION);
        GetEvidenceCommand command = new GetEvidenceCommand();
        Evidence evidence = command.execute(evidenceId);
        log.info("start");
        return evidence;
    }

    /**
     *
     * @param evidenceId id de Evidence a recuperar
     * @return Evidence para el id solicitado
     * @throws ScopixException excepcion en caso de error
     */
    public Evidence getEvidence(int evidenceId) throws ScopixException {
        log.info("start");
        GetEvidenceCommand command = new GetEvidenceCommand();
        Evidence evidence = command.execute(evidenceId);
        log.info("start");
        return evidence;
    }

    /**
     *
     * @param idEvidenceEvaluation id de Evidence Evaluation a recuperar
     * @param sessionId id de session de usuario conectado
     * @return EvidenceEvaluation para el id solicitado
     * @throws ScopixException excepcion en caso de error
     */
    public EvidenceEvaluation getEvidenceEvaluation(int idEvidenceEvaluation, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.GET_EVIDENCE_EVALUATION_PERMISSION);
        EvidenceEvaluation evidenceEvaluation = getEvidenceEvaluation(idEvidenceEvaluation);
        log.info("start");
        return evidenceEvaluation;
    }

    public EvidenceEvaluation getEvidenceEvaluation(int idEvidenceEvaluation) throws ScopixException {
        log.info("start");
        GetEvidenceEvaluationCommand command = new GetEvidenceEvaluationCommand();
        EvidenceEvaluation evidenceEvaluation = command.execute(idEvidenceEvaluation);
        log.info("start");
        return evidenceEvaluation;
    }

    /**
     *
     * @param pendingEvaluationId id de Pending Evaluation a recuperar
     * @param sessionId id de session de usuario conectado
     * @return PendingEvaluation para el id solicitado
     * @throws ScopixException excepcion en caso de error
     */
    public PendingEvaluation getPendingEvaluation(int pendingEvaluationId, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.GET_PENDING_EVALUATION_PERMISSION);

        PendingEvaluation pendingEvaluation = getPendingEvaluation(pendingEvaluationId);
        log.info("end");
        return pendingEvaluation;
    }

    public PendingEvaluation getPendingEvaluation(int pendingEvaluationId) throws ScopixException {
        log.info("start");

        GetPendingEvaluationCommand command = new GetPendingEvaluationCommand();
        PendingEvaluation pendingEvaluation = command.execute(pendingEvaluationId);
        log.info("end");
        return pendingEvaluation;
    }

    /**
     *
     * @param idObservedSituation id a eliminar
     * @param sessionId id session de usuario conectado
     * @throws ScopixException excepcion en caso de error
     */
    public void removeObservedSituation(int idObservedSituation, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.REMOVE_OBSERVED_SITUATION_PERMISSION);
        RemoveObservedSituationCommand command = new RemoveObservedSituationCommand();
        command.execute(idObservedSituation);
        log.info("start");
    }

    /**
     *
     * @param idObservedSituationEvaluation id a eliminar
     * @param sessionId id session de usuario conectado
     * @throws ScopixException excepcion en caso de error
     */
    public void removeObservedSituationEvaluation(int idObservedSituationEvaluation, long sessionId)
            throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId,
                EvaluationManagerPermissions.REMOVE_OBSERVED_SITUATION_EVALUATION_PERMISSION);
        RemoveObservedSituationEvaluationCommand command = new RemoveObservedSituationEvaluationCommand();
        command.execute(idObservedSituationEvaluation);
        log.info("start");
    }

    /**
     *
     * @param idObservedMetric id a eliminar
     * @param sessionId id session de usuario conectado
     * @throws ScopixException excepcion en caso de error
     */
    public void removeObservedMetric(int idObservedMetric, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.REMOVE_OBSERVED_METRIC_PERMISSION);
        RemoveObservedMetricCommand command = new RemoveObservedMetricCommand();
        command.execute(idObservedMetric);
        log.info("end");
    }

    /**
     *
     * @param idMetricEvaluation id a eliminar
     * @param sessionId id session de usuario conectado
     * @throws ScopixException excepcion en caso de error
     */
    public void removeMetricEvaluation(int idMetricEvaluation, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.REMOVE_METRIC_EVALUATION_PERMISSION);
        RemoveMetricEvaluationCommand command = new RemoveMetricEvaluationCommand();
        command.execute(idMetricEvaluation);
        log.info("end");
    }

    /**
     *
     * @param idEvidence id a eliminar
     * @param sessionId id session de usuario conectado
     * @throws ScopixException excepcion en caso de error
     */
    public void removeEvidence(int idEvidence, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.REMOVE_EVIDENCE_PERMISSION);
        RemoveEvidenceCommand command = new RemoveEvidenceCommand();
        command.execute(idEvidence);
        log.info("end");
    }

    /**
     *
     * @param idEvidenceEvaluation id a eliminar
     * @param sessionId id session de usuario conectado
     * @throws ScopixException excepcion en caso de error
     */
    public void removeEvidenceEvaluation(int idEvidenceEvaluation, long sessionId) throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.REMOVE_EVIDENCE_EVALUATION_PERMISSION);
        RemoveEvidenceEvaluationCommand command = new RemoveEvidenceEvaluationCommand();
        command.execute(idEvidenceEvaluation);
        log.info("end");
    }

    /**
     *
     * @param observedMetricId filtro para buscar lista
     * @param sessionId id de session de usuario conectado
     * @return List<EvidencesAndProofsDTO> lista de EvidencesAndProofsDTO de un observedMetricId dado
     * @throws ScopixException excepcion en caso de error
     */
    public List<EvidencesAndProofsDTO> getEvidencesAndProofs(Integer observedMetricId, long sessionId)
            throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, EvaluationManagerPermissions.GET_EVIDENCE_AND_PROOF_PERMISSION);
        GetEvidencesAndProofsCommand command = new GetEvidencesAndProofsCommand();
        List<EvidencesAndProofsDTO> list = command.execute(observedMetricId);
        log.info("end");
        return list;
    }

    /**
     * Return the full path of the evidence.
     *
     * @param observedMetricId id de observed Metric
     * @param evidenceId id de Evidencia
     * @param sessionId id de session usuario conectado
     * @return String path de evidencia
     * @throws ScopixException excepcion en caso de error
     */
    public String getEvidencesPath(Integer observedMetricId, Integer evidenceId, long sessionId)
            throws ScopixException {
        log.info("start");
        AuditingManager manager = SpringSupport.getInstance().findBeanByClassName(AuditingManager.class);
        String evidence = manager.getEvidencePath(evidenceId, observedMetricId);
        log.info("end");
        return evidence;
    }

    /**
     * Return the full path of the proof
     *
     * @param proofId Id of the proof
     * @param observedMetricId Id of the observed metric
     * @param withMarks This indicate if the path is withmarks or without marks
     * @param sessionId Id of user session
     * @return String Full path of the proof
     * @throws ScopixException excepcion en caso de error
     */
    public String getProofsPath(Integer proofId, Integer observedMetricId, boolean withMarks, long sessionId)
            throws ScopixException {
        log.info("start");
        AuditingManager manager = SpringSupport.getInstance().findBeanByClassName(AuditingManager.class);
        String evidence = manager.getProofPath(proofId, observedMetricId, withMarks);
        log.info("end");
        return evidence;
    }

    /**
     *
     * @return SecurityManager manager de Seguridad
     */
    public SecurityManager getSecurityManager() {
        if (securityManager == null) {
            securityManager = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        }
        return securityManager;
    }

    /**
     *
     * @param securityManager manager de Seguridad
     */
    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    private EvidenceRequest getEvidenceRequest(Integer evidenceRequestId) throws ScopixException {
        GetEvidenceRequestCommand command = new GetEvidenceRequestCommand();
        EvidenceRequest evidenceRequest = command.execute(evidenceRequestId);
        return evidenceRequest;
    }

    private void updateEvidenceRequest(EvidenceRequest evidenceRequest) throws ScopixException {
        if (evidenceRequest == null || evidenceRequest.getId() == null || evidenceRequest.getId() <= 0) {
            //"periscopeexception.entity.validate.notNullEntity",new String[]{
            throw new ScopixException("label.evidenceRequest");
        }
        UpdateEvidenceRequestCommand command = new UpdateEvidenceRequestCommand();
        command.execute(evidenceRequest);
    }

    /**
     *
     * @return CorporateStructureManager manager de Corporate
     */
    public CorporateStructureManager getCorporateStructureManager() {
        if (corporateStructureManager == null) {
            corporateStructureManager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        }
        return corporateStructureManager;
    }

    /**
     *
     * @param corporateStructureManager manager de Corporate
     */
    public void setCorporateStructureManager(CorporateStructureManager corporateStructureManager) {
        this.corporateStructureManager = corporateStructureManager;
    }

    /**
     *
     * @param idStore id de Store
     * @param closeSituationDate fecha para cerrar situaciones
     * @param situationTemplateIds templates ids a cerrar
     * @param sessionId id de session de usuario conectado
     * @return List<ObservedSituation> cerrados
     * @throws ScopixException Excepcion en caso de Error
     */
    public List<ObservedSituation> closeSituations(Integer idStore, Date closeSituationDate, Integer[] situationTemplateIds,
            long sessionId) throws ScopixException {
        log.info("start [storeId:" + idStore + "][closeSituationDate:" + closeSituationDate + "]");
        //recuperamos todos los os para el store y fecha de cierre
        List<ObservedSituation> observedSituations = null;
        log.debug("isCloseSituation: " + isCloseSituation);
        if (!isCloseSituation) { //se agrega para que no se puedan cerrar antes de que se cierre la transaccion
            isCloseSituation = true;
            try {
                getSecurityManager().checkSecurity(sessionId,
                        CorporateStructureManagerPermissions.EXTRACTION_PLAN_TO_PAST_PERMISSION);
                observedSituations = closeStoreSituation(idStore, closeSituationDate, situationTemplateIds);
            } catch (ScopixException e) {
                log.error("Error = " + e, e);
                throw e;
            } finally {
                isCloseSituation = false;
                log.debug("isCloseSituation: " + isCloseSituation);
            }
        }
        log.info("end");
        return observedSituations;
    }

    private List<ObservedSituation> closeStoreSituation(Integer idStore, Date closeSituationDate,
            Integer[] situationTemplateIds) throws ScopixException {
        log.info("start [idStore:" + idStore + "][closeSituationDate:" + closeSituationDate + "]"
                + "[situationTemplateIds:" + situationTemplateIds + "]");
        List<ObservedSituation> observedSituations = getObservedSituationPendingListCommand().execute(idStore,
                closeSituationDate,
                situationTemplateIds);
        //para cada os recuperado procedemos a crearle el pending evaluation asociado y encolarlo
        List<PendingEvaluation> pendingEvaluations = new ArrayList<PendingEvaluation>();
        for (ObservedSituation os : observedSituations) {
            log.debug("[os:" + os.getId() + "]");
            PendingEvaluation peAux = getPendingEvaluationByObservedSituationCommand().execute(os.getId());
            if (peAux == null) {
                ExtractionPlanCustomizing epc = getEpcByOs(os);
                PendingEvaluation pe = createNewPendingEvaluation(os, epc, CreationType.MANUAL);
                pendingEvaluations.add(pe);
            }
        }
        //encolamos los pendingEvaluation
        log.debug("pendingEvaluations.size = " + pendingEvaluations.size());
        enqueuePendingEvaluationForEvaluation(pendingEvaluations);
        return observedSituations;
    }

    public void closeSituationsCron() {
        //Close all Situation for day
        log.info("start");
        log.debug("isCloseSituation: " + isCloseSituation);

        if (!isCloseSituation) {
            isCloseSituation = true;
            try {
                GetEvidenceForStoreDaySituationCommand command1 = new GetEvidenceForStoreDaySituationCommand();
                GetEvidenceForStoreDaySituationRealCommand command2 = new GetEvidenceForStoreDaySituationRealCommand();
                int countCloseSituation = getCountCloseSituation();

                for (int day = 1; day <= countCloseSituation; day++) {
                    Date date = new Date();
                    date = DateUtils.addDays(date, -1 * day);
                    log.debug("closeSituation Day " + date);
                    List<StorePlan> storePlanSituation = command1.execute(date);

                    List<StorePlan> storeSituationReal = command2.execute(date);
                    double minPerc = getConfiguration().getDouble("min_perc_close_situation", 0.75);
                    boolean closeSituation = getConfiguration().getBoolean("close_situation", false);
                    boolean existsStore;
                    for (StorePlan storePlan : storePlanSituation) {
                        existsStore = false;
                        for (StorePlan storePlanReal : storeSituationReal) {
                            if (storePlan.getStoreId().equals(storePlanReal.getStoreId())) {
                                existsStore = true;
                                if (storePlan.getSituationName().equals(storePlanReal.getSituationName())) {
                                    if (!storePlan.getCount().equals(storePlanReal.getCount())) {
                                        int in = storePlan.getCount();
                                        int out = storePlanReal.getCount();
                                        float perc = (float) ((float) out / (float) in);
                                        log.debug("[perc:" + perc + "][in:" + in + "][out:" + out + "][minPerc:" + minPerc + "]"
                                                + "[storePlan:" + storePlan.getName() + "]"
                                                + "[SituationName:" + storePlan.getSituationName() + "]");
                                        if (perc >= minPerc && perc < 1) {
                                            Integer[] situationTemplateIds = new Integer[]{storePlan.getSituationId()};
                                            if (closeSituation) {
                                                closeStoreSituation(storePlan.getStoreId(), date, situationTemplateIds);
                                            }
                                        }
                                    }
                                }
                                break;
                            }
                        }
                        if (!existsStore) {
                            Integer[] situationTemplateIds = new Integer[]{storePlan.getSituationId()};
                            if (closeSituation) {
                                closeStoreSituation(storePlan.getStoreId(), date, situationTemplateIds);
                            }
                        }
                    }
                }

            } catch (ScopixException e) {
                log.error(e, e);
            } finally {
                isCloseSituation = false;
            }

        }
        log.info("end");
    }

    private int getCountCloseSituation() {
        log.info("start");
        Integer initDayOfWeek = getConfiguration().getInt("star_week", 1);
        Calendar cal = Calendar.getInstance();
        Integer dayActual = cal.get(Calendar.DAY_OF_WEEK);
        // 4,5,6,7,1,2,3
        Integer[] arrayDays;
        Integer actual = null;
        arrayDays = new Integer[7];
        for (int i = 0; i < 7; i++) {
            if (actual == null) {
                actual = initDayOfWeek;
            } else {
                actual = actual + 1;
            }
            if (actual > 7) {
                actual = 1;
            }
            arrayDays[i] = actual;
        }

        log.debug("arrays weekly " + Arrays.toString(arrayDays));
        int count = 0;
        for (Integer day : arrayDays) {
            if (day == dayActual) {
                break;
            } else {
                count = count + 1;
            }
        }
        if (count == 0) { //if count = 0 close situation all week 
            count = 7;
        }
        log.info("end " + count);
        return count;
    }

    /**
     *
     * @return GetOperatorQueueForAObservedSituation comando
     */
    public GetOperatorQueueForAObservedSituation getOperatorQueueForAObservedSituation() {
        if (operatorQueueForAObservedSituation == null) {
            operatorQueueForAObservedSituation = new GetOperatorQueueForAObservedSituation();
        }
        return operatorQueueForAObservedSituation;
    }

    /**
     *
     * @param operatorQueueForAObservedSituation comando
     */
    public void setOperatorQueueForAObservedSituation(GetOperatorQueueForAObservedSituation operatorQueueForAObservedSituation) {
        this.operatorQueueForAObservedSituation = operatorQueueForAObservedSituation;
    }

    /**
     *
     * @return AddPendingEvaluationCommand comando
     */
    public AddPendingEvaluationCommand getAddPendingEvaluationCommand() {
        if (addPendingEvaluationCommand == null) {
            addPendingEvaluationCommand = new AddPendingEvaluationCommand();
        }
        return addPendingEvaluationCommand;
    }

    /**
     *
     * @param addPendingEvaluationCommand comando
     */
    public void setAddPendingEvaluationCommand(AddPendingEvaluationCommand addPendingEvaluationCommand) {
        this.addPendingEvaluationCommand = addPendingEvaluationCommand;
    }

    /**
     *
     * @return GetObservedSituationPendingListCommand comando
     */
    public GetObservedSituationPendingListCommand getObservedSituationPendingListCommand() {
        if (observedSituationPendingListCommand == null) {
            observedSituationPendingListCommand = new GetObservedSituationPendingListCommand();
        }
        return observedSituationPendingListCommand;
    }

    /**
     *
     * @param value comnado
     */
    public void setObservedSituationPendingListCommand(GetObservedSituationPendingListCommand value) {
        this.observedSituationPendingListCommand = value;
    }

    /**
     * Crea una nueva Area para la tupla Store-AreaType
     *
     * @param areaType AreaType a la cual se desea asociar el store
     * @param store Store que se desea asociar
     * @return Area asocia a Store-AreaType
     * @throws ScopixException En caso de Exception de almacenamiento
     */
    public Area createNewArea(AreaType areaType, Store store) throws ScopixException {
        log.info("start [areaType:" + areaType.getId() + "][store:" + store.getId() + "]");
        Area a = new Area();
        a.setStore(store);
        a.setAreaType(areaType);
        a.setName(areaType.getName());
        a.setDescription(areaType.getDescription());
        a = addAutomanticArea(a);
        log.info("end");
        return a;
    }

    private Area addAutomanticArea(Area a) throws ScopixException {
        log.info("start");
        AddAreaCommand command = new AddAreaCommand();
        command.execute(a);
        log.info("end");
        return a;

    }

    /**
     * @return the metricListCommand
     */
    public GetMetricListCommand getMetricListCommand() {
        if (metricListCommand == null) {
            metricListCommand = new GetMetricListCommand();
        }
        return metricListCommand;
    }

    /**
     * @param metricListCommand the metricListCommand to set
     */
    public void setMetricListCommand(GetMetricListCommand metricListCommand) {
        this.metricListCommand = metricListCommand;
    }

    /**
     * @return the pendingEvaluationByObservedSituationCommand
     */
    public GetPendingEvaluationByObservedSituationCommand getPendingEvaluationByObservedSituationCommand() {
        if (pendingEvaluationByObservedSituationCommand == null) {
            pendingEvaluationByObservedSituationCommand = new GetPendingEvaluationByObservedSituationCommand();
        }
        return pendingEvaluationByObservedSituationCommand;
    }

    /**
     * @param value the pendingEvaluationByObservedSituationCommand to set
     */
    public void setPendingEvaluationByObservedSituationCommand(GetPendingEvaluationByObservedSituationCommand value) {
        this.pendingEvaluationByObservedSituationCommand = value;
    }

    /**
     * @return the countPendingEvaluationByObservedSituationCommand
     */
    public GetCountPendingEvaluationByObservedSituationCommand getCountPendingEvaluationByObservedSituationCommand() {
        if (countPendingEvaluationByObservedSituationCommand == null) {
            countPendingEvaluationByObservedSituationCommand = new GetCountPendingEvaluationByObservedSituationCommand();
        }
        return countPendingEvaluationByObservedSituationCommand;
    }

    /**
     * @param value
     */
    public void setCountPendingEvaluationByObservedSituationCommand(GetCountPendingEvaluationByObservedSituationCommand value) {
        this.countPendingEvaluationByObservedSituationCommand = value;
    }

    public PropertiesConfiguration getConfiguration() {
        if (configuration == null) {
            try {
                configuration = new PropertiesConfiguration("system.properties");
                configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
            } catch (ConfigurationException e) {
                log.error("No es posible cargar propiedades: [" + e + "]", e);
            }
        }
        return configuration;
    }

    /**
     * Expere all pending evaluation
     *
     * @param date
     * @return Integer count pending evalutios expired
     * @throws ScopixException
     */
    public List<Integer> expirePendingEvaluations(String date) throws ScopixException {
        log.info("start");
        GetAllPendingEvalutionLiveExpiredCommand command = new GetAllPendingEvalutionLiveExpiredCommand();
        List<Integer> pendingEvaluationExpired = command.execute(date);
        log.info("end");
        return pendingEvaluationExpired;
    }

    public boolean expiredPendingEvaluation(Integer pendingEvaluationId) throws ScopixException {
        PendingEvaluation pe = getPendingEvaluation(pendingEvaluationId);
        getCommonEvalManager().asingToCheckPendingEvaluation(pe, "MANUAL_EXPIRED");
        getCommonEvalManager().expirePendingEvaluation(pe);
        return true;
    }

    /**
     * @return the commonEvalManager
     */
    public CommonEvaluationManager getCommonEvalManager() {
        if (commonEvalManager == null) {
            commonEvalManager = SpringSupport.getInstance().findBeanByClassName(CommonEvaluationManager.class);
        }
        return commonEvalManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("start");
        //creating job for expired pending_evaluations
        try {
            if (getConfiguration().getString("EXPIRE_LIVE.INTERVAL") != null) { //solo si la subida esta activa
                CronTrigger trigger = new CronTrigger();
                trigger.setCronExpression(getConfiguration().getString("EXPIRE_LIVE.INTERVAL"));
                trigger.setName("ExpiredPendingEvaluationTrigger");
                trigger.setGroup("ExpiredPendingEvaluationTrigger");

                JobDetail job = new JobDetail("ExpiredPendingEvaluationJob", "ExpiredPendingEvaluationJob",
                        ExpiredPendingEvaluationLiveJob.class);

                addJob(job, trigger);

                CronTrigger trigger2 = new CronTrigger();
                trigger2.setCronExpression(getConfiguration().getString("TRANSFERSFTP.INTERVAL"));
                trigger2.setName("RetryRegionTransferPendingEvaluationsTrigger");
                trigger2.setGroup("RetryRegionTransferPendingEvaluationsTrigger");

                JobDetail job2 = new JobDetail("RetryRegionTransferPendingEvaluationsJob",
                        "RetryRegionTransferPendingEvaluationsJob", RetryRegionTransferPendingEvaluationsJob.class);
                addJob(job2, trigger2);
            }
            HttpSupport.initInstance(getConfiguration().getInt(MAX_ROUTES, 100), getConfiguration().getInt(MAX_PER_ROUTE, 25));
        } catch (ParseException e) {
            log.warn("no es posible generar trigger " + e, e);
        } catch (ScopixException e) {
            log.warn("no es posible generar trigger " + e, e);
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
     * @return the addMarquisCommand
     */
    public AddMarquisCommand getAddMarquisCommand() {
        if (addMarquisCommand == null) {
            addMarquisCommand = new AddMarquisCommand();
        }
        return addMarquisCommand;
    }

    /**
     * gets the desired evidence region transfers an parse them into the desired dto
     *
     * @param storeId
     * @param situationTemplateId
     * @param day
     * @param startDate
     * @param endDate
     * @return List<EvidenceRegionTransferDTO>
     */
    public List<EvidenceRegionTransferDTO> getEvidenceRegionTransfers(Integer storeId, Integer situationTemplateId, Integer transmNotTransm, Date day,
            Date startDate, Date endDate) {
        GetRegionTransferByCriteriaCommand command = new GetRegionTransferByCriteriaCommand();
        return command.execute(storeId, situationTemplateId, transmNotTransm, day, startDate, endDate);

    }

    /**
     * gets the desired evidence region transfers stats
     *
     * @param storeId
     * @param situationTemplateId
     * @param transmNotTransm
     * @param day
     * @param startDate
     * @param endDate EvidenceRegionTransferStatsDTO
     * @return EvidenceRegionTransferStatsDTO
     */
    public EvidenceRegionTransferStatsDTO getEvidenceRegionTransfersStats(Integer storeId, Integer situationTemplateId, Integer transmNotTransm, Date day,
            Date startDate, Date endDate) {
        GetRegionTransferStatsByCriteriaCommand command = new GetRegionTransferStatsByCriteriaCommand();
        return command.execute(storeId, situationTemplateId, transmNotTransm, day, startDate, day);
    }

    /**
     * gets list map of failed transmitted evidence map
     *
     * @param daysAgo
     * @return List<Map<String, Object>>
     */
    public List<Map<String, Object>> getFailedEvidenceTransfersCommand(Integer daysAgo) {
        return getFindFailedEvidenceTransfersCommand().execute(daysAgo);
    }

    /**
     * reschedule a failed evidence region transfer
     *
     * @param ertId
     * @return EvidenceRegionTransfer
     */
    public EvidenceRegionTransfer retryFailedRegionTransfer(Integer ertId) {
        return getRetryUpdateRegionTransferEntryCommand().execute(ertId);
    }

    /**
     * Finds the desired region server given a codeName
     *
     * @param codeName
     * @return RegionServer
     */
    public RegionServer getRegionServerByCodeName(String codeName) {
        return getFindRegionServerByCodeNameCommand().execute(codeName);
    }

    /**
     * @return the mapTransfer
     */
    public Map<String, ScopixPoolExecutor> getMapTransfer() {
        if (mapTransfer == null) {
            mapTransfer = new HashMap<String, ScopixPoolExecutor>();
        }
        return mapTransfer;
    }

    /**
     * @param mapTransfer the mapTransfer to set
     */
    public void setMapTransfer(Map<String, ScopixPoolExecutor> mapTransfer) {
        this.mapTransfer = mapTransfer;
    }

    /**
     * @return the createRegionTransferEntryCommand
     */
    public CreateRegionTransferEntryCommand getCreateRegionTransferEntryCommand() {
        if (createRegionTransferEntryCommand == null) {
            createRegionTransferEntryCommand = new CreateRegionTransferEntryCommand();
        }
        return createRegionTransferEntryCommand;
    }

    /**
     * @param createRegionTransferEntryCommand the createRegionTransferEntryCommand to set
     */
    public void setCreateRegionTransferEntryCommand(CreateRegionTransferEntryCommand createRegionTransferEntryCommand) {
        this.createRegionTransferEntryCommand = createRegionTransferEntryCommand;
    }

    /**
     * @return the findFailedEvidenceTransfersCommand
     */
    public FindFailedEvidenceTransfersCommand getFindFailedEvidenceTransfersCommand() {
        if (findFailedEvidenceTransfersCommand == null) {
            findFailedEvidenceTransfersCommand = new FindFailedEvidenceTransfersCommand();
        }
        return findFailedEvidenceTransfersCommand;
    }

    /**
     * @param findFailedEvidenceTransfersCommand the findFailedEvidenceTransfersCommand to set
     */
    public void setFindFailedEvidenceTransfersCommand(FindFailedEvidenceTransfersCommand findFailedEvidenceTransfersCommand) {
        this.findFailedEvidenceTransfersCommand = findFailedEvidenceTransfersCommand;
    }

    /**
     * @return the pendingEvidenceRegionTransfers
     */
    public PendingEvidenceRegionTransfers getPendingEvidenceRegionTransfers() {
        if (pendingEvidenceRegionTransfers == null) {
            pendingEvidenceRegionTransfers = SpringSupport.getInstance().findBeanByClassName(PendingEvidenceRegionTransfers.class);
        }
        return pendingEvidenceRegionTransfers;
    }

    /**
     * @param pendingEvidenceRegionTransfers the pendingEvidenceRegionTransfers to set
     */
    public void setPendingEvidenceRegionTransfers(PendingEvidenceRegionTransfers pendingEvidenceRegionTransfers) {
        this.pendingEvidenceRegionTransfers = pendingEvidenceRegionTransfers;
    }

    /**
     * @return the retrtryUpdateRegionTransferEntryCommand
     */
    public RetryUpdateRegionTransferEntryCommand getRetryUpdateRegionTransferEntryCommand() {
        if (retryUpdateRegionTransferEntryCommand == null) {
            retryUpdateRegionTransferEntryCommand = new RetryUpdateRegionTransferEntryCommand();
        }
        return retryUpdateRegionTransferEntryCommand;
    }

    /**
     * @param retryUpdateRegionTransferEntryCommand the retryUpdateRegionTransferEntryCommand to set
     */
    public void setRetryUpdateRegionTransferEntryCommand(RetryUpdateRegionTransferEntryCommand retryUpdateRegionTransferEntryCommand) {
        this.retryUpdateRegionTransferEntryCommand = retryUpdateRegionTransferEntryCommand;
    }

    /**
     * @return the findRegionServerByCodeNameCommand
     */
    public FindRegionServerByCodeNameCommand getFindRegionServerByCodeNameCommand() {
        if (findRegionServerByCodeNameCommand == null) {
            findRegionServerByCodeNameCommand = new FindRegionServerByCodeNameCommand();
        }
        return findRegionServerByCodeNameCommand;
    }

    /**
     * @param findRegionServerByCodeNameCommand the findRegionServerByCodeNameCommand to set
     */
    public void setFindRegionServerByCodeNameCommand(FindRegionServerByCodeNameCommand findRegionServerByCodeNameCommand) {
        this.findRegionServerByCodeNameCommand = findRegionServerByCodeNameCommand;
    }

    /**
     * @return the retryExecutor
     */
    public ScopixPoolExecutor getRetryExecutor() {
        if (retryExecutor == null) {
            LinkedBlockingQueue<Runnable> retryTransferQueue = new LinkedBlockingQueue<Runnable>();
            retryExecutor = new ScopixPoolExecutor(5, retryTransferQueue, "retryQueue");
        }
        return retryExecutor;
    }

    /**
     * @param retryExecutor the retryExecutor to set
     */
    public void setRetryExecutor(ScopixPoolExecutor retryExecutor) {
        this.retryExecutor = retryExecutor;
    }

    /**
     * Solicitudes automaticas para BEE
     *
     * @param defaultDate
     * @throws ScopixException Excepcion si no es posible recuperar HttpSupport desde el sistema
     */
    public void automaticBee(Date defaultDate) throws ScopixException {
        log.info("start");

        CloseableHttpResponse response = null;
        HttpSupport httpSupport = null;
        try {
            httpSupport = HttpSupport.getInstance();
        } catch (HttpClientInitializationException e) {
            throw new ScopixException("No es posible recuperar httpSupport", e);
        }
        Date dateBee = new Date();
        if (defaultDate != null) {
            dateBee = defaultDate;
        }
        GetUrlsBeeCommand command = new GetUrlsBeeCommand();
        List<String> urlsBee = command.execute(dateBee);
        for (String url : urlsBee) {
            try {
                String urlEncode = StringUtils.replace(url, " ", "%20");
                response = httpSupport.httpGet(urlEncode, null);

            } catch (HttpGetException e) {
                log.error(e, e);
            } finally {

                if (response != null) {

                    httpSupport.closeHttpEntity(response.getEntity());
                    httpSupport.closeHttpResponse(response);
                }
            }
        }
        log.info("end");
    }

}
