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

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.dto.PendingEvaluationDTO;
import com.scopix.periscope.queuemanagement.services.webservices.QueueManagementWebServices;
import com.scopix.periscope.queuemanagement.services.webservices.client.QueueManagementWebServiceClient;

/**
 * 
 * @author César Abarza Suazo.
 */
@Results({ @Result(name = "success", value = "/WEB-INF/jsp/queuemanagement/nfirst/nfirstList.jsp"),
		@Result(name = "filter", value = "/WEB-INF/jsp/queuemanagement/nfirst/filters/filters.jsp"),
		@Result(name = "list", value = "/WEB-INF/jsp/queuemanagement/nfirst/nfirstListTable.jsp") })
@Namespace("/queuemanagement")
@ParentPackage(value = "default")
public class NFirstAction extends BaseAction implements SessionAware {

	private static final String FILTER = "filter";
	private static final String LIST = "list";
	private List<PendingEvaluationDTO> pendingEvaluationDTOs;
	private Map session;
	private Integer quantity;
	private String pages;

	@Override
	public String execute() throws Exception {
		session.put("nList", null);
		return SUCCESS;
	}

	public String list() throws ScopixException {
		// Validar fechas y hacer consulta
		if (quantity == null) {
			this.addActionError(this.getText("nfirst.filters.quantity.notValid"));
			pendingEvaluationDTOs = new ArrayList<PendingEvaluationDTO>();
			session.put("nList", null);
		} else {
			Integer queueId = (Integer) session.get("OPERATOR_QUEUE");
			if (queueId == null || queueId < -1) {
				this.addActionError(this.getText("error.general.requiredField",
						new String[] { this.getText("label.queueManagement.operatorQueues") }));
			} else {
				long sessionId = getSession().get("sessionId") != null ? (Long) getSession().get("sessionId") : 0L;
				QueueManagementWebServices webService = SpringSupport.getInstance()
						.findBeanByClassName(QueueManagementWebServiceClient.class).getWebService();
				pendingEvaluationDTOs = webService.getNFirstElementOfQueue(quantity, queueId, sessionId);
				session.put("nList", pendingEvaluationDTOs);
				setPages("30");
			}
		}
		return LIST;
	}

	public String filters() {
		return FILTER;
	}

	public String readyList() {
		pendingEvaluationDTOs = (List<PendingEvaluationDTO>) session.get("nList");
		return LIST;
	}

	public String showAll() {
		pendingEvaluationDTOs = (List<PendingEvaluationDTO>) session.get("nList");
		setPages((getPages() == null || !getPages().equals("30")) ? "30" : "" + getPendingEvaluationDTOs().size());
		return LIST;
	}

	public List<Integer> getQuantityValues() {
		List<Integer> quantityValues = new ArrayList<Integer>();
		quantityValues.add(50);
		quantityValues.add(100);
		quantityValues.add(200);
		quantityValues.add(300);
		return quantityValues;
	}

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	/**
	 * @return the pendingEvaluationDTOs
	 */
	public List<PendingEvaluationDTO> getPendingEvaluationDTOs() {
		return pendingEvaluationDTOs;
	}

	/**
	 * @param pendingEvaluationDTOs the pendingEvaluationDTOs to set
	 */
	public void setPendingEvaluationDTOs(List<PendingEvaluationDTO> pendingEvaluationDTOs) {
		this.pendingEvaluationDTOs = pendingEvaluationDTOs;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the pages
	 */
	public String getPages() {
		return pages;
	}

	/**
	 * @param pages the pages to set
	 */
	public void setPages(String pages) {
		this.pages = pages;
	}
}
