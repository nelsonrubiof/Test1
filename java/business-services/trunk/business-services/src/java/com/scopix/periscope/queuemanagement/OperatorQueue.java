/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * OperatorQueue.java
 *
 * Created on 23-03-2010, 01:56:14 PM
 *
 */
package com.scopix.periscope.queuemanagement;

import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import javax.persistence.Entity;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class OperatorQueue extends BusinessObject implements Comparable<OperatorQueue> {

    @Lob
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(mappedBy = "operatorQueue")
    private List<OperatorQueueDetail> operatorQueueDetailList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operatorQueue", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
        CascadeType.REMOVE})
    private List<PendingEvaluation> pendingEvaluations;
    private Boolean activo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date inputDate) {
        this.creationDate = inputDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public List<OperatorQueueDetail> getOperatorQueueDetailList() {
        return operatorQueueDetailList;
    }

    public void setOperatorQueueDetailList(List<OperatorQueueDetail> operatorQueueDetailList) {
        this.operatorQueueDetailList = operatorQueueDetailList;
    }

    /**
     * @return the pendingEvaluations
     */
    public List<PendingEvaluation> getPendingEvaluations() {
        return pendingEvaluations;
    }

    /**
     * @param pendingEvaluations the pendingEvaluations to set
     */
    public void setPendingEvaluations(List<PendingEvaluation> pendingEvaluations) {
        this.pendingEvaluations = pendingEvaluations;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public int compareTo(OperatorQueue o) {
        return this.getId() - o.getId();
    }
}
