/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * 
 * CorporateStructureManagementHibernateDAO.java
 * 
 * Created on 08-09-2010, 12:13:20 PM
 */

package com.scopix.periscope.corporatestructuremanagement.dao;

import java.util.List;
import java.util.Set;

import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.corporatestructuremanagement.Country;
import com.scopix.periscope.corporatestructuremanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.EvidenceServicesServer;
import com.scopix.periscope.corporatestructuremanagement.Region;
import com.scopix.periscope.corporatestructuremanagement.Sensor;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceExtractionServicesServerDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceRequestDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.SensorAndEvidenceExtractionServicesServerDTO;
import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.vadaro.VadaroEvent;
import java.util.Date;

/**
 *
 * @author nelson
 */
public interface CorporateStructureManagementHibernateDAO {

    /**
     * Obtain all actives evidence request for a particular evidence provider
     * 
     * @param evidenceProviderId
     * @return
     */
    List<? extends EvidenceRequest> getActiveEvidenceRequests(Integer evidenceProviderId);

    /**
     * Obtain a list of Area object using filters.
     *
     * @param area
     *            Filter object
     * @return List<Area> List of Area objects
     */
    List<Area> getAreaList(Area area);

    /**
     * Obtain a list of AreaType object using filters.
     *
     * @param areaType
     *            Filter object
     * @return List<AreaType> List of AreaType objects
     */
    List<AreaType> getAreaTypeList(AreaType areaType);

    /**
     * Obtain a list of Country object using filters.
     *
     * @param country
     *            Filter object
     * @return List<Country> List of Country objects
     */
    List<Country> getCountryList(Country country);

    /**
     * Obtain a list of EvidenceExtractionServicesServer object using filters.
     *
     * @param eess
     *            Filter object
     * @return List<EvidenceExtractionServicesServer> List of EvidenceExtractionServicesServer objects
     */
    List<EvidenceExtractionServicesServer> getEvidenceExtractionServicesServersList(EvidenceExtractionServicesServer eess);

    /**
     * Obtain a list of EvidenceProvider object using filters.
     *
     * @param evidenceProvider
     *            Filter object
     * @return List<EvidenceProvider> List of EvidenceProvider objects
     */
    List<EvidenceProvider> getEvidenceProvidersList(EvidenceProvider evidenceProvider);

    /**
     *
     * @param evidenceRequestIds
     * @return
     */
    List<EvidenceRequest> getEvidenceRequestsList(List<Integer> evidenceRequestIds) throws ScopixException;

    /**
     * 
     * @param stores
     * @return
     */
    List<EvidenceExtractionServicesServerDTO> getEvidenceExtractionServicesServerListForStores(List<String> stores);

    /**
     * Obtain a list of EvidenceServicesServer object using filters.
     *
     * @param ess
     *            Filter object
     * @return List<EvidenceServicesServer> List of EvidenceServicesServer objects
     */
    List<EvidenceServicesServer> getEvidenceServicesServersList(EvidenceServicesServer ess);

    /**
     * Obtain a list of EvidenceExtractionServicesServer object using filters.
     *
     * @param eess
     *            Filter object
     * @return List<EvidenceExtractionServicesServer> List of EvidenceExtractionServicesServer objects
     */
    List<EvidenceExtractionServicesServer> getFreeEvidenceExtractionServicesServersList(EvidenceExtractionServicesServer eess);

    /**
     * Obtain a list of Region object using filters.
     *
     * @param region
     *            Filter object
     * @return List<Region> List of Region objects
     */
    List<Region> getRegionList(Region region);

    Sensor getSensor(String name);

    /**
     * Obtain a list of Sensor object using filters.
     *
     * @param sensor
     *            Filter object
     * @return List<EvidenceProvider> List of EvidenceProvider objects
     */
    List<Sensor> getSensorList(Sensor sensor);

    /**
     * Obtain a list of Store object using filters.
     *
     * @param store
     *            Filter object
     * @return List<Store> List of Store objects
     */
    List<Store> getStoreList(Store store);

    List<EvidenceRequestDTO> getActiveEvidenceRequestDTOs(Integer id) throws ScopixException;

    List<SituationTemplate> getSituationTemplateListByArea(Set<Integer> areaIds);

    List<EvidenceProvider> getEvidenceProvidersByType(String type, Integer storeId);

    List<VadaroEvent> getLastVadaroEvents(Integer minutes, Integer storeId, Date timeEvidence);

    List<EvidenceProvider> getEvidenceProvidersListByArea(EvidenceProvider evidenceProvider);

    List<SensorAndEvidenceExtractionServicesServerDTO> getSensorAndEvidenceExtractionServicesServerList(List<String> sensors)
            throws ScopixException;
}
