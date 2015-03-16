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
 *  SituationExtractionRequest.java
 * 
 *  Created on 06-06-2012, 01:43:17 PM
 * 
 */
package com.scopix.periscope.extractionmanagement;

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
