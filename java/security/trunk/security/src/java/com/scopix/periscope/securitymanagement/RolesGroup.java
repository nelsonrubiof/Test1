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
 * RolesGroup.java
 *
 * Created on 17-06-2008, 03:25:24 PM
 *
 */
package com.scopix.periscope.securitymanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.hibernate.validator.NotNull;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@Entity
public class RolesGroup extends BusinessObject implements Comparable<RolesGroup> {

    @NotNull
    private String name;
    @NotNull
    private String description;
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "rel_roles_group_role", joinColumns = {@JoinColumn(name = "roles_group_id")}, inverseJoinColumns = {
        @JoinColumn(name = "role_id")})
    private List<Role> roles;
    @ManyToMany(targetEntity = PeriscopeUser.class, fetch = FetchType.LAZY, mappedBy = "rolesGroups", cascade = {
        CascadeType.PERSIST, CascadeType.MERGE})
    private List<PeriscopeUser> periscopeUsers;
    private boolean deleted;

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

    public List<Role> getRoles() {
        if (roles == null) {
            roles = new ArrayList<Role>();
        }
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<PeriscopeUser> getPeriscopeUsers() {
        if (periscopeUsers == null) {
            periscopeUsers = new ArrayList<PeriscopeUser>();
        }
        return periscopeUsers;
    }

    public void setPeriscopeUsers(List<PeriscopeUser> periscopeUsers) {
        this.periscopeUsers = periscopeUsers;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int compareTo(RolesGroup o) {
        return this.getId() - o.getId();
    }
}
