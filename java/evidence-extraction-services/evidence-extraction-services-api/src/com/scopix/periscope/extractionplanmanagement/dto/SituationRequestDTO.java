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
 * SituationRequestDTO.java
 *
 * Created on 30-03-2009, 08:58:40 PM
 *
 */
package com.scopix.periscope.extractionplanmanagement.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Cesar Abarza
 */
public class SituationRequestDTO implements Comparable<SituationRequestDTO> {

	private Integer situationTemplateId;
	/**
	 * frecuency y duration ya no deben ocuparse desde estos campos sino desde
	 * los rangos
	 */
	private Integer frecuency; // In minutes
	private Integer duration; // In seconds
	private List<MetricRequestDTO> metricRequestDTOs;
	private List<SituationSensorDTO> situationSensors;
	/**
	 * se utilzan para el manejo de random de camara
	 */
	private Boolean randomCamera;
	private List<SituationRequestRangeDTO> situationRequestRangeDTOs;
	private Integer priorization;

	/**
	 * @return the situationTemplateId
	 */
	public Integer getSituationTemplateId() {
		return situationTemplateId;
	}

	/**
	 * @param situationTemplateId the situationTemplateId to set
	 */
	public void setSituationTemplateId(Integer situationTemplateId) {
		this.situationTemplateId = situationTemplateId;
	}

	/**
	 * @return the frecuency
	 */
	public Integer getFrecuency() {
		return frecuency;
	}

	/**
	 * @param frecuency the frecuency to set
	 */
	public void setFrecuency(Integer frecuency) {
		this.frecuency = frecuency;
	}

	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * @return the metricRequestDTOs
	 */
	public List<MetricRequestDTO> getMetricRequestDTOs() {
		if (metricRequestDTOs == null) {
			metricRequestDTOs = new ArrayList<MetricRequestDTO>();
		}
		return metricRequestDTOs;
	}

	/**
	 * @param metricRequestDTOs the metricRequestDTOs to set
	 */
	public void setMetricRequestDTOs(List<MetricRequestDTO> metricRequestDTOs) {
		this.metricRequestDTOs = metricRequestDTOs;
	}

	@Override
	public int compareTo(SituationRequestDTO o) {
		return this.getSituationTemplateId() - o.getSituationTemplateId();
	}

	/**
	 * @return the situationSensors
	 */
	public List<SituationSensorDTO> getSituationSensors() {
		if (situationSensors == null) {
			situationSensors = new ArrayList<SituationSensorDTO>();
		}
		return situationSensors;
	}

	/**
	 * @param situationSensors the situationSensors to set
	 */
	public void setSituationSensors(List<SituationSensorDTO> situationSensors) {
		this.situationSensors = situationSensors;
	}

	public List<SituationRequestRangeDTO> getSituationRequestRangeDTOs() {
		if (situationRequestRangeDTOs == null) {
			situationRequestRangeDTOs = new ArrayList<SituationRequestRangeDTO>();
		}
		return situationRequestRangeDTOs;
	}

	public void setSituationRequestRangeDTOs(List<SituationRequestRangeDTO> situationRequestRangeDTOs) {
		this.situationRequestRangeDTOs = situationRequestRangeDTOs;
	}

	public Boolean getRandomCamera() {
		return randomCamera;
	}

	public void setRandomCamera(Boolean randomCamera) {
		this.randomCamera = randomCamera;
	}

	public Integer getPriorization() {
		return priorization;
	}

	public void setPriorization(Integer priorization) {
		this.priorization = priorization;
	}
}
