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
 *  CiscoPeopleCountingExtractionRequest.java
 * 
 *  Created on 06-02-2014, 04:52:30 PM
 * 
 */

package com.scopix.periscope.extractionmanagement;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import org.apache.log4j.Logger;

/**
 *
 * @author carlos polo
 */
@Entity
public class CiscoPeopleCountingExtractionRequest extends EvidenceExtractionRequest {

    public static final int DELAY = 18;
    private static Logger log = Logger.getLogger(CiscoPeopleCountingExtractionRequest.class);

    @Override
    public Date getExtractionStartTime() {
        log.info("start");
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getRequestedTime());
        cal.add(Calendar.MINUTE, DELAY);

        log.info("end, cal.getTime(): [" + cal.getTime() + "]");
        return cal.getTime();
    }

    @Override
    public boolean getAllowsExtractionPlanToPast() {
        return true;
    }
}