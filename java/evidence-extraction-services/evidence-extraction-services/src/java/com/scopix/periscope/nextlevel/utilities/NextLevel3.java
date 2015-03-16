package com.scopix.periscope.nextlevel.utilities;

import com.scopix.periscope.nextlevel.NextLevelManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import org.apache.log4j.Logger;

/*
 * 
 * Copyright (c) 2012, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 * 
 * Clase que ejecuta las operaciones para la nueva versión
 * de integración entre EES y NextLevel
 *
 * @author    carlos polo
 * @created   19-oct-2012
 *
 */
public class NextLevel3 {

    private String domain;
    private String user;
    private String pwd;
    private Integer httpsPort;
    private boolean logged;
    private NextLevelManager manager;
    private static Logger log = Logger.getLogger(NextLevel3.class);
    private static final String PROPERTIES_NEXTLEVEL3_STREAMPROFILE = "nextLevel3.streamProfile";
    private static final String PROPERTIES_NEXTLEVEL3_EXPORTPROFILE = "nextLevel3.exportProfile";

    public NextLevel3(String value, String userName, String pass) {
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

    /**
     * Ejecuta operación de autenticación con NextLevel para la nueva versión
     *
     * @author carlos polo
     * @param storeName nombre del store
     * @version 3.0
     * @return String xmlResponse
     * @date 19-oct-2012
     */
    public synchronized String gatewayLogon(String storeName) {
        log.info("Inicio gatewayLogon");
        String ret = null;

        log.debug("isLogged: " + isLogged());

        if (!isLogged()) {
            //En el PDF en el ejemplo no muestran al final del URL la parte /content, pero en la especificación sí.
            //Se implementó como está en la especificación del API
            String url = domain + "/api/nlssgateway/v1/DeviceCommands/authenticate/content";
            String data = "<User>"
                    + "<userID>" + user + "</userID>"
                    + "<password>" + pwd + "</password>"
                    + "</User>";

            try {
                //Ejecuta operación
                setLogged(true);
                ret = getManager().postNextLevel3(url, data, storeName, getHttpsPort(), getDomain(), getUser(), getPwd());
            } catch (ScopixException e) {
                log.warn("Revisar Conexión " + e);
                return ret;
            }

            log.info("end");
            this.setUser(user);
            this.setPwd(pwd);

            if (ret != null && ret.length() > 0) {
                setLogged(true);
            } else {
                //Si no devuelve nada establece que no está conectado
                setLogged(false);
            }
        } else {
            ret = "_"; //Si ya se encuentra logged, se llena la variable para que en el NextLevelManager.loginGatewayNextLevel3 
            //no vuelva a pedir autenticación
        }

        return ret;
    }

    /**
     * Ejecuta operación para obtener un video exportado con NextLevel para la nueva versión
     *
     * @author carlos polo
     * @param deviceID
     * @param startTime
     * @param endTime
     * @param storeName
     * @param userId
     * @version 3.0
     * @date 19-oct-2012
     */
    public void generateClip(String deviceID, long startTime, long endTime, String storeName, String userId) {
        log.info("Inicio generateClip");

        String channelId = "0";
        String fileDesc = "";
        String rvsEnabled = "0";
        String notes = "";
        String audioEnabled = "1";
        String streamProfile = getManager().getExtractionManager().getStringProperties(PROPERTIES_NEXTLEVEL3_STREAMPROFILE);
        String exportProfile = getManager().getExtractionManager().getStringProperties(PROPERTIES_NEXTLEVEL3_EXPORTPROFILE);

        String url = getDomain() + "/api/nlssgateway/v1/DeviceCommands/outboundCommand";
        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        strBuilder.append("<NLSSCommands xmlns=\"http://www.nlss.com/NLSSCommands\">");
        strBuilder.append("<commandType>RaCMExportClip</commandType>");
        strBuilder.append("<targetID>").append(deviceID).append("</targetID>");
        strBuilder.append("<payload>");
        strBuilder.append("<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        strBuilder.append("<RaCMExportClipRequest xmlns=\"http://www.nlss.com/NLSSCommands\">");
        strBuilder.append("<channelID>").append(channelId).append("</channelID>");
        strBuilder.append("<fileName>").append(deviceID).append("</fileName>");
        strBuilder.append("<fileDescription>").append(fileDesc).append("</fileDescription>");
        strBuilder.append("<userID>").append(userId).append("</userID>");
        strBuilder.append("<username>").append(userId).append("</username>");
        strBuilder.append("<streamProfile>").append(streamProfile).append("</streamProfile>");
        strBuilder.append("<exportProfile>").append(exportProfile).append("</exportProfile>");
        strBuilder.append("<RVSEnabled>").append(rvsEnabled).append("</RVSEnabled>");
        strBuilder.append("<notes>").append(notes).append("</notes>");
        strBuilder.append("<startTime>").append(startTime).append("</startTime>");
        strBuilder.append("<stopTime>").append(endTime).append("</stopTime>");
        strBuilder.append("<sessionID>").append(UUID.randomUUID()).append("</sessionID>");
        strBuilder.append("<audioEnabled>").append(audioEnabled).append("</audioEnabled>");
        strBuilder.append("</RaCMExportClipRequest>]]>");
        strBuilder.append("</payload>");
        strBuilder.append("</NLSSCommands>");

        try {
            getManager().postNextLevel3(url, strBuilder.toString(), storeName, getHttpsPort(), getDomain(), getUser(), getPwd());

        } catch (ScopixException e) {
            log.error("No es posible solicitar archivo " + e, e);
        }
        log.info("Fin generateClip");
    }

    public File getClip(String file, String storeName) {
        return getFile(domain, file, storeName);
    }

    public File getFile(String urlGetFile, String nameFile, String storeName) {
        log.info("Inicio getFile");
        String url = urlGetFile + "/nlss/images/clips/" + nameFile;
        File tmp = getManager().getNextLevel3File(url, storeName, getHttpsPort());
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