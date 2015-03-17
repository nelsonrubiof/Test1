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
 * CorporateDTO.java
 *
 * Created on 15-06-2010, 03:47:06 PM
 *
 */
package com.scopix.periscope.securitymanagement.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gustavo Alvarez
 */
@XmlRootElement(name = "CorporateDTO")
public class CorporateDTO {

    private Integer corporateId;
    private String name;
    private String description;
    private List<AreaTypeDTO> areaTypes;
    private List<StoreDTO> stores;

    public Integer getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(Integer corporateId) {
        this.corporateId = corporateId;
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

    public List<StoreDTO> getStores() {
        if (stores == null) {
            stores = new ArrayList<StoreDTO>();
        }
        return stores;
    }

    public void setStores(List<StoreDTO> stores) {
        this.stores = stores;
    }

    public List<AreaTypeDTO> getAreaTypes() {
        if (areaTypes == null) {
            areaTypes = new ArrayList<AreaTypeDTO>();
        }
        return areaTypes;
    }

    public void setAreaTypes(List<AreaTypeDTO> areaTypes) {
        this.areaTypes = areaTypes;
    }
}
