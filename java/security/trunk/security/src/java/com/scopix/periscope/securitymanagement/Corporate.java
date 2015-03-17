/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import javax.persistence.OneToMany;

/**
 *
 * @author marko.perich
 */
@Entity
public class Corporate extends BusinessObject implements Comparable<Corporate> {

    private String name;
    private String description;
    @ManyToMany(targetEntity = PeriscopeUser.class, fetch = FetchType.LAZY, mappedBy = "corporates", cascade = {
        CascadeType.PERSIST, CascadeType.MERGE})
    private List<PeriscopeUser> periscopeUsers;
    @OneToMany(mappedBy = "corporate", fetch = FetchType.LAZY)
    private List<AreaType> areaTypes;
    private Boolean active;

    /**
     * @return the areaTypes
     */
    public List<AreaType> getAreaTypes() {
        return areaTypes;
    }

    /**
     * @param areaTypes the areaTypes to set
     */
    public void setAreaTypes(List<AreaType> areaTypes) {
        this.areaTypes = areaTypes;
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

    public int compareTo(Corporate o) {
        return this.getId() - o.getId();
    }
    /**
     * Comparator to compare by Description .
     */
    public static Comparator<Corporate> compratorByDescription = new Comparator<Corporate>() {

        public int compare(Corporate o1, Corporate o2) {

            return o1.getDescription().compareTo(o2.getDescription());
        }
    };

    /**
     * @return the periscopeUsers
     */
    public List<PeriscopeUser> getPeriscopeUsers() {
        if (periscopeUsers == null) {
            periscopeUsers = new ArrayList<PeriscopeUser>();
        }
        return periscopeUsers;
    }

    /**
     * @param periscopeUsers the periscopeUsers to set
     */
    public void setPeriscopeUsers(List<PeriscopeUser> periscopeUsers) {
        this.periscopeUsers = periscopeUsers;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
