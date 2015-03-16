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
 * EvidenceAvailableDTO.java
 *
 * Created on 09-05-2008, 11:51:10 AM
 *
 */
package com.scopix.periscope.evaluationmanagement.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class EvidenceAvailableDTO {

    private String path;

    private List<Integer> evidenceRequestIds;

    /**
     * El id del servidor de evidencias en business services (evidence server)
     */
    private Integer evidenceServicesServerId;
    
    private Date evidenceDate; 

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Integer> getEvidenceRequestIds() {
        if (evidenceRequestIds == null){
            evidenceRequestIds = new ArrayList<Integer>();
        }
        return evidenceRequestIds;
    }

    public void setEvidenceRequestIds(List<Integer> evidenceRequestIds) {
        this.evidenceRequestIds = evidenceRequestIds;
    }

    public Integer getEvidenceServicesServerId() {
        return evidenceServicesServerId;
    }

    public void setEvidenceServicesServerId(Integer evidenceServicesServerId) {
        this.evidenceServicesServerId = evidenceServicesServerId;
    }

    public Date getEvidenceDate() {
        return evidenceDate;
    }

    public void setEvidenceDate(Date evidenceDate) {
        this.evidenceDate = evidenceDate;
    }
}
