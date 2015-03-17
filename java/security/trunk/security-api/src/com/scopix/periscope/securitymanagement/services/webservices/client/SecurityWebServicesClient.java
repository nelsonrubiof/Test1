/*
 * 
 * Copyright @ 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * SecurityWebServicesClient.java
 *
 * Created on 23-05-2008, 10:59:07 AM
 *
 */
package com.scopix.periscope.securitymanagement.services.webservices.client;

import com.scopix.periscope.periscopefoundation.evidence_common.dispatcher.DispatcherUtil;
import com.scopix.periscope.periscopefoundation.evidence_common.exception.ConfigurationException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.webservices.client.WebServiceUrlMapping;
import com.scopix.periscope.securitymanagement.services.webservices.SecurityWebServices;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@SpringBean
public class SecurityWebServicesClient {

    private Logger log = Logger.getLogger(SecurityWebServicesClient.class);
    private String url;
    private static SecurityWebServices webService;
    /**************************************************FOR TEST*****************************************************************/
    private static SecurityWebServicesClient instance;

    /**
     * This method initialize the instance, implementing the singleton patron, this is usefull when the class is not a SpringBean
     * @return
     * @throws java.net.MalformedURLException
     * @throws com.scopix.periscope.periscopefoundation.evidence_common.exception.ConfigurationException
     * @throws java.io.IOException
     */
    public static synchronized SecurityWebServicesClient getInstance() throws ConfigurationException,
            IOException {
        if (instance == null) {
            instance = new SecurityWebServicesClient();
        }
        return instance;
    }

    private SecurityWebServicesClient() throws ConfigurationException, IOException {
        this.init();
    }

    /***************************************************************************************************************************/
    private void init() throws ConfigurationException, IOException {
        url = WebServiceUrlMapping.getInstance().getURL("SECURITY");
        webService = DispatcherUtil.createWSAgent(url, SecurityWebServices.class);
    }

    public SecurityWebServices getWebService() {
        return webService;
    }
}
