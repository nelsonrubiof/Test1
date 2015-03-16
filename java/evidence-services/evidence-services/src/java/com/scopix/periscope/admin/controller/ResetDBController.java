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
 * ResetDBController.java
 *
 * Created on 18-06-2008, 02:04:27 AM
 *
 */
package com.scopix.periscope.admin.controller;

import com.scopix.periscope.admin.EvidenceServicesAdminService;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.periscopefoundation.view.XMLView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author marko.perich
 */
@SpringBean
public class ResetDBController extends AbstractController {
    private EvidenceServicesAdminService adminService;
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        getAdminService().resetDb();
        return new ModelAndView(new XMLView());

    }

    /**
     * @return the adminService
     */
    public EvidenceServicesAdminService getAdminService() {
        if (adminService == null) {
            adminService = SpringSupport.getInstance().findService(EvidenceServicesAdminService.class);
        }
        return adminService;
    }

    /**
     * @param adminService the adminService to set
     */
    public void setAdminService(EvidenceServicesAdminService adminService) {
        this.adminService = adminService;
    }
}