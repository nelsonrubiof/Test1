/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionmanagement;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author nelson
 */
@Entity
public class CognimaticsPeopleCounter212ExtractionRequest extends EvidenceExtractionRequest {

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
