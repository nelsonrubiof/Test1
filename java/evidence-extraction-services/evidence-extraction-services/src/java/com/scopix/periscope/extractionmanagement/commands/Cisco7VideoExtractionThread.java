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
 *  Cisco7VideoExtractionThread.java
 * 
 *  Created on 02-10-2013, 06:13:30 PM
 * 
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import com.scopix.periscope.cisco7.Cisco7Manager;
import com.scopix.periscope.cisco7.Vsm7ApiPojos.Device;
import com.scopix.periscope.cisco7.VsomServices;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author Gustavo Alvarez
 * @modified Carlos Polo
 */
public class Cisco7VideoExtractionThread extends ExtractEvidenceThread {

    private long stopUTC;
    private long startUTC;
    private String vsomUser;
    private String vsomPort;
    private String storeName;
    private String vsomDomain;
    private String cameraName;
    private String vsomPassword;
    private boolean initialized;
    private String vsomProtocol;
    private String vsomIpAddress;
    private String mediaServerIP;
    private String mediaServerPort;
    private EvidenceFile evidenceFile;
    private String mediaServerProtocol;
    private static String uploadLocalDir;
    private static final String FILE_EXTENSION = ".mp4";
    private String fileName; //name of the file without extension
    private static Logger log = Logger.getLogger(Cisco7VideoExtractionThread.class);

    public void init(String vsomIpAddress, String vsomUser, String vsomPass, String vsomDomain, String cameraName,
            String vsomProtocol, String vsomPort, String mediaServerIP, String mediaServerProtocol, String mediaServerPort,
            long startUTC, long stopUTC, String fileName, EvidenceFile evidenceFile, String storeName) {

    	log.info("start, vsomIpAddress: ["+vsomIpAddress+"], vsomUser: ["+vsomUser+"], vsomDomain: ["+vsomDomain+"], "
                + "cameraName: ["+cameraName+"], vsomProtocol: ["+vsomProtocol+"], vsomPort: ["+vsomPort+"], "
                + "mediaServerIP: ["+mediaServerIP+"], mediaServerProtocol: ["+mediaServerProtocol+"], "
                + "mediaServerPort: ["+mediaServerPort+"], startUTC: ["+startUTC+"], stopUTC: ["+stopUTC+"], "
                + "fileName: ["+fileName+"], evidenceFileId: ["+evidenceFile.getId()+"], storeName: ["+storeName+"]");

        setStopUTC(stopUTC);
        setInitialized(true);
        setStartUTC(startUTC);
        setFileName(fileName);
        setVsomUser(vsomUser);
        setVsomPort(vsomPort);
        setStoreName(storeName);
        setVsomPassword(vsomPass);
        setVsomDomain(vsomDomain);
        setCameraName(cameraName);
        setVsomProtocol(vsomProtocol);
        setMediaServerIP(mediaServerIP);
        setVsomIpAddress(vsomIpAddress);
        setEvidenceFile(evidenceFile);
        setMediaServerPort(mediaServerPort);
        setMediaServerProtocol(mediaServerProtocol);

        try {
            ClassPathResource res = new ClassPathResource("system.properties");
            Properties prop = null;
            prop = new Properties();
            prop.load(res.getInputStream());
            setUploadLocalDir(prop.getProperty("UploadJob.uploadLocalDir"));
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
        log.info("end");
    }

    @Override
    public void run() {
        log.info("start, isInitialized: [" + isInitialized() + "]");
        if (isInitialized()) {
            try {
                callCisco7();
            } catch (Exception e) {
            	log.error("fileName (clipName): ["+getFileName()+"], cameraName: ["+getCameraName()+"], "
                		+ "storeName: ["+getStoreName()+"], evidenceFileId: ["+getEvidenceFile().getId()+"], "
            				+ "vsomIpAddress: ["+getVsomIpAddress()+"]. MENSAJE: "+e.getMessage(), e);
            }
        } else {
            log.error("Hilo no inicializado. Invocar metodo init() antes de su ejecucion");
            throw new RuntimeException("Hilo no inicializado. Invocar metodo init() antes de su ejecucion");
        }
        log.info("end");
    }

    private void callCisco7() throws ScopixException {
    	log.info("start, cameraName: ["+getCameraName()+"], vsomIpAddress: ["+getVsomIpAddress()+"], "
    			+ "storeName: ["+getStoreName()+"], evidenceFileId: ["+getEvidenceFile().getId()+"]");
        try {
            //obtiene instancia del vsomServices (con autenticación incluída)
            Cisco7Manager cisco7Manager = SpringSupport.getInstance().findBeanByClassName(Cisco7Manager.class);

            VsomServices vsomServices = cisco7Manager.getVsomServices(getStoreName(), getVsomIpAddress(), 
                    getVsomUser(), getVsomPassword(), getVsomDomain(), getVsomProtocol(), 
                    getVsomPort(), getMediaServerProtocol(), getMediaServerPort());

            Device camera = cisco7Manager.getCamera(getCameraName(), vsomServices, getStoreName());

            if (camera != null) {
                //en media servers definidos en la misma maquina de vsom, la IP 127.0.0.1 no funciona.
                String umsHostName = getMediaServerIP();
                if (umsHostName == null) {
                    log.debug("camera.managedByRef: [" + camera.managedByRef + "]");
                    umsHostName = vsomServices.getUmsHostName(camera.managedByRef);
                }

                if (umsHostName != null) {
                    log.debug("umsHostName: [" + umsHostName + "]");
                    String securityToken = vsomServices.getSecurityToken(camera, 36000); //expires in 10 hours from now
                    log.debug("security token: [" + securityToken + "]");

                    String clipName = getFileName();
                    String filePath = FilenameUtils.separatorsToUnix(getUploadLocalDir() + clipName + FILE_EXTENSION);
                    log.debug("filePath: [" + filePath + "]");
                    
                    //Crea request de video
                    String serviceReturn = vsomServices.createClipRequest(camera,
                            umsHostName, clipName, getVsomUser(), getStartUTC(), getStopUTC(), securityToken);

                    log.debug("clip request return: [" + serviceReturn + "]");
                    if (serviceReturn != null) {
                        processServiceReturn(vsomServices, serviceReturn,
                                clipName, umsHostName, securityToken, filePath, evidenceFile);
                    } else {
                        throw new ScopixException("Fallo en clip request"); //detalle en el catch del run()
                    }
                } else {
                    throw new ScopixException("Error obteniendo umsHostName"); //detalle en el catch del run()
                }
            } else {
                throw new ScopixException("Device no encontrado"); //detalle en el catch del run()
            }
        } catch (RuntimeException e) {
            log.error(e, e);
            throw new ScopixException("Error llamando a Cisco 7 " + e.getMessage(), e);
        }
        log.info("end");
    }

    /**
     *
     * @param vsomServices
     * @param serviceReturn
     * @param clipName
     * @param umsHostName
     * @param securityToken
     * @param filePath
     * @param evidenceFile
     * @throws PeriscopeException
     */
    private void processServiceReturn(VsomServices vsomServices, String serviceReturn, String clipName,
            String umsHostName, String securityToken, String filePath, EvidenceFile evidenceFile) throws ScopixException {

        log.info("start, serviceReturn: ["+serviceReturn+"], clipName: ["+clipName+"], "
        		+ "umsHostName: ["+umsHostName+"], filePath: ["+filePath+"], evidenceFile: ["+evidenceFile+"]");
        
        if (serviceReturn.equalsIgnoreCase(VsomServices.CLIP_CREATED_FAILED)) {
        	throw new ScopixException("fallo en clip request"); //detalle en el catch del run()

        } else if (serviceReturn.equalsIgnoreCase(VsomServices.CLIP_ALREADY_EXISTS)) {
            log.debug("clip ya existe, nombre: [" + clipName + "], filePath: ["+filePath+"], "
            		+ "se procede a descargarlo. CameraName: ["+getCameraName()+"], storeName: ["+getStoreName()+"]");
            serviceReturn = vsomServices.downloadClip(umsHostName, clipName, getVsomUser(), securityToken, filePath);
            log.debug("download clip return (already exists): [" + serviceReturn + "]");
        } else {
            //iterate while getting video
            do {
                log.debug("obteniendo video: [" + clipName + "]. "
                		+ "CameraName: ["+getCameraName()+"], storeName: ["+getStoreName()+"]");
                threadSleep();
                serviceReturn = vsomServices.downloadClip(umsHostName, clipName, getVsomUser(), securityToken, filePath);
                log.debug("download clip return: [" + serviceReturn + "]");

            } while (serviceReturn.equalsIgnoreCase(VsomServices.CLIP_IN_PROGRESS));
        }

        log.debug("video serviceReturn: [" + serviceReturn + "]");
        if (serviceReturn.equalsIgnoreCase(VsomServices.CLIP_DOWNLOAD_SUCCESS)) {
            //actualiza en BD el evidence file luego de ser descargado
            EvidenceFileDAO dao = HibernateSupport.getInstance().findDao(EvidenceFileDAO.class);
            dao.updateEvidenceFile(evidenceFile, getFileName() + FILE_EXTENSION);
        } else {
        	throw new ScopixException("error generando video"); //detalle en el catch del run()
        }
        log.info("end");
    }

    /**
     * Duerme hilo actual por 5 segundos
     */
    private void threadSleep() {
        try {
        	log.debug("durmiendo hilo actual por 5 segundos para "
                    + "dar tiempo a persistencia de evidence file con id: [" + getEvidenceFile().getId() + "]");
            Thread.sleep(5000); // sleep for 5000 milliseconds,
        } catch (InterruptedException ex) {
            log.warn(ex.getMessage(), ex);
        }
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public String getVsomIpAddress() {
        return vsomIpAddress;
    }

    public void setVsomIpAddress(String vsomIpAddress) {
        this.vsomIpAddress = vsomIpAddress;
    }

    public String getVsomUser() {
        return vsomUser;
    }

    public void setVsomUser(String vsomUser) {
        this.vsomUser = vsomUser;
    }

    public String getVsomDomain() {
        return vsomDomain;
    }

    public void setVsomDomain(String vsomDomain) {
        this.vsomDomain = vsomDomain;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public long getStartUTC() {
        return startUTC;
    }

    public void setStartUTC(long startUTC) {
        this.startUTC = startUTC;
    }

    public long getStopUTC() {
        return stopUTC;
    }

    public void setStopUTC(long stopUTC) {
        this.stopUTC = stopUTC;
    }

    public static String getUploadLocalDir() {
        return uploadLocalDir;
    }

    public static void setUploadLocalDir(String aUploadLocalDir) {
        uploadLocalDir = aUploadLocalDir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getVsomProtocol() {
        return vsomProtocol;
    }

    public void setVsomProtocol(String vsomProtocol) {
        this.vsomProtocol = vsomProtocol;
    }

    public String getVsomPort() {
        return vsomPort;
    }

    public void setVsomPort(String vsomPort) {
        this.vsomPort = vsomPort;
    }

    public String getMediaServerIP() {
        return mediaServerIP;
    }

    public void setMediaServerIP(String mediaServerIP) {
        this.mediaServerIP = mediaServerIP;
    }

    public String getMediaServerProtocol() {
        return mediaServerProtocol;
    }

    public void setMediaServerProtocol(String mediaServerProtocol) {
        this.mediaServerProtocol = mediaServerProtocol;
    }

    public String getMediaServerPort() {
        return mediaServerPort;
    }

    public void setMediaServerPort(String mediaServerPort) {
        this.mediaServerPort = mediaServerPort;
    }

    /**
     * @return the storeName
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * @param storeName the storeName to set
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * @return the vsomPassword
     */
    public String getVsomPassword() {
        return vsomPassword;
    }

    /**
     * @param vsomPassword the vsomPassword to set
     */
    public void setVsomPassword(String vsomPassword) {
        this.vsomPassword = vsomPassword;
    }
    
	public EvidenceFile getEvidenceFile() {
		return evidenceFile;
	}

	public void setEvidenceFile(EvidenceFile evidenceFile) {
		this.evidenceFile = evidenceFile;
	}
}