/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * QualityControlSecurityWebServicesClient.java
 *
 * Created on 09-07-2010, 03:06:24 PM
 *
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scopix.periscope.qualitycontrol.services.webservices.client;

import com.scopix.periscope.periscopefoundation.evidence_common.dispatcher.DispatcherUtil;
import com.scopix.periscope.periscopefoundation.evidence_common.exception.ConfigurationException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.webservices.client.WebServiceUrlMapping;
import com.scopix.periscope.qualitycontrol.services.webservices.QualityControlSecurityWebServices;
import java.io.IOException;

/**
 *
 * @author Gustavo Alvarez
 */
@SpringBean
public class QualityControlSecurityWebServicesClient {

    private String url;
    private static QualityControlSecurityWebServices webService;
    /**************************************************FOR TEST**************************************************************/
    private static QualityControlSecurityWebServicesClient instance;

    /**
     * This method initialize the instance, implementing the singleton patron, this is usefull when the class is not a SpringBean
     * @return
     * @throws java.net.MalformedURLException
     * @throws com.scopix.periscope.periscopefoundation.evidence_common.exception.ConfigurationException
     * @throws java.io.IOException
     */
    public static synchronized QualityControlSecurityWebServicesClient getInstance() throws ConfigurationException,
            IOException {
        if (instance == null) {
            instance = new QualityControlSecurityWebServicesClient();
        }
        return instance;
    }

    private QualityControlSecurityWebServicesClient() throws ConfigurationException, IOException {
        this.init();
    }

    /************************************************************************************************************************/
    private void init() throws ConfigurationException, IOException {
        url = WebServiceUrlMapping.getInstance().getURL("QUALITY_CONTROL_SECURITY");
        webService = DispatcherUtil.createWSAgent(url, QualityControlSecurityWebServices.class);
    }

    public QualityControlSecurityWebServices getWebService() {
        return webService;
    }
}
