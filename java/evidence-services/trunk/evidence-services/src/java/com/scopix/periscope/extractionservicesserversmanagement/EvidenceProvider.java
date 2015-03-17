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
 * EvidenceProvider.java
 *
 * Created on 28-06-2008, 06:10:49 AM
 *
 */
package com.scopix.periscope.extractionservicesserversmanagement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.scopix.periscope.periscopefoundation.BusinessObject;

/**
 * 
 * @author marko.perich
 */
@Entity
public class EvidenceProvider extends BusinessObject implements Comparable<EvidenceProvider> {

	private String description;
	private String providerType;
	private Integer deviceId;
	@OneToMany(mappedBy = "evidenceProvider")
	private List<EvidenceProviderRequest> evidenceProviderRequests;
	@ManyToOne
	private EvidenceExtractionServicesServer evidenceExtractionServicesServer;
	private HashMap<String, String> definitionData;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EvidenceExtractionServicesServer getEvidenceExtractionServicesServer() {
		return evidenceExtractionServicesServer;
	}

	public void setEvidenceExtractionServicesServer(EvidenceExtractionServicesServer evidenceExtractionServicesServer) {
		this.evidenceExtractionServicesServer = evidenceExtractionServicesServer;
	}

	public HashMap<String, String> getDefinitionData() {
		return definitionData;
	}

	public void setDefinitionData(HashMap<String, String> definitionData) {
		this.definitionData = definitionData;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the evidenceProviderRequests
	 */
	public List<EvidenceProviderRequest> getEvidenceProviderRequests() {
		if (evidenceProviderRequests == null) {
			evidenceProviderRequests = new ArrayList<EvidenceProviderRequest>();
		}
		return evidenceProviderRequests;
	}

	/**
	 * @param evidenceProviderRequests the evidenceProviderRequests to set
	 */
	public void setEvidenceProviderRequests(List<EvidenceProviderRequest> evidenceProviderRequests) {
		this.evidenceProviderRequests = evidenceProviderRequests;
	}

	public int compareTo(EvidenceProvider o) {
		return this.getId() - o.getId();
	}

	public static Comparator<EvidenceProvider> deviceIdComparator = new Comparator<EvidenceProvider>() {

		public int compare(EvidenceProvider ep1, EvidenceProvider ep2) {
			return ep1.getDeviceId() - ep2.getDeviceId();
		}
	};
}