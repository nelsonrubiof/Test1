/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scopix.periscope.businessservices.commons.controller;

import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.periscopefoundation.view.XMLView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author Admin
 */
@SpringBean(rootClass = SendExtractionPlanToPastController.class)
public class SendExtractionPlanToPastController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        manager.sendExtractionPlanToPastCron();
        return new ModelAndView(new XMLView());
    }
    
}
