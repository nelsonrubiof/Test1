/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.securitymanagement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marko.perich
 */
public class QueueManagerPermissions {

    public static final String GET_PENDING_EVALUATION_LIST_FOR_QUEUE_MANAGER_PERMISSION =
            "GET_PENDING_EVALUATION_LIST_FOR_QUEUE_MANAGER_PERMISSION";
    public static final String CHANGE_PRIORITY_PERMISSION = "CHANGE_PRIORITY_PERMISSION";
    public static final String REFRESH_QUEUE_FOR_QUEUE_MANAGER_PERMISSION = "REFRESH_QUEUE_FOR_QUEUE_MANAGER_PERMISSION";
    public static final String CHANGE_STATE_PERMISSION = "CHANGE_STATE_PERMISSION";
    public static final String GET_AREAS_PERMISSION = "GET_AREAS_PERMISSION";
    public static final String GET_STORES_FOR_QM_PERMISSION = "GET_STORES_FOR_QM_PERMISSION";
    public static final String BACK_TO_QUEUE_PERMISSION = "BACK_TO_QUEUE_PERMISSION";
    public static final String GET_SUMMARY_LIST_PERMISSION = "GET_SUMMARY_LIST_PERMISSION";
    public static final String GET_N_FIRST_ELEMENT_OF_QUEUE_LIST_PERMISSION = "GET_N_FIRST_ELEMENT_OF_QUEUE_LIST_PERMISSION";

    public static List<String> getPermissionsList() {
        List<String> permissions = new ArrayList<String>();
        permissions.add(GET_PENDING_EVALUATION_LIST_FOR_QUEUE_MANAGER_PERMISSION);
        permissions.add(CHANGE_PRIORITY_PERMISSION);
        permissions.add(REFRESH_QUEUE_FOR_QUEUE_MANAGER_PERMISSION);
        permissions.add(CHANGE_STATE_PERMISSION);
        permissions.add(GET_AREAS_PERMISSION);
        permissions.add(GET_STORES_FOR_QM_PERMISSION);
        permissions.add(BACK_TO_QUEUE_PERMISSION);
        permissions.add(GET_SUMMARY_LIST_PERMISSION);
        permissions.add(GET_N_FIRST_ELEMENT_OF_QUEUE_LIST_PERMISSION);
        return permissions;
    }
}
