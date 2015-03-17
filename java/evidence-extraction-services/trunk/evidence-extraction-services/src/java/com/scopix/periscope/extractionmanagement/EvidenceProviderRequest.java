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
 * EvidenceRequest.java
 *
 * Created on 16-06-2008, 05:34:27 PM
 *
 */
package com.scopix.periscope.extractionmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.Comparator;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * This class represents a Evidence request from business services
 * 
 * @author marko.perich
 */
@Entity
public class EvidenceProviderRequest extends BusinessObject implements Comparable<EvidenceProviderRequest> {

    private Integer deviceId;
    private String uniqueDeviceId;
    @ManyToOne
    private EvidenceProvider evidenceProvider;
    @ManyToOne
    private MetricRequest metricRequest;
    @ManyToOne
    private SituationRequest situationRequest;

    @Override
    public int compareTo(EvidenceProviderRequest o) {
        return this.getId() - o.getId();
    }

    /**
     * @return the deviceId
     */
    public Integer getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return the evidenceProvider
     */
    public EvidenceProvider getEvidenceProvider() {
        return evidenceProvider;
    }

    /**
     * @param evidenceProvider the evidenceProvider to set
     */
    public void setEvidenceProvider(EvidenceProvider evidenceProvider) {
        this.evidenceProvider = evidenceProvider;
    }

    /**
     * @return the metricRequest
     */
    public MetricRequest getMetricRequest() {
        return metricRequest;
    }

    /**
     * @param metricRequest the metricRequest to set
     */
    public void setMetricRequest(MetricRequest metricRequest) {
        this.metricRequest = metricRequest;
    }
    public static Comparator<EvidenceProviderRequest> deviceIdComparator = new Comparator<EvidenceProviderRequest>() {

        @Override
        public int compare(EvidenceProviderRequest ep1, EvidenceProviderRequest ep2) {
            return ep1.getDeviceId() - ep2.getDeviceId();
        }
    };

    /**
     * @return the uniqueDeviceId
     */
    public String getUniqueDeviceId() {
        return uniqueDeviceId;
    }

    /**
     * @param uniqueDeviceId the uniqueDeviceId to set
     */
    public void setUniqueDeviceId(String uniqueDeviceId) {
        this.uniqueDeviceId = uniqueDeviceId;
    }

    public SituationRequest getSituationRequest() {
        return situationRequest;
    }

    public void setSituationRequest(SituationRequest situationRequest) {
        this.situationRequest = situationRequest;
    }
}
