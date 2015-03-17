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
 *  ExternalApplication.java
 * 
 *  Created on 03-03-2014, 11:29:49 AM
 * 
 */
package com.scopix.periscope.synchronize;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 *
 * @author Nelson
 */
public interface ExternalApplication {

    void sendData(int observedSituationId) throws ScopixException;
}
