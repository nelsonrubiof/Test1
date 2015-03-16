/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * ExtractionServicesServersManager.java
 *
 * Created on 16-06-2008, 07:32:48 PM
 *
 */
package com.scopix.periscope.extractionservicesserversmanagement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import com.scopix.periscope.extractionplanmanagement.services.webservices.ExtractionPlanWebService;
import com.scopix.periscope.extractionplanmanagement.services.webservices.client.ExtractionPlanWebServiceClient;
import com.scopix.periscope.extractionservicesserversmanagement.commands.GetExtractionPlanToPastCommand;
import com.scopix.periscope.extractionservicesserversmanagement.commands.SendExtractionPlanToExtractionServerCommand;
import com.scopix.periscope.extractionservicesserversmanagement.dao.EEServicesServerDAO;
import com.scopix.periscope.extractionservicesserversmanagement.dao.EvidenceExtractionServicesServerDAO;
import com.scopix.periscope.extractionservicesserversmanagement.dao.EvidenceProviderRequestDAO;
import com.scopix.periscope.extractionservicesserversmanagement.dao.ExtractionPlanDetailDAO;
import com.scopix.periscope.extractionservicesserversmanagement.dao.MetricRequestDAO;
import com.scopix.periscope.extractionservicesserversmanagement.dao.SituationExtractionRequestDAO;
import com.scopix.periscope.extractionservicesserversmanagement.dao.SituationRequestDAO;
import com.scopix.periscope.extractionservicesserversmanagement.dao.SituationRequestRangeDAO;
import com.scopix.periscope.extractionservicesserversmanagement.dao.SituationSensorDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 * 
 * @author marko.perich
 */
@SpringBean(rootClass = ExtractionServicesServersManager.class)
@Transactional(rollbackFor = { ScopixException.class })
public class ExtractionServicesServersManager implements InitializingBean {

	private static Logger log = Logger.getLogger(ExtractionServicesServersManager.class);
	private GenericDAO dao;
	private EEServicesServerDAO eEServicesServerDAO;
	private EvidenceProviderRequestDAO evidenceProviderRequestDAO;
	private MetricRequestDAO metricRequestDAO;
	private SituationExtractionRequestDAO situationExtractionRequestDAO;
	private SituationRequestRangeDAO situationRequestRangeDAO;
	private SituationSensorDAO situationSensorDAO;
	private SituationRequestDAO situationRequestDAO;

	@Override
	public void afterPropertiesSet() throws Exception {
		initializeWebserviceClient();
	}

	public void initializeWebserviceClient() {
		EvidenceExtractionServicesServerDAO dao = SpringSupport.getInstance().findBeanByClassName(
				EvidenceExtractionServicesServerDAO.class);
		try {
			log.info("Initializing ExtractionPlanWebServicesClient");
			// initialize web services
			List<EvidenceExtractionServicesServer> evidenceExtractionServicesServers = dao
					.getAllEvidenceExtractionServicesServerEnabled();
			Map<String, String> servers = new HashMap<String, String>();

			log.debug("after getAllEvidenceExtractionServicesServerEnabled, list: [" + evidenceExtractionServicesServers + "]");

			for (EvidenceExtractionServicesServer evidenceServicesServer : evidenceExtractionServicesServers) {
				servers.put(evidenceServicesServer.getUrl(), evidenceServicesServer.getUrl());
			}
			log.debug("servers: [" + servers + "]");

			ExtractionPlanWebServiceClient.getInstance().initializeClients(servers);
		} catch (Exception ex) {
			log.warn("Error initializing Web services  for Evidence Services Servers: [" + ex.getMessage() + "]", ex);
		}

	}

	/**
	 * This method iterates thru the list, saves the information for each
	 * evidenceExtractionServicesServer and invokes the webservice in the
	 * evidence extraction server to set the new extractionplan.
	 * 
	 * @param evidenceExtractionServicesServers List of evidence extraction
	 *            servers managed by this evidence server and all the
	 *            information to create its extractionplans
	 * 
	 */
	public void newExtractionPlan(List<EvidenceExtractionServicesServer> evidenceExtractionServicesServers)
			throws ScopixException {
		log.info("start");
		ExtractionPlanDetailDAO dao = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanDetailDAO.class);
		for (EvidenceExtractionServicesServer evidenceExtractionServicesServer : evidenceExtractionServicesServers) {
			// la limpieza se deba hacer por id en Business Services no por
			// serverId
			log.debug("EvidenceExtractionServicesServer : " + evidenceExtractionServicesServer.getId() + " deleting EP.");
			deleteExtractionPlan(evidenceExtractionServicesServer.getIdAtBusinessServices(),
					evidenceExtractionServicesServer.getExtractionPlans());

			// save evidenceExtractionServicesServer
			log.debug("Saving EvidenceExtractionServicesServer : " + evidenceExtractionServicesServer.getId());
			getDao().save(evidenceExtractionServicesServer);

			log.debug("Saving EvidenceExtractionServicesServer.providers: "
					+ evidenceExtractionServicesServer.getEvidenceProviders().size());
			getDao().save(evidenceExtractionServicesServer.getEvidenceProviders());

			log.debug("Saving EvidenceExtractionServicesServer.getExtractionPlans: "
					+ evidenceExtractionServicesServer.getExtractionPlans().size());
			getDao().save(evidenceExtractionServicesServer.getExtractionPlans());

			for (ExtractionPlan ep : evidenceExtractionServicesServer.getExtractionPlans()) {

				log.debug("For ExtractionPlan: " + ep.getId());

				log.debug("Saving ExtractionPlan.getStoreTimes: " + ep.getStoreTimes().size());
				getDao().save(ep.getStoreTimes());

				log.debug("Saving ExtractionPlan.getSituationRequests: " + ep.getSituationRequests().size());
				getSituationRequestDAO().save(ep.getSituationRequests());

				for (SituationRequest situationRequest : ep.getSituationRequests()) {

					log.debug("For situationRequest: " + situationRequest.getId());

					log.debug("Saving situationRequest.getMetricRequests: " + situationRequest.getMetricRequests().size());
					getMetricRequestDAO().save(situationRequest.getMetricRequests());

					log.debug("Saving situationRequest.getSituationSensors: " + situationRequest.getSituationSensors().size());
					getSituationSensorDAO().save(situationRequest.getSituationSensors());

					log.debug("Saving MetricRequest: " + situationRequest.getMetricRequests().size());
					for (MetricRequest mr : situationRequest.getMetricRequests()) {
						getEvidenceProviderRequestDAO().save(mr.getEvidenceProviderRequests());
					}

					// se agrega nueva estructura para manejo de random Camera
					log.debug("Saving situationRequest.getSituationRequestRanges: "
							+ situationRequest.getSituationRequestRanges().size());
					getSituationRequestRangeDAO().save(situationRequest.getSituationRequestRanges());

					log.debug("Saving SituationRequestRanges: " + situationRequest.getSituationRequestRanges().size());
					for (SituationRequestRange srr : situationRequest.getSituationRequestRanges()) {
						getSituationExtractionRequestDAO().save(srr.getSituationExtractionRequests());
					}
				}

				log.debug("Saving saveAllExtractionPlanDetails: " + ep.getExtractionPlanDetails().size());
				dao.saveAllExtractionPlanDetail(ep.getExtractionPlanDetails());

			}
			initializeWebserviceClient();

			log.debug("Getting extractionPlanWebService ");
			ExtractionPlanWebService extractionPlanWebService = ExtractionPlanWebServiceClient.getInstance().getWebServiceClient(
					evidenceExtractionServicesServer.getUrl());

			log.debug("Creating ExtractionPlanToExtractionServer ");
			SendExtractionPlanToExtractionServerCommand sendExtractionPlanToExtractionServerCommand = new SendExtractionPlanToExtractionServerCommand();

			log.debug("Sending ExtractionPlanToExtractionServer ");
			sendExtractionPlanToExtractionServerCommand.execute(evidenceExtractionServicesServer, extractionPlanWebService);

		}
		log.info("end");
	}

	/**
	 * Corresponde al id del EvidenceExtractionServicesServer en Business
	 * Services
	 */
	private void deleteExtractionPlan(Integer id, List<ExtractionPlan> eps) throws ScopixException {
		EvidenceExtractionServicesServerDAO dao = SpringSupport.getInstance().findBeanByClassName(
				EvidenceExtractionServicesServerDAO.class);
		try {
			ExtractionPlan epClient = null;
			if (eps.size() == 1) {
				epClient = eps.get(0);
			} else {
				// revisar que hacer solo deberia venir 1
				throw new ScopixException("Se recibe mas de un Extraction Plan desde Cliente");
			}

			EvidenceExtractionServicesServer evidenceExtractionServicesServer = dao
					.getEvidenceExtractionServicesServerByEvidenceExtractionServicesIdInBusinessServices(id,
							epClient.getStoreName());
			if (evidenceExtractionServicesServer != null) {
				// && evidenceExtractionServicesServer.getExtractionPlan() !=
				// null) {
				// recorremos los planes asociados y verificamos que sea el para
				// store que se recibe
				for (ExtractionPlan epServer : evidenceExtractionServicesServer.getExtractionPlans()) {
					// esto solo deberia ocurrir una vez en cada EPClient
					if (epServer.getStoreId().equals(epClient.getStoreId())) {
						// evidenceExtractionServicesServer.getExtractionPlan().setExpiration(new
						// Date());
						epServer.setExpiration(new Date());
						// SpringSupport.getInstance().findGenericDAO().save(evidenceExtractionServicesServer.getExtractionPlan());
						getDao().save(epServer);
					}
				}
			}
		} catch (NullPointerException ex) {
			log.debug("[deleteExtractioPlan] error: " + ex.getMessage());
		}
	}

	public List<Integer> extractionPlanToPast(String date, Integer extractionServicesServerId, String storeName)
			throws ScopixException {
		log.debug("[extractionPlanToPast] start");
		List<Integer> data = null;
		GetExtractionPlanToPastCommand command = new GetExtractionPlanToPastCommand();
		data = command.execute(date, extractionServicesServerId, storeName);
		log.debug("[extractionPlanToPast] end, data = " + data);
		return data;
	}

	/**
	 * @return the dao
	 */
	public GenericDAO getDao() {
		if (dao == null) {
			dao = HibernateSupport.getInstance().findGenericDAO();
		}
		return dao;
	}

	/**
	 * @param dao the dao to set
	 */
	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}

	public EEServicesServerDAO geteEServicesServerDAO() {
		if (eEServicesServerDAO == null) {
			eEServicesServerDAO = SpringSupport.getInstance().findBeanByClassName(EEServicesServerDAO.class);
		}
		return eEServicesServerDAO;
	}

	public void seteEServicesServerDAO(EEServicesServerDAO eEServicesServerDAO) {
		this.eEServicesServerDAO = eEServicesServerDAO;
	}

	public EvidenceProviderRequestDAO getEvidenceProviderRequestDAO() {
		if (evidenceProviderRequestDAO == null) {
			evidenceProviderRequestDAO = SpringSupport.getInstance().findBeanByClassName(EvidenceProviderRequestDAO.class);
		}
		return evidenceProviderRequestDAO;
	}

	public void setEvidenceProviderRequestDAO(EvidenceProviderRequestDAO evidenceProviderRequestDAO) {
		this.evidenceProviderRequestDAO = evidenceProviderRequestDAO;
	}

	public MetricRequestDAO getMetricRequestDAO() {
		if (metricRequestDAO == null) {
			metricRequestDAO = SpringSupport.getInstance().findBeanByClassName(MetricRequestDAO.class);
		}
		return metricRequestDAO;
	}

	public void setMetricRequestDAO(MetricRequestDAO metricRequestDAO) {
		this.metricRequestDAO = metricRequestDAO;
	}

	public SituationExtractionRequestDAO getSituationExtractionRequestDAO() {
		if (situationExtractionRequestDAO == null) {
			situationExtractionRequestDAO = SpringSupport.getInstance().findBeanByClassName(SituationExtractionRequestDAO.class);
		}
		return situationExtractionRequestDAO;
	}

	public void setSituationExtractionRequestDAO(SituationExtractionRequestDAO situationExtractionRequestDAO) {
		this.situationExtractionRequestDAO = situationExtractionRequestDAO;
	}

	public SituationRequestRangeDAO getSituationRequestRangeDAO() {
		if (situationRequestRangeDAO == null) {
			situationRequestRangeDAO = SpringSupport.getInstance().findBeanByClassName(SituationRequestRangeDAO.class);
		}
		return situationRequestRangeDAO;
	}

	public void setSituationRequestRangeDAO(SituationRequestRangeDAO situationRequestRangeDAO) {
		this.situationRequestRangeDAO = situationRequestRangeDAO;
	}

	public SituationSensorDAO getSituationSensorDAO() {
		if (situationSensorDAO == null) {
			situationSensorDAO = SpringSupport.getInstance().findBeanByClassName(SituationSensorDAO.class);
		}
		return situationSensorDAO;
	}

	public void setSituationSensorDAO(SituationSensorDAO situationSensorDAO) {
		this.situationSensorDAO = situationSensorDAO;
	}

	public SituationRequestDAO getSituationRequestDAO() {
		if (situationRequestDAO == null) {
			situationRequestDAO = SpringSupport.getInstance().findBeanByClassName(SituationRequestDAO.class);
		}
		return situationRequestDAO;
	}

	public void setSituationRequestDAO(SituationRequestDAO situationRequestDAO) {
		this.situationRequestDAO = situationRequestDAO;
	}
}
