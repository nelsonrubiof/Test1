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
 * ExtractionPlanWebServiceClient.java
 *
 * Created on 18-06-2008, 03:19:36 AM
 *
 */
package com.scopix.periscope.evidencemanagement.services.webservices.client;

import com.scopix.periscope.evidencemanagement.services.webservices.EvidenceWebService;
import com.scopix.periscope.periscopefoundation.evidence_common.dispatcher.DispatcherUtil;
import com.scopix.periscope.periscopefoundation.evidence_common.exception.ConfigurationException;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author marko.perich
 * @version 1.0.0
 */
@SpringBean
public class EvidenceWebServiceClient {

    private static Logger log = Logger.getLogger(EvidenceWebServiceClient.class);
    private static Map<String, EvidenceWebService> webServices = new HashMap();
    private static EvidenceWebServiceClient instance;

    private EvidenceWebServiceClient() {
    }

    /**
     *
     * @return EvidenceWebServiceClient instanacia con servicios asociados
     */
    public static synchronized EvidenceWebServiceClient getInstance() {
        if (instance == null) {
            instance = new EvidenceWebServiceClient();
        }
        return instance;
    }

    /**
     *
     * @param serverURLs mapa con urls que se deben levantar
     * @throws ScopixException En caso de Excepcion
     */
    public synchronized void initializeClients(Map<String, String> serverURLs) throws ScopixException {
        log.debug("initializeClients");
        instance = null;
        instance = getInstance();
        webServices.clear();
        EvidenceWebService webService;
        for (String key : serverURLs.keySet()) {
            log.debug("Initializing: " + key + ":" + serverURLs.get(key));
            try {
                webService = DispatcherUtil.createWSAgent(serverURLs.get(key), EvidenceWebService.class);
                webServices.put(key, webService);
            } catch (ConfigurationException e) {
                log.error(e, e);
                throw new ScopixException("Error initializing web services clients.", e);
            } catch (MalformedURLException e) {
                log.error(e, e);
                throw new ScopixException("Error initializing web services clients.", e);
            }
        }
    }

    /**
     *
     * @param key llave del serivicio que se necesita
     * @return EvidenceWebService servicio instanciado
     */
    public EvidenceWebService getWebServiceClient(String key) {
        return webServices.get(key);
    }
}
