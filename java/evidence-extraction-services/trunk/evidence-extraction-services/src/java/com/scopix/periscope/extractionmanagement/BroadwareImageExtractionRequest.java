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
 * BroadwareImageExtractionRequest.java
 *
 * Created on 19-05-2008, 04:07:59 PM
 *
 */
package com.scopix.periscope.extractionmanagement;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import org.apache.log4j.Logger;

/**
 *
 * @author marko.perich
 */
@Entity
public class BroadwareImageExtractionRequest extends EvidenceExtractionRequest {

    private static Logger log = Logger.getLogger(BroadwareVideoExtractionRequest.class);

    @Override
    public Date getExtractionStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getRequestedTime());
        cal.add(Calendar.MINUTE, 1);

        return cal.getTime();
    }

    @Override
    public boolean getAllowsExtractionPlanToPast() {
        return true;
    }
}
