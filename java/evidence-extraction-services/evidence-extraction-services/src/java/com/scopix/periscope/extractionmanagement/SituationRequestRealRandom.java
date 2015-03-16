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
 *  SituationRequestRealRandom.java
 * 
 *  Created on 31-08-2012, 05:57:45 PM
 * 
 */
package com.scopix.periscope.extractionmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author nelson
 */
@Entity
public class SituationRequestRealRandom extends BusinessObject {

    @Temporal(TemporalType.TIME)
    private Date requestedTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date executionTime;
    @ManyToOne
    private SituationRequestRange situationRequestRange;
    @Temporal(TemporalType.TIME)
    private Date initBlock;
    @Temporal(TemporalType.TIME)
    private Date endBlock;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    private String timeZoneId;

    public Date getRequestedTime() {
        return requestedTime;
    }

    public void setRequestedTime(Date requestedTime) {
        this.requestedTime = requestedTime;
    }

    public SituationRequestRange getSituationRequestRange() {
        return situationRequestRange;
    }

    public void setSituationRequestRange(SituationRequestRange situationRequestRange) {
        this.situationRequestRange = situationRequestRange;
    }

    public Date getInitBlock() {
        return initBlock;
    }

    public void setInitBlock(Date initBlock) {
        this.initBlock = initBlock;
    }

    public Date getEndBlock() {
        return endBlock;
    }

    public void setEndBlock(Date endBlock) {
        this.endBlock = endBlock;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }
}
