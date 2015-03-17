/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author nelson
 */
@Entity
public class SituationExtractionRequest extends BusinessObject {

    @ManyToOne    
    private SituationRequestRange situationRequestRange;
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date timeSample; //Formato HHmm

    public SituationRequestRange getSituationRequestRange() {
        return situationRequestRange;
    }

    public void setSituationRequestRange(SituationRequestRange situationRequestRange) {
        this.situationRequestRange = situationRequestRange;
    }

    public Date getTimeSample() {
        return timeSample;
    }

    public void setTimeSample(Date timeSample) {
        this.timeSample = timeSample;
    }

}
