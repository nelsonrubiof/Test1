/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
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
 *  RegionServerFrontEndDTO.java
 * 
 *  Created on Jul 30, 2014, 12:20:06 PM
 * 
 */
package com.scopix.periscope.frontend.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@XmlRootElement(name = "RegionServerFrontEndDTO")
public class RegionServerFrontEndDTO {

    private Integer id;
    private String codeName;
    private boolean active;
    private String sFTPIp;
    private String sFTPUser;
    private String sFTPPassword;
    private String sFTPPath;
    private String serverIp;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the codeName
     */
    public String getCodeName() {
        return codeName;
    }

    /**
     * @param codeName the codeName to set
     */
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the sFTPIp
     */
    public String getsFTPIp() {
        return sFTPIp;
    }

    /**
     * @param sFTPIp the sFTPIp to set
     */
    public void setsFTPIp(String sFTPIp) {
        this.sFTPIp = sFTPIp;
    }

    /**
     * @return the sFTPUser
     */
    public String getsFTPUser() {
        return sFTPUser;
    }

    /**
     * @param sFTPUser the sFTPUser to set
     */
    public void setsFTPUser(String sFTPUser) {
        this.sFTPUser = sFTPUser;
    }

    /**
     * @return the sFTPPassword
     */
    public String getsFTPPassword() {
        return sFTPPassword;
    }

    /**
     * @param sFTPPassword the sFTPPassword to set
     */
    public void setsFTPPassword(String sFTPPassword) {
        this.sFTPPassword = sFTPPassword;
    }

    /**
     * @return the sFTPPath
     */
    public String getsFTPPath() {
        return sFTPPath;
    }

    /**
     * @param sFTPPath the sFTPPath to set
     */
    public void setsFTPPath(String sFTPPath) {
        this.sFTPPath = sFTPPath;
    }

    /**
     * @return the serverIp
     */
    public String getServerIp() {
        return serverIp;
    }

    /**
     * @param serverIp the serverIp to set
     */
    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
}
