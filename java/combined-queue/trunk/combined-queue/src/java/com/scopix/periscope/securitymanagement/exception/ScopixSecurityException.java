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
 *  ScopixSecurityException.java
 * 
 *  Created on Dec 4, 2013, 12:04:45 PM
 * 
 */
package com.scopix.periscope.securitymanagement.exception;


/**
 *
 * @author Sebastian
 */
public class ScopixSecurityException extends Exception {
    
    /**
     *
     */
    public ScopixSecurityException() {
        super();
    }     
    /**
     *
     * @param message String con mensaje a agregar a la excepcion
     */
    public ScopixSecurityException(String message) {
        super(message);
    }

    /**
     *
     * @param cause Causa por la cual generamos la excepcion
     */
    public ScopixSecurityException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param message String con mensaje a agregar a la excepcion
     * @param cause Causa por la cual generamos la excepcion
     */
    public ScopixSecurityException(String message, Throwable cause) {
        super(message, cause);
    }
     
}
