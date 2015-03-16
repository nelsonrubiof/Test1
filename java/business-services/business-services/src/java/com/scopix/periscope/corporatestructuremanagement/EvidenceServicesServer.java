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
 * EvidenceServicesServer.java
 *
 * Created on 27-03-2008, 02:39:40 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement;

import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class EvidenceServicesServer extends BusinessObject {

    @Lob
    private String url;
    
    @Lob
    private String evidencePath;

    @Lob
    private String proofPath;

    private Boolean alternativeMode;

    @Lob
    private String alternativeEvidencePath;

    @Lob
    private String alternativeSFTPip;

    @Lob
    private String alternativeSFTPuser;

    @Lob
    private String alternativeSFTPpassword;

    @Lob
    private String alternativeRemoteSFTPPath;

    @Lob
    private String localFilePath;

    @OneToMany(mappedBy = "evidenceServicesServer", fetch = FetchType.LAZY)
    private List<EvidenceExtractionServicesServer> evidenceExtractionServicesServers;

    @OneToMany(mappedBy = "evidenceServicesServer", fetch = FetchType.LAZY)
    private List<Store> stores;

    @OneToMany(mappedBy = "evidenceServicesServer", fetch = FetchType.LAZY)
    private List<Evidence> evidences;

    public List<EvidenceExtractionServicesServer> getEvidenceExtractionServicesServers() {
        return evidenceExtractionServicesServers;
    }

    public void setEvidenceExtractionServicesServers(List<EvidenceExtractionServicesServer> evidenceExtractionServicesServers) {
        this.evidenceExtractionServicesServers = evidenceExtractionServicesServers;
    }

    public List<Store> getStores() {
        if (stores == null) {
            stores = new ArrayList<Store>();
        }
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }

    public void setEvidences(List<Evidence> evidences) {
        this.evidences = evidences;
    }

    public String getEvidencePath() {
        return evidencePath;
    }

    public void setEvidencePath(String evidencePath) {
        this.evidencePath = evidencePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the alternativeMode
     */
    public Boolean getAlternativeMode() {
        if(alternativeMode==null){
            alternativeMode = false;
        }
        return alternativeMode;
    }

    /**
     * @param alternativeMode the alternativeMode to set
     */
    public void setAlternativeMode(Boolean alternativeMode) {
        this.alternativeMode = alternativeMode;
    }

    /**
     * @return the alternativeSFTPip
     */
    public String getAlternativeSFTPip() {
        return alternativeSFTPip;
    }

    /**
     * @param alternativeSFTPip the alternativeSFTPip to set
     */
    public void setAlternativeSFTPip(String alternativeSFTPip) {
        this.alternativeSFTPip = alternativeSFTPip;
    }

    /**
     * @return the alternativeSFTPuser
     */
    public String getAlternativeSFTPuser() {
        return alternativeSFTPuser;
    }

    /**
     * @param alternativeSFTPuser the alternativeSFTPuser to set
     */
    public void setAlternativeSFTPuser(String alternativeSFTPuser) {
        this.alternativeSFTPuser = alternativeSFTPuser;
    }

    /**
     * @return the alternativeSFTPpassword
     */
    public String getAlternativeSFTPpassword() {
        return alternativeSFTPpassword;
    }

    /**
     * @param alternativeSFTPpassword the alternativeSFTPpassword to set
     */
    public void setAlternativeSFTPpassword(String alternativeSFTPpassword) {
        this.alternativeSFTPpassword = alternativeSFTPpassword;
    }

    /**
     * @return the alternativeEvidencePath
     */
    public String getAlternativeEvidencePath() {
        return alternativeEvidencePath;
    }

    /**
     * @param alternativeEvidencePath the alternativeEvidencePath to set
     */
    public void setAlternativeEvidencePath(String alternativeEvidencePath) {
        this.alternativeEvidencePath = alternativeEvidencePath;
    }

    public String getAlternativeRemoteSFTPPath() {
        return alternativeRemoteSFTPPath;
    }

    public void setAlternativeRemoteSFTPPath(String alternativeRemoteSFTPPath) {
        this.alternativeRemoteSFTPPath = alternativeRemoteSFTPPath;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    public String getProofPath() {
        return proofPath;
    }

    public void setProofPath(String proofPath) {
        this.proofPath = proofPath;
    }
}
