/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 *
 *
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.EvidenceServicesServer;
import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.EvidenceRegionTransfer;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.PendingEvidenceRegionTransfers;
import com.scopix.periscope.evaluationmanagement.RegionServer;
import com.scopix.periscope.evaluationmanagement.dao.EvidenceRegionTransferDAO;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.FileUtils;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.scheduler.fileManager.FileManager;
import com.scopix.periscope.scheduler.fileManager.FileManagerFactory;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Sebastian
 */
public class TransferToRegionServerFTPCommand implements Runnable {

    private static Logger log = Logger.getLogger(TransferToRegionServerFTPCommand.class);
    private Evidence evidence;
    private RegionServer regionServer;
    private ObservedMetric observedMetric;
    private EvidenceRegionTransferDAO evidenceRegionTransferDAO;
    private PendingEvidenceRegionTransfers pendingEvidenceRegionTransfers;
    private EvidenceRegionTransfer evidenceRegionTransfer;
    private GenericDAO genericDAO;
    private Integer retries;

    public TransferToRegionServerFTPCommand(Evidence evidence, ObservedMetric observedMetric,
            RegionServer regionServer, EvidenceRegionTransfer evidenceRegionTransfer, Integer retries) {
        this.evidence = evidence;
        this.regionServer = regionServer;
        this.observedMetric = observedMetric;
        this.evidenceRegionTransfer = evidenceRegionTransfer;
        this.retries = retries;
    }

    private TransferToRegionServerFTPCommand() {
    }

    @Override
    public void run() {
        {
            log.debug("start");
            //getting region server again (becuase configuration might have change)
            regionServer = getGenericDAO().get(regionServer.getId(), RegionServer.class);
            if (regionServer.isActive()) {
                log.debug("Sending evidence to region server:" + regionServer.getCodeName());
                EvidenceServicesServer evidenceServicesServer = evidence.getEvidenceServicesServer();
                String sftpIp = regionServer.getsFTPIp();
                String sftpUser = regionServer.getsFTPUser();
                String sftpPassword = regionServer.getsFTPPassword();
                log.debug("SFTP: ip=" + sftpIp);
                String separator = "/";
                String sftpPath = regionServer.getsFTPPath();
                sftpPath += separator + observedMetric.getMetric().getArea().getStore().getCorporate().getName();
                sftpPath += separator + observedMetric.getMetric().getArea().getStore().getName();
                sftpPath += separator + FileUtils.getPathFromDate(evidence.getEvidenceDate(), separator);
                sftpPath += separator;
                sftpPath = FilenameUtils.separatorsToSystem(sftpPath);
                log.debug("Remote path: " + sftpPath);
                String separatorLocal = "/";
                String evidencePath = evidenceServicesServer.getLocalFilePath() + "";
                evidencePath += observedMetric.getMetric().getArea().getStore().getCorporate().getName();
                evidencePath += separatorLocal + observedMetric.getMetric().getArea().getStore().getName();
                evidencePath += separatorLocal + FileUtils.getPathFromDate(evidence.getEvidenceDate(), separatorLocal);
                evidencePath += separatorLocal + evidence.getEvidencePath();
                evidencePath = FilenameUtils.separatorsToSystem(evidencePath);
                log.debug("Local path: " + evidencePath);
                sendEvidence(sftpIp, sftpUser, sftpPassword, evidencePath, sftpPath, evidence.getEvidencePath());
            } else {
                log.info("Could not send evidence to region server:" + regionServer + ". The server is not active or the config is missing");
            }
            log.debug("end");

        }

    }

    private void sendEvidence(String sftpIp, String sftpUser, String sftpPassword, String localFile, String remotePath, String fileName) {
        log.info("start [localFile:" + localFile + "][remotePath:" + remotePath + "]");
        int count = 0;
        String remoteFile = remotePath + fileName;
        do {
            FileManager fileManager = FileManagerFactory.getFilemanager("SFTP2");
            try {
                fileManager.connectAndLogin(sftpIp, sftpUser, sftpPassword);
                log.debug("sent to alternative SFTP");
                fileManager.makeDirectoryRecursive(remotePath);
                fileManager.putFileResuming(localFile, remoteFile);
                getEvidenceRegionTransferDAO().updateById(evidenceRegionTransfer.getId(), Boolean.TRUE, "");
                getPendingEvidenceRegionTransfers().removeTransfer(evidenceRegionTransfer.getId());
                break;
            } catch (Exception ex) {
                log.error("Error trying to upload file. ", ex);
                if (count >= retries || Thread.currentThread().isInterrupted()) {
                    String messageError = "";
                    if (ex != null) {
                        messageError = ex.getMessage();
                    }
                    if (Thread.currentThread().isInterrupted()) {
                        log.error("The thread has timeout");
                        messageError = "THREAD TIMEOUT :" + messageError;
                    }
                    processFailedTransmission(messageError);
                    //deleteCorruptFile(sftpIp, sftpUser, sftpPassword, remoteFile);
                    break;
                }
                count++;
            } finally {
                try {
                    fileManager.disconnect();
                } catch (Exception ex) {
                    log.error("Error trying to disconect.", ex);
                }
            }
        } while (true);
        log.info("end");
    }

    private void processFailedTransmission(String messageError) {
        log.debug("Saving record error in transmition");
        getEvidenceRegionTransferDAO().updateById(evidenceRegionTransfer.getId(), Boolean.FALSE, messageError);
        getPendingEvidenceRegionTransfers().removeTransfer(evidenceRegionTransfer.getId());
    }

//    private void deleteCorruptFile(String sftpIp, String sftpUser, String sftpPassword, String serverFilePath) {
//        int count = 0;
//        do {
//            FileManager fileManager = FileManagerFactory.getFilemanager("SFTP2");
//            try {
//                fileManager.connectAndLogin(sftpIp, sftpUser, sftpPassword);
//                log.debug("deleting corrupt file to alternative SFTP: " + serverFilePath);
//                fileManager.deleteFile(serverFilePath);
//
//                break;
//            } catch (Exception ex) {
//                log.error("Error delete file. ", ex);
//                if (count >= 5) {
//                    break;
//                }
//                //esperamos 1 segundo antes de continuar
//                try {
//                    log.debug("wait " + count);
//                    Thread.currentThread().sleep(new Long(1000));
//                } catch (InterruptedException ex1) {
//                    log.warn("Unable to interrump thread. ", ex1);
//                }
//                count++;
//            } finally {
//                try {
//                    fileManager.disconnect();
//                } catch (Exception ex) {
//                    log.error("Error trying to disconect.", ex);
//                }
//            }
//        } while (true);
//        log.info("end");
//    }
    /**
     * @return the evidenceRegionTransferDAO
     */
    public EvidenceRegionTransferDAO getEvidenceRegionTransferDAO() {
        if (evidenceRegionTransferDAO == null) {
            evidenceRegionTransferDAO = SpringSupport.getInstance().findBeanByClassName(EvidenceRegionTransferDAO.class);
        }

        return evidenceRegionTransferDAO;
    }

    /**
     * @param evidenceRegionTransferDAO the evidenceRegionTransferDAO to set
     */
    public void setEvidenceRegionTransferDAO(EvidenceRegionTransferDAO evidenceRegionTransferDAO) {
        this.evidenceRegionTransferDAO = evidenceRegionTransferDAO;
    }

    /**
     * @return the pendingEvidenceRegionTransfers
     */
    public PendingEvidenceRegionTransfers getPendingEvidenceRegionTransfers() {
        if (pendingEvidenceRegionTransfers == null) {
            pendingEvidenceRegionTransfers = SpringSupport.getInstance().findBeanByClassName(PendingEvidenceRegionTransfers.class);
        }
        return pendingEvidenceRegionTransfers;
    }

    /**
     * @param pendingEvidenceRegionTransfers the pendingEvidenceRegionTransfers
     * to set
     */
    public void setPendingEvidenceRegionTransfers(PendingEvidenceRegionTransfers pendingEvidenceRegionTransfers) {
        this.pendingEvidenceRegionTransfers = pendingEvidenceRegionTransfers;
    }

    /**
     * @return the genericDAO
     */
    public GenericDAO getGenericDAO() {
        if (genericDAO == null) {
            genericDAO = HibernateSupport.getInstance().findGenericDAO();
        }
        return genericDAO;
    }

    /**
     * @param genericDAO the genericDAO to set
     */
    public void setGenericDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }
}
