/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  StoreVO.java
 * 
 *  Created on 24-08-2012, 10:43:59 AM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement;

/**
 *
 * @author nelson
 */
public class StoreVO {

    private Integer id;
    private String name;
    private String description;
    private Integer numbersResult;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumbersResult() {
        return numbersResult;
    }

    public void setNumbersResult(Integer numbersResult) {
        this.numbersResult = numbersResult;
    }
}
