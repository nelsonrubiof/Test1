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
 * OperatorQueueManagementPermissions.java
 *
 * Created on 25-03-2010, 03:42:35 PM
 *
 */
package com.scopix.periscope.securitymanagement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo Alvarez
 */
public class OperatorQueueManagementPermissions {

    public static final String GET_OPERATOR_QUEUE_PERMISSION = "GET_OPERATOR_QUEUE_PERMISSION";
    public static final String GET_OPERATOR_QUEUE_LIST_PERMISSION = "GET_OPERATOR_QUEUE_LIST_PERMISSION";
    public static final String ADD_OPERATOR_QUEUE_PERMISSION = "ADD_OPERATOR_QUEUE_PERMISSION";
    public static final String EDIT_OPERATOR_QUEUE_PERMISSION = "EDIT_OPERATOR_QUEUE_PERMISSION";
    public static final String DELETE_OPERATOR_QUEUE_PERMISSION = "DELETE_OPERATOR_QUEUE_PERMISSION";

    public static List<String> getPermissionsList() {
        List<String> permissions = new ArrayList<String>();
        permissions.add(GET_OPERATOR_QUEUE_PERMISSION);
        permissions.add(GET_OPERATOR_QUEUE_LIST_PERMISSION);
        permissions.add(ADD_OPERATOR_QUEUE_PERMISSION);
        permissions.add(EDIT_OPERATOR_QUEUE_PERMISSION);
        permissions.add(DELETE_OPERATOR_QUEUE_PERMISSION);

        return permissions;
    }
}
