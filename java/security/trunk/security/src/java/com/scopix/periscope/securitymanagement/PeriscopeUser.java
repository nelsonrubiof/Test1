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
 * PeriscopeUser.java
 *
 * Created on 27-03-2008, 03:31:27 PM
 *
 */
package com.scopix.periscope.securitymanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.NotNull;

/**
 *
 * @author César Abarza Suazo
 */
@Entity
public class PeriscopeUser extends BusinessObject {

    @NotNull
    private String name;
    @Enumerated(EnumType.STRING)
    private UserState userState;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;
    @NotNull
    private String password;
    private boolean deleted;
    private String email;
    @ManyToMany(targetEntity = RolesGroup.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "rel_periscope_user_roles_group", joinColumns = {@JoinColumn(name = "periscope_user_id")},
    inverseJoinColumns = {@JoinColumn(name = "roles_group_id")})
    private List<RolesGroup> rolesGroups;
    @OneToMany(mappedBy = "periscopeUser", fetch = FetchType.LAZY)
    private List<UserLogin> userLogins;
    @ManyToMany(targetEntity = Store.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "rel_periscope_user_store", joinColumns = {@JoinColumn(name = "periscope_user_id")}, inverseJoinColumns = {
        @JoinColumn(name = "store_id")})
    private List<Store> stores;
    @ManyToMany(targetEntity = AreaType.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "rel_periscope_user_area_type", joinColumns = {@JoinColumn(name = "periscope_user_id")},
    inverseJoinColumns = {@JoinColumn(name = "area_type_id")})
    private List<AreaType> areaTypes;
    @ManyToMany(targetEntity = Corporate.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "rel_periscope_user_corporate", joinColumns = {@JoinColumn(name = "periscope_user_id")},
    inverseJoinColumns = {@JoinColumn(name = "corporate_id")})
    private List<Corporate> corporates;
    private String fullName;
    private String jobPosition;
    @ManyToOne
    private Corporate mainCorporate;

    public String getName() {
        return name;
    }

    public void setName(String userName) {
        this.name = userName;
    }

    public UserState getUserState() {
        return userState;
    }

    public void setUserState(UserState userState) {
        this.userState = userState;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<RolesGroup> getRolesGroups() {
        if (rolesGroups == null) {
            rolesGroups = new ArrayList<RolesGroup>();
        }
        return rolesGroups;
    }

    public void setRolesGroups(List<RolesGroup> rolesGroups) {
        this.rolesGroups = rolesGroups;
    }

    public List<UserLogin> getUserLogins() {
        return userLogins;
    }

    public void setUserLogins(List<UserLogin> userLogins) {
        if (userLogins == null) {
            userLogins = new ArrayList<UserLogin>();
        }
        this.userLogins = userLogins;
    }

    public List<Store> getStores() {
        if (stores == null) {
            stores = new ArrayList<Store>();
        }
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public List<AreaType> getAreaTypes() {
        if (areaTypes == null) {
            areaTypes = new ArrayList<AreaType>();
        }
        return areaTypes;
    }

    public void setAreaTypes(List<AreaType> areaTypes) {
        this.areaTypes = areaTypes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the corporates
     */
    public List<Corporate> getCorporates() {
        if (corporates == null) {
            corporates = new ArrayList<Corporate>();
        }
        return corporates;
    }

    /**
     * @param corporates the corporates to set
     */
    public void setCorporates(List<Corporate> corporates) {
        this.corporates = corporates;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the mainCorporate
     */
    public Corporate getMainCorporate() {
        return mainCorporate;
    }

    /**
     * @param mainCorporate the mainCorporate to set
     */
    public void setMainCorporate(Corporate mainCorporate) {
        this.mainCorporate = mainCorporate;
    }

    /**
     * @return the jobPosition
     */
    public String getJobPosition() {
        return jobPosition;
    }

    /**
     * @param jobPosition the jobPosition to set
     */
    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }
}
