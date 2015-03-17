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
 *  BroadwareHTTPVideoFileReadyThread.java
 * 
 *  Created on 04-07-2013, 10:08:06 AM
 * 
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * Recupera un file via http desde un servidor con Broadware
 *
 * @author nelson
 * @version 1.0.0
 */
public class BroadwareHTTPVideoFileReadyThread extends ExtractEvidenceThread {

    private static Logger log = Logger.getLogger(BroadwareHTTPVideoFileReadyThread.class);
    private boolean initialized;
    private String broadwareHTTPURL;
    private Integer evidenceId;
    private String storeName;
    private String broadwareFile;

    @Override
    public void run() {
        log.info("start");
        try {
            if (initialized) {
                File tmp = getFile(broadwareHTTPURL);
                //una vez descargado el file solicitamos al manager que almacene los dato y lo mueva a processed
                if (tmp != null) {
                    SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class).
                            broadwareHTTPDownloadFile(evidenceId, storeName, tmp, broadwareFile);
                } else {
                    log.error("no es posible recuperar archivo " + broadwareFile);
                }
            }
        } catch (Exception e) {
            log.error("No es posible terminar la ejecucion " + e, e);
        }
        log.info("end");
    }

    /**
     *
     * @param broadwareHTTPURL url a invocar
     * @param evidenceId id de la evidencia asociada
     * @param storeName store para el cual se desea recuperar evidencia
     * @param broadwareFile nombre original en broadware, necesario para el proceso de borrado
     */
    public void init(String broadwareHTTPURL, Integer evidenceId, String storeName, String broadwareFile) {
        log.info("start");
        this.broadwareHTTPURL = broadwareHTTPURL;
        this.broadwareFile = broadwareFile;
        this.evidenceId = evidenceId;
        this.storeName = storeName;
        this.setName(this.getClass().getName() + "-" + this.evidenceId);
        initialized = true;
        log.info("end");
    }

    private File getFile(String urlStr) {
        log.info("start [url:" + urlStr + "]");
        File tmp;
        HttpClient client = new DefaultHttpClient();
        try {
            //creamos el file temporal donde recibiremos el response
            tmp = File.createTempFile("tempbwm", ".bwm");
            OutputStream out = new FileOutputStream(tmp);
            HttpGet get = new HttpGet(urlStr);
            //pedimos el archivo
            HttpResponse response = client.execute(get);
            HttpEntity resEntity = response.getEntity();
            byte[] buf = new byte[1];
            int len;
            while ((len = resEntity.getContent().read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            EntityUtils.consume(resEntity);
        } catch (IOException e) {
            log.error("Error en peticion getFile " + e, e);
            tmp = null;
        } finally {
            //cerramos la conexion
            client.getConnectionManager().shutdown();
        }
        log.info("end [url:" + urlStr + "]");
        return tmp;
    }
}
