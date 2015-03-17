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
 *  SituationRequestRangeType.java
 * 
 *  Created on 29-08-2012, 12:57:41 PM
 * 
 */
package com.scopix.periscope.extractionmanagement;

/**
 *
 * @author nelson
 */
public enum SituationRequestRangeType {

    AUTOMATIC_EVIDENCE, RANDOM, FIXED, REAL_RANDOM;

    public String getName() {
        return this.name();
    }
}
