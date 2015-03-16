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
 * EvidenceExtractionServicesServerDTO.java
 *
 * Created on 27-06-2008, 08:53:25 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marko.perich
 */
public class EvidenceExtractionServicesServerDTO { 

    private Integer serverId;

    private String name;

    private String url;

    private String sshAddress;

    private String sshPassword;

    private String sshPort;

    private String sshUser;
    
    private String sshLocalTunnelPort;

    private String sshRemoteTunnelPort;

    private ExtractionPlanDTO extractionPlanDTO;

    private List<EvidenceProviderDTO> evidenceProviderDTOs;

    private Integer idAtBusinessServices;
    private boolean useTunnel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ExtractionPlanDTO getExtractionPlanDTO() {
        return extractionPlanDTO;
    }

    public void setExtractionPlanDTO(ExtractionPlanDTO extractionPlanDTO) {
        this.extractionPlanDTO = extractionPlanDTO;
    }

    public List<EvidenceProviderDTO> getEvidenceProviderDTOs() {
        if (evidenceProviderDTOs == null) {
            evidenceProviderDTOs = new ArrayList<EvidenceProviderDTO>();
        }
        return evidenceProviderDTOs;
    }

    public void setEvidenceProviderDTOs(List<EvidenceProviderDTO> evidenceProviderDTOs) {
        this.evidenceProviderDTOs = evidenceProviderDTOs;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getSshAddress() {
        return sshAddress;
    }

    public void setSshAddress(String sshAddress) {
        this.sshAddress = sshAddress;
    }

    public String getSshPort() {
        return sshPort;
    }

    public void setSshPort(String sshPort) {
        this.sshPort = sshPort;
    }

    public String getSshLocalTunnelPort() {
        return sshLocalTunnelPort;
    }

    public void setSshLocalTunnelPort(String sshLocalTunnelPort) {
        this.sshLocalTunnelPort = sshLocalTunnelPort;
    }

    public String getSshRemoteTunnelPort() {
        return sshRemoteTunnelPort;
    }

    public void setSshRemoteTunnelPort(String sshRemoteTunnelPort) {
        this.sshRemoteTunnelPort = sshRemoteTunnelPort;
    }

    public String getSshPassword() {
        return sshPassword;
    }

    public void setSshPassword(String sshPassword) {
        this.sshPassword = sshPassword;
    }

    public String getSshUser() {
        return sshUser;
    }

    public void setSshUser(String sshUser) {
        this.sshUser = sshUser;
    }

    public Integer getIdAtBusinessServices() {
        return idAtBusinessServices;
    }

    public void setIdAtBusinessServices(Integer idAtBusinessServices) {
        this.idAtBusinessServices = idAtBusinessServices;
    }

    public boolean isUseTunnel() {
        return useTunnel;
    }

    public void setUseTunnel(boolean useTunnel) {
        this.useTunnel = useTunnel;
    }
}
