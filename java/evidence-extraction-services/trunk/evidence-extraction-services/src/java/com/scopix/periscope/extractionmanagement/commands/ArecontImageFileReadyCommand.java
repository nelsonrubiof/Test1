/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * AxisP3301ImageFileReadyCommand.java
 * 
 * Created on 16-08-2010, 04:27:59 PM
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author Gustavo Alvarez
 */
public class ArecontImageFileReadyCommand {

    private static String uploadLocalDir;
    private ExtractionManager extractionManager;
    private static Logger log = Logger.getLogger(ArecontImageFileReadyCommand.class);

    public ArecontImageFileReadyCommand() {
        log.info("start");
        if (uploadLocalDir == null) {
            Properties prop = null;
            ClassPathResource res = new ClassPathResource("system.properties");
            try {
                prop = new Properties();
                prop.load(res.getInputStream());
                uploadLocalDir = prop.getProperty("UploadJob.uploadLocalDir");
            } catch (IOException e) {
                log.warn("[convertValue]", e);
            }
        }
        log.info("end");
    }

    /**
     *
     * @param fileName
     * @param tmp
     * @param isLive
     * @throws ScopixException
     */
    public void execute(String fileName, File tmp, Boolean isLive) throws ScopixException {
        log.info("start, fileName: [" + fileName + "], isLive: [" + isLive + "]");
        try {
            // movemos el archivo a la posicion definitiva
            FileUtils.moveFile(tmp, new File(uploadLocalDir + fileName + ".jpg"));
            // Save the date when file is created
            EvidenceFileDAO dao = SpringSupport.getInstance().findBeanByClassName(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.findEvidenceFileByFileName(fileName + ".jpg");
            if (evidenceFile != null) {
                evidenceFile.setFileCreationDate(new Date());
                HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            }

            log.debug("file [" + fileName + ".jpg] was moved and registered");
            if (isLive) {
                log.debug("invoking evidence upload push, fileName: [" + fileName + ".jpg]");
                getExtractionManager().uploadEvidence(fileName + ".jpg");
            }

        } catch (IOException ex) {
            log.error("Error extrayendo archivo jpg de [" + fileName + "]", ex);
        }
        log.info("end");
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
