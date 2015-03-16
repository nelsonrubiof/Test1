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
 * BroadwareImageExtractionThread.java
 *
 * Created on 15-10-2009, 12:21:00 PM
 *
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;

import com.scopix.periscope.exception.HttpClientInitializationException;
import com.scopix.periscope.exception.HttpGetException;
import com.scopix.periscope.http.HttpSupport;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 *
 * @author cesar.abarza
 */
public class BroadwareImageExtractionThread extends ExtractEvidenceThread {

    private static Logger log = Logger.getLogger(BroadwareImageExtractionThread.class);
    private String extractURL;
    private boolean initialized;

    public void init(String extractURL) throws ScopixException {
        log.debug("init(). extractURL :" + extractURL);
        this.extractURL = extractURL;
        this.setName(this.getClass().getSimpleName());
        initialized = true;
    }

    @SuppressWarnings("static-access")
    public void run() {
        log.info("start");
        try {
            if (initialized) {
                try {
                    Thread.currentThread().sleep(5000);
                    callBroadware(extractURL);

                } catch (ScopixException ex) {
                    log.error("Error calling Broadware.", ex);
                } catch (InterruptedException ex) {
                    log.error("Error Interrupting the Thread.", ex);
                }

            } else {
                log.debug("Thread not initialized. Call to init() before run()");
                throw new RuntimeException("Thread not initialized. Call to init() before run()");
            }
        } catch (Exception e) {
            log.error("No es posible terminar la ejecucion " + e, e);
        }
        log.info("end");
    }

    private void callBroadware(String urlString) throws ScopixException {
    	log.debug("callBroadwareImage().urlString: " + urlString);
        CloseableHttpResponse response =  null;
        HttpSupport httpSupport;
        
        try {
			httpSupport = HttpSupport.getInstance();
		} catch (HttpClientInitializationException e) {
			 throw new ScopixException("Cannot initialize Http Support.", e);
		}
        
    	try {
    		response = httpSupport.httpGet(urlString, new HashMap<String, String>());
            
            int responseCode = response.getStatusLine().getStatusCode();
            long contentlength = 0;
            
            if (response != null && response.getEntity() != null){
            	contentlength = response.getEntity().getContentLength();
            	BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            	String inputLine;
            	
            	while ((inputLine = in.readLine()) != null) {
            		log.debug("inputLine: " + inputLine);
            	}
            	in.close();
            }
            log.debug("contentlength: " + contentlength + " responseCode" + responseCode);

        } catch (IOException ex) {
            throw new ScopixException("Cannot create clip.", ex);
        } catch (HttpGetException e) {
        	throw new ScopixException("Cannot get clip.", e);
		}finally{
			if (response != null){
				 httpSupport.closeHttpEntity(response.getEntity());
			     httpSupport.closeHttpResponse(response);
			}
		}
    }
}
