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
 * RegionDTO.java
 *
 * Created on 04-08-2008, 01:23:02 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class RegionDTO implements Comparable<RegionDTO> {

    private Integer id;

    private String name;

    private String description;

    private CountryDTO country;
    
    private List<StoreDTO> stores;

    public int compareTo(RegionDTO o) {
        return this.getId() - o.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public List<StoreDTO> getStores() {
        if (stores == null) {
            stores = new ArrayList<StoreDTO>();
        }
        return stores;
    }

    public void setStores(List<StoreDTO> stores) {
        this.stores = stores;
    }
}
