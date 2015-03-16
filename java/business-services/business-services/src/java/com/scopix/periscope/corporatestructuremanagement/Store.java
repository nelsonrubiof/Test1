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
 * Store.java
 *
 * Created on 25-04-2008, 01:00:57 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement;

import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.extractionplanmanagement.Formula;
import com.scopix.periscope.queuemanagement.OperatorQueueDetail;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class Store extends Place implements Comparable<Store> {

    @Lob
    private String address;
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<Area> areas;
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<Metric> metrics;
    //@OneToOne(fetch = FetchType.EAGER, mappedBy = "store")
    @ManyToOne
    //@PrimaryKeyJoinColumn
    private EvidenceExtractionServicesServer evidenceExtractionServicesServer;
    @ManyToOne
    private EvidenceServicesServer evidenceServicesServer;
    @ManyToOne
    private Corporate corporate;
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<EvidenceProvider> evidenceProviders;
    @ManyToOne
    private Country country;
    @ManyToOne
    private Region region;
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<PeriodInterval> periodIntervals;
    @OneToMany(mappedBy="store")
    private List<OperatorQueueDetail> operatorQueueDetails;
    /**
     * Latitude
     * Third coordenate for google maps
     */
    private Double latitudeCoordenate;
    /**
     * Longitude
     * Third coordenate for google maps
     */
    private Double longitudeCoordenate;
    @ManyToMany(targetEntity = Formula.class, mappedBy = "stores", fetch = FetchType.LAZY,
    cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Formula> formulas;
    
    private String timeZoneId;

    public List<Area> getAreas() {
        if (areas == null) {
            areas = new ArrayList<Area>();
        }
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EvidenceExtractionServicesServer getEvidenceExtractionServicesServer() {
        return evidenceExtractionServicesServer;
    }

    public void setEvidenceExtractionServicesServer(EvidenceExtractionServicesServer evidenceExtractionServicesServer) {
        this.evidenceExtractionServicesServer = evidenceExtractionServicesServer;
    }

    public EvidenceServicesServer getEvidenceServicesServer() {
        return evidenceServicesServer;
    }

    public void setEvidenceServicesServer(EvidenceServicesServer evidenceServicesServer) {
        this.evidenceServicesServer = evidenceServicesServer;
    }

    public Corporate getCorporate() {
        return corporate;
    }

    public void setCorporate(Corporate corporate) {
        this.corporate = corporate;
    }

    public List<EvidenceProvider> getEvidenceProviders() {
        if (evidenceProviders == null) {
            evidenceProviders = new ArrayList<EvidenceProvider>();
        }
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

    public int compareTo(Store o) {
        return this.getId() - o.getId();
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Double getLatitudeCoordenate() {
        return latitudeCoordenate;
    }

    public void setLatitudeCoordenate(Double latitudeCoordenate) {
        this.latitudeCoordenate = latitudeCoordenate;
    }

    public Double getLongitudeCoordenate() {
        return longitudeCoordenate;
    }

    public void setLongitudeCoordenate(Double longitudeCoordenate) {
        this.longitudeCoordenate = longitudeCoordenate;
    }

    /**
     * @return the periodIntervals
     */
    public List<PeriodInterval> getPeriodIntervals() {
        if (periodIntervals == null) {
            periodIntervals = new ArrayList<PeriodInterval>();
        }
        return periodIntervals;
    }

    /**
     * @param periodIntervals the periodIntervals to set
     */
    public void setPeriodIntervals(List<PeriodInterval> periodIntervals) {
        this.periodIntervals = periodIntervals;
    }

    /**
     * @return the formulas
     */
    public List<Formula> getFormulas() {
        if (formulas == null) {
            formulas = new ArrayList<Formula>();
        }
        return formulas;
    }

    /**
     * @param formulas the formulas to set
     */
    public void setFormulas(List<Formula> formulas) {
        this.formulas = formulas;
    }

    public List<OperatorQueueDetail> getOperatorQueueDetails() {
        return operatorQueueDetails;
    }

    public void setOperatorQueueDetails(List<OperatorQueueDetail> operatorQueueDetails) {
        this.operatorQueueDetails = operatorQueueDetails;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }
}
