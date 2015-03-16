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
 * UploadJobException.java
 *
 * Created on 10 de julio de 2007, 12:58
 *
 */

package com.scopix.periscope.scheduler.exception;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;


/**
 *
 * @author marko.perich
 */
public class UploadJobException extends ScopixException {
    
    public UploadJobException(String message) {
        super(message);
    }
    
    public UploadJobException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public UploadJobException(Throwable cause) {
        super(cause);
    }
}
