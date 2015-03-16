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
 * EvidenceExtractionServicesServer.java
 *
 * Created on 16-06-2008, 05:47:45 PM
 *
 */


package com.scopix.periscope.extractionservicesserversmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author marko.perich
 */
@Entity
public class EvidenceExtractionServicesServer extends BusinessObject {

    /**
     * El id del servidor de evidencias en business services (evidence server)
     */
    private Integer serverId;
    
    private String name;
    
    private String url;
    
    private String sshAddress;
    
    private String sshPort;
    
    private String sshUser;
    
    private String sshPassword;
    
    private String sshLocalTunnelPort;
    
    private String sshRemoteTunnelPort;

    private Integer idAtBusinessServices;

    //por cambio de modelo ahora es una Lista no uno solo
//    @OneToOne(mappedBy = "evidenceExtractionServicesServer")
    @OneToMany(mappedBy = "evidenceExtractionServicesServer")
    private List<ExtractionPlan> extractionPlans;
       
    @OneToMany(mappedBy = "evidenceExtractionServicesServer")
    private List<EvidenceProvider> evidenceProviders;

    
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


    public List<EvidenceProvider> getEvidenceProviders() {
        return evidenceProviders;
    }

    public void setEvidenceProviders(List<EvidenceProvider> evidenceProviders) {
        this.evidenceProviders = evidenceProviders;
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

    public List<ExtractionPlan> getExtractionPlans() {
        if (extractionPlans == null) {
            extractionPlans = new ArrayList<ExtractionPlan>();
        }
        return extractionPlans;
    }

    public void setExtractionPlans(List<ExtractionPlan> extractionPlans) {
        this.extractionPlans = extractionPlans;
    }
    
    public void addExtractionPlan(ExtractionPlan ep) {
        getExtractionPlans().add(ep);
    }

//    public ExtractionPlan getExtractionPlan() {
//        return extractionPlan;
//    }
//
//    public void setExtractionPlan(ExtractionPlan extractionPlan) {
//        this.extractionPlan = extractionPlan;
//    }

    /**
     * @return the useTunnel
     */
    public boolean isUseTunnel() {
        return useTunnel;
    }

    /**
     * @param useTunnel the useTunnel to set
     */
    public void setUseTunnel(boolean useTunnel) {
        this.useTunnel = useTunnel;
    }

}