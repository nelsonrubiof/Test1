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
public class EvaluationQueueManagerPermissions {

    public static final String DEQUEUE_PERMISSION = "DEQUEUE_PERMISSION";
    public static final String ENQUEUE_PERMISSION = "ENQUEUE_PERMISSION";
    public static final String ENQUEUE_LIST_PERMISSION = "ENQUEUE_LIST_PERMISSION";
    public static final String REFRESH_QUEUE_FOR_EVALUATION_QUEUE_MANAGER_PERMISSION =
            "REFRESH_QUEUE_FOR_EVALUATION_QUEUE_MANAGER_PERMISSION";

    public static List<String> getPermissionsList() {
        List<String> permissions = new ArrayList<String>();
        permissions.add(DEQUEUE_PERMISSION);
        permissions.add(ENQUEUE_PERMISSION);
        permissions.add(ENQUEUE_LIST_PERMISSION);
        permissions.add(REFRESH_QUEUE_FOR_EVALUATION_QUEUE_MANAGER_PERMISSION);
        return permissions;
    }
}
