/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * ReadUrlPHPImageExtractionCommand.java
 * 
 * Created on 16-08-2010, 04:25:15 PM
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.exception.HttpClientInitializationException;
import com.scopix.periscope.exception.HttpGetException;
import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.ReadUrlPHPEvidenceProvider;
import com.scopix.periscope.extractionmanagement.ReadUrlPHPImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.http.HttpSupport;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author Nelson Rubio
 * @version 1.0.0
 */
@SpringBean
public class ReadUrlPHPImageExtractionCommand implements
        ProviderAdaptor<ReadUrlPHPImageExtractionRequest, ReadUrlPHPEvidenceProvider> {

    private Date date;
    private static Logger log = Logger.getLogger(ReadUrlPHPImageExtractionCommand.class);

    public void execute(ReadUrlPHPImageExtractionRequest imageER, ReadUrlPHPEvidenceProvider evProv, Date date)
            throws ScopixException {
        log.info("start");
        this.date = date;
        prepareEvidence(imageER, evProv);
        log.info("end");
    }

    @Override
    public void prepareEvidence(ReadUrlPHPImageExtractionRequest evidenceRequest, ReadUrlPHPEvidenceProvider evidenceProvider)
            throws ScopixException {
        log.info("start");
        try {
            Calendar dateEjecution = Calendar.getInstance();
            if (date != null) {
                dateEjecution.setTime(date);
            }

            // se utiliza creationTimestamp cuando est� presente para solicitar evidencia del d�a que corresponde
            // en evidencia Auto_Generada
            if (EvidenceRequestType.AUTO_GENERATED.equals(evidenceRequest.getType())
                    && evidenceRequest.getCreationTimestamp() != null) {
                dateEjecution.setTime(evidenceRequest.getEvidenceDate()); // getCreationTimestamp()
            }

            /**
             * Si existe un TimeZone debemos calcular que hora es en el destino es decir de donde se recupera la imagen
             */
            Calendar evidenceDate = Calendar.getInstance();
            evidenceDate.setTimeInMillis(dateEjecution.getTimeInMillis());
            if (evidenceRequest.getExtractionPlan().getTimeZoneId() != null
                    && evidenceRequest.getExtractionPlan().getTimeZoneId().length() > 0) {
                dateEjecution.setTimeZone(TimeZone.getTimeZone(evidenceRequest.getExtractionPlan().getTimeZoneId()));

                double d = TimeZoneUtils.getDiffInHoursTimeZone(evidenceRequest.getExtractionPlan().getTimeZoneId());
                Date newDateOfDiffHours = new Date(dateEjecution.getTimeInMillis());
                newDateOfDiffHours = DateUtils.addHours(newDateOfDiffHours, (int) d * -1);
                evidenceDate.setTimeInMillis(newDateOfDiffHours.getTime());
                configureDate(evidenceDate, evidenceRequest);
            }
            configureDate(dateEjecution, evidenceRequest);
            // the name of the clip must be in local time
            String name = DateFormatUtils.format(evidenceDate.getTime(), "yyyyMMdd");
            name = name + "_" + evidenceRequest.getId();

            String protocol = evidenceProvider.getProtocol();
            String serverIP = evidenceProvider.getIpAddress();
            String port = evidenceProvider.getPort();

            String user = evidenceProvider.getUserName();
            String pass = evidenceProvider.getPassword();

            // Llamada de la forma:
            // protocol://serverIP/read_url.php?user=&pwd=query=
            // se espera en query https://10.113.38.151/jpg//image.jpg
            String url = protocol + "://" + serverIP + ":" + port + "/read/read_url.php?" + "user=" + user + "&pwd=" + pass
                    + "&type=IMAGE" + "&query=" + evidenceProvider.getQuery();

            EvidenceFileDAO dao = SpringSupport.getInstance().findBeanByClassName(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.findEvidenceFileByFileName(name + ".jpg");
            if (evidenceFile != null) {
                evidenceFile.setFileCreationDate(new Date());
                HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            } else {
                evidenceFile = new EvidenceFile();
                evidenceFile.setEvidenceDate(new Date(evidenceDate.getTimeInMillis()));
                evidenceFile.setEvidenceExtractionRequest(evidenceRequest);
                evidenceFile.setFilename(name + ".jpg");
                HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            }
            callReadUrl(url, name, user, pass, Integer.parseInt(port), evidenceRequest.getLive());
        } catch (Exception e) {
            throw new ScopixException(e.getMessage(), e);
        }
        log.info("end");
    }

    private void callReadUrl(String urlString, String name, String user, String password, int port, Boolean isLive)
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

            response = httpSupport.httpGet(urlString, requestHeaders);

            long contentlength = 0;
            if (response != null && response.getEntity() != null) {
                contentlength = response.getEntity().getContentLength();

                InputStream is = response.getEntity().getContent();
                // is.read();
                ReadUrlPHPImageFileReadyCommand command = new ReadUrlPHPImageFileReadyCommand();
                command.execute(name, is, isLive);
            }
            EntityUtils.consume(response.getEntity());
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
        log.info("start");
        dia.set(Calendar.HOUR_OF_DAY, 0);
        dia.set(Calendar.MINUTE, 0);
        dia.set(Calendar.SECOND, 0);
        dia.set(Calendar.MILLISECOND, 0);
        log.debug("dia: [" + dia.getTime() + "]");
        Date requestedTime = DateUtils.parseDate(DateFormatUtils.format(evidenceRequest.getRequestedTime(), "HH:mm:ss"),
                new String[] { "HH:mm:ss" });
        Calendar hora = Calendar.getInstance();
        hora.setTime(requestedTime);
        log.debug("hora: [" + hora.getTime() + "]");
        dia.set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY));
        dia.set(Calendar.MINUTE, hora.get(Calendar.MINUTE));
        dia.set(Calendar.SECOND, hora.get(Calendar.SECOND));
        log.info("end");
    }
}