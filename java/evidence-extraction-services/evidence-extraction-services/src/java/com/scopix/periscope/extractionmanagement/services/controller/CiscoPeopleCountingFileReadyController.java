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
 *  CiscoPeopleCountingFileReadyController.java
 * 
 *  Created on 10-02-2014, 08:24:15 PM
 * 
 */

package com.scopix.periscope.extractionmanagement.services.controller;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.periscopefoundation.view.XMLView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author carlos polo
 */
@SpringBean
public class CiscoPeopleCountingFileReadyController extends AbstractController {
    
    private static Logger log = Logger.getLogger(CiscoPeopleCountingFileReadyController.class);

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("start");
        //url = notifyURL+"?filename="+fileName+"&xmldate="+xmlDate+"&xmlvaluein="+xmlValueIn+"&xmlvalueout="+xmlValueOut;
        String fileName = request.getParameter("filename");
        String xmlDate = request.getParameter("xmldate");
        String xmlValueIn = request.getParameter("xmlvaluein");
        String xmlValueOut = request.getParameter("xmlvalueout");
        String storeName = request.getParameter("storename");
        Integer evidenceFileId = Integer.valueOf(request.getParameter("evfileid"));

        log.info("fileName: [" + fileName + "], xmlDate: [" + xmlDate + "], xmlValueIn: [" + xmlValueIn + "], "
                + "xmlValueOut: [" + xmlValueOut + "], storeName: [" + storeName + "], evidenceFileId: ["+evidenceFileId+"]");
        
        ExtractionManager extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
        extractionManager.ciscoPeopleCountingFileReady(fileName, xmlDate, xmlValueIn, xmlValueOut, storeName, evidenceFileId);

        log.info("end");
        return new ModelAndView(new XMLView());
    }
}