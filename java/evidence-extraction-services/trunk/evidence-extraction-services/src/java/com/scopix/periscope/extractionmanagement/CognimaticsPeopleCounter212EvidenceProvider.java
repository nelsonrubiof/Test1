/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionmanagement;

import javax.persistence.Entity;

/**
 *
 * @author nelson
 */
@Entity
public class CognimaticsPeopleCounter212EvidenceProvider extends EvidenceProvider {

    private String ipAddress;
    private String userName;
    private String password;
    private String protocol;
    private String port;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
