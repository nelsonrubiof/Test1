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
 *  KeepAliveThread.java
 * 
 *  Created on 13-01-2014, 09:46:32 AM
 * 
 */
package com.scopix.periscope.cisco7.thread;

import com.scopix.periscope.cisco7.VsomServices;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import org.apache.log4j.Logger;

/**
 * Hilo que verificará cada cierto tiempo que la sesión esté activa
 * 
 * @author carlos polo
 */
public class KeepAliveThread extends Thread {
    
    private Long intervalInSecs;
    private boolean stop = false;
    private VsomServices vsomServices;
    private static Logger log = Logger.getLogger(KeepAliveThread.class);
    
    public KeepAliveThread(VsomServices vsomServices, Long intervalInSecs){
        log.info("start");
        this.vsomServices = vsomServices;
        this.intervalInSecs = intervalInSecs;
        log.info("end, intervalInSecs: [" + intervalInSecs + "]");
    }
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        log.info("start");
        while(!isStop()){
            try {
                Thread.sleep(getIntervalInSecs() * 1000);
                verifySession();

            } catch (ScopixException e) {
                log.error(e.getMessage(), e);
            } catch (InterruptedException e) {
                setStop(true);
                log.warn("interrumpiendo keepAliveThread", e);
            }
        }
        log.debug("end, hilo detenido");
    }
    
    /**
     * Verifica si la sesión está activa
     * 
     * @throws PeriscopeException 
     */
    public void verifySession() throws ScopixException {
        log.debug("start, verificando sesion");
        vsomServices.verifySession();
        log.debug("end");
    }

    /**
     * @return the vsomServices
     */
    public VsomServices getVsomServices() {
        return vsomServices;
    }

    /**
     * @param vsomServices the vsomServices to set
     */
    public void setVsomServices(VsomServices vsomServices) {
        this.vsomServices = vsomServices;
    }

    /**
     * @return the stop
     */
    public boolean isStop() {
        return stop;
    }

    /**
     * @param stop the stop to set
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }

    /**
     * @return the intervalInSecs
     */
    public Long getIntervalInSecs() {
        return intervalInSecs;
    }

    /**
     * @param intervalInSecs the intervalInSecs to set
     */
    public void setIntervalInSecs(Long intervalInSecs) {
        this.intervalInSecs = intervalInSecs;
    }
}