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
 *  CiscoPeopleCountingExtractionCommand.java
 * 
 *  Created on 06-02-2014, 04:55:05 PM
 * 
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.extractionmanagement.CiscoPeopleCountingEvidenceProvider;
import com.scopix.periscope.extractionmanagement.CiscoPeopleCountingExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.utilities.ScopixUtilities;

/**
 *
 * @author carlos polo
 */
public class CiscoPeopleCountingExtractionCommand
        implements ProviderAdaptor<CiscoPeopleCountingExtractionRequest, CiscoPeopleCountingEvidenceProvider> {

    private Date date;
    private String notifyURL;
    private String storeName;
    private String serviceURL;
    private ExtractEvidencePoolExecutor extractEvidencePoolExecutor;
    private static Logger log = Logger.getLogger(CiscoPeopleCountingExtractionCommand.class);

    public void execute(CiscoPeopleCountingExtractionRequest evidenceRequest,
            CiscoPeopleCountingEvidenceProvider evidenceProvider, Date date,
            ExtractEvidencePoolExecutor poolExecutor) throws ScopixException {

    	log.info("start, storeName: [" + storeName + "]");
        this.date = date;
        this.extractEvidencePoolExecutor = poolExecutor;
        prepareEvidence(evidenceRequest, evidenceProvider);
        log.info("end");
    }

    @Override
    public void prepareEvidence(CiscoPeopleCountingExtractionRequest evidenceExtractionRequest,
            CiscoPeopleCountingEvidenceProvider evidenceProvider) throws ScopixException {

    	log.info("start, evidenceExtractionRequest.getId(): ["+evidenceExtractionRequest.getId()+"], storeName: [" + storeName + "]");
        try {
        	//Calcula la fecha de evidencia de acuerdo al tipo de generación
            Date evidenceDate = ScopixUtilities.calculateEvidenceDate(date, evidenceExtractionRequest);
            log.debug("evidenceDate: ["+evidenceDate+"]");

            Integer cameraId = evidenceExtractionRequest.getEvidenceProvider().getId();
            String cameraName = evidenceExtractionRequest.getEvidenceProvider().getName();

            //the name of the clip must be in local time
            String fileName = DateFormatUtils.format(evidenceDate.getTime(), "yyyyMMdd");
            fileName = fileName + "_" + evidenceExtractionRequest.getId() + ".xml";

            //register evidence file
            log.debug("fileName: [" + fileName + "]");
            EvidenceFileDAO dao = HibernateSupport.getInstance().findDao(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.registerEvidenceFile(fileName, evidenceExtractionRequest, evidenceDate);

            CiscoPeopleCountingExtractionThread ciscoPCountingVideoExThread = new CiscoPeopleCountingExtractionThread();
            String requestDate = DateFormatUtils.format(evidenceDate.getTime(), "yyyy-MM-dd HH:mm:ss");
            log.debug("requestDate: [" + requestDate + "]");

            ciscoPCountingVideoExThread.init(getServiceURL(), cameraId, requestDate,
                    getStoreName(), evidenceFile.getId(), getNotifyURL(), fileName, cameraName);
            //ejecuta en el pool de hilos
            extractEvidencePoolExecutor.pause();
            extractEvidencePoolExecutor.runTask(ciscoPCountingVideoExThread);
            extractEvidencePoolExecutor.resume();

        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new ScopixException(e);
        }
        log.info("end");
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
}