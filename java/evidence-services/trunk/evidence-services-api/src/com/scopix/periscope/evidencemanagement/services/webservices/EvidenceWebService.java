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
 * EvidenceWebService.java
 *
 * Created on 18-06-2008, 03:54:29 AM
 *
 */
package com.scopix.periscope.evidencemanagement.services.webservices;

import com.scopix.periscope.evidencemanagement.dto.NewAutomaticEvidenceDTO;
import com.scopix.periscope.evidencemanagement.dto.NewEvidenceDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import java.util.Map;
import java.util.Set;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author marko.perich
 * @version 1.0.0
 */
@WebService(name = "EvidenceWebService")
//@CustomWebService
public interface EvidenceWebService {

    /**
     *
     * @param newEvidence Objeto que representa a una nueva evidencia recibida desde EES
     * @throws ScopixWebServiceException Excepcion en caso de Error
     */
    @WebMethod
    void acceptNewEvidence(NewEvidenceDTO newEvidence) throws ScopixWebServiceException;

    /**
     * 
     * @param automaticEvidenceDTO Objeto que representa a una nueva evidencia Automatica recibida desde EES
     * @throws ScopixWebServiceException Excepcion en caso de Error
     */
    @WebMethod
    void acceptAutomaticNewEvidence(NewAutomaticEvidenceDTO automaticEvidenceDTO) throws ScopixWebServiceException;

    /**
     * 
     * @param dateStart fecha inicio
     * @param dateEnd fecha Termino
     * @param providerIds ids de providers
     * @param storeName Nombre del Store
     * @return Map<Integer, Integer> datos asociados
     * @throws ScopixWebServiceException Excepcion en caso de Error
     */
    @WebMethod
    Map<Integer, Integer> getEvidenceRequestsByProvider(String dateStart, String dateEnd, Set<Integer> providerIds,
            String storeName) throws ScopixWebServiceException;
}
