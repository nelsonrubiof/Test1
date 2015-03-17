/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * PeriscopeUserDTO.java
 *
 * Created on 17-06-2010, 09:25:10 AM
 *
 */
package com.scopix.periscope.securitymanagement.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gustavo Alvarez
 */
@XmlRootElement(name = "PeriscopeUserDTO")
public class PeriscopeUserDTO {
    private Integer userId;
    private String userName;
    private String fullName;
    private String password;
    private String email;
    private String jobPosition;
    private Date startDate;
    private Date modificationDate;
    private String userState;
    private boolean delete;
    private Integer mainCorporateId;
    private List<AreaTypeDTO> areaTypes;
    private List<StoreDTO> stores;
    private List<CorporateDTO> corporates;
    private List<RolesGroupDTO> rolesGroups;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public List<AreaTypeDTO> getAreaTypes() {
        if (areaTypes == null) {
            areaTypes = new ArrayList<AreaTypeDTO>();
        }
        return areaTypes;
    }

    public void setAreaTypes(List<AreaTypeDTO> areaTypes) {
        this.areaTypes = areaTypes;
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

    public List<CorporateDTO> getCorporates() {
        if (corporates == null) {
            corporates = new ArrayList<CorporateDTO>();
        }
        return corporates;
    }

    public void setCorporates(List<CorporateDTO> corporates) {
        this.corporates = corporates;
    }

    public List<RolesGroupDTO> getRolesGroups() {
        return rolesGroups;
    }

    public void setRolesGroups(List<RolesGroupDTO> rolesGroups) {
        this.rolesGroups = rolesGroups;
    }

    public Integer getMainCorporateId() {
        return mainCorporateId;
    }

    public void setMainCorporateId(Integer mainCorporateId) {
        this.mainCorporateId = mainCorporateId;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
    
}