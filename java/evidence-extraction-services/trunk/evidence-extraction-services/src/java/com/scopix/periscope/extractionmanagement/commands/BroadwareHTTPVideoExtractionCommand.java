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
 * BroadwareHTTPVideoExtractionCommand.java
 *
 * Created on 26-02-2013, 13:00:00 PM
 *
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.extractionmanagement.BroadwareHTTPEvidenceProvider;
import com.scopix.periscope.extractionmanagement.BroadwareHTTPVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author gustavo.alvarez
 */
public class BroadwareHTTPVideoExtractionCommand implements
        ProviderAdaptor<BroadwareHTTPVideoExtractionRequest, BroadwareHTTPEvidenceProvider> {

    private static Logger log = Logger.getLogger(BroadwareHTTPVideoExtractionCommand.class);
    private String broadwareCallback;
    private ExtractEvidencePoolExecutor extractEvidencePoolExecutor;
    private Date date;

    public BroadwareHTTPVideoExtractionCommand() {
        if (broadwareCallback == null) {
            Properties prop = null;
            ClassPathResource res = new ClassPathResource("system.properties");
            try {
                prop = new Properties();
                prop.load(res.getInputStream());
                broadwareCallback = prop.getProperty("BroadwareCallback");
            } catch (IOException e) {
                log.warn("[BroadwareCallback]", e);
            }

        }
    }

    public void execute(BroadwareHTTPVideoExtractionRequest videoER, BroadwareHTTPEvidenceProvider evProv,
            ExtractEvidencePoolExecutor eepe, Date date) throws ScopixException {
        extractEvidencePoolExecutor = eepe;
        this.date = date;
        prepareEvidence(videoER, evProv);
    }

    public void prepareEvidence(BroadwareHTTPVideoExtractionRequest evidenceExtractionRequest, 
            BroadwareHTTPEvidenceProvider evidenceProvider) throws ScopixException {
        log.debug("extract()");
        try {
            long startUTC = 0;
            long stopUTC = 0;

            Calendar dateEjecution = Calendar.getInstance();
            // (date != null) => extraction plan to past
            if (date != null) {
                dateEjecution.setTimeInMillis(date.getTime());
            }

            // se utiliza creationTimestamp cuando est� presente para solicitar evidencia del d�a que corresponde
            // en evidencia Auto_Generada
            if (EvidenceRequestType.AUTO_GENERATED.equals(evidenceExtractionRequest.getType())
                    && evidenceExtractionRequest.getCreationTimestamp() != null) {
                dateEjecution.setTime(evidenceExtractionRequest.getEvidenceDate()); //getCreationTimestamp()
            }

            /**
             * Si existe un TimeZone debemos calcular que hora es en el destino es decir de donde se recupera la imagen
             */
            Calendar evidenceDate = Calendar.getInstance();
            evidenceDate.setTimeInMillis(dateEjecution.getTimeInMillis());
            if (evidenceExtractionRequest.getExtractionPlan().getTimeZoneId() != null
                    && evidenceExtractionRequest.getExtractionPlan().getTimeZoneId().length() > 0) {
                dateEjecution.setTimeZone(TimeZone.getTimeZone(
                        evidenceExtractionRequest.getExtractionPlan().getTimeZoneId()));

                double d = TimeZoneUtils.getDiffInHoursTimeZone(
                        evidenceExtractionRequest.getExtractionPlan().getTimeZoneId());
                Date newDateOfDiffHours = new Date(dateEjecution.getTimeInMillis());
                newDateOfDiffHours = DateUtils.addHours(newDateOfDiffHours, (int) d * -1);
                evidenceDate.setTimeInMillis(newDateOfDiffHours.getTime());
                configureDate(evidenceDate, evidenceExtractionRequest);
            }

            configureDate(dateEjecution, evidenceExtractionRequest);

            // the name of the clip must be in local time
            String name = DateFormatUtils.format(dateEjecution.getTime(), "yyyyMMdd");
            name = name + "_" + evidenceExtractionRequest.getId();

            startUTC = dateEjecution.getTimeInMillis();
            log.debug("startUTC: " + startUTC);

            stopUTC = startUTC + (evidenceExtractionRequest.getLengthInSecs() * 1000);
            String serverIP = evidenceProvider.getIpAddress();
            String source = evidenceProvider.getLoopName();

            //register evidence file
            EvidenceFileDAO dao = SpringSupport.getInstance().findBeanByClassName(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.findEvidenceFileByFileName(name + ".bwm");
            if (evidenceFile == null) {
                evidenceFile = new EvidenceFile();
                evidenceFile.setEvidenceDate(new Date(evidenceDate.getTimeInMillis()));
                evidenceFile.setEvidenceExtractionRequest(evidenceExtractionRequest);
                evidenceFile.setFilename(name + ".bwm");
            } else {
                //le cambiamos la fecha de creacion del file
                evidenceFile.setFileCreationDate(null);
            }

            HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);

            //agregamos el store name al callback
            String notifyURL = broadwareCallback + "spring/broadwarehttpvideofileready?filename=" + name;

            String url = "http://" + serverIP
                    + "/cgi-bin/smanager.bwt?command=save"
                    + "&source=" + source
                    + "&savemode=local&name=" + name
                    + "&startUTC=" + startUTC
                    + "&stopUTC=" + stopUTC
                    + "&saveformat=bwm"
                    + "&notifyURL=" + notifyURL;

            //create task and pass to pool executor
            //callBroadware(url);
            BroadwareHTTPVideoExtractionThread bvet = new BroadwareHTTPVideoExtractionThread();
            bvet.init(url);
            extractEvidencePoolExecutor.runTask(bvet);

        } catch (Exception e) {
            throw new ScopixException(e.getMessage(), e);
        }
        log.debug("_extract()");
    }
    
    private void configureDate(Calendar dia, EvidenceExtractionRequest evidenceRequest) throws ParseException {
        dia.set(Calendar.HOUR_OF_DAY, 0);
        dia.set(Calendar.MINUTE, 0);
        dia.set(Calendar.SECOND, 0);
        dia.set(Calendar.MILLISECOND, 0);
        log.debug("dia " + dia.getTime());
        Date requestedTime = DateUtils.parseDate(
                DateFormatUtils.format(evidenceRequest.getRequestedTime(), "HH:mm:ss"), new String[]{"HH:mm:ss"});
        Calendar hora = Calendar.getInstance();
        hora.setTime(requestedTime);
        log.debug("hora " + hora.getTime());
        dia.set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY));
        dia.set(Calendar.MINUTE, hora.get(Calendar.MINUTE));
        dia.set(Calendar.SECOND, hora.get(Calendar.SECOND));
    }
}
