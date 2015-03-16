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
 * EvidenceRequest.java
 *
 * Created on 16-06-2008, 05:34:27 PM
 *
 */
package com.scopix.periscope.extractionservicesserversmanagement;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.scopix.periscope.periscopefoundation.BusinessObject;

/**
 * This class represents a Evidence request from business services
 * 
 * @author marko.perich
 */
@Entity
public class MetricRequest extends BusinessObject implements Comparable<MetricRequest> {

	private Integer metricTemplateId;
	@ManyToOne
	private SituationRequest situationRequest;
	@OneToMany(mappedBy = "metricRequest")
	private List<EvidenceProviderRequest> evidenceProviderRequests;

	public int compareTo(MetricRequest o) {
		return this.getId() - o.getId();
	}

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
	 * @return the evidenceProviderRequests
	 */
	public List<EvidenceProviderRequest> getEvidenceProviderRequests() {
		return evidenceProviderRequests;
	}

	/**
	 * @param evidenceProviderRequests the evidenceProviderRequests to set
	 */
	public void setEvidenceProviderRequests(List<EvidenceProviderRequest> evidenceProviderRequests) {
		this.evidenceProviderRequests = evidenceProviderRequests;
	}

	/**
	 * @return the situationRequest
	 */
	public SituationRequest getSituationRequest() {
		return situationRequest;
	}

	/**
	 * @param situationRequest the situationRequest to set
	 */
	public void setSituationRequest(SituationRequest situationRequest) {
		this.situationRequest = situationRequest;
	}
}
