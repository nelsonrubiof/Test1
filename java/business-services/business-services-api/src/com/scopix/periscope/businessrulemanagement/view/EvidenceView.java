/*
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
 *
 */
package com.scopix.periscope.businessrulemanagement.view;

import java.util.Date;

/**
 * This class represents a DTO. <br>
 * It is used to send and receive data out of the application, such as web
 * services or via web.
 *
 * @author maximiliano.vazquez
 *
 */
public class EvidenceView {
    
    public Integer getEvidenceId() {
        return this.evidenceId;
    }
    
    public String getEvidencePath() {
        return this.evidencePath;
    }
    
    public Integer getEvidenceRequestId() {
        return this.evidenceRequestId;
    }
    
    public EvidenceReturnCodeView getEvidenceReturnCodeView() {
        return this.evidenceReturnCodeView;
    }
    
    public void setEvidenceId(Integer evidenceId) {
        this.evidenceId = evidenceId;
    }
    
    public void setEvidencePath(String evidencePath) {
        this.evidencePath = evidencePath;
    }
    
    public void setEvidenceRequestId(Integer evidenceRequestId) {
        this.evidenceRequestId = evidenceRequestId;
    }
    
    public void setEvidenceReturnCodeView(EvidenceReturnCodeView evidenceReturnCodeView) {
        this.evidenceReturnCodeView = evidenceReturnCodeView;
    }
    
    public Integer getEvidenceType() {
        return evidenceType;
    }
    
    public void setEvidenceType(Integer evidenceType) {
        this.evidenceType = evidenceType;
    }

    public Date getEvidenceTimestamp() {
        return evidenceTimestamp;
    }

    public void setEvidenceTimestamp(Date evidenceTimestamp) {
        this.evidenceTimestamp = evidenceTimestamp;
    }
        
    private Integer evidenceId;
    private Integer evidenceType;
    private String evidencePath;
    private Integer evidenceRequestId;
    private EvidenceReturnCodeView evidenceReturnCodeView;
    private Date evidenceTimestamp;

}
