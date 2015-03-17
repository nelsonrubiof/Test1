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
package com.scopix.periscope.corporatestructuremanagement.services.webservices.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.corporatestructuremanagement.services.webservices.CorporateWebServices;
import com.scopix.periscope.periscopefoundation.evidence_common.dispatcher.DispatcherUtil;
import com.scopix.periscope.periscopefoundation.evidence_common.exception.ConfigurationException;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.webservices.client.WebServiceUrlMapping;

/**
 * 
 * @author Cesar Abarza Suazo.
 */
@SpringBean(rootClass = CorporateWebServiceClient.class)
public class CorporateWebServiceClient {

	private Logger log = Logger.getLogger(CorporateWebServiceClient.class);
	private static Map<String, CorporateWebServices> webServices;

	public CorporateWebServiceClient() {
		if (webServices == null) {
			webServices = new HashMap<String, CorporateWebServices>();
		}
	}

	public CorporateWebServices getWebService(String corporateName) throws ScopixException {
		log.info("start [corporateName:" + corporateName + "]");
		CorporateWebServices webService = webServices.get(corporateName);
		if (webService == null) {
			try {
				String corp = StringUtils.replace(corporateName.toUpperCase(), " ", "_");
				webService = getWebServiceByKey("BUSINESS_SERVICES_CORPORATE_" + corp);
				webServices.put(corporateName, webService);
			} catch (ConfigurationException e) {
				log.error(e, e);
				throw new ScopixException(e);
			}
		}
		log.info("end");
		return webService;
	}

	public CorporateWebServices getWebService() throws ScopixException {
		log.info("start");
		CorporateWebServices webService = getWebServiceByKey("CORPORATE");
		log.info("end");
		return webService;

	}

	private CorporateWebServices getWebServiceByKey(String key) throws ScopixException {
		log.info("start");
		CorporateWebServices webService = null;
		try {
			webService = null;
			String url = WebServiceUrlMapping.getInstance().getURL(key);
			webService = DispatcherUtil.createWSAgent(url, CorporateWebServices.class);
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
