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
 * Created on 27-03-2008, 05:39:40 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.validator.NotNull;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class EvidenceExtractionServicesServer extends BusinessObject {

    @Lob
    private String name;
    @Lob
    private String url;
//    @OneToOne
//    private Store store;
    @OneToMany(mappedBy = "evidenceExtractionServicesServer", fetch = FetchType.LAZY)
    private List<Store> stores;
    @ManyToOne
    private EvidenceServicesServer evidenceServicesServer;
    @Lob
    private String sshAddress;
    @Lob
    private String sshPort;
    @Lob
    private String sshUser;
    @Lob
    private String sshPassword;
    @Lob
    private String sshLocalTunnelPort;
    @Lob
    private String sshRemoteTunnelPort;
    
    @NotNull
    private boolean useTunnel;

    public EvidenceServicesServer getEvidenceServicesServer() {
        return evidenceServicesServer;
    }

    public void setEvidenceServicesServer(EvidenceServicesServer evidenceServicesServer) {
        this.evidenceServicesServer = evidenceServicesServer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getSshPassword() {
        return sshPassword;
    }

    public void setSshPassword(String sshPassword) {
        this.sshPassword = sshPassword;
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

    public String getSshUser() {
        return sshUser;
    }

    public void setSshUser(String sshUser) {
        this.sshUser = sshUser;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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

//    public void addStore(Store store) {
//        boolean add = true;
//        for (Store st : getStores()){
//            if(st.getId().equals(store.getId())) {
//                add = false;
//                break;
//            }
//        }
//        if (add) {
//            getStores().add(store);
//        }
//    }

    public boolean isUseTunnel() {
        return useTunnel;
    }

    public void setUseTunnel(boolean useTunnel) {
        this.useTunnel = useTunnel;
    }

}
