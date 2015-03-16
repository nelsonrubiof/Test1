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
 *  VadaroPeopleCountingExtractionCommand.java
 * 
 *  Created on 21-04-2014, 18:08:00 PM
 * 
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.exception.HttpClientInitializationException;
import com.scopix.periscope.exception.HttpGetException;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.VadaroPeopleCountingEvidenceProvider;
import com.scopix.periscope.extractionmanagement.VadaroPeopleCountingExtractionRequest;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.http.HttpSupport;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.utilities.ScopixUtilities;

/**
 * Integración people-counting para el provider Vadaro
 * @author Carlos
 */
public class VadaroPeopleCountingExtractionCommand 
	implements ProviderAdaptor<VadaroPeopleCountingExtractionRequest, VadaroPeopleCountingEvidenceProvider> {
	
	private Date date;
	private static Logger log = Logger.getLogger(VadaroPeopleCountingExtractionCommand.class);

	/**
	 * Ejecuta comando de extracción
	 * 
	 * @param  evExtractionRequest
	 * @param  evidenceProvider
	 * @param  date
	 * @throws ScopixException
	 */
    public void execute(VadaroPeopleCountingExtractionRequest evExtractionRequest, 
    		VadaroPeopleCountingEvidenceProvider evidenceProvider, Date date) throws ScopixException {

    	log.info("start");
        this.date = date;
        prepareEvidence(evExtractionRequest, evidenceProvider);
        log.info("end");
    }

	/**
	 * Prepara extracción de evidencia
	 * 
	 * @param  evExtractionRequest
	 * @param  evidenceProvider
	 * @throws ScopixException
	 */
	@Override
    public void prepareEvidence(VadaroPeopleCountingExtractionRequest evExtractionRequest, 
    		VadaroPeopleCountingEvidenceProvider evidenceProvider) throws ScopixException {

    	log.info("start, evidenceExtractionRequest.getId(): ["+evExtractionRequest.getId()+"]");
    	try {
			//calcula la fecha de evidencia de acuerdo al tipo de generación
			Date evidenceDate = ScopixUtilities.calculateEvidenceDate(date, evExtractionRequest);
			log.debug("evidenceDate: ["+evidenceDate+"]");
			
            //nombre del archivo
            String fileName = DateFormatUtils.format(evidenceDate.getTime(), "yyyyMMdd");
            fileName = fileName + "_" + evExtractionRequest.getId() + ".xml";
			
            //registra evidenceFile en base de datos
            log.debug("fileName: [" + fileName + "]");
            EvidenceFileDAO dao = HibernateSupport.getInstance().findDao(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.registerEvidenceFile(fileName, evExtractionRequest, evidenceDate);

			//obtiene parámetros de la cámara
			String port = evidenceProvider.getPort();
			String user = evidenceProvider.getUserName();
			String password = evidenceProvider.getPassword();
			String protocol = evidenceProvider.getProtocol();
			String serverIP = evidenceProvider.getIpAddress();

			String strFromDate = fileName.substring(0, 8); //ej: 20140406_2638358.xml
			Date fromDate = DateUtils.parseDate(strFromDate, new String[]{"yyyyMMdd"});

			Calendar currentDate = Calendar.getInstance();
			double diff = TimeZoneUtils.getDiffHourTimezoneFromServer("GMT0");

			Date gmtDate = DateUtils.addHours(currentDate.getTime(), (int)diff);
			Calendar calGmt = Calendar.getInstance();
			calGmt.setTime(gmtDate);

			int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
			int gmtDay = calGmt.get(Calendar.DAY_OF_MONTH);

			int addDays = 1;
			if(currentDay-gmtDay!=0){
				addDays = 2;
			}
			log.debug("currentDate: ["+currentDate.getTime()+"], calGmt: ["+calGmt.getTime()+"], "
					+ "currentDay: ["+currentDay+"], gmtDay: ["+gmtDay+"], addDays: ["+addDays+"]");

			Date toDate = DateUtils.addDays(fromDate, addDays);
			String strToDate = DateFormatUtils.format(toDate, "yyyyMMdd");

			String from = strFromDate + "00";
			String to   = strToDate   + "00";

			//ej: http://localhost:28081/cgi-bin/report.xml?from=2014040600&to=2014040700
			String url = protocol + "://" + serverIP + ":" + port + "/cgi-bin/report.xml?from="+from+"&to="+to;
			//invoca conteo de la correspondiente cámara
			String timezoneId = evExtractionRequest.getExtractionPlan().getTimeZoneId();
			callVadaroPeopleCounting(url, user, password, evidenceFile, fileName, timezoneId);

    	} catch (ParseException e) {
            throw new ScopixException(e.getMessage(), e);
        }
    	log.info("end");
    }
    
    /**
     * Invoca conteo de la correspondiente cámara
     * 
     * @param  url
     * @param  user
     * @param  password
     * @param  evidenceFile
     * @param  fileName
     * @param  timezoneId
     * @throws ScopixException
     */
    private void callVadaroPeopleCounting(String url, String user, 
    		String password, EvidenceFile evidenceFile, String fileName, String timezoneId) throws ScopixException {

    	log.info("start, URL: [" + url + "], fileName: [" + fileName + "]");
    	HttpEntity httpEntity = null;
    	HttpSupport httpSupport = null;
        CloseableHttpResponse httpResponse = null;
        HashMap<String, String> requestHeaders = null;

        try {
			httpSupport = HttpSupport.getInstance();
		} catch (HttpClientInitializationException e) {
			 throw new ScopixException("Error obteniendo instancia de httpSupport", e);
		}

        try {
            if (!"NOT_USED".equalsIgnoreCase(user) && !"NOT_USED".equalsIgnoreCase(password)) {
            	String toEncode = user+":"+password;
            	String encoding = Base64.encodeBase64String(toEncode.getBytes());
            	
            	requestHeaders = new HashMap<String, String>();
            	requestHeaders.put("Authorization", "Basic " + encoding);
            }

			//realiza petición GET
			httpResponse = httpSupport.httpGet(url, requestHeaders);

			if(httpResponse != null && httpResponse.getEntity() != null){
			    httpEntity = httpResponse.getEntity();
				int statusCode = httpResponse.getStatusLine().getStatusCode();
			    log.debug("httpResponse status code: [" + statusCode + "]");
			    
			    if(statusCode != 200){
			    	throw new ScopixException("error en peticion de conteo, statusCode: ["+statusCode+"], URL: ["+url+"]");
			    }
			    //obtiene contenido retornado
			    String xmlContent = EntityUtils.toString(httpEntity);
			    
			    if(xmlContent==null || xmlContent.trim().equals("")){
			    	throw new ScopixException("No se existe contenido en respuesta XML, URL: ["+url+"]");
			    }

			    //invoca comando para generación de archivo XML formato scopix
			    VadaroPeopleCountingFileReadyCommand fileReadyCommand = new VadaroPeopleCountingFileReadyCommand();
			    fileReadyCommand.execute(xmlContent, evidenceFile, fileName, timezoneId);

			}else{
				throw new ScopixException("no hubo respuesta de conteo, URL: ["+url+"]");
			}
		} catch (org.apache.http.ParseException e) {
			throw new ScopixException(e.getMessage(), e);
		} catch (HttpGetException e) {
			throw new ScopixException(e.getMessage(), e);
		} catch (IOException e) {
			throw new ScopixException(e.getMessage(), e);
		} finally {
            if (httpResponse != null) {
                httpSupport.closeHttpEntity(httpEntity);
                httpSupport.closeHttpResponse(httpResponse);
            }
        }
    	log.info("end");
    }
}