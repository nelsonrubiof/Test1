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
 * Evidence.java
 *
 * Created on May 3, 2007, 5:26 PM
 *
 */

package com.scopix.periscope.evidencemanagement;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlanDetail;
import com.scopix.periscope.periscopefoundation.BusinessObject;

/**
 * Entity that represents one piece of evidence. <br>
 * <br>
 * This class should be persistent.
 * 
 * @author jorge
 */
@Entity
public class Evidence extends BusinessObject {

	@ManyToOne(fetch = FetchType.EAGER)
	private ExtractionPlanDetail extractionPlanDetail;

	private int evidenceType;

	private String evidenceUri;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date evidenceTimestamp;

	private Integer deviceId;

	private Integer processId;

	public ExtractionPlanDetail getExtractionPlanDetail() {
		return extractionPlanDetail;
	}

	public void setExtractionPlanDetail(ExtractionPlanDetail extractionPlanDetail) {
		this.extractionPlanDetail = extractionPlanDetail;
	}

	public int getEvidenceType() {
		return evidenceType;
	}

	public void setEvidenceType(int evidenceType) {
		this.evidenceType = evidenceType;
	}

	public String getEvidenceUri() {
		return evidenceUri;
	}

	public void setEvidenceUri(String evidenceUri) {
		this.evidenceUri = evidenceUri;
	}

	public Date getEvidenceTimestamp() {
		return evidenceTimestamp;
	}

	public void setEvidenceTimestamp(Date evidenceTimestamp) {
		this.evidenceTimestamp = evidenceTimestamp;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
}
