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
 * ReadUrlPHPEvidenceProvider.java
 *
 * Created on 16-08-2010, 04:05:56 PM
 *
 */
package com.scopix.periscope.extractionmanagement;

import javax.persistence.Entity;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author Nelson Rubio
 * @version 1.0.0
 */
@Entity
public class ReadUrlPHPEvidenceProvider extends EvidenceProvider {

    //rutas del server 
    private String protocol;
    private String ipAddress;
    private String port;
    //en caso se ser necesario al conectarse a algun provider en particular
    private String userName;
    private String password;
    //url completa para solicitar imagen o xml por el momento
    private String query;
    

    public String getProtocol() {
        StringEscapeUtils.escapeHtml(port);
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
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

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }
}
