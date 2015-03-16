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
 *  ViewTemplateController.java
 * 
 *  Created on 15-12-2011, 03:34:03 PM
 * 
 */
package com.scopix.periscope.qualitycontrol.services.controllers;

import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.evaluationmanagement.EvaluationManager;
import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.periscopefoundation.util.config.SystemConfig;
import com.scopix.periscope.qualitycontrol.QualityControlManager;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.io.InputStream;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author nelson
 */
@SpringBean(rootClass = ViewTemplateController.class)
public class ViewTemplateController extends AbstractController {

    private static Logger log = Logger.getLogger(ViewTemplateController.class);

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("start");
        try {
            Integer evidenceId = getIntegerParameter(request, "evidenceId");
            Integer evidenceEvaluationId = getIntegerParameter(request, "evidenceEvaluationId");
            String evidencePrePath = request.getParameter("evidencePrePath");
            log.debug("[evidenceId:" + evidenceId + "][evidencePrePath" + evidencePrePath + "]");
            QualityControlManager manager = SpringSupport.getInstance().findBeanByClassName(QualityControlManager.class);
            EvaluationManager evaluationManager = SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class);

            Evidence evidence = manager.getEvidence(evidenceId);

            EvidenceEvaluation ee = evaluationManager.getEvidenceEvaluation(evidenceEvaluationId);
            SituationTemplate st = null;
            //revisamos si nuestra evidencia fue evaluada por esta evaluacion y retornamos la situacion que corresponde
            for (Evidence e : ee.getEvidences()) {
                if (e.getId().equals(evidence.getId())) {
                    st = ee.getObservedMetric().getObservedSituation().getSituation().getSituationTemplate();
                    break;
                }
            }
            log.debug("st:" + st);
            EvidenceProvider provider = manager.getEvidenceProvider(evidenceId);
//            SituationTemplate st = 
//                evidence.getObservedMetrics().get(0).getObservedSituation().getSituation().
//                    getSituationTemplate();

            if (st == null) {
                return null;
            }

            if (provider != null) {
                String templatePath = evidencePrePath + SystemConfig.getStringParameter("TEMPLATES_FOLDER");
                String template = provider.getTemplatePath(st.getId());
                templatePath += "/" + template;
                templatePath = "smb:" + FilenameUtils.separatorsToUnix(templatePath);
                log.debug("templatePath:" + templatePath + " st:" + st.getId() + " ep:" + provider.getId());
                if (template != null && template.length() > 0) {
                    Map result = manager.getFile(templatePath);
                    int size = (Integer) result.get("size");
                    InputStream is = (InputStream) result.get("inputStream");
                    response.setHeader("Expires", "-6000");
                    response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                    response.setHeader("Pragma", "public");
                    response.setContentType("image/jpg");
                    response.addHeader("Content-Disposition", "filename='" + FilenameUtils.getName(templatePath) + "'");
                    byte[] img = new byte[size];
                    response.setContentLength(img.length);
                    while (is.read(img) > -1) {
                        response.getOutputStream().write(img);
                    }
                    response.getOutputStream().flush();
                }
            }
        } catch (ScopixException e) {
            log.info("Error recuperando archivo " + e);
        }
        return null;
    }

    private Integer getIntegerParameter(HttpServletRequest request, String parameter) {
        Integer ret = null;
        try {
            ret = Integer.valueOf(request.getParameter(parameter));
        } catch (NumberFormatException e) {
            ret = 0;
        }
        return ret;
    }
}
