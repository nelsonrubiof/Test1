/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * BroadwareVideoExtractionCommand.java
 *
 * Created on 22-05-2008, 06:01:14 PM
 *
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.nextlevel.NextLevelManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author marko.perich
 */
public class NextLevelVideoExtractionThread extends ExtractEvidenceThread {

    private static Logger log = Logger.getLogger(NextLevelVideoExtractionThread.class);
    private Integer extractDuration;
    private String deviceID;
    private long startUTC;
    private boolean initialized;
    private Set<String> alternativesFileName;
    private String storeName;
    private String urlGateway;
    private String user;
    private String pass;

    public void init(long startUTC, Integer duration, String uuid, Set<String> alternativesFileName, String storeName,
            String urlGateway, String user, String pass) throws ScopixException {

        log.info("start [duration:" + duration + "][startUTC:" + startUTC + "][uuid:" + uuid + "]");

        this.extractDuration = duration;

        this.deviceID = uuid;
        this.startUTC = startUTC;
        this.storeName = storeName;
        this.alternativesFileName = alternativesFileName;
        this.urlGateway = urlGateway;
        this.user = user;
        this.pass = pass;

        this.setName(this.getClass().getSimpleName() + "-" + alternativesFileName);
        initialized = true;
        log.info("end");
    }

    @SuppressWarnings("static-access")
    @Override
    public void run() {
        log.info("start");
        try {
            if (initialized) {
                try {
                    callNextLevel();

                } catch (ScopixException ex) {
                    log.error("Error calling NextLevel.", ex);
                } catch (InterruptedException ex) {
                    log.error("Error Interrupting the Thread.", ex);
                }

            } else {
                log.debug("Thread not initialized. Call to init() before run()");
                throw new RuntimeException("Thread not initialized. Call to init() before run()");
            }
        } catch (Exception e) {
            log.error("No es posible terminar proceso fileName:" + alternativesFileName + " e:" + e, e);
        }
        log.info("end");
    }

    @SuppressWarnings("static-access")
    private void callNextLevel() throws ScopixException, InterruptedException {
        log.info("start");
        //la duracion llega en millisecons y el startUTC debe estar en nanosegundos
        long start = startUTC * 1000;
        long end = (startUTC + (extractDuration * 1000)) * 1000;

        String nameNextLevel = this.deviceID + "_" + start + "_" + end + ".flv";
        log.debug("[start:" + start + "][end:" + end + "][name:" + nameNextLevel + "]");
        if (!alternativesFileName.contains(nameNextLevel)) {
            SpringSupport.getInstance().findBeanByClassName(NextLevelManager.class).getNextLevel(urlGateway, user, pass).
                    generateClip(deviceID, start, end, storeName);
            alternativesFileName.add(nameNextLevel);
            //esperamos 5 seg antes de seguir
            Thread.currentThread().sleep(5000);
        }

        log.info("end");
    }
}
