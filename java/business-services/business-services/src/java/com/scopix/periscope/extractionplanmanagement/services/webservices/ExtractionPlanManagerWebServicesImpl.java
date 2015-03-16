/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  ExtractionPlanManagerWebServicesImpl.java
 * 
 *  Created on 21-09-2010, 04:34:03 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.services.webservices;

import java.util.LinkedHashMap;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceProviderDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.SituationSensorDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanManager;
import com.scopix.periscope.extractionplanmanagement.dto.DetalleSolicitudDTO;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanCustomizingDTO;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanRangeDTO;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanRangeDetailDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.SortUtil;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.templatemanagement.TemplatesManager;
import com.scopix.periscope.templatemanagement.dto.MetricTemplateDTO;
import com.scopix.periscope.templatemanagement.dto.SituationTemplateDTO;

/**
 * @author nelson
 */
@WebService(endpointInterface = "com.scopix.periscope.extractionplanmanagement.services.webservices.ExtractionPlanManagerWebServices")
// @CustomWebService(serviceClass = ExtractionPlanManagerWebServices.class)
@SpringBean(rootClass = ExtractionPlanManagerWebServices.class)
public class ExtractionPlanManagerWebServicesImpl implements ExtractionPlanManagerWebServices {

	private Logger log = Logger.getLogger(ExtractionPlanManagerWebServicesImpl.class);

	@Override
	public List<SituationTemplateDTO> getSituationTemplates(long sessionId) throws ScopixWebServiceException {
		log.debug("start");
		SituationTemplate situationTemplate = new SituationTemplate();
		situationTemplate.setActive(Boolean.TRUE);
		List<SituationTemplateDTO> dTOs = null;
		try {
			dTOs = SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class)
					.getSituationTemplateDTOs(situationTemplate, sessionId);

			SituationTemplateDTO dummy = new SituationTemplateDTO();
			dTOs.add(dummy);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		LinkedHashMap<String, Boolean> colsSort = new LinkedHashMap<String, Boolean>();
		colsSort.put("name", Boolean.FALSE);

		SortUtil.sortByColumn(colsSort, dTOs);
		log.debug("end");
		return dTOs;
	}

	@Override
	public List<StoreDTO> getStores(long sessionId) throws ScopixWebServiceException {
		log.debug("start");
		List<StoreDTO> storeDTOs = null;
		try {
			storeDTOs = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class)
					.getStoreDTOs(null, sessionId);

			// ordenar por name, descripcion
			LinkedHashMap<String, Boolean> colsSort = new LinkedHashMap<String, Boolean>();
			colsSort.put("name", Boolean.FALSE);
			colsSort.put("description", Boolean.FALSE);
			SortUtil.sortByColumn(colsSort, storeDTOs);

			StoreDTO dtoBlank = new StoreDTO();
			storeDTOs.add(dtoBlank);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end " + storeDTOs.size());
		return storeDTOs;

	}

	/**
     *
     */
	@Override
	public List<ExtractionPlanCustomizingDTO> getExtractionPlanCustomizings(Integer storeId, String estado, long sessionId)
			throws ScopixWebServiceException {
		log.debug("start");
		List<ExtractionPlanCustomizingDTO> extractionPlanCustomizingDTOs = null;
		try {
			extractionPlanCustomizingDTOs = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.getExtractionPlanCustomizingDTOs(storeId, estado, sessionId);
            //DUMMY 
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		// ordenada por areaType
		LinkedHashMap<String, Boolean> colsSort = new LinkedHashMap<String, Boolean>();
		colsSort.put("areaType", Boolean.FALSE);
		SortUtil.sortByColumn(colsSort, extractionPlanCustomizingDTOs);
		ExtractionPlanCustomizingDTO dummy = new ExtractionPlanCustomizingDTO();

		extractionPlanCustomizingDTOs.add(dummy);
		log.debug("end " + extractionPlanCustomizingDTOs.size());
		return extractionPlanCustomizingDTOs;
	}

	@Override
	public ExtractionPlanCustomizingDTO createExtractionPlanCustomizing(Integer situationTemplateId, Integer storeId,
			long sessionId) throws ScopixWebServiceException {
		log.debug("start");
		ExtractionPlanCustomizingDTO customizingDTO = null;
		try {
			customizingDTO = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.createExtractionPlanCustomizingDTO(situationTemplateId, storeId, sessionId);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
		return customizingDTO;
	}

	/**
	 * Retorna la lista de Evidence Provider para un Store - SituationTemplate
	 * 
	 * @param storeId
	 * @param situationTemplateId
	 * @param sessionId
	 * @return
	 * @throws com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException
	 */
	@Override
	public List<EvidenceProviderDTO> getEvidenceProvidersStoreSituationTemplate(Integer storeId, Integer situationTemplateId,
			long sessionId) throws ScopixWebServiceException {
		log.info("start [storeId:" + storeId + "][situationTemplateId:" + situationTemplateId + "]");
		List<EvidenceProviderDTO> dTOs = null;
		try {
			dTOs = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.getEvidenceProvidersStoreSituationTemplate(storeId, situationTemplateId, sessionId);
			EvidenceProviderDTO dummy = new EvidenceProviderDTO();
			dTOs.add(dummy);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.info("end " + dTOs.size());
		return dTOs;
	}

	@Override
	public List<SituationSensorDTO> getSensors(Integer extractionPlanCustomizingId, long sessionId)
			throws ScopixWebServiceException {
		log.debug("start");
		List<SituationSensorDTO> sensorDTOs = null;
		try {
			sensorDTOs = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.getSensors(extractionPlanCustomizingId, sessionId);

			if (sensorDTOs.size() == 1) {
				sensorDTOs.add(new SituationSensorDTO());
			}

			log.debug("end SensorsDTO.size() = " + sensorDTOs.size());
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}

		return sensorDTOs;
	}

	// retorna una lista en null si no existen metricTemplate para el epc
	@Override
	public List<MetricTemplateDTO> getMetricTemplates(Integer extractionPlanCustomizingId, long sessionId)
			throws ScopixWebServiceException {
		log.debug("start");
		List<MetricTemplateDTO> metricTemplateDTOs = null;
		try {
			metricTemplateDTOs = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.getMetricTemplates(extractionPlanCustomizingId, sessionId);

			if (metricTemplateDTOs.size() == 1) {
				metricTemplateDTOs.add(new MetricTemplateDTO());
			}
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
		return metricTemplateDTOs;
	}

	@Override
	public ExtractionPlanCustomizingDTO getUltimoExtractionPlanCustomizingNoEnviado(Integer situationTemplateId, Integer storeId,
			long sessionId) throws ScopixWebServiceException {
		log.debug("start");
		ExtractionPlanCustomizingDTO planCustomizingDTO = null;
		try {
			planCustomizingDTO = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.getUltimoExtractionPlanCustomizingNoEnviado(situationTemplateId, storeId, sessionId);

		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end epcId: " + planCustomizingDTO.getId());
		return planCustomizingDTO;
	}

	@Override
	public ExtractionPlanCustomizingDTO getUltimoExtractionPlanCustomizingEnviado(Integer situationTemplateId, Integer storeId,
			long sessionId) throws ScopixWebServiceException {
		log.debug("start");
		ExtractionPlanCustomizingDTO planCustomizingDTO = null;
		try {
			planCustomizingDTO = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.getUltimoExtractionPlanCustomizingEnviado(situationTemplateId, storeId, sessionId);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end epcId: " + planCustomizingDTO.getId());
		return planCustomizingDTO;

	}

	@Override
	public ExtractionPlanCustomizingDTO getExtractionPlanCustomizingDatosGenerales(Integer extractionPlanCustomizingId,
			long sessionId) throws ScopixWebServiceException {
		log.debug("start");
		ExtractionPlanCustomizingDTO planCustomizingDTO = null;
		try {
			planCustomizingDTO = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.getExtractionPlanCustomizingDatosGenerales(extractionPlanCustomizingId, sessionId);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
		return planCustomizingDTO;
	}

	@Override
	public List<ExtractionPlanRangeDTO> getExtractionPlanRanges(Integer extractionPlanCustomizingId, long sessionId)
			throws ScopixWebServiceException {
		log.debug("start");
		List<ExtractionPlanRangeDTO> planRangeDTOs = null;
		try {
			planRangeDTOs = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.getExtractionPlanRanges(extractionPlanCustomizingId, sessionId);
			ExtractionPlanRangeDTO dummy = new ExtractionPlanRangeDTO();
			planRangeDTOs.add(dummy);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
		return planRangeDTOs;
	}

	@Override
	public List<ExtractionPlanRangeDetailDTO> getExtractionPlanRangeDetails(Integer extractionPlanRangeId, long sessionId)
			throws ScopixWebServiceException {
		log.debug("start");
		List<ExtractionPlanRangeDetailDTO> planRangeDetailDTOs = null;
		try {
			planRangeDetailDTOs = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.getExtractionPlanRangeDetails(extractionPlanRangeId, sessionId);

			ExtractionPlanRangeDetailDTO dummy = new ExtractionPlanRangeDetailDTO();
			planRangeDetailDTOs.add(dummy);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
		return planRangeDetailDTOs;
	}

	@Override
	public void saveEPCGeneral(ExtractionPlanCustomizingDTO planCustomizingDTO, long sessionId) throws ScopixWebServiceException {
		log.debug("start");
		try {
			SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.saveEPCGeneral(planCustomizingDTO, sessionId);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
	}

	@Override
	public ExtractionPlanRangeDTO saveExtractionPlanRange(Integer extractionPlanCustomizingId,
			ExtractionPlanRangeDTO planRangeDTO, long sessionId) throws ScopixWebServiceException {
		log.debug("start");
		ExtractionPlanRangeDTO dto = null;
		try {
			dto = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.saveExtractionPlanRange(extractionPlanCustomizingId, planRangeDTO, sessionId);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
		return dto;
	}

	@Override
	public List<ExtractionPlanRangeDetailDTO> regenerateDetailForRange(Integer extractionPlanCustomizingId,
			ExtractionPlanRangeDTO planRangeDTO, long sessionId) throws ScopixWebServiceException {
		log.debug("start");
		List<ExtractionPlanRangeDetailDTO> lista = null;
		try {
			lista = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.regenerateDetailForRange(extractionPlanCustomizingId, planRangeDTO, sessionId);

			ExtractionPlanRangeDetailDTO dummy = new ExtractionPlanRangeDetailDTO();
			lista.add(dummy);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
		return lista;
	}

	@Override
	public List<String> copyCalendarActions(Integer situationTemplateId, Integer storeId, long sessionId)
			throws ScopixWebServiceException {
		log.debug("start");
		List<String> actions = null;
		try {
			actions = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.copyCalendarActions(situationTemplateId, storeId, sessionId);
			actions.add("");
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
		return actions;
	}

	@Override
	public void copyCalendar(Integer extractionPlanCustomizingId, Integer situationTemplateId, Integer storeId, long sessionId)
			throws ScopixWebServiceException {
		log.debug("start");
		try {
			SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.copyCalendar(extractionPlanCustomizingId, situationTemplateId, storeId, sessionId);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
	}

	@Override
	public void copyDayInDays(Integer extractionPlanCustomizingId, Integer day, List<Integer> days, Boolean copyDetail,
			long sessionId) throws ScopixWebServiceException {
		log.debug("start");
		try {
			SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.copyDayInDays(extractionPlanCustomizingId, day, days, copyDetail, sessionId);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
	}

	@Override
	public void deleteExtractionPlanRange(Integer extractionPlanCustomizingId, Integer extractionPlanRangeId, long sessionId)
			throws ScopixWebServiceException {
		log.debug("start");
		try {
			SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.deleteExtractionPlanRange(extractionPlanCustomizingId, extractionPlanRangeId, sessionId);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
	}

	@Override
	public void regenerateDetailForEPC(Integer extractionPlanCustomizingId, List<Integer> days, long sessionId)
			throws ScopixWebServiceException {
		log.debug("start");
		try {
			SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.regenerateDetailForEPC(extractionPlanCustomizingId, days, sessionId);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
	}

	@Override
	public ExtractionPlanRangeDTO getExtractionPlanRange(Integer extractionPlanRangeId, long sessionId)
			throws ScopixWebServiceException {
		log.debug("start");
		ExtractionPlanRangeDTO planRangeDTO = null;
		try {
			planRangeDTO = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.getExtractionPlanRange(extractionPlanRangeId, sessionId);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
		return planRangeDTO;
	}

	@Override
	public void cleanExtractionPlanCutomizingDay(Integer extractionPlanCustomizingId, List<Integer> days, long sessionId)
			throws ScopixWebServiceException {
		log.debug("start");
		try {
			SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.cleanExtractionPlanCutomizingDay(extractionPlanCustomizingId, days, sessionId);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
	}

	@Override
	public List<DetalleSolicitudDTO> getDetalleSolicitudes(Integer extractionPlanCustomizingId, long sessionId)
			throws ScopixWebServiceException {
		log.debug("start");
		List<DetalleSolicitudDTO> solicitudDTOs = null;
		try {
			solicitudDTOs = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.getDetalleSolicitudes(extractionPlanCustomizingId, sessionId);
			DetalleSolicitudDTO dummy = new DetalleSolicitudDTO();
			solicitudDTOs.add(dummy);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
		return solicitudDTOs;
	}

	@Override
	public ExtractionPlanCustomizingDTO copyEPCToEdition(Integer extractionPlanCustomizingId, long sessionId)
			throws ScopixWebServiceException {
		log.debug("start");
		ExtractionPlanCustomizingDTO dto = null;
		try {
			dto = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.copyEPCToEdition(extractionPlanCustomizingId, sessionId);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
		return dto;
	}

	@Override
	public void disableExtractionPlanCustomizings(List<Integer> epcIds, long sessionId) throws ScopixWebServiceException {
		log.debug("start");
		try {
			SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.disableExtractionPlanCustomizings(epcIds, sessionId);
		} catch (ScopixException pex) {
			throw new ScopixWebServiceException(pex);
		}
		log.debug("end");
	}

	@Override
	public ExtractionPlanCustomizingDTO copyEPCFullToEdition(Integer extractionPlanCustomizingId, long sessionId)
			throws ScopixWebServiceException {
		log.debug("start");
		ExtractionPlanCustomizingDTO dto = null;
		try {
			dto = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class)
					.copyEPCFullToEdition(extractionPlanCustomizingId, sessionId);
		} catch (ScopixException e) {
			throw new ScopixWebServiceException(e);
		}
		log.debug("end");
		return dto;
	}
}
