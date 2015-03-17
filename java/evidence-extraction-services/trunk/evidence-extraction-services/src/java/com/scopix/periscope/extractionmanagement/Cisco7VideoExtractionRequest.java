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
 *  Cisco7VideoExtractionRequest.java
 * 
 *  Created on 02-10-2013, 05:00:54 PM
 * 
 */
package com.scopix.periscope.extractionmanagement;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class Cisco7VideoExtractionRequest extends EvidenceExtractionRequestVideo {

    private int lengthInSecs;

    public int getLengthInSecs() {
        return lengthInSecs;
    }

    public void setLengthInSecs(int lengthInSecs) {
        this.lengthInSecs = lengthInSecs;
    }
    
    @Override
    public Date getExtractionStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getRequestedTime());
        cal.add(Calendar.SECOND, getLengthInSecs());
        cal.add(Calendar.MINUTE, 3);

        return cal.getTime();
    }

    @Override
    public boolean getAllowsExtractionPlanToPast() {
        return true;
    }
}
