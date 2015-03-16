/*
 * 
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
 * EvidenceFile.java
 *
 * Created on 18-06-2008, 04:32:58 PM
 *
 */
package com.scopix.periscope.extractionmanagement;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.scopix.periscope.periscopefoundation.BusinessObject;

/**
 *
 * @author marko.perich
 */
@Entity
public class EvidenceFile extends BusinessObject {

    private String filename;
    @ManyToOne
    private EvidenceExtractionRequest evidenceExtractionRequest;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date evidenceDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;
    @Temporal(TemporalType.TIMESTAMP)
	private Date creationDate = new Date();
	@Temporal(TemporalType.TIMESTAMP)
    private Date fileCreationDate;
    private Boolean noCheckNagios;
    private String alternativeFileName;
    /**
     * indica si el arhivo ya fue descargado desde la fuente
     */
    private Boolean downloadedFile;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public EvidenceExtractionRequest getEvidenceExtractionRequest() {
        return evidenceExtractionRequest;
    }

    public void setEvidenceExtractionRequest(EvidenceExtractionRequest evidenceExtractionRequest) {
        this.evidenceExtractionRequest = evidenceExtractionRequest;
    }

    public Date getEvidenceDate() {
        return evidenceDate;
    }

    public void setEvidenceDate(Date evidenceDate) {
        this.evidenceDate = evidenceDate;
    }

    /**
     * @return the uploadDate
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * @param uploadDate the uploadDate to set
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * @return the fileCreationDate
     */
    public Date getFileCreationDate() {
        return fileCreationDate;
    }

    /**
     * @param fileCreationDate the fileCreationDate to set
     */
    public void setFileCreationDate(Date fileCreationDate) {
        this.fileCreationDate = fileCreationDate;
    }

    /**
     * @return the no_check_nagios
     */
    public Boolean getNoCheckNagios() {
        return noCheckNagios;
    }

    /**
     * @param no_check_nagios the no_check_nagios to set
     */
    public void setNoCheckNagios(Boolean noCheckNagios) {
        this.noCheckNagios = noCheckNagios;
    }

    public String getAlternativeFileName() {
        return alternativeFileName;
    }

    public void setAlternativeFileName(String alternativeFileName) {
        this.alternativeFileName = alternativeFileName;
    }

    public Boolean getDownloadedFile() {
        return downloadedFile;
    }

    public void setDownloadedFile(Boolean downloadedFile) {
        this.downloadedFile = downloadedFile;
    }
}