/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * RulesLog.java
 *
 * Created on 27-03-2008, 02:41:19 PM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author César Abarza Suazo
 */
@Entity
public class RulesLog extends BusinessObject {

    @ManyToOne
    private ObservedSituation observedSituation;

    @Lob
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    private Date logDate;

    public ObservedSituation getObservedSituation() {
        return observedSituation;
    }

    public void setObservedSituation(ObservedSituation observedSituation) {
        this.observedSituation = observedSituation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }
}
