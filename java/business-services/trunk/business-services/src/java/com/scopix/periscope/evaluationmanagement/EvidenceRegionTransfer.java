/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
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
 *  EvidenceRegionTransfer.java
 * 
 *  Created on Jul 21, 2014, 11:04:48 AM
 * 
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Sebastian
 */
@Entity
public class EvidenceRegionTransfer extends BusinessObject {

    @ManyToOne
    private Evidence evidence;
    private String regionServerName;
    private String regionServerIp;
    private boolean completed;
    @Temporal(TemporalType.TIMESTAMP)
    private Date transmisitionDate;
    @Lob
    private String errorMessage;

    /**
     * @return the evidence
     */
    public Evidence getEvidence() {
        return evidence;
    }

    /**
     * @param evidence the evidence to set
     */
    public void setEvidence(Evidence evidence) {
        this.evidence = evidence;
    }

    /**
     * @return the regionServerName
     */
    public String getRegionServerName() {
        return regionServerName;
    }

    /**
     * @param regionServerName the regionServerName to set
     */
    public void setRegionServerName(String regionServerName) {
        this.regionServerName = regionServerName;
    }

    /**
     * @return the regionServerIp
     */
    public String getRegionServerIp() {
        return regionServerIp;
    }

    /**
     * @param regionServerIp the regionServerIp to set
     */
    public void setRegionServerIp(String regionServerIp) {
        this.regionServerIp = regionServerIp;
    }

    /**
     * @return the completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * @param completed the completed to set
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * @return the transmisitionDate
     */
    public Date getTransmisitionDate() {
        return transmisitionDate;
    }

    /**
     * @param transmisitionDate the transmisitionDate to set
     */
    public void setTransmisitionDate(Date transmisitionDate) {
        this.transmisitionDate = transmisitionDate;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }  
}
