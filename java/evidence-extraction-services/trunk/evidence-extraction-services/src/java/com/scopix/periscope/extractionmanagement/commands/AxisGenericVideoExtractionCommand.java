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
 * AxisP3301ImageExtractionCommand.java
 *
 * Created on 16-08-2010, 04:25:15 PM
 *
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.exception.HttpClientInitializationException;
import com.scopix.periscope.exception.HttpGetException;
import com.scopix.periscope.extractionmanagement.AxisGenericEvidenceProvider;
import com.scopix.periscope.extractionmanagement.AxisGenericVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
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
public class AxisGenericVideoExtractionCommand implements
        ProviderAdaptor<AxisGenericVideoExtractionRequest, AxisGenericEvidenceProvider> {

    private static Logger log = Logger.getLogger(AxisGenericVideoExtractionCommand.class);
    private Date date;
    private AxisGenericVideoFileReadyCommand fileReadyCommand;
    private static final String EXTENSION = ".mpeg";
    private SchedulerManager schedulerManager;

    public void execute(AxisGenericVideoExtractionRequest extractionRequest, AxisGenericEvidenceProvider evidenceProvider,
            Date date) throws ScopixException {
        this.date = date;
        prepareEvidence(extractionRequest, evidenceProvider);
    }

    @Override
    public void prepareEvidence(AxisGenericVideoExtractionRequest evidenceRequest, AxisGenericEvidenceProvider evidenceProvider)
            throws ScopixException {
        log.info("start");
        try {
            long startUTC = 0;

            Calendar dateEjecution = Calendar.getInstance();
            if (date != null) {
                dateEjecution.setTime(date);
            }

            // se utiliza creationTimestamp cuando está presente para solicitar evidencia del día que corresponde
            // en evidencia Auto_Generada
            if (EvidenceRequestType.AUTO_GENERATED.equals(evidenceRequest.getType())
                    && evidenceRequest.getCreationTimestamp() != null) {
                dateEjecution.setTime(evidenceRequest.getEvidenceDate()); //getCreationTimestamp()
            }

            /**
             * Si existe un TimeZone debemos calcular que hora es en el destino es decir de donde se recupera la imagen
             */
            //if (getSchedulerManager().getTimeZoneId() != null && getSchedulerManager().getTimeZoneId().length() > 0) {
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
            startUTC = dateEjecution.getTimeInMillis();
            log.debug("startUTC: " + startUTC);

            // the name of the clip must be in local time
            String name = DateFormatUtils.format(dateEjecution.getTime(), "yyyyMMdd");
            name = name + "_" + evidenceRequest.getId();

            EvidenceFileDAO dao = SpringSupport.getInstance().findBeanByClassName(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.findEvidenceFileByFileName(name + EXTENSION);
            if (evidenceFile != null) {
                evidenceFile.setFileCreationDate(new Date());
                HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            } else {
                evidenceFile = new EvidenceFile();
                evidenceFile.setEvidenceDate(new Date(evidenceDate.getTimeInMillis()));
                evidenceFile.setEvidenceExtractionRequest(evidenceRequest);
                evidenceFile.setFilename(name + EXTENSION);
                HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            }
            callAxisGeneric(name, evidenceProvider, evidenceRequest);
        } catch (Exception e) {
            throw new ScopixException(e.getMessage(), e);
        }
        log.info("end");
    }

    private void callAxisGeneric(String name, AxisGenericEvidenceProvider evidenceProvider,
            AxisGenericVideoExtractionRequest evidenceRequest) throws ScopixException {

    	 String protocol = evidenceProvider.getProtocol();
         String serverIP = evidenceProvider.getIpAddress();
         String port = evidenceProvider.getPort();
         String resolution = evidenceProvider.getResolution();
         Integer fps = evidenceProvider.getFramerate();
         Integer duration = evidenceRequest.getLengthInSecs();

         //Llamada de la forma:
         //http://64.81.251.106:5555/axis-cgi/mjpg/video.cgi?resolution=640x480&duration=600&fps=2
         String urlString = protocol + "://" + serverIP + ":" + port + "/axis-cgi/mjpg/video.cgi?"
                 + "resolution=" + resolution
                 + "&duration=" + duration
                 + "&fps=" + fps;
    	
        log.debug("callAxisGeneric().urlString: " + urlString);
        CloseableHttpResponse response =  null;
        HttpSupport httpSupport;
		try {
			httpSupport = HttpSupport.getInstance();
		} catch (HttpClientInitializationException e) {
			 throw new ScopixException("Cannot initialize Http Support.", e);
		}

        try {
            this.allowCertificatesHTTPSConnections();
            HashMap<String, String> requestHeaders = new HashMap<String, String>();
            
            if (!evidenceProvider.getUserName().equals("NOT_USED") && !evidenceProvider.getPassword().equals("NOT_USED")) {
                String encoding = new sun.misc.BASE64Encoder().encode((evidenceProvider.getUserName() + ":" + evidenceProvider.getPassword()).getBytes());
                
                requestHeaders.put("Authorization", "Basic " + encoding);
            }
            response = httpSupport.httpGet(urlString, requestHeaders);

            long contentlength = 0;
            if (response != null && response.getEntity() != null){
            	
            	InputStream is = response.getEntity().getContent();
            	contentlength = response.getEntity().getContentLength();
                is.available();
                File tmp = generateFileTemp(is);
                is.close();
                if (tmp == null) {
                    log.error("no es posible generar archivo desde " + urlString);
                    throw new ScopixException("Error Generando Archivo desde " + urlString);
                }
                int numFrames = getNumFrames(tmp);
                double fpsMpeg = (double) numFrames / (double) duration;
                getFileReadyCommand().execute(name, tmp, fpsMpeg, numFrames, resolution);
                try {
                    //antes de terminar eliminamos el archivo temporal
                    FileUtils.forceDelete(tmp);
                } catch (IOException e1) {
                    log.warn("no es posible eliminar archivo " + e1);
                }
            	
            }

            log.debug("contentlength: " + contentlength);
        } catch (IOException e) {
            throw new ScopixException("Cannot create image.", e);
		} catch (HttpGetException e) {
			throw new ScopixException("Error fetching the image.", e);
		}finally{
			if (response != null){
				 httpSupport.closeHttpEntity(response.getEntity());
			     httpSupport.closeHttpResponse(response);
			}
		}
        log.debug("_callAxisGeneric()");
    
    }

    private void allowCertificatesHTTPSConnections() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {

                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }};
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = new HostnameVerifier() {

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

    public AxisGenericVideoFileReadyCommand getFileReadyCommand() {
        if (fileReadyCommand == null) {
            fileReadyCommand = new AxisGenericVideoFileReadyCommand();
        }
        return fileReadyCommand;
    }

    public void setFileReadyCommand(AxisGenericVideoFileReadyCommand fileReadyCommand) {
        this.fileReadyCommand = fileReadyCommand;
    }

    private File generateFileTemp(InputStream inputStream) {
        File tmp = null;
        log.info("start");
        try {
            tmp = File.createTempFile("motion_jpeg", ".scpx");
            OutputStream out = new FileOutputStream(tmp);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
        } catch (IOException e) {
            log.error("Error " + e, e);
            tmp = null;
        }
        log.info("end");
        return tmp;
    }

    private int getNumFrames(File tmp) {
        log.info("start");
        int countFiles = 0;
        try {
            InputStream inputStream = new FileInputStream(tmp);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(inputStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                if (strLine.equals("--myboundary")) {
                    countFiles++;
                }
            }
            in.close();
        } catch (IOException e) {
            log.error("Error: " + e, e);
        }
        log.info("end");
        return countFiles;
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
}
