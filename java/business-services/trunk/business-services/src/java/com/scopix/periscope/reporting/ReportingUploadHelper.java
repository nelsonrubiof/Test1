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
 *  ReportingUploadHelper.java
 * 
 *  Created on 15-02-2011, 09:13:09 AM
 * 
 */
package com.scopix.periscope.reporting;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.transfer.ReportingUploadTransferManager;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class ReportingUploadHelper {

    private static Logger log = Logger.getLogger(ReportingUploadHelper.class);
    private UploadProcess process;
    private ReportingUploadTransferManager transferManager;

    public void init(UploadProcess process) {
        setProcess(process);
    }

    public void execute() {
        //ejecutamos el envio del UploadProcess inicalizado
        if (getProcess() != null) {
            try {
                getTransferManager().setCancelProcess(false);
                getProcess().setComments("Init Process\n");
                getTransferManager().validateConnection();
                //Traspasamos los datos genericos
                getTransferManager().revisionDateHierarchy(getProcess());
                //stores
                getProcess().setComments(getProcess().getComments() + "Transfer Stores\n");
                getTransferManager().transferStores(getProcess());

                getProcess().setComments(getProcess().getComments() + "Transfer Product\n");
                getTransferManager().transferProduct(getProcess());

                getProcess().setComments(getProcess().getComments() + "Transfer Metric Templates\n");
                getTransferManager().transferMetricTemplate(getProcess());

                getProcess().setComments(getProcess().getComments() + "Transfer Area Type\n");
                getTransferManager().transferAreaType();
                
                getProcess().setComments(getProcess().getComments() + "Transfer Product and Areas\n");
                getTransferManager().transferProductAndAreas(getProcess());
                
                //preparamos los datos necesarios para el proceso
                getProcess().setComments(getProcess().getComments() + "Preparing Data Process\n");
                getTransferManager().prepareDataForProcess();
                getProcess().setComments(getProcess().getComments() + "Sending\n");
                //por cada detalle enviamos
                for (UploadProcessDetail detail : getProcess().getProcessDetails()) {
                    if (detail.getTotalRecords() == 0) {
                        //no ejecutamos nada para agilizar el proceso
                        continue;
                    }
                    Integer totProcessUpload = getProcess().getTotalUpload();
                    getTransferManager().transferReportingData(detail);
                    if (totProcessUpload + detail.getUpRecords() != getProcess().getTotalUpload()) {
                        getProcess().setTotalUpload(totProcessUpload + detail.getUpRecords());
                    }
                }
                getTransferManager().cleanDataForProcess();
                getProcess().setComments(getProcess().getComments() + "Process Completed\n");
                getProcess().setProcessState(ProcessState.FINISHED);
                getProcess().setMotiveClosing(MotiveClosing.OK);
            } catch (ScopixException e) {
                if (e.getMessage().equals("MANUAL")) {
                    getProcess().setMotiveClosing(MotiveClosing.MANUAL);
                    getProcess().setComments(getProcess().getComments() + "Cancel " + getProcess().getMotiveClosing());
                } else {
                    getProcess().setMotiveClosing(MotiveClosing.ERROR);
                    getProcess().setComments(getProcess().getComments() + getProcess().getMotiveClosing() + "\n" + e);
                }
                getProcess().setProcessState(ProcessState.FINISHED);
                log.error(e, e);
            } catch (Exception e) {
                getProcess().setProcessState(ProcessState.FINISHED);
                getProcess().setMotiveClosing(MotiveClosing.ERROR);
                getProcess().setComments(getProcess().getComments() + getProcess().getMotiveClosing() + "\n" + e);
                log.error(e, e);
            } finally {
                GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
                getProcess().setEndDateProcess(new Date());
                dao.save(getProcess());
                ReportingManager rm = SpringSupport.getInstance().findBeanByClassName(ReportingManager.class);
                rm.setProcessRunning(null);
                setProcess(null);
            }
        }
    }

    public UploadProcess getProcess() {
        return process;
    }

    public void setProcess(UploadProcess process) {
        this.process = process;
    }

    public ReportingUploadTransferManager getTransferManager() {
        if (transferManager == null) {
            transferManager = SpringSupport.getInstance().findBeanByClassName(ReportingUploadTransferManager.class);
        }
        return transferManager;
    }

    public void setTransferManager(ReportingUploadTransferManager transferManager) {
        this.transferManager = transferManager;
    }
}
