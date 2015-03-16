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
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author marko.perich
 */
@SpringBean
public class ExtractionPlanToPastController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Integer> data = null;
        String dateParameter = request.getParameter("date");
        String storeName = request.getParameter("storeName");
        try {
            if (dateParameter != null && dateParameter.length() > 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date date = sdf.parse(dateParameter);
                ExtractionManager extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
                data = extractionManager.getPastEvidence(date, storeName);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new ScopixException("Te parameter date is required, the format is dd-MM-yyyy, \nfor example, " +
                    "\nlocalhost:8080/ees/spring/extractionplantopast?date=14-07-2008", e);
        }

        return new ModelAndView(data.toString());

    }
}
