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
 *  Camera.java
 * 
 *  Created on 08-09-2011, 03:19:21 PM
 * 
 */
package com.scopix.periscope.nextlevel;

/**
 *
 * @author nelson
 */
public class Camera {

    private String deviceID;
    private String deviceName;
    private String deviceDescription;
    private String deviceLocation;
    private String systemContact;
    private String deviceModel;
    private String serialNumber;
    private String macAddress;
    private String firmwareVersion;
    private Long firmwareReleasedDate;
    private String logicVersion;
    private Long logicReleasedDate;
    private String bootVersion;
    private Long bootReleasedDate;
    private String rescueVersion;
    private Long rescueReleasedDate;
    private String hardwareVersion;
    private String systemObjectID;
    private String realDeviceID;
    private Integer connectionState;
    private Integer adminState;
    private Double coordX;
    private Double coordY;
    private Double coordZ;
    private Boolean isPTZCamera;
    private String discoveredIPAddress;
    private String defaultUsername;
    private String defaultPassword;
    private String assignedGatewayID;
    private Integer primaryChannelID;
    private String customStreamURL;
    private Integer customStreamType;
    private Boolean isMulticastEnabled;
    private Integer activeStreams;
    private Integer activeAnalytics;
    private Integer activeForensics;
    private Integer activeRecordings;
    private Boolean isInputPort;
    private Integer isOutputPort;

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceDescription() {
        return deviceDescription;
    }

    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

    public String getDeviceLocation() {
        return deviceLocation;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    public String getSystemContact() {
        return systemContact;
    }

    public void setSystemContact(String systemContact) {
        this.systemContact = systemContact;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public Long getFirmwareReleasedDate() {
        return firmwareReleasedDate;
    }

    public void setFirmwareReleasedDate(Long firmwareReleasedDate) {
        this.firmwareReleasedDate = firmwareReleasedDate;
    }

    public String getLogicVersion() {
        return logicVersion;
    }

    public void setLogicVersion(String logicVersion) {
        this.logicVersion = logicVersion;
    }

    public Long getLogicReleasedDate() {
        return logicReleasedDate;
    }

    public void setLogicReleasedDate(Long logicReleasedDate) {
        this.logicReleasedDate = logicReleasedDate;
    }

    public String getBootVersion() {
        return bootVersion;
    }

    public void setBootVersion(String bootVersion) {
        this.bootVersion = bootVersion;
    }

    public Long getBootReleasedDate() {
        return bootReleasedDate;
    }

    public void setBootReleasedDate(Long bootReleasedDate) {
        this.bootReleasedDate = bootReleasedDate;
    }

    public String getRescueVersion() {
        return rescueVersion;
    }

    public void setRescueVersion(String rescueVersion) {
        this.rescueVersion = rescueVersion;
    }

    public Long getRescueReleasedDate() {
        return rescueReleasedDate;
    }

    public void setRescueReleasedDate(Long rescueReleasedDate) {
        this.rescueReleasedDate = rescueReleasedDate;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public String getSystemObjectID() {
        return systemObjectID;
    }

    public void setSystemObjectID(String systemObjectID) {
        this.systemObjectID = systemObjectID;
    }

    public String getRealDeviceID() {
        return realDeviceID;
    }

    public void setRealDeviceID(String realDeviceID) {
        this.realDeviceID = realDeviceID;
    }

    public Integer getConnectionState() {
        return connectionState;
    }

    public void setConnectionState(Integer connectionState) {
        this.connectionState = connectionState;
    }

    public Integer getAdminState() {
        return adminState;
    }

    public void setAdminState(Integer adminState) {
        this.adminState = adminState;
    }

    public Double getCoordX() {
        return coordX;
    }

    public void setCoordX(Double coordX) {
        this.coordX = coordX;
    }

    public Double getCoordY() {
        return coordY;
    }

    public void setCoordY(Double coordY) {
        this.coordY = coordY;
    }

    public Double getCoordZ() {
        return coordZ;
    }

    public void setCoordZ(Double coordZ) {
        this.coordZ = coordZ;
    }

    public Boolean getIsPTZCamera() {
        return isPTZCamera;
    }

    public void setIsPTZCamera(Boolean isPTZCamera) {
        this.isPTZCamera = isPTZCamera;
    }

    public String getDiscoveredIPAddress() {
        return discoveredIPAddress;
    }

    public void setDiscoveredIPAddress(String discoveredIPAddress) {
        this.discoveredIPAddress = discoveredIPAddress;
    }

    public String getDefaultUsername() {
        return defaultUsername;
    }

    public void setDefaultUsername(String defaultUsername) {
        this.defaultUsername = defaultUsername;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public String getAssignedGatewayID() {
        return assignedGatewayID;
    }

    public void setAssignedGatewayID(String assignedGatewayID) {
        this.assignedGatewayID = assignedGatewayID;
    }

    public Integer getPrimaryChannelID() {
        return primaryChannelID;
    }

    public void setPrimaryChannelID(Integer primaryChannelID) {
        this.primaryChannelID = primaryChannelID;
    }

    public String getCustomStreamURL() {
        return customStreamURL;
    }

    public void setCustomStreamURL(String customStreamURL) {
        this.customStreamURL = customStreamURL;
    }

    public Integer getCustomStreamType() {
        return customStreamType;
    }

    public void setCustomStreamType(Integer customStreamType) {
        this.customStreamType = customStreamType;
    }

    public Boolean getIsMulticastEnabled() {
        return isMulticastEnabled;
    }

    public void setIsMulticastEnabled(Boolean isMulticastEnabled) {
        this.isMulticastEnabled = isMulticastEnabled;
    }

    public Integer getActiveStreams() {
        return activeStreams;
    }

    public void setActiveStreams(Integer activeStreams) {
        this.activeStreams = activeStreams;
    }

    public Integer getActiveAnalytics() {
        return activeAnalytics;
    }

    public void setActiveAnalytics(Integer activeAnalytics) {
        this.activeAnalytics = activeAnalytics;
    }

    public Integer getActiveForensics() {
        return activeForensics;
    }

    public void setActiveForensics(Integer activeForensics) {
        this.activeForensics = activeForensics;
    }

    public Integer getActiveRecordings() {
        return activeRecordings;
    }

    public void setActiveRecordings(Integer activeRecordings) {
        this.activeRecordings = activeRecordings;
    }

    public Boolean getIsInputPort() {
        return isInputPort;
    }

    public void setIsInputPort(Boolean isInputPort) {
        this.isInputPort = isInputPort;
    }

    public Integer getIsOutputPort() {
        return isOutputPort;
    }

    public void setIsOutputPort(Integer isOutputPort) {
        this.isOutputPort = isOutputPort;
    }
}
