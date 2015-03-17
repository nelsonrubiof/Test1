/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scopix.periscope.corporatestructuremanagement;

/**
 *
 * @author Admin
 */
public class StorePlan {
    private Integer storeId;
    private String name;
    private Integer dayOfWeek;
    private String situationName;
    private Integer situationId;
    private Integer count;

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
     * @return the dayOfWeek
     */
    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * @param dayOfWeek the dayOfWeek to set
     */
    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * @return the situationName
     */
    public String getSituationName() {
        return situationName;
    }

    /**
     * @param situationName the situationName to set
     */
    public void setSituationName(String situationName) {
        this.situationName = situationName;
    }

    /**
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return the storeId
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * @param storeId the storeId to set
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * @return the situationId
     */
    public Integer getSituationId() {
        return situationId;
    }

    /**
     * @param situationId the situationId to set
     */
    public void setSituationId(Integer situationId) {
        this.situationId = situationId;
    }
}
