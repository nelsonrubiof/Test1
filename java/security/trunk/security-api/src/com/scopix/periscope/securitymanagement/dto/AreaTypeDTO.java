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
 * AreaTypeDTO.java
 *
 * Created on 15-06-2010, 04:01:23 PM
 *
 */
package com.scopix.periscope.securitymanagement.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gustavo Alvarez
 */
@XmlRootElement(name = "AreaTypeDTO")
public class AreaTypeDTO {
    private Integer areaTypeId;
    private String name;
    private String description;

    public Integer getAreaTypeId() {
        return areaTypeId;
    }

    public void setAreaTypeId(Integer areaTypeId) {
        this.areaTypeId = areaTypeId;
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