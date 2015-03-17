/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Nelson
 */
@SpringBean
public class DownloadVideoConverterFileReadyCommand extends VideoFileReadyCommand {

    private static final Logger log = Logger.getLogger(DownloadVideoConverterFileReadyCommand.class);
    private GenericDAO dao;
    private static final String CONVERTER_PATH_CONVERTED = "converter.path_converted";
    private static final String CONVERTER_PATH_CONVERTER = "converter.path";

    public void execute(String error, String evidenceId, String originalName, String convertedName, String waitForConverter)
            throws ScopixException {
        log.info("start [evidenceId:" + evidenceId + "][originalName:" + originalName + "][convertedName:" + convertedName + "]");
        Integer eId = Integer.valueOf(evidenceId);
        log.debug("eId " + eId);
        EvidenceFile evidenceFile = getDao().get(eId, EvidenceFile.class);
        String pathConverted = getConfiguration().getString(CONVERTER_PATH_CONVERTED, "");
        log.debug("pathConverted: " + pathConverted);
        String uploadLocalDir = getConfiguration().getString("UploadJob.uploadLocalDir");
        log.debug("uploadLocalDir: " + uploadLocalDir);
        File f = new File(pathConverted + "/" + convertedName);

        String extension = FilenameUtils.getExtension(convertedName);
        String name = DateFormatUtils.format(evidenceFile.getEvidenceDate(), "yyyyMMdd");
        name = name + "_" + evidenceFile.getEvidenceExtractionRequest().getId() + "." + extension;
        String relaPath = uploadLocalDir + name;
        log.debug("relaPath:" + relaPath);
        super.finishFileReady(evidenceFile, f, relaPath);

        //antes de salir borramos el original de la carpeta converted
        if (error.equals("false")) {
            String pathConverter = getConfiguration().getString(CONVERTER_PATH_CONVERTER, "");
            String deleteOriginal = pathConverter + "/" + originalName;
            try {
                FileUtils.forceDelete(new File(deleteOriginal));
            } catch (IOException e) {
                log.warn("Error borrando file " + deleteOriginal + " " + e);
            }
        }
        log.info("end");
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
