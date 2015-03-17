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

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.scopix.periscope.periscopefoundation.BusinessObject;

/**
 * This class represents a Evidence request from business services
 * 
 * @author marko.perich
 */
@Entity
public class StoreTime extends BusinessObject implements Comparable<StoreTime> {

	private Integer dayOfWeek;
	private String openHour; // In minutes
	private String endHour; // In seconds
	@ManyToOne
	private ExtractionPlan extractionPlan;

	public int compareTo(StoreTime o) {
		return this.getDayOfWeek() - o.getDayOfWeek();
	}

	/**
	 * @return the dayOfWeek
	 */
	public Integer getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * @param dayOfWeek the dayOfWeek to set
	 */
	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	/**
	 * @return the openHour
	 */
	public String getOpenHour() {
		return openHour;
	}

	/**
	 * @param openHour the openHour to set
	 */
	public void setOpenHour(String openHour) {
		this.openHour = openHour;
	}

	/**
	 * @return the endHour
	 */
	public String getEndHour() {
		return endHour;
	}

	/**
	 * @param endHour the endHour to set
	 */
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	/**
	 * @return the extractionPlan
	 */
	public ExtractionPlan getExtractionPlan() {
		return extractionPlan;
	}

	/**
	 * @param extractionPlan the extractionPlan to set
	 */
	public void setExtractionPlan(ExtractionPlan extractionPlan) {
		this.extractionPlan = extractionPlan;
	}
}
