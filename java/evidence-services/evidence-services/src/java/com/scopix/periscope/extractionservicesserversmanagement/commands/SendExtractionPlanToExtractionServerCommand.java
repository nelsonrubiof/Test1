/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * SendExtractionPlanToExtractionServerCommand.java
 * 
 * Created on 02-07-2008, 03:43:10 AM
 */
package com.scopix.periscope.extractionservicesserversmanagement.commands;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.log4j.Logger;

import com.jcraft.jsch.JSchException;
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
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceProvider;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceProviderRequest;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceRequest;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlan;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlanDetail;
import com.scopix.periscope.extractionservicesserversmanagement.MetricRequest;
import com.scopix.periscope.extractionservicesserversmanagement.SituationExtractionRequest;
import com.scopix.periscope.extractionservicesserversmanagement.SituationRequest;
import com.scopix.periscope.extractionservicesserversmanagement.SituationRequestRange;
import com.scopix.periscope.extractionservicesserversmanagement.SituationSensor;
import com.scopix.periscope.extractionservicesserversmanagement.StoreTime;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.SSHUtil;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.cxf.configuration.jsse.TLSClientParameters;

/**
 *
 * @author marko.perich
 */
public class SendExtractionPlanToExtractionServerCommand {

    Logger log = Logger.getLogger(SendExtractionPlanToExtractionServerCommand.class);

    public void execute(EvidenceExtractionServicesServer evidenceExtractionServicesServer,
            ExtractionPlanWebService extractionPlanWebService) throws ScopixException {
        log.info("start, evidenceExtractionServicesServer=" + evidenceExtractionServicesServer.getName()
                + ", extractionPlanWebService=" + extractionPlanWebService);
        SSHUtil sshutil = null;
        try {
            // convert DTO to BO
            ExtractionServerDTO extractionServerDTO = convertToDTO(evidenceExtractionServicesServer);
            log.debug("useTunnel: " + evidenceExtractionServicesServer.isUseTunnel());
            if (evidenceExtractionServicesServer.isUseTunnel()) {
                log.debug("sshAddress=" + evidenceExtractionServicesServer.getSshAddress() + ", sshPort="
                        + evidenceExtractionServicesServer.getSshPort());
                // open ssh connection
                sshutil = new SSHUtil(evidenceExtractionServicesServer.getSshAddress(),
                        Integer.parseInt(evidenceExtractionServicesServer.getSshPort()),
                        evidenceExtractionServicesServer.getSshUser(), evidenceExtractionServicesServer.getSshPassword());
                sshutil.connect();
                log.debug("SshLocalTunnelPort=" + evidenceExtractionServicesServer.getSshLocalTunnelPort() + ", SshRemoteTunnelPort="
                        + evidenceExtractionServicesServer.getSshRemoteTunnelPort());
                sshutil.addTunnel(Integer.parseInt(evidenceExtractionServicesServer.getSshLocalTunnelPort()), "127.0.0.1",
                        Integer.parseInt(evidenceExtractionServicesServer.getSshRemoteTunnelPort()));
            }

            boolean https = false;
            try {
                URL url = new URL(evidenceExtractionServicesServer.getUrl());
                if (url.getProtocol().equals("https")) {
                    https = true;
                }
            } catch (MalformedURLException e) {
                log.error("error in URL service" + e, e);
                throw new ScopixException(e);
            }
            // TODO pasa al DispacherUtil de Foundation
            Client client = ClientProxy.getClient(extractionPlanWebService);
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

            // call the webservice
            extractionPlanWebService.newExtractionPlan(extractionServerDTO);

        } catch (NumberFormatException e) {
            throw new ScopixException(e);
        } catch (JSchException e) {
            throw new ScopixException(e);
        } catch (ScopixWebServiceException e) {
            throw new ScopixException(e);
        } finally {
            if (sshutil != null) {
                sshutil.disconnect();
            }
        }
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

    private ExtractionServerDTO convertToDTO(EvidenceExtractionServicesServer evidenceExtractionServicesServer) {
        log.info("start, evidenceExtractionServicesServer=" + evidenceExtractionServicesServer);
        ExtractionServerDTO extractionServerDTO = new ExtractionServerDTO();
        // Se modifica id a enviar para mantener consistencia en recuperacion por id del evidenceExtractionServicesServer
        // que se esta enviando
        // extractionServerDTO.setServerId(evidenceExtractionServicesServer.getServerId());

        extractionServerDTO.setServerId(evidenceExtractionServicesServer.getId());
        if (evidenceExtractionServicesServer.getExtractionPlans().size() > 1) {
            log.warn("existe mas de un extraction plan para enviar "
                    + evidenceExtractionServicesServer.getExtractionPlans().size());
        }
        ExtractionPlan ep = evidenceExtractionServicesServer.getExtractionPlans().get(0);
        extractionServerDTO.setStoreTimeDTOs(this.conververtStoreTimesToDTO(ep.getStoreTimes()));

        extractionServerDTO.setStoreName(ep.getStoreName());
        extractionServerDTO.setTimeZone(ep.getTimeZoneId());
        extractionServerDTO.setStoreId(ep.getStoreId());

        List<EvidenceProviderDTO> evidenceProviderDTOs = new ArrayList<EvidenceProviderDTO>();
        for (EvidenceProvider evidenceProvider : evidenceExtractionServicesServer.getEvidenceProviders()) {
            evidenceProviderDTOs.add(convertToDTO(evidenceProvider));
        }
        extractionServerDTO.setEvidenceProviderDTOs(evidenceProviderDTOs);
        List<EvidenceExtractionRequestDTO> evidenceExtractionRequestDTOs = convertToDTO(ep.getExtractionPlanDetails());
        List<SituationRequestDTO> situationRequestDTOs = convertNewSituationRequestToDTO(ep.getSituationRequests());
        extractionServerDTO.setEvidenceExtractionRequestDTOs(evidenceExtractionRequestDTOs);
        extractionServerDTO.setSituationRequestDTOs(situationRequestDTOs);
        log.info("end, extractionServerDTO=" + extractionServerDTO);
        return extractionServerDTO;
    }

    private List<EvidenceExtractionRequestDTO> convertToDTO(List<ExtractionPlanDetail> extractionPlanDetails) {
        log.debug("start, extractionPlanDetails=" + extractionPlanDetails.size());
        List<EvidenceExtractionRequestDTO> evidenceExtractionRequestDTOs = new ArrayList<EvidenceExtractionRequestDTO>();

        for (ExtractionPlanDetail extractionPlanDetail : extractionPlanDetails) {
            EvidenceExtractionRequestDTO evidenceExtractionRequestDTO = new EvidenceExtractionRequestDTO();
            EvidenceRequest evidenceRequest = getEvidenceRequestPriorizado(extractionPlanDetail.getEvidenceRequests());

            evidenceExtractionRequestDTO.setDeviceId(evidenceRequest.getDeviceId());
            evidenceExtractionRequestDTO.setRequestType(evidenceRequest.getRequestType());

            Calendar cal = Calendar.getInstance();
            cal.setTime(evidenceRequest.getRequestedTime());

            DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String reqTime = dateFormat.format(evidenceRequest.getRequestedTime());

            evidenceExtractionRequestDTO.setRequestedTime(reqTime);
            evidenceExtractionRequestDTO.setDuration(evidenceRequest.getDuration());
            evidenceExtractionRequestDTO.setDayOfWeek(evidenceRequest.getDayOfWeek());
            evidenceExtractionRequestDTO.setExtractionPlanDetailId(evidenceRequest.getExtractionPlanDetail().getId());
            evidenceExtractionRequestDTO.setPriorization(evidenceRequest.getPriorization());

            log.debug("evidenceRequest isLive: [" + evidenceRequest.getLive() + "]");
            evidenceExtractionRequestDTO.setLive(evidenceRequest.getLive());
            evidenceExtractionRequestDTOs.add(evidenceExtractionRequestDTO);

        }
        log.debug("end, evidenceExtractionRequestDTOs=" + evidenceExtractionRequestDTOs.size());
        return evidenceExtractionRequestDTOs;
    }

    private List<SituationRequestDTO> convertNewSituationRequestToDTO(List<SituationRequest> situationRequests) {
        log.info("start, situationRequests=" + situationRequests.size());
        List<SituationRequestDTO> situationRequestDTOs = new ArrayList<SituationRequestDTO>();
        if (situationRequests != null && !situationRequests.isEmpty()) {
            for (SituationRequest sr : situationRequests) {
                SituationRequestDTO srdto = new SituationRequestDTO();
                srdto.setSituationTemplateId(sr.getSituationTemplateId());
                // srdto.setDuration(sr.getDuration());
                // srdto.setFrecuency(sr.getFrecuency());
                srdto.setRandomCamera(sr.getRandomCamera());
                srdto.setPriorization(sr.getPriorization());
                srdto.setSituationSensors(convertSituationSendorToDTO(sr.getSituationSensors()));
                srdto.setMetricRequestDTOs(convertMetricRequestToDTO(sr.getMetricRequests()));
                srdto.setSituationRequestRangeDTOs(convertSituationRequestRangeToDTO(sr.getSituationRequestRanges()));
                situationRequestDTOs.add(srdto);
            }
        }
        log.info("end, situationRequestDTOs=" + situationRequestDTOs.size());
        return situationRequestDTOs;
    }

    private List<SituationSensorDTO> convertSituationSendorToDTO(List<SituationSensor> situationSensors) {
        log.debug("start, situationSensors=" + situationSensors.size());
        List<SituationSensorDTO> situationSensorDTOs = new ArrayList<SituationSensorDTO>();
        for (SituationSensor situationSensor : situationSensors) {
            SituationSensorDTO situationSensorDTO = new SituationSensorDTO();
            situationSensorDTO.setId(situationSensor.getSensorId());
            situationSensorDTO.setName(situationSensor.getName());
            situationSensorDTO.setDescription(situationSensor.getDescription());
            situationSensorDTOs.add(situationSensorDTO);
        }
        log.debug("end, situationSensorDTOs=" + situationSensorDTOs.size());
        return situationSensorDTOs;
    }

    private List<StoreTimeDTO> conververtStoreTimesToDTO(List<StoreTime> storeTimes) {
        log.debug("start, storeTimes=" + storeTimes.size());
        List<StoreTimeDTO> storeTimeDTOs = new ArrayList<StoreTimeDTO>();
        for (StoreTime storeTime : storeTimes) {
            StoreTimeDTO dTO = new StoreTimeDTO();
            dTO.setCloseHour(storeTime.getEndHour());
            dTO.setOpenHour(storeTime.getOpenHour());
            dTO.setDay(storeTime.getDayOfWeek());
            storeTimeDTOs.add(dTO);
        }
        log.debug("end, storeTimeDTOs=" + storeTimeDTOs.size());
        return storeTimeDTOs;
    }

    private List<MetricRequestDTO> convertMetricRequestToDTO(List<MetricRequest> metricRequests) {
        log.info("start, metricRequests=" + metricRequests.size());
        List<MetricRequestDTO> metricRequestDTOs = new ArrayList<MetricRequestDTO>();
        if (metricRequests != null && !metricRequests.isEmpty()) {
            for (MetricRequest mr : metricRequests) {
                MetricRequestDTO mrdto = new MetricRequestDTO();
                mrdto.setMetricTemplateId(mr.getMetricTemplateId());
                mrdto.setEvidenceProviderRequestDTOs(convertEvidenceProviderRequestToDTO(mr.getEvidenceProviderRequests()));
                metricRequestDTOs.add(mrdto);
            }
        }
        log.info("end, metricRequestDTOs=" + metricRequestDTOs.size());
        return metricRequestDTOs;
    }

    private List<SituationRequestRangeDTO> convertSituationRequestRangeToDTO(List<SituationRequestRange> situationRequestRanges) {
        log.info("start, situationRequestRanges=" + situationRequestRanges.size());
        List<SituationRequestRangeDTO> ret = new ArrayList<SituationRequestRangeDTO>();
        for (SituationRequestRange range : situationRequestRanges) {
            SituationRequestRangeDTO dto = new SituationRequestRangeDTO();
            dto.setDayOfWeek(range.getDayOfWeek());
            dto.setDuration(range.getDuration());
            dto.setFrecuency(range.getFrecuency());
            dto.setInitialTime(DateFormatUtils.format(range.getInitialTime(), "HHmm"));
            dto.setEndTime(DateFormatUtils.format(range.getEndTime(), "HHmm"));
            dto.setRangeType(range.getRangeType());
            dto.setSamples(range.getSamples());
            dto.setSituationExtractionRequestDTOs(convertSituationExtractionRequestToDTO(range.getSituationExtractionRequests()));
            ret.add(dto);
        }
        log.info("end, situationRequestRangeDTOs=" + ret.size());
        return ret;
    }

    private List<SituationExtractionRequestDTO> convertSituationExtractionRequestToDTO(
            List<SituationExtractionRequest> situationExtractionRequests) {
        log.info("start, situationExtractionRequests=" + situationExtractionRequests.size());
        List<SituationExtractionRequestDTO> ret = new ArrayList<SituationExtractionRequestDTO>();
        for (SituationExtractionRequest extractionRequest : situationExtractionRequests) {
            SituationExtractionRequestDTO dto = new SituationExtractionRequestDTO();
            dto.setTimeSample(DateFormatUtils.format(extractionRequest.getTimeSample(), "HHmm"));
            ret.add(dto);
        }
        log.info("end, situationExtractionRequestDTOs=" + ret.size());
        return ret;
    }

    private List<EvidenceProviderRequestDTO> convertEvidenceProviderRequestToDTO(
            List<EvidenceProviderRequest> evidenceProviderRequests) {
        log.debug("start, evidenceProviderRequests=" + evidenceProviderRequests.size());
        List<EvidenceProviderRequestDTO> evidenceProviderRequestDTOs = new ArrayList<EvidenceProviderRequestDTO>();
        if (evidenceProviderRequests != null && !evidenceProviderRequests.isEmpty()) {
            for (EvidenceProviderRequest epr : evidenceProviderRequests) {
                EvidenceProviderRequestDTO eprdto = new EvidenceProviderRequestDTO();
                eprdto.setDeviceId(epr.getDeviceId());
                evidenceProviderRequestDTOs.add(eprdto);
            }
        }
        log.debug("end, evidenceProviderRequests=" + evidenceProviderRequests.size());
        return evidenceProviderRequestDTOs;
    }

    private EvidenceProviderDTO convertToDTO(EvidenceProvider evidenceProvider) {
        log.debug("start, evidenceProvider=" + evidenceProvider);
        EvidenceProviderDTO evidenceProviderDTO = new EvidenceProviderDTO();
        evidenceProviderDTO.setDefinitionData(evidenceProvider.getDefinitionData());
        evidenceProviderDTO.setDescription(evidenceProvider.getDescription());
        evidenceProviderDTO.setProviderType(evidenceProvider.getProviderType());
        evidenceProviderDTO.setId(evidenceProvider.getDeviceId());
        log.debug("end, evidenceProviderDTO=" + evidenceProviderDTO.getId());
        return evidenceProviderDTO;
    }

    private EvidenceRequest getEvidenceRequestPriorizado(List<EvidenceRequest> evidenceRequests) {
        EvidenceRequest er = null;
        for (EvidenceRequest evidenceRequest : evidenceRequests) {
            if (er == null) {
                er = evidenceRequest;
            } else if (evidenceRequest.getPriorization() != null && er.getPriorization() == null) {
                er = evidenceRequest;
            } else if (evidenceRequest.getPriorization() != null && evidenceRequest.getPriorization() < er.getPriorization()) {
                er = evidenceRequest;
            }
        }
        log.debug("er.priorization " + er.getPriorization());
        return er;
    }
}
