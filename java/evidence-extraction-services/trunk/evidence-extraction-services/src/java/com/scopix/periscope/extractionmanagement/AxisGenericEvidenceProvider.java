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
 * AxisGenericEvidenceProvider.java
 *
 * Created on 20-05-2008, 09:26:28 PM
 *
 */
package com.scopix.periscope.extractionmanagement;

import javax.persistence.Entity;

/**
 *
 * @author Nelson
 */
@Entity
public class AxisGenericEvidenceProvider extends EvidenceProvider {

    private String protocol;
    private String ipAddress;
    private String port;
    private String userName;
    private String password;
    private int framerate;
    private String resolution;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String user) {
        this.userName = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getFramerate() {
        return framerate;
    }

    public void setFramerate(int framerate) {
        this.framerate = framerate;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}