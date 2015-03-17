/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  AlertWebServicesClient.java
 * 
 *  Created on 29-05-2012, 10:41:27 AM
 * 
 */
package com.scopix.periscope.alerts.webservices.client;

import com.scopix.periscope.alerts.webservices.AlertWebServices;
import com.scopix.periscope.periscopefoundation.evidence_common.dispatcher.DispatcherUtil;
import com.scopix.periscope.periscopefoundation.evidence_common.exception.ConfigurationException;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.webservices.client.WebServiceUrlMapping;
import com.scopix.periscope.queuemanagement.services.webservices.QueueManagementWebServices;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
@SpringBean
public class AlertWebServicesClient {

    private static Logger log = Logger.getLogger(AlertWebServicesClient.class);
    private static Map<String, AlertWebServices> webServices;

    public AlertWebServicesClient() {
        if (webServices == null) {
            webServices = new HashMap<String, AlertWebServices>();
        }
    }

    public AlertWebServices getWebService(String corporateName) throws ScopixException {
        log.info("start [corporateName:" + corporateName + "]");
        AlertWebServices webService = webServices.get(corporateName);
        if (webService == null) {
            try {

                String corp = StringUtils.replace(corporateName.toUpperCase(), " ", "_");
                webService = getWebServiceByKey("BUSINESS_SERVICES_ALERT_" + corp);
                webServices.put(corporateName, webService);
            } catch (ConfigurationException e) {
                log.error(e, e);
                throw new ScopixException(e);
            }
        }
        log.info("end");
        return webService;
    }

    public AlertWebServices getWebService() throws ScopixException {
        log.info("start");
        AlertWebServices webService = getWebServiceByKey("ALERT");
        log.info("end");
        return webService;

    }

    private AlertWebServices getWebServiceByKey(String key) throws ScopixException {
        log.info("start");
        AlertWebServices webService = null;
        try {
            webService = null;
            String url = WebServiceUrlMapping.getInstance().getURL(key);
            webService = DispatcherUtil.createWSAgent(url, AlertWebServices.class);
        } catch (IOException e) {
            log.error(e, e);
            throw new ScopixException(e);
        } catch (ConfigurationException e) {
            log.error(e, e);
            throw new ScopixException(e);
        }
        log.info("end");
        return webService;
    }
}
