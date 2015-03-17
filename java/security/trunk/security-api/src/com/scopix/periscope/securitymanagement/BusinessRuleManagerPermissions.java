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
public class BusinessRuleManagerPermissions {

    public static final String ADD_EVIDENCE_REQUEST_PERMISSION = "ADD_EVIDENCE_REQUEST_PERMISSION";
    public static final String ADD_METRIC_PERMISSION = "ADD_METRIC_PERMISSION";
    public static final String ADD_SITUATION_PERMISSION = "UPDATE_SITUATION_PERMISSION";
    public static final String GET_EVIDENCE_REQUEST_PERMISSION = "GET_EVIDENCE_REQUEST_PERMISSION";
    public static final String GET_EVIDENCE_REQUEST_LIST_PERMISSION = "GET_EVIDENCE_REQUEST_LIST_PERMISSION";
    public static final String GET_FREE_EVIDENCE_REQUEST_LIST_PERMISSION = "GET_FREE_EVIDENCE_REQUEST_LIST_PERMISSION";
    public static final String GET_FREE_METRIC_LIST_PERMISSION = "GET_FREE_METRIC_LIST_PERMISSION";
    public static final String GET_METRIC_PERMISSION = "GET_METRIC_PERMISSION";
    public static final String GET_METRIC_LIST_PERMISSION = "GET_METRIC_LIST_PERMISSION";
    public static final String GET_SITUATION_PERMISSION = "GET_SITUATION_PERMISSION";
    public static final String GET_SITUATION_LIST_PERMISSION = "GET_SITUATION_LIST_PERMISSION";
    public static final String REMOVE_EVIDENCE_REQUEST_PERSMISSION = "REMOVE_EVIDENCE_REQUEST_PERSMISSION";
    public static final String REMOVE_METRIC_PERMISSION = "REMOVE_METRIC_PERMISSION";
    public static final String REMOVE_SITUATION_PERMISSION = "REMOVE_SITUATION_PERMISSION";
    public static final String UPDATE_EVIDENCE_REQUEST_PERMISSION = "UPDATE_EVIDENCE_REQUEST_PERMISSION";
    public static final String UPDATE_METRIC_PERMISSION = "UPDATE_METRIC_PERMISSION";
    public static final String UPDATE_SITUATION_PERMISSION = "UPDATE_SITUATION_PERMISSION";

    public static List<String> getPermissionsList() {

        List<String> permissions = new ArrayList<String>();
        permissions.add(ADD_EVIDENCE_REQUEST_PERMISSION);
        permissions.add(ADD_METRIC_PERMISSION);
        permissions.add(ADD_SITUATION_PERMISSION);
        permissions.add(GET_EVIDENCE_REQUEST_PERMISSION);
        permissions.add(GET_EVIDENCE_REQUEST_LIST_PERMISSION);
        permissions.add(GET_FREE_EVIDENCE_REQUEST_LIST_PERMISSION);
        permissions.add(GET_FREE_METRIC_LIST_PERMISSION);
        permissions.add(GET_METRIC_PERMISSION);
        permissions.add(GET_METRIC_LIST_PERMISSION);
        permissions.add(GET_SITUATION_PERMISSION);
        permissions.add(GET_SITUATION_LIST_PERMISSION);
        permissions.add(REMOVE_EVIDENCE_REQUEST_PERSMISSION);
        permissions.add(REMOVE_METRIC_PERMISSION);
        permissions.add(REMOVE_SITUATION_PERMISSION);
        permissions.add(UPDATE_EVIDENCE_REQUEST_PERMISSION);
        permissions.add(UPDATE_METRIC_PERMISSION);
        permissions.add(UPDATE_SITUATION_PERMISSION);

        return permissions;
    }
}
