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
 * AreaDTO.java
 *
 * Created on 26-05-2008, 10:25:48 AM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.dto;

import java.util.Comparator;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class AreaDTO implements Comparable<AreaDTO> {

    private Integer id;
    private StoreDTO store;
    private String description;
    private String name;
    private AreaTypeDTO areaType;
    private static Comparator<AreaDTO> comparatorByName;

    public static Comparator<AreaDTO> getComparatorByName() {
        comparatorByName = new Comparator<AreaDTO>() {

            public int compare(AreaDTO o1, AreaDTO o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };

        return comparatorByName;
    }

    public static void setComparatorByName(Comparator<AreaDTO> aComparatorByName) {
        comparatorByName = aComparatorByName;
    }

    public StoreDTO getStore() {
        return store;
    }

    public void setStore(StoreDTO store) {
        this.store = store;
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

    public int compareTo(AreaDTO o) {
        return this.getId() - o.getId();
    }

    public AreaTypeDTO getAreaType() {
        return areaType;
    }

    public void setAreaType(AreaTypeDTO areaType) {
        this.areaType = areaType;
    }
}