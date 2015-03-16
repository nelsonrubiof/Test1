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
 *  Cisco7VideoExtractionCommand.java
 * 
 *  Created on 02-10-2013, 05:48:04 PM
 * 
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.extractionmanagement.Cisco7EvidenceProvider;
import com.scopix.periscope.extractionmanagement.Cisco7VideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.utilities.ScopixUtilities;

/**
 *
 * @author Gustavo Alvarez
 * @modified Carlos Polo
 */
public class Cisco7VideoExtractionCommand implements ProviderAdaptor<Cisco7VideoExtractionRequest, Cisco7EvidenceProvider> {

    private Date date;
    private String storeName;
    private static final String NOT_USED = "NOT_USED";
    private ExtractEvidencePoolExecutor extractEvidencePoolExecutor;
    private static Logger log = Logger.getLogger(Cisco7VideoExtractionCommand.class);

    public void execute(Cisco7VideoExtractionRequest cer,
            Cisco7EvidenceProvider evProv, Date date, ExtractEvidencePoolExecutor eepe) throws ScopixException {

        log.info("start, storeName: [" + storeName + "]");
        this.extractEvidencePoolExecutor = eepe;
        this.date = date;
        prepareEvidence(cer, evProv);
        log.info("end");
    }

    @Override
    public void prepareEvidence(Cisco7VideoExtractionRequest evidenceExtractionRequest,
            Cisco7EvidenceProvider evidenceProvider) throws ScopixException {

        log.info("start, evidenceExtractionRequest.getId(): [" + evidenceExtractionRequest.getId() + "], storeName: [" + storeName + "]");
        try {
            long startUTC = 0;
            long stopUTC = 0;

            //Calcula la fecha de evidencia de acuerdo al tipo de generación
            Date evidenceDate = ScopixUtilities.calculateEvidenceDate(date, evidenceExtractionRequest);
            log.debug("evidenceDate: [" + evidenceDate + "]");

            // the name of the clip must be in local time
            String name = DateFormatUtils.format(evidenceDate, "yyyyMMdd");
            name = name + "_" + evidenceExtractionRequest.getId();
            String fileName = name + ".mp4";

            //UTC corresponde al long de la hora en server segun hora evidencia
            //si evidencia es 17:00 en NY se debe recuperar que hora es en el server y generar UTC
            //en sfco deben ser 14:00 
            double diff = TimeZoneUtils.getDiffHourTimezoneToServer(evidenceExtractionRequest.getExtractionPlan().getTimeZoneId());
            Date UTCDate = new Date(evidenceDate.getTime());
            UTCDate = DateUtils.addHours(UTCDate, (int) diff);

            log.debug("UTCDate: [" + UTCDate + "][diff: " + diff + "]");
            startUTC = UTCDate.getTime();
            log.debug("startUTC milisegundos: [" + startUTC + "]");

            stopUTC = startUTC + (evidenceExtractionRequest.getLengthInSecs() * 1000);
            log.debug("stopUTC milisegundos: [" + stopUTC + "]");

            //Cisco7 ocupa los tiempos en segundos
            startUTC = startUTC / 1000;
            stopUTC = stopUTC / 1000;

            log.debug("startUTC segundos: [" + startUTC + "]");
            log.debug("stopUTC segundos: [" + stopUTC + "]");

            String vsomServerIP = evidenceProvider.getVsomIpAddress();
            String vsomUser = evidenceProvider.getVsomUser();
            String vsomPass = evidenceProvider.getVsomPass();
            String cameraName = evidenceProvider.getCameraName();
            String vsomProtocol = evidenceProvider.getVsomProtocol();
            String vsomPort = evidenceProvider.getVsomPort();
            String vsomDomain = "";

            log.debug("evidenceProvider.getVsomDomain(): [" + evidenceProvider.getVsomDomain() + "]");
            if (!NOT_USED.equalsIgnoreCase(evidenceProvider.getVsomDomain())) {
                vsomDomain = evidenceProvider.getVsomDomain();
            }

            String mediaServerIP = null;
            log.debug("evidenceProvider.getMediaServerIp(): " + evidenceProvider.getMediaServerIp());
            if (!NOT_USED.equalsIgnoreCase(evidenceProvider.getMediaServerIp())) {
                mediaServerIP = evidenceProvider.getMediaServerIp();
            }

            String mediaServerProtocol = evidenceProvider.getMediaServerProtocol();
            String mediaServerPort = evidenceProvider.getMediaServerPort();

            //register evidence file
            log.debug("fileName: [" + fileName + "], cameraName: [" + cameraName + "], storeName: [" + getStoreName() + "]");
            EvidenceFileDAO dao = HibernateSupport.getInstance().findDao(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.registerEvidenceFile(fileName, evidenceExtractionRequest, evidenceDate);

            Cisco7VideoExtractionThread cisco7VideoExThread = new Cisco7VideoExtractionThread();
            cisco7VideoExThread.init(vsomServerIP, vsomUser, vsomPass, vsomDomain, cameraName,
                    vsomProtocol, vsomPort, mediaServerIP, mediaServerProtocol, mediaServerPort,
                    startUTC, stopUTC, name, evidenceFile, getStoreName());
            //ejecuta en el pool de hilos
            extractEvidencePoolExecutor.pause();
            extractEvidencePoolExecutor.runTask(cisco7VideoExThread);
            extractEvidencePoolExecutor.resume();

        } catch (ParseException pex) {
            log.error(pex.getMessage(), pex);
            throw new ScopixException(pex);
        }

        log.info("end");
    }

    /**
     * @return the storeName
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * @param storeName the storeName to set
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
