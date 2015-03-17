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
 * WSResult.java
 *
 * Created on May 3, 2007, 9:28 AM
 *
 */

package com.scopix.periscope.evidence_server.ws;

/**
 *
 * @author jorge
 */
public class WSResult {
    public static final int RET_OK = 100;
    public static final int RET_ERROR = 200;
    
    private int retCode;
    private String desc;
   
    /**
     * Creates a new instance of WSResult
     */
    public WSResult() {
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
}
