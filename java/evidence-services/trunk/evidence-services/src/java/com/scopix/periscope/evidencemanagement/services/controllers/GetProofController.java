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
 * GetProofController.java
 *
 * Created on 29-07-2010, 03:10:06 PM
 *
 */
package com.scopix.periscope.evidencemanagement.services.controllers;

import com.scopix.periscope.evaluationmanagement.services.webservices.EvaluationWebServices;
import com.scopix.periscope.evaluationmanagement.services.webservices.client.EvaluationWebServicesClient;
import com.scopix.periscope.evidencemanagement.FileManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.InputStream;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author Gustavo Alvarez
 */
@SpringBean(rootClass = GetProofController.class)
public class GetProofController extends AbstractController {

    private Logger log = Logger.getLogger(GetProofController.class);

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //CHECKSTYLE:OFF
        Boolean withMarks = Boolean.valueOf(request.getParameter("withMarks"));
        //CHECKSTYLE:ON
        Integer proofId = request.getParameter("id") != null ? new Integer(request.getParameter("id")) : null;
        log.debug("[handleRequestInternal] proof = " + proofId);
        Integer observedMetricId = request.getParameter("omId") != null ? new Integer(request.getParameter("omId")) : null;
        log.debug("[handleRequestInternal] observedMetric = " + observedMetricId);
        Long sessionId = request.getParameter("sessionId") != null ? new Long(request.getParameter("sessionId")) : null;
        log.debug("[handleRequestInternal] sessionId = " + sessionId);


        EvaluationWebServices webService = SpringSupport.getInstance().findBeanByClassName(EvaluationWebServicesClient.class).
                getWebService();
        String proofPath = webService.getProofsPath(proofId, observedMetricId, withMarks, sessionId);
        log.debug("Proof path = " + proofPath);

        FileManager fileManager = SpringSupport.getInstance().findBeanByClassName(FileManager.class);
        Map map = fileManager.getFile(proofPath);
        InputStream is = (InputStream) map.get("is");
        Integer size = (Integer) map.get("size");
        String contentType = "image/jpeg";
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setContentType(contentType);
        response.addHeader("Content-Disposition", "filename=proof.jpeg");
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
