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
 * StoreDTO.java
 *
 * Created on 26-05-2008, 10:25:35 AM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class StoreDTO implements Comparable<StoreDTO> {

    private Integer id;
    private String address;
    private List<AreaDTO> areas;
    private String description;
    private String name;
    private CorporateDTO corporate;
    private CountryDTO country;
    private RegionDTO region;
    private Integer eessId;
    private List<PeriodIntervalDTO> periodIntervalDTOs;
    private static Comparator<StoreDTO> comparatorByName;

    public static Comparator<StoreDTO> getComparatorByName() {
        comparatorByName = new Comparator<StoreDTO>() {

            public int compare(StoreDTO o1, StoreDTO o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        return comparatorByName;
    }

    public static void setComparatorByName(Comparator<StoreDTO> aComparatorByName) {
        comparatorByName = aComparatorByName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<AreaDTO> getAreas() {
        if(areas == null) {
            areas = new ArrayList<AreaDTO>();
        }
        return areas;
    }

    public void setAreas(List<AreaDTO> areas) {
        this.areas = areas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int compareTo(StoreDTO o) {
        return this.getId() - o.getId();
    }

    public CorporateDTO getCorporate() {
        return corporate;
    }

    public void setCorporate(CorporateDTO corporate) {
        this.corporate = corporate;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
    }

    public List<PeriodIntervalDTO> getPeriodIntervalDTOs() {
        if (periodIntervalDTOs == null) {
            periodIntervalDTOs = new ArrayList<PeriodIntervalDTO>();
        }
        return periodIntervalDTOs;
    }

    public void setPeriodIntervalDTOs(List<PeriodIntervalDTO> periodIntervalDTOs) {
        this.periodIntervalDTOs = periodIntervalDTOs;
    }
    
    public Integer getEessId() {
        return eessId;
    }

    public void setEessId(Integer eessId) {
        this.eessId = eessId;
    }
}
