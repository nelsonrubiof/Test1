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
 * Cisco7ImageExtractionCommand.java
 * 
 * Created on 26-11-2013, 10:56:36 AM
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.extractionmanagement.Cisco7EvidenceProvider;
import com.scopix.periscope.extractionmanagement.Cisco7ImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.utilities.ScopixUtilities;

/**
 *
 * @author Gustavo Alvarez
 * @modified Carlos Polo
 */
public class Cisco7ImageExtractionCommand implements ProviderAdaptor<Cisco7ImageExtractionRequest, Cisco7EvidenceProvider> {

    private Date date;
    private String storeName;
    private String checkImages;
    private ExtractEvidencePoolExecutor extractEvidencePoolExecutor;
    private static Logger log = Logger.getLogger(Cisco7ImageExtractionCommand.class);

    public void execute(Cisco7ImageExtractionRequest cer, Cisco7EvidenceProvider evProv, Date date,
            ExtractEvidencePoolExecutor eepe) throws ScopixException {

        log.info("start, storeName: [" + storeName + "]");
        this.extractEvidencePoolExecutor = eepe;
        this.date = date;
        prepareEvidence(cer, evProv);
        log.info("end");
    }

    @Override
    public void prepareEvidence(Cisco7ImageExtractionRequest evidenceExtractionRequest, Cisco7EvidenceProvider evidenceProvider)
            throws ScopixException {

        log.info("start, evidenceExtractionRequest.getId(): [" + evidenceExtractionRequest.getId() + "], storeName: ["
                + storeName + "], isLive: [" + evidenceExtractionRequest.getLive() + "]");
        try {
            long requestedTime = 0; // no se usará por ahora, solo aplica para getThumbnailForRecording

            // Calcula la fecha de evidencia de acuerdo al tipo de generación
            Date evidenceDate = ScopixUtilities.calculateEvidenceDate(date, evidenceExtractionRequest);
            log.debug("evidenceDate: [" + evidenceDate + "]");

            String camDescription = evidenceProvider.getDescription();

            // the name of the clip must be in local time
            String name = DateFormatUtils.format(evidenceDate.getTime(), "yyyyMMdd");
            name = name + "_" + evidenceExtractionRequest.getId() + "_" + (camDescription.toUpperCase().replaceAll(" ", ""));
            String fileName = name + ".jpg";

            // register evidence file
            log.debug("fileName: [" + fileName + "], camDescription: [" + camDescription + "], storeName: [" + getStoreName()
                    + "]");
            EvidenceFileDAO dao = HibernateSupport.getInstance().findDao(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.registerEvidenceFile(fileName, evidenceExtractionRequest, evidenceDate);

            Cisco7ImageExtractionThread cisco7ImageExThread = new Cisco7ImageExtractionThread();
            cisco7ImageExThread.init(evidenceProvider, requestedTime, name, evidenceFile, getStoreName(), getCheckImages(),
                    evidenceExtractionRequest.getLive());

            extractEvidencePoolExecutor.pause();
            extractEvidencePoolExecutor.runTask(cisco7ImageExThread);
            extractEvidencePoolExecutor.resume();

        } catch (ParseException pex) {
            log.error(pex.getMessage(), pex);
            throw new ScopixException(pex);
        }
        log.info("end");
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

    public String getCheckImages() {
        return checkImages;
    }

    public void setCheckImages(String checkImages) {
        this.checkImages = checkImages;
    }
}