package com.scopix.periscope.nextlevel.utilities;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.extractionmanagement.commands.ExtractEvidenceThread;
import com.scopix.periscope.nextlevel.NextLevelManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.File;
import org.apache.log4j.Logger;

/*
 * 
 * Copyright (c) 2012, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 * 
 * Clase que ejecuta las operaciones para descargar los archivos solicitados para la nueva versión
 * de integración entre EES y NextLevel
 *
 * @author    carlos polo
 * @created   22-oct-2012
 *
 */
public class NextLevel3GetClipFileThread extends ExtractEvidenceThread {

    private String pwd;
    private String file;
    private String user;
    private String storeName;
    private String urlGateway;
    private static Logger log = Logger.getLogger(NextLevel3GetClipFileThread.class);

    public NextLevel3GetClipFileThread(String nameNextLevel, String storeName, String urlGateway, String user, String pwd) {
        this.setName("NextLevel3GetClipFileThread" + nameNextLevel);
        this.file = nameNextLevel;
        this.storeName = storeName;
        this.urlGateway = urlGateway;
        this.user = user;
        this.pwd = pwd;
    }

    @Override
    public void run() {
        log.info("start [file:" + file + "]");
        //Recuperamos el file desde NextLevel
        try {
            File tmp = SpringSupport.getInstance().findBeanByClassName(NextLevelManager.class).getNextLevel3(storeName, urlGateway, user, pwd).getClip(file, storeName);

            if (tmp == null) {
                log.error("No se recibe file solicitado");
            } else {
                SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class).nextLevelVideoReady(file, tmp);
            }
        } catch (Exception e) {
            log.error("No es posible terminar proceso " + e, e);
        }
        log.info("end");
    }
}