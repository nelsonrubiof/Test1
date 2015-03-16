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
 * ExtractionPlan.java
 *
 * Created on 19-05-2008, 04:37:46 PM
 *
 */
package com.scopix.periscope.extractionmanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.scopix.periscope.periscopefoundation.BusinessObject;

/**
 * 
 * @author marko.perich
 */
@Entity
public class ExtractionPlan extends BusinessObject {

	@OneToOne
	private ExtractionServer extractionServer;
	// @OneToMany(fetch = FetchType.EAGER, mappedBy = "extractionPlan")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "extractionPlan")
	private List<EvidenceExtractionRequest> evidenceExtractionRequests;
	@OneToMany(mappedBy = "extractionPlan", fetch = FetchType.LAZY)
	private List<SituationRequest> situationRequests;
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate = null;
	@OneToMany(mappedBy = "extractionPlan")
	private List<StoreTime> storeTimes;
	private String storeName;
	private String timeZoneId;
	private Integer storeId;

	public List<EvidenceExtractionRequest> getEvidenceExtractionRequests() {
		if (evidenceExtractionRequests == null) {
			evidenceExtractionRequests = new ArrayList<EvidenceExtractionRequest>();
		}
		return evidenceExtractionRequests;
	}

	public void setEvidenceExtractioRequests(List<EvidenceExtractionRequest> evidenceExtractionRequests) {
		this.evidenceExtractionRequests = evidenceExtractionRequests;
	}

	public ExtractionServer getExtractionServer() {
		return extractionServer;
	}

	public void setExtractionServer(ExtractionServer extractionServer) {
		this.extractionServer = extractionServer;
	}

	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the situationRequests
	 */
	public List<SituationRequest> getSituationRequests() {
		if (situationRequests == null) {
			situationRequests = new ArrayList<SituationRequest>();
		}
		return situationRequests;
	}

	/**
	 * @param situationRequests the situationRequests to set
	 */
	public void setSituationRequests(List<SituationRequest> situationRequests) {
		this.situationRequests = situationRequests;
	}

	/**
	 * @return the storeTimes
	 */
	public List<StoreTime> getStoreTimes() {
		if (storeTimes == null) {
			storeTimes = new ArrayList<StoreTime>();
		}
		return storeTimes;
	}

	/**
	 * @param storeTimes the storeTimes to set
	 */
	public void setStoreTimes(List<StoreTime> storeTimes) {
		this.storeTimes = storeTimes;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getTimeZoneId() {
		return timeZoneId;
	}

	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
}
