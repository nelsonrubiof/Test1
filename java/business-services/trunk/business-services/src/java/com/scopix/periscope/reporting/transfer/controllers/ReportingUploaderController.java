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
 *  ReportingUploaderController.java
 * 
 *  Created on 01-09-2011, 09:17:51 AM
 * 
 */
package com.scopix.periscope.reporting.transfer.controllers;

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
@SpringBean(rootClass = ReportingUploaderController.class)
public class ReportingUploaderController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReportingManager manager = SpringSupport.getInstance().findBeanByClassName(ReportingManager.class);
        Integer uploadProcessId = Integer.parseInt(request.getParameter("upload_process_id"));
        manager.uploadProcess(uploadProcessId);
        return new ModelAndView(new XMLView());
    }
}
