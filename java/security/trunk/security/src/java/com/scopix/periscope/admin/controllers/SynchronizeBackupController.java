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
 *  SynchronizeBackupController.java
 * 
 *  Created on 08-08-2012, 03:53:07 PM
 * 
 */
package com.scopix.periscope.admin.controllers;

import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.periscopefoundation.view.XMLView;
import com.scopix.periscope.securitymanagement.SynchronizationManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author nelson
 */
@SpringBean
public class SynchronizeBackupController extends AbstractController {

    private static Logger log = Logger.getLogger(SynchronizeBackupController.class);

    public SynchronizeBackupController() {
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer corporateId = null;
        try {
            corporateId = Integer.valueOf(request.getParameter("corporateId"));
        } catch (NumberFormatException e) {
            log.error("" + e, e);
        }
        SynchronizationManager sm = SpringSupport.getInstance().findBeanByClassName(SynchronizationManager.class);
        sm.synchronizeUserCorporate(corporateId);
        return new ModelAndView(new XMLView());
    }
}
