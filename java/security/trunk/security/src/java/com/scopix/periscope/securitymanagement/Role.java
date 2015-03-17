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
 * Role.java
 *
 * Created on 27-03-2008, 03:23:52 PM
 *
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
import org.hibernate.validator.NotNull;

/**
 *
 * @author César Abarza Suazo
 */
@Entity
public class Role extends BusinessObject implements Comparable<Role> {

    @NotNull
    private String roleName;
    @ManyToMany(targetEntity = RolesGroup.class, fetch = FetchType.LAZY, mappedBy = "roles", cascade = {CascadeType.PERSIST,
        CascadeType.MERGE})
    private List<RolesGroup> rolesGroups;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public int compareTo(Role o) {
        return this.getId() - o.getId();
    }
    public static Comparator<Role> compratorByRoleName = new Comparator<Role>() {

        public int compare(Role o1, Role o2) {
            return o1.getRoleName().compareTo(o2.getRoleName());
        }
    };
}
