/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.evidencemanagement.dto.NewAutomaticEvidenceDTO;
import com.scopix.periscope.evidencemanagement.dto.NewEvidenceDTO;
import com.scopix.periscope.evidencemanagement.dto.SituationMetricsDTO;
import com.scopix.periscope.evidencemanagement.services.webservices.EvidenceWebService;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.extractionmanagement.ExtractionPlan;
import com.scopix.periscope.extractionmanagement.SituationMetricExtractionRequest;
import com.scopix.periscope.extractionmanagement.dao.EvidenceExtractionDAOImpl;
import com.scopix.periscope.extractionplanmanagement.EServerDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.FileUtils;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.scheduler.devicemanager.DeviceUtil;
import com.scopix.periscope.scheduler.exception.FileManagerException;
import com.scopix.periscope.scheduler.exception.UploadJobException;
import com.scopix.periscope.scheduler.file.FileUtil;
import com.scopix.periscope.scheduler.fileManager.FileManager;
import com.scopix.periscope.scheduler.fileManager.FileManagerFactory;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author nelson
 */
public class UploadEvidenceToFileCommand {

    private static Logger log = Logger.getLogger(UploadEvidenceToFileCommand.class);
    private FileManager remoteFileManager;
    private EvidenceFile evidenceFile;
    private Map<String, String> params;
    private EvidenceWebService webservice;
    private String storeName;

    public void execute(String fileName, EvidenceFile ef, Map<String, String> parameters, String remoteFileManagerType,
            FileManager localFileManager, EvidenceWebService ws, Set<String> evidenceUploading) {
        log.info("start [fileName:" + fileName + "][parameters:" + parameters + "]"
                + "[remoteFileManagerType:" + remoteFileManagerType + "][ef:" + ef + "]");
        evidenceUploading.add(fileName);
        log.info(" current " + fileName);
        try {

            setRemoteFileManager(FileManagerFactory.getFilemanager(remoteFileManagerType));
            setEvidenceFile(ef);
            setParams(parameters);
            setWebservice(ws);
            //recuperamos el nombre del store dado el evidenceFile recibido
            setStoreName(ef.getEvidenceExtractionRequest().getExtractionPlan().getStoreName());

            if (getEvidenceFile() != null && getEvidenceFile().getUploadDate() == null) {
                //verificando de que modo se debe operar: transferencia por red o extraccion a dispositivo externo
                if (!params.get(UploadEvidenceCommand.UPLOAD_FILEMANAGER_TYPE).equalsIgnoreCase(
                        ExtractionManager.UPLOAD_FILE_MANAGER_LOCAL)) {
                    uploadEvidenceNetworkAvailable();
                } else {
                    uploadEvidenceToExternalDevice();
                }

                //eliminando evidencia
                localFileManager.deleteFile(getParams().get(UploadEvidenceCommand.UPLOAD_LOCAL_DIR) + fileName);

                //Marcar updalodDate
                getEvidenceFile().setUploadDate(new Date());
                HibernateSupport.getInstance().findGenericDAO().save(getEvidenceFile());
            } else {
                log.debug("no se procesa evidence " + fileName);
                if (getEvidenceFile().getUploadDate() != null) {
                    //intentamos eliminar el archivo
                    log.debug("intentando borrar archivo " + fileName);
                    localFileManager.deleteFile(getParams().get(UploadEvidenceCommand.UPLOAD_LOCAL_DIR) + fileName);
                }
            }
        } catch (UploadJobException e) {
            log.debug("Error uploading file.", e);
        } catch (IOException e) {
            log.debug("Error uploading file.", e);
        } catch (FileManagerException e) {
            log.debug("Error uploading file.", e);
        } catch (RuntimeException e) {
            log.debug("Error uploading file.", e);
        } finally {
            // lo removemos por si no se pudo subir este sea tomado por otro thread
            evidenceUploading.remove(fileName);
        }
        log.info("end");
    }

    public void uploadEvidenceNetworkAvailable() throws UploadJobException {
        log.debug("start");

        if (getEvidenceFile() != null && getEvidenceFile().getUploadDate() == null) {
            //subiendo la evidencia
            //TODO lo comentamos para realizar pruebas
            uploadToEvidenceManager(getParams(), getEvidenceFile());

            //notificando
            log.debug("type:" + getEvidenceFile().getEvidenceExtractionRequest().getType());
            if (getEvidenceFile().getEvidenceExtractionRequest().getType().equals(EvidenceRequestType.AUTO_GENERATED)
                    || getEvidenceFile().getEvidenceExtractionRequest().getType().equals(EvidenceRequestType.REAL_RANDOM)) {

                notifyToAutomaticEvidenceManager(getEvidenceFile(), getWebservice());
            } else {
                notifyToEvidenceManager(getEvidenceFile(), getWebservice());
            }
        }
        log.debug("end");
    }

    /**
     *
     * @param params
     * @param evidenceFile
     * @throws com.scopix.periscope.scheduler.exception.UploadJobException if can not connect to fileManager, create the target
     * folder or create the file in the server
     */
    public void uploadToEvidenceManager(Map<String, String> params, EvidenceFile evidenceFile) throws UploadJobException {
        log.info("start [fileName:" + evidenceFile.getFilename() + "]");

        // connect to server
        try {
            getRemoteFileManager().connectAndLogin(params.get(UploadEvidenceCommand.UPLOAD_HOST),
                    params.get(UploadEvidenceCommand.UPLOAD_USER),
                    params.get(UploadEvidenceCommand.UPLOAD_PASSWORD));
        } catch (FileManagerException ex) {
            throw new UploadJobException("Error connecting to file manager.", ex);
        }

        try {
            //The new Path need to be: yyyy/MM/dd     (20100727)

            String dayDir = FileUtils.getPathFromDate(evidenceFile.getEvidenceDate(),
                    getRemoteFileManager().getRemotePathSeparator());
            String remoteDir = params.get(UploadEvidenceCommand.UPLOAD_REMOTE_DIR) + getStoreName() + "/" + dayDir;
            try {
//            log.debug("Making directory " + params.get(UploadEvidenceCommand.UPLOAD_REMOTE_DIR) + dayDir);
//            getRemoteFileManager().makeDirectory(params.get(UploadEvidenceCommand.UPLOAD_REMOTE_DIR) + dayDir);
                log.debug("Making directory " + remoteDir);
                getRemoteFileManager().makeDirectory(remoteDir);
            } catch (FileManagerException ex) {
                log.debug("Error making directory. " + ex.getMessage());
                throw new UploadJobException(ex);
            }

            // actually save the file in the server
            try {
//            log.debug("uploading file to backend " + params.get(UploadEvidenceCommand.UPLOAD_REMOTE_DIR) + dayDir);
                log.debug("uploading file to backend " + remoteDir);
                getRemoteFileManager().putFile(params.get(UploadEvidenceCommand.UPLOAD_LOCAL_DIR) + evidenceFile.getFilename(),
                        //params.get(UploadEvidenceCommand.UPLOAD_REMOTE_DIR) + dayDir
                        remoteDir
                        + getRemoteFileManager().getRemotePathSeparator()
                        + evidenceFile.getFilename());
            } catch (FileManagerException ex) {
                log.debug("uploading file to backend " + ex.getMessage());
                throw new UploadJobException(ex);
            }
        } catch (UploadJobException e) {
            throw (e);
        } finally {
            //si se logro realizar la conexion intentamos cerrarla 
            // close connection
            try {
                getRemoteFileManager().disconnect();
            } catch (FileManagerException ex) {
                throw new UploadJobException(ex);
            }
        }
        log.info("end");
    }

    /**
     * Se envia la notificacion a business services de la evidencia automatica.
     *
     * @param evidenceFile informacion de la evidencia
     * @param webService Webservice que proporciona la conectividad para la notificacion
     * @throws com.scopix.periscope.scheduler.exception.UploadJobException
     */
    public void notifyToAutomaticEvidenceManager(EvidenceFile evidenceFile, EvidenceWebService webService)
            throws UploadJobException {
        log.debug("start");
        try {

            // create DTO to send to the automatic web service            
            String dayDir = DateFormatUtils.format(evidenceFile.getEvidenceDate(), "yyyy-MM-dd HH:mm:ss");

            log.debug("dayDir: " + dayDir);
            log.debug("deviceId: " + evidenceFile.getEvidenceExtractionRequest().
                    getEvidenceProvider().getDeviceId());
            log.debug("fileName: " + evidenceFile.getFilename());
            log.debug("processId: " + evidenceFile.getEvidenceExtractionRequest().getProcessId());

            ExtractionPlan extractionPlanLocal = null;
            try {
                EServerDAO dao = SpringSupport.getInstance().findBeanByClassName(EServerDAO.class);
                //extractionPlanLocal = dao.getExtractionPlanEnable(params.get(UploadEvidenceCommand.UPLOAD_STORE));
                extractionPlanLocal = dao.getExtractionPlanEnable(getStoreName());
            } catch (ObjectRetrievalFailureException ex) {
                log.warn("Extraction Plan not loaded." + ex.getMessage());
            } catch (IndexOutOfBoundsException ex) {
                log.warn("Extraction Plan not loaded." + ex.getMessage());
            }

            if (extractionPlanLocal == null) {
                throw new UploadJobException("No se encuentar plan para store " + getStoreName());
            }
            NewAutomaticEvidenceDTO automaticEvidenceDTO = new NewAutomaticEvidenceDTO();
            automaticEvidenceDTO.setEvidenceDate(dayDir);
            automaticEvidenceDTO.setEvidenceProviderId(evidenceFile.getEvidenceExtractionRequest().getEvidenceProvider().
                    getDeviceId());
            automaticEvidenceDTO.setFileName(evidenceFile.getFilename());
            automaticEvidenceDTO.setProcessId(evidenceFile.getEvidenceExtractionRequest().getProcessId());
            automaticEvidenceDTO.setExtractionPlanServerId(extractionPlanLocal.getExtractionServer().getServerId());

//            List<HashMap<String, Integer>> situationMetricList = new ArrayList<HashMap<String, Integer>>();
            List<SituationMetricExtractionRequest> situationMetricExtractionRequests = HibernateSupport.getInstance().findDao(
                    EvidenceExtractionDAOImpl.class).getSituationMetricExtractionRequest(
                            evidenceFile.getEvidenceExtractionRequest().getId());
            log.debug("isEmpty: " + situationMetricExtractionRequests.isEmpty());
            Map<Integer, Set<Integer>> situationMetrics = new HashMap<Integer, Set<Integer>>();
            //nos aseguramos que sean unicos
            for (SituationMetricExtractionRequest smer : situationMetricExtractionRequests) {
                if (!situationMetrics.containsKey(smer.getSituationTemplateId())) {
                    situationMetrics.put(smer.getSituationTemplateId(), new HashSet<Integer>());
                }
                situationMetrics.get(smer.getSituationTemplateId()).add(smer.getMetricTemplateId());

//                HashMap<String, Integer> situationMetricMap = new HashMap<String, Integer>();
//                situationMetricMap.put("situation", smer.getSituationTemplateId());
//                situationMetricMap.put("metric", smer.getMetricTemplateId());
//                log.debug("situationMetricMap:" + situationMetricMap);
//                situationMetricList.add(situationMetricMap);
            }

//            log.debug("situationMetricList:" + situationMetricList.size());
//            automaticEvidenceDTO.setSituationMetricList(situationMetricList);
            List<SituationMetricsDTO> l = new ArrayList<SituationMetricsDTO>();
            for(Integer st: situationMetrics.keySet()){
                SituationMetricsDTO dto = new SituationMetricsDTO();
                dto.setSituationId(st);
                for (Integer mt: situationMetrics.get(st)) {
                    dto.getMetricIds().add(mt);
                }
                l.add(dto);
            }
            automaticEvidenceDTO.setSituationMetrics(l);

            log.debug("automaticEvidenceDTO.situationMetrics:" + automaticEvidenceDTO.getSituationMetrics());
            //call to web service
            webService.acceptAutomaticNewEvidence(automaticEvidenceDTO);
            log.debug("evidencia enviada " + evidenceFile.getFilename());

        } catch (ScopixWebServiceException e) {
            log.debug("Error notifing evidence manager." + e);
            throw new UploadJobException("Error notifing evidence manager.", e);
        } catch (ScopixException e) {
            log.debug("Error notifing evidence manager." + e);
            throw new UploadJobException("Error notifing evidence manager.", e);
        }
        log.debug("end [fileName:" + evidenceFile.getFilename() + "]");
    }

    /**
     * Se envia la notificacion a business services de la evidencia
     *
     * @param evidenceFile informacion de la evidencia
     * @param webservice Webservice que proporciona la conectividad para la notificacion
     * @throws com.scopix.periscope.scheduler.exception.UploadJobException
     */
    public void notifyToEvidenceManager(EvidenceFile evidenceFile, EvidenceWebService webservice) throws UploadJobException {
        log.debug("start [fileName:" + evidenceFile.getFilename() + "]");
        try {

            // create DTO to send to the web service
            String dayDir = DateFormatUtils.format(evidenceFile.getEvidenceDate(), "yyyy-MM-dd HH:mm:ss");

            NewEvidenceDTO newEvidenceDTO = new NewEvidenceDTO();
            newEvidenceDTO.setEvidenceDate(dayDir);
            newEvidenceDTO.setExtractionPlanDetailId(evidenceFile.getEvidenceExtractionRequest().getRemoteRequestId());
            newEvidenceDTO.setFilename(evidenceFile.getFilename());

            // call the web service
            webservice.acceptNewEvidence(newEvidenceDTO);
            log.debug("evidencia enviada " + evidenceFile.getFilename());
        } catch (ScopixWebServiceException e) {
            log.debug("Error notifing evidence manager." + e);
            throw new UploadJobException("Error notifing evidence manager.", e);
        }
        log.debug("end [fileName:" + evidenceFile.getFilename() + "]");
    }

    public void uploadEvidenceToExternalDevice() throws UploadJobException, IOException {
        log.debug("start");
        FileUtil fileUtil = SpringSupport.getInstance().findBeanByClassName(FileUtil.class);

        //verificando si estan creadas las carpetas de CORPORATE y STORE en disco. De no estarlo, se crean
        fileUtil.makeDirectory(getParams().get(UploadEvidenceCommand.ROOT_FOLDER)
                + getParams().get(UploadEvidenceCommand.UPLOAD_CORPORATE));
        fileUtil.makeDirectory(getParams().get(UploadEvidenceCommand.ROOT_FOLDER)
                + getParams().get(UploadEvidenceCommand.UPLOAD_CORPORATE) + "/"
                //                + getParams().get(UploadEvidenceCommand.UPLOAD_STORE));
                + getStoreName());

        //subiendo la evidencia
        uploadToEvidenceManager(getParams(), getEvidenceFile());

        //creando-actualizando archivos de control
        createControlFile();
        log.debug("end");
    }

    public void createControlFile() throws UploadJobException, IOException {
        log.debug("start");
        //FileUtil fileUtil = SpringSupport.getInstance().findBeanByClassName(FileUtil.class);
        FileUtil fileUtil = new FileUtil();
        DeviceUtil deviceUtil = SpringSupport.getInstance().findBeanByClassName(DeviceUtil.class);

        //creando el archivo de control con los datos obligatorios
        String dayDir = DateFormatUtils.format(getEvidenceFile().getEvidenceDate(), "yyyy/MM/dd");
        String dayDirFull = DateFormatUtils.format(getEvidenceFile().getEvidenceDate(), "yyyy-MM-dd HH:mm:ss");

        String fileNameTemp = getEvidenceFile().getFilename();
        String ctrlFileName = fileNameTemp.substring(0, fileNameTemp.indexOf(".")) + ".ctrl";

        String fileURL = getParams().get(UploadEvidenceCommand.UPLOAD_REMOTE_DIR) + dayDir
                + getRemoteFileManager().getRemotePathSeparator()
                + ctrlFileName;

        fileUtil.createFile(fileURL);

        //obligatorios
        fileUtil.writeLine("EVIDENCE_FILE_ID=" + getEvidenceFile().getId());
        fileUtil.writeLine("TYPE=" + getEvidenceFile().getEvidenceExtractionRequest().getType());
        fileUtil.writeLine("EVIDENCE_DATE=" + dayDirFull);
        fileUtil.writeLine("FILE_NAME=" + getEvidenceFile().getFilename());
        fileUtil.writeLine("EVIDENCE_WEBSERVICE_URL=" + getParams().get(UploadEvidenceCommand.EVIDENCE_WEBSERVICE_URL));
        fileUtil.writeLine("UPLOAD_REMOTE_DIR=" + getParams().get(UploadEvidenceCommand.UPLOAD_REMOTE_DIR_FOR_EXTERNAL_DEVICE));
        fileUtil.writeLine("UPLOAD_HOST=" + getParams().get(UploadEvidenceCommand.UPLOAD_HOST));
        fileUtil.writeLine("UPLOAD_USER=" + getParams().get(UploadEvidenceCommand.UPLOAD_USER));
        fileUtil.writeLine("UPLOAD_PASSWORD=" + getParams().get(UploadEvidenceCommand.UPLOAD_PASSWORD));
        //Esto indica que protocolo usar para subir la evidencia al sistema
        fileUtil.writeLine("UPLOAD_FILE_MANAGER_TYPE=" + getParams().get(UploadEvidenceCommand.PROTOCOL_TO_UPLOAD));
        fileUtil.writeLine("CORPORATE=" + getParams().get(UploadEvidenceCommand.UPLOAD_CORPORATE));
        //fileUtil.writeLine("STORE=" + getParams().get(UploadEvidenceCommand.UPLOAD_STORE));
        fileUtil.writeLine("STORE=" + getStoreName());

        if (getEvidenceFile().getEvidenceExtractionRequest().getType().equals(EvidenceRequestType.AUTO_GENERATED)) {
            notifyToAutomaticExternalDevice(getEvidenceFile(), fileUtil);
        } else {
            notifyToExternalDevice(getEvidenceFile(), fileUtil);
        }
        fileUtil.close();

        //creando o actualizando el archivo main.ctrl
        deviceUtil.writeMainControlFile(getParams().get(UploadEvidenceCommand.UPLOAD_CORPORATE)
                //                + getRemoteFileManager().getLocalPathSeparator() + getParams().get(UploadEvidenceCommand.UPLOAD_STORE)
                + getRemoteFileManager().getLocalPathSeparator() + getStoreName()
                + getRemoteFileManager().getLocalPathSeparator() + dayDir + getRemoteFileManager().getLocalPathSeparator());
        log.debug("end");
    }

    /**
     *
     * @param evidenceFile
     * @param fileUtil
     * @throws UploadJobException
     */
    public void notifyToAutomaticExternalDevice(EvidenceFile evidenceFile, FileUtil fileUtil)
            throws UploadJobException {
        log.debug("start");

        try {
            ExtractionPlan extractionPlanLocal = null;
            try {

                EServerDAO dao = SpringSupport.getInstance().findBeanByClassName(EServerDAO.class);
                //recuperamos el extractionPlan para el store
//                extractionPlanLocal = dao.getExtractionPlanEnable(params.get(UploadEvidenceCommand.UPLOAD_STORE));
                extractionPlanLocal = dao.getExtractionPlanEnable(getStoreName());
            } catch (ObjectRetrievalFailureException orfex) {
                log.error("Extraction Plan not loaded." + orfex.getMessage());
                throw new UploadJobException(orfex);
            } catch (IndexOutOfBoundsException iobex) {
                log.error("Extraction Plan not loaded." + iobex.getMessage());
                throw new UploadJobException(iobex);
            }

            log.debug("deviceId:" + evidenceFile.getEvidenceExtractionRequest().
                    getEvidenceProvider().getDeviceId());
            log.debug("processId:" + evidenceFile.getEvidenceExtractionRequest().getProcessId());
            log.debug("extractionPlanServerId:" + extractionPlanLocal.getExtractionServer().getServerId());

            fileUtil.writeLine("DEVICE_ID=" + evidenceFile.getEvidenceExtractionRequest().getEvidenceProvider().getDeviceId());
            fileUtil.writeLine("PROCESS_ID=" + evidenceFile.getEvidenceExtractionRequest().getProcessId());
            fileUtil.writeLine("EXTRACTION_PLAN_SERVER_ID=" + extractionPlanLocal.getExtractionServer().getServerId());

            List<SituationMetricExtractionRequest> situationMetricExtractionRequests = HibernateSupport.getInstance().findDao(
                    EvidenceExtractionDAOImpl.class).getSituationMetricExtractionRequest(
                            evidenceFile.getEvidenceExtractionRequest().getId());
            log.debug("isEmpty: " + situationMetricExtractionRequests.isEmpty());

            int contador = 1;
            for (SituationMetricExtractionRequest smer : situationMetricExtractionRequests) {
                fileUtil.writeLine("SITUATION" + contador + "=" + smer.getSituationTemplateId());
                fileUtil.writeLine("METRIC" + contador + "=" + smer.getMetricTemplateId());
                contador++;
            }

            log.debug("evidencia enviada " + evidenceFile.getFilename());

        } catch (IOException e) {
            log.debug("Error notifing external device." + e);
            throw new UploadJobException("IOError notifing external device", e);
        } catch (ScopixException e) {
            log.debug("Error notifing external device.", e);
            throw new UploadJobException("Error notifing external device.", e);
        }
        log.debug("end");
    }

    /**
     *
     * @param evidenceFile
     * @param fileUtil
     * @throws UploadJobException
     */
    public void notifyToExternalDevice(EvidenceFile evidenceFile, FileUtil fileUtil) throws UploadJobException {
        log.debug("start");
        try {

            log.debug("REMOTE_REQUEST_ID: " + evidenceFile.getEvidenceExtractionRequest().getRemoteRequestId());
            fileUtil.writeLine("REMOTE_REQUEST_ID=" + evidenceFile.getEvidenceExtractionRequest().getRemoteRequestId());

        } catch (IOException ioex) {
            log.debug("IOError notifing external device." + ioex.getMessage());
            throw new UploadJobException("Error notifing external device", ioex);
        }
        log.debug("end");
    }

    /**
     * @return the remoteFileManager
     */
    public FileManager getRemoteFileManager() {
        return remoteFileManager;
    }

    /**
     * @param remoteFileManager the remoteFileManager to set
     */
    public void setRemoteFileManager(FileManager remoteFileManager) {
        this.remoteFileManager = remoteFileManager;
    }

    /**
     * @return the evidenceFile
     */
    public EvidenceFile getEvidenceFile() {
        return evidenceFile;
    }

    /**
     * @param evidenceFile the evidenceFile to set
     */
    public void setEvidenceFile(EvidenceFile evidenceFile) {
        this.evidenceFile = evidenceFile;
    }

    /**
     * @return the params
     */
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    /**
     * @return the webservice
     */
    public EvidenceWebService getWebservice() {
        return webservice;
    }

    /**
     * @param webservice the webservice to set
     */
    public void setWebservice(EvidenceWebService webservice) {
        this.webservice = webservice;
    }

    /**
     * @return the storeName
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * @param storeName the storeName to set
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
