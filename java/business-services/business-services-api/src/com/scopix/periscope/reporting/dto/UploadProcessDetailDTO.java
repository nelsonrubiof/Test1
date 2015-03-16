/*
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
 *  UploadProcessDetailDTO.java
 *  
 *  Created on 10-01-2011, 05:22:08 PM
 */
package com.scopix.periscope.reporting.dto;

import com.scopix.periscope.corporatestructuremanagement.dto.AreaTypeDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;

/**
 *
 * @author Nelson Rubio
 */
public class UploadProcessDetailDTO {

    private Integer id;
    private AreaTypeDTO areaType;
    private StoreDTO store;
    private String dateEnd;
    private Integer totalRecords;
    private Integer upRecords;
    private Double percent;

    public AreaTypeDTO getAreaType() {
        return areaType;
    }

    public void setAreaType(AreaTypeDTO areaType) {
        this.areaType = areaType;
    }

    public StoreDTO getStore() {
        return store;
    }

    public void setStore(StoreDTO store) {
        this.store = store;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Integer getUpRecords() {
        return upRecords;
    }

    public void setUpRecords(Integer upRecords) {
        this.upRecords = upRecords;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
