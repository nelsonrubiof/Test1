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
 * Store.java
 *
 * Created on 25-04-2008, 01:00:57 PM
 *
 */
package com.scopix.periscope.securitymanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.Comparator;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class Store extends BusinessObject implements Comparable<Store> {

    public int compareTo(Store o2) {
        return this.getId() - o2.getId();
    }
    
    private String name;
    private String description;
    @ManyToOne
    private Corporate corporate;

    // The Store id in the associated corporate
    private Integer corporateStoreId;

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
     * @return the corporateStoreId
     */
    public Integer getCorporateStoreId() {
        return corporateStoreId;
    }

    /**
     * @param corporateStoreId the corporateStoreId to set
     */
    public void setCorporateStoreId(Integer corporateStoreId) {
        this.corporateStoreId = corporateStoreId;
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
    public static Comparator<Store> compratorByDescriptionWithCorporate = new Comparator<Store>() {

        public int compare(Store o1, Store o2) {

            return o1.getDescriptionWithCorporate().compareTo(o2.getDescriptionWithCorporate());
        }
    };
}
