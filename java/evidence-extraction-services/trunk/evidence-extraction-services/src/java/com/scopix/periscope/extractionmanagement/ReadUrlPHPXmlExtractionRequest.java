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
 * ReadUrlPHPXmlExtractionRequest.java
 *
 * Created on 16-08-2010, 04:06:35 PM
 *
 */
package com.scopix.periscope.extractionmanagement;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author Nelson Rubio
 * @version 1.0.0
 */
@Entity
public class ReadUrlPHPXmlExtractionRequest extends EvidenceExtractionRequest {

   public static final int DELAY = 18; //se cambia para obtener  evidencias cada 15 min orig 65

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
