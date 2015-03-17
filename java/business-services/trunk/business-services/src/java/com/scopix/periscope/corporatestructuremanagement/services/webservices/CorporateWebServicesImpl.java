/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * EvaluationWebServicesImpl.java
 *
 * Created on 15-05-2008, 06:20:52 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.services.webservices;

import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.corporatestructuremanagement.Sensor;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.corporatestructuremanagement.dto.*;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.LinkedList;
import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@WebService(endpointInterface = "com.scopix.periscope.corporatestructuremanagement.services.webservices.CorporateWebServices")
@SpringBean(rootClass = CorporateWebServices.class)
public class CorporateWebServicesImpl implements CorporateWebServices {

    private Logger log = Logger.getLogger(CorporateWebServices.class);

    /**
     * This method is for obtain the corporate id without security
     *
     * @return
     * @throws
     * com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    @Override
    public Integer getCorporateId() throws ScopixWebServiceException {
        Integer id = null;
        try {
            CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
            id = manager.getCorporateId();
            log.debug("corporateId: " + id);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return id;
    }

    @Override
    public String getCorporateLogoPath() throws ScopixWebServiceException {
        String logo = null;
        try {
            CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
            logo = manager.getLogoPath();
            log.debug("logo path: " + logo);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return logo;
    }

    @Override
    public StatusSendEPCDTO sendExtractionPlanCustomizings(List<Integer> extractionPlanCustomizingIds, Integer storeId,
            long sessionId) throws ScopixWebServiceException {
        StatusSendEPCDTO statusSendEPCDTO = null;
        try {
            statusSendEPCDTO = new StatusSendEPCDTO();
            CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
            if (!manager.isSendExecutionEPC()) {
                statusSendEPCDTO = manager.inicalizarEnvioThread(extractionPlanCustomizingIds, storeId, sessionId);
            } else {
                statusSendEPCDTO = getStatusSendEPCExecution(sessionId);
            }
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return statusSendEPCDTO;
    }

    @Override
    public StatusSendEPCDTO sendExtractionPlanCustomizingFull(Integer storeId, long sessionId) throws ScopixWebServiceException {
        StatusSendEPCDTO statusSendEPCDTO = new StatusSendEPCDTO();
        try {
            CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
            if (!manager.isSendExecutionEPC()) {
                statusSendEPCDTO = manager.inicalizarEnvioFullThread(storeId, sessionId);
            } else {
                statusSendEPCDTO = getStatusSendEPCExecution(sessionId);
            }
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return statusSendEPCDTO;
    }

    @Override
    public StatusSendEPCDTO getStatusSendEPCExecution(long sessionId) throws ScopixWebServiceException {
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        return manager.getStatusSendEPCExecutionDTO(sessionId);
    }

    @Override
    public StatusSendEPCDTO getStatusSendEPC(long sessionId) throws ScopixWebServiceException {
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        return manager.getStatusSendEPCDTO(sessionId);
    }

    @Override
    public List<StoreDTO> getStoreDTOs(long sessionId) throws ScopixWebServiceException {
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        List<StoreDTO> list = null;
        try {
            list = manager.getStoreDTOs(null, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return list;
    }

    @Override
    public List<EvidenceProviderDTO> getEvidenceProviderDTOs(Integer storeId, long sessionId) throws ScopixWebServiceException {
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        List<EvidenceProviderDTO> list = null;
        try {
            list = manager.getEvidenceProviderDTOs(storeId, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return list;
    }

    @Override
    public List<SensorDTO> getSensorList(long sessionId) throws ScopixWebServiceException {
        List<SensorDTO> dtoList = new LinkedList<SensorDTO>();
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        try {
            List<Sensor> sensorList = manager.getSensorList(null, sessionId);

            SensorDTO dto = null;
            for (Sensor sensor : sensorList) {
                dto = new SensorDTO();
                dto.setId(sensor.getId());
                dto.setName(sensor.getName());
                dto.setDescription(sensor.getDescription());
                dto.setUsername(sensor.getUserName());
                dto.setPassword(sensor.getUserPassword());
                dto.setUrl(sensor.getUrl());
                dto.setStoreId(sensor.getStore().getId());  //PENDING revisar si vienen esos datos efectivamente por el tema lazy
                dto.setAreaId(sensor.getArea().getId());    //PENDING revisar si vienen esos datos efectivamente por el tema lazy

                dtoList.add(dto);
            }
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return dtoList;
    }

    @Override
    public List<StoreDTO> getStoreList(long sessionId) throws ScopixWebServiceException {

        List<StoreDTO> dtoList = new LinkedList<StoreDTO>();
        try {
            CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
            List<Store> storeList = manager.getStoreList(null, sessionId);

            StoreDTO dto = null;
            for (Store store : storeList) {
                log.debug("eessId: " + store.getEvidenceExtractionServicesServer().getId());
                dto = new StoreDTO();
                dto.setId(store.getId());
                dto.setName(store.getName());
                dto.setDescription(store.getDescription());
                dto.setAddress(store.getAddress());
                dto.setEessId(store.getEvidenceExtractionServicesServer().getId());

                dtoList.add(dto);
            }

        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return dtoList;
    }

    @Override
    public List<EvidenceExtractionServicesServerDTO> getEvidenceExtractionServicesServerListForStores(List<String> stores, long sessionId)
            throws ScopixWebServiceException {
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);

        List<EvidenceExtractionServicesServerDTO> list = null;
        try {
            list = manager.getEvidenceExtractionServicesServerListForStores(stores, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return list;
    }

    @Override
    public List<EvidenceExtractionServicesServerDTO> getEvidenceExtractionServicesServerList(EvidenceExtractionServicesServerDTO eess, long sessionId)
            throws ScopixWebServiceException {

        List<EvidenceExtractionServicesServerDTO> eessDTOs = null;
        try {
            List<EvidenceExtractionServicesServer> list = null;

            EvidenceExtractionServicesServer eessParamVO = null;
            if (eess != null) {
                eessParamVO = new EvidenceExtractionServicesServer();
                eessParamVO.setId(eess.getIdAtBusinessServices());
                eessParamVO.setUrl(eess.getUrl());
            }

            CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
            list = manager.getEvidenceExtractionServicesServers(eessParamVO, sessionId);

            eessDTOs = new LinkedList<EvidenceExtractionServicesServerDTO>();
            EvidenceExtractionServicesServerDTO eessDTO = null;
            for (EvidenceExtractionServicesServer eessVO : list) {
                eessDTO = new EvidenceExtractionServicesServerDTO();

                eessDTO.setIdAtBusinessServices(eessVO.getId());
                eessDTO.setName(eessVO.getName());
                eessDTO.setServerId(eessVO.getEvidenceServicesServer().getId());
                eessDTO.setUseTunnel(eessVO.isUseTunnel());
                eessDTO.setSshAddress(eessVO.getSshAddress());
                eessDTO.setSshLocalTunnelPort(eessVO.getSshLocalTunnelPort());
                eessDTO.setSshPassword(eessVO.getSshPassword());
                eessDTO.setSshPort(eessVO.getSshPort());
                eessDTO.setSshRemoteTunnelPort(eessVO.getSshRemoteTunnelPort());
                eessDTO.setSshUser(eessVO.getSshUser());
                eessDTO.setUrl(eessVO.getUrl());
            }
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return eessDTOs;
    }

    @Override
    public List<StoreDTO> getStoreDTOsList() throws ScopixWebServiceException {
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        List<StoreDTO> list = null;
        try {
            list = manager.getStoreDTOs(null);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return list;
    }
}
