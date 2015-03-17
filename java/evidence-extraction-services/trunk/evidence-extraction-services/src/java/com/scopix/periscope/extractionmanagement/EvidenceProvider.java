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
 * EvidenceProvider.java
 *
 * Created on 20-05-2008, 09:23:05 PM
 *
 */
package com.scopix.periscope.extractionmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author marko.perich
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class EvidenceProvider extends BusinessObject {

    private String name;
    private String description;
    private Integer deviceId;
    @ManyToOne
    private ExtractionServer extractionServer;
    @OneToMany(mappedBy = "evidenceProvider", fetch = FetchType.LAZY)
    private List<EvidenceProviderRequest> evidenceProviderRequests;
    private String uniqueDeviceId;

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

    public ExtractionServer getExtractionServer() {
        return extractionServer;
    }

    public void setExtractionServer(ExtractionServer extractionServer) {
        this.extractionServer = extractionServer;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return the evidenceProviderRequests
     */
    public List<EvidenceProviderRequest> getEvidenceProviderRequests() {
        if (evidenceProviderRequests == null) {
            evidenceProviderRequests = new ArrayList<EvidenceProviderRequest>();
        }
        return evidenceProviderRequests;
    }

    /**
     * @param evidenceProviderRequests the evidenceProviderRequests to set
     */
    public void setEvidenceProviderRequests(List<EvidenceProviderRequest> evidenceProviderRequests) {
        this.evidenceProviderRequests = evidenceProviderRequests;
    }
    public static Comparator<EvidenceProvider> deviceIdComparator = new Comparator<EvidenceProvider>() {

        public int compare(EvidenceProvider ep1, EvidenceProvider ep2) {
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
}
