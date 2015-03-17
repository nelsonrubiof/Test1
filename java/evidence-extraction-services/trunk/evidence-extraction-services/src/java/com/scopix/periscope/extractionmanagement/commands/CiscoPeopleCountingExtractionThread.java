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
 *  CiscoPeopleCountingExtractionThread.java
 * 
 *  Created on 07-02-2014, 01:00:02 PM
 * 
 */

package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.pcounting.dto.PeopleCountingDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;

/**
 *
 * @author carlos polo
 */
public class CiscoPeopleCountingExtractionThread extends ExtractEvidenceThread {

    private String fileName;
    private Integer cameraId;
    private String cameraName;
    private String requestDate;
    private String notifyURL;
    private String storeName;
    private Integer evidenceFileId;
    private boolean initialized;
    private String serviceURL; //cisco-people-counting
    private static Logger log = Logger.getLogger(CiscoPeopleCountingExtractionThread.class);
    public static final String CISCO_PEOPLE_COUNTING_SERVICE_URL = "cisco.pcounting.service.url";
    
    public void init(String serviceURL, Integer cameraId, String requestDate, 
            String storeName, Integer evidenceFileId, String notifiyURL, String fileName, String cameraName) {

        log.info("start, cameraId: ["+cameraId+"], requestDate: ["+requestDate+"], storeName: ["+storeName+"], "
                + "evidenceFileId: ["+evidenceFileId+"], fileName: ["+fileName+"], cameraName: ["+cameraName+"], "
                + "notifiyURL: [" + notifiyURL + "], serviceURL: ["+serviceURL+"]");

        setInitialized(true);
        setServiceURL(serviceURL);
        setCameraId(cameraId);
        setRequestDate(requestDate);
        setStoreName(storeName);
        setEvidenceFileId(evidenceFileId);
        setNotifyURL(notifiyURL);
        setFileName(fileName);
        setCameraName(cameraName);
        log.info("end");
    }
    
    @Override
    public void run() {
        log.info("start, isInitialized: [" + isInitialized() + "]");
        if (isInitialized()) {
            try {
                callCiscoPeopleCounting();
            } catch (Exception e) {
            	log.error("serviceURL: [" + getServiceURL() + "], fileName: ["+getFileName()+"], "
            		+ "cameraId: ["+getCameraId()+"], cameraName: ["+getCameraName()+"], requestDate: ["+getRequestDate()+"], "
        				+ "storeName: ["+getStoreName()+"], evidenceFileId: ["+getEvidenceFileId()+"]. MENSAJE: "+e.getMessage(), e);
            }
        } else {
            log.error("Hilo no inicializado. Invocar metodo init() antes de su ejecucion");
            throw new RuntimeException("Hilo no inicializado. Invocar metodo init() antes de su ejecucion");
        }
        log.info("end");
    }
    
    private void callCiscoPeopleCounting() throws ScopixException {
        log.info("start, serviceURL: [" + getServiceURL() + "], fileName: ["+getFileName()+"], "
        		+ "cameraId: ["+getCameraId()+"], cameraName: ["+getCameraName()+"], requestDate: ["+getRequestDate()+"], "
        				+ "storeName: ["+getStoreName()+"], evidenceFileId: ["+getEvidenceFileId()+"]");

        WebClient serviceClient = WebClient.create(getServiceURL());
        serviceClient.path("/getCiscoPeopleCounting").accept(MediaType.APPLICATION_JSON_TYPE).type(MediaType.APPLICATION_JSON_TYPE);

        PeopleCountingDTO peopleCountingDTO = new PeopleCountingDTO();
        
        try {
            peopleCountingDTO.setFileName(getFileName());
            peopleCountingDTO.setCameraId(getCameraId());
            peopleCountingDTO.setRequestDate(getRequestDate());
            peopleCountingDTO.setNotifyURL(getNotifyURL());
            peopleCountingDTO.setStoreName(getStoreName());
            peopleCountingDTO.setEvidenceFileId(getEvidenceFileId());
            peopleCountingDTO.setCameraName(getCameraName());

            //Invoca servicio
            log.debug("disponiendose a invocar servicio de people counting");
            serviceClient.post(peopleCountingDTO);
            log.debug("servicio invocado");
        } catch (Exception e) {
            if (e instanceof ScopixException) {
                throw ((ScopixException) e);
            } else {
                throw new ScopixException(e.getMessage(), e);
            }
        }
        log.info("end");
    }

    /**
     * @return the initialized
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * @param initialized the initialized to set
     */
    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    /**
     * @return the serviceURL
     */
    public String getServiceURL() {
        return serviceURL;
    }

    /**
     * @param serviceURL the serviceURL to set
     */
    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the cameraId
     */
    public Integer getCameraId() {
        return cameraId;
    }

    /**
     * @param cameraId the cameraId to set
     */
    public void setCameraId(Integer cameraId) {
        this.cameraId = cameraId;
    }

    /**
     * @return the requestDate
     */
    public String getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate the requestDate to set
     */
    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * @return the notifyURL
     */
    public String getNotifyURL() {
        return notifyURL;
    }

    /**
     * @param notifyURL the notifyURL to set
     */
    public void setNotifyURL(String notifyURL) {
        this.notifyURL = notifyURL;
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
     * @return the evidenceFileId
     */
    public Integer getEvidenceFileId() {
        return evidenceFileId;
    }

    /**
     * @param evidenceFileId the evidenceFileId to set
     */
    public void setEvidenceFileId(Integer evidenceFileId) {
        this.evidenceFileId = evidenceFileId;
    }

    /**
     * @return the cameraName
     */
    public String getCameraName() {
        return cameraName;
    }

    /**
     * @param cameraName the cameraName to set
     */
    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }
}