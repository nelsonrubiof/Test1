/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * VideoConverterController.java
 * 
 * Created on 26/08/2014
 */
package com.scopix.periscope.extractionmanagement.services.controller;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

@SpringBean
public class VideoConverterFileReadyController extends AbstractController {

    private static Logger log = Logger.getLogger(VideoConverterFileReadyController.class);

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("start");

        // http://localhost:8080/ees/spring/videoconverterfileready?waitForConverter=N&evidenceId=001&originalName=videoTmp.avi&error=false&convertedName=videoTmp.mp4
        String error = request.getParameter("error");
        String evidenceId = request.getParameter("evidenceId");
        String originalName = request.getParameter("originalName");
            String convertedName = request.getParameter("convertedName");
        String waitForConverter = request.getParameter("waitForConverter");

        log.debug("evidenceId: [" + evidenceId + "], originalName: [" + originalName + "], convertedName: [" + convertedName
                + "], error: [" + error + "], waitForConverter: [" + waitForConverter + "]");

        ExtractionManager manager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
        manager.videoConverterFileReady(error, evidenceId, originalName, convertedName, waitForConverter);

        log.info("end");
        return null;
    }
}