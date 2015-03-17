/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * VideoExtractionCommand.java
 * 
 * Created on 22-05-2008, 06:01:14 PM
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.exception.HttpClientInitializationException;
import com.scopix.periscope.exception.HttpGetException;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.KumGoEvidenceProvider;
import com.scopix.periscope.extractionmanagement.KumGoImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.http.HttpSupport;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.schedulermanagement.SchedulerManager;

/**
 *
 * @author marko.perich
 */
@SpringBean
public class KumGoImageExtractionCommand implements ProviderAdaptor<KumGoImageExtractionRequest, KumGoEvidenceProvider> {

    private Date date;
    private SchedulerManager schedulerManager;
    private static Logger log = Logger.getLogger(KumGoImageExtractionCommand.class);

    public KumGoImageExtractionCommand() {
    }

    public void execute(KumGoImageExtractionRequest imageER, KumGoEvidenceProvider evProv, Date date) throws ScopixException {
        log.info("start");
        this.date = date;
        prepareEvidence(imageER, evProv);
        log.info("end");
    }

    @Override
    public void prepareEvidence(KumGoImageExtractionRequest evidenceRequest, KumGoEvidenceProvider evidenceProvider)
            throws ScopixException {
        log.info("start");
        try {
            /**
             * se realiza esto para que Evidence
             */
            Calendar dia = Calendar.getInstance();
            if (date != null) {
                dia.setTime(date);
            }

            // se utiliza creationTimestamp cuando está presente para solicitar evidencia del día que corresponde
            // en evidencia Auto_Generada
            if (EvidenceRequestType.AUTO_GENERATED.equals(evidenceRequest.getType())
                    && evidenceRequest.getCreationTimestamp() != null) {
                dia.setTime(evidenceRequest.getEvidenceDate()); // getCreationTimestamp()
            }

            /**
             * Si existe un TimeZone debemos calcular que hora es en el destino es decir de donde se recupera la imagen
             */
            if (evidenceRequest.getExtractionPlan().getTimeZoneId() != null
                    && evidenceRequest.getExtractionPlan().getTimeZoneId().length() > 0) {
                double d = TimeZoneUtils.getDiffInHoursTimeZone(evidenceRequest.getExtractionPlan().getTimeZoneId());
                Date newDateOfDiffHours = new Date(dia.getTimeInMillis());
                newDateOfDiffHours = DateUtils.addHours(newDateOfDiffHours, (int) d * -1);
                dia.setTime(newDateOfDiffHours);
            }

            dia.set(Calendar.HOUR_OF_DAY, 0);
            dia.set(Calendar.MINUTE, 0);
            dia.set(Calendar.SECOND, 0);
            dia.set(Calendar.MILLISECOND, 0);

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date requestedTime = sdf.parse(sdf.format(evidenceRequest.getRequestedTime()));
            Calendar hora = Calendar.getInstance();
            hora.setTime(requestedTime);
            log.debug("hora: [" + hora.getTime() + "]");

            // agregamos los valores al dia ya que la hora esta para 01-01-1970 y esto puede estar en otra zona horaria
            dia.set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY));
            dia.set(Calendar.MINUTE, hora.get(Calendar.MINUTE));
            dia.set(Calendar.SECOND, hora.get(Calendar.SECOND));

            // the name of the clip must be in local time
            String name = DateFormatUtils.format(dia.getTime(), "yyyyMMdd");
            name = name + "_" + evidenceRequest.getId();

            long startUTC = dia.getTimeInMillis();
            log.debug("startUTC: [" + startUTC + "]");

            EvidenceFileDAO dao = SpringSupport.getInstance().findBeanByClassName(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.findEvidenceFileByFileName(name + ".jpg");
            if (evidenceFile != null) {
                evidenceFile.setFileCreationDate(new Date());
                HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            } else {
                evidenceFile = new EvidenceFile();
                evidenceFile.setEvidenceDate(new Date(startUTC));
                evidenceFile.setEvidenceExtractionRequest(evidenceRequest);
                evidenceFile.setFilename(name + ".jpg");
                HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            }

            String protocol = evidenceProvider.getProtocol();
            String serverIP = evidenceProvider.getIpAddress();
            String port = evidenceProvider.getPort();
            String user = evidenceProvider.getUserName();
            String pass = evidenceProvider.getPassword();

            String url = protocol + "://" + serverIP + ":" + port + "/adm/snapshot.cgi";
            callIPCisco2500(url, user, pass, name, evidenceRequest.getLive());

        } catch (ParseException e) {
            throw new ScopixException(e.getMessage(), e);
        }
        log.info("end");
    }

    @SuppressWarnings("restriction")
    private void callIPCisco2500(String urlString, String user, String password, String name, Boolean isLive)
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
            this.allowCertificatesHTTPSConnections();
            HashMap<String, String> requestHeaders = new HashMap<String, String>();

            if (!user.equals("NOT_USED") && !password.equals("NOT_USED")) {
                String encoding = new sun.misc.BASE64Encoder().encode((user + ":" + password).getBytes());

                requestHeaders.put("Authorization", "Basic " + encoding);
            }
            response = httpSupport.httpGet(urlString, requestHeaders);

            long contentlength = 0;
            if (response != null && response.getEntity() != null) {

                byte[] buffer = new byte[65536];
                // filename is repeated as the filepath inside the repository
                File tmp = File.createTempFile("kumgo", ".jpg");
                FileOutputStream jpg = new FileOutputStream(tmp); // uploadLocalDir + fileName + ".jpg"
                int bytesRead = 1;
                while (bytesRead > 0) {
                    bytesRead = response.getEntity().getContent().read(buffer, 0, 65536);
                    if (bytesRead > 0) {
                        jpg.write(buffer, 0, bytesRead);
                    }
                }
                jpg.flush();
                jpg.close();

                KumGoImageFileReadyCommand command = new KumGoImageFileReadyCommand();
                command.execute(name, tmp, isLive);
                contentlength = response.getEntity().getContentLength();
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

    private void allowCertificatesHTTPSConnections() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }
            } };
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            log.error("Error when try to set the defaults certificates handlers", e);
        }
    }

    public SchedulerManager getSchedulerManager() {
        if (schedulerManager == null) {
            schedulerManager = SpringSupport.getInstance().findBeanByClassName(SchedulerManager.class);
        }
        return schedulerManager;
    }

    public void setSchedulerManager(SchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }
}