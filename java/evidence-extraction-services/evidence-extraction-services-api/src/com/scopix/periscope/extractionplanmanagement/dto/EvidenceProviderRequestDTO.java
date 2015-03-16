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
 * EvidenceProviderRequestDTO.java
 *
 * Created on 30-03-2009, 09:02:54 PM
 *
 */
package com.scopix.periscope.extractionplanmanagement.dto;

/**
 * 
 * @author Cesar Abarza
 */
public class EvidenceProviderRequestDTO implements Comparable<EvidenceProviderRequestDTO> {

	private Integer deviceId;

	public int compareTo(EvidenceProviderRequestDTO o) {
		return this.getDeviceId() - o.getDeviceId();
	}

	/**
	 * @return the deviceId
	 */
	public Integer getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
}
