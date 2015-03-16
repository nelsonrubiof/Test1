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
 *  ExtractionPlanRangeType.java
 * 
 *  Created on 15-09-2010, 05:54:29 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement;

/**
 *
 * @author nelson
 */
public enum ExtractionPlanRangeType {
    /**
     * AUTOMATIC_EVIDENCE   : Automatico
     * RANDOM               : Aleatorio
     * FIXED                : Fijo
     * REAL_RANDOM          : Aleatorio en Evidence Extraction
     */
    AUTOMATIC_EVIDENCE, RANDOM, FIXED, REAL_RANDOM;

    public String getName() {
        return this.name();
    }
}
