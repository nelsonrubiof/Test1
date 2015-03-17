/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * CorporateDTOs.java
 *
 *
 */
package com.scopix.periscope.securitymanagement.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author EmO
 */
@XmlRootElement(name = "StoreListDTO")
public class StoreListDTO {

	private List<StoreDTO> list;

	public List<StoreDTO> getList() {
		return list;
	}

	public void setList(List<StoreDTO> list) {
		this.list = list;
	}

}
