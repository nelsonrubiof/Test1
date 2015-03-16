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
 *  NextLevelProperties.java
 * 
 *  Created on 15-09-2011, 03:51:12 PM
 * 
 */
package com.scopix.periscope.nextlevel;

/**
 *
 * @author nelson
 */
public class NextLevelProperties {

    public static final String CONNECTION_CENTRAL = "central";
    public static final String CONNECTION_GATEWAY = "gateway";
    private String urlCentral;
    private String userCentral;
    private String passwordCentral;
    private String clientCentral;
    private String gatewayID;
    private String urlGateway;
    private String userGateway;
    private String passwordGatewey;
    private String connectionType;
    private boolean connectionCentral;

    public String getUrlCentral() {
        return urlCentral;
    }

    public void setUrlCentral(String urlCentral) {
        this.urlCentral = urlCentral;
    }

    public String getUserCentral() {
        return userCentral;
    }

    public void setUserCentral(String userCentral) {
        this.userCentral = userCentral;
    }

    public String getPasswordCentral() {
        return passwordCentral;
    }

    public void setPasswordCentral(String passwordCentral) {
        this.passwordCentral = passwordCentral;
    }

    public String getClientCentral() {
        return clientCentral;
    }

    public void setClientCentral(String clientCentral) {
        this.clientCentral = clientCentral;
    }

    public String getGatewayID() {
        return gatewayID;
    }

    public void setGatewayID(String gatewayID) {
        this.gatewayID = gatewayID;
    }

    public String getUrlGateway() {
        return urlGateway;
    }

    public void setUrlGateway(String urlGateway) {
        this.urlGateway = urlGateway;
    }

    public String getUserGateway() {
        return userGateway;
    }

    public void setUserGateway(String userGateway) {
        this.userGateway = userGateway;
    }

    public String getPasswordGatewey() {
        return passwordGatewey;
    }

    public void setPasswordGatewey(String passwordGatewey) {
        this.passwordGatewey = passwordGatewey;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connection) {
        if (connection.equals(CONNECTION_CENTRAL)) {
            setConnectionCentral(true);
        }
        this.connectionType = connection;
    }

    public boolean isConnectionCentral() {
        return connectionCentral;
    }

    public void setConnectionCentral(boolean connectionCentral) {
        this.connectionCentral = connectionCentral;
    }
}
