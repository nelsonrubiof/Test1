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
 * Sensor.java
 *
 * Created on 27-03-2008, 03:06:39 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author César Abarza
 */
@Entity
public class Sensor extends BusinessObject implements Comparable<Sensor> {

    @Lob
    private String name;
    @Lob
    private String description;
    @ManyToOne
    private Area area;
    @ManyToOne
    private Store store;
    @Lob
    private String url;
    @Lob
    private String userName;
    @Lob
    private String userPassword;

    @ManyToMany(targetEntity = ExtractionPlanCustomizing.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
        CascadeType.MERGE}, mappedBy = "sensors")
    private List<ExtractionPlanCustomizing> extractionPlanCustomizings;

//    @ManyToMany(targetEntity = SituationTemplateStoreRelation.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
//        CascadeType.MERGE}, mappedBy = "sensors")
//    private List<SituationTemplateStoreRelation> situationTemplateStoreRelations;

    public int compareTo(Sensor o) {
        return this.getId() - o.getId();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the area
     */
    public Area getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(Area area) {
        this.area = area;
    }

    /**
     * @return the store
     */
    public Store getStore() {
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword the userPassword to set
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

//    /**
//     * @return the situationTemplateStoreRelations
//     */
//    public List<SituationTemplateStoreRelation> getSituationTemplateStoreRelations() {
//        if (situationTemplateStoreRelations == null) {
//            situationTemplateStoreRelations = new ArrayList<SituationTemplateStoreRelation>();
//        }
//        return situationTemplateStoreRelations;
//    }
//
//    /**
//     * @param situationTemplateStoreRelations the situationTemplateStoreRelations to set
//     */
//    public void setSituationTemplateStoreRelations(List<SituationTemplateStoreRelation> situationTemplateStoreRelations) {
//        this.situationTemplateStoreRelations = situationTemplateStoreRelations;
//    }

    public List<ExtractionPlanCustomizing> getExtractionPlanCustomizings() {
        return extractionPlanCustomizings;
    }

    public void setExtractionPlanCustomizings(List<ExtractionPlanCustomizing> extractionPlanCustomizings) {
        this.extractionPlanCustomizings = extractionPlanCustomizings;
    }
}
