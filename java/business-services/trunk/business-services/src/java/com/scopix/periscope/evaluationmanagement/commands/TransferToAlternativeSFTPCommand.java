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
 * AddEvidenceCommand.java
 *
 * Created on 08-05-2008, 03:21:19 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.EvidenceServicesServer;
import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.periscopefoundation.util.FileUtils;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.io.IOException;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystem;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.Selectors;
import org.apache.commons.vfs.UserAuthenticator;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.auth.StaticUserAuthenticator;
import org.apache.commons.vfs.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class TransferToAlternativeSFTPCommand {

    private static Logger log = Logger.getLogger(TransferToAlternativeSFTPCommand.class);

    public void execute(Evidence evidence) {
        log.debug("start");
        EvidenceServicesServer evidenceServicesServer = evidence.getEvidenceServicesServer();
        if (evidenceServicesServer.getAlternativeMode()) {
            String sftpIp = evidenceServicesServer.getAlternativeSFTPip();
            String sftpUser = evidenceServicesServer.getAlternativeSFTPuser();
            String sftpPassword = evidenceServicesServer.getAlternativeSFTPpassword();
            log.debug("SFTP: ip=" + sftpIp + ", user=" + sftpUser + ", pwd=" + sftpPassword);
            String separator = (evidenceServicesServer.getAlternativeRemoteSFTPPath().indexOf("/") >= 0) ? "/" : "\\";
            String sftpPath = evidenceServicesServer.getAlternativeRemoteSFTPPath();
            ObservedMetric om = evidence.getObservedMetrics().get(0);
            sftpPath += om.getMetric().getArea().getStore().getCorporate().getName();
            sftpPath += separator + om.getMetric().getArea().getStore().getName();
            sftpPath += separator + FileUtils.getPathFromDate(evidence.getEvidenceDate(), separator) + separator;
            log.debug("Remote path: " + sftpPath);

            String separatorLocal = (evidenceServicesServer.getLocalFilePath().indexOf("/") >= 0) ? "/" : "\\";
            String evidencePath = evidenceServicesServer.getLocalFilePath();
            evidencePath += om.getMetric().getArea().getStore().getCorporate().getName();
            evidencePath += separatorLocal + om.getMetric().getArea().getStore().getName();
            evidencePath += separatorLocal + FileUtils.getPathFromDate(evidence.getEvidenceDate(), separatorLocal);
            evidencePath += separatorLocal + evidence.getEvidencePath();
            log.debug("Local path: " + evidencePath);

            TransferThread th = new TransferThread();
            th.init(sftpIp, sftpUser, sftpPassword, evidencePath, sftpPath, evidence.getEvidencePath(), evidence);
            th.start();
//            Thread t = new Thread(th);
//            t.start();            
        }
        log.debug("end");
    }

    class TransferThread extends Thread {
//    class TransferThread implements Runnable {

        private String sftpIp;
        private String sftpUser;
        private String sftpPassword;
        private String localFile;
        private String remotePath;
        private String remoteFileName;
        private Evidence evidence;

        public void init(String sftpIp, String sftpUser, String sftpPassword,
                String localFile, String remotePath, String remoteFileName, Evidence evidence) {
            log.debug("start");
            this.sftpIp = sftpIp;
            this.sftpUser = sftpUser;
            this.sftpPassword = sftpPassword;
            this.localFile = localFile;
            this.remotePath = remotePath;
            this.remoteFileName = remoteFileName;
            this.evidence = evidence;
            log.debug("end");
        }

        @Override
        public void run() {
            log.info("start");
            int count = 0;
            remotePath = "sftp://" + sftpIp + "/" + remotePath;
            String remoteFile = remotePath + remoteFileName;
            FileSystemManager manager = null;
            FileSystemOptions opts = new FileSystemOptions();
            do {
                try {
                    manager = VFS.getManager();
                    UserAuthenticator auth = new StaticUserAuthenticator(null, sftpUser, sftpPassword);
                    DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);
                    SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
                    //Crear Directorio
                    FileObject sftpFile = manager.resolveFile(remotePath, opts);
                    sftpFile.createFolder();

                    FileObject fileObject = manager.resolveFile(remoteFile, opts);
                    FileObject localFileObject = manager.resolveFile(localFile);
                    fileObject.copyFrom(localFileObject, Selectors.SELECT_SELF);
                    log.debug("sent to alternative SFTP");
                    evidence.setSentToAlternativeSFTP(Boolean.TRUE);
                    HibernateSupport.getInstance().findGenericDAO().save(evidence);
                    break;
                } catch (FileSystemException ex) {
                    try {
                        log.error("Error " + ex, ex);
                        evidence.setSentToAlternativeSFTP(Boolean.FALSE);
                        HibernateSupport.getInstance().findGenericDAO().save(evidence);
                        if (count >= 3) {
                            break;
                        }
                        count++;
                    } catch (Exception e) {
                        break;
                    }
                } finally {
                    FileObject sftpFile = null;
                    try {
                        sftpFile = manager.resolveFile(remotePath, opts);
                        FileSystem fs = null;
                        fs = sftpFile.getFileSystem();
                        manager.closeFileSystem(fs);
                    } catch (IOException ex) {
                        log.error("Error " + ex.getMessage(), ex);
                    }
                }
            } while (true);
            log.info("end");
        }
    }
}
