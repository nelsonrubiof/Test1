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
 *  AutomaticEvidenceInjectionController.java
 * 
 *  Created on 05-12-2011, 12:17:25 PM
 * 
 */
package com.scopix.periscope.extractionmanagement.services.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.periscopefoundation.view.XMLView;

/**
 *
 * @author nelson
 */
@SpringBean
public class AutomaticEvidenceInjectionController extends AbstractController {

    private static Logger log = Logger.getLogger(AutomaticEvidenceInjectionController.class);

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
        log.info("start");
        log.debug(request.getParameterMap());

        String sensorID = request.getParameter("sensorID");

        if (sensorID == null || sensorID.length() == 0) {
            throw new Exception("No se recibe parametro sensorID");
        }

        //Cargamos los datos de los properties
        Integer delay = null;
        try {
            ClassPathResource res = new ClassPathResource("system.properties");
            PropertiesConfiguration property = new PropertiesConfiguration(res.getFile());
            property.setReloadingStrategy(new FileChangedReloadingStrategy());

            delay = property.getInt("MotionDetection.delay");
        } catch (IOException e) {
            log.error("No es posible leer system.properties " + e);
        } catch (ConfigurationException e) {
            log.error("No es posible crear levantar archivo de configuracion system.properties " + e);
        }

        String fecha = request.getParameter("date");
        String hora = request.getParameter("time");
        String delayString = request.getParameter("delay");

        log.debug("sensorID: ["+sensorID+"], fecha: ["+fecha+"], hora: ["+hora+"], delayString: ["+delayString+"]");

        boolean now = true;
        Date fechaEvidencia = new Date();
        if (fecha != null) {
            now = false;
            fechaEvidencia = DateUtils.parseDate(fecha, new String[]{"yyyyMMdd"});
            log.debug("1. fechaEvidencia: ["+fechaEvidencia+"]");
        }

        if (hora != null) {
            now = false;
            String newFecha = DateFormatUtils.format(fechaEvidencia, "yyyyMMdd");
            log.debug("2. newFecha: ["+newFecha+"]");
            fechaEvidencia = DateUtils.parseDate(newFecha + " " + hora, new String[]{"yyyyMMdd HHmm"});
            log.debug("2. fechaEvidencia: ["+fechaEvidencia+"]");
        }

        if (delayString != null) {
            delay = Integer.valueOf(delayString);
        }

        fechaEvidencia = DateUtils.addSeconds(fechaEvidencia, delay);
        log.debug("3. fechaEvidencia: ["+fechaEvidencia+"]");

        boolean chkInterval = false;
        String checkInternval = request.getParameter("check_interval");
        log.debug("checkInternval: ["+checkInternval+"]");
        
        if (checkInternval != null) {
            chkInterval = Boolean.valueOf(checkInternval);
        }

        log.debug("chkInterval (boolean): ["+chkInterval+"]");
        String cameraName = request.getParameter("cameraName");
        log.debug("cameraName: ["+cameraName+"]");

        Integer duration = null;
        if (request.getParameter("duration") != null) {
            duration = Integer.valueOf(request.getParameter("duration"));
        }
        
        Integer beeId = null;
        log.debug("beeId: " + request.getParameter("beeId"));
        if (request.getParameter("beeId") != null) {
            beeId = Integer.valueOf(request.getParameter("beeId"));
        }

        try {
            ExtractionManager extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
            extractionManager.injectAutomaticEvidence(sensorID, fechaEvidencia, chkInterval, now, cameraName, duration, beeId);
        } catch (Exception e) {
            throw e;
        }
        log.info("end");
        return new ModelAndView(new XMLView());
    }
}
