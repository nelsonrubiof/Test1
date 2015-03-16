/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 * 
 */
package com.scopix.periscope.admin.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.scopix.periscope.admin.services.AdminService;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.periscopefoundation.view.XMLView;

/**
 * 
 * This controller is used reset Ghostbuster configuration on demand.
 * 
 * @author EmO
 */
@SpringBean
public class ReloadGBConfigController extends AbstractController {

    private Logger log = Logger.getLogger(ReloadGBConfigController.class);

    public ReloadGBConfigController() {
    }

    /**
     *
     * @param request HttpServletRequest 
     * @param response HttpServletResponse
     * @return ModelAndView con una vista por default XmlView
     * @throws Exception Enc caso de Excepcion
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            AdminService adminService = SpringSupport.getInstance().findService(AdminService.class);
			adminService.reloadGhostbusterConfig();

        } catch (Exception e) {
            log.error("Error: ", e);
            throw e;
        }

        return new ModelAndView(new XMLView());

    }
}
