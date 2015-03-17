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
 *  UploadProcessDetail.java
 *  
 *  Created on 11-01-2011, 10:58:11 AM
 */
package com.scopix.periscope.reporting;

import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Nelson Rubio
 */
@Entity
public class UploadProcessDetail extends BusinessObject {

    @ManyToOne
    private UploadProcess uploadProcess;
    @OneToOne
    private AreaType areaType;
    @OneToOne
    private Store store;
    @Temporal(value = TemporalType.DATE)
    private Date dateEnd;
    private Integer totalRecords;
    private Integer upRecords;

    public UploadProcess getUploadProcess() {
        return uploadProcess;
    }

    public void setUploadProcess(UploadProcess uploadProcess) {
        this.uploadProcess = uploadProcess;
    }

    public AreaType getAreaType() {
        return areaType;
    }

    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
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
}
