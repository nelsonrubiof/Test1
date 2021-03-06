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
 * CorporateStructureManagementHibernateDAOMock.java
 * 
 * Created on 08-09-2010, 12:16:20 PM
 */
package mockup;

import java.util.ArrayList;
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
import com.scopix.periscope.corporatestructuremanagement.dao.CorporateStructureManagementHibernateDAO;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceExtractionServicesServerDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceRequestDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.SensorAndEvidenceExtractionServicesServerDTO;
import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.vadaro.VadaroEvent;
import java.util.Date;

/**
 * Instancia de un CorporateStructureManagementHibernateDAO el cual en su metodo getActiveEvidenceRequests retorna una lista de
 * EvidenceRequest con un solo registro del Tipo RequestedVideo RequestsVideo
 * 
 * @author nelson
 */
public class CorporateStructureManagementHibernateDAOMock implements CorporateStructureManagementHibernateDAO {

    public CorporateStructureManagementHibernateDAOMock() {
    }

    @Override
    public List<EvidenceRequest> getActiveEvidenceRequests(Integer evidenceProviderId) {
        Integer newIdER = (int) (evidenceProviderId * Math.random());
        EvidenceRequest er = ManagerMock.genEvidenceRequestVideo(newIdER);
        EvidenceProvider provider = ManagerMock.genEvidenceProvider(1, "Evidence Provider 1", "Evidence Provider 1");
        provider.setEvidenceProviderType(ManagerMock.genEvidenceProviderType());
        er.setEvidenceProvider(provider);
        List<EvidenceRequest> ers = new ArrayList<EvidenceRequest>();
        ers.add(er);
        return ers;
    }

    @Override
    public List<Area> getAreaList(Area area) {
        List<Area> lista = new ArrayList<Area>();
        lista.add(new Area());
        return lista;
    }

    @Override
    public List<AreaType> getAreaTypeList(AreaType areaType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Country> getCountryList(Country country) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EvidenceExtractionServicesServer> getEvidenceExtractionServicesServersList(EvidenceExtractionServicesServer eess) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * retorna una lista con 2 EV de prueba
     */
    @Override
    public List<EvidenceProvider> getEvidenceProvidersList(EvidenceProvider evidenceProvider) {
        List<EvidenceProvider> list = new ArrayList<EvidenceProvider>();
        EvidenceProvider ep = ManagerMock.genEvidenceProvider(1, "Evidence Provider 1", "Evidence Provider 1");
        ep.setEvidenceProviderType(ManagerMock.genEvidenceProviderType());
        list.add(ep);
        ep = ManagerMock.genEvidenceProvider(2, "Evidence Provider 2", "Evidence Provider 2");
        ep.setEvidenceProviderType(ManagerMock.genEvidenceProviderType());
        list.add(ep);
        return list;

    }

    @Override
    public List<EvidenceRequest> getEvidenceRequestsList(List<Integer> evidenceRequestIds) throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EvidenceServicesServer> getEvidenceServicesServersList(EvidenceServicesServer ess) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EvidenceExtractionServicesServer> getFreeEvidenceExtractionServicesServersList(
            EvidenceExtractionServicesServer eess) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Region> getRegionList(Region region) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Sensor getSensor(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // retorna una lsita de 2 sensor
    @Override
    public List<Sensor> getSensorList(Sensor sensor) {
        List<Sensor> sensors = new ArrayList<Sensor>();
        sensors.add(ManagerMock.genSensor(1, "Sensor 1", "Sensor 1"));
        sensors.add(ManagerMock.genSensor(2, "Sensor 2", "Sensor 2"));
        return sensors;
    }

    /**
     * retorna una lista con 2 Store
     */
    @Override
    public List<Store> getStoreList(Store store) {
        List<Store> stores = new ArrayList<Store>();
        stores.add(ManagerMock.genStore(1, "Store 1", "Store 1"));
        stores.add(ManagerMock.genStore(2, "Store 2", "Store 2"));
        return stores;
    }

    @Override
    public List<EvidenceRequestDTO> getActiveEvidenceRequestDTOs(Integer storeId) throws ScopixException {
        Integer newIdER = (int) (storeId * Math.random());
        EvidenceRequestDTO erdto = ManagerMock.genEvidenceRequestVideoDTO(newIdER);
        List<EvidenceRequestDTO> ers = new ArrayList<EvidenceRequestDTO>();
        ers.add(erdto);
        return ers;

    }

    @Override
    public List<SituationTemplate> getSituationTemplateListByArea(Set<Integer> areaIds) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EvidenceProvider> getEvidenceProvidersListByArea(EvidenceProvider evidenceProvider) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<SensorAndEvidenceExtractionServicesServerDTO> getSensorAndEvidenceExtractionServicesServerList(
            List<String> sensors) throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools |
                                                                       // Templates.
    }

    @Override
    public List<EvidenceExtractionServicesServerDTO> getEvidenceExtractionServicesServerListForStores(List<String> stores) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools |
                                                                       // Templates.
    }

    @Override
    public List<EvidenceProvider> getEvidenceProvidersByType(String type, Integer storeId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<VadaroEvent> getLastVadaroEvents(Integer minutes, Integer storeId, Date timeEvidence) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
}
