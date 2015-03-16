/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * CorporateStructureManager.java
 *
 * Created on 27-03-2008, 06:41:07 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement;

import com.scopix.periscope.JCIFSUtil;
import com.scopix.periscope.corporatestructuremanagement.commands.AddAreaCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.AddAreaTypeCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.AddCorporateCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.AddCountryCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.AddEvidenceExtractionServicesServerCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.AddEvidenceProviderCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.AddEvidenceProviderTypeCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.AddEvidenceServicesServerCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.AddRegionCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.AddSensorCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.AddStoreCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.CreateOrEditEvidenceTransmissionStrategies;
import com.scopix.periscope.corporatestructuremanagement.commands.DeleteEvidenceTransmissionStrategyCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.DeleteMultipleEvTransmStrs;
import com.scopix.periscope.corporatestructuremanagement.commands.DeleteRegionServerCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.FindEvTransmStgiesByIdCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetAreaCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetAreaListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetAreaTypeCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetAreaTypeListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetCorporateCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetCountryCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetCountryListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetEvidenceExtractionServicesServerCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetEvidenceExtractionServicesServerListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetEvidenceExtractionServicesServerListForStoresCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetEvidenceProviderCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetEvidenceProviderListByAreaCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetEvidenceProviderListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetEvidenceProviderTypeCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetEvidenceProviderTypeListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetEvidenceServicesServerCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetEvidenceServicesServerListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetFreeEvidenceExtractionServicesServerListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetRegionCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetRegionListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetRegionServerByIdCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetRegionServersCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetSensorAndEvidenceExtractionServicesServerListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetSensorCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetSensorListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetSituationStoreDayCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetSituationStoreDayReadyCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetSituationTemplateListByAreasCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetStoreCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetStoreListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.RemoveAreaCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.RemoveAreaTypeCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.RemoveCountryCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.RemoveEvidenceExtractionServicesServerCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.RemoveEvidenceProviderCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.RemoveEvidenceProviderTypeCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.RemoveEvidenceServicesServerCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.RemoveRegionCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.RemoveSensorCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.RemoveStoreCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.SaveEvidenceTransmissionStrategyCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.SaveOrUpdateRegionServerCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.SaveSendExtractionPlanProcessCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.SendEPCThread;
import com.scopix.periscope.corporatestructuremanagement.commands.SendExtractionPlanCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.SendExtractionPlanToPastCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.SynchronizeToSecurityCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.TransformStoreToDTOCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.TransformToEvidenceTransmStrategyFEDTOCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.TransformToEvidenceTransmissionStrategy;
import com.scopix.periscope.corporatestructuremanagement.commands.TransformToRegionServerCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.TransformToRegionServerFrontEndDTO;
import com.scopix.periscope.corporatestructuremanagement.commands.TransformToSitTemplateFEDTOCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.TransformToStoreFrontEndDTOCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.UpdateAreaCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.UpdateAreaTypeCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.UpdateCorporateCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.UpdateCountryCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.UpdateEvidenceExtractionServicesServerCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.UpdateEvidenceProviderCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.UpdateEvidenceServicesServerCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.UpdateRegionCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.UpdateRelationEvidenceProviderLocationCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.UpdateSensorCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.UpdateStoreCommand;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceExtractionServicesServerDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceProviderDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.SensorAndEvidenceExtractionServicesServerDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.StatusSendEPCDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import com.scopix.periscope.evaluationmanagement.EvidenceTransmitionStrategy;
import com.scopix.periscope.evaluationmanagement.RegionServer;
import com.scopix.periscope.evaluationmanagement.commands.GetTransmStgiesByCriteriaCommand;
import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanManager;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanMetric;
import com.scopix.periscope.extractionplanmanagement.commands.GetCantidadDetailsByIdsCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GetCantidadDetailsCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GetExtractionPlanCustomizingByIdCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GetExtractionPlanCustomizingListByIdsCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GetExtractionPlanCustomizingListCommand;
import com.scopix.periscope.extractionplanmanagement.commands.TransformEvidenceProvidersToDTOCommand;
import com.scopix.periscope.extractionservicesserversmanagement.services.webservices.EvidenceServicesWebService;
import com.scopix.periscope.extractionservicesserversmanagement.services.webservices.client.EvidenceServicesWebServiceClient;
import com.scopix.periscope.frontend.dto.EvidenceTransmStrategyFEDTO;
import com.scopix.periscope.frontend.dto.RegionServerFrontEndDTO;
import com.scopix.periscope.frontend.dto.SituationTemplateFrontEndDTO;
import com.scopix.periscope.frontend.dto.StoreFrontEndDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.SortUtil;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.periscopefoundation.util.config.SystemConfig;
import com.scopix.periscope.securitymanagement.CorporateStructureManagerPermissions;
import com.scopix.periscope.securitymanagement.SecurityManager;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.templatemanagement.commands.GetSituationTemplateListCommand;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gustavo Alvarez
 */
@SpringBean(rootClass = CorporateStructureManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class CorporateStructureManager implements InitializingBean {

    private SecurityManager securityManager;
    private GetStoreListCommand storeListCommand;
    private TransformStoreToDTOCommand transformStoreToDTOCommand;
    private TransformEvidenceProvidersToDTOCommand transformEvidenceProvidersToDTOCommand;
    private GetExtractionPlanCustomizingListCommand extractionPlanCustomizingListCommand;
    private GetExtractionPlanCustomizingListByIdsCommand extractionPlanCustomizingListByIdsCommand;
    private GetEvidenceExtractionServicesServerCommand evidenceExtractionServicesServerCommand;
    private EvidenceServicesWebServiceClient webServiceClient;
    private ExtractionPlanManager extractionPlanManager;
    private GetExtractionPlanCustomizingByIdCommand extractionPlanCustomizingByIdCommand;
    private GetStoreCommand storeCommand;
    private SendExtractionPlanCommand sendExtractionPlanCommand;
    private TransformToStoreFrontEndDTOCommand transformToStoreFrontEndDTO;
    private TransformToSitTemplateFEDTOCommand transformToSitTemplateFEDTOCommand;
    private GetRegionServersCommand getRegionServersCommand;
    private TransformToRegionServerFrontEndDTO transformToRegionServerFrontEndDTO;
    private TransformToEvidenceTransmissionStrategy transformToEvidenceTransmissionStrategy;
    private SaveEvidenceTransmissionStrategyCommand saveEvidenceTransmissionStrategy;
    private GetRegionServerByIdCommand getRegionServerByIdCommand;
    private DeleteRegionServerCommand deleteRegionServerCommand;
    private TransformToRegionServerCommand transformToRegionServerCommand;
    private SaveOrUpdateRegionServerCommand saveOrUpdateRegionServerCommand;
    private static Logger log = Logger.getLogger(CorporateStructureManager.class);
    /**
     * para manejo de envio de EPC
     */
    private boolean sendExecutionEPC;
    private boolean sendEpcToPast;
    private String sendExecutionEPCMessage;
    private Integer sendExecutionEPCStatus;
    private Double maxCiclos;
    private GetCantidadDetailsCommand cantidadDetails;
    private GetCantidadDetailsByIdsCommand cantidadDetailsByIdsCommand;
    private SaveSendExtractionPlanProcessCommand saveSendExtractionPlanProcessCommand;
    private SynchronizeToSecurityCommand synchronizeToSecurityCommand;
    private GetSensorAndEvidenceExtractionServicesServerListCommand sensorAndEvidenceExtractionServicesServerListCommand;
    private GetTransmStgiesByCriteriaCommand getTransmStgiesByCriteriaCommand;
    private TransformToEvidenceTransmStrategyFEDTOCommand transformToEvidenceTransmStrategyFEDTOCommand;
    private DeleteEvidenceTransmissionStrategyCommand deleteEvidenceTransmissionStrategyCommand;
    private FindEvTransmStgiesByIdCommand findEvTransmStgiesByIdCommand;
    private DeleteMultipleEvTransmStrs deleteMultipleEvTransmStrs;
    private CreateOrEditEvidenceTransmissionStrategies createOrEditEvidenceTransmissionStrategies;
    private PropertiesConfiguration configuration;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            log.info("Initializing ExtractionPlanWebServiceslient");
            // initialize web services
            List<EvidenceServicesServer> evidenceServicesServers = HibernateSupport.getInstance().findGenericDAO().
                    getAll(EvidenceServicesServer.class);
            Map<String, String> servers = new HashMap<String, String>();

            for (EvidenceServicesServer evidenceServicesServer : evidenceServicesServers) {
                servers.put(evidenceServicesServer.getUrl(), evidenceServicesServer.getUrl());
            }

//            SpringSupport.getInstance().findBeanByClassName(EvidenceServicesWebServiceClientImpl.class).
            getWebServiceClient().initializeClients(servers);

            //EvidenceServicesWebServiceClientImpl.getInstance().initializeClients(servers);
        } catch (Exception ex) {
            log.warn("Error initializing Web services  for Evidence Services Servers " + ex, ex);
        }

    }

    /**
     *
     * @param evidenceExtractionServicesServer
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void addEvidenceExtractionServicesServer(EvidenceExtractionServicesServer evidenceExtractionServicesServer,
            long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId,
                CorporateStructureManagerPermissions.ADD_EVIDENCE_EXTRACTION_SERVER_PERMISSION);

        if (evidenceExtractionServicesServer == null) {
            //"periscopeexception.entity.validate.notNullEntity", 
            throw new ScopixException("label.evidenceExtractionServicesServer");
        }

        AddEvidenceExtractionServicesServerCommand command = new AddEvidenceExtractionServicesServerCommand();
        command.execute(evidenceExtractionServicesServer);
        log.debug("finish");
    }

    /**
     *
     * @param evidenceProvider
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void addEvidenceProvider(EvidenceProvider evidenceProvider, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.ADD_EVIDENCE_PROVIDER_PERMISSION);

        if (evidenceProvider == null) {
            //"periscopeexception.entity.validate.notNullEntity"
            throw new ScopixException("label.evidenceProvider");
        }

        AddEvidenceProviderCommand command = new AddEvidenceProviderCommand();
        command.execute(evidenceProvider);
        log.debug("finish");
    }

    /**
     *
     * @param sensor
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void addSensor(Sensor sensor, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.ADD_SENSOR_PERMISSION);

        if (sensor == null) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.sensor");
        }

        AddSensorCommand command = new AddSensorCommand();
        command.execute(sensor);
        log.debug("finish");
    }

    /**
     *
     * @param evidenceProviderType
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void addEvidenceProviderType(EvidenceProviderType evidenceProviderType, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.ADD_EVIDENCE_PROVIDER_TYPE_PERMISSION);

        if (evidenceProviderType == null) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{                
            throw new ScopixException("label.evidenceProviderType");
        }

        AddEvidenceProviderTypeCommand command = new AddEvidenceProviderTypeCommand();
        command.execute(evidenceProviderType);
        log.debug("finish");
    }

    /**
     *
     * @param evidenceServicesServer
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void addEvidenceServicesServer(EvidenceServicesServer evidenceServicesServer, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.ADD_EVIDENCE_SERVER_PERMISSION);

        if (evidenceServicesServer == null) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.evidenceServicesServer");
        }

        AddEvidenceServicesServerCommand command = new AddEvidenceServicesServerCommand();
        command.execute(evidenceServicesServer);
        log.debug("finish");
    }

    /**
     *
     * @param corporate
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void addCorporate(Corporate corporate, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.ADD_CORPORATE_PERMISSION);

        if (corporate == null) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.corporate");
        }

        AddCorporateCommand command = new AddCorporateCommand();
        command.execute(corporate);
        log.debug("finish");
    }

    /**
     *
     * @param store
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void addStore(Store store, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.ADD_STORE_PERMISSION);
        if (store == null) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.store");
        }

        AddStoreCommand command = new AddStoreCommand();
        command.execute(store);
        log.debug("finish");
    }

    /**
     *
     * @param area
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void addArea(Area area, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.ADD_AREA_PERMISSION);

        if (area == null) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.area");
        }

        AddAreaCommand command = new AddAreaCommand();
        command.execute(area);
        log.debug("finish");
    }

    /**
     *
     * @param areaType
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void addAreaType(AreaType areaType, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.ADD_AREATYPE_PERMISSION);

        if (areaType == null) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.areaType");
        }

        AddAreaTypeCommand command = new AddAreaTypeCommand();
        command.execute(areaType);
        log.debug("finish");
    }

    /**
     * Add a region entity.
     *
     * @param region Region object
     * @param sessionId Valid sessionId of a logged user.
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void addRegion(Region region, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.ADD_REGION_PERMISSION);

        if (region == null) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.region");
        }

        AddRegionCommand command = new AddRegionCommand();
        command.execute(region);
        log.debug("finish");
    }

    /**
     * Add a new country.
     *
     * @param country Country object
     * @param sessionId Valid sessionId of a logged user.
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void addCountry(Country country, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.ADD_COUNTRY_PERMISSION);

        if (country == null) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.country");
        }

        AddCountryCommand command = new AddCountryCommand();
        command.execute(country);
        log.debug("finish");
    }

    /**
     *
     * @param idEvidenceExtractionServicesServer ID de EESS
     * @param sessionId ID de sesion
     * @return Retorna un EESS
     * @throws ScopixException Excepcion de Scopix
     */
    public EvidenceExtractionServicesServer getEvidenceExtractionServicesServer(Integer idEvidenceExtractionServicesServer,
            long sessionId)
            throws ScopixException {
        log.debug("start [idEvidenceExtractionServicesServer:" + idEvidenceExtractionServicesServer + "]");
        getSecurityManager().checkSecurity(sessionId,
                CorporateStructureManagerPermissions.GET_EVIDENCE_EXTRACTION_SERVER_PERMISSION);

        EvidenceExtractionServicesServer evidenceExtractionServicesServer = getEvidenceExtractionServicesServerCommand().execute(idEvidenceExtractionServicesServer);

        log.debug("finish");
        return evidenceExtractionServicesServer;
    }

    public List<EvidenceExtractionServicesServerDTO> getEvidenceExtractionServicesServerListForStores(
            List<String> stores, long sessionId)
            throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId,
                CorporateStructureManagerPermissions.GET_LIST_EVIDENCE_EXTRACTION_SERVER_PERMISSION);

        GetEvidenceExtractionServicesServerListForStoresCommand command = new GetEvidenceExtractionServicesServerListForStoresCommand();
        List<EvidenceExtractionServicesServerDTO> evidenceExtractionServicesServersList = command.execute(stores);

        log.info("finish");
        return evidenceExtractionServicesServersList;
    }

    /**
     *
     * @param evidenceExtractionServicesServer
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<EvidenceExtractionServicesServer> getEvidenceExtractionServicesServers(
            EvidenceExtractionServicesServer evidenceExtractionServicesServer, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId,
                CorporateStructureManagerPermissions.GET_LIST_EVIDENCE_EXTRACTION_SERVER_PERMISSION);

        GetEvidenceExtractionServicesServerListCommand command = new GetEvidenceExtractionServicesServerListCommand();
        List<EvidenceExtractionServicesServer> evidenceExtractionServicesServers = command.execute(
                evidenceExtractionServicesServer);

        log.debug("finish");
        return evidenceExtractionServicesServers;
    }

    /**
     *
     * @param evidenceExtractionServicesServer
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<EvidenceExtractionServicesServer> getFreeEvidenceExtractionServicesServers(
            EvidenceExtractionServicesServer evidenceExtractionServicesServer, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId,
                CorporateStructureManagerPermissions.GET_FREE_LIST_EVIDENCE_EXTRACTION_SERVER_PERMISSION);

        GetFreeEvidenceExtractionServicesServerListCommand command = new GetFreeEvidenceExtractionServicesServerListCommand();
        List<EvidenceExtractionServicesServer> evidenceExtractionServicesServers = command.execute(
                evidenceExtractionServicesServer);

        log.debug("finish");
        return evidenceExtractionServicesServers;
    }

    /**
     *
     * @param idEvidenceProvider
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public EvidenceProvider getEvidenceProvider(int idEvidenceProvider, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_EVIDENCE_PROVIDER_PERMISSION);

        EvidenceProvider evidenceProvider = getEvidenceProvider(idEvidenceProvider);
        log.debug("finish");
        return evidenceProvider;
    }

    public EvidenceProvider getEvidenceProvider(int idEvidenceProvider) throws ScopixException {
        log.debug("start");

        GetEvidenceProviderCommand command = new GetEvidenceProviderCommand();
        EvidenceProvider evidenceProvider = command.execute(idEvidenceProvider);

        log.debug("finish");
        return evidenceProvider;
    }

    /**
     *
     * @param idSensor
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public Sensor getSensor(int idSensor, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_SENSOR_PERMISSION);

        GetSensorCommand command = new GetSensorCommand();
        Sensor sensor = command.execute(idSensor);

        log.debug("finish");
        return sensor;
    }

    /**
     *
     * @param idSensor
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public Sensor getSensor(String sensorName, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_SENSOR_PERMISSION);

        GetSensorCommand command = new GetSensorCommand();
        Sensor sensor = command.execute(sensorName);

        log.debug("finish");
        return sensor;
    }

    /**
     *
     * @param evidenceProvider
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<EvidenceProvider> getEvidenceProviderList(EvidenceProvider evidenceProvider, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_LIST_EVIDENCE_PROVIDER_PERMISSION);

        GetEvidenceProviderListCommand command = new GetEvidenceProviderListCommand();
        List<EvidenceProvider> evidenceProviders = command.execute(evidenceProvider);

        log.debug("finish");
        return evidenceProviders;
    }

    public List<EvidenceProvider> getEvidenceProviderListByArea(EvidenceProvider evidenceProvider, long sessionId)
            throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_LIST_EVIDENCE_PROVIDER_PERMISSION);

        GetEvidenceProviderListByAreaCommand command = new GetEvidenceProviderListByAreaCommand();
        List<EvidenceProvider> evidenceProviders = command.execute(evidenceProvider);

        log.info("finish");
        return evidenceProviders;
    }

    /**
     *
     * @param sensor
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<Sensor> getSensorList(Sensor sensor, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_SENSOR_LIST_PERMISSION);

        GetSensorListCommand command = new GetSensorListCommand();
        List<Sensor> sensors = command.execute(sensor);

        log.debug("finish");
        return sensors;
    }

    /**
     *
     * @param idEvidenceProviderType
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public EvidenceProviderType getEvidenceProviderType(int idEvidenceProviderType, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_EVIDENCE_PROVIDER_TYPE_PERMISSION);

        GetEvidenceProviderTypeCommand command = new GetEvidenceProviderTypeCommand();
        EvidenceProviderType evidenceProviderType = command.execute(idEvidenceProviderType);

        log.debug("finish");
        return evidenceProviderType;
    }

    /**
     *
     * @param ept
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<EvidenceProviderType> getEvidenceProviderTypeList(EvidenceProviderType ept, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId,
                CorporateStructureManagerPermissions.GET_LIST_EVIDENCE_PROVIDER_TYPE_PERMISSION);

        GetEvidenceProviderTypeListCommand command = new GetEvidenceProviderTypeListCommand();
        List<EvidenceProviderType> evidenceProviderTypes = command.execute();

        log.debug("finish");
        return evidenceProviderTypes;
    }

    /**
     *
     * @param idEvidenceServicesServer
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public EvidenceServicesServer getEvidenceServicesServer(int idEvidenceServicesServer, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_EVIDENCE_SERVER_PERMISSION);

        EvidenceServicesServer evidenceServicesServer = getEvidenceServicesServer(idEvidenceServicesServer);
        log.debug("finish");
        return evidenceServicesServer;
    }

    public EvidenceServicesServer getEvidenceServicesServer(int idEvidenceServicesServer) throws ScopixException {
        log.debug("start");

        GetEvidenceServicesServerCommand command = new GetEvidenceServicesServerCommand();
        EvidenceServicesServer evidenceServicesServer = command.execute(idEvidenceServicesServer);

        log.debug("finish");
        return evidenceServicesServer;
    }

    /**
     *
     * @param evidenceServicesServer
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<EvidenceServicesServer> getEvidenceServicesServers(EvidenceServicesServer evidenceServicesServer, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_LIST_EVIDENCE_SERVER_PERMISSION);

        GetEvidenceServicesServerListCommand command = new GetEvidenceServicesServerListCommand();
        List<EvidenceServicesServer> evidenceServicesServers = command.execute(evidenceServicesServer);

        log.debug("finish");
        return evidenceServicesServers;
    }

    /**
     *
     * @param idStore
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public Store getStore(int idStore, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_STORE_PERMISSION);

        GetStoreCommand command = new GetStoreCommand();
        Store store = command.execute(idStore);

        log.debug("finish");
        return store;
    }

    /**
     *
     * @param store
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<Store> getStoreList(Store store, long sessionId) throws ScopixException {
        log.debug("start");
        try {
            getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_LIST_STORE_PERMISSION);
        } catch (ScopixException pe) {
            throw new ScopixException(pe.getMessage(), pe);
        }

        List<Store> stores = getStoreListCommand().execute(store);

        log.debug("finish");
        return stores;
    }

    /**
     *
     * @param store
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<Store> getStoreList(Store store) throws ScopixException {
        log.debug("start");
        List<Store> stores = getStoreListCommand().execute(store);
        log.debug("finish");
        return stores;
    }

    public List<StoreDTO> getStoreDTOs(Store store, long sessionId) throws ScopixException {
        List<Store> list = getStoreList(store, sessionId);

        List<StoreDTO> storeDTOs = getTransformStoreToDTOCommand().execute(list);
        return storeDTOs;

    }

    public List<StoreDTO> getStoreDTOs(Store store) throws ScopixException {
        List<Store> list = getStoreList(store);

        List<StoreDTO> storeDTOs = getTransformStoreToDTOCommand().execute(list);
        return storeDTOs;

    }

    public List<StoreFrontEndDTO> getStoreFrontEndDTOs() throws ScopixException {
        List<Store> list = getStoreList(null);
        List<StoreFrontEndDTO> storeDTOs = getTransformToStoreFrontEndDTO().execute(list);
        return storeDTOs;
    }

    /**
     *
     * @param idArea
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public Area getArea(int idArea, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_AREA_PERMISSION);

        GetAreaCommand command = new GetAreaCommand();
        Area area = command.execute(idArea);

        log.debug("finish");
        return area;
    }

    /**
     *
     * @param area
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<Area> getAreaList(Area area, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_LIST_AREA_PERMISSION);

        GetAreaListCommand command = new GetAreaListCommand();
        List<Area> areas = command.execute(area);

        log.debug("finish");
        return areas;
    }

    /**
     *
     * @param idAreaType
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public AreaType getAreaType(int idAreaType, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_AREATYPE_PERMISSION);

        GetAreaTypeCommand command = new GetAreaTypeCommand();
        AreaType areaType = command.execute(idAreaType);

        log.debug("finish");
        return areaType;
    }

    /**
     *
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public Corporate getCorporate(long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_CORPORATE_PERMISSION);

        GetCorporateCommand command = new GetCorporateCommand();
        Corporate corporate = command.execute();

        log.debug("finish");
        return corporate;
    }

    /**
     * This method is for obtain the corporate id without security
     *
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public Integer getCorporateId() throws ScopixException {
        log.debug("start");
        GetCorporateCommand command = new GetCorporateCommand();
        Corporate corporate = command.execute();

        log.debug("finish");
        return corporate.getUniqueCorporateId();
    }

    public String getCorporateName() throws ScopixException {
        log.debug("start");
        GetCorporateCommand command = new GetCorporateCommand();
        Corporate corporate = command.execute();

        log.debug("finish");
        return corporate.getName();
    }

    /**
     *
     * @param idRegion
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public Region getRegion(int idRegion, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_REGION_PERMISSION);

        GetRegionCommand command = new GetRegionCommand();
        Region region = command.execute(idRegion);

        log.debug("finish");
        return region;
    }

    /**
     *
     * @param region
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<Region> getRegionList(Region region, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_REGION_LIST_PERMISSION);

        GetRegionListCommand command = new GetRegionListCommand();
        List<Region> regions = command.execute(region);

        log.debug("finish");
        return regions;
    }

    /**
     * Get one country.
     *
     * @param idCountry Country id.
     * @param sessionId Valid sessionId of a logged user.
     * @return Country
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public Country getCountry(int idCountry, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_COUNTRY_PERMISSION);

        GetCountryCommand command = new GetCountryCommand();
        Country country = command.execute(idCountry);

        log.debug("finish");
        return country;
    }

    /**
     *
     * @param country Country object.
     * @param sessionId Valid sessionId of a logged user.
     * @return List<Country>
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<Country> getCountryList(Country country, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_COUNTRY_LIST_PERMISSION);

        GetCountryListCommand command = new GetCountryListCommand();
        List<Country> countries = command.execute(country);

        log.debug("finish");
        return countries;
    }

    /**
     *
     * @param idEvidenceExtractionServicesServer
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void removeEvidenceExtractionServicesServer(int idEvidenceExtractionServicesServer, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId,
                CorporateStructureManagerPermissions.REMOVE_EVIDENCE_EXTRACTION_SERVER_PERMISSION);

        RemoveEvidenceExtractionServicesServerCommand command = new RemoveEvidenceExtractionServicesServerCommand();
        command.execute(idEvidenceExtractionServicesServer);
        log.debug("finish");
    }

    /**
     *
     * @param idEvidenceProvider
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void removeEvidenceProvider(int idEvidenceProvider, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.REMOVE_EVIDENCE_PROVIDER_PERMISSION);

        RemoveEvidenceProviderCommand command = new RemoveEvidenceProviderCommand();
        command.execute(idEvidenceProvider);
        log.debug("finish");
    }

    /**
     *
     * @param idSensor
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void removeSensor(int idSensor, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.REMOVE_SENSOR_PERMISSION);

        RemoveSensorCommand command = new RemoveSensorCommand();
        command.execute(idSensor);
        log.debug("finish");
    }

    /**
     *
     * @param idEvidenceProviderType
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void removeEvidenceProviderType(int idEvidenceProviderType, long sessionId) throws ScopixException,
            ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId,
                CorporateStructureManagerPermissions.REMOVE_EVIDENCE_PROVIDER_TYPE_PERMISSION);

        RemoveEvidenceProviderTypeCommand command = new RemoveEvidenceProviderTypeCommand();
        command.execute(idEvidenceProviderType);
        log.debug("finish");
    }

    /**
     *
     * @param idEvidenceServicesServer
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void removeEvidenceServicesServer(int idEvidenceServicesServer, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.REMOVE_EVIDENCE_SERVER_PERMISSION);

        RemoveEvidenceServicesServerCommand command = new RemoveEvidenceServicesServerCommand();
        command.execute(idEvidenceServicesServer);
        log.debug("finish");
    }

    /**
     *
     * @param idStore
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void removeStore(int idStore, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.REMOVE_STORE_PERMISSION);

        RemoveStoreCommand command = new RemoveStoreCommand();
        command.execute(idStore);
        log.debug("finish");
    }

    /**
     *
     * @param idArea
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void removeArea(int idArea, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.REMOVE_AREA_PERMISSION);

        RemoveAreaCommand command = new RemoveAreaCommand();
        command.execute(idArea);
        log.debug("finish");
    }

    /**
     *
     * @param idAreaType
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void removeAreaType(int idAreaType, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.REMOVE_AREATYPE_PERMISSION);

        RemoveAreaTypeCommand command = new RemoveAreaTypeCommand();
        command.execute(idAreaType);
        log.debug("finish");
    }

    /**
     *
     * @param idRegion
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void removeRegion(int idRegion, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.REMOVE_REGION_PERMISSION);

        RemoveRegionCommand command = new RemoveRegionCommand();
        command.execute(idRegion);

        log.debug("finish");
    }

    /**
     *
     * @param idCountry Country id
     * @param sessionId Valid sessionId of a logged user.
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void removeCountry(int idCountry, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.REMOVE_COUNTRY_PERMISSION);

        RemoveCountryCommand command = new RemoveCountryCommand();
        command.execute(idCountry);

        log.debug("finish");
    }

    /**
     *
     * @param corporate
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void updateCorporate(Corporate corporate, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.UPDATE_CORPORATE_PERMISSION);

        if (corporate == null || corporate.getId() == null || corporate.getId() <= 0) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.corporate");
        }

        UpdateCorporateCommand command = new UpdateCorporateCommand();
        command.execute(corporate);
        log.debug("finish");
    }

    /**
     *
     * @param areaType
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void updateAreaType(AreaType areaType, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.UPDATE_AREATYPE_PERMISSION);

        if (areaType == null || areaType.getId() == null || areaType.getId() <= 0) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.areaType");
        }

        UpdateAreaTypeCommand command = new UpdateAreaTypeCommand();
        command.execute(areaType);
        log.debug("finish");
    }

    /**
     *
     * @param areaType
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<AreaType> getAreaTypeList(AreaType areaType, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_LIST_AREATYPE_PERMISSION);

        GetAreaTypeListCommand command = new GetAreaTypeListCommand();
        List<AreaType> areaTypes = command.execute(areaType);

        log.debug("finish");
        return areaTypes;
    }

    /**
     *
     * @param area
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void updateArea(Area area, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.UPDATE_AREA_PERMISSION);

        if (area == null || area.getId() == null || area.getId() <= 0) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.area");
        }

        UpdateAreaCommand command = new UpdateAreaCommand();
        command.execute(area);
        log.debug("finish");
    }

    /**
     *
     * @param store
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void updateStore(Store store, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.UPDATE_STORE_PERMISSION);

        if (store == null || store.getId() == null || store.getId() <= 0) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.store");
        }

        UpdateStoreCommand command = new UpdateStoreCommand();
        command.execute(store);
        log.debug("finish");
    }

    /**
     *
     * @param server
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void updateEvidenceServicesServer(EvidenceServicesServer server, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.UPDATE_EVIDENCE_SERVER_PERMISSION);

        if (server == null || server.getId() == null || server.getId() <= 0) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.evidenceServicesServer");
        }

        UpdateEvidenceServicesServerCommand command = new UpdateEvidenceServicesServerCommand();
        command.execute(server);
        log.debug("finish");
    }

    /**
     *
     * @param server
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void updateEvidenceExtractionServicesServer(EvidenceExtractionServicesServer server, long sessionId)
            throws ScopixException {
        log.info("start");
        getSecurityManager().checkSecurity(sessionId,
                CorporateStructureManagerPermissions.UPDATE_EVIDENCE_EXTRACTION_SERVER_PERMISSION);

        if (server == null || server.getId() == null || server.getId() <= 0) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.evidenceExtractionServicesServer");
        }

        UpdateEvidenceExtractionServicesServerCommand command = new UpdateEvidenceExtractionServicesServerCommand();
        command.execute(server);
        Map<String, String> servers = new HashMap<String, String>();
        servers.put(server.getUrl(), server.getUrl());

        getWebServiceClient().initializeClients(servers);
        log.info("end");
    }

    /**
     *
     * @param evidenceServicesServerId
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void sendExtractionPlanToEES(Integer evidenceExtractionServicesServerId, List<Integer> epcIds, long sessionId,
            Store store) throws ScopixException {
        log.info("start");

        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.SEND_EXTRACTION_PLAN);

        EvidenceExtractionServicesServer evidenceExtractionServicesServer = getEvidenceExtractionServicesServer(
                evidenceExtractionServicesServerId, sessionId);

        EvidenceServicesWebService extractionPlanWebService = getWebServiceClient().
                getWebServiceClient(evidenceExtractionServicesServer.getEvidenceServicesServer().getUrl());

        //Turn off chunking so that NTLM can occur
        Client client = ClientProxy.getClient(extractionPlanWebService);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(0);
        httpClientPolicy.setReceiveTimeout(0);
        httpClientPolicy.setAllowChunking(false);
        http.setClient(httpClientPolicy);

        getExtractionPlanManager().setCurrentCreateRecord(0);
        if (epcIds != null && !epcIds.isEmpty()) {

            List<ExtractionPlanCustomizing> extractionPlanCustomizings = getExtractionPlanCustomizingListByIdsCommand().execute(epcIds);
            //validamos todos los epc antes de continuar
            for (ExtractionPlanCustomizing planCustomizing : extractionPlanCustomizings) {
                if (!isSendable(planCustomizing)) {
                    throw new ScopixException("EPC_NO_VALID_SEND"); //al menos uno de los epc no validos para envio
                }
            }

            int cant = getCantidadDetailsByIdsCommand().execute(epcIds, store.getId());

            setMaxCiclos(calculaMaxCiclos(cant));
            for (ExtractionPlanCustomizing planCustomizing : extractionPlanCustomizings) {
                //generamos los request
                validateAndGenerateRecords(planCustomizing, sessionId);
            }
        } else {
            //recuperamos la lista de todos los epc.isActive null
            ExtractionPlanCustomizing epc = new ExtractionPlanCustomizing();
            epc.setStore(store);
            List<ExtractionPlanCustomizing> extractionPlanCustomizings = getExtractionPlanCustomizingListCommand().execute(epc,
                    GetExtractionPlanCustomizingListCommand.EDITION);
            //validamos todos los epc antes de continuar
            for (ExtractionPlanCustomizing planCustomizing : extractionPlanCustomizings) {
                if (!isSendable(planCustomizing)) {
                    throw new ScopixException("EPC_NO_VALID_SEND"); //epc no valido para envio
                }
            }
            int cant = getCantidadDetails().execute(null, epc.getStore().getId());
            setMaxCiclos(calculaMaxCiclos(cant));
            for (ExtractionPlanCustomizing planCustomizing : extractionPlanCustomizings) {
                //generamos los request
                validateAndGenerateRecords(planCustomizing, sessionId);
            }
        }

        getSendExtractionPlanCommand().execute(evidenceExtractionServicesServer, extractionPlanWebService, store);

        log.info("end");
    }

    private void validateAndGenerateRecords(ExtractionPlanCustomizing epc, long sessionId)
            throws ScopixException, ScopixException {
        inactiveEPC(epc, sessionId);

        //se debe verificar si el EPC a enviar esta con random de camaras
        //si el SituationTemplate no esta activo se aborta la operacion y se inactiva el epc
        if (epc.getSituationTemplate().getActive().equals(Boolean.FALSE)) {
            epc.setActive(Boolean.FALSE);
        } else if (epc.isRandomCamera()) {//si el epc es con random de camaras debemos dejar este como activo solamente
            getExtractionPlanManager().activateEPC(epc);
        } else {
            getExtractionPlanManager().generate(epc, sessionId);
        }
    }

    private Double calculaMaxCiclos(Integer base) {
        return new Double((100 * new Double(base.toString())) / 90);
    }

    /**
     *
     * @param evidenceProvider
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void updateEvidenceProvider(EvidenceProvider evidenceProvider, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.UPDATE_EVIDENCE_PROVIDER_PERMISSION);

        if (evidenceProvider == null || evidenceProvider.getId() == null || evidenceProvider.getId() <= 0) {
            //"periscopeexception.entity.validate.notNullEntity",new String[]{
            throw new ScopixException("label.evidenceProvider");
        }
        UpdateEvidenceProviderCommand command = new UpdateEvidenceProviderCommand();
        command.execute(evidenceProvider);
        log.debug("finish");
    }

    /**
     *
     * @param sensor
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void updateSensor(Sensor sensor, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.UPDATE_SENSOR_PERMISSION);

        if (sensor == null || sensor.getId() == null || sensor.getId() <= 0) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.sensor");
        }
        UpdateSensorCommand command = new UpdateSensorCommand();
        command.execute(sensor);
        log.debug("finish");
    }

    /**
     * Update a Region
     *
     * @param region Region object
     * @param sessionId Valid sessionId of a logged user.
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void updateRegion(Region region, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.UPDATE_REGION_PERMISSION);

        if (region == null || region.getId() == null || region.getId() <= 0) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.region");
        }

        UpdateRegionCommand command = new UpdateRegionCommand();
        command.execute(region);

        log.debug("finish");
    }

    /**
     * Update a Country
     *
     * @param country Country object
     * @param sessionId Valid sessionId of a logged user.
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void updateCountry(Country country, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.UPDATE_COUNTRY_PERMISSION);

        if (country == null || country.getId() == null || country.getId() <= 0) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.country");
        }

        UpdateCountryCommand command = new UpdateCountryCommand();
        command.execute(country);

        log.debug("finish");
    }

    public List<EvidenceRequest> sendExtractionPlanToPast(Integer storeId, Date date, long sessionId) throws
            ScopixException, ScopixException {
        log.debug("start, date = " + date);
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.EXTRACTION_PLAN_TO_PAST_PERMISSION);
        SendExtractionPlanToPastCommand command = new SendExtractionPlanToPastCommand();
        List<EvidenceRequest> evidenceRequests = command.execute(storeId, date);
        log.debug("finish, evidenceRequests = " + evidenceRequests);
        return evidenceRequests;
    }

    public void sendExtractionPlanToPastCron() {
        log.info("start [SendEpcToPast:" + isSendEpcToPast() + "]");
        Integer count = 0;
        if (!isSendEpcToPast()) {
            try {
                setSendEpcToPast(true);
                int maxDayEpcToPat = getConfiguration().getInt("max_day_epc_to_past", 1);
                log.debug("[max_day_epc_to_past:" + maxDayEpcToPat + "]");
                for (int day = 1; day <= maxDayEpcToPat; day++) {
                    Date date = new Date();
                    date = DateUtils.addDays(date, -1 * day);
                    GetSituationStoreDayCommand command1 = new GetSituationStoreDayCommand();
                    List<StorePlan> storePlanSituation = command1.execute(date);
                    GetSituationStoreDayReadyCommand command2 = new GetSituationStoreDayReadyCommand();
                    List<StorePlan> storeSituationReady = command2.execute(date);

                    boolean existsStore;
                    for (StorePlan storePlan : storePlanSituation) {
                        existsStore = false;
                        for (StorePlan storePlanReady : storeSituationReady) {
                            if (storePlan.getStoreId().equals(storePlanReady.getStoreId())) {
                                existsStore = true;
                                if (storePlan.getSituationName().equals(storePlanReady.getSituationName())) {
                                    if (!storePlan.getCount().equals(storePlanReady.getCount())) {
                                        count = requestEvidenceToPast(storePlan, date, count);
                                    }
                                }
                                break;
                            }
                        }
                        if (!existsStore) {
                            count = requestEvidenceToPast(storePlan, date, count);
                        }
                    }
                }

            } catch (Exception e) {
                log.error(e, e);
            } finally {
                setSendEpcToPast(false);
            }
            log.info("end [evidence requested:" + count + "]");
        }
    }

    private Integer requestEvidenceToPast(StorePlan storePlan, Date date, Integer count) {
        log.info("start [storePlan:" + storePlan.getName() + "][date:" + date + "]");
        SendExtractionPlanToPastCommand command = new SendExtractionPlanToPastCommand();
        try {
            List<EvidenceRequest> evidenceRequests = command.execute(storePlan.getStoreId(), date);
            if (evidenceRequests != null && !evidenceRequests.isEmpty()) {
                log.debug("Request for store [" + storePlan.getName() + ":" + evidenceRequests.size()
                        + "]");
                count = count + evidenceRequests.size();
            }
        } catch (ScopixException e) {
            log.error("Error sendExtractionPlanToPastCron for store "
                    + storePlan.getName() + " e:" + e, e);
        }
        log.info("end");
        return count;
    }

    /**
     * Save the template file for the evidence provider selected
     *
     * @param file
     * @param storeId
     * @param ep
     * @param extension
     * @return
     * @throws ScopixException
     */
    public String saveTemplateFileOnDisk(File file, Integer storeId, EvidenceProvider ep, String extension, SituationTemplate st)
            throws ScopixException {
        log.info("start");
        JCIFSUtil vfsUtil = SpringSupport.getInstance().findBeanByClassName(JCIFSUtil.class);
        GetStoreCommand getStoreCommand = new GetStoreCommand();
        Store store = getStoreCommand.execute(storeId);

        //String separator = (store.getEvidenceServicesServer().getEvidencePath().indexOf("/") >= 0) ? "/" : "\\";
        String separator = "/";
        String path = store.getEvidenceServicesServer().getEvidencePath();
        path += store.getCorporate().getName() + separator + store.getName() + separator + SystemConfig.getStringParameter(
                "TEMPLATES_FOLDER");
        log.debug("path:" + path);
        vfsUtil.mkDirSmb(path);
        String name = ep.getDescription() + "_" + ep.getId() + "_" + st.getId() + extension;
        path += separator + name;
        vfsUtil.createFileSmb(file, FilenameUtils.separatorsToSystem(path));
        log.info("end name:" + name);
        return name;
    }

    public String saveCorporateLogo(File file, String extension, long sessionId) throws ScopixException {
        UpdateCorporateCommand updateCorporateCommand = new UpdateCorporateCommand();

        JCIFSUtil vfsUtil = SpringSupport.getInstance().findBeanByClassName(JCIFSUtil.class);
        GetCorporateCommand command = new GetCorporateCommand();
        Corporate corporate = command.execute();
        String path = SystemConfig.getStringParameter("CORPORATE_PATH");
        String fileName = corporate.getName() + extension;

        if (corporate.getLogo() != null && corporate.getLogo().length() > 0) {
            vfsUtil.deleteFile(path + corporate.getLogo());
        }
        vfsUtil.createFileSmb(file, path + fileName);
        corporate.setLogo(fileName);
        updateCorporateCommand.execute(corporate);

        return fileName;
    }

    public Map getLogoContent() throws ScopixException {

        JCIFSUtil vfsUtil = SpringSupport.getInstance().findBeanByClassName(JCIFSUtil.class);
        String path = this.getLogoPath();
        Map result = vfsUtil.getFileSmb(path);
        return result;
    }

    public String getLogoPath() throws ScopixException {
        GetCorporateCommand command = new GetCorporateCommand();
        Corporate corporate = command.execute();
        String path;

        if (corporate.getLogo() != null && corporate.getLogo().length() > 0) {
            path = SystemConfig.getStringParameter("CORPORATE_PATH") + corporate.getLogo();
        } else {
            path = SystemConfig.getStringParameter("CORPORATE_PATH") + SystemConfig.getStringParameter("TRANSPARENT_IMAGE");
        }

        return path;
    }

    public void updateRelationEvidenceProviderLocation(Integer evidenceProviderId,
            List<RelationEvidenceProviderLocation> evidenceProviderLocations,
            long sessionId) throws ScopixException,
            ScopixException {
        getSecurityManager().checkSecurity(sessionId,
                CorporateStructureManagerPermissions.UPDATE_RELATION_EVIDENCE_PROVIDER_LOCATION);
        if (evidenceProviderLocations != null) {
            UpdateRelationEvidenceProviderLocationCommand command = new UpdateRelationEvidenceProviderLocationCommand();
            command.execute(evidenceProviderId, evidenceProviderLocations);
        }
    }

    public SecurityManager getSecurityManager() {
        if (securityManager == null) {
            securityManager = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        }

        return securityManager;
    }

    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    public GetStoreListCommand getStoreListCommand() {
        if (storeListCommand == null) {
            storeListCommand = new GetStoreListCommand();
        }
        return storeListCommand;
    }

    public void setStoreListCommand(GetStoreListCommand storeListCommand) {
        this.storeListCommand = storeListCommand;
    }

    public TransformStoreToDTOCommand getTransformStoreToDTOCommand() {
        if (transformStoreToDTOCommand == null) {
            transformStoreToDTOCommand = new TransformStoreToDTOCommand();
        }
        return transformStoreToDTOCommand;
    }

    public void setTransformStoreToDTOCommand(TransformStoreToDTOCommand transformStoreToDTOCommand) {
        this.transformStoreToDTOCommand = transformStoreToDTOCommand;
    }

    public GetExtractionPlanCustomizingListCommand getExtractionPlanCustomizingListCommand() {
        if (extractionPlanCustomizingListCommand == null) {
            extractionPlanCustomizingListCommand = new GetExtractionPlanCustomizingListCommand();
        }
        return extractionPlanCustomizingListCommand;
    }

    public void setExtractionPlanCustomizingListCommand(GetExtractionPlanCustomizingListCommand value) {
        this.extractionPlanCustomizingListCommand = value;
    }

    public GetEvidenceExtractionServicesServerCommand getEvidenceExtractionServicesServerCommand() {
        if (evidenceExtractionServicesServerCommand == null) {
            evidenceExtractionServicesServerCommand = new GetEvidenceExtractionServicesServerCommand();
        }
        return evidenceExtractionServicesServerCommand;
    }

    public void setEvidenceExtractionServicesServerCommand(GetEvidenceExtractionServicesServerCommand value) {
        this.evidenceExtractionServicesServerCommand = value;
    }

    public EvidenceServicesWebServiceClient getWebServiceClient() {
        if (webServiceClient == null) {
            webServiceClient = SpringSupport.getInstance().findBeanByClassName(EvidenceServicesWebServiceClient.class);
        }
        return webServiceClient;
    }

    public void setWebServiceClient(EvidenceServicesWebServiceClient webServiceClient) {
        this.webServiceClient = webServiceClient;
    }

    public ExtractionPlanManager getExtractionPlanManager() {
        if (extractionPlanManager == null) {
            extractionPlanManager = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class);
        }
        return extractionPlanManager;
    }

    public void setExtractionPlanManager(ExtractionPlanManager extractionPlanManager) {
        this.extractionPlanManager = extractionPlanManager;
    }

    public SendExtractionPlanCommand getSendExtractionPlanCommand() {
        if (sendExtractionPlanCommand == null) {
            sendExtractionPlanCommand = new SendExtractionPlanCommand();
        }
        return sendExtractionPlanCommand;
    }

    public void setSendExtractionPlanCommand(SendExtractionPlanCommand sendExtractionPlanCommand) {
        this.sendExtractionPlanCommand = sendExtractionPlanCommand;
    }

    public void sendExtractionPlanCustomizing(List<Integer> extractionPlanCustomizingIds, Integer storeId, long sessionId)
            throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.SEND_EXTRACTION_PLAN);

        /*
         * ExtractionPlanCustomizing epc = getExtractionPlanCustomizingByIdCommand().execute(extractionPlanCustomizingIds); if
         * (epc == null) { throw new ScopixException("NOT_EXIST_EPC_REQUESTED"); //no existe epc solicitado } //si epc activo
         * return true if (epc.isActive() != null && epc.isActive().equals(Boolean.TRUE)) { return; } if (epc.isActive() != null
         * && epc.isActive().equals(Boolean.FALSE)) { throw new ScopixException("EPC_INACTIVE"); //epc no valido para envio }
         */
        //recuperamos el Store por id
        if (storeId == null) {
            throw new ScopixException("NOT_STORE"); //no puede ser store null
        }
        Store store = getStoreCommand().execute(storeId);

        //recuperamos el evidenceExtractionServicesServerId asociado al epc.store
        Integer evidenceExtractionServicesServerId = store.getEvidenceExtractionServicesServer().getId();

        sendExtractionPlanToEES(evidenceExtractionServicesServerId, extractionPlanCustomizingIds, sessionId, store);
        log.debug("finish");
    }

    private Boolean isSendable(ExtractionPlanCustomizing epc) {
        boolean ret = true;
        ret = ret && !epc.getExtractionPlanMetrics().isEmpty();
        for (ExtractionPlanMetric epm : epc.getExtractionPlanMetrics()) {
            ret = ret && !epm.getEvidenceProviders().isEmpty();
        }
        ret = ret && !epc.getExtractionPlanRanges().isEmpty();
        ret = ret && (epc.isActive() == null);
        return ret;
    }

    public GetExtractionPlanCustomizingByIdCommand getExtractionPlanCustomizingByIdCommand() {
        if (extractionPlanCustomizingByIdCommand == null) {
            extractionPlanCustomizingByIdCommand = new GetExtractionPlanCustomizingByIdCommand();
        }
        return extractionPlanCustomizingByIdCommand;
    }

    public void setExtractionPlanCustomizingByIdCommand(GetExtractionPlanCustomizingByIdCommand value) {
        this.extractionPlanCustomizingByIdCommand = value;
    }

    public void sendExtractionPlanCustomizingFull(Integer storeId, long sessionId) throws ScopixException,
            ScopixException {

        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.SEND_EXTRACTION_PLAN);
        //recuperamos el Store por id
        if (storeId == null) {
            throw new ScopixException("NOT_STORE"); //no puede ser store null
        }
        Store store = getStoreCommand().execute(storeId);
        //recuperamos el evidenceExtractionServicesServerId asociado al epc.store
        Integer evidenceExtractionServicesServerId = store.getEvidenceExtractionServicesServer().getId();

        sendExtractionPlanToEES(evidenceExtractionServicesServerId, null, sessionId, store);
    }

    public GetStoreCommand getStoreCommand() {
        if (storeCommand == null) {
            storeCommand = new GetStoreCommand();
        }
        return storeCommand;
    }

    public void setStoreCommand(GetStoreCommand storeCommand) {
        this.storeCommand = storeCommand;
    }

    //deja Inactivo un epc En particular
    public void inactiveEPC(ExtractionPlanCustomizing epc, long sessionId) throws ScopixException {
        log.info("start");
        //recuperamos todos los epc enviados para el situtation Template Store que se esta intentando generar
        List<ExtractionPlanCustomizing> epcs = getExtractionPlanCustomizingListCommand().execute(epc,
                GetExtractionPlanCustomizingListCommand.SENT);

        for (ExtractionPlanCustomizing epcActivo : epcs) {
            if (!epcActivo.getId().equals(epc.getId())) {
                getExtractionPlanManager().inactivateExtractionPlanCustomizing(epcActivo.getId(), sessionId);
            }
        }
        log.info("end");
    }

    public boolean isSendExecutionEPC() {
        return sendExecutionEPC;
    }

    public void setSendExecutionEPC(boolean sendExecutionEPC) {
        this.sendExecutionEPC = sendExecutionEPC;
    }

    public StatusSendEPCDTO inicalizarEnvioThread(List<Integer> extractionPlanCustomizingIds, Integer storeId, long sessionId)
            throws ScopixException,
            ScopixException {
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.SEND_EXTRACTION_PLAN);
        setSendExecutionEPC(true);

        StatusSendEPCDTO statusSendEPCDTO = new StatusSendEPCDTO();
        SendEPCThread thread = new SendEPCThread();
        SendExtractionPlanProcess process = getSaveSendExtractionPlanProcessCommand().execute(sessionId, null,
                extractionPlanCustomizingIds);
        thread.init(extractionPlanCustomizingIds, storeId, sessionId, process.getId());
        thread.setName("EPC_PROCESS_" + process.getId());
        thread.start();
        statusSendEPCDTO.setStatus(-1);
        //sendExtractionPlanCustomizing(extractionPlanCustomizingId, sessionId);
        return statusSendEPCDTO;
    }

    public StatusSendEPCDTO inicalizarEnvioFullThread(Integer storeId, long sessionId) throws ScopixException,
            ScopixException {
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.SEND_EXTRACTION_PLAN);
        setSendExecutionEPC(true);

        SendExtractionPlanProcess process = getSaveSendExtractionPlanProcessCommand().execute(sessionId, storeId, null);
        SendEPCThread thread = new SendEPCThread();
        thread.init(null, storeId, sessionId, process.getId());
        thread.setName("FULL_EPC_STORE_" + storeId);
        thread.start();
        //sendExtractionPlanCustomizingFull(storeId, sessionId);
        StatusSendEPCDTO statusSendEPCDTO = new StatusSendEPCDTO();
        statusSendEPCDTO.setStatus(-1);
        return statusSendEPCDTO;
    }

    public String getSendExecutionEPCMessage() {
        return sendExecutionEPCMessage;
    }

    public void setSendExecutionEPCMessage(String sendExecutionEPCMessage) {
        this.sendExecutionEPCMessage = sendExecutionEPCMessage;
    }

    public Integer getSendExecutionEPCStatus() {
        return sendExecutionEPCStatus;
    }

    public void setSendExecutionEPCStatus(Integer sendExecutionEPCStatus) {
        this.sendExecutionEPCStatus = sendExecutionEPCStatus;
    }

    public Double getMaxCiclos() {
        return maxCiclos;
    }

    public void setMaxCiclos(Double value) {
        this.maxCiclos = value;
    }

    /**
     * genera un DTO con los datos de la ejecucion actual
     */
    public StatusSendEPCDTO getStatusSendEPCExecutionDTO(long sessionId) {
        log.debug("current=" + getExtractionPlanManager().getCurrentCreateRecord() + " max=" + getMaxCiclos()
                + " msg=" + getSendExecutionEPCMessage() + " status=" + getSendExecutionEPCStatus());
        StatusSendEPCDTO dto = new StatusSendEPCDTO();
        dto.setCurrent(getExtractionPlanManager().getCurrentCreateRecord());
        BigDecimal bd = new BigDecimal(getMaxCiclos() == null ? 0 : getMaxCiclos().doubleValue());
        bd = bd.setScale(0, BigDecimal.ROUND_UP);
        //dto.setMax(getMaxCiclos().intValue());
        dto.setMax(bd.intValue());
        dto.setMessage(getSendExecutionEPCMessage() == null ? "" : getSendExecutionEPCMessage());
        dto.setStatus(getSendExecutionEPCStatus() == null ? -1 : getSendExecutionEPCStatus());
        return dto;
    }

    public StatusSendEPCDTO getStatusSendEPCDTO(long sessionId) {
        StatusSendEPCDTO dto = new StatusSendEPCDTO();
        dto.setStatus(-2);
        if (isSendExecutionEPC()) {
            dto = getStatusSendEPCExecutionDTO(sessionId);
        }
        return dto;
    }

    public GetCantidadDetailsCommand getCantidadDetails() {
        if (cantidadDetails == null) {
            cantidadDetails = new GetCantidadDetailsCommand();
        }
        return cantidadDetails;
    }

    public void setCantidadDetails(GetCantidadDetailsCommand cantidadDetails) {
        this.cantidadDetails = cantidadDetails;
    }

    public SaveSendExtractionPlanProcessCommand getSaveSendExtractionPlanProcessCommand() {
        if (saveSendExtractionPlanProcessCommand == null) {
            saveSendExtractionPlanProcessCommand = new SaveSendExtractionPlanProcessCommand();
        }
        return saveSendExtractionPlanProcessCommand;
    }

    public void setSaveSendExtractionPlanProcessCommand(SaveSendExtractionPlanProcessCommand value) {
        this.saveSendExtractionPlanProcessCommand = value;
    }

    public void synchronizeStoreAreaType() {

        getSynchronizeToSecurityCommand().execute();
    }

    public SynchronizeToSecurityCommand getSynchronizeToSecurityCommand() {
        if (synchronizeToSecurityCommand == null) {
            synchronizeToSecurityCommand = new SynchronizeToSecurityCommand();
        }
        return synchronizeToSecurityCommand;
    }

    public void setSynchronizeToSecurityCommand(SynchronizeToSecurityCommand synchronizeToSecurityCommand) {
        this.synchronizeToSecurityCommand = synchronizeToSecurityCommand;
    }

    public GetExtractionPlanCustomizingListByIdsCommand getExtractionPlanCustomizingListByIdsCommand() {
        if (extractionPlanCustomizingListByIdsCommand == null) {
            extractionPlanCustomizingListByIdsCommand = new GetExtractionPlanCustomizingListByIdsCommand();
        }
        return extractionPlanCustomizingListByIdsCommand;
    }

    public void setExtractionPlanCustomizingListByIdsCommand(
            GetExtractionPlanCustomizingListByIdsCommand extractionPlanCustomizingListByIdsCommand) {
        this.extractionPlanCustomizingListByIdsCommand = extractionPlanCustomizingListByIdsCommand;
    }

    public GetCantidadDetailsByIdsCommand getCantidadDetailsByIdsCommand() {
        if (cantidadDetailsByIdsCommand == null) {
            cantidadDetailsByIdsCommand = new GetCantidadDetailsByIdsCommand();
        }
        return cantidadDetailsByIdsCommand;
    }

    public void setCantidadDetailsByIdsCommand(GetCantidadDetailsByIdsCommand cantidadDetailsByIdsCommand) {
        this.cantidadDetailsByIdsCommand = cantidadDetailsByIdsCommand;
    }

    public List<EvidenceProviderDTO> getEvidenceProviderDTOs(Integer storeId, long sessionId) throws ScopixException {
        List<EvidenceProviderDTO> evidenceProviderDTOs = null;
        try {
            Store store = new Store();
            store.setId(storeId);
            EvidenceProvider evidenceProviderFilter = new EvidenceProvider();
            evidenceProviderFilter.setStore(store);

            List<EvidenceProvider> evidenceProviders = getEvidenceProviderList(evidenceProviderFilter, sessionId);
            LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
            cols.put("description", Boolean.FALSE);
            SortUtil.sortByColumn(cols, evidenceProviders);

            evidenceProviderDTOs = getTransformEvidenceProvidersToDTOCommand().execute(evidenceProviders);

        } catch (ScopixException e) {
            throw new ScopixException(e.getMessage(), e);
        }
        return evidenceProviderDTOs;
    }

    public TransformEvidenceProvidersToDTOCommand getTransformEvidenceProvidersToDTOCommand() {
        if (transformEvidenceProvidersToDTOCommand == null) {
            transformEvidenceProvidersToDTOCommand = new TransformEvidenceProvidersToDTOCommand();
        }
        return transformEvidenceProvidersToDTOCommand;
    }

    public void setTransformEvidenceProvidersToDTOCommand(TransformEvidenceProvidersToDTOCommand value) {
        this.transformEvidenceProvidersToDTOCommand = value;
    }

    public List<SituationTemplate> getSituationTemplateListByArea(Set<Integer> areaIds) throws ScopixException {
        GetSituationTemplateListByAreasCommand command = new GetSituationTemplateListByAreasCommand();
        return command.execute(areaIds);
    }

    public List<SituationTemplate> getSituationTemplateList() {
        List<SituationTemplate> l = null;
        try {
            GetSituationTemplateListCommand command = new GetSituationTemplateListCommand();
            SituationTemplate st = new SituationTemplate();
            st.setActive(true);
            l = command.execute(st);

        } catch (ScopixException e) {
            log.error("No es posible reciperar situaciones " + e, e);
        }
        return l;

    }

    public List<SituationTemplateFrontEndDTO> getSituationTemplateFrontEndDTOList() throws ScopixException {
        List<SituationTemplate> l = getSituationTemplateList();
        List<SituationTemplateFrontEndDTO> dtoList = getTransformToSitTemplateFEDTOCommand().execute(l);
        return dtoList;

    }

    public List<SensorAndEvidenceExtractionServicesServerDTO> getSensorAndEvidenceExtractionServicesServerList(
            List<String> sensors, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId,
                CorporateStructureManagerPermissions.GET_LIST_SENSOR_EVIDENCE_EXTRACTION_SERVER_PERMISSION);

        List<SensorAndEvidenceExtractionServicesServerDTO> list = getSensorAndEvidenceExtractionServicesServerListCommand().
                execute(sensors);

        log.debug("finish");
        return list;
    }

    public List<EvidenceTransmStrategyFEDTO> getEvidenceTransmStrategyDTOlist(Integer storeId, Integer situationTemplateId) throws ScopixException {
        Store store = null;
        if (storeId != null) {
            store = new Store();
            store.setId(storeId);
        }
        SituationTemplate situationTemplate = null;
        if (situationTemplateId != null) {
            situationTemplate = new SituationTemplate();
            situationTemplate.setId(situationTemplateId);
        }
        return getEvidenceTransmStrategyDTOlist(store, situationTemplate);

    }

    public List<EvidenceTransmStrategyFEDTO> getEvidenceTransmStrategyDTOlist(Store store, SituationTemplate situationTemplate) throws ScopixException {
        List<EvidenceTransmitionStrategy> l = getGetTransmStgiesByCriteriaCommand().execute(store, situationTemplate);
        List<EvidenceTransmStrategyFEDTO> dtoList = getTransformToEvidenceTransmStrategyFEDTOCommand().execute(l);
        return dtoList;
    }

    public List<RegionServerFrontEndDTO> getRegionServerFrontEndDTOList(boolean full) throws ScopixException {
        List<RegionServer> l = getGetRegionServersCommand().execute();
        List<RegionServerFrontEndDTO> dtoList = getTransformToRegionServerFrontEndDTO().execute(l, full);
        return dtoList;
    }

    public EvidenceTransmitionStrategy getEvidenceTransmitionStrategyById(Integer Id) throws ScopixException {
        EvidenceTransmitionStrategy ets = getFindEvTransmStgiesByIdCommand().execute(Id);
        return ets;
    }

    public EvidenceTransmStrategyFEDTO getEvidenceTransmStrategyFEDTObyId(Integer Id) throws ScopixException {
        EvidenceTransmitionStrategy ets = getFindEvTransmStgiesByIdCommand().execute(Id);
        EvidenceTransmStrategyFEDTO etsDTO = getTransformToEvidenceTransmStrategyFEDTOCommand().execute(ets);
        return etsDTO;

    }

    public void saveOrUpdateEvTranmStratFromDTO(EvidenceTransmStrategyFEDTO estDTO) throws ScopixException {
        EvidenceTransmitionStrategy ets = getTransformToEvidenceTransmissionStrategy().execute(estDTO);
        getSaveEvidenceTransmissionStrategy().execute(ets);
    }

    public void deleteEvidenceTransmissionStrategy(Integer etsId) {
        EvidenceTransmitionStrategy ets = new EvidenceTransmitionStrategy();
        ets.setId(etsId);
        getDeleteEvidenceTransmissionStrategyCommand().execute(ets);
    }

    public RegionServerFrontEndDTO getRegionServerDTOById(Integer id) {
        RegionServer rs = getGetRegionServerByIdCommand().execute(id);
        return getTransformToRegionServerFrontEndDTO().execute(rs, true);
    }

    public void deleteRegionServerById(Integer id) {
        RegionServer regionServer = new RegionServer();
        regionServer.setId(id);
        getDeleteRegionServerCommand().execute(regionServer);
    }

    public void saveOrUpdateRegionServerFromDTO(RegionServerFrontEndDTO regionServerDTO) {
        RegionServer regionServer = getTransformToRegionServerCommand().execute(regionServerDTO);
        getSaveOrUpdateRegionServerCommand().execute(regionServer);

    }

    public void deleteEvidenceTransmissionStrategies(List<Integer> ids) {
        getDeleteMultipleEvTransmStrs().execute(ids);
    }

    public void createOrEditEvidenceTransmissionStrategies(List<Integer> storeIds, List<Integer> situationTemplateIds, List<Integer> regionServerIds) throws ScopixException {
        getCreateOrEditEvidenceTransmissionStrategies().execute(storeIds, situationTemplateIds, regionServerIds);
    }

    public GetSensorAndEvidenceExtractionServicesServerListCommand getSensorAndEvidenceExtractionServicesServerListCommand() {
        if (sensorAndEvidenceExtractionServicesServerListCommand == null) {
            sensorAndEvidenceExtractionServicesServerListCommand = new GetSensorAndEvidenceExtractionServicesServerListCommand();
        }
        return sensorAndEvidenceExtractionServicesServerListCommand;
    }

    public void setSensorAndEvidenceExtractionServicesServerListCommand(
            GetSensorAndEvidenceExtractionServicesServerListCommand getSensorAndEvidenceExtractionServicesServerListCommand) {
        this.sensorAndEvidenceExtractionServicesServerListCommand = getSensorAndEvidenceExtractionServicesServerListCommand;
    }

    /**
     * @return the transformToStoreFrontEndDTO
     */
    public TransformToStoreFrontEndDTOCommand getTransformToStoreFrontEndDTO() {
        if (transformToStoreFrontEndDTO == null) {
            transformToStoreFrontEndDTO = new TransformToStoreFrontEndDTOCommand();
        }
        return transformToStoreFrontEndDTO;
    }

    /**
     * @param transformToStoreFrontEndDTO the transformToStoreFrontEndDTO to set
     */
    public void setTransformToStoreFrontEndDTO(TransformToStoreFrontEndDTOCommand transformToStoreFrontEndDTO) {
        this.transformToStoreFrontEndDTO = transformToStoreFrontEndDTO;
    }

    /**
     * @return the transformToSitTemplateFEDTOCommand
     */
    public TransformToSitTemplateFEDTOCommand getTransformToSitTemplateFEDTOCommand() {
        if (transformToSitTemplateFEDTOCommand == null) {
            transformToSitTemplateFEDTOCommand = new TransformToSitTemplateFEDTOCommand();
        }
        return transformToSitTemplateFEDTOCommand;
    }

    /**
     * @param transformToSitTemplateFEDTOCommand the transformToSitTemplateFEDTOCommand to set
     */
    public void setTransformToSitTemplateFEDTOCommand(TransformToSitTemplateFEDTOCommand transformToSitTemplateFEDTOCommand) {
        this.transformToSitTemplateFEDTOCommand = transformToSitTemplateFEDTOCommand;
    }

    /**
     * @return the getTransmStgiesByCriteriaCommand
     */
    public GetTransmStgiesByCriteriaCommand getGetTransmStgiesByCriteriaCommand() {
        if (getTransmStgiesByCriteriaCommand == null) {
            getTransmStgiesByCriteriaCommand = new GetTransmStgiesByCriteriaCommand();
        }
        return getTransmStgiesByCriteriaCommand;
    }

    /**
     * @param getTransmStgiesByCriteriaCommand the getTransmStgiesByCriteriaCommand to set
     */
    public void setGetTransmStgiesByCriteriaCommand(GetTransmStgiesByCriteriaCommand getTransmStgiesByCriteriaCommand) {
        this.getTransmStgiesByCriteriaCommand = getTransmStgiesByCriteriaCommand;
    }

    /**
     * @return the transformToEvidenceTransmStrategyFEDTOCommand
     */
    public TransformToEvidenceTransmStrategyFEDTOCommand getTransformToEvidenceTransmStrategyFEDTOCommand() {
        if (transformToEvidenceTransmStrategyFEDTOCommand == null) {
            transformToEvidenceTransmStrategyFEDTOCommand = new TransformToEvidenceTransmStrategyFEDTOCommand();
        }
        return transformToEvidenceTransmStrategyFEDTOCommand;
    }

    /**
     * @param transformToEvidenceTransmStrategyFEDTOCommand the transformToEvidenceTransmStrategyFEDTOCommand to set
     */
    public void setTransformToEvidenceTransmStrategyFEDTOCommand(TransformToEvidenceTransmStrategyFEDTOCommand transformToEvidenceTransmStrategyFEDTOCommand) {
        this.transformToEvidenceTransmStrategyFEDTOCommand = transformToEvidenceTransmStrategyFEDTOCommand;
    }

    /**
     * @return the getRegionServersCommand
     */
    public GetRegionServersCommand getGetRegionServersCommand() {
        if (getRegionServersCommand == null) {
            getRegionServersCommand = new GetRegionServersCommand();
        }
        return getRegionServersCommand;
    }

    /**
     * @param getRegionServersCommand the getRegionServersCommand to set
     */
    public void setGetRegionServersCommand(GetRegionServersCommand getRegionServersCommand) {
        this.getRegionServersCommand = getRegionServersCommand;
    }

    /**
     * @return the transformToRegionServerFrontEndDTO
     */
    public TransformToRegionServerFrontEndDTO getTransformToRegionServerFrontEndDTO() {
        if (transformToRegionServerFrontEndDTO == null) {
            transformToRegionServerFrontEndDTO = new TransformToRegionServerFrontEndDTO();
        }
        return transformToRegionServerFrontEndDTO;
    }

    /**
     * @param transformToRegionServerFrontEndDTO the transformToRegionServerFrontEndDTO to set
     */
    public void setTransformToRegionServerFrontEndDTO(TransformToRegionServerFrontEndDTO transformToRegionServerFrontEndDTO) {
        this.transformToRegionServerFrontEndDTO = transformToRegionServerFrontEndDTO;
    }

    /**
     * @return the transformToEvidenceTransmissionStrategy
     */
    public TransformToEvidenceTransmissionStrategy getTransformToEvidenceTransmissionStrategy() {
        if (transformToEvidenceTransmissionStrategy == null) {
            transformToEvidenceTransmissionStrategy = new TransformToEvidenceTransmissionStrategy();
        }
        return transformToEvidenceTransmissionStrategy;
    }

    /**
     * @param transformToEvidenceTransmissionStrategy the transformToEvidenceTransmissionStrategy to set
     */
    public void setTransformToEvidenceTransmissionStrategy(TransformToEvidenceTransmissionStrategy transformToEvidenceTransmissionStrategy) {
        this.transformToEvidenceTransmissionStrategy = transformToEvidenceTransmissionStrategy;
    }

    /**
     * @return the saveEvidenceTransmissionStrategy
     */
    public SaveEvidenceTransmissionStrategyCommand getSaveEvidenceTransmissionStrategy() {
        if (saveEvidenceTransmissionStrategy == null) {
            saveEvidenceTransmissionStrategy = new SaveEvidenceTransmissionStrategyCommand();
        }
        return saveEvidenceTransmissionStrategy;
    }

    /**
     * @param saveEvidenceTransmissionStrategy the saveEvidenceTransmissionStrategy to set
     */
    public void setSaveEvidenceTransmissionStrategy(SaveEvidenceTransmissionStrategyCommand saveEvidenceTransmissionStrategy) {
        this.saveEvidenceTransmissionStrategy = saveEvidenceTransmissionStrategy;
    }

    /**
     * @return the deleteEvidenceTransmissionStrategyCommand
     */
    public DeleteEvidenceTransmissionStrategyCommand getDeleteEvidenceTransmissionStrategyCommand() {
        if (deleteEvidenceTransmissionStrategyCommand == null) {
            deleteEvidenceTransmissionStrategyCommand = new DeleteEvidenceTransmissionStrategyCommand();
        }
        return deleteEvidenceTransmissionStrategyCommand;
    }

    /**
     * @param deleteEvidenceTransmissionStrategyCommand the deleteEvidenceTransmissionStrategyCommand to set
     */
    public void setDeleteEvidenceTransmissionStrategyCommand(DeleteEvidenceTransmissionStrategyCommand deleteEvidenceTransmissionStrategyCommand) {
        this.deleteEvidenceTransmissionStrategyCommand = deleteEvidenceTransmissionStrategyCommand;
    }

    /**
     * @return the findEvTransmStgiesByIdCommand
     */
    public FindEvTransmStgiesByIdCommand getFindEvTransmStgiesByIdCommand() {
        if (findEvTransmStgiesByIdCommand == null) {
            findEvTransmStgiesByIdCommand = new FindEvTransmStgiesByIdCommand();

        }
        return findEvTransmStgiesByIdCommand;
    }

    /**
     * @param findEvTransmStgiesByIdCommand the findEvTransmStgiesByIdCommand to set
     */
    public void setFindEvTransmStgiesByIdCommand(FindEvTransmStgiesByIdCommand findEvTransmStgiesByIdCommand) {
        this.findEvTransmStgiesByIdCommand = findEvTransmStgiesByIdCommand;
    }

    /**
     * @return the getRegionServerByIdCommand
     */
    public GetRegionServerByIdCommand getGetRegionServerByIdCommand() {
        if (getRegionServerByIdCommand == null) {
            getRegionServerByIdCommand = new GetRegionServerByIdCommand();
        }
        return getRegionServerByIdCommand;
    }

    /**
     * @param getRegionServerByIdCommand the getRegionServerByIdCommand to set
     */
    public void setGetRegionServerByIdCommand(GetRegionServerByIdCommand getRegionServerByIdCommand) {
        this.getRegionServerByIdCommand = getRegionServerByIdCommand;
    }

    /**
     * @return the deleteRegionServerCommand
     */
    public DeleteRegionServerCommand getDeleteRegionServerCommand() {
        if (deleteRegionServerCommand == null) {
            deleteRegionServerCommand = new DeleteRegionServerCommand();
        }
        return deleteRegionServerCommand;
    }

    /**
     * @param deleteRegionServerCommand the deleteRegionServerCommand to set
     */
    public void setDeleteRegionServerCommand(DeleteRegionServerCommand deleteRegionServerCommand) {
        this.deleteRegionServerCommand = deleteRegionServerCommand;
    }

    /**
     * @return the transformToRegionServerCommand
     */
    public TransformToRegionServerCommand getTransformToRegionServerCommand() {
        if (transformToRegionServerCommand == null) {
            transformToRegionServerCommand = new TransformToRegionServerCommand();
        }
        return transformToRegionServerCommand;
    }

    /**
     * @param transformToRegionServerCommand the transformToRegionServerCommand to set
     */
    public void setTransformToRegionServerCommand(TransformToRegionServerCommand transformToRegionServerCommand) {
        this.transformToRegionServerCommand = transformToRegionServerCommand;
    }

    /**
     * @return the saveOrUpdateRegionServerCommand
     */
    public SaveOrUpdateRegionServerCommand getSaveOrUpdateRegionServerCommand() {
        if (saveOrUpdateRegionServerCommand == null) {
            saveOrUpdateRegionServerCommand = new SaveOrUpdateRegionServerCommand();
        }
        return saveOrUpdateRegionServerCommand;
    }

    /**
     * @param saveOrUpdateRegionServerCommand the saveOrUpdateRegionServerCommand to set
     */
    public void setSaveOrUpdateRegionServerCommand(SaveOrUpdateRegionServerCommand saveOrUpdateRegionServerCommand) {
        this.saveOrUpdateRegionServerCommand = saveOrUpdateRegionServerCommand;
    }

    /**
     * @return the deleteMultipleEvTransmStrs
     */
    public DeleteMultipleEvTransmStrs getDeleteMultipleEvTransmStrs() {
        if (deleteMultipleEvTransmStrs == null) {
            deleteMultipleEvTransmStrs = new DeleteMultipleEvTransmStrs();
        }
        return deleteMultipleEvTransmStrs;
    }

    /**
     * @param deleteMultipleEvTransmStrs the deleteMultipleEvTransmStrs to set
     */
    public void setDeleteMultipleEvTransmStrs(DeleteMultipleEvTransmStrs deleteMultipleEvTransmStrs) {
        this.deleteMultipleEvTransmStrs = deleteMultipleEvTransmStrs;
    }

    /**
     * @return the createOrEditEvidenceTransmissionStrategies
     */
    public CreateOrEditEvidenceTransmissionStrategies getCreateOrEditEvidenceTransmissionStrategies() {
        if (createOrEditEvidenceTransmissionStrategies == null) {
            createOrEditEvidenceTransmissionStrategies = new CreateOrEditEvidenceTransmissionStrategies();
        }

        return createOrEditEvidenceTransmissionStrategies;
    }

    /**
     * @param createOrEditEvidenceTransmissionStrategies the createOrEditEvidenceTransmissionStrategies to set
     */
    public void setCreateOrEditEvidenceTransmissionStrategies(CreateOrEditEvidenceTransmissionStrategies createOrEditEvidenceTransmissionStrategies) {
        this.createOrEditEvidenceTransmissionStrategies = createOrEditEvidenceTransmissionStrategies;
    }

    /**
     * @return the sendEpcToPast
     */
    public boolean isSendEpcToPast() {
        return sendEpcToPast;
    }

    /**
     * @param sendEpcToPast the sendEpcToPast to set
     */
    public void setSendEpcToPast(boolean sendEpcToPast) {
        this.sendEpcToPast = sendEpcToPast;
    }

    /**
     * @return the configuration
     */
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
     * @param configuration the configuration to set
     */
    public void setConfiguration(PropertiesConfiguration configuration) {
        this.configuration = configuration;
    }

}
