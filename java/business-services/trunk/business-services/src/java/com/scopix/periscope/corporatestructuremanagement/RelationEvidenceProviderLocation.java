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
 * EvidenceProvider.java
 *
 * Created on 27-03-2008, 03:06:39 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class RelationEvidenceProviderLocation extends BusinessObject implements Comparable<RelationEvidenceProviderLocation> {

    @Enumerated(EnumType.STRING)
    private Location location;
    @ManyToOne
    private EvidenceProvider evidenceProviderFrom;
    @ManyToOne
    private EvidenceProvider evidenceProviderTo;
    private Integer viewOrder;
    private Boolean defaultEvidenceProvider;
    @OneToOne
    private Area area;

    public int compareTo(RelationEvidenceProviderLocation o) {
        return this.getId() - o.getId();
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the evidenceProviderFrom
     */
    public EvidenceProvider getEvidenceProviderFrom() {
        return evidenceProviderFrom;
    }

    /**
     * @param evidenceProviderFrom the evidenceProviderFrom to set
     */
    public void setEvidenceProviderFrom(EvidenceProvider evidenceProviderFrom) {
        this.evidenceProviderFrom = evidenceProviderFrom;
    }

    /**
     * @return the evidenceProviderTo
     */
    public EvidenceProvider getEvidenceProviderTo() {
        return evidenceProviderTo;
    }

    /**
     * @param evidenceProviderTo the evidenceProviderTo to set
     */
    public void setEvidenceProviderTo(EvidenceProvider evidenceProviderTo) {
        this.evidenceProviderTo = evidenceProviderTo;
    }

    /**
     * @return the viewOrder
     */
    public Integer getViewOrder() {
        return viewOrder;
    }

    /**
     * @param viewOrder the viewOrder to set
     */
    public void setViewOrder(Integer viewOrder) {
        this.viewOrder = viewOrder;
    }

    /**
     * @return the defaultEvidenceProvider
     */
    public Boolean getDefaultEvidenceProvider() {
        return defaultEvidenceProvider;
    }

    /**
     * @param defaultEvidenceProvider the defaultEvidenceProvider to set
     */
    public void setDefaultEvidenceProvider(Boolean defaultEvidenceProvider) {
        this.defaultEvidenceProvider = defaultEvidenceProvider;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
