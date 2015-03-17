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
 *  VsomServices.java
 * 
 *  Created on 08-10-2013, 02:37:45 PM
 * 
 */
package com.scopix.periscope.cisco7;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scopix.periscope.cisco7.Vsm7ApiPojos.BaseRef;
import com.scopix.periscope.cisco7.Vsm7ApiPojos.Device;
import com.scopix.periscope.cisco7.Vsm7ApiPojos.LoginRequest;
import com.scopix.periscope.cisco7.Vsm7ApiPojos.LoginResponse;
import com.scopix.periscope.cisco7.Vsm7ApiPojos.PageResponse;
import com.scopix.periscope.cisco7.Vsm7ApiPojos.SecurityTokenResponse;
import com.scopix.periscope.cisco7.Vsm7ApiPojos.UmsResponse;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 *
 * @author Gustavo Alvarez
 * @modified Carlos Polo
 */
public class VsomServices {

    private Gson gson;
    private String domain;
    private int retryNumber;
    private String userName;
    private String password;
    private String hostName;
    private String vsomPort;
    private String urlPrefix;
    private Date lastVerifyDate;
    private String vsomProtocol;
    private String mediaServerPort;
    private String mediaServerProtocol;
    private Vsm7HttpSupport vmsHttpSupport;
    private static final String SUCCESS = "SUCCESS";
    private static final String PROGRESS = "progress";
    private static final String ALREADY_EXISTS = "already exists";
    public static final String CLIP_IN_PROGRESS = "ClipInProgress";
    private static Logger log = Logger.getLogger(VsomServices.class);
    public static final String CLIP_CREATED_FAILED = "ClipCreateFailed";
    public static final String CLIP_ALREADY_EXISTS = "ClipAlreadyExists";
    public static final String CLIP_CREATE_SUCCESS = "ClipCreateSuccess";
    public static final String CLIP_DOWNLOAD_FAILED = "ClipDownloadFailed";
    public static final String CLIP_DOWNLOAD_SUCCESS = "ClipDownloadSuccess";

    /**
     * Constructor de la clase, también realiza proceso de autenticación
     *
     * @param hostName
     * @param userName
     * @param password
     * @param domain
     * @param vsomProtocol
     * @param vsomPort
     * @param mediaServerProtocol
     * @param mediaServerPort
     * @param retryNumber
     * @throws ScopixException
     */
    public VsomServices(String hostName, String userName, String password, String domain, String vsomProtocol,
        String vsomPort, String mediaServerProtocol, String mediaServerPort, int retryNumber) throws ScopixException {

        log.info("start");
        gson = new GsonBuilder().create();
        vmsHttpSupport = new Vsm7HttpSupport();

        this.mediaServerPort = mediaServerPort;
        this.mediaServerProtocol = mediaServerProtocol;

        //invoca proceso de autenticación, en caso de fallo realiza reintentos de autenticación
        callAuthentication(hostName, userName, password, domain, vsomProtocol, vsomPort, retryNumber);
        log.info("end");
    }

    /**
     * Invoca proceso de autenticación, en caso de fallo realiza reintentos de autenticación
     *
     * @param hostName
     * @param userName
     * @param password
     * @param domain
     * @param vsomProtocol
     * @param vsomPort
     * @param retryNumber
     * @return
     * @throws ScopixException
     */
    private boolean callAuthentication(String hostName, String userName, String password,
        String domain, String vsomProtocol, String vsomPort, int retryNumber) throws ScopixException {

        log.info("start, hostName: [" + hostName + "], userName: [" + userName + "], "
            + "domain: [" + domain + "], vsomProtocol: [" + vsomProtocol + "], vsomPort: [" + vsomPort + "], retryNumber: [" + retryNumber + "]");

        this.domain = domain;
        this.userName = userName;
        this.password = password;
        this.hostName = hostName;
        this.vsomPort = vsomPort;
        boolean autenticado = false;
        this.retryNumber = retryNumber;
        this.vsomProtocol = vsomProtocol;

        try {
            //autentica con el correspondiente VSOM de Cisco7
            autenticado = authenticate(userName, password, domain, vsomProtocol, hostName, vsomPort);

            if (!autenticado) {
                log.debug("numero de intentos para autenticar: [" + retryNumber + "]");
                for (int i = 0; i < retryNumber; i++) {
                    autenticado = authenticate(userName, password, domain, vsomProtocol, hostName, vsomPort);
                    if (autenticado) {
                        break;
                    }
                }

                if (!autenticado) {
                    throw new ScopixException("Error en autenticacion, hostName: [" + hostName + "]");
                }
            }
        } catch (ScopixException e) {
            throw e;
        }
        log.info("end, autenticado: [" + autenticado + "]");
        return autenticado;
    }

    /**
     * Autentica con el VMS de Cisco7
     *
     * @param userName
     * @param password
     * @param domain
     * @param vsomProtocol
     * @param hostName
     * @param vsomPort
     * @return
     * @throws ScopixException
     */
    private boolean authenticate(String userName, String password,
        String domain, String vsomProtocol, String hostName, String vsomPort) throws ScopixException {

        boolean autenticado = false;
        log.info("start, username: [" + userName + "], domain: [" + domain + "], "
            + "vsomProtocol: [" + vsomProtocol + "], hostName: [" + hostName + "], vsomPort: [" + vsomPort + "]");

        try {
            //Login to VSM and receive Session ID (sid)
            LoginRequest loginRequest = new LoginRequest(userName, password, domain);
            StringEntity input = new StringEntity(gson.toJson(loginRequest));

            urlPrefix = vsomProtocol + "://" + hostName + ":" + vsomPort + "/ismserver/json/";
            String completeURL = urlPrefix + "authentication/login";
            log.debug("url de autenticacion: [" + completeURL + "]");

            String jsonResp = vmsHttpSupport.httpPost2File(completeURL, input, null, null);
            LoginResponse loginResponse = gson.fromJson(jsonResp, LoginResponse.class);

            if (loginResponse != null) {
                if (loginResponse.status.errorType.equals("FAILURE")) {
                    log.error("Error: [" + loginResponse.status.errorType + "]");
                } else {
                    //set session id
                    String sessionId = loginResponse.data.uid;
                    log.debug("sessionId: [" + sessionId + "]");
                    vmsHttpSupport.setSessionId(sessionId);
                    autenticado = true;
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new ScopixException(e.getMessage(), e);
        }
        log.info("end, autenticado: [" + autenticado + "]");
        return autenticado;
    }

    /**
     * Obtiene objeto device a partir del nombre del dispositivo
     *
     * @param byExactName
     * @return
     * @throws ScopixException
     */
    public Device getCameraByExactName(String byExactName) throws ScopixException {
        log.info("start, byExactName: [" + byExactName + "]");
        Device responseDevice = null;
        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("{\"filter\":{\"pageInfo\":{\"start\":0,\"limit\":100},").
            append("\"sortCriteria\":{\"name\":\"name\",\"sortOrder\":\"ASC\"},").
            append("\"byExactName\":\"").append(byExactName).append("\"}}");

        try {
            String completeURL = urlPrefix + "camera/v1_0/getCameras";
            log.debug("URL getCameraByExactName: [" + completeURL + "]");

            //verifica el tiempo transcurrido desde la última verificación de sesión del VSOM
            checkLastVerifySession();

            String jsonResp = vmsHttpSupport.httpPost2File(completeURL, new StringEntity(sBuilder.toString()), null, null);

            PageResponse getCameraResponse = gson.fromJson(jsonResp, PageResponse.class);
            if (getCameraResponse != null) {
                if (!getCameraResponse.status.errorType.equals("SUCCESS")) {
                    log.error("Error: [" + getCameraResponse.status.errorMsg + "]");
                } else {
                    // NB! That API return array/list, but we will look only for 1st
                    // camera in this example
                    if (getCameraResponse.data.totalRows != 1) {
                        log.debug("Error: cameraNameContains too vague, found: [" + getCameraResponse.data.totalRows + "] cameras");
                    } else {
                        Device cm = getCameraResponse.data.items[0];
                        log.debug("camera UID: [" + cm.uid + "]");
                        responseDevice = cm;
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
        	throw new ScopixException(e.getMessage(), e);
        }
        log.info("end, responseDevice: [" + responseDevice + "]");
        return responseDevice;
    }

    public String getUmsHostName(BaseRef baseRef) throws ScopixException {
        log.info("start");
        String response = null;

        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("{\"umsRef\":{" + "\"refUid\":\"").append(baseRef.refUid).
            append("\",\"refName\":\"").append(baseRef.refName).append("\",\"refObjectType\":\"device_vs_ums\"}}");

        try {
            String completeURL = urlPrefix + "mediaserver/v1_0/getUms";
            log.debug("URL getUmsHostName: [" + completeURL + "]");

            //verifica el tiempo transcurrido desde la última verificación de sesión del VSOM
            checkLastVerifySession();

            //We need hostname/IP address for VSMS server which manage that camera
            String jsonResp = vmsHttpSupport.httpPost2File(completeURL, new StringEntity(sBuilder.toString()), null, null);
            UmsResponse getUmsResponse = gson.fromJson(jsonResp, UmsResponse.class);

            if (getUmsResponse != null) {
                log.debug("UMS hostname: [" + getUmsResponse.data.deviceAccess.hostname_ip + "]");
                response = getUmsResponse.data.deviceAccess.hostname_ip;
            }
        } catch (UnsupportedEncodingException e) {
        	throw new ScopixException(e.getMessage(), e);
        }
        log.info("end, response: [" + response + "]");
        return response;
    }

    public String getSecurityToken(Device cm, int expire) throws ScopixException {
        log.info("start");
        String response = null;
        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("{\"cameraRef\":{").append("\"refUid\":\"").append(cm.uid).
            append("\",\"refName\":\"").append(cm.name).append("\",\"refObjectType\":\"").
            append(cm.objectType).append("\"},\"tokenExpiryInSecs\":\"").append(expire).append("\"}");

        try {
            String completeURL = urlPrefix + "camera/v1_0/getSecurityToken";
            log.debug("URL getSecurityToken: [" + completeURL + "]");

            //verifica el tiempo transcurrido desde la última verificación de sesión del VSOM
            checkLastVerifySession();

            //Get security token, expire time in seconds after time when this API was issued
            String jsonResp = vmsHttpSupport.httpPost2File(completeURL, new StringEntity(sBuilder.toString()), null, null);
            SecurityTokenResponse getSecurityTokenResponse = gson.fromJson(jsonResp, SecurityTokenResponse.class);

            if (getSecurityTokenResponse != null) {
                if (!getSecurityTokenResponse.status.errorType.equals("SUCCESS")) {
                    log.debug("Error: [" + getSecurityTokenResponse.status.errorMsg + "]");
                } else {
                    //Some media players require URL encoding of security token
                    String uriEncodedSecurityToken = URLEncoder.encode(getSecurityTokenResponse.data.toString(), "ISO-8859-1");
                    log.debug("Security Token: [" + uriEncodedSecurityToken + "]");
                    response = uriEncodedSecurityToken;
                }
            }
        } catch (UnsupportedEncodingException e) {
        	throw new ScopixException(e.getMessage(), e);
        }
        log.info("end, response: [" + response + "]");
        return response;
    }

    /**
     * Crea petición de generación de video en el VSOM
     *
     * @param camera
     * @param umsHostName
     * @param clipName
     * @param userName
     * @param startTimeSeconds
     * @param endTimeSeconds
     * @param securityToken
     * @return
     * @throws ScopixException
     */
    public String createClipRequest(Device camera,
        String umsHostName, String clipName, String userName,
        long startTimeSeconds, long endTimeSeconds, String securityToken) throws ScopixException {

        log.info("start, cameraName: [" + camera.name + "], cameraIP: [" + camera.deviceAccess.hostname_ip + "], "
            + "umsHostName: [" + umsHostName + "], clipName: [" + clipName + "]");
        String response = null;

        String userNameBase64Encoded;
        String clipNameBase64Encoded;
        String recordingName = getCameraRecordings(camera);

        userNameBase64Encoded = new String(Base64.encodeBase64(userName.getBytes()));
        clipNameBase64Encoded = new String(Base64.encodeBase64(clipName.getBytes()));

        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("<ClipRecordingRequest><ClipRecording><cameraUid>").append(camera.uid).
            append("</cameraUid><name>").append(clipNameBase64Encoded).append("</name><recording>").
            append(recordingName).append("</recording><startTime><seconds>").append(startTimeSeconds).
            append("</seconds><milliseconds>0</milliseconds></startTime><endTime><seconds>").append(endTimeSeconds).
            append("</seconds><milliseconds>0</milliseconds></endTime><type>local</type><local><format>mp4</format>").
            append("<audioRequired>false</audioRequired><requestor>").append(userNameBase64Encoded).
            append("</requestor></local></ClipRecording><Asynch>").
            append("<requestId>AXClient</requestId></Asynch></ClipRecordingRequest>");

        try {
            String lcUrl = mediaServerProtocol + "://" + umsHostName + ":"
                + mediaServerPort + "/ums/Recording/clipping/clip?token=" + securityToken;

            log.debug("URL createClipRequest: [" + lcUrl + "], camera.uid: [" + camera.uid + "], recordingName: [" + recordingName + "], "
                + "startTimeSeconds: [" + startTimeSeconds + "], endTimeSeconds: [" + endTimeSeconds + "]");

            //verifica el tiempo transcurrido desde la última verificación de sesión del VSOM
            checkLastVerifySession();

            String responseString = vmsHttpSupport.httpPost2File(lcUrl, new StringEntity(sBuilder.toString()), null, null);
            if (responseString != null) {
                if (responseString.contains(PROGRESS)) {
                    log.debug("Clip in progress-");
                    response = CLIP_IN_PROGRESS;
                } else if (responseString.contains(ALREADY_EXISTS)) {
                    log.debug("Clip already exists-");
                    response = CLIP_ALREADY_EXISTS;
                } else if (responseString.contains(SUCCESS)) {
                    log.debug("Clip Create issued - Success-");
                    response = CLIP_CREATE_SUCCESS;
                } else {
                    log.debug("Clip Create Failed: " + responseString);
                    response = CLIP_CREATED_FAILED;
                }
            } else {
                response = CLIP_CREATED_FAILED;
            }
        } catch (UnsupportedEncodingException e) {
        	throw new ScopixException(e.getMessage(), e);
        }
        log.info("end, response: [" + response + "]");
        return response;
    }

    public String getCameraRecordings(Device camera) throws ScopixException {
        log.info("start, cameraName: [" + camera.name + "], cameraIP: [" + camera.deviceAccess.hostname_ip + "]");
        String response = null;
        try {
            StringBuilder sBuilder = new StringBuilder();
            sBuilder.append("{\"deviceRef\":{" + "\"refUid\":\"").append(camera.uid).
                append("\",\"refName\":\"").append(camera.name).append("\",\"refObjectType\":\"device_vs_camera_ip\"}}");

            String completeURL = urlPrefix + "camera/v1_0/getRecordingsByCamera";
            log.debug("URL getCameraRecordings: [" + completeURL + "]");

            //verifica el tiempo transcurrido desde la última verificación de sesión del VSOM
            checkLastVerifySession();

            String jsonResp = vmsHttpSupport.httpPost2File(completeURL, new StringEntity(sBuilder.toString()), null, null);

            PageResponse getRecordingsResponse = gson.fromJson(jsonResp, PageResponse.class);
            if (getRecordingsResponse != null) {
                if (!getRecordingsResponse.status.errorType.equals(SUCCESS)) {
                    log.debug("Error: [" + getRecordingsResponse.status.errorMsg + "]");
                } else {
                    // NB! That API return array/list, but we will look only for 1st camera in this example
                    if (getRecordingsResponse.data.totalRows != 1) {
                        log.debug("Error: There is more than one Recording, found  ["
                            + getRecordingsResponse.data.totalRows + "] recordings,  will use 1st one,");
                    }
                    Device recordingStream1 = getRecordingsResponse.data.items[0];  // use 1st recording (hack)
                    log.debug("Recording name: [" + recordingStream1.name + "]");

                    response = recordingStream1.name;
                }
            }
        } catch (UnsupportedEncodingException e) {
        	throw new ScopixException(e.getMessage(), e);
        }
        log.info("end, response: [" + response + "]");
        return response;
    }

    /**
     * Descarga video solicitado
     *
     * @param umsHostName
     * @param clipName
     * @param userName
     * @param securityToken
     * @param filePath
     * @return
     * @throws ScopixException
     */
    public String downloadClip(String umsHostName, String clipName,
        String userName, String securityToken, String filePath) throws ScopixException {

        log.info("start, umsHostName: [" + umsHostName + "], "
            + "clipName: [" + clipName + "], filePath: [" + filePath + "], userName: [" + userName + "]");
        String response = null;

        String userNameBase64Encoded;
        String clipNameBase64Encoded;

        userNameBase64Encoded = new String(Base64.encodeBase64(userName.getBytes()));
        clipNameBase64Encoded = new String(Base64.encodeBase64(clipName.getBytes()));

        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(mediaServerProtocol).append("://").append(umsHostName).append(":").
            append(mediaServerPort).append("/ums/Recording/clipping/upload/").append(clipNameBase64Encoded).
            append("?requestor=").append(userNameBase64Encoded).append("&token=").append(securityToken);

        String url = sBuilder.toString();
        log.debug("URL downloadClip: [" + url + "]");

        //verifica el tiempo transcurrido desde la última verificación de sesión del VSOM
        checkLastVerifySession();

        String responseString = vmsHttpSupport.httpGet2File(url, filePath);

        if (responseString != null) {
            if (responseString.contains(PROGRESS)) {
                log.debug("clip in progress, clipName: [" + clipName + "]");
                response = CLIP_IN_PROGRESS;
            } else {
                log.debug("clip doesn't exist, download failed, clipName: [" + clipName + "]");
                response = CLIP_DOWNLOAD_FAILED;
            }
        } else {
            log.debug("clip downloaded successfully, clipName: [" + clipName + "]");
            response = CLIP_DOWNLOAD_SUCCESS;
        }
        log.info("end, response: [" + response + "]");
        return response;
    }

    /**
     * Descarga snapshot de una cámara en el instante actual
     *
     * @param camera
     * @param filePath
     * @param size
     * @throws ScopixException
     */
    public void getThumbnailNow(Device camera, String filePath, String size, String checkImages) throws ScopixException {
        log.info("start, cameraName: [" + camera.name + "], "
            + "cameraIP: [" + camera.deviceAccess.hostname_ip + "], filePath: [" + filePath + "]");
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("{\"request\":{\"cameraRef\":{\"refUid\":\"").append(camera.uid).append("\",\"refName\":\"").
            append(camera.name).append("\",\"refObjectType\":\"").append(camera.objectType).
            append("\"},\"numThumbnails\":1,\"forRecordings\":false, \"thumbnailResolution\":\"").
            append(size).append("\"}}");

        // size: full, half, thumbnail		
        try {
            //verifica el tiempo transcurrido desde la última verificación de sesión del VSOM
            checkLastVerifySession();

            vmsHttpSupport.httpPost2File(urlPrefix
                + "camera/v1_0/getThumbnails", new StringEntity(sBuilder.toString()), filePath, checkImages);
        } catch (UnsupportedEncodingException e) {
            throw new ScopixException(e.getMessage(), e);
        }
        log.info("end");
    }
    
    /**
     * Descarga snapshot de una cámara en el instante actual con petición GET
     * directamente al media server
     *
     * @param camera
     * @param filePath
     * @throws ScopixException
     */
    public void getThumbnailNowGET(String cameraUuid, String filePath) throws ScopixException {
    	log.info("start, cameraUuid: [" + cameraUuid + "], filePath: [" + filePath + "]");
    	
    	//https://192.168.55.55/thumbnails.jpg?name=bab998b7-ac9b-4ffc-aad0-c1a90cb5cc1f_vs1&resolution=full&quality=medium
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(vsomProtocol).append("://").append(hostName).
				append("/thumbnails.jpg?name=").append(cameraUuid).append("_vs1&resolution=full&quality=medium");

        String url = sBuilder.toString();
        log.debug("URL getThumbnailNowGET: [" + url + "]");
    	
        try{
        	//checks session timeout
        	checkLastVerifySession();
        	vmsHttpSupport.httpGet2File(url, filePath);
        	
        }catch (IllegalArgumentException e){
        	log.error("if cameraUuid is wrong, the response entity'll be null in httpGet2File method");
        	throw new ScopixException("getThumbnailNowGET error: ["+e.getMessage()+"]", e);
        }
    	log.info("end");
    }

    /**
     * Descarga snapshot de una cámara en un instante determinado
     *
     * @param camera
     * @param filePath
     * @param resolution
     * @param numThumbnails
     * @param startTimeMil
     * @param endTimeMil
     * @throws ScopixException
     */
    public void getThumbnailForRecording(Device camera, String filePath,
        String resolution, int numThumbnails, long startTimeMil, long endTimeMil) throws ScopixException {

        log.info("start, cameraName: [" + camera.name + "], cameraIP: [" + camera.deviceAccess.hostname_ip + "], "
            + "filePath: [" + filePath + "], startTimeMil: [" + startTimeMil + "], endTimeMil: [" + endTimeMil + "]");

        try {
            String recordingCatalogUid = this.getRecordingCatalogEntries(camera);
            log.debug("getRecordingCatalogEntries: [" + recordingCatalogUid + "]");

            if (recordingCatalogUid == null) {
                throw new ScopixException("No se encontraron RecordingCatalogEntries para la camara: [" + camera.name + "]");
            }

            StringBuilder sBuilder = new StringBuilder();
            sBuilder.append("{\"request\":{\"cameraRef\":{\"refUid\":\"");
            sBuilder.append(camera.uid);
            sBuilder.append("\",\"refName\":\"");
            sBuilder.append(camera.name);
            sBuilder.append("\",\"refObjectType\":\"");
            sBuilder.append(camera.objectType);
            sBuilder.append("\"},\"numThumbnails\":").append(numThumbnails).append(",\"forRecordings\":true, ");
            sBuilder.append("\"recordingCatalogEntryUid\":\"").append(recordingCatalogUid).append("\",");
            sBuilder.append("\"startTimeInMSec\":").append(startTimeMil)
                .append(",\"endTimeInMSec\":").append(endTimeMil).append(",");
            sBuilder.append("\"thumbnailResolution\":\"").append(resolution).append("\"}}");

            //verifica el tiempo transcurrido desde la última verificación de sesión del VSOM
            checkLastVerifySession();

            vmsHttpSupport.httpPost2File(urlPrefix
                + "camera/v1_0/getThumbnails", new StringEntity(sBuilder.toString()), filePath, null);

        } catch (UnsupportedEncodingException e) {
        	throw new ScopixException(e.getMessage(), e);
        }
        log.info("end");
    }

    public String getRecordingCatalogEntries(Device cm) throws ScopixException {
        log.info("start, cameraName: [" + cm.name + "], cameraIP: [" + cm.deviceAccess.hostname_ip + "]");
        Device recordingCatalogUid = null;
        try {
            StringBuilder sBuilder = new StringBuilder();
            sBuilder.append("{\"cameraRef\":{").append("\"refUid\":\"");
            sBuilder.append(cm.uid).append("\",").append("\"refName\":\"").append(cm.name);
            sBuilder.append("\",").append("\"refObjectType\":\"device_vs_camera_ip\"}}");

            //verifica el tiempo transcurrido desde la última verificación de sesión del VSOM
            checkLastVerifySession();

            String jR = vmsHttpSupport.httpPost2File(urlPrefix
                + "camera/v1_0/getRecordingCatalogEntries", new StringEntity(sBuilder.toString()), null, null);

            PageResponse getResponse = gson.fromJson(jR, PageResponse.class);
            if (!getResponse.status.errorType.equals("SUCCESS")) {
                log.debug("Error: [" + getResponse.status.errorMsg + "]");
                return null;
            }
            //NB! That API return array/list, but we will look only for 1st recording in this example
            recordingCatalogUid = getResponse.data.items[0];  // use 1st recording (hack)
            log.debug("Recording name: [" + recordingCatalogUid.name + "]");

        } catch (UnsupportedEncodingException e) {
        	throw new ScopixException(e.getMessage(), e);
        }
        log.info("end, recordingCatalogUid.uid: [" + recordingCatalogUid.uid + "]");
        return recordingCatalogUid.uid;
    }

    /**
     * Verifica si la sesión sigue activa
     *
     * @throws ScopixException
     */
    public void verifySession() throws ScopixException {
        log.info("start, urlPrefix: [" + urlPrefix + "]");
        String response = vmsHttpSupport.httpPost2File(urlPrefix + "authentication/verifySession", null, null, null);
        log.debug("verify session response: [" + response + "]");

        if (response != null && response.contains("session_not_valid")) {
            log.debug("la sesion del vsom [" + urlPrefix + "] ha expirado o es invalida, se procede a re-autenticar");
            //invoca proceso de autenticación, en caso de fallo realiza reintentos de autenticación
            boolean autenticado = callAuthentication(hostName, userName, password, domain, vsomProtocol, vsomPort, retryNumber);
            if (autenticado) {
                setLastVerifyDate(Calendar.getInstance().getTime());
            }
        } else {
            setLastVerifyDate(Calendar.getInstance().getTime());
        }
        log.info("end");
    }

    /**
     * Verifica el tiempo transcurrido desde la última verificación de sesión del VSOM
     *
     * @throws ScopixException
     */
    private void checkLastVerifySession() throws ScopixException {
        log.info("start");
        Date currentTime = Calendar.getInstance().getTime();
        Long elapsedTime = currentTime.getTime() - getLastVerifyDate().getTime();

        log.debug("currentTime: [" + currentTime + "], "
            + "lastVerifyDate: [" + getLastVerifyDate() + "], elapsedTime (miliseconds): [" + elapsedTime + "]");

        if (elapsedTime > 240000) { //mayor que 4 minutos, debe verificar sesión
            log.debug("han transcurrido mas de 4 minutos desde la ultima verificacion de sesion, se procedera a validarla");
            verifySession();
        }
        log.info("end");
    }

    /**
     * Cierra sesión con el VMS y libera recursos
     *
     * @throws ScopixException
     */
    public void logout() throws ScopixException {
        log.info("start, urlPrefix: [" + urlPrefix + "]");
        String response = vmsHttpSupport.httpPost2File(urlPrefix + "authentication/logout", null, null, null);
        log.debug("end, logout response: [" + response + "]");
    }

    /**
     * Cierra httpClient y connectionEvictor
     */
    public void shutdownHttpConnection() {
        log.info("start, urlPrefix: [" + urlPrefix + "]");
        vmsHttpSupport.shutdown();
        log.info("end");
    }

    /**
     * @return the lastVerifyDate
     */
    public Date getLastVerifyDate() {
        log.info("start " + lastVerifyDate);
        if (lastVerifyDate == null) {
            try {
                lastVerifyDate = DateUtils.parseDate("1970-01-01 00:00:00", new String[]{"yyyy-MM-dd HH:mm:ss"});
            } catch (ParseException e) {
                log.warn("se inicializa lastVerifyDate con now()");
                lastVerifyDate = new Date();
            }
        }
        log.info("end " + lastVerifyDate);
        return lastVerifyDate;
    }

    /**
     * @param lastVerifyDate the lastVerifyDate to set
     */
    public void setLastVerifyDate(Date lastVerifyDate) {
        this.lastVerifyDate = lastVerifyDate;
    }
}
