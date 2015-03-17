/*
 * 
 * Copyright © 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * GetEvidenceController.java
 *
 * Created on 28-09-2008, 03:06:24 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.services.controllers;

import com.scopix.periscope.JCIFSUtil;
import com.scopix.periscope.evaluationmanagement.AuditingManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author César Abarza Suazo.
 */
@SpringBean(rootClass = GetEvidenceController.class)
public class GetEvidenceController extends AbstractController {

    private Logger log = Logger.getLogger(GetEvidenceController.class);
    private Properties prop;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Integer evidenceId = request.getParameter("id") != null ? new Integer(request.getParameter("id")) : null;
        log.debug("evidence = " + evidenceId);
        Integer observedMetricId = request.getParameter("omId") != null ? new Integer(request.getParameter("omId")) : null;
        log.debug("observedMetric = " + observedMetricId);


        AuditingManager manager = SpringSupport.getInstance().findBeanByClassName(AuditingManager.class);
        String evidence = manager.getEvidencePath(evidenceId, observedMetricId);

        JCIFSUtil vfsUtil = SpringSupport.getInstance().findBeanByClassName(JCIFSUtil.class);
        log.debug("evidence path = " + evidence);
        Map map = vfsUtil.getFileSmb(evidence);
        InputStream is = (InputStream) map.get("is");
        Integer size = (Integer) map.get("size");
        String contentType = "video/x-flv";
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setContentType(contentType);
        response.addHeader("Content-Disposition", "filename=evidence.flv");
        byte[] img = new byte[2048];
        response.setContentLength(size);
        int count = 0;
        while ((count = is.read(img)) > -1) {
            response.getOutputStream().write(img, 0, count);
        }
        response.getOutputStream().flush();
        response.getOutputStream().close();
        is.close();
        return null;
    }
}
