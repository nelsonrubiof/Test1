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

import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.admin.services.AdminService;
import com.scopix.periscope.periscopefoundation.view.XMLView;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * 
 * This controller is used to fire a database reset from web. After reseting
 * database, its populated with some mocks.
 * 
 * @author maximiliano.vazquez
 * @version 1.0.0
 */
@SpringBean
public class ResetDBController extends AbstractController {

    private Logger log = Logger.getLogger(ResetDBController.class);

    /**
     * Constructor Class
     */
    public ResetDBController() {
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
            adminService.resetDb();
        //adminService.insertEvidenceRequestMock();
        //adminService.createCodeTables();
        //adminService.createRipleyPlaces();
        //adminService.createRipleyBusinessRules();
        } catch (Exception e) {
            log.error("Error: ", e);
            throw e;
        }

        return new ModelAndView(new XMLView());

    }
}
