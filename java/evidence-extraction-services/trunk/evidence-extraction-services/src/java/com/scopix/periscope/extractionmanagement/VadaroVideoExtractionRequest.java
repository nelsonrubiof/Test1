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
 *  VadaroImageExtractionRequest.java
 * 
 *  Created on 09-07-2014, 04:54:58 PM
 * 
 */
package com.scopix.periscope.extractionmanagement;

import java.util.Date;
import javax.persistence.Entity;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Nelson
 */
@Entity
public class VadaroVideoExtractionRequest extends EvidenceExtractionRequest {

    private static final Logger log = Logger.getLogger(VadaroVideoExtractionRequest.class);
    private int lengthInSecs;

    @Override
    public Date getExtractionStartTime() {
        log.info("start");
        Date ret = this.getRequestedTime();
        ret = DateUtils.addSeconds(ret, getLengthInSecs());
        log.info("end, [ret:" + ret + "]");
        return ret;

    }

    @Override
    public boolean getAllowsExtractionPlanToPast() {
        return false;
    }

    /**
     * @return the lengthInSecs
     */
    public int getLengthInSecs() {
        return lengthInSecs;
    }

    /**
     * @param lengthInSecs the lengthInSecs to set
     */
    public void setLengthInSecs(int lengthInSecs) {
        this.lengthInSecs = lengthInSecs;
    }

}
