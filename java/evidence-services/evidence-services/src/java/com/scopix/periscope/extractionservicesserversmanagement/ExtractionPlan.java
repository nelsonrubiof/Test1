/*
 *
 * Copyright � 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * Evidence.java
 *
 * Created on May 3, 2007, 5:26 PM
 *
 */
package com.scopix.periscope.extractionservicesserversmanagement;

import com.scopix.periscope.evidencemanagement.ProcessEES;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * This object represents an extraction plan.
 *
 * @author jorge
 */
@Entity
public class ExtractionPlan extends BusinessObject {

    @Temporal(TemporalType.DATE)
    private Date expiration;
    @OneToMany(mappedBy = "extractionPlan", fetch = FetchType.LAZY)
    private List<ExtractionPlanDetail> extractionPlanDetails;
    @OneToMany(mappedBy = "extractionPlan", fetch = FetchType.LAZY)
    private List<SituationRequest> situationRequests;
    @OneToOne
    private EvidenceExtractionServicesServer evidenceExtractionServicesServer;
    @OneToMany(mappedBy = "extractionPlan")
    private List<StoreTime> storeTimes;
    private String storeName;
    private String timeZoneId;
    private Integer storeId;
    @OneToMany(mappedBy = "extractionPlan")
    private List<ProcessEES> processEESs;

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public List<ExtractionPlanDetail> getExtractionPlanDetails() {
        if (extractionPlanDetails == null) {
            extractionPlanDetails = new ArrayList<ExtractionPlanDetail>();
        }

        return extractionPlanDetails;
    }

    public void setExtractionPlanDetails(List<ExtractionPlanDetail> extractionPlanDetails) {
        this.extractionPlanDetails = extractionPlanDetails;
    }

    public EvidenceExtractionServicesServer getEvidenceExtractionServicesServer() {
        return evidenceExtractionServicesServer;
    }

    public void setEvidenceExtractionServicesServer(EvidenceExtractionServicesServer evidenceExtractionServicesServer) {
        this.evidenceExtractionServicesServer = evidenceExtractionServicesServer;
    }

    /**
     * @return the situationRequests
     */
    public List<SituationRequest> getSituationRequests() {
        if (situationRequests == null) {
            situationRequests = new ArrayList<SituationRequest>();
        }
        return situationRequests;
    }

    /**
     * @param situationRequests the situationRequests to set
     */
    public void setSituationRequests(List<SituationRequest> situationRequests) {
        this.situationRequests = situationRequests;
    }

    /**
     * @return the storeTimes
     */
    public List<StoreTime> getStoreTimes() {
        if (storeTimes == null) {
            storeTimes = new ArrayList<StoreTime>();
        }
        return storeTimes;
    }

    /**
     * @param storeTimes the storeTimes to set
     */
    public void setStoreTimes(List<StoreTime> storeTimes) {
        this.storeTimes = storeTimes;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
}
