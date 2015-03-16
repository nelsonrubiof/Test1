/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * VideoFileReadyCommand.java
 * 
 * Created on 25/09/2014
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;

import com.scopix.periscope.converter.VideoConverterDTO;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.exceptions.DaoInstanceNotFoundException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.Arrays;

public abstract class VideoFileReadyCommand {

    private WebClient serviceClient;
    private PropertiesConfiguration configuration;
    private static final String CONVERTER_URL = "converter.url";
    private static final String CONVERTER_EXECUTE = "converter.execute";
    private static final String CONVERTER_NOTIFY_URL = "converter.notify.url";
    private static final String CONVERTER_EXTENSIONS = "converter.extensions";
    private static Logger log = Logger.getLogger(VideoFileReadyCommand.class);

    /**
     * Copia video en directorio de entrada del converter e invoca servicio notificando al converter
     *
     * @param tmpFile
     * @param waitForConverter
     * @param evidenceFileId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    protected boolean executeConverter(File tmpFile, String waitForConverter, Integer evidenceFileId, String fileName)
            throws ScopixException {
        log.info("start, tmpFile: [" + tmpFile.getAbsolutePath() + "], waitForConverter: [" + waitForConverter + "]");
        boolean convert = false;
        String tmpName = FilenameUtils.getName(tmpFile.getAbsolutePath());
        boolean mustConvert = mustConvertVideo(tmpName);
        log.debug("mustConvert: [" + mustConvert + "], tmpName: [" + tmpName + "]");

        if (mustConvert) {
            // copia video en directorio de entrada del converter e invoca servicio notificando al converter
            File fileToConverter = copyVideoToConverterDir(tmpFile, fileName);
//            callVideoConverterService(tmpFile, waitForConverter, evidenceFileId);
            callVideoConverterService(fileToConverter, waitForConverter, evidenceFileId);
            convert = true;
        }
        log.info("end, convert: [" + convert + "]");
        return convert;
    }

    /**
     * Determina si el video debe convertirse o no
     *
     * @param videoBaseName
     * @return
     */
    private boolean mustConvertVideo(String videoBaseName) {
        log.info("start, videoBaseName: [" + videoBaseName + "]");
        boolean mustConvert = false;
        String videoExtension = FilenameUtils.getExtension(videoBaseName);
        String convertir = getConfiguration().getString(CONVERTER_EXECUTE);
        String[] saExtensions = getConfiguration().getStringArray(CONVERTER_EXTENSIONS);
        //String plainExtensions = StringUtils.join(saExtensions, ", ");

        log.debug("convertir: [" + convertir + "], extensiones: [" + saExtensions + "]");
        //plainExtensions.indexOf(videoExtension) > 0
        if (convertir != null && "S".equalsIgnoreCase(convertir) && Arrays.asList(saExtensions).contains(videoExtension)) {
            mustConvert = true;
        }
        log.info("end, mustConvert: [" + mustConvert + "]");
        return mustConvert;
    }

    /**
     * Invoca servicio del converter
     *
     * @param tmpFile
     * @param waitForConverter
     */
    private void callVideoConverterService(File tmpFile, String waitForConverter, Integer evidenceFileId) throws ScopixException {
        log.info("start, tmpFile: [" + tmpFile.getAbsolutePath() + "], waitForConverter: [" + waitForConverter + "]");
        int statusCode = -1;
        try {
            String notifyURL = getConfiguration().getString(CONVERTER_NOTIFY_URL);
            log.debug("notifyURL: [" + notifyURL + "/convertVideo]");

            VideoConverterDTO videoConverterDTO = new VideoConverterDTO();
            videoConverterDTO.setFileName(FilenameUtils.getName(tmpFile.getAbsolutePath()));
            videoConverterDTO.setWaitForConverter(waitForConverter);
            videoConverterDTO.setUrlNotificacion(notifyURL);
            videoConverterDTO.setEvidenceFileId(evidenceFileId);

            Response response = getServiceClient().post(videoConverterDTO, Response.class);
            statusCode = response.getStatusInfo().getStatusCode();

        } catch (Exception e) {
            throw new ScopixException("error invocando servicio de converter: [" + e.getMessage() + "]", e);
        }
        log.info("end, statusCode: [" + statusCode + "]");
    }

    /**
     * Copia archivo en directorio de entrada del converter
     *
     * @param tmpFile
     */
    private File copyVideoToConverterDir(File tmpFile, String fileName) {
        log.info("start, tmpFile original: [" + tmpFile.getAbsolutePath() + "]");
        File fileToConverter = null;
        try {
            String converterPath = getConfiguration().getString("converter.path");
            log.debug("converter path: [" + converterPath + "]");
            fileToConverter = new File(converterPath + "/" + fileName);
            // Copia archivo al directorio de conversion con el nombre final
            FileUtils.copyFile(tmpFile, fileToConverter);

        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
        }
        log.info("end");
        return fileToConverter;
    }

    /**
     * Actualiza evidence file en BD y mueve el temporal al directorio processed
     *
     * @param evidenceFile
     * @param tmpFile
     * @param processedFilePath
     * @throws ScopixException
     */
    protected void finishFileReady(EvidenceFile evidenceFile, File tmpFile, String processedFilePath) throws ScopixException {
        log.info("start, evidenceFile.id: [" + evidenceFile.getId() + "], processedFilePath: [" + processedFilePath + "]");
        try {
            // actualiza en BD el evidence file luego de ser descargado
            EvidenceFileDAO dao = HibernateSupport.getInstance().findDao(EvidenceFileDAO.class);
            dao.updateEvidenceFile(evidenceFile, FilenameUtils.getName(processedFilePath));

            log.debug("moviendo archivo [" + tmpFile.getAbsolutePath() + "] a [" + processedFilePath + "]");
            FileUtils.moveFile(tmpFile, new File(processedFilePath));

        } catch (DaoInstanceNotFoundException e) {
            throw new ScopixException(e.getMessage(), e);
        } catch (IOException e) {
            throw new ScopixException(e.getMessage(), e);
        }
        log.info("end");
    }

    /**
     * @return the serviceClient
     */
    public WebClient getServiceClient() {
        log.info("start");
        if (serviceClient == null) {
            String converterURL = getConfiguration().getString(CONVERTER_URL);
            log.debug("converterURL: [" + converterURL + "]");

            serviceClient = WebClient.create(converterURL);
            serviceClient.path("/convertVideo").accept(MediaType.APPLICATION_JSON_TYPE).type(MediaType.APPLICATION_JSON_TYPE);
        }
        log.info("end");
        return serviceClient;
    }

    /**
     * @param serviceClient the serviceClient to set
     */
    public void setServiceClient(WebClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    public PropertiesConfiguration getConfiguration() {
        if (configuration == null) {
            try {
                configuration = new PropertiesConfiguration("system.properties");
                configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
            } catch (ConfigurationException e) {
                log.error("No es posible cargar propiedades " + e, e);
            }
        }
        return configuration;
    }

    public void setConfiguration(PropertiesConfiguration configuration) {
        this.configuration = configuration;
    }
}
