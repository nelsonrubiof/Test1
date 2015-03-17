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
 *  RegionServerDTO.java
 * 
 *  Created on Jul 24, 2014, 9:41:03 AM
 * 
 */

package com.scopix.periscope.evaluationmanagement.dto;

/**
 *
 * @author Sebastian
 */
public class RegionTransferSendDTO {
    
    private String serverCodeName;
    private String ip;

    /**
     * @return the serverCodeName
     */
    public String getServerCodeName() {
        return serverCodeName;
    }

    /**
     * @param serverCodeName the serverCodeName to set
     */
    public void setServerCodeName(String serverCodeName) {
        this.serverCodeName = serverCodeName;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
   
}
