/**
 *
 * Copyright © 2014, SCOPIX. All rights reserved.
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
 *
 */
package com.scopix.periscope.frontend.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Sebastian
 *
 */
@XmlRootElement(name = "EvidenceRegionTransferDTO")
public class EvidenceRegionTransferDTO {

    private Integer evidenceId;
    private String evidenceFileName;
    private String evidenceDate;
    private String regionServerName;
    private boolean completed;
    private String transmissionDate;
    private String errorMessage;

    /**
     * @return the evidenceId
     */
    public Integer getEvidenceId() {
        return evidenceId;
    }

    /**
     * @param evidenceId the evidenceId to set
     */
    public void setEvidenceId(Integer evidenceId) {
        this.evidenceId = evidenceId;
    }

    /**
     * @return the evidenceFileName
     */
    public String getEvidenceFileName() {
        return evidenceFileName;
    }

    /**
     * @param evidenceFileName the evidenceFileName to set
     */
    public void setEvidenceFileName(String evidenceFileName) {
        this.evidenceFileName = evidenceFileName;
    }

    /**
     * @return the evidenceDate
     */
    public String getEvidenceDate() {
        return evidenceDate;
    }

    /**
     * @param evidenceDate the evidenceDate to set
     */
    public void setEvidenceDate(String evidenceDate) {
        this.evidenceDate = evidenceDate;
    }

    /**
     * @return the regionServerName
     */
    public String getRegionServerName() {
        return regionServerName;
    }

    /**
     * @param regionServerName the regionServerName to set
     */
    public void setRegionServerName(String regionServerName) {
        this.regionServerName = regionServerName;
    }

    /**
     * @return the completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * @param completed the completed to set
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * @return the transmissionDate
     */
    public String getTransmissionDate() {
        return transmissionDate;
    }

    /**
     * @param transmissionDate the transmissionDate to set
     */
    public void setTransmissionDate(String transmissionDate) {
        this.transmissionDate = transmissionDate;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
