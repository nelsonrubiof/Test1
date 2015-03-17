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

import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanMetric;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class EvidenceProvider extends BusinessObject implements Comparable<EvidenceProvider> {

    @Lob
    private String description;
    @Lob
    private String name;
    @ManyToOne
    private EvidenceProviderType evidenceProviderType;
    //@ManyToOne
    @ManyToMany(targetEntity = Area.class, fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    //@Cascade({CascadeType.SAVE_UPDATE})
    @JoinTable(name = "rel_evidence_provider_area",
    joinColumns = {
        @JoinColumn(name = "evidence_provider_id")},
    inverseJoinColumns = {
        @JoinColumn(name = "area_id")})
    private List<Area> areas;
    //private Area area;
    @ManyToOne
    private Store store;
    @OneToMany(mappedBy = "evidenceProvider", fetch = FetchType.LAZY)
    private List<EvidenceRequest> evidenceRequests;
    @Lob
    private String definitionData;
//    @ManyToMany(targetEntity = ExtractionPlanCustomizing.class, fetch = FetchType.LAZY, mappedBy = "evidenceProviders")
    @ManyToMany(targetEntity = ExtractionPlanMetric.class, fetch = FetchType.LAZY, mappedBy = "evidenceProviders")
//    @Cascade({CascadeType.SAVE_UPDATE})
    private List<ExtractionPlanMetric> extractionPlanMetrics;
//    private List<ExtractionPlanCustomizing> extractionPlanCustomizings;
    @OneToMany(mappedBy = "evidenceProviderFrom")
    private List<RelationEvidenceProviderLocation> relationEvidenceProviderLocationsFrom;
    @OneToMany(mappedBy = "evidenceProviderTo")
    private List<RelationEvidenceProviderLocation> relationEvidenceProviderLocationsTO;
    @OneToMany(mappedBy = "evidenceProvider", fetch = FetchType.LAZY)
    private List<EvidenceProviderTemplate> evidenceProviderTemplates;
    private static Comparator<EvidenceProvider> comparatorByDescription;

    public static Comparator<EvidenceProvider> getComparatorByDescription() {
        comparatorByDescription = new Comparator<EvidenceProvider>() {

            public int compare(EvidenceProvider o1, EvidenceProvider o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        };

        return comparatorByDescription;
    }

    public static void setComparatorByDescription(Comparator<EvidenceProvider> aComparatorByDescription) {
        comparatorByDescription = aComparatorByDescription;
    }

    public EvidenceProviderType getEvidenceProviderType() {
        return evidenceProviderType;
    }

    public void setEvidenceProviderType(EvidenceProviderType evidenceProviderType) {
        this.evidenceProviderType = evidenceProviderType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EvidenceRequest> getEvidenceRequests() {

        return evidenceRequests;
    }

    public void setEvidenceRequests(List<EvidenceRequest> evidenceRequests) {
        this.evidenceRequests = evidenceRequests;
    }

    public String getDefinitionData() {
        return definitionData;
    }

    public void setDefinitionData(String definitionData) {
        this.definitionData = definitionData;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public int compareTo(EvidenceProvider o) {
        return this.getId() - o.getId();
    }

    /**
     * @return the relationEvidenceProviderLocationsFrom
     */
    public List<RelationEvidenceProviderLocation> getRelationEvidenceProviderLocationsFrom() {
        if (relationEvidenceProviderLocationsFrom == null) {
            relationEvidenceProviderLocationsFrom = new ArrayList<RelationEvidenceProviderLocation>();
        }
        return relationEvidenceProviderLocationsFrom;
    }

    /**
     * @param relationEvidenceProviderLocationsFrom the relationEvidenceProviderLocationsFrom to set
     */
    public void setRelationEvidenceProviderLocationsFrom(
            List<RelationEvidenceProviderLocation> relationEvidenceProviderLocationsFrom) {
        this.relationEvidenceProviderLocationsFrom = relationEvidenceProviderLocationsFrom;
    }

    /**
     * @return the relationEvidenceProviderLocationsTO
     */
    public List<RelationEvidenceProviderLocation> getRelationEvidenceProviderLocationsTO() {
        if (relationEvidenceProviderLocationsTO == null) {
            relationEvidenceProviderLocationsTO = new ArrayList<RelationEvidenceProviderLocation>();
        }
        return relationEvidenceProviderLocationsTO;
    }

    /**
     * @param relationEvidenceProviderLocationsTO the relationEvidenceProviderLocationsTO to set
     */
    public void setRelationEvidenceProviderLocationsTO(
            List<RelationEvidenceProviderLocation> relationEvidenceProviderLocationsTO) {
        this.relationEvidenceProviderLocationsTO = relationEvidenceProviderLocationsTO;
    }

    public Boolean getDefaultEvidenceProvider() {
        Boolean resp = null;
        if (!this.getRelationEvidenceProviderLocationsFrom().isEmpty()) {
            resp = this.getRelationEvidenceProviderLocationsFrom().get(0).getDefaultEvidenceProvider();
        }
        return resp;
    }

    public Integer getViewOrder() {
        Integer resp = null;
        if (!this.getRelationEvidenceProviderLocationsFrom().isEmpty()) {
            resp = this.getRelationEvidenceProviderLocationsFrom().get(0).getViewOrder();
        }
        return resp;
    }

    public List<Area> getAreas() {
        if (areas == null) {
            areas = new ArrayList<Area>();
        }
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public List<ExtractionPlanMetric> getExtractionPlanMetrics() {
        if(extractionPlanMetrics == null) {
            extractionPlanMetrics = new ArrayList<ExtractionPlanMetric>();
        }
        return extractionPlanMetrics;
    }

    public void setExtractionPlanMetrics(List<ExtractionPlanMetric> extractionPlanMetrics) {
        this.extractionPlanMetrics = extractionPlanMetrics;
    }

    public List<EvidenceProviderTemplate> getEvidenceProviderTemplates() {
        if (evidenceProviderTemplates == null) {
            evidenceProviderTemplates = new ArrayList<EvidenceProviderTemplate>();
        }
        return evidenceProviderTemplates;
    }

    public void setEvidenceProviderTemplates(List<EvidenceProviderTemplate> evidenceProviderTemplates) {
        this.evidenceProviderTemplates = evidenceProviderTemplates;
    }

    public String getTemplatePath(Integer situationTemplateid) {
        String ret = "";
        for (EvidenceProviderTemplate ept : getEvidenceProviderTemplates()) {
            if (ept.getSituationTemplate().getId().equals(situationTemplateid)){
                ret = ept.getTemplatePath();
                break;
            }
        }
        return ret;
    }
}
