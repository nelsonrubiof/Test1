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
 * BroadwareHTTPImageFileReadyCommand.java
 *
 * Created on 26-02-2013, 13:00:00 PM
 *
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.BroadwareHTTPEvidenceProvider;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author gustavo.alvarez
 */
public class BroadwareHTTPVideoFileReadyCommand {

    private static Logger log = Logger.getLogger(BroadwareHTTPVideoFileReadyCommand.class);
    private static String START = "start";
    private static String END = "end";
    private static String uploadLocalDir;
    private GenericDAO dao;

    public BroadwareHTTPVideoFileReadyCommand() {
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
    }

    /**
     * This method cpies the file from the broadware folder to the one parametrized in the system (i.e: /media0/processed), then
     * enqueues the deletion adding a new task to the received DeleteEvidencePoolExecutor.
     *
     * @param fileName
     * @param deleteEvidencePoolExecutor
     */
    public void execute(Integer evidenceId, File tmp, DeleteEvidencePoolExecutor deleteEvidencePoolExecutor,
            String originalName) {
        log.info(START + "[evidence_id:" + evidenceId + "][orignalName:" + originalName + "]");
        try {
            EvidenceFile evidenceFile = getDao().get(evidenceId, EvidenceFile.class);
            //dao.findEvidenceFileByFileName(fileName + ".bwm");

            if (evidenceFile != null) {
                String outFile = uploadLocalDir + evidenceFile.getFilename();
                BroadwareHTTPEvidenceProvider bhttpep =
                        (BroadwareHTTPEvidenceProvider) evidenceFile.getEvidenceExtractionRequest().getEvidenceProvider();
                //cambiar buffer reader por lectura por http
                log.debug("move file " + tmp.getAbsolutePath() + " to " + outFile);

                FileUtils.moveFile(tmp, new File(outFile));

                evidenceFile.setFileCreationDate(new Date());
                getDao().save(evidenceFile);

                DeleteBroadwareEvidenceThread dbet = new DeleteBroadwareEvidenceThread();
                dbet.init(originalName, bhttpep.getIpAddress());
                deleteEvidencePoolExecutor.runTask(dbet);
            } else {
                throw new ScopixException("No existe evidence file asociado al file name: " + originalName);
            }

        } catch (ScopixException ex) {
            log.error("Error obteniendo archivo " + originalName + ": ", ex);
        } catch (IOException ex) {
            log.error("Error obteniendo archivo " + originalName + ": ", ex);
        }
        log.info(END);
    }

    /**
     * @return the dao
     */
    public GenericDAO getDao() {
        if (dao == null) {
            dao = HibernateSupport.getInstance().findGenericDAO();
        }
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(GenericDAO dao) {
        this.dao = dao;
    }
}
