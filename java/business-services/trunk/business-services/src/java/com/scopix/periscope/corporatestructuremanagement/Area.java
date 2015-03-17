/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * Area.java
 *
 * Created on 25-04-2008, 07:04:11 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement;

import com.scopix.periscope.extractionplanmanagement.Metric;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class Area extends Place {

    @ManyToOne
    private Store store;

    //@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    @ManyToMany(targetEntity = EvidenceProvider.class, fetch = FetchType.LAZY, mappedBy = "areas")
    private List<EvidenceProvider> evidenceProviders;

    @ManyToOne
    private AreaType areaType;
    
    @OneToMany(mappedBy="area", fetch=FetchType.LAZY)
    private List<Metric> metrics;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<EvidenceProvider> getEvidenceProviders() {
        return evidenceProviders;
    }

    public void setEvidenceProviders(List<EvidenceProvider> evidenceProviders) {
        this.evidenceProviders = evidenceProviders;
    }

    public List<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }

    public AreaType getAreaType() {
        return areaType;
    }

    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }
}
