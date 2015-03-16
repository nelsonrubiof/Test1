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
 * AxisP3301ImageFileReadyCommand.java
 *
 * Created on 16-08-2010, 04:27:59 PM
 *
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author Gustavo Alvarez
 */
public class NextLevelVideoFileReadyCommand {

    private static Logger log = Logger.getLogger(NextLevelVideoFileReadyCommand.class);
    private static String uploadLocalDir;
    private GenericDAO dao;

    public NextLevelVideoFileReadyCommand() {
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
     * Copia archivo exportado a la carpeta /processed, aplica para la actual y nueva versión de
     * integración con NextLevel
     * 
     * @author  marko perich
     * @param   fileNameNextLevel
     * @param   alternativesFileName
     * @param   tmpFile
     * @version 3.0
     * @date    19-oct-2012
     */
    public void execute(String fileNameNextLevel, Set<String> alternativesFileName, File tmpFile) {
        log.info("start file length" + tmpFile.length());
        try {
            if (tmpFile.length() > 0) {
                //Recuperamos todos los EvidenceFile que en su alternativeFileName tengan el nombre NextLevel
                EvidenceFileDAO evidenceFileDAO = SpringSupport.getInstance().findBeanByClassName(EvidenceFileDAO.class);
                List<EvidenceFile> evidenceFiles = evidenceFileDAO.findEvidenceFileByAlternativeName(fileNameNextLevel);

                for (EvidenceFile ef : evidenceFiles) {
                    log.debug("Archivo a crear: " + (uploadLocalDir + ef.getFilename()));
                    File evFile = new File(uploadLocalDir + ef.getFilename());
                    FileUtils.copyFile(tmpFile, evFile);
                    log.debug("file copy to " + evFile.getAbsolutePath() + " from " + tmpFile.getAbsolutePath());
                    ef.setFileCreationDate(new Date());
                    getDao().save(ef);
                    log.info("Archivo descargado exitosamente!");
                }
                //sacamos de alternativeFileName el fileNameNextLevel recibido e ingresado a los evidence file correspondientes
                alternativesFileName.remove(fileNameNextLevel);
            } else {
                log.warn("archivo recibido para " + fileNameNextLevel + " no tiene contenido");
            }
            //borramos el tmp
            log.debug("borrando temporal " + tmpFile.getName());
            FileUtils.forceDelete(tmpFile);
        } catch (IOException e) {
            log.error("Error extrayendo video de " + fileNameNextLevel + ": " + e, e);
        }
        log.info("end");
    }

    public GenericDAO getDao() {
        if (dao == null) {
            dao = HibernateSupport.getInstance().findGenericDAO();
        }
        return dao;
    }

    public void setDao(GenericDAO dao) {
        this.dao = dao;
    }
}
