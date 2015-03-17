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
 * MetricRequestDTO.java
 *
 * Created on 30-03-2009, 09:01:16 PM
 *
 */
package com.scopix.periscope.extractionplanmanagement.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Cesar
 */
public class MetricRequestDTO implements Comparable<MetricRequestDTO> {

	private Integer metricTemplateId;
	private List<EvidenceProviderRequestDTO> evidenceProviderRequestDTOs;

	/**
	 * @return the metricTemplateId
	 */
	public Integer getMetricTemplateId() {
		return metricTemplateId;
	}

	/**
	 * @param metricTemplateId the metricTemplateId to set
	 */
	public void setMetricTemplateId(Integer metricTemplateId) {
		this.metricTemplateId = metricTemplateId;
	}

	/**
	 * @return the evidenceProviderRequestDTOs
	 */
	public List<EvidenceProviderRequestDTO> getEvidenceProviderRequestDTOs() {
		if (evidenceProviderRequestDTOs == null) {
			evidenceProviderRequestDTOs = new ArrayList<EvidenceProviderRequestDTO>();
		}
		return evidenceProviderRequestDTOs;
	}

	/**
	 * @param evidenceProviderRequestDTOs the evidenceProviderRequestDTOs to set
	 */
	public void setEvidenceProviderRequestDTOs(List<EvidenceProviderRequestDTO> evidenceProviderRequestDTOs) {
		this.evidenceProviderRequestDTOs = evidenceProviderRequestDTOs;
	}

	public int compareTo(MetricRequestDTO o) {
		return this.getMetricTemplateId() - o.getMetricTemplateId();
	}
}
