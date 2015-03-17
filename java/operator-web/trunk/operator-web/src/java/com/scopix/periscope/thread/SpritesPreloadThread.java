/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * SpritesPreloadThread.java
 * 
 * Created on 22/08/2014
 */
package com.scopix.periscope.thread;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;

import com.scopix.periscope.model.Client;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.transport.http.HTTPConduit;

public class SpritesPreloadThread implements Runnable {

    private Client cliente;
    private String videoNames;
    private WebClient serviceClient;
    private static final Logger log = Logger.getLogger(SpritesPreloadThread.class);

    public void init(String videoNames) {
        log.info("start, videoNames: [" + videoNames + "]");
        setVideoNames(videoNames);
        cliente = (Client) Sessions.getCurrent().getAttribute("CLIENT");

        serviceClient = WebClient.create(cliente.getOperatorImgPrivateServicesURL());
                //new code
        HTTPConduit conduit = WebClient.getConfig(serviceClient).getHttpConduit();
        TLSClientParameters params
                = conduit.getTlsClientParameters();

        if (params == null) {
            params = new TLSClientParameters();
            conduit.setTlsClientParameters(params);
        }

        params.setTrustManagers(new TrustManager[]{new X509TrustManager() {

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
        }
        });

        params.setDisableCNCheck(true);
        // end new code
        serviceClient.path("/getSpritesURLs").accept(MediaType.APPLICATION_JSON_TYPE).type(MediaType.APPLICATION_JSON_TYPE);
        log.info("end");
    }

    @Override
    public void run() {
        log.info("start, videoNames: [" + getVideoNames() + "]");
        try {
            String spritesURLs = serviceClient.post(getVideoNames(), String.class);
            log.debug("spritesURLs: [" + spritesURLs + "]");

            if (spritesURLs != null && !"".equals(spritesURLs.trim())) {
                Clients.evalJavaScript("precargarImagenes('" + spritesURLs + "');");
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("end");
    }

    public String getVideoNames() {
        return videoNames;
    }

    public void setVideoNames(String videoNames) {
        this.videoNames = videoNames;
    }
}