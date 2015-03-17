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
 *  SecurityExceptionType.java
 * 
 *  Created on 28-06-2013, 01:07:57 PM
 * 
 */
package com.scopix.periscope.periscopefoundation.exception;

/**
 *
 * @author nelson
 */
public enum SecurityExceptionType {

    USER_NOT_FOUND, NO_HAVE_PERMISSIONS, SESSION_EXPIRED, ACCESS_DENIED, SESSION_NOT_FOUND;

    public String getName() {
        return this.name();
    }
}
