/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * SendExtractionPlanCommand.java
 * 
 * Created on 30-06-2008, 06:07:21 PM
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.corporatestructuremanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.PeriodInterval;
import com.scopix.periscope.corporatestructuremanagement.Sensor;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.corporatestructuremanagement.dao.CorporateStructureManagementHibernateDAO;
import com.scopix.periscope.corporatestructuremanagement.dao.CorporateStructureManagementHibernateDAOImpl;
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
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanManager;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanMetric;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRangeDetail;
import com.scopix.periscope.extractionservicesserversmanagement.services.webservices.EvidenceServicesWebService;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.StringUtil;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author marko.perich
 */
public class SendExtractionPlanCommand {

    private GenericDAO genericDao;
    private CorporateStructureManagementHibernateDAO daoCorporateStructureManager;
    private ExtractionPlanManager wizardManager;
    private Logger log = Logger.getLogger(SendExtractionPlanCommand.class);
    /**
     * List of valid evidence providers
     */
    private List<EvidenceProvider> evidenceProviders = null;

    /**
     * This method create the dto and call a web service to evidence-services for a specific store of corporate
     *
     * @param evidenceExtractionServicesServer
     * @param evidenceServicesWebService
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    public void execute(EvidenceExtractionServicesServer evidenceExtractionServicesServer,
            EvidenceServicesWebService evidenceServicesWebService, Store store) throws ScopixException {
        log.debug("start, evidenceExtractionServicesServerId = " + evidenceExtractionServicesServer.getId()
                + ", evidenceExtractionWebService = " + evidenceServicesWebService);

        evidenceExtractionServicesServer = getGenericDao().get(evidenceExtractionServicesServer.getId(),
                EvidenceExtractionServicesServer.class);

        List<EvidenceExtractionServicesServerDTO> evidenceExtractionServicesServerDTOs = new ArrayList<EvidenceExtractionServicesServerDTO>();
        // Convert to DTO
        EvidenceExtractionServicesServerDTO evidenceExtractionServicesServerDTO = convertToDTO(evidenceExtractionServicesServer,
                store);
        evidenceExtractionServicesServerDTOs.add(evidenceExtractionServicesServerDTO);
        // Send a list with only one element
        try {
            // Asumimos que se envio correctamente
            evidenceServicesWebService.newExtractionPlan(evidenceExtractionServicesServerDTOs);
        } catch (Exception e) {
            log.error("" + e, e);
            throw new ScopixException("OPERATION_INCOMPLETE");

        }
        log.debug("end");
    }

    /**
     * Convert a EvidenceExtractionServicesServer to a EvidenceExtractionServicesServerDTO
     *
     * @param evidenceExtractionServicesServer
     * @return
     */
    private EvidenceExtractionServicesServerDTO convertToDTO(EvidenceExtractionServicesServer evidenceExtractionServicesServer,
            Store store) throws ScopixException {
        log.debug("start, evidenceExtractionServicesServerId = " + evidenceExtractionServicesServer.getId());
        EvidenceExtractionServicesServerDTO evidenceExtractionServicesServerDTO = new EvidenceExtractionServicesServerDTO();

        // evidenceExtractionServicesServerDTO.setName(evidenceExtractionServicesServer.getStore().getName());
        evidenceExtractionServicesServerDTO.setName(store.getName());
        evidenceExtractionServicesServerDTO.setUrl(evidenceExtractionServicesServer.getUrl());
        evidenceExtractionServicesServerDTO.setServerId(evidenceExtractionServicesServer.getEvidenceServicesServer().getId());
        evidenceExtractionServicesServerDTO.setIdAtBusinessServices(evidenceExtractionServicesServer.getId());
        evidenceExtractionServicesServerDTO.setUseTunnel(evidenceExtractionServicesServer.isUseTunnel());
        evidenceExtractionServicesServerDTO.setSshAddress(evidenceExtractionServicesServer.getSshAddress());
        evidenceExtractionServicesServerDTO.setSshUser(evidenceExtractionServicesServer.getSshUser());
        evidenceExtractionServicesServerDTO.setSshPassword(evidenceExtractionServicesServer.getSshPassword());
        evidenceExtractionServicesServerDTO.setSshPort(evidenceExtractionServicesServer.getSshPort());
        evidenceExtractionServicesServerDTO.setSshLocalTunnelPort(evidenceExtractionServicesServer.getSshLocalTunnelPort());
        evidenceExtractionServicesServerDTO.setSshRemoteTunnelPort(evidenceExtractionServicesServer.getSshRemoteTunnelPort());

        // create extraction plan for this evidence extraction server
        evidenceExtractionServicesServerDTO
                .setExtractionPlanDTO(createExtractionPlanDTO(evidenceExtractionServicesServer, store));

        // create evidence provider list for this evidence extraction server
        evidenceExtractionServicesServerDTO.setEvidenceProviderDTOs(createEvidenceProviderDTOs(evidenceExtractionServicesServer));
        log.debug("end, evidenceExtractionServicesServerDTO = " + evidenceExtractionServicesServerDTO);
        return evidenceExtractionServicesServerDTO;
    }

    /**
     * Create a list of evidenceProvidersDTO from the list of evidenceProvider
     *
     * @param evidenceExtractionServicesServer
     * @return
     */
    protected List<EvidenceProviderDTO> createEvidenceProviderDTOs(
            EvidenceExtractionServicesServer evidenceExtractionServicesServer) {
        log.debug("start, evidenceExtractionServicesServerId = " + evidenceExtractionServicesServer.getId());
        List<EvidenceProviderDTO> evidenceProviderDTOs = new ArrayList<EvidenceProviderDTO>();

        for (EvidenceProvider evidenceProvider : evidenceProviders) {
            EvidenceProviderDTO evidenceProviderDTO = new EvidenceProviderDTO();
            evidenceProviderDTO.setId(evidenceProvider.getId());
            evidenceProviderDTO.setDescription(evidenceProvider.getDescription());
            evidenceProviderDTO.setProviderType(evidenceProvider.getEvidenceProviderType().getDescription());
            HashMap<String, String> providerData = StringUtil.getHashMapFromString(evidenceProvider.getDefinitionData());
            evidenceProviderDTO.setDefinitionData(providerData);
            evidenceProviderDTOs.add(evidenceProviderDTO);
        }
        log.debug("end, evidenceProviderDTOs = " + evidenceProviderDTOs);
        return evidenceProviderDTOs;
    }

    /**
     * This method create a extractionPlanDTO
     *
     * @param evidenceExtractionServicesServer
     * @return
     */
    protected ExtractionPlanDTO createExtractionPlanDTO(EvidenceExtractionServicesServer evidenceExtractionServicesServer,
            Store store) throws ScopixException {
        log.debug("start, evidenceExtractionServicesServerId = " + evidenceExtractionServicesServer.getId());

        // create extractionplan DTO
        ExtractionPlanDTO extractionPlanDTO = new ExtractionPlanDTO();

        extractionPlanDTO.setStoreName(store.getName());
        extractionPlanDTO.setTimeZone(store.getTimeZoneId());
        extractionPlanDTO.setStoreId(store.getId());
        // Necesario para el envio de sensores
        // UPDATE (20120622): nueva versión de EES ya no trabaja con este dato
        extractionPlanDTO.setStoreTimeDTOs(new ArrayList<StoreTimeDTO>());
        // this.getStoreTimeDTOs(store.getPeriodIntervals()));

        ArrayList<ExtractionPlanDetailDTO> extractionPlanDetailDTOs = new ArrayList<ExtractionPlanDetailDTO>();

        // create the list of evidence requests DTOs
        setEvidenceProviders(new ArrayList<EvidenceProvider>());

        log.debug("before getActiveEvidenceRequestDTOs");
        List<EvidenceRequestDTO> evidenceRequestDTOs = getDaoCorporateStructureManager().getActiveEvidenceRequestDTOs(
                store.getId());
        log.debug("after getActiveEvidenceRequestDTOs");

        log.debug("before generando lista Provider ids");
        Set<Integer> providerIds = new HashSet<Integer>();
        for (EvidenceRequestDTO dto : evidenceRequestDTOs) {
            providerIds.add(dto.getDeviceId());
        }
        log.debug("after generando lista Provider ids");

        log.debug("before generando lista Provider unica Objetos");
        for (Integer provideId : providerIds) {
            EvidenceProvider ep = getGenericDao().get(provideId, EvidenceProvider.class);
            if (Collections.binarySearch(evidenceProviders, ep) < 0) {
                evidenceProviders.add(ep);
            }
        }
        log.debug("after generando lista Provider unica Objetos");

        log.debug("before generando lista situationRequestDTOs");
        List<SituationRequestDTO> situationRequestDTOs = this.createNewSituationRequestDTOs(store);
        log.debug("after generando lista situationRequestDTOs");

        log.debug("before sort evidenceRequestDTOs size:" + evidenceRequestDTOs.size());
        // sort them
        Comparator comparator = new EvidenceRequestDTO();
        Collections.sort(evidenceRequestDTOs, comparator);
        log.debug("after sort evidenceRequestDTOs");

        // create details of extraction plan
        EvidenceRequestDTO evidenceRequestDTOTemp = null;
        ExtractionPlanDetailDTO extractionPlanDetailDTO = null;

        log.debug("before generate extractionPlanDetailDTO con evidenceRequestDto asociados");
        // iterate the list of evidence request DTOs
        int idExtractionPlanDetailDTO = 1;
        for (EvidenceRequestDTO evidenceRequestDTO : evidenceRequestDTOs) {
            //
            if ((evidenceRequestDTOTemp == null) || (comparator.compare(evidenceRequestDTOTemp, evidenceRequestDTO) != 0)) {
                evidenceRequestDTOTemp = new EvidenceRequestDTO();
                evidenceRequestDTOTemp.setDeviceId(evidenceRequestDTO.getDeviceId());
                evidenceRequestDTOTemp.setRequestType(evidenceRequestDTO.getRequestType());
                evidenceRequestDTOTemp.setDuration(evidenceRequestDTO.getDuration());
                evidenceRequestDTOTemp.setRequestedTime(evidenceRequestDTO.getRequestedTime());
                evidenceRequestDTOTemp.setDayOfWeek(evidenceRequestDTO.getDayOfWeek());
                evidenceRequestDTOTemp.setPriorization(evidenceRequestDTO.getPriorization());
                evidenceRequestDTOTemp.setLive(evidenceRequestDTO.getLive());
                extractionPlanDetailDTO = new ExtractionPlanDetailDTO();
                extractionPlanDetailDTOs.add(extractionPlanDetailDTO);
            }
            log.debug("evidenceRequestDTO isLive: [" + evidenceRequestDTO.getLive() + "]");
            extractionPlanDetailDTO.getEvidenceRequestDTOs().add(evidenceRequestDTO);
        }
        log.debug("after generate extractionPlanDetailDTO con evidenceRequestDto asociados extractionPlanDetailDTOs size:"
                + extractionPlanDetailDTOs.size());
        extractionPlanDTO.setExtractionPlanDetails(extractionPlanDetailDTOs);
        extractionPlanDTO.setSituationRequestDTOs(situationRequestDTOs);
        log.debug("end, extractionPlanDTO = " + extractionPlanDTO);
        return extractionPlanDTO;
    }

    protected List<SituationRequestDTO> createNewSituationRequestDTOs(Store store) {
        log.debug("start, storeId = " + store);
        List<SituationRequestDTO> situationRequestDTOs = new ArrayList<SituationRequestDTO>();

        try {
            // recuperamos todos los epcs que contengan sensores o que algunos
            // de sus provider sean automaticos
            // o que sean contengan random de camaras
            List<ExtractionPlanCustomizing> epcs = getWizardManager().getAutomaticExtractionPlanCustomizings(store);

            if (epcs != null && !epcs.isEmpty()) {
                // Collections.sort(evidenceProviders);
                for (ExtractionPlanCustomizing epc : epcs) {

                    // Creamos un situation Request por epc cada epc y le
                    // agregamos los sensores
                    SituationRequestDTO situationRequestDTO = getNewSituationRequestDTO(epc, situationRequestDTOs);

                    // recorremos todos los rangos
                    log.debug("[ranges:" + epc.getExtractionPlanRanges().size() + "]");
                    for (ExtractionPlanRange epr : epc.getExtractionPlanRanges()) {
                        createSituationRequestRangesDTOs(epr, situationRequestDTO);
                    }
                    // recorremos todos las metricas si se las agregamos a la
                    // situacion
                    log.debug("[metrics:" + epc.getExtractionPlanMetrics().size() + "]");
                    for (ExtractionPlanMetric epm : epc.getExtractionPlanMetrics()) {
                        MetricRequestDTO metricRequestDTO = getMetricRequestDTO(epm, situationRequestDTO);
                        addNewEvidenceProviderDtoToMetricRequestDto(epm, metricRequestDTO);
                    }
                }
            }
        } catch (ScopixException e) {
            log.debug("error, message = " + e, e);
        }
        log.debug("end, storeId = " + store.getId());
        return situationRequestDTOs;
    }

    private void createSituationRequestRangesDTOs(ExtractionPlanRange epr, SituationRequestDTO situationRequestDTO) {
        // agregamos los rangos a la situacion
        log.info("start [epr:" + epr + "][init:" + epr.getInitialTime() + "][end:" + epr.getEndTime() + "]");
        SituationRequestRangeDTO situationRequestRangeDTO = new SituationRequestRangeDTO();
        situationRequestRangeDTO.setDayOfWeek(epr.getDayOfWeek());
        situationRequestRangeDTO.setDuration(epr.getDuration());
        situationRequestRangeDTO.setFrecuency(epr.getFrecuency());
        situationRequestRangeDTO.setInitialTime(DateFormatUtils.format(epr.getInitialTime(), "HHmm"));
        situationRequestRangeDTO.setEndTime(DateFormatUtils.format(epr.getEndTime(), "HHmm"));
        situationRequestRangeDTO.setRangeType(epr.getExtractionPlanRangeType().getName());
        situationRequestRangeDTO.setSamples(epr.getSamples());

        for (ExtractionPlanRangeDetail detail : epr.getExtractionPlanRangeDetails()) {
            SituationExtractionRequestDTO requestDTO = new SituationExtractionRequestDTO();
            requestDTO.setTimeSample(DateFormatUtils.format(detail.getTimeSample(), "HHmm"));
            situationRequestRangeDTO.getSituationExtractionRequestDTOs().add(requestDTO);
        }

        // agregamos el rango y sus detalles al situationRequestDTO
        situationRequestDTO.getSituationRequestRangeDTOs().add(situationRequestRangeDTO);
        log.info("end");
    }

    /**
     * Recupera un SituationRequestDTO de la lista situationRequestDTOs o lo crea si no existe agregandolo a la lista
     */
    protected SituationRequestDTO getNewSituationRequestDTO(ExtractionPlanCustomizing epc,
            List<SituationRequestDTO> situationRequestDTOs) {
        SituationRequestDTO srdto = new SituationRequestDTO();
        srdto.setSituationTemplateId(epc.getSituationTemplate().getId());
        Collections.sort(situationRequestDTOs);

        int index = Collections.binarySearch(situationRequestDTOs, srdto);
        SituationRequestDTO situationRequestDTO = null;

        if (index >= 0) {
            situationRequestDTO = situationRequestDTOs.get(index);
        } else {
            situationRequestDTO = new SituationRequestDTO();
            situationRequestDTO.setSituationTemplateId(epc.getSituationTemplate().getId());
            situationRequestDTO.setRandonCamera(epc.isRandomCamera());
            situationRequestDTO.setPriorization(epc.getPriorization());
            // se sacan al detalle que se agregara el situationRequestDTO
            // situationRequestDTO.setDuration(epr.getDuration());
            // situationRequestDTO.setFrecuency(epr.getFrecuency());
            List<SituationSensorDTO> situationSensors = new ArrayList<SituationSensorDTO>();
            if (epc.getSensors() != null) {
                for (Sensor sensor : epc.getSensors()) {
                    SituationSensorDTO situationSensorDTO = new SituationSensorDTO();
                    situationSensorDTO.setId(sensor.getId());
                    situationSensorDTO.setName(sensor.getName());
                    situationSensorDTO.setDescription(sensor.getDescription());
                    situationSensors.add(situationSensorDTO);
                }
            }
            situationRequestDTO.setSituationSensors(situationSensors);
            situationRequestDTOs.add(situationRequestDTO);
        }
        return situationRequestDTO;
    }

    /**
     * Recupera un SituationRequestDTO de la lista situationRequestDTOs o lo crea si no existe agregandolo a la lista
     */
    protected SituationRequestDTO getSituationRequestDTO(ExtractionPlanCustomizing epc,
            List<SituationRequestDTO> situationRequestDTOs, ExtractionPlanRange epr) {
        SituationRequestDTO srdto = new SituationRequestDTO();
        srdto.setSituationTemplateId(epc.getSituationTemplate().getId());
        Collections.sort(situationRequestDTOs);

        int index = Collections.binarySearch(situationRequestDTOs, srdto);
        SituationRequestDTO situationRequestDTO = null;

        if (index >= 0) {
            situationRequestDTO = situationRequestDTOs.get(index);
        } else {
            situationRequestDTO = new SituationRequestDTO();
            situationRequestDTO.setSituationTemplateId(epc.getSituationTemplate().getId());
            situationRequestDTO.setDuration(epr.getDuration());
            situationRequestDTO.setFrecuency(epr.getFrecuency());
            situationRequestDTO.setRandonCamera(epc.isRandomCamera());
            List<SituationSensorDTO> situationSensors = new ArrayList<SituationSensorDTO>();
            if (epc.getSensors() != null) {
                for (Sensor sensor : epc.getSensors()) {
                    SituationSensorDTO situationSensorDTO = new SituationSensorDTO();
                    situationSensorDTO.setId(sensor.getId());
                    situationSensorDTO.setName(sensor.getName());
                    situationSensorDTO.setDescription(sensor.getDescription());
                    situationSensors.add(situationSensorDTO);
                }
            }
            situationRequestDTO.setSituationSensors(situationSensors);
            situationRequestDTOs.add(situationRequestDTO);
        }
        return situationRequestDTO;
    }

    /**
     * recupera un MetricRequestDTO o lo genera si no existe en la lista de situationRequestDTO.getMetricRequestDTOs()
     */
    protected MetricRequestDTO getMetricRequestDTO(ExtractionPlanMetric epm, SituationRequestDTO situationRequestDTO) {
        MetricRequestDTO mrdto = new MetricRequestDTO();
        mrdto.setMetricTemplateId(epm.getMetricTemplate().getId());
        Collections.sort(situationRequestDTO.getMetricRequestDTOs());

        int index = Collections.binarySearch(situationRequestDTO.getMetricRequestDTOs(), mrdto);
        MetricRequestDTO metricRequestDTO = null;
        if (index >= 0) {
            metricRequestDTO = situationRequestDTO.getMetricRequestDTOs().get(index);
        } else {
            metricRequestDTO = new MetricRequestDTO();
            metricRequestDTO.setMetricTemplateId(epm.getMetricTemplate().getId());
            situationRequestDTO.getMetricRequestDTOs().add(metricRequestDTO);
        }
        return metricRequestDTO;
    }

    protected void addNewEvidenceProviderDtoToMetricRequestDto(ExtractionPlanMetric epm, MetricRequestDTO metricRequestDTO) {
        for (EvidenceProvider ep : epm.getEvidenceProviders()) {
            if (Collections.binarySearch(evidenceProviders, ep) < 0) {
                evidenceProviders.add(ep);
            }
            EvidenceProviderRequestDTO eprdto = new EvidenceProviderRequestDTO();
            eprdto.setEvidenceProviderId(ep.getId());
            Collections.sort(metricRequestDTO.getEvidenceProviderRequestDTOs());
            int index = Collections.binarySearch(metricRequestDTO.getEvidenceProviderRequestDTOs(), eprdto);
            if (index < 0) {
                EvidenceProviderRequestDTO evidenceProviderRequestDTO = new EvidenceProviderRequestDTO();
                evidenceProviderRequestDTO.setEvidenceProviderId(ep.getId());
                metricRequestDTO.getEvidenceProviderRequestDTOs().add(evidenceProviderRequestDTO);
            }
        }
    }

    /**
     * Agrega todos los EvidenceProviderRequestDTO generados para un ExtractionPlanCustomizing al metricRequestDTO
     */
    protected void addEvidenceProviderDtoToMetricRequestDto(ExtractionPlanCustomizing epc, MetricRequestDTO metricRequestDTO) {
        /**
         * reemplazamos la busqueda de los providers para el epc
         */
        for (ExtractionPlanMetric epm : epc.getExtractionPlanMetrics()) {
            // for (EvidenceProvider ep : epc.getEvidenceProviders()) {
            for (EvidenceProvider ep : epm.getEvidenceProviders()) {
                if (Collections.binarySearch(evidenceProviders, ep) < 0) {
                    evidenceProviders.add(ep);
                }
                EvidenceProviderRequestDTO eprdto = new EvidenceProviderRequestDTO();
                eprdto.setEvidenceProviderId(ep.getId());
                Collections.sort(metricRequestDTO.getEvidenceProviderRequestDTOs());
                int index = Collections.binarySearch(metricRequestDTO.getEvidenceProviderRequestDTOs(), eprdto);
                if (index < 0) {
                    EvidenceProviderRequestDTO evidenceProviderRequestDTO = new EvidenceProviderRequestDTO();
                    evidenceProviderRequestDTO.setEvidenceProviderId(ep.getId());
                    metricRequestDTO.getEvidenceProviderRequestDTOs().add(evidenceProviderRequestDTO);
                }
            }
        }
    }

    /**
     * this method create a list of storeTimeDTOs from a list of period intervals
     *
     * @param periodIntervals
     * @return
     */
    private List<StoreTimeDTO> getStoreTimeDTOs(List<PeriodInterval> periodIntervals) {
        log.debug("start, periodIntervals = " + periodIntervals);
        List<StoreTimeDTO> storeTimeDTOs = new ArrayList<StoreTimeDTO>();

        for (PeriodInterval pi : periodIntervals) {
            if (pi.isSunday()) {
                StoreTimeDTO dTO = new StoreTimeDTO();
                dTO.setDay(Calendar.SUNDAY);
                dTO.setOpenHour(pi.getInitTime());
                dTO.setCloseHour(pi.getEndTime());
                storeTimeDTOs.add(dTO);
            }
            if (pi.isMonday()) {
                StoreTimeDTO dTO = new StoreTimeDTO();
                dTO.setDay(Calendar.MONDAY);
                dTO.setOpenHour(pi.getInitTime());
                dTO.setCloseHour(pi.getEndTime());
                storeTimeDTOs.add(dTO);
            }
            if (pi.isTuesday()) {
                StoreTimeDTO dTO = new StoreTimeDTO();
                dTO.setDay(Calendar.TUESDAY);
                dTO.setOpenHour(pi.getInitTime());
                dTO.setCloseHour(pi.getEndTime());
                storeTimeDTOs.add(dTO);
            }
            if (pi.isWednesday()) {
                StoreTimeDTO dTO = new StoreTimeDTO();
                dTO.setDay(Calendar.WEDNESDAY);
                dTO.setOpenHour(pi.getInitTime());
                dTO.setCloseHour(pi.getEndTime());
                storeTimeDTOs.add(dTO);
            }
            if (pi.isThursday()) {
                StoreTimeDTO dTO = new StoreTimeDTO();
                dTO.setDay(Calendar.THURSDAY);
                dTO.setOpenHour(pi.getInitTime());
                dTO.setCloseHour(pi.getEndTime());
                storeTimeDTOs.add(dTO);
            }
            if (pi.isFriday()) {
                StoreTimeDTO dTO = new StoreTimeDTO();
                dTO.setDay(Calendar.FRIDAY);
                dTO.setOpenHour(pi.getInitTime());
                dTO.setCloseHour(pi.getEndTime());
                storeTimeDTOs.add(dTO);
            }
            if (pi.isSaturday()) {
                StoreTimeDTO dTO = new StoreTimeDTO();
                dTO.setDay(Calendar.SATURDAY);
                dTO.setOpenHour(pi.getInitTime());
                dTO.setCloseHour(pi.getEndTime());
                storeTimeDTOs.add(dTO);
            }
        }
        log.debug("end, storeTimeDTOs = " + storeTimeDTOs);
        return storeTimeDTOs;
    }

    public GenericDAO getGenericDao() {
        if (genericDao == null) {
            genericDao = HibernateSupport.getInstance().findGenericDAO();
        }
        return genericDao;
    }

    public void setGenericDao(GenericDAO daoEvidenceExtraction) {
        this.genericDao = daoEvidenceExtraction;
    }

    public CorporateStructureManagementHibernateDAO getDaoCorporateStructureManager() {
        if (daoCorporateStructureManager == null) {
            daoCorporateStructureManager = SpringSupport.getInstance().findBeanByClassName(
                    CorporateStructureManagementHibernateDAOImpl.class);
        }
        return daoCorporateStructureManager;
    }

    public void setDaoCorporateStructureManager(CorporateStructureManagementHibernateDAO daoCorporateStructureManager) {
        this.daoCorporateStructureManager = daoCorporateStructureManager;
    }

    public ExtractionPlanManager getWizardManager() {
        if (wizardManager == null) {
            wizardManager = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class);
        }
        return wizardManager;
    }

    public void setWizardManager(ExtractionPlanManager wizardManager) {
        this.wizardManager = wizardManager;
    }

    public List<EvidenceProvider> getEvidenceProviders() {
        return evidenceProviders;
    }

    public void setEvidenceProviders(List<EvidenceProvider> evidenceProviders) {
        this.evidenceProviders = evidenceProviders;
    }
}
