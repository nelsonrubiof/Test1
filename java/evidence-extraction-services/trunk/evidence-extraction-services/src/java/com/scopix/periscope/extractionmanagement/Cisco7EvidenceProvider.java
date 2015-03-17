 /*
 *  
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
 * 
 *  Cisco7EvidenceProvider.java
 * 
 *  Created on 02-10-2013, 04:59:38 PM
 * 
 */
package com.scopix.periscope.extractionmanagement;

import javax.persistence.Entity;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class Cisco7EvidenceProvider extends EvidenceProvider {

	private String uuid;
    private String vsomUser;
    private String vsomPass;
    private String vsomPort;
    private String vsomDomain;
    private String cameraName;
    private String vsomProtocol;
    private String vsomIpAddress;
    private String mediaServerIp;
    private String mediaServerPort;
    private String mediaServerProtocol;
    private static final long serialVersionUID = -8547630485679131899L;

    public String getVsomIpAddress() {
        return vsomIpAddress;
    }

    public void setVsomIpAddress(String vsomIpAddress) {
        this.vsomIpAddress = vsomIpAddress;
    }

    public String getVsomUser() {
        return vsomUser;
    }

    public void setVsomUser(String vsomUser) {
        this.vsomUser = vsomUser;
    }

    public String getVsomPass() {
        return vsomPass;
    }

    public void setVsomPass(String vsomPass) {
        this.vsomPass = vsomPass;
    }

    public String getVsomDomain() {
        return vsomDomain;
    }

    public void setVsomDomain(String vsomDomain) {
        this.vsomDomain = vsomDomain;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getVsomProtocol() {
        return vsomProtocol;
    }

    public void setVsomProtocol(String vsomProtocol) {
        this.vsomProtocol = vsomProtocol;
    }

    public String getVsomPort() {
        return vsomPort;
    }

    public void setVsomPort(String vsomPort) {
        this.vsomPort = vsomPort;
    }
    
    public String getMediaServerIp() {
        return mediaServerIp;
    }

    public void setMediaServerIp(String mediaServerIp) {
        this.mediaServerIp = mediaServerIp;
    }

    public String getMediaServerProtocol() {
        return mediaServerProtocol;
    }

    public void setMediaServerProtocol(String mediaServerProtocol) {
        this.mediaServerProtocol = mediaServerProtocol;
    }

    public String getMediaServerPort() {
        return mediaServerPort;
    }

    public void setMediaServerPort(String mediaServerPort) {
        this.mediaServerPort = mediaServerPort;
    }

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}