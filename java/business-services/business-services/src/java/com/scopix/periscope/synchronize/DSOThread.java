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
 *  DSRThread.java
 * 
 *  Created on 07-03-2014, 03:11:26 PM
 * 
 */
package com.scopix.periscope.synchronize;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.apache.log4j.Logger;

/**
 *
 * @author Nelson
 */
public class DSOThread extends Thread {

    private static Logger log = Logger.getLogger(DSOThread.class);
    private int observedSituationId;
    private DSOExternalApplication dso;

    public void init(int observedSituationid) {
        this.setObservedSituationId(observedSituationid);
    }

    @Override
    public void run() {
        log.info("start");
        try {
             getDso().sendData(getObservedSituationId());
        } catch (ScopixException e) {
            log.warn("could not send data to dso " + e);
        }
        log.info("end");
    }

    /**
     * @return the observedSituationId
     */
    public int getObservedSituationId() {
        return observedSituationId;
    }

    /**
     * @param observedSituationId the observedSituationId to set
     */
    public void setObservedSituationId(int observedSituationId) {
        this.observedSituationId = observedSituationId;
    }

    /**
     * @return the dso
     */
    public DSOExternalApplication getDso() {
        if (dso == null) {
            dso = SpringSupport.getInstance().findBeanByClassName(DSOExternalApplication.class);
        }
        return dso;
    }

    /**
     * @param dso the dso to set
     */
    public void setDso(DSOExternalApplication dso) {
        this.dso = dso;
    }
}
