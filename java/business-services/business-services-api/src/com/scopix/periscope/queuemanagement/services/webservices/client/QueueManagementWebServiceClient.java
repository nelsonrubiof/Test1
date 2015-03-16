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
 * QueueManagementWebServiceClient.java
 *
 * Created on 20-05-2008, 01:41:56 PM
 *
 */
package com.scopix.periscope.queuemanagement.services.webservices.client;

import com.scopix.periscope.periscopefoundation.evidence_common.dispatcher.DispatcherUtil;
import com.scopix.periscope.periscopefoundation.evidence_common.exception.ConfigurationException;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.webservices.client.WebServiceUrlMapping;
import com.scopix.periscope.queuemanagement.services.webservices.QueueManagementWebServices;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Cesar Abarza Suazo.
 */
@SpringBean
public class QueueManagementWebServiceClient {

    private static Logger log = Logger.getLogger(QueueManagementWebServiceClient.class);

    private static Map<String, QueueManagementWebServices> webServices;

    public QueueManagementWebServiceClient() {
        if (webServices == null) {
            webServices = new HashMap<String, QueueManagementWebServices>();
        }
    }

    public QueueManagementWebServices getWebService(String corporateName) throws ScopixException {
        log.info("start [corporateName:" + corporateName + "]");
        QueueManagementWebServices webService = webServices.get(corporateName);
        if (webService == null) {
            try {
                String corp = StringUtils.replace(corporateName.toUpperCase(), " ", "_");
                webService = getWebServiceByKey("BUSINESS_SERVICES_QUEUE_" + corp);
                webServices.put(corporateName, webService);
            } catch (ConfigurationException e) {
                log.error(e, e);
                throw new ScopixException(e);
            }
        }
        log.info("end");
        return webService;
    }

    public QueueManagementWebServices getWebService() throws ScopixException {
        log.info("start");
        QueueManagementWebServices webService = getWebServiceByKey("QUEUE_MANAGEMENT");
        log.info("end");
        return webService;

    }

    private QueueManagementWebServices getWebServiceByKey(String key) throws ScopixException {
        log.info("start");
        QueueManagementWebServices webService = null;
        try {
            webService = null;
            String url = WebServiceUrlMapping.getInstance().getURL(key);
            webService = DispatcherUtil.createWSAgent(url, QueueManagementWebServices.class);
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
