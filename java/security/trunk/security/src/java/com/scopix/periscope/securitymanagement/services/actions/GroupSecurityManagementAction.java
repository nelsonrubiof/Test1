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
 * GroupSecurityManagementAction.java
 *
 * Created on 17-06-2008, 03:13:37 PM
 *
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.securitymanagement.services.actions;

import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.Role;
import com.scopix.periscope.securitymanagement.RolesGroup;
import com.scopix.periscope.securitymanagement.SynchronizationManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@Results({
    @Result(name = "success", value = "/WEB-INF/jsp/securitymanagement/groups/groupManagement.jsp"),
    @Result(name = "filter", value = "/WEB-INF/jsp/securitymanagement/groups/filter.jsp"),
    @Result(name = "list", value = "/WEB-INF/jsp/securitymanagement/groups/list.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/securitymanagement/groups/edit.jsp"),
    @Result(name = "roles", value = "/WEB-INF/jsp/securitymanagement/groups/roles.jsp")
})
@Namespace("/securitymanagement")
@ParentPackage("default")
public class GroupSecurityManagementAction extends BaseAction implements SessionAware {

    private static final String FILTER = "filter";
    private static final String LIST = "list";
    private static final String EDIT = "edit";
    private static final String ROLES = "roles";
    private RolesGroup rolesGroup;
    private List<RolesGroup> rolesGroups;
    private Map session;
    private Integer[] groupRoles;
    private Integer[] noGroupRoles;

    private SynchronizationManager synchronizationManager;

    @Override
    public String execute() throws Exception {
        this.loadFilters();
        return SUCCESS;
    }

    public String readyList() throws ScopixException {
        this.list();
        return LIST;
    }

    public String cancel() {
        this.loadFilters();
        return SUCCESS;
    }

    public String list() throws ScopixException {
        if (rolesGroup == null || rolesGroup.getName() == null) {
            rolesGroup = (RolesGroup) getSession().get("filter_group");
        }
        if (rolesGroup != null) {
            rolesGroups = SpringSupport.getInstance().findBeanByClassName(
                    com.scopix.periscope.securitymanagement.SecurityManager.class).getRolesGroupList(rolesGroup, session.
                    containsKey("sessionId") ? (Long) session.get("sessionId") : 0L);
            getSession().put("filter_group", rolesGroup);
        }
        return LIST;
    }

    public String filter() {
        return FILTER;
    }

    public String newGroup() {
        this.setEditable(false);
        rolesGroup = new RolesGroup();
        session.put("rolesGroup", rolesGroup);
        session.remove("roles");
        return EDIT;
    }

    public String editGroup() throws ScopixException {
        this.setEditable(true);
        rolesGroup = SpringSupport.getInstance().
                findBeanByClassName(com.scopix.periscope.securitymanagement.SecurityManager.class).
                getRolesGroup(rolesGroup.getId(), session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0L);
        List<Role> roles = rolesGroup.getRoles();
        roles.isEmpty();
        session.put("rolesGroup", rolesGroup);
        session.remove("roles");
        rolesGroup.setRoles(roles);
        return EDIT;
    }

    public String showRoles() {
        rolesGroup = (RolesGroup) session.get("rolesGroup");
        return ROLES;
    }

    public String save() throws ScopixException {
        //Validar que vengan todos los datos
        //Validar claves = y argo 6
        //Validar nombre de usuario no exista
        if (rolesGroup.getName() == null || rolesGroup.getName().length() == 0) {
            this.addActionError(this.getText("error.securitymanagement.goupName.invalid"));
        }
        if (rolesGroup.getDescription() == null || rolesGroup.getDescription().length() == 0) {
            this.addActionError(this.getText("error.securitymanagement.description.invalid"));
        }
        if (((RolesGroup) session.get("rolesGroup")) == null || ((RolesGroup) session.get("rolesGroup")).getRoles() == null ||
                ((RolesGroup) session.get("rolesGroup")).getRoles().isEmpty()) {
            this.addActionError(this.getText("error.securitymanagement.group.roles.empty"));
        }
        if (!this.isEditable() && rolesGroup.getName() != null && SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.SecurityManager.class).
                findSpecificRolesGroupByName(rolesGroup.getName(), session.containsKey("sessionId")
                ? (Long) session.get("sessionId") : 0L) != null) {
            this.addActionError(this.getText("error.securitymanagement.group.alreadyExist"));
        }
        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return EDIT;
        }
        rolesGroup.setRoles(((RolesGroup) session.get("rolesGroup")).getRoles());
        session.remove("rolesGroup");

        if (isEditable()) {
            SpringSupport.getInstance().findBeanByClassName(com.scopix.periscope.securitymanagement.SecurityManager.class).
                    updateRolesGroup(rolesGroup, session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0);
        } else {
            SpringSupport.getInstance().findBeanByClassName(com.scopix.periscope.securitymanagement.SecurityManager.class).
                    addRolesGroup(rolesGroup, session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0);
        }

        getSynchronizationManager().synchronizeRoleGroup(rolesGroup.getId());
        this.loadFilters();
        return SUCCESS;
    }

    public String deleteGroup() throws ScopixException {
        SpringSupport.getInstance().findBeanByClassName(com.scopix.periscope.securitymanagement.SecurityManager.class).
                deleteRolesGroup(rolesGroup.getId(), session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0);
        this.list();
        getSynchronizationManager().synchronizeRoleGroup(rolesGroup.getId());
        return LIST;
    }

    public String addRoleToGroup() {
        List<Role> roles = (List<Role>) session.get("roles");
        rolesGroup = (RolesGroup) session.get("rolesGroup");
        if (noGroupRoles != null && noGroupRoles.length > 0 && roles != null) {
            Collections.sort(roles);
            for (Integer id : noGroupRoles) {
                Role role = new Role();
                role.setId(id);
                int index = Collections.binarySearch(roles, role);
                if (index >= 0) {
                    role = roles.remove(index);
                    rolesGroup.getRoles().add(role);
                }
            }
            session.put("roles", roles);
        }
        return ROLES;
    }

    public String removeRoleFromGroup() {
        List<Role> roles = (List<Role>) session.get("roles");
        rolesGroup = (RolesGroup) session.get("rolesGroup");
        if (groupRoles != null && groupRoles.length > 0 && rolesGroup.getRoles() != null) {
            Collections.sort(rolesGroup.getRoles());
            for (Integer id : groupRoles) {
                Role role = new Role();
                role.setId(id);
                int index = Collections.binarySearch(rolesGroup.getRoles(), role);
                if (index >= 0) {
                    role = rolesGroup.getRoles().remove(index);
                    if (roles == null) {
                        roles = new ArrayList<Role>();
                    }
                    roles.add(role);
                }
            }
            session.put("roles", roles);
        }
        return ROLES;
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

    public RolesGroup getRolesGroup() {
        return rolesGroup;
    }

    public void setRolesGroup(RolesGroup rolesGroup) {
        this.rolesGroup = rolesGroup;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public List<Role> getRoles() throws ScopixException {
        List<Role> roles = (List<Role>) session.get("roles");
        if (roles == null) {
            roles = SpringSupport.getInstance().
                    findBeanByClassName(com.scopix.periscope.securitymanagement.SecurityManager.class).
                    getRolesList(new Role(), session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0);
        }
        if (roles != null) {
            Collections.sort(roles);
            for (Role role : rolesGroup.getRoles()) {
                int index = Collections.binarySearch(roles, role);
                if (index >= 0) {
                    roles.remove(index);
                }
            }
            Collections.sort(roles, Role.compratorByRoleName);
            session.put("roles", roles);
        }
        return roles;
    }

    public List<Role> getAssignRoles() {
        List<Role> roles = ((RolesGroup) session.get("rolesGroup")).getRoles();
        Collections.sort(roles, Role.compratorByRoleName);
        return roles;
    }

    public Integer[] getGroupRoles() {
        return groupRoles;
    }

    public void setGroupRoles(Integer[] groupRoles) {
        this.groupRoles = groupRoles;
    }

    public Integer[] getNoGroupRoles() {
        return noGroupRoles;
    }

    public void setNoGroupRoles(Integer[] noGroupRoles) {
        this.noGroupRoles = noGroupRoles;
    }

    private void loadFilters() {
        rolesGroup = (RolesGroup) getSession().get("filter_group");
    }

    public SynchronizationManager getSynchronizationManager() {
        if (synchronizationManager == null) {
            synchronizationManager = SpringSupport.getInstance().findBeanByClassName(SynchronizationManager.class);
        }
        return synchronizationManager;
    }

    public void setSynchronizationManager(SynchronizationManager synchronizationManager) {
        this.synchronizationManager = synchronizationManager;
    }
}
