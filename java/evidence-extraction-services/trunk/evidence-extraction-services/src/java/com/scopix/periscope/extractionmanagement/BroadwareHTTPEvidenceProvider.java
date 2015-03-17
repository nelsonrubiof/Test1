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
 * BroadwareHTTPEvidenceProvider.java
 *
 * Created on 26-02-2013, 13:00:00 PM
 *
 */
package com.scopix.periscope.extractionmanagement;

import javax.persistence.Entity;

/**
 *
 * @author gustavo.alvarez
 */
@Entity
public class BroadwareHTTPEvidenceProvider extends EvidenceProvider {

    private String ipAddress;
    private String loopName;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLoopName() {
        return loopName;
    }

    public void setLoopName(String loopName) {
        this.loopName = loopName;
    }
}