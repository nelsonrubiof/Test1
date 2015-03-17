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
 *  ReportingManagerPermissions.java
 * 
 *  Created on 27-01-2011, 12:29:18 PM
 * 
 */
package com.scopix.periscope.securitymanagement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class ReportingManagerPermissions {

    public static final String GET_UPLOAD_PROCESS_PERMISSION = "GET_UPLOAD_PROCESS_PERMISSION";
    public static final String GET_UPLOAD_PROCESS_DETAIL_PERMISSION = "GET_UPLOAD_PROCESS_DETAIL_PERMISSION";
    public static final String ADD_UPLOAD_PROCESS_DETAIL_PERMISSION = "ADD_UPLOAD_PROCESS_DETAIL_PERMISSION";
    public static final String DELETE_UPLOAD_PROCESS_DETAIL_PERMISSION = "DELETE_UPLOAD_PROCESS_DETAIL_PERMISSION";
    public static final String UPLOAD_PROCESS_NOW_PERMISSION = "UPLOAD_PROCESS_NOW_PERMISSION";
    public static final String CANCEL_UPLOAD_PROCESS_PERMISSION = "CANCEL_UPLOAD_PROCESS_PERMISSION";

    public static List<String> getPermissionsList() {
        List<String> permissions = new ArrayList<String>();
        permissions.add(GET_UPLOAD_PROCESS_PERMISSION);
        permissions.add(GET_UPLOAD_PROCESS_DETAIL_PERMISSION);
        permissions.add(ADD_UPLOAD_PROCESS_DETAIL_PERMISSION);
        permissions.add(DELETE_UPLOAD_PROCESS_DETAIL_PERMISSION);
        permissions.add(UPLOAD_PROCESS_NOW_PERMISSION);
        permissions.add(CANCEL_UPLOAD_PROCESS_PERMISSION);
        return permissions;
    }
}
