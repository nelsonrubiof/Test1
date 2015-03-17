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
 * Corporate.java
 *
 * Created on 30-05-2008, 05:42:51 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

/**
 *
 * @author César Abarza Suazo.
 */
@Entity
public class Corporate extends Place {

    /**
     * Unique client id in the system. This is the corporate id registered in security database
     */
    private Integer uniqueCorporateId;

    /**
     * The list of stores associated to this corporate
     */
    @OneToMany(mappedBy = "corporate", fetch = FetchType.LAZY)
    private List<Store> stores;

    @Lob
    private String logo;

    public List<Store> getStores() {
        if (stores == null) {
            stores = new ArrayList<Store>();
        }
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    /**
     * @return the uniqueCorporateId
     */
    public /**
     * Unique client id in the system. This is the corporate id registered in security database
     */
    Integer getUniqueCorporateId() {
        return uniqueCorporateId;
    }

    /**
     * @param uniqueCorporateId the uniqueCorporateId to set
     */
    public void setUniqueCorporateId(Integer uniqueCorporateId) {
        this.uniqueCorporateId = uniqueCorporateId;
    }

    /**
     * @return the logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * @param logo the logo to set
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }
}
