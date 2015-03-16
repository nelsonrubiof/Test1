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
 * EvaluationWebServicesClient.java
 *
 * Created on 27-05-2008, 06:28:17 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.services.webservices.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.corporatestructuremanagement.services.webservices.client.CorporateWebServiceClient;
import com.scopix.periscope.evaluationmanagement.services.webservices.EvaluationWebServices;
import com.scopix.periscope.periscopefoundation.evidence_common.dispatcher.DispatcherUtil;
import com.scopix.periscope.periscopefoundation.evidence_common.exception.ConfigurationException;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.webservices.client.WebServiceUrlMapping;

/**
 * 
 * @author Cesar Abarza Suazo.
 */
@SpringBean(rootClass = EvaluationWebServicesClient.class)
public class EvaluationWebServicesClient {

	private Logger log = Logger.getLogger(CorporateWebServiceClient.class);
	private static Map<String, EvaluationWebServices> webServices;

	public EvaluationWebServicesClient() {
		if (webServices == null) {
			webServices = new HashMap<String, EvaluationWebServices>();
		}
	}

	public EvaluationWebServices getWebService(String corporateName) throws ScopixException {
		log.info("start [corporateName:" + corporateName + "]");
		EvaluationWebServices webService = webServices.get(corporateName);
		if (webService == null) {
			try {
				String corp = StringUtils.replace(corporateName.toUpperCase(), " ", "_");
				webService = getWebServiceByKey("BUSINESS_SERVICES_EVALUATION_" + corp);
				webServices.put(corporateName, webService);
			} catch (ConfigurationException e) {
				log.error(e, e);
				throw new ScopixException(e);
			}
		}
		log.info("end");
		return webService;
	}

	public EvaluationWebServices getWebService() throws ScopixException {
		log.info("start");
		EvaluationWebServices webService = getWebServiceByKey("EVALUATION");
		log.info("end");
		return webService;

	}

	private EvaluationWebServices getWebServiceByKey(String key) throws ScopixException {
		log.info("start");
		EvaluationWebServices webService = null;
		try {
			webService = null;
			String url = WebServiceUrlMapping.getInstance().getURL(key);
			webService = DispatcherUtil.createWSAgent(url, EvaluationWebServices.class);
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
