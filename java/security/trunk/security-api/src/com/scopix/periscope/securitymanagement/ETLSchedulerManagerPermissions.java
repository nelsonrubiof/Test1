/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * ETLSchedulerManagerPermissions.java
 *
 * Created on 10-08-2009, 02:10:44 PM
 *
 */
package com.scopix.periscope.securitymanagement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class ETLSchedulerManagerPermissions {

    public static final String ADD_ETL_SERVICE_PERMISSION = "ADD_ETL_SERVICE_PERMISSION";
    public static final String EDIT_ETL_SERVICE_PERMISSION = "EDIT_ETL_SERVICE_PERMISSION";
    public static final String DELETE_ETL_SERVICE_PERMISSION = "DELETE_ETL_SERVICE_PERMISSION";
    public static final String GET_ETL_SERVICE_PERMISSION = "GET_ETL_SERVICE_PERMISSION";
    public static final String GET_ETL_SERVICE_LIST_PERMISSION = "GET_ETL_SERVICE_LIST_PERMISSION";
    public static final String EXECUTE_JOB_NOW_PERMISSION = "EXECUTE_JOB_NOW_PERMISSION";
    public static final String ACTIVATE_ETL_SERVICE_PERMISSION = "ACTIVATE_ETL_SERVICE_PERMISSION";
    public static List<String> getPermissionsList() {

        List<String> permissions = new ArrayList<String>();
        permissions.add(ADD_ETL_SERVICE_PERMISSION);
        permissions.add(EDIT_ETL_SERVICE_PERMISSION);
        permissions.add(DELETE_ETL_SERVICE_PERMISSION);
        permissions.add(GET_ETL_SERVICE_PERMISSION);
        permissions.add(GET_ETL_SERVICE_LIST_PERMISSION);
        permissions.add(EXECUTE_JOB_NOW_PERMISSION);
        permissions.add(ACTIVATE_ETL_SERVICE_PERMISSION);

        return permissions;
    }
}
