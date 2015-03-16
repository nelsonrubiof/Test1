/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * ReadUrlPHPImageFileReadyCommand.java
 * 
 * Created on 16-08-2010, 04:27:59 PM
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

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
 * @author Nelson Rubio
 * @version 1.0.0
 */
public class ReadUrlPHPImageFileReadyCommand {

    private static String filePath;
    private static String uploadLocalDir;
    private ExtractionManager extractionManager;
    private static Logger log = Logger.getLogger(ReadUrlPHPImageFileReadyCommand.class);

    public ReadUrlPHPImageFileReadyCommand() {
        log.info("start");
        if (filePath == null) {
            Properties prop = null;
            ClassPathResource res = new ClassPathResource("system.properties");
            try {
                prop = new Properties();
                prop.load(res.getInputStream());
                filePath = prop.getProperty("BWMFilePath");
                uploadLocalDir = prop.getProperty("UploadJob.uploadLocalDir");
            } catch (IOException e) {
                log.warn("[convertValue]", e);
            }
        }
        log.info("end");
    }

    public void execute(String fileName, InputStream image, Boolean isLive) throws ScopixException {
        log.info("start, fileName: [" + fileName + "], isLive: [" + isLive + "]");
        try {
            byte[] buffer = new byte[65536];
            // filename is repeated as the filepath inside the repository
            FileOutputStream jpg = new FileOutputStream(uploadLocalDir + fileName + ".jpg");
            int bytesRead = 1;
            while (bytesRead > 0) {
                bytesRead = image.read(buffer, 0, 65536);
                if (bytesRead > 0) {
                    jpg.write(buffer, 0, bytesRead);
                }
            }
            jpg.flush();
            jpg.close();
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
        } finally {
            try {
                if (image != null) {
                    image.close();
                }
            } catch (IOException ex) {
                log.error("Error cerrando FileInputStream: ", ex);
            }
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
