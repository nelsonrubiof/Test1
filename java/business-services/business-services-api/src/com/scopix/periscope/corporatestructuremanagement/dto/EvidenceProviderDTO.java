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
 * Created on 28-06-2008, 06:06:58 AM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.dto;

import java.util.HashMap;

/**
 *
 * @author marko.perich
 */
public class EvidenceProviderDTO {

    private Integer id;
    private String description;
    private String areaType;
    private String providerType;
    private HashMap<String, String> definitionData;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HashMap<String, String> getDefinitionData() {
        return definitionData;
    }

    public void setDefinitionData(HashMap<String, String> definitionData) {
        this.definitionData = definitionData;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }
}
