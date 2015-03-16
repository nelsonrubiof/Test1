/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  TransferProofsCommand.java
 * 
 *  Created on 26-07-2011, 11:34:13 AsM
 * 
 */
package com.scopix.periscope.businesswarehouse.transfer.commands;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.hibernate.exception.JDBCConnectionException;

import com.scopix.periscope.businesswarehouse.transfer.TransferProofFilesLog;
import com.scopix.periscope.businesswarehouse.transfer.TransferProofFilesStatus;
import com.scopix.periscope.businesswarehouse.transfer.dao.TransferBWHibernateDAO;
import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import com.scopix.periscope.businesswarehouse.transfer.utils.ZipCompresser;
import com.scopix.periscope.managergui.transfer.services.webservices.client.TransferProofsWebServicesClient;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.FileUtils;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.scheduler.fileManager.FileManager;
import com.scopix.periscope.scheduler.fileManager.FileManagerFactory;

/**
 *
 * @author Gustavo Alvarez
 */
public class TransferProofsCommand {

    private TransferHibernateDAO dao;
    private TransferBWHibernateDAO daoBW;
    private FileManager fileManager;
    private TransferProofsWebServicesClient transferProofWebServicesClient;
    private Logger log = Logger.getLogger(TransferProofsCommand.class);
    private final String yyyyMMdd = "yyyyMMdd";

    /**
     * Encargada de transferir los proofs (imagenes) al servidor destino
     * 
     * @param corporateName Nombre del cliente
     * @param endDate Fecha final del traspaso
     * @param uploadPivotDate Fecha inicial o pivote a tomar en cuenta para la transferencia
     * @param compressBasePath Ruta al directorio base de los archivos a comprimir
     * @param compressPrefix Prefijo de los archivos comprimidos generados
     * @param compressDestinationPath Ruta al directorio destino donde quedaran los archivos comprimidos
     * @param remotePath Ruta en el servidor destino donde quedaran los archivos comprimidos
     * @param host IP servidor destino
     * @param user Usuario para el traspaso por sftp
     * @param password Password del usuario para el traspaso
     * @throws PeriscopeException 
     */
    public void execute(String corporateName, Date endDate, Date uploadPivotDate, String compressBasePath, String compressPrefix,
            String compressDestinationPath, String remotePath, String host, String user, String password) 
            throws ScopixException {
        log.info("start");
        try {

            log.debug("endDate: " + endDate.toString());
            log.debug("uploadPivotDate: " + uploadPivotDate.toString());
            log.debug("compressBasePath: " + compressBasePath);
            log.debug("compressPrefix: " + compressPrefix);
            log.debug("compressDestinationPath: " + compressDestinationPath);
            log.debug("remotePath: " + remotePath);
            log.debug("host: " + host);
            log.debug("user: " + user);

            setFileManager(FileManagerFactory.getFilemanager("SFTP"));

            getFileManager().connectAndLogin(host, user, password);
            log.info("conectado");

            //verificar si existen archivos pendientes por subir. De existir, encolarlos
            List<TransferProofFilesLog> list = getDao().getPendingFilesToTransfer();
            log.debug("TransferProofFilesLog list size: " + list.size());

            if (list != null && list.size() > 0) {
                log.info("archivos por encolar: " + list.size());
                for (TransferProofFilesLog tpf : list) {
                    log.debug("enviando archivo: " + tpf.getFileName());
                    getFileManager().putFile(compressDestinationPath + tpf.getFileName(),
                            remotePath + tpf.getFileName());
                    log.debug("archivo enviado");

                    //encolar llamando al webservice:  lista id's proofs, nombre archivo comprimido
                    getTransferProofWebServicesClient().getWebService().addTransferLog(tpf.getFileName(), tpf.getProofsIds());
                    log.debug("encolado");

                    if (tpf != null && tpf.getProofsIds().size() > 0) {
                        //actualizar proof
                        getDao().updateProofSentToMis(tpf.getProofsIds(), Boolean.TRUE);
                    } else {
                        log.warn("TransferProofFileLog sin proofs asociados. tpf_id = " + tpf.getId());
                    }

                    //actualizar estado
                    getDao().updateTransferProofFilesLogStatus(tpf.getId(), TransferProofFilesStatus.FINISHED);
                    log.debug("estado de transferProofsFiles actualizado");

                    try {
                        //eliminar el/los archivo comprimido
                        org.apache.commons.io.FileUtils.forceDelete(
                                new File(compressDestinationPath + tpf.getFileName()));
                        log.debug("eliminado");
                    } catch (IOException ex) {
                        log.error(ex.getMessage(), ex);
                    }
                }
            }

            Date initTransferProofDate = getDao().getInitialTransferDateForProofFiles(endDate, uploadPivotDate);
            if (initTransferProofDate == null) {
                log.debug("initTransferProofDate is null, exiting...");
                return;
            }
            log.info("initTransferProofDate: " + initTransferProofDate.toString());
            Calendar calIterator = Calendar.getInstance();
            calIterator.setTime(initTransferProofDate);
            Map<Integer, Set<String>> proofToCompress = null;
            Set<String> proofPaths = null;
            List<TransferProofFilesLog> compressedFiles = null;
            log.debug("upload_pivot_date: " + uploadPivotDate.toString());
            while (Integer.valueOf(DateFormatUtils.format(calIterator.getTime(), yyyyMMdd)).intValue()
                    <= Integer.valueOf(DateFormatUtils.format(endDate, yyyyMMdd)).intValue()) {
                log.info("procesando fecha: " + calIterator.getTime().toString());
                List<ProofBW> proofBW = getDao().getTransferProofList(calIterator.getTime(), uploadPivotDate);
                log.info("proof size: " + proofBW.size());

                String prePath = corporateName + "/";
                String datePath = FileUtils.getPathFromDate(calIterator.getTime(), "/");
                proofToCompress = new HashMap<Integer, Set<String>>();
                String pathProof = null;
                for (ProofBW p : proofBW) {
                    pathProof = prePath + p.getStoreName() + "/" + datePath + "/";
                    proofPaths = new HashSet<String>();
                    proofPaths.add(pathProof + p.getPathWithMarks());
                    proofPaths.add(pathProof + p.getPathWithoutMarks());
                    proofToCompress.put(p.getId(), proofPaths);
                }

                //comprimir archivos.
                //compressBasePath: ubicacion base de los proofs
                //compressDestinyPath: ubicación destino de los archivos comprimidos
                compressedFiles = compressFiles(proofToCompress, compressBasePath,
                        compressPrefix + "_" + DateFormatUtils.format(calIterator, "yyyyMMdd"), compressDestinationPath);
                log.info("archivos comprimidos: " + compressedFiles.size());

                try {
                    //conectar
                    if (compressedFiles != null) {
                        //se transmiten el/los archivos generados por la compresion
                        for (TransferProofFilesLog tpf : compressedFiles) {
                            //enviar por sftp
                            getFileManager().putFile(compressDestinationPath + tpf.getFileName(),
                                    remotePath + tpf.getFileName());
                            log.info("transferido");

                            //encolar por WS
                            getTransferProofWebServicesClient().getWebService().
                                    addTransferLog(tpf.getFileName(), tpf.getProofsIds());
                            log.info("encolado");

                            if (tpf != null && tpf.getProofsIds().size() > 0) {
                                //actualizar proof
                                getDao().updateProofSentToMis(tpf.getProofsIds(), Boolean.TRUE);
                            } else {
                                log.warn("TransferProofFileLog sin proofs asociados. tpf_id = " + tpf.getId());
                            }

                            //actualizar estado
                            getDao().updateTransferProofFilesLogStatus(tpf.getId(), TransferProofFilesStatus.FINISHED);
                            log.info("estado de transferProofsFiles actualizado");

                            //eliminar el(los) archivo(s) comprimido(s)
                            org.apache.commons.io.FileUtils.forceDelete(
                                    new File(compressDestinationPath + tpf.getFileName()));
                            log.info("eliminado");
                        }
                    }
                } catch (ScopixException e) {
                    log.error("no se pudo transferir el archivo. ", e);
                } catch (IOException ioex) {
                    log.error("error eliminando el archivo", ioex);
                }

                calIterator.add(Calendar.DATE, 1);
            }
        } catch (JDBCConnectionException e) {
            log.error(e, e);
            throw new ScopixException(e);
        } finally {
            getFileManager().disconnect();
        }
        log.info("end");
    }

    /**
     * Función que comprime los archivos.
     * 
     * @param map
     * @param basePath
     * @param prefix
     * @param destinationPath
     * @return 
     */
    private List<TransferProofFilesLog> compressFiles(Map<Integer, Set<String>> map,
            String basePath, String prefix, String destinationPath) {
        List<TransferProofFilesLog> res = null;
        ZipCompresser zipManager = new ZipCompresser(basePath, prefix, destinationPath, map, null);

        try {
            res = zipManager.compress();
        } catch (Exception e) {
            log.error("no se pueden comprimir los archivos.", e);
        }

        return res;
    }

    public TransferHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(TransferHibernateDAO.class);
        }
        return dao;
    }

    public void setDao(TransferHibernateDAO dao) {
        this.dao = dao;
    }

    public TransferBWHibernateDAO getDaoBW() {
        if (daoBW == null) {
            daoBW = SpringSupport.getInstance().findBeanByClassName(TransferBWHibernateDAO.class);
        }
        return daoBW;
    }

    public void setDaoBW(TransferBWHibernateDAO daoBW) {
        this.daoBW = daoBW;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public TransferProofsWebServicesClient getTransferProofWebServicesClient() {
        if (transferProofWebServicesClient == null) {
            transferProofWebServicesClient =
                    SpringSupport.getInstance().findBeanByClassName(TransferProofsWebServicesClient.class);
        }
        return transferProofWebServicesClient;
    }

    public void setTransferProofWebServicesClient(TransferProofsWebServicesClient transferProofWebServicesClient) {
        this.transferProofWebServicesClient = transferProofWebServicesClient;
    }
}
