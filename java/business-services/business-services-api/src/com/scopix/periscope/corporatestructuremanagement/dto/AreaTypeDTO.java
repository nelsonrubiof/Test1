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
 * AreaTypeDTO.java
 *
 * Created on 04-08-2008, 01:17:49 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.dto;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class AreaTypeDTO implements Comparable<AreaTypeDTO> {

    private Integer id;

    private String name;

    private String description;

    public int compareTo(AreaTypeDTO o) {
        return this.getId() - o.getId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
