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
 * BroadwareVideoExtractionRequest.java
 *
 * Created on 19-05-2008, 04:04:04 PM
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
public class BroadwareVideoExtractionRequest extends EvidenceExtractionRequestVideo {
    //EvidenceExtractionRequest

    private int lengthInSecs;

    /**
     *
     * @return int length in Seconds for video
     */
    @Override
    public int getLengthInSecs() {
        return lengthInSecs;
    }

    @Override
    public void setLengthInSecs(int lengthInSecs) {
        this.lengthInSecs = lengthInSecs;
    }

    /**
     *
     * @return Init Extraction Evidence
     */
    @Override
    public Date getExtractionStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getRequestedTime());
        cal.add(Calendar.SECOND, getLengthInSecs());
        //cal.add(Calendar.MINUTE, 3);

        //FIXME Si es necesario mantener Agregar a Properties de lo contrario estandarizar
        cal.add(Calendar.MINUTE, 30);

        return cal.getTime();
    }

    @Override
    public boolean getAllowsExtractionPlanToPast() {
        return true;
    }
}
