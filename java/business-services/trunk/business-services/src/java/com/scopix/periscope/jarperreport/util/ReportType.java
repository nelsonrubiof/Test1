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
 *  ReportType.java
 * 
 *  Created on 16-04-2012, 05:23:58 PM
 * 
 */
package com.scopix.periscope.jarperreport.util;

/**
 *
 * @author nelson
 */
public enum ReportType {

    PDF, HTML, EXCEL, RTF, CSV;

    public String getName() {
        return this.name();
    }
}
