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
 * QualityControlWebServicesClient.java
 *
 * Created on 04-06-2008, 04:09:01 PM
 *
 */
package com.scopix.periscope.qualitycontrol.services.webservices.client;

import com.scopix.periscope.periscopefoundation.evidence_common.dispatcher.DispatcherUtil;
import com.scopix.periscope.periscopefoundation.evidence_common.exception.ConfigurationException;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.webservices.client.WebServiceUrlMapping;
import com.scopix.periscope.qualitycontrol.services.webservices.QualityControlWebServices;
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
public class QualityControlWebServicesClient {

    private Logger log = Logger.getLogger(QualityControlWebServicesClient.class);
    private static Map<String, QualityControlWebServices> webServices;

    public QualityControlWebServicesClient() {
        if (webServices == null) {
            webServices = new HashMap<String, QualityControlWebServices>();
        }
    }

    public QualityControlWebServices getWebService(String corporateName) throws ScopixException {
        log.info("start [corporateName:" + corporateName + "]");
        QualityControlWebServices webService = webServices.get(corporateName);
        if (webService == null) {
            try {
                String corp = StringUtils.replace(corporateName.toUpperCase(), " ", "_");
                webService = getWebServiceByKey("BUSINESS_SERVICES_QUALITY_CONTROL_" + corp);
                webServices.put(corporateName, webService);
            } catch (ConfigurationException e) {
                log.error(e, e);
                throw new ScopixException(e);
            }
        }
        log.info("end");
        return webService;
    }

    public QualityControlWebServices getWebService() throws ScopixException {
        log.info("start");
        QualityControlWebServices webService = getWebServiceByKey("QUALITY_CONTROL");
        log.info("end");
        return webService;
    }

    private QualityControlWebServices getWebServiceByKey(String key) throws ScopixException {
        log.info("start");
        QualityControlWebServices webService = null;
        try {
            webService = null;
            String url = WebServiceUrlMapping.getInstance().getURL(key);
            webService = DispatcherUtil.createWSAgent(url, QualityControlWebServices.class);
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
