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
 *  ScopixException.java
 * 
 *  Created on 01-02-2013, 10:19:16 AM
 * 
 */
package com.scopix.periscope.periscopefoundation.exception;

import java.util.List;

import com.scopix.periscope.periscopefoundation.exception.notification.RaygunNotifier;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
public class ScopixException extends Exception {

	private static final long serialVersionUID = -4103463382095903554L;

	public ScopixException() {
		super();
		notifyRaygun(null);
    }


	public ScopixException(List<String> tags) {
		super();
		notifyRaygun(tags);
	}
    
    /**
     *
     * @param message String con mensaje a agregar a la excepcion
     */
    public ScopixException(String message) {
        super(message);
		notifyRaygun(null);
    }

	public ScopixException(String message, List<String> tags) {
		super(message);
		notifyRaygun(tags);
	}

    /**
     *
     * @param cause Causa por la cual generamos la excepcion
     */
    public ScopixException(Throwable cause) {
        super(cause);
		notifyRaygun(null);
    }

    public ScopixException(List<String> tags, Throwable cause) {
    	super(cause);
		notifyRaygun(tags);
    }

    /**
     *
     * @param message String con mensaje a agregar a la excepcion
     * @param cause Causa por la cual generamos la excepcion
     */
	public ScopixException(String message, Throwable cause) {
		super(message, cause);
		notifyRaygun(null);
	}

	public ScopixException(String message, List<String> tags, Throwable cause) {
		super(message, cause);
		notifyRaygun(tags);
    }

	private void notifyRaygun(List<String> tagsFromException) {
		RaygunNotifier.getInstance().notifyServer(this, tagsFromException);
	}

}