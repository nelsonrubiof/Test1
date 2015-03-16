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

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import org.hibernate.validator.NotNull;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class Country extends BusinessObject {

    @NotNull
    @Lob
    private String name;

    @Lob
    private String description;

    @NotNull
    @Lob
    private String code;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private List<Region> regions;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private List<Store> stores;
    
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Region> getRegions() {
        if (regions == null) {
            regions = new ArrayList<Region>();
        }
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }
}
