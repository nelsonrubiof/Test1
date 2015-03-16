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
 * ExtractionPlanDetail.java
 *
 * Created on June 18, 2007, 12:13 PM
 *
 */

package com.scopix.periscope.extractionservicesserversmanagement;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.scopix.periscope.evidencemanagement.Evidence;
import com.scopix.periscope.periscopefoundation.BusinessObject;

/**
 * 
 * @author jorge
 */
@Entity
public class ExtractionPlanDetail extends BusinessObject {

	@ManyToOne
	private ExtractionPlan extractionPlan;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "extractionPlanDetail")
	private List<EvidenceRequest> evidenceRequests;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "extractionPlanDetail")
	private List<Evidence> evidences;

	public ExtractionPlan getExtractionPlan() {
		return extractionPlan;
	}

	public void setExtractionPlan(ExtractionPlan extractionPlan) {
		this.extractionPlan = extractionPlan;
	}

	public List<EvidenceRequest> getEvidenceRequests() {
		if (evidenceRequests == null) {
			evidenceRequests = new ArrayList<EvidenceRequest>();
		}
		return evidenceRequests;
	}

	public void setEvidenceRequests(List<EvidenceRequest> evidenceRequests) {
		this.evidenceRequests = evidenceRequests;
	}

	public List<Evidence> getEvidences() {
		if (evidences == null) {
			evidences = new ArrayList<Evidence>();
		}
		return evidences;
	}

	public void setEvidences(List<Evidence> evidences) {
		this.evidences = evidences;
	}

}
