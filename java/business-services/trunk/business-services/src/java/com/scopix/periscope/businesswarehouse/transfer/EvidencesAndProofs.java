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
 *  EvidencesAndProofs.java
 * 
 *  Created on 22-07-2011, 03:03:14 PM
 * 
 */
package com.scopix.periscope.businesswarehouse.transfer;

import com.scopix.periscope.businesswarehouse.transfer.commands.ProofBW;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Gustavo Alvarez
 */
public class EvidencesAndProofs {

    private Integer evidenceId;
    private List<ProofBW> proofs;
    private String metricType;
    private Integer cameraId;
    private Integer storeId;
    private Date evidenceDate;
    private Integer observedMetricId;

    public Integer getEvidenceId() {
        return evidenceId;
    }

    public void setEvidenceId(Integer evidenceId) {
        this.evidenceId = evidenceId;
    }

    public void setMetricType(String metricType) {
        this.metricType = metricType;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getCameraId() {
        return cameraId;
    }

    public void setCameraId(Integer cameraId) {
        this.cameraId = cameraId;
    }

    public List<ProofBW> getProofs() {
        if (proofs == null) {
            proofs = new ArrayList<ProofBW>();
        }
        return proofs;
    }

    public void setProofs(List<ProofBW> proofs) {
        this.proofs = proofs;
    }

    public String getMetricType() {
        return metricType;
    }

    public Date getEvidenceDate() {
        return evidenceDate;
    }

    public void setEvidenceDate(Date evidenceDate) {
        this.evidenceDate = evidenceDate;
    }

    public Integer getObservedMetricId() {
        return observedMetricId;
    }

    public void setObservedMetricId(Integer observedMetricId) {
        this.observedMetricId = observedMetricId;
    }
}
