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
 * ExtractionPlanManager.java
 *
 * Created on 02-07-2008, 04:39:21 AM
 *
 */
package com.scopix.periscope.extractionplanmanagement;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.extractionmanagement.ExtractionPlan;
import com.scopix.periscope.extractionmanagement.ExtractionServer;
import com.scopix.periscope.extractionmanagement.MetricRequest;
import com.scopix.periscope.extractionmanagement.SituationRequest;
import com.scopix.periscope.extractionmanagement.SituationRequestRange;
import com.scopix.periscope.extractionmanagement.SituationSensor;
import com.scopix.periscope.extractionplanmanagement.commands.dao.EvidenceExtractionRequestDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.persistence.dao.EvidenceProviderRequestDAO;
import com.scopix.periscope.persistence.dao.ExtractionPlanDAO;
import com.scopix.periscope.persistence.dao.ExtractionServerDAO;
import com.scopix.periscope.persistence.dao.MetricRequestDAO;
import com.scopix.periscope.persistence.dao.SituationExtractionRequestDAO;
import com.scopix.periscope.persistence.dao.SituationRequestDAO;
import com.scopix.periscope.persistence.dao.SituationRequestRangeDAO;
import com.scopix.periscope.persistence.dao.SituationSensorDAO;
import com.scopix.periscope.persistence.dao.StoreTimeDAO;

/**
 * 
 * @author marko.perich
 */
@SpringBean
@Transactional(rollbackFor = { ScopixException.class })
public class ExtractionPlanManager {

	private ExtractionManager extractionManager;
	private static Logger log = Logger.getLogger(ExtractionPlanManager.class);

	private MetricRequestDAO metricRequestDAO;
	private SituationSensorDAO situationSensorDAO;
	private ExtractionServerDAO extractionServerDAO;
	private ExtractionPlanDAO extractionPlanDAO;
	private StoreTimeDAO storeTimeDAO;
	private SituationRequestDAO situationRequestDAO;
	private EvidenceProviderRequestDAO evidenceProviderRequestDAO;
	private SituationRequestRangeDAO situationRequestRangeDAO;
	private SituationExtractionRequestDAO situationExtractionRequestDAO;

	public void newExtractionPlan(ExtractionServer extractionServer) throws ScopixException {
		EvidenceExtractionRequestDAO dao = SpringSupport.getInstance().findBeanByClassName(EvidenceExtractionRequestDAO.class);

		expireExtractioPlan(extractionServer.getExtractionPlan());

		log.debug("before save extractionServer");
		getExtractionServerDAO().save(extractionServer);
		log.debug("before save ExtractionPlan");
		getExtractionPlanDAO().save(extractionServer.getExtractionPlan());
		log.debug("before save StoreTimes");
		getStoreTimeDAO().save(extractionServer.getExtractionPlan().getStoreTimes());
		log.debug("before save EvidenceProviders");
		dao.saveEvidenceProviders(extractionServer.getEvidenceProviders());
		log.debug("before save EvidenceExtractioRequests");
		dao.saveEERS(extractionServer.getExtractionPlan().getEvidenceExtractionRequests());
		log.debug("before save SituationRequests");
		getSituationRequestDAO().save(extractionServer.getExtractionPlan().getSituationRequests());
		for (SituationRequest situationRequest : extractionServer.getExtractionPlan().getSituationRequests()) {

			// si la situacion es random de Camaras agregamos un nuevo sensor
			if (situationRequest.getRandomCamera()) {
				SituationSensor situationSensor = new SituationSensor();
				situationSensor.setName("RANDOM_CAMERA_" + situationRequest.getId());
				situationSensor.setDescription("Random Camera for situationRequest " + situationRequest.getId());
				situationSensor.setSituationRequest(situationRequest);
				situationRequest.getSituationSensors().add(situationSensor);
			}

			log.debug("before save MetricRequests");
			getMetricRequestDAO().save(situationRequest.getMetricRequests());
			log.debug("before save SituationSensors");
			getSituationSensorDAO().save(situationRequest.getSituationSensors());
			for (MetricRequest mr : situationRequest.getMetricRequests()) {
				getEvidenceProviderRequestDAO().save(mr.getEvidenceProviderRequests());
			}
			log.debug("before save SituationRequestRanges");
			getSituationRequestRangeDAO().save(situationRequest.getSituationRequestRanges());
			for (SituationRequestRange range : situationRequest.getSituationRequestRanges()) {
				getSituationExtractionRequestDAO().save(range.getSituationExtractionRequests());
			}

		}
		// movemos la ejecucion del update al WebService para que el epc este
		// almacenado correctamente;
		// getExtractionManager().updateExtractionPlanByStoreName(extractionServer.getExtractionPlan().getStoreName());

	}

	private void expireExtractioPlan(ExtractionPlan extractionPlan) {
		try {
			log.debug("ExtractionPlan Id = " + extractionPlan.getId());
			getExtractionPlanDAO().expire(extractionPlan);
			log.debug("ExtractionPlan expirationDate = " + extractionPlan.getExpirationDate());
		} catch (NullPointerException ex) {
			log.error(ex);
		}
	}

	public ExtractionManager getExtractionManager() {
		if (extractionManager == null) {
			extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
		}
		return extractionManager;
	}

	public void setExtractionManager(ExtractionManager extractionManager) {
		this.extractionManager = extractionManager;
	}

	public MetricRequestDAO getMetricRequestDAO() {
		if (metricRequestDAO == null) {
			metricRequestDAO = SpringSupport.getInstance().findBeanByClassName(MetricRequestDAO.class);
		}
		return metricRequestDAO;
	}

	public SituationSensorDAO getSituationSensorDAO() {
		if (situationSensorDAO == null) {
			situationSensorDAO = SpringSupport.getInstance().findBeanByClassName(SituationSensorDAO.class);
		}
		return situationSensorDAO;
	}

	public ExtractionServerDAO getExtractionServerDAO() {
		if (extractionServerDAO == null) {
			extractionServerDAO = SpringSupport.getInstance().findBeanByClassName(ExtractionServerDAO.class);
		}
		return extractionServerDAO;
	}

	public ExtractionPlanDAO getExtractionPlanDAO() {
		if (extractionPlanDAO == null) {
			extractionPlanDAO = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanDAO.class);
		}
		return extractionPlanDAO;
	}

	public StoreTimeDAO getStoreTimeDAO() {
		if (storeTimeDAO == null) {
			storeTimeDAO = SpringSupport.getInstance().findBeanByClassName(StoreTimeDAO.class);
		}
		return storeTimeDAO;
	}

	public SituationRequestDAO getSituationRequestDAO() {
		if (situationRequestDAO == null) {
			situationRequestDAO = SpringSupport.getInstance().findBeanByClassName(SituationRequestDAO.class);
		}
		return situationRequestDAO;
	}

	public EvidenceProviderRequestDAO getEvidenceProviderRequestDAO() {
		if (evidenceProviderRequestDAO == null) {
			evidenceProviderRequestDAO = SpringSupport.getInstance().findBeanByClassName(EvidenceProviderRequestDAO.class);
		}
		return evidenceProviderRequestDAO;
	}

	public SituationRequestRangeDAO getSituationRequestRangeDAO() {
		if (situationRequestRangeDAO == null) {
			situationRequestRangeDAO = SpringSupport.getInstance().findBeanByClassName(SituationRequestRangeDAO.class);
		}
		return situationRequestRangeDAO;
	}

	public SituationExtractionRequestDAO getSituationExtractionRequestDAO() {
		if (situationExtractionRequestDAO == null) {
			situationExtractionRequestDAO = SpringSupport.getInstance().findBeanByClassName(SituationExtractionRequestDAO.class);
		}
		return situationExtractionRequestDAO;
	}

}
