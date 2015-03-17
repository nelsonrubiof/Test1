/*
 * 
 * Copyright @ 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * AreaType.java
 *
 * Created on 25-04-2008, 07:04:11 PM
 *
 */
package com.scopix.periscope.securitymanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class AreaType extends BusinessObject implements Comparable<AreaType> {

    private String name;
    private String description;
    @ManyToOne
    private Corporate corporate;
    @ManyToMany(targetEntity = PeriscopeUser.class, fetch = FetchType.LAZY, mappedBy = "areaTypes",
    cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PeriscopeUser> periscopeUsers;
    // The AreaType id in the associated corporate
    private Integer corporateAreaTypeId;

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

    public List<PeriscopeUser> getPeriscopeUsers() {
        if (periscopeUsers == null) {
            periscopeUsers = new ArrayList<PeriscopeUser>();
        }
        return periscopeUsers;
    }

    public void setPeriscopeUsers(List<PeriscopeUser> periscopeUsers) {
        this.periscopeUsers = periscopeUsers;
    }

    public int compareTo(AreaType o) {
        return this.getId() - o.getId();
    }

    /**
     * @return the corporate
     */
    public Corporate getCorporate() {
        return corporate;
    }

    /**
     * @param corporate the corporate to set
     */
    public void setCorporate(Corporate corporate) {
        this.corporate = corporate;
    }

    /**
     * @return the corporateAreaTypeId
     */
    public Integer getCorporateAreaTypeId() {
        return corporateAreaTypeId;
    }

    /**
     * @param corporateAreaTypeId the corporateAreaTypeId to set
     */
    public void setCorporateAreaTypeId(Integer corporateAreaTypeId) {
        this.corporateAreaTypeId = corporateAreaTypeId;
    }

    /**
     * 
     * @return the concatenation of Corporate description + this.description
     */
    public String getDescriptionWithCorporate() {
        return this.getCorporate().getDescription() + " - " + this.description;
    }
    /**
     * Comparator to compare by Description including corporate.
     */
    public static Comparator<AreaType> compratorByDescriptionWithCorporate = new Comparator<AreaType>() {

        public int compare(AreaType o1, AreaType o2) {

            return o1.getDescriptionWithCorporate().compareTo(o2.getDescriptionWithCorporate());
        }
    };
}
