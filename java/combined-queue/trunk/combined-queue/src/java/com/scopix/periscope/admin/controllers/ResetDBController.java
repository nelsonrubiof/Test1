/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  ResetDBController.java
 * 
 *  Created on 25-09-2013, 05:08:22 PM
 * 
 */
package com.scopix.periscope.admin.controllers;
 
import com.scopix.periscope.admin.services.AdminService;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.periscopefoundation.view.XMLView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Nelson Rubio
 * @autor-email nelson.rubio@scopixsolutions.com
 * @version 1.0.0
 */
@SpringBean
public class ResetDBController extends AbstractController {

    /**
     * Calls the reset db  process in order to create the databases if not already created.
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        AdminService adminService = SpringSupport.getInstance().findService(AdminService.class);
        adminService.resetDb();

        return new ModelAndView(new XMLView());

    }
}
