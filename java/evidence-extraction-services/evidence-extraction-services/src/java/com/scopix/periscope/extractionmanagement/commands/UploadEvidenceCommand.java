/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * UploadEvidenceCommand.java
 * 
 * Created on 18-06-2008, 02:26:43 PM
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.scopix.periscope.evidencemanagement.services.webservices.EvidenceWebService;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.scheduler.exception.FileManagerException;
import com.scopix.periscope.scheduler.exception.UploadJobException;
import com.scopix.periscope.scheduler.fileManager.FileManager;
import com.scopix.periscope.scheduler.fileManager.FileManagerFactory;

/**
 *
 * @author marko.perich
 */
public class UploadEvidenceCommand {

    public static final String UPLOAD_HOST = "UPLOAD_HOST";
    public static final String UPLOAD_USER = "UPLOAD_USER";
    public static final String ROOT_FOLDER = "ROOT_FOLDER";
    private UploadEvidencePoolExecutor uploadEvidencePoolExecutor;
    public static final String UPLOAD_PASSWORD = "UPLOAD_PASSWORD";
    public static final String UPLOAD_CORPORATE = "UPLOAD_CORPORATE";
    public static final String UPLOAD_LOCAL_DIR = "UPLOAD_LOCAL_DIR";
    public static final String UPLOAD_REMOTE_DIR = "UPLOAD_REMOTE_DIR";
    public static final String UPLOAD_MAX_THREADS = "UPLOAD_MAX_THREADS";
    public static final String PROTOCOL_TO_UPLOAD = "PROTOCOL_TO_UPLOAD";
    private static Logger log = Logger.getLogger(UploadEvidenceCommand.class);
    public static final String UPLOAD_FILEMANAGER_TYPE = "UPLOAD_FILEMANAGER_TYPE";
    public static final String EVIDENCE_WEBSERVICE_URL = "EVIDENCE_WEBSERVICE_URL";
    public static final String UPLOAD_REMOTE_DIR_FOR_EXTERNAL_DEVICE = "UPLOAD_REMOTE_DIR_FOR_EXTERNAL_DEVICE";

    public UploadEvidenceCommand(UploadEvidencePoolExecutor uploadEvidencePoolExecutor) {
        log.info("start");
        setUploadEvidencePoolExecutor(uploadEvidencePoolExecutor);
        log.info("end");
    }

    /**
     * 
     * @param params
     * @param webservice
     * @param evidenceUploading
     * @param fileName
     * @throws UploadJobException
     */
    public void execute(Map<String, String> params, EvidenceWebService webservice, Set<String> evidenceUploading, String fileName)
            throws UploadJobException {

        log.info("start, fileName: [" + fileName + "] (for live push it will be != NULL)");
        try {
            if (fileName != null) {
                evidenceUploadPush(params, webservice, evidenceUploading, fileName);
            } else {
                evidenceNormalUpload(params, webservice, evidenceUploading);
            }
        } catch (ScopixException ex) {
            throw new UploadJobException(ex);
        }
        log.info("end");
    }

    /**
     * 
     * @param params
     * @param webservice
     * @param evidenceUploading
     * @param fileName
     * @throws UploadJobException
     * @throws ScopixException
     */
    public synchronized void evidenceUploadPush(Map<String, String> params, EvidenceWebService webservice,
            Set<String> evidenceUploading, String fileName) throws UploadJobException, ScopixException {

        log.info("start");
        FileManager fileManager = getFileManager();

        try {
            getUploadEvidencePoolExecutor().pause();

            executeUploadThread(params, webservice, evidenceUploading, fileName, fileManager);
            getUploadEvidencePoolExecutor().resume();

        } finally {
            disconnectFileManager(fileManager);
        }
        log.info("end");
    }

    /**
     * @param params
     * @param webservice
     * @param evidenceUploading
     * @param fileName
     * @param fileManager
     */
    public void executeUploadThread(Map<String, String> params, EvidenceWebService webservice, Set<String> evidenceUploading,
            String fileName, FileManager fileManager) {

        log.info("start");
        String uploadFileManagerType = params.get(UploadEvidenceCommand.UPLOAD_FILEMANAGER_TYPE);
        UploadEvidenceThread uploadThread = new UploadEvidenceThread();
        uploadThread.init(params, webservice, uploadFileManagerType, fileManager, fileName, evidenceUploading);

        getUploadEvidencePoolExecutor().runTask(uploadThread);
        log.info("end");
    }

    /**
     * This method obtain the filenames and send to executor through the thread. FileManager always is "NETBIOS" because load the
     * filenames from localhost.
     *
     * @param params
     * @param webservice
     * @throws UploadJobException
     * @throws PeriscopeException
     */
    public synchronized void evidenceNormalUpload(Map<String, String> params, EvidenceWebService webservice,
            Set<String> evidenceUploading) throws UploadJobException, ScopixException {

        log.info("start");
        FileManager fileManager = getFileManager();

        try {
            Vector<String> fileNames = getFileNames(params, fileManager);
            // eliminamos los que esten en ejecucion
            if (evidenceUploading != null) {
                fileNames.removeAll(evidenceUploading);
            }
            // se cambia commando para que ordene todos los files segun prioridad sin importar el store
            GetEvidenceExtractionRequestByPriorizationCommand command = new GetEvidenceExtractionRequestByPriorizationCommand();
            List<String> fileNamesPriorizados = command.execute(fileNames); // , storeName
            log.debug("after GetEvidenceExtractionRequestByPriorizationCommand execute");

            // se deben levantar los Evidence request asociados y ordenar por priorizacion y fecha de los archivos
            getUploadEvidencePoolExecutor().pause();
            // limpiamos la cola del Pool Executor
            getUploadEvidencePoolExecutor().getQueue().clear();

            for (String fileName : fileNamesPriorizados) {
                executeUploadThread(params, webservice, evidenceUploading, fileName, fileManager);
            }

            getUploadEvidencePoolExecutor().resume();

        } finally {
            disconnectFileManager(fileManager);
        }
        log.info("end");
    }

    /**
     * @param fileManager
     * @throws UploadJobException
     */
    public void disconnectFileManager(FileManager fileManager) throws UploadJobException {
        log.info("start");
        try {
            fileManager.disconnect();
        } catch (FileManagerException ex) {
            throw new UploadJobException("Error disconnecting from file server at the end of the session", ex);
        }
        log.info("end");
    }

    /**
     * @param params
     * @param fileManager
     * @return
     * @throws UploadJobException
     */
    public Vector<String> getFileNames(Map<String, String> params, FileManager fileManager) throws UploadJobException {
        log.info("start");
        // surround with try finally to close connection at end
        // obtain the files in the folder
        Vector<String> fileNames = null;
        try {
            fileManager.changeDirectory(params.get(UploadEvidenceCommand.UPLOAD_LOCAL_DIR));
            // retorna los files ordenados por fecha de modificacion
            fileNames = fileManager.getFileNames();
            // Collections.sort(fileNames);
        } catch (FileManagerException ex) {
            try {
                fileManager.disconnect();
            } catch (FileManagerException ex2) {
                throw new UploadJobException("Error disconnecting from ftp server while managing exception.", ex2);
            }
            throw new UploadJobException(
                    "Error changing working directory." + params.get(UploadEvidenceCommand.UPLOAD_LOCAL_DIR), ex);
        }
        log.info("end");
        return fileNames;
    }

    /**
     * @return
     * @throws UploadJobException
     */
    public FileManager getFileManager() throws UploadJobException {
        log.info("start");
        // this file manager is always local
        FileManager fileManager = FileManagerFactory.getFilemanager("NETBIOS");

        try {
            fileManager.connectAndLogin("", "", "");
        } catch (FileManagerException ex) {
            throw new UploadJobException("Error connecting to file manager.", ex);
        }
        log.info("end");
        return fileManager;
    }

    /**
     * @return the uploadEvidencePoolExecutor
     */
    public UploadEvidencePoolExecutor getUploadEvidencePoolExecutor() {
        return uploadEvidencePoolExecutor;
    }

    /**
     * @param uploadEvidencePoolExecutor the uploadEvidencePoolExecutor to set
     */
    public void setUploadEvidencePoolExecutor(UploadEvidencePoolExecutor uploadEvidencePoolExecutor) {
        this.uploadEvidencePoolExecutor = uploadEvidencePoolExecutor;
    }
}