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
 *  ResultAnalyticsType.java
 * 
 *  Created on 13-02-2013, 12:03:01 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement;

/**
 *
 * @author nelson
 */
public enum ResultAnalyticsType {

    IMAGE_ANALYTICS, COUNTER_ANALYTICS;

    public String getName() {
        return this.name();
    }
}
