/*
 * 
 * Copyright ? 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * SecurityResultVO.java
 *
 * Created on 25-08-2008, 12:37:12 PM
 *
 */
package com.scopix.periscope.securitymanagement;

/**
 * This class represent the result of security web services, errorCode equal "" or null then no error, other value represent a
 * error.
 * sessionId is the user session id.
 * 
 * @author C?sar Abarza Suazo.
 */
public class SecurityResultVO {

    private String errorCode;
    private String errorMessage;
    private Long sessionId;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
}
