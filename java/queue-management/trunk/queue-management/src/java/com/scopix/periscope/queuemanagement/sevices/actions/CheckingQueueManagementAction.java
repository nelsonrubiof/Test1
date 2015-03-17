/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.queuemanagement.sevices.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.scopix.periscope.corporatestructuremanagement.dto.AreaDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import com.scopix.periscope.evaluationmanagement.EvaluationQueue;
import com.scopix.periscope.evaluationmanagement.EvaluationState;
import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.QueueManager;
import com.scopix.periscope.queuemanagement.dto.FilteringData;
import com.scopix.periscope.queuemanagement.dto.OperatorQueueDTO;
import com.scopix.periscope.queuemanagement.dto.PendingEvaluationDTO;
import com.scopix.periscope.queuemanagement.services.webservices.QueueManagementWebServices;
import com.scopix.periscope.queuemanagement.services.webservices.client.QueueManagementWebServiceClient;

/**
 * 
 * @author Gustavo Alvarez
 */
@Results({ @Result(name = "success", value = "/WEB-INF/jsp/queuemanagement/checking/checkingList.jsp"),
		@Result(name = "filter", value = "/WEB-INF/jsp/queuemanagement/checking/filters/filters.jsp"),
		@Result(name = "areafilter", value = "/WEB-INF/jsp/queuemanagement/checking/filters/areaFilter.jsp"),
		@Result(name = "list", value = "/WEB-INF/jsp/queuemanagement/checking/checkingListTable.jsp") })
@Namespace("/queuemanagement")
@ParentPackage(value = "default")
public class CheckingQueueManagementAction extends BaseAction implements SessionAware {

	Logger log = Logger.getLogger(CheckingQueueManagementAction.class);
	private static final String AREA_FILTER = "areafilter";
	private static final String FILTER = "filter";
	private static final String LIST = "list";
	private static List<PendingEvaluationDTO> pendingEvaluationDTOs;
	private FilteringData filters;
	private String position;
	private String pageChecking;
	private volatile Integer[] chk;
	private String[] checks;
	private List<StoreDTO> stores;
	public List<AreaDTO> areas;
	private Map session;

	/**
	 * 
	 * @return
	 * @throws java.lang.Exception
	 */
	@SkipValidation
	@Override
	public String execute() throws Exception {
		this.setPendingEvaluationDTOs(new ArrayList<PendingEvaluationDTO>());
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws ScopixException
	 */
	@SkipValidation
	public String list() throws ScopixException {
		// Validate Filters
		if (getFilters() == null) {
			// this.addActionError(this.getText("pendinglist.filters.null"));
			filters = (FilteringData) ServletActionContext.getRequest().getSession().getAttribute("CHECKING-FILTERS");
		}
		if (filters != null) {
			if (getFilters().getDate() == null) {
				this.addActionError(this.getText("pendinglist.filters.date.notValid"));
			} else {
				Integer queueId = (Integer) session.get("OPERATOR_QUEUE");
				if (queueId == null || queueId < 0) {
					this.addActionError(this.getText("error.general.requiredField",
							new String[] { this.getText("label.queueManagement.operatorQueues") }));
				} else {
					getFilters().setQueue(EvaluationQueue.OPERATOR.name());
					getFilters().setStatus(EvaluationState.CHECKING.name());
					getFilters().setQueueNameId(queueId);
					ServletActionContext.getRequest().getSession().setAttribute("CHECKING-FILTERS", filters);
					QueueManagementWebServices webService = SpringSupport.getInstance()
							.findBeanByClassName(QueueManagementWebServiceClient.class).getWebService();
					long sessionId = session.get("sessionId") != null ? (Long) session.get("sessionId") : 0L;
					List<PendingEvaluationDTO> aux = webService.getPendingEvaluationList(getFilters(), sessionId);
					if (aux != null && !aux.isEmpty()) {
						this.setPendingEvaluationDTOs(aux);
						setPageChecking("30");
					} else {
						this.setPendingEvaluationDTOs(new ArrayList<PendingEvaluationDTO>());
					}
				}
			}
		}
		return LIST;
	}

	/**
	 * 
	 * @return
	 */
	@SkipValidation
	public String readyList() {
		SpringSupport.getInstance().findBeanByClassName(com.scopix.periscope.queuemanagement.QueueManager.class)
				.setChecked(getPendingEvaluationDTOs(), getAllSelected("checkingChecks"), true);
		Collections.sort(getPendingEvaluationDTOs(), PendingEvaluationDTO.getComparatorByPriority());
		return LIST;
	}

	/**
	 * 
	 * @return
	 */
	@SkipValidation
	public String showAll() {
		setPageChecking((pageChecking == null || !pageChecking.equals("30")) ? "30" : "" + getPendingEvaluationDTOs().size());
		this.removeCheckedValues("checs");
		return LIST;
	}

	@Validations(conversionErrorFields = { @ConversionErrorFieldValidator(fieldName = "checks", message = ""),
			@ConversionErrorFieldValidator(fieldName = "startPosition", message = "You must give a valid start position") })
	public String recover() throws ScopixException {
		List<Integer> checked = getAllSelected("checkingChecks");
		SpringSupport.getInstance().findBeanByClassName(com.scopix.periscope.queuemanagement.QueueManager.class)
				.uncheck(getPendingEvaluationDTOs());
		if (checked.size() == 0) {
			this.addActionError(this.getText("error.nothing.checked"));
		} else if (getStartPosition() == null) {
			this.addActionError(this.getText("error.invalidStartPosition"));
		} else {
			Integer queueId = (Integer) session.get("OPERATOR_QUEUE");
			if (queueId == null || queueId < 0) {
				this.addActionError(this.getText("error.general.requiredField",
						new String[] { this.getText("label.queueManagement.operatorQueues") }));
			} else {
				filters = (FilteringData) ServletActionContext.getRequest().getSession().getAttribute("CHECKING-FILTERS");
				filters.setQueueNameId(queueId);
				QueueManagementWebServices webService = SpringSupport.getInstance()
						.findBeanByClassName(QueueManagementWebServiceClient.class).getWebService();
				long sessionId = session.get("sessionId") != null ? (Long) session.get("sessionId") : 0L;
				List<PendingEvaluationDTO> aux = webService.backToQueue(checked, filters, getStartPosition(),
						EvaluationState.CHECKING, sessionId);
				if (aux != null) {
					this.setPendingEvaluationDTOs(aux);
				}
				this.removeCheckedValues("checkingChecks");
			}
		}
		return LIST;
	}

	/**
	 * this method change state to deleted
	 * 
	 * @return
	 * @throws ScopixException
	 */
	@SkipValidation
	public String changeState() throws ScopixException {
		List<Integer> checked = getAllSelected("checks");
		SpringSupport.getInstance().findBeanByClassName(QueueManager.class).uncheck(getPendingEvaluationDTOs());
		if (checked.size() == 0) {
			this.addActionError(this.getText("error.nothing.checked"));
		} else {
			Integer queueId = (Integer) session.get("OPERATOR_QUEUE");
			if (queueId == null || queueId < 0) {
				this.addActionError(this.getText("error.general.requiredField",
						new String[] { this.getText("label.queueManagement.operatorQueues") }));
			} else {
				QueueManagementWebServices webService = SpringSupport.getInstance()
						.findBeanByClassName(QueueManagementWebServiceClient.class).getWebService();
				long sessionId = session.get("sessionId") != null ? (Long) session.get("sessionId") : 0L;
				webService.changeState(checked, EvaluationState.DELETED.name(), sessionId);
				filters = (FilteringData) ServletActionContext.getRequest().getSession().getAttribute("CHECKING-FILTERS");
				filters.setQueueNameId(queueId);
				List<PendingEvaluationDTO> aux = webService.getPendingEvaluationList(filters, sessionId);
				if (aux != null) {
					this.setPendingEvaluationDTOs(aux);
				}
				this.removeCheckedValues("checks");
			}
		}
		return LIST;
	}

	public String filters() {
		return FILTER;
	}

	/*
	 * This method keep the id of checks in session scope
	 * 
	 * @param checksName
	 */
	private void keepCheckedValues(String checksName) {
		String page = ServletActionContext.getRequest().getParameter("d-16389-p");
		if (page == null) {
			page = "1";
		}
		String order = ServletActionContext.getRequest().getParameter("d-16389-o");
		String sort = ServletActionContext.getRequest().getParameter("d-16389-s");
		if (order != null
				&& (!order.equals((String) ServletActionContext.getRequest().getSession().getAttribute("previousOrder")) || !sort
						.equals((String) ServletActionContext.getRequest().getSession().getAttribute("previousSort")))) {
			this.removeCheckedValues(checksName);
			ServletActionContext.getRequest().getSession().setAttribute("previousOrder", order);
			ServletActionContext.getRequest().getSession().setAttribute("previousSort", sort);
		} else {
			Map<String, List<Integer>> checked = (Map<String, List<Integer>>) ServletActionContext.getRequest().getSession()
					.getAttribute(checksName);
			if (checked == null) {
				checked = new LinkedHashMap<String, List<Integer>>();
			}
			List<Integer> selected = new ArrayList<Integer>();
			if (getIntegerChecs() != null) {
				for (Integer checkId : getIntegerChecs()) {
					selected.add(checkId);
				}
			}
			// Adding selected to page
			checked.put((String) ServletActionContext.getRequest().getSession().getAttribute("previousPage"), selected);
			ServletActionContext.getRequest().getSession().setAttribute(checksName, checked);
		}
		// At end change previous page
		ServletActionContext.getRequest().getSession().setAttribute("previousPage", page);
	}

	/**
	 * this method remove the checks values from session scope
	 * 
	 * @param checksName
	 */
	private void removeCheckedValues(String checksName) {
		ServletActionContext.getRequest().getSession().removeAttribute(checksName);
		ServletActionContext.getRequest().getSession().setAttribute("previousPage", "1");
		ServletActionContext.getRequest().getSession().removeAttribute("previousOrder");
		ServletActionContext.getRequest().getSession().removeAttribute("previousSort");
	}

	/**
	 * this method get all checks than be selected
	 * 
	 * @param checkName
	 * @return
	 */
	private List<Integer> getAllSelected(String checkName) {
		List<Integer> allSelected = null;
		Map<String, List<Integer>> checked = null;
		// get new selections
		this.keepCheckedValues(checkName);
		checked = (Map<String, List<Integer>>) ServletActionContext.getRequest().getSession().getAttribute(checkName);
		allSelected = new ArrayList<Integer>();
		if (checked != null) {
			// get all selected
			for (List<Integer> pageSelected : checked.values()) {
				allSelected.addAll(pageSelected);
			}
		}
		return allSelected;
	}

	/**
	 * 
	 * @return list of pendingEvaluationSelected
	 */
	public Integer[] getIntegerChecs() {
		if (getChk() == null) {
			if (getChecks() != null && getChecks().length > 0 && !getChecks()[0].equalsIgnoreCase("false")) {
				setChk(new Integer[getChecks().length]);
				for (int i = 0; i < getChk().length; i++) {
					getChk()[i] = Integer.parseInt(getChecks()[i]);
				}
			}
			setChecks(null);
		}
		return getChk();
	}

	public List<PendingEvaluationDTO> getPendingEvaluationDTOs() {
		if (pendingEvaluationDTOs == null) {
			pendingEvaluationDTOs = new ArrayList<PendingEvaluationDTO>();
		}
		return pendingEvaluationDTOs;
	}

	public Integer getStartPosition() {
		Integer value = null;
		try {
			value = Integer.parseInt(position);
			if (value <= 0) {
				value = null;
				position = null;
			}
		} catch (Exception e) {
			position = null;
		}
		return value;
	}

	public void setPendingEvaluationDTOs(List<PendingEvaluationDTO> pendingEvaluationDTOList) {
		pendingEvaluationDTOs = pendingEvaluationDTOList;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer[] getChk() {
		return chk;
	}

	public void setChk(Integer[] chk) {
		this.chk = chk;
	}

	public String[] getChecks() {
		return checks;
	}

	public void setChecks(String[] checks) {
		this.checks = checks;
	}

	public FilteringData getFilters() {
		return filters;
	}

	public void setFilters(FilteringData filters) {
		this.filters = filters;
	}

	public String getToday() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(new Date());
	}

	public List<StoreDTO> getStoreArray() throws ScopixException {
		stores = (List<StoreDTO>) session.get("stores");
		if (stores == null || stores.isEmpty()) {
			QueueManagementWebServices webService = SpringSupport.getInstance()
					.findBeanByClassName(QueueManagementWebServiceClient.class).getWebService();
			long sessionId = session.get("sessionId") != null ? (Long) session.get("sessionId") : 0L;
			stores = webService.getStoreList(sessionId);
			if (stores == null) {
				stores = new ArrayList<StoreDTO>();
			}
			session.put("stores", stores);
		}
		Collections.sort(stores, StoreDTO.getComparatorByName());
		return stores;
	}

	public String getAreas() {
		if (filters != null && filters.getStore() != null && filters.getStore() > 0) {
			stores = (List<StoreDTO>) session.get("stores");
			if (stores != null) {
				Collections.sort(stores);
				StoreDTO store = new StoreDTO();
				store.setId(filters.getStore());
				int index = Collections.binarySearch(stores, store);
				if (index >= 0) {
					areas = stores.get(index).getAreas();
				}
				Collections.sort(areas, AreaDTO.getComparatorByName());
				Collections.sort(stores, StoreDTO.getComparatorByName());
			}
		}
		if (areas == null) {
			areas = new ArrayList<AreaDTO>();
		}
		return AREA_FILTER;
	}

	public List<AreaDTO> getAreaArray() {
		if (areas == null) {
			areas = new ArrayList<AreaDTO>();
		}
		return areas;
	}

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public String getPageChecking() {
		return pageChecking;
	}

	public void setPageChecking(String pageChecking) {
		this.pageChecking = pageChecking;
	}

	public List<OperatorQueueDTO> getOperatorQueues() throws ScopixException {
		List<OperatorQueueDTO> list = new ArrayList<OperatorQueueDTO>();
		try {
			list = SpringSupport.getInstance().findBeanByClassName(QueueManagementWebServiceClient.class).getWebService()
					.getOperatorQueues();
		} catch (Exception e) {
			log.debug("[getOperatorQueues] exception: " + e.getMessage());
		}
		if (list == null) {
			list = new ArrayList<OperatorQueueDTO>();
		}
		return list;
	}
}
