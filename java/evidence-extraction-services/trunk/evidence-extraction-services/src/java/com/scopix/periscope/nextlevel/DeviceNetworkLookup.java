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
 *  DeviceNetworkLookup.java
 * 
 *  Created on 08-09-2011, 02:12:55 PM
 * 
 */
package com.scopix.periscope.nextlevel;

/**
 *
 * @author nelson
 */
public class DeviceNetworkLookup {

    private String gatewayID;
    private String deviceName;
    private String firmwareVersion;
    private String customerID;
    private String deviceIp;
    private Integer deviceWebPort;
    private Integer deviceRtmpPort;
    private String dataKey;
    private Integer status;

    public String getGatewayID() {
        return gatewayID;
    }

    public void setGatewayID(String gatewayID) {
        this.gatewayID = gatewayID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public Integer getDeviceWebPort() {
        return deviceWebPort;
    }

    public void setDeviceWebPort(Integer deviceWebPort) {
        this.deviceWebPort = deviceWebPort;
    }

    public Integer getDeviceRtmpPort() {
        return deviceRtmpPort;
    }

    public void setDeviceRtmpPort(Integer deviceRtmpPort) {
        this.deviceRtmpPort = deviceRtmpPort;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
