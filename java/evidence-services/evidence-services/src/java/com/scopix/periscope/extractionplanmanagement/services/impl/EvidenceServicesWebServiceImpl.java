/*
 * 
 * Copyright (c)2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * EvidenceServicesWebServiceImpl.java
 * 
 * Created on 18-06-2008, 01:08:27 PM
 */
package com.scopix.periscope.extractionplanmanagement.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceExtractionServicesServerDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceProviderDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceProviderRequestDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceRequestDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.ExtractionPlanDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.ExtractionPlanDetailDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.MetricRequestDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.SituationExtractionRequestDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.SituationRequestDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.SituationRequestRangeDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.SituationSensorDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.StoreTimeDTO;
import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceProvider;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceProviderRequest;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceRequest;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlan;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlanDetail;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionServicesServersManager;
import com.scopix.periscope.extractionservicesserversmanagement.MetricRequest;
import com.scopix.periscope.extractionservicesserversmanagement.SituationExtractionRequest;
import com.scopix.periscope.extractionservicesserversmanagement.SituationRequest;
import com.scopix.periscope.extractionservicesserversmanagement.SituationRequestRange;
import com.scopix.periscope.extractionservicesserversmanagement.SituationSensor;
import com.scopix.periscope.extractionservicesserversmanagement.StoreTime;
import com.scopix.periscope.extractionservicesserversmanagement.services.webservices.EvidenceServicesWebService;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author marko.perich
 * @version 1.0.0
 */
// @CustomWebService(serviceClass = EvidenceServicesWebService.class)
@WebService(endpointInterface = "com.scopix.periscope.extractionservicesserversmanagement.services.webservices.EvidenceServicesWebService")
@SpringBean(rootClass = EvidenceServicesWebService.class)
public class EvidenceServicesWebServiceImpl implements EvidenceServicesWebService {

    private static Logger log = Logger.getLogger(EvidenceServicesWebServiceImpl.class);

    /**
     * return extractionServerManager instance
     * 
     * @return ExtractionServicesServersManager @
     */
    protected ExtractionServicesServersManager getExtractionServicesServersManager() {

        return SpringSupport.getInstance().findBeanByClassName(ExtractionServicesServersManager.class);

    }

    /**
     * accept a new extraction plan for the servers managed for this evidence services server
     *
     * @param evidenceExtractionServicesServerDTOs
     * @throws ScopixWebServiceException
     */
    @Override
    public void newExtractionPlan(List<EvidenceExtractionServicesServerDTO> evidenceExtractionServicesServerDTOs)
            throws ScopixWebServiceException {
        log.info("start");
        // convert DTO to BO and invoke manager
        try {
            List<EvidenceExtractionServicesServer> evidenceExtractionServicesServers = new ArrayList<EvidenceExtractionServicesServer>();
            for (EvidenceExtractionServicesServerDTO evidenceExtractionServicesServerDTO : evidenceExtractionServicesServerDTOs) {
                evidenceExtractionServicesServers.add(convertToBusinessObject(evidenceExtractionServicesServerDTO));
            }
            getExtractionServicesServersManager().newExtractionPlan(evidenceExtractionServicesServers);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
    }

    /**
     * Converts a EvidenceExtractionServicesServerDTO into a EvidenceExtractionServicesServer
     *
     * @param evidenceExtractionServicesServerDTO
     * @return
     */
    public EvidenceExtractionServicesServer convertToBusinessObject(
            EvidenceExtractionServicesServerDTO evidenceExtractionServicesServerDTO) {
        log.info("start, evidenceExtractionServicesServerDTO=" + evidenceExtractionServicesServerDTO.getName());
        EvidenceExtractionServicesServer evidenceExtractionServicesServer = new EvidenceExtractionServicesServer();
        evidenceExtractionServicesServer.setName(evidenceExtractionServicesServerDTO.getName());
        evidenceExtractionServicesServer.setUrl(evidenceExtractionServicesServerDTO.getUrl());
        evidenceExtractionServicesServer.setServerId(evidenceExtractionServicesServerDTO.getServerId());
        evidenceExtractionServicesServer.setIdAtBusinessServices(evidenceExtractionServicesServerDTO.getIdAtBusinessServices());
        evidenceExtractionServicesServer.setSshAddress(evidenceExtractionServicesServerDTO.getSshAddress());
        evidenceExtractionServicesServer.setSshPort(evidenceExtractionServicesServerDTO.getSshPort());
        evidenceExtractionServicesServer.setSshUser(evidenceExtractionServicesServerDTO.getSshUser());
        evidenceExtractionServicesServer.setSshPassword(evidenceExtractionServicesServerDTO.getSshPassword());
        evidenceExtractionServicesServer.setSshLocalTunnelPort(evidenceExtractionServicesServerDTO.getSshLocalTunnelPort());
        evidenceExtractionServicesServer.setSshRemoteTunnelPort(evidenceExtractionServicesServerDTO.getSshRemoteTunnelPort());
        
        evidenceExtractionServicesServer.setUseTunnel(evidenceExtractionServicesServerDTO.isUseTunnel());
        // convert the evidence providers
        evidenceExtractionServicesServer.setEvidenceProviders(convertToBusinessObject(evidenceExtractionServicesServer,
                evidenceExtractionServicesServerDTO.getEvidenceProviderDTOs()));
        // convert the extraction plan
        evidenceExtractionServicesServer.addExtractionPlan(convertToBusinessObject(evidenceExtractionServicesServer,
                evidenceExtractionServicesServerDTO.getExtractionPlanDTO(),
                evidenceExtractionServicesServer.getEvidenceProviders()));
        log.info("end, evidenceExtractionServicesServer=" + evidenceExtractionServicesServer.getName());
        return evidenceExtractionServicesServer;
    }

    private ExtractionPlan convertToBusinessObject(EvidenceExtractionServicesServer evidenceExtractionServicesServer,
            ExtractionPlanDTO extractionPlanDTO, List<EvidenceProvider> evidenceProviders) {
        log.info("start, evidenceExtractionServicesServer=" + evidenceExtractionServicesServer + ", extractionPlanDTO="
                + extractionPlanDTO.getStoreName() + ", " + " evidenceProviders=" + evidenceProviders.size());
        ExtractionPlan extractionPlan = new ExtractionPlan();
        // se agregan nuevos campos al extraction plan
        extractionPlan.setStoreName(extractionPlanDTO.getStoreName());
        extractionPlan.setStoreId(extractionPlanDTO.getStoreId());
        extractionPlan.setTimeZoneId(extractionPlanDTO.getTimeZone());

        extractionPlan.setEvidenceExtractionServicesServer(evidenceExtractionServicesServer);
        extractionPlan.setStoreTimes(this.getStoreTimes(extractionPlan, extractionPlanDTO.getStoreTimeDTOs()));
        extractionPlan.setExtractionPlanDetails(convertToBusinessObject(extractionPlan,
                extractionPlanDTO.getExtractionPlanDetails()));
        extractionPlan.setSituationRequests(convertNewSituationRequestToBusinessObject(extractionPlan,
                extractionPlanDTO.getSituationRequestDTOs(), evidenceProviders));
        log.info("end, extractionPlan=" + extractionPlan);
        return extractionPlan;
    }

    private List<ExtractionPlanDetail> convertToBusinessObject(ExtractionPlan extractionPlan,
            List<ExtractionPlanDetailDTO> extractionPlanDetailDTOs) {
        log.info("start, extractionPlan=" + extractionPlan + ", extractionPlanDetailDTOs=" + extractionPlanDetailDTOs.size());
        List<ExtractionPlanDetail> extractionPlanDetails = new ArrayList<ExtractionPlanDetail>();
        for (ExtractionPlanDetailDTO extractionPlanDetailDTO : extractionPlanDetailDTOs) {
            ExtractionPlanDetail extractionPlanDetail = new ExtractionPlanDetail();
            extractionPlanDetail.setEvidenceRequests(convertToBusinessObject(extractionPlanDetail,
                    extractionPlanDetailDTO.getEvidenceRequestDTOs()));
            extractionPlanDetail.setExtractionPlan(extractionPlan);
            extractionPlanDetails.add(extractionPlanDetail);

        }
        log.info("end, extractionPlanDetails=" + extractionPlanDetails.size());
        return extractionPlanDetails;
    }

    private List<EvidenceRequest> convertToBusinessObject(ExtractionPlanDetail extractionPlanDetail,
            List<EvidenceRequestDTO> evidenceRequestDTOs) {
        log.info("start, extractionPlanDetail=" + extractionPlanDetail + ", evidenceRequestDTOs=" + evidenceRequestDTOs.size());
        List<EvidenceRequest> evidenceRequests = new ArrayList<EvidenceRequest>();
        for (EvidenceRequestDTO evidenceRequestDTO : evidenceRequestDTOs) {
            EvidenceRequest evidenceRequest = new EvidenceRequest();
            evidenceRequest.setExtractionPlanDetail(extractionPlanDetail);
            evidenceRequest.setBusinessServicesRequestId(evidenceRequestDTO.getBusinessServicesRequestId());
            evidenceRequest.setRequestType(evidenceRequestDTO.getRequestType());
            evidenceRequest.setRequestedTime(evidenceRequestDTO.getRequestedTime());
            evidenceRequest.setDeviceId(evidenceRequestDTO.getDeviceId());
            evidenceRequest.setDuration(evidenceRequestDTO.getDuration());
            evidenceRequest.setDayOfWeek(evidenceRequestDTO.getDayOfWeek());
            evidenceRequest.setPriorization(evidenceRequestDTO.getPriorization());
            evidenceRequest.setType(EvidenceRequestType.SCHEDULED);

            log.debug("evidenceRequestDTO isLive: [" + evidenceRequestDTO.getLive() + "]");
            evidenceRequest.setLive(evidenceRequestDTO.getLive());
            evidenceRequests.add(evidenceRequest);
        }
        log.info("end, evidenceRequests=" + evidenceRequests.size());
        return evidenceRequests;
    }

    private List<EvidenceProvider> convertToBusinessObject(EvidenceExtractionServicesServer evidenceExtractionServicesServer,
            List<EvidenceProviderDTO> evidenceProviderDTOs) {
        log.info("start, evidenceExtractionServicesServer=" + evidenceExtractionServicesServer + ", evidenceProviderDTOs="
                + evidenceProviderDTOs.size());
        List<EvidenceProvider> evidenceProviders = new ArrayList<EvidenceProvider>();

        for (EvidenceProviderDTO evidenceProviderDTO : evidenceProviderDTOs) {
            EvidenceProvider evidenceProvider = new EvidenceProvider();
            evidenceProvider.setDescription(evidenceProviderDTO.getDescription());
            evidenceProvider.setEvidenceExtractionServicesServer(evidenceExtractionServicesServer);
            evidenceProvider.setDefinitionData(evidenceProviderDTO.getDefinitionData());
            evidenceProvider.setProviderType(evidenceProviderDTO.getProviderType());
            evidenceProvider.setDeviceId(evidenceProviderDTO.getId());
            evidenceProviders.add(evidenceProvider);
        }
        log.info("end, evidenceProviders=" + evidenceProviders.size());
        return evidenceProviders;
    }

    private List<SituationRequest> convertNewSituationRequestToBusinessObject(ExtractionPlan extractionPlan,
            List<SituationRequestDTO> situationRequestDTOs, List<EvidenceProvider> evidenceProviders) {
        log.info("start, extractionPlan=" + extractionPlan + ", situationRequestDTOs=" + situationRequestDTOs.size()
                + ", evidenceProviders=" + evidenceProviders.size());
        List<SituationRequest> situationRequests = new ArrayList<SituationRequest>();
        for (SituationRequestDTO situationRequestDTO : situationRequestDTOs) {
            SituationRequest situationRequest = new SituationRequest();
            // sacamos estos valores ya que pasan a ser parte de los rangos de la situacion
            // situationRequest.setDuration(situationRequestDTO.getDuration());
            // situationRequest.setFrecuency(situationRequestDTO.getFrecuency());
            situationRequest.setRandomCamera(situationRequestDTO.getRandonCamera());
            situationRequest.setSituationTemplateId(situationRequestDTO.getSituationTemplateId());
            situationRequest.setPriorization(situationRequestDTO.getPriorization());
            situationRequest.setExtractionPlan(extractionPlan);
            situationRequest.setSituationSensors(convertSituationSensorToBusinessObject(
                    situationRequestDTO.getSituationSensors(), situationRequest));
            situationRequest.setMetricRequests(convertMetricRequestToBusinessObject(situationRequest,
                    situationRequestDTO.getMetricRequestDTOs(), evidenceProviders));
            // nueva estructura para manejo de randomCamera
            situationRequest.setSituationRequestRanges(convertSituationRangeToBusinessObject(situationRequest,
                    situationRequestDTO.getSituationRequestRangeDTOs()));
            situationRequests.add(situationRequest);
        }
        log.info("end, situationRequests=" + situationRequests.size());
        return situationRequests;
    }

    private List<SituationSensor> convertSituationSensorToBusinessObject(List<SituationSensorDTO> situationSensorDTOs,
            SituationRequest situationRequest) {
        log.debug("start, situationSensorDTOs=" + situationSensorDTOs.size() + ", situationRequest=" + situationRequest);
        List<SituationSensor> situationSensors = new ArrayList<SituationSensor>();
        for (SituationSensorDTO dto : situationSensorDTOs) {
            SituationSensor situationSensor = new SituationSensor();
            situationSensor.setSensorId(dto.getId());
            situationSensor.setName(dto.getName());
            situationSensor.setDescription(dto.getDescription());
            situationSensor.setSituationRequest(situationRequest);
            situationSensors.add(situationSensor);
        }
        log.info("end, situationSensorDTOs=" + situationSensors.size());
        return situationSensors;
    }

    private List<StoreTime> getStoreTimes(ExtractionPlan extractionPlan, List<StoreTimeDTO> storeTimeDTOs) {
        log.info("start, extractionPlan=" + extractionPlan + ", storeTimeDTOs=" + storeTimeDTOs.size());
        List<StoreTime> storeTimes = new ArrayList<StoreTime>();
        for (StoreTimeDTO storeTimeDTO : storeTimeDTOs) {
            StoreTime storeTime = new StoreTime();
            storeTime.setDayOfWeek(storeTimeDTO.getDay());
            storeTime.setOpenHour(storeTimeDTO.getOpenHour());
            storeTime.setEndHour(storeTimeDTO.getCloseHour());
            storeTime.setExtractionPlan(extractionPlan);
            storeTimes.add(storeTime);
        }
        log.info("end, storeTimes=" + storeTimes.size());
        return storeTimes;
    }

    private List<MetricRequest> convertMetricRequestToBusinessObject(SituationRequest situationRequest,
            List<MetricRequestDTO> metricRequestDTOs, List<EvidenceProvider> evidenceProviders) {
        log.info("start, situationRequest=" + situationRequest + ", metricRequestDTOs=" + metricRequestDTOs.size()
                + ", evidenceProviders=" + evidenceProviders.size());
        List<MetricRequest> metricRequests = new ArrayList<MetricRequest>();
        for (MetricRequestDTO metricRequestDTO : metricRequestDTOs) {
            MetricRequest metricRequest = new MetricRequest();
            metricRequest.setMetricTemplateId(metricRequestDTO.getMetricTemplateId());
            metricRequest.setSituationRequest(situationRequest);
            metricRequest.setEvidenceProviderRequests(convertEvidenceProviderRequestToBusinessObject(metricRequest,
                    metricRequestDTO.getEvidenceProviderRequestDTOs(), evidenceProviders));
            metricRequests.add(metricRequest);
        }
        log.debug("end, metricRequests=" + metricRequests.size());
        return metricRequests;
    }

    private List<SituationRequestRange> convertSituationRangeToBusinessObject(SituationRequest situationRequest,
            List<SituationRequestRangeDTO> situationRequestRangeDTOs) {
        log.info("start");
        List<SituationRequestRange> ranges = new ArrayList<SituationRequestRange>();
        try {
            String[] parsePatterns = new String[] { "HHmm" };
            for (SituationRequestRangeDTO situationRequestRangeDTO : situationRequestRangeDTOs) {
                SituationRequestRange range = new SituationRequestRange();
                range.setInitialTime(DateUtils.parseDate(situationRequestRangeDTO.getInitialTime(), parsePatterns));
                range.setEndTime(DateUtils.parseDate(situationRequestRangeDTO.getEndTime(), parsePatterns));
                range.setFrecuency(situationRequestRangeDTO.getFrecuency());
                range.setDuration(situationRequestRangeDTO.getDuration());
                range.setDayOfWeek(situationRequestRangeDTO.getDayOfWeek());
                range.setSituationRequest(situationRequest);
                range.setRangeType(situationRequestRangeDTO.getRangeType());
                range.setSamples(situationRequestRangeDTO.getSamples());

                range.setSituationExtractionRequests(convertSituationExtractionRequestToBusinessObject(range,
                        situationRequestRangeDTO.getSituationExtractionRequestDTOs()));
                ranges.add(range);
            }
        } catch (ParseException e) {
            log.error("No es posible generar ranges " + e, e);
        }
        log.info("end, ranges=" + ranges.size());
        return ranges;
    }

    private List<SituationExtractionRequest> convertSituationExtractionRequestToBusinessObject(SituationRequestRange range,
            List<SituationExtractionRequestDTO> situationExtractionRequestDTOs) {
        log.info("start");
        List<SituationExtractionRequest> situationExtractionRequests = new ArrayList<SituationExtractionRequest>();
        String[] parsePatterns = new String[] { "HHmm" };
        try {
            for (SituationExtractionRequestDTO dTO : situationExtractionRequestDTOs) {
                SituationExtractionRequest situationExtractionRequest = new SituationExtractionRequest();
                situationExtractionRequest.setSituationRequestRange(range);
                situationExtractionRequest.setTimeSample(DateUtils.parseDate(dTO.getTimeSample(), parsePatterns));
                situationExtractionRequests.add(situationExtractionRequest);
            }
        } catch (ParseException e) {
            log.error("Error generando situationExtractionRequest " + e, e);
        }
        log.info("end, situationExtractionRequests=" + situationExtractionRequests.size());
        return situationExtractionRequests;
    }

    private List<EvidenceProviderRequest> convertEvidenceProviderRequestToBusinessObject(MetricRequest metricRequest,
            List<EvidenceProviderRequestDTO> evidenceProviderDTOs, List<EvidenceProvider> evidenceProviders) {
        log.info("start, metricRequest=" + metricRequest + ", evidenceProviderDTOs=" + evidenceProviderDTOs
                + ", evidenceProviders=" + evidenceProviders.size());
        List<EvidenceProviderRequest> evidenceProviderRequests = new ArrayList<EvidenceProviderRequest>();
        Collections.sort(evidenceProviders, EvidenceProvider.deviceIdComparator);
        for (EvidenceProviderRequestDTO evidenceProviderRequestDTO : evidenceProviderDTOs) {
            log.debug("evidenceProviderRequestDTO=" + evidenceProviderRequestDTO.getEvidenceProviderId());
            EvidenceProviderRequest evidenceProviderRequest = new EvidenceProviderRequest();
            evidenceProviderRequest.setDeviceId(evidenceProviderRequestDTO.getEvidenceProviderId());
            evidenceProviderRequest.setMetricRequest(metricRequest);
            EvidenceProvider evidenceProvider = new EvidenceProvider();
            evidenceProvider.setDeviceId(evidenceProviderRequestDTO.getEvidenceProviderId());
            int index = Collections.binarySearch(evidenceProviders, evidenceProvider, EvidenceProvider.deviceIdComparator);
            if (index >= 0) {
                evidenceProviderRequest.setEvidenceProvider(evidenceProviders.get(index));
            }
            evidenceProviderRequests.add(evidenceProviderRequest);
        }
        log.debug("end, evidenceProviderRequests=" + evidenceProviderRequests.size());
        return evidenceProviderRequests;
    }

    /**
     * Calls extractionPlanToPast command from the manager
     * 
     * @param date
     * @param extractionServicesServerId
     * @param storeName
     * @return
     * @throws ScopixWebServiceException
     */
    @Override
    public List<Integer> extractionPlanToPast(String date, Integer extractionServicesServerId, String storeName)
            throws ScopixWebServiceException {
        log.info("start");
        List<Integer> response = null;
        try {
            response = getExtractionServicesServersManager().extractionPlanToPast(date, extractionServicesServerId, storeName);
            log.debug("response size: " + (response == null ? null : response.size()));
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return response;
    }
}
