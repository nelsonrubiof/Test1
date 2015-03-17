/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.evidencemanagement.services.webservices.EvidenceWebService;
import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.scheduler.fileManager.FileManager;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author marko.perich
 */
public class UploadEvidenceThread extends Thread {

    private static Logger log = Logger.getLogger(UploadEvidenceThread.class);
    private Map<String, String> params;
    private EvidenceWebService webservice;
    private String remoteFileManagerType;
    private FileManager localFileManager;
    private String fileName;
    private boolean initialized = false;
    private Set<String> evidenceUploading;

    /**
     * This method initializes the variables the thread work with.
     *
     * @param params Map of parameters used by the task <br>
     * The easiest way is: <br><br>
     * Map params = new HashMap<String, String>();<br>
     * params.put(UploadEvidenceCommand.UPLOAD_HOST, uploadHost); <br>
     * params.put(UploadEvidenceCommand.UPLOAD_USER, uploadUser);<br>
     * params.put(UploadEvidenceCommand.UPLOAD_PASSWORD, uploadPassword);<br>
     * params.put(UploadEvidenceCommand.UPLOAD_LOCAL_DIR, uploadLocalDir);<br>
     * params.put(UploadEvidenceCommand.UPLOAD_REMOTE_DIR, uploadRemoteDir);<br>
     * params.put(UploadEvidenceCommand.UPLOAD_FILEMANAGER_TYPE, uploadFileManagerType);<br>
     * params.put(UploadEvidenceCommand.UPLOAD_MAX_THREADS, uploadMaxThreads);<br>
     * @param remoteFileManagerType type File Manager used
     * @param localFilemanager File Manager used locale
     * @param webservice EvidenceWebService called to notify uploading of new evidence
     * @param evidenceUploading Set file current upload
     * @param fileName String name faile uploading
     */
    public void init(Map<String, String> params, EvidenceWebService webservice, String remoteFileManagerType,
            FileManager localFilemanager, String fileName, Set<String> evidenceUploading) {
        this.params = params;
        this.webservice = webservice;
        this.remoteFileManagerType = remoteFileManagerType;
        this.localFileManager = localFilemanager;
        this.fileName = fileName;
        this.initialized = true;
        this.evidenceUploading = evidenceUploading;
        this.setName(this.getClass().getSimpleName() + "-" + this.fileName);
    }

    /**
     * Calls to uploadToEvidenceManager, notifyToEvidenceManager and then deletes the file from local folder
     */
    @Override
    public void run() {
        log.info("start [fileName:" + fileName + "]");
        try {
            if (initialized) {
                ExtractionManager em = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
                em.uploadEvidenceToFile(fileName, params, remoteFileManagerType, localFileManager, webservice, evidenceUploading);
            } else {
                throw new RuntimeException("Thread not initialized. Call to init() before run()");
            }
        } catch (Exception e) {
            //se necesita que el thread termine correctamente
            log.error("No es posible terminar ejecucion " + e, e);
        }
        log.info("end [fileName:" + fileName + "]");
    }
}
