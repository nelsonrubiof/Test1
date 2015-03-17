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
public class CognimaticsPeopleCounter141ExtractionRequest extends EvidenceExtractionRequest {

    private static Logger log = Logger.getLogger(CognimaticsPeopleCounter141ExtractionRequest.class);
    public static final int DELAY = 18; //se cambia para obtener  evidencias cada 15 min min orig 65

    @Override
    public Date getExtractionStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getRequestedTime());
        // se le agregan 65 minutos de desface para lograr que la evidencia de las 11:00 sea entre las 11 y las 12
        cal.add(Calendar.MINUTE, DELAY);

        return cal.getTime();
    }

    @Override
    public boolean getAllowsExtractionPlanToPast() {
        return true;
    }
}
