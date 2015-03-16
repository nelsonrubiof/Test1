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
 *  GenerateDataOldController.java
 * 
 *  Created on 06-09-2011, 03:00:57 PM
 * 
 */
package com.scopix.periscope.reporting.controllers;

import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.periscopefoundation.view.XMLView;
import com.scopix.periscope.reporting.ReportingManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author nelson
 */
@SpringBean(rootClass = GenerateDataOldController.class)
public class GenerateDataOldController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        ReportingManager manager = SpringSupport.getInstance().findBeanByClassName(ReportingManager.class);
        manager.generateDataFromDataOld();
        return new ModelAndView(new XMLView());

    }
}
