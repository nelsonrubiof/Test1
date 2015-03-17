package com.scopix.periscope.extractionmanagement;

import javax.persistence.Entity;

/*
 * 
 * Copyright (c) 2012, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 * 
 * Clase que extiende propiedades de la tabla EvidenceProvider para la nueva versión
 * de integración entre EES y NextLevel
 *
 * @author    carlos polo
 * @created   19-oct-2012
 *
 */
@Entity
public class NextLevel3EvidenceProvider extends EvidenceProvider {

    private String uuid;
    /*ip del Gateway**/
    private String ipAddress;
    /*puerto del Gateway**/
    private String port;
    /*userName del Gateway**/
    private String userName;
    /*userName del Gateway**/
    private String password;
    private String protocol;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUrlGateway() {
        return getProtocol() + "://" + getIpAddress() + ":" + getPort();
    }
}