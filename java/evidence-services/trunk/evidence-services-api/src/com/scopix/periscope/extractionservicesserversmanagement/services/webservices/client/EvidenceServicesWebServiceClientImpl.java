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
 * EvidenceExtractionWebServiceClient.java
 *
 * Created on 18-06-2008, 03:19:36 AM
 *
 */
package com.scopix.periscope.extractionservicesserversmanagement.services.webservices.client;

import com.scopix.periscope.extractionservicesserversmanagement.services.webservices.EvidenceServicesWebService;
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
@SpringBean(rootClass = EvidenceServicesWebServiceClientImpl.class)
public class EvidenceServicesWebServiceClientImpl implements EvidenceServicesWebServiceClient {

    private static Logger log = Logger.getLogger(EvidenceServicesWebServiceClientImpl.class);
    private static Map<String, EvidenceServicesWebService> webServices = new HashMap<String, EvidenceServicesWebService>();

    private EvidenceServicesWebServiceClientImpl() {
    }

    @Override
    public synchronized void initializeClients(Map<String, String> clientUrls) throws ScopixException {
        EvidenceServicesWebService webService;
        for (String key : clientUrls.keySet()) {
            String wsUrl = key + "/spring/services/EvidenceServicesWebService";
//            String wsUrl = key + "/spring/services/EvidenceExtractionWebService";
            log.info("initializing Evidence Extraction Web Service: " + wsUrl);
            try {
                webService = DispatcherUtil.createWSAgent(wsUrl, EvidenceServicesWebService.class);
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

    @Override
    public EvidenceServicesWebService getWebServiceClient(String key) {
        return webServices.get(key);
    }
}
