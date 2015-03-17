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
 *  NextLevel.java
 * 
 *  Created on 29-02-2012, 05:43:52 PM
 * 
 */
package com.scopix.periscope.nextlevel.utilities;

import com.scopix.periscope.nextlevel.NextLevelManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class NextLevel {

    private static Logger log = Logger.getLogger(NextLevel.class);
    private String domain;
    private String user;
    private String pwd;
    private boolean logged;
    private Integer httpsPort;
    private NextLevelManager manager;

    public NextLevel(String value, String userName, String pass) {
        domain = value;
        user = userName;
        pwd = pass;
        try {
            URL aURL = new URL(domain);
            httpsPort = aURL.getPort();
        } catch (MalformedURLException e) {
            log.error("No se recibe puerto en solicitud " + domain);
            httpsPort = 1;
        }
    }

    public String gatewayLogon(String storeName) {
        log.info("start");

        String url = getDomain() + "/api/nlssgateway/v1/DeviceCommands/authenticate";
        String data = "<Person xmlns=\"http://www.nlss.com/Gateway\">"
                + "<userID>" + user + "</userID>"
                + "<password>" + pwd + "</password>"
                + "</Person>";
        setLogged(true);
        String ret = null;
        try {

            ret = getManager().post(url, data, storeName, getHttpsPort(), getDomain(), getUser(), getPwd());
        } catch (ScopixException e) {
            log.warn("Revisar Connexion " + e);
            return ret;
        }
        log.info("end");
        this.setUser(user);
        this.setPwd(pwd);
        if (ret != null && ret.length() > 0) {
            setLogged(true);
        } else {
            //si no vuelve nada aseguramos que no se esta conectado
            setLogged(false);
        }
        return ret;
    }

    public void getCameraList(String storeName, String user, String pwd) {
        log.info("start");
        String url = getDomain() + "/api/nlssgateway/v1/Camera";
        getManager().get(url, storeName, getHttpsPort(), getDomain(), getUser(), getPwd());
        log.info("end");
    }

    public void getCameraStreamInfo(String deviceID, String storeName) {
        log.info("start");
        String url = getDomain() + "/api/nlssgateway/v1/StreamingChannel/deviceID/" + deviceID;
        getManager().get(url, storeName, getHttpsPort(), getDomain(), getUser(), getPwd());
        log.info("end");
    }

    public void getCameraStreamingVideo(String deviceID, String storeName) {
        log.info("start");
        String url = getDomain() + "/api/nlssgateway/v1/StreamingChannelVideo/deviceID/" + deviceID + "/channelID/0";
        getManager().get(url, storeName, getHttpsPort(), getDomain(), getUser(), getPwd());
        log.info("end");
    }

    public void generateClip(String deviceID, long startTime, long endTime, String storeName) {
        log.info("start");
        //String urlGenClip,
        String url = getDomain() + "/api/nlssgateway/v1/DeviceCommands/outboundCommand";
        // channelID es 0 para las camaras axis
        String data = "<NLSSCommands xmlns=\"http://www.nlss.com/NLSSCommands\">"
                + "<commandType>RaCMExportClip</commandType>"
                + "<targetID>" + deviceID + "</targetID>"
                + "<payload><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "     <RaCMExportClipRequest xmlns=\"http://www.nlss.com/NLSSCommands\">"
                + "         <channelID>0</channelID>"
                + "         <fileName>" + deviceID + "</fileName>"
                + "         <startTime>" + startTime + "</startTime>"
                + "         <stopTime>" + endTime + "</stopTime>"
                + "         <sessionID>" + UUID.randomUUID() + "</sessionID>"
                + "     </RaCMExportClipRequest>]]>"
                + "</payload>"
                + "</NLSSCommands>";

        try {
            getManager().post(url, data, storeName, getHttpsPort(), getDomain(), getUser(), getPwd());
        } catch (ScopixException e) {
            log.error("No es posible solicitar archivo " + e, e);
        }
        log.info("end");
    }

    public File getFile(String urlGetFile, String nameFile, String storeName) {
        log.info("start");
        String url = urlGetFile + "/nlss/images/clips/" + nameFile;
        File tmp = getManager().getFile(url, storeName, getHttpsPort());
        log.info("end file length " + tmp != null ? tmp.length() : "null");
        return tmp;
    }

    private NextLevelManager getManager() {
        if (manager == null) {
            manager = SpringSupport.getInstance().findBeanByClassName(NextLevelManager.class);
        }
        return manager;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public File getClip(String file, String storeName) {
        return getFile(domain, file, storeName);
    }

    public Integer getHttpsPort() {
        return httpsPort;
    }

    public void setHttpsPort(Integer httpsPort) {
        this.httpsPort = httpsPort;
    }

    /**
     * @return the logged
     */
    public boolean isLogged() {
        return logged;
    }

    /**
     * @param logged the logged to set
     */
    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}