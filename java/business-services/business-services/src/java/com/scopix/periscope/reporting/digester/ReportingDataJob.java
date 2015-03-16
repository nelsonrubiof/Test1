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
 *  ReportingDataJob.java
 * 
 *  Created on 14-02-2011, 06:28:46 PM
 * 
 */
package com.scopix.periscope.reporting.digester;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.ReportingManager;
import com.scopix.periscope.reporting.UploadProcess;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author nelson
 */
public class ReportingDataJob implements Job {

    private static Logger log = Logger.getLogger(ReportingDataJob.class);

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        log.debug("start");
        ReportingDigester digester = SpringSupport.getInstance().findBeanByClassName(ReportingDigester.class);
        Boolean active = false;
        try {
            ReportingManager manager = SpringSupport.getInstance().findBeanByClassName(ReportingManager.class);
            //se coloca default por si no existe el archivo o la propiedad definida
            active = digester.getConfiguration().getBoolean("uploading.state.active", Boolean.FALSE);
            //si no esta activo no hacemos nada   
            if (active) {
                //verificasmos que no este corriendo un processo
                if (manager.getProcessRunning() == null) {
                    // se verifica si existe nueva data para subir
                    if (manager.existNewDataUploading()) {
                        //recuperamos el proceso agendado si existe, si no existe creamos uno nuevo
                        UploadProcess upScheduled = manager.isUploadProcessScheduled();
                        if (upScheduled == null) {
                            //crear proceso todas tiendas todas las areas
                            manager.createNewUploadProcess();
                        }
                        //ejecutamos la subida automatica por sistema
                        manager.uploadAutomaticNow();
                    } else {
                        log.info("No existe nueva data por subir");
                    }
                } else {
                    log.info("Existe un proceso en estado RUNNING no se hace nada");
                }
            }
        } catch (ScopixException e) {
            log.error("ScopixException " + e, e);
        } 
        log.debug("end");
    }
}
