/*
 *
 * Copyright � 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * RejectedHistory.java
 *
 * Created on 13-03-2009, 11:31:21 AM
 *
 */
package com.scopix.periscope.qualitycontrol;

import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class RejectedHistory extends BusinessObject implements Comparable<RejectedHistory> {

    @Temporal(TemporalType.TIMESTAMP)
    private Date rejectDate;

    @ManyToOne
    private PendingEvaluation pendingEvaluation;

    @Lob
    private String rejectComment;

    public Date getRejectDate() {
        return rejectDate;
    }

    public void setRejectDate(Date rejectDate) {
        this.rejectDate = rejectDate;
    }

    public PendingEvaluation getPendingEvaluation() {
        return pendingEvaluation;
    }

    public void setPendingEvaluation(PendingEvaluation pendingEvaluation) {
        this.pendingEvaluation = pendingEvaluation;
    }

    public String getRejectComment() {
        return rejectComment;
    }

    public void setRejectComment(String rejectComment) {
        this.rejectComment = rejectComment;
    }

    public int compareTo(RejectedHistory o) {
        return this.getRejectDate().compareTo(o.getRejectDate());
    }
}
