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
 *  CreationType.java
 * 
 *  Created on 24-08-2012, 02:00:17 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement;

/**
 *
 * @author nelson
 */
public enum CreationType {

    AUTOMATIC, MANUAL;

    public String getName() {
        return this.toString();
    }
}
