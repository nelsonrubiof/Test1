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
 *  Site.java
 * 
 *  Created on 08-09-2011, 01:46:17 PM
 * 
 */
package com.scopix.periscope.nextlevel;

/**
 *
 * @author nelson
 */
public class Site {

    private String siteID; // 0
    private String customerID;
    private String siteName;
    private String siteAddress1;
    private String siteAddress2;
    private String siteAddressCity;
    private String siteAddressState;
    private Integer siteAddressCountry;
    private Integer siteAddressZipCode;
    private Integer siteCode;
    private String timezoneInfo;
    private String ntpServerIp;
    private String ntpSync;
    private Double coordX;
    private Double coordY;
    private Integer rms;
    private Integer status;

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteAddress1() {
        return siteAddress1;
    }

    public void setSiteAddress1(String siteAddress1) {
        this.siteAddress1 = siteAddress1;
    }

    public String getSiteAddress2() {
        return siteAddress2;
    }

    public void setSiteAddress2(String siteAddress2) {
        this.siteAddress2 = siteAddress2;
    }

    public String getSiteAddressCity() {
        return siteAddressCity;
    }

    public void setSiteAddressCity(String siteAddressCity) {
        this.siteAddressCity = siteAddressCity;
    }

    public String getSiteAddressState() {
        return siteAddressState;
    }

    public void setSiteAddressState(String siteAddressState) {
        this.siteAddressState = siteAddressState;
    }

    public Integer getSiteAddressCountry() {
        return siteAddressCountry;
    }

    public void setSiteAddressCountry(Integer siteAddressCountry) {
        this.siteAddressCountry = siteAddressCountry;
    }

    public Integer getSiteAddressZipCode() {
        return siteAddressZipCode;
    }

    public void setSiteAddressZipCode(Integer siteAddressZipCode) {
        this.siteAddressZipCode = siteAddressZipCode;
    }

    public Integer getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(Integer siteCode) {
        this.siteCode = siteCode;
    }

    public String getTimezoneInfo() {
        return timezoneInfo;
    }

    public void setTimezoneInfo(String timezoneInfo) {
        this.timezoneInfo = timezoneInfo;
    }

    public String getNtpServerIp() {
        return ntpServerIp;
    }

    public void setNtpServerIp(String ntpServerIp) {
        this.ntpServerIp = ntpServerIp;
    }

    public String getNtpSync() {
        return ntpSync;
    }

    public void setNtpSync(String ntpSync) {
        this.ntpSync = ntpSync;
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

    public Integer getRms() {
        return rms;
    }

    public void setRms(Integer rms) {
        this.rms = rms;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
