/*
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
 *  ProcessState.java
 *  
 *  Created on 11-01-2011, 11:07:52 AM
 */
package com.scopix.periscope.reporting;

/**
 *
 * @author Nelson Rubio
 */
public enum ProcessState {

        SCHEDULED, RUNNING, FINISHED;

    public String getName() {
        return this.name();
    }
}
