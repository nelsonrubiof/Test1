/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * ExtractionPlanWebServiceImpl.java
 * 
 * Created on 02-07-2008, 04:26:04 AM
 */
package com.scopix.periscope.extractionplanmanagement.services.webservices.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.extractionmanagement.ArecontEvidenceProvider;
import com.scopix.periscope.extractionmanagement.ArecontImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.AxisGenericEvidenceProvider;
import com.scopix.periscope.extractionmanagement.AxisGenericVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.AxisP3301EvidenceProvider;
import com.scopix.periscope.extractionmanagement.AxisP3301ImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.BrickcomEvidenceProvider;
import com.scopix.periscope.extractionmanagement.BrickcomImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.BroadwareEvidenceProvider;
import com.scopix.periscope.extractionmanagement.BroadwareHTTPEvidenceProvider;
import com.scopix.periscope.extractionmanagement.BroadwareHTTPVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.BroadwareImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.BroadwareVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.Cisco3520F_1_2_1EvidenceProvider;
import com.scopix.periscope.extractionmanagement.Cisco3520F_1_2_1ImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.Cisco7EvidenceProvider;
import com.scopix.periscope.extractionmanagement.Cisco7ImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.Cisco7VideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.CiscoPeopleCountingEvidenceProvider;
import com.scopix.periscope.extractionmanagement.CiscoPeopleCountingExtractionRequest;
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter141EvidenceProvider;
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter141ExtractionRequest;
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter150EvidenceProvider;
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter150ExtractionRequest;
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter212EvidenceProvider;
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter212ExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceProvider;
import com.scopix.periscope.extractionmanagement.EvidenceProviderRequest;
import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.extractionmanagement.ExtractionPlan;
import com.scopix.periscope.extractionmanagement.ExtractionServer;
import com.scopix.periscope.extractionmanagement.KumGoEvidenceProvider;
import com.scopix.periscope.extractionmanagement.KumGoImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.MetricRequest;
import com.scopix.periscope.extractionmanagement.NextLevel3EvidenceProvider;
import com.scopix.periscope.extractionmanagement.NextLevel3VideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.NextLevelEvidenceProvider;
import com.scopix.periscope.extractionmanagement.NextLevelVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.PeopleCountingEvidenceProvider;
import com.scopix.periscope.extractionmanagement.PeopleCountingExtractionRequest;
import com.scopix.periscope.extractionmanagement.ReadUrlPHPEvidenceProvider;
import com.scopix.periscope.extractionmanagement.ReadUrlPHPImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.ReadUrlPHPXmlExtractionRequest;
import com.scopix.periscope.extractionmanagement.SituationExtractionRequest;
import com.scopix.periscope.extractionmanagement.SituationRequest;
import com.scopix.periscope.extractionmanagement.SituationRequestRange;
import com.scopix.periscope.extractionmanagement.SituationRequestRangeType;
import com.scopix.periscope.extractionmanagement.SituationSensor;
import com.scopix.periscope.extractionmanagement.StoreTime;
import com.scopix.periscope.extractionmanagement.VMSGatewayEvidenceProvider;
import com.scopix.periscope.extractionmanagement.VMSGatewayVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.VadaroEvidenceProvider;
import com.scopix.periscope.extractionmanagement.VadaroPeopleCountingEvidenceProvider;
import com.scopix.periscope.extractionmanagement.VadaroPeopleCountingExtractionRequest;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanManager;
import com.scopix.periscope.extractionplanmanagement.dto.EvidenceExtractionRequestDTO;
import com.scopix.periscope.extractionplanmanagement.dto.EvidenceProviderDTO;
import com.scopix.periscope.extractionplanmanagement.dto.EvidenceProviderRequestDTO;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionServerDTO;
import com.scopix.periscope.extractionplanmanagement.dto.MetricRequestDTO;
import com.scopix.periscope.extractionplanmanagement.dto.SituationExtractionRequestDTO;
import com.scopix.periscope.extractionplanmanagement.dto.SituationRequestDTO;
import com.scopix.periscope.extractionplanmanagement.dto.SituationRequestRangeDTO;
import com.scopix.periscope.extractionplanmanagement.dto.SituationSensorDTO;
import com.scopix.periscope.extractionplanmanagement.dto.StoreTimeDTO;
import com.scopix.periscope.extractionplanmanagement.services.webservices.ExtractionPlanWebService;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author marko.perich
 */
@WebService(endpointInterface = "com.scopix.periscope.extractionplanmanagement.services.webservices.ExtractionPlanWebService")
@SpringBean(rootClass = ExtractionPlanWebService.class)
public class ExtractionPlanWebServiceImpl implements ExtractionPlanWebService {

    public static final String REQUESTED_TYPE_VIDEO = "RequestedVideo";
    public static final String REQUESTED_TYPE_XML = "RequestedXml";
    private static final String REQUESTED_TYPE_IMAGE = "RequestedImage";
    private static Logger log = Logger.getLogger(ExtractionPlanWebServiceImpl.class);

    @Override
    public void newExtractionPlan(ExtractionServerDTO extractionServerDTO) throws ScopixWebServiceException {
        log.info("start, extractionServerDTO=" + extractionServerDTO);

        try {
            ExtractionServer extractionServer = new ExtractionServer();
            extractionServer.setServerId(extractionServerDTO.getServerId());

            ExtractionPlan extractionPlan = new ExtractionPlan();
            extractionPlan.setExtractionServer(extractionServer);
            extractionPlan.setStoreName(extractionServerDTO.getStoreName());
            extractionPlan.setTimeZoneId(extractionServerDTO.getTimeZone());
            extractionPlan.setStoreId(extractionServerDTO.getStoreId());
            extractionPlan.setStoreTimes(this.getStoreTimes(extractionPlan, extractionServerDTO.getStoreTimeDTOs()));
            List<EvidenceProvider> evidenceProviders = new ArrayList<EvidenceProvider>();

            for (EvidenceProviderDTO evidenceProviderDTO : extractionServerDTO.getEvidenceProviderDTOs()) {
                EvidenceProvider provider = convertToBusinessObject(extractionServer, evidenceProviderDTO);
                evidenceProviders.add(provider);
            }

            extractionServer.setEvidenceProviders(evidenceProviders);

            List<EvidenceExtractionRequest> evidenceExtractionRequests = new ArrayList<EvidenceExtractionRequest>();
            for (EvidenceExtractionRequestDTO evidenceExtractionRequestDTO : extractionServerDTO
                    .getEvidenceExtractionRequestDTOs()) {
                evidenceExtractionRequests.add(convertToBusinessObject(extractionPlan, evidenceExtractionRequestDTO));
            }

            // List<SituationRequest> situationRequests = convertSituationRequestToBusinessObject(extractionPlan,
            // extractionServerDTO.getSituationRequestDTOs(), evidenceProviders);
            List<SituationRequest> situationRequests = convertNewSituationRequestToBusinessObject(extractionPlan,
                    extractionServerDTO.getSituationRequestDTOs(), evidenceProviders);

            extractionPlan.setEvidenceExtractioRequests(evidenceExtractionRequests);
            extractionPlan.setSituationRequests(situationRequests);
            extractionServer.setExtractionPlan(extractionPlan);

            SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class).newExtractionPlan(extractionServer);
            //
            // SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class).updateExtractionPlanByStoreName(
            // extractionServer.getExtractionPlan().getStoreName());

            SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class).updateExtractionPlan();
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);

        }
        log.debug("end");

    }

    private EvidenceExtractionRequest convertToBusinessObject(ExtractionPlan extractionPlan,
            EvidenceExtractionRequestDTO evidenceExtractionRequestDTO) throws ScopixWebServiceException {
        log.debug("start, extractionPlan=" + extractionPlan + ", evidenceExtractionRequestDTO=" + evidenceExtractionRequestDTO);
        EvidenceProvider evidenceProvider = getEvidenceProvider(extractionPlan, evidenceExtractionRequestDTO.getDeviceId());
        EvidenceExtractionRequest evidenceExtractionRequest = createExtractionRequest(extractionPlan, evidenceProvider,
                evidenceExtractionRequestDTO);
        evidenceExtractionRequest.setExtractionPlan(extractionPlan);
        log.debug("end, evidenceExtractionRequest=" + evidenceExtractionRequest);
        return evidenceExtractionRequest;
    }

    private EvidenceExtractionRequest createExtractionRequest(ExtractionPlan extractionPlan, EvidenceProvider evidenceProvider,
            EvidenceExtractionRequestDTO evidenceExtractionRequestDTO) throws ScopixWebServiceException {
        log.debug("start, extractionPlan=" + extractionPlan + ", evidenceProvider=" + evidenceProvider
                + ", evidenceExtractionRequestDTO=" + evidenceExtractionRequestDTO);
        EvidenceExtractionRequest evidenceExtractionRequest = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date reqDate;
        try {
            reqDate = sdf.parse(evidenceExtractionRequestDTO.getRequestedTime());
        } catch (ParseException e) {
            log.error("Error parsing evidence date: " + evidenceExtractionRequestDTO.getRequestedTime(), e);
            throw new ScopixWebServiceException(
                    "Error parsing evidence date: " + evidenceExtractionRequestDTO.getRequestedTime(), e);
        }

        // valores dependientes del tipo de provider
        log.debug("EvidenceProvider: " + evidenceProvider + ", RequestType: " + evidenceExtractionRequestDTO.getRequestType());
        if (evidenceProvider instanceof BroadwareEvidenceProvider) {
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_IMAGE)) {
                BroadwareImageExtractionRequest broadwareImageExtractionRequest = new BroadwareImageExtractionRequest();
                broadwareImageExtractionRequest.setExtractionPlan(extractionPlan);
                broadwareImageExtractionRequest.setEvidenceProvider(evidenceProvider);
                broadwareImageExtractionRequest.setExtractionPlan(extractionPlan);
                broadwareImageExtractionRequest.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                evidenceExtractionRequest = broadwareImageExtractionRequest;
            } else if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_VIDEO)) {
                BroadwareVideoExtractionRequest broadwareVideoExtractionRequest = new BroadwareVideoExtractionRequest();
                broadwareVideoExtractionRequest.setExtractionPlan(extractionPlan);
                broadwareVideoExtractionRequest.setEvidenceProvider(evidenceProvider);
                broadwareVideoExtractionRequest.setExtractionPlan(extractionPlan);
                broadwareVideoExtractionRequest.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                broadwareVideoExtractionRequest.setLengthInSecs(evidenceExtractionRequestDTO.getDuration());
                evidenceExtractionRequest = broadwareVideoExtractionRequest;
            }
        } else if (evidenceProvider instanceof AxisP3301EvidenceProvider) {
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_IMAGE)) {
                AxisP3301ImageExtractionRequest axisP3301ImageExtractionRequest = new AxisP3301ImageExtractionRequest();
                axisP3301ImageExtractionRequest.setExtractionPlan(extractionPlan);
                axisP3301ImageExtractionRequest.setEvidenceProvider(evidenceProvider);
                axisP3301ImageExtractionRequest.setExtractionPlan(extractionPlan);
                axisP3301ImageExtractionRequest.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                evidenceExtractionRequest = axisP3301ImageExtractionRequest;
            }
        } else if (evidenceProvider instanceof KumGoEvidenceProvider) {
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_IMAGE)) {
                KumGoImageExtractionRequest kumGoImageExtractionRequest = new KumGoImageExtractionRequest();
                kumGoImageExtractionRequest.setExtractionPlan(extractionPlan);
                kumGoImageExtractionRequest.setEvidenceProvider(evidenceProvider);
                kumGoImageExtractionRequest.setExtractionPlan(extractionPlan);
                kumGoImageExtractionRequest.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                evidenceExtractionRequest = kumGoImageExtractionRequest;
            }
        } else if (evidenceProvider instanceof PeopleCountingEvidenceProvider) {
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_XML)) {
                PeopleCountingExtractionRequest peopleCountingExtractionRequest = new PeopleCountingExtractionRequest();
                peopleCountingExtractionRequest.setExtractionPlan(extractionPlan);
                peopleCountingExtractionRequest.setEvidenceProvider(evidenceProvider);
                peopleCountingExtractionRequest.setExtractionPlan(extractionPlan);
                peopleCountingExtractionRequest.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                evidenceExtractionRequest = peopleCountingExtractionRequest;
            }
        } else if (evidenceProvider instanceof CognimaticsPeopleCounter141EvidenceProvider) {
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_XML)) {
                // Version 1.41 de People Counting
                CognimaticsPeopleCounter141ExtractionRequest cognimaticsPeopleCounter141ExtractionRequest = new CognimaticsPeopleCounter141ExtractionRequest();
                cognimaticsPeopleCounter141ExtractionRequest.setExtractionPlan(extractionPlan);
                cognimaticsPeopleCounter141ExtractionRequest.setEvidenceProvider(evidenceProvider);
                cognimaticsPeopleCounter141ExtractionRequest.setExtractionPlan(extractionPlan);
                cognimaticsPeopleCounter141ExtractionRequest.setRemoteRequestId(evidenceExtractionRequestDTO
                        .getExtractionPlanDetailId());
                evidenceExtractionRequest = cognimaticsPeopleCounter141ExtractionRequest;
            }
        } else if (evidenceProvider instanceof CognimaticsPeopleCounter212EvidenceProvider) {
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_XML)) {
                // Version 2.12 de People Counting
                CognimaticsPeopleCounter212ExtractionRequest cognimaticsPeopleCounter212ExtractionRequest = new CognimaticsPeopleCounter212ExtractionRequest();
                cognimaticsPeopleCounter212ExtractionRequest.setExtractionPlan(extractionPlan);
                cognimaticsPeopleCounter212ExtractionRequest.setEvidenceProvider(evidenceProvider);
                cognimaticsPeopleCounter212ExtractionRequest.setExtractionPlan(extractionPlan);
                cognimaticsPeopleCounter212ExtractionRequest.setRemoteRequestId(evidenceExtractionRequestDTO
                        .getExtractionPlanDetailId());
                evidenceExtractionRequest = cognimaticsPeopleCounter212ExtractionRequest;
            }
        } else if (evidenceProvider instanceof CognimaticsPeopleCounter150EvidenceProvider) {
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_XML)) {
                // Version 2.12 de People Counting
                CognimaticsPeopleCounter150ExtractionRequest cognimaticsPeopleCounter150ExtractionRequest = new CognimaticsPeopleCounter150ExtractionRequest();
                cognimaticsPeopleCounter150ExtractionRequest.setExtractionPlan(extractionPlan);
                cognimaticsPeopleCounter150ExtractionRequest.setEvidenceProvider(evidenceProvider);
                cognimaticsPeopleCounter150ExtractionRequest.setExtractionPlan(extractionPlan);
                cognimaticsPeopleCounter150ExtractionRequest.setRemoteRequestId(evidenceExtractionRequestDTO
                        .getExtractionPlanDetailId());
                evidenceExtractionRequest = cognimaticsPeopleCounter150ExtractionRequest;
            }
        } else if (evidenceProvider instanceof AxisGenericEvidenceProvider) {
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_VIDEO)) {
                AxisGenericVideoExtractionRequest axisGenericVideoExtractionRequest = new AxisGenericVideoExtractionRequest();
                axisGenericVideoExtractionRequest.setExtractionPlan(extractionPlan);
                axisGenericVideoExtractionRequest.setEvidenceProvider(evidenceProvider);
                axisGenericVideoExtractionRequest.setExtractionPlan(extractionPlan);
                axisGenericVideoExtractionRequest.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                axisGenericVideoExtractionRequest.setLengthInSecs(evidenceExtractionRequestDTO.getDuration());
                evidenceExtractionRequest = axisGenericVideoExtractionRequest;
            }
        } else if (evidenceProvider instanceof NextLevelEvidenceProvider) { // agregado 20110908
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_VIDEO)) {
                NextLevelVideoExtractionRequest nextLevelVideoExtractionRequest = new NextLevelVideoExtractionRequest();
                nextLevelVideoExtractionRequest.setExtractionPlan(extractionPlan);
                nextLevelVideoExtractionRequest.setEvidenceProvider(evidenceProvider);
                nextLevelVideoExtractionRequest.setExtractionPlan(extractionPlan);
                nextLevelVideoExtractionRequest.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                nextLevelVideoExtractionRequest.setLengthInSecs(evidenceExtractionRequestDTO.getDuration());
                evidenceExtractionRequest = nextLevelVideoExtractionRequest;
            }
        } else if (evidenceProvider instanceof BrickcomEvidenceProvider) { // agregado 20110928
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_IMAGE)) {
                BrickcomImageExtractionRequest extractionRequest = new BrickcomImageExtractionRequest();
                extractionRequest.setExtractionPlan(extractionPlan);
                extractionRequest.setEvidenceProvider(evidenceProvider);
                extractionRequest.setExtractionPlan(extractionPlan);
                extractionRequest.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                evidenceExtractionRequest = extractionRequest;
            }
        } else if (evidenceProvider instanceof ArecontEvidenceProvider) { // agregado 20110928
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_IMAGE)) {
                ArecontImageExtractionRequest extractionRequest = new ArecontImageExtractionRequest();
                extractionRequest.setExtractionPlan(extractionPlan);
                extractionRequest.setEvidenceProvider(evidenceProvider);
                extractionRequest.setExtractionPlan(extractionPlan);
                extractionRequest.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                evidenceExtractionRequest = extractionRequest;
            }
        } else if (evidenceProvider instanceof VMSGatewayEvidenceProvider) {
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_VIDEO)) {
                VMSGatewayVideoExtractionRequest vmsGatewayVideoExtractionRequest = new VMSGatewayVideoExtractionRequest();
                vmsGatewayVideoExtractionRequest.setExtractionPlan(extractionPlan);
                vmsGatewayVideoExtractionRequest.setEvidenceProvider(evidenceProvider);
                vmsGatewayVideoExtractionRequest.setExtractionPlan(extractionPlan);
                vmsGatewayVideoExtractionRequest.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                vmsGatewayVideoExtractionRequest.setLengthInSecs(evidenceExtractionRequestDTO.getDuration());
                if (((VMSGatewayEvidenceProvider) evidenceProvider).getExtractionPlanToPast()) {
                    vmsGatewayVideoExtractionRequest.setAllowsExtractionPlanToPast(true);
                } else {
                    vmsGatewayVideoExtractionRequest.setAllowsExtractionPlanToPast(false);
                }
                evidenceExtractionRequest = vmsGatewayVideoExtractionRequest;
            }
        } else if (evidenceProvider instanceof NextLevel3EvidenceProvider) {
            log.debug("EvidenceProvider es del tipo NextLevel3EvidenceProvider");
            /* carlos polo 19-oct-2012. Se crea condición para la nueva versión de integración entre EES y NextLevel */
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_VIDEO)) {
                log.debug("Request type del evidenceExtractionRequestDTO es de tipo VIDEO");
                NextLevel3VideoExtractionRequest nextLevel3VideoExtractionRequest = new NextLevel3VideoExtractionRequest();
                nextLevel3VideoExtractionRequest.setExtractionPlan(extractionPlan);
                nextLevel3VideoExtractionRequest.setEvidenceProvider(evidenceProvider);
                nextLevel3VideoExtractionRequest.setExtractionPlan(extractionPlan);
                nextLevel3VideoExtractionRequest.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                nextLevel3VideoExtractionRequest.setLengthInSecs(evidenceExtractionRequestDTO.getDuration());
                evidenceExtractionRequest = nextLevel3VideoExtractionRequest;
            }
        } else if (evidenceProvider instanceof BroadwareHTTPEvidenceProvider) {
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_VIDEO)) {
                BroadwareHTTPVideoExtractionRequest broadwareHTTPVideoExtractionRequest = new BroadwareHTTPVideoExtractionRequest();
                broadwareHTTPVideoExtractionRequest.setExtractionPlan(extractionPlan);
                broadwareHTTPVideoExtractionRequest.setEvidenceProvider(evidenceProvider);
                broadwareHTTPVideoExtractionRequest.setExtractionPlan(extractionPlan);
                broadwareHTTPVideoExtractionRequest.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                broadwareHTTPVideoExtractionRequest.setLengthInSecs(evidenceExtractionRequestDTO.getDuration());
                evidenceExtractionRequest = broadwareHTTPVideoExtractionRequest;
            }
        } else if (evidenceProvider instanceof Cisco3520F_1_2_1EvidenceProvider) {
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_IMAGE)) {
                Cisco3520F_1_2_1ImageExtractionRequest cisco3520ImageExtractionRequest = new Cisco3520F_1_2_1ImageExtractionRequest();
                cisco3520ImageExtractionRequest.setExtractionPlan(extractionPlan);
                cisco3520ImageExtractionRequest.setEvidenceProvider(evidenceProvider);
                cisco3520ImageExtractionRequest.setExtractionPlan(extractionPlan);
                cisco3520ImageExtractionRequest.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                evidenceExtractionRequest = cisco3520ImageExtractionRequest;
            }
        } else if (evidenceProvider instanceof ReadUrlPHPEvidenceProvider) {
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_IMAGE)) {
                ReadUrlPHPImageExtractionRequest request = new ReadUrlPHPImageExtractionRequest();
                request.setExtractionPlan(extractionPlan);
                request.setEvidenceProvider(evidenceProvider);
                request.setExtractionPlan(extractionPlan);
                request.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                evidenceExtractionRequest = request;
            } else if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_XML)) {
                ReadUrlPHPXmlExtractionRequest request = new ReadUrlPHPXmlExtractionRequest();
                request.setExtractionPlan(extractionPlan);
                request.setEvidenceProvider(evidenceProvider);
                request.setExtractionPlan(extractionPlan);
                request.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                evidenceExtractionRequest = request;
            }
        } else if (evidenceProvider instanceof Cisco7EvidenceProvider) {
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_VIDEO)) {
                Cisco7VideoExtractionRequest request = new Cisco7VideoExtractionRequest();
                request.setExtractionPlan(extractionPlan);
                request.setEvidenceProvider(evidenceProvider);
                request.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                request.setLengthInSecs(evidenceExtractionRequestDTO.getDuration());
                evidenceExtractionRequest = request;
            } else if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_IMAGE)) {
                Cisco7ImageExtractionRequest request = new Cisco7ImageExtractionRequest();
                request.setExtractionPlan(extractionPlan);
                request.setEvidenceProvider(evidenceProvider);
                request.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                evidenceExtractionRequest = request;
            }
        } else if (evidenceProvider instanceof CiscoPeopleCountingEvidenceProvider) {
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_XML)) {
                CiscoPeopleCountingExtractionRequest ciscoPeopleCountingExtractionRequest = new CiscoPeopleCountingExtractionRequest();
                ciscoPeopleCountingExtractionRequest.setExtractionPlan(extractionPlan);
                ciscoPeopleCountingExtractionRequest.setEvidenceProvider(evidenceProvider);
                ciscoPeopleCountingExtractionRequest.setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                evidenceExtractionRequest = ciscoPeopleCountingExtractionRequest;
            }
        } else if (evidenceProvider instanceof VadaroPeopleCountingEvidenceProvider) {
            if (evidenceExtractionRequestDTO.getRequestType().equals(REQUESTED_TYPE_XML)) {
                VadaroPeopleCountingExtractionRequest vadaroPeopleCountingExtractionRequest = new VadaroPeopleCountingExtractionRequest();
                vadaroPeopleCountingExtractionRequest.setExtractionPlan(extractionPlan);
                vadaroPeopleCountingExtractionRequest.setEvidenceProvider(evidenceProvider);
                vadaroPeopleCountingExtractionRequest
                        .setRemoteRequestId(evidenceExtractionRequestDTO.getExtractionPlanDetailId());
                evidenceExtractionRequest = vadaroPeopleCountingExtractionRequest;
            }
        } else {
            log.error("Combinacion evidenceProvider:" + evidenceProvider.getClass().getSimpleName() + " evidenceType:"
                    + evidenceExtractionRequestDTO.getRequestType() + " no soportado");
            throw new ScopixWebServiceException("Combinacion evidenceProvider:" + evidenceProvider.getClass().getSimpleName()
                    + " evidenceType:" + evidenceExtractionRequestDTO.getRequestType() + " no soportado");
        }
        log.debug("EvidenceExtractionRequest: " + evidenceExtractionRequest);
        // valores comunes
        if (evidenceExtractionRequest == null) {
            log.error("Combinacion evidenceProvider:" + evidenceProvider.getClass().getSimpleName() + " evidenceType:"
                    + evidenceExtractionRequestDTO.getRequestType() + " no soportado");
            throw new ScopixWebServiceException("Combinacion evidenceProvider:" + evidenceProvider.getClass().getSimpleName()
                    + " evidenceType:" + evidenceExtractionRequestDTO.getRequestType() + " no soportado");
        }
        evidenceExtractionRequest.setDayOfWeek(evidenceExtractionRequestDTO.getDayOfWeek());
        evidenceExtractionRequest.setRequestedTime(reqDate);
        evidenceExtractionRequest.setType(EvidenceRequestType.SCHEDULED);
        evidenceExtractionRequest.setCreationTimestamp(new Date());
        evidenceExtractionRequest.setPriorization(evidenceExtractionRequestDTO.getPriorization());
        evidenceExtractionRequest.setLive(evidenceExtractionRequestDTO.getLive());

        log.debug("end, evidenceExtractionRequest=" + evidenceExtractionRequest + ", isLive: ["
                + evidenceExtractionRequest.getLive() + "]");
        return evidenceExtractionRequest;
    }

    private EvidenceProvider getEvidenceProvider(ExtractionPlan extractionPlan, Integer deviceId) {
        log.debug("start, extractionPlan=" + extractionPlan + ", deviceId=" + deviceId);
        EvidenceProvider selectedEvidenceProvider = null;
        for (EvidenceProvider evidenceProvider : extractionPlan.getExtractionServer().getEvidenceProviders()) {
            if (evidenceProvider.getDeviceId().equals(deviceId)) {
                selectedEvidenceProvider = evidenceProvider;
                break;
            }
        }
        log.debug("end, selectedEvidenceProvider=" + selectedEvidenceProvider);
        return selectedEvidenceProvider;

    }

    private EvidenceProvider convertToBusinessObject(ExtractionServer extractionServer, EvidenceProviderDTO evidenceProviderDTO)
            throws ScopixWebServiceException {
        log.debug("start, extractionServer=" + extractionServer + ", evidenceProviderDTO=" + evidenceProviderDTO);
        EvidenceProvider evidenceProvider = null;
        if (evidenceProviderDTO.getProviderType().equals("Broadware")
                || evidenceProviderDTO.getProviderType().equals("AutomaticBroadware")) {
            evidenceProvider = createBroadwareEvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("PeopleCounting")
                || evidenceProviderDTO.getProviderType().equals("AutomaticPeopleCounting")) {
            evidenceProvider = createPeopleCountingEvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("KumGo")) {
            evidenceProvider = createKumGoEvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("CognimaticsPeopleCounter141")) {
            evidenceProvider = createCognimaticsPeopleCounter141EvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("AXIS_P3301_v1.0_Snapshot")) {
            evidenceProvider = createAxisP3301v01EvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("CognimaticsPeopleCounter212")) {
            evidenceProvider = createCognimaticsPeopleCounter212EvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("CognimaticsPeopleCounter150")) {
            evidenceProvider = createCognimaticsPeopleCounter150EvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("AxisGeneric")) {
            evidenceProvider = createAxisGenericEvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("NextLevel")) {
            evidenceProvider = createNextLevelEvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("Brickcom")) {
            evidenceProvider = createBrickcomEvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("Arecont")) {
            evidenceProvider = createArecontEvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("VMSGateway")) {
            evidenceProvider = createVMSGatewayEvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("NextLevel3")) {
            evidenceProvider = createNextLevel3EvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("BroadwareHTTP")) {
            evidenceProvider = createBroadwareHTTPEvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("Cisco_3520_V1.2.1.Image")) {
            evidenceProvider = createCisco3520EvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("ReadUrlProvider")) {
            evidenceProvider = createReadUrlEvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("Cisco7")) {
            evidenceProvider = createCisco7EvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("CiscoPeopleCounting")) {
            evidenceProvider = createCiscoPeopleCountingEvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("VadaroPeopleCounting")) {
            evidenceProvider = createVadaroPeopleCountingEvidenceProvider(evidenceProviderDTO);
        } else if (evidenceProviderDTO.getProviderType().equals("VadaroProvider")) {
            evidenceProvider = createVadaroEvidenceProvider(evidenceProviderDTO);
        } else {
            throw new ScopixWebServiceException("Tipo Provider no especificado " + evidenceProviderDTO.getProviderType());
        }

        evidenceProvider.setName(evidenceProviderDTO.getDescription());
        evidenceProvider.setDescription(evidenceProviderDTO.getDescription());
        evidenceProvider.setExtractionServer(extractionServer);
        evidenceProvider.setDeviceId(evidenceProviderDTO.getId());
        log.debug("end, evidenceProvider=" + evidenceProvider);
        return evidenceProvider;
    }

    private EvidenceProvider createAxisP3301v01EvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.debug("start, evidenceProviderDTO=" + evidenceProviderDTO);
        AxisP3301EvidenceProvider evidenceProvider = new AxisP3301EvidenceProvider();
        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();
        evidenceProvider.setIpAddress(definitionData.get("ip_address"));
        evidenceProvider.setProtocol(definitionData.get("protocol"));
        evidenceProvider.setPort(definitionData.get("port"));
        evidenceProvider.setUserName(definitionData.get("user"));
        evidenceProvider.setPassword(definitionData.get("pass"));
        evidenceProvider.setResolution(definitionData.get("resolution"));
        log.debug("end, evidenceProvider=" + evidenceProvider);
        return evidenceProvider;
    }

    private EvidenceProvider createBroadwareEvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.debug("start, evidenceProviderDTO=" + evidenceProviderDTO);
        BroadwareEvidenceProvider evidenceProvider = new BroadwareEvidenceProvider();
        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();
        evidenceProvider.setIpAddress(definitionData.get("ip_address"));
        evidenceProvider.setLoopName(definitionData.get("loop_name"));
        evidenceProvider.setUniqueDeviceId(definitionData.get("unique_id"));
        log.debug("end, evidenceProvider=" + evidenceProvider);
        return evidenceProvider;
    }

    private EvidenceProvider createPeopleCountingEvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.debug("start, evidenceProviderDTO=" + evidenceProviderDTO);
        PeopleCountingEvidenceProvider evidenceProvider = new PeopleCountingEvidenceProvider();
        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();
        evidenceProvider.setIpAddress(definitionData.get("ip_address"));
        evidenceProvider.setUserName(definitionData.get("user"));
        evidenceProvider.setPassword(definitionData.get("pass"));
        evidenceProvider.setUniqueDeviceId(definitionData.get("unique_id"));
        log.debug("end, evidenceProvider=" + evidenceProvider);
        return evidenceProvider;
    }

    private EvidenceProvider createCognimaticsPeopleCounter141EvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.debug("start, evidenceProviderDTO=" + evidenceProviderDTO);
        CognimaticsPeopleCounter141EvidenceProvider evidenceProvider = new CognimaticsPeopleCounter141EvidenceProvider();
        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();
        evidenceProvider.setIpAddress(definitionData.get("ip_address"));
        evidenceProvider.setUserName(definitionData.get("user"));
        evidenceProvider.setPassword(definitionData.get("pass"));
        evidenceProvider.setProtocol(definitionData.get("protocol"));
        evidenceProvider.setPort(definitionData.get("port"));
        evidenceProvider.setUniqueDeviceId(definitionData.get("unique_id"));
        log.debug("end, evidenceProvider=" + evidenceProvider);
        return evidenceProvider;
    }

    private EvidenceProvider createCognimaticsPeopleCounter212EvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.debug("start, evidenceProviderDTO=" + evidenceProviderDTO);
        CognimaticsPeopleCounter212EvidenceProvider evidenceProvider = new CognimaticsPeopleCounter212EvidenceProvider();
        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();
        evidenceProvider.setIpAddress(definitionData.get("ip_address"));
        evidenceProvider.setUserName(definitionData.get("user"));
        evidenceProvider.setPassword(definitionData.get("pass"));
        evidenceProvider.setProtocol(definitionData.get("protocol"));
        evidenceProvider.setPort(definitionData.get("port"));
        evidenceProvider.setUniqueDeviceId(definitionData.get("unique_id"));
        log.debug("end, evidenceProvider=" + evidenceProvider);
        return evidenceProvider;
    }

    private EvidenceProvider createKumGoEvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.debug("start, evidenceProviderDTO=" + evidenceProviderDTO);
        KumGoEvidenceProvider evidenceProvider = new KumGoEvidenceProvider();
        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();
        evidenceProvider.setIpAddress(definitionData.get("ip_address"));
        evidenceProvider.setUserName(definitionData.get("user"));
        evidenceProvider.setPassword(definitionData.get("pass"));
        evidenceProvider.setProtocol(definitionData.get("protocol"));
        evidenceProvider.setPort(definitionData.get("port"));
        log.debug("end, evidenceProvider=" + evidenceProvider);
        return evidenceProvider;
    }

    private List<SituationRequest> convertNewSituationRequestToBusinessObject(ExtractionPlan extractionPlan,
            List<SituationRequestDTO> situationRequestDTOs, List<EvidenceProvider> evidenceProviders) {
        log.debug("start, extractionPlan=" + extractionPlan + ", situationRequestDTOs=" + situationRequestDTOs
                + ", evidenceProviders=" + evidenceProviders);
        List<SituationRequest> situationRequests = new ArrayList<SituationRequest>();
        for (SituationRequestDTO situationRequestDTO : situationRequestDTOs) {
            SituationRequest situationRequest = new SituationRequest();
            // Se eliminan del situationRequest ya que pasan al SituationRequestRange
            // situationRequest.setDuration(situationRequestDTO.getDuration());
            // situationRequest.setFrecuency(situationRequestDTO.getFrecuency());
            situationRequest.setRandomCamera(situationRequestDTO.getRandomCamera());
            situationRequest.setPriorization(situationRequestDTO.getPriorization());
            situationRequest.setSituationTemplateId(situationRequestDTO.getSituationTemplateId());
            situationRequest.setExtractionPlan(extractionPlan);

            situationRequest.setSituationSensors(convertSituationSensorsToBusinessObject(
                    situationRequestDTO.getSituationSensors(), situationRequest));
            // agregar relacion entre provider y situation
            situationRequest.setMetricRequests(convertMetricRequestToBusinessObject(situationRequest,
                    situationRequestDTO.getMetricRequestDTOs(), evidenceProviders));
            situationRequest.setSituationRequestRanges(convertSituatioRequestRangeToBusinessObject(situationRequest,
                    situationRequestDTO.getSituationRequestRangeDTOs()));

            situationRequests.add(situationRequest);
        }
        log.debug("end, situationRequests=" + situationRequests);
        return situationRequests;
    }

    private List<SituationSensor> convertSituationSensorsToBusinessObject(List<SituationSensorDTO> situationSensorDTOs,
            SituationRequest situationRequest) {
        log.debug("start, situationSensorDTOs=" + situationSensorDTOs + ", situationRequest=" + situationRequest);
        List<SituationSensor> situationSensors = new ArrayList<SituationSensor>();
        for (SituationSensorDTO dto : situationSensorDTOs) {
            SituationSensor situationSensor = new SituationSensor();
            situationSensor.setSensorId(dto.getId());
            situationSensor.setName(dto.getName());
            situationSensor.setDescription(dto.getDescription());
            situationSensor.setSituationRequest(situationRequest);
            situationSensors.add(situationSensor);
        }

        log.debug("end, situationSensors=" + situationSensors);
        return situationSensors;
    }

    private List<StoreTime> getStoreTimes(ExtractionPlan extractionPlan, List<StoreTimeDTO> storeTimeDTOs) {
        log.debug("start, extractionPlan=" + extractionPlan + ", storeTimeDTOs=" + storeTimeDTOs);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        List<StoreTime> storeTimes = new ArrayList<StoreTime>();
        for (StoreTimeDTO storeTimeDTO : storeTimeDTOs) {
            try {
                StoreTime storeTime = new StoreTime();
                storeTime.setDayOfWeek(storeTimeDTO.getDay());
                storeTime.setOpenHour(sdf.parse(storeTimeDTO.getOpenHour()));
                storeTime.setEndHour(sdf.parse(storeTimeDTO.getCloseHour()));
                storeTime.setExtractionPlan(extractionPlan);
                storeTimes.add(storeTime);
            } catch (ParseException ex) {
                log.debug("error: " + ex.getMessage());
            }
        }
        log.debug("end, storeTimes=" + storeTimes);
        return storeTimes;
    }

    private List<MetricRequest> convertMetricRequestToBusinessObject(SituationRequest situationRequest,
            List<MetricRequestDTO> metricRequestDTOs, List<EvidenceProvider> evidenceProviders) {
        log.debug("start, situationRequest=" + situationRequest + ", metricRequestDTOs=" + metricRequestDTOs
                + ", evidenceProviders=" + evidenceProviders);
        List<MetricRequest> metricRequests = new ArrayList<MetricRequest>();
        for (MetricRequestDTO metricRequestDTO : metricRequestDTOs) {
            MetricRequest metricRequest = new MetricRequest();
            metricRequest.setMetricTemplateId(metricRequestDTO.getMetricTemplateId());
            metricRequest.setSituationRequest(situationRequest);
            metricRequest.setEvidenceProviderRequests(convertEvidenceProviderRequestToBusinessObject(metricRequest,
                    metricRequestDTO.getEvidenceProviderRequestDTOs(), evidenceProviders));
            metricRequests.add(metricRequest);
        }
        log.debug("end, metricRequests=" + metricRequests);
        return metricRequests;
    }

    private List<SituationRequestRange> convertSituatioRequestRangeToBusinessObject(SituationRequest situationRequest,
            List<SituationRequestRangeDTO> situationRequestRangeDTOs) {
        log.info("start");
        String[] parsePatterns = new String[] { "HHmm" };
        List<SituationRequestRange> ret = new ArrayList<SituationRequestRange>();

        try {
            for (SituationRequestRangeDTO dto : situationRequestRangeDTOs) {
                SituationRequestRange range = new SituationRequestRange();
                range.setSituationRequest(situationRequest);
                range.setDayOfWeek(dto.getDayOfWeek());
                range.setDuration(dto.getDuration());
                range.setFrecuency(dto.getFrecuency());
                range.setInitialTime(DateUtils.parseDate(dto.getInitialTime(), parsePatterns));
                range.setEndTime(DateUtils.parseDate(dto.getEndTime(), parsePatterns));
                // se coloca el nuevo tipo
                try {
                    range.setRangeType(SituationRequestRangeType.valueOf(dto.getRangeType()));
                } catch (IllegalArgumentException e) { // se asume que no viene
                    log.warn("Tipo no definido " + e);
                    range.setRangeType(null);
                }
                range.setSamples(dto.getSamples());
                range.setSituationExtractionRequests(convertSituationExtractionRequestToBusinessObject(
                        dto.getSituationExtractionRequestDTOs(), range));
                ret.add(range);
            }
        } catch (ParseException e) {
            log.error("Error generando situationRequestRanges " + e, e);
        }
        log.info("end, situationRequestRanges=" + ret.size());
        return ret;
    }

    private List<SituationExtractionRequest> convertSituationExtractionRequestToBusinessObject(
            List<SituationExtractionRequestDTO> situationExtractionRequestDTOs, SituationRequestRange range) {
        log.info("start");
        String[] parsePatterns = new String[] { "HHmm" };
        List<SituationExtractionRequest> ret = new ArrayList<SituationExtractionRequest>();

        try {
            for (SituationExtractionRequestDTO dto : situationExtractionRequestDTOs) {
                SituationExtractionRequest extractionRequest = new SituationExtractionRequest();
                extractionRequest.setSituationRequestRange(range);
                extractionRequest.setTimeSample(DateUtils.parseDate(dto.getTimeSample(), parsePatterns));
                ret.add(extractionRequest);
            }
        } catch (ParseException e) {
            log.error("Error generando situationExtractionRequests " + e, e);
        }
        log.info("end, situationExtractionRequests=" + ret.size());
        return ret;
    }

    private List<EvidenceProviderRequest> convertEvidenceProviderRequestToBusinessObject(MetricRequest metricRequest,
            List<EvidenceProviderRequestDTO> evidenceProviderDTOs, List<EvidenceProvider> evidenceProviders) {
        log.debug("start, metricRequest=" + metricRequest + ", evidenceProviderDTOs=" + evidenceProviderDTOs
                + ", evidenceProviders=" + evidenceProviders);
        List<EvidenceProviderRequest> evidenceProviderRequests = new ArrayList<EvidenceProviderRequest>();
        Collections.sort(evidenceProviders, EvidenceProvider.deviceIdComparator);
        for (EvidenceProviderRequestDTO evidenceProviderRequestDTO : evidenceProviderDTOs) {
            EvidenceProviderRequest evidenceProviderRequest = new EvidenceProviderRequest();
            evidenceProviderRequest.setDeviceId(evidenceProviderRequestDTO.getDeviceId());
            evidenceProviderRequest.setMetricRequest(metricRequest);
            // agregamos al evidenceProviderRequest la situacion a la cual pertenece para mantener el Random
            evidenceProviderRequest.setSituationRequest(metricRequest.getSituationRequest());

            EvidenceProvider evidenceProvider = new EvidenceProvider();
            evidenceProvider.setDeviceId(evidenceProviderRequestDTO.getDeviceId());
            int index = Collections.binarySearch(evidenceProviders, evidenceProvider, EvidenceProvider.deviceIdComparator);
            if (index >= 0) {
                evidenceProviderRequest.setEvidenceProvider(evidenceProviders.get(index));
                evidenceProviderRequest.setUniqueDeviceId(evidenceProviders.get(index).getUniqueDeviceId());
            }
            evidenceProviderRequests.add(evidenceProviderRequest);
        }
        log.debug("end, evidenceProviderRequests=" + evidenceProviderRequests);
        return evidenceProviderRequests;
    }

    @Override
    public List<Integer> extractionPlanToPast(String date, String storeName) throws ScopixWebServiceException {
        log.debug("start, date=" + date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Integer> data = null;
        try {
            ExtractionManager extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
            data = extractionManager.getPastEvidence(sdf.parse(date), storeName);
        } catch (ParseException e) {
            log.error(e);
            throw new ScopixWebServiceException(e);
        } catch (ScopixException e) {
            log.error(e);
            throw new ScopixWebServiceException(e);
        }
        log.debug("end, data=" + data);
        return data;
    }

    private EvidenceProvider createCognimaticsPeopleCounter150EvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.debug("start, evidenceProviderDTO=" + evidenceProviderDTO);
        CognimaticsPeopleCounter150EvidenceProvider evidenceProvider = new CognimaticsPeopleCounter150EvidenceProvider();
        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();
        evidenceProvider.setIpAddress(definitionData.get("ip_address"));
        evidenceProvider.setUserName(definitionData.get("user"));
        evidenceProvider.setPassword(definitionData.get("pass"));
        evidenceProvider.setProtocol(definitionData.get("protocol"));
        evidenceProvider.setPort(definitionData.get("port"));
        evidenceProvider.setUniqueDeviceId(definitionData.get("unique_id"));
        log.debug("end, evidenceProvider=" + evidenceProvider);
        return evidenceProvider;
    }

    private EvidenceProvider createAxisGenericEvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.debug("start, evidenceProviderDTO=" + evidenceProviderDTO);
        AxisGenericEvidenceProvider evidenceProvider = new AxisGenericEvidenceProvider();
        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();
        evidenceProvider.setIpAddress(definitionData.get("ip_address"));
        evidenceProvider.setUserName(definitionData.get("user"));
        evidenceProvider.setPassword(definitionData.get("pass"));
        evidenceProvider.setProtocol(definitionData.get("protocol"));
        evidenceProvider.setPort(definitionData.get("port"));
        evidenceProvider.setUniqueDeviceId(definitionData.get("unique_id"));
        // unicos para este tipo de provider
        evidenceProvider.setFramerate(Integer.parseInt(definitionData.get("framerate")));
        evidenceProvider.setResolution(definitionData.get("resolution"));

        log.debug("end, evidenceProvider=" + evidenceProvider);
        return evidenceProvider;

    }

    private EvidenceProvider createNextLevelEvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.info("start, evidenceProviderDTO=" + evidenceProviderDTO);
        NextLevelEvidenceProvider evidenceProvider = new NextLevelEvidenceProvider();
        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();
        evidenceProvider.setUniqueDeviceId(definitionData.get("unique_id"));

        evidenceProvider.setIpAddress(definitionData.get("ip_address"));
        evidenceProvider.setProtocol(definitionData.get("protocol"));
        evidenceProvider.setPort(definitionData.get("port"));
        evidenceProvider.setUserName(definitionData.get("user"));
        evidenceProvider.setPassword(definitionData.get("pass"));

        evidenceProvider.setUuid(definitionData.get("uuid"));
        log.info("end, evidenceProvider=" + evidenceProvider);
        return evidenceProvider;
    }

    private EvidenceProvider createNextLevel3EvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.info("start, evidenceProviderDTO=" + evidenceProviderDTO);
        NextLevel3EvidenceProvider evidenceProvider = new NextLevel3EvidenceProvider();
        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();
        evidenceProvider.setUniqueDeviceId(definitionData.get("unique_id"));

        evidenceProvider.setIpAddress(definitionData.get("ip_address"));
        evidenceProvider.setProtocol(definitionData.get("protocol"));
        evidenceProvider.setPort(definitionData.get("port"));
        evidenceProvider.setUserName(definitionData.get("user"));
        evidenceProvider.setPassword(definitionData.get("pass"));

        evidenceProvider.setUuid(definitionData.get("uuid"));
        log.info("end, evidenceProvider=" + evidenceProvider);
        return evidenceProvider;
    }

    private EvidenceProvider createBrickcomEvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.info("start, evidenceProviderDTO=" + evidenceProviderDTO);
        BrickcomEvidenceProvider evidenceProvider = new BrickcomEvidenceProvider();
        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();
        evidenceProvider.setIpAddress(definitionData.get("ip_address"));
        evidenceProvider.setProtocol(definitionData.get("protocol"));
        evidenceProvider.setPort(definitionData.get("port"));
        evidenceProvider.setUniqueDeviceId(definitionData.get("unique_id"));
        evidenceProvider.setUserName(definitionData.get("user"));
        evidenceProvider.setPassword(definitionData.get("pass"));
        log.info("end, evidenceProvider=" + evidenceProvider);
        return evidenceProvider;
    }

    private EvidenceProvider createArecontEvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.info("start, evidenceProviderDTO=" + evidenceProviderDTO);
        ArecontEvidenceProvider evidenceProvider = new ArecontEvidenceProvider();
        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();
        evidenceProvider.setIpAddress(definitionData.get("ip_address"));
        evidenceProvider.setProtocol(definitionData.get("protocol"));
        evidenceProvider.setPort(definitionData.get("port"));
        evidenceProvider.setUniqueDeviceId(definitionData.get("unique_id"));
        evidenceProvider.setUserName(definitionData.get("user"));
        evidenceProvider.setPassword(definitionData.get("pass"));
        log.info("end, evidenceProvider=" + evidenceProvider);
        return evidenceProvider;
    }

    private EvidenceProvider createVMSGatewayEvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.info("start, evidenceProviderDTO=" + evidenceProviderDTO);
        VMSGatewayEvidenceProvider evidenceProvider = new VMSGatewayEvidenceProvider();
        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();
        evidenceProvider.setIpAddress(definitionData.get("vms_gw_ip"));
        evidenceProvider.setProvider(definitionData.get("provider"));
        evidenceProvider.setProviderType(definitionData.get("provider_type"));
        // se espera el valor true de lo contrario se asume false
        evidenceProvider.setExtractionPlanToPast(definitionData.get("extraction_plan_to_past") != null ? Boolean
                .valueOf(definitionData.get("extraction_plan_to_past")) : false);

        // se agregan por uso de otro protocolo o puerto
        evidenceProvider.setProtocol(definitionData.get("protocol"));
        evidenceProvider.setPort(definitionData.get("port"));

        log.info("end");
        return evidenceProvider;
    }

    private EvidenceProvider createBroadwareHTTPEvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.debug("start, evidenceProviderDTO=" + evidenceProviderDTO);
        BroadwareHTTPEvidenceProvider evidenceProvider = new BroadwareHTTPEvidenceProvider();
        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();
        evidenceProvider.setIpAddress(definitionData.get("ip_address"));
        evidenceProvider.setLoopName(definitionData.get("loop_name"));
        evidenceProvider.setUniqueDeviceId(definitionData.get("unique_id"));
        evidenceProvider.setDescription(evidenceProviderDTO.getDescription());
        log.debug("end, evidenceProvider=" + evidenceProvider);
        return evidenceProvider;
    }

    /**
     * Nelson Rubio 2013-06-03
     */
    private EvidenceProvider createCisco3520EvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.info("start, evidenceProviderDTO=" + evidenceProviderDTO);
        Cisco3520F_1_2_1EvidenceProvider evidenceProvider = new Cisco3520F_1_2_1EvidenceProvider();
        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();
        evidenceProvider.setIpAddress(definitionData.get("ip_address"));
        evidenceProvider.setProtocol(definitionData.get("protocol"));
        evidenceProvider.setPort(definitionData.get("port"));
        evidenceProvider.setUniqueDeviceId(definitionData.get("unique_id"));
        evidenceProvider.setUserName(definitionData.get("user"));
        evidenceProvider.setPassword(definitionData.get("pass"));
        log.info("end, evidenceProvider=" + evidenceProvider);
        return evidenceProvider;
    }

    private EvidenceProvider createReadUrlEvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.info("start, evidenceProviderDTO=" + evidenceProviderDTO);
        ReadUrlPHPEvidenceProvider evidenceProvider = new ReadUrlPHPEvidenceProvider();
        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();
        evidenceProvider.setIpAddress(definitionData.get("ip_address"));
        evidenceProvider.setProtocol(definitionData.get("protocol"));
        evidenceProvider.setPort(definitionData.get("port"));
        evidenceProvider.setUniqueDeviceId(definitionData.get("unique_id"));
        evidenceProvider.setUserName(definitionData.get("user"));
        evidenceProvider.setPassword(definitionData.get("pass"));
        evidenceProvider.setQuery(definitionData.get("query"));
        log.info("end, evidenceProvider=" + evidenceProvider);
        return evidenceProvider;
    }

    private EvidenceProvider createCisco7EvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.info("start, evidenceProviderDTO: [" + evidenceProviderDTO + "]");
        Cisco7EvidenceProvider evidenceProvider = new Cisco7EvidenceProvider();
        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();

        evidenceProvider.setVsomIpAddress(definitionData.get("vsom_ip_address"));
        evidenceProvider.setVsomUser(definitionData.get("vsom_username"));
        evidenceProvider.setVsomPass(definitionData.get("vsom_password"));
        evidenceProvider.setVsomDomain(definitionData.get("vsom_domain"));
        evidenceProvider.setCameraName(definitionData.get("camera_name"));
        evidenceProvider.setVsomProtocol(definitionData.get("vsom_protocol"));
        evidenceProvider.setVsomPort(definitionData.get("vsom_port"));
        evidenceProvider.setMediaServerIp(definitionData.get("media_server_ip"));
        evidenceProvider.setMediaServerProtocol(definitionData.get("media_server_protocol"));
        evidenceProvider.setMediaServerPort(definitionData.get("media_server_port"));
        evidenceProvider.setUuid(definitionData.get("uuid"));

        log.info("end, evidenceProvider: [" + evidenceProvider + "]");
        return evidenceProvider;
    }

    private EvidenceProvider createCiscoPeopleCountingEvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.info("start, evidenceProviderDTO: [" + evidenceProviderDTO + "]");
        CiscoPeopleCountingEvidenceProvider evidenceProvider = new CiscoPeopleCountingEvidenceProvider();

        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();

        evidenceProvider.setPort(definitionData.get("port"));
        evidenceProvider.setUserName(definitionData.get("user"));
        evidenceProvider.setPassword(definitionData.get("pass"));
        evidenceProvider.setProtocol(definitionData.get("protocol"));
        evidenceProvider.setIpAddress(definitionData.get("ip_address"));
        log.debug("end, evidenceProvider: [" + evidenceProvider + "]");
        return evidenceProvider;
    }

    private EvidenceProvider createVadaroPeopleCountingEvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.info("start, evidenceProviderDTO: [" + evidenceProviderDTO + "]");
        VadaroPeopleCountingEvidenceProvider evidenceProvider = new VadaroPeopleCountingEvidenceProvider();

        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();

        evidenceProvider.setPort(definitionData.get("port"));
        evidenceProvider.setUserName(definitionData.get("user"));
        evidenceProvider.setPassword(definitionData.get("pass"));
        evidenceProvider.setProtocol(definitionData.get("protocol"));
        evidenceProvider.setIpAddress(definitionData.get("ip_address"));

        log.debug("end, evidenceProvider: [" + evidenceProvider + "]");
        return evidenceProvider;
    }

    private EvidenceProvider createVadaroEvidenceProvider(EvidenceProviderDTO evidenceProviderDTO) {
        log.info("start, evidenceProviderDTO: [" + evidenceProviderDTO + "]");
        VadaroEvidenceProvider evidenceProvider = new VadaroEvidenceProvider();

        Map<String, String> definitionData = evidenceProviderDTO.getDefinitionData();

        evidenceProvider.setPort(definitionData.get("port"));
        evidenceProvider.setProtocol(definitionData.get("protocol"));
        evidenceProvider.setIpAddress(definitionData.get("ip_address"));
        evidenceProvider.setUniqueDeviceId(evidenceProviderDTO.getDescription());
        evidenceProvider.setLoopName(definitionData.get("queue"));

        log.debug("end, evidenceProvider: [" + evidenceProvider + "]");
        return evidenceProvider;
    }

}
