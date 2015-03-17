/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * 
 * Cisco7ImageExtractionThread.java
 * 
 * Created on 13-01-2014, 02:46:01 PM
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
import com.scopix.periscope.extractionmanagement.Cisco7EvidenceProvider;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author carlos polo
 */
public class Cisco7ImageExtractionThread extends ExtractEvidenceThread {

    private Boolean isLive;
    private String vsomPort;
    private String vsomUser;
    private String storeName;
    private String cameraUuid;
    private String vsomDomain;
    private String cameraName;
    private String checkImages;
    private long requestedTime;
    private String vsomPassword;
    private boolean initialized;
    private String vsomProtocol;
    private String vsomIpAddress;
    private String mediaServerIP;
    private String mediaServerPort;
    private EvidenceFile evidenceFile;
    private String mediaServerProtocol;
    private static String uploadLocalDir;
    private ExtractionManager extractionManager;
    private static final String NOT_USED = "NOT_USED";
    private static final String FILE_EXTENSION = ".jpg";
    private String fileName; // name of the file without extension
    private static Logger log = Logger.getLogger(Cisco7ImageExtractionThread.class);

    public void init(Cisco7EvidenceProvider evidenceProvider, long requestedTime, String fileName, EvidenceFile evidenceFile,
            String storeName, String checkImages, Boolean isLive) {

        log.info("start, requestedTime: [" + requestedTime + "], fileName: [" + fileName + "], " + "evidenceFileId: ["
                + evidenceFile.getId() + "], storeName: [" + storeName + "], checkImages:[" + checkImages + "], isLive: ["
                + isLive + "]");

        String vsomDomain = "";
        log.debug("evidenceProvider.getVsomDomain(): [" + evidenceProvider.getVsomDomain() + "]");
        if (!NOT_USED.equalsIgnoreCase(evidenceProvider.getVsomDomain())) {
            vsomDomain = evidenceProvider.getVsomDomain();
        }

        String mediaServerIP = null;
        log.debug("evidenceProvider.getMediaServerIp(): [" + evidenceProvider.getMediaServerIp() + "]");
        if (!NOT_USED.equalsIgnoreCase(evidenceProvider.getMediaServerIp())) {
            mediaServerIP = evidenceProvider.getMediaServerIp();
        }

        setIsLive(isLive);
        setInitialized(true);
        setFileName(fileName);
        setStoreName(storeName);
        setVsomDomain(vsomDomain);
        setCheckImages(checkImages);
        setEvidenceFile(evidenceFile);
        setMediaServerIP(mediaServerIP);
        setRequestedTime(requestedTime);
        setCameraUuid(evidenceProvider.getUuid());
        setVsomUser(evidenceProvider.getVsomUser());
        setVsomPort(evidenceProvider.getVsomPort());
        setVsomPassword(evidenceProvider.getVsomPass());
        setCameraName(evidenceProvider.getCameraName());
        setVsomProtocol(evidenceProvider.getVsomProtocol());
        setVsomIpAddress(evidenceProvider.getVsomIpAddress());
        setMediaServerPort(evidenceProvider.getMediaServerPort());
        setMediaServerProtocol(evidenceProvider.getMediaServerProtocol());

        try {
            ClassPathResource res = new ClassPathResource("system.properties");
            Properties prop = null;
            prop = new Properties();
            prop.load(res.getInputStream());
            setUploadLocalDir(prop.getProperty("UploadJob.uploadLocalDir"));

        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
        log.info("end, vsomIpAddress: [" + vsomIpAddress + "], vsomUser: [" + vsomUser + "], vsomDomain: [" + vsomDomain + "], "
                + "cameraName: [" + cameraName + "], vsomProtocol: [" + vsomProtocol + "], vsomPort: [" + vsomPort + "], "
                + "mediaServerIP: [" + mediaServerIP + "], mediaServerProtocol: [" + mediaServerProtocol + "], "
                + "mediaServerPort: [" + mediaServerPort + "]");
    }

    @Override
    public void run() {
        log.info("start, isInitialized: [" + isInitialized() + "]");
        if (isInitialized()) {
            try {
                callCisco7();
            } catch (Exception e) {
                log.error("fileName: [" + getFileName() + "], cameraName: [" + getCameraName() + "], " + "storeName: ["
                        + getStoreName() + "], evidenceFileId: [" + getEvidenceFile().getId() + "], " + "vsomIpAddress: ["
                        + getVsomIpAddress() + "]. MENSAJE: " + e.getMessage(), e);
            }
        } else {
            log.error("Hilo no inicializado. Invocar metodo init() antes de su ejecucion");
            throw new RuntimeException("Hilo no inicializado. Invocar metodo init() antes de su ejecucion");
        }
        log.info("end");
    }

    private void callCisco7() throws ScopixException {
        log.info("start, cameraName: [" + getCameraName() + "], vsomIpAddress: [" + getVsomIpAddress() + "], " + "storeName: ["
                + getStoreName() + "], evidenceFileId: [" + getEvidenceFile().getId() + "], isLive: [" + getIsLive() + "]");

        try {
            // obtiene instancia del vsomServices (con autenticación incluída)
            Cisco7Manager cisco7Manager = SpringSupport.getInstance().findBeanByClassName(Cisco7Manager.class);

            VsomServices vsomServices = cisco7Manager.getVsomServices(getStoreName(), getVsomIpAddress(), getVsomUser(),
                    getVsomPassword(), getVsomDomain(), getVsomProtocol(), getVsomPort(), getMediaServerProtocol(),
                    getMediaServerPort());

            String filePath = FilenameUtils.separatorsToUnix(getUploadLocalDir() + getFileName() + FILE_EXTENSION);
            log.debug("filePath: [" + filePath + "]");

            // long startTimeMil = getRequestedTime();
            // long endTimeMil = startTimeMil + 100;
            // vsomServices.getThumbnailForRecording(camera, filePath, "full", 1, startTimeMil, endTimeMil);

            if (getCameraUuid() != null && !NOT_USED.equalsIgnoreCase(getCameraUuid())) {
                log.debug("invokes GET request with database cameraUuid: [" + getCameraUuid() + "]");
                vsomServices.getThumbnailNowGET(getCameraUuid(), filePath);

            } else {
                log.debug("invokes POST request through VSOM");
                callVSOMGetThumbnailNow(cisco7Manager, vsomServices, filePath);
            }

            // actualiza en BD el evidence file luego de ser descargado
            EvidenceFileDAO dao = HibernateSupport.getInstance().findDao(EvidenceFileDAO.class);
            dao.updateEvidenceFile(evidenceFile, getFileName() + FILE_EXTENSION);

            log.debug("file [" + getFileName() + FILE_EXTENSION + "] was moved and registered");
            if (isLive) {
                log.debug("invoking evidence upload push, fileName: [" + getFileName() + FILE_EXTENSION + "]");
                getExtractionManager().uploadEvidence(getFileName() + FILE_EXTENSION);
            }

        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            throw new ScopixException("Error llamando a Cisco 7: [" + e.getMessage() + "]", e);
        }
        log.info("end");
    }

    /**
     * Downloads current snapshot through VSOM
     * 
     * @param cisco7Manager
     * @param vsomServices
     * @param filePath
     * @throws ScopixException
     */
    private void callVSOMGetThumbnailNow(Cisco7Manager cisco7Manager, VsomServices vsomServices, String filePath)
            throws ScopixException {

        log.info("start");
        Device camera = cisco7Manager.getCamera(getCameraName(), vsomServices, getStoreName());
        if (camera != null) {
            vsomServices.getThumbnailNow(camera, filePath, "full", getCheckImages()); // size: full, half, thumbnail
        } else {
            throw new ScopixException("Device not found"); // detalle en el catch del run()
        }
        log.info("end");
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

    public long getRequestedTime() {
        return requestedTime;
    }

    public void setRequestedTime(long requestedTime) {
        this.requestedTime = requestedTime;
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

    public String getCheckImages() {
        return checkImages;
    }

    public void setCheckImages(String checkImages) {
        this.checkImages = checkImages;
    }

    public EvidenceFile getEvidenceFile() {
        return evidenceFile;
    }

    public void setEvidenceFile(EvidenceFile evidenceFile) {
        this.evidenceFile = evidenceFile;
    }

    /**
     * @return the cameraUuid
     */
    public String getCameraUuid() {
        return cameraUuid;
    }

    /**
     * @param cameraUuid the cameraUuid to set
     */
    public void setCameraUuid(String cameraUuid) {
        this.cameraUuid = cameraUuid;
    }

    /**
     * @return the isLive
     */
    public Boolean getIsLive() {
        return isLive;
    }

    /**
     * @param isLive the isLive to set
     */
    public void setIsLive(Boolean isLive) {
        this.isLive = isLive;
    }

    /**
     * @return the extractionManager
     */
    public ExtractionManager getExtractionManager() {
        if (extractionManager == null) {
            extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
        }
        return extractionManager;
    }
}