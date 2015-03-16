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
 * VMSGatewayEvidenceProvider.java
 *
 * Created on 13-09-2011, 11:01:33 AM
 *
 */
package com.scopix.periscope.extractionmanagement;

import javax.persistence.Entity;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class VMSGatewayEvidenceProvider extends EvidenceProvider {

    private String ipAddress;
    private String provider;
    //axis5 NO permite extraction plan al pasado
    //i3
    //milestone
    private String providerType;
    private boolean extractionPlanToPast;
    private String port;
    private String protocol;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public boolean getExtractionPlanToPast() {
        return extractionPlanToPast;
    }

    public void setExtractionPlanToPast(boolean extractionPlanToPast) {
        this.extractionPlanToPast = extractionPlanToPast;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
