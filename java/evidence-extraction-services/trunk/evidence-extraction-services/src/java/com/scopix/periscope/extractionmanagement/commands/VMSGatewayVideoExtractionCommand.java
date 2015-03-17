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
 * VMSGatewayVideoExtractionCommand.java
 *
 * Created on 13-09-2011, 11:18:05 AM
 *
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.TimeZone;

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
import org.springframework.core.io.ClassPathResource;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.exception.HttpClientInitializationException;
import com.scopix.periscope.exception.HttpGetException;
import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.VMSGatewayEvidenceProvider;
import com.scopix.periscope.extractionmanagement.VMSGatewayVideoExtractionRequest;
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
 * @author Gustavo Alvarez
 */
@SpringBean
public class VMSGatewayVideoExtractionCommand implements
        ProviderAdaptor<VMSGatewayVideoExtractionRequest, VMSGatewayEvidenceProvider> {

    private static Logger log = Logger.getLogger(VMSGatewayVideoExtractionCommand.class);
    private Date date;
    private String vmsGatewayCallback;
    private SchedulerManager schedulerManager;

    public VMSGatewayVideoExtractionCommand() {
        if (vmsGatewayCallback == null) {
            Properties prop = null;
            ClassPathResource res = new ClassPathResource("system.properties");
            try {
                prop = new Properties();
                prop.load(res.getInputStream());
                vmsGatewayCallback = prop.getProperty("VMSGatewayCallback");
            } catch (IOException e) {
                log.error("[VMSGatewayCallback]", e);
            }
        }
    }

    public void execute(VMSGatewayVideoExtractionRequest videoER, VMSGatewayEvidenceProvider evProv, Date date)
            throws ScopixException {
        this.date = date;
        prepareEvidence(videoER, evProv);
    }

    @Override
    public void prepareEvidence(VMSGatewayVideoExtractionRequest evidenceExtractionRequest,
            VMSGatewayEvidenceProvider evidenceProvider) throws ScopixException {
        log.info("start");
        long startUTCInSeconds = 0;
        long stopUTCInSeconds = 0;

        try {
            Calendar dateEjecution = Calendar.getInstance();

            if (date != null) {
                dateEjecution.setTime(date);
            }

            // se utiliza creationTimestamp cuando está presente para solicitar evidencia del día que corresponde
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

            startUTCInSeconds = dateEjecution.getTimeInMillis() / 1000;
            log.debug("startUTC: " + startUTCInSeconds);

            stopUTCInSeconds = startUTCInSeconds + (evidenceExtractionRequest.getLengthInSecs());
            log.debug("stopUTC: " + stopUTCInSeconds);

            //verificamos que no exista la evidencia
            EvidenceFileDAO dao = SpringSupport.getInstance().findBeanByClassName(EvidenceFileDAO.class);

            //recuperamos la evidencia para el evidenceExtractionRequest y fecha de solicitud
            EvidenceFile evidenceFile = dao.findEvidenceFileByEERAndDate(
                    evidenceExtractionRequest.getId(), dateEjecution.getTime());

            if (evidenceFile == null) {
                //register evidence file
                evidenceFile = new EvidenceFile();
                evidenceFile.setEvidenceDate(new Date(evidenceDate.getTimeInMillis()));
                evidenceFile.setEvidenceExtractionRequest(evidenceExtractionRequest);
            } else {
                //le cambiamos la fecha de creacion del file
                evidenceFile.setFileCreationDate(null);
            }
            HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);

            String vmsGatewayIP = evidenceProvider.getIpAddress();
            String source = evidenceProvider.getProvider();
            String sourceType = evidenceProvider.getProviderType();
            String notifyURL = vmsGatewayCallback + "spring/vmsgatewayvideofileready";
            String protocol = evidenceProvider.getProtocol();
            String port = evidenceProvider.getPort();

            String urlStr = protocol + "://" + vmsGatewayIP + ":" + port + "/vmsgateway/createFile.php?"
                    + "source=" + source
                    + "&sourceType=" + sourceType
                    + "&evidenceFileId=" + evidenceFile.getId()
                    + "&startUTC=" + startUTCInSeconds
                    + "&stopUTC=" + stopUTCInSeconds
                    + "&notifyURL=" + notifyURL;

            log.debug("vmsGateway().urlString: " + urlStr);
            CloseableHttpResponse response = null;
            HttpSupport httpSupport;
            try {
                httpSupport = HttpSupport.getInstance();
            } catch (HttpClientInitializationException e) {
                throw new ScopixException("Cannot initialize Http Support.", e);
            }

            try {
                //this.allowCertificatesHTTPSConnections();
                HashMap<String, String> requestHeaders = new HashMap<String, String>();

                response = httpSupport.httpGet(urlStr, requestHeaders);

                long contentlength = 0;
                if (response != null && response.getEntity() != null) {
                    contentlength = response.getEntity().getContentLength();

                    BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        //verificar que se hace
                        log.debug(inputLine);
                    }
                    in.close();
                }

                log.debug("contentlength: " + contentlength);
            } catch (IOException e) {
                throw new ScopixException("Cannot create image.", e);
            } catch (HttpGetException e) {
                throw new ScopixException("Error fetching the image.", e);
            } finally {
                if (response != null) {
                    log.debug("close connectons");
                    httpSupport.closeHttpEntity(response.getEntity());
                    httpSupport.closeHttpResponse(response);
                }
            }
            log.debug("_vmsGateway()");
        } catch (ParseException e) {
            throw new ScopixException("Cannot create image.", e);
        }

        log.info("end");
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

//    private void allowCertificatesHTTPSConnections() {
//        try {
//            TrustManager[] trustAllCerts = new TrustManager[]{
//                new X509TrustManager() {
//
//                    @Override
//                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                        return null;
//                    }
//
//                    @Override
//                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
//                    }
//
//                    @Override
//                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
//                    }
//                }};
//            // Install the all-trusting trust manager
//            SSLContext sc = SSLContext.getInstance("SSL");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//
//            HostnameVerifier allHostsValid = new HostnameVerifier() {
//
//                @Override
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//                }
//            };
//
//            // Install the all-trusting host verifier
//            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
//        } catch (Exception e) {
//            log.error("Error when try to set the defaults certificates handlers", e);
//        }
//    }
}
