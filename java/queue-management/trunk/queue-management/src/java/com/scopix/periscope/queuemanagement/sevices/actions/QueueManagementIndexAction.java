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
 * QueueManagementIndexAction.java
 *
 * Created on 20-05-2008, 11:16:03 AM
 *
 */
package com.scopix.periscope.queuemanagement.sevices.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import com.scopix.periscope.corporatestructuremanagement.services.webservices.client.CorporateWebServiceClient;
import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.dto.OperatorQueueDTO;
import com.scopix.periscope.queuemanagement.services.webservices.QueueManagementWebServices;
import com.scopix.periscope.queuemanagement.services.webservices.client.QueueManagementWebServiceClient;
import com.scopix.periscope.securitymanagement.services.webservices.SecurityWebServices;
import com.scopix.periscope.securitymanagement.services.webservices.client.SecurityWebServicesClient;

/**
 * 
 * @author César Abarza Suazo.
 */
@Results({ @Result(name = "success", value = "/WEB-INF/jsp/queuemanagement/queueManagement.jsp"),
		@Result(name = "index", value = "/WEB-INF/jsp/index.jsp"), @Result(name = "banner", value = "/WEB-INF/jsp/banner.jsp"),
		@Result(name = "logout", value = "/WEB-INF/jsp/logout.jsp") })
@Namespace("/")
@ParentPackage(value = "default")
public class QueueManagementIndexAction extends BaseAction implements SessionAware {

	private Logger log = Logger.getLogger(QueueManagementIndexAction.class);
	private static final String LOGOUT = "logout";
	private static final String INDEX = "index";
	private static final String BANNER = "banner";
	private String user;
	private String password;
	private Map session;
	private Integer queue;

	@Override
	public String execute() throws Exception {
		if (queue != null) {
			session.put("OPERATOR_QUEUE", queue);
		} else {
			session.remove("OPERATOR_QUEUE");
		}
		return INDEX;
	}

	public String showLogin() {
		if (session.get("sessionId") != null) {
			return SUCCESS;
		} else {
			return LOGIN;
		}
	}

	public String login() throws ScopixException {
		Integer corporate = SpringSupport.getInstance().findBeanByClassName(CorporateWebServiceClient.class).getWebService()
				.getCorporateId();
		long sessionId = SpringSupport.getInstance().findBeanByClassName(SecurityWebServicesClient.class).getWebService()
				.login(user, password, corporate);
		if (sessionId > 0) {
			session.put("sessionId", sessionId);
			return SUCCESS;
		} else {
			return LOGIN;
		}
	}

	public String logout() throws ScopixException {
		if (session.get("sessionId") != null) {
			SecurityWebServices webService = SpringSupport.getInstance().findBeanByClassName(SecurityWebServicesClient.class)
					.getWebService();
			long sessionId = session.get("sessionId") != null ? (Long) session.get("sessionId") : 0L;
			webService.logout(sessionId);
			session.remove("sessionId");
			return LOGOUT;
		} else {
			return INDEX;
		}
	}

	public List<OperatorQueueDTO> getOperatorQueues() throws ScopixException {
		List<OperatorQueueDTO> list = new ArrayList<OperatorQueueDTO>();
		try {
			list = SpringSupport.getInstance().findBeanByClassName(QueueManagementWebServiceClient.class).getWebService()
					.getOperatorQueues();
		} catch (Exception e) {
			log.debug("[getOperatorQueues] Error: " + e.getMessage());
		}
		if (list == null) {
			list = new ArrayList<OperatorQueueDTO>();
		}
		OperatorQueueDTO dto = new OperatorQueueDTO();
		dto.setId(0);
		dto.setName("Default Queue");
		OperatorQueueDTO dto2 = new OperatorQueueDTO();
		dto2.setId(-1);
		dto2.setName("Rejected Queue");
		list.add(dto);
		list.add(dto2);
		return list;
	}

	public String updateSelectedOperatorQueue() {
		if (queue != null) {
			session.put("OPERATOR_QUEUE", queue);
		} else {
			session.remove("OPERATOR_QUEUE");
		}
		return null;
	}

	public String showBanner() {
		return BANNER;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCorporate() throws ScopixException {
		String corporate = null;
		QueueManagementWebServices webService = SpringSupport.getInstance()
				.findBeanByClassName(QueueManagementWebServiceClient.class).getWebService();
		long sessionId = session.get("sessionId") != null ? (Long) session.get("sessionId") : 0L;
		List<StoreDTO> stores = webService.getStoreList(sessionId);
		if (stores != null && !stores.isEmpty()) {
			corporate = (stores.get(0).getCorporate() != null) ? stores.get(0).getCorporate().getDescription() : "";
		}
		return corporate;
	}

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	/**
	 * @return the queues
	 */
	public Integer getQueue() {
		return queue;
	}

	/**
	 * @param queues the queues to set
	 */
	public void setQueue(Integer queue) {
		this.queue = queue;
	}
}
