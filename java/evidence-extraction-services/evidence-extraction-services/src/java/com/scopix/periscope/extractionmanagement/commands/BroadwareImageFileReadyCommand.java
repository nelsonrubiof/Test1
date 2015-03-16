/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * BroadwareImageFileReadyCommand.java
 * 
 * Created on 09-06-2008, 08:08:42 PM
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
 * This class is responsible of transforming the bwm file with only one frame to a JPG file and copy to the path of the files to
 * be uploaded to the evidence server
 *
 * @author marko.perich
 */
public class BroadwareImageFileReadyCommand {

    private static String filePath;
    private static String uploadLocalDir;
    private ExtractionManager extractionManager;
    private static Logger log = Logger.getLogger(BroadwareImageFileReadyCommand.class);

    public BroadwareImageFileReadyCommand() {
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

    /**
     *
     * @param fileName is the name of the file without path and without extension
     */
    public void execute(String fileName, DeleteEvidencePoolExecutor deleteEvidencePoolExecutor, Boolean isLive)
            throws ScopixException {

        log.info("start, fileName: [" + fileName + "], isLive: [" + isLive + "]");
        FileInputStream fs = null;
        String isName = filePath + fileName + "/" + fileName + ".bwm";
        try {
            byte[] buffer = new byte[65536];
            // filename is repeated as the filepath inside the repository
            fs = new FileInputStream(isName);
            FileOutputStream jpg = new FileOutputStream(uploadLocalDir + fileName + ".jpg");
            fs.skip(initialJpegPosition(fileName));
            int bytesRead = 1;
            while (bytesRead > 0) {
                bytesRead = fs.read(buffer, 0, 65536);
                if (bytesRead > 0) {
                    jpg.write(buffer, 0, bytesRead);
                }
            }
            jpg.close();
            // Save the date when file is created
            EvidenceFileDAO dao = SpringSupport.getInstance().findBeanByClassName(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.findEvidenceFileByFileName(fileName + ".jpg");
            if (evidenceFile != null) {
                evidenceFile.setFileCreationDate(new Date());
                HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            }

            // remove source folder
            DeleteBroadwareEvidenceThread dbet = new DeleteBroadwareEvidenceThread();
            dbet.init(fileName, null);
            deleteEvidencePoolExecutor.runTask(dbet);

            log.debug("file [" + fileName + ".jpg] was moved and registered");
            if (isLive) {
                log.debug("invoking evidence upload push, fileName: [" + fileName + ".jpg]");
                getExtractionManager().uploadEvidence(fileName + ".jpg");
            }

        } catch (IOException ex) {
            log.error("Error extrayendo archivo jpg de [" + isName + "]", ex);
        } finally {
            try {
                if (fs != null) {
                    fs.close();
                }
            } catch (IOException ex) {
                log.error("Error cerrando FileInputStream: ", ex);
            }
        }
        log.info("end");
    }

    @SuppressWarnings("resource")
    private int initialJpegPosition(String filename) {
        log.info("start");
        String searchText = "</Header>";
        String fileName = filePath + filename + "/" + filename + ".bwm";
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            // Reads until the end-of-file met
            while (reader.ready()) {
                // Read line-by-line directly
                sb.append(reader.readLine());
            }
        } catch (IOException ex) {
            log.error(ex);
        }
        String fileText = sb.toString();

        log.info("end, position in file: [" + (fileText.indexOf(searchText) + 61) + "]");
        return fileText.indexOf(searchText) + 61;
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
