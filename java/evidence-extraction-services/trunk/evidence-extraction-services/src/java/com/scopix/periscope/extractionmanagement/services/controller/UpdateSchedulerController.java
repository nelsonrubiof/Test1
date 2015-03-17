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
 * UpdateSchedulerController.java
 *
 * Created on 20-05-2008, 02:09:26 AM
 *
 */


package com.scopix.periscope.extractionmanagement.services.controller;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
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
public class UpdateSchedulerController extends AbstractController {

   @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

    ExtractionManager extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
    extractionManager.updateExtractionPlan();
    //extractionManager.updateExtractionPlanByStoreName("STORE_NAME");

    return new ModelAndView(new XMLView());
      
  }
}