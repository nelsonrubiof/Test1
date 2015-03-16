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
 * GetCorporateLogoController.java
 *
 * Created on 09-08-2010, 10:01:02 AM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.services.controllers;

import com.scopix.periscope.corporatestructuremanagement.services.webservices.CorporateWebServices;
import com.scopix.periscope.corporatestructuremanagement.services.webservices.client.CorporateWebServiceClient;
import com.scopix.periscope.evidencemanagement.FileManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
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
 * @version 1.0.0
 */
@SpringBean(rootClass = GetCorporateLogoController.class)
public class GetCorporateLogoController extends AbstractController {

    private static Logger log = Logger.getLogger(GetCorporateLogoController.class);
    private CorporateWebServices corporateWebServices;
    private FileManager fileManager;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("start");

        String logoPath = getCorporateWebServices().getCorporateLogoPath();
        log.debug("logo: " + logoPath);

        try {

            Map map = getFileManager().getFile(logoPath);
            log.debug("[handleRequestInternal] logo content retrieved");
            InputStream is = (InputStream) map.get("is");
            Integer size = (Integer) map.get("size");
            log.debug("[handleRequestInternal] logo size: " + size);
            String contentType = "image/jpeg";
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType(contentType);
            response.addHeader("Content-Disposition", "filename=logo.jpeg");
            byte[] img = new byte[2048];
            response.setContentLength(size);
            int count = 0;
            while ((count = is.read(img)) > -1) {
                response.getOutputStream().write(img, 0, count);
            }
            response.getOutputStream().flush();
            response.getOutputStream().close();
            is.close();
        } catch (Exception e) {
            log.error(e);
        }
        log.info("end");
        return null;
    }

    /**
     * @return the corporateWebServices
     */
    public CorporateWebServices getCorporateWebServices() {
        if (corporateWebServices == null) {
            try {
                corporateWebServices = SpringSupport.getInstance().findBeanByClassName(CorporateWebServiceClient.class).
                        getWebService();
            } catch (ScopixException e) {
                log.error("No es posible generar servicio " + e, e);
            }

        }
        return corporateWebServices;
    }

    /**
     * @param corporateWebServices the corporateWebServices to set
     */
    public void setCorporateWebServices(CorporateWebServices corporateWebServices) {
        this.corporateWebServices = corporateWebServices;
    }

    /**
     * @return the fileManager
     */
    public FileManager getFileManager() {
        if (fileManager == null) {
            fileManager = SpringSupport.getInstance().findBeanByClassName(FileManager.class);
        }
        return fileManager;
    }

    /**
     * @param fileManager the fileManager to set
     */
    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }
}
