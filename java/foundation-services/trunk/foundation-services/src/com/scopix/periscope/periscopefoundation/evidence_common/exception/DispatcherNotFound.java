/*
 *
 * Copyright 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * ServiceException.java
 * 
 * Created on May 3, 2007, 5:19 PM
 *
 */

package com.scopix.periscope.periscopefoundation.evidence_common.exception;

/**
 *
 * @author jorge
 */
public class DispatcherNotFound extends DispatcherException {
    
    public DispatcherNotFound(String message) {
        super(message);
    }
    
    public DispatcherNotFound(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DispatcherNotFound(Throwable cause) {
        super(cause);
    }
    
}
