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
 * ExtractionServerDTO.java
 *
 * Created on 02-07-2008, 02:29:26 AM
 *
 */
package com.scopix.periscope.extractionplanmanagement.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author marko.perich
 */
public class ExtractionServerDTO {

	private Integer serverId;
	private List<EvidenceProviderDTO> evidenceProviderDTOs;
	private List<EvidenceExtractionRequestDTO> evidenceExtractionRequestDTOs;
	private List<SituationRequestDTO> situationRequestDTOs;
	private List<StoreTimeDTO> storeTimeDTOs;
	private String storeName;
	private String timeZone;
	private Integer storeId;

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public List<EvidenceProviderDTO> getEvidenceProviderDTOs() {
		if (evidenceProviderDTOs == null) {
			evidenceProviderDTOs = new ArrayList<EvidenceProviderDTO>();
		}
		return evidenceProviderDTOs;
	}

	public void setEvidenceProviderDTOs(List<EvidenceProviderDTO> evidenceProviderDTOs) {
		this.evidenceProviderDTOs = evidenceProviderDTOs;
	}

	public List<EvidenceExtractionRequestDTO> getEvidenceExtractionRequestDTOs() {
		if (evidenceExtractionRequestDTOs == null) {
			evidenceExtractionRequestDTOs = new ArrayList<EvidenceExtractionRequestDTO>();
		}
		return evidenceExtractionRequestDTOs;
	}

	public void setEvidenceExtractionRequestDTOs(List<EvidenceExtractionRequestDTO> evidenceExtractionRequestDTOs) {
		this.evidenceExtractionRequestDTOs = evidenceExtractionRequestDTOs;
	}

	/**
	 * @return the situationRequestDTOs
	 */
	public List<SituationRequestDTO> getSituationRequestDTOs() {
		if (situationRequestDTOs == null) {
			situationRequestDTOs = new ArrayList<SituationRequestDTO>();
		}
		return situationRequestDTOs;
	}

	/**
	 * @param situationRequestDTOs the situationRequestDTOs to set
	 */
	public void setSituationRequestDTOs(List<SituationRequestDTO> situationRequestDTOs) {
		this.situationRequestDTOs = situationRequestDTOs;
	}

	/**
	 * @return the storeTimeDTOs
	 */
	public List<StoreTimeDTO> getStoreTimeDTOs() {
		if (storeTimeDTOs == null) {
			storeTimeDTOs = new ArrayList<StoreTimeDTO>();
		}
		return storeTimeDTOs;
	}

	/**
	 * @param storeTimeDTOs the storeTimeDTOs to set
	 */
	public void setStoreTimeDTOs(List<StoreTimeDTO> storeTimeDTOs) {
		this.storeTimeDTOs = storeTimeDTOs;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		ret.append("[serverId:").append(getServerId()).append("]");
		ret.append("[evidenceProviderDTOs:").append(getEvidenceProviderDTOs().size()).append("]");
		ret.append("[evidenceExtractionRequestDTOs:").append(getEvidenceExtractionRequestDTOs().size()).append("]");
		ret.append("[situationRequestDTOs:").append(getSituationRequestDTOs().size()).append("]");
		ret.append("[storeTimeDTOs:").append(getStoreTimeDTOs().size()).append("]");
		ret.append("[storeName:").append(getStoreName()).append("]");
		ret.append("[timeZone:").append(getTimeZone()).append("]");
		ret.append("[storeId:").append(getStoreId()).append("]");
		return ret.toString();
	}
}
