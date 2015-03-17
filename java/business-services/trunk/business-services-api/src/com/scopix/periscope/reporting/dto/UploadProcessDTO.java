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
 *  UploadProcessDTO.java
 *  
 *  Created on 10-01-2011, 05:21:50 PM
 */
package com.scopix.periscope.reporting.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nelson Rubio
 */
public class UploadProcessDTO {

    private Integer id;
    /**
     * Esta fecha debe llevar el formato:  yyyy-MM-dd HH:mm:ss
     */
    private String dateProcess;
    private String loginUser;
    private String loginUserRunning;
    private String processState;
    private String motiveClosing;
    private String observations;
    private Integer totalGlobal;
    private Integer processedGlobal;
    private Double percentGlobal;
    private List<UploadProcessDetailDTO> processDetails;
    private String startDate;
    private String endDate;

    public String getDateProcess() {
        return dateProcess;
    }

    public void setDateProcess(String dateProcess) {
        this.dateProcess = dateProcess;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public String getMotiveClosing() {
        return motiveClosing;
    }

    public void setMotiveClosing(String moviteClosing) {
        this.motiveClosing = moviteClosing;
    }

    public List<UploadProcessDetailDTO> getProcessDetails() {
        if (processDetails == null) {
            processDetails = new ArrayList<UploadProcessDetailDTO>();
        }
        return processDetails;
    }

    public void setProcessDetails(List<UploadProcessDetailDTO> processDetails) {
        this.processDetails = processDetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalGlobal() {
        return totalGlobal;
    }

    public void setTotalGlobal(Integer totalGlobal) {
        this.totalGlobal = totalGlobal;
    }

    public Integer getProcessedGlobal() {
        return processedGlobal;
    }

    public void setProcessedGlobal(Integer processedGlobal) {
        this.processedGlobal = processedGlobal;
    }

    public Double getPercentGlobal() {
        return percentGlobal;
    }

    public void setPercentGlobal(Double percentGlobal) {
        this.percentGlobal = percentGlobal;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLoginUserRunning() {
        return loginUserRunning;
    }

    public void setLoginUserRunning(String loginUserRunning) {
        this.loginUserRunning = loginUserRunning;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

}
