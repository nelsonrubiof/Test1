/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * AxisP3301ImageExtractionCommand.java
 * 
 * Created on 16-08-2010, 04:25:15 PM
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.exception.HttpClientInitializationException;
import com.scopix.periscope.exception.HttpGetException;
import com.scopix.periscope.extractionmanagement.AxisP3301EvidenceProvider;
import com.scopix.periscope.extractionmanagement.AxisP3301ImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.http.HttpSupport;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author Gustavo Alvarez
 */
@SpringBean
public class AxisP3301ImageExtractionCommand implements
        ProviderAdaptor<AxisP3301ImageExtractionRequest, AxisP3301EvidenceProvider> {

    private Date date;
    private static Logger log = Logger.getLogger(AxisP3301ImageExtractionCommand.class);

    public void execute(AxisP3301ImageExtractionRequest imageER, AxisP3301EvidenceProvider evProv, Date date)
            throws ScopixException {

        log.info("start");
        this.date = date;
        prepareEvidence(imageER, evProv);
        log.info("end");
    }

    @Override
    public void prepareEvidence(AxisP3301ImageExtractionRequest evidenceRequest, AxisP3301EvidenceProvider evidenceProvider)
            throws ScopixException {

        log.info("start");
        try {
            long startUTC = 0;

            Calendar dateExecution = Calendar.getInstance();
            if (date != null) {
                dateExecution.setTime(date);
            }

            // se utiliza creationTimestamp cuando estï¿½ presente para solicitar evidencia del dï¿½a que corresponde
            // en evidencia Auto_Generada
            if (EvidenceRequestType.AUTO_GENERATED.equals(evidenceRequest.getType())
                    && evidenceRequest.getCreationTimestamp() != null) {
                dateExecution.setTime(evidenceRequest.getEvidenceDate()); // getCreationTimestamp()
            }

            /**
             * Si existe un TimeZone debemos calcular que hora es en el destino es decir de donde se recupera la imagen
             */
            Calendar evidenceDate = Calendar.getInstance();
            evidenceDate.setTimeInMillis(dateExecution.getTimeInMillis());
            if (evidenceRequest.getExtractionPlan().getTimeZoneId() != null
                    && evidenceRequest.getExtractionPlan().getTimeZoneId().length() > 0) {
                dateExecution.setTimeZone(TimeZone.getTimeZone(evidenceRequest.getExtractionPlan().getTimeZoneId()));

                double d = TimeZoneUtils.getDiffInHoursTimeZone(evidenceRequest.getExtractionPlan().getTimeZoneId());
                Date newDateOfDiffHours = new Date(dateExecution.getTimeInMillis());
                newDateOfDiffHours = DateUtils.addHours(newDateOfDiffHours, (int) d * -1);
                evidenceDate.setTimeInMillis(newDateOfDiffHours.getTime());
                configureDate(evidenceDate, evidenceRequest);
            }
            configureDate(dateExecution, evidenceRequest);
            // the name of the clip must be in local time
            String name = DateFormatUtils.format(evidenceDate.getTime(), "yyyyMMdd");
            name = name + "_" + evidenceRequest.getId();
            // startUTC = (dia.getTimeInMillis() + hora.getTimeInMillis()) + (dia.get(Calendar.ZONE_OFFSET) + hora.get(
            // Calendar.DST_OFFSET));
            startUTC = dateExecution.getTimeInMillis();
            log.debug("startUTC: [" + startUTC + "]");

            String protocol = evidenceProvider.getProtocol();
            String serverIP = evidenceProvider.getIpAddress();
            String port = evidenceProvider.getPort();
            String user = evidenceProvider.getUserName();
            String pass = evidenceProvider.getPassword();

            String resolution = evidenceProvider.getResolution() == null ? "640x480" : evidenceProvider.getResolution();
            // Llamada de la forma:
            // protocol://serverIP/axis-cgi/jpg/image.cgi?resolution=640x480
            String url = protocol + "://" + serverIP + ":" + port + "/axis-cgi/jpg/image.cgi?resolution=" + resolution;

            EvidenceFileDAO dao = SpringSupport.getInstance().findBeanByClassName(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.findEvidenceFileByFileName(name + ".jpg");
            if (evidenceFile != null) {
                evidenceFile.setFileCreationDate(new Date());
                dao.save(evidenceFile);
                // HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            } else {
                evidenceFile = new EvidenceFile();
                evidenceFile.setEvidenceDate(new Date(evidenceDate.getTimeInMillis()));
                evidenceFile.setEvidenceExtractionRequest(evidenceRequest);
                evidenceFile.setFilename(name + ".jpg");
                dao.save(evidenceFile);
                // HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            }
            callAxisP3301(url, name, user, pass, evidenceRequest.getLive());
        } catch (ScopixException | ParseException e) {
            throw new ScopixException(e.getMessage(), e);
        }
        log.info("end");
    }

    @SuppressWarnings("restriction")
    private void callAxisP3301(String urlString, String fileName, String user, String password, Boolean isLive)
            throws ScopixException {
        log.info("start, urlString: [" + urlString + "], isLive: [" + isLive + "]");
        CloseableHttpResponse response = null;
        HttpSupport httpSupport;
        try {
            httpSupport = HttpSupport.getInstance();
        } catch (HttpClientInitializationException e) {
            throw new ScopixException("Cannot initialize Http Support.", e);
        }

        try {
            // this.allowCertificatesHTTPSConnections();
            HashMap<String, String> requestHeaders = new HashMap<String, String>();

            if (!user.equals("NOT_USED") && !password.equals("NOT_USED")) {
                String encoding = new sun.misc.BASE64Encoder().encode((user + ":" + password).getBytes());

                requestHeaders.put("Authorization", "Basic " + encoding);
            }
            response = httpSupport.httpGet(urlString, requestHeaders);

            long contentlength = 0;
            if (response != null && response.getEntity() != null) {
                AxisP3301ImageFileReadyCommand command = new AxisP3301ImageFileReadyCommand();
                contentlength = response.getEntity().getContentLength();
                command.execute(fileName, response.getEntity().getContent(), isLive);
            }

            log.debug("contentlength: [" + contentlength + "]");
        } catch (IOException e) {
            throw new ScopixException("Cannot create image.", e);
        } catch (HttpGetException e) {
            throw new ScopixException("Error fetching the image.", e);
        } finally {
            if (response != null) {
                httpSupport.closeHttpEntity(response.getEntity());
                httpSupport.closeHttpResponse(response);
            }
        }
        log.info("end");
    }

    private void configureDate(Calendar dia, EvidenceExtractionRequest evidenceRequest) throws ParseException {
        dia.set(Calendar.HOUR_OF_DAY, 0);
        dia.set(Calendar.MINUTE, 0);
        dia.set(Calendar.SECOND, 0);
        dia.set(Calendar.MILLISECOND, 0);
        log.debug("dia: [" + dia.getTime() + "]");
        Date requestedTime = DateUtils.parseDate(DateFormatUtils.format(evidenceRequest.getRequestedTime(), "HH:mm:ss"),
                new String[]{"HH:mm:ss"});
        Calendar hora = Calendar.getInstance();
        hora.setTime(requestedTime);
        log.debug("hora: [" + hora.getTime() + "]");
        dia.set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY));
        dia.set(Calendar.MINUTE, hora.get(Calendar.MINUTE));
        dia.set(Calendar.SECOND, hora.get(Calendar.SECOND));
    }
}
