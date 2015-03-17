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
 * EvidenceProviderDTO.java 
 *
 * Created on 02-07-2008, 02:06:57 AM
 *
 */
package com.scopix.periscope.extractionplanmanagement.dto;

import java.util.HashMap;

/**
 * 
 * @author marko.perich
 */
public class EvidenceProviderDTO {

	private Integer id;
	private String description;
	private String providerType;
	private HashMap<String, String> definitionData;

	/**
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return
	 */
	public HashMap<String, String> getDefinitionData() {
		return definitionData;
	}

	/**
	 * 
	 * @param definitionData
	 */
	public void setDefinitionData(HashMap<String, String> definitionData) {
		this.definitionData = definitionData;
	}

	/**
	 * 
	 * @return
	 */
	public String getProviderType() {
		return providerType;
	}

	/**
	 * 
	 * @param providerType
	 */
	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}
}
