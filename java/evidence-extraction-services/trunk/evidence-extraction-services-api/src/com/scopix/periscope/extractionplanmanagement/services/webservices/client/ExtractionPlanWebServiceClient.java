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
 * ExtractionPlanWebServiceClient.java
 *
 * Created on 02-07-2008, 01:59:09 AM
 *
 */
package com.scopix.periscope.extractionplanmanagement.services.webservices.client;

import com.scopix.periscope.extractionplanmanagement.services.webservices.ExtractionPlanWebService;
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
public class ExtractionPlanWebServiceClient {

    private static Logger log = Logger.getLogger(ExtractionPlanWebServiceClient.class);
    private static Map<String, ExtractionPlanWebService> webServices = new HashMap<String, ExtractionPlanWebService>();
    private static ExtractionPlanWebServiceClient instance;

    private ExtractionPlanWebServiceClient() {
    }

    public static synchronized ExtractionPlanWebServiceClient getInstance() {
        if (instance == null) {
            instance = new ExtractionPlanWebServiceClient();
        }
        return instance;
    }

    public synchronized void initializeClients(Map<String, String> clientUrls) throws ScopixException {
        ExtractionPlanWebService webService;
        for (String key : clientUrls.keySet()) {
            String wsUrl = key + "/spring/services/ExtractionPlanWebService";
            log.info("initializing Extraction Plan Web Service: " + wsUrl);
            try {
                webService = DispatcherUtil.createWSAgent(wsUrl, ExtractionPlanWebService.class);
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

    public ExtractionPlanWebService getWebServiceClient(String key) {
        return webServices.get(key);
    }
}
