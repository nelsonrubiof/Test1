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
 * StoreDTO.java
 *
 * Created on 15-06-2010, 03:54:27 PM
 *
 */
package com.scopix.periscope.securitymanagement.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gustavo Alvarez
 */
@XmlRootElement(name = "StoreDTO")
public class StoreDTO {
    private Integer storeId;
    private String name;
    private String description;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}