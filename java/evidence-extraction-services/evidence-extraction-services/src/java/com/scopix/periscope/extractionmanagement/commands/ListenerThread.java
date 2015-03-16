/*
 * Copyright (C) 2007, SCOPIX. All rights reserved. This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and disclosure. It is protected by copyright, patent
 * and other intellectual and industrial property laws. Copy, reverse engineering, disassembly or decompilation of all or part of
 * it, except to the extent required to obtain interoperability with other independently created software as specified by a
 * license agreement, is prohibited. 
 * 
 * ListenerThread.java 
 * Created on 10-01-2014, 04:51:20 PM
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.apache.log4j.Logger;
import org.glassfish.tyrus.client.ClientManager;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.extractionmanagement.ScopixListenerJob;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.vadaro.VadaroEvent;
import com.scopix.periscope.vadaro.VadaroParser;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 *
 * @author EmO
 */
public class ListenerThread extends ExtractEvidenceThread {

    private static Logger log = Logger.getLogger(ListenerThread.class);
    private int restartMinutesDelay;
    private ExtractionManager extractionManager;
    private ScopixListenerJob listener;
    private int reconnectSecondsDelay;
    Session session = null;
    private boolean keepAlive = true;
    CountDownLatch messageLatch = null;
    private final String basePath;

    public ListenerThread(int restartMinutesDelay, int reconnectSecondsDelay, ScopixListenerJob listener) {
        this.reconnectSecondsDelay = reconnectSecondsDelay;
        this.restartMinutesDelay = restartMinutesDelay;
        this.listener = listener;
        log.info("init thread " + this.getClass().getSimpleName() + "-" + listener.getStoreName() + "." + listener.getQueueName());
        this.setName(this.getClass().getSimpleName() + "-" + listener.getStoreName() + "." + listener.getQueueName());
        basePath = getExtractionManager().getStringProperties("UploadJob.uploadLocalDir") + "events/";
    }

    @Override
    public void run() {

        String URI = listener.getProtocol() + "://" + listener.getIp() + ":" + listener.getPort()
                + "/cgi-bin/rv_websocket?Service=Queue%20Counting";
        log.info("start, URI: [" + URI + "]");

        final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();
        while (keepAlive) {
            try {
                messageLatch = new CountDownLatch(1);

                ClientManager client = ClientManager.createClient();
                session = client.connectToServer(new Endpoint() {

                    @Override
                    public void onOpen(Session session, EndpointConfig config) {
                        session.addMessageHandler(new MessageHandler.Whole<String>() {

                            @Override
                            public void onMessage(String message) {
                                log.debug("Received message: " + message);
                                if (message.contains("QueueCounting")) {
                                    VadaroEvent parserEvent = VadaroParser.parserEvent(message, listener.getTimeZone());
                                    try {
                                        // validate if queue name is correct
                                        log.debug("event parser " + parserEvent.getName() + " listener queue "
                                                + listener.getQueueName());
                                        if (parserEvent.getName().equals(listener.getQueueName())) {
                                            // change provider in event for provider in system
                                            //save file
                                            try {
                                                String dateFileName = DateFormatUtils.format(parserEvent.getTime(),
                                                        "yyyyMMdd_HHmmss.SSS");
                                                FileUtils.writeStringToFile(
                                                        new File(basePath + dateFileName + "_"
                                                                + listener.getProviderName() + ".xml"),
                                                        message,
                                                        "UTF-8");
                                            } catch (IOException e) {
                                                log.error("Not save File " + e);
                                            }
                                            parserEvent.setName(listener.getProviderName());
                                            getExtractionManager().injectEventVadaroToEvidenceRequest(parserEvent);

                                            //Validar si es abandomend para 
                                            if (parserEvent.getAbandoned() > 0) {
                                                getExtractionManager().injectEventVadaroAbandnedToEvidenceRequest(parserEvent.getTime(), parserEvent.getName());
                                            }
                                        } else {
                                            log.warn("event not valid " + message);
                                        }
                                    } catch (ScopixException e) {
                                        log.error("Error injecting evidence for message: " + message, e);
                                    }
                                }
                            }
                        });

                    }
                }, cec, new URI(URI));
                messageLatch.await(restartMinutesDelay, TimeUnit.MINUTES);
            } catch (InterruptedException ie) {
                keepAlive = false;
                log.debug("Thread interrupted: " + listener, ie);

            } catch (Exception e) {
                log.debug("Error on listener: " + listener, e);
                try {
                    messageLatch.await(reconnectSecondsDelay, TimeUnit.SECONDS);
                } catch (InterruptedException e1) {
                    keepAlive = false;
                    log.debug("Error on waiting a restart: " + listener, e1);
                }
            } finally {
                shutdown();
            }
            log.debug("Restart: " + (new Date()) + " on: " + URI);
        }
    }

    public void shutdown() {
        log.debug("Start");
        if (session != null && session.isOpen()) {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Restarting Listener."));
                log.debug("Session closed.");
            } catch (IOException e) {
                log.debug("Error on closing on restart: " + listener, e);
            }
        }
        log.debug("End");
    }

    @Override
    public void interrupt() {
        kill();
        super.interrupt();
    }

    public ExtractionManager getExtractionManager() {
        if (extractionManager == null) {
            extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
        }
        return extractionManager;
    }

    public void kill() {
        log.debug("Start");
        keepAlive = false;
        messageLatch.countDown();
        log.debug("End");
    }
}
