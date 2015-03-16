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
 * UploadEvidenceToExternalDeviceWebServiceClient.java
 *
 * Created on 11-01-2010, 02:18:24 PM
 *
 */
package com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client;

import com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.UploadEvidenceToExternalDeviceWebService;
import com.scopix.periscope.extractionplanmanagement.services.webservices.client.ExtractionPlanWebServiceClient;
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
 * @author Gustavo Alvarez
 * @version 1.0.0
 */
@SpringBean
public class UploadEvidenceToExternalDeviceWebServiceClient {

    private static Logger log = Logger.getLogger(ExtractionPlanWebServiceClient.class);
    private static Map<String, UploadEvidenceToExternalDeviceWebService> webServices =
            new HashMap<String, UploadEvidenceToExternalDeviceWebService>();
    private static UploadEvidenceToExternalDeviceWebServiceClient instance;

    private UploadEvidenceToExternalDeviceWebServiceClient() {
    }

    /**
     *
     * @return
     */
    public static synchronized UploadEvidenceToExternalDeviceWebServiceClient getInstance() {
        if (instance == null) {
            instance = new UploadEvidenceToExternalDeviceWebServiceClient();
        }
        return instance;
    }

    /**
     *
     * @param clientUrls
     * @throws ScopixException
     */
    public synchronized void initializeClients(Map<String, String> clientUrls) throws ScopixException {
        UploadEvidenceToExternalDeviceWebService webService;
        for (String key : clientUrls.keySet()) {
            String wsUrl = key + "/spring/services/UploadEvidenceToExternalDeviceWebService";
            log.info("initializing Upload Evidence to External Device Web Service: " + wsUrl);
            try {
                webService = DispatcherUtil.createWSAgent(wsUrl, UploadEvidenceToExternalDeviceWebService.class);
                webServices.put(key, webService);
            } catch (ConfigurationException e) {
                log.error(e, e);
                throw new ScopixException("Error initializing web services clients.", e);
            } catch (MalformedURLException e) {
                log.debug(e, e);
                throw new ScopixException("Error initializing web services clients.", e);
            }
        }
    }

    /**
     *
     * @param key
     * @return
     */
    public UploadEvidenceToExternalDeviceWebService getWebServiceClient(String key) {
        return webServices.get(key);
    }
}
