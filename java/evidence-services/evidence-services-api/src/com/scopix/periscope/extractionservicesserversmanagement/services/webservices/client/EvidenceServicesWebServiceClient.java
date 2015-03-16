/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scopix.periscope.extractionservicesserversmanagement.services.webservices.client;

import com.scopix.periscope.extractionservicesserversmanagement.services.webservices.EvidenceServicesWebService;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.util.Map;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
public interface EvidenceServicesWebServiceClient {


    /**
     *
     * @param key llave del servicio a solicitar
     * @return EvidenceServicesWebService servicio instanciado
     */
    EvidenceServicesWebService getWebServiceClient(String key);

    /**
     *
     * @param clientUrls Map de los clientes a levantar
     * @throws ScopixException en caso de Error
     */
    void initializeClients(Map<String, String> clientUrls) throws ScopixException;

}
