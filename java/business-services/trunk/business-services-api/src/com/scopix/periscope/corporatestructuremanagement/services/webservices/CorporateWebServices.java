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
 * CorporateWebServices.java
 *
 * Created on 20-05-2008, 11:37:05 AM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.services.webservices;

import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceExtractionServicesServerDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceProviderDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.SensorDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.StatusSendEPCDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import java.util.List;
import javax.jws.WebService;

/**
 *
 * @author Cesar Abarza Suazo.
 */
@WebService(name = "CorporateWebServices")
public interface CorporateWebServices {

    Integer getCorporateId() throws ScopixWebServiceException;

    String getCorporateLogoPath() throws ScopixWebServiceException;

    StatusSendEPCDTO sendExtractionPlanCustomizings(List<Integer> extractionPlanCustomizingIds, Integer storeId,
            long sessionId) throws ScopixWebServiceException;

    StatusSendEPCDTO sendExtractionPlanCustomizingFull(Integer storeId, long sessionId) throws ScopixWebServiceException;

    /**
     * recuperacion del status de la ejecucion de envio actual
     */
    StatusSendEPCDTO getStatusSendEPCExecution(long sessionId) throws ScopixWebServiceException;

    /**
     * *
     * recupera el status para la existencia o no de proceso de envio de epc
     */
    StatusSendEPCDTO getStatusSendEPC(long sessionId) throws ScopixWebServiceException;

    List<StoreDTO> getStoreDTOs(long sessionId) throws ScopixWebServiceException;
    
    List<StoreDTO> getStoreDTOsList() throws ScopixWebServiceException;

    List<EvidenceProviderDTO> getEvidenceProviderDTOs(Integer storeId, long sessionId) throws ScopixWebServiceException;

    List<SensorDTO> getSensorList(long sessionId) throws ScopixWebServiceException;

    List<StoreDTO> getStoreList(long sessionId) throws ScopixWebServiceException;

    List<EvidenceExtractionServicesServerDTO> getEvidenceExtractionServicesServerListForStores(List<String> stores, 
            long sessionId) throws ScopixWebServiceException;

    List<EvidenceExtractionServicesServerDTO> getEvidenceExtractionServicesServerList(EvidenceExtractionServicesServerDTO eess,
            long sessionId) throws ScopixWebServiceException;
}
