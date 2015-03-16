/*
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
 *  UploadProcess.java
 *  
 *  Created on 11-01-2011, 10:57:57 AM
 */
package com.scopix.periscope.reporting;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Nelson Rubio
 */
@Entity
public class UploadProcess extends BusinessObject {

    @Temporal(value = TemporalType.DATE)
    private Date dateProcess;
    private String loginUser;
    @Enumerated(value = EnumType.STRING)
    private ProcessState processState;
    @Enumerated(value = EnumType.STRING)
    private MotiveClosing motiveClosing;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startDateProcess;
    private Integer totalGlobal;
    private Integer totalUpload;
    @Lob
    private String comments;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date endDateProcess;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "uploadProcess")
    private List<UploadProcessDetail> processDetails;

    private String loginUserRunning;

    public Date getDateProcess() {
        return dateProcess;
    }

    public void setDateProcess(Date dateProcess) {
        this.dateProcess = dateProcess;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public ProcessState getProcessState() {
        return processState;
    }

    public void setProcessState(ProcessState processState) {
        this.processState = processState;
    }

    public MotiveClosing getMotiveClosing() {
        return motiveClosing;
    }

    public void setMotiveClosing(MotiveClosing value) {
        this.motiveClosing = value;
    }

    public List<UploadProcessDetail> getProcessDetails() {
        return processDetails;
    }

    public void setProcessDetails(List<UploadProcessDetail> processDetails) {
        this.processDetails = processDetails;
    }

    public Date getStartDateProcess() {
        return startDateProcess;
    }

    public void setStartDateProcess(Date startDateProcess) {
        this.startDateProcess = startDateProcess;
    }


    public Integer getTotalUpload() {
        return totalUpload;
    }

    public void setTotalUpload(Integer totalUpload) {
        this.totalUpload = totalUpload;
    }

    public Integer getTotalGlobal() {
        return totalGlobal;
    }

    public void setTotalGlobal(Integer totalGlobal) {
        this.totalGlobal = totalGlobal;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getEndDateProcess() {
        return endDateProcess;
    }

    public void setEndDateProcess(Date value) {
        this.endDateProcess = value;
    }

    public String getLoginUserRunning() {
        return loginUserRunning;
    }

    public void setLoginUserRunning(String loginUserRunning) {
        this.loginUserRunning = loginUserRunning;
    }
}
