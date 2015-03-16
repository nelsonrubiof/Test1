/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * BroadwareImageFileReadyCommand.java
 *
 * Created on 09-06-2008, 08:08:42 PM
 *
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author marko.perich
 */
public class BroadwareVideoFileReadyCommand {

    private static Logger log = Logger.getLogger(BroadwareVideoFileReadyCommand.class);
    private static String filePath;
    private static String uploadLocalDir;

    public BroadwareVideoFileReadyCommand() {
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
    }

    /**
     * This method cpies the file from the broadware folder to the one
     * parametrized in the system (i.e: /media0/processed), then enqueues the
     * deletion adding a new task to the received DeleteEvidencePoolExecutor.
     *
     * @param fileName
     * @param deleteEvidencePoolExecutor
     */
    @SuppressWarnings("static-access")
    public void execute(String fileName, DeleteEvidencePoolExecutor deleteEvidencePoolExecutor) throws ScopixException {
        log.debug("execute(filename = " + fileName + " )");
        FileInputStream fs = null;
        String separator = (filePath.indexOf("/") >= 0) ? "/" : "\\";
        String isName = filePath + fileName + separator + fileName + ".bwm";
        String osName = uploadLocalDir + fileName + ".bwm";

        log.debug("orig. filename:" + isName);
        log.debug("dest. filename:" + osName);
        try {
            byte[] buffer = new byte[65536];
            //filename is repeated as the filepath inside the repository
            fs = new FileInputStream(isName);
            FileOutputStream jpg = new FileOutputStream(osName);
            int bytesRead = 1;
            while (bytesRead > 0) {
                bytesRead = fs.read(buffer, 0, 65536);
                if (bytesRead > 0) {
                    jpg.write(buffer, 0, bytesRead);
                }
            }
            jpg.close();

            EvidenceFileDAO dao = SpringSupport.getInstance().findBeanByClassName(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.findEvidenceFileByFileName(fileName + ".bwm");
            if (evidenceFile != null) {
                evidenceFile.setFileCreationDate(new Date());
                HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            }

            // remove source folder
            // http://<host>/cgi-bin/smanager.bwt?command=remove&name=<archivename>
            DeleteBroadwareEvidenceThread dbet = new DeleteBroadwareEvidenceThread();
            dbet.init(fileName, null);
            deleteEvidencePoolExecutor.runTask(dbet);

        } catch (IOException ex) {
            log.error("Error copiando archivo " + isName + ": ", ex);
        } finally {
            try {
                if (fs != null) {
                    fs.close();
                }
            } catch (IOException ex) {
                log.error("Error cerrando FileInputStream: ", ex);
            }
        }
        log.debug("_execute");

    }
}
