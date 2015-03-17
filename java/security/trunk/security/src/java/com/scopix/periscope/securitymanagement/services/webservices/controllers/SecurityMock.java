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
 * JumboMock.java
 *
 * Created on 12-06-2008, 12:00:17 PM
 *
 */
package com.scopix.periscope.securitymanagement.services.webservices.controllers;

import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.periscopefoundation.view.XMLView;
import com.scopix.periscope.securitymanagement.BusinessRuleManagerPermissions;
import com.scopix.periscope.securitymanagement.CorporateStructureManagerPermissions;
import com.scopix.periscope.securitymanagement.EvaluationManagerPermissions;
import com.scopix.periscope.securitymanagement.EvaluationQueueManagerPermissions;
import com.scopix.periscope.securitymanagement.PeriscopeUser;
import com.scopix.periscope.securitymanagement.QualityControlManagerPermissions;
import com.scopix.periscope.securitymanagement.QueueManagerPermissions;
import com.scopix.periscope.securitymanagement.Role;
import com.scopix.periscope.securitymanagement.RolesGroup;
import com.scopix.periscope.securitymanagement.SecurityManagerPermissions;
import com.scopix.periscope.securitymanagement.TemplatesManagerPermissions;
import com.scopix.periscope.securitymanagement.WizardManagerPermissions;
import com.scopix.periscope.securitymanagement.UserState;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@SpringBean(rootClass = SecurityMock.class)
public class SecurityMock extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
        this.loadPermissions();
        return new ModelAndView(new XMLView());
    }

    public void loadPermissions() {
        List<String> roles = new ArrayList<String>();
        List<Role> rolesList = new ArrayList<Role>();
        Role roleObject = null;
        roles.addAll(BusinessRuleManagerPermissions.getPermissionsList());
        roles.addAll(CorporateStructureManagerPermissions.getPermissionsList());
        roles.addAll(EvaluationManagerPermissions.getPermissionsList());
        roles.addAll(EvaluationQueueManagerPermissions.getPermissionsList());
        roles.addAll(QualityControlManagerPermissions.getPermissionsList());
        roles.addAll(QueueManagerPermissions.getPermissionsList());
        roles.addAll(SecurityManagerPermissions.getPermissionsList());
        roles.addAll(TemplatesManagerPermissions.getPermissionsList());
        roles.addAll(WizardManagerPermissions.getPermissionsList());
        for (String role : roles) {
            roleObject = new Role();
            roleObject.setRoleName(role);
            SpringSupport.getInstance().findBeanByClassName(com.scopix.periscope.securitymanagement.SecurityManager.class).
                    addRole(roleObject);
            rolesList.add(roleObject);
        }

        RolesGroup rolesGroupAdmin = new RolesGroup();
        rolesGroupAdmin.setDescription("Default roles group for Admin User");
        rolesGroupAdmin.setName("Admin Roles Group");
        rolesGroupAdmin.setRoles(rolesList);
        HibernateSupport.getInstance().findGenericDAO().save(rolesGroupAdmin);


        PeriscopeUser periscopeUser = new PeriscopeUser();
        periscopeUser.setName("admin");
        periscopeUser.setPassword("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92");
        periscopeUser.setStartDate(new Date());
        periscopeUser.setUserState(UserState.ACTIVE);
        periscopeUser.getRolesGroups().add(rolesGroupAdmin);
        HibernateSupport.getInstance().findGenericDAO().save(periscopeUser);

        periscopeUser = new PeriscopeUser();
        periscopeUser.setName("evidence-services");
        periscopeUser.setPassword("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92");
        periscopeUser.setStartDate(new Date());
        periscopeUser.setUserState(UserState.ACTIVE);
        periscopeUser.getRolesGroups().add(rolesGroupAdmin);
        HibernateSupport.getInstance().findGenericDAO().save(periscopeUser);
    }
}
